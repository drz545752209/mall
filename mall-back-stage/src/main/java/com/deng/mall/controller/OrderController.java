package com.deng.mall.controller;

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

    @RequestMapping(value = "orderList")
    public ModelAndView getOrderList(@RequestParam(required = false, defaultValue = "1") Long offset,
                                     @RequestParam(required = false, defaultValue = "10") Integer limit,
                                     HttpServletRequest request
    ) {
        List<BoOrder> orderList;
        ModelAndView mav = new ModelAndView();
        orderList = orderService.getBoOrderList(limit, offset-1,request);
        mav.setViewName("bizorder.html");
        mav.addObject("orderList", orderList);
        return mav;
    }

    @RequestMapping(value = "sendGoods")
    public ModelAndView sendGoods(Integer orderId,
                                  @RequestParam(required = false, defaultValue = "1") Long offset,
                                  @RequestParam(required = false, defaultValue = "10") Integer limit,
                                  HttpServletRequest request){
        List<BoOrder> orderList;
        ModelAndView mav = new ModelAndView();

        orderList = orderService.getBoOrderList(limit, offset-1,request);
        orderService.sendGoods(orderId);
        mav.setViewName("bizorder.html");
        mav.addObject("orderList", orderList);
        return null;
    }

}
