package com.banixc.j2ee.ems.entity;

import java.math.BigDecimal;

import static com.banixc.j2ee.ems.framework.formatter.FloatFormatter.Formatter;

public class MarkSum {
    private BigDecimal mark;
    private Course course;
    private User user;
    private BigDecimal credit;

    public MarkSum(BigDecimal mark, Course course, User user, BigDecimal credit) {
        this.mark = mark;
        this.course = course;
        this.user = user;
        this.credit = credit;
    }

    public MarkSum() {
    }

    public BigDecimal getMark() {
        return mark;
    }

    public void setMark(BigDecimal mark) {
        this.mark = Formatter(mark);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = (mark.compareTo(new BigDecimal("60"))==-1)?new BigDecimal(0):Formatter(credit);
    }
}
