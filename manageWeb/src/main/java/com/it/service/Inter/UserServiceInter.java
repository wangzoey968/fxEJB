package com.it.service.Inter;

import com.it.entity.Tb_User;

import java.util.List;

/**
 * Created by wangzy on 2018/5/22.
 */
public interface UserServiceInter {

    public Tb_User getUser(Integer id);

    public List<Tb_User> getUsers();

}
