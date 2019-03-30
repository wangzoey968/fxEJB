package com.it.server;

import com.it.api.OrderServiceLocal;
import com.it.api.table.order.Tb_Order;
import com.it.api.table.order.Tb_OrderProcess;
import com.it.web.order.service.OrderService;
import com.it.web.user.service.Core;

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
public class OrderServiceRemote implements OrderServiceLocal {

    @Override
    public Tb_Order makeOrder(String sessionId, Tb_Order order) throws Exception {
        return OrderService.makeOrder(Core.getUser(sessionId), order);
    }

    @Override
    public List<Tb_Order> listOrders(String sessionId, String timeFrom, String timeTo, String key) throws Exception {
        return OrderService.listOrders(sessionId, timeFrom, timeTo, key);
    }

    @Override
    public Tb_Order editOrder(String sessionId, Tb_Order order) throws Exception {
        return OrderService.editOrder(sessionId, order);
    }

    @Override
    public void delOrders(String sessionId, List<Long> orderIds) throws Exception {
        OrderService.delOrders(sessionId, orderIds);
    }

    @Override
    public List<Tb_OrderProcess> listProcess(String sessionId, String key) throws Exception {
        return OrderService.listProcess(sessionId, key);
    }
}
