/*******************************************************************************
 * Copyright (c) 2005, 2014
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_product1")
public class Product1 extends IdEntity {

    private String code;//商品代码, 有点意思就行
    private String name;//商品名称 日文
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

    @Column(name = "cre_date")
    public Date getCreateDate() {
        return createDate;
    }

    @Column(name = "upd_date")
    public Date getUpdateDate() {
        return updateDate;
    }

    @Column(name = "cre_user_id")
    public Long getCreateUser() {
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