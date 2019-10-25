package com.cp.dao;

import com.cp.pojo.User;

import java.util.List;

public interface UserMapper {
    List<User> selectUser();
}
