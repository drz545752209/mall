package com.deng.mall.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;


import com.deng.mall.domain.Product;
import com.deng.mall.service.impl.ProductServiceImpl;
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
    ProductServiceImpl productServiceImpl;

    @RequestMapping(value = "upFile", method = RequestMethod.POST)
    @ResponseBody
    public List<String> upfileProductImg(@RequestParam("productImgs") MultipartFile[] productImgs, HttpServletRequest request) {
        String picPath = request.getServletContext().getRealPath("templates/images/");
        List<String> productImgPaths = UpfileUtils.loadFileList(productImgs, picPath);
        return productImgPaths;
    }

    @RequestMapping(value = "/productList")
    public ModelAndView getProductList(@RequestParam(value = "type", required = false) String type,
                                       @RequestParam(value = "isShow", required = false) String isShow,
                                       @RequestParam(value = "pageNum", required = false, defaultValue = "0L") Long pageNum,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        List<Product> productList;
        List<String> productTypeList;

        if (type != null && !"".equals(type)) {
            productList = productServiceImpl.getProductByType(type,pageNum, pageSize);
        } else if (isShow != null && !"".equals(isShow)) {
            productList = productServiceImpl.getProductListByIsShow(isShow,pageNum, pageSize);
        } else {
            productList = productServiceImpl.getAllProduct(pageNum, pageSize);
        }
        productTypeList = productServiceImpl.getProductTypeList();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("bizproduct.html");

        mv.addObject("productList", productList);
        mv.addObject("productTypeList", productTypeList);

        return mv;
    }

    @RequestMapping("/getProductForSave")
    public @ResponseBody
    Product getProductForSave(String saveId) {
        ModelAndView mv = new ModelAndView();
        Product product;
        product = productServiceImpl.getProductById(saveId);

        return product;
    }


    @RequestMapping(value = "/batchDelete")
    public String batchDelete(String idStr) {
        List<Integer> ids = StrUntils.getIds(idStr);
        productServiceImpl.batchDeleteProduct(ids);
        return "bizproduct.html";
    }

    @RequestMapping(value = "/delete")
    public String delete(String deleteId) {
        productServiceImpl.deleteProduct(deleteId);
        return "bizproduct.html";
    }

    @RequestMapping(value = "/create")
    public String create(String idStr) {
        return "bizproduct.html";
    }

    @RequestMapping(value = "/downShelf")
    public String downShelf(String idStr) {
        List<Integer> ids = StrUntils.getIds(idStr);
        productServiceImpl.batchDownShelf(ids);
        return "bizproduct.html";
    }

    @RequestMapping(value = "/upShelf")
    public String upShelf(String idStr) {
        List<Integer> ids = StrUntils.getIds(idStr);
        productServiceImpl.batchUpShelf(ids);
        return "bizproduct.html";
    }

    @RequestMapping(value = "/save")
    public String save(Product product) {
        return null;
    }


}
