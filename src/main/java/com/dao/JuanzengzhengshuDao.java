package com.dao;

import com.entity.JuanzengzhengshuEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.JuanzengzhengshuVO;
import com.entity.view.JuanzengzhengshuView;


/**
 * 捐赠证书 DAO 层。
 * 说明：对应数据库表的 MyBatis-Plus 基础 CRUD，并提供页面展示、统计图表所需的自定义查询。
 */
public interface JuanzengzhengshuDao extends BaseMapper<JuanzengzhengshuEntity> {

	/**
	 * 查询捐赠证书VO 列表，给需要精简字段的业务场景使用。
	 */
	List<JuanzengzhengshuVO> selectListVO(@Param("ew") Wrapper<JuanzengzhengshuEntity> wrapper);

	/**
	 * 查询单条捐赠证书VO 数据。
	 */
	JuanzengzhengshuVO selectVO(@Param("ew") Wrapper<JuanzengzhengshuEntity> wrapper);

	/**
	 * 查询捐赠证书视图列表，包含页面展示需要的扩展字段。
	 */
	List<JuanzengzhengshuView> selectListView(@Param("ew") Wrapper<JuanzengzhengshuEntity> wrapper);

	/**
	 * 查询捐赠证书视图列表，包含页面展示需要的扩展字段。
	 */
	List<JuanzengzhengshuView> selectListView(Pagination page,@Param("ew") Wrapper<JuanzengzhengshuEntity> wrapper);


	/**
	 * 查询单条捐赠证书视图数据。
	 */
	JuanzengzhengshuView selectView(@Param("ew") Wrapper<JuanzengzhengshuEntity> wrapper);


}
