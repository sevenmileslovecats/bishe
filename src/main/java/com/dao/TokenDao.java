
package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.TokenEntity;

/**
 * 登录令牌 DAO 层。
 * 说明：对应数据库表的 MyBatis-Plus 基础 CRUD，并提供页面展示、统计图表所需的自定义查询。
 */
public interface TokenDao extends BaseMapper<TokenEntity> {

	/**
	 * 查询登录令牌视图列表，包含页面展示需要的扩展字段。
	 */
	List<TokenEntity> selectListView(@Param("ew") Wrapper<TokenEntity> wrapper);

	/**
	 * 查询登录令牌视图列表，包含页面展示需要的扩展字段。
	 */
	List<TokenEntity> selectListView(Pagination page,@Param("ew") Wrapper<TokenEntity> wrapper);

}
