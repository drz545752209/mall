package com.deng.mall.service;

import com.deng.mall.domain.Product;
import com.deng.mall.domain.Promotion;

import java.util.HashMap;
import java.util.List;

public interface PromotionService {
    List<Promotion> getPromotionByProductIds(List<Product> products);

    Promotion getPromotionByProductId(Product product);

    /**
     * @param promotions
     * @param products
     * @return  打折商品value为折扣，不打折的返回10
     */
    HashMap<Integer,Integer> getPromotionDiscount(List<Promotion> promotions,List<Product> products);

    void addPromotionProduct(Promotion promotion);
}
