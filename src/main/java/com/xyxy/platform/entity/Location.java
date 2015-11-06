package com.xyxy.platform.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 地域信息表
 */
@Entity
@Table(name = "t_location")
public class Location extends IdEntity {

	/**
	 * 地域名称
	 */
	private String name;

	/**
	 * 地域描述
	 */
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}