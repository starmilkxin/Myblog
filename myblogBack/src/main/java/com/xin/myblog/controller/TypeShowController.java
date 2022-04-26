package com.xin.myblog.controller;

import com.alibaba.fastjson.JSON;
import com.xin.myblog.pojo.*;
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
public class TypeShowController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    /**
     * 查询所有types的信息
     * @return 所有types的数据
     */
    @ResponseBody
    @RequestMapping(path = "/typesAll", method = RequestMethod.GET)
    public String types(HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        //按照type所被使用的次数，查询全部types集合
        List<Type> types = typeService.listTypeMost(10000);
        Page tmp = new Page();
        tmp.setLimit(10000);
        for(Type type : types) {
            List<Blog> blogs = blogService.listBlogByPage_FuzzyName_Type_Recommend_Published(tmp, null, type.getId(), null, true);
            type.setBlogs(blogs);
        }
        data.put("types", types);
        //当前type的Id
        Integer activeTypeId = Integer.valueOf(request.getParameter("activeTypeId"));
        activeTypeId = (activeTypeId == null || activeTypeId == -1) ? types.get(0).getId() : activeTypeId;
        data.put("activeTypeId", activeTypeId);
        //设置page的行数
        Page page = new Page();
        page.setRows(typeService.getTypeNumberByTypeId(activeTypeId));
        data.put("page", page);
        //按照条件查询相对应type的博客
        List<Blog> list = blogService.listBlogByPage_FuzzyName_Type_Recommend_Published(page, null, activeTypeId, null, true);
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
    @RequestMapping(path = "/types", method = RequestMethod.POST)
    public String types(@RequestBody Page page, @RequestParam Integer activeTypeId) {
        Map<String, Object> data = new HashMap<>();
        page.setRows(typeService.getTypeNumberByTypeId(activeTypeId));
        //按照条件查询相对应type的博客
        List<Blog> list = blogService.listBlogByPage_FuzzyName_Type_Recommend_Published(page, null, activeTypeId, null, true);
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
