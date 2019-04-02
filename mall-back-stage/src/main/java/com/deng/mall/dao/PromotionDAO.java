package com.deng.mall.dao;

import com.deng.mall.domain.Promotion;
import com.deng.mall.domain.PromotionExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * PromotionDAO继承基类
 */
@Mapper
public interface PromotionDAO extends MyBatisBaseDao<Promotion, Integer, PromotionExample> {
}