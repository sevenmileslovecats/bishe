package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.JieshouxinxiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.JieshouxinxiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.JieshouxinxiView;


/**
 * 接收信息 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface JieshouxinxiService extends IService<JieshouxinxiEntity> {

    /**
     * 分页查询接收信息数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询接收信息VO 列表，给需要精简字段的业务场景使用。
     */
    List<JieshouxinxiVO> selectListVO(Wrapper<JieshouxinxiEntity> wrapper);

    /**
     * 查询单条接收信息VO 数据。
     */
    JieshouxinxiVO selectVO(@Param("ew") Wrapper<JieshouxinxiEntity> wrapper);

    /**
     * 查询接收信息视图列表，包含页面展示需要的扩展字段。
     */
    List<JieshouxinxiView> selectListView(Wrapper<JieshouxinxiEntity> wrapper);

    /**
     * 查询单条接收信息视图数据。
     */
    JieshouxinxiView selectView(@Param("ew") Wrapper<JieshouxinxiEntity> wrapper);

    /**
     * 分页查询接收信息数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params,Wrapper<JieshouxinxiEntity> wrapper);



}

