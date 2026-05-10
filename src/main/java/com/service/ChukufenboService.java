package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ChukufenboEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.ChukufenboVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.ChukufenboView;


/**
 * 出库分拨 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface ChukufenboService extends IService<ChukufenboEntity> {

    /**
     * 分页查询出库分拨数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询出库分拨VO 列表，给需要精简字段的业务场景使用。
     */
    List<ChukufenboVO> selectListVO(Wrapper<ChukufenboEntity> wrapper);

    /**
     * 查询单条出库分拨VO 数据。
     */
    ChukufenboVO selectVO(@Param("ew") Wrapper<ChukufenboEntity> wrapper);

    /**
     * 查询出库分拨视图列表，包含页面展示需要的扩展字段。
     */
    List<ChukufenboView> selectListView(Wrapper<ChukufenboEntity> wrapper);

    /**
     * 查询单条出库分拨视图数据。
     */
    ChukufenboView selectView(@Param("ew") Wrapper<ChukufenboEntity> wrapper);

    /**
     * 分页查询出库分拨数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params,Wrapper<ChukufenboEntity> wrapper);



}

