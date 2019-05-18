package com.deng.mall.service;

import com.deng.mall.domain.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface ProductService {

     HashMap<String,Object> getProductByType(String isShow, String type, String keyword, long pageNum, int pageSize, HttpServletRequest request);

     Product getProductById(String id);

     Product getProductByName(String productName);

     List<Product> getProductListById(List<Integer> ids) ;

     void batchDeleteProduct(List<Integer> ids);

     int deleteProduct(String id);

     List<String> getProductTypeList();

     Boolean batchUpShelf(List<Integer> ids);

     Boolean batchDownShelf(List<Integer> ids);

     void save(Product product);

     void create(Product product);

     boolean updateComment(Integer productId,String comment);

     List<Product> sortBySumConsume(boolean isAsc,String typeName,String keyWord,Integer limit,Long offset);

}
