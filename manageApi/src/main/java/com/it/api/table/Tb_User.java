package com.it.api.table;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tb_user")
public class Tb_User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, unique = true, nullable = false)
    private String loginName;            //登录名称，使用手机号；

    @Column(length = 45, nullable = false)
    private String userName;             //用户名，使用真实名称，可重复；

    @Column(length = 45, nullable = false)
    private String password;             //用户密码

    @Column(length = 45)
    private String superPassword;        //超级密码

    @Column(length = 45, nullable = false)
    private String role;                 //角色；

    @Column(nullable = false)
    private Boolean enable = true;      //用户是否启用；

    @Transient
    private Set<String> authList;        //权限列表（运行时有效）；

    @Transient
    public void checkAuth(String auth) throws Exception {
        if (!authList.contains(auth)) throw new Exception("没有当前权限:" + auth);
    }

    @Transient
    public Boolean containsAuth(String auth) {
        return authList.contains(auth);
    }

    @Override
    public String toString() {
        return "Tb_User{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", superPassword='" + superPassword + '\'' +
                ", role='" + role + '\'' +
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
        return loginName;
    }

    public void setLoginname(String loginName) {
        this.loginName = loginName;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Set<String> getAuthList() {
        return authList;
    }

    public void setAuthList(Set<String> authList) {
        this.authList = authList;
    }
}
