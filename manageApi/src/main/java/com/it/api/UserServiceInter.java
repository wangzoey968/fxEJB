package com.it.api;

/**
 * Created by wangzy on 2018/6/7.
 */
public interface UserServiceInter {

    public void test() throws Exception;

    public String getUserId(String uid)throws Exception;

    public String getUserNameByUserId(Long userId) throws Exception;

}
