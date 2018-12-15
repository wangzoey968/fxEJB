package com.it.web.supplier.service;

import com.it.api.table.customer.Tb_Supplier;
import com.it.api.table.user.Tb_User;
import com.it.web.supplier.dao.SupplierDaoInter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SupplierServiceImpl implements SupplierServiceInter {

    @Autowired
    private SupplierDaoInter supplierDaoInter;

    @Override
    public List<Tb_Supplier> selectSupplier(Tb_User user) throws Exception {
        return supplierDaoInter.selectSupplier(user);
    }

    @Override
    public Tb_Supplier insertSupplier(Tb_User user, Tb_Supplier supplier) throws Exception {
        return supplierDaoInter.insertSupplier(user, supplier);
    }

    @Override
    public Tb_Supplier updateSupplier(Tb_User user, Tb_Supplier supplier) throws Exception {
        return supplierDaoInter.updateSupplier(user, supplier);
    }

    @Override
    public void deleteSupplier(Tb_User user, Tb_Supplier supplier) throws Exception {
        supplierDaoInter.deleteSupplier(user, supplier);
    }
    
}
