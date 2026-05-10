package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.WuzixinxiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.WuzixinxiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.WuzixinxiView;


/**
 * 物资信息 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface WuzixinxiService extends IService<WuzixinxiEntity> {

    /**
     * 分页查询物资信息数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询物资信息VO 列表，给需要精简字段的业务场景使用。
     */
    List<WuzixinxiVO> selectListVO(Wrapper<WuzixinxiEntity> wrapper);

    /**
     * 查询单条物资信息VO 数据。
     */
    WuzixinxiVO selectVO(@Param("ew") Wrapper<WuzixinxiEntity> wrapper);

    /**
     * 查询物资信息视图列表，包含页面展示需要的扩展字段。
     */
    List<WuzixinxiView> selectListView(Wrapper<WuzixinxiEntity> wrapper);

    /**
     * 查询单条物资信息视图数据。
     */
    WuzixinxiView selectView(@Param("ew") Wrapper<WuzixinxiEntity> wrapper);

    /**
     * 分页查询物资信息数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params,Wrapper<WuzixinxiEntity> wrapper);



    /**
     * 查询物资信息数值统计结果。
     */
    List<Map<String, Object>> selectValue(Map<String, Object> params,Wrapper<WuzixinxiEntity> wrapper);

    /**
     * 按时间维度查询物资信息统计结果。
     */
    List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params,Wrapper<WuzixinxiEntity> wrapper);

    /**
     * 按字段分组查询物资信息统计结果。
     */
    List<Map<String, Object>> selectGroup(Map<String, Object> params,Wrapper<WuzixinxiEntity> wrapper);



}

