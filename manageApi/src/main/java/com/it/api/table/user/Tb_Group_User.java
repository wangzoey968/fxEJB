package com.it.api.table.user;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wangzy on 2018/7/31.
 * 分组表和用户表的中间表
 */
@Entity
@Table(name = "tb_group_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"tb_group_id", "tb_user_id"})})
public class Tb_Group_User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * {@link Tb_Group#id}
     */
    @Column(nullable = false)
    private Long tb_group_id;

    /**
     * {@link Tb_User#id}
     */
    @Column(nullable = false)
    private Long tb_user_id;

    @Override
    public String toString() {
        return "Tb_Group_User{" +
                "id=" + id +
                ", tb_group_id=" + tb_group_id +
                ", tb_user_id=" + tb_user_id +
                '}';
    }

    public Tb_Group_User() {
    }

    public Tb_Group_User(Long tb_group_id, Long tb_user_id) {
        this.tb_group_id = tb_group_id;
        this.tb_user_id = tb_user_id;
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

    public Long getTb_user_id() {
        return tb_user_id;
    }

    public void setTb_user_id(Long tb_user_id) {
        this.tb_user_id = tb_user_id;
    }
}