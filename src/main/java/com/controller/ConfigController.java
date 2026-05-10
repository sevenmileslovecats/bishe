
package com.controller;


import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.ConfigEntity;
import com.service.ConfigService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.ValidatorUtils;

/**
 * 配置/轮播图 模块后端接口。
 * 说明：供管理端、前台端对应页面通过 HTTP 请求调用。
 */
@RestController
@RequestMapping("config")
public class ConfigController{

	@Autowired
	private ConfigService configService;

    /**
     * 功能：分页查询配置/轮播图数据。
     * 使用端：管理端配置/轮播图管理列表页。
     * 前端触发：admin/src/views/modules/config/list.vue 通过 $http.get('config/page') 触发。
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ConfigEntity config){
        EntityWrapper<ConfigEntity> ew = new EntityWrapper<ConfigEntity>();
    	PageUtils page = configService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, config), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 功能：查询配置/轮播图前台列表数据。
     * 使用端：前台配置/轮播图列表页，部分管理端通用列表也会复用。
     * 前端触发：front/src/pages/config/list.vue 通过 $http.get('config/list') 触发。
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ConfigEntity config){
        EntityWrapper<ConfigEntity> ew = new EntityWrapper<ConfigEntity>();
    	PageUtils page = configService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, config), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 功能：查询配置/轮播图管理端详情。
     * 使用端：管理端配置/轮播图列表页、编辑页。
     * 前端触发：管理端通过 $http.get('config/info/{id}') 触发。
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        ConfigEntity config = configService.selectById(id);
        return R.ok().put("data", config);
    }

    /**
     * 功能：查询配置/轮播图前台详情。
     * 使用端：前台配置/轮播图详情页或编辑回显页。
     * 前端触发：front/src/pages/config/detail.vue 或 add.vue 触发。
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") String id){
        ConfigEntity config = configService.selectById(id);
        return R.ok().put("data", config);
    }

    /**
     * 功能：按配置名称查询配置项。
     * 使用端：登录页、首页、注册页读取背景图和 Logo。
     * 前端触发：前端通过 $http.get('config/info?name=...') 触发。
     */
    @IgnoreAuth
    @RequestMapping("/info")
    public R infoByName(@RequestParam String name){
        ConfigEntity config = configService.selectOne(new EntityWrapper<ConfigEntity>().eq("name", name));
        return R.ok().put("data", config);
    }

    /**
     * 功能：管理端新增配置/轮播图记录。
     * 使用端：管理端配置/轮播图新增表单。
     * 前端触发：管理端表单通过 $http.post('config/save') 触发。
     */
    @PostMapping("/save")
    public R save(@RequestBody ConfigEntity config){
//    	ValidatorUtils.validateEntity(config);
    	configService.insert(config);
        return R.ok();
    }

    /**
     * 功能：修改配置/轮播图记录。
     * 使用端：管理端编辑页、前台个人中心或详情页操作。
     * 前端触发：前端表单提交时通过 $http.post('config/update') 触发。
     */
    @RequestMapping("/update")
    public R update(@RequestBody ConfigEntity config){
//        ValidatorUtils.validateEntity(config);
        configService.updateById(config);//全部更新
        return R.ok();
    }

    /**
     * 功能：删除配置/轮播图记录。
     * 使用端：管理端列表页或前台详情页/我的列表。
     * 前端触发：删除按钮通过 $http.post('config/delete') 触发。
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
    	configService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}
