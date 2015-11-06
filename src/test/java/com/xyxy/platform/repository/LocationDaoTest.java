/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.repository;

import com.google.common.collect.Lists;
import com.xyxy.platform.entity.Location;
import com.xyxy.platform.entity.Task;
import com.xyxy.platform.modules.core.persistence.DynamicSpecifications;
import com.xyxy.platform.modules.core.persistence.SearchFilter;
import com.xyxy.platform.modules.core.test.spring.SpringTransactionalTestCase;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class LocationDaoTest extends SpringTransactionalTestCase {

	@Autowired
	private LocationDao locationDao;

	@Test
	public void test() throws Exception {
		for (Location location : locationDao.findAll()) {
			System.out.println(location.getName());
		}
		final String name = "广";
		final Long id = 1l;
		System.out.println("------------------------------------");

		// LIKE
		SearchFilter filter = new SearchFilter("name", SearchFilter.Operator.EQ, "杭州");
		List<Location> locationList = locationDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter), Location.class));
		//com.xyxy.platform.examples.showcase.repository.jpa.DynamicSpecificationTest
		System.out.println(locationList);
	}
}
