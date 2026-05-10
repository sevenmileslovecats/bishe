package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.NewsEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.NewsVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.NewsView;


/**
 * 公告信息 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface NewsService extends IService<NewsEntity> {

    /**
     * 分页查询公告信息数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询公告信息VO 列表，给需要精简字段的业务场景使用。
     */
    List<NewsVO> selectListVO(Wrapper<NewsEntity> wrapper);

    /**
     * 查询单条公告信息VO 数据。
     */
    NewsVO selectVO(@Param("ew") Wrapper<NewsEntity> wrapper);

    /**
     * 查询公告信息视图列表，包含页面展示需要的扩展字段。
     */
    List<NewsView> selectListView(Wrapper<NewsEntity> wrapper);

    /**
     * 查询单条公告信息视图数据。
     */
    NewsView selectView(@Param("ew") Wrapper<NewsEntity> wrapper);

    /**
     * 分页查询公告信息数据，供 Controller 列表接口调用。
     */
    PageUtils queryPage(Map<String, Object> params,Wrapper<NewsEntity> wrapper);



}

