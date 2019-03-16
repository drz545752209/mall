package com.deng.mall.dao;

import com.deng.mall.domain.OrderDetail;
import com.deng.mall.domain.OrderDetailExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * OrderDetailDAO继承基类
 */
@Mapper
public interface OrderDetailDAO extends MyBatisBaseDao<OrderDetail, Integer, OrderDetailExample> {
}