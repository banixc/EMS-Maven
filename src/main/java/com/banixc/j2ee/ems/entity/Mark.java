package com.banixc.j2ee.ems.entity;

import com.banixc.j2ee.ems.base.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

import static com.banixc.j2ee.ems.framework.formatter.FloatFormatter.Formatter;

@Entity
public class Mark extends BaseEntity{
    private BigDecimal mark;
    private int id;
    private User user;
    private Point point;

    public boolean f = false;

    @Basic
    @Column(name = "mark", nullable = false, precision = 2)
    public BigDecimal getMark() {
        return mark;
    }

    public void setMark(BigDecimal mark) {
        this.mark = Formatter(mark);
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mark mark1 = (Mark) o;

        if (id != mark1.id) return false;
        if (mark != null ? !mark.equals(mark1.mark) : mark1.mark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mark != null ? mark.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "point_id", referencedColumnName = "id", nullable = false)
    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public String valid() {
        if(mark.compareTo(new BigDecimal("0"))==-1) return "分数必须大于或等于0";
        if(point==null) return "没有分数类型";
        if(user==null) return "没有分数主体";
        return null;
    }
}
