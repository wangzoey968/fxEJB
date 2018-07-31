package com.it.api.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_usersession")
public class Tb_UserSession implements Serializable {

    @Id
    @Column(length = 45)
    String sessionId;

    @Column
    Long userId;

    @Column
    Long lastAccessTime;

    public Tb_UserSession() {
    }

    public Tb_UserSession(String sessionId, Long userId, Long lastAccessTime) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.lastAccessTime = lastAccessTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }
}
