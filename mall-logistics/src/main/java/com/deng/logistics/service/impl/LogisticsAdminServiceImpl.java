package com.deng.logistics.service.impl;

import com.deng.common.utils.Encryption;
import com.deng.logistics.dao.LogisticsAdminDAO;
import com.deng.logistics.domain.LogisticsAdmin;
import com.deng.logistics.domain.LogisticsAdminExample;
import com.deng.logistics.service.LogisticsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service("logisticsAdminService")
public class LogisticsAdminServiceImpl implements LogisticsAdminService {
    @Autowired
    LogisticsAdminDAO logisticsAdminDAO;

    @Override
    public boolean hasAdmin(LogisticsAdmin logisticsAdmin) {
        LogisticsAdminExample logisticsAdminExample=new LogisticsAdminExample();
        LogisticsAdminExample.Criteria logisticsAdminExampleCriteria= logisticsAdminExample.createCriteria();
        logisticsAdminExampleCriteria.andNameEqualTo(logisticsAdmin.getName());
        logisticsAdminExampleCriteria.andPwdEqualTo(logisticsAdmin.getPwd());
        //是否存在查询结果集
        boolean result=logisticsAdminDAO.selectByExample(logisticsAdminExample).size()>=1?true:false;

        return result;
    }

    @Override
    public boolean hasAdminName(String adminName) {
        LogisticsAdminExample logisticsAdminExample=new LogisticsAdminExample();
        LogisticsAdminExample.Criteria logisticsAdminExampleCriteria= logisticsAdminExample.createCriteria();
        logisticsAdminExampleCriteria.andNameEqualTo(adminName);

        boolean result=logisticsAdminDAO.selectByExample(logisticsAdminExample).size()==0?false:true;

        return result;
    }

    @Override
    public boolean insertSelective(LogisticsAdmin logisticsAdmin) {
        boolean result=logisticsAdminDAO.insertSelective(logisticsAdmin)>0?true:false;

        return result;
    }

    @Override
    public boolean hasCookie(HttpServletRequest request, HttpServletResponse resp, boolean isValidate, LogisticsAdmin logisticsAdmin) {
        Cookie[] cookies=request.getCookies();
        String password= Encryption.str2MD5(logisticsAdmin.getPwd());
        String adminName=String.format("mall%s",logisticsAdmin.getName());

        for (Cookie cookie:cookies){
            if (StringUtils.isEmpty(cookie.getName())&&cookie.getName().startsWith("mall")){
                if (cookie.getName().equals(adminName) && cookie.getValue().equals(password)){
                    return true;
                }
            }
        }
        if (isValidate){
            Cookie cookie;
            cookie = new Cookie(adminName,password);
            cookie.setMaxAge(60*60*24*3);
            resp.addCookie(cookie);
        }
        return false;
    }

    @Override
    public List<String> getCompanyNames() {
        List<String> companyNames=logisticsAdminDAO.selectCompanyName();
        return companyNames;
    }
}
