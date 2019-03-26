package com.deng.mall.controller;

import com.deng.mall.domain.BoStock;
import com.deng.mall.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("stock")
public class StockController {
    @Autowired
    StockService stockService;

    @RequestMapping("/stockList")
    public ModelAndView getStockList(@RequestParam(required = false, defaultValue = "0") Long offset,
                                     @RequestParam(required = false, defaultValue = "10") Integer limit) {

            List<BoStock> boStockList= stockService.getStockList(limit,offset);
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.setViewName("bizstock.html");
            modelAndView.addObject("boStockList",boStockList);

            return modelAndView;
    }

    @RequestMapping("/save")
    public String saveStock(      @RequestParam(value = "id")Integer id,
                                  @RequestParam(value = "count")Long count,
                                  @RequestParam(value = "stockInDate")String stockInDate,
                                  @RequestParam(value = "stockOutDate")String stockOutDate
                                  )
    {

        stockService.saveStock(id,count,stockInDate,stockOutDate);
        return  "bizstock.html";
    }
}
