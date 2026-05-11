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
 * 捐赠证书 模块后端接口。
 * 说明：供管理端、前台端对应页面通过 HTTP 请求调用。
 */
@RestController
@RequestMapping("/juanzengzhengshu")
public class JuanzengzhengshuController {
    @Autowired
    private JuanzengzhengshuService juanzengzhengshuService;
    /**
     * 功能：分页查询捐赠证书数据。
     * 使用端：管理端捐赠证书管理列表页。
     * 前端触发：admin/src/views/modules/juanzengzhengshu/list.vue 通过 $http.get('juanzengzhengshu/page') 触发。
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
     * 功能：查询捐赠证书前台列表数据。
     * 使用端：前台捐赠证书列表页，部分管理端通用列表也会复用。
     * 前端触发：front/src/pages/juanzengzhengshu/list.vue 通过 $http.get('juanzengzhengshu/list') 触发。
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
     * 功能：查询捐赠证书不分页列表。
     * 使用端：前后台表单页的下拉、联动和重复校验场景。
     * 前端触发：表单页按 tableName 拼接 $http.get('juanzengzhengshu/lists') 触发。
     */
    @RequestMapping("/lists")
    public R list( JuanzengzhengshuEntity juanzengzhengshu){
       	EntityWrapper<JuanzengzhengshuEntity> ew = new EntityWrapper<JuanzengzhengshuEntity>();
      	ew.allEq(MPUtil.allEQMapPre( juanzengzhengshu, "juanzengzhengshu"));
        return R.ok().put("data", juanzengzhengshuService.selectListView(ew));
    }
    /**
     * 功能：按条件查询单条捐赠证书视图数据。
     * 使用端：前后台表单联动或详情回显辅助接口。
     * 前端触发：前端按条件通过 $http.get('juanzengzhengshu/query') 触发。
     */
    @RequestMapping("/query")
    public R query(JuanzengzhengshuEntity juanzengzhengshu){
        EntityWrapper< JuanzengzhengshuEntity> ew = new EntityWrapper< JuanzengzhengshuEntity>();
 		ew.allEq(MPUtil.allEQMapPre( juanzengzhengshu, "juanzengzhengshu"));
		JuanzengzhengshuView juanzengzhengshuView =  juanzengzhengshuService.selectView(ew);
		return R.ok("查询捐赠证书成功").put("data", juanzengzhengshuView);
    }
    /**
     * 功能：查询捐赠证书管理端详情。
     * 使用端：管理端捐赠证书列表页、编辑页。
     * 前端触发：管理端通过 $http.get('juanzengzhengshu/info/{id}') 触发。
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
     * 功能：查询捐赠证书前台详情。
     * 使用端：前台捐赠证书详情页或编辑回显页。
     * 前端触发：front/src/pages/juanzengzhengshu/detail.vue 或 add.vue 触发。
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
     * 功能：管理端新增捐赠证书记录。
     * 使用端：管理端捐赠证书新增表单。
     * 前端触发：管理端表单通过 $http.post('juanzengzhengshu/save') 触发。
     */
    @RequestMapping("/save")
    @SysLog("新增捐赠证书")
    public R save(@RequestBody JuanzengzhengshuEntity juanzengzhengshu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(juanzengzhengshu);
        juanzengzhengshuService.insert(juanzengzhengshu);
        return R.ok().put("data",juanzengzhengshu.getId());
    }
    /**
     * 功能：前台新增捐赠证书记录。
     * 使用端：前台捐赠证书新增表单或详情页操作。
     * 前端触发：前台表单通过 $http.post('juanzengzhengshu/add') 触发。
     */
    @SysLog("新增捐赠证书")
    @RequestMapping("/add")
    public R add(@RequestBody JuanzengzhengshuEntity juanzengzhengshu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(juanzengzhengshu);
        juanzengzhengshuService.insert(juanzengzhengshu);
        return R.ok().put("data",juanzengzhengshu.getId());
    }
    /**
     * 功能：修改捐赠证书记录。
     * 使用端：管理端编辑页、前台个人中心或详情页操作。
     * 前端触发：前端表单提交时通过 $http.post('juanzengzhengshu/update') 触发。
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
     * 功能：删除捐赠证书记录。
     * 使用端：管理端列表页或前台详情页/我的列表。
     * 前端触发：删除按钮通过 $http.post('juanzengzhengshu/delete') 触发。
     */
    @RequestMapping("/delete")
    @SysLog("删除捐赠证书")
    public R delete(@RequestBody Long[] ids){
        juanzengzhengshuService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}
