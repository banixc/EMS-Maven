package com.banixc.j2ee.ems.controller;

import com.banixc.j2ee.ems.entity.User;
import com.banixc.j2ee.ems.framework.message.Result;
import com.banixc.j2ee.ems.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class BaseController {

    public static final String SESSION_MESSAGE = "SESSION_MESSAGE";
    protected static final String SESSION_USER_ID = "SESSION_USER_ID";
    public static final String SESSION_USER_ADMIN = "SESSION_USER_ADMIN";
    public static final String SESSION_LAST_REQUEST = "SESSION_LAST_REQUEST";
    private static final String ATTRIBUTE_CURRENT_USER = "current";
    private static final String DEFAULT_TITLE = "选课系统::";

    @Resource(name="userService")
    protected IUserService userService;

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected HttpSession session;

    protected User currentUser = null;

    protected String o = "操作";

    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        session = request.getSession();
        Object o = session.getAttribute(SESSION_USER_ID);

        if(o != null) {
            currentUser = userService.get((int) o);
            request.setAttribute(ATTRIBUTE_CURRENT_USER, currentUser);
        }

    }

    protected void success() {
        session.setAttribute(SESSION_MESSAGE, new Result(o+"成功!",Result.RESULT_SUCCESS));
    }

    protected void error(String r) {
        session.setAttribute(SESSION_MESSAGE, new Result(o + "失败," + r + "!",Result.RESULT_ERROR));
    }

    protected void setTitle(String title) {
        request.setAttribute("title", DEFAULT_TITLE + title);
    }

    protected String redirect() {
        String uri = (String) session.getAttribute(SESSION_LAST_REQUEST);
        return "redirect:" + ((uri==null)?"/":uri);
    }

    protected String redirect(String uri) {
        return "redirect:" + uri;
    }

    protected boolean isAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }

    protected void result(String result) {
        if (result == null)
            success();
        else
            error(result);
    }

}
