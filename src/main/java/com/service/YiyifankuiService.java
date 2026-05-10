package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.YiyifankuiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.YiyifankuiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.YiyifankuiView;


/**
 * 异议反馈 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface YiyifankuiService extends IService<YiyifankuiEntity> {

    /**
     * 分页查询异议反馈数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询异议反馈VO 列表，给需要精简字段的业务场景使用。
     */
    List<YiyifankuiVO> selectListVO(Wrapper<YiyifankuiEntity> wrapper);

    /**
     * 查询单条异议反馈VO 数据。
     */
    YiyifankuiVO selectVO(@Param("ew") Wrapper<YiyifankuiEntity> wrapper);

    /**
     * 查询异议反馈视图列表，包含页面展示需要的扩展字段。
     */
    List<YiyifankuiView> selectListView(Wrapper<YiyifankuiEntity> wrapper);

    /**
     * 查询单条异议反馈视图数据。
     */
    YiyifankuiView selectView(@Param("ew") Wrapper<YiyifankuiEntity> wrapper);

    /**
     * 分页查询异议反馈数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params,Wrapper<YiyifankuiEntity> wrapper);



}

