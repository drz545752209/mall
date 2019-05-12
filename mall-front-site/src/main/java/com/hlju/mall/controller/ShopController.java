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

import java.util.*;


@Controller
public class ShopController {
	
	@Autowired
	ProductService productService;
	@Autowired
	PromotionService promotionService;

	@RequestMapping(value = { "/index", "/index.html" }, method=RequestMethod.GET)
	public ModelAndView toIndex(String typeName,
								String keyword,
								String sortType,
								@RequestParam(required = false,defaultValue = "true") String isAsc,
								@RequestParam(required = false,defaultValue = "10")Integer pageSize,
								@RequestParam(required = false,defaultValue = "1")Long pageNum
								) {
		ModelAndView mav=new ModelAndView();
		List<Product> productList;
		HashMap<String,Object> resultMap=productService.getProductByType("1",typeName,keyword,pageNum-1,pageSize,null);

		productList= (List<Product>) resultMap.get("productList");
		Integer dataNum= (Integer) resultMap.get("dataNum");

		List<String> productTypeList=productService.getProductTypeList();
		HashMap<Integer,Integer> promotionDiscountMap=null;

		if (!StringUtils.isEmpty(typeName)){
			mav.addObject("typeName",typeName);
		}

		if (!StringUtils.isEmpty(keyword)){
			mav.addObject("keyword",keyword);
		}

		if (!StringUtils.isEmpty(sortType)){
			switch (sortType){
				case "销量":
					if ("false".equals(isAsc)){
						productList=productService.sortBySumConsume(false,typeName,keyword,pageSize,pageNum);
					}
					else {
						productList=productService.sortBySumConsume(true,typeName,keyword,pageSize,pageNum);
					}
					break;
				case "价格":
					Comparator<Product> priceComparator=(o1, o2) -> o1.getPrice()-o2.getPrice();
					productList.sort(priceComparator);
					if ("false".equals(isAsc)){
						Collections.reverse(productList);
					}
					break;
				default:
					System.out.println("未知");
			}
			mav.addObject("sortType",sortType);
			mav.addObject("isAsc",isAsc);
		}

		if (productList!=null&&productList.size()>0){
			List<Promotion> promotions =promotionService.getPromotionByProductIds(productList);
			promotionDiscountMap=promotionService.getPromotionDiscount(promotions,productList);
		}

		//分页
		PageFucker pageInfo=new PageFucker(pageSize,pageNum,dataNum);
		pageInfo.computePage();

		mav.addObject("productList",productList);
		mav.addObject("productTypeList",productTypeList);
		mav.addObject("pageInfo",pageInfo);
		mav.addObject("promotionDiscountMap",promotionDiscountMap);

		mav.setViewName("index.html");
		return mav;
	}


}
