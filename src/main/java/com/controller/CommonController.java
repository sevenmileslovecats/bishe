package com.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import com.hankcs.hanlp.HanLP;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.json.JSONArray;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.IgnoreAuth;
import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import com.baidu.aip.util.Base64Util;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.ConfigEntity;
import com.entity.WuzixinxiEntity;
import com.service.CommonService;
import com.service.ConfigService;
import com.service.WuzixinxiService;
import com.utils.*;

/**
 * 通用业务 模块后端接口。
 * 说明：供管理端、前台端对应页面通过 HTTP 请求调用。
 */
@RestController
public class CommonController{
	@Autowired
	private CommonService commonService;

	@Autowired
	private WuzixinxiService wuzixinxiService;

    private static AipFace client = null;

    @Autowired
    private ConfigService configService;


    /**
     * 功能：查询指定表字段的下拉选项。
     * 使用端：前后台新增/编辑表单的下拉框。
     * 前端触发：表单页通过 $http.get('option/{tableName}/{columnName}') 触发。
     */
	@IgnoreAuth
    @RequestMapping("/option/{tableName}/{columnName}")
	public R getOption(@PathVariable("tableName") String tableName, @PathVariable("columnName") String columnName,@RequestParam(required = false) String conditionColumn,@RequestParam(required = false) String conditionValue,String level,String parent, HttpServletRequest request) throws IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("table", tableName);
		params.put("column", columnName);
		if(StringUtils.isNotBlank(level)) {
			params.put("level", level);
		}
		if(StringUtils.isNotBlank(parent)) {
			params.put("parent", parent);
		}
        if(StringUtils.isNotBlank(conditionColumn)) {
            params.put("conditionColumn", conditionColumn);
        }
        if(StringUtils.isNotBlank(conditionValue)) {
            params.put("conditionValue", conditionValue);
        }
		params.putAll(CommonUtil.parseRequest(request));
		List<String> data = commonService.getOption(params);
		return R.ok().put("data", data);
	}

    /**
     * 功能：根据下拉选项联动查询关联字段。
     * 使用端：前后台表单联动字段。
     * 前端触发：表单页选择下拉项后触发。
     */
    @IgnoreAuth
    @RequestMapping("/follow/{tableName}/{columnName}")
	public R getFollowByOption(@PathVariable("tableName") String tableName, @PathVariable("columnName") String columnName, @RequestParam String columnValue) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("table", tableName);
		params.put("column", columnName);
		params.put("columnValue", columnValue);
		Map<String, Object> result = commonService.getFollowByOption(params);
        Object o = null;
        try {
            Class<?> c1 = Class.forName("com.entity."+tableName.substring(0, 1).toUpperCase()+tableName.substring(1)+"Entity");
            o = MapUtils.mapToObject(result, c1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok().put("data", o);
	}

    /**
     * 功能：通用审核接口。
     * 使用端：管理端带审核功能的列表页。
     * 前端触发：审核按钮通过 $http.post('sh/{tableName}') 触发。
     */
    @RequestMapping("/sh/{tableName}")
	public R sh(@PathVariable("tableName") String tableName, @RequestBody Map<String, Object> map) {
		map.put("table", tableName);
		commonService.sh(map);
		return R.ok();
	}

    /**
     * 功能：统计通用业务字段提醒数量。
     * 使用端：管理端/前台首页提醒和待办入口。
     * 前端触发：页面加载提醒数据时触发。
     */
    @IgnoreAuth
    @RequestMapping("/remind/{tableName}/{columnName}/{type}")
	public R remindCount(@PathVariable("tableName") String tableName, @PathVariable("columnName") String columnName,
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("table", tableName);
		map.put("column", columnName);
		map.put("type", type);

		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}

		int count = commonService.remindCount(map);
		return R.ok().put("count", count);
	}

    /**
     * 功能：通用字段求和统计。
     * 使用端：首页统计或模块统计区域。
     * 前端触发：统计组件通过 $http.get('cal/{tableName}/{columnName}') 触发。
     */
    @IgnoreAuth
    @RequestMapping("/cal/{tableName}/{columnName}")
	public R cal(@PathVariable("tableName") String tableName, @PathVariable("columnName") String columnName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("table", tableName);
		params.put("column", columnName);
		Map<String, Object> result = commonService.selectCal(params);
		return R.ok().put("data", result);
	}

    /**
     * 功能：按字段分组统计通用业务数据。
     * 使用端：管理端首页统计、前台统计图表。
     * 前端触发：图表组件通过 group 接口触发。
     */
    @IgnoreAuth
    @RequestMapping("/group/{tableName}/{columnName}")
	public R group(@PathVariable("tableName") String tableName, @PathVariable("columnName") String columnName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("table", tableName);
		params.put("column", columnName);
		List<Map<String, Object>> result = commonService.selectGroup(params);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(Map<String, Object> m : result) {
			for(String k : m.keySet()) {
				if(m.get(k) instanceof Date) {
					m.put(k, sdf.format((Date)m.get(k)));
				}
			}
		}
		return R.ok().put("data", result);
	}

    /**
     * 功能：生成通用词云统计数据。
     * 使用端：统计图表页面。
     * 前端触发：词云组件加载时触发。
     */
    @IgnoreAuth
    @RequestMapping("/wordCloud/{tableName}/{columnName}")
	public R wordCloud(@PathVariable("tableName") String tableName, @PathVariable("columnName") String columnName) {
		if (tableName == null || tableName.isEmpty() || columnName == null || columnName.isEmpty()) {
			return R.error("Table name and column name cannot be empty");
		}

		Map<String, Object> params = new HashMap<>();
		params.put("table", tableName);
		params.put("column", columnName);
		List<Map<String, Object>> result = new ArrayList<>();
		List<String> listText = commonService.selectGroup(params).stream().map(m -> m.get(columnName).toString()).collect(Collectors.toList());

		Map<String, Integer> keywordCounts = new HashMap<>();
		for (String text : listText) {
			List<String> phrases = HanLP.extractPhrase(text, 3);
			List<String> keywords = HanLP.extractKeyword(text, 3);

			// 合并两种结果
			Set<String> uniqueTerms = new HashSet<>();
			uniqueTerms.addAll(phrases);
			uniqueTerms.addAll(keywords);

			for (String term : uniqueTerms) {
				if (term.length() > 1) {
					keywordCounts.put(term, keywordCounts.getOrDefault(term, 0) + 1);
				}
			}
		}

		keywordCounts.forEach((key, value) -> {
			Map<String, Object> item = new HashMap<>();
			item.put(columnName, key);
			item.put("total", value);
			result.add(item);
		});

		return R.ok().put("data", result);
	}

    /**
     * 功能：通用库存/数量字段增减更新。
     * 使用端：业务表单提交后的联动更新。
     * 前端触发：保存、接收、分拨等操作后触发。
     */
    @RequestMapping("/updateColumn/{tableName}/{type}")
	public R updateColumn(@PathVariable("tableName") String tableName, @PathVariable("type") int type, HttpServletRequest request) throws IOException {
		Map<String, Object> params = new HashMap<>();
		params.put("tableName", tableName);
		params.put("type", type);
		params.putAll(CommonUtil.parseRequest(request));
		if("wuzixinxi".equals(tableName) && type == 2) {
			Object condCol = params.get("csuConditionColumn");
			Object condVal = params.get("csuConditionColumnValue");
			Object updateCol = params.get("csuUpdateColumn");
			Object updateVal = params.get("csuUpdateColumnValue");
			if("juanzengbianhao".equals(condCol) && "wuzishuliang".equals(updateCol) && condVal != null && updateVal != null) {
				String v = updateVal.toString();
				if(v.startsWith("-")) {
					int delta;
					try {
						delta = Integer.parseInt(v.substring(1));
					} catch (Exception e) {
						return R.error("数量格式错误");
					}
					EntityWrapper<WuzixinxiEntity> ew = new EntityWrapper<WuzixinxiEntity>();
					ew.eq(condCol.toString(), condVal.toString());
					WuzixinxiEntity exist = wuzixinxiService.selectOne(ew);
					if(exist == null) {
						return R.error("库存不足");
					}
					Integer base = exist.getWuzishuliang() == null ? 0 : exist.getWuzishuliang();
					if(base - delta < 0) {
						return R.error("库存不足");
					}
					exist.setWuzishuliang(base - delta);
					wuzixinxiService.updateById(exist);
					return R.ok("更新成功");
				}
			}
			return R.ok("更新成功");
		}
		if (type == 1) {
			Map<String, Object> params1 = new HashMap<>();
			params1.put("table", tableName);
			params1.put("column", params.get("csuConditionColumn"));
			params1.put("columnValue", params.get("csuConditionColumnValue"));
			Map<String, Object> res = commonService.getFollowByOption(params1);
			if (res != null && (res.get(params.get("csuUpdateColumn").toString())+"").equals(params.get("csuUpdateColumnValue").toString())) {
				return R.error("更新失败");
			}
		}
		if (commonService.updateColumn(params)) {
			return R.ok("更新成功");
		}
		return R.error("库存不足");
	}

    /**
     * 功能：通用库存/数量字段回滚更新。
     * 使用端：业务删除或撤销后的联动更新。
     * 前端触发：删除相关记录后触发。
     */
    @RequestMapping("/deleteColumn/{tableName}")
	public R deleteColumn(@PathVariable("tableName") String tableName, HttpServletRequest request) throws IOException {
		Map<String, Object> params = new HashMap<>();
		params.put("tableName", tableName);
		params.putAll(CommonUtil.parseRequest(request));
		if (commonService.deleteColumn(params)) {
			return R.ok("删除成功");
		}
		return R.error("删除失败");
	}

    /**
     * 功能：查询当前用户的评论收藏聚合列表。
     * 使用端：前台我的评论页面。
     * 前端触发：front/src/pages/storeup/list.vue 通过 $http.get('comment/list') 触发。
     */
    @RequestMapping("/comment/list")
	public R comment(HttpServletRequest request) throws Exception {
		List result = new ArrayList<>();
		List<String> tableNames = new ArrayList<>();
		tableNames.add("discussshiyongfankui");
		for (String tableName : tableNames) {
			EntityWrapper wrapper = new EntityWrapper();
			wrapper.setSqlSelect("*,'"+tableName.replace("discuss","")+"' AS tablename");
			if (!request.getSession().getAttribute("role").toString().equals("管理员")) {
				wrapper.eq("userid", request.getSession().getAttribute("userId"));
			}
			List list = (List) SpringContextUtils.getBeanMethod(tableName + "Service", "selectMaps", new Class[]{Wrapper.class}, new Object[]{wrapper});
			if (!list.isEmpty()) {
				result.addAll( list);
			}
		}
		return R.ok().put("data", result);
	}

    /**
     * 功能：统计通用业务图表数值。
     * 使用端：管理端首页统计、模块统计图表。
     * 前端触发：统计图组件通过 value 接口触发。
     */
    @IgnoreAuth
    @RequestMapping("/value/{tableName}/{xColumnName}/{yColumnName}")
	public R value(@PathVariable("tableName") String tableName, @PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("table", tableName);
		params.put("xColumn", xColumnName);
		params.put("yColumn", yColumnName);
		List<Map<String, Object>> result = commonService.selectValue(params);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(Map<String, Object> m : result) {
			for(String k : m.keySet()) {
				if(m.get(k) instanceof Date) {
					m.put(k, sdf.format((Date)m.get(k)));
				}
			}
		}
		return R.ok().put("data", result);
	}

    /**
     * 功能：统计通用业务图表数值。
     * 使用端：管理端首页统计、模块统计图表。
     * 前端触发：统计图组件通过 value 接口触发。
     */
    @IgnoreAuth
    @RequestMapping("/value/{tableName}/{xColumnName}/{yColumnName}/{timeStatType}")
	public R valueDay(@PathVariable("tableName") String tableName, @PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("table", tableName);
		params.put("xColumn", xColumnName);
		params.put("yColumn", yColumnName);
		params.put("timeStatType", timeStatType);
		List<Map<String, Object>> result = commonService.selectTimeStatValue(params);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(Map<String, Object> m : result) {
			for(String k : m.keySet()) {
				if(m.get(k) instanceof Date) {
					m.put(k, sdf.format((Date)m.get(k)));
				}
			}
		}
		return R.ok().put("data", result);
	}




	/*
	 * 百度百科
	 */
    /**
     * 功能：按名称查询百科信息。
     * 使用端：前台详情页扩展信息区域。
     * 前端触发：前端通过 $http.get('baike/{name}') 触发。
     */
	@RequestMapping("/baike/{name}")
    @IgnoreAuth
	public R baike(@PathVariable("name") String name) {
		String url = "https://baike.baidu.com/api/openapi/BaikeLemmaCardApi?scope=103&format=json&appid=379020&bk_key=%s&bk_length=800";
		String response = HttpClientUtils.doGet(String.format(url, name));

		String content = new JSONObject(response).getString("abstract");
		return R.ok().put("data", content);
	}
}
