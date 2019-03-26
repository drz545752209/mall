package com.deng.mall.service.impl;

import com.deng.mall.SmscManagerSubsystemApplication;
import com.deng.mall.domain.BoOrder;
import com.deng.mall.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.reflect.Field;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SmscManagerSubsystemApplication.class)
@WebAppConfiguration
public class OrderServiceImplTest {
    @Autowired
    OrderService orderService;

    @Test
    public void testBoOrderList() {
        List<BoOrder> boOrderList=orderService.getBoOrderList(2,0L);
       for (BoOrder boOrder:boOrderList){
           System.out.println(boOrder.getOperator());
       }
    }

    @Test
    public  void getClazz(){
        try {
            Class clazz=Class.forName("com.deng.mall.domain.BoOrder");
            for (Field declaredField : clazz.getDeclaredFields()) {
                System.out.println(declaredField.getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}