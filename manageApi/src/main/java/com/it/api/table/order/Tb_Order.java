package com.it.api.table.order;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wangzy on 2018/9/5.
 */
@Entity
@Table(name = "tb_order")
public class Tb_Order implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*订单是否可用*/
    @Column(nullable = false)
    private Boolean enabled = true;

    /*订单号,业务逻辑生成独一无二的订单号*/
    @Column(nullable = false, unique = true, length = 45)
    private String orderId;

    /*订单类型*/
    @Column(nullable = false, length = 45)
    private String orderType;

    /*订单标题*/
    @Column(nullable = false, length = 120)
    private String orderTitle = "";

    /*订单数量*/
    @Column(nullable = false)
    private Integer amount = 0;

    /*客户ID*/
    @Column(nullable = false)
    private Long customerId;

    /*客户名称*/
    @Column(nullable = false, length = 45)
    private String customerName;

    /*业务经理id*/
    @Column
    private Long bizId = 0L;

    /*业务经理*/
    @Column(length = 45)
    private String bizName = "";

    /*创建者id*/
    @Column(nullable = false)
    private Long createorId = 0L;

    /*订单创建者*/
    @Column(nullable = false, length = 45)
    private String creatorName;

    /*订单创建时间*/
    @Column(nullable = false)
    private Long createTime = System.currentTimeMillis();

    /*最后修改订单的人*/
    @Column(length = 45)
    private String lastUpdateName;

    /*最后修改订单的时间*/
    @Column
    private Long lastUpdateTime;

    /*工厂交货时间*/
    @Column(nullable = false)
    private Long presetDeadlineTime;

    /*本地文件路径*/
    @Column(columnDefinition = " TEXT ")
    private String fileLocalPaths;

    /*上传文件路径*/
    @Column(columnDefinition = " TEXT ")
    private String remoteFilePaths;

    /*是否被删除*/
    @Column(columnDefinition = "BIT DEFAULT 0 NOT NULL")
    private Boolean deleted = false;

    /*文件是否就绪*/
    @Column(columnDefinition = "BIT NOT NULL DEFAULT 0 ")
    private Boolean fileReady = false;

    /*加急等级*/
    @Column(columnDefinition = "INT NOT NULL DEFAULT 0 ")
    private Integer urgentLevel = 0;

    /*策略区域*/
    @Column(length = 45)
    private String policyArea;

    /*是否已下单*/
    @Column(nullable = false)
    private Boolean created = false;

    /*生产完成*/
    @Column(nullable = false)
    private Boolean produceFinish = false;

    /*已入库*/
    @Column(nullable = false)
    private Boolean inStorage = false;

    /*已收货*/
    @Column(nullable = false)
    private Boolean received = false;

    /*是否需要打包*/
    @Column(nullable = false)
    private Boolean demandPack = true;

    /*打包要求*/
    @Column(nullable = false, length = 150)
    private String packNote = "正常打包";

    /*是否是补单*/
    @Column(nullable = false)
    private Boolean rework = false;

    /*补单对应的事故单ID;*/
    @Column
    private Long accidentId;

    /*备注*/
    @Column(length = 512)
    private String note;

    /*订单金额*/
    @Column(nullable = false)
    private Double totalMoney = 0D;

    /*对应账单,一个账单对应一个订单*/
    @Column(nullable = false)
    private Long incomeId = 0L;

    //获取param
    public String getParam() {
        return orderTitle + "/" + orderType + "/" + orderId;
    }

    @Override
    public String toString() {
        return "Tb_Order{" +
                "id=" + id +
                ", enabled=" + enabled +
                ", orderId='" + orderId + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderTitle='" + orderTitle + '\'' +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", bizId=" + bizId +
                ", bizName='" + bizName + '\'' +
                ", createorId=" + createorId +
                ", creatorName='" + creatorName + '\'' +
                ", createTime=" + createTime +
                ", lastUpdateName='" + lastUpdateName + '\'' +
                ", lastUpdateTime=" + lastUpdateTime +
                ", presetDeadlineTime=" + presetDeadlineTime +
                ", fileLocalPaths='" + fileLocalPaths + '\'' +
                ", remoteFilePaths='" + remoteFilePaths + '\'' +
                ", deleted=" + deleted +
                ", fileReady=" + fileReady +
                ", urgentLevel=" + urgentLevel +
                ", policyArea='" + policyArea + '\'' +
                ", created=" + created +
                ", produceFinish=" + produceFinish +
                ", inStorage=" + inStorage +
                ", received=" + received +
                ", demandPack=" + demandPack +
                ", packNote='" + packNote + '\'' +
                ", rework=" + rework +
                ", accidentId=" + accidentId +
                ", note='" + note + '\'' +
                ", totalMoney=" + totalMoney +
                ", incomeId=" + incomeId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public Long getCreateorId() {
        return createorId;
    }

    public void setCreateorId(Long createorId) {
        this.createorId = createorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateName() {
        return lastUpdateName;
    }

    public void setLastUpdateName(String lastUpdateName) {
        this.lastUpdateName = lastUpdateName;
    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getPresetDeadlineTime() {
        return presetDeadlineTime;
    }

    public void setPresetDeadlineTime(Long presetDeadlineTime) {
        this.presetDeadlineTime = presetDeadlineTime;
    }

    public String getFileLocalPaths() {
        return fileLocalPaths;
    }

    public void setFileLocalPaths(String fileLocalPaths) {
        this.fileLocalPaths = fileLocalPaths;
    }

    public String getRemoteFilePaths() {
        return remoteFilePaths;
    }

    public void setRemoteFilePaths(String remoteFilePaths) {
        this.remoteFilePaths = remoteFilePaths;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getFileReady() {
        return fileReady;
    }

    public void setFileReady(Boolean fileReady) {
        this.fileReady = fileReady;
    }

    public Integer getUrgentLevel() {
        return urgentLevel;
    }

    public void setUrgentLevel(Integer urgentLevel) {
        this.urgentLevel = urgentLevel;
    }

    public String getPolicyArea() {
        return policyArea;
    }

    public void setPolicyArea(String policyArea) {
        this.policyArea = policyArea;
    }

    public Boolean getCreated() {
        return created;
    }

    public void setCreated(Boolean created) {
        this.created = created;
    }

    public Boolean getProduceFinish() {
        return produceFinish;
    }

    public void setProduceFinish(Boolean produceFinish) {
        this.produceFinish = produceFinish;
    }

    public Boolean getInStorage() {
        return inStorage;
    }

    public void setInStorage(Boolean inStorage) {
        this.inStorage = inStorage;
    }

    public Boolean getReceived() {
        return received;
    }

    public void setReceived(Boolean received) {
        this.received = received;
    }

    public Boolean getDemandPack() {
        return demandPack;
    }

    public void setDemandPack(Boolean demandPack) {
        this.demandPack = demandPack;
    }

    public String getPackNote() {
        return packNote;
    }

    public void setPackNote(String packNote) {
        this.packNote = packNote;
    }

    public Boolean getRework() {
        return rework;
    }

    public void setRework(Boolean rework) {
        this.rework = rework;
    }

    public Long getAccidentId() {
        return accidentId;
    }

    public void setAccidentId(Long accidentId) {
        this.accidentId = accidentId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Long getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Long incomeId) {
        this.incomeId = incomeId;
    }
}