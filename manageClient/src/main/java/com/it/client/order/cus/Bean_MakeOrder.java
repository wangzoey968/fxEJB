package com.it.client.order.cus;

import com.it.api.table.order.Tb_Order;

import java.io.File;

public class Bean_MakeOrder {

    //创建订单
    private Tb_Order order = new Tb_Order();

    public Tb_Order getOrder() {
        return order;
    }

    public void setOrder(Tb_Order order) {
        this.order = order;
    }

    //设置所属的文件
    private File file = null;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    //标记该订单的状态
    private Integer orderStatus = null;

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
