package com.it.api;

/**
 * Created by wangzy on 2018/8/22.
 */
public interface CoreServiceLocal {

    public Long getUserIdBySession(String sessionId) throws Exception;

    public String getUsernameBySession(String sessionId) throws Exception;

    public String getUsernameById(Long userId) throws Exception;

}
