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
 * 捐赠人
 * 后端接口
 * @author 
 * @email 
 * @date 2026-04-27 08:55:00
 */
@RestController
@RequestMapping("/juanzengren")
public class JuanzengrenController {
    @Autowired
    private JuanzengrenService juanzengrenService;







	@Autowired
	private TokenService tokenService;

	/**
	 * 登录
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
     * 注册
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
	 * 退出
	 */
	@RequestMapping("/logout")
	public R logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return R.ok("退出成功");
	}

	/**
     * 获取用户的session用户信息
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request){
    	Long id = (Long)request.getSession().getAttribute("userId");
        JuanzengrenEntity u = juanzengrenService.selectById(id);
        return R.ok().put("data", u);
    }

    /**
     * 密码重置
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
     * 获取账号列表
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
     * 后台列表
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
     * 前台列表
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
     * 列表
     */
    @RequestMapping("/lists")
    public R list( JuanzengrenEntity juanzengren){
       	EntityWrapper<JuanzengrenEntity> ew = new EntityWrapper<JuanzengrenEntity>();
      	ew.allEq(MPUtil.allEQMapPre( juanzengren, "juanzengren"));
        return R.ok().put("data", juanzengrenService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(JuanzengrenEntity juanzengren){
        EntityWrapper< JuanzengrenEntity> ew = new EntityWrapper< JuanzengrenEntity>();
 		ew.allEq(MPUtil.allEQMapPre( juanzengren, "juanzengren"));
		JuanzengrenView juanzengrenView =  juanzengrenService.selectView(ew);
		return R.ok("查询捐赠人成功").put("data", juanzengrenView);
    }

    /**
     * 后台详情
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
     * 前台详情
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
     * 后台保存
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
     * 前台保存
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
     * 修改
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
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除捐赠人")
    public R delete(@RequestBody Long[] ids){
        juanzengrenService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
