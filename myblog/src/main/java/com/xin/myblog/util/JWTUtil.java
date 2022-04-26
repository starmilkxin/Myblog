package com.xin.myblog.util;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xin.myblog.controller.LoginController;
import com.xin.myblog.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {
    private Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    @Value("${jwt.secret}")
    private String JWTSCRET;

    public String createToken(String userJson){
        // 指定token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 6);
        Map<String, Object> header = new HashMap<>();
        header.put("type", "JWT");
        String token = JWT.create()
                .withHeader(header)
                .withClaim("user", userJson)
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(JWTSCRET));
        return token;
    }

    public Boolean checkToken(String token) {
        // 创建解析对象，使用的算法和secret要与创建token时保持一致
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(JWTSCRET)).build();
        // 解析指定的token
        DecodedJWT decodedToken = null;
        try {
            decodedToken = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return false;
        }
//        // 获取解析后的token中的payload信息
//        Claim userName = decodedToken.getClaim("userName");
//        System.out.println(userName.asString());
//        // 输出超时时间
//        System.out.println(decodedToken.getExpiresAt());
//        System.out.println(decodedToken.getToken());
//        System.out.println(decodedToken.getHeader());
//        System.out.println(decodedToken.getPayload());
//        System.out.println(decodedToken.getSignature());
        return true;
    }

    public User getUser(String token) {
        // 创建解析对象，使用的算法和secret要与创建token时保持一致
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(JWTSCRET)).build();
        // 解析指定的token
        DecodedJWT decodedToken = null;
        try {
            decodedToken = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }
        token = decodedToken.getClaim("user").asString();
        return JSON.parseObject(token, User.class);
    }
}
