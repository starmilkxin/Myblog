package com.xin.myblog.service;

import com.xin.myblog.dao.MessageMapper;
import com.xin.myblog.pojo.Message;
import com.xin.myblog.pojo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    public List<Message> listMessage(Page page) {
        return messageMapper.selectMessages(page.getOffset(), page.getLimit());
    }

    public int findMessageRows(Integer status) {
        return messageMapper.selectMessageRows(status);
    }

    public int addMessage(Message message) {
        return messageMapper.insertMessage(message);
    }

    public int updateMessageStatus(Integer status, int id) {
        return messageMapper.updateMessageStatus(status, id);
    }
}
