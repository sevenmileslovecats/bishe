package com.dao;

import com.entity.JieshoujigouEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.JieshoujigouVO;
import com.entity.view.JieshoujigouView;


/**
 * 接收机构 DAO 层。
 * 说明：对应数据库表的 MyBatis-Plus 基础 CRUD，并提供页面展示、统计图表所需的自定义查询。
 */
public interface JieshoujigouDao extends BaseMapper<JieshoujigouEntity> {

	/**
	 * 查询接收机构VO 列表，给需要精简字段的业务场景使用。
	 */
	List<JieshoujigouVO> selectListVO(@Param("ew") Wrapper<JieshoujigouEntity> wrapper);

	/**
	 * 查询单条接收机构VO 数据。
	 */
	JieshoujigouVO selectVO(@Param("ew") Wrapper<JieshoujigouEntity> wrapper);

	/**
	 * 查询接收机构视图列表，包含页面展示需要的扩展字段。
	 */
	List<JieshoujigouView> selectListView(@Param("ew") Wrapper<JieshoujigouEntity> wrapper);

	/**
	 * 查询接收机构视图列表，包含页面展示需要的扩展字段。
	 */
	List<JieshoujigouView> selectListView(Pagination page,@Param("ew") Wrapper<JieshoujigouEntity> wrapper);


	/**
	 * 查询单条接收机构视图数据。
	 */
	JieshoujigouView selectView(@Param("ew") Wrapper<JieshoujigouEntity> wrapper);


}
