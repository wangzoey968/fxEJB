package com.it.api.table.customer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_customerlog")
public class Tb_CustomerLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long tb_customer_id;

    @Column
    private String sessionId;

    @Column
    private String action;

    @Column
    private Long actionTime;

    public Tb_CustomerLog() {
    }

    public Tb_CustomerLog(Long tb_customer_id, String action, Long actionTime) {
        this.tb_customer_id = tb_customer_id;
        this.action = action;
        this.actionTime = actionTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTb_customer_id() {
        return tb_customer_id;
    }

    public void setTb_customer_id(Long tb_customer_id) {
        this.tb_customer_id = tb_customer_id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getActionTime() {
        return actionTime;
    }

    public void setActionTime(Long actionTime) {
        this.actionTime = actionTime;
    }
}
