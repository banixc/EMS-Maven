package com.banixc.j2ee.ems.entity;

import com.banixc.j2ee.ems.base.BaseEntity;
import com.banixc.j2ee.ems.framework.util.MD5Util;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User extends BaseEntity{
    private int id;
    private String uid;
    private String name;
    private String passhash;
    private String phone;
    private String email;
    private boolean admin;
    private Collection<Mark> marks;
    private Collection<Course> courses;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "uid", nullable = false, length = 16)
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "passhash", nullable = false, length = 32)
    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 32)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 128)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "admin", nullable = false)
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (admin != user.admin) return false;
        if (uid != null ? !uid.equals(user.uid) : user.uid != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (passhash != null ? !passhash.equals(user.passhash) : user.passhash != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (passhash != null ? passhash.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (admin ? 1 : 0);
        return result;
    }

    @OneToMany(mappedBy = "user")
    public Collection<Mark> getMarks() {
        return marks;
    }

    public void setMarks(Collection<Mark> marksById) {
        this.marks = marksById;
    }

    @ManyToMany(mappedBy = "selectors")
    public Collection<Course> getCourses() {
        return courses;
    }

    public void setCourses(Collection<Course> courses) {
        this.courses = courses;
    }

    public Collection<Mark> getMarkByCourse(Course course) {
        List<Mark> marks = new ArrayList<>();
        for(Mark mark: this.marks) {
            if(course.getPoints().contains(mark.getPoint()))
                marks.add(mark);
        }
        return marks;
    }

    public Mark getMarkByPoint(Point point){
        for (Mark mark: marks ) {
            if(point.equals(mark.getPoint()))
                return mark;
        }
        return null;
    }

    public MarkSum getSumMarkByCourse(Course course){
        Collection<Mark> marks = getMarkByCourse(course);
        BigDecimal sum = new BigDecimal("0");
        MarkSum markSum = new MarkSum();
        markSum.setCourse(course);
        markSum.setUser(this);
        for(Mark mark: marks) {
            sum = sum.add(mark.getMark().multiply(mark.getPoint().getPercent()));
        }
        markSum.setMark(sum.multiply(new BigDecimal("0.01")));
        markSum.setCredit(markSum.getMark().multiply(course.getCredit()).multiply(new BigDecimal("0.01")));
        return markSum;
    }

    @Override
    public String valid() {
        if(empty(name)) return "姓名为空";
        if(empty(uid)) return "UID为空";
        if(empty(phone)) return "手机号为空";
        if(empty(email)) return "邮箱为空";
        return null;
    }

    public String getPassHash(String password) {
        return hashPass(uid,password);
    }

    public static String hashPass(String uid,String password) {
        return MD5Util.string2MD5(password + uid);
    }
}
