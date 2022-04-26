package com.xin.myblog.controller;

import com.xin.myblog.pojo.Blog;
import com.xin.myblog.pojo.Page;
import com.xin.myblog.pojo.Tag;
import com.xin.myblog.service.BlogService;
import com.xin.myblog.service.TagService;
import com.xin.myblog.service.TypeService;
import com.xin.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TagShowController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/tags/{id}", method = RequestMethod.GET)
    public String tags(Model model, Page page, HttpServletRequest request, @PathVariable("id") Long id) {
        //按照tag所被使用的次数，查询全部tags集合
        List<Tag> tags = tagService.listTagMost(10000);
        Page tmp = new Page();
        tmp.setLimit(10000);
        for(Tag tag : tags) {
            List<Blog> blogs = blogService.listBlogPublishedByPage_Tag(tmp, tag.getId());
            tag.setBlogs(blogs);
        }
        model.addAttribute("tags", tags);
        //如果id没有指定（首页点进来的），那么就默认id是tags集合的第一个
        if (id == -1 && !tags.isEmpty()) {
            id = tags.get(0).getId();
        }
        model.addAttribute("activeTagId", id);
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("page");
        if (current_str != null) {
            page.setCurrent(Integer.parseInt(current_str));
        }else {
            page.setCurrent(1);
        }
        page.setRows(tagService.getTagNumberByTagId(id));
        //按照条件查询相对应tag的博客
        List<Blog> blogs = blogService.listBlogPublishedByPage_Tag(page, id);
        for (Blog blog : blogs) {
            List<Tag> tags2 =  tagService.getTagByIdS(blog.getTagsId());
            blog.setTags(tags2);
            blog.setUser(userService.findUserById(blog.getUserId()));
            blog.setType(typeService.getTypeById(blog.getTypeId()));
        }
        model.addAttribute("blogs", blogs);
        model.addAttribute("page", page);
        return "tags";
    }

    @RequestMapping(path = "/tags", method = RequestMethod.POST)
    public String tagsAJAX(Model model, Page page, HttpServletRequest request) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("desPage");
        if (current_str != null) {
            page.setCurrent(Integer.parseInt(current_str));
        }else {
            page.setCurrent(1);
        }
        Long id = Long.parseLong(request.getParameter("activeTagId"));
        page.setRows(tagService.getTagNumberByTagId(id));
        //按照条件查询相对应tag的博客
        List<Blog> blogs = blogService.listBlogPublishedByPage_Tag(page, id);
        for (Blog blog : blogs) {
            List<Tag> tags2 =  tagService.getTagByIdS(blog.getTagsId());
            blog.setTags(tags2);
            blog.setUser(userService.findUserById(blog.getUserId()));
            blog.setType(typeService.getTypeById(blog.getTypeId()));
        }
        model.addAttribute("blogs", blogs);
        model.addAttribute("page", page);
        model.addAttribute("activeTagId", id);
        return "tags :: blogList";
    }
}
