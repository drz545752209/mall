package com.deng.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ImportResource({"classpath:/config/dubbo-back-provider.xml","classpath:/config/dubbo-back-consumer.xml"})
public class SmscManagerSubsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmscManagerSubsystemApplication.class, args);
	}

}

