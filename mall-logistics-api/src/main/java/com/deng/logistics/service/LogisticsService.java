package com.deng.logistics.service;

import com.deng.logistics.domain.Logistics;

public interface LogisticsService {
    boolean updateLogisticeScore(Integer orderDetailId,Integer score);

    boolean insertLogistice(Logistics logistics);
}
