package com.xin.myblog.controller;

import com.xin.myblog.pojo.Blog;
import com.xin.myblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@Controller
public class ArchivesShowController {
    @Autowired
    private BlogService blogService;

    @RequestMapping(path = "/archives", method = RequestMethod.GET)
    public String archives(Model model) {
        Map<String, List<Blog>> archiveMap = blogService.listBlogPublishedWithYear();
        model.addAttribute("archiveMap", archiveMap);
        model.addAttribute("blogCount", blogService.findRowsBlogByFuzzyName_Type_Recommend_Published(null, null, null, true));
        return "archives";
    }
}
