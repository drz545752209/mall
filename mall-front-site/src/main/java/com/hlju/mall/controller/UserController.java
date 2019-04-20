package com.hlju.mall.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hlju.mall.domain.User;
import com.hlju.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;



	@RequestMapping(value = "/check_username")
	public @ResponseBody String checkUserName(String name){
		User user=new User();
		user.setName(name);
		boolean status=userService.hasUserName(user);
		String result=status?"false":"true";

		return result;
	}

    //跳转注册页
    @RequestMapping(method = RequestMethod.GET,value = {"/register","register.html"})
    public String register(){
        return "register";
    }

	@RequestMapping(method = RequestMethod.POST, value = { "/register", "/register.html" })
	public ModelAndView register(String username,String password, HttpServletRequest httpServletRequest) {
		ModelAndView mav = new ModelAndView();

		String verificationCode = (String) httpServletRequest.getSession().getAttribute("verificationCode");
		String clientVCode = httpServletRequest.getParameter("verificationCode");

		User user=new User();
		user.setName(username);
		user.setPwd(password);
		Boolean hasUserName = userService.hasUserName(user);

		if (!verificationCode.equals(clientVCode)) {
			// 失败
			mav.addObject("msg", "验证码错误");
			mav.setViewName("register");
		} else if (hasUserName) {
			// 用户名存在
			mav.addObject("msg", "用户名已存在");
			mav.setViewName("register");
		} else {
			// 验证成功
			userService.insertSelective(user);
			mav.setViewName("/login");
		}

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/login", "/login.html" })
	public String login() throws Exception {
		return "login";
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
			mav.setViewName("redirect:/index");
		} else {
			mav.setViewName("/login");
		}
		return mav;
	}

	@RequestMapping(value = "/saveUserMsg")
	public ModelAndView saveUserMsg(User user,HttpServletRequest req){
    	HttpSession session=req.getSession();
    	String userName= (String) session.getAttribute("userName");
    	boolean status=userService.saveUserMsg(user,userName);
    	ModelAndView mav=new ModelAndView();
    	mav.setViewName("status");
    	mav.addObject("status",status);
    	return mav;
	}

	@RequestMapping(value = "/toUserMsg")
	public ModelAndView toUserMsg(){
    	ModelAndView mav=new ModelAndView();
    	mav.setViewName("/user_msg");

    	return  mav;
	}


}
