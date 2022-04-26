package com.xin.myblog.interceptor;

import com.xin.myblog.annotation.LoginRequired;
import com.xin.myblog.pojo.User;
import com.xin.myblog.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            LoginRequired loginRequired =  method.getAnnotation(LoginRequired.class);
            if (loginRequired != null) {
                //从请求头中获取token
                String token = request.getHeader("Authorization");
                if (token != null) {
                    User user = null;
                    if (jwtUtil.checkToken(token)
                            && (user = jwtUtil.getUser(token)) != null
                            && user.getType() == 1) {
                        request.setAttribute("user", user);
                        return true;
                    }
                }
                request.getRequestDispatcher("/admin").forward(request, response);
                return false;
            }
        }
        return true;
    }
}
