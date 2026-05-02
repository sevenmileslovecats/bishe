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

import com.entity.JuanzengzhengshuEntity;
import com.entity.view.JuanzengzhengshuView;

import com.service.JuanzengzhengshuService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 捐赠证书
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
@RestController
@RequestMapping("/juanzengzhengshu")
public class JuanzengzhengshuController {
    @Autowired
    private JuanzengzhengshuService juanzengzhengshuService;










    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,JuanzengzhengshuEntity juanzengzhengshu,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("juanzengren")) {
			juanzengzhengshu.setZhanghao((String)request.getSession().getAttribute("username"));
		}
        //设置查询条件
        EntityWrapper<JuanzengzhengshuEntity> ew = new EntityWrapper<JuanzengzhengshuEntity>();


        //查询结果
		PageUtils page = juanzengzhengshuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, juanzengzhengshu), params), params));
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
    public R list(@RequestParam Map<String, Object> params,JuanzengzhengshuEntity juanzengzhengshu,
                @RequestParam(required = false) Double wuzishuliangstart,
                @RequestParam(required = false) Double wuzishuliangend,
                @RequestParam(required = false) Double wuzizhongliangstart,
                @RequestParam(required = false) Double wuzizhongliangend,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date banfashijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date banfashijianend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<JuanzengzhengshuEntity> ew = new EntityWrapper<JuanzengzhengshuEntity>();
        if(wuzishuliangstart!=null) ew.ge("wuzishuliang", wuzishuliangstart);
        if(wuzishuliangend!=null) ew.le("wuzishuliang", wuzishuliangend);
        if(wuzizhongliangstart!=null) ew.ge("wuzizhongliang", wuzizhongliangstart);
        if(wuzizhongliangend!=null) ew.le("wuzizhongliang", wuzizhongliangend);
        if(banfashijianstart!=null) ew.ge("banfashijian", banfashijianstart);
        if(banfashijianend!=null) ew.le("banfashijian", banfashijianend);

        //查询结果
		PageUtils page = juanzengzhengshuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, juanzengzhengshu), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( JuanzengzhengshuEntity juanzengzhengshu){
       	EntityWrapper<JuanzengzhengshuEntity> ew = new EntityWrapper<JuanzengzhengshuEntity>();
      	ew.allEq(MPUtil.allEQMapPre( juanzengzhengshu, "juanzengzhengshu"));
        return R.ok().put("data", juanzengzhengshuService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(JuanzengzhengshuEntity juanzengzhengshu){
        EntityWrapper< JuanzengzhengshuEntity> ew = new EntityWrapper< JuanzengzhengshuEntity>();
 		ew.allEq(MPUtil.allEQMapPre( juanzengzhengshu, "juanzengzhengshu"));
		JuanzengzhengshuView juanzengzhengshuView =  juanzengzhengshuService.selectView(ew);
		return R.ok("查询捐赠证书成功").put("data", juanzengzhengshuView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        JuanzengzhengshuEntity juanzengzhengshu = juanzengzhengshuService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(juanzengzhengshu,deSens);
        return R.ok().put("data", juanzengzhengshu);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        JuanzengzhengshuEntity juanzengzhengshu = juanzengzhengshuService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(juanzengzhengshu,deSens);
        return R.ok().put("data", juanzengzhengshu);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增捐赠证书")
    public R save(@RequestBody JuanzengzhengshuEntity juanzengzhengshu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(juanzengzhengshu);
        juanzengzhengshuService.insert(juanzengzhengshu);
        return R.ok().put("data",juanzengzhengshu.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增捐赠证书")
    @RequestMapping("/add")
    public R add(@RequestBody JuanzengzhengshuEntity juanzengzhengshu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(juanzengzhengshu);
        juanzengzhengshuService.insert(juanzengzhengshu);
        return R.ok().put("data",juanzengzhengshu.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改捐赠证书")
    public R update(@RequestBody JuanzengzhengshuEntity juanzengzhengshu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(juanzengzhengshu);
        //全部更新
        juanzengzhengshuService.updateById(juanzengzhengshu);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除捐赠证书")
    public R delete(@RequestBody Long[] ids){
        juanzengzhengshuService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
