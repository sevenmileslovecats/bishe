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
import lombok.Data;

import com.entity.view.YanshoujiluView;

import cn.hutool.core.lang.Console;

import java.io.IOException;

/**
 * 验收记录 模块后端接口。
 * 说明：供管理端、前台端对应页面通过 HTTP 请求调用。
 */
@RestController
@RequestMapping("/yanshoujilu")
public class YanshoujiluController {
    @Autowired
    private YanshoujiluService yanshoujiluService;

    @Autowired
    private JuanzengwuziService juanzengwuziService;

    @Autowired
    private WuzixinxiService wuzixinxiService;

    @Data
    public static class AcceptDTO {
        private Long juanzengwuziId;
        private YanshoujiluEntity yanshoujilu;
    }


    /**
     * 功能：分页查询验收记录数据。
     * 使用端：管理端验收记录管理列表页。
     * 前端触发：admin/src/views/modules/yanshoujilu/list.vue 通过 $http.get('yanshoujilu/page') 触发。
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,YanshoujiluEntity yanshoujilu,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("juanzengren")) {
			yanshoujilu.setZhanghao((String)request.getSession().getAttribute("username"));
		}
        //设置查询条件
        EntityWrapper<YanshoujiluEntity> ew = new EntityWrapper<YanshoujiluEntity>();


        //查询结果
		PageUtils page = yanshoujiluService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yanshoujilu), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


    /**
     * 功能：查询验收记录前台列表数据。
     * 使用端：前台验收记录列表页，部分管理端通用列表也会复用。
     * 前端触发：front/src/pages/yanshoujilu/list.vue 通过 $http.get('yanshoujilu/list') 触发。
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,YanshoujiluEntity yanshoujilu,
                @RequestParam(required = false) Double wuzishuliangstart,
                @RequestParam(required = false) Double wuzishuliangend,
                @RequestParam(required = false) Double wuzizhongliangstart,
                @RequestParam(required = false) Double wuzizhongliangend,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date yanshoushijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date yanshoushijianend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<YanshoujiluEntity> ew = new EntityWrapper<YanshoujiluEntity>();
        if(wuzishuliangstart!=null) ew.ge("wuzishuliang", wuzishuliangstart);
        if(wuzishuliangend!=null) ew.le("wuzishuliang", wuzishuliangend);
        if(wuzizhongliangstart!=null) ew.ge("wuzizhongliang", wuzizhongliangstart);
        if(wuzizhongliangend!=null) ew.le("wuzizhongliang", wuzizhongliangend);
        if(yanshoushijianstart!=null) ew.ge("yanshoushijian", yanshoushijianstart);
        if(yanshoushijianend!=null) ew.le("yanshoushijian", yanshoushijianend);

        //查询结果
		PageUtils page = yanshoujiluService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yanshoujilu), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




    /**
     * 功能：查询验收记录不分页列表。
     * 使用端：前后台表单页的下拉、联动和重复校验场景。
     * 前端触发：表单页按 tableName 拼接 $http.get('yanshoujilu/lists') 触发。
     */
    @RequestMapping("/lists")
    public R list( YanshoujiluEntity yanshoujilu){
       	EntityWrapper<YanshoujiluEntity> ew = new EntityWrapper<YanshoujiluEntity>();
      	ew.allEq(MPUtil.allEQMapPre( yanshoujilu, "yanshoujilu"));
        return R.ok().put("data", yanshoujiluService.selectListView(ew));
    }

    /**
     * 功能：按条件查询单条验收记录视图数据。
     * 使用端：前后台表单联动或详情回显辅助接口。
     * 前端触发：前端按条件通过 $http.get('yanshoujilu/query') 触发。
     */
    @RequestMapping("/query")
    public R query(YanshoujiluEntity yanshoujilu){
        EntityWrapper< YanshoujiluEntity> ew = new EntityWrapper< YanshoujiluEntity>();
 		ew.allEq(MPUtil.allEQMapPre( yanshoujilu, "yanshoujilu"));
		YanshoujiluView yanshoujiluView =  yanshoujiluService.selectView(ew);
		return R.ok("查询验收记录成功").put("data", yanshoujiluView);
    }

    /**
     * 功能：查询验收记录管理端详情。
     * 使用端：管理端验收记录列表页、编辑页。
     * 前端触发：管理端通过 $http.get('yanshoujilu/info/{id}') 触发。
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        YanshoujiluEntity yanshoujilu = yanshoujiluService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(yanshoujilu,deSens);
        return R.ok().put("data", yanshoujilu);
    }

    /**
     * 功能：查询验收记录前台详情。
     * 使用端：前台验收记录详情页或编辑回显页。
     * 前端触发：front/src/pages/yanshoujilu/detail.vue 或 add.vue 触发。
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        YanshoujiluEntity yanshoujilu = yanshoujiluService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(yanshoujilu,deSens);
        return R.ok().put("data", yanshoujilu);
    }




    /**
     * 功能：管理端新增验收记录记录。
     * 使用端：管理端验收记录新增表单。
     * 前端触发：管理端表单通过 $http.post('yanshoujilu/save') 触发。
     */
    @RequestMapping("/save")
    @SysLog("新增验收记录")
    @Transactional
    public R save(@RequestBody YanshoujiluEntity yanshoujilu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(yanshoujilu);
        boolean inserted = yanshoujiluService.insert(yanshoujilu);
        if(!inserted) {
            throw new RuntimeException("验收记录保存失败");
        }
        insertWuzixinxi(yanshoujilu);
        return R.ok().put("data",yanshoujilu.getId());
    }

    /**
     * 功能：前台新增验收记录记录。
     * 使用端：前台验收记录新增表单或详情页操作。
     * 前端触发：前台表单通过 $http.post('yanshoujilu/add') 触发。
     */
    @SysLog("新增验收记录")
    @RequestMapping("/add")
    @Transactional
    public R add(@RequestBody YanshoujiluEntity yanshoujilu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(yanshoujilu);
        boolean inserted = yanshoujiluService.insert(yanshoujilu);
        if(!inserted) {
            throw new RuntimeException("验收记录保存失败");
        }
        insertWuzixinxi(yanshoujilu);
        return R.ok().put("data",yanshoujilu.getId());
    }

    /**
     * 功能：验收捐赠物资并同步生成/更新验收记录。
     * 使用端：管理端或前台验收记录表单。
     * 前端触发：front/src/pages/yanshoujilu/add.vue 通过 $http.post('yanshoujilu/accept') 触发。
     */
    @RequestMapping("/accept")
    @Transactional
    @SysLog("验收捐赠物资")
    public R accept(@RequestBody AcceptDTO dto, HttpServletRequest request){

        if(dto == null || dto.getJuanzengwuziId() == null || dto.getYanshoujilu() == null) {
            return R.error("参数错误");
        }
        YanshoujiluEntity yanshoujilu = dto.getYanshoujilu();
        if(yanshoujilu.getWuzimingcheng() == null || yanshoujilu.getWuzishuliang() == null) {
            return R.error("物资名称或数量不能为空");
        }
        JuanzengwuziEntity juanzengwuzi = juanzengwuziService.selectById(dto.getJuanzengwuziId());
        if(juanzengwuzi == null) {
            return R.error("捐赠物资不存在");
        }

        if("已验收".equals(juanzengwuzi.getYanshouzhuangtai())) {

            return R.error("已验收");
        }

        boolean inserted = yanshoujiluService.insert(yanshoujilu);
        if(!inserted) {
            throw new RuntimeException("验收记录保存失败");
        }

        insertWuzixinxi(yanshoujilu);

        juanzengwuzi.setYanshouzhuangtai("已验收");
        boolean ok = juanzengwuziService.updateById(juanzengwuzi);
        if(!ok) {
            throw new RuntimeException("验收状态更新失败");
        }
        return R.ok().put("data", yanshoujilu.getId());
    }

    private void insertWuzixinxi(YanshoujiluEntity yanshoujilu) {
        if(StringUtils.isBlank(yanshoujilu.getJuanzengbianhao())) {
            throw new RuntimeException("donation number is required");
        }
        WuzixinxiEntity wuzixinxi = new WuzixinxiEntity();
        wuzixinxi.setJuanzengbianhao(yanshoujilu.getJuanzengbianhao());
        wuzixinxi.setWuzimingcheng(yanshoujilu.getWuzimingcheng());
        wuzixinxi.setWuzizhonglei(yanshoujilu.getWuzizhonglei());
        wuzixinxi.setWuziguige(yanshoujilu.getXinjiuchengdu());
        wuzixinxi.setWuzixiangqing(yanshoujilu.getWuzishuoming());
        wuzixinxi.setWuzitupian(yanshoujilu.getWuzitupian());
        wuzixinxi.setWuzishuliang(yanshoujilu.getWuzishuliang());
        wuzixinxi.setBaozhiqi(parseDate(yanshoujilu.getYouxiaoqi()));
        wuzixinxi.setCunchuweizhi("待入库");
        boolean inserted = wuzixinxiService.insert(wuzixinxi);
        if(!inserted) {
            throw new RuntimeException("material information save failed");
        }
    }

    private Date parseDate(String date) {
        if(StringUtils.isBlank(date)) {
            return null;
        }
        String[] patterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"};
        for(String pattern : patterns) {
            try {
                return new SimpleDateFormat(pattern).parse(date);
            } catch (ParseException e) {
                // try next supported pattern
            }
        }
        return null;
    }





    /**
     * 功能：修改验收记录记录。
     * 使用端：管理端编辑页、前台个人中心或详情页操作。
     * 前端触发：前端表单提交时通过 $http.post('yanshoujilu/update') 触发。
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改验收记录")
    public R update(@RequestBody YanshoujiluEntity yanshoujilu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(yanshoujilu);
        //全部更新
        yanshoujiluService.updateById(yanshoujilu);
        return R.ok();
    }





    /**
     * 功能：删除验收记录记录。
     * 使用端：管理端列表页或前台详情页/我的列表。
     * 前端触发：删除按钮通过 $http.post('yanshoujilu/delete') 触发。
     */
    @RequestMapping("/delete")
    @SysLog("删除验收记录")
    public R delete(@RequestBody Long[] ids){
        yanshoujiluService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
