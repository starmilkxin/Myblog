package com.xin.myblog.controller;

import com.xin.myblog.pojo.*;
import com.xin.myblog.service.*;
import com.xin.myblog.util.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(path = {"/index", "/"}, method = RequestMethod.GET)
    public String index(Model model, Page page, HttpServletRequest request) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("page");
        if (current_str != null) {
            page.setCurrent(Integer.parseInt(current_str));
        }else {
            page.setCurrent(1);
        }
        page.setRows(blogService.findRowsBlogByFuzzyName_Type_Recommend_Published(null, null, null, true));
        //设置blogs的内容
        List<Map<String, Object>> blogs = new ArrayList<>();
        List<Blog> list = blogService.listBlogPublishedByPage(page);
        for (Blog blog : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", blog.getTitle());
            map.put("userAvatar", userService.findUserById(blog.getUserId()).getAvatar());
            map.put("description", blog.getDescription());
            map.put("firstPicture", blog.getFirstPicture());
            map.put("userNickname", userService.findUserById(blog.getUserId()).getNickname());
            map.put("createTime", blog.getCreateTime());
            map.put("views", blog.getViews());
            map.put("typeId", blog.getTypeId());
            map.put("typeName", typeService.getTypeById(blog.getTypeId()).getName());
            map.put("tags", tagService.getTagByIdS(blog.getTagsId()));
            map.put("blogId", blog.getId());
            map.put("flag", blog.getFlag());
            blogs.add(map);
        }
        model.addAttribute("blogs", blogs);
        //设置types的内容
        List<Map<String, Object>> types = new ArrayList<>();
        List<Type> list2 =  typeService.listTypeMost(6);
        for (Type type : list2) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", type.getName());
            map.put("number", typeService.getTypeNumberByTypeId(type.getId()));
            map.put("id", type.getId());
            types.add(map);
        }
        model.addAttribute("types", types);
        //设置tags的内容
        Page tmp = new Page();
        tmp.setLimit(6);
        List<Map<String, Object>> tags = new ArrayList<>();
        List<Tag> list3 = tagService.listTagMost(tmp.getLimit());
        for (Tag tag : list3) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", tag.getName());
            map.put("number", tagService.getTagNumberByTagId(tag.getId()));
            map.put("id", tag.getId());
            tags.add(map);
        }
        model.addAttribute("tags", tags);
        //设置最新推荐blog的内容
        model.addAttribute("recommendBlogs", blogService.listBlogRecommendMostNear(8));
        return "index";
    }

    @RequestMapping(path = {"/index", "/"}, method = RequestMethod.POST)
    public String indexAJAX(Model model, Page page, HttpServletRequest request) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("desPage");
        if (current_str != null) {
            page.setCurrent(Integer.parseInt(current_str));
        }else {
            page.setCurrent(1);
        }
        page.setRows(blogService.findRowsBlogByFuzzyName_Type_Recommend_Published(null, null, null, true));
        //设置blogs的内容
        List<Map<String, Object>> blogs = new ArrayList<>();
        List<Blog> list = blogService.listBlogPublishedByPage(page);
        for (Blog blog : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", blog.getTitle());
            map.put("userAvatar", userService.findUserById(blog.getUserId()).getAvatar());
            map.put("description", blog.getDescription());
            map.put("firstPicture", blog.getFirstPicture());
            map.put("userNickname", userService.findUserById(blog.getUserId()).getNickname());
            map.put("createTime", blog.getCreateTime());
            map.put("views", blog.getViews());
            map.put("typeId", blog.getTypeId());
            map.put("typeName", typeService.getTypeById(blog.getTypeId()).getName());
            map.put("tags", tagService.getTagByIdS(blog.getTagsId()));
            map.put("blogId", blog.getId());
            map.put("flag", blog.getFlag());
            blogs.add(map);
        }
        model.addAttribute("blogs", blogs);
        model.addAttribute("page", page);
        return "index :: blogList";
    }

    //搜索提交时
    @RequestMapping(path = "/search", method = RequestMethod.POST)
    public String search(Model model, Page page, HttpServletRequest request) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("page");
        if (current_str != null) {
            page.setCurrent(Integer.parseInt(current_str));
        }else {
            page.setCurrent(1);
        }
        String name = request.getParameter("query");
        page.setRows(blogService.findRowsBlogByFuzzyName_Type_Recommend_Published(name, null, null, true));
        //设置blogs的内容
        List<Map<String, Object>> blogs = new ArrayList<>();
        List<Blog> list = blogService.listBlogByPage_FuzzyName_Type_Recommend_Published(page, name, null, null, true);
        for (Blog blog : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", blog.getTitle());
            map.put("userAvatar", userService.findUserById(blog.getUserId()).getAvatar());
            map.put("description", blog.getDescription());
            map.put("firstPicture", blog.getFirstPicture());
            map.put("userNickname", userService.findUserById(blog.getUserId()).getNickname());
            map.put("createTime", blog.getCreateTime());
            map.put("views", blog.getViews());
            map.put("typeId", blog.getTypeId());
            map.put("typeName", typeService.getTypeById(blog.getTypeId()).getName());
            map.put("tags", tagService.getTagByIdS(blog.getTagsId()));
            map.put("blogId", blog.getId());
            map.put("flag", blog.getFlag());
            blogs.add(map);
        }
        model.addAttribute("blogs", blogs);
        //设置查询的字段的名称
        model.addAttribute("query", name);
        //设置查询的字段的名称
        model.addAttribute("query", name);
        return "search";
    }

    @RequestMapping(path = "/searchAJAX", method = RequestMethod.POST)
    public String searchAJAX(Model model, Page page, HttpServletRequest request) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("desPage");
        if (current_str != null) {
            page.setCurrent(Integer.parseInt(current_str));
        }else {
            page.setCurrent(1);
        }
        String name = request.getParameter("query");
        page.setRows(blogService.findRowsBlogByFuzzyName_Type_Recommend_Published(name, null, null, true));
        //设置blogs的内容
        List<Map<String, Object>> blogs = new ArrayList<>();
        List<Blog> list = blogService.listBlogByPage_FuzzyName_Type_Recommend_Published(page, name, null, null, true);
        for (Blog blog : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", blog.getTitle());
            map.put("userAvatar", userService.findUserById(blog.getUserId()).getAvatar());
            map.put("description", blog.getDescription());
            map.put("firstPicture", blog.getFirstPicture());
            map.put("userNickname", userService.findUserById(blog.getUserId()).getNickname());
            map.put("createTime", blog.getCreateTime());
            map.put("views", blog.getViews());
            map.put("typeId", blog.getTypeId());
            map.put("typeName", typeService.getTypeById(blog.getTypeId()).getName());
            map.put("tags", tagService.getTagByIdS(blog.getTagsId()));
            map.put("blogId", blog.getId());
            map.put("flag", blog.getFlag());
            blogs.add(map);
        }
        model.addAttribute("blogs", blogs);
        model.addAttribute("page", page);
        //设置查询的字段的名称
        model.addAttribute("query", name);
        return "search :: blogList";
    }

    @RequestMapping(path = "/blog/{id}", method = RequestMethod.GET)
    public String blog(@PathVariable("id") int id, Model model) {
        //增加views阅读量
        blogService.updateBlogviews(id);
        //设置blog的内容
        Blog blog = blogService.getBlogById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("title", blog.getTitle());
        map.put("userAvatar", userService.findUserById(blog.getUserId()).getAvatar());
        String description = blog.getDescription();
        map.put("description", description);
        map.put("firstPicture", blog.getFirstPicture());
        map.put("userNickname", userService.findUserById(blog.getUserId()).getNickname());
        map.put("createTime", blog.getCreateTime());
        map.put("updateTime", blog.getUpdateTime());
        map.put("views", blog.getViews());
        map.put("typeId", blog.getTypeId());
        map.put("typeName", typeService.getTypeById(blog.getTypeId()).getName());
        map.put("tags", tagService.getTagByIdS(blog.getTagsId()));
        map.put("blogId", blog.getId());
        map.put("flag", blog.getFlag());
        map.put("commentabled", blog.getCommentabled());
        map.put("appreciation", blog.getAppreciation());
        //markdown转换html
        String content = blog.getContent();
        content = MarkdownUtils.markdownToHtmlExtensions(content);
        map.put("content", content);
        model.addAttribute("blog", map);
        //设置comment的内容
        //首先找到博客的一级评论
        List<Comment> firstlevel_comment = commentService.listCommentByBlogIdAndParentCommentId(blog.getId(), (long)-1);
        //找到并设置一级评论下的所有二级、三级...评论
        for (Comment tmp : firstlevel_comment) {
            List<Comment> otherlevel_comment = commentService.get_kidreplys(tmp);
            tmp.setReplyComments(otherlevel_comment);
        }
        model.addAttribute("comments", firstlevel_comment);
        return "blog";
    }
}
