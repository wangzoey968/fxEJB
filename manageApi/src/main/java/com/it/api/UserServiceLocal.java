package com.it.api;

import com.it.api.table.user.Tb_Auth;
import com.it.api.table.user.Tb_Role;
import com.it.api.table.user.Tb_User;
import com.it.api.table.user.Tb_User_Role;

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

    //role的操作
    public List<Tb_Role> listUserRole(String sessionId, Long userId) throws Exception;

    public Tb_Role addRole(String sessionId, Tb_Role role) throws Exception;

    public Tb_Role updateRole(String sessionId, Tb_Role role) throws Exception;

    public void deleteRole(String sessionId, Long roleId) throws Exception;

    public List<Tb_Role> listAllRole(String sessionId) throws Exception;

    //auth操作
    public List<Tb_Auth> listUserAuth(String sessionId, Long userId) throws Exception;

    public Tb_Auth addAuth(String sessionId, Tb_Auth auth) throws Exception;

    public Tb_Auth updateAuth(String sessionId, Tb_Auth auth) throws Exception;

    public void deleteAuth(String sessionId, Long authId) throws Exception;

    //获取某个role下的全部auth
    public List<Tb_Auth> listRoleAuth(String sessionId, Long roleId) throws Exception;

    //为用户分配角色,或者为用户移除某个角色,根据isAppend的值
    public Tb_User_Role addUserRole(String sessionId, Long userId, Long roleId) throws Exception;

    public void deleteUserRole(String sessionId, Long userId, Long roleId) throws Exception;

}
