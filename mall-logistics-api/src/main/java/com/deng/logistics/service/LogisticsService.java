package com.deng.logistics.service;

import com.deng.logistics.domain.Logistics;

import java.util.List;

public interface LogisticsService {
    boolean updateLogisticeScore(Integer orderDetailId,Integer score);

    boolean insertLogistice(Logistics logistics);

    List<Logistics> getLogisticsList(String companyName);
}
