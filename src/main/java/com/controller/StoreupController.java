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

import com.entity.StoreupEntity;
import com.entity.view.StoreupView;

import com.service.StoreupService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 收藏/评论 模块后端接口。
 * 说明：供管理端、前台端对应页面通过 HTTP 请求调用。
 */
@RestController
@RequestMapping("/storeup")
public class StoreupController {
    @Autowired
    private StoreupService storeupService;
    /**
     * 功能：分页查询收藏/评论数据。
     * 使用端：管理端收藏/评论管理列表页。
     * 前端触发：admin/src/views/modules/storeup/list.vue 通过 $http.get('storeup/page') 触发。
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,StoreupEntity storeup,
		HttpServletRequest request){
        if(!request.getSession().getAttribute("role").toString().equals("管理员")) {
            storeup.setUserid((Long)request.getSession().getAttribute("userId"));
        }
        //设置查询条件
        EntityWrapper<StoreupEntity> ew = new EntityWrapper<StoreupEntity>();


        //查询结果
		PageUtils page = storeupService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, storeup), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }
    /**
     * 功能：查询收藏/评论前台列表数据。
     * 使用端：前台收藏/评论列表页，部分管理端通用列表也会复用。
     * 前端触发：front/src/pages/storeup/list.vue 通过 $http.get('storeup/list') 触发。
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,StoreupEntity storeup,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<StoreupEntity> ew = new EntityWrapper<StoreupEntity>();

        //查询结果
		PageUtils page = storeupService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, storeup), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }
    /**
     * 功能：查询收藏/评论不分页列表。
     * 使用端：前后台表单页的下拉、联动和重复校验场景。
     * 前端触发：表单页按 tableName 拼接 $http.get('storeup/lists') 触发。
     */
    @RequestMapping("/lists")
    public R list( StoreupEntity storeup){
       	EntityWrapper<StoreupEntity> ew = new EntityWrapper<StoreupEntity>();
      	ew.allEq(MPUtil.allEQMapPre( storeup, "storeup"));
        return R.ok().put("data", storeupService.selectListView(ew));
    }
    /**
     * 功能：按条件查询单条收藏/评论视图数据。
     * 使用端：前后台表单联动或详情回显辅助接口。
     * 前端触发：前端按条件通过 $http.get('storeup/query') 触发。
     */
    @RequestMapping("/query")
    public R query(StoreupEntity storeup){
        EntityWrapper< StoreupEntity> ew = new EntityWrapper< StoreupEntity>();
 		ew.allEq(MPUtil.allEQMapPre( storeup, "storeup"));
		StoreupView storeupView =  storeupService.selectView(ew);
		return R.ok("查询收藏表成功").put("data", storeupView);
    }
    /**
     * 功能：查询收藏/评论管理端详情。
     * 使用端：管理端收藏/评论列表页、编辑页。
     * 前端触发：管理端通过 $http.get('storeup/info/{id}') 触发。
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        StoreupEntity storeup = storeupService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(storeup,deSens);
        return R.ok().put("data", storeup);
    }
    /**
     * 功能：查询收藏/评论前台详情。
     * 使用端：前台收藏/评论详情页或编辑回显页。
     * 前端触发：front/src/pages/storeup/detail.vue 或 add.vue 触发。
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        StoreupEntity storeup = storeupService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(storeup,deSens);
        return R.ok().put("data", storeup);
    }
    /**
     * 功能：管理端新增收藏/评论记录。
     * 使用端：管理端收藏/评论新增表单。
     * 前端触发：管理端表单通过 $http.post('storeup/save') 触发。
     */
    @RequestMapping("/save")
    @SysLog("新增收藏表")
    public R save(@RequestBody StoreupEntity storeup, HttpServletRequest request){
        //ValidatorUtils.validateEntity(storeup);
        if(storeup.getUserid()==null){
            storeup.setUserid((Long) request.getSession().getAttribute("userId"));
        }
        storeupService.insert(storeup);
        return R.ok().put("data",storeup.getId());
    }
    /**
     * 功能：前台新增收藏/评论记录。
     * 使用端：前台收藏/评论新增表单或详情页操作。
     * 前端触发：前台表单通过 $http.post('storeup/add') 触发。
     */
    @SysLog("新增收藏表")
    @RequestMapping("/add")
    public R add(@RequestBody StoreupEntity storeup, HttpServletRequest request){
        //ValidatorUtils.validateEntity(storeup);
        storeupService.insert(storeup);
        return R.ok().put("data",storeup.getId());
    }
    /**
     * 功能：校验收藏/评论账号是否存在。
     * 使用端：注册、找回或账号校验表单。
     * 前端触发：前端通过 $http.get('storeup/security') 触发。
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        StoreupEntity storeup = storeupService.selectOne(new EntityWrapper<StoreupEntity>().eq("", username));
        return R.ok().put("data", storeup);
    }
    /**
     * 功能：修改收藏/评论记录。
     * 使用端：管理端编辑页、前台个人中心或详情页操作。
     * 前端触发：前端表单提交时通过 $http.post('storeup/update') 触发。
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody StoreupEntity storeup, HttpServletRequest request){
        //ValidatorUtils.validateEntity(storeup);
        //全部更新
        storeupService.updateById(storeup);
        return R.ok();
    }
    /**
     * 功能：删除收藏/评论记录。
     * 使用端：管理端列表页或前台详情页/我的列表。
     * 前端触发：删除按钮通过 $http.post('storeup/delete') 触发。
     */
    @RequestMapping("/delete")
    @SysLog("删除收藏表")
    public R delete(@RequestBody Long[] ids){
        storeupService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    /**
     * 功能：按点击量等条件返回收藏/评论自动排序列表。
     * 使用端：前台推荐列表或首页推荐区域。
     * 前端触发：前端推荐组件通过 $http.get('storeup/autoSort') 触发。
     */
    @IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,StoreupEntity storeup, HttpServletRequest request,String pre){
        EntityWrapper<StoreupEntity> ew = new EntityWrapper<StoreupEntity>();
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

		PageUtils page = storeupService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, storeup), params), params));
        return R.ok().put("data", page);
    }
}
