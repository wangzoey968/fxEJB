package com.it.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by wangzy on 2018/5/11.
 */
@Entity
@Table(name = "tb_user")
public class Tb_User implements Serializable, Comparable<Tb_User> {

    /**
     * {@link Tb_User_Role#tb_user_id}
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "createTime", length = 255, nullable = true)
    private Long createTime = System.currentTimeMillis();

    @Column(name = "username", length = 255, nullable = false)
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "isViewer")
    private Boolean isViewer;

    @Column(name = "note")
    private String note;

    @Column(name = "parentId")
    private Long parentId;

    @Transient
    private List<Tb_User> ownedUsers;

    @Transient
    private List<Tb_Role> roles;

    @Override
    public String toString() {
        return "Tb_User{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", isViewer=" + isViewer +
                ", note='" + note + '\'' +
                ", parentId=" + parentId +
                ", ownedUsers=" + ownedUsers +
                ", roles=" + roles +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getViewer() {
        return isViewer;
    }

    public void setViewer(Boolean viewer) {
        isViewer = viewer;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Tb_User> getOwnedUsers() {
        return ownedUsers;
    }

    public void setOwnedUsers(List<Tb_User> ownedUsers) {
        this.ownedUsers = ownedUsers;
    }

    public List<Tb_Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Tb_Role> roles) {
        this.roles = roles;
    }

    @Override
    public int compareTo(Tb_User u) {
        return this.getUsername().compareTo(u.getUsername()) > 0 ? 1 : (this.getUsername().compareTo(u.getUsername()) == 0 ? 0 : -1);
    }
}
