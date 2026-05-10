package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.WuzishenlingEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.WuzishenlingVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.WuzishenlingView;


/**
 * 物资申领 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface WuzishenlingService extends IService<WuzishenlingEntity> {

    /**
     * 分页查询物资申领数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询物资申领VO 列表，给需要精简字段的业务场景使用。
     */
    List<WuzishenlingVO> selectListVO(Wrapper<WuzishenlingEntity> wrapper);

    /**
     * 查询单条物资申领VO 数据。
     */
    WuzishenlingVO selectVO(@Param("ew") Wrapper<WuzishenlingEntity> wrapper);

    /**
     * 查询物资申领视图列表，包含页面展示需要的扩展字段。
     */
    List<WuzishenlingView> selectListView(Wrapper<WuzishenlingEntity> wrapper);

    /**
     * 查询单条物资申领视图数据。
     */
    WuzishenlingView selectView(@Param("ew") Wrapper<WuzishenlingEntity> wrapper);

    /**
     * 分页查询物资申领数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params,Wrapper<WuzishenlingEntity> wrapper);



    /**
     * 查询物资申领数值统计结果。
     */
    List<Map<String, Object>> selectValue(Map<String, Object> params,Wrapper<WuzishenlingEntity> wrapper);

    /**
     * 按时间维度查询物资申领统计结果。
     */
    List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params,Wrapper<WuzishenlingEntity> wrapper);

    /**
     * 按字段分组查询物资申领统计结果。
     */
    List<Map<String, Object>> selectGroup(Map<String, Object> params,Wrapper<WuzishenlingEntity> wrapper);



}

