package com.xin.myblog.dao;

import com.xin.myblog.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public User selectByUsernameAndPassword(String username, String password);

    public User selectUserById(Long id);
}
