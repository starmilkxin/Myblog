package com.xin.myblog.service;

import com.xin.myblog.dao.UserMapper;
import com.xin.myblog.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserByUsernameAndPassword(String username, String password) {
        return userMapper.selectByUsernameAndPassword(username, password);
    };

    public User findUserById(Long id) {
        return userMapper.selectUserById(id);
    }
}
