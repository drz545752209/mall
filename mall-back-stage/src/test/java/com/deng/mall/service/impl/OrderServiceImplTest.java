package com.deng.mall.service.impl;

import com.deng.mall.SmscManagerSubsystemApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.util.WebUtils;

import java.lang.reflect.Field;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SmscManagerSubsystemApplication.class)
@WebAppConfiguration
public class OrderServiceImplTest {
    @Autowired
//    OrderService orderService;

    @Test
    public void testBoOrderList() {
//        List<UserBoOrder> boOrderList=orderService.getBoOrderList(2,0L);
//       for (UserBoOrder boOrder:boOrderList){
//           System.out.println(boOrder.getOperator());
//       }
    }

    @Test
    public void test(){
        for (int i=0;i<2;i++){
            System.out.println(i);
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

    @Test
    public void printWebPath() {
        String path = WebUtils.class.getResource("/").getPath();
        System.out.println(path);
    }

}