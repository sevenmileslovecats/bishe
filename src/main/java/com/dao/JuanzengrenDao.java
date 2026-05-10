package com.dao;

import com.entity.JuanzengrenEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.JuanzengrenVO;
import com.entity.view.JuanzengrenView;


/**
 * 捐赠人 DAO 层。
 * 说明：对应数据库表的 MyBatis-Plus 基础 CRUD，并提供页面展示、统计图表所需的自定义查询。
 */
public interface JuanzengrenDao extends BaseMapper<JuanzengrenEntity> {

	/**
	 * 查询捐赠人VO 列表，给需要精简字段的业务场景使用。
	 */
	List<JuanzengrenVO> selectListVO(@Param("ew") Wrapper<JuanzengrenEntity> wrapper);

	/**
	 * 查询单条捐赠人VO 数据。
	 */
	JuanzengrenVO selectVO(@Param("ew") Wrapper<JuanzengrenEntity> wrapper);

	/**
	 * 查询捐赠人视图列表，包含页面展示需要的扩展字段。
	 */
	List<JuanzengrenView> selectListView(@Param("ew") Wrapper<JuanzengrenEntity> wrapper);

	/**
	 * 查询捐赠人视图列表，包含页面展示需要的扩展字段。
	 */
	List<JuanzengrenView> selectListView(Pagination page,@Param("ew") Wrapper<JuanzengrenEntity> wrapper);


	/**
	 * 查询单条捐赠人视图数据。
	 */
	JuanzengrenView selectView(@Param("ew") Wrapper<JuanzengrenEntity> wrapper);


}
