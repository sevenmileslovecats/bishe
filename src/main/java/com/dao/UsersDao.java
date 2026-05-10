package com.dao;

import com.entity.UsersEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.UsersVO;
import com.entity.view.UsersView;


/**
 * 管理员 DAO 层。
 * 说明：对应数据库表的 MyBatis-Plus 基础 CRUD，并提供页面展示、统计图表所需的自定义查询。
 */
public interface UsersDao extends BaseMapper<UsersEntity> {

	/**
	 * 查询管理员VO 列表，给需要精简字段的业务场景使用。
	 */
	List<UsersVO> selectListVO(@Param("ew") Wrapper<UsersEntity> wrapper);

	/**
	 * 查询单条管理员VO 数据。
	 */
	UsersVO selectVO(@Param("ew") Wrapper<UsersEntity> wrapper);

	/**
	 * 查询管理员视图列表，包含页面展示需要的扩展字段。
	 */
	List<UsersView> selectListView(@Param("ew") Wrapper<UsersEntity> wrapper);

	/**
	 * 查询管理员视图列表，包含页面展示需要的扩展字段。
	 */
	List<UsersView> selectListView(Pagination page,@Param("ew") Wrapper<UsersEntity> wrapper);


	/**
	 * 查询单条管理员视图数据。
	 */
	UsersView selectView(@Param("ew") Wrapper<UsersEntity> wrapper);


}
