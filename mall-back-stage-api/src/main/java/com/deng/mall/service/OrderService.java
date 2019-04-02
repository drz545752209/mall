package com.deng.mall.service;

import com.deng.mall.domain.BoOrder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
     List<BoOrder> getBoOrderList(Integer limit, Long offset, HttpServletRequest request);
     void sendGoods(Integer orderId);
     void backGoods(Integer orderId);
}
