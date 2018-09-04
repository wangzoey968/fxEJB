package com.it.api.table.user;

import com.it.api.table.user.Tb_Role;
import com.it.api.table.user.Tb_Role_Auth;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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

    @Column(length = 255, nullable = false, unique = true)
    private String authname;

    @Column(length = 255)
    private String note;

    @Column
    private Long parent_id;

    /**
     * 这个是为了判断权限是否是额外扩展或限制添加的
     */
    @Transient
    private Boolean isExtend;

    @Transient
    private List<Tb_Role> roles = null;

    @Transient
    private List<Tb_User> users = null;

    @Transient
    private List<Tb_Group> groups = null;

    public Tb_Auth() {
    }

    public Tb_Auth(String authname, String note, Long parent_id) {
        this.authname = authname;
        this.note = note;
        this.parent_id = parent_id;
    }

    @Override
    public String toString() {
        return "Tb_Auth{" +
                "id=" + id +
                ", authname='" + authname + '\'' +
                ", note='" + note + '\'' +
                ", parent_id=" + parent_id +
                ", isExtend=" + isExtend +
                ", roles=" + roles +
                ", users=" + users +
                ", groups=" + groups +
                '}';
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

    public Boolean getExtend() {
        return isExtend;
    }

    public void setExtend(Boolean extend) {
        isExtend = extend;
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
