package com.it.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by wangzy on 2018/5/26.
 */
@Entity
@Table(name = "tb_role")
public class Tb_Role implements Serializable {

    /**
     * {@link Tb_User_Role#tb_role_id}
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rolename", length = 255, nullable = false)
    private String rolename;

    @Column(name = "note", length = 255)
    private String note;

    @Transient
    private List<Tb_User> users;

    @Transient
    private List<Tb_Auth> auths;

    @Override
    public String toString() {
        return "Tb_Role{" +
                "id=" + id +
                ", rolename='" + rolename + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Tb_User> getUsers() {
        return users;
    }

    public void setUsers(List<Tb_User> users) {
        this.users = users;
    }

    public List<Tb_Auth> getAuths() {
        return auths;
    }

    public void setAuths(List<Tb_Auth> auths) {
        this.auths = auths;
    }

}
