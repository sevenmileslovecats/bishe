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

import com.entity.ShiyongfankuiEntity;
import com.entity.view.ShiyongfankuiView;

import com.service.ShiyongfankuiService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;
import com.service.StoreupService;
import com.entity.StoreupEntity;

/**
 * 使用反馈 模块后端接口。
 * 说明：供管理端、前台端对应页面通过 HTTP 请求调用。
 */
@RestController
@RequestMapping("/shiyongfankui")
public class ShiyongfankuiController {
    @Autowired
    private ShiyongfankuiService shiyongfankuiService;

    @Autowired
    private StoreupService storeupService;
    /**
     * 功能：分页查询使用反馈数据。
     * 使用端：管理端使用反馈管理列表页。
     * 前端触发：admin/src/views/modules/shiyongfankui/list.vue 通过 $http.get('shiyongfankui/page') 触发。
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ShiyongfankuiEntity shiyongfankui,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("jieshoujigou")) {
			shiyongfankui.setJigouzhanghao((String)request.getSession().getAttribute("username"));
		}
        //设置查询条件
        EntityWrapper<ShiyongfankuiEntity> ew = new EntityWrapper<ShiyongfankuiEntity>();


        //查询结果
		PageUtils page = shiyongfankuiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shiyongfankui), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }
    /**
     * 功能：查询使用反馈前台列表数据。
     * 使用端：前台使用反馈列表页，部分管理端通用列表也会复用。
     * 前端触发：front/src/pages/shiyongfankui/list.vue 通过 $http.get('shiyongfankui/list') 触发。
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ShiyongfankuiEntity shiyongfankui,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date fankuishijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date fankuishijianend,
                @RequestParam(required = false) Double wuzishuliangstart,
                @RequestParam(required = false) Double wuzishuliangend,
                @RequestParam(required = false) Double discussnumstart,
                @RequestParam(required = false) Double discussnumend,
                @RequestParam(required = false) Double storeupnumstart,
                @RequestParam(required = false) Double storeupnumend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<ShiyongfankuiEntity> ew = new EntityWrapper<ShiyongfankuiEntity>();
        if(fankuishijianstart!=null) ew.ge("fankuishijian", fankuishijianstart);
        if(fankuishijianend!=null) ew.le("fankuishijian", fankuishijianend);
        if(wuzishuliangstart!=null) ew.ge("wuzishuliang", wuzishuliangstart);
        if(wuzishuliangend!=null) ew.le("wuzishuliang", wuzishuliangend);
        if(discussnumstart!=null) ew.ge("discussnum", discussnumstart);
        if(discussnumend!=null) ew.le("discussnum", discussnumend);
        if(storeupnumstart!=null) ew.ge("storeupnum", storeupnumstart);
        if(storeupnumend!=null) ew.le("storeupnum", storeupnumend);

        //查询结果
		PageUtils page = shiyongfankuiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, shiyongfankui), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }
    /**
     * 功能：查询使用反馈不分页列表。
     * 使用端：前后台表单页的下拉、联动和重复校验场景。
     * 前端触发：表单页按 tableName 拼接 $http.get('shiyongfankui/lists') 触发。
     */
    @RequestMapping("/lists")
    public R list( ShiyongfankuiEntity shiyongfankui){
       	EntityWrapper<ShiyongfankuiEntity> ew = new EntityWrapper<ShiyongfankuiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( shiyongfankui, "shiyongfankui"));
        return R.ok().put("data", shiyongfankuiService.selectListView(ew));
    }

    /**
     * 功能：按条件查询单条使用反馈视图数据。
     * 使用端：前后台表单联动或详情回显辅助接口。
     * 前端触发：前端按条件通过 $http.get('shiyongfankui/query') 触发。
     */
    @RequestMapping("/query")
    public R query(ShiyongfankuiEntity shiyongfankui){
        EntityWrapper< ShiyongfankuiEntity> ew = new EntityWrapper< ShiyongfankuiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( shiyongfankui, "shiyongfankui"));
		ShiyongfankuiView shiyongfankuiView =  shiyongfankuiService.selectView(ew);
		return R.ok("查询使用反馈成功").put("data", shiyongfankuiView);
    }
    /**
     * 功能：查询使用反馈管理端详情。
     * 使用端：管理端使用反馈列表页、编辑页。
     * 前端触发：管理端通过 $http.get('shiyongfankui/info/{id}') 触发。
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ShiyongfankuiEntity shiyongfankui = shiyongfankuiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(shiyongfankui,deSens);
        return R.ok().put("data", shiyongfankui);
    }

    /**
     * 功能：查询使用反馈前台详情。
     * 使用端：前台使用反馈详情页或编辑回显页。
     * 前端触发：front/src/pages/shiyongfankui/detail.vue 或 add.vue 触发。
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ShiyongfankuiEntity shiyongfankui = shiyongfankuiService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(shiyongfankui,deSens);
        return R.ok().put("data", shiyongfankui);
    }
    /**
     * 功能：管理端新增使用反馈记录。
     * 使用端：管理端使用反馈新增表单。
     * 前端触发：管理端表单通过 $http.post('shiyongfankui/save') 触发。
     */
    @RequestMapping("/save")
    @SysLog("新增使用反馈")
    public R save(@RequestBody ShiyongfankuiEntity shiyongfankui, HttpServletRequest request){
        //ValidatorUtils.validateEntity(shiyongfankui);
        shiyongfankuiService.insert(shiyongfankui);
        return R.ok().put("data",shiyongfankui.getId());
    }
    /**
     * 功能：前台新增使用反馈记录。
     * 使用端：前台使用反馈新增表单或详情页操作。
     * 前端触发：前台表单通过 $http.post('shiyongfankui/add') 触发。
     */
    @SysLog("新增使用反馈")
    @RequestMapping("/add")
    public R add(@RequestBody ShiyongfankuiEntity shiyongfankui, HttpServletRequest request){
        //ValidatorUtils.validateEntity(shiyongfankui);
        shiyongfankuiService.insert(shiyongfankui);
        return R.ok().put("data",shiyongfankui.getId());
    }
    /**
     * 功能：修改使用反馈记录。
     * 使用端：管理端编辑页、前台个人中心或详情页操作。
     * 前端触发：前端表单提交时通过 $http.post('shiyongfankui/update') 触发。
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改使用反馈")
    public R update(@RequestBody ShiyongfankuiEntity shiyongfankui, HttpServletRequest request){
        //ValidatorUtils.validateEntity(shiyongfankui);
        //全部更新
        shiyongfankuiService.updateById(shiyongfankui);
        return R.ok();
    }
    /**
     * 功能：删除使用反馈记录。
     * 使用端：管理端列表页或前台详情页/我的列表。
     * 前端触发：删除按钮通过 $http.post('shiyongfankui/delete') 触发。
     */
    @RequestMapping("/delete")
    @SysLog("删除使用反馈")
    public R delete(@RequestBody Long[] ids){
        shiyongfankuiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}
