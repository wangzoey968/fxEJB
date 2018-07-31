package com.it.web.user.dao;

import com.it.api.table.user.Tb_User;
import com.it.api.table.user.Tb_UserAuthExtend;
import com.it.util.DaoBase;
import org.hibernate.LockMode;

import java.util.List;

public class UserDao extends DaoBase {

    /**
     * 插入用户
     */
    public Tb_User insertUser(Tb_User user) {
        session.save(user);
        return user;
    }

    /**
     * 修改用户
     */
    public Tb_User updateUser(Tb_User user) {
        Tb_User u = (Tb_User) session.createQuery("from Tb_User where id=:id").setParameter("id", user.getId()).uniqueResult();
        u.setLoginname(user.getLoginname());
        u.setEnable(user.getEnable());
        u.setPassword(user.getPassword());
        u.setSuperPassword(user.getSuperPassword());
        u.setRole(user.getRole());
        u.setUsername(user.getUsername());
        session.update(u);
        u.setPassword("******");
        u.setSuperPassword("******");
        return u;
    }

    /**
     * 查询用户
     */
    public List<Tb_User> listUsers(String name) {
        List<Tb_User> list = session.createQuery("from Tb_User where (loginName like :name or userName like :name)")
                .setParameter("name", "%" + name + "%")
                .list();
        return list;
    }

    /**
     * 允许或禁止登录
     */
    public Tb_User isEnableUserLogin(Long userId, boolean isEnable) {
        Tb_User user = new Tb_User();
        user.setId(userId);
        session.refresh(user, LockMode.UPGRADE_NOWAIT);
        user.setEnable(isEnable);
        session.update(user);
        return user;
    }

    /**
     * 添加或修改用户扩展权限
     */
    public Tb_UserAuthExtend saveOrUpdateUserAuthExtend(Tb_UserAuthExtend userAuthExtend) {
        session.saveOrUpdate(userAuthExtend);
        return userAuthExtend;
    }

    /**
     * 删除用户扩展
     */
    public void deleteUserAuthExtend(Tb_UserAuthExtend userAuthExtend) {
        session.delete(userAuthExtend);
    }

    /**
     * 查询用户扩展权限
     */
    public List<Tb_UserAuthExtend> listUserAuthExtends(Long userId) {
        List<Tb_UserAuthExtend> list = session.createQuery("FROM Tb_UserAuthExtend WHERE userId = :userId")
                .setParameter("userId", userId)
                .list();
        return list;
    }

}
