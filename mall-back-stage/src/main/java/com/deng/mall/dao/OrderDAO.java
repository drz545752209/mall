package com.deng.mall.dao;

import com.deng.mall.domain.BoOrder;
import com.deng.mall.domain.Order;
import com.deng.mall.domain.OrderExample;
import com.deng.mall.domain.UserBoOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * OrderDAO继承基类
 */
@Mapper
public interface OrderDAO extends MyBatisBaseDao<Order, Integer, OrderExample> {
    List<BoOrder> selectOrderBo(@Param("limit") Integer limit, @Param("offset") Long offset,@Param("storeId")Integer storeId);

    List<UserBoOrder> selectUserBoOrder(@Param("limit") Integer limit, @Param("offset") Long offset, @Param("userId")Integer userId);
}