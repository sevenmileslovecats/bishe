
package com.service;

import java.util.Map;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.ConfigEntity;
import com.utils.PageUtils;


/**
 * 配置/轮播图 服务层。
 * 说明：承接 Controller 业务，封装分页查询、VO/View 查询和统计查询等服务能力。
 */
public interface ConfigService extends IService<ConfigEntity> {
	/**
	 * 分页查询配置/轮播图数据，供 Controller 列表接口调用。
	 */
	PageUtils queryPage(Map<String, Object> params,Wrapper<ConfigEntity> wrapper);
}
