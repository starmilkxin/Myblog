package com.xin.myblog.controller;

import com.alibaba.fastjson.JSON;
import com.xin.myblog.pojo.Result;
import com.xin.myblog.pojo.User;
import com.xin.myblog.service.UserService;
import com.xin.myblog.util.BlogUtil;
import com.xin.myblog.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    /**
     * 前端发送用户名和密码给后端，后端验证并返回token
     * @param body 用户名与密码
     * @return token或失败信息
     */
    @ResponseBody
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestBody String body) {
        Map<String, String> data = JSON.parseObject(body, Map.class);
        String password = data.get("password");
        String username = data.get("username");
        password = BlogUtil.md5(password);
        User user = userService.findUserByUsernameAndPassword(username, password);
        if (user != null) {
            user.setUsername(null);
            user.setPassword(null);
            String token = jwtUtil.createToken(JSON.toJSONString(user));
            return JSON.toJSONString(Result.success().data("token", token));
        }else {
            return JSON.toJSONString(Result.error().setMessage("用户名和密码错误").setCode(Result.ERROR_LOGIN));
        }
    }

    /**
     * 请求后端，获取user信息（如果登录的话）
     * @param request
     * @return user信息，未登录则user是null
     */
    @ResponseBody
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public String user(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        User user = null;
        if (token != null && token.length() > 0) {
            user = jwtUtil.getUser(token);
        }
        return JSON.toJSONString(Result.success().data("user", user));
    }

    //logout的实现直接在前端js中实现
//    @RequestMapping(path = "/logout", method = RequestMethod.GET)
//    public String logout(HttpServletResponse response, @CookieValue("Authorization") String token) {
//        if (token != null) {
//            Cookie cookie = new Cookie("Authorization", null);
//            cookie.setMaxAge(0);
//            response.addCookie(cookie);
//        }
//        return "redirect:/admin";
//    }
}
