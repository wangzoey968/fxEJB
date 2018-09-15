package com.it.server;

import com.it.api.OrderServiceLocal;
import com.it.api.table.order.Tb_Order;
import com.it.web.order.service.OrderService;
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
public class OrderServiceRemote implements OrderServiceLocal {

    @Override
    public Tb_Order makeOrder(String sessionId, Tb_Order order) throws Exception {
        return OrderService.makeOrder(Core.getUser(sessionId), order);
    }

}
