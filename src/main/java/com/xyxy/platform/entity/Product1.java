package com.xyxy.platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_product1")
public class Product1 extends IdEntity implements Serializable {

    /*private String code;//商品代码, 有点意思就行, 英文数字*/
    private String name;//商品名称 日文
    private Date createDate;//创建时间
    private Date updateDate;//更新时间
    private Long createUserId;//创建用户
    private Long updateUserId;//更新用户
    private Product2 product2; //商品代码字段jan_cd, 有点意思就行, 英文数字*/

    @Column(name = "jan_name", length = 40)
    public String getName() {
        return name;
    }

    @Column(name = "cre_date")
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+08:00")
    public Date getCreateDate() {
        return createDate;
    }

    @Column(name = "upd_date")
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+08:00")
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

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="jan_cd", referencedColumnName="jan_cd")
    public Product2 getProduct2() {
        return product2;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setProduct2(Product2 product2) {
        this.product2 = product2;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}