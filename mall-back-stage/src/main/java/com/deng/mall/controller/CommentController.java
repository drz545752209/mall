package com.deng.mall.controller;

import com.deng.common.utils.PageFucker;
import com.hlju.mall.domain.BoComment;
import com.hlju.mall.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("comment")
public class CommentController {
    @Autowired(required = false)
    CommentService commentService;

    @RequestMapping("/scanComment")
    public ModelAndView scanComment(
            @RequestParam(required = false, defaultValue = "1") Long offset,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            HttpServletRequest req
    ) {
        ModelAndView mav=new ModelAndView();

        HttpSession session = req.getSession();
        String bizName = (String) session.getAttribute("loginName");
        List<BoComment> boCommentList=commentService.getCommentBoList(bizName,limit, offset-1);
        Integer dateNum=commentService.getCommentCountByBizName(bizName);

        PageFucker pageInfo=new PageFucker(limit,offset,dateNum);
        pageInfo.computePage();

        mav.addObject("pageInfo",pageInfo);
        mav.addObject("commmentBoList",boCommentList);
        mav.setViewName("bizcomment");

        return mav;
    }
}
