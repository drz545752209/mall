package com.deng.mall.service;

import com.deng.mall.domain.BoStock;

import java.util.List;

public interface StockService {
   List<BoStock> getStockList(Integer limit, Long offset);
   void saveStock(Integer id,Long count, String inDate,String outDate);
}
