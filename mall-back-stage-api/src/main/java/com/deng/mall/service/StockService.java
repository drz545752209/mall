package com.deng.mall.service;

import com.deng.mall.domain.BoStock;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface StockService {
   List<BoStock> getStockList(Integer limit, Long offset, HttpServletRequest request);
   void saveStock(Integer id,String count, String inDate,String outDate);
}
