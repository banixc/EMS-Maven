package com.banixc.j2ee.ems.controller;

import com.banixc.j2ee.ems.framework.auth.Auth;
import com.banixc.j2ee.ems.entity.User;
import com.banixc.j2ee.ems.framework.util.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.banixc.j2ee.ems.framework.auth.Auth.AUTH_USER_ADMIN;
import static com.banixc.j2ee.ems.framework.auth.Auth.AUTH_USER_LOGIN;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    //all_user

    //用户的详细信息
    @RequestMapping
    @Auth(AUTH_USER_LOGIN)
    public String detail(){
        return detail(currentUser.getId());
    }

    //查看某个用户信息
    @RequestMapping("/{id:[\\d]+}")
    @Auth(AUTH_USER_LOGIN)
    public String detail(@PathVariable("id") int id){
        o="访问";
        User user = userService.get(id);
        if (null == user) {
            error("没有找到ID:" + id + "的用户");
            return redirect();
        } else {
            request.setAttribute("user", user);
            setTitle("用户详情::" + user.getName());
            return "user_detail";
        }
    }

    //only_admin
    //显示所有用户
    @RequestMapping("/list/{page:[\\d]+}")
    @Auth(AUTH_USER_ADMIN)
    public String list(@PathVariable("page") int page){
        Pagination pagination = userService.query(page);
        setTitle("用户列表::第" + page + "页");
        request.setAttribute("pagination",pagination);
        return "user_list";
    }

    //显示管理员用户
    @RequestMapping("/admin/list/{page:[\\d]+}")
    @Auth(AUTH_USER_ADMIN)
    public String list_admin(@PathVariable("page") int page){
        Pagination pagination = userService.query(page, true);
        setTitle("用户列表::管理员::第" + page + "页");
        request.setAttribute("pagination",pagination);
        request.setAttribute("admin", true);
        return "user_list";
    }

    //显示学生用户
    @RequestMapping("/student/list/{page:[\\d]+}")
    @Auth(AUTH_USER_ADMIN)
    public String list_student(@PathVariable("page") int page){
        Pagination pagination = userService.query(page,false);
        setTitle("用户列表::学生::第" + page + "页");
        request.setAttribute("pagination",pagination);
        request.setAttribute("admin", false);
        return "user_list";
    }



    //搜索
    @RequestMapping("/search/{s}")
    @Auth(AUTH_USER_LOGIN)
    public String search(@PathVariable("s") String s) throws UnsupportedEncodingException {
        s = URLDecoder.decode(s,"UTF-8");
        Pagination p = userService.query(s);
        request.setAttribute("pagination", p);
        setTitle("用户列表::搜索[" + s + "]共" + p.getList().size() + "个结果");
        request.setAttribute("search", s);
        return "user_list";
    }

    //编辑某个用户信息
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @Auth(AUTH_USER_LOGIN)
    public String edit(User user){
        o = "保存信息";
        //两种编辑方式
        if(isAdmin() || user.getId() == currentUser.getId()) {
            //管理员的编辑方式
            userService.update(user);
            success();
        } else {
            //非管理员的编辑方式
            error("没有这个权限");
        }
        return redirect("/user/" + user.getId());
    }

    //删除某个用户
    @RequestMapping(value = "/remove/{id:[\\d]+}")
    @Auth(AUTH_USER_ADMIN)
    public String remove(@PathVariable("id") int id){
        o = "删除用户ID:" + id;
        if(userService.remove(id))
            success();
        else
            error("该用户可能还关联其他信息");
        return redirect("/user/list/1");
    }

    @RequestMapping("/add")
    @Auth(AUTH_USER_ADMIN)
    public String add(User user){
        o = "添加用户";
        result(userService.add(user));
        return redirect();
    }


    //重置密码
    @RequestMapping(value = "/password")
    @Auth(AUTH_USER_LOGIN)
    public String reset(int id, String old_pass, String new_pass){
        o = "修改密码";
        if(old_pass==null || old_pass.isEmpty()) {
            error("您的密码不能为空");
        } else if(new_pass==null || new_pass.isEmpty()) {
            error("新密码不能为空");
        } else if(isAdmin()) {
            //管理员需要输入自己的密码
            if(userService.login(currentUser.getUid(),old_pass) == null)
                error("您的密码不正确");
            else {
            userService.reset(id, new_pass);
            success();
            }
        } else if(id != currentUser.getId()){
            error("没有这个权限");
        } else {
            result(userService.reset(id,old_pass,new_pass));
        }
        return redirect("/user/" + id);
    }


}
