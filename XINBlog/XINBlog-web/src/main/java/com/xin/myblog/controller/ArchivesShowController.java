package com.xin.myblog.controller;

import com.alibaba.fastjson.JSON;
import com.xin.myblog.pojo.Blog;
import com.xin.myblog.pojo.Result;
import com.xin.myblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/x")
public class ArchivesShowController {
    @Autowired
    private BlogService blogService;

    /**
     * 查询时间轴
     * @return 返回List，元素为某年的时间和所有博客
     */
    @ResponseBody
    @RequestMapping(path = "/archives", method = RequestMethod.GET)
    public String archives() {
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> archiveMap = blogService.listBlogPublishedWithYear();
        data.put("archiveMap", archiveMap);
        data.put("blogCount", blogService.findRowsBlogByFuzzyName_Type_Recommend_Published(null, null, null, true));
        return JSON.toJSONString(Result.success().data(data));
    }
}
