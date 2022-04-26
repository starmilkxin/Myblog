package com.xin.myblog.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private int id;
    private String fromIp;
    private String fromNickname;
    private String toIp;
    private String toNickname;
    private int blogId;
    private String content;
    /**
     * status
     * 0-未读、1-已读、2-删除
     */
    private int status;
    private Date createTime;
}
