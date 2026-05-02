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

import com.entity.YiyifankuiEntity;
import com.entity.view.YiyifankuiView;

import com.service.YiyifankuiService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 异议反馈
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-27 08:55:02
 */
@RestController
@RequestMapping("/yiyifankui")
public class YiyifankuiController {
    @Autowired
    private YiyifankuiService yiyifankuiService;










    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,YiyifankuiEntity yiyifankui,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jieshoujigou")) {
			yiyifankui.setJigouzhanghao((String)request.getSession().getAttribute("username"));
		}
        //设置查询条件
        EntityWrapper<YiyifankuiEntity> ew = new EntityWrapper<YiyifankuiEntity>();


        //查询结果
		PageUtils page = yiyifankuiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yiyifankui), params), params));
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
    public R list(@RequestParam Map<String, Object> params,YiyifankuiEntity yiyifankui,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date tijiaoshijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date tijiaoshijianend,
                @RequestParam(required = false) Double wuzishuliangstart,
                @RequestParam(required = false) Double wuzishuliangend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<YiyifankuiEntity> ew = new EntityWrapper<YiyifankuiEntity>();
        if(tijiaoshijianstart!=null) ew.ge("tijiaoshijian", tijiaoshijianstart);
        if(tijiaoshijianend!=null) ew.le("tijiaoshijian", tijiaoshijianend);
        if(wuzishuliangstart!=null) ew.ge("wuzishuliang", wuzishuliangstart);
        if(wuzishuliangend!=null) ew.le("wuzishuliang", wuzishuliangend);

        //查询结果
		PageUtils page = yiyifankuiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yiyifankui), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( YiyifankuiEntity yiyifankui){
       	EntityWrapper<YiyifankuiEntity> ew = new EntityWrapper<YiyifankuiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( yiyifankui, "yiyifankui"));
        return R.ok().put("data", yiyifankuiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(YiyifankuiEntity yiyifankui){
        EntityWrapper< YiyifankuiEntity> ew = new EntityWrapper< YiyifankuiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( yiyifankui, "yiyifankui"));
		YiyifankuiView yiyifankuiView =  yiyifankuiService.selectView(ew);
		return R.ok("查询异议反馈成功").put("data", yiyifankuiView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        YiyifankuiEntity yiyifankui = yiyifankuiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(yiyifankui,deSens);
        return R.ok().put("data", yiyifankui);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        YiyifankuiEntity yiyifankui = yiyifankuiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(yiyifankui,deSens);
        return R.ok().put("data", yiyifankui);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增异议反馈")
    public R save(@RequestBody YiyifankuiEntity yiyifankui, HttpServletRequest request){
        //ValidatorUtils.validateEntity(yiyifankui);
        yiyifankuiService.insert(yiyifankui);
        return R.ok().put("data",yiyifankui.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增异议反馈")
    @RequestMapping("/add")
    public R add(@RequestBody YiyifankuiEntity yiyifankui, HttpServletRequest request){
        //ValidatorUtils.validateEntity(yiyifankui);
        yiyifankuiService.insert(yiyifankui);
        return R.ok().put("data",yiyifankui.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改异议反馈")
    public R update(@RequestBody YiyifankuiEntity yiyifankui, HttpServletRequest request){
        //ValidatorUtils.validateEntity(yiyifankui);
        //全部更新
        yiyifankuiService.updateById(yiyifankui);
        return R.ok();
    }

    /**
     * 审核
     */
    @RequestMapping("/shBatch")
    @Transactional
    @SysLog("审核异议反馈")
    public R update(@RequestBody Long[] ids, @RequestParam String sfsh, @RequestParam String shhf){
        List<YiyifankuiEntity> list = new ArrayList<YiyifankuiEntity>();
        for(Long id : ids) {
            YiyifankuiEntity yiyifankui = yiyifankuiService.selectById(id);
            yiyifankui.setSfsh(sfsh);
            yiyifankui.setShhf(shhf);
            list.add(yiyifankui);
        }
        yiyifankuiService.updateBatchById(list);
        return R.ok();
    }




    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除异议反馈")
    public R delete(@RequestBody Long[] ids){
        yiyifankuiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
