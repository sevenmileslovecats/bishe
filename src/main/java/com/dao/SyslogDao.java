package com.dao;

import com.entity.SyslogEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.SyslogVO;
import com.entity.view.SyslogView;


/**
 * 系统日志 DAO 层。
 * 说明：对应数据库表的 MyBatis-Plus 基础 CRUD，并提供页面展示、统计图表所需的自定义查询。
 */
public interface SyslogDao extends BaseMapper<SyslogEntity> {

	/**
	 * 查询系统日志VO 列表，给需要精简字段的业务场景使用。
	 */
	List<SyslogVO> selectListVO(@Param("ew") Wrapper<SyslogEntity> wrapper);

	/**
	 * 查询单条系统日志VO 数据。
	 */
	SyslogVO selectVO(@Param("ew") Wrapper<SyslogEntity> wrapper);

	/**
	 * 查询系统日志视图列表，包含页面展示需要的扩展字段。
	 */
	List<SyslogView> selectListView(@Param("ew") Wrapper<SyslogEntity> wrapper);

	/**
	 * 查询系统日志视图列表，包含页面展示需要的扩展字段。
	 */
	List<SyslogView> selectListView(Pagination page,@Param("ew") Wrapper<SyslogEntity> wrapper);


	/**
	 * 查询单条系统日志视图数据。
	 */
	SyslogView selectView(@Param("ew") Wrapper<SyslogEntity> wrapper);


}
