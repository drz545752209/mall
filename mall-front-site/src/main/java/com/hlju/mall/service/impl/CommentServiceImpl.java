package com.hlju.mall.service.impl;

import com.deng.common.utils.StrUntils;
import com.deng.logistics.service.LogisticsService;
import com.deng.mall.service.BizService;
import com.deng.mall.service.OrderService;
import com.deng.mall.service.ProductService;
import com.hlju.mall.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("commentService")
public class CommentServiceImpl implements CommentService {
    @Autowired
    BizService bizService;
    @Autowired
    LogisticsService logisticsService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;

    @Override
    @Transactional
    public boolean addComment(String commentJson) {
        Map resultMap=StrUntils.json2Map(commentJson);
        Integer orderId= Integer.valueOf(resultMap.get("orderId").toString()) ;
        Integer productId= Integer.valueOf(resultMap.get("productId").toString()) ;
        Integer logisticsScore= Integer.valueOf(resultMap.get("logisticsScore").toString());
        Integer storeScore= Integer.valueOf(resultMap.get("storeScore").toString());
        Integer storeId=orderService.getStoreIdByOrderId(orderId);
        String comment= resultMap.get("commentDesc").toString();

        boolean var1=logisticsService.updateLogisticeScore(orderId,logisticsScore);
        boolean var2=bizService.updateBizScore(storeId,storeScore);
        boolean var3=productService.updateComment(productId,comment);

        if (var1&&var2&&var3){
            return true;
        }

        return false;
    }
}
