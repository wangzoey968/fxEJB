package com.it.web.user.dao;

import com.it.api.table.customer.Tb_CustomerLog;
import com.it.api.table.user.Tb_User;
import com.it.api.table.user.Tb_UserLog;
import com.it.util.DaoBase;
import com.it.util.HibernateUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class CoreDao {

    public Tb_User selectUserByUserId(Long userId) {
        return (Tb_User) HibernateUtil.openSession().createQuery("FROM Tb_User WHERE id=:userId")
                .setParameter("userId", userId).uniqueResult();
    }

    public Tb_User selectUserByLoginName(String loginName) {
        Session session = HibernateUtil.openSession();
        return (Tb_User) session.createQuery("FROM Tb_User WHERE loginname=:loginName")
                .setParameter("loginName", loginName).uniqueResult();
    }

    public List<Tb_UserLog> selectUserLog() {
        return HibernateUtil.openSession().createQuery("FROM Tb_UserLog").list();
    }

    public void saveOrUpdate(Tb_UserLog log) {
        Session session = HibernateUtil.openSession();
        session.saveOrUpdate(log);
    }

    public void delete(Tb_UserLog sUser) {
        HibernateUtil.openSession().delete(sUser);
    }

    public List<String> listUserAuth(Tb_User user) {
        List<String> res = new ArrayList<>();
        /*if (user.getRole().equals("管理员")) {
            res.addAll(AuthList.auths);
        } else {
            session.createQuery("FROM Tb_Auth WHERE authname=:authName")
                    .setParameter("authName", user.getRole())
                    .list();
        }
        List<Tb_UserAuthExtend> list = session.createQuery("from Tb_UserAuthExtend where userId=:userId").setParameter("userId", user.getId()).list();
        for (Tb_UserAuthExtend extend : list) {
            switch (extend.getExtendType()) {
                case "include":
                    res.add(extend.getAuthName());
                    break;
                case "exclude":
                    res.remove(extend.getAuthName());
                    break;
            }
        }*/
        return res;
    }

    public List<Tb_CustomerLog> selectCusLog() {
        Session session = HibernateUtil.openSession();
        List<Tb_CustomerLog> list = session.createQuery("from Tb_CustomerLog ").list();
        return list;
    }


}
