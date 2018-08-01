package com.it.api.table.user;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wangzy on 2018/7/31.
 * 权限表和用户表的中间表n:n
 */
@Entity
@Table(name = "tb_auth_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"tb_auth_id", "tb_user_id"})})
public class Tb_Auth_User implements Serializable {

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

    @Override
    public String toString() {
        return "Tb_Auth_User{" +
                "id=" + id +
                ", tb_auth_id=" + tb_auth_id +
                ", tb_user_id=" + tb_user_id +
                '}';
    }

    public Tb_Auth_User() {
    }

    public Tb_Auth_User(Long tb_auth_id, Long tb_user_id) {
        this.tb_auth_id = tb_auth_id;
        this.tb_user_id = tb_user_id;
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
}
