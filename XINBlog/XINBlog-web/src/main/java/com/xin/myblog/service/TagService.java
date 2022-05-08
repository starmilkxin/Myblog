package com.xin.myblog.service;

import com.xin.myblog.dao.TagMapper;
import com.xin.myblog.pojo.Page;
import com.xin.myblog.pojo.Tag;
import com.xin.myblog.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagMapper tagMapper;

    public List<Tag> listTagByPage(Page page) {
        return tagMapper.selectTagByPage(page.getOffset(), page.getLimit());
    }

    public List<Tag> listTag() {
        return tagMapper.selectAllTag();
    }

    public Tag getTagById(Long id) {
        return tagMapper.selectTagById(id);
    }

    public List<Tag> getTagByIdS(String ids) {
        if (ids == null || ids.equals("")) {
            return null;
        }
        String[] strs = ids.split(",");
        List<Tag> tags = new ArrayList<>();
        for(String str : strs) {
            Long id = Long.parseLong(str);
            Tag tag = tagMapper.selectTagById(id);
            tags.add(tag);
        }
        return tags;
    }

    public Tag getTagByName(String name) {
        return tagMapper.selectTagByName(name);
    }

    public int findTagRows() {
        return tagMapper.selectTagRows();
    }

    public List<Tag> listTagMost(int limit) {
        List<Long> tagIds = tagMapper.selectTagIdMost(0, limit);
        List<Tag> tags = new ArrayList<>();
        for (Long i : tagIds) {
            Tag tag = tagMapper.selectTagById(i);
            tags.add(tag);
        }
        return tags;
    }

    public int saveTag(Tag tag) {
        return tagMapper.insertTag(tag);
    }

    public int updateTag(Long id, Tag tag) {
        return tagMapper.updateTag(id, tag.getName());
    }

    public int deleteTag(Long id) {
        return tagMapper.deleteTag(id);
    }

    public int getTagNumberByTagId(Long id) {
        return tagMapper.selectTagNumberByTagId(id);
    }

}
