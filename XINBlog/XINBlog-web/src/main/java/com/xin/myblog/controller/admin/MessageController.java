package com.xin.myblog.controller.admin;

import com.alibaba.fastjson.JSON;
import com.xin.myblog.annotation.LoginRequired;
import com.xin.myblog.exception.extendedException.InternalServerErrorException;
import com.xin.myblog.pojo.Message;
import com.xin.myblog.pojo.Page;
import com.xin.myblog.pojo.Result;
import com.xin.myblog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/admin")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @LoginRequired
    @ResponseBody
    @RequestMapping(path="/message", method = RequestMethod.POST)
    public String message(@RequestBody Page page) {
        List<Message> messages = messageService.listMessage(page);
        page.setRows(messageService.findMessageRows(null));
        return JSON.toJSONString(Result.success().data("messages", messages).data("page", page));
    }

    @LoginRequired
    @ResponseBody
    @RequestMapping(path="/messageUpdate", method = RequestMethod.GET)
    public String messageUpdate(@RequestParam int status, @RequestParam int id) {
        int n = messageService.updateMessageStatus(status, id);
        if (n == 0) {
            throw new InternalServerErrorException(null);
        }
        return JSON.toJSONString(Result.success());
    }

    @LoginRequired
    @ResponseBody
    @RequestMapping(path="/whetherUnreadMessage", method=RequestMethod.GET)
    public String whetherUnreadMessage() {
        int n = messageService.findMessageRows(0);
        return JSON.toJSONString(Result.success().data("n", n));
    }
}
