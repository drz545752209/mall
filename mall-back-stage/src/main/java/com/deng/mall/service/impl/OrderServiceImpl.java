package com.deng.mall.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.deng.common.utils.StrUntils;
import com.deng.logistics.domain.Logistics;
import com.deng.logistics.service.LogisticsService;
import com.deng.mall.dao.BizDAO;
import com.deng.mall.dao.OrderDAO;
import com.deng.mall.dao.OrderDetailDAO;
import com.deng.mall.dao.StoreDAO;
import com.deng.mall.domain.*;
import com.deng.mall.mq.SendMsg;
import com.deng.mall.service.OrderService;
import com.deng.mall.service.ProductService;
import com.deng.mall.service.StockService;
import com.hlju.mall.domain.User;
import com.hlju.mall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
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
    @Autowired(required = false)
    UserService userService;
    @Autowired(required = false)
    LogisticsService logisticsService;
    @Autowired
    StockService stockService;

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

    /**
     * 商家查询处理订单
     * @param pageSize
     * @param pageNum
     * @param request
     * @return
     */
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


    /**用户查询订单
     * @param pageSize
     * @param pageNum
     * @param userName
     * @return
     */
    public  List<UserBoOrder> getQueryOrder(Integer pageSize, Long pageNum,String userName){
        List<UserBoOrder> boOrderList;
        Product product;
        User user=new User();
        user.setName(userName);
        Integer userId=userService.selectUserNameByExamle(user).get(0).getId();

        List<UserBoOrder> userBoOrders=orderDAO.selectUserBoOrder(pageSize, pageNum*pageSize,userId);

        return userBoOrders;
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

    private Logistics getLogistics(String userName,Integer orderDetailId,Integer storeId){
        Logistics logistics=new Logistics();
        logistics.setOrderDetailId(orderDetailId);
        logistics.setStatus("待发货");

        User user=new User();
        user.setName(userName);
        Integer userId=userService.selectUserNameByExamle(user).get(0).getId();
        logistics.setUserId(userId);

        Store store=storeDAO.selectByPrimaryKey(storeId);
        Integer bizId=store.getBizId();
        logistics.setBizId(bizId);

        return logistics;
    }

    @Override
    @Transactional
    public boolean createOrder(String shopInfo,String userName) {
        List<HashMap> productIds=StrUntils.json2MapList(shopInfo);

        for (HashMap resultMap:productIds){
            try{
            String productId=resultMap.get("productId").toString();
            Integer buyNum= (Integer) resultMap.get("buyNum");
            //抹除小数
            BigDecimal var1= (BigDecimal) resultMap.get("newPrice");
            Integer totalPrice=var1.intValue()*buyNum;

            Product product=productService.getProductById(productId);
            String StoreName=product.getStoreName();

            StoreExample storeExample=new StoreExample();
            StoreExample.Criteria storeCriteria=storeExample.createCriteria();
            storeCriteria.andNameEqualTo(StoreName);
            List<Store> stores=storeDAO.selectByExample(storeExample);
            Integer storeId=stores.get(0).getId();

            User user=new User();
            user.setName(userName);
            Integer userId=userService.selectUserNameByExamle(user).get(0).getId();
            Order order=new Order();
            order.setUserId(userId);
            order.setStoreId(storeId);

            OrderDetailExample orderDetailExample=new OrderDetailExample();
            Long orderDetailId=orderDetailDAO.countByExample(orderDetailExample);
            order.setDetailId(orderDetailId.intValue()+1);

            OrderDetail orderDetail=new OrderDetail();
            orderDetail.setId(orderDetailId.intValue()+1);
            orderDetail.setProductId(product.getId());
            orderDetail.setStatus("待发货");
            orderDetail.setCountConsume((long)buyNum);
            orderDetail.setSumConsume((long)totalPrice);

            orderDAO.insertSelective(order);
            orderDetailDAO.insertSelective(orderDetail);

            Logistics logistics=getLogistics(userName,orderDetailId.intValue()+1,storeId);
            logisticsService.insertLogistice(logistics);

            //消费库存
            Stock stock=stockService.getStockByProductId(product);
            Long updateStockNum=stock.getCount()-buyNum;
            stock.setCount(updateStockNum);
            stock.setOutDate(StrUntils.getNow());
            stockService.saveStock(stock);

            }catch (Exception e){
                e.printStackTrace();
                //手动处理异常后aop没有办法捕获异常，手动处理回滚事务
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }
        return true;
    }

    @Override
    public Integer getStoreIdByOrderId(Integer orderId) {
        Order order=orderDAO.selectByPrimaryKey(orderId);
        return order.getStoreId();
    }

    @Override
    public Integer getOrderDetailIdById(Integer orderId) {
        Order order=orderDAO.selectByPrimaryKey(orderId);
        return order.getDetailId();
    }

    @Override
    public Order getOrderById(Integer orderId) {
         Order order=orderDAO.selectByPrimaryKey(orderId);
        return order;
    }


}
