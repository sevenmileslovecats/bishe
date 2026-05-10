package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.WuzizhongleiEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.WuzizhongleiVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.WuzizhongleiView;


/**
 * 物资种类 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface WuzizhongleiService extends IService<WuzizhongleiEntity> {

    /**
     * 分页查询物资种类数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询物资种类VO 列表，给需要精简字段的业务场景使用。
     */
    List<WuzizhongleiVO> selectListVO(Wrapper<WuzizhongleiEntity> wrapper);

    /**
     * 查询单条物资种类VO 数据。
     */
    WuzizhongleiVO selectVO(@Param("ew") Wrapper<WuzizhongleiEntity> wrapper);

    /**
     * 查询物资种类视图列表，包含页面展示需要的扩展字段。
     */
    List<WuzizhongleiView> selectListView(Wrapper<WuzizhongleiEntity> wrapper);

    /**
     * 查询单条物资种类视图数据。
     */
    WuzizhongleiView selectView(@Param("ew") Wrapper<WuzizhongleiEntity> wrapper);

    /**
     * 分页查询物资种类数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params,Wrapper<WuzizhongleiEntity> wrapper);



}

