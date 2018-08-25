package com.it.api.table.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzy on 2018/5/26.
 */
@Entity
@Table(name = "tb_role")
public class Tb_Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rolename", length = 255, nullable = false)
    private String rolename;

    @Column(name = "note", length = 255)
    private String note;

    @Column
    private Long parent_id;

    @Transient
    private List<Tb_User> users = null;

    @Transient
    private List<Tb_Auth> auths = null;

    @Transient
    private List<Tb_Group> groups = null;

    @Override
    public String toString() {
        return "Tb_Role{" +
                "id=" + id +
                ", rolename='" + rolename + '\'' +
                ", note='" + note + '\'' +
                ", parent_id=" + parent_id +
                ", users=" + users +
                ", auths=" + auths +
                '}';
    }

    public Tb_Role() {
    }

    public Tb_Role(String rolename, String note, Long parent_id) {
        this.rolename = rolename;
        this.note = note;
        this.parent_id = parent_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
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

    public List<Tb_Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Tb_Group> groups) {
        this.groups = groups;
    }
}
