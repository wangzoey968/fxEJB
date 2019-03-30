package com.it.api.table.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Tb_Paper implements Serializable, Cloneable {

    @Id
    private String paperName;

    /*纸张宽度单位mm*/
    @Column(nullable = false)
    private Integer width;

    /*纸张高度单位mm*/
    @Column(nullable = false)
    private Integer height;

    /*克重单位为g/m^2*/
    @Column(nullable = false)
    private Double weight;

    /*纸张使用时,对应的订单类型限制,类型为json型的数组*/
    @Column
    private String typeLimit;

    @Override
    public String toString() {
        return "Tb_Paper{" +
                "paperName='" + paperName + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", weight=" + weight +
                ", typeLimit='" + typeLimit + '\'' +
                '}';
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getTypeLimit() {
        return typeLimit;
    }

    public void setTypeLimit(String typeLimit) {
        this.typeLimit = typeLimit;
    }
}
