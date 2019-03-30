package com.it.api.table.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 处理流程
 */
@Entity
@Table(name = "tb_orderprocess")
public class Tb_OrderProcess implements Serializable, Cloneable {

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderType;

    @Column(nullable = false)
    private String process;

    @Override
    public String toString() {
        return "Tb_OrderProcess{" +
                "id=" + id +
                ", orderType='" + orderType + '\'' +
                ", process='" + process + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }
}
