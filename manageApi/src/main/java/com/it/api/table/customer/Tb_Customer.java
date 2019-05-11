package com.it.api.table.customer;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wangzy on 2018/5/11.
 */
@Entity
@Table(name = "tb_customer")
public class Tb_Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String cusname;

    @Column(length = 255, nullable = false)
    private String loginname;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 45)
    private String superPassword;

    @Column(nullable = false)
    private Boolean enable = true;

    @Column(length = 255)
    private Long createTime;

    @Column
    private String address;

    @Column
    private String province;

    @Column
    private String city;

    @Column
    private String county;

    @Column
    private String town;

    @Column
    private String detail;

    /*固定电话*/
    @Column
    private String phone;

    /*移动电话*/
    @Column
    private String telephone;

    @Column
    private String qq;

    @Column
    private String wechat;

    @Column
    private String note;

    @Override
    public String toString() {
        return "Tb_Customer{" +
                "id=" + id +
                ", cusname='" + cusname + '\'' +
                ", loginname='" + loginname + '\'' +
                ", password='" + password + '\'' +
                ", superPassword='" + superPassword + '\'' +
                ", enable=" + enable +
                ", createTime=" + createTime +
                ", address='" + address + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", town='" + town + '\'' +
                ", detail='" + detail + '\'' +
                ", phone='" + phone + '\'' +
                ", telephone='" + telephone + '\'' +
                ", qq='" + qq + '\'' +
                ", wechat='" + wechat + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
