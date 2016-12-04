package com.banixc.j2ee.ems.controller;

import com.banixc.j2ee.ems.framework.auth.Auth;
import com.banixc.j2ee.ems.base.BaseController;
import com.banixc.j2ee.ems.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.banixc.j2ee.ems.framework.auth.Auth.AUTH_USER_LOGIN;
import static com.banixc.j2ee.ems.framework.auth.Auth.AUTH_USER_UNLOGIN;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class MainController extends BaseController {

    @RequestMapping("/")
    @Auth(AUTH_USER_LOGIN)
    public String index() {
        return redirect("/course/list/1");
    }


    @RequestMapping(value = "/login", method = GET)
    @Auth(AUTH_USER_UNLOGIN)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/login", method = POST)
    @Auth(AUTH_USER_UNLOGIN)
    public String login(String uid, String password){
        User user = userService.login(uid ,password);
        if(null == user) {
            error("登陆失败，用户名或密码错误！");
            return redirect("/login");
        } else {
            session.setAttribute(SESSION_USER_ID,user.getId());
            session.setAttribute(SESSION_USER_ADMIN,user.isAdmin());
            return redirect("/");
        }
    }

    @RequestMapping("/logout")
    @Auth(AUTH_USER_LOGIN)
    public String logout(){
        session.removeAttribute(SESSION_USER_ID);
        session.removeAttribute(SESSION_USER_ADMIN);
        return redirect("/");
    }
}
