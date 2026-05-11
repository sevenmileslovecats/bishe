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
 * 使用反馈评论 模块后端接口。
 * 说明：供管理端、前台端对应页面通过 HTTP 请求调用。
 */
@RestController
@RequestMapping("/discussshiyongfankui")
public class DiscussshiyongfankuiController {
    @Autowired
    private DiscussshiyongfankuiService discussshiyongfankuiService;
    /**
     * 功能：分页查询使用反馈评论数据。
     * 使用端：管理端使用反馈评论管理列表页。
     * 前端触发：admin/src/views/modules/discussshiyongfankui/list.vue 通过 $http.get('discussshiyongfankui/page') 触发。
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
     * 功能：查询使用反馈评论前台列表数据。
     * 使用端：前台使用反馈评论列表页，部分管理端通用列表也会复用。
     * 前端触发：front/src/pages/discussshiyongfankui/list.vue 通过 $http.get('discussshiyongfankui/list') 触发。
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
     * 功能：查询使用反馈评论不分页列表。
     * 使用端：前后台表单页的下拉、联动和重复校验场景。
     * 前端触发：表单页按 tableName 拼接 $http.get('discussshiyongfankui/lists') 触发。
     */
    @RequestMapping("/lists")
    public R list( DiscussshiyongfankuiEntity discussshiyongfankui){
       	EntityWrapper<DiscussshiyongfankuiEntity> ew = new EntityWrapper<DiscussshiyongfankuiEntity>();
      	ew.allEq(MPUtil.allEQMapPre( discussshiyongfankui, "discussshiyongfankui"));
        return R.ok().put("data", discussshiyongfankuiService.selectListView(ew));
    }
    /**
     * 功能：按条件查询单条使用反馈评论视图数据。
     * 使用端：前后台表单联动或详情回显辅助接口。
     * 前端触发：前端按条件通过 $http.get('discussshiyongfankui/query') 触发。
     */
    @RequestMapping("/query")
    public R query(DiscussshiyongfankuiEntity discussshiyongfankui){
        EntityWrapper< DiscussshiyongfankuiEntity> ew = new EntityWrapper< DiscussshiyongfankuiEntity>();
 		ew.allEq(MPUtil.allEQMapPre( discussshiyongfankui, "discussshiyongfankui"));
		DiscussshiyongfankuiView discussshiyongfankuiView =  discussshiyongfankuiService.selectView(ew);
		return R.ok("查询使用反馈评论成功").put("data", discussshiyongfankuiView);
    }
    /**
     * 功能：查询使用反馈评论管理端详情。
     * 使用端：管理端使用反馈评论列表页、编辑页。
     * 前端触发：管理端通过 $http.get('discussshiyongfankui/info/{id}') 触发。
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
     * 功能：查询使用反馈评论前台详情。
     * 使用端：前台使用反馈评论详情页或编辑回显页。
     * 前端触发：front/src/pages/discussshiyongfankui/detail.vue 或 add.vue 触发。
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
     * 功能：管理端新增使用反馈评论记录。
     * 使用端：管理端使用反馈评论新增表单。
     * 前端触发：管理端表单通过 $http.post('discussshiyongfankui/save') 触发。
     */
    @RequestMapping("/save")
    @SysLog("新增使用反馈评论")
    public R save(@RequestBody DiscussshiyongfankuiEntity discussshiyongfankui, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discussshiyongfankui);
        discussshiyongfankuiService.insert(discussshiyongfankui);
        return R.ok().put("data",discussshiyongfankui.getId());
    }
    /**
     * 功能：前台新增使用反馈评论记录。
     * 使用端：前台使用反馈评论新增表单或详情页操作。
     * 前端触发：前台表单通过 $http.post('discussshiyongfankui/add') 触发。
     */
    @SysLog("新增使用反馈评论")
    @RequestMapping("/add")
    public R add(@RequestBody DiscussshiyongfankuiEntity discussshiyongfankui, HttpServletRequest request){
        //ValidatorUtils.validateEntity(discussshiyongfankui);
        discussshiyongfankuiService.insert(discussshiyongfankui);
        return R.ok().put("data",discussshiyongfankui.getId());
    }
    /**
     * 功能：校验使用反馈评论账号是否存在。
     * 使用端：注册、找回或账号校验表单。
     * 前端触发：前端通过 $http.get('discussshiyongfankui/security') 触发。
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        DiscussshiyongfankuiEntity discussshiyongfankui = discussshiyongfankuiService.selectOne(new EntityWrapper<DiscussshiyongfankuiEntity>().eq("", username));
        return R.ok().put("data", discussshiyongfankui);
    }
    /**
     * 功能：修改使用反馈评论记录。
     * 使用端：管理端编辑页、前台个人中心或详情页操作。
     * 前端触发：前端表单提交时通过 $http.post('discussshiyongfankui/update') 触发。
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
     * 功能：删除使用反馈评论记录。
     * 使用端：管理端列表页或前台详情页/我的列表。
     * 前端触发：删除按钮通过 $http.post('discussshiyongfankui/delete') 触发。
     */
    @RequestMapping("/delete")
    @SysLog("删除使用反馈评论")
    public R delete(@RequestBody Long[] ids){
        discussshiyongfankuiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    /**
     * 功能：按点击量等条件返回使用反馈评论自动排序列表。
     * 使用端：前台推荐列表或首页推荐区域。
     * 前端触发：前端推荐组件通过 $http.get('discussshiyongfankui/autoSort') 触发。
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
