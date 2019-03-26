package com.deng.mall.mq;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.deng.mall.SmscManagerSubsystemApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SmscManagerSubsystemApplication.class)
@WebAppConfiguration
public class SendMsgTest {
    @Autowired
    DefaultMQProducer defaultMQProducer;
    final static Logger logger= LoggerFactory.getLogger(SendMsgTest.class);

    @Test
    public void sendTest(){
        HashMap<String,Object> jsonMap=new HashMap<>();
        jsonMap.put("orderId",1);
        jsonMap.put("status","发货");
        String orderMsg= JSONObject.toJSONString(jsonMap);
        System.out.println(defaultMQProducer.toString());
    }


    @Test
    public void print(){
        logger.info("1234");
    }
}