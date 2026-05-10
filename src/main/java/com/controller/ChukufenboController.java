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
 * 出库分拨 模块后端接口。
 * 说明：供管理端、前台端对应页面通过 HTTP 请求调用。
 */
@RestController
@RequestMapping("/chukufenbo")
public class ChukufenboController {
    @Autowired
    private ChukufenboService chukufenboService;
    /**
     * 功能：分页查询出库分拨数据。
     * 使用端：管理端出库分拨管理列表页。
     * 前端触发：admin/src/views/modules/chukufenbo/list.vue 通过 $http.get('chukufenbo/page') 触发。
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
     * 功能：查询出库分拨前台列表数据。
     * 使用端：前台出库分拨列表页，部分管理端通用列表也会复用。
     * 前端触发：front/src/pages/chukufenbo/list.vue 通过 $http.get('chukufenbo/list') 触发。
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
     * 功能：查询出库分拨不分页列表。
     * 使用端：前后台表单页的下拉、联动和重复校验场景。
     * 前端触发：表单页按 tableName 拼接 $http.get('chukufenbo/lists') 触发。
     */
    @RequestMapping("/lists")
    public R list( ChukufenboEntity chukufenbo){
       	EntityWrapper<ChukufenboEntity> ew = new EntityWrapper<ChukufenboEntity>();
      	ew.allEq(MPUtil.allEQMapPre( chukufenbo, "chukufenbo"));
        return R.ok().put("data", chukufenboService.selectListView(ew));
    }

    /**
     * 功能：按条件查询单条出库分拨视图数据。
     * 使用端：前后台表单联动或详情回显辅助接口。
     * 前端触发：前端按条件通过 $http.get('chukufenbo/query') 触发。
     */
    @RequestMapping("/query")
    public R query(ChukufenboEntity chukufenbo){
        EntityWrapper< ChukufenboEntity> ew = new EntityWrapper< ChukufenboEntity>();
 		ew.allEq(MPUtil.allEQMapPre( chukufenbo, "chukufenbo"));
		ChukufenboView chukufenboView =  chukufenboService.selectView(ew);
		return R.ok("查询出库分拨成功").put("data", chukufenboView);
    }

    /**
     * 功能：查询出库分拨管理端详情。
     * 使用端：管理端出库分拨列表页、编辑页。
     * 前端触发：管理端通过 $http.get('chukufenbo/info/{id}') 触发。
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
     * 功能：查询出库分拨前台详情。
     * 使用端：前台出库分拨详情页或编辑回显页。
     * 前端触发：front/src/pages/chukufenbo/detail.vue 或 add.vue 触发。
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
     * 功能：管理端新增出库分拨记录。
     * 使用端：管理端出库分拨新增表单。
     * 前端触发：管理端表单通过 $http.post('chukufenbo/save') 触发。
     */
    @RequestMapping("/save")
    @SysLog("新增出库分拨")
    public R save(@RequestBody ChukufenboEntity chukufenbo, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chukufenbo);
        chukufenboService.insert(chukufenbo);
        return R.ok().put("data",chukufenbo.getId());
    }

    /**
     * 功能：前台新增出库分拨记录。
     * 使用端：前台出库分拨新增表单或详情页操作。
     * 前端触发：前台表单通过 $http.post('chukufenbo/add') 触发。
     */
    @SysLog("新增出库分拨")
    @RequestMapping("/add")
    public R add(@RequestBody ChukufenboEntity chukufenbo, HttpServletRequest request){
        //ValidatorUtils.validateEntity(chukufenbo);
        chukufenboService.insert(chukufenbo);
        return R.ok().put("data",chukufenbo.getId());
    }
    /**
     * 功能：修改出库分拨记录。
     * 使用端：管理端编辑页、前台个人中心或详情页操作。
     * 前端触发：前端表单提交时通过 $http.post('chukufenbo/update') 触发。
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
     * 功能：删除出库分拨记录。
     * 使用端：管理端列表页或前台详情页/我的列表。
     * 前端触发：删除按钮通过 $http.post('chukufenbo/delete') 触发。
     */
    @RequestMapping("/delete")
    @SysLog("删除出库分拨")
    public R delete(@RequestBody Long[] ids){
        chukufenboService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}
