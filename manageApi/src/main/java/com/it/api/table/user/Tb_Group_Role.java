package com.it.api.table.user;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wangzy on 2018/7/31.
 * 分组表和角色表的中间表
 */
@Entity
@Table(name = "tb_group_role",uniqueConstraints = {@UniqueConstraint(columnNames = {"tb_group_id","tb_role_id"})})
public class Tb_Group_Role implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tb_group_id;

    @Column(nullable = false)
    private Long tb_role_id;

    @Override
    public String toString() {
        return "Tb_Group_Role{" +
                "id=" + id +
                ", tb_group_id=" + tb_group_id +
                ", tb_role_id=" + tb_role_id +
                '}';
    }

    public Tb_Group_Role() {
    }

    public Tb_Group_Role(Long tb_group_id, Long tb_role_id) {
        this.tb_group_id = tb_group_id;
        this.tb_role_id = tb_role_id;
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

    public Long getTb_role_id() {
        return tb_role_id;
    }

    public void setTb_role_id(Long tb_role_id) {
        this.tb_role_id = tb_role_id;
    }
}
