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

import com.entity.DiscussshiyongfankuiEntity;
import com.entity.view.DiscussshiyongfankuiView;

import com.service.DiscussshiyongfankuiService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 使用反馈评论
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-27 08:55:02
 */
@RestController
@RequestMapping("/discussshiyongfankui")
public class DiscussshiyongfankuiController {
    @Autowired
    private DiscussshiyongfankuiService discussshiyongfankuiService;










    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,DiscussshiyongfankuiEntity discussshiyongfankui,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<DiscussshiyongfankuiEntity> ew = new EntityWrapper<DiscussshiyongfankuiEntity>();


        //查询结果
		PageUtils page = discussshiyongfankuiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussshiyongfankui), params), params));
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
    public R list(@RequestParam Map<String, Object> params,DiscussshiyongfankuiEntity discussshiyongfankui,
                @RequestParam(required = false) Double thumbsupnumstart,
                @RequestParam(required = false) Double thumbsupnumend,
                @RequestParam(required = false) Double crazilynumstart,
                @RequestParam(required = false) Double crazilynumend,
                @RequestParam(required = false) Double istopstart,
                @RequestParam(required = false) Double istopend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<DiscussshiyongfankuiEntity> ew = new EntityWrapper<DiscussshiyongfankuiEntity>();
        if(thumbsupnumstart!=null) ew.ge("thumbsupnum", thumbsupnumstart);
        if(thumbsupnumend!=null) ew.le("thumbsupnum", thumbsupnumend);
        if(crazilynumstart!=null) ew.ge("crazilynum", crazilynumstart);
        if(crazilynumend!=null) ew.le("crazilynum", crazilynumend);
        if(istopstart!=null) ew.ge("istop", istopstart);
        if(istopend!=null) ew.le("istop", istopend);

        //查询结果
		PageUtils page = discussshiyongfankuiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussshiyongfankui), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( DiscussshiyongfankuiEntity discussshiyongfankui){
       	EntityWrapper<DiscussshiyongfankuiEntity> ew = new EntityWrapper<DiscussshiyongfankuiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( discussshiyongfankui, "discussshiyongfankui"));
        return R.ok().put("data", discussshiyongfankuiService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(DiscussshiyongfankuiEntity discussshiyongfankui){
        EntityWrapper< DiscussshiyongfankuiEntity> ew = new EntityWrapper< DiscussshiyongfankuiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( discussshiyongfankui, "discussshiyongfankui"));
		DiscussshiyongfankuiView discussshiyongfankuiView =  discussshiyongfankuiService.selectView(ew);
		return R.ok("查询使用反馈评论成功").put("data", discussshiyongfankuiView);
    }

    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        DiscussshiyongfankuiEntity discussshiyongfankui = discussshiyongfankuiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(discussshiyongfankui,deSens);
        return R.ok().put("data", discussshiyongfankui);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        DiscussshiyongfankuiEntity discussshiyongfankui = discussshiyongfankuiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(discussshiyongfankui,deSens);
        return R.ok().put("data", discussshiyongfankui);
    }




    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增使用反馈评论")
    public R save(@RequestBody DiscussshiyongfankuiEntity discussshiyongfankui, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discussshiyongfankui);
        discussshiyongfankuiService.insert(discussshiyongfankui);
        return R.ok().put("data",discussshiyongfankui.getId());
    }

    /**
     * 前台保存
     */
    @SysLog("新增使用反馈评论")
    @RequestMapping("/add")
    public R add(@RequestBody DiscussshiyongfankuiEntity discussshiyongfankui, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discussshiyongfankui);
        discussshiyongfankuiService.insert(discussshiyongfankui);
        return R.ok().put("data",discussshiyongfankui.getId());
    }



     /**
     * 获取用户密保
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        DiscussshiyongfankuiEntity discussshiyongfankui = discussshiyongfankuiService.selectOne(new EntityWrapper<DiscussshiyongfankuiEntity>().eq("", username));
        return R.ok().put("data", discussshiyongfankui);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody DiscussshiyongfankuiEntity discussshiyongfankui, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discussshiyongfankui);
        //全部更新
        discussshiyongfankuiService.updateById(discussshiyongfankui);
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除使用反馈评论")
    public R delete(@RequestBody Long[] ids){
        discussshiyongfankuiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

	/**
     * 前台智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,DiscussshiyongfankuiEntity discussshiyongfankui, HttpServletRequest request,String pre){
        EntityWrapper<DiscussshiyongfankuiEntity> ew = new EntityWrapper<DiscussshiyongfankuiEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        // 组装参数
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicktime");
        params.put("order", "desc");

		PageUtils page = discussshiyongfankuiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, discussshiyongfankui), params), params));
        return R.ok().put("data", page);
    }









}
