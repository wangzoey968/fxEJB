package com.it.api.table.user;

import com.it.api.table.user.Tb_Role;
import com.it.api.table.user.Tb_Role_Auth;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by wangzy on 2018/5/26.
 * 权限表
 */
@Entity
@Table(name = "tb_auth")
public class Tb_Auth implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String authname;

    @Column(length = 255)
    private String note;

    @Column
    private Long parent_id;

    @Transient
    private List<Tb_Role> roles;

    @Transient
    private List<Tb_User> users;

    @Transient
    private List<Tb_Group> groups;

    @Override
    public String toString() {
        return "Tb_Auth{" +
                "id=" + id +
                ", authname='" + authname + '\'' +
                ", note='" + note + '\'' +
                ", parent_id=" + parent_id +
                ", roles=" + roles +
                ", users=" + users +
                ", groups=" + groups +
                '}';
    }

    public Tb_Auth() {
    }

    public Tb_Auth(String authname, String note, Long parent_id, List<Tb_Role> roles, List<Tb_User> users, List<Tb_Group> groups) {
        this.authname = authname;
        this.note = note;
        this.parent_id = parent_id;
        this.roles = roles;
        this.users = users;
        this.groups = groups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthname() {
        return authname;
    }

    public void setAuthname(String authname) {
        this.authname = authname;
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

    public List<Tb_Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Tb_Role> roles) {
        this.roles = roles;
    }

    public List<Tb_User> getUsers() {
        return users;
    }

    public void setUsers(List<Tb_User> users) {
        this.users = users;
    }

    public List<Tb_Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Tb_Group> groups) {
        this.groups = groups;
    }
}
