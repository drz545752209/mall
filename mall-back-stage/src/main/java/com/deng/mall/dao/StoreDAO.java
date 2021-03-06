package com.deng.mall.dao;

import com.deng.mall.domain.Store;
import com.deng.mall.domain.StoreExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * StoreDAO继承基类
 */
@Mapper
public interface StoreDAO extends MyBatisBaseDao<Store, Integer, StoreExample> {
}