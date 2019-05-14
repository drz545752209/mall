package com.deng.mall;

import com.deng.mall.utils.JedisUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.JedisPool;

@SpringBootApplication
@EnableTransactionManagement
@ImportResource({"classpath:/config/dubbo-back-provider.xml","classpath:/config/dubbo-back-consumer.xml"})
@Import({JedisUtils.class, JedisPool.class})
public class SmscManagerSubsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmscManagerSubsystemApplication.class, args);
	}

}

