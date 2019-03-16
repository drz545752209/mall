package com.deng.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/bizManage")
public class BizManageController {

    @RequestMapping(value = "/index")
    public String toManageIndex() {
        return "bizmanager.html";
    }



}
