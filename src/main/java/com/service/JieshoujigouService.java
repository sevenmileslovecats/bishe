package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.JieshoujigouEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.JieshoujigouVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.JieshoujigouView;


/**
 * 接收机构 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface JieshoujigouService extends IService<JieshoujigouEntity> {

    /**
     * 分页查询接收机构数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询接收机构VO 列表，给需要精简字段的业务场景使用。
     */
    List<JieshoujigouVO> selectListVO(Wrapper<JieshoujigouEntity> wrapper);

    /**
     * 查询单条接收机构VO 数据。
     */
    JieshoujigouVO selectVO(@Param("ew") Wrapper<JieshoujigouEntity> wrapper);

    /**
     * 查询接收机构视图列表，包含页面展示需要的扩展字段。
     */
    List<JieshoujigouView> selectListView(Wrapper<JieshoujigouEntity> wrapper);

    /**
     * 查询单条接收机构视图数据。
     */
    JieshoujigouView selectView(@Param("ew") Wrapper<JieshoujigouEntity> wrapper);

    /**
     * 分页查询接收机构数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params,Wrapper<JieshoujigouEntity> wrapper);



}

