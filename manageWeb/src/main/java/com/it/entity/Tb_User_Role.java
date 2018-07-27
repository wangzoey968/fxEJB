package com.it.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wangzy on 2018/5/26.
 */
@Entity
@Table(name = "tb_user_role")
public class Tb_User_Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * {@link Tb_Role#id}
     */
    @Column(name = "tb_role_id",nullable = false)
    private Integer tb_role_id;

    /**
     * {@link Tb_User#id}
     */
    @Column(name = "tb_user_id",nullable = false)
    private Integer tb_user_id;

    @Override
    public String toString() {
        return "Tb_User_Role{" +
                "id=" + id +
                ", tb_role_id=" + tb_role_id +
                ", tb_user_id=" + tb_user_id +
                '}';
    }

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

    public Integer getTb_user_id() {
        return tb_user_id;
    }

    public void setTb_user_id(Integer tb_user_id) {
        this.tb_user_id = tb_user_id;
    }
}
