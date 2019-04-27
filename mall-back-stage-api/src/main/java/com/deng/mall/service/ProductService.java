package com.deng.mall.service;

import com.deng.mall.domain.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductService {

     List<Product> getProductByType(String isShow, String type, String keyword, long pageNum, int pageSize, HttpServletRequest request);

     Product getProductById(String id);

     List<Product> getProductListById(List<Integer> ids) ;

     void batchDeleteProduct(List<Integer> ids);

     int deleteProduct(String id);

     List<String> getProductTypeList();

     Boolean batchUpShelf(List<Integer> ids);

     Boolean batchDownShelf(List<Integer> ids);

     void save(Product product);

     void create(Product product);

     boolean updateComment(Integer productId,String comment);
}
