package com.xin.myblog.service;

import com.xin.myblog.dao.CommentMapper;
import com.xin.myblog.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    //存放某个评论下所有已经封装好的子评论
    private List<Comment> kidreplys_all;

    @Autowired
    private CommentMapper commentMapper;

    public List<Comment> listCommentByBlogIdAndParentCommentId(Integer blogId, Long parentCommentId) {
        return commentMapper.selectCommentByBlogIdANDParentCommentId(blogId, parentCommentId);
    }

    public int saveComment(Comment comment){
        comment.setCreateTime(new Date());
        if (comment.getAdminComment() == null) {
            comment.setAdminComment(false);
        }
        return commentMapper.insertComment(comment);
    }

    public void find_kidreplys(Comment comment) {
        List<Comment> list = listCommentByBlogIdAndParentCommentId(comment.getBlogId(), comment.getId());
        for (Comment tmp : list) {
            tmp.setParentComment(comment);
            kidreplys_all.add(tmp);
            find_kidreplys(tmp);
        }
    }

    public List<Comment> get_kidreplys(Comment comment) {
        kidreplys_all = new ArrayList<>();
        find_kidreplys(comment);
        return kidreplys_all;
    }

    public int get_comment_rowsByIp(String ip) {
        Date today = new Date();
        Date yesterday = new Date(today.getTime() - 24*60*60*1000);
        return commentMapper.get_comment_rowsByIp(ip, yesterday);
    }
}
