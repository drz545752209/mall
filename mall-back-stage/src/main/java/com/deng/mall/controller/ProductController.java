package com.deng.mall.controller;


import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import com.deng.common.utils.PageFucker;
import com.deng.common.utils.StrUntils;
import com.deng.mall.domain.Biz;
import com.deng.mall.domain.Product;
import com.deng.mall.domain.Promotion;
import com.deng.mall.service.BizService;
import com.deng.mall.service.ProductService;
import com.deng.mall.service.PromotionService;
import com.deng.mall.utils.UpfileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    BizService bizService;
    @Autowired
    PromotionService promotionService;

    @RequestMapping(value = "upFile", method = RequestMethod.POST)
    @ResponseBody
    public List<String> upfileProductImg(@RequestParam("file[]") MultipartFile[] productImgs, HttpServletRequest request) {
//        String picPath = request.getServletContext().getRealPath("static/product_img/");
        String picPath = "E:/新建文件夹/mall/mall-back-stage/src/main/resources/static/product_img";
        List<String> productImgPaths = UpfileUtils.loadFileList(productImgs, picPath);

        String frontPicPath = "E:/新建文件夹/mall/mall-front-site/src/main/resources/static/product_img";
        UpfileUtils.loadFileList(productImgs, frontPicPath);
        return productImgPaths;
    }

    @RequestMapping(value = "/productList")
    public ModelAndView getProductList(@RequestParam(value = "type", required = false) String type,
                                       @RequestParam(value = "keyword",required = false) String keyword,
                                       @RequestParam(value = "isShow", required = false, defaultValue = "0") String isShow,
                                       @RequestParam(value = "pageNum", required = false, defaultValue = "1") Long pageNum,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                       HttpServletRequest request
    ) {
        List<Product> productList;
        List<String> productTypeList;

        HashMap<String,Object> resultMap =productService.getProductByType(isShow, type, keyword,pageNum-1, pageSize,request);

        productList= (List<Product>) resultMap.get("productList");
        Integer dataNum= (Integer) resultMap.get("dataNum");

        productTypeList = productService.getProductTypeList();
        ModelAndView mv = new ModelAndView();

        if (productList!=null&&productTypeList!=null){
            mv.addObject("productList", productList);
            mv.addObject("productTypeList", productTypeList);
            mv.addObject("hasProduct",true);
            mv.addObject("hasType",true);
            mv.setViewName("bizproduct.html");
        }else if (productList==null){
            String bizName= (String) request.getSession().getAttribute("loginName");
            Biz biz=new Biz();
            biz.setName(bizName);
            Integer bizId=bizService.selectBizNameByExamle(biz).get(0).getId();
            mv.addObject("bizId",bizId);
            mv.setViewName("bizstore");
        }else {
            mv.addObject("hasProduct",false);
            mv.addObject("hasType",false);
            mv.setViewName("bizproduct.html");
        }

        PageFucker pageInfo=new PageFucker(pageSize,pageNum,dataNum);
        pageInfo.computePage();

        mv.addObject("pageInfo",pageInfo);

        if (!StringUtils.isEmpty(type)){
            mv.addObject("typeName",type);
        }else {
            mv.addObject("typeName",null);
        }


        mv.addObject("isShow",isShow);

        return mv;
    }

    @RequestMapping("/getProductForSave")
    public @ResponseBody
    Product getProductForSave(String saveId) {
        Product product;
        product = productService.getProductById(saveId);

        return product;
    }


    @RequestMapping(value = "/batchDelete")
    public String batchDelete(String idStr) {
        List<Integer> ids = StrUntils.getIds(idStr);
        productService.batchDeleteProduct(ids);
        return "redirect:/product/productList";
    }

    @RequestMapping(value = "/delete")
    public String delete(String deleteId) {
        productService.deleteProduct(deleteId);
        return "redirect:/product/productList";
    }

    @RequestMapping(value = "/downShelf")
    public @ResponseBody String downShelf(String idStr) {
        List<Integer> ids = StrUntils.getIds(idStr);
        productService.batchDownShelf(ids);
        return "susess";
    }

    @RequestMapping(value = "/upShelf")
    public @ResponseBody String upShelf(String idStr) {
        List<Integer> ids = StrUntils.getIds(idStr);
        productService.batchUpShelf(ids);
        return "susess";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Product product) {
        productService.save(product);
        return "redirect:/product/productList";
    }


    @RequestMapping(value = "/create")
    public String create(Product product) {
        productService.create(product);
        return "redirect:/product/productList";
    }

    @RequestMapping(value = "/getPromotionForSave")
    public @ResponseBody
    Product getPromotionProduct(String promotionId){
        Product product;
        product = productService.getProductById(promotionId);
        return product;
    }

    @RequestMapping(value = "/promotion")
    public String addPromotion(Promotion promotion){
       promotionService.addPromotionProduct(promotion);

       return "redirect:/product/productList";
    }

}
