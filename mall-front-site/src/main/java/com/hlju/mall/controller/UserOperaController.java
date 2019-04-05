package com.hlju.mall.controller;


import com.deng.mall.domain.Product;
import com.deng.mall.domain.Promotion;
import com.deng.mall.domain.Stock;
import com.deng.mall.service.ProductService;
import com.deng.mall.service.PromotionService;
import com.deng.mall.service.StockService;
import com.hlju.mall.domain.Shopcar;
import com.hlju.mall.service.ShopcarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
public class UserOperaController {
    @Autowired
    ProductService productService;
    @Autowired
    PromotionService promotionService;
    @Autowired
    StockService stockService;
    @Autowired
    ShopcarService shopcarService;

    @RequestMapping("/shopdetail")
    public ModelAndView toProductDetail(@RequestParam(name = "id")Integer productId){
         Product product=productService.getProductById(productId.toString());
         Promotion promotion= promotionService.getPromotionByProductId(product);
         Stock stock=stockService.getStockByProductId(product);

         ModelAndView mav=new ModelAndView();
         mav.addObject("shopInfo",product);
         mav.addObject("promotionInfo",promotion);
         mav.addObject("stockInfo",stock);
         mav.setViewName("shop_deatil");

         return mav;
    }

    @RequestMapping("/addToShopcar")
    public ModelAndView addToShopcar(@RequestParam(name = "shopId")Integer productId,
                                     @RequestParam(name = "newPrice",required = false)Double newPrice,
                                     @RequestParam(name = "oldPrice")Integer oldPrice,
                                     @RequestParam(name = "buyNum")Integer buyNum,
                                     HttpServletResponse resp,
                                     HttpServletRequest req
                                     ){
           ModelAndView mav=new ModelAndView();
           boolean status=shopcarService.addShopcarToRedis(productId,buyNum,oldPrice,newPrice,resp);

           mav.addObject("status",status);
           mav.addObject("productId",productId);
           mav.setViewName("status");
           return mav;
    }

    @RequestMapping("/scanShopcar")
    public ModelAndView scanShopcar(
            HttpServletRequest req
    ){
         List<Shopcar> shopcarList=shopcarService.getShopcarList(req);
         HashMap<String,Object> sumMap=shopcarService.computeSum(shopcarList);
         ModelAndView mav=new ModelAndView();
         if (shopcarList.size()>0){
             mav.addObject("shopcarList",shopcarList);
             mav.addObject("shopNum",sumMap.get("shopNum"));
             mav.addObject("totalPrice",sumMap.get("totalPrice"));
         }
         mav.setViewName("shopcar");

         return mav;
    }

    @RequestMapping("/delFromShopcar")
    public ModelAndView delFromShopcar(
            @RequestParam(name = "shopId")Integer productId,
            HttpServletRequest req
    ){
        ModelAndView mav=new ModelAndView();

       boolean result=shopcarService.delShopcarFromRedis(productId,req);

       mav.addObject("status",result);
       mav.addObject("productId",productId);
       mav.setViewName("status");

       return  mav;
    }

    @RequestMapping("/commitOrder")
    public String commitOrder(
        @RequestParam("productId")Integer productId,
        @RequestParam("userId")Integer userId
    ){
      return null;
    }


}
