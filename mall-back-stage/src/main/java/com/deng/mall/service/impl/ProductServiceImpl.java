package com.deng.mall.service.impl;

import com.deng.mall.dao.*;
import com.deng.mall.domain.*;
import com.deng.mall.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService{
    final static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    ProductDAO productDAO;
    @Autowired
    BizDAO bizDAO;
    @Autowired
    StoreDAO storeDAO;
    @Autowired
    PromotionDAO promotionDAO;
    @Autowired
    StockDAO stockDAO;

    private String getStoreNameByBiz(String bizName){
        //获取bizid
        BizExample bizExample=new BizExample();
        BizExample.Criteria bizExampleCriteria=bizExample.createCriteria();
        bizExampleCriteria.andNameEqualTo(bizName);
        List<Biz> bizs=bizDAO.selectByExample(bizExample);
        Integer bizId=bizs.get(0).getId();
        //获取
        StoreExample storeExample=new StoreExample();
        StoreExample.Criteria storeExampleCriteria=storeExample.createCriteria();
        storeExampleCriteria.andBizIdEqualTo(bizId);
        List<Store> stores=storeDAO.selectByExample(storeExample);

        if (stores!=null&&stores.size()!=0){
            String storeName=stores.get(0).getName();
            return storeName;
        }

        return null;
    }

    public List<Product> getProductByType(String isShow, String type, String keyword,long pageNum, int pageSize, HttpServletRequest request) {
        List<Product> productList;
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();

        if (request!=null){
            HttpSession session=request.getSession();
            String bizName= (String) session.getAttribute("loginName");
            String storeName = getStoreNameByBiz(bizName);
            if (StringUtils.isEmpty(storeName)){
                return null;
            }
            criteria.andStoreNameEqualTo(storeName);
        }

        if (!StringUtils.isEmpty(type)&&!type.equals("null")){
            criteria.andTypeEqualTo(type);
        }
        if (!StringUtils.isEmpty(keyword)){
            keyword="%"+keyword+"%";
            criteria.andNameLike(keyword);
        }

        criteria.andIsDelEqualTo(false);
        if ("1".equals(isShow)){
            criteria.andIsShowEqualTo(true);
        }else {
            criteria.andIsShowEqualTo(false);
        }

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
        if (typeList!=null&&typeList.size()!=0){
            return typeList;
        }
        return null;
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

    @Transactional
    public void create(Product record){
        Integer productId;
        if (productDAO.getMaxId()==null){
            productId=1;
        }else {
            productId=productDAO.getMaxId().intValue()+1;
        }
        productDAO.insertSelective(record);
        Promotion promotion=new Promotion();
        promotion.setProductId(productId);
        promotionDAO.insertSelective(promotion);
        Stock stock=new Stock();
        stock.setProductId(productId);
        stock.setInDate(new Date());
        stockDAO.insertSelective(stock);
    }

    @Override
    public boolean updateComment(Integer productId,String comment) {
        Product product=productDAO.selectByPrimaryKey(productId);
        product.setComment(comment);
        productDAO.updateByPrimaryKeySelective(product);

        return true;
    }

    @Override
    public List<Product> sortBySumConsume(boolean isAsc,String typeName,String keyWord,Integer limit,Long offset) {
        List<Product> products=productDAO.orderByComsume(typeName,keyWord,limit,offset);
        if (isAsc){
            Collections.reverse(products);
        }
        return products;
    }
}
