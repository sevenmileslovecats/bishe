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
 * 接收信息
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
@RestController
@RequestMapping("/jieshouxinxi")
public class JieshouxinxiController {
    @Autowired
    private JieshouxinxiService jieshouxinxiService;










    /**
     * 后台列表
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
     * 前台列表
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
     * 列表
     */
    @RequestMapping("/lists")
    public R list( JieshouxinxiEntity jieshouxinxi){
       	EntityWrapper<JieshouxinxiEntity> ew = new EntityWrapper<JieshouxinxiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( jieshouxinxi, "jieshouxinxi"));
        return R.ok().put("data", jieshouxinxiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(JieshouxinxiEntity jieshouxinxi){
        EntityWrapper< JieshouxinxiEntity> ew = new EntityWrapper< JieshouxinxiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( jieshouxinxi, "jieshouxinxi"));
		JieshouxinxiView jieshouxinxiView =  jieshouxinxiService.selectView(ew);
		return R.ok("查询接收信息成功").put("data", jieshouxinxiView);
    }

    /**
     * 后台详情
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
     * 前台详情
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
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增接收信息")
    public R save(@RequestBody JieshouxinxiEntity jieshouxinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(jieshouxinxi);
        jieshouxinxiService.insert(jieshouxinxi);
        return R.ok().put("data",jieshouxinxi.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增接收信息")
    @RequestMapping("/add")
    public R add(@RequestBody JieshouxinxiEntity jieshouxinxi, HttpServletRequest request){
        //ValidatorUtils.validateEntity(jieshouxinxi);
        jieshouxinxiService.insert(jieshouxinxi);
        return R.ok().put("data",jieshouxinxi.getId());
    }





    /**
     * 修改
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
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除接收信息")
    public R delete(@RequestBody Long[] ids){
        jieshouxinxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
