package com.deng.mall.mq;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SendMsg {
    final static String backStageTopicName="backStageTopic";
    final static Logger logger= LoggerFactory.getLogger(SendMsg.class);


    public void sendMsg(String msg,String tags,DefaultMQProducer orderMQProduct){
        Message message = new Message(backStageTopicName, tags, msg.getBytes());
        System.out.println("111");
        try {
            try {
                SendResult sendResult = orderMQProduct.send(message);
                logger.info("发送消息:[]",sendResult.toString());
            } catch (MQClientException e) {
                e.printStackTrace();
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }

    }

}
