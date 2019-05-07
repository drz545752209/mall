package com.deng.logistics.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	final static Logger LOGGER= LoggerFactory.getLogger(InterceptorConfig.class);

    @Autowired
	LogisticsLoginInterceptor logisticsLoginInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
		String [] excludeStr= {"/admin/login", "/admin/login.html","/login","/admin/register","/admin/register.html",
				"/register","/register.html",
				"/css/*","/js/*","/img/*.gif","/img/*.jpg","/defaultKaptcha"
				};
//		registry.addInterceptor(logisticsLoginInterceptor)
//		                        .addPathPatterns("/**")
//		                        .excludePathPatterns(Arrays.asList(excludeStr));
    }

}
