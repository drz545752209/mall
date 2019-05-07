package com.deng.mall.service.impl;

import com.deng.mall.dao.StoreDAO;
import com.deng.mall.domain.Store;
import com.deng.mall.domain.StoreExample;
import com.deng.mall.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("storeService")
public class StoreServiceImpl implements StoreService {
    @Autowired
    StoreDAO storeDAO;

    @Override
    public boolean createStore(Store store) {
        Integer count=storeDAO.insertSelective(store);
        return count>0?true:false;
    }

    @Override
    public List<Store> getStoresByBizId(Integer bizId) {
        StoreExample storeExample = new StoreExample();
        StoreExample.Criteria criteria = storeExample.createCriteria();
        criteria.andBizIdEqualTo(bizId);
        List<Store> stores = storeDAO.selectByExample(storeExample);

        return stores;
    }

    @Override
    public Store getStoreByName(String storeName) {
        StoreExample storeExample=new StoreExample();
        StoreExample.Criteria storeExampleCriteria=storeExample.createCriteria();
        storeExampleCriteria.andNameEqualTo(storeName);

        List<Store> stores=storeDAO.selectByExample(storeExample);
        if (stores.size()==0){
            return null;
        }else {
            return  stores.get(0);
        }

    }
}
