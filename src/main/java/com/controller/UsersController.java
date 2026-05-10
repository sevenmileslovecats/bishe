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

import com.entity.UsersEntity;
import com.entity.view.UsersView;

import com.service.UsersService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 管理员 模块后端接口。
 * 说明：供管理端、前台端对应页面通过 HTTP 请求调用。
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;
	@Autowired
	private TokenService tokenService;

    /**
     * 功能：管理员登录认证，生成并返回访问 Token。
     * 使用端：管理端/前台登录页。
     * 前端触发：登录页提交账号密码时通过 $http.get('users/login') 触发。
     */
	@IgnoreAuth
    @RequestMapping(value = "/login")
	public R login(String username, String password, String captcha, HttpServletRequest request) {
		if(!VerifyCodeUtils.validateCaptcha(request, captcha)) {
			return R.error("\u9a8c\u8bc1\u7801\u9519\u8bef");
		}
		// 根据登录查询用户信息
        UsersEntity u = usersService.selectOne(new EntityWrapper<UsersEntity>().eq("username", username));
        if(u==null || !u.getPassword().equals(password)) {
            //账号或密码不正确提示
			return R.error("账号或密码不正确");
		}
        // 获取登录token
        String token = tokenService.generateToken(u.getId(), username,"users",  "管理员" );
        //返回token
		return R.ok().put("token", token);
	}


    /**
     * 功能：注册管理员账号。
     * 使用端：前台注册页或管理端注册入口。
     * 前端触发：注册页提交表单时通过 $http.post('users/register') 触发。
     */
    @IgnoreAuth
    @RequestMapping("/register")
    public R register(@RequestBody UsersEntity users){
    	//ValidatorUtils.validateEntity(users);
        //根据登录账号获取用户信息判断是否存在该用户，否则返回错误信息
    	UsersEntity u = usersService.selectOne(new EntityWrapper<UsersEntity>().eq("username", users.getUsername()));
		if(u!=null) {
			return R.error("注册用户已存在");
		}
		Long uId = new Date().getTime();
		users.setId(uId);
        users.setPassword(users.getPassword());
        //保存用户
        usersService.insert(users);
        return R.ok();
    }


    /**
     * 功能：注销管理员账号。
     * 使用端：管理端管理员账号维护。
     * 前端触发：账号注销操作通过 $http.post('users/logoff') 触发。
     */
    @RequestMapping("/logoff")
    public R logOff(HttpServletRequest request){
        Long id = (Long)request.getSession().getAttribute("userId");
        UsersEntity u = usersService.selectById(id);
        if(u==null) {
            return R.error("账号不存在");
        }
        usersService.updateById(u);
        return R.ok("注销成功");
    }

    /**
     * 功能：退出当前管理员登录会话。
     * 使用端：管理端/前台顶部退出登录按钮。
     * 前端触发：退出按钮通过 $http.get('users/logout') 触发。
     */
    @RequestMapping("/logout")
	public R logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return R.ok("退出成功");
	}

    /**
     * 功能：获取当前登录管理员的 Session 用户信息。
     * 使用端：个人中心、表单自动带入当前用户信息。
     * 前端触发：页面初始化时通过 $http.get('users/session') 触发。
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request){
    	Long id = (Long)request.getSession().getAttribute("userId");
        UsersEntity u = usersService.selectById(id);
        return R.ok().put("data", u);
    }

    /**
     * 功能：重置管理员账号密码。
     * 使用端：管理端账号维护或找回密码流程。
     * 前端触发：前端通过 $http.get('users/resetPass') 触发。
     */
    @IgnoreAuth
    @RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request){
    	//根据登录账号判断是否存在用户信息，否则返回错误信息
        UsersEntity u = usersService.selectOne(new EntityWrapper<UsersEntity>().eq("username", username));
    	if(u==null) {
    		return R.error("账号不存在");
    	}
        u.setPassword("123456");
        usersService.updateById(u);
        return R.ok("密码已重置为：123456");
    }

    /**
     * 功能：查询管理员账号列表。
     * 使用端：后台账号选择、关联账号下拉场景。
     * 前端触发：账号选择组件通过 $http.get('users/accountList') 触发。
     */
    @RequestMapping("/accountList")
    public R getAccountList(@RequestParam Map<String, Object> params,UsersEntity users){
        EntityWrapper<UsersEntity> ew = new EntityWrapper<UsersEntity>();
        Wrapper<UsersEntity> wrapper =MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, users), params), params);
        List<Map> list = usersService.selectList(wrapper).stream().map(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", v.getId());
            map.put("account", v.getUsername());
            return map;
        }).collect(Collectors.toList());
        return R.ok().put("data", list);
    }



    /**
     * 功能：分页查询管理员数据。
     * 使用端：管理端管理员管理列表页。
     * 前端触发：admin/src/views/modules/users/list.vue 通过 $http.get('users/page') 触发。
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,UsersEntity users,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<UsersEntity> ew = new EntityWrapper<UsersEntity>();


        //查询结果
		PageUtils page = usersService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, users), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }


    /**
     * 功能：查询管理员前台列表数据。
     * 使用端：前台管理员列表页，部分管理端通用列表也会复用。
     * 前端触发：front/src/pages/users/list.vue 通过 $http.get('users/list') 触发。
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,UsersEntity users,
		HttpServletRequest request){
        //设置查询条件
        EntityWrapper<UsersEntity> ew = new EntityWrapper<UsersEntity>();

        //查询结果
		PageUtils page = usersService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, users), params), params));
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }




    /**
     * 功能：查询管理员不分页列表。
     * 使用端：前后台表单页的下拉、联动和重复校验场景。
     * 前端触发：表单页按 tableName 拼接 $http.get('users/lists') 触发。
     */
    @RequestMapping("/lists")
    public R list( UsersEntity users){
       	EntityWrapper<UsersEntity> ew = new EntityWrapper<UsersEntity>();
      	ew.allEq(MPUtil.allEQMapPre( users, "users"));
        return R.ok().put("data", usersService.selectListView(ew));
    }

    /**
     * 功能：按条件查询单条管理员视图数据。
     * 使用端：前后台表单联动或详情回显辅助接口。
     * 前端触发：前端按条件通过 $http.get('users/query') 触发。
     */
    @RequestMapping("/query")
    public R query(UsersEntity users){
        EntityWrapper< UsersEntity> ew = new EntityWrapper< UsersEntity>();
 		ew.allEq(MPUtil.allEQMapPre( users, "users"));
		UsersView usersView =  usersService.selectView(ew);
		return R.ok("查询管理员成功").put("data", usersView);
    }

    /**
     * 功能：查询管理员管理端详情。
     * 使用端：管理端管理员列表页、编辑页。
     * 前端触发：管理端通过 $http.get('users/info/{id}') 触发。
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        UsersEntity users = usersService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(users,deSens);
        return R.ok().put("data", users);
    }

    /**
     * 功能：查询管理员前台详情。
     * 使用端：前台管理员详情页或编辑回显页。
     * 前端触发：front/src/pages/users/detail.vue 或 add.vue 触发。
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        UsersEntity users = usersService.selectById(id);
        Map<String, String> deSens = new HashMap<>();
        //给需要脱敏的字段脱敏
        DeSensUtil.desensitize(users,deSens);
        return R.ok().put("data", users);
    }




    /**
     * 功能：管理端新增管理员记录。
     * 使用端：管理端管理员新增表单。
     * 前端触发：管理端表单通过 $http.post('users/save') 触发。
     */
    @RequestMapping("/save")
    @SysLog("新增管理员")
    public R save(@RequestBody UsersEntity users, HttpServletRequest request){
        //ValidatorUtils.validateEntity(users);
        //验证账号唯一性，否则返回错误信息
        UsersEntity u = usersService.selectOne(new EntityWrapper<UsersEntity>().eq("username", users.getUsername()));
        if(u!=null) {
            return R.error("用户已存在");
        }
		users.setId(new Date().getTime());
        usersService.insert(users);
        return R.ok().put("data",users.getId());
    }

    /**
     * 功能：前台新增管理员记录。
     * 使用端：前台管理员新增表单或详情页操作。
     * 前端触发：前台表单通过 $http.post('users/add') 触发。
     */
    @SysLog("新增管理员")
    @RequestMapping("/add")
    public R add(@RequestBody UsersEntity users, HttpServletRequest request){
        //ValidatorUtils.validateEntity(users);
        //验证账号唯一性，否则返回错误信息
        UsersEntity u = usersService.selectOne(new EntityWrapper<UsersEntity>().eq("username", users.getUsername()));
        if(u!=null) {
            return R.error("用户已存在");
        }
		users.setId(new Date().getTime());
        usersService.insert(users);
        return R.ok().put("data",users.getId());
    }



    /**
     * 功能：校验管理员账号是否存在。
     * 使用端：注册、找回或账号校验表单。
     * 前端触发：前端通过 $http.get('users/security') 触发。
     */
    @RequestMapping("/security")
    @IgnoreAuth
    public R security(@RequestParam String username){
        UsersEntity users = usersService.selectOne(new EntityWrapper<UsersEntity>().eq("username", username));
        return R.ok().put("data", users);
    }


    /**
     * 功能：修改管理员记录。
     * 使用端：管理端编辑页、前台个人中心或详情页操作。
     * 前端触发：前端表单提交时通过 $http.post('users/update') 触发。
     */
    @RequestMapping("/update")
    @Transactional
    @IgnoreAuth
    public R update(@RequestBody UsersEntity users, HttpServletRequest request){
        //ValidatorUtils.validateEntity(users);
        //全部更新
        usersService.updateById(users);
        if(null!=users.getUsername())
        {
            // 修改token
            TokenEntity tokenEntity = new TokenEntity();
            tokenEntity.setUsername(users.getUsername());
            tokenService.update(tokenEntity, new EntityWrapper<TokenEntity>().eq("userid", users.getId()));
        }
        return R.ok();
    }





    /**
     * 功能：删除管理员记录。
     * 使用端：管理端列表页或前台详情页/我的列表。
     * 前端触发：删除按钮通过 $http.post('users/delete') 触发。
     */
    @RequestMapping("/delete")
    @SysLog("删除管理员")
    public R delete(@RequestBody Long[] ids){
        usersService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 功能：按点击量等条件返回管理员自动排序列表。
     * 使用端：前台推荐列表或首页推荐区域。
     * 前端触发：前端推荐组件通过 $http.get('users/autoSort') 触发。
     */
    @IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,UsersEntity users, HttpServletRequest request,String pre) {
        EntityWrapper<UsersEntity> ew = new EntityWrapper<UsersEntity>();
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

        PageUtils page = usersService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, users), params), params));
        return R.ok().put("data", page);
    }
}
