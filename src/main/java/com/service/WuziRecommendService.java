package com.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.JieshoujigouEntity;
import com.entity.WuzishenlingEntity;
import com.entity.WuzixinxiEntity;
import com.llm.DeepseekClient;
import com.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 物资智能推荐服务。
 * 说明：为接收机构物资信息列表页提供规则推荐和 DeepSeek 推荐兜底能力。
 */
@Service("wuziRecommendService")
public class WuziRecommendService {

    private static final int CANDIDATE_LIMIT = 10;
    private static final int RECOMMEND_LIMIT = 5;

    @Autowired
    private JieshoujigouService jieshoujigouService;

    @Autowired
    private WuzishenlingService wuzishenlingService;

    @Autowired
    private WuzixinxiService wuzixinxiService;

    @Autowired
    private DeepseekClient deepseekClient;
    /**
     * 为当前登录接收机构生成物资推荐结果，供前台物资信息列表页的智能推荐按钮调用。
     */
    public R recommend(HttpServletRequest request) {
        String tableName = String.valueOf(request.getSession().getAttribute("tableName"));
        String jigouzhanghao = String.valueOf(request.getSession().getAttribute("username"));
        if(!"jieshoujigou".equals(tableName) || StringUtils.isBlank(jigouzhanghao) || "null".equals(jigouzhanghao)) {
            return R.error(403, "仅接收机构可以使用智能推荐");
        }

        JieshoujigouEntity jigou = jieshoujigouService.selectOne(
            new EntityWrapper<JieshoujigouEntity>().eq("jigouzhanghao", jigouzhanghao)
        );
        if(jigou == null) {
            return R.error("未找到当前机构信息");
        }
        if(!"是".equals(jigou.getSfsh())) {
            return R.error("当前机构尚未审核通过，无法使用智能推荐");
        }

        List<WuzishenlingEntity> history = wuzishenlingService.selectList(
            new EntityWrapper<WuzishenlingEntity>().eq("jigouzhanghao", jigouzhanghao)
        );
        Map<String, Object> preference = buildPreference(history);

        List<WuzixinxiEntity> stockList = wuzixinxiService.selectList(
            new EntityWrapper<WuzixinxiEntity>().gt("wuzishuliang", 0).ge("baozhiqi", todayStart())
        );
        if(stockList == null || stockList.isEmpty()) {
            return R.ok().put("data", new ArrayList<Map<String, Object>>())
                .put("candidateCount", 0)
                .put("source", "rule")
                .put("sourceMessage", "暂无库存可用物资，未调用 DeepSeek")
                .put("message", "暂无库存可用物资");
        }

        List<Map<String, Object>> topCandidates = buildTopCandidates(stockList, preference);
        RecommendResult result = applyModelOrRuleFallback(jigou, preference, topCandidates);

        return R.ok().put("data", result.getRows())
            .put("candidateCount", topCandidates.size())
            .put("source", result.getSource())
            .put("sourceMessage", result.getMessage())
            .put("preference", preference);
    }

    private Map<String, Object> buildPreference(List<WuzishenlingEntity> history) {
        Map<String, Integer> categoryCount = new HashMap<String, Integer>();
        Map<String, Integer> nameCount = new HashMap<String, Integer>();
        Map<String, Integer> categoryQuantity = new HashMap<String, Integer>();
        int totalApplyCount = 0;
        int totalApplyQuantity = 0;

        if(history != null) {
            for(WuzishenlingEntity item : history) {
                if(item == null) {
                    continue;
                }
                totalApplyCount++;
                int quantity = item.getShenlingshuliang() == null ? 0 : item.getShenlingshuliang();
                totalApplyQuantity += quantity;
              /**
               * Service方法，服务WuziRecommend相关业务查询。
               */
                addCount(categoryCount, item.getWuzizhonglei(), 1);
              /**
               * Service方法，服务WuziRecommend相关业务查询。
               */
                addCount(nameCount, item.getWuzimingcheng(), 1);
              /**
               * Service方法，服务WuziRecommend相关业务查询。
               */
                addCount(categoryQuantity, item.getWuzizhonglei(), quantity);
            }
        }

        Map<String, Object> preference = new HashMap<String, Object>();
        preference.put("totalApplyCount", totalApplyCount);
        preference.put("totalApplyQuantity", totalApplyQuantity);
        preference.put("categoryCount", categoryCount);
        preference.put("nameCount", nameCount);
        preference.put("categoryQuantity", categoryQuantity);
        preference.put("topCategory", findTopKey(categoryCount));
        preference.put("topMaterial", findTopKey(nameCount));
        return preference;
    }

    private List<Map<String, Object>> buildTopCandidates(List<WuzixinxiEntity> stockList, Map<String, Object> preference) {
        List<Map<String, Object>> scored = new ArrayList<Map<String, Object>>();
        for(WuzixinxiEntity item : stockList) {
            Map<String, Object> row = toRecommendationRow(item);
            int score = calculateScore(item, preference);
            row.put("score", score);
            row.put("reason", buildRuleReason(item, preference));
            scored.add(row);
        }

        Collections.sort(scored, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Integer score1 = (Integer)o1.get("score");
                Integer score2 = (Integer)o2.get("score");
                int byScore = score2.compareTo(score1);
                if(byScore != 0) {
                    return byScore;
                }
                Integer stock1 = (Integer)o1.get("wuzishuliang");
                Integer stock2 = (Integer)o2.get("wuzishuliang");
                return stock2.compareTo(stock1);
            }
        });

        return new ArrayList<Map<String, Object>>(scored.subList(0, Math.min(CANDIDATE_LIMIT, scored.size())));
    }

    private RecommendResult applyModelOrRuleFallback(JieshoujigouEntity jigou,
                                                     Map<String, Object> preference,
                                                     List<Map<String, Object>> topCandidates) {
        DeepseekClient.RecommendResponse modelResponse = deepseekClient.recommendMaterialsWithStatus(jigou, preference, topCandidates);
        List<Map<String, Object>> checked = validateModelResult(modelResponse.getRecommendations(), topCandidates);
        String source = checked.isEmpty() ? "rule" : "deepseek";
        String message = checked.isEmpty() ? modelResponse.getMessage() : "DeepSeek 已参与推荐";
        if(checked.size() >= RECOMMEND_LIMIT) {
            /**
             * Service方法，服务WuziRecommend相关业务查询。
             */
            return new RecommendResult(checked, source, message);
        }

        Set<Long> usedIds = new HashSet<Long>();
        for(Map<String, Object> row : checked) {
            usedIds.add((Long)row.get("id"));
        }
        for(Map<String, Object> row : topCandidates) {
            Long id = (Long)row.get("id");
            if(usedIds.contains(id)) {
                continue;
            }
            checked.add(row);
            usedIds.add(id);
            if(checked.size() >= RECOMMEND_LIMIT) {
                break;
            }
        }
        /**
         * Service方法，服务WuziRecommend相关业务查询。
         */
        return new RecommendResult(checked, source, message);
    }

    private List<Map<String, Object>> validateModelResult(List<DeepseekClient.ModelRecommendation> modelResult,
                                                          List<Map<String, Object>> topCandidates) {
        List<Map<String, Object>> checked = new ArrayList<Map<String, Object>>();
        if(modelResult == null || modelResult.isEmpty()) {
            return checked;
        }

        Map<Long, Map<String, Object>> candidateMap = new LinkedHashMap<Long, Map<String, Object>>();
        for(Map<String, Object> candidate : topCandidates) {
            candidateMap.put((Long)candidate.get("id"), candidate);
        }

        Collections.sort(modelResult, new Comparator<DeepseekClient.ModelRecommendation>() {
            @Override
            public int compare(DeepseekClient.ModelRecommendation o1, DeepseekClient.ModelRecommendation o2) {
                Integer rank1 = o1.getRank() == null ? 999 : o1.getRank();
                Integer rank2 = o2.getRank() == null ? 999 : o2.getRank();
                return rank1.compareTo(rank2);
            }
        });

        Set<Long> usedIds = new HashSet<Long>();
        for(DeepseekClient.ModelRecommendation modelItem : modelResult) {
            if(modelItem == null || modelItem.getId() == null || usedIds.contains(modelItem.getId())) {
                continue;
            }
            Map<String, Object> candidate = candidateMap.get(modelItem.getId());
            if(candidate == null) {
                continue;
            }
            Map<String, Object> row = new HashMap<String, Object>(candidate);
            if(StringUtils.isNotBlank(modelItem.getReason())) {
                row.put("reason", limitText(modelItem.getReason(), 80));
            }
            checked.add(row);
            usedIds.add(modelItem.getId());
            if(checked.size() >= RECOMMEND_LIMIT) {
                break;
            }
        }
        return checked;
    }

    @SuppressWarnings("unchecked")
    private int calculateScore(WuzixinxiEntity item, Map<String, Object> preference) {
        Map<String, Integer> categoryCount = (Map<String, Integer>)preference.get("categoryCount");
        Map<String, Integer> nameCount = (Map<String, Integer>)preference.get("nameCount");
        Map<String, Integer> categoryQuantity = (Map<String, Integer>)preference.get("categoryQuantity");

        int score = 10;
        score += Math.min(getCount(categoryCount, item.getWuzizhonglei()) * 25, 120);
        score += Math.min(getCount(nameCount, item.getWuzimingcheng()) * 35, 140);
        score += Math.min(getCount(categoryQuantity, item.getWuzizhonglei()) * 2, 80);
        score += Math.min(item.getWuzishuliang() == null ? 0 : item.getWuzishuliang() / 10, 40);

        Date expiry = item.getBaozhiqi();
        if(expiry != null) {
            long days = daysBetween(new Date(), expiry);
            if(days < 0) {
                score -= 50;
            } else if(days <= 30) {
                score += 15;
            } else if(days <= 90) {
                score += 8;
            }
        }
        return score;
    }

    @SuppressWarnings("unchecked")
    private String buildRuleReason(WuzixinxiEntity item, Map<String, Object> preference) {
        Map<String, Integer> categoryCount = (Map<String, Integer>)preference.get("categoryCount");
        Map<String, Integer> nameCount = (Map<String, Integer>)preference.get("nameCount");
        int sameName = getCount(nameCount, item.getWuzimingcheng());
        int sameCategory = getCount(categoryCount, item.getWuzizhonglei());
        int stock = item.getWuzishuliang() == null ? 0 : item.getWuzishuliang();

        if(sameName > 0) {
            return "该机构历史申领过" + item.getWuzimingcheng() + "，当前库存" + stock + "，适合继续补充。";
        }
        if(sameCategory > 0) {
            return "该机构历史偏好" + item.getWuzizhonglei() + "，当前物资库存可用，推荐优先关注。";
        }
        return "当前库存可用，物资类型可补充机构应急储备，适合作为备选申领。";
    }

    private Map<String, Object> toRecommendationRow(WuzixinxiEntity item) {
        Map<String, Object> row = new HashMap<String, Object>();
        row.put("id", item.getId());
        row.put("juanzengbianhao", item.getJuanzengbianhao());
        row.put("wuzimingcheng", item.getWuzimingcheng());
        row.put("wuzizhonglei", item.getWuzizhonglei());
        row.put("wuziguige", item.getWuziguige());
        row.put("wuzixiangqing", item.getWuzixiangqing());
        row.put("wuzitupian", item.getWuzitupian());
        row.put("wuzishuliang", item.getWuzishuliang() == null ? 0 : item.getWuzishuliang());
        row.put("baozhiqi", item.getBaozhiqi());
        row.put("cunchuweizhi", item.getCunchuweizhi());
        return row;
    }

    private void addCount(Map<String, Integer> map, String key, int count) {
        if(StringUtils.isBlank(key)) {
            return;
        }
        map.put(key, getCount(map, key) + count);
    }

    private int getCount(Map<String, Integer> map, String key) {
        if(map == null || StringUtils.isBlank(key) || map.get(key) == null) {
            return 0;
        }
        return map.get(key);
    }

    private String findTopKey(Map<String, Integer> map) {
        String topKey = "";
        int topValue = 0;
        for(Map.Entry<String, Integer> entry : map.entrySet()) {
            if(entry.getValue() != null && entry.getValue() > topValue) {
                topKey = entry.getKey();
                topValue = entry.getValue();
            }
        }
        return topKey;
    }

    private long daysBetween(Date start, Date end) {
        Calendar s = Calendar.getInstance();
        s.setTime(start);
        Calendar e = Calendar.getInstance();
        e.setTime(end);
        long diff = e.getTimeInMillis() - s.getTimeInMillis();
        return diff / (24L * 60L * 60L * 1000L);
    }

    private String limitText(String text, int maxLength) {
        if(text == null || text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength);
    }

    private Date todayStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private static class RecommendResult {
        private List<Map<String, Object>> rows;
        private String source;
        private String message;

        RecommendResult(List<Map<String, Object>> rows, String source, String message) {
            this.rows = rows;
            this.source = source;
            this.message = message;
        }

        public List<Map<String, Object>> getRows() {
            return rows;
        }

        public String getSource() {
            return source;
        }

        public String getMessage() {
            return message;
        }
    }
}
