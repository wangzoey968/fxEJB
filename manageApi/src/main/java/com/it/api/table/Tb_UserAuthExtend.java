package com.it.api.table;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户权限扩展；
 */
@Entity
@Table(name = "tb_userauthextend")
public class Tb_UserAuthExtend implements Serializable {

    @Id
    private Long userId;

    @Id
    @Column(length = 45, nullable = false)
    private String authName;

    @Column(length = 10, nullable = false)
    private String extendType;       //include、exclude

    public Tb_UserAuthExtend() {
    }

    public Tb_UserAuthExtend(Long userId, String authName, String extendType) {
        this.userId = userId;
        this.authName = authName;
        this.extendType = extendType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getExtendType() {
        return extendType;
    }

    public void setExtendType(String extendType) {
        this.extendType = extendType;
    }

}
