package com.deng.mall.interceptor;

import com.deng.common.utils.Encryption;
import com.deng.mall.domain.Biz;
import com.deng.mall.service.BizService;
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
public class BizLoginInterceptor implements HandlerInterceptor {
    protected static final Logger logger = LoggerFactory.getLogger(BizLoginInterceptor.class);
    @Autowired
    BizService bizService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession httpSession = request.getSession();
        String loginName = (String) httpSession.getAttribute("loginName");
        Cookie[] cookies = request.getCookies();
        if (!StringUtils.isEmpty(loginName)) {
            logger.info("当前用户：" + loginName);
            return true;
        }
        if (cookies.length>0){
            for (Cookie cookie : cookies) {
                if (!StringUtils.isEmpty(cookie.getName())&&cookie.getName().startsWith("mall")) {
                    String bizName = cookie.getName().trim().substring("mall".length());
                    String password = Encryption.str2MD5(cookie.getValue());
                    Biz biz = new Biz();
                    biz.setName(bizName);
                    biz.setPwd(password);
                    boolean isValidate = bizService.hasBiz(biz);
                    if (isValidate) {
                        logger.info("当前用户：" + loginName);
                        return true;
                    }
                }
            }
        }
        try {
            response.sendRedirect("/bizManage/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
