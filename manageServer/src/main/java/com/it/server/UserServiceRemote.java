package com.it.server;

import com.it.api.UserServiceLocal;
import com.it.api.table.user.Tb_Auth;
import com.it.api.table.user.Tb_Role;
import com.it.api.table.user.Tb_User;
import com.it.web.user.service.Core;
import com.it.web.user.service.UserService;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import java.util.List;

/**
 * Created by wangzy on 2018/6/7.
 */
@Stateless
@Remote
@TransactionManagement(TransactionManagementType.BEAN)
public class UserServiceRemote implements UserServiceLocal {

    @Override
    public Long getUserIdBySession(String sessionId) throws Exception {
        Tb_User user = Core.getUser(sessionId);
        return user.getId();
    }

    @Override
    public String getUsernameBySession(String sessionId) throws Exception {
        Tb_User user = Core.getUser(sessionId);
        return user.getUsername();
    }

    @Override
    public String getUsernameById(Long userId) throws Exception {
        return Core.getUsername(userId);
    }

    @Override
    public List<Tb_User> listUser(String sessionId, String key) throws Exception {
        return UserService.listUser(Core.getUser(sessionId), key);
    }

    @Override
    public Tb_User addUser(String sessionId, Tb_User user) throws Exception {
        return UserService.insertUser(Core.getUser(sessionId), user);
    }

    @Override
    public Tb_User updateUser(String sessionId, Tb_User user) throws Exception {
        return UserService.updateUser(Core.getUser(sessionId), user);
    }

    @Override
    public void deleteUser(String sessionId, Long userId) throws Exception {
        UserService.deleteUser(Core.getUser(sessionId), userId);
    }

    @Override
    public List<Tb_Role> listUserRole(String sessionId, Long userId) throws Exception {
        return UserService.listUserRole(Core.getUser(sessionId), userId);
    }

    @Override
    public Tb_Role addRole(String sessionId, Tb_Role role) throws Exception {
        return UserService.addRole(Core.getUser(sessionId), role);
    }

    @Override
    public Tb_Role updateRole(String sessionId, Tb_Role role) throws Exception {
        return UserService.updateRole(Core.getUser(sessionId), role);
    }

    @Override
    public void deleteRole(String sessionId, Long roleId) throws Exception {
        UserService.deleteRole(Core.getUser(sessionId), roleId);
    }

    @Override
    public List<Tb_Role> listAllRole(String sessionId) throws Exception {
        return UserService.listAllRole(Core.getUser(sessionId));
    }

    //auth操作
    @Override
    public List<Tb_Auth> listUserAuth(String sessionId, Long userId) throws Exception {
        return UserService.listAuth(Core.getUser(sessionId), userId);
    }

    @Override
    public Tb_Auth addAuth(String sessionId, Tb_Auth auth) throws Exception {
        return UserService.addAuth(Core.getUser(sessionId), auth);
    }

    @Override
    public Tb_Auth updateAuth(String sessionId, Tb_Auth auth) throws Exception {
        return UserService.updateAuth(Core.getUser(sessionId), auth);
    }

    @Override
    public void deleteAuth(String sessionId, Long authId) throws Exception {
        UserService.deleteAuth(Core.getUser(sessionId), authId);
    }

    @Override
    public List<Tb_Auth> listRoleAuth(String sessionId, Long roleId) throws Exception {
        return UserService.listRoleAuth(Core.getUser(sessionId), roleId);
    }

    //为用户分配角色
    @Override
    public void assignRole(String sessionId, Boolean isAppend, Long userId, Long roleId) throws Exception {
        UserService.assignRole(Core.getUser(sessionId), isAppend, userId, roleId);
    }
}
