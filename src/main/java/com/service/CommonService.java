package com.service;

import java.util.List;
import java.util.Map;

public interface CommonService {
	/**
	 * Service方法，服务通用业务相关业务查询。
	 */
	List<String> getOption(Map<String, Object> params);

	/**
	 * Service方法，服务通用业务相关业务查询。
	 */
	Map<String, Object> getFollowByOption(Map<String, Object> params);

	/**
	 * Service方法，服务通用业务相关业务查询。
	 */
	void sh(Map<String, Object> params);

	/**
	 * Service方法，服务通用业务相关业务查询。
	 */
	int remindCount(Map<String, Object> params);

	/**
	 * Service方法，服务通用业务相关业务查询。
	 */
	Map<String, Object> selectCal(Map<String, Object> params);

	/**
	 * 按字段分组查询通用业务统计结果。
	 */
	List<Map<String, Object>> selectGroup(Map<String, Object> params);

	/**
	 * 查询通用业务数值统计结果。
	 */
	List<Map<String, Object>> selectValue(Map<String, Object> params);

	/**
	 * 按时间维度查询通用业务统计结果。
	 */
	List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params);

	/**
	 * Service方法，服务通用业务相关业务查询。
	 */
	Boolean updateColumn(Map<String, Object> params);

	/**
	 * Service方法，服务通用业务相关业务查询。
	 */
	Boolean deleteColumn(Map<String, Object> params);
}
