package com.deng.seckill.dao;

import com.deng.seckill.domain.SeckillOrder;
import com.deng.seckill.domain.SeckillOrderExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * SeckillOrderDAO继承基类
 */
@Repository
@Mapper
public interface SeckillOrderDAO extends MyBatisBaseDao<SeckillOrder, Integer, SeckillOrderExample> {
    SeckillOrder selectByUserIdAndGoodsId(@Param("userId") long userId , @Param("goodsId") long goodsId );
}