package com.xin.myblog.dao;

import com.xin.myblog.pojo.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TypeMapper {
    List<Type> selectTypeByPage(int offset, int limit);

    List<Type> selectAllType();

    Type selectTypeById(Integer id);

    Type selectTypeByName(String name);

    int selectTypeRows();

    int insertType(Type type);

    int updateType(Integer id, String name);

    int deleteType(Integer id);

    List<Integer> selectTypeIdMost(int offset, int limit);

    Integer selectTypeNumberByTypeId(int id);
}
