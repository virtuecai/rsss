package com.xyxy.platform.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_shukka_H")
public class ShukkaH extends IdEntity implements Serializable {

    private String contenerId; //集装箱ID
    private Long warehouseId; //创库id
    private Date shukkaDate; //出库日期
    private Long userId; //用户id
    private String customerId; //得意先id
    private Long saleCd; //订单编号
    private Integer status; //状态
    private Date creDate; //创建日期
    private Date updDate; //更新日期
    private Long creUserId; //创建用户id
    private Long updUserId; //更细用户id

    private List<ShukkaD> shukkaDList;

    public ShukkaH() {
    }

    public String getContenerId() {
        return contenerId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public Date getShukkaDate() {
        return shukkaDate;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Long getSaleCd() {
        return saleCd;
    }

    public Integer getStatus() {
        return status;
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shukka_h_id")
    public List<ShukkaD> getShukkaDList() {
        return shukkaDList;
    }

    public void setContenerId(String contenerId) {
        this.contenerId = contenerId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public void setShukkaDate(Date shukkaDate) {
        this.shukkaDate = shukkaDate;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setSaleCd(Long saleCd) {
        this.saleCd = saleCd;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public void setShukkaDList(List<ShukkaD> shukkaDList) {
        this.shukkaDList = shukkaDList;
    }
}