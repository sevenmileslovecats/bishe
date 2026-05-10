package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.ShiyongfankuiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.ShiyongfankuiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.ShiyongfankuiView;


/**
 * 使用反馈 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface ShiyongfankuiService extends IService<ShiyongfankuiEntity> {

    /**
     * 分页查询使用反馈数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询使用反馈VO 列表，给需要精简字段的业务场景使用。
     */
    List<ShiyongfankuiVO> selectListVO(Wrapper<ShiyongfankuiEntity> wrapper);

    /**
     * 查询单条使用反馈VO 数据。
     */
    ShiyongfankuiVO selectVO(@Param("ew") Wrapper<ShiyongfankuiEntity> wrapper);

    /**
     * 查询使用反馈视图列表，包含页面展示需要的扩展字段。
     */
    List<ShiyongfankuiView> selectListView(Wrapper<ShiyongfankuiEntity> wrapper);

    /**
     * 查询单条使用反馈视图数据。
     */
    ShiyongfankuiView selectView(@Param("ew") Wrapper<ShiyongfankuiEntity> wrapper);

    /**
     * 分页查询使用反馈数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params,Wrapper<ShiyongfankuiEntity> wrapper);



}

