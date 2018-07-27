package com.it.server;

import com.it.api.UserServiceLocal;

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
public class UserServiceRemote implements UserServiceLocal {

    @Override
    public void test() throws Exception {
        System.out.println("this is test of OrderServiceLocal");
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
