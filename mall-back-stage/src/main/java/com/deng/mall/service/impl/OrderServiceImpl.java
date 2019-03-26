package com.deng.mall.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.deng.mall.dao.OrderDAO;
import com.deng.mall.dao.OrderDetailDAO;
import com.deng.mall.domain.*;
import com.deng.mall.mq.SendMsg;
import com.deng.mall.service.OrderService;
import com.deng.mall.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    ProductService productService;
    @Autowired
    OrderDetailDAO orderDetailDAO;
    @Autowired
    DefaultMQProducer defaultMQProducer;

    final static Logger LOGGER= LoggerFactory.getLogger(OrderServiceImpl.class);


    public List<BoOrder> getBoOrderList(Integer limit, Long offset) {
        List<BoOrder> boOrderList;
        Product product;
        boOrderList = orderDAO.selectOrderBo(limit, offset);
        for (BoOrder boOrder : boOrderList) {
            product = productService.getProductById(boOrder.getProductId().toString());
            boOrder.setProductName(product.getName());
        }
        return boOrderList;
    }

    @Override
    @Transactional
    public void sendGoods(Integer orderId) {
        Order order;
        OrderDetail orderDetail;
        HashMap<String,Object> jsonMap=new HashMap<>();
        jsonMap.put("orderId",orderId);
        jsonMap.put("status","发货");
        String orderMsg=JSONObject.toJSONString(jsonMap);
        if (orderId != null && !"".equals(orderId)) {
            order=orderDAO.selectByPrimaryKey(orderId);
            orderDetail=orderDetailDAO.selectByPrimaryKey(order.getDetailId());
            SendMsg sendMsg=new SendMsg();
            sendMsg.sendMsg(orderMsg,"sendGoods",defaultMQProducer);
            orderDetail.setStatus("等待发货");
            orderDetailDAO.updateByPrimaryKey(orderDetail);
        }
    }

    @Override
    public void backGoods(Integer orderId) {

    }


}
