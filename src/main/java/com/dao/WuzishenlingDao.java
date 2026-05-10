package com.dao;

import com.entity.WuzishenlingEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.WuzishenlingVO;
import com.entity.view.WuzishenlingView;


/**
 * 物资申领 DAO 层。
 * 说明：对应数据库表的 MyBatis-Plus 基础 CRUD，并提供页面展示、统计图表所需的自定义查询。
 */
public interface WuzishenlingDao extends BaseMapper<WuzishenlingEntity> {

	/**
	 * 查询物资申领VO 列表，给需要精简字段的业务场景使用。
	 */
	List<WuzishenlingVO> selectListVO(@Param("ew") Wrapper<WuzishenlingEntity> wrapper);

	/**
	 * 查询单条物资申领VO 数据。
	 */
	WuzishenlingVO selectVO(@Param("ew") Wrapper<WuzishenlingEntity> wrapper);

	/**
	 * 查询物资申领视图列表，包含页面展示需要的扩展字段。
	 */
	List<WuzishenlingView> selectListView(@Param("ew") Wrapper<WuzishenlingEntity> wrapper);

	/**
	 * 查询物资申领视图列表，包含页面展示需要的扩展字段。
	 */
	List<WuzishenlingView> selectListView(Pagination page,@Param("ew") Wrapper<WuzishenlingEntity> wrapper);


	/**
	 * 查询单条物资申领视图数据。
	 */
	WuzishenlingView selectView(@Param("ew") Wrapper<WuzishenlingEntity> wrapper);


    /**
     * 查询物资申领数值统计结果。
     */
    List<Map<String, Object>> selectValue(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<WuzishenlingEntity> wrapper);

    /**
     * 按时间维度查询物资申领统计结果。
     */
    List<Map<String, Object>> selectTimeStatValue(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<WuzishenlingEntity> wrapper);

    /**
     * 按字段分组查询物资申领统计结果。
     */
    List<Map<String, Object>> selectGroup(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<WuzishenlingEntity> wrapper);



}
