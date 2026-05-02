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

import com.entity.ChukufenboEntity;
import com.entity.view.ChukufenboView;

import com.service.ChukufenboService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 出库分拨
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-27 08:55:01
 */
@RestController
@RequestMapping("/chukufenbo")
public class ChukufenboController {
    @Autowired
    private ChukufenboService chukufenboService;
    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ChukufenboEntity chukufenbo,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jieshoujigou")) {
			chukufenbo.setJigouzhanghao((String)request.getSession().getAttribute("username"));
		}
        //设置查询条件
        EntityWrapper<ChukufenboEntity> ew = new EntityWrapper<ChukufenboEntity>();


        //查询结果
		PageUtils page = chukufenboService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chukufenbo), params), params));
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
    public R list(@RequestParam Map<String, Object> params,ChukufenboEntity chukufenbo,
                @RequestParam(required = false) Double wuzishuliangstart,
                @RequestParam(required = false) Double wuzishuliangend,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date chukushijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date chukushijianend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<ChukufenboEntity> ew = new EntityWrapper<ChukufenboEntity>();
        if(wuzishuliangstart!=null) ew.ge("wuzishuliang", wuzishuliangstart);
        if(wuzishuliangend!=null) ew.le("wuzishuliang", wuzishuliangend);
        if(chukushijianstart!=null) ew.ge("chukushijian", chukushijianstart);
        if(chukushijianend!=null) ew.le("chukushijian", chukushijianend);

        //查询结果
		PageUtils page = chukufenboService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, chukufenbo), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ChukufenboEntity chukufenbo){
       	EntityWrapper<ChukufenboEntity> ew = new EntityWrapper<ChukufenboEntity>();
      	ew.allEq(MPUtil.allEQMapPre( chukufenbo, "chukufenbo"));
        return R.ok().put("data", chukufenboService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ChukufenboEntity chukufenbo){
        EntityWrapper< ChukufenboEntity> ew = new EntityWrapper< ChukufenboEntity>();
 		ew.allEq(MPUtil.allEQMapPre( chukufenbo, "chukufenbo"));
		ChukufenboView chukufenboView =  chukufenboService.selectView(ew);
		return R.ok("查询出库分拨成功").put("data", chukufenboView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ChukufenboEntity chukufenbo = chukufenboService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(chukufenbo,deSens);
        return R.ok().put("data", chukufenbo);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ChukufenboEntity chukufenbo = chukufenboService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(chukufenbo,deSens);
        return R.ok().put("data", chukufenbo);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增出库分拨")
    public R save(@RequestBody ChukufenboEntity chukufenbo, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chukufenbo);
        chukufenboService.insert(chukufenbo);
        return R.ok().put("data",chukufenbo.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增出库分拨")
    @RequestMapping("/add")
    public R add(@RequestBody ChukufenboEntity chukufenbo, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chukufenbo);
        chukufenboService.insert(chukufenbo);
        return R.ok().put("data",chukufenbo.getId());
    }
    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改出库分拨")
    public R update(@RequestBody ChukufenboEntity chukufenbo, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chukufenbo);
        //全部更新
        chukufenboService.updateById(chukufenbo);
        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除出库分拨")
    public R delete(@RequestBody Long[] ids){
        chukufenboService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}
