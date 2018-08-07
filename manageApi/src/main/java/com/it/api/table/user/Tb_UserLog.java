package com.it.api.table.user;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户日志,包含登录,退出
 */
@Entity
@Table(name = "tb_userlog")
public class Tb_UserLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tb_user_id;

    @Column
    private String sessionId;

    @Column
    private String action;

    @Column
    private Long actionTime;

    @Override
    public String toString() {
        return "Tb_UserLog{" +
                "id=" + id +
                ", tb_user_id=" + tb_user_id +
                ", action='" + action + '\'' +
                ", actionTime=" + actionTime +
                '}';
    }

    public Tb_UserLog() {
    }

    public Tb_UserLog(Long tb_user_id, String action, Long actionTime) {
        this.tb_user_id = tb_user_id;
        this.action = action;
        this.actionTime = actionTime;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getActionTime() {
        return actionTime;
    }

    public void setActionTime(Long actionTime) {
        this.actionTime = actionTime;
    }
}
