package com.deng.mall.service.impl;

import com.deng.mall.dao.StockDAO;
import com.deng.mall.domain.BoStock;
import com.deng.mall.domain.Stock;
import com.deng.mall.service.StockService;
import com.deng.mall.utils.StrUntils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("stockService")
public class StockServiceImpl implements StockService {
    @Autowired
    StockDAO stockDAO;

    @Override
    public List<BoStock> getStockList(Integer limit, Long offset) {
        List<BoStock> boStockList;
        boStockList=stockDAO.selectStockList(limit,offset);
        return boStockList;
    }

    @Override
    public void saveStock(Integer id,Long count, String inDate, String outDate) {
        Stock stock=new Stock();
        stock.setId(id);
        stock.setCount(count);
        stock.setInDate(StrUntils.str2Date(inDate));
        stock.setOutDate(StrUntils.str2Date(outDate));
        stockDAO.updateByPrimaryKeySelective(stock);
    }
}
