package com.deng.mall.service.impl;

import com.deng.mall.dao.PromotionDAO;
import com.deng.mall.domain.Product;
import com.deng.mall.domain.Promotion;
import com.deng.mall.domain.PromotionExample;
import com.deng.mall.service.ProductService;
import com.deng.mall.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("promotionService")
public class PromotionServiceImpl  implements PromotionService {
    @Autowired
    PromotionDAO promotionDAO;

    @Override
    public List<Promotion> getPromotionByProductId(List<Product> products) {
        List<Integer> productIds=new ArrayList<>();
        for (Product product:products){
            productIds.add(product.getId());
        }

        PromotionExample promotionExample=new PromotionExample();
        PromotionExample.Criteria promotionExampleCriteria=promotionExample.createCriteria();
        promotionExampleCriteria.andProductIdIn(productIds);
        List<Promotion> promotions=promotionDAO.selectByExample(promotionExample);

        return promotions;
    }

    @Override
    public HashMap<Integer,Integer> getPromotionDiscount(List<Promotion> promotions,List<Product> products){
        HashMap<Integer,Integer> promotionMap=new HashMap<>();
        for (Product product:products){
            promotionMap.put(product.getId(),0);
        }
        for (Promotion promotion:promotions){
            promotionMap.put(promotion.getProductId(),promotion.getDicount());
        }
        return promotionMap;
    }
}
