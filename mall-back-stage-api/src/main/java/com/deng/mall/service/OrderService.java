package com.deng.mall.service;

import com.deng.mall.domain.BoOrder;

import java.util.List;

public interface OrderService {
    public List<BoOrder> getBoOrderList(Integer pageSize, Long pageNum);
}
