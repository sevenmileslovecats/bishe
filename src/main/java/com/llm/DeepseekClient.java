package com.llm;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.entity.JieshoujigouEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DeepSeek recommendation client.
 */
@Component
public class DeepseekClient {

    @Value("${deepseek.api-key:}")
    private String apiKey;

    @Value("${deepseek.base-url:https://api.deepseek.com}")
    private String baseUrl;

    @Value("${deepseek.model:deepseek-v4-flash}")
    private String model;

    @Value("${deepseek.timeout-ms:20000}")
    private int timeoutMs;

    public List<ModelRecommendation> recommendMaterials(JieshoujigouEntity jigou,
                                                        Map<String, Object> preference,
                                                        List<Map<String, Object>> candidates) {
        return recommendMaterialsWithStatus(jigou, preference, candidates).getRecommendations();
    }

    public RecommendResponse recommendMaterialsWithStatus(JieshoujigouEntity jigou,
                                                          Map<String, Object> preference,
                                                          List<Map<String, Object>> candidates) {
        String currentApiKey = currentApiKey();
        if(StringUtils.isBlank(currentApiKey) || candidates == null || candidates.isEmpty()) {
            return RecommendResponse.fail("未配置 DeepSeek API Key，已使用规则推荐");
        }

        try {
            JSONObject requestBody = buildRequestBody(jigou, preference, candidates);
            JSONObject response = postChatCompletion(requestBody, currentApiKey);
            String content = response.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
            List<ModelRecommendation> recommendations = parseRecommendations(content);
            if(recommendations.isEmpty()) {
                return RecommendResponse.fail("DeepSeek 未返回有效推荐结果，已使用规则推荐");
            }
            return RecommendResponse.ok(recommendations);
        } catch (Exception e) {
            return RecommendResponse.fail("DeepSeek 调用失败：" + safeError(e));
        }
    }

    private JSONObject buildRequestBody(JieshoujigouEntity jigou,
                                        Map<String, Object> preference,
                                        List<Map<String, Object>> candidates) {
        JSONObject body = new JSONObject();
        body.put("model", model);
        body.put("stream", false);
        body.put("temperature", 0.2);
        body.put("max_tokens", 1200);

        JSONObject thinking = new JSONObject();
        thinking.put("type", "disabled");
        body.put("thinking", thinking);

        JSONObject responseFormat = new JSONObject();
        responseFormat.put("type", "json_object");
        body.put("response_format", responseFormat);

        JSONArray messages = new JSONArray();
        JSONObject system = new JSONObject();
        system.put("role", "system");
        system.put("content", "你是公益捐赠物资推荐专家。只能从候选物资中选择，不能编造物资，必须只输出JSON对象。");
        messages.add(system);

        JSONObject user = new JSONObject();
        user.put("role", "user");
        user.put("content", buildPrompt(jigou, preference, candidates));
        messages.add(user);
        body.put("messages", messages);
        return body;
    }

    private String buildPrompt(JieshoujigouEntity jigou,
                               Map<String, Object> preference,
                               List<Map<String, Object>> candidates) {
        JSONObject input = new JSONObject();
        JSONObject org = new JSONObject();
        org.put("jigouzhanghao", jigou.getJigouzhanghao());
        org.put("jigoumingcheng", jigou.getJigoumingcheng());
        org.put("quyu", jigou.getQuyu());
        org.put("jigoujianjie", jigou.getJigoujianjie());
        input.put("机构信息", org);
        input.put("历史偏好", preference);

        JSONArray candidateArray = new JSONArray();
        for(Map<String, Object> candidate : candidates) {
            JSONObject item = new JSONObject();
            item.put("id", candidate.get("id"));
            item.put("wuzimingcheng", candidate.get("wuzimingcheng"));
            item.put("wuzizhonglei", candidate.get("wuzizhonglei"));
            item.put("wuziguige", candidate.get("wuziguige"));
            item.put("wuzishuliang", candidate.get("wuzishuliang"));
            item.put("baozhiqi", candidate.get("baozhiqi"));
            item.put("ruleScore", candidate.get("score"));
            candidateArray.add(item);
        }
        input.put("候选物资Top10", candidateArray);

        return "请基于机构信息、历史偏好、候选物资Top10，从候选物资中选出最适合申领的Top5。\n"
            + "要求：\n"
            + "1. 只能使用候选物资中的id，不得新增或修改id。\n"
            + "2. 推荐理由要结合历史偏好、区域/机构特点、库存和物资用途，80字以内。\n"
            + "3. 只返回JSON对象，格式必须为：{\"recommendations\":[{\"id\":数字,\"rank\":1,\"reason\":\"理由\"}]}。\n"
            + "4. rank从1开始，最多5条，不要输出任何解释文字。\n"
            + input.toJSONString();
    }

    private JSONObject postChatCompletion(JSONObject requestBody, String currentApiKey) throws Exception {
        String endpoint = normalizeBaseUrl(baseUrl) + "/chat/completions";
        HttpURLConnection conn = (HttpURLConnection)new URL(endpoint).openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(timeoutMs);
        conn.setReadTimeout(timeoutMs);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestProperty("Authorization", "Bearer " + currentApiKey);

        byte[] payload = requestBody.toJSONString().getBytes(StandardCharsets.UTF_8);
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(payload);
        outputStream.flush();
        outputStream.close();

        int code = conn.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
            code >= 200 && code < 300 ? conn.getInputStream() : conn.getErrorStream(),
            StandardCharsets.UTF_8
        ));
        StringBuilder responseText = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null) {
            responseText.append(line);
        }
        reader.close();
        conn.disconnect();

        if(code < 200 || code >= 300) {
            throw new RuntimeException("DeepSeek request failed: " + code + " " + responseText.toString());
        }
        return JSONObject.parseObject(responseText.toString());
    }

    private String currentApiKey() {
        if(StringUtils.isNotBlank(apiKey)) {
            return apiKey.trim();
        }
        String envApiKey = System.getenv("DEEPSEEK_API_KEY");
        return StringUtils.isBlank(envApiKey) ? "" : envApiKey.trim();
    }

    private String safeError(Exception e) {
        if(e == null || StringUtils.isBlank(e.getMessage())) {
            return "未知错误，已使用规则推荐";
        }
        String message = e.getMessage();
        if(message.length() > 120) {
            message = message.substring(0, 120);
        }
        return message;
    }

    private List<ModelRecommendation> parseRecommendations(String content) {
        List<ModelRecommendation> list = new ArrayList<ModelRecommendation>();
        if(StringUtils.isBlank(content)) {
            return list;
        }

        JSONObject json = JSONObject.parseObject(content);
        JSONArray array = json.getJSONArray("recommendations");
        if(array == null) {
            return list;
        }
        for(int i = 0; i < array.size(); i++) {
            JSONObject item = array.getJSONObject(i);
            ModelRecommendation recommendation = new ModelRecommendation();
            recommendation.setId(item.getLong("id"));
            recommendation.setRank(item.getInteger("rank"));
            recommendation.setReason(item.getString("reason"));
            list.add(recommendation);
        }
        return list;
    }

    private String normalizeBaseUrl(String url) {
        if(StringUtils.isBlank(url)) {
            return "https://api.deepseek.com";
        }
        if(url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }

    public static class ModelRecommendation {
        private Long id;
        private Integer rank;
        private String reason;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    public static class RecommendResponse {
        private boolean success;
        private String message;
        private List<ModelRecommendation> recommendations;

        static RecommendResponse ok(List<ModelRecommendation> recommendations) {
            RecommendResponse response = new RecommendResponse();
            response.success = true;
            response.message = "DeepSeek 已参与推荐";
            response.recommendations = recommendations;
            return response;
        }

        static RecommendResponse fail(String message) {
            RecommendResponse response = new RecommendResponse();
            response.success = false;
            response.message = message;
            response.recommendations = new ArrayList<ModelRecommendation>();
            return response;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public List<ModelRecommendation> getRecommendations() {
            return recommendations;
        }
    }
}
