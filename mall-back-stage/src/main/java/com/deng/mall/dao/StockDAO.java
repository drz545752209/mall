package com.deng.mall.dao;

import com.deng.mall.domain.Stock;
import com.deng.mall.domain.StockExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * StockDAO继承基类
 */
@Mapper
public interface StockDAO extends MyBatisBaseDao<Stock, Integer, StockExample> {
}