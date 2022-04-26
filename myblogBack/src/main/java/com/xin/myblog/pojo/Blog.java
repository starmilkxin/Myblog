package com.xin.myblog.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Blog {
    private Integer id;
    private String title;
    private String content;
    private String firstPicture;
    private String description;
    //标记
    private String flag;
    //浏览次数
    private Integer views;
    //赞赏开启
    private Boolean appreciation;
    //版权开启
    private Boolean shareStatement;
    //评论开启
    private Boolean commentabled;
    //是否发布
    private Boolean published;
    //是否推荐
    private Boolean recommend;
    private Date createTime;
    private Date updateTime;

    //多个blog对应一个type
    private Type type;
    private Integer typeId;

    //多个blog对应多个tag
    private List<Tag> tags = new ArrayList<>();
    private String tagsId;

    //多个blog对应多个comment
    private List<Comment> comments = new ArrayList<>();

    //多个blog对应一个user
    private User user;
    private Long userId;

    public Blog() {
    }

    public Blog(int id, String title, String content, String firstPicture, String flag, Integer views, Boolean appreciation, Boolean shareStatement, Boolean commentabled, Boolean published, Boolean recommend, Date createTime, Date updateTime, Type type, Integer typeId, List<Tag> tags, String tagsId, List<Comment> comments, User user, Long userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.firstPicture = firstPicture;
        this.flag = flag;
        this.views = views;
        this.appreciation = appreciation;
        this.shareStatement = shareStatement;
        this.commentabled = commentabled;
        this.published = published;
        this.recommend = recommend;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.type = type;
        this.typeId = typeId;
        this.tags = tags;
        this.tagsId = tagsId;
        this.comments = comments;
        this.user = user;
        this.userId = userId;
    }
}