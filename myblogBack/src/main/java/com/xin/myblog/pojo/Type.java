package com.xin.myblog.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Type {
    private Integer id;
    private String name;

    //一个type对应多个blog
    private List<Blog> blogs = new ArrayList<>();

    public Type() {
    }

    public Type(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
