package com.deng.logistics.controller;

import com.deng.logistics.domain.LogisticsBo;
import com.deng.logistics.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/logistics")
public class LogisticsManagerController {
    @Autowired
    LogisticsService logisticsService;

    @RequestMapping("/toLogisticsAdmin")
    public String toIndex(){
        return "index";
    }

    @RequestMapping("/toLogistics")
    public ModelAndView toLogistics(HttpServletRequest req){
        ModelAndView mav=new ModelAndView();

        HttpSession session = req.getSession();
        String userName= (String) session.getAttribute("logisticsName");
        if(!StringUtils.isEmpty(userName)){
           List<LogisticsBo> logisticsBos=logisticsService.getLogisticsList(userName);
           mav.addObject("logisticsBos",logisticsBos);
           mav.setViewName("logistics");
           return mav;
        }

        mav.setViewName("login");
        return mav;
    }


}
