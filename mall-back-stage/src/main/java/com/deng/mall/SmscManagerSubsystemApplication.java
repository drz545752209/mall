package com.deng.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:/config/dubbo-back-provider.xml"})
public class SmscManagerSubsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmscManagerSubsystemApplication.class, args);
	}

}

