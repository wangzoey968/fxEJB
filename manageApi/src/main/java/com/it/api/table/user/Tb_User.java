package com.it.api.table.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "tb_user")
public class Tb_User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, unique = true, nullable = false)
    private String loginname;            //登录名称，使用手机号,不可重复

    @Column(length = 45, nullable = false)
    private String username;             //用户名，使用真实名称，可重复；

    @Column(length = 45, nullable = false)
    private String password;             //用户密码

    @Column(length = 45)
    private String superPassword;        //超级密码

    @Column(nullable = false)
    private Boolean isEnable = true;      //用户是否启用；

    @Column(length = 45)
    private String email;

    @Transient
    private List<Tb_Role> roles = new ArrayList<>();

    /**
     * auths是仅为了权限扩展/限制的临时存放;
     */
    @Transient
    private List<Tb_Auth> auths = new ArrayList<>();

    @Transient
    private List<Tb_Group> groups = new ArrayList<>();

    @Override
    public String toString() {
        return "Tb_User{" +
                "id=" + id +
                ", loginname='" + loginname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", superPassword='" + superPassword + '\'' +
                ", isEnable=" + isEnable +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", auths=" + auths +
                ", groups=" + groups +
                '}';
    }

    public Tb_User() {
    }

    public Tb_User(String loginname, String username, String password, String superPassword, Boolean isEnable, String email, List<Tb_Role> roles, List<Tb_Auth> auths, List<Tb_Group> groups) {
        this.loginname = loginname;
        this.username = username;
        this.password = password;
        this.superPassword = superPassword;
        this.isEnable = isEnable;
        this.email = email;
        this.roles = roles;
        this.auths = auths;
        this.groups = groups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSuperPassword() {
        return superPassword;
    }

    public void setSuperPassword(String superPassword) {
        this.superPassword = superPassword;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<Tb_Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Tb_Group> groups) {
        this.groups = groups;
    }
}
