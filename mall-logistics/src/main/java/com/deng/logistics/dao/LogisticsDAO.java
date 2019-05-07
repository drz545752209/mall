package com.deng.logistics.dao;

import com.deng.logistics.domain.Logistics;
import com.deng.logistics.domain.LogisticsBo;
import com.deng.logistics.domain.LogisticsExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * LogisticsDAO继承基类
 */
@Repository
@Mapper
public interface LogisticsDAO extends MyBatisBaseDao<Logistics, Integer, LogisticsExample> {
    List<LogisticsBo> selectBoLogistics(@Param("companyName")String companyName);
}