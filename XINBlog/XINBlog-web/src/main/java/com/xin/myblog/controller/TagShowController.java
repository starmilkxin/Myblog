package com.xin.myblog.controller;

import com.alibaba.fastjson.JSON;
import com.xin.myblog.pojo.Blog;
import com.xin.myblog.pojo.Page;
import com.xin.myblog.pojo.Result;
import com.xin.myblog.pojo.Tag;
import com.xin.myblog.service.BlogService;
import com.xin.myblog.service.TagService;
import com.xin.myblog.service.TypeService;
import com.xin.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/x")
public class TagShowController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(path = "/tagsAll", method = RequestMethod.GET)
    public String tags(HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        //按照tag所被使用的次数，查询全部tags集合
        List<Tag> tags = tagService.listTagMost(10000);
        Page tmp = new Page();
        tmp.setLimit(100);
        for(Tag tag : tags) {
            List<Blog> blogs = blogService.listBlogPublishedByPage_Tag(tmp, tag.getId());
            tag.setBlogs(blogs);
        }
        data.put("tags", tags);
        //当前tag的Id
        Long activeTagId = Long.valueOf(request.getParameter("activeTagId"));
        activeTagId = (activeTagId == null || activeTagId == -1) ? tags.get(0).getId() : activeTagId;
        data.put("activeTagId", activeTagId);
        //设置page的行数
        Page page = new Page();
        page.setRows(tagService.getTagNumberByTagId(activeTagId));
        data.put("page", page);
        //按照条件查询相对应tag的博客
        List<Blog> list = blogService.listBlogPublishedByPage_Tag(page, activeTagId);
        //设置blogs的内容
        List<Map<String, Object>> blogs = new ArrayList<>();
        for (Blog blog : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", blog.getTitle());
            map.put("userAvatar", userService.findUserById(blog.getUserId()).getAvatar());
            map.put("description", blog.getDescription());
            map.put("firstPicture", blog.getFirstPicture());
            map.put("userNickname", userService.findUserById(blog.getUserId()).getNickname());
            //日期格式化
            String createTime = new SimpleDateFormat("yyyy-MM-dd").format(blog.getCreateTime());
            map.put("createTime", createTime);
            map.put("views", blog.getViews());
            map.put("typeId", blog.getTypeId());
            map.put("typeName", typeService.getTypeById(blog.getTypeId()).getName());
            map.put("tags", tagService.getTagByIdS(blog.getTagsId()));
            map.put("blogId", blog.getId());
            map.put("flag", blog.getFlag());
            blogs.add(map);
        }
        data.put("blogs", blogs);
        return JSON.toJSONString(Result.success().data(data));
    }

    @ResponseBody
    @RequestMapping(path = "/tags", method = RequestMethod.POST)
    public String tags(@RequestBody Page page, @RequestParam Long activeTagId) {
        Map<String, Object> data = new HashMap<>();
        page.setRows(tagService.getTagNumberByTagId(activeTagId));
        //按照条件查询相对应tag的博客
        List<Blog> list = blogService.listBlogPublishedByPage_Tag(page, activeTagId);
        //设置blogs的内容
        List<Map<String, Object>> blogs = new ArrayList<>();
        for (Blog blog : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", blog.getTitle());
            map.put("userAvatar", userService.findUserById(blog.getUserId()).getAvatar());
            map.put("description", blog.getDescription());
            map.put("firstPicture", blog.getFirstPicture());
            map.put("userNickname", userService.findUserById(blog.getUserId()).getNickname());
            //日期格式化
            String createTime = new SimpleDateFormat("yyyy-MM-dd").format(blog.getCreateTime());
            map.put("createTime", createTime);
            map.put("views", blog.getViews());
            map.put("typeId", blog.getTypeId());
            map.put("typeName", typeService.getTypeById(blog.getTypeId()).getName());
            map.put("tags", tagService.getTagByIdS(blog.getTagsId()));
            map.put("blogId", blog.getId());
            map.put("flag", blog.getFlag());
            blogs.add(map);
        }
        data.put("blogs", blogs);
        data.put("page", page);
        return JSON.toJSONString(Result.success().data(data));
    }
}
