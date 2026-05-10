package com.dao;

import com.entity.StoreupEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.StoreupVO;
import com.entity.view.StoreupView;


/**
 * 收藏/评论 DAO 层。
 * 说明：对应数据库表的 MyBatis-Plus 基础 CRUD，并提供页面展示、统计图表所需的自定义查询。
 */
public interface StoreupDao extends BaseMapper<StoreupEntity> {

	/**
	 * 查询收藏/评论VO 列表，给需要精简字段的业务场景使用。
	 */
	List<StoreupVO> selectListVO(@Param("ew") Wrapper<StoreupEntity> wrapper);

	/**
	 * 查询单条收藏/评论VO 数据。
	 */
	StoreupVO selectVO(@Param("ew") Wrapper<StoreupEntity> wrapper);

	/**
	 * 查询收藏/评论视图列表，包含页面展示需要的扩展字段。
	 */
	List<StoreupView> selectListView(@Param("ew") Wrapper<StoreupEntity> wrapper);

	/**
	 * 查询收藏/评论视图列表，包含页面展示需要的扩展字段。
	 */
	List<StoreupView> selectListView(Pagination page,@Param("ew") Wrapper<StoreupEntity> wrapper);


	/**
	 * 查询单条收藏/评论视图数据。
	 */
	StoreupView selectView(@Param("ew") Wrapper<StoreupEntity> wrapper);


}
