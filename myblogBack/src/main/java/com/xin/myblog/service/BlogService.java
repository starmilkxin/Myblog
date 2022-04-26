package com.xin.myblog.service;

import com.xin.myblog.dao.BlogMapper;
import com.xin.myblog.dao.Tag_BlogMapper;
import com.xin.myblog.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private Tag_BlogMapper tag_blogMapper;

    @Autowired
    private TagService tagService;

    public List<Blog> listBlogByPage(Page page) {
        return blogMapper.selectBlogByPage(page.getOffset(), page.getLimit());
    }

    public List<Blog> listBlogByPage_FuzzyName_Type_Recommend_Published(Page page, String name, Integer id, Boolean recommend, Boolean published) {
        return blogMapper.selectBlogByPage_FuzzyName_Type_Recommend_Published(page.getOffset(), page.getLimit(), name, id, recommend, published);
    }

    public Blog getBlogById(Integer id) {
        return blogMapper.selectBlogById(id);
    }

    public Blog getBlogByName(String name) {
        return blogMapper.selectBlogByName(name);
    }

    public int findBlogRows() {
        return blogMapper.selectBlogRows();
    }

    public int findRowsBlogByFuzzyName_Type_Recommend_Published(String name, Integer id, Boolean recommend, Boolean published) {
        return blogMapper.selectBlogRowsByFuzzyName_Type_Recommend_Published(name, id, recommend, published);
    }

    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        int n = blogMapper.insertBlog(blog);
        //此时blog有了id后，再将blog中所有tag信息插入
        List<Tag> tags = tagService.getTagByIdS(blog.getTagsId());
        if (tags != null) {
            for (Tag tag : tags) {
                tag_blogMapper.insertAllTags(blogMapper.selectMostNearBlogId(), tag.getId());
            }
        }
        return n;
    }

    public int updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        //首先要将t_tag_blog表中的相关的全删除
        tag_blogMapper.deleteAllTags(blog);
        //再将blog中所有tag信息插入
        List<Tag> tags = tagService.getTagByIdS(blog.getTagsId());
        if (tags != null) {
            for (Tag tag : tags) {
                tag_blogMapper.insertAllTags(blog.getId(), tag.getId());
            }
        }
        return blogMapper.updateBlog(blog);
    }

    public int updateBlogviews(Integer id) {
        return blogMapper.updateBlogViews(id);
    }

    public int deleteBlog(Integer id) {
        return blogMapper.deleteBlog(id);
    }

    public Type findType(Blog blog) {
        return blogMapper.selectType(blog);
    }

    public User findUser(Blog blog) {
        return blogMapper.selectUser(blog);
    }

    public List<Blog> listBlogRecommendMostNear(int limit) {
        return blogMapper.selectBlogRecommendMostNear(0, limit);
    }

    public List<Blog> listBlogPublishedByPage(Page page) {
        return blogMapper.selectBlogPublishedByPage(page.getOffset(), page.getLimit());
    }

    public List<Blog> listBlogPublishedByPage_Tag(Page page, Long id) {
        return blogMapper.selectBlogPublishedByPage_Tag(page.getOffset(), page.getLimit(), id);
    }

    public List<Map<String, Object>> listBlogPublishedWithYear() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<String> years = blogMapper.selectYear();
        for (String year : years) {
            List<Blog> blogs =  blogMapper.selectBlogPublishedByYear(year);
            Map<String, Object> map = new HashMap<>();
            map.put("year", year);
            map.put("blogs", blogs);
            list.add(map);
        }
        return list;
    }
}
