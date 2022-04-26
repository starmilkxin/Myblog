package com.xin.myblog.dao;

import com.xin.myblog.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> selectCommentByBlogIdANDParentCommentId(Integer blogId, Long parentCommentId, int offset, int limit);

    int selectCommentByBlogIdANDParentCommentIdRows(Integer blogId, Long parentCommentId);

    int insertComment(Comment comment);

    int get_comment_rowsByIp(String ip, Date yesterday);
}
