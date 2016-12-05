package com.banixc.j2ee.ems.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

import static com.banixc.j2ee.ems.framework.formatter.FloatFormatter.Formatter;

@Entity
public class Course extends BaseEntity {
    private int id;
    private String name;
    private BigDecimal credit;
    private int num;
    private String teacherName;
    private SchoolYear schoolYear;
    private Collection<Point> points;
    private Collection<User> selectors;

    public boolean canSelect(User user) {
        return selectors.size() < num && !selectors.contains(user);
    }

    public boolean canCancel(User user) {
        return selectors.contains(user);
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "credit", nullable = false, precision = 2)
    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = Formatter(credit);
    }

    @Basic
    @Column(name = "num", nullable = false)
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Basic
    @Column(name = "teacher_name", nullable = false, length = 64)
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (id != course.id) return false;
        if (num != course.num) return false;
        if (name != null ? !name.equals(course.name) : course.name != null) return false;
        if (credit != null ? !credit.equals(course.credit) : course.credit != null) return false;
        if (teacherName != null ? !teacherName.equals(course.teacherName) : course.teacherName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (credit != null ? credit.hashCode() : 0);
        result = 31 * result + num;
        result = 31 * result + (teacherName != null ? teacherName.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "school_year_id", referencedColumnName = "id", nullable = false)
    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }

    @OneToMany(mappedBy = "course")
    public Collection<Point> getPoints() {
        return points;
    }

    public void setPoints(Collection<Point> points) {
        this.points = points;
    }

    @ManyToMany
    @JoinTable(name = "course_select", schema = "ems", joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false))
    public Collection<User> getSelectors() {
        return selectors;
    }

    public void setSelectors(Collection<User> selectors) {
        this.selectors = selectors;
    }

    public String valid(){
        if(empty(name)) return "课程名称不能为空";
        if(empty(teacherName)) return "老师名称不能为空";
        if(empty(credit)) return "学分必须大于0";
        return null;
    }

    //合并部分信息
    public void merge(Course course) {
        if(this.id != course.id) return;
        name = course.getName();
        teacherName = course.getTeacherName();
        credit = course.getCredit();
        schoolYear = course.getSchoolYear();
        num = course.getNum();
    }
}
