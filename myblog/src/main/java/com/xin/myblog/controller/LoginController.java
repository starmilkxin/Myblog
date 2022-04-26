package com.xin.myblog.controller;

import com.alibaba.fastjson.JSON;
import com.xin.myblog.pojo.User;
import com.xin.myblog.service.UserService;
import com.xin.myblog.util.BlogUtil;
import com.xin.myblog.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(method = RequestMethod.GET)
    public String loginPage() {
        return "admin/login";
    }

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String loginSuccess() {
        return "admin/index";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String username, @RequestParam String password, @RequestParam("srcPath") String srcPath, HttpServletResponse response, RedirectAttributes attributes) {
        password = BlogUtil.md5(password);
        User user = userService.findUserByUsernameAndPassword(username, password);
        if (user != null) {
            String token = jwtUtil.createToken(JSON.toJSONString(user));
            Cookie cookie = new Cookie("Authorization", token);
            cookie.setMaxAge(60 * 60 * 6);
            response.addCookie(cookie);
            return "redirect:" + ((srcPath == null || srcPath == "" || srcPath == "/admin") ? "/admin/index" : srcPath);
        }else {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin";
        }
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletResponse response, @CookieValue("Authorization") String token) {
        if (token != null) {
            Cookie cookie = new Cookie("Authorization", null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return "redirect:/admin";
    }
}
