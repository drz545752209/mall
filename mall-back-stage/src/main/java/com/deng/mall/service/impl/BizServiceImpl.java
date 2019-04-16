package com.deng.mall.service.impl;

import com.deng.common.utils.Encryption;
import com.deng.mall.dao.BizDAO;
import com.deng.mall.dao.StoreDAO;
import com.deng.mall.domain.Biz;
import com.deng.mall.domain.BizExample;
import com.deng.mall.domain.Store;
import com.deng.mall.domain.StoreExample;
import com.deng.mall.service.BizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service("bizService")
public class BizServiceImpl implements BizService {
    @Autowired
    BizDAO bizDAO;
    @Autowired
    StoreDAO storeDAO;

    @Override
    public int insertSelective(Biz record) {
        String password = record.getPwd();
        record.setPwd(Encryption.str2MD5(password));
        return bizDAO.insertSelective(record);
    }

    @Override
    public boolean hasBiz(Biz record) {
        List<Biz> users = selectByExample(record);
        if (users.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<Biz> selectByExample(Biz record) {
        BizExample bizExample = new BizExample();
        BizExample.Criteria criteria = bizExample.createCriteria();
        criteria.andNameEqualTo(record.getName());
        String password = Encryption.str2MD5(record.getPwd());
        criteria.andPwdEqualTo(password);
        return bizDAO.selectByExample(bizExample);
    }

    @Override
    public boolean hasBizName(Biz record) {
        List<Biz> users = selectBizNameByExamle(record);
        if (users.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<Biz> selectBizNameByExamle(Biz record) {
        BizExample bizExample= new BizExample();
        BizExample.Criteria criteria = bizExample.createCriteria();
        criteria.andNameEqualTo(record.getName());
        return bizDAO.selectByExample(bizExample);
    }

    @Override
    public boolean hasCookie(HttpServletRequest request, HttpServletResponse resp, boolean isValidate, Biz biz) {
        Cookie[] cookies=request.getCookies();
        String password=Encryption.str2MD5(biz.getPwd());
        String bizName=String.format("mall%s",biz.getName());

        for (Cookie cookie:cookies){
            if (StringUtils.isEmpty(cookie.getName())&&cookie.getName().startsWith("mall")){
                if (cookie.getName().equals(bizName) && cookie.getValue().equals(password)){
                    return true;
                }
            }
        }
        if (isValidate){
            Cookie cookie;
            cookie = new Cookie(bizName,password);
            cookie.setMaxAge(60*60*24*3);
            resp.addCookie(cookie);
        }
        return false;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean updateBizScore(Integer storeId, Integer score) {
        Store store=storeDAO.selectByPrimaryKey(storeId);
        Integer curScore=store.getScore();
        Long salesVolume=store.getSalesVolume();
        long updateScore=(curScore*salesVolume+score)/(salesVolume+1);
        store.setScore((int)updateScore);

        StoreExample storeExample=new StoreExample();
        StoreExample.Criteria storeExampleCriteria=storeExample.createCriteria();
        storeExampleCriteria.andIdEqualTo(storeId);
        storeDAO.updateByExampleSelective(store,storeExample);

        StoreExample selectBizExample=new StoreExample();
        StoreExample.Criteria selectBizCriteria=storeExample.createCriteria();
        selectBizCriteria.andBizIdEqualTo(store.getBizId());
        List<Store> bizStores=storeDAO.selectByExample(selectBizExample);
        Integer bizStoreSum=bizStores.size();
        Integer bizStoreScoreSum=0;
        for (Store var:bizStores){
            bizStoreScoreSum+=var.getScore();
        }

        BizExample bizExample=new BizExample();
        BizExample.Criteria bizExampleCriteria=bizExample.createCriteria();
        bizExampleCriteria.andIdEqualTo(store.getBizId());
        List<Biz> bizs=bizDAO.selectByExample(bizExample);
        Biz biz=bizs.get(0);

        Integer curBizScore=biz.getBizScore();
        Integer updateBizScore=bizStoreScoreSum/bizStoreSum;
        biz.setBizScore(updateBizScore);
        bizDAO.updateByExampleSelective(biz,bizExample);

        return true;
    }

}
