package com.xin.myblog.controller;

import com.xin.myblog.pojo.Blog;
import com.xin.myblog.pojo.Page;
import com.xin.myblog.pojo.Tag;
import com.xin.myblog.pojo.Type;
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
public class TypeShowController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/types/{id}", method = RequestMethod.GET)
    public String types(Model model, Page page, HttpServletRequest request, @PathVariable("id") int id) {
        //按照type所被使用的次数，查询全部types集合
        List<Type> types = typeService.listTypeMost(10000);
        Page tmp = new Page();
        tmp.setLimit(10000);
        for(Type type : types) {
            List<Blog> blogs = blogService.listBlogByPage_FuzzyName_Type_Recommend_Published(tmp, null, type.getId(), null, true);
            type.setBlogs(blogs);
        }
        model.addAttribute("types", types);
        //如果id没有指定（首页点进来的），那么就默认id是types集合的第一个
        if (id == -1 && !types.isEmpty()) {
            id = types.get(0).getId();
        }
        model.addAttribute("activeTypeId", id);
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("page");
        if (current_str != null) {
            page.setCurrent(Integer.parseInt(current_str));
        }else {
            page.setCurrent(1);
        }
        page.setRows(typeService.getTypeNumberByTypeId(id));
        //按照条件查询相对应type的博客
        List<Blog> blogs = blogService.listBlogByPage_FuzzyName_Type_Recommend_Published(page, null, id, null, true);
        for (Blog blog : blogs) {
            List<Tag> tags =  tagService.getTagByIdS(blog.getTagsId());
            blog.setTags(tags);
            blog.setUser(userService.findUserById(blog.getUserId()));
            blog.setType(typeService.getTypeById(blog.getTypeId()));
        }
        model.addAttribute("blogs", blogs);
        model.addAttribute("page", page);
        return "types";
    }

    @RequestMapping(path = "/types", method = RequestMethod.POST)
    public String typesAJAX(Model model, Page page, HttpServletRequest request) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("desPage");
        if (current_str != null) {
            page.setCurrent(Integer.parseInt(current_str));
        }else {
            page.setCurrent(1);
        }
        int id = Integer.parseInt(request.getParameter("activeTypeId"));
        page.setRows(typeService.getTypeNumberByTypeId(id));
        //按照条件查询相对应type的博客
        List<Blog> blogs = blogService.listBlogByPage_FuzzyName_Type_Recommend_Published(page, null, id, null, true);
        for (Blog blog : blogs) {
            List<Tag> tags =  tagService.getTagByIdS(blog.getTagsId());
            blog.setTags(tags);
            blog.setUser(userService.findUserById(blog.getUserId()));
            blog.setType(typeService.getTypeById(blog.getTypeId()));
        }
        model.addAttribute("blogs", blogs);
        model.addAttribute("page", page);
        model.addAttribute("activeTypeId", id);
        return "types :: blogList";
    }
}
