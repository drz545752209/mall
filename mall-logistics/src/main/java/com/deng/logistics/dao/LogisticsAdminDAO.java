package com.deng.logistics.dao;

import com.deng.logistics.domain.LogisticsAdmin;
import com.deng.logistics.domain.LogisticsAdminExample;
import org.apache.ibatis.annotations.Mapper;

/**
 * LogisticsAdminDAO继承基类
 */
@Mapper
public interface LogisticsAdminDAO extends MyBatisBaseDao<LogisticsAdmin, Integer, LogisticsAdminExample> {
}