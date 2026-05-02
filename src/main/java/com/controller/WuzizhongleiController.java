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
 * 物资种类
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
@RestController
@RequestMapping("/wuzizhonglei")
public class WuzizhongleiController {
    @Autowired
    private WuzizhongleiService wuzizhongleiService;










    /**
     * 后台列表
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
     * 前台列表
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
     * 列表
     */
    @RequestMapping("/lists")
    public R list( WuzizhongleiEntity wuzizhonglei){
       	EntityWrapper<WuzizhongleiEntity> ew = new EntityWrapper<WuzizhongleiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( wuzizhonglei, "wuzizhonglei"));
        return R.ok().put("data", wuzizhongleiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(WuzizhongleiEntity wuzizhonglei){
        EntityWrapper< WuzizhongleiEntity> ew = new EntityWrapper< WuzizhongleiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( wuzizhonglei, "wuzizhonglei"));
		WuzizhongleiView wuzizhongleiView =  wuzizhongleiService.selectView(ew);
		return R.ok("查询物资种类成功").put("data", wuzizhongleiView);
    }

    /**
     * 后台详情
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
     * 前台详情
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
     * 后台保存
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
     * 前台保存
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
     * 修改
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
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除物资种类")
    public R delete(@RequestBody Long[] ids){
        wuzizhongleiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
