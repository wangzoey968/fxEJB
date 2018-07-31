package com.it.api.table.user;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_usersession")
public class Tb_UserLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tb_user_id;

    @Column
    private Long lastAccessTime;

    @Override
    public String toString() {
        return "Tb_UserLog{" +
                "id=" + id +
                ", tb_user_id=" + tb_user_id +
                ", lastAccessTime=" + lastAccessTime +
                '}';
    }

    public Tb_UserLog() {
    }

    public Tb_UserLog(Long tb_user_id, Long lastAccessTime) {
        this.tb_user_id = tb_user_id;
        this.lastAccessTime = lastAccessTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTb_user_id() {
        return tb_user_id;
    }

    public void setTb_user_id(Long tb_user_id) {
        this.tb_user_id = tb_user_id;
    }

    public Long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

}
