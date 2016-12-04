package com.banixc.j2ee.ems.service;

import com.banixc.j2ee.ems.dao.IUserDAO;
import com.banixc.j2ee.ems.entity.User;
import com.banixc.j2ee.ems.framework.util.Pagination;

import static com.banixc.j2ee.ems.base.BaseDAOImpl.DEFAULT_PAGE_ROWS;
import static com.banixc.j2ee.ems.entity.User.hashPass;

public class UserServiceImpl implements IUserService {

    private IUserDAO userDAO;

    public IUserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User login(String uid, String password) {
        return userDAO.get("from User where uid = ? and passhash = ?", new Object[] {uid, hashPass(uid,password)});
    }

    @Override
    public User get(int id) {
        return userDAO.get(User.class, id);
    }

    @Override
    public String add(User user) {
        String result = user.valid();
        if(result != null) return result;
        User temp = userDAO.get("from User where uid = ? ", new Object[] {user.getUid()});
        if(temp!=null)
            return "UID重复";
        userDAO.save(user);
        return null;
    }

    @Override
    public Pagination query(int page) {
        try {
            return userDAO.find("from User ", page, DEFAULT_PAGE_ROWS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Pagination();
    }

    @Override
    public Pagination query(int page, boolean admin) {
        try {
            Object[] params = {admin};
            return userDAO.find("from User user where user.admin = ?", params, page, DEFAULT_PAGE_ROWS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Pagination();
    }

    @Override
    public Pagination query(String s) {
        try {
            Object[] params = {'%'+s+'%','%'+s+'%'};
            return new Pagination(0L,0,userDAO.find("from User user where user.name like ? or user.uid like ?", params));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Pagination();
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public boolean remove(int id) {
        User user = userDAO.get(User.class, id);
        userDAO.delete(user);
        return userDAO.get(User.class, id) == null;
    }

    @Override
    public String reset(int id, String old_pass, String new_pass) {
        User user = userDAO.get(User.class,id);
        if(login(user.getUid(),old_pass) == null) return "您的密码不正确";
        else
            reset(id,new_pass);
        return null;
    }

    public void reset(int id, String pass) {
        User user = userDAO.get(User.class,id);
        user.setPasshash(user.getPassHash(pass));
        userDAO.update(user);
    }


}
