package com.deng.mall.controller;

import com.deng.mall.domain.Biz;
import com.deng.mall.service.BizService;
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
@RequestMapping(value = "/bizManage")
public class BizManageController {

    @Autowired
    BizService bizService;

    //跳转注册页
    @RequestMapping(method = RequestMethod.GET,value = {"/bizregister","bizregister.html"})
    public String register(){
        return "bizregister.html";
    }

    @RequestMapping(method = RequestMethod.POST, value = { "/bizregister", "/bizregister.html" })
    public ModelAndView register(String username,String password, HttpServletRequest httpServletRequest) {
        ModelAndView mav = new ModelAndView();
        Biz biz=new Biz();
        biz.setName(username);
        biz.setPwd(password);

        String verificationCode = (String) httpServletRequest.getSession().getAttribute("verificationCode");
        String clientVCode = httpServletRequest.getParameter("verificationCode");
        Boolean hasUserName = bizService.hasBizName(biz);

        if (!verificationCode.equals(clientVCode)) {
            mav.addObject("msg", "验证码错误");
            mav.setViewName("bizregister");
        } else if (hasUserName) {
            mav.addObject("msg", "用户名已存在");
            mav.setViewName("bizregister");
        } else {
            bizService.insertSelective(biz);
            mav.setViewName("/bizlogin");
        }

        return mav;
    }

    @RequestMapping("/check_username")
    public @ResponseBody String checkBizName(Biz biz){
        String result;
        Boolean hasUserName=bizService.hasBizName(biz);
        if (hasUserName){
            result="false";
        }else {
            result="true";
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = { "/login", "/login.html" })
    public String login() throws Exception {
        return "bizlogin.html";
    }



    @RequestMapping(method = RequestMethod.POST, value = { "/userVerify" })
    public ModelAndView loginValidate(String username,String password, HttpServletRequest res, HttpServletResponse resp) {
        Biz biz=new Biz();
        biz.setName(username);
        biz.setPwd(password);

        boolean isValidate = bizService.hasBiz(biz);
        boolean hasCookie=bizService.hasCookie(res,resp,isValidate,biz);
        ModelAndView mav = new ModelAndView();
        if (isValidate || hasCookie) {
            HttpSession session = res.getSession();
            session.setAttribute("loginName", biz.getName());
            mav.setViewName("redirect:/bizManage/index");
        } else {
            mav.setViewName("/bizlogin");
        }
        return mav;
    }

    @RequestMapping(value = "/index")
    public String toManageIndex() {
        return "bizmanager.html";
    }

}
