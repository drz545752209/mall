package com.deng.mall.dao;

import com.deng.mall.domain.Order;
import com.deng.mall.domain.OrderExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * OrderDAO继承基类
 */
@Mapper
public interface OrderDAO extends MyBatisBaseDao<Order, Integer, OrderExample> {
}