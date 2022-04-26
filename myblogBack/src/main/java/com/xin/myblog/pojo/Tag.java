package com.xin.myblog.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Tag {
    private Long id;
    private String name;

    //多个tag对应多个blog
    private List<Blog> blogs = new ArrayList<>();

    public Tag() {
    }

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
