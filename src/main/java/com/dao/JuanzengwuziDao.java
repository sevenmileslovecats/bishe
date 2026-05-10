package com.dao;

import com.entity.JuanzengwuziEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.JuanzengwuziVO;
import com.entity.view.JuanzengwuziView;


/**
 * 捐赠物资 DAO 层。
 * 说明：对应数据库表的 MyBatis-Plus 基础 CRUD，并提供页面展示、统计图表所需的自定义查询。
 */
public interface JuanzengwuziDao extends BaseMapper<JuanzengwuziEntity> {

	/**
	 * 查询捐赠物资VO 列表，给需要精简字段的业务场景使用。
	 */
	List<JuanzengwuziVO> selectListVO(@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);

	/**
	 * 查询单条捐赠物资VO 数据。
	 */
	JuanzengwuziVO selectVO(@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);

	/**
	 * 查询捐赠物资视图列表，包含页面展示需要的扩展字段。
	 */
	List<JuanzengwuziView> selectListView(@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);

	/**
	 * 查询捐赠物资视图列表，包含页面展示需要的扩展字段。
	 */
	List<JuanzengwuziView> selectListView(Pagination page,@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);


	/**
	 * 查询单条捐赠物资视图数据。
	 */
	JuanzengwuziView selectView(@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);


    /**
     * 查询捐赠物资数值统计结果。
     */
    List<Map<String, Object>> selectValue(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);

    /**
     * 按时间维度查询捐赠物资统计结果。
     */
    List<Map<String, Object>> selectTimeStatValue(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);

    /**
     * 按字段分组查询捐赠物资统计结果。
     */
    List<Map<String, Object>> selectGroup(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);



}
