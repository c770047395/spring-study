package com.cp.dao;

import com.cp.pojo.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class UserMapperImpl extends SqlSessionDaoSupport implements UserMapper {
    //在原来所有操作都是用sqlSession来执行，现在都使用SqlSessionTemplate

    public List<User> selectUser() {

        addUser(new User(9,"xixix","123"));
        deleteUser(9);

        return getSqlSession().getMapper(UserMapper.class).selectUser();
    }

    public int addUser(User user) {
        return getSqlSession().getMapper(UserMapper.class).addUser(user);
    }

    public int deleteUser(int id) {
        return getSqlSession().getMapper(UserMapper.class).deleteUser(id);
    }
}
