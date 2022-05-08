package com.xin.myblog.dao;

import com.xin.myblog.pojo.Blog;
import com.xin.myblog.pojo.Type;
import com.xin.myblog.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlogMapper {
    List<Blog> selectBlogByPage(int offset, int limit);

    List<Blog> selectBlogByPage_FuzzyName_Type_Recommend_Published(int offset, int limit, String name, Integer id, Boolean recommend, Boolean published);

    Blog selectBlogById(Integer id);

    Blog selectBlogByName(String name);

    int selectBlogRows();

    int selectBlogRowsByFuzzyName_Type_Recommend_Published(String name, Integer id, Boolean recommend, Boolean published);

    int insertBlog(Blog blog);

    int updateBlog(Blog blog);

    int updateBlogViews(Integer id);

    int deleteBlog(Integer id);

    Type selectType(Blog blog);

    User selectUser(Blog blog);

    List<Blog> selectBlogRecommendMostNear(int offset, int limit);

    List<Blog> selectBlogPublishedByPage(int offset, int limit);

    List<Blog> selectBlogPublishedByPage_Tag(int offset, int limit, Long id);

    @Select("SELECT date_format(b.create_time, '%Y') as year\n" +
            "FROM t_blog b\n" +
            "WHERE published = true\n" +
            "GROUP BY year\n" +
            "ORDER BY year DESC")
    List<String> selectYear();

    @Select("SELECT *\n" +
            "FROM t_blog\n" +
            "WHERE date_format(create_time, '%Y') = #{year}\n" +
            "AND published = true\n" +
            "ORDER BY create_time DESC")
    List<Blog> selectBlogPublishedByYear(String year);

    @Select("SELECT id\n" +
            "FROM t_blog\n" +
            "ORDER BY update_time DESC\n" +
            "Limit 0, 1")
    Integer selectMostNearBlogId();
}
