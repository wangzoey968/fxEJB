package com.it.server;

import com.it.api.UserServiceLocal;
import com.it.api.table.user.Tb_User;
import com.it.web.user.service.Core;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

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
    public String getUsernameByUserId(Long userId) throws Exception {
        return Core.getUsername(userId);
    }

}
