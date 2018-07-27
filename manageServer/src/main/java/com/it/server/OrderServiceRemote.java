package com.it.server;

import com.it.api.OrderServiceLocal;

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
public class OrderServiceRemote implements OrderServiceLocal {

    @Override
    public void test() throws Exception {
        System.out.println("this is test of OrderServiceLocal");
    }

}
