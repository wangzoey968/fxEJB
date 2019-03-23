package com.it.api.table.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by wangzy on 2019/3/23.
 */
@Entity
@Table(name = "tb_income")
public class Tb_Income implements Serializable {

    @Id
    private Long id;

    /*可用*/
    @Column(nullable = false)
    private Boolean enabled = true;

    /*订单id*/
    @Column(nullable = false)
    private String orderId = "";

    /*订单金额*/
    @Column(nullable = false)
    private Double orderMoney = 0D;

    /*优惠金额*/
    @Column(nullable = false)
    private Double dicount = 0D;

    /*配送费用*/
    @Column(nullable = false)
    private Double expressFee = 0D;

    /*已收金额*/
    @Column(nullable = false)
    private Double receivedMoney = 0D;

    /*应收合计*/
    @Column(nullable = false)
    private Double shouldReceive = 0D;

    /*收款完成*/
    @Column(nullable = false)
    private Boolean finished = false;

}













