package com.deng.mall.interceptor;

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
    BizLoginInterceptor bizLoginInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
		String [] excludeStr= {"/bizlogin", "/bizlogin.html", "/bizregister", "/bizregister.html"
				,"/bizManage/bizregister","/bizManage/login","/bizManage/userVerify","/bizManage/check_username"
				,"/css/*","/js/*","/img/*.gif","/img/*.jpg","/defaultKaptcha"
				};
		registry.addInterceptor(bizLoginInterceptor)
		                        .addPathPatterns("/**")
		                        .excludePathPatterns(Arrays.asList(excludeStr));
    }

}
