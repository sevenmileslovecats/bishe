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

import com.entity.JieshouxinxiEntity;
import com.entity.view.JieshouxinxiView;

import com.service.JieshouxinxiService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 接收信息 模块后端接口。
 * 说明：供管理端、前台端对应页面通过 HTTP 请求调用。
 */
@RestController
@RequestMapping("/jieshouxinxi")
public class JieshouxinxiController {
    @Autowired
    private JieshouxinxiService jieshouxinxiService;










    /**
     * 功能：分页查询接收信息数据。
     * 使用端：管理端接收信息管理列表页。
     * 前端触发：admin/src/views/modules/jieshouxinxi/list.vue 通过 $http.get('jieshouxinxi/page') 触发。
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,JieshouxinxiEntity jieshouxinxi,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jieshoujigou")) {
			jieshouxinxi.setJigouzhanghao((String)request.getSession().getAttribute("username"));
		}
        //设置查询条件
        EntityWrapper<JieshouxinxiEntity> ew = new EntityWrapper<JieshouxinxiEntity>();


        //查询结果
		PageUtils page = jieshouxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jieshouxinxi), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


    /**
     * 功能：查询接收信息前台列表数据。
     * 使用端：前台接收信息列表页，部分管理端通用列表也会复用。
     * 前端触发：front/src/pages/jieshouxinxi/list.vue 通过 $http.get('jieshouxinxi/list') 触发。
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,JieshouxinxiEntity jieshouxinxi,
                @RequestParam(required = false) Double wuzishuliangstart,
                @RequestParam(required = false) Double wuzishuliangend,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date qianshoushijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date qianshoushijianend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<JieshouxinxiEntity> ew = new EntityWrapper<JieshouxinxiEntity>();
        if(wuzishuliangstart!=null) ew.ge("wuzishuliang", wuzishuliangstart);
        if(wuzishuliangend!=null) ew.le("wuzishuliang", wuzishuliangend);
        if(qianshoushijianstart!=null) ew.ge("qianshoushijian", qianshoushijianstart);
        if(qianshoushijianend!=null) ew.le("qianshoushijian", qianshoushijianend);

        //查询结果
		PageUtils page = jieshouxinxiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jieshouxinxi), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




    /**
     * 功能：查询接收信息不分页列表。
     * 使用端：前后台表单页的下拉、联动和重复校验场景。
     * 前端触发：表单页按 tableName 拼接 $http.get('jieshouxinxi/lists') 触发。
     */
    @RequestMapping("/lists")
    public R list( JieshouxinxiEntity jieshouxinxi){
       	EntityWrapper<JieshouxinxiEntity> ew = new EntityWrapper<JieshouxinxiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( jieshouxinxi, "jieshouxinxi"));
        return R.ok().put("data", jieshouxinxiService.selectListView(ew));
    }

    /**
     * 功能：按条件查询单条接收信息视图数据。
     * 使用端：前后台表单联动或详情回显辅助接口。
     * 前端触发：前端按条件通过 $http.get('jieshouxinxi/query') 触发。
     */
    @RequestMapping("/query")
    public R query(JieshouxinxiEntity jieshouxinxi){
        EntityWrapper< JieshouxinxiEntity> ew = new EntityWrapper< JieshouxinxiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( jieshouxinxi, "jieshouxinxi"));
		JieshouxinxiView jieshouxinxiView =  jieshouxinxiService.selectView(ew);
		return R.ok("查询接收信息成功").put("data", jieshouxinxiView);
    }

    /**
     * 功能：查询接收信息管理端详情。
     * 使用端：管理端接收信息列表页、编辑页。
     * 前端触发：管理端通过 $http.get('jieshouxinxi/info/{id}') 触发。
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        JieshouxinxiEntity jieshouxinxi = jieshouxinxiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(jieshouxinxi,deSens);
        return R.ok().put("data", jieshouxinxi);
    }

    /**
     * 功能：查询接收信息前台详情。
     * 使用端：前台接收信息详情页或编辑回显页。
     * 前端触发：front/src/pages/jieshouxinxi/detail.vue 或 add.vue 触发。
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        JieshouxinxiEntity jieshouxinxi = jieshouxinxiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(jieshouxinxi,deSens);
        return R.ok().put("data", jieshouxinxi);
    }




    /**
     * 功能：管理端新增接收信息记录。
     * 使用端：管理端接收信息新增表单。
     * 前端触发：管理端表单通过 $http.post('jieshouxinxi/save') 触发。
     */
    @RequestMapping("/save")
    @SysLog("新增接收信息")
    public R save(@RequestBody JieshouxinxiEntity jieshouxinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(jieshouxinxi);
        jieshouxinxiService.insert(jieshouxinxi);
        return R.ok().put("data",jieshouxinxi.getId());
    }

    /**
     * 功能：前台新增接收信息记录。
     * 使用端：前台接收信息新增表单或详情页操作。
     * 前端触发：前台表单通过 $http.post('jieshouxinxi/add') 触发。
     */
    @SysLog("新增接收信息")
    @RequestMapping("/add")
    public R add(@RequestBody JieshouxinxiEntity jieshouxinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(jieshouxinxi);
        jieshouxinxiService.insert(jieshouxinxi);
        return R.ok().put("data",jieshouxinxi.getId());
    }





    /**
     * 功能：修改接收信息记录。
     * 使用端：管理端编辑页、前台个人中心或详情页操作。
     * 前端触发：前端表单提交时通过 $http.post('jieshouxinxi/update') 触发。
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改接收信息")
    public R update(@RequestBody JieshouxinxiEntity jieshouxinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(jieshouxinxi);
        //全部更新
        jieshouxinxiService.updateById(jieshouxinxi);
        return R.ok();
    }





    /**
     * 功能：删除接收信息记录。
     * 使用端：管理端列表页或前台详情页/我的列表。
     * 前端触发：删除按钮通过 $http.post('jieshouxinxi/delete') 触发。
     */
    @RequestMapping("/delete")
    @SysLog("删除接收信息")
    public R delete(@RequestBody Long[] ids){
        jieshouxinxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
