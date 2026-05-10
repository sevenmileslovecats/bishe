package com.dao;

import com.entity.WuzixinxiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.WuzixinxiVO;
import com.entity.view.WuzixinxiView;


/**
 * 物资信息 DAO 层。
 * 说明：对应数据库表的 MyBatis-Plus 基础 CRUD，并提供页面展示、统计图表所需的自定义查询。
 */
public interface WuzixinxiDao extends BaseMapper<WuzixinxiEntity> {

	/**
	 * 查询物资信息VO 列表，给需要精简字段的业务场景使用。
	 */
	List<WuzixinxiVO> selectListVO(@Param("ew") Wrapper<WuzixinxiEntity> wrapper);

	/**
	 * 查询单条物资信息VO 数据。
	 */
	WuzixinxiVO selectVO(@Param("ew") Wrapper<WuzixinxiEntity> wrapper);

	/**
	 * 查询物资信息视图列表，包含页面展示需要的扩展字段。
	 */
	List<WuzixinxiView> selectListView(@Param("ew") Wrapper<WuzixinxiEntity> wrapper);

	/**
	 * 查询物资信息视图列表，包含页面展示需要的扩展字段。
	 */
	List<WuzixinxiView> selectListView(Pagination page,@Param("ew") Wrapper<WuzixinxiEntity> wrapper);


	/**
	 * 查询单条物资信息视图数据。
	 */
	WuzixinxiView selectView(@Param("ew") Wrapper<WuzixinxiEntity> wrapper);


    /**
     * 查询物资信息数值统计结果。
     */
    List<Map<String, Object>> selectValue(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<WuzixinxiEntity> wrapper);

    /**
     * 按时间维度查询物资信息统计结果。
     */
    List<Map<String, Object>> selectTimeStatValue(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<WuzixinxiEntity> wrapper);

    /**
     * 按字段分组查询物资信息统计结果。
     */
    List<Map<String, Object>> selectGroup(@Param("params") Map<String, Object> params,@Param("ew") Wrapper<WuzixinxiEntity> wrapper);



}
