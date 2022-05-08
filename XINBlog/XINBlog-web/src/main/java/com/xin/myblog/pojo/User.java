package com.xin.myblog.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class User {
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String email;
    private String avatar;
    //1是管理员，2不是管理员
    private Integer type;
    private Date createTime;
    private Date updateTime;

    //一个user对应多个blog
    private List<Blog> blogs = new ArrayList<>();

    public User() {
    }

    public User(Long id, String nickname, String username, String password, String email, String avatar, Integer type, Date createTime, Date updateTime) {
        this.id = id;
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.type = type;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
