package com.controller;

import com.annotation.IgnoreAuth;
import com.utils.VerifyCodeUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class CaptchaController {

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
