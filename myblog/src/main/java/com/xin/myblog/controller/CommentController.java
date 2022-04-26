package com.xin.myblog.controller;

import com.xin.myblog.pojo.Comment;
import com.xin.myblog.pojo.User;
import com.xin.myblog.service.CommentService;
import com.xin.myblog.util.JWTUtil;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;

@Controller
public class CommentController {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CommentService commentService;

    @Value("${comment.avatar}")
    private String avatar;

    @RequestMapping(path = "/comments/{blogId}", method = RequestMethod.GET)
    public String comments(@PathVariable int blogId, Model model) {
        //设置comment的内容
        //首先找到博客的一级评论
        List<Comment> firstlevel_comment = commentService.listCommentByBlogIdAndParentCommentId(blogId, (long)-1);
        //找到并设置一级评论下的所有二级、三级...评论
        for (Comment tmp : firstlevel_comment) {
            List<Comment> otherlevel_comment = commentService.get_kidreplys(tmp);
            tmp.setReplyComments(otherlevel_comment);
        }
        model.addAttribute("comments", firstlevel_comment);
        return "blog :: commentList";
    }

    @RequestMapping(path = "/comments", method = RequestMethod.POST)
    public String post(Comment comment, HttpServletRequest request, @CookieValue("Authorization") String token) {
        User user = jwtUtil.getUser(token);
        //24小时内发了5条评论后就不能发评论
        String ip = request.getHeader("X-Forwarded-For");
        int n = commentService.get_comment_rowsByIp(ip);
        if (n > 5) {
            return "redirect:/comments/" + comment.getBlogId();
        }
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
        commentService.saveComment(comment);
        return "redirect:/comments/" + comment.getBlogId();
    }
}
