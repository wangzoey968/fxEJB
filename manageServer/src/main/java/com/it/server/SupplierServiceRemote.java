package com.it.server;

import com.it.api.SupplierServiceLocal;
import com.it.api.table.customer.Tb_Supplier;
import com.it.api.table.user.Tb_User;
import com.it.web.supplier.service.SupplierServiceInter;
import com.it.web.user.service.Core;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import java.util.List;

@Stateless
@Remote
@TransactionManagement(TransactionManagementType.BEAN)
public class SupplierServiceRemote implements SupplierServiceLocal {

    @Autowired
    private SupplierServiceInter supplierServiceInter;

    @Override
    public List<Tb_Supplier> selectSupplier(String sessionId) throws Exception {
        Tb_User user = Core.getUser(sessionId);
        return supplierServiceInter.selectSupplier(user);
    }

    @Override
    public Tb_Supplier insertSupplier(String sessionId, Tb_Supplier supplier) throws Exception {
        Tb_User user = Core.getUser(sessionId);
        return supplierServiceInter.insertSupplier(user, supplier);
    }

    @Override
    public Tb_Supplier updateSupplier(String sessionId, Tb_Supplier supplier) throws Exception {
        Tb_User user = Core.getUser(sessionId);
        return supplierServiceInter.updateSupplier(user, supplier);
    }

    @Override
    public void deleteSupplier(String sessionId, Tb_Supplier supplier) throws Exception {
        Tb_User user = Core.getUser(sessionId);
        supplierServiceInter.deleteSupplier(user, supplier);
    }
}
