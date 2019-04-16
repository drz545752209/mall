package com.deng.logistics.dao;

import com.deng.logistics.domain.Logistics;
import com.deng.logistics.domain.LogisticsExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * LogisticsDAO继承基类
 */
@Repository
@Mapper
public interface LogisticsDAO extends MyBatisBaseDao<Logistics, Integer, LogisticsExample> {
}