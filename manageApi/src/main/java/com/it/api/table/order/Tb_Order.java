package com.it.api.table.order;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wangzy on 2018/9/5.
 */
@Entity
@Table(name = "tb_order")
public class Tb_Order implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 是否是补单
     */
    @Column(nullable = false)
    private Boolean rework = false;

    /**
     * 补单对应的事故单ID;
     */
    @Column
    private Long accidentOrderId;

    /**
     * 加急等级
     */
    @Column(columnDefinition = "INT NOT NULL DEFAULT 0 ")
    private Integer urgentLevel = 0;

    /**
     * 订单是否生效
     */
    @Column(columnDefinition = "BIT NOT NULL")
    private Boolean activated;

    /**
     * 是否被删除
     */
    @Column(columnDefinition = "BIT DEFAULT 0 NOT NULL")
    private Boolean deleted = false;

    /**
     * 下单途径
     */
    @Column(length = 16)
    private String createOrderWay;

    /**
     * 订单号,业务逻辑生成独一无二的订单号
     */
    @Column(nullable = false, unique = true, length = 45)
    private String orderId;

    /**
     * 订单标题
     */
    @Column(nullable = false, length = 120)
    private String orderTitle = "";

    /**
     * 订单类型
     */
    @Column(nullable = false, length = 45)
    private String orderType;

    /**
     * 注意事项
     */
    @Column(length = 512)
    private String importantInfo = "";

    /**
     * 订单创建者
     */
    @Column(nullable = false, length = 45)
    private String creatorName;

    /**
     * 订单创建时间
     */
    @Column(nullable = false)
    private Long createTime;

    /**
     * 最后修改订单的人
     */
    @Column(length = 45)
    private String lastUpdateUsername;

    /**
     * 最后修改订单的时间
     */
    @Column
    private Long lastUpdateTime;

    /**
     * 工厂交货时间
     */
    @Column(nullable = false)
    private Long deadlineTime;

    /**
     * 业务经理
     */
    @Column(length = 45)
    private String businessManagerName = "";

    /**
     * 客户ID
     */
    @Column(nullable = false)
    private Long customerId;

    /**
     * 客户名称
     */
    @Column(nullable = false, length = 45)
    private String customerName;

    /**
     * 策略区域,上海/苏州
     */
    @Column(length = 45)
    private String policyArea;

    /**
     * 客户的订单号
     */
    @Column(length = 45)
    private String customerOrderId;

    /**
     * 文件是否就绪
     */
    @Column(columnDefinition = "BIT NOT NULL DEFAULT 0 ")
    private Boolean fileReady = false;

    /**
     * 文件路径
     */
    @Column(length = 512)
    private String fileLocalPath;

    /**
     * 是否需要配送费用
     */
    @Column(nullable = false)
    private Boolean demandPostMoney = false;

    /**
     * 配送费用
     */
    @Column(nullable = false)
    private Double postMoney = 0D;

    /**
     * 产品指导价
     */
    @Column(columnDefinition = "DOUBLE NOT NULL DEFAULT 0")
    private Double guidePrice = 0D;

    /**
     * 出厂价
     */
    @Column(columnDefinition = "DOUBLE NOT NULL DEFAULT 0")
    private Double marketPrice = 0D;

    /**
     * 产品费用、实收价
     */
    @Column(nullable = false)
    private Double paidPrice = 0D;

    /**
     * 产品类型
     */
    @Column(nullable = false, length = 45)
    private String productType;

    /**
     * 算价明细
     */
    @Column(columnDefinition = " TEXT ")
    private String calcNote;

    /**
     * 对账备注
     */
    @Column(length = 120)
    private String duiZhangNote;

    /**
     * 收款账单ID
     */
    @Column(nullable = false)
    private Long incomeId;

    /**
     * 付款账单ID
     */
    @Column
    private Long payoutId;

    /**
     * 经营成本
     */
    @Column(nullable = false)
    private Double businessCost = 0D;

    /**
     * 是否需要打包
     */
    @Column(nullable = false)
    private Boolean demandPack = true;

    /**
     * 打包完成
     */
    @Column
    private Boolean packReady = null;

    /**
     * 是否正常打包
     */
    @Column(nullable = false)
    private Boolean normalPack = true;

    /**
     * 打包要求
     */
    @Column(nullable = false, length = 150)
    private String packDemand = "正常打包";

    /**
     * 打包数量
     */
    @Column(nullable = false)
    private Integer packCount = 0;

    /**
     * 打包操作员
     */
    @Column(length = 45)
    private String packUserName;

    /**
     * 打包时间
     */
    @Column
    private Long packTime;

    /**
     * 是否进入成品仓库
     */
    @Column(nullable = false)
    private Boolean inStorage = false;

    /**
     * 是否已交到客户手里
     */
    @Column(nullable = false)
    private Boolean delivered = false;

    /**
     * 送货区域
     */
    @Column(length = 45)
    private String postArea;

    /**
     * 大件/小件
     */
    @Column(nullable = false, length = 2)
    private String productSize = "大件";

    /**
     * 约定配送时间
     */
    @Column(nullable = false)
    private Long preSetPostTime;

    /**
     * 预设配送方式
     */
    @Column(length = 16)
    private String preSetPostWay;

    /**
     * 预设送货人
     */
    @Column(length = 45)
    private String preSetPostMan = "";

    /**
     * 实际配送方式
     */
    @Column(length = 16)
    private String realPostWay;

    /**
     * 实际交货人
     */
    @Column(length = 45)
    private String realPostMan;

    /**
     * 实际交货时间
     */
    @Column
    private Long realPostTime;

    @Override
    public String toString() {
        return "Tb_Order{" +
                "id=" + id +
                ", rework=" + rework +
                ", accidentOrderId=" + accidentOrderId +
                ", urgentLevel=" + urgentLevel +
                ", activated=" + activated +
                ", deleted=" + deleted +
                ", createOrderWay='" + createOrderWay + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderTitle='" + orderTitle + '\'' +
                ", orderType='" + orderType + '\'' +
                ", importantInfo='" + importantInfo + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", createTime=" + createTime +
                ", lastUpdateUsername='" + lastUpdateUsername + '\'' +
                ", lastUpdateTime=" + lastUpdateTime +
                ", deadlineTime=" + deadlineTime +
                ", businessManagerName='" + businessManagerName + '\'' +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", policyArea='" + policyArea + '\'' +
                ", customerOrderId='" + customerOrderId + '\'' +
                ", fileReady=" + fileReady +
                ", fileLocalPath='" + fileLocalPath + '\'' +
                ", demandPostMoney=" + demandPostMoney +
                ", postMoney=" + postMoney +
                ", guidePrice=" + guidePrice +
                ", marketPrice=" + marketPrice +
                ", paidPrice=" + paidPrice +
                ", productType='" + productType + '\'' +
                ", calcNote='" + calcNote + '\'' +
                ", duiZhangNote='" + duiZhangNote + '\'' +
                ", incomeId=" + incomeId +
                ", payoutId=" + payoutId +
                ", businessCost=" + businessCost +
                ", demandPack=" + demandPack +
                ", packReady=" + packReady +
                ", normalPack=" + normalPack +
                ", packDemand='" + packDemand + '\'' +
                ", packCount=" + packCount +
                ", packUserName='" + packUserName + '\'' +
                ", packTime=" + packTime +
                ", inStorage=" + inStorage +
                ", delivered=" + delivered +
                ", postArea='" + postArea + '\'' +
                ", productSize='" + productSize + '\'' +
                ", preSetPostTime=" + preSetPostTime +
                ", preSetPostWay='" + preSetPostWay + '\'' +
                ", preSetPostMan='" + preSetPostMan + '\'' +
                ", realPostWay='" + realPostWay + '\'' +
                ", realPostMan='" + realPostMan + '\'' +
                ", realPostTime=" + realPostTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getRework() {
        return rework;
    }

    public void setRework(Boolean rework) {
        this.rework = rework;
    }

    public Long getAccidentOrderId() {
        return accidentOrderId;
    }

    public void setAccidentOrderId(Long accidentOrderId) {
        this.accidentOrderId = accidentOrderId;
    }

    public Integer getUrgentLevel() {
        return urgentLevel;
    }

    public void setUrgentLevel(Integer urgentLevel) {
        this.urgentLevel = urgentLevel;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getCreateOrderWay() {
        return createOrderWay;
    }

    public void setCreateOrderWay(String createOrderWay) {
        this.createOrderWay = createOrderWay;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getImportantInfo() {
        return importantInfo;
    }

    public void setImportantInfo(String importantInfo) {
        this.importantInfo = importantInfo;
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

    public String getLastUpdateUsername() {
        return lastUpdateUsername;
    }

    public void setLastUpdateUsername(String lastUpdateUsername) {
        this.lastUpdateUsername = lastUpdateUsername;
    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(Long deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public String getBusinessManagerName() {
        return businessManagerName;
    }

    public void setBusinessManagerName(String businessManagerName) {
        this.businessManagerName = businessManagerName;
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

    public String getPolicyArea() {
        return policyArea;
    }

    public void setPolicyArea(String policyArea) {
        this.policyArea = policyArea;
    }

    public String getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(String customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public Boolean getFileReady() {
        return fileReady;
    }

    public void setFileReady(Boolean fileReady) {
        this.fileReady = fileReady;
    }

    public String getFileLocalPath() {
        return fileLocalPath;
    }

    public void setFileLocalPath(String fileLocalPath) {
        this.fileLocalPath = fileLocalPath;
    }

    public Boolean getDemandPostMoney() {
        return demandPostMoney;
    }

    public void setDemandPostMoney(Boolean demandPostMoney) {
        this.demandPostMoney = demandPostMoney;
    }

    public Double getPostMoney() {
        return postMoney;
    }

    public void setPostMoney(Double postMoney) {
        this.postMoney = postMoney;
    }

    public Double getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(Double guidePrice) {
        this.guidePrice = guidePrice;
    }

    public Double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Double getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(Double paidPrice) {
        this.paidPrice = paidPrice;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getCalcNote() {
        return calcNote;
    }

    public void setCalcNote(String calcNote) {
        this.calcNote = calcNote;
    }

    public String getDuiZhangNote() {
        return duiZhangNote;
    }

    public void setDuiZhangNote(String duiZhangNote) {
        this.duiZhangNote = duiZhangNote;
    }

    public Long getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Long incomeId) {
        this.incomeId = incomeId;
    }

    public Long getPayoutId() {
        return payoutId;
    }

    public void setPayoutId(Long payoutId) {
        this.payoutId = payoutId;
    }

    public Double getBusinessCost() {
        return businessCost;
    }

    public void setBusinessCost(Double businessCost) {
        this.businessCost = businessCost;
    }

    public Boolean getDemandPack() {
        return demandPack;
    }

    public void setDemandPack(Boolean demandPack) {
        this.demandPack = demandPack;
    }

    public Boolean getPackReady() {
        return packReady;
    }

    public void setPackReady(Boolean packReady) {
        this.packReady = packReady;
    }

    public Boolean getNormalPack() {
        return normalPack;
    }

    public void setNormalPack(Boolean normalPack) {
        this.normalPack = normalPack;
    }

    public String getPackDemand() {
        return packDemand;
    }

    public void setPackDemand(String packDemand) {
        this.packDemand = packDemand;
    }

    public Integer getPackCount() {
        return packCount;
    }

    public void setPackCount(Integer packCount) {
        this.packCount = packCount;
    }

    public String getPackUserName() {
        return packUserName;
    }

    public void setPackUserName(String packUserName) {
        this.packUserName = packUserName;
    }

    public Long getPackTime() {
        return packTime;
    }

    public void setPackTime(Long packTime) {
        this.packTime = packTime;
    }

    public Boolean getInStorage() {
        return inStorage;
    }

    public void setInStorage(Boolean inStorage) {
        this.inStorage = inStorage;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public String getPostArea() {
        return postArea;
    }

    public void setPostArea(String postArea) {
        this.postArea = postArea;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public Long getPreSetPostTime() {
        return preSetPostTime;
    }

    public void setPreSetPostTime(Long preSetPostTime) {
        this.preSetPostTime = preSetPostTime;
    }

    public String getPreSetPostWay() {
        return preSetPostWay;
    }

    public void setPreSetPostWay(String preSetPostWay) {
        this.preSetPostWay = preSetPostWay;
    }

    public String getPreSetPostMan() {
        return preSetPostMan;
    }

    public void setPreSetPostMan(String preSetPostMan) {
        this.preSetPostMan = preSetPostMan;
    }

    public String getRealPostWay() {
        return realPostWay;
    }

    public void setRealPostWay(String realPostWay) {
        this.realPostWay = realPostWay;
    }

    public String getRealPostMan() {
        return realPostMan;
    }

    public void setRealPostMan(String realPostMan) {
        this.realPostMan = realPostMan;
    }

    public Long getRealPostTime() {
        return realPostTime;
    }

    public void setRealPostTime(Long realPostTime) {
        this.realPostTime = realPostTime;
    }
}
