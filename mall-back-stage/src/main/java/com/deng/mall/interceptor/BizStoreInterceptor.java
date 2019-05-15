package com.deng.mall.interceptor;

import com.deng.mall.domain.Biz;
import com.deng.mall.domain.Store;
import com.deng.mall.service.BizService;
import com.deng.mall.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component("bizStoreInterceptor")
public class BizStoreInterceptor implements HandlerInterceptor {
    protected static final Logger logger = LoggerFactory.getLogger(BizLoginInterceptor.class);
    @Autowired
    BizService bizService;
    @Autowired
    StoreService storeService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String bizName= (String) request.getSession().getAttribute("loginName");
        Biz biz=new Biz();
        biz.setName(bizName);
        Integer bizId=bizService.selectBizNameByExamle(biz).get(0).getId();
        List<Store> stores=storeService.getStoresByBizId(bizId);

        if (stores.size()==0){
            request.setAttribute("bizId",bizId);

            request.getRequestDispatcher("/bizstore.html");
        }else {
            return true;
        }

        return false;
    }
}
