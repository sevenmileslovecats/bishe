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
 * 异议反馈 模块后端接口。
 * 说明：供管理端、前台端对应页面通过 HTTP 请求调用。
 */
@RestController
@RequestMapping("/yiyifankui")
public class YiyifankuiController {
    @Autowired
    private YiyifankuiService yiyifankuiService;
    /**
     * 功能：分页查询异议反馈数据。
     * 使用端：管理端异议反馈管理列表页。
     * 前端触发：admin/src/views/modules/yiyifankui/list.vue 通过 $http.get('yiyifankui/page') 触发。
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
     * 功能：查询异议反馈前台列表数据。
     * 使用端：前台异议反馈列表页，部分管理端通用列表也会复用。
     * 前端触发：front/src/pages/yiyifankui/list.vue 通过 $http.get('yiyifankui/list') 触发。
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
     * 功能：查询异议反馈不分页列表。
     * 使用端：前后台表单页的下拉、联动和重复校验场景。
     * 前端触发：表单页按 tableName 拼接 $http.get('yiyifankui/lists') 触发。
     */
    @RequestMapping("/lists")
    public R list( YiyifankuiEntity yiyifankui){
       	EntityWrapper<YiyifankuiEntity> ew = new EntityWrapper<YiyifankuiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( yiyifankui, "yiyifankui"));
        return R.ok().put("data", yiyifankuiService.selectListView(ew));
    }
    /**
     * 功能：按条件查询单条异议反馈视图数据。
     * 使用端：前后台表单联动或详情回显辅助接口。
     * 前端触发：前端按条件通过 $http.get('yiyifankui/query') 触发。
     */
    @RequestMapping("/query")
    public R query(YiyifankuiEntity yiyifankui){
        EntityWrapper< YiyifankuiEntity> ew = new EntityWrapper< YiyifankuiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( yiyifankui, "yiyifankui"));
		YiyifankuiView yiyifankuiView =  yiyifankuiService.selectView(ew);
		return R.ok("查询异议反馈成功").put("data", yiyifankuiView);
    }
    /**
     * 功能：查询异议反馈管理端详情。
     * 使用端：管理端异议反馈列表页、编辑页。
     * 前端触发：管理端通过 $http.get('yiyifankui/info/{id}') 触发。
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
     * 功能：查询异议反馈前台详情。
     * 使用端：前台异议反馈详情页或编辑回显页。
     * 前端触发：front/src/pages/yiyifankui/detail.vue 或 add.vue 触发。
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
     * 功能：管理端新增异议反馈记录。
     * 使用端：管理端异议反馈新增表单。
     * 前端触发：管理端表单通过 $http.post('yiyifankui/save') 触发。
     */
    @RequestMapping("/save")
    @SysLog("新增异议反馈")
    public R save(@RequestBody YiyifankuiEntity yiyifankui, HttpServletRequest request){
        //ValidatorUtils.validateEntity(yiyifankui);
        yiyifankuiService.insert(yiyifankui);
        return R.ok().put("data",yiyifankui.getId());
    }
    /**
     * 功能：前台新增异议反馈记录。
     * 使用端：前台异议反馈新增表单或详情页操作。
     * 前端触发：前台表单通过 $http.post('yiyifankui/add') 触发。
     */
    @SysLog("新增异议反馈")
    @RequestMapping("/add")
    public R add(@RequestBody YiyifankuiEntity yiyifankui, HttpServletRequest request){
        //ValidatorUtils.validateEntity(yiyifankui);
        yiyifankuiService.insert(yiyifankui);
        return R.ok().put("data",yiyifankui.getId());
    }
    /**
     * 功能：修改异议反馈记录。
     * 使用端：管理端编辑页、前台个人中心或详情页操作。
     * 前端触发：前端表单提交时通过 $http.post('yiyifankui/update') 触发。
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
     * 功能：批量审核异议反馈记录。
     * 使用端：管理端异议反馈审核按钮。
     * 前端触发：列表页审核操作通过 $http.post('yiyifankui/shBatch') 触发。
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
     * 功能：删除异议反馈记录。
     * 使用端：管理端列表页或前台详情页/我的列表。
     * 前端触发：删除按钮通过 $http.post('yiyifankui/delete') 触发。
     */
    @RequestMapping("/delete")
    @SysLog("删除异议反馈")
    public R delete(@RequestBody Long[] ids){
        yiyifankuiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}
