package com.banixc.j2ee.ems.dao;

import com.banixc.j2ee.ems.framework.util.Pagination;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class BaseDAOImpl<T> implements IBaseDao<T> {

    private SessionFactory sessionFactory;
    public static final int DEFAULT_PAGE_ROWS = 20;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Serializable save(T o) {
        Session session = sessionFactory.getCurrentSession();
        return session.save(o);
    }

    @Override
    public void delete(T o) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(o);
    }

    @Override
    public void update(T o) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(o);
    }

    @Override
    public void saveOrUpdate(T o) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(o);
    }

    @Override
    public List<T> find(String hql) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(hql).list();
    }

    @Override
    public List<T> find(String hql, Object[] params) {
        Session session = sessionFactory.getCurrentSession();
        Query q = null;
        try {
            q = session.createQuery(hql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert q!=null;
        if (null != params && params.length > 0) {
            int i = 0;
            for (Object o:params)
                q.setParameter(i++, o);
        }

        return q.list();
    }

    @Override
    public List<T> find(String hql, List<Object> params) {
        return find(hql, params.toArray(new Object[params.size()]));
    }

    @Override
    public Pagination find(String hql, Integer page, Integer rows) throws Exception {
        return find(hql, new Object[0], page, rows);
    }

    @Override
    public Pagination find(String hql, Object[] params, Integer page, Integer rows) throws Exception {

        if (null == page || page < 1)
            page = 1;
        if (null == rows || rows < 1)
            rows = DEFAULT_PAGE_ROWS;

        int indexFrom = hql.indexOf("from");

        if (indexFrom == -1) {
            throw new Exception("无效HQL语句,没有包含 from");
        }

        String countHql = "select count(*) " + hql.substring(indexFrom);

        Query qc = this.getCurrentSession().createQuery(countHql);

        if (null != params && params.length > 0) {
            int i = 0;
            for (Object o : params)
                qc.setParameter(i++, o);
        }

        long total = ((Long) qc.uniqueResult()).longValue();

        if (total < 1L)
            return new Pagination(0L, 0, Collections.EMPTY_LIST);

        Query q = getCurrentSession().createQuery(hql);

        if (null != params && params.length > 0) {
            int i = 0;
            for (Object o:params)
                q.setParameter(i++, o);
        }

        List list = q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();

        return new Pagination(total, page, list);

    }

    @Override
    public Pagination find(String hql, List<Object> params, Integer page, Integer rows) throws Exception {
        Object[] objects = params == null? new Object[0] : new Object[params.size()];

        return find(hql,objects, page, rows);
    }

    @Override
    public T get(Class<T> c, Serializable id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(c, id);

    }

    @Override
    public T get(String hql, Object[] params) {
        List<T> list = find(hql, params);
        if (null != list && list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    @Override
    public T get(String hql, List<Object> params) {
        List<T> list = find(hql, params);
        if (null != list && list.size() > 0)
            return list.get(0);
        else
            return null;
    }
}
