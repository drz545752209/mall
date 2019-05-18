package com.deng.seckill.service.impl;


import com.deng.mall.domain.Product;
import com.deng.mall.domain.Stock;
import com.deng.mall.service.OrderService;
import com.deng.mall.service.ProductService;
import com.deng.mall.service.StockService;
import com.deng.seckill.bo.GoodsBo;
import com.deng.seckill.dao.SeckillProductDAO;
import com.deng.seckill.domain.SeckillProduct;
import com.deng.seckill.domain.SeckillProductExample;
import com.deng.seckill.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Service("seckillGoodsService")
public class SeckillGoodsServiceImpl implements SeckillGoodsService {
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    @Autowired
    StockService stockService;
    @Autowired
    SeckillProductDAO seckillProductDAO;

    @Override
    public List<GoodsBo> getSeckillGoodsList() {
       List<SeckillProduct> seckillProducts=seckillProductDAO.selectByExample(new SeckillProductExample());
       List<GoodsBo> goodsBos=new ArrayList<>();

       for (SeckillProduct seckillProduct:seckillProducts){
           GoodsBo goodsBo=new GoodsBo();

           goodsBo.setEndDate(seckillProduct.getEndDate());
           goodsBo.setStartDate(seckillProduct.getStartDate());
           goodsBo.setSeckillPrice(seckillProduct.getSeckilPrice());
           goodsBo.setStockCount(seckillProduct.getStockCount());
           goodsBo.setId((long)seckillProduct.getGoodsId());

           Product product=  productService.getProductById(seckillProduct.getGoodsId().toString());
           goodsBo.setGoodsDetail(product.getDescription());
           goodsBo.setGoodsImg(product.getImg());
           goodsBo.setGoodsName(product.getName());
           goodsBo.setGoodsTitle(product.getDescription());
           goodsBo.setGoodsPrice(product.getPrice());

           Stock stock=stockService.getStockByProductId(product);
           goodsBo.setGoodsStock(stock.getId());

           goodsBos.add(goodsBo);
       }

       return goodsBos;
    }

    @Override
    public GoodsBo getseckillGoodsBoByGoodsId(Long goodsId) {
        GoodsBo goodsBo=new GoodsBo();

        SeckillProductExample seckillProductExample=new SeckillProductExample();
        SeckillProductExample.Criteria criteria=seckillProductExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId.intValue());

        List<SeckillProduct> seckillProducts=seckillProductDAO.selectByExample(seckillProductExample);
        if (seckillProducts.size()>0){
            SeckillProduct  seckillProduct =seckillProducts.get(0);
            goodsBo.setEndDate(seckillProduct.getEndDate());
            goodsBo.setStartDate(seckillProduct.getStartDate());
            goodsBo.setSeckillPrice(seckillProduct.getSeckilPrice());
            goodsBo.setStockCount(seckillProduct.getStockCount());
            goodsBo.setId((long)seckillProduct.getGoodsId());

            Product product=  productService.getProductById(seckillProduct.getGoodsId().toString());
            goodsBo.setGoodsDetail(product.getDescription());
            goodsBo.setGoodsImg(product.getImg());
            goodsBo.setGoodsName(product.getName());
            goodsBo.setGoodsTitle(product.getDescription());
            goodsBo.setGoodsPrice(product.getPrice());

            Stock stock=stockService.getStockByProductId(product);
            goodsBo.setGoodsStock(stock.getId());
        }

        return  goodsBo;
    }

    @Override
    public int reduceStock(Long goodsId) {
        SeckillProductExample seckillProductExample=new SeckillProductExample();
        SeckillProductExample.Criteria criteria=seckillProductExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId.intValue());
        SeckillProduct seckillProduct=seckillProductDAO.selectByExample(seckillProductExample).get(0);
        Integer stockCount=seckillProduct.getStockCount();

        seckillProduct.setStockCount(stockCount-1);
        int result=seckillProductDAO.updateByPrimaryKeySelective(seckillProduct);

        return  result;
    }

    @Override
    public boolean insertSeckillGoods(String productId, Integer price, Date startTime, Date endTime) {
        Product product=productService.getProductById(productId);
        Stock stock=stockService.getStockByProductId(product);

        SeckillProduct seckillProduct=new SeckillProduct();

        seckillProduct.setStartDate(startTime);
        seckillProduct.setEndDate(endTime);
        seckillProduct.setGoodsId(product.getId());
        seckillProduct.setSeckilPrice(price);
        seckillProduct.setStockCount(stock.getCount().intValue());

        boolean result= seckillProductDAO.insertSelective(seckillProduct)>0?true:false;

        return result;
    }

}
