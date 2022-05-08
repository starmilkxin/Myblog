package com.xin.myblog.controller.admin;

import com.alibaba.fastjson.JSON;
import com.xin.myblog.annotation.LoginRequired;
import com.xin.myblog.exception.extendedException.InternalServerErrorException;
import com.xin.myblog.exception.extendedException.RepeatException;
import com.xin.myblog.pojo.Page;
import com.xin.myblog.pojo.Result;
import com.xin.myblog.pojo.Tag;
import com.xin.myblog.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/admin")
public class TagController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TagService tagService;

    /**
     * 根据page获取tags的信息
     * @param page page信息
     * @return tags信息
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(path = "/tags", method = RequestMethod.POST)
    public String tags(@RequestBody Page page) {
        page.setRows(tagService.findTagRows());
        List<Map<String, Object>> tags = new ArrayList<>();
        List<Tag> list = tagService.listTagByPage(page);
        for (Tag tag : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", tag.getId());
            map.put("name", tag.getName());
            tags.add(map);
        }
        return JSON.toJSONString(Result.success().data("tags", tags).data("page", page));
    }

    /**
     * 接受tag的id和name，执行插入或更新tag操作
     * @param receivedData tag的id和name
     * @return 是否成功插入或更新tag
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(path = "/tags/input", method = RequestMethod.POST)
    public String input(@RequestBody Map<String, Object> receivedData) {
        Tag tag = new Tag();
        tag.setId(((Integer)receivedData.get("id")).longValue());
        tag.setName((String)receivedData.get("name"));
        Tag tag_same = tagService.getTagByName(tag.getName());
        if (tag_same != null) {
            throw new RepeatException(null);
        }
        int n = 0;
        //新增
        if (tag.getId() == -1) {
            n = tagService.saveTag(tag);
        }else{
            //更新
            n = tagService.updateTag(tag.getId(), tag);
        }
        if (n == 0) {
            throw new InternalServerErrorException(null);
        }else {
            return JSON.toJSONString(Result.success());
        }
    }

    /**
     * 根据tag的id删除tag
     * @param id tag的id
     * @return 是否成功删除tag
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(path = "/tags/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") Integer id, @RequestParam("pageCurrent") int pageCurrent) {
        int n = tagService.deleteTag(id.longValue());
        if (n == 0) {
            throw new InternalServerErrorException(null);
        }else {
            Page page = new Page();
            page.setCurrent(pageCurrent);
            //查询删除后的总页数，如果比之前的current_str小1的话，那么current_str就减1
            int rows = tagService.findTagRows();
            int limit = page.getLimit();
            int total = 0;
            if (rows % limit == 0) {
                total =  rows / limit;
            }else {
                total =  rows / limit + 1;
            }
            if (total == page.getCurrent() - 1) {
                page.setCurrent(total);
            }
            return JSON.toJSONString(Result.success().data("page", page));
        }
    }
}