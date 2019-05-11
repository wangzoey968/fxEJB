package com.it.web.order.service;

import com.it.api.table.order.Tb_Order;
import com.it.api.table.order.Tb_OrderProcess;
import com.it.api.table.user.Tb_User;
import com.it.util.HibernateUtil;
import com.it.web.user.service.Core;
import com.sun.tools.corba.se.idl.constExpr.Times;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Test;

import javax.ejb.EJB;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    /**
     * 创建订单
     */
    public static Tb_Order makeOrder(Tb_User user, Tb_Order order) throws Exception {
        //if (!Core.getUserAllAuths(user).contains("下单")) throw new Exception("没有下单权限");
        Session session = HibernateUtil.openSession();
        session.save(order);
        return order;
    }

    public static List<Tb_Order> listOrders(String sessionId, List<String> types, String timeFrom, String timeTo, String key) throws Exception {
        Session session = HibernateUtil.openSession();
        Long from = Timestamp.valueOf(timeFrom).getTime();
        Long to = Timestamp.valueOf(timeTo).getTime();
        List<Tb_Order> list = (List<Tb_Order>) session.createQuery("from Tb_Order where orderType in :ot and createTime >:f and createTime<:t and orderTitle like :k")
                .setParameterList("ot", types)
                .setParameter("f", from)
                .setParameter("t", to)
                .setParameter("k", key == null ? "%%" : "%" + key.trim() + "%").list();
        return list;
    }

    public static Tb_Order editOrder(String sessionId, Tb_Order order) throws Exception {
        Session session = HibernateUtil.openSession();
        session.getTransaction().begin();
        session.update(order);
        session.getTransaction().commit();
        return order;
    }

    public static void delOrders(String sessionId, List<Long> orderIds) throws Exception {
        Session session = HibernateUtil.openSession();
        session.getTransaction().begin();
        session.createQuery("delete from Tb_Order  where id in:oids").setParameterList("oids", orderIds).executeUpdate();
        session.getTransaction().commit();
    }

    public static Tb_OrderProcess makeProcess(String sessionId, Tb_OrderProcess process) throws Exception {
        Session session = HibernateUtil.openSession();
        session.getTransaction().begin();
        session.save(process);
        session.getTransaction().commit();
        return process;
    }

    public static List<Tb_OrderProcess> listProcess(String sessionId, String key) throws Exception {
        Session session = HibernateUtil.openSession();
        Query query = session.createQuery("from Tb_OrderProcess where orderType like :k");
        List<Tb_OrderProcess> list = query.setParameter("k", key == null ? "%%" : "%" + key.trim() + "%").list();
        return list;
    }

    public static Tb_OrderProcess editProcess(String sessionId, Tb_OrderProcess process) throws Exception {
        Session session = HibernateUtil.openSession();
        session.getTransaction().begin();
        session.saveOrUpdate(process);
        session.getTransaction().commit();
        return process;
    }

    public static void delProcess(String sessionId, Tb_OrderProcess process) throws Exception {
        Session session = HibernateUtil.openSession();
        session.getTransaction().begin();
        session.delete(process);
        session.getTransaction().commit();
    }

}
