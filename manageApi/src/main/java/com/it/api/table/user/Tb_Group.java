package com.it.api.table.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by wangzy on 2018/7/31.
 * 分组表,分组表与用户表(tb_user)、角色表(tb_role)、权限表(tb_auth)都是多对多的关系
 */
@Entity
@Table(name = "tb_group")
public class Tb_Group implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String groupname;

    @Column
    private String note;

    @Column
    private  Long parent_id;

    @Transient
    private List<Tb_User> users;

    @Transient
    private List<Tb_Role> roles;

    @Transient
    private List<Tb_Auth> auths;

    @Override
    public String toString() {
        return "Tb_Group{" +
                "id=" + id +
                ", groupname='" + groupname + '\'' +
                ", note='" + note + '\'' +
                ", parent_id=" + parent_id +
                ", users=" + users +
                ", roles=" + roles +
                ", auths=" + auths +
                '}';
    }

    public Tb_Group() {
    }

    public Tb_Group(String groupname, String note, Long parent_id, List<Tb_User> users, List<Tb_Role> roles, List<Tb_Auth> auths) {
        this.groupname = groupname;
        this.note = note;
        this.parent_id = parent_id;
        this.users = users;
        this.roles = roles;
        this.auths = auths;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
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

    public List<Tb_Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Tb_Role> roles) {
        this.roles = roles;
    }

    public List<Tb_Auth> getAuths() {
        return auths;
    }

    public void setAuths(List<Tb_Auth> auths) {
        this.auths = auths;
    }
}
