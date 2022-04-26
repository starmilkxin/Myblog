package com.xin.myblog.controller.admin;

import com.alibaba.fastjson.JSON;
import com.xin.myblog.annotation.LoginRequired;
import com.xin.myblog.pojo.*;
import com.xin.myblog.service.BlogService;
import com.xin.myblog.service.TagService;
import com.xin.myblog.service.TypeService;
import com.xin.myblog.util.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    /**
     * 根据查询条件，查询符合的博客数据并返回
     * @param receivedData 从前端收到的查询条件
     * @return 符合的博客数据
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(path = "/blogs", method = RequestMethod.POST)
    public String blogs(@RequestBody Map<String, Object> receivedData) {
        Map<String, Object> data = new HashMap<>();

        String title = (String)receivedData.get("title");
        title = (title.equals("")) ? null : title;
        Integer typeId = (Integer)receivedData.get("typeId");
        typeId = typeId == -1 ? null : typeId;
        Boolean recommend = (Boolean)receivedData.get("whetherrecommend");
        recommend = !recommend ? null : recommend;
        //设置page信息
        Map<String, Integer> pageMap = (Map<String, Integer>)receivedData.get("page");
        Page page = new Page();
        page.setCurrent(pageMap.get("current"));
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
        List<Type> list_type = typeService.listType();
        //为了要能选择全部类别，还需要一个"全类别"
        Type type_all = new Type(null, "全类别");
        list_type.add(type_all);
        data.put("blogs", blogs);
        data.put("types", list_type);
        data.put("page", page);
        return JSON.toJSONString(Result.success().data(data));
    }

    /**
     * 获取博客的信息
     * @param id 博客id
     * @param request
     * @return 返回博客的信息内容
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(path = "/blogs/input", method = RequestMethod.GET)
    public String input(@RequestParam(value="blogId", defaultValue = "-1") String id, HttpServletRequest request) {
        Integer blogId = Integer.parseInt(id);
        String token = request.getHeader("Authorization");
        //获取token，并且存入redis中
        String uuid = UUID.randomUUID().toString();
        User user = jwtUtil.getUser(token);
        String redisKey = "inputBlog" + ":" + user.getUsername() + ":" + user.getId();
        redisTemplate.opsForValue().set(redisKey, uuid);
        redisTemplate.expire(redisKey, 6, TimeUnit.HOURS);
        return JSON.toJSONString(Result.success()
                .data("types", typeService.listType())
                .data("tags", tagService.listTag())
                .data("blog", blogService.getBlogById(blogId))
                .data("uuid",uuid));
    }

    /**
     * 提交/编辑博客
     * @param receivedData 前端发送的博客数据
     * @param request
     * @return 是否成功提交
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(path = "/blogsInput", method = RequestMethod.POST)
    public synchronized String post(@RequestBody Map<String, Object> receivedData, HttpServletRequest request) {
        Blog blog = null;
        Integer id = (Integer)receivedData.get("id");
        if (id != - 1) {
            blog = blogService.getBlogById(id);
        }else {
            blog = new Blog();
        }
        blog.setFlag((String)receivedData.get("flag"));
        blog.setTitle((String)receivedData.get("title"));
        blog.setContent((String)receivedData.get("content"));
        blog.setTypeId((Integer)receivedData.get("typeId"));
        blog.setTagsId(StringUtils.join((List)receivedData.get("tagsId"),','));
        blog.setFirstPicture((String)receivedData.get("firstPicture"));
        blog.setDescription((String)receivedData.get("description"));
        blog.setAppreciation((Boolean)receivedData.get("appreciation"));
        blog.setShareStatement((Boolean)receivedData.get("shareStatement"));
        blog.setCommentabled((Boolean)receivedData.get("commentabled"));
        blog.setPublished((Boolean)receivedData.get("published"));
        blog.setRecommend((Boolean)receivedData.get("recommend"));

        //使用Redis中的key保证幂等性
        //只有新增时才考虑幂等性
        String token = request.getHeader("Authorization");
        User user = jwtUtil.getUser(token);
        if (blog.getId() == null || blog.getId() == -1) {
            String redisKey = "inputBlog" + ":" + user.getUsername() + ":" + user.getId();
            String uuid = ((String)receivedData.get("uuid"));
            String correct_uuid = (String)redisTemplate.opsForValue().get(redisKey);
            if (correct_uuid != null && correct_uuid.equals(uuid)) {
                redisTemplate.delete(redisKey);
            }else {
                return JSON.toJSONString(Result.error().setMessage("重复提交").setCode(Result.ERROR_REPEAT));
            }
        }
        blog.setUser(user);
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
            return JSON.toJSONString(Result.error().setMessage("操作失败"));
        }else {
            return JSON.toJSONString(Result.success());
        }
    }

    /**
     * 删除指定博客并返回之前页数
     * @param id 博客id
     * @param pageCurrent 之前的页数
     * @return 是否成功删除
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(path = "/blogs/delete", method = RequestMethod.GET)
    public String delete(@RequestParam String id, @RequestParam String pageCurrent) {
        Integer blogId = Integer.parseInt(id);
        int n = blogService.deleteBlog(blogId);
        if (n == 0) {
            return JSON.toJSONString(Result.error().setMessage("删除失败"));
        }
        //查询删除后的总页数，如果比之前的current_str小1的话，那么current_str就减1
        Page page = new Page();
        page.setCurrent(Integer.parseInt(pageCurrent));
        int rows = blogService.findBlogRows();
        int limit = page.getLimit();
        int total = 0;
        if (rows % limit == 0) {
            total =  rows / limit;
        }else {
            total =  rows / limit + 1;
        }
        if (total == Integer.parseInt(pageCurrent) - 1) {
            page.setCurrent(total);
        }
        return JSON.toJSONString(Result.success().data("page", page));
    }
}
