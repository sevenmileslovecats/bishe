package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.DiscussshiyongfankuiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.DiscussshiyongfankuiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.DiscussshiyongfankuiView;


/**
 * 使用反馈评论 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface DiscussshiyongfankuiService extends IService<DiscussshiyongfankuiEntity> {

    /**
     * 分页查询使用反馈评论数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询使用反馈评论VO 列表，给需要精简字段的业务场景使用。
     */
    List<DiscussshiyongfankuiVO> selectListVO(Wrapper<DiscussshiyongfankuiEntity> wrapper);

    /**
     * 查询单条使用反馈评论VO 数据。
     */
    DiscussshiyongfankuiVO selectVO(@Param("ew") Wrapper<DiscussshiyongfankuiEntity> wrapper);

    /**
     * 查询使用反馈评论视图列表，包含页面展示需要的扩展字段。
     */
    List<DiscussshiyongfankuiView> selectListView(Wrapper<DiscussshiyongfankuiEntity> wrapper);

    /**
     * 查询单条使用反馈评论视图数据。
     */
    DiscussshiyongfankuiView selectView(@Param("ew") Wrapper<DiscussshiyongfankuiEntity> wrapper);

    /**
     * 分页查询使用反馈评论数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params,Wrapper<DiscussshiyongfankuiEntity> wrapper);



}

