package com.dao;

import com.entity.ShiyongfankuiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.ShiyongfankuiVO;
import com.entity.view.ShiyongfankuiView;


/**
 * 使用反馈 DAO 层。
 * 说明：对应数据库表的 MyBatis-Plus 基础 CRUD，并提供页面展示、统计图表所需的自定义查询。
 */
public interface ShiyongfankuiDao extends BaseMapper<ShiyongfankuiEntity> {

	/**
	 * 查询使用反馈VO 列表，给需要精简字段的业务场景使用。
	 */
	List<ShiyongfankuiVO> selectListVO(@Param("ew") Wrapper<ShiyongfankuiEntity> wrapper);

	/**
	 * 查询单条使用反馈VO 数据。
	 */
	ShiyongfankuiVO selectVO(@Param("ew") Wrapper<ShiyongfankuiEntity> wrapper);

	/**
	 * 查询使用反馈视图列表，包含页面展示需要的扩展字段。
	 */
	List<ShiyongfankuiView> selectListView(@Param("ew") Wrapper<ShiyongfankuiEntity> wrapper);

	/**
	 * 查询使用反馈视图列表，包含页面展示需要的扩展字段。
	 */
	List<ShiyongfankuiView> selectListView(Pagination page,@Param("ew") Wrapper<ShiyongfankuiEntity> wrapper);


	/**
	 * 查询单条使用反馈视图数据。
	 */
	ShiyongfankuiView selectView(@Param("ew") Wrapper<ShiyongfankuiEntity> wrapper);


}
