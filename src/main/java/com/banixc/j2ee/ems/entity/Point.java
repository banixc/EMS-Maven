package com.banixc.j2ee.ems.entity;

import com.banixc.j2ee.ems.base.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

import static com.banixc.j2ee.ems.framework.formatter.FloatFormatter.Formatter;

@Entity
public class Point extends BaseEntity{
    private BigDecimal percent;
    private int id;
    private String name;
    private Course course;
    private Collection<Mark> marks;
    public boolean f = false;

    @OneToMany(mappedBy = "point")
    public Collection<Mark> getMarks() {
        return marks;
    }

    public void setMarks(Collection<Mark> marks) {
        this.marks = marks;
    }

    @Basic
    @Column(name = "percent", nullable = false, precision = 2)
    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = Formatter(percent);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (id != point.id) return false;
        if (percent != null ? !percent.equals(point.percent) : point.percent != null) return false;
        if (name != null ? !name.equals(point.name) : point.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = percent != null ? percent.hashCode() : 0;
        result = 31 * result + id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String valid(){
        if(empty(name)) return "考核点名称不能为空";
        if(empty(percent)) return "考核点比重必须大于0";
        return null;
    }

    public BigDecimal getMarkByStudent(User student) {
        for (Mark mark:marks)
            if (mark.getPoint().getId() == id)
                return mark.getMark();
        return new BigDecimal("0");
    }
}
