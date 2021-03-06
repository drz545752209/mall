package com.hlju.mall.controller;


import com.deng.common.utils.PageFucker;
import com.deng.mall.domain.*;
import com.deng.mall.service.*;
import com.hlju.mall.config.JedisUtils;
import com.hlju.mall.domain.BoComment;
import com.hlju.mall.domain.Shopcar;
import com.hlju.mall.service.CommentService;
import com.hlju.mall.service.ShopcarService;
import com.hlju.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
    @Autowired
    OrderService orderService;
    @Autowired
    CommentService commentService;
    @Autowired
    StoreService storeService;
    @Autowired
    UserService userService;

    @RequestMapping("/shopdetail")
    public ModelAndView toProductDetail(@RequestParam(name = "id") Integer productId) {
        Product product = productService.getProductById(productId.toString());
        Promotion promotion = promotionService.getPromotionByProductId(product);
        Stock stock = stockService.getStockByProductId(product);
        Integer commentCount = commentService.getCommentCount(productId);
        Store store = storeService.getStoreByName(product.getStoreName());

        List<String> imgs = JedisUtils.LRANGE(product.getImg(), 0, -1);
        List<String> nums = new ArrayList<>();
        int imgNum = imgs.size();

        ModelAndView mav = new ModelAndView();
        mav.addObject("shopInfo", product);
        mav.addObject("promotionInfo", promotion);
        mav.addObject("stockInfo", stock);
        mav.addObject("commentCount", commentCount);
        mav.addObject("productId", productId);
        mav.addObject("storeScore", store.getScore());

        if (imgNum > 0) {
            mav.addObject("imgs", imgs.subList(1, imgNum));
            mav.addObject("firstPicture", imgs.get(0));
            for (int var = 1; var < imgNum; var++) {
                nums.add(String.valueOf(var));
            }
            mav.addObject("nums", nums);
        }

        mav.setViewName("shop_deatil");

        return mav;
    }

    @RequestMapping("/lookingMorePicture")
    public ModelAndView lookingMorePicture(String imageName){
        ModelAndView mav=new ModelAndView();
        List<String> imgs= JedisUtils.LRANGE(imageName,0,-1);
        List<String> nums=new ArrayList<>();
        int imgNum=imgs.size();
        if (imgNum>0){
            mav.addObject("imgs",imgs.subList(1,imgNum));
        }
        mav.addObject("firstPicture",imgs.get(0));

        for (int var=1;var<imgNum;var++){
            nums.add(String.valueOf(var));
        }
        mav.addObject("nums",nums);

        mav.setViewName("show-picture");
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

    @RequestMapping("/saveShopcar")
    public  ModelAndView saveShop(@RequestParam(name = "shopId")Integer productId,
                                  @RequestParam(name = "buyNum")Integer buyNum,
                                  HttpServletRequest req
                                  ){
        ModelAndView mav=new ModelAndView();
        boolean status=shopcarService.saveShopcarFromRedis(productId,buyNum,req);

        if (status){
            mav.setViewName("redirect:/scanShopcar");
        }else {
            mav.setViewName("status");
            mav.addObject("productId",productId);
            mav.addObject("status",status);
        }

        return mav;
    }

    @RequestMapping("/scanShopcar")
    public ModelAndView scanShopcar(
            HttpServletRequest req
    ){
         ModelAndView mav=new ModelAndView();

         List<Shopcar> shopcarList=shopcarService.getShopcarList(req);
         HashMap<String,Object> sumMap=shopcarService.computeSum(shopcarList);
         String userName= (String) req.getSession().getAttribute("userName");
         if (!StringUtils.isEmpty(userName)){
             Integer balance=userService.showUserBalance(userName);
             mav.addObject("balance",balance);
         }

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
            HttpServletRequest req,
            HttpServletResponse resp
    ){
        ModelAndView mav=new ModelAndView();

       boolean result=shopcarService.delShopcarFromRedis(productId,req,resp);

       mav.addObject("status",result);
       mav.addObject("productId",productId);
       mav.setViewName("status");

       return  mav;
    }

    @RequestMapping(value = "/commitOrder",method = RequestMethod.POST)
    public ModelAndView commitOrder(
            @RequestParam  String orderInfo,
            HttpServletRequest req,
            HttpServletResponse resp
    ) {
        ModelAndView mav=new ModelAndView();
        String status = "false";

        HttpSession session = req.getSession();
        String userName = (String) session.getAttribute("userName");
        List<Integer> productIds=orderService.createOrder(orderInfo,userName);

        for (Integer productId:productIds){
           status = shopcarService.delShopcarFromRedis(productId,req,resp)?"true":"false";
        }

        mav.addObject("status",status);
        if ("true".equals(status)){
            mav.addObject("message","交易成功");
        }else {
            mav.addObject("message","交易失败");
        }

        mav.setViewName("status");
        return mav;
    }

    @RequestMapping("/cancelShopping")
    public ModelAndView cancelShopping(Integer orderId){
        ModelAndView mav=new ModelAndView();
        boolean status=orderService.applyBackCash(orderId);

        mav.addObject("status",status);
        if (status){
            mav.addObject("message","退款成功");
        }else {
            mav.addObject("message","退款失败");
        }

        mav.setViewName("status");

        return mav;
    }

    @RequestMapping("/queryOrder")
    public ModelAndView queryOrder(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")Long pageNum,
            @RequestParam(name = "pageSize", required = false,defaultValue = "10")Integer pageSize,
            HttpServletRequest req
    ){
        ModelAndView mav=new ModelAndView();

        HttpSession session=req.getSession();
        String userName= (String) session.getAttribute("userName");

        //HttpServletRequest没有序列化，不支持dubbo
        List<UserBoOrder> userBoOrders=orderService.getQueryOrder(pageSize,pageNum-1,userName);

        Integer dataNum=orderService.getQueryOrderCount(userName);

        PageFucker pageInfo=new PageFucker(pageSize,pageNum,dataNum);
        pageInfo.computePage();

        mav.addObject("orderList",userBoOrders);
        mav.addObject("pageInfo",pageInfo);

        mav.setViewName("order");
        return mav;
    }

    @RequestMapping("/toComment")
    public ModelAndView toComment(
            @RequestParam(name = "productId")Integer productId,
            @RequestParam(name = "orderId")Integer orderId
    ){
        ModelAndView mav=new ModelAndView();
        mav.setViewName("comment");
        mav.addObject("productId",productId);
        mav.addObject("orderId",orderId);

        return mav;
    }

    @RequestMapping("/addComment")
    public ModelAndView addComment(
            String sendResult) {
        ModelAndView mav = new ModelAndView();
        boolean status=commentService.addComment(sendResult);
        mav.setViewName("status");
        mav.addObject("status",status);

        return mav;
    }

    @RequestMapping("/scanProductComment")
    public  ModelAndView toScanComment(Integer productId){
        ModelAndView mav=new ModelAndView();

        List<BoComment> boComments=commentService.getProductCommentList(productId);


        mav.addObject("commentList",boComments);
        mav.setViewName("scan-comment");

        return mav;
    }


}
