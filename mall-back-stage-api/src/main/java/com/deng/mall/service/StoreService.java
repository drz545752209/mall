package com.deng.mall.service;

import com.deng.mall.domain.Store;

import java.util.List;

public interface StoreService {
    boolean createStore(Store store);

    List<Store> getStoresByBizId(Integer bizId);
}
