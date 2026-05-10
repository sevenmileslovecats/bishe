package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.JuanzengrenEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.JuanzengrenVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.JuanzengrenView;


/**
 * 捐赠人 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface JuanzengrenService extends IService<JuanzengrenEntity> {

    /**
     * 分页查询捐赠人数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询捐赠人VO 列表，给需要精简字段的业务场景使用。
     */
    List<JuanzengrenVO> selectListVO(Wrapper<JuanzengrenEntity> wrapper);

    /**
     * 查询单条捐赠人VO 数据。
     */
    JuanzengrenVO selectVO(@Param("ew") Wrapper<JuanzengrenEntity> wrapper);

    /**
     * 查询捐赠人视图列表，包含页面展示需要的扩展字段。
     */
    List<JuanzengrenView> selectListView(Wrapper<JuanzengrenEntity> wrapper);

    /**
     * 查询单条捐赠人视图数据。
     */
    JuanzengrenView selectView(@Param("ew") Wrapper<JuanzengrenEntity> wrapper);

    /**
     * 分页查询捐赠人数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params,Wrapper<JuanzengrenEntity> wrapper);



}

