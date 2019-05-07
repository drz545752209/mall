package com.deng.logistics.interceptor;

import com.deng.common.utils.Encryption;
import com.deng.logistics.domain.LogisticsAdmin;
import com.deng.logistics.service.LogisticsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component("bizLoginInterceptor")
public class LogisticsLoginInterceptor implements HandlerInterceptor {
    protected static final Logger logger = LoggerFactory.getLogger(LogisticsLoginInterceptor.class);
    @Autowired
    LogisticsAdminService logisticsAdminService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession httpSession = request.getSession();
        String loginName = (String) httpSession.getAttribute("logisticsName");
        Cookie[] cookies = request.getCookies();
        if (!StringUtils.isEmpty(loginName)) {
            logger.info("当前用户：" + loginName);
            return true;
        }
        if (cookies.length>0){
            for (Cookie cookie : cookies) {
                if (!StringUtils.isEmpty(cookie.getName())&&cookie.getName().startsWith("mall")) {
                    String logName = cookie.getName().trim().substring("mall".length());
                    String password = Encryption.str2MD5(cookie.getValue());
                    LogisticsAdmin logisticsAdmin=new LogisticsAdmin();
                    logisticsAdmin.setName(logName);
                    logisticsAdmin.setPwd(password);
                    boolean isValidate = logisticsAdminService.hasAdmin(logisticsAdmin);
                    if (isValidate) {
                        logger.info("当前用户：" + loginName);
                        return true;
                    }
                }
            }
        }
        try {
            response.sendRedirect("/admin/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
