package com.deng.mall.controller;

import com.deng.common.utils.PageFucker;
import com.deng.mall.domain.BoStock;
import com.deng.mall.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("stock")
public class StockController {
    @Autowired
    StockService stockService;

    @RequestMapping("/stockList")
    public ModelAndView getStockList(@RequestParam(value = "pageNum",required = false, defaultValue = "1") Long offset,
                                     @RequestParam(required = false, defaultValue = "10") Integer limit,
                                     HttpServletRequest request) {

        List<BoStock> boStockList = stockService.getStockList(limit, offset-1,request);
        Integer dateNum = stockService.getStockListCount(request);

        ModelAndView modelAndView = new ModelAndView();

        PageFucker pageInfo=new PageFucker(limit,offset,dateNum);
        pageInfo.computePage();

        modelAndView.addObject("boStockList", boStockList);
        modelAndView.addObject("pageInfo",pageInfo);
        modelAndView.setViewName("bizstock.html");

        return modelAndView;
    }

    @RequestMapping("/save")
    public String saveStock(      @RequestParam(value = "id")Integer id,
                                  @RequestParam(value = "count")String count,
                                  @RequestParam(value = "stockInDate")String stockInDate,
                                  @RequestParam(value = "stockOutDate")String stockOutDate
                                  )
    {
        stockService.saveStock(id,count,stockInDate,stockOutDate);
        return "redirect:/stock/stockList";
    }
}
