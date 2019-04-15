package com.hlju.mall.service;


import com.hlju.mall.domain.Shopcar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

public interface ShopcarService {
       boolean addShopcarToRedis(Integer productId, Integer buyNum,Integer oldPrice,Double newPrice, HttpServletResponse resp);

       boolean delShopcarFromRedis(Integer productId,HttpServletRequest req);

       boolean saveShopcarFromRedis(Integer productId, Integer buyNum, HttpServletRequest req);

       List<Shopcar> getShopcarList(HttpServletRequest req);

       HashMap<String,Object> computeSum(List<Shopcar> shopcars);
}
