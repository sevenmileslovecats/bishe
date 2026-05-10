package com.dao;

import com.entity.DiscussshiyongfankuiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.DiscussshiyongfankuiVO;
import com.entity.view.DiscussshiyongfankuiView;


/**
 * 使用反馈评论 DAO 层。
 * 说明：对应数据库表的 MyBatis-Plus 基础 CRUD，并提供页面展示、统计图表所需的自定义查询。
 */
public interface DiscussshiyongfankuiDao extends BaseMapper<DiscussshiyongfankuiEntity> {

	/**
	 * 查询使用反馈评论VO 列表，给需要精简字段的业务场景使用。
	 */
	List<DiscussshiyongfankuiVO> selectListVO(@Param("ew") Wrapper<DiscussshiyongfankuiEntity> wrapper);

	/**
	 * 查询单条使用反馈评论VO 数据。
	 */
	DiscussshiyongfankuiVO selectVO(@Param("ew") Wrapper<DiscussshiyongfankuiEntity> wrapper);

	/**
	 * 查询使用反馈评论视图列表，包含页面展示需要的扩展字段。
	 */
	List<DiscussshiyongfankuiView> selectListView(@Param("ew") Wrapper<DiscussshiyongfankuiEntity> wrapper);

	/**
	 * 查询使用反馈评论视图列表，包含页面展示需要的扩展字段。
	 */
	List<DiscussshiyongfankuiView> selectListView(Pagination page,@Param("ew") Wrapper<DiscussshiyongfankuiEntity> wrapper);


	/**
	 * 查询单条使用反馈评论视图数据。
	 */
	DiscussshiyongfankuiView selectView(@Param("ew") Wrapper<DiscussshiyongfankuiEntity> wrapper);


}
