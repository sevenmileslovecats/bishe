package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.UsersEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.UsersVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.UsersView;


/**
 * 管理员 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface UsersService extends IService<UsersEntity> {

    /**
     * 分页查询管理员数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询管理员VO 列表，给需要精简字段的业务场景使用。
     */
    List<UsersVO> selectListVO(Wrapper<UsersEntity> wrapper);

    /**
     * 查询单条管理员VO 数据。
     */
    UsersVO selectVO(@Param("ew") Wrapper<UsersEntity> wrapper);

    /**
     * 查询管理员视图列表，包含页面展示需要的扩展字段。
     */
    List<UsersView> selectListView(Wrapper<UsersEntity> wrapper);

    /**
     * 查询单条管理员视图数据。
     */
    UsersView selectView(@Param("ew") Wrapper<UsersEntity> wrapper);

    /**
     * 分页查询管理员数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params,Wrapper<UsersEntity> wrapper);



}

