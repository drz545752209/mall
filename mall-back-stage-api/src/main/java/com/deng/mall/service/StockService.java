package com.deng.mall.service;

import com.deng.mall.domain.BoStock;
import com.deng.mall.domain.Product;
import com.deng.mall.domain.Stock;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface StockService {
   List<BoStock> getStockList(Integer limit, Long offset, HttpServletRequest request);

   Stock getStockByProductId(Product product);

   void saveStock(Integer id,String count, String inDate,String outDate);

   boolean saveStock(Stock stock);
}
