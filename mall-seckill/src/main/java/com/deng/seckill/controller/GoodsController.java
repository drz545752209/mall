package com.deng.seckill.controller;


import com.deng.seckill.bo.GoodsBo;
import com.deng.seckill.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    SeckillGoodsService seckillGoodsService;


    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav=new ModelAndView();

        List<GoodsBo> goodsList = seckillGoodsService.getSeckillGoodsList();
        mav.addObject("goodsList", goodsList);
        mav.setViewName("goods_list");
        return mav;
    }


    @RequestMapping("/to_detail")
    public ModelAndView detail(
                         @RequestParam("goodsId")long goodsId ,
                         HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mav=new ModelAndView();
        GoodsBo goods = seckillGoodsService.getseckillGoodsBoByGoodsId(goodsId);
        if(goods == null){
            System.out.println("没有找到界面");
        }else {
            mav.addObject("goods", goods);
            long startAt = goods.getStartDate().getTime();
            long endAt = goods.getEndDate().getTime();
            long now = System.currentTimeMillis();

            int miaoshaStatus = 0;
            int remainSeconds = 0;
            if(now < startAt ) {//秒杀还没开始，倒计时
                miaoshaStatus = 0;
                remainSeconds = (int)((startAt - now )/1000);
            }else  if(now > endAt){//秒杀已经结束
                miaoshaStatus = 2;
                remainSeconds = -1;
            }else {//秒杀进行中
                miaoshaStatus = 1;
                remainSeconds = 0;
            }
            mav.addObject("seckillStatus", miaoshaStatus);
            mav.addObject("remainSeconds", remainSeconds);

            mav.setViewName("goods_detail");
        }
        return mav;
    }

}

