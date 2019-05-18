package com.deng.seckill.dao;

import com.deng.seckill.domain.SeckillProduct;
import com.deng.seckill.domain.SeckillProductExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * SeckillProductDAO继承基类
 */
@Repository
@Mapper
public interface SeckillProductDAO extends MyBatisBaseDao<SeckillProduct, Integer, SeckillProductExample> {
}