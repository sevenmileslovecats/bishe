package com.dao;

import com.entity.JieshouxinxiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.JieshouxinxiVO;
import com.entity.view.JieshouxinxiView;


/**
 * 接收信息 DAO 层。
 * 说明：对应数据库表的 MyBatis-Plus 基础 CRUD，并提供页面展示、统计图表所需的自定义查询。
 */
public interface JieshouxinxiDao extends BaseMapper<JieshouxinxiEntity> {

	/**
	 * 查询接收信息VO 列表，给需要精简字段的业务场景使用。
	 */
	List<JieshouxinxiVO> selectListVO(@Param("ew") Wrapper<JieshouxinxiEntity> wrapper);

	/**
	 * 查询单条接收信息VO 数据。
	 */
	JieshouxinxiVO selectVO(@Param("ew") Wrapper<JieshouxinxiEntity> wrapper);

	/**
	 * 查询接收信息视图列表，包含页面展示需要的扩展字段。
	 */
	List<JieshouxinxiView> selectListView(@Param("ew") Wrapper<JieshouxinxiEntity> wrapper);

	/**
	 * 查询接收信息视图列表，包含页面展示需要的扩展字段。
	 */
	List<JieshouxinxiView> selectListView(Pagination page,@Param("ew") Wrapper<JieshouxinxiEntity> wrapper);


	/**
	 * 查询单条接收信息视图数据。
	 */
	JieshouxinxiView selectView(@Param("ew") Wrapper<JieshouxinxiEntity> wrapper);


}
