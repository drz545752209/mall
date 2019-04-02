package com.deng.mall.service;

import com.deng.mall.domain.Product;
import com.deng.mall.domain.Promotion;

import java.util.HashMap;
import java.util.List;

public interface PromotionService {
    List<Promotion> getPromotionByProductId(List<Product> products);

    /**
     * @param promotions
     * @param products
     * @return  打折商品value为折扣，不打折的返回0
     */
    HashMap<Integer,Integer> getPromotionDiscount(List<Promotion> promotions,List<Product> products);
}
