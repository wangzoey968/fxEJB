package com.it.api.table.customer;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 客户账户
 */
@Entity
@Table(name = "tb_customeraccount")
public class Tb_CustomerAccount implements Serializable {

    @Id
    Long tb_customer_id;         //客户id

    @Column(nullable = false)
    BigDecimal credit;      //信用额度

    @Column(nullable = false)
    BigDecimal deposit;       //现金存款

    @Column(nullable = false)
    BigDecimal creditUse;     //信用使用

    @Column(nullable = false)
    BigDecimal noCheckout;      //未结账金额

    @Transient
    BigDecimal getCreditCanUse() {      //信用余额
        //credit.add(deposit).plus(creditUse);
        return null;
    }

    @Column(nullable = false, length = 16)
    String level = "正常";                  //正常、停用

    @Column(columnDefinition = "VARCHAR(16) NOT NULL DEFAULT '货到付款'")
    String checkoutCycle = "货到付款";      //货到付款、周结、月结

    @Column(columnDefinition = "INT NOT NULL DEFAULT '3'")
    Integer checkoutDayPlus = 3;    //结账日期添加；

}