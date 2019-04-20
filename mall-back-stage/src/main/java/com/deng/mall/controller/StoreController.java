package com.deng.mall.controller;

import com.deng.mall.domain.Store;
import com.deng.mall.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("store")
public class StoreController {
    @Autowired
    StoreService storeService;

    @RequestMapping("/create")
    public @ResponseBody String createStore(
            Store store
    ){
       boolean status= storeService.createStore(store);
       String result=status?"true":"false";
       return result;
    }
}
