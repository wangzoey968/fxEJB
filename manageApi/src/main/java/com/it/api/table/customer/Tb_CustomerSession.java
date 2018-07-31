package com.it.api.table.customer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Tb_CustomerSession implements Serializable {

    @Id
    @Column(length = 45)
    String sessionId;

    @Column
    Long customerId;

    @Column
    Long lastAccessTime;

    public Tb_CustomerSession() {
    }

    public Tb_CustomerSession(String sessionId, Long customerId, Long lastAccessTime) {
        this.sessionId = sessionId;
        this.customerId = customerId;
        this.lastAccessTime = lastAccessTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

}
