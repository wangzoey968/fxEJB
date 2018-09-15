package com.it.api;

import com.it.api.table.order.Tb_Order;

/**
 * Created by wangzy on 2018/6/7.
 */
public interface OrderServiceLocal {

    public Tb_Order makeOrder(String sessionId, Tb_Order order) throws Exception;

}
