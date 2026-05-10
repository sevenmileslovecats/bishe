
package com.dao;

import java.util.List;
import java.util.Map;

/**
 * 通用业务 DAO 层。
 * 说明：对应数据库表的 MyBatis-Plus 基础 CRUD，并提供页面展示、统计图表所需的自定义查询。
 */
public interface CommonDao{
	/**
	 * DAO方法，服务通用业务相关业务查询。
	 */
	List<String> getOption(Map<String, Object> params);

	/**
	 * DAO方法，服务通用业务相关业务查询。
	 */
	Map<String, Object> getFollowByOption(Map<String, Object> params);

	/**
	 * DAO方法，服务通用业务相关业务查询。
	 */
	List<String> getFollowByOption2(Map<String, Object> params);

	/**
	 * DAO方法，服务通用业务相关业务查询。
	 */
	void sh(Map<String, Object> params);

	/**
	 * DAO方法，服务通用业务相关业务查询。
	 */
	int remindCount(Map<String, Object> params);

	/**
	 * DAO方法，服务通用业务相关业务查询。
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
	 * DAO方法，服务通用业务相关业务查询。
	 */
	int updateColumn(Map<String, Object> params);

	/**
	 * DAO方法，服务通用业务相关业务查询。
	 */
	int deleteColumn(Map<String, Object> params);
}
