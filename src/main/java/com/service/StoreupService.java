package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.StoreupEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.StoreupVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.StoreupView;


/**
 * 收藏/评论 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface StoreupService extends IService<StoreupEntity> {

    /**
     * 分页查询收藏/评论数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询收藏/评论VO 列表，给需要精简字段的业务场景使用。
     */
    List<StoreupVO> selectListVO(Wrapper<StoreupEntity> wrapper);

    /**
     * 查询单条收藏/评论VO 数据。
     */
    StoreupVO selectVO(@Param("ew") Wrapper<StoreupEntity> wrapper);

    /**
     * 查询收藏/评论视图列表，包含页面展示需要的扩展字段。
     */
    List<StoreupView> selectListView(Wrapper<StoreupEntity> wrapper);

    /**
     * 查询单条收藏/评论视图数据。
     */
    StoreupView selectView(@Param("ew") Wrapper<StoreupEntity> wrapper);

    /**
     * 分页查询收藏/评论数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params,Wrapper<StoreupEntity> wrapper);



}

