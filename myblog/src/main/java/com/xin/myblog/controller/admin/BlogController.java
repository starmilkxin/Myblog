package com.xin.myblog.controller.admin;

import com.xin.myblog.annotation.LoginRequired;
import com.xin.myblog.pojo.Blog;
import com.xin.myblog.pojo.Page;
import com.xin.myblog.pojo.Type;
import com.xin.myblog.pojo.User;
import com.xin.myblog.service.BlogService;
import com.xin.myblog.service.TagService;
import com.xin.myblog.service.TypeService;
import com.xin.myblog.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(path = "/admin")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JWTUtil jwtUtil;

    @LoginRequired
    @RequestMapping(path = "/blogs", method = RequestMethod.GET)
    public String blogs(Model model, Page page, HttpServletRequest request) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("page");
        if (current_str != null && !current_str.equals("")) {
            page.setCurrent(Integer.parseInt(current_str));
        }else {
            page.setCurrent(1);
        }
        page.setRows(blogService.findBlogRows());
        List<Map<String, Object>> blogs = new ArrayList<>();
        List<Blog> list = blogService.listBlogByPage(page);

        for (Blog blog : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", blog.getId());
            map.put("title", blog.getTitle());
            map.put("type", blogService.findType(blog));
            map.put("recommend", blog.getRecommend());
            map.put("published", blog.getPublished());
            map.put("createTime", blog.getCreateTime());
            map.put("updateTime", blog.getUpdateTime());
            blogs.add(map);
        }
        List<Type> list_type = typeService.listType();
        //为了要能选择全部类别，还需要一个"全类别"
        Type type_all = new Type(null, "全类别");
        list_type.add(type_all);
        model.addAttribute("types", list_type);
        model.addAttribute("blogs", blogs);
        return "admin/blogs";
    }

    @LoginRequired
    @RequestMapping(path = "/blogs/search", method = RequestMethod.POST)
    public String search(Model model, Page page, HttpServletRequest request) {
        String title = request.getParameter("title");
        title = (title.equals("")) ? null : title;
        String typeId_str = request.getParameter("typeId");
        Integer typeId;
        if (typeId_str == null || typeId_str.equals("") || typeId_str.equals("全类别")) {
            typeId = null;
        }else {
            typeId = Integer.parseInt(typeId_str);
        }
        String recommend_str = request.getParameter("recommend");
        //当不勾选recommend的时候，就不用管recommend的true和false了。当勾选recommend后，只筛选recommend为true的
        Boolean recommend = (recommend_str.equals("true")) ? true : null;
        String current_str = request.getParameter("page");
        if (current_str != null && !current_str.equals("")) {
            page.setCurrent(Integer.parseInt(current_str));
        }else {
            page.setCurrent(1);
        }
        page.setRows(blogService.findRowsBlogByFuzzyName_Type_Recommend_Published(title, typeId, recommend, null));
        List<Map<String, Object>> blogs = new ArrayList<>();
        List<Blog> list = blogService.listBlogByPage_FuzzyName_Type_Recommend_Published(page, title, typeId, recommend, null);
        for (Blog blog : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", blog.getId());
            map.put("title", blog.getTitle());
            map.put("type", blogService.findType(blog));
            map.put("recommend", blog.getRecommend());
            map.put("published", blog.getPublished());
            map.put("createTime", blog.getCreateTime());
            map.put("updateTime", blog.getUpdateTime());
            blogs.add(map);
        }
        model.addAttribute("blogs", blogs);
        model.addAttribute("page", page);
        return "admin/blogs :: blogList";
    }

    @LoginRequired
    @RequestMapping(path = "/blogs/input", method = RequestMethod.GET)
    public String input(Model model, Page page, HttpServletRequest request, @CookieValue("Authorization") String token) {
        //获取token，并且存入redis中
        String uuid = UUID.randomUUID().toString();
        User user = jwtUtil.getUser(token);
        String redisKey = "inputBlog" + ":" + user.getUsername() + ":" + user.getId();
        redisTemplate.opsForValue().set(redisKey, uuid);
        redisTemplate.expire(redisKey, 2, TimeUnit.HOURS);

        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("redisToken", uuid);
        page.setCurrent(1)  ;
        return "admin/blogs-input";
    }

    @LoginRequired
    //单个编辑时
    @RequestMapping(path = "/blogs/{id}/input", method = RequestMethod.GET)
    public String editInput(@PathVariable("id") Integer id, Model model, Page page, HttpServletRequest request) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("page");
        if (current_str != null) {
            page.setCurrent(Integer.parseInt(current_str));
        }else {
            current_str = "1";
            page.setCurrent(1);
        }
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("blog", blogService.getBlogById(id));
        return "admin/blogs-input";
    }

    @LoginRequired
    @RequestMapping(path = "/blogs", method = RequestMethod.POST)
    public synchronized String post(Blog blog, RedirectAttributes attributes , HttpServletRequest request, @CookieValue("Authorization") String token) {
        //使用Redis中的key保证幂等性
        //只有新增时才考虑幂等性
        User user = jwtUtil.getUser(token);
        if (blog.getId() == null || blog.getId() == 0) {
            String redisKey = "inputBlog" + ":" + user.getUsername() + ":" + user.getId();
            String uuid = request.getParameter("redisToken");
            String correct_uuid = (String)redisTemplate.opsForValue().get(redisKey);
            if (correct_uuid != null && correct_uuid.equals(uuid)) {
                redisTemplate.delete(redisKey);
            }else {
                return "redirect:/admin/blogs";
            }
        }
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("page");
        blog.setUser(user);
        blog.setType(typeService.getTypeById(blog.getTypeId()));
        blog.setTags(tagService.getTagByIdS(blog.getTagsId()));
        if (blog.getAppreciation() == null) {
            blog.setAppreciation(false);
        }
        if (blog.getCommentabled() == null) {
            blog.setCommentabled(false);
        }
        if (blog.getRecommend() == null) {
            blog.setRecommend(false);
        }
        if (blog.getShareStatement() == null) {
            blog.setShareStatement(false);
        }
        if (blog.getViews() == null) {
            blog.setViews(0);
        }
        int n = 0;
        //Id为空，说明是新增，否则是编辑
        if (blog.getId() == null || blog.getId() == 0) {
            n = blogService.saveBlog(blog);
        }else {
            n = blogService.updateBlog(blog);
        }
        if (n == 0) {
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/blogs?page=" + current_str;
    }

    @LoginRequired
    @RequestMapping(path = "/blogs/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes, HttpServletRequest request, Page page) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("page");
        if (current_str != null) {
        }else {
            current_str = "1";
        }
        int n = blogService.deleteBlog(id);
        if (n == 0) {
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        //查询删除后的总页数，如果比之前的current_str小1的话，那么current_str就减1
        int rows = blogService.findBlogRows();
        int limit = page.getLimit();
        int total = 0;
        if (rows % limit == 0) {
            total =  rows / limit;
        }else {
            total =  rows / limit + 1;
        }
        if (total == Integer.parseInt(current_str) - 1) {
            current_str = String.valueOf(total);
        }
        return "redirect:/admin/blogs?page=" + current_str;
    }
}
