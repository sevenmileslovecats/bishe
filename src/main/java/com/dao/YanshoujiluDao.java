package com.dao;

import com.entity.YanshoujiluEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.YanshoujiluVO;
import com.entity.view.YanshoujiluView;


/**
 * 验收记录 DAO 层。
 * 说明：对应数据库表的 MyBatis-Plus 基础 CRUD，并提供页面展示、统计图表所需的自定义查询。
 */
public interface YanshoujiluDao extends BaseMapper<YanshoujiluEntity> {

	/**
	 * 查询验收记录VO 列表，给需要精简字段的业务场景使用。
	 */
	List<YanshoujiluVO> selectListVO(@Param("ew") Wrapper<YanshoujiluEntity> wrapper);

	/**
	 * 查询单条验收记录VO 数据。
	 */
	YanshoujiluVO selectVO(@Param("ew") Wrapper<YanshoujiluEntity> wrapper);

	/**
	 * 查询验收记录视图列表，包含页面展示需要的扩展字段。
	 */
	List<YanshoujiluView> selectListView(@Param("ew") Wrapper<YanshoujiluEntity> wrapper);

	/**
	 * 查询验收记录视图列表，包含页面展示需要的扩展字段。
	 */
	List<YanshoujiluView> selectListView(Pagination page,@Param("ew") Wrapper<YanshoujiluEntity> wrapper);


	/**
	 * 查询单条验收记录视图数据。
	 */
	YanshoujiluView selectView(@Param("ew") Wrapper<YanshoujiluEntity> wrapper);


}
