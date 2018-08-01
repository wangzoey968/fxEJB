package com.it.api.table.user;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wangzy on 2018/7/31.
 * 分组表和权限表的中间表
 */
@Entity
@Table(name = "tb_group_auth", uniqueConstraints = {@UniqueConstraint(columnNames = {"tb_group_id", "tb_auth_id"})})
public class Tb_Group_Auth implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * {@link Tb_Group#id}
     */
    @Column(nullable = false)
    private Long tb_group_id;

    /**
     * {@link Tb_Auth#id}
     */
    @Column(nullable = false)
    private Long tb_auth_id;

    @Override
    public String toString() {
        return "Tb_Group_Auth{" +
                "id=" + id +
                ", tb_group_id=" + tb_group_id +
                ", tb_auth_id=" + tb_auth_id +
                '}';
    }

    public Tb_Group_Auth() {
    }

    public Tb_Group_Auth(Long tb_group_id, Long tb_auth_id) {
        this.tb_group_id = tb_group_id;
        this.tb_auth_id = tb_auth_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTb_group_id() {
        return tb_group_id;
    }

    public void setTb_group_id(Long tb_group_id) {
        this.tb_group_id = tb_group_id;
    }

    public Long getTb_auth_id() {
        return tb_auth_id;
    }

    public void setTb_auth_id(Long tb_auth_id) {
        this.tb_auth_id = tb_auth_id;
    }
}
