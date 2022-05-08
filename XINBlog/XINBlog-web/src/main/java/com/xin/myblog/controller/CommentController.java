package com.xin.myblog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xin.myblog.event.MessageProducer;
import com.xin.myblog.exception.extendedException.ToMuchCommentException;
import com.xin.myblog.pojo.*;
import com.xin.myblog.service.CommentService;
import com.xin.myblog.service.UserService;
import com.xin.myblog.util.JWTUtil;
import com.xin.myblog.util.SensitiveFilter;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping(path = "/x")
public class CommentController {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CommentService commentService;

    @Value("${comment.avatar}")
    private String avatar;

    @Autowired
    private UserService userService;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private MessageProducer messageProducer;

    /**
     * 获取博客的所有评论
     * @param blogId 博客id
     * @return 博客评论信息
     */
    @ResponseBody
    @RequestMapping(path = "/comments", method = RequestMethod.POST)
    public String comments(@RequestParam int blogId, @RequestBody Page page) {
        Map<String, Object> data = new HashMap<>();
        //设置page
        page.setRows(commentService.findCommentByBlogIdAndParentCommentIdRows(blogId, (long)-1));
        //设置comment的内容
        //首先找到博客的一级评论
        List<Comment> firstlevel_comment = commentService.listCommentByBlogIdAndParentCommentId(blogId, (long)-1, page);
        //找到并设置一级评论下的所有二级、三级...评论
        for (Comment tmp : firstlevel_comment) {
            List<Comment> otherlevel_comment = commentService.get_kidreplys(tmp);
            tmp.setReplyComments(otherlevel_comment);
        }
        data.put("page", page);
        data.put("comments", firstlevel_comment);
        return JSON.toJSONString(Result.success().data(data));
    }

    /**
     * 前端发送给后端评论信息
     * @param comment 评论信息
     * @param blogId 博客id
     * @param request
     * @return result
     */
    @ResponseBody
    @RequestMapping(path = "/commentsInput", method = RequestMethod.POST)
    public String post(@RequestBody Comment comment, @RequestParam int blogId, HttpServletRequest request) {
        //24小时内发了5条评论后就不能发评论
        String ip = request.getHeader("X-Forwarded-For");
        int n = commentService.get_comment_rowsByIp(ip);
        if (n > 5) {
            throw new ToMuchCommentException(null);
        }
        //从请求头中获取token并解析
        String token = request.getHeader("Authorization");
        User user = jwtUtil.getUser(token);
        //如果登录了，并且type是1，那么就是博主发的评论
        if (user != null && user.getType() == 1) {
            comment.setAdminComment(true);
        }
        if (user != null) {
            comment.setAvatar(user.getAvatar());
        }else {
            //1到10随机数拼接url获取随机的10个头像
            Random r = new Random();
            int rand = r.nextInt(10) + 1;
            comment.setAvatar(avatar + rand + ".png");
        }
        comment.setIp(request.getRemoteAddr());
        //comment中的content进行脏词过滤
        comment.setContent(sensitiveFilter.filter(comment.getContent()));
        commentService.saveComment(comment);

        //利用mq进行异步的message存储
        Message message = new Message();
        message.setFromIp(ip);
        message.setFromNickname(comment.getNickname());
        //突然发现暂时没法获取toIp。。。
        message.setToIp("");
        message.setToNickname(comment.getParentCommentNickname());
        message.setBlogId(comment.getBlogId());
        message.setContent(comment.getContent());
        message.setStatus(0);
        message.setCreateTime(comment.getCreateTime());
        messageProducer.fireMessage(message);

        return JSON.toJSONString(Result.success().setMessage("评论成功 (❁´◡`❁)"));
    }
}
