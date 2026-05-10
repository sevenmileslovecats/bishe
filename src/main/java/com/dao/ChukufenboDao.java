package com.dao;

import com.entity.ChukufenboEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.ChukufenboVO;
import com.entity.view.ChukufenboView;


/**
 * 出库分拨 DAO 层。
 * 说明：对应数据库表的 MyBatis-Plus 基础 CRUD，并提供页面展示、统计图表所需的自定义查询。
 */
public interface ChukufenboDao extends BaseMapper<ChukufenboEntity> {

	/**
	 * 查询出库分拨VO 列表，给需要精简字段的业务场景使用。
	 */
	List<ChukufenboVO> selectListVO(@Param("ew") Wrapper<ChukufenboEntity> wrapper);

	/**
	 * 查询单条出库分拨VO 数据。
	 */
	ChukufenboVO selectVO(@Param("ew") Wrapper<ChukufenboEntity> wrapper);

	/**
	 * 查询出库分拨视图列表，包含页面展示需要的扩展字段。
	 */
	List<ChukufenboView> selectListView(@Param("ew") Wrapper<ChukufenboEntity> wrapper);

	/**
	 * 查询出库分拨视图列表，包含页面展示需要的扩展字段。
	 */
	List<ChukufenboView> selectListView(Pagination page,@Param("ew") Wrapper<ChukufenboEntity> wrapper);


	/**
	 * 查询单条出库分拨视图数据。
	 */
	ChukufenboView selectView(@Param("ew") Wrapper<ChukufenboEntity> wrapper);


}
