package com.deng.mall.controller;

import com.deng.common.utils.Encryption;
import com.deng.mall.domain.Biz;
import com.deng.mall.service.BizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    public @ResponseBody String checkBizName(String username,String password){
        String result;

        Biz biz=new Biz();
        biz.setName(username);
        biz.setPwd(password);

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

    @RequestMapping(value = "/toModify")
    public String toModify()
    {
        return "bizModify";
    }

    @RequestMapping(value = "/modifyUser")
    public ModelAndView modifyUser(@RequestParam(value = "new_pwd")String newPwd,
                                   @RequestParam(value = "old_pwd")String oldPwd,
                                   HttpServletRequest req){
        ModelAndView mav =new ModelAndView();
        Biz biz=new Biz();
        biz.setPwd(oldPwd);
        String loginName = (String) req.getSession().getAttribute("loginName");
        if (StringUtils.isEmpty(loginName)){
            mav.addObject("message","用户未登录");
            mav.setViewName("status");
            return mav;
        }
        biz.setName(loginName);
        boolean hasPwd=bizService.hasBiz(biz);
        if (hasPwd){
            bizService.updateBizPwd(loginName, Encryption.str2MD5(newPwd));
            mav.addObject("message","修改成功");
        }else {
            mav.addObject("message","原密码不正确");
        }
        mav.setViewName("status");

        return mav;
    }

    @RequestMapping(value = "toModifyMsg")
    public ModelAndView toModifyMsg(HttpServletRequest req){
        ModelAndView mav=new ModelAndView();
        String userName= (String) req.getSession().getAttribute("loginName");
        Biz biz = new Biz();
        biz.setName(userName);

        if (bizService.selectBizNameByExamle(biz).size()>0){
            biz=bizService.selectBizNameByExamle(biz).get(0);
        }

        mav.addObject("biz",biz);
        mav.setViewName("bizmsg");

        return mav;
    }

    @RequestMapping(value = "modifyMsg")
    public ModelAndView modifyMsg(String contantWay,String address,HttpServletRequest req){
        ModelAndView mav=new ModelAndView();

        String userName= (String) req.getSession().getAttribute("loginName");
        if (StringUtils.isEmpty(userName)){
            mav.addObject("message","用户未登录");
            mav.addObject("status",false);
            mav.setViewName("status");
            return mav;
        }
        bizService.updateMsg(userName,contantWay,address);
        mav.addObject("message","修改成功");
        mav.addObject("status",true);
        mav.setViewName("status");

        return mav;
    }


    @RequestMapping(value = "/index")
    public String toManageIndex() {
        return "bizmanager.html";
    }

}
