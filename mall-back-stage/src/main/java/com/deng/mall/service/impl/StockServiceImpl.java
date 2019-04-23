package com.deng.mall.service.impl;

import com.deng.common.utils.StrUntils;
import com.deng.mall.dao.BizDAO;
import com.deng.mall.dao.ProductDAO;
import com.deng.mall.dao.StockDAO;
import com.deng.mall.dao.StoreDAO;
import com.deng.mall.domain.*;
import com.deng.mall.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service("stockService")
public class StockServiceImpl implements StockService {
    @Autowired
    StockDAO stockDAO;
    @Autowired
    BizDAO bizDAO;
    @Autowired
    StoreDAO storeDAO;
    @Autowired
    ProductDAO productDAO;


    private List<Integer> getStockIdByBiz(Integer pageSize, Long pageNum,String bizName){
        //获取bizid
        BizExample bizExample=new BizExample();
        BizExample.Criteria bizExampleCriteria=bizExample.createCriteria();
        bizExampleCriteria.andNameEqualTo(bizName);
        List<Biz> bizs=bizDAO.selectByExample(bizExample);
        Integer bizId=bizs.get(0).getId();
        //获取店铺id
        StoreExample storeExample=new StoreExample();
        StoreExample.Criteria storeExampleCriteria=storeExample.createCriteria();
        storeExampleCriteria.andBizIdEqualTo(bizId);
        List<Store> stores=storeDAO.selectByExample(storeExample);
        String storeName=stores.get(0).getName();
        //获取商品id
        ProductExample productExample=new ProductExample();
        ProductExample.Criteria productExampleCriteria=productExample.createCriteria();
        productExampleCriteria.andStoreNameEqualTo(storeName);
        productExample.setLimit(pageSize);
        productExample.setOffset(pageSize*pageNum);
        List<Product> products=productDAO.selectByExample(productExample);
        List<Integer> productIds=new ArrayList<>();
        for (Product product:products){
            productIds.add(product.getId());
        }
        //获取库存id
        StockExample stockExample=new StockExample();
        StockExample.Criteria stockExampleCriteria=stockExample.createCriteria();
        stockExampleCriteria.andProductIdIn(productIds);
        List<Stock> stocks=stockDAO.selectByExample(stockExample);
        List<Integer> stockIds=new ArrayList<>();
        for (Stock stock:stocks){
            stockIds.add( stock.getId());
        }
        return stockIds;
    }

    @Override
    public List<BoStock> getStockList(Integer pageSize, Long pageNum, HttpServletRequest request) {
        List<BoStock> boStockList;
        HttpSession httpSession=request.getSession();
        String bizName= (String) httpSession.getAttribute("loginName");
        List<Integer> stockIds=getStockIdByBiz(pageSize,pageSize*pageNum,bizName);
        boStockList=stockDAO.selectStockList(pageSize,pageSize*pageNum,stockIds);
        return boStockList;
    }

    @Override
    public Stock getStockByProductId(Product product) {
        if (product!=null){
            StockExample stockExample=new StockExample();
            StockExample.Criteria criteria=stockExample.createCriteria();
            criteria.andProductIdEqualTo(product.getId());
            List<Stock> stocks=stockDAO.selectByExample(stockExample);
            if (stocks.size()!=0){
                return stocks.get(0);
            }else {
                return null;
            }

        }
        return null;
    }

    @Override
    public void saveStock(Integer id,String count, String inDate, String outDate) {
        Stock stock=new Stock();
        stock.setId(id);
        stock.setCount(Long.valueOf(count));
        stock.setInDate(StrUntils.str2Date(inDate));
        stock.setOutDate(StrUntils.str2Date(outDate));
        stockDAO.updateByPrimaryKeySelective(stock);
    }

    @Override
    public boolean saveStock(Stock stock) {
        if (stock!=null){
            stockDAO.updateByPrimaryKeySelective(stock);
            return true;
        }
        return  false;
    }
}
