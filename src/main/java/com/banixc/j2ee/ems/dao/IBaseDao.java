package com.banixc.j2ee.ems.dao;


import com.banixc.j2ee.ems.framework.util.Pagination;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<T> {

    Serializable save(T o);
    void delete(T o);
    void update(T o);
    void saveOrUpdate(T o);

    List<T> find(String hql);
    List<T> find(String hql, Object[] params);
    List<T> find(String hql, List<Object> params);

    Pagination find(String hql, Integer page, Integer rows) throws Exception;
    Pagination find(String hql, Object[] params, Integer page, Integer rows) throws Exception;
    Pagination find(String hql, List<Object> params, Integer page, Integer rows) throws Exception;

    T get(Class<T> c, Serializable id);
    T get(String hql, Object[] params);
    T get(String hql, List<Object> params);

}