package com.xin.myblog.dao;

import com.xin.myblog.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {
    List<Tag> selectTagByPage(int offset, int limit);

    List<Tag> selectAllTag();

    Tag selectTagById(Long id);

    Tag selectTagByName(String name);

    int selectTagRows();

    int insertTag(Tag type);

    int updateTag(Long id, String name);

    int deleteTag(Long id);

    List<Long> selectTagIdMost(int offset, int limit);

    Integer selectTagNumberByTagId(Long id);
}
