package com.banixc.j2ee.ems.controller;


import com.banixc.j2ee.ems.framework.auth.Auth;
import com.banixc.j2ee.ems.base.BaseController;
import com.banixc.j2ee.ems.entity.Course;
import com.banixc.j2ee.ems.entity.Mark;
import com.banixc.j2ee.ems.entity.Point;
import com.banixc.j2ee.ems.entity.SchoolYear;
import com.banixc.j2ee.ems.service.ICourseService;
import com.banixc.j2ee.ems.framework.util.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import static com.banixc.j2ee.ems.framework.auth.Auth.*;


@Controller
@RequestMapping("/course")
public class CourseController extends BaseController {

    @Resource(name = "courseService")
    private ICourseService courseService;

    //all_user
    //显示所有课程
    @RequestMapping("/list/{page:[\\d]+}")
    @Auth(AUTH_USER_LOGIN)
    public String list(@PathVariable("page") int page) {
        request.setAttribute("schoolYears", courseService.getSchoolYearList());
        Pagination pagination = courseService.query(page);
        request.setAttribute("pagination", pagination);
        setTitle("课程列表::第" + page + "页");
        return "course_list";
    }

    //搜索
    @RequestMapping("/search/{s}")
    @Auth(AUTH_USER_LOGIN)
    public String search(@PathVariable("s") String s) throws UnsupportedEncodingException {
        s = URLDecoder.decode(s, "UTF-8");
        Pagination p = courseService.query(s);
        request.setAttribute("pagination", p);
        request.setAttribute("schoolYears", courseService.getSchoolYearList());
        setTitle("课程列表::搜索[" + s + "]共" + p.getList().size() + "个结果");
        request.setAttribute("search", s);
        return "course_list";
    }

    //显示某一学年的课程
    @RequestMapping("/year/{year:[\\d]+}/list/{page:[\\d]+}")
    @Auth(AUTH_USER_LOGIN)
    public String list(@PathVariable("year") int year, @PathVariable("page") int page) {
        o = "查找";
        SchoolYear schoolYear = courseService.getSchoolYear(year);
        if (schoolYear == null) {
            error("没有找到ID:" + year + "的学年");
            return redirect();
        } else {
            request.setAttribute("schoolYears", courseService.getSchoolYearList());
            request.setAttribute("schoolYear", schoolYear);
            Pagination pagination = courseService.findBySchoolYear(schoolYear, page);
            request.setAttribute("pagination", pagination);
            setTitle("课程列表::第" + year + "学年::第" + page + "页");
            return "course_list";
        }
    }

    //显示某个课程详细信息
    @RequestMapping("/{id:[\\d]+}")
    @Auth(AUTH_USER_LOGIN)
    public String detail(@PathVariable("id") int id) {
        o = "查找";
        Course course = courseService.get(id);
        if (null == course) {
            error("没有找到ID:" + id + "的课程");
            return redirect();
        } else {
            request.setAttribute("schoolYears", courseService.getSchoolYearList());
            request.setAttribute("course", course);
            setTitle("课程详情::" + course.getName());
            return "course_detail";
        }
    }

    //only_admin
    //添加
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Auth(AUTH_USER_ADMIN)
    public String add(Course course) {
        o = "添加课程";
        result(courseService.add(course));
        return redirect();
    }

    //编辑课程信息
    @RequestMapping(value = "/edit/message", method = RequestMethod.POST)
    @Auth(AUTH_USER_ADMIN)
    public String edit(Course course) {
        o = "保存课程信息";
        result(courseService.update(course));
        return redirect("/course/" + course.getId());
    }

    //编辑课程信息
    @RequestMapping(value = "/edit/point/{id:[\\d]+}", method = RequestMethod.POST)
    @Auth(AUTH_USER_ADMIN)
    @ResponseBody
    public String point(@PathVariable("id") int id, @RequestBody List<Point> points) {
        o = "保存课程考核点";
        Course course = courseService.get(id);
        if (null == course) {
            error("找不到当前课程");
        }
        result(courseService.updatePoint(points, course));
        return "";
    }

    //保存分数
    @RequestMapping(value = "/mark/save", method = RequestMethod.POST)
    @Auth(AUTH_USER_ADMIN)
    @ResponseBody
    public String mark(@RequestBody List<Mark> marks) {
        o = "保存分数";
        result(courseService.saveMark(marks));
        return "";
    }

    //添加学年
    @RequestMapping(value = "/year/add")
    @Auth(AUTH_USER_ADMIN)
    public String year_add(SchoolYear schoolYear) {
        o = "添加学年";
        result(courseService.add_year(schoolYear));
        return redirect();
    }

    //编辑学年
    @RequestMapping(value = "/year/edit")
    @Auth(AUTH_USER_ADMIN)
    public String year_edit(SchoolYear schoolYear) {
        o = "编辑学年信息";
        result(courseService.edit_year(schoolYear));
        return redirect();
    }

    //删除学年
    @RequestMapping(value = "/year/remove/{id:[\\d]+}")
    @Auth(AUTH_USER_ADMIN)
    public String year_remove(@PathVariable("id") int id) {
        o = "删除学年";
        String result = courseService.remove_year(id);
        if (result == null) {
            success();
            return redirect("/course/list/1");
        } else {
            error(result);
            return redirect();
        }
    }

    //only_student
    //已选课列表
    @RequestMapping("/select")
    @Auth(AUTH_USER_STUDENT)
    public String select() {
        setTitle("选课列表");
        request.setAttribute("pagination", courseService.query(currentUser));
        request.setAttribute("is_select_page", true);
        return "course_list";
    }

    //学生选课
    @RequestMapping("/select/{id:[\\d]+}")
    @Auth(AUTH_USER_STUDENT)
    public String select(@PathVariable("id") int id) {
        o = "选课ID:" + id;
        result(courseService.select(id, currentUser));
        return redirect();
    }

    //取消选课
    @RequestMapping("/cancel/{id:[\\d]+}")
    @Auth(AUTH_USER_STUDENT)
    public String cancel(@PathVariable("id") int id) {
        o = "退课ID:" + id;
        result(courseService.cancel(id, currentUser));
        return redirect();
    }


}
