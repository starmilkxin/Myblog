package com.xin.myblog.controller;

import com.alibaba.fastjson.JSON;
import com.xin.myblog.pojo.*;
import com.xin.myblog.service.*;
import com.xin.myblog.util.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(path = "/x")
public class IndexController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    /**
     * 在首页查找博客列表数据
     * @param page 分页对象
     * @return 博客列表数据
     */
    @ResponseBody
    @RequestMapping(path = {"/index", "/"}, method = RequestMethod.POST)
    public String index(@RequestBody Page page) {
        Map<String, Object> data = new HashMap<>();
        //page设置总共数据数量
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
        //设置页数
        data.put("page", page);
        return JSON.toJSONString(Result.success().data(data));
    }

    /**
     * 查找首页右侧type，tag和recommend数据
     * @return 首页右侧数据
     */
    @ResponseBody
    @RequestMapping(path = {"/indexRight"}, method = RequestMethod.POST)
    public String indexRight() {
        Map<String, Object> data = new HashMap<>();
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
        data.put("types", types);
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
        data.put("tags", tags);
        //设置最新推荐blog的内容
        data.put("recommendBlogs", blogService.listBlogRecommendMostNear(8));
        return JSON.toJSONString(Result.success().data(data));
    }

    /**
     * 搜索博客
     * @param page
     * @param name 搜索的内容
     * @return 符合条件的博客数据
     */
    @ResponseBody
    @RequestMapping(path = "/search", method = RequestMethod.POST)
    public String search(@RequestBody Page page, @RequestParam String name) {
        name = name == null ? "" : name;
        System.out.println("name is: " + name);
        Map<String, Object> data = new HashMap<>();
        //设置page的总行数
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

    /**
     * 获取博客的具体内容
     * @param id 博客id
     * @return 具体博客的内容(不包括评论)
     */
    @ResponseBody
    @RequestMapping(path = "/blogContent", method = RequestMethod.GET)
    public String blog(@RequestParam("blogId") int id) {
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
        String content = blog.getContent();
        //markdown转换html(最初的editormd需要，现在的mavonEditor不需要)
//        content = MarkdownUtils.markdownToHtmlExtensions(content);
        map.put("content", content);
        return JSON.toJSONString(Result.success().data("blog", map));
    }
}
