package com.xin.myblog.controller.admin;

import com.alibaba.fastjson.JSON;
import com.xin.myblog.annotation.LoginRequired;
import com.xin.myblog.exception.extendedException.InternalServerErrorException;
import com.xin.myblog.exception.extendedException.RepeatException;
import com.xin.myblog.pojo.Page;
import com.xin.myblog.pojo.Result;
import com.xin.myblog.pojo.Type;
import com.xin.myblog.service.TypeService;
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
public class TypeController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TypeService typeService;

    /**
     * 根据page获取types的信息
     * @param page page信息
     * @return types信息
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(path = "/types", method = RequestMethod.POST)
    public String types(@RequestBody Page page) {
        page.setRows(typeService.findTypeRows());
        List<Map<String, Object>> types = new ArrayList<>();
        List<Type> list = typeService.listTypeByPage(page);
        for (Type type : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", type.getId());
            map.put("name", type.getName());
            types.add(map);
        }
        return JSON.toJSONString(Result.success().data("types", types).data("page", page));
    }

    /**
     * 接受type的id和name，执行插入或更新type操作
     * @param receivedData type的id和name
     * @return 是否成功插入或更新type
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(path = "/types/input", method = RequestMethod.POST)
    public String input(@RequestBody Map<String, Object> receivedData) {
        Type type = new Type();
        type.setId((Integer)receivedData.get("id"));
        type.setName((String)receivedData.get("name"));
        Type type_same = typeService.getTypeByName(type.getName());
        if (type_same != null) {
            throw new RepeatException(null);
        }
        int n = 0;
        //新增
        if (type.getId() == -1) {
            n = typeService.saveType(type);
        }else{
            //更新
            n = typeService.updateType(type.getId(), type);
        }
        if (n == 0) {
            throw new InternalServerErrorException(null);
        }else {
            return JSON.toJSONString(Result.success());
        }
    }

    /**
     * 根据type的id删除type
     * @param id type的id
     * @return 是否成功删除type
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(path = "/types/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("id") Integer id, @RequestParam("pageCurrent") int pageCurrent) {
        int n = typeService.deleteType(id);
        if (n == 0) {
            throw new InternalServerErrorException(null);
        }else {
            Page page = new Page();
            page.setCurrent(pageCurrent);
            //查询删除后的总页数，如果比之前的current_str小1的话，那么current_str就减1
            int rows = typeService.findTypeRows();
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
