package com.deng.mall.service;

import com.deng.mall.domain.Product;

import java.util.List;

public interface ProductService {

     List<Product> getProductByType(String isShow,String type, long pageNum, int pageSize);

     Product getProductById(String id);

     List<Product> getProductListById(List<Integer> ids) ;

     void batchDeleteProduct(List<Integer> ids);

     int deleteProduct(String id);

     List<String> getProductTypeList();

     Boolean batchUpShelf(List<Integer> ids);

     Boolean batchDownShelf(List<Integer> ids);

     void save(Product product);

     void create(Product product);
}
