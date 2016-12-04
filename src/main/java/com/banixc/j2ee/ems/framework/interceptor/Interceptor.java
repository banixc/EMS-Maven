package com.banixc.j2ee.ems.framework.interceptor;

import com.banixc.j2ee.ems.framework.auth.Auth;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.banixc.j2ee.ems.framework.auth.Auth.*;
import static com.banixc.j2ee.ems.base.BaseController.SESSION_LAST_REQUEST;
import static com.banixc.j2ee.ems.base.BaseController.SESSION_USER_ADMIN;


public class Interceptor extends HandlerInterceptorAdapter {

    public static final String REDIRECT_UNLOGIN_URI = "/login";
    public static final String REDIRECT_LOGIN_URI = "/";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;

        //权限验证

        if (handler instanceof HandlerMethod) {

            Auth auth = ((HandlerMethod) handler).getMethod().getAnnotation(Auth.class);
            if (auth == null) return true;
            if (auth.value() > 0) {
                Object o = request.getSession().getAttribute(SESSION_USER_ADMIN);
                flag = null != o && (auth.value() & (((boolean) o) ? AUTH_USER_ADMIN : AUTH_USER_STUDENT)) != 0;
            } else {
                flag = null == request.getSession().getAttribute(SESSION_USER_ADMIN);
            }

            if (!flag) {
                response.sendRedirect(auth.value()>0?REDIRECT_UNLOGIN_URI:REDIRECT_LOGIN_URI);
            }
        }


        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

            //保存上次的请求链接
        if(!"GET".equals(request.getMethod())) return;
        if(request.getRequestURI().matches("/static/.*")) return;
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_LAST_REQUEST,request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,Object handler,Exception ex) throws Exception{


    }
}