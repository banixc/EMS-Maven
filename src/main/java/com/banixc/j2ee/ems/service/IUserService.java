package com.banixc.j2ee.ems.service;

import com.banixc.j2ee.ems.entity.User;
import com.banixc.j2ee.ems.framework.util.Pagination;

public interface IUserService {
    User login(String uid, String password);
    User get(int id);
    String add(User user);
    Pagination query(int page);
    Pagination query(int page, boolean admin);
    Pagination query(String s);
    void update(User user);
    boolean remove(int id);
    String reset(int id, String old_pass, String new_pass);
    void reset(int id, String pass);
}
