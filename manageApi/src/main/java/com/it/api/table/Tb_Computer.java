package com.it.api.table;


import javax.persistence.*;
import java.io.Serializable;

/**
 * 登记电脑设置；
 */
@Entity
@Table(name = "tb_computer")
public class Tb_Computer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String computerName;

    //网卡MAC,修改时不允许改；
    @Column(nullable = false, length = 32, unique = true)
    private String macId;

    @Column(nullable = false)
    private Boolean reg = false;

    //提交注册的人的ID，修改时不允许改；
    @Column(nullable = false)
    private Long regUserId;

    @Column
    private String note;

    public Tb_Computer() {
    }

    public Tb_Computer(String computerName, String macId, Boolean reg, Long regUserId, String note) {
        this.computerName = computerName;
        this.macId = macId;
        this.reg = reg;
        this.regUserId = regUserId;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }

    public Boolean getReg() {
        return reg;
    }

    public void setReg(Boolean reg) {
        this.reg = reg;
    }

    public Long getRegUserId() {
        return regUserId;
    }

    public void setRegUserId(Long regUserId) {
        this.regUserId = regUserId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
