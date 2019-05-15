package com.deng.mall.controller;

import com.deng.common.utils.PageFucker;
import com.deng.logistics.service.LogisticsAdminService;
import com.deng.mall.domain.BoOrder;
import com.deng.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    LogisticsAdminService logisticsAdminService;

    @RequestMapping(value = "orderList")
    public ModelAndView getOrderList(@RequestParam(required = false, defaultValue = "1") Long offset,
                                     @RequestParam(required = false, defaultValue = "10") Integer limit,
                                     HttpServletRequest request
    ) {
        List<BoOrder> orderList;
        ModelAndView mav = new ModelAndView();
        orderList = orderService.getBoOrderList(limit, offset-1,request);
        Integer dateNum=orderService.getBoOrderCount(request);
        List<String> companyList=logisticsAdminService.getCompanyNames();

        PageFucker pageInfo=new PageFucker(limit,offset,dateNum);
        pageInfo.computePage();

        mav.setViewName("bizorder.html");
        mav.addObject("companyList",companyList);
        mav.addObject("orderList", orderList);
        mav.addObject("pageInfo",pageInfo);

        return mav;
    }

    @RequestMapping(value = "sendGoods")
    public ModelAndView sendGoods(Integer orderId, String company){
        ModelAndView mav = new ModelAndView();

        orderService.sendGoods(orderId,company);
        mav.setViewName("bizorder.html");

        return null;
    }

    @RequestMapping(value = "backGoods")
    public ModelAndView backGoods(Integer orderId){
        ModelAndView mav = new ModelAndView();

        orderService.backGoods(orderId);
        mav.setViewName("bizorder.html");

        return null;
    }

}
