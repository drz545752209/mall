package com.hlju.mall.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component("userLoginInterceptor")
public class UserLoginInterceptor implements HandlerInterceptor{
	protected static final Logger logger = LoggerFactory.getLogger(UserLoginInterceptor.class);

	
	public boolean preHandle(HttpServletRequest request,HttpServletResponse httpServletResponse,Object handler) {
		HttpSession httpSession=request.getSession();
		String loginName=(String) httpSession.getAttribute("userName");
		if(loginName==null||"".equals(loginName)) {
			logger.info("用户未登录");
			try {
				httpServletResponse.sendRedirect("/login");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}else {
			logger.info("当前用户："+loginName);
			return true;
		}
	}

}
