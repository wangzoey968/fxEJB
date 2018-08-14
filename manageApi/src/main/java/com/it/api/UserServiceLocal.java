package com.it.api;

import com.it.api.table.user.Tb_User;

import java.util.List;

/**
 * Created by wangzy on 2018/6/7.
 */
public interface UserServiceLocal {

    public Long getUserIdBySession(String sessionId) throws Exception;

    public String getUsernameBySession(String sessionId) throws Exception;

    public String getUsernameByUserId(Long userId) throws Exception;

    public List<Tb_User> listUser(String sessionId, String key) throws Exception;

    public Tb_User addUser(String sessionId, Tb_User user) throws Exception;

    public Tb_User updateUser(String sessionId, Tb_User user) throws Exception;

    public void deleteUser(String sessionId, Long userId) throws Exception;

}
