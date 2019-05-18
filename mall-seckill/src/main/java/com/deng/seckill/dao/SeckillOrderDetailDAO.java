package com.deng.seckill.dao;

import com.deng.seckill.domain.SeckillOrderDetail;
import com.deng.seckill.domain.SeckillOrderDetailExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * SeckillOrderDetailDAO继承基类
 */
@Mapper
public interface SeckillOrderDetailDAO extends MyBatisBaseDao<SeckillOrderDetail, Integer, SeckillOrderDetailExample> {
}