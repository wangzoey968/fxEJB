package com.it.api;

import com.it.api.table.user.*;

import java.util.List;

/**
 * Created by wangzy on 2018/6/7.
 */
public interface UserServiceLocal {

    //user的操作
    public List<Tb_User> listUser(String sessionId, String key) throws Exception;

    public Tb_User addUser(String sessionId, Tb_User user) throws Exception;

    public Tb_User updateUser(String sessionId, Tb_User user) throws Exception;

    public void deleteUser(String sessionId, Long userId) throws Exception;

    //role的操作---------------------------
    public List<Tb_Role> listUserRole(String sessionId, Long userId) throws Exception;

    public Tb_Role addRole(String sessionId, Tb_Role role) throws Exception;

    public Tb_Role updateRole(String sessionId, Tb_Role role) throws Exception;

    public void deleteRole(String sessionId, Long roleId) throws Exception;

    public List<Tb_Role> listAllRole(String sessionId) throws Exception;

    //auth操作-------------------------------
    public List<Tb_Auth> listUserAuth(String sessionId, Long userId) throws Exception;

    public Tb_Auth addRole1Auth(String sessionId, Long roleId, Tb_Auth auth) throws Exception;

    public Tb_Auth addAuth(String sessionId, Tb_Auth auth) throws Exception;

    public Tb_Auth updateAuth(String sessionId, Tb_Auth auth) throws Exception;

    public void deleteAuth(String sessionId, Long authId) throws Exception;

    //获取某个role下的全部auth-----------------------
    public List<Tb_Auth> listRoleAuth(String sessionId, Long userId, Long roleId) throws Exception;

    //对tb_user_role操作
    public Tb_User_Role addUserRole(String sessionId, Long userId, Long roleId) throws Exception;

    public void deleteUserRole(String sessionId, Long userId, Long roleId) throws Exception;

    //对tb_role_auth操作
    public Tb_Role_Auth addRoleAuth(String sessionId, Long roleId, Long authId) throws Exception;

    public void deleteRoleAuth(String sessionId, Long roleId, Long authId) throws Exception;

    //对tb_user_auth操作
    public Tb_User_Auth addUserAuth(String sessionId, Long userId, Long authId, Boolean isExtend) throws Exception;

    public void deleteUserAuth(String sessionId, Long userId, Long authId) throws Exception;

}
