package com.cp.service;

import com.cp.dao.UserDao;
import com.cp.dao.UserDaoImpl;
import com.cp.dao.UserDaoMysqlImpl;
import com.cp.dao.UserDaoOracleImpl;

public class UserServiceImpl implements UserService {
//    private UserDao userDao = new UserDaoOracleImpl();
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void getUser() {
        userDao.getUser();
    }
}
