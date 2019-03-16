package com.deng.mall.service.impl;


import com.deng.mall.dao.OrderDAO;
import com.deng.mall.dao.OrderDetailDAO;
import com.deng.mall.domain.*;
import com.deng.mall.service.OrderService;
import com.hlju.mall.domain.User;
import com.hlju.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    OrderDetailDAO orderDetailDAO;
    @Autowired
    ProductServiceImpl productServiceImpl;
    @Autowired
    UserService userService;


    public List<BoOrder> getBoOrderList(Integer pageSize, Long pageNum) {
        List<Order> orderList;
        List<OrderDetail> orderDetailList;
        List<Product> productList;
        List<User> userList;
        List<BoOrder> boOrderList;
        List<Integer> orderIds=new ArrayList<>();
        List<Integer> userIds=new ArrayList<>();
        List<Integer> productIds=new ArrayList<>();


        //查订单
        OrderExample orderExample=new OrderExample();
        orderExample.setOffset(pageNum);
        orderExample.setLimit(pageSize);
        orderList=orderDAO.selectByExample(orderExample);
        for (Order order:orderList){
            orderIds.add(order.getId());
            userIds.add(order.getUserId());
        }

        //查订单详情
        OrderDetailExample orderDetailExample=new OrderDetailExample();
        OrderDetailExample.Criteria criteria1=orderDetailExample.createCriteria();
        criteria1.andIdIn(orderIds);
        orderDetailExample.or(criteria1);
        orderDetailList=orderDetailDAO.selectByExample(orderDetailExample);
        for (OrderDetail orderDetail:orderDetailList){
            productIds.add(orderDetail.getProductId());
        }

        //查产品
        productList= productServiceImpl.getProductListById(productIds);
        //查客户
        userList=userService.selectUserListByIds(userIds);



        return null;
    }



}
