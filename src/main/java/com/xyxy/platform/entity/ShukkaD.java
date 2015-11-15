package com.xyxy.platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_shukka_d")
public class ShukkaD extends IdEntity implements Serializable {

    private String contenerId; //集装箱ID
    private String janCd; //商品代码
    private Integer caseQty; //箱数
    private Integer qty; //数量
    private String customId; //合同号
    private String janNameJp; //商品日文名
    private String janNameCn; //商品中文名
    private Double unitQty; //入数
    private Double unitPrice; //单价
    private Double grossWeight; //毛重
    private Double netWeight; //净重
    private String madeinCountry; //原产国 缺省为日本
    private Date creDate; //创建时间
    private Date updDate; //更新时间
    private Long creUserId; //创建用户id
    private Long updUserId; //更新用户id

    private Long shukkaHId; //主表关联字段

    public ShukkaD() {
    }

    public String getContenerId() {
        return contenerId;
    }

    public String getJanCd() {
        return janCd;
    }

    public Integer getCaseQty() {
        return caseQty;
    }

    public Integer getQty() {
        return qty;
    }

    public String getCustomId() {
        return customId;
    }

    public String getJanNameJp() {
        return janNameJp;
    }

    public String getJanNameCn() {
        return janNameCn;
    }

    public Double getUnitQty() {
        return unitQty;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Double getGrossWeight() {
        return grossWeight;
    }

    public Double getNetWeight() {
        return netWeight;
    }

    public String getMadeinCountry() {
        return madeinCountry;
    }

    public Date getCreDate() {
        return creDate;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public Long getCreUserId() {
        return creUserId;
    }

    public Long getUpdUserId() {
        return updUserId;
    }

    @Column(name = "shukka_h_id")
    public Long getShukkaHId() {
        return shukkaHId;
    }

    public void setContenerId(String contenerId) {
        this.contenerId = contenerId;
    }

    public void setJanCd(String janCd) {
        this.janCd = janCd;
    }

    public void setCaseQty(Integer caseQty) {
        this.caseQty = caseQty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public void setJanNameJp(String janNameJp) {
        this.janNameJp = janNameJp;
    }

    public void setJanNameCn(String janNameCn) {
        this.janNameCn = janNameCn;
    }

    public void setUnitQty(Double unitQty) {
        this.unitQty = unitQty;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setGrossWeight(Double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    public void setMadeinCountry(String madeinCountry) {
        this.madeinCountry = madeinCountry;
    }

    public void setCreDate(Date creDate) {
        this.creDate = creDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public void setCreUserId(Long creUserId) {
        this.creUserId = creUserId;
    }

    public void setUpdUserId(Long updUserId) {
        this.updUserId = updUserId;
    }

    public void setShukkaHId(Long shukkaHId) {
        this.shukkaHId = shukkaHId;
    }
}