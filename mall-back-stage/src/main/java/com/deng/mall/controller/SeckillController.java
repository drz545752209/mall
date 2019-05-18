package com.deng.mall.controller;

import com.deng.common.utils.ListUtils;
import com.deng.mall.domain.Biz;
import com.deng.mall.domain.Product;
import com.deng.mall.domain.Stock;
import com.deng.mall.service.BizService;
import com.deng.mall.service.ProductService;
import com.deng.mall.service.StockService;
import com.deng.mall.utils.JedisUtils;
import com.deng.seckill.bo.GoodsBo;
import com.deng.seckill.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RequestMapping("bizseckill")
@Controller
public class SeckillController {

    @Autowired
    ProductService productService;
    @Autowired
    SeckillGoodsService seckillGoodsService;
    @Autowired
    BizService bizService;
    @Autowired
    StockService stockService;

    @RequestMapping("seckillList")
    public  ModelAndView getSeckillList(HttpServletRequest request){
        ModelAndView mav=new ModelAndView();
        HttpSession session=request.getSession();
        String bizName= (String) session.getAttribute("loginName");
        List<String> productIds= JedisUtils.LRANGE(bizName,0,-1);
        HashSet<String> resultSet=new ListUtils<String>().list2HashMap(productIds);
        if (productIds.size()==0){
            mav.addObject("message","请先刷新缓存,点击商品查看页可以刷新");
            mav.addObject("status",false);
            mav.setViewName("status");
            return mav;
        }
        List<GoodsBo> goodsBos =seckillGoodsService.getSeckillGoodsList();
        List<GoodsBo> goodsBoList=new ArrayList<>();
        for (GoodsBo goodsBo:goodsBos){
            if (resultSet.contains(goodsBo.getId().toString())){
                goodsBoList.add(goodsBo);
            }
        }

        mav.addObject("goodsBoList",goodsBoList);
        mav.setViewName("bizseckill");
        return mav;
    }

    @RequestMapping("toSeckillManager")
    public String seckillManager(){
        return "bizseckill";
    }

    @RequestMapping("addSeckill")
    public ModelAndView addSeckill(GoodsBo goodsBo){
        ModelAndView mav=new ModelAndView();
        Product product=productService.getProductByName(goodsBo.getGoodsName());
        if (product==null){
            mav.addObject("message","商品不存在");
            mav.addObject("status",false);
            mav.setViewName("status");
            return mav;
        }
        if (goodsBo.getGoodsStock()-goodsBo.getStockCount()<0){
            mav.addObject("message","添加失败，库存数不足");
            mav.addObject("status",false);
            mav.setViewName("status");
            return mav;
        }
        boolean var1=seckillGoodsService.insertSeckillGoods(product.getId().toString(),
                goodsBo.getSeckillPrice(),goodsBo.getStartDate(),goodsBo.getEndDate());
        if (var1){
            Stock stock=new Stock();
            stock.setCount((long) (goodsBo.getGoodsStock()-goodsBo.getStockCount()));
            stockService.saveStock(stock);
        }
        mav.setViewName("redirect:/bizseckill/seckillList");
        return mav;
    }
}
