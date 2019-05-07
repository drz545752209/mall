package com.deng.logistics.controller;

import com.deng.common.utils.Encryption;
import com.deng.logistics.domain.LogisticsAdmin;
import com.deng.logistics.service.LogisticsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
public class LogisticsAdminController {
    @Autowired
    LogisticsAdminService logisticsAdminService;

    //跳转注册页
    @RequestMapping(method = RequestMethod.GET,value = {"/register","/register.html"})
    public String register(){
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST, value = { "/register", "/register.html" })
    public ModelAndView register(String username, String password,String companyName, HttpServletRequest httpServletRequest) {
        ModelAndView mav = new ModelAndView();
        LogisticsAdmin logisticsAdmin=new LogisticsAdmin();
        logisticsAdmin.setName(username);
        logisticsAdmin.setPwd(Encryption.str2MD5(password));
        logisticsAdmin.setCompanyName(companyName);

        String verificationCode = (String) httpServletRequest.getSession().getAttribute("verificationCode");
        String clientVCode = httpServletRequest.getParameter("verificationCode");
        Boolean hasUserName = logisticsAdminService.hasAdminName(username);

        if (!verificationCode.equals(clientVCode)) {
            mav.addObject("msg", "验证码错误!");
            mav.setViewName("register");
        } else if (hasUserName) {
            mav.addObject("msg", "用户名已存在!");
            mav.setViewName("register");
        } else {
            logisticsAdminService.insertSelective(logisticsAdmin);
            mav.setViewName("/login");
        }

        return mav;
    }

    @RequestMapping("/check_username")
    public @ResponseBody
    String checkBizName(String username, String password){
        String result;

        LogisticsAdmin logisticsAdmin=new LogisticsAdmin();
        logisticsAdmin.setName(username);
        logisticsAdmin.setPwd(password);

        Boolean hasUserName=logisticsAdminService.hasAdminName(username);
        if (hasUserName){
            result="false";
        }else {
            result="true";
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = { "/login", "/login.html" })
    public String login() {
        return "login";
    }



    @RequestMapping(method = RequestMethod.POST, value = { "/userVerify" })
    public ModelAndView loginValidate(String username,String password, HttpServletRequest res, HttpServletResponse resp) {
        LogisticsAdmin logisticsAdmin=new LogisticsAdmin();
        logisticsAdmin.setName(username);
        logisticsAdmin.setPwd(Encryption.str2MD5(password));

        boolean isValidate = logisticsAdminService.hasAdmin(logisticsAdmin);
        boolean hasCookie=logisticsAdminService.hasCookie(res,resp,isValidate,logisticsAdmin);
        ModelAndView mav = new ModelAndView();
        if (isValidate || hasCookie) {
            HttpSession session = res.getSession();
            session.setAttribute("logisticsName", logisticsAdmin.getName());
            mav.setViewName("/index");
        } else {
            mav.addObject("msg","用户名或密码不正确");
            mav.setViewName("/login");
        }
        return mav;
    }

    @RequestMapping(value = "/index")
    public String toManageIndex() {
        return "index";
    }
}
