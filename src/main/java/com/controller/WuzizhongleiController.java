package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import java.lang.*;
import java.math.*;
import com.utils.*;
import com.service.*;
import com.entity.*;
import com.entity.view.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;
import com.annotation.SysLog;

import com.entity.WuzizhongleiEntity;
import com.entity.view.WuzizhongleiView;

import com.service.WuzizhongleiService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 物资种类 模块后端接口。
 * 说明：供管理端、前台端对应页面通过 HTTP 请求调用。
 */
@RestController
@RequestMapping("/wuzizhonglei")
public class WuzizhongleiController {
    @Autowired
    private WuzizhongleiService wuzizhongleiService;










    /**
     * 功能：分页查询物资种类数据。
     * 使用端：管理端物资种类管理列表页。
     * 前端触发：admin/src/views/modules/wuzizhonglei/list.vue 通过 $http.get('wuzizhonglei/page') 触发。
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,WuzizhongleiEntity wuzizhonglei,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<WuzizhongleiEntity> ew = new EntityWrapper<WuzizhongleiEntity>();


        //查询结果
		PageUtils page = wuzizhongleiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, wuzizhonglei), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


    /**
     * 功能：查询物资种类前台列表数据。
     * 使用端：前台物资种类列表页，部分管理端通用列表也会复用。
     * 前端触发：front/src/pages/wuzizhonglei/list.vue 通过 $http.get('wuzizhonglei/list') 触发。
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,WuzizhongleiEntity wuzizhonglei,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<WuzizhongleiEntity> ew = new EntityWrapper<WuzizhongleiEntity>();

        //查询结果
		PageUtils page = wuzizhongleiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, wuzizhonglei), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




    /**
     * 功能：查询物资种类不分页列表。
     * 使用端：前后台表单页的下拉、联动和重复校验场景。
     * 前端触发：表单页按 tableName 拼接 $http.get('wuzizhonglei/lists') 触发。
     */
    @RequestMapping("/lists")
    public R list( WuzizhongleiEntity wuzizhonglei){
       	EntityWrapper<WuzizhongleiEntity> ew = new EntityWrapper<WuzizhongleiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( wuzizhonglei, "wuzizhonglei"));
        return R.ok().put("data", wuzizhongleiService.selectListView(ew));
    }

    /**
     * 功能：按条件查询单条物资种类视图数据。
     * 使用端：前后台表单联动或详情回显辅助接口。
     * 前端触发：前端按条件通过 $http.get('wuzizhonglei/query') 触发。
     */
    @RequestMapping("/query")
    public R query(WuzizhongleiEntity wuzizhonglei){
        EntityWrapper< WuzizhongleiEntity> ew = new EntityWrapper< WuzizhongleiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( wuzizhonglei, "wuzizhonglei"));
		WuzizhongleiView wuzizhongleiView =  wuzizhongleiService.selectView(ew);
		return R.ok("查询物资种类成功").put("data", wuzizhongleiView);
    }

    /**
     * 功能：查询物资种类管理端详情。
     * 使用端：管理端物资种类列表页、编辑页。
     * 前端触发：管理端通过 $http.get('wuzizhonglei/info/{id}') 触发。
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        WuzizhongleiEntity wuzizhonglei = wuzizhongleiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(wuzizhonglei,deSens);
        return R.ok().put("data", wuzizhonglei);
    }

    /**
     * 功能：查询物资种类前台详情。
     * 使用端：前台物资种类详情页或编辑回显页。
     * 前端触发：front/src/pages/wuzizhonglei/detail.vue 或 add.vue 触发。
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        WuzizhongleiEntity wuzizhonglei = wuzizhongleiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(wuzizhonglei,deSens);
        return R.ok().put("data", wuzizhonglei);
    }




    /**
     * 功能：管理端新增物资种类记录。
     * 使用端：管理端物资种类新增表单。
     * 前端触发：管理端表单通过 $http.post('wuzizhonglei/save') 触发。
     */
    @RequestMapping("/save")
    @SysLog("新增物资种类")
    public R save(@RequestBody WuzizhongleiEntity wuzizhonglei, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(wuzizhongleiService.selectCount(new EntityWrapper<WuzizhongleiEntity>().eq("wuzizhonglei", wuzizhonglei.getWuzizhonglei()))>0) {
            return R.error("物资种类已存在");
        }
        //ValidatorUtils.validateEntity(wuzizhonglei);
        wuzizhongleiService.insert(wuzizhonglei);
        return R.ok().put("data",wuzizhonglei.getId());
    }

    /**
     * 功能：前台新增物资种类记录。
     * 使用端：前台物资种类新增表单或详情页操作。
     * 前端触发：前台表单通过 $http.post('wuzizhonglei/add') 触发。
     */
    @SysLog("新增物资种类")
    @RequestMapping("/add")
    public R add(@RequestBody WuzizhongleiEntity wuzizhonglei, HttpServletRequest request){
        //验证字段唯一性，否则返回错误信息
        if(wuzizhongleiService.selectCount(new EntityWrapper<WuzizhongleiEntity>().eq("wuzizhonglei", wuzizhonglei.getWuzizhonglei()))>0) {
            return R.error("物资种类已存在");
        }
        //ValidatorUtils.validateEntity(wuzizhonglei);
        wuzizhongleiService.insert(wuzizhonglei);
        return R.ok().put("data",wuzizhonglei.getId());
    }





    /**
     * 功能：修改物资种类记录。
     * 使用端：管理端编辑页、前台个人中心或详情页操作。
     * 前端触发：前端表单提交时通过 $http.post('wuzizhonglei/update') 触发。
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改物资种类")
    public R update(@RequestBody WuzizhongleiEntity wuzizhonglei, HttpServletRequest request){
        //ValidatorUtils.validateEntity(wuzizhonglei);
        //验证字段唯一性，否则返回错误信息
        if(wuzizhongleiService.selectCount(new EntityWrapper<WuzizhongleiEntity>().ne("id", wuzizhonglei.getId()).eq("wuzizhonglei", wuzizhonglei.getWuzizhonglei()))>0) {
            return R.error("物资种类已存在");
        }
        //全部更新
        wuzizhongleiService.updateById(wuzizhonglei);
        return R.ok();
    }





    /**
     * 功能：删除物资种类记录。
     * 使用端：管理端列表页或前台详情页/我的列表。
     * 前端触发：删除按钮通过 $http.post('wuzizhonglei/delete') 触发。
     */
    @RequestMapping("/delete")
    @SysLog("删除物资种类")
    public R delete(@RequestBody Long[] ids){
        wuzizhongleiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
