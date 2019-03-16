package com.deng.mall.controller;

import com.deng.mall.domain.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "order")
public class OrderController {

    @RequestMapping(value = "orderList")
    public String getOrderList(@RequestParam(required = false,defaultValue = "0")Long pageNum,
                               @RequestParam(required = false,defaultValue = "10")Integer pageSize
                               ){
             List<Order> orderList;
          return  null;
    }

}
