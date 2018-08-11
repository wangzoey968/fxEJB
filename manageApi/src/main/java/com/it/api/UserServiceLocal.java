package com.it.api;

/**
 * Created by wangzy on 2018/6/7.
 */
public interface UserServiceLocal {

    public Long getUserIdBySession(String sessionId)throws Exception;

    public String getUsernameBySession(String sessionId)throws Exception;

    public String getUsernameByUserId(Long userId) throws Exception;

}
