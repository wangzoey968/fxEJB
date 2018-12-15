package com.it.api;

import com.it.api.table.customer.Tb_Supplier;

import java.util.List;

public interface SupplierServiceLocal {

    public List<Tb_Supplier> selectSupplier(String sessionId) throws Exception;

    public Tb_Supplier insertSupplier(String sessionId, Tb_Supplier supplier) throws Exception;

    public Tb_Supplier updateSupplier(String sessionId, Tb_Supplier supplier) throws Exception;

    public void deleteSupplier(String sessionId, Tb_Supplier supplier) throws Exception;

}
