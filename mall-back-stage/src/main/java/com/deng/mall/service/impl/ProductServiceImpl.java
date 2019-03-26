package com.deng.mall.service.impl;

import com.deng.mall.dao.ProductDAO;
import com.deng.mall.domain.Product;
import com.deng.mall.domain.ProductExample;
import com.deng.mall.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService{
    final static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    ProductDAO productDAO;

    public List<Product> getProductByType(String isShow,String type,long pageNum,int pageSize) {
        List<Product> productList;
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        if (!StringUtils.isEmpty(type)){
            criteria.andTypeEqualTo(type);
        }
        criteria.andIsDelEqualTo(false);
        if ("1".equals(isShow)){
            criteria.andIsShowEqualTo(true);
        }else {
            criteria.andIsShowEqualTo(false);
        }
        productExample.or(criteria);
        productExample.setOffset(pageNum*pageSize);
        productExample.setLimit(pageSize);
        productList = productDAO.selectByExample(productExample);
        return productList;
    }

    public Product getProductById(String id) {
        Product product = productDAO.selectByPrimaryKey(Integer.valueOf(id));
        if (product.getIsDel()) {
            return null;
        } else {
            return product;
        }
    }

    public List<Product> getProductListById(List<Integer> ids) {
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andIdIn(ids);
        productExample.or(criteria);
        List<Product> productList = productDAO.selectByExample(productExample);
        return productList;
    }



    @Transactional
    public void batchDeleteProduct(List<Integer> ids) {
        List<Product> productList = getProductListById(ids);
        try{
            for (Product product : productList) {
                product.setIsDel(true);
                productDAO.updateByPrimaryKey(product);
            }
            logger.info("batchDelete products ids:[%s]", ids.toString());
        }catch (Exception e){
            logger.error("batchDelete failed , roll back!!");
        }
    }

    public int deleteProduct(String id) {
        int result = productDAO.deleteByPrimaryKey(Integer.valueOf(id));
        return result;
    }

    public List<String> getProductTypeList() {
        List<String> typeList = productDAO.getTypeList();
        return typeList;
    }

    @Transactional
    public Boolean batchUpShelf(List<Integer> ids) {
        try {
            List<Product> productList = getProductListById(ids);
            for (Product product : productList) {
                product.setIsShow(true);
                productDAO.updateByPrimaryKey(product);
            }
            logger.info("batchUpShelf products ids:[%s]", ids.toString());
        } catch (Exception e) {
            logger.error("batchUpShelf failed , roll back!!");
            return false;
        }
        return true;
    }

    @Transactional
    public Boolean batchDownShelf(List<Integer> ids) {
        try {
            List<Product> productList = getProductListById(ids);
            for (Product product : productList) {
                product.setIsShow(false);
                productDAO.updateByPrimaryKey(product);
            }
            logger.info("batchDownShelf products ids:[%s]", ids.toString());
        } catch (Exception e) {
            logger.error("batchDownShelf failed , roll back!!");
            return false;
        }
        return true;
    }

    public  void save(Product product){
        productDAO.updateByPrimaryKeySelective(product);
    }

    public void create(Product record){
        productDAO.insertSelective(record);
    }
}