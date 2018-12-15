package com.it.web.supplier.dao;

import com.it.api.table.customer.Tb_Supplier;
import com.it.api.table.user.Tb_User;

import java.util.List;

public interface SupplierDaoInter {

    public List<Tb_Supplier> selectSupplier(Tb_User user) throws Exception;

    public Tb_Supplier insertSupplier(Tb_User user, Tb_Supplier supplier) throws Exception;

    public Tb_Supplier updateSupplier(Tb_User user, Tb_Supplier supplier) throws Exception;

    public void deleteSupplier(Tb_User user, Tb_Supplier supplier) throws Exception;

}
