package com.controller;

import com.annotation.IgnoreAuth;
import com.utils.VerifyCodeUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码 模块后端接口。
 * 说明：供管理端、前台端对应页面通过 HTTP 请求调用。
 */
@RestController
public class CaptchaController {

    /**
     * 功能：生成登录验证码图片，并把验证码写入 Session。
     * 使用端：管理端/前台登录页。
     * 前端触发：登录页验证码图片访问 '/captcha' 或 '/captcha.jpg' 触发。
     */
    @IgnoreAuth
    @GetMapping(value = {"/captcha", "/captcha.jpg"}, produces = MediaType.IMAGE_JPEG_VALUE)
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        String code = VerifyCodeUtils.generateVerifyCode(4);
        request.getSession().setAttribute("captcha", code);
        VerifyCodeUtils.outputImage(120, 48, response.getOutputStream(), code);
    }
}
