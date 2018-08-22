package com.it.api.table.user;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wangzy on 2018/7/31.
 * 权限表和用户表的中间表n:n
 */
@Entity
@Table(name = "tb_user_auth", uniqueConstraints = {@UniqueConstraint(columnNames = {"tb_auth_id", "tb_user_id"})})
public class Tb_User_Auth implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * {@link Tb_Auth#id}
     */
    @Column(nullable = false)
    private Long tb_auth_id;

    /**
     * {@link Tb_User#id}
     */
    @Column(nullable = false)
    private Long tb_user_id;

    /**
     * 进行权限扩展或限制的时候使用
     * 如果想为用户分配多余的非角色权限,就生成一条记录,extend为true;
     * 如果想禁用用户的某角色下的某项权限,就生成一条记录,extend为false;
     */
    @Column
    private Boolean extend = true;

    @Override
    public String toString() {
        return "Tb_User_Auth{" +
                "id=" + id +
                ", tb_auth_id=" + tb_auth_id +
                ", tb_user_id=" + tb_user_id +
                ", extend=" + extend +
                '}';
    }

    public Tb_User_Auth() {
    }

    public Tb_User_Auth(Long tb_auth_id, Long tb_user_id, Boolean extend) {
        this.tb_auth_id = tb_auth_id;
        this.tb_user_id = tb_user_id;
        this.extend = extend;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTb_auth_id() {
        return tb_auth_id;
    }

    public void setTb_auth_id(Long tb_auth_id) {
        this.tb_auth_id = tb_auth_id;
    }

    public Long getTb_user_id() {
        return tb_user_id;
    }

    public void setTb_user_id(Long tb_user_id) {
        this.tb_user_id = tb_user_id;
    }

    public Boolean getExtend() {
        return extend;
    }

    public void setExtend(Boolean extend) {
        this.extend = extend;
    }
}
