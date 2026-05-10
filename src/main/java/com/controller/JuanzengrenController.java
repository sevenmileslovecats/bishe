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
import com.service.TokenService;
import com.entity.TokenEntity;
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

import com.entity.JuanzengrenEntity;
import com.entity.view.JuanzengrenView;

import com.service.JuanzengrenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 捐赠人 模块后端接口。
 * 说明：供管理端、前台端对应页面通过 HTTP 请求调用。
 */
@RestController
@RequestMapping("/juanzengren")
public class JuanzengrenController {
    @Autowired
    private JuanzengrenService juanzengrenService;







	@Autowired
	private TokenService tokenService;

    /**
     * 功能：捐赠人登录认证，生成并返回访问 Token。
     * 使用端：管理端/前台登录页。
     * 前端触发：登录页提交账号密码时通过 $http.get('juanzengren/login') 触发。
     */
	@IgnoreAuth
    @RequestMapping(value = "/login")
	public R login(String username, String password, String captcha, HttpServletRequest request) {
		if(!VerifyCodeUtils.validateCaptcha(request, captcha)) {
			return R.error("\u9a8c\u8bc1\u7801\u9519\u8bef");
		}
		// 根据登录查询用户信息
        JuanzengrenEntity u = juanzengrenService.selectOne(new EntityWrapper<JuanzengrenEntity>().eq("zhanghao", username));
        // 判断用户锁定状态
        if(u!=null && u.getStatus().intValue()==1) {
            //返回已锁定提示
            return R.error("账号已锁定，请联系管理员。");
        }
        if(u==null || !u.getMima().equals(password)) {
            //账号或密码不正确提示
			return R.error("账号或密码不正确");
		}
        // 获取登录token
        String token = tokenService.generateToken(u.getId(), username,"juanzengren",  "捐赠人" );
        //返回token
		return R.ok().put("token", token);
	}



    /**
     * 功能：注册捐赠人账号。
     * 使用端：前台注册页或管理端注册入口。
     * 前端触发：注册页提交表单时通过 $http.post('juanzengren/register') 触发。
     */
    @IgnoreAuth
    @RequestMapping("/register")
    public R register(@RequestBody JuanzengrenEntity juanzengren){
    	//ValidatorUtils.validateEntity(juanzengren);
        //根据登录账号获取用户信息判断是否存在该用户，否则返回错误信息
    	JuanzengrenEntity u = juanzengrenService.selectOne(new EntityWrapper<JuanzengrenEntity>().eq("zhanghao", juanzengren.getZhanghao()));
		if(u!=null) {
			return R.error("注册用户已存在");
		}
		Long uId = new Date().getTime();
		juanzengren.setId(uId);
        //保存用户
        juanzengrenService.insert(juanzengren);
        return R.ok();
    }



    /**
     * 功能：退出当前捐赠人登录会话。
     * 使用端：管理端/前台顶部退出登录按钮。
     * 前端触发：退出按钮通过 $http.get('juanzengren/logout') 触发。
     */
    @RequestMapping("/logout")
	public R logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return R.ok("退出成功");
	}

    /**
     * 功能：获取当前登录捐赠人的 Session 用户信息。
     * 使用端：个人中心、表单自动带入当前用户信息。
     * 前端触发：页面初始化时通过 $http.get('juanzengren/session') 触发。
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request){
    	Long id = (Long)request.getSession().getAttribute("userId");
        JuanzengrenEntity u = juanzengrenService.selectById(id);
        return R.ok().put("data", u);
    }

    /**
     * 功能：重置捐赠人账号密码。
     * 使用端：管理端账号维护或找回密码流程。
     * 前端触发：前端通过 $http.get('juanzengren/resetPass') 触发。
     */
    @IgnoreAuth
    @RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request){
    	//根据登录账号判断是否存在用户信息，否则返回错误信息
        JuanzengrenEntity u = juanzengrenService.selectOne(new EntityWrapper<JuanzengrenEntity>().eq("zhanghao", username));
    	if(u==null) {
    		return R.error("账号不存在");
    	}
        //重置密码为123456
        u.setMima("123456");
        juanzengrenService.updateById(u);
        return R.ok("密码已重置为：123456");
    }

    /**
     * 功能：查询捐赠人账号列表。
     * 使用端：后台账号选择、关联账号下拉场景。
     * 前端触发：账号选择组件通过 $http.get('juanzengren/accountList') 触发。
     */
    @RequestMapping("/accountList")
    public R getAccountList(@RequestParam Map<String, Object> params,JuanzengrenEntity juanzengren){
        EntityWrapper<JuanzengrenEntity> ew = new EntityWrapper<JuanzengrenEntity>();
        Wrapper<JuanzengrenEntity> wrapper =MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, juanzengren), params), params);
        List<Map> list = juanzengrenService.selectList(wrapper).stream().map(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", v.getId());
            map.put("account", v.getZhanghao());
            return map;
        }).collect(Collectors.toList());
        return R.ok().put("data", list);
    }






    /**
     * 功能：分页查询捐赠人数据。
     * 使用端：管理端捐赠人管理列表页。
     * 前端触发：admin/src/views/modules/juanzengren/list.vue 通过 $http.get('juanzengren/page') 触发。
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,JuanzengrenEntity juanzengren,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<JuanzengrenEntity> ew = new EntityWrapper<JuanzengrenEntity>();


        //查询结果
		PageUtils page = juanzengrenService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, juanzengren), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


    /**
     * 功能：查询捐赠人前台列表数据。
     * 使用端：前台捐赠人列表页，部分管理端通用列表也会复用。
     * 前端触发：front/src/pages/juanzengren/list.vue 通过 $http.get('juanzengren/list') 触发。
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,JuanzengrenEntity juanzengren,
                @RequestParam(required = false) Double statusstart,
                @RequestParam(required = false) Double statusend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<JuanzengrenEntity> ew = new EntityWrapper<JuanzengrenEntity>();
        if(statusstart!=null) ew.ge("status", statusstart);
        if(statusend!=null) ew.le("status", statusend);

        //查询结果
		PageUtils page = juanzengrenService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, juanzengren), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




    /**
     * 功能：查询捐赠人不分页列表。
     * 使用端：前后台表单页的下拉、联动和重复校验场景。
     * 前端触发：表单页按 tableName 拼接 $http.get('juanzengren/lists') 触发。
     */
    @RequestMapping("/lists")
    public R list( JuanzengrenEntity juanzengren){
       	EntityWrapper<JuanzengrenEntity> ew = new EntityWrapper<JuanzengrenEntity>();
      	ew.allEq(MPUtil.allEQMapPre( juanzengren, "juanzengren"));
        return R.ok().put("data", juanzengrenService.selectListView(ew));
    }

    /**
     * 功能：按条件查询单条捐赠人视图数据。
     * 使用端：前后台表单联动或详情回显辅助接口。
     * 前端触发：前端按条件通过 $http.get('juanzengren/query') 触发。
     */
    @RequestMapping("/query")
    public R query(JuanzengrenEntity juanzengren){
        EntityWrapper< JuanzengrenEntity> ew = new EntityWrapper< JuanzengrenEntity>();
 		ew.allEq(MPUtil.allEQMapPre( juanzengren, "juanzengren"));
		JuanzengrenView juanzengrenView =  juanzengrenService.selectView(ew);
		return R.ok("查询捐赠人成功").put("data", juanzengrenView);
    }

    /**
     * 功能：查询捐赠人管理端详情。
     * 使用端：管理端捐赠人列表页、编辑页。
     * 前端触发：管理端通过 $http.get('juanzengren/info/{id}') 触发。
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        JuanzengrenEntity juanzengren = juanzengrenService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(juanzengren,deSens);
        return R.ok().put("data", juanzengren);
    }

    /**
     * 功能：查询捐赠人前台详情。
     * 使用端：前台捐赠人详情页或编辑回显页。
     * 前端触发：front/src/pages/juanzengren/detail.vue 或 add.vue 触发。
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        JuanzengrenEntity juanzengren = juanzengrenService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(juanzengren,deSens);
        return R.ok().put("data", juanzengren);
    }




    /**
     * 功能：管理端新增捐赠人记录。
     * 使用端：管理端捐赠人新增表单。
     * 前端触发：管理端表单通过 $http.post('juanzengren/save') 触发。
     */
    @RequestMapping("/save")
    @SysLog("新增捐赠人")
    public R save(@RequestBody JuanzengrenEntity juanzengren, HttpServletRequest request){
        //ValidatorUtils.validateEntity(juanzengren);
        //验证账号唯一性，否则返回错误信息
        JuanzengrenEntity u = juanzengrenService.selectOne(new EntityWrapper<JuanzengrenEntity>().eq("zhanghao", juanzengren.getZhanghao()));
        if(u!=null) {
            return R.error("用户已存在");
        }
    	juanzengren.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
		juanzengren.setId(new Date().getTime());
        juanzengrenService.insert(juanzengren);
        return R.ok().put("data",juanzengren.getId());
    }

    /**
     * 功能：前台新增捐赠人记录。
     * 使用端：前台捐赠人新增表单或详情页操作。
     * 前端触发：前台表单通过 $http.post('juanzengren/add') 触发。
     */
    @SysLog("新增捐赠人")
    @RequestMapping("/add")
    public R add(@RequestBody JuanzengrenEntity juanzengren, HttpServletRequest request){
        //ValidatorUtils.validateEntity(juanzengren);
        //验证账号唯一性，否则返回错误信息
        JuanzengrenEntity u = juanzengrenService.selectOne(new EntityWrapper<JuanzengrenEntity>().eq("zhanghao", juanzengren.getZhanghao()));
        if(u!=null) {
            return R.error("用户已存在");
        }
    	juanzengren.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
		juanzengren.setId(new Date().getTime());
        juanzengrenService.insert(juanzengren);
        return R.ok().put("data",juanzengren.getId());
    }





    /**
     * 功能：修改捐赠人记录。
     * 使用端：管理端编辑页、前台个人中心或详情页操作。
     * 前端触发：前端表单提交时通过 $http.post('juanzengren/update') 触发。
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改捐赠人")
    public R update(@RequestBody JuanzengrenEntity juanzengren, HttpServletRequest request){
        //ValidatorUtils.validateEntity(juanzengren);
        //全部更新
        juanzengrenService.updateById(juanzengren);
        if(null!=juanzengren.getZhanghao())
        {
            // 修改token
            TokenEntity tokenEntity = new TokenEntity();
            tokenEntity.setUsername(juanzengren.getZhanghao());
            tokenService.update(tokenEntity, new EntityWrapper<TokenEntity>().eq("userid", juanzengren.getId()));
        }
        return R.ok();
    }





    /**
     * 功能：删除捐赠人记录。
     * 使用端：管理端列表页或前台详情页/我的列表。
     * 前端触发：删除按钮通过 $http.post('juanzengren/delete') 触发。
     */
    @RequestMapping("/delete")
    @SysLog("删除捐赠人")
    public R delete(@RequestBody Long[] ids){
        juanzengrenService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
