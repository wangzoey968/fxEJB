package com.it.api;

import com.it.api.table.order.Tb_Order;
import com.it.api.table.order.Tb_OrderProcess;

import java.util.List;

/**
 * Created by wangzy on 2018/6/7.
 */
public interface OrderServiceLocal {

    public Tb_Order makeOrder(String sessionId, Tb_Order order) throws Exception;

    public List<Tb_Order> listOrders(String sessionId, String timeFrom, String timeTo, String key) throws Exception;

    public Tb_Order editOrder(String sessionId, Tb_Order order) throws Exception;

    public void delOrders(String sessionId, List<Long> orderIds) throws Exception;

    public List<Tb_OrderProcess> listProcess(String sessionId, String key) throws Exception;

}
