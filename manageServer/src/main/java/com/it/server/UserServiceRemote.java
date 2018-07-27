package com.it.server;

import com.it.api.UserServiceInter;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 * Created by wangzy on 2018/6/7.
 */
@Stateless
@Remote
@TransactionManagement(TransactionManagementType.BEAN)
public class UserServiceRemote implements UserServiceInter {

    @Override
    public void test() throws Exception {
        System.out.println("this is test of OrderServiceInter");
    }

    @Override
    public String getUserId(String uid) throws Exception {
        return null;
    }

    @Override
    public String getUserNameByUserId(Long userId) throws Exception {
        return null;
    }

}
