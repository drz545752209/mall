package com.deng.logistics.service.impl;

import com.deng.logistics.dao.LogisticsAdminDAO;
import com.deng.logistics.dao.LogisticsDAO;
import com.deng.logistics.domain.Logistics;
import com.deng.logistics.domain.LogisticsExample;
import com.deng.logistics.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("logisticsService")
public class LogisticsServiceImpl implements LogisticsService {
    @Autowired
    LogisticsDAO logisticsDAO;
    @Autowired
    LogisticsAdminDAO logisticsAdminDAO;

    @Override
    public boolean updateLogisticeScore(Integer orderDetailId, Integer score) {
        LogisticsExample select = new LogisticsExample();
        LogisticsExample update = new LogisticsExample();
        LogisticsExample.Criteria selectCriteria = select.createCriteria();
        selectCriteria.andOrderDetailIdEqualTo(orderDetailId);
        List<Logistics> logisticsList = logisticsDAO.selectByExample(select);
        Logistics updateLogistics = logisticsList.get(0);

        LogisticsExample.Criteria updateCriteria = update.createCriteria();
        updateLogistics.setScore(score);
        updateCriteria.andIdEqualTo(updateLogistics.getId());
        logisticsDAO.updateByExampleSelective(updateLogistics, update);

        return true;
    }

    @Override
    public boolean insertLogistice(Logistics logistics) {
        Integer count = logisticsDAO.insertSelective(logistics);
        return count > 0;
    }

    @Override
    public List<Logistics> getLogisticsList(String companyName) {
        LogisticsExample logisticsExample = new LogisticsExample();
        LogisticsExample.Criteria logisticsExampleCriteria = logisticsExample.createCriteria();
        logisticsExampleCriteria.andCompanyNameEqualTo(companyName);

        List<Logistics> logistics = logisticsDAO.selectByExample(logisticsExample);
        if (logistics.size() == 0) {
            return null;
        }

        return logistics;
    }
}
