package com.deng.mall.dao;

import com.deng.mall.domain.Biz;
import com.deng.mall.domain.BizExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * BizDAO继承基类
 */
@Mapper
public interface BizDAO extends MyBatisBaseDao<Biz, Integer, BizExample> {
}