
package com.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.TokenEntity;
import com.utils.PageUtils;


/**
 * 登录令牌 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface TokenService extends IService<TokenEntity> {
    /**
     * 分页查询登录令牌数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询登录令牌视图列表，包含页面展示需要的扩展字段。
     */
    List<TokenEntity> selectListView(Wrapper<TokenEntity> wrapper);

    /**
     * 分页查询登录令牌数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params,Wrapper<TokenEntity> wrapper);

    /**
     * Service方法，服务登录令牌相关业务查询。
     */
    String generateToken(Long userid,String username,String tableName, String role);

    /**
     * Service方法，服务登录令牌相关业务查询。
     */
    TokenEntity getTokenEntity(String token);
}
