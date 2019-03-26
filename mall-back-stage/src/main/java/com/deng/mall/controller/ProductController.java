package com.deng.mall.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;


import com.deng.mall.domain.Product;
import com.deng.mall.service.ProductService;
import com.deng.mall.utils.StrUntils;
import com.deng.mall.utils.UpfileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value = "upFile", method = RequestMethod.POST)
    @ResponseBody
    public List<String> upfileProductImg(@RequestParam("productImgs") MultipartFile[] productImgs, HttpServletRequest request) {
        String picPath = request.getServletContext().getRealPath("templates/images/");
        List<String> productImgPaths = UpfileUtils.loadFileList(productImgs, picPath);
        return productImgPaths;
    }

    @RequestMapping(value = "/productList")
    public ModelAndView getProductList(@RequestParam(value = "type", required = false) String type,
                                       @RequestParam(value = "isShow", required = false, defaultValue = "0") String isShow,
                                       @RequestParam(value = "pageNum", required = false, defaultValue = "0") Long pageNum,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        List<Product> productList;
        List<String> productTypeList;

        productList = productService.getProductByType(isShow, type, pageNum, pageSize);
        productTypeList = productService.getProductTypeList();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("bizproduct.html");

        mv.addObject("productList", productList);
        mv.addObject("productTypeList", productTypeList);

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
        return "bizproduct.html";
    }

    @RequestMapping(value = "/delete")
    public String delete(String deleteId) {
        productService.deleteProduct(deleteId);
        return "bizproduct.html";
    }

    @RequestMapping(value = "/downShelf")
    public String downShelf(String idStr) {
        List<Integer> ids = StrUntils.getIds(idStr);
        productService.batchDownShelf(ids);
        return "bizproduct.html";
    }

    @RequestMapping(value = "/upShelf")
    public String upShelf(String idStr) {
        List<Integer> ids = StrUntils.getIds(idStr);
        productService.batchUpShelf(ids);
        return "bizproduct.html";
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



}
