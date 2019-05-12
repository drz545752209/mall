package com.deng.logistics.service.impl;

import com.deng.logistics.dao.LogisticsAdminDAO;
import com.deng.logistics.dao.LogisticsDAO;
import com.deng.logistics.domain.Logistics;
import com.deng.logistics.domain.LogisticsAdminExample;
import com.deng.logistics.domain.LogisticsBo;
import com.deng.logistics.domain.LogisticsExample;
import com.deng.logistics.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public List<LogisticsBo> getLogisticsList(String userName) {
        LogisticsAdminExample logisticsAdminExample=new LogisticsAdminExample();
        LogisticsAdminExample.Criteria logisticsAdminExampleCriteria=logisticsAdminExample.createCriteria();
        logisticsAdminExampleCriteria.andNameEqualTo(userName);
        String companyName=logisticsAdminDAO.selectByExample(logisticsAdminExample).get(0).getCompanyName();

        List<LogisticsBo> logisticsBos=logisticsDAO.selectBoLogistics(companyName);

        if (logisticsBos.size()==0){
            return null;
        }

        return logisticsBos;
    }

    @Override
    public boolean updateLogisticeStatus(Integer id,String status) {
        boolean result=false;
        if (!StringUtils.isEmpty(id)&&!StringUtils.isEmpty(status)){
            Logistics logistics=new Logistics();
            logistics.setId(id);
            logistics.setStatus(status);
            result=logisticsDAO.updateByPrimaryKeySelective(logistics)>0?true:false;
        }
        return result;
    }

    @Override
    public Logistics getLogisticsByDetailOrderId(Integer detailOrderId) {
        LogisticsExample logisticsExample=new LogisticsExample();
        LogisticsExample.Criteria logisticsExampleCriteria=logisticsExample.createCriteria();
        logisticsExampleCriteria.andOrderDetailIdEqualTo(detailOrderId);
        List<Logistics> logistics=logisticsDAO.selectByExample(logisticsExample);
        if (logistics.size()!=0){
            return logistics.get(0);
        }
        return null;
    }

    @Override
    public boolean updateLogistics(Logistics logistics) {
        boolean result=logisticsDAO.updateByPrimaryKeySelective(logistics)==0?false:true;

        return result;
    }
}
