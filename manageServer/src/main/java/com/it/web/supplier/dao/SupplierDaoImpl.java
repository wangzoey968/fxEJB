package com.it.web.supplier.dao;

import com.it.api.table.customer.Tb_Supplier;
import com.it.api.table.user.Tb_User;
import com.it.util.HibernateUtil;
import com.it.web.user.service.Core;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by wangzy on 2018/11/1.
 */
public class SupplierDaoImpl implements SupplierDaoInter {

    @Override
    public List<Tb_Supplier> selectSupplier(Tb_User user) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new RuntimeException("你没有管理权限");
        Session session = HibernateUtil.openSession();
        List<Tb_Supplier> list = session.createQuery("from Tb_Supplier").list();
        return list;
    }

    @Override
    public Tb_Supplier insertSupplier(Tb_User user, Tb_Supplier supplier) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new RuntimeException("你没有管理权限");
        Session session = HibernateUtil.openSession();
        session.save(supplier);
        return supplier;
    }

    @Override
    public Tb_Supplier updateSupplier(Tb_User user, Tb_Supplier supplier) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new RuntimeException("你没有管理权限");
        Session session = HibernateUtil.openSession();
        session.update(supplier);
        return supplier;
    }

    @Override
    public void deleteSupplier(Tb_User user, Tb_Supplier supplier) throws Exception {
        if (!Core.getUserAllAuths(user).contains("超管")) throw new RuntimeException("你没有管理权限");
        Session session = HibernateUtil.openSession();
        session.delete(supplier);
    }

}
