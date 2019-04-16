package com.deng.logistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:/config/dubbo-logistics-provider.xml"})
public class MallLogisticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallLogisticsApplication.class, args);
	}
	
}
