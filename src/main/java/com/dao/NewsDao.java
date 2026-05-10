package com.dao;

import com.entity.NewsEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.NewsVO;
import com.entity.view.NewsView;


/**
 * 公告信息 DAO 层。
 * 说明：对应数据库表的 MyBatis-Plus 基础 CRUD，并提供页面展示、统计图表所需的自定义查询。
 */
public interface NewsDao extends BaseMapper<NewsEntity> {

	/**
	 * 查询公告信息VO 列表，给需要精简字段的业务场景使用。
	 */
	List<NewsVO> selectListVO(@Param("ew") Wrapper<NewsEntity> wrapper);

	/**
	 * 查询单条公告信息VO 数据。
	 */
	NewsVO selectVO(@Param("ew") Wrapper<NewsEntity> wrapper);

	/**
	 * 查询公告信息视图列表，包含页面展示需要的扩展字段。
	 */
	List<NewsView> selectListView(@Param("ew") Wrapper<NewsEntity> wrapper);

	/**
	 * 查询公告信息视图列表，包含页面展示需要的扩展字段。
	 */
	List<NewsView> selectListView(Pagination page,@Param("ew") Wrapper<NewsEntity> wrapper);


	/**
	 * 查询单条公告信息视图数据。
	 */
	NewsView selectView(@Param("ew") Wrapper<NewsEntity> wrapper);


}
