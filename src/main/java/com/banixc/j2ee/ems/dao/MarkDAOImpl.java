package com.banixc.j2ee.ems.dao;

import com.banixc.j2ee.ems.base.BaseDAOImpl;
import com.banixc.j2ee.ems.entity.Mark;
import com.banixc.j2ee.ems.entity.Point;
import com.banixc.j2ee.ems.entity.User;
import org.hibernate.Session;

import java.util.List;

public class MarkDAOImpl extends BaseDAOImpl<Mark> implements IMarkDAO {

    @Override
    public void saveOrUpdate(Mark o) {
        Session session = getSessionFactory().getCurrentSession();
        User user = session.get(User.class,o.getUser().getId());
        if(user == null) return;

        //查找
        for(Mark mark:user.getMarks()) {
            if(mark.getPoint().getId() == o.getPoint().getId()) {
                mark.setMark(o.getMark());
                session.update(mark);
                return;
            }
        }
        //新建
        o.setPoint(session.get(Point.class,o.getPoint().getId()));
        o.setUser(user);
        session.save(o);
    }
}
