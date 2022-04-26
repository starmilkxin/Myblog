package com.xin.myblog.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Comment {
    private Long id;
    private String nickname;
    private String content;
    private String avatar;
    private Date createTime;
    //判断是否是博主发的评论
    private Boolean adminComment;

    //多个comment对应一个blog
    private Blog blog;
    private int blogId;

    //一个父类评论有多个子类评论
    private List<Comment> replyComments = new ArrayList<>();

    //一个子类评论只有一个父类评论
    private Comment parentComment;
    private int parentCommentId;

    private String ip;

    public Comment() {
    }

    public Comment(Long id, String nickname, String content, String avatar, Date createTime, Blog blog, int blogId, List<Comment> replyComments, Comment parentComment, int parentCommentId) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.avatar = avatar;
        this.createTime = createTime;
        this.blog = blog;
        this.blogId = blogId;
        this.replyComments = replyComments;
        this.parentComment = parentComment;
        this.parentCommentId = parentCommentId;
    }

}
