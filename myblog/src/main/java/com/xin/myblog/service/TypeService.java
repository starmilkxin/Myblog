package com.xin.myblog.service;

import com.xin.myblog.dao.TypeMapper;
import com.xin.myblog.pojo.Page;
import com.xin.myblog.pojo.Tag;
import com.xin.myblog.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeService {
    @Autowired
    private TypeMapper typeMapper;

    public List<Type> listTypeByPage(Page page) {
        return typeMapper.selectTypeByPage(page.getOffset(), page.getLimit());
    }

    public List<Type> listType() {
        return typeMapper.selectAllType();
    }

    public Type getTypeById(Integer id) {
        return typeMapper.selectTypeById(id);
    }

    public Type getTypeByName(String name) {
        return typeMapper.selectTypeByName(name);
    }

    public int findTypeRows() {
        return typeMapper.selectTypeRows();
    }

    public int saveType(Type type) {
        return typeMapper.insertType(type);
    }

    public int updateType(Integer id, Type type) {
        return typeMapper.updateType(id, type.getName());
    }

    public int deleteType(Integer id) {
        return typeMapper.deleteType(id);
    }

    public List<Type> listTypeMost(int limit) {
        List<Integer> typeIds = typeMapper.selectTypeIdMost(0, limit);
        List<Type> types = new ArrayList<>();
        for (int i : typeIds) {
            Type type = typeMapper.selectTypeById(i);
            types.add(type);
        }
        return types;
    }

    public int getTypeNumberByTypeId(int id) {
        return typeMapper.selectTypeNumberByTypeId(id);
    }
}
