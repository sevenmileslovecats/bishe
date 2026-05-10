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

import com.entity.JieshoujigouEntity;
import com.entity.view.JieshoujigouView;

import com.service.JieshoujigouService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 接收机构 模块后端接口。
 * 说明：供管理端、前台端对应页面通过 HTTP 请求调用。
 */
@RestController
@RequestMapping("/jieshoujigou")
public class JieshoujigouController {
    @Autowired
    private JieshoujigouService jieshoujigouService;







	@Autowired
	private TokenService tokenService;

    /**
     * 功能：接收机构登录认证，生成并返回访问 Token。
     * 使用端：管理端/前台登录页。
     * 前端触发：登录页提交账号密码时通过 $http.get('jieshoujigou/login') 触发。
     */
	@IgnoreAuth
    @RequestMapping(value = "/login")
	public R login(String username, String password, String captcha, HttpServletRequest request) {
		if(!VerifyCodeUtils.validateCaptcha(request, captcha)) {
			return R.error("\u9a8c\u8bc1\u7801\u9519\u8bef");
		}
		// 根据登录查询用户信息
        JieshoujigouEntity u = jieshoujigouService.selectOne(new EntityWrapper<JieshoujigouEntity>().eq("jigouzhanghao", username));
        // 判断用户锁定状态
        if(u!=null && u.getStatus().intValue()==1) {
            //返回已锁定提示
            return R.error("账号已锁定，请联系管理员。");
        }
        if(u==null || !u.getMima().equals(password)) {
            //账号或密码不正确提示
			return R.error("账号或密码不正确");
		}
        // 如果用户是待审核状态，返回请联系管理员审核提示
        if(!"是".equals(u.getSfsh())) return R.error("账号已锁定，请联系管理员审核。");
        // 获取登录token
        String token = tokenService.generateToken(u.getId(), username,"jieshoujigou",  "接收机构" );
        //返回token
		return R.ok().put("token", token);
	}



    /**
     * 功能：注册接收机构账号。
     * 使用端：前台注册页或管理端注册入口。
     * 前端触发：注册页提交表单时通过 $http.post('jieshoujigou/register') 触发。
     */
    @IgnoreAuth
    @RequestMapping("/register")
    public R register(@RequestBody JieshoujigouEntity jieshoujigou){
    	//ValidatorUtils.validateEntity(jieshoujigou);
        //根据登录账号获取用户信息判断是否存在该用户，否则返回错误信息
    	JieshoujigouEntity u = jieshoujigouService.selectOne(new EntityWrapper<JieshoujigouEntity>().eq("jigouzhanghao", jieshoujigou.getJigouzhanghao()));
		if(u!=null) {
			return R.error("注册用户已存在");
		}
		Long uId = new Date().getTime();
		jieshoujigou.setId(uId);
        //保存用户
        jieshoujigouService.insert(jieshoujigou);
        return R.ok();
    }



    /**
     * 功能：退出当前接收机构登录会话。
     * 使用端：管理端/前台顶部退出登录按钮。
     * 前端触发：退出按钮通过 $http.get('jieshoujigou/logout') 触发。
     */
    @RequestMapping("/logout")
	public R logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return R.ok("退出成功");
	}

    /**
     * 功能：获取当前登录接收机构的 Session 用户信息。
     * 使用端：个人中心、表单自动带入当前用户信息。
     * 前端触发：页面初始化时通过 $http.get('jieshoujigou/session') 触发。
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request){
    	Long id = (Long)request.getSession().getAttribute("userId");
        JieshoujigouEntity u = jieshoujigouService.selectById(id);
        return R.ok().put("data", u);
    }

    /**
     * 功能：重置接收机构账号密码。
     * 使用端：管理端账号维护或找回密码流程。
     * 前端触发：前端通过 $http.get('jieshoujigou/resetPass') 触发。
     */
    @IgnoreAuth
    @RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request){
    	//根据登录账号判断是否存在用户信息，否则返回错误信息
        JieshoujigouEntity u = jieshoujigouService.selectOne(new EntityWrapper<JieshoujigouEntity>().eq("jigouzhanghao", username));
    	if(u==null) {
    		return R.error("账号不存在");
    	}
        //重置密码为123456
        u.setMima("123456");
        jieshoujigouService.updateById(u);
        return R.ok("密码已重置为：123456");
    }

    /**
     * 功能：查询接收机构账号列表。
     * 使用端：后台账号选择、关联账号下拉场景。
     * 前端触发：账号选择组件通过 $http.get('jieshoujigou/accountList') 触发。
     */
    @RequestMapping("/accountList")
    public R getAccountList(@RequestParam Map<String, Object> params,JieshoujigouEntity jieshoujigou){
        EntityWrapper<JieshoujigouEntity> ew = new EntityWrapper<JieshoujigouEntity>();
        Wrapper<JieshoujigouEntity> wrapper =MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jieshoujigou), params), params);
        List<Map> list = jieshoujigouService.selectList(wrapper).stream().map(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", v.getId());
            map.put("account", v.getJigouzhanghao());
            return map;
        }).collect(Collectors.toList());
        return R.ok().put("data", list);
    }






    /**
     * 功能：分页查询接收机构数据。
     * 使用端：管理端接收机构管理列表页。
     * 前端触发：admin/src/views/modules/jieshoujigou/list.vue 通过 $http.get('jieshoujigou/page') 触发。
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,JieshoujigouEntity jieshoujigou,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<JieshoujigouEntity> ew = new EntityWrapper<JieshoujigouEntity>();


        //查询结果
		PageUtils page = jieshoujigouService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jieshoujigou), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


    /**
     * 功能：查询接收机构前台列表数据。
     * 使用端：前台接收机构列表页，部分管理端通用列表也会复用。
     * 前端触发：front/src/pages/jieshoujigou/list.vue 通过 $http.get('jieshoujigou/list') 触发。
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,JieshoujigouEntity jieshoujigou,
                @RequestParam(required = false) Double statusstart,
                @RequestParam(required = false) Double statusend,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<JieshoujigouEntity> ew = new EntityWrapper<JieshoujigouEntity>();
        if(statusstart!=null) ew.ge("status", statusstart);
        if(statusend!=null) ew.le("status", statusend);

        //查询结果
		PageUtils page = jieshoujigouService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jieshoujigou), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




    /**
     * 功能：查询接收机构不分页列表。
     * 使用端：前后台表单页的下拉、联动和重复校验场景。
     * 前端触发：表单页按 tableName 拼接 $http.get('jieshoujigou/lists') 触发。
     */
    @RequestMapping("/lists")
    public R list( JieshoujigouEntity jieshoujigou){
       	EntityWrapper<JieshoujigouEntity> ew = new EntityWrapper<JieshoujigouEntity>();
      	ew.allEq(MPUtil.allEQMapPre( jieshoujigou, "jieshoujigou"));
        return R.ok().put("data", jieshoujigouService.selectListView(ew));
    }

    /**
     * 功能：按条件查询单条接收机构视图数据。
     * 使用端：前后台表单联动或详情回显辅助接口。
     * 前端触发：前端按条件通过 $http.get('jieshoujigou/query') 触发。
     */
    @RequestMapping("/query")
    public R query(JieshoujigouEntity jieshoujigou){
        EntityWrapper< JieshoujigouEntity> ew = new EntityWrapper< JieshoujigouEntity>();
 		ew.allEq(MPUtil.allEQMapPre( jieshoujigou, "jieshoujigou"));
		JieshoujigouView jieshoujigouView =  jieshoujigouService.selectView(ew);
		return R.ok("查询接收机构成功").put("data", jieshoujigouView);
    }

    /**
     * 功能：查询接收机构管理端详情。
     * 使用端：管理端接收机构列表页、编辑页。
     * 前端触发：管理端通过 $http.get('jieshoujigou/info/{id}') 触发。
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        JieshoujigouEntity jieshoujigou = jieshoujigouService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(jieshoujigou,deSens);
        return R.ok().put("data", jieshoujigou);
    }

    /**
     * 功能：查询接收机构前台详情。
     * 使用端：前台接收机构详情页或编辑回显页。
     * 前端触发：front/src/pages/jieshoujigou/detail.vue 或 add.vue 触发。
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        JieshoujigouEntity jieshoujigou = jieshoujigouService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(jieshoujigou,deSens);
        return R.ok().put("data", jieshoujigou);
    }




    /**
     * 功能：管理端新增接收机构记录。
     * 使用端：管理端接收机构新增表单。
     * 前端触发：管理端表单通过 $http.post('jieshoujigou/save') 触发。
     */
    @RequestMapping("/save")
    @SysLog("新增接收机构")
    public R save(@RequestBody JieshoujigouEntity jieshoujigou, HttpServletRequest request){
        //ValidatorUtils.validateEntity(jieshoujigou);
        //验证账号唯一性，否则返回错误信息
        JieshoujigouEntity u = jieshoujigouService.selectOne(new EntityWrapper<JieshoujigouEntity>().eq("jigouzhanghao", jieshoujigou.getJigouzhanghao()));
        if(u!=null) {
            return R.error("用户已存在");
        }
    	jieshoujigou.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
		jieshoujigou.setId(new Date().getTime());
        jieshoujigouService.insert(jieshoujigou);
        return R.ok().put("data",jieshoujigou.getId());
    }

    /**
     * 功能：前台新增接收机构记录。
     * 使用端：前台接收机构新增表单或详情页操作。
     * 前端触发：前台表单通过 $http.post('jieshoujigou/add') 触发。
     */
    @SysLog("新增接收机构")
    @RequestMapping("/add")
    public R add(@RequestBody JieshoujigouEntity jieshoujigou, HttpServletRequest request){
        //ValidatorUtils.validateEntity(jieshoujigou);
        //验证账号唯一性，否则返回错误信息
        JieshoujigouEntity u = jieshoujigouService.selectOne(new EntityWrapper<JieshoujigouEntity>().eq("jigouzhanghao", jieshoujigou.getJigouzhanghao()));
        if(u!=null) {
            return R.error("用户已存在");
        }
    	jieshoujigou.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
		jieshoujigou.setId(new Date().getTime());
        jieshoujigouService.insert(jieshoujigou);
        return R.ok().put("data",jieshoujigou.getId());
    }





    /**
     * 功能：修改接收机构记录。
     * 使用端：管理端编辑页、前台个人中心或详情页操作。
     * 前端触发：前端表单提交时通过 $http.post('jieshoujigou/update') 触发。
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改接收机构")
    public R update(@RequestBody JieshoujigouEntity jieshoujigou, HttpServletRequest request){
        //ValidatorUtils.validateEntity(jieshoujigou);
        //全部更新
        jieshoujigouService.updateById(jieshoujigou);
        if(null!=jieshoujigou.getJigouzhanghao())
        {
            // 修改token
            TokenEntity tokenEntity = new TokenEntity();
            tokenEntity.setUsername(jieshoujigou.getJigouzhanghao());
            tokenService.update(tokenEntity, new EntityWrapper<TokenEntity>().eq("userid", jieshoujigou.getId()));
        }
        return R.ok();
    }

    /**
     * 功能：批量审核接收机构记录。
     * 使用端：管理端接收机构审核按钮。
     * 前端触发：列表页审核操作通过 $http.post('jieshoujigou/shBatch') 触发。
     */
    @RequestMapping("/shBatch")
    @Transactional
    @SysLog("审核接收机构")
    public R update(@RequestBody Long[] ids, @RequestParam String sfsh, @RequestParam String shhf){
        List<JieshoujigouEntity> list = new ArrayList<JieshoujigouEntity>();
        for(Long id : ids) {
            JieshoujigouEntity jieshoujigou = jieshoujigouService.selectById(id);
            jieshoujigou.setSfsh(sfsh);
            jieshoujigou.setShhf(shhf);
            list.add(jieshoujigou);
        }
        jieshoujigouService.updateBatchById(list);
        return R.ok();
    }




    /**
     * 功能：删除接收机构记录。
     * 使用端：管理端列表页或前台详情页/我的列表。
     * 前端触发：删除按钮通过 $http.post('jieshoujigou/delete') 触发。
     */
    @RequestMapping("/delete")
    @SysLog("删除接收机构")
    public R delete(@RequestBody Long[] ids){
        jieshoujigouService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }










}
