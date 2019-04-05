package com.hlju.mall;

import com.hlju.mall.config.JedisUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import redis.clients.jedis.JedisPool;

@SpringBootApplication
@ImportResource({"classpath:/config/dubbo-front-consumer.xml"})
@Import({JedisUtils.class, JedisPool.class})
public class DigitalMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalMallApplication.class, args);
	}
	
}
