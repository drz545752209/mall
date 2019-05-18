package com.deng.seckill.controller;


import com.deng.seckill.bo.GoodsBo;
import com.deng.seckill.domain.SeckillOrder;
import com.deng.seckill.domain.SeckillOrderDetail;
import com.deng.seckill.service.SeckillGoodsService;
import com.deng.seckill.service.SeckillOrderService;
import com.hlju.mall.domain.User;
import com.hlju.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("seckill")
public class SeckillController{


    @Autowired
    SeckillGoodsService seckillGoodsService;

    @Autowired
    SeckillOrderService seckillOrderService;

    @Autowired
    UserService userService;


    @RequestMapping("/seckill")
    public ModelAndView list(@RequestParam("goodsId") long goodsId,
                        HttpServletRequest request) {
        ModelAndView mav=new ModelAndView();

        User user= new User();
        HttpSession session = request.getSession();
        String userName= (String) session.getAttribute("userName");
        user.setName(userName);

        user=userService.selectUserNameByExamle(user).get(0);

        //判断库存
        GoodsBo goods = seckillGoodsService.getseckillGoodsBoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            mav.addObject("errmsg","商品已经秒杀完毕");
            mav.setViewName("miaosha_fail");
            return mav;
        }
        //判断是否已经秒杀到了
        SeckillOrder order = seckillOrderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            mav.addObject("errmsg", "不能重复秒杀");
            mav.addObject("goodsId",goodsId);
            mav.setViewName("miaosha_fail");
            return mav;
        }
        //减库存 下订单 写入秒杀订单
        SeckillOrderDetail orderInfo = seckillOrderService.insert(user, goods);


        mav.addObject("orderInfo", orderInfo);
        mav.addObject("goods", goods);
        mav.addObject("user", user);
        mav.setViewName("order_detail");

        return mav;
    }

    @RequestMapping(value = "toPay")
    public ModelAndView toPay(HttpServletRequest request,
                              @RequestParam("goodsId")long goodsId
                              ){
        User user= new User();
        ModelAndView mav=new ModelAndView();
        HttpSession session = request.getSession();
        String userName= (String) session.getAttribute("userName");
        user.setName(userName);

        user=userService.selectUserNameByExamle(user).get(0);

        SeckillOrderDetail orderInfo=seckillOrderService.getSeckillOrderDetailByUserId(user.getId());

        GoodsBo goods = seckillGoodsService.getseckillGoodsBoByGoodsId(goodsId);

        mav.addObject("orderInfo", orderInfo);
        mav.addObject("goods", goods);
        mav.addObject("user", user);
        mav.setViewName("order_detail");

        return mav;
    }

    @RequestMapping(value = "/pay")
    public  @ResponseBody String pay(HttpServletRequest request,Integer price,Integer infoId){
        HttpSession httpSession=request.getSession();
        String userName= (String) httpSession.getAttribute("userName");
        User user=new User();
        user.setName(userName);
        user=userService.selectUserNameByExamle(user).get(0);
        SeckillOrderDetail seckillOrderDetail=new SeckillOrderDetail();
        seckillOrderDetail.setStatus(1);
        seckillOrderDetail.setId(infoId);
        boolean var1=userService.incrUserCash(user,price);
        boolean var2=seckillOrderService.changeStatus(seckillOrderDetail);

        if (var1&&var2){
          return    "支付成功";
        }

        return  "支付失败,请先为账户充值";
    }

}
