package com.xin.myblog.controller.admin;

import com.xin.myblog.annotation.LoginRequired;
import com.xin.myblog.pojo.Page;
import com.xin.myblog.pojo.Type;
import com.xin.myblog.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @LoginRequired
    @RequestMapping(path = "/types", method = RequestMethod.GET)
    public String types(Model model, Page page, HttpServletRequest request) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("page");
        if (current_str != null) {
            page.setCurrent(Integer.parseInt(current_str));
        }else {
            page.setCurrent(1);
        }
        page.setRows(typeService.findTypeRows());
        List<Map<String, Object>> types = new ArrayList<>();
        List<Type> list = typeService.listTypeByPage(page);
        for (Type type : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", type.getId());
            map.put("name", type.getName());
            types.add(map);
        }
        model.addAttribute("types", types);
        return "admin/types";
    }

    @LoginRequired
    @RequestMapping(path = "/types/input", method = RequestMethod.GET)
    public String input(Model model, Page page, HttpServletRequest request) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("page");
        if (current_str != null) {
            page.setCurrent(Integer.parseInt(current_str));
        }else {
            page.setCurrent(1);
        }
        return "admin/types-input";
    }

    @LoginRequired
    @RequestMapping(path = "/types/{id}/input", method = RequestMethod.GET)
    public String editInput(@PathVariable("id") Integer id, Model model, Page page, HttpServletRequest request) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("page");
        if (current_str != null) {
            page.setCurrent(Integer.parseInt(current_str));
        }else {
            page.setCurrent(1);
        }
        model.addAttribute("type", typeService.getTypeById(id));
        return "admin/types-input";
    }

    @LoginRequired
    @RequestMapping(path = "/types", method = RequestMethod.POST)
    public String post(Type type, RedirectAttributes attributes, Page page) {
        Type type_same = typeService.getTypeByName(type.getName());
        if (type_same != null) {
            attributes.addFlashAttribute("message", "无法添加重复的分类");
            return "redirect:/admin/types";
        }
        int n = typeService.saveType(type);
        if (n == 0) {
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        //因为是新增，所以直接跳转到最后一页即可
        page.setRows(typeService.findTypeRows());
        int total = page.getTotal();
        return "redirect:/admin/types?page=" + total;
    }

    @LoginRequired
    @RequestMapping(path = "/types/{id}", method = RequestMethod.POST)
    public String editpost(@PathVariable("id") Integer id , Type type, RedirectAttributes attributes, HttpServletRequest request) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("page");
        if (current_str != null) {
        }else {
            current_str = "1";
        }
        Type type_same = typeService.getTypeByName(type.getName());
        if (type_same != null) {
            attributes.addFlashAttribute("message", "无法添加重复的分类");
            return "redirect:/admin/types?page=" + current_str;
        }
        int n = typeService.updateType(id, type);
        if (n == 0) {
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/types?page=" + current_str;
    }

    @LoginRequired
    @RequestMapping("/types/{id}/delete")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes, HttpServletRequest request, Page page) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("page");
        if (current_str != null) {
        }else {
            current_str = "1";
        }
        int n = typeService.deleteType(id);
        if (n == 0) {
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        //查询删除后的总页数，如果比之前的current_str小1的话，那么current_str就减1
        int rows = typeService.findTypeRows();
        int limit = page.getLimit();
        int total = 0;
        if (rows % limit == 0) {
            total =  rows / limit;
        }else {
            total =  rows / limit + 1;
        }
        if (total == Integer.parseInt(current_str) - 1) {
            current_str = String.valueOf(total);
        }
        return "redirect:/admin/types?page=" + current_str;
    }
}
