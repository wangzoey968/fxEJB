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
    private String loginname;

    @Column(length = 255, nullable = false)
    private String username;

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
    private String phone;

    @Column
    private String note;

    public Tb_Customer() {
    }

    public Tb_Customer(Long createTime, String username, String password, String address, String phone, String note) {
        this.createTime = createTime;
        this.username = username;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
