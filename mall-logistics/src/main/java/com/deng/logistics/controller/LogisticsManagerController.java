package com.deng.logistics.controller;

import com.deng.logistics.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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
    public String toLogistics(HttpServletRequest req){


        return "logistics";
    }


}
