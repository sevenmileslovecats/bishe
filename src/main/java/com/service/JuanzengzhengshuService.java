package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.JuanzengzhengshuEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.JuanzengzhengshuVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.JuanzengzhengshuView;


/**
 * 捐赠证书 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface JuanzengzhengshuService extends IService<JuanzengzhengshuEntity> {

    /**
     * 分页查询捐赠证书数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询捐赠证书VO 列表，给需要精简字段的业务场景使用。
     */
    List<JuanzengzhengshuVO> selectListVO(Wrapper<JuanzengzhengshuEntity> wrapper);

    /**
     * 查询单条捐赠证书VO 数据。
     */
    JuanzengzhengshuVO selectVO(@Param("ew") Wrapper<JuanzengzhengshuEntity> wrapper);

    /**
     * 查询捐赠证书视图列表，包含页面展示需要的扩展字段。
     */
    List<JuanzengzhengshuView> selectListView(Wrapper<JuanzengzhengshuEntity> wrapper);

    /**
     * 查询单条捐赠证书视图数据。
     */
    JuanzengzhengshuView selectView(@Param("ew") Wrapper<JuanzengzhengshuEntity> wrapper);

    /**
     * 分页查询捐赠证书数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params,Wrapper<JuanzengzhengshuEntity> wrapper);



}

