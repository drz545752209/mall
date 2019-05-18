package com.deng.seckill.controller;


import com.hlju.mall.domain.User;
import com.hlju.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;



    @RequestMapping(method = RequestMethod.GET, value = { "/login", "/login.html" })
    public String login() throws Exception {
        return "seckilllogin";
    }

    @RequestMapping(method = RequestMethod.POST, value = { "/user/login" })
    public ModelAndView loginValidate(String username,String password, HttpServletRequest res) {
        User user=new User();
        user.setName(username);
        user.setPwd(password);

        boolean isValidate = userService.hasPassword(user);
        ModelAndView mav = new ModelAndView();
        if (isValidate) {
            HttpSession session = res.getSession();
            session.setAttribute("userName", user.getName());
            mav.setViewName("redirect:/goods/list");
        } else {
            mav.setViewName("/seckilllogin");
        }
        return mav;
    }


}

