package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.YanshoujiluEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.YanshoujiluVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.YanshoujiluView;


/**
 * 验收记录 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface YanshoujiluService extends IService<YanshoujiluEntity> {

    /**
     * 分页查询验收记录数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询验收记录VO 列表，给需要精简字段的业务场景使用。
     */
    List<YanshoujiluVO> selectListVO(Wrapper<YanshoujiluEntity> wrapper);

    /**
     * 查询单条验收记录VO 数据。
     */
    YanshoujiluVO selectVO(@Param("ew") Wrapper<YanshoujiluEntity> wrapper);

    /**
     * 查询验收记录视图列表，包含页面展示需要的扩展字段。
     */
    List<YanshoujiluView> selectListView(Wrapper<YanshoujiluEntity> wrapper);

    /**
     * 查询单条验收记录视图数据。
     */
    YanshoujiluView selectView(@Param("ew") Wrapper<YanshoujiluEntity> wrapper);

    /**
     * 分页查询验收记录数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params,Wrapper<YanshoujiluEntity> wrapper);



}

