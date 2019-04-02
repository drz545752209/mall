package com.hlju.mall.controller;


import com.deng.common.utils.PageFucker;
import com.deng.mall.domain.Product;
import com.deng.mall.domain.Promotion;
import com.deng.mall.service.ProductService;
import com.deng.mall.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;


@Controller
public class ShopController {
	
	@Autowired
	ProductService productService;
	@Autowired
	PromotionService promotionService;

	@RequestMapping(value = { "/index", "/index.html" }, method=RequestMethod.GET)
	public ModelAndView toIndex(String typeName,
								@RequestParam(required = false,defaultValue = "10")Integer pageSize,
								@RequestParam(required = false,defaultValue = "1")Long pageNum
								) {
		ModelAndView mav=new ModelAndView();
		List<Product> productList=productService.getProductByType("1",typeName,pageNum-1,pageSize,null);
		List<String> productTypeList=productService.getProductTypeList();


		List<Promotion> promotions =promotionService.getPromotionByProductId(productList);
		HashMap<Integer,Integer> promotionDiscountMap=promotionService.getPromotionDiscount(promotions,productList);

		//分页
		PageFucker pageInfo=new PageFucker(pageSize,pageNum,productList.size());
		pageInfo.computePage();

		mav.addObject("productList",productList);
		mav.addObject("productTypeList",productTypeList);
		mav.addObject("pageInfo",pageInfo);
		mav.addObject("promotionDiscountMap",promotionDiscountMap);

		mav.setViewName("index.html");
		return mav;
	}

	@RequestMapping(value = { "/search"}, method=RequestMethod.GET)
	public ModelAndView search(@RequestParam()String keyword) {

		return null;
	}
	

}
