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

import com.entity.NewsEntity;
import com.entity.view.NewsView;

import com.service.NewsService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 公告信息 模块后端接口。
 * 说明：供管理端、前台端对应页面通过 HTTP 请求调用。
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;
    /**
     * 功能：分页查询公告信息数据。
     * 使用端：管理端公告信息管理列表页。
     * 前端触发：admin/src/views/modules/news/list.vue 通过 $http.get('news/page') 触发。
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,NewsEntity news,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<NewsEntity> ew = new EntityWrapper<NewsEntity>();


        //查询结果
		PageUtils page = newsService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, news), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


    /**
     * 功能：查询公告信息前台列表数据。
     * 使用端：前台公告信息列表页，部分管理端通用列表也会复用。
     * 前端触发：front/src/pages/news/list.vue 通过 $http.get('news/list') 触发。
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,NewsEntity news,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<NewsEntity> ew = new EntityWrapper<NewsEntity>();

        //查询结果
		PageUtils page = newsService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, news), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




    /**
     * 功能：查询公告信息不分页列表。
     * 使用端：前后台表单页的下拉、联动和重复校验场景。
     * 前端触发：表单页按 tableName 拼接 $http.get('news/lists') 触发。
     */
    @RequestMapping("/lists")
    public R list( NewsEntity news){
       	EntityWrapper<NewsEntity> ew = new EntityWrapper<NewsEntity>();
      	ew.allEq(MPUtil.allEQMapPre( news, "news"));
        return R.ok().put("data", newsService.selectListView(ew));
    }

    /**
     * 功能：按条件查询单条公告信息视图数据。
     * 使用端：前后台表单联动或详情回显辅助接口。
     * 前端触发：前端按条件通过 $http.get('news/query') 触发。
     */
    @RequestMapping("/query")
    public R query(NewsEntity news){
        EntityWrapper< NewsEntity> ew = new EntityWrapper< NewsEntity>();
 		ew.allEq(MPUtil.allEQMapPre( news, "news"));
		NewsView newsView =  newsService.selectView(ew);
		return R.ok("查询公告信息成功").put("data", newsView);
    }

    /**
     * 功能：查询公告信息管理端详情。
     * 使用端：管理端公告信息列表页、编辑页。
     * 前端触发：管理端通过 $http.get('news/info/{id}') 触发。
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        NewsEntity news = newsService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(news,deSens);
        return R.ok().put("data", news);
    }

    /**
     * 功能：查询公告信息前台详情。
     * 使用端：前台公告信息详情页或编辑回显页。
     * 前端触发：front/src/pages/news/detail.vue 或 add.vue 触发。
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        NewsEntity news = newsService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(news,deSens);
        return R.ok().put("data", news);
    }




    /**
     * 功能：管理端新增公告信息记录。
     * 使用端：管理端公告信息新增表单。
     * 前端触发：管理端表单通过 $http.post('news/save') 触发。
     */
    @RequestMapping("/save")
    @SysLog("新增公告信息")
    public R save(@RequestBody NewsEntity news, HttpServletRequest request){
        //ValidatorUtils.validateEntity(news);
        newsService.insert(news);
        return R.ok().put("data",news.getId());
    }

    /**
     * 功能：前台新增公告信息记录。
     * 使用端：前台公告信息新增表单或详情页操作。
     * 前端触发：前台表单通过 $http.post('news/add') 触发。
     */
    @SysLog("新增公告信息")
    @RequestMapping("/add")
    public R add(@RequestBody NewsEntity news, HttpServletRequest request){
        //ValidatorUtils.validateEntity(news);
        newsService.insert(news);
        return R.ok().put("data",news.getId());
    }





    /**
     * 功能：修改公告信息记录。
     * 使用端：管理端编辑页、前台个人中心或详情页操作。
     * 前端触发：前端表单提交时通过 $http.post('news/update') 触发。
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改公告信息")
    public R update(@RequestBody NewsEntity news, HttpServletRequest request){
        //ValidatorUtils.validateEntity(news);
        //全部更新
        newsService.updateById(news);
        return R.ok();
    }





    /**
     * 功能：删除公告信息记录。
     * 使用端：管理端列表页或前台详情页/我的列表。
     * 前端触发：删除按钮通过 $http.post('news/delete') 触发。
     */
    @RequestMapping("/delete")
    @SysLog("删除公告信息")
    public R delete(@RequestBody Long[] ids){
        newsService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
