package com.xin.myblog.dao;

import com.xin.myblog.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Tag_BlogMapper {
    int deleteAllTags(Blog blog);

    int insertAllTags(Integer blogId, Long tagId);
}
