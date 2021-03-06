package com.deng.mall.dao;

import com.deng.mall.domain.Product;
import com.deng.mall.domain.ProductExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProductDAO继承基类
 */
@Mapper
public interface ProductDAO extends MyBatisBaseDao<Product, Integer, ProductExample> {
    List<String> getTypeList();
    Long getMaxId();
    List<Product> orderByComsume(@Param(value = "type")String type,@Param(value = "name")String name
            ,@Param(value = "limit")Integer limit,@Param(value = "offset")Long offset);
}