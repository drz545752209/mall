package com.deng.mall.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.deng.mall.dao.BizDAO;
import com.deng.mall.dao.OrderDAO;
import com.deng.mall.dao.OrderDetailDAO;
import com.deng.mall.dao.StoreDAO;
import com.deng.mall.domain.*;
import com.deng.mall.mq.SendMsg;
import com.deng.mall.service.OrderService;
import com.deng.mall.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @Autowired
    BizDAO bizDAO;
    @Autowired
    StoreDAO storeDAO;

    final static Logger LOGGER= LoggerFactory.getLogger(OrderServiceImpl.class);


    private Integer getStoreIdByBiz(String bizName){
        //获取bizid
        BizExample bizExample=new BizExample();
        BizExample.Criteria bizExampleCriteria=bizExample.createCriteria();
        bizExampleCriteria.andNameEqualTo(bizName);
        List<Biz> bizs=bizDAO.selectByExample(bizExample);
        Integer bizId=bizs.get(0).getId();
        //获取
        StoreExample storeExample=new StoreExample();
        StoreExample.Criteria storeExampleCriteria=storeExample.createCriteria();
        storeExampleCriteria.andBizIdEqualTo(bizId);
        List<Store> stores=storeDAO.selectByExample(storeExample);
        Integer storeId=stores.get(0).getId();

        return storeId;
    }

    public List<BoOrder> getBoOrderList(Integer pageSize, Long pageNum, HttpServletRequest request) {
        List<BoOrder> boOrderList;
        Product product;
        HttpSession session=request.getSession();
        String bizName= (String) session.getAttribute("loginName");
        Integer storeId=getStoreIdByBiz(bizName);
        boOrderList = orderDAO.selectOrderBo(pageSize, pageNum*pageSize,storeId);
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
