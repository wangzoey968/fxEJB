package com.it.api.table.user;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wangzy on 2018/5/26.
 * 角色表和权限表的中间表
 */
@Entity
@Table(name = "tb_role_auth", uniqueConstraints = {@UniqueConstraint(columnNames = {"tb_role_id", "tb_auth_id"})})
public class Tb_Role_Auth implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * {@link Tb_Role#id}
     */
    @Column(name = "tb_role_id", nullable = false)
    private Long tb_role_id;

    /**
     * {@link Tb_Auth#id}
     */
    @Column(name = "tb_auth_id", nullable = false)
    private Long tb_auth_id;

    @Override
    public String toString() {
        return "Tb_Role_Auth{" +
                "id=" + id +
                ", tb_role_id=" + tb_role_id +
                ", tb_auth_id=" + tb_auth_id +
                '}';
    }

    public Tb_Role_Auth() {
    }

    public Tb_Role_Auth(Long tb_role_id, Long tb_auth_id) {
        this.tb_role_id = tb_role_id;
        this.tb_auth_id = tb_auth_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTb_role_id() {
        return tb_role_id;
    }

    public void setTb_role_id(Long tb_role_id) {
        this.tb_role_id = tb_role_id;
    }

    public Long getTb_auth_id() {
        return tb_auth_id;
    }

    public void setTb_auth_id(Long tb_auth_id) {
        this.tb_auth_id = tb_auth_id;
    }
}
