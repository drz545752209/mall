package com.deng.mall.service;

import com.deng.mall.domain.BoOrder;

import java.util.List;

public interface OrderService {
     List<BoOrder> getBoOrderList(Integer limit, Long offset);
     void sendGoods(Integer orderId);
     void backGoods(Integer orderId);
}
