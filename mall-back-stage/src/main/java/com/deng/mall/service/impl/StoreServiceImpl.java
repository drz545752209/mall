package com.deng.mall.service.impl;

import com.deng.mall.dao.StoreDAO;
import com.deng.mall.domain.Store;
import com.deng.mall.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("storeService")
public class StoreServiceImpl implements StoreService {
    @Autowired
    StoreDAO storeDAO;

    @Override
    public boolean createStore(Store store) {
        Integer count=storeDAO.insertSelective(store);
        return count>0?true:false;
    }
}
