package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.SyslogEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.SyslogVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.SyslogView;


/**
 * 系统日志 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface SyslogService extends IService<SyslogEntity> {

    /**
     * 分页查询系统日志数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询系统日志VO 列表，给需要精简字段的业务场景使用。
     */
    List<SyslogVO> selectListVO(Wrapper<SyslogEntity> wrapper);

    /**
     * 查询单条系统日志VO 数据。
     */
    SyslogVO selectVO(@Param("ew") Wrapper<SyslogEntity> wrapper);

    /**
     * 查询系统日志视图列表，包含页面展示需要的扩展字段。
     */
    List<SyslogView> selectListView(Wrapper<SyslogEntity> wrapper);

    /**
     * 查询单条系统日志视图数据。
     */
    SyslogView selectView(@Param("ew") Wrapper<SyslogEntity> wrapper);

    /**
     * 分页查询系统日志数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params,Wrapper<SyslogEntity> wrapper);



}

