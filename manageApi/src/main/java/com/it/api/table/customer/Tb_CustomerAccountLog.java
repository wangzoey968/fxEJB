package com.it.api.table.customer;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class Tb_CustomerAccountLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long tb_customer_id;       //客户id

    @Column(nullable = false, length = 45)
    String customerName;    //客户名称,冗余字段

    @Column(nullable = false)
    BigDecimal creditChange;        //信用额度变化

    @Column(nullable = false)
    BigDecimal depositChange;      //现金存款变化

    @Column(nullable = false, length = 45)
    String action;              //预存款充值、预存款消费、调整信用额度

    @Column(nullable = false)
    Long tb_user_id;  //操作人id

    @Column(nullable = false)
    Timestamp actionTime;  //操作时间

    @Column(length = 2048)
    String note = ""; //备注

}
