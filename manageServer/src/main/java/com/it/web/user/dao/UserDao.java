package com.it.web.user.dao;

import com.it.api.table.user.Tb_User;
import com.it.util.HibernateUtil;
import org.hibernate.LockMode;
import org.hibernate.Session;

import java.util.List;

public class UserDao {

    /**
     * 插入用户
     */
    public Tb_User insertUser(Tb_User user) {
        Session session = HibernateUtil.openSession();
        session.save(user);
        return user;
    }

    /**
     * 修改用户
     */
    public Tb_User updateUser(Tb_User user) {
        Session session = HibernateUtil.openSession();
        Tb_User u = (Tb_User) session.createQuery("from Tb_User where id=:id").setParameter("id", user.getId()).uniqueResult();
        u.setLoginname(user.getLoginname());
        u.setEnable(user.getEnable());
        u.setPassword(user.getPassword());
        u.setSuperPassword(user.getSuperPassword());
        //u.setRole(user.getRole());
        u.setUsername(user.getUsername());
        session.update(u);
        u.setPassword("******");
        u.setSuperPassword("******");
        return u;
    }

    /**
     * 查询用户
     */
    public List<Tb_User> listUser(String key) {
        Session session = HibernateUtil.openSession();
        List<Tb_User> list = session.createQuery("from Tb_User where (loginname like :k or username like :k)")
                .setParameter("k", key == null ? "%%" : "%" + key + "%")
                .list();
        return list;
    }

    /**
     * 允许或禁止登录
     */
    public Tb_User isEnableUserLogin(Long userId, boolean isEnable) {
        Session session = HibernateUtil.openSession();
        Tb_User user = new Tb_User();
        user.setId(userId);
        session.refresh(user, LockMode.UPGRADE_NOWAIT);
        user.setEnable(isEnable);
        session.update(user);
        return user;
    }

}
