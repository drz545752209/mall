package com.hlju.mall.service.impl;

import com.deng.mall.domain.Product;
import com.deng.mall.service.ProductService;
import com.hlju.mall.config.JedisUtils;
import com.hlju.mall.domain.Shopcar;
import com.hlju.mall.service.ShopcarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service("shopcarService")
public class ShopcarServiceImpl implements ShopcarService {
    @Autowired
    ProductService productService;

    @Override
    public boolean addShopcarToRedis(Integer productId, Integer buyNum,Integer oldPrice,Double newPrice, HttpServletResponse resp) {
        String uuidStr = UUID.randomUUID().toString();
        String cookieKey=String.format("mallShopcar%s",uuidStr);
        Cookie cookie=new Cookie(cookieKey,uuidStr);
        cookie.setMaxAge(3600*24*7);
        resp.addCookie(cookie);

        Product product=productService.getProductById(productId.toString());

        Shopcar shopcar=new Shopcar();
        shopcar.setProductId(productId);
        shopcar.setBuyNum(buyNum);
        shopcar.setImg(product.getImg());
        shopcar.setProductName(product.getName());
        shopcar.setProductDesc(product.getDescription());
        shopcar.setOldPrice(oldPrice);
        if (newPrice!=null){
            shopcar.setNewPrice(newPrice);
        }

        //在redis中保存7天
        return JedisUtils.set(uuidStr,shopcar,3600*24*7);
    }


    @Override
    public List<Shopcar> getShopcarList(HttpServletRequest req) {
        Cookie[] cookies=req.getCookies();
        List<Shopcar> shopcars=new ArrayList<>();
        HashMap<String,Shopcar> carMap=new HashMap<>();
        for (Cookie cookie:cookies){
            String cookieKey=cookie.getName();
            String cookieValue=cookie.getValue();
            if (cookieKey!=null&&cookieKey.startsWith("mallShopcar")){
                Shopcar shopcar=(Shopcar) JedisUtils.get(cookieValue,true);
                if (carMap.containsKey(shopcar.getProductName())){
                    Shopcar sc=carMap.get(shopcar.getProductName());
                    Integer buyNum=shopcar.getBuyNum()+sc.getBuyNum();
                    shopcar.setBuyNum(buyNum);
                }
                carMap.put(shopcar.getProductName(),shopcar);
            }
        }
        for (String key:carMap.keySet()){
            shopcars.add(carMap.get(key));
        }
        return shopcars;
    }

    @Override
    public HashMap<String, Object> computeSum(List<Shopcar> shopcars) {
        HashMap<String,Object> resultMap=new HashMap<>();
        Integer totalCount=0;
        Double totalPrice=0.0;
        for (Shopcar shopcar:shopcars){
             totalCount+=shopcar.getBuyNum();
             totalPrice+=shopcar.getNewPrice()*shopcar.getBuyNum();
        }
        resultMap.put("shopNum",totalCount);
        resultMap.put("totalPrice",totalPrice);

        return resultMap;
    }

    @Override
    public boolean delShopcarFromRedis(Integer productId,HttpServletRequest req) {
        Cookie[] cookies=req.getCookies();
        for (Cookie cookie:cookies){
            String cookieKey=cookie.getName();
            String cookieValue=cookie.getValue();
            if (cookieKey!=null&&cookieKey.startsWith("mallShopcar")){
                Shopcar shopcar=(Shopcar) JedisUtils.get(cookieValue,true);
                if (shopcar.getProductId()==productId){
                    //删除redis
                    JedisUtils.del(cookieValue,true);
                    //删除cookie
                    cookie.setMaxAge(0);
                }
            }
        }
        return true;
    }

    @Override
    public boolean saveShopcarFromRedis(Integer productId, Integer buyNum, HttpServletRequest req) {
        Cookie[] cookies=req.getCookies();
        for (Cookie cookie:cookies){
            String cookieKey=cookie.getName();
            String cookieValue=cookie.getValue();
            if (cookieKey!=null&&cookieKey.startsWith("mallShopcar")){
                Shopcar shopcar=(Shopcar) JedisUtils.get(cookieValue,true);
                if (shopcar.getProductId()==productId){
                    Integer var1=shopcar.getBuyNum()+buyNum;
                    shopcar.setBuyNum(var1);
                    return true;
                }
            }
        }
        return false;
    }

}
