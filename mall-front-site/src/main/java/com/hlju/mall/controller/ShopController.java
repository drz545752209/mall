package com.hlju.mall.controller;


import com.deng.common.utils.PageFucker;
import com.deng.mall.domain.Product;
import com.deng.mall.domain.Promotion;
import com.deng.mall.service.ProductService;
import com.deng.mall.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
								String keyword,
								@RequestParam(required = false,defaultValue = "10")Integer pageSize,
								@RequestParam(required = false,defaultValue = "1")Long pageNum
								) {
		ModelAndView mav=new ModelAndView();
		List<Product> productList=productService.getProductByType("1",typeName,keyword,pageNum-1,pageSize,null);
		List<String> productTypeList=productService.getProductTypeList();
		HashMap<Integer,Integer> promotionDiscountMap=null;

		if (productList.size()>0){
			List<Promotion> promotions =promotionService.getPromotionByProductIds(productList);
			promotionDiscountMap=promotionService.getPromotionDiscount(promotions,productList);
		}

		if (!StringUtils.isEmpty(typeName)){
			mav.addObject("typeName",typeName);
		}

		if (!StringUtils.isEmpty(keyword)){
			mav.addObject("keyword",keyword);
		}

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


}
