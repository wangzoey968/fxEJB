package com.it.api.table;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by wangzy on 2018/5/26.
 */
@Entity
@Table(name = "tb_auth")
public class Tb_Auth implements Serializable {

    /**
     * {@link Tb_Role_Auth#tb_auth_id}
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authname", length = 255, nullable = false)
    private String authname;

    @Column(name = "remark", length = 255)
    private String remark;

    @Transient
    private List<Tb_Role> roles;

    @Override
    public String toString() {
        return "Tb_Auth{" +
                "id=" + id +
                ", authname='" + authname + '\'' +
                ", remark='" + remark + '\'' +
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Tb_Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Tb_Role> roles) {
        this.roles = roles;
    }
}
