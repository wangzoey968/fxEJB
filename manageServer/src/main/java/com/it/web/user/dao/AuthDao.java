package com.it.web.user.dao;

import com.it.api.table.Tb_User;
import com.it.api.table.Tb_UserAuthExtend;
import com.it.api.table.Tb_UserSession;
import com.it.util.DaoBase;
import com.it.web.user.service.AuthList;

import java.util.ArrayList;
import java.util.List;

public class AuthDao extends DaoBase {

    public Tb_User selectUserByUserId(Long userId) {
        return (Tb_User) session.createQuery("FROM Tb_User WHERE id=:userId")
                .setParameter("userId", userId).uniqueResult();
    }

    public Tb_User selectUserByLoginName(String loginName) {
        return (Tb_User) session.createQuery("FROM Tb_User WHERE loginName=:loginName")
                .setParameter("loginName", loginName).uniqueResult();
    }

    public List<Tb_UserSession> selectUserSession() {
        return session.createQuery("FROM Tb_UserSession").list();
    }

    public void saveOrUpdate(Tb_UserSession user) {
        session.saveOrUpdate(user);
    }

    public void delete(Tb_UserSession sUser) {
        session.delete(sUser);
    }

    public List<String> listUserAuth(Tb_User user) {
        List<String> res = new ArrayList<>();
        if (user.getRole().equals("管理员")) {
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
        }
        return res;
    }


}
