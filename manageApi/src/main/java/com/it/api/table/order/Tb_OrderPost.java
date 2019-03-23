package com.it.api.table.order;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_orderpost")
public class Tb_OrderPost implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*是否可用*/
    @Column
    private Boolean enabled = true;

    /*对应的订单*/
    @Column(nullable = false, length = 45)
    private String orderId;

    /*配送方式
    自提,送货,快递*/
    @Column(nullable = false)
    private String postWay = "送货";

    /*自提时间*/
    @Column
    private Long fetchTime;

    /*自提信息*/
    @Column(length = 45)
    private String fetchNote;

    /*快递单号*/
    @Column(length = 45)
    private String expressNum;

    /*快递费*/
    @Column
    private Double expressFee;

    /*快递创建时间*/
    @Column
    private Long expressCreateTime;

    /*发送快递人员*/
    @Column(length = 45)
    private String expressCreator;

    /*预设的配送人id*/
    @Column
    private Long presetPostmanId;

    /*预设的配送人姓名*/
    @Column
    private String presetPostmanName;

    /*预设的配送时间*/
    @Column
    private Long presetPostTime;

    /*实际配送人id*/
    @Column
    private Long realPostmanId;

    /*实际配送人姓名*/
    @Column
    private String realPostmanName;

    /*实际配送时间*/
    @Column
    private Long realPostTime;

    @Override
    public String toString() {
        return "Tb_OrderPost{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", enabled=" + enabled +
                ", postWay='" + postWay + '\'' +
                ", fetchTime=" + fetchTime +
                ", fetchNote='" + fetchNote + '\'' +
                ", expressNum='" + expressNum + '\'' +
                ", expressFee=" + expressFee +
                ", expressCreateTime=" + expressCreateTime +
                ", expressCreator='" + expressCreator + '\'' +
                ", presetPostmanId=" + presetPostmanId +
                ", presetPostmanName='" + presetPostmanName + '\'' +
                ", presetPostTime=" + presetPostTime +
                ", realPostmanId=" + realPostmanId +
                ", realPostmanName='" + realPostmanName + '\'' +
                ", realPostTime=" + realPostTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPostWay() {
        return postWay;
    }

    public void setPostWay(String postWay) {
        this.postWay = postWay;
    }

    public Long getFetchTime() {
        return fetchTime;
    }

    public void setFetchTime(Long fetchTime) {
        this.fetchTime = fetchTime;
    }

    public String getFetchNote() {
        return fetchNote;
    }

    public void setFetchNote(String fetchNote) {
        this.fetchNote = fetchNote;
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public Double getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(Double expressFee) {
        this.expressFee = expressFee;
    }

    public Long getExpressCreateTime() {
        return expressCreateTime;
    }

    public void setExpressCreateTime(Long expressCreateTime) {
        this.expressCreateTime = expressCreateTime;
    }

    public String getExpressCreator() {
        return expressCreator;
    }

    public void setExpressCreator(String expressCreator) {
        this.expressCreator = expressCreator;
    }

    public Long getPresetPostmanId() {
        return presetPostmanId;
    }

    public void setPresetPostmanId(Long presetPostmanId) {
        this.presetPostmanId = presetPostmanId;
    }

    public String getPresetPostmanName() {
        return presetPostmanName;
    }

    public void setPresetPostmanName(String presetPostmanName) {
        this.presetPostmanName = presetPostmanName;
    }

    public Long getPresetPostTime() {
        return presetPostTime;
    }

    public void setPresetPostTime(Long presetPostTime) {
        this.presetPostTime = presetPostTime;
    }

    public Long getRealPostmanId() {
        return realPostmanId;
    }

    public void setRealPostmanId(Long realPostmanId) {
        this.realPostmanId = realPostmanId;
    }

    public String getRealPostmanName() {
        return realPostmanName;
    }

    public void setRealPostmanName(String realPostmanName) {
        this.realPostmanName = realPostmanName;
    }

    public Long getRealPostTime() {
        return realPostTime;
    }

    public void setRealPostTime(Long realPostTime) {
        this.realPostTime = realPostTime;
    }
}
