package com.xin.myblog.controller.admin;

import com.xin.myblog.annotation.LoginRequired;
import com.xin.myblog.pojo.Page;
import com.xin.myblog.pojo.Tag;
import com.xin.myblog.service.TagService;
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
public class TagController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TagService tagService;

    @LoginRequired
    @RequestMapping(path = "/tags", method = RequestMethod.GET)
    public String tags(Model model, Page page, HttpServletRequest request) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("page");
        if (current_str != null) {
            page.setCurrent(Integer.parseInt(current_str));
        }else {
            page.setCurrent(1);
        }
        page.setRows(tagService.findTagRows());
        List<Map<String, Object>> tags = new ArrayList<>();
        List<Tag> list = tagService.listTagByPage(page);
        for (Tag tag : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", tag.getId());
            map.put("name", tag.getName());
            tags.add(map);
        }
        model.addAttribute("tags", tags);
        return "admin/tags";
    }

    @LoginRequired
    @RequestMapping(path = "/tags/input", method = RequestMethod.GET)
    public String input(Page page, HttpServletRequest request) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("page");
        if (current_str != null) {
            page.setCurrent(Integer.parseInt(current_str));
        }else {
            page.setCurrent(1);
        }
        return "admin/tags-input";
    }

    @LoginRequired
    //单个编辑时
    @RequestMapping(path = "/tags/{id}/input", method = RequestMethod.GET)
    public String editInput(@PathVariable("id") Long id, Model model, Page page, HttpServletRequest request) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("page");
        if (current_str != null) {
            page.setCurrent(Integer.parseInt(current_str));
        }else {
            page.setCurrent(1);
        }
        model.addAttribute("tag", tagService.getTagById(id));
        return "admin/tags-input";
    }

    @LoginRequired
    @RequestMapping(path = "/tags", method = RequestMethod.POST)
    public String post(Tag tag, RedirectAttributes attributes, Page page) {
        Tag tag_same = tagService.getTagByName(tag.getName());
        if (tag_same != null) {
            attributes.addFlashAttribute("message", "无法添加重复的分类");
            return "redirect:/admin/tags";
        }
        int n = tagService.saveTag(tag);
        if (n == 0) {
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        //因为是新增，所以直接跳转到最后一页即可
        page.setRows(tagService.findTagRows());
        int total = page.getTotal();
        return "redirect:/admin/tags?page=" + total;
    }

    @LoginRequired
    //单个编辑的提交
    @RequestMapping(path = "/tags/{id}", method = RequestMethod.POST)
    public String editpost(@PathVariable("id") Long id , Tag tag, RedirectAttributes attributes, HttpServletRequest request) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("page");
        if (current_str != null) {
        }else {
            current_str = "1";
        }
        Tag tag_same = tagService.getTagByName(tag.getName());
        if (tag_same != null) {
            attributes.addFlashAttribute("message", "无法添加重复的分类");
            return "redirect:/admin/tags?page=" + current_str;
        }
        int n = tagService.updateTag(id, tag);
        if (n == 0) {
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/tags?page=" + current_str;
    }

    @LoginRequired
    @RequestMapping(path = "/tags/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes, HttpServletRequest request, Page page) {
        //获取到请求参数page表示第几页，如果为空就默认第一页
        String current_str = request.getParameter("page");
        if (current_str != null) {
        }else {
            current_str = "1";
        }
        int n = tagService.deleteTag(id);
        if (n == 0) {
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        //查询删除后的总页数，如果比之前的current_str小1的话，那么current_str就减1
        int rows = tagService.findTagRows();
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
        return "redirect:/admin/tags?page=" + current_str;
    }
}