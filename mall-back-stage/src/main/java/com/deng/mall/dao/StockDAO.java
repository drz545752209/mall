package com.deng.mall.dao;

import com.deng.mall.domain.BoStock;
import com.deng.mall.domain.Stock;
import com.deng.mall.domain.StockExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * StockDAO继承基类
 */
@Mapper
public interface StockDAO extends MyBatisBaseDao<Stock, Integer, StockExample> {
    List<BoStock> selectStockList(@Param("limit") Integer limit, @Param("offset") Long offset,
                                  @Param("stockIds")List<Integer> stockIds);

    Integer selectStockCount(@Param("stockIds")List<Integer> stockIds);
}