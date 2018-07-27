package com.it.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wangzy on 2018/5/26.
 */
@Entity
@Table(name = "tb_role_auth")
public class Tb_Role_Auth implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * {@link Tb_Role#id}
     */
    @Column(name = "tb_role_id",nullable = false)
    private Integer tb_role_id;

    /**
     * {@link Tb_Auth#id}
     */
    @Column(name = "tb_auth_id",nullable = false)
    private Integer tb_auth_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTb_role_id() {
        return tb_role_id;
    }

    public void setTb_role_id(Integer tb_role_id) {
        this.tb_role_id = tb_role_id;
    }

    public Integer getTb_auth_id() {
        return tb_auth_id;
    }

    public void setTb_auth_id(Integer tb_auth_id) {
        this.tb_auth_id = tb_auth_id;
    }
}
