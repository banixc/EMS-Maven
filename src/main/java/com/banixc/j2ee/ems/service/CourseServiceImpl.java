package com.banixc.j2ee.ems.service;

import com.banixc.j2ee.ems.dao.*;
import com.banixc.j2ee.ems.entity.*;
import com.banixc.j2ee.ems.framework.util.Pagination;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import static com.banixc.j2ee.ems.base.BaseDAOImpl.DEFAULT_PAGE_ROWS;

public class CourseServiceImpl implements ICourseService {

    private ICourseDAO courseDAO;

    private ISchoolYearDAO schoolYearDAO;

    private IPointDAO pointDAO;

    private IMarkDAO markDAO;

    private IUserDAO userDAO;

    public IUserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public IMarkDAO getMarkDAO() {
        return markDAO;
    }

    public void setMarkDAO(IMarkDAO markDAO) {
        this.markDAO = markDAO;
    }

    public IPointDAO getPointDAO() {
        return pointDAO;
    }

    public void setPointDAO(IPointDAO pointDAO) {
        this.pointDAO = pointDAO;
    }

    public ISchoolYearDAO getSchoolYearDAO() {
        return schoolYearDAO;
    }

    public void setSchoolYearDAO(ISchoolYearDAO schoolYearDAO) {
        this.schoolYearDAO = schoolYearDAO;
    }

    public ICourseDAO getCourseDAO() {
        return courseDAO;
    }

    public void setCourseDAO(ICourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Override
    public String add(Course course) {
        String result = course.valid();
        if(result != null) return result;
        else {
            Serializable id = courseDAO.save(course);
            if(id != null) return null;
            else
                return "未知错误";
        }
    }

    @Override
    public Course get(int id) {
        return courseDAO.get(Course.class, id);
    }

    @Override
    public Pagination query(int page) {
        try {
            return courseDAO.find("from Course", page, DEFAULT_PAGE_ROWS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Pagination();
    }

    @Override
    public Pagination findBySchoolYear(SchoolYear schoolYear, int page) {
        try {
            Object[] params = {schoolYear};
            return courseDAO.find("from Course course where course.schoolYear = ?", params, page, DEFAULT_PAGE_ROWS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Pagination();
    }

    @Override
    public Pagination query(User student) {
        return new Pagination(0L,0, student.getCourses());
    }

    @Override
    public Pagination query(String s) {
        try {
            Object[] params = {'%'+s+'%','%'+s+'%'};
            return new Pagination(0L,0,courseDAO.find("from Course course where course.name like ? or course.teacherName like ?", params));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Pagination();
    }

    @Override
    public String select(int course_id, User student) {
        Course course = get(course_id);
        if (null == course) return "尚未开设该课程";
        if (course.getSelectors().contains(student)) return "您已选择该课程";
        if (course.getSelectors().size() >= course.getNum()) return "该课程选课人数已满";
        course.getSelectors().add(student);
        courseDAO.save(course);
        return null;
    }

    @Override
    public String cancel(int course_id, User student) {
        Course course = get(course_id);
        if (null == course) return "尚未开设该课程";
        if( course.getSelectors().remove(student)) {
            courseDAO.save(course);
            return null;
        } else {
            return "尚未选修该课程";
        }

    }

    @Override
    public String update(Course course) {
        String r = course.valid();
        if(r==null) {
            course.setSchoolYear(schoolYearDAO.get(SchoolYear.class,course.getSchoolYear().getId()));
            Course old = courseDAO.get(Course.class,course.getId());
            old.merge(course);
            courseDAO.update(old);
        }
        return r;
    }

    @Override
    public String updatePoint(Collection<Point> points, Course course) {
        BigDecimal decimal = new BigDecimal("-100.00");
        for (Point point: points) {
            String r = point.valid();
            if(r != null) return r;
            decimal = decimal.add(point.getPercent());
        }
        if(decimal.abs().compareTo(new BigDecimal("0.02")) == 1)
            return "考核点比重之和不为100%";

        for (Point point : points) {
            point.setCourse(course);
            for (Point p : course.getPoints())
                if (p.getId() == point.getId()) {
                    //合并
                    p.f = true;
                    point.f = true;
                    p.setName(point.getName());
                    p.setPercent(point.getPercent());
                    break;
                }
        }

        for (Point p : course.getPoints()) {
            if(!p.f)
                pointDAO.delete(p);
        }

        course.getPoints().removeIf(p -> !p.f);

        points.removeIf(p -> p.f);
        course.getPoints().addAll(points);
        for (Point point:course.getPoints()) {
            pointDAO.saveOrUpdate(point);
        }
        return null;
    }

    @Override
    public String saveMark(Collection<Mark> marks) {
        for (Mark mark:marks) {
            String result = mark.valid();
            if(result!=null) return result;
        }
        for (Mark mark:marks) {
            markDAO.saveOrUpdate(mark);
        }
        return null;
    }

    @Override
    public List<SchoolYear> getSchoolYearList() {
        return schoolYearDAO.find("from SchoolYear");
    }

    @Override
    public SchoolYear getSchoolYear(int id) {
        return schoolYearDAO.get(SchoolYear.class, id);
    }

    @Override
    public String add_year(SchoolYear schoolYear) {
        String result = schoolYear.valid();
        if(result != null) return result;
        schoolYearDAO.save(schoolYear);
        return null;
    }

    @Override
    public String edit_year(SchoolYear schoolYear) {
        String result = schoolYear.valid();
        if(result != null) return result;
        schoolYearDAO.update(schoolYear);
        return null;
    }

    @Override
    public String remove_year(int id) {
        SchoolYear schoolYear = schoolYearDAO.get(SchoolYear.class,id);
        if(schoolYear == null) return "找不到这个学年";
        schoolYearDAO.delete(schoolYear);
        return null;
    }

}
