package com.xyxy.platform.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_product2")
public class Product2 extends IdEntity implements Serializable {

    private String code;//商品代码, 有点意思就行
    private String name;//商品名称 中文
    private Double grossWeight;//毛重 单位 g
    private Double netWeight;//净重 单位 g
    private Date createDate;//创建时间
    private Date updateDate;//更新时间
    private Long createUserId;//创建用户
    private Long updateUserId;//更新用户

    @Column(name = "jan_cd")
    public String getCode() {
        return code;
    }

    @Column(name = "jan_name")
    public String getName() {
        return name;
    }

    @Column(name = "gross_weight")
    public Double getGrossWeight() {
        return grossWeight;
    }

    @Column(name = "net_weight")
    public Double getNetWeight() {
        return netWeight;
    }

    @Column(name = "cre_date")
    public Date getCreateDate() {
        return createDate;
    }

    @Column(name = "upd_date")
    public Date getUpdateDate() {
        return updateDate;
    }

    @Column(name = "cre_user_id")
    public Long getCreateUserId() {
        return createUserId;
    }

    @Column(name = "upd_user_id")
    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrossWeight(Double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}