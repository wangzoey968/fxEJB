package com.it.api.table.user;

import javax.persistence.*;
import java.io.Serializable;
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
    private Boolean enable = true;      //用户是否启用；

    /**
     * 一个用户可能有多个角色,还可能有些许扩展权限
     */
    @Transient
    private List<Tb_Role> roles;

    /**
     * 扩展权限
     */
    @Transient
    private List<Tb_UserAuthExtend> extendAuths;

    @Transient
    private List<String> authList;        //权限列表(运行时有效)

    @Transient
    public void checkAuth(String auth) throws Exception {
        if (!authList.contains(auth)) throw new Exception("没有当前权限:" + auth);
    }

    @Transient
    public Boolean containsAuth(String auth) {
        return authList.contains(auth);
    }

    public Tb_User() {
    }

    public Tb_User(String loginname, String username, String password, String superPassword, Boolean enable, List<String> authList) {
        this.loginname = loginname;
        this.username = username;
        this.password = password;
        this.superPassword = superPassword;
        this.enable = enable;
        this.authList = authList;
    }

    @Override
    public String toString() {
        return "Tb_User{" +
                "id=" + id +
                ", loginname='" + loginname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", superPassword='" + superPassword + '\'' +
                ", enable=" + enable +
                ", authList=" + authList +
                '}';
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
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public List<Tb_Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Tb_Role> roles) {
        this.roles = roles;
    }

    public List<Tb_UserAuthExtend> getExtendAuths() {
        return extendAuths;
    }

    public void setExtendAuths(List<Tb_UserAuthExtend> extendAuths) {
        this.extendAuths = extendAuths;
    }

    public List<String> getAuthList() {
        return authList;
    }

    public void setAuthList(List<String> authList) {
        this.authList = authList;
    }
}
