package com.deng.mall.controller;

import com.hlju.mall.domain.BoComment;
import com.hlju.mall.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ModelAndView scanComment(HttpServletRequest req) {
        ModelAndView mav=new ModelAndView();

        HttpSession session = req.getSession();
        String bizName = (String) session.getAttribute("loginName");
        List<BoComment> boCommentList=commentService.getCommentBoList(bizName);

        mav.addObject("commmentBoList",boCommentList);
        mav.setViewName("bizcomment");

        return mav;
    }
}
