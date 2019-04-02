package com.hlju.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:/config/dubbo-front-consumer.xml"})
public class DigitalMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalMallApplication.class, args);
	}
	
}
