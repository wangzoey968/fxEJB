package com.it.web.order.service;

import com.it.api.table.order.Tb_Order;
import com.it.api.table.user.Tb_User;
import com.it.util.HibernateUtil;
import com.it.web.user.service.Core;
import org.hibernate.Session;

public class OrderService {

    /**
     * 创建订单
     */
    public static Tb_Order makeOrder(Tb_User user, Tb_Order order) throws Exception {
        //if (!Core.getUserAllAuths(user).contains("下单")) throw new Exception("没有下单权限");
        Session session = HibernateUtil.openSession();
        System.out.println("下单......");
        return null;
    }

}
