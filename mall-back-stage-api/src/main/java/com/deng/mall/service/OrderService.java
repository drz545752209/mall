package com.deng.mall.service;

import com.deng.mall.domain.BoOrder;
import com.deng.mall.domain.UserBoOrder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
     List<BoOrder> getBoOrderList(Integer limit, Long offset, HttpServletRequest request);
     List<UserBoOrder> getQueryOrder(Integer pageSize, Long pageNum, String userName);
     void sendGoods(Integer orderId);
     void backGoods(Integer orderId);
     boolean createOrder(String shopInfo,String userName);
}
