package com.it.service.Impl;

import com.it.dao.inter.UserDaoInter;
import com.it.entity.Tb_User;
import com.it.service.Inter.UserServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangzy on 2018/5/22.
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserServiceInter {

    /**
     * spring进行注入的时候默认的是使用接口,jdk动态代理,为了解耦
     */
    @Autowired
    UserDaoInter userDaoImpl;

    @Override
    public Tb_User getUser(Integer id) {
        return userDaoImpl.getUser(id);
    }

    @Override
    public List<Tb_User> getUsers() {
        return userDaoImpl.getUsers();
    }

}
