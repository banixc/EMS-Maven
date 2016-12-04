package com.banixc.j2ee.ems.service;

import com.banixc.j2ee.ems.entity.*;
import com.banixc.j2ee.ems.framework.util.Pagination;

import java.util.Collection;

public interface ICourseService {
    String add(Course course);
    Course get(int id);
    Pagination query(int page);
    Pagination query(User student);
    Pagination query(String s);
    Pagination findBySchoolYear(SchoolYear schoolYear, int page);
    String select(int course_id, User student);
    String cancel(int course_id, User student);
    String update(Course course);
    String updatePoint(Collection<Point> points, Course course);
    String saveMark(Collection<Mark> marks);
    Collection<SchoolYear> getSchoolYearList();
    SchoolYear getSchoolYear(int id);
    String add_year(SchoolYear schoolYear);
    String edit_year(SchoolYear schoolYear);
    String remove_year(int id);
}
