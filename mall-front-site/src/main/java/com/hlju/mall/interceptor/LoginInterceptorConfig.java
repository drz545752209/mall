package com.hlju.mall.interceptor;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class LoginInterceptorConfig implements ApplicationContextAware,WebMvcConfigurer{
	ApplicationContext applicationContext;
	
	@Autowired
	UserLoginInterceptor userLoginInterceptor;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext=applicationContext;
	}
	
	public void addInterceptors(InterceptorRegistry registry) {
		String[] filterPath={"/commitOrder","/queryOrder","/toComment","/addComment","/user/saveUserMsg"
		};
		registry.addInterceptor(userLoginInterceptor)
		                        .addPathPatterns(filterPath);
	}

}
