package com.xin.myblog.dao;

import com.xin.myblog.pojo.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    List<Message> selectMessages(int offset, int limit);

    int selectMessageRows(Integer status);

    int insertMessage(Message message);

    int updateMessageStatus(int status, int id);
}
