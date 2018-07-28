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

}
