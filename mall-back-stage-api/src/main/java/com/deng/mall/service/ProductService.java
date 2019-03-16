package com.deng.mall.service;

import com.deng.mall.domain.Product;

import java.util.List;

public interface ProductService {
     List<Product> getAllProduct(long pageNum, int pageSize);

     List<Product> getProductByType(String type, long pageNum, int pageSize);

     Product getProductById(String id);

     List<Product> getProductListById(List<Integer> ids) ;

     List<Product> getProductListByIsShow(String isShow,long pageNum,int pageSize);

     void batchDeleteProduct(List<Integer> ids);

     int deleteProduct(String id);

     List<String> getProductTypeList();

     void batchUpShelf(List<Integer> ids);

     void batchDownShelf(List<Integer> ids);

      void save(Product product);
}
