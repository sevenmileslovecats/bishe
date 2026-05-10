package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.JuanzengwuziEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.JuanzengwuziVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.JuanzengwuziView;


/**
 * 捐赠物资 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface JuanzengwuziService extends IService<JuanzengwuziEntity> {

    /**
     * 分页查询捐赠物资数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询捐赠物资VO 列表，给需要精简字段的业务场景使用。
     */
    List<JuanzengwuziVO> selectListVO(Wrapper<JuanzengwuziEntity> wrapper);

    /**
     * 查询单条捐赠物资VO 数据。
     */
    JuanzengwuziVO selectVO(@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);

    /**
     * 查询捐赠物资视图列表，包含页面展示需要的扩展字段。
     */
    List<JuanzengwuziView> selectListView(Wrapper<JuanzengwuziEntity> wrapper);

    /**
     * 查询单条捐赠物资视图数据。
     */
    JuanzengwuziView selectView(@Param("ew") Wrapper<JuanzengwuziEntity> wrapper);

    /**
     * 分页查询捐赠物资数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params,Wrapper<JuanzengwuziEntity> wrapper);



    /**
     * 查询捐赠物资数值统计结果。
     */
    List<Map<String, Object>> selectValue(Map<String, Object> params,Wrapper<JuanzengwuziEntity> wrapper);

    /**
     * 按时间维度查询捐赠物资统计结果。
     */
    List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params,Wrapper<JuanzengwuziEntity> wrapper);

    /**
     * 按字段分组查询捐赠物资统计结果。
     */
    List<Map<String, Object>> selectGroup(Map<String, Object> params,Wrapper<JuanzengwuziEntity> wrapper);



}

