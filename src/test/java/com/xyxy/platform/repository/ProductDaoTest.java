/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.repository;

import com.google.common.collect.Lists;
import com.xyxy.platform.entity.Product;
import com.xyxy.platform.entity.Product1;
import com.xyxy.platform.modules.core.persistence.DynamicSpecifications;
import com.xyxy.platform.modules.core.persistence.SearchFilter;
import com.xyxy.platform.modules.core.test.spring.SpringTransactionalTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ProductDaoTest extends SpringTransactionalTestCase {

	@Autowired
	private Product1Dao productDao;

	@Test
	public void findAll() throws Exception {
		Iterable<Product1> all = productDao.findAll();
		for(Product1 p: all) {
			System.out.println(p);
		}
	}

	@Test
	public void findAllByNoParams() throws Exception {
		List<Product1> all = productDao.findAllByNoParams();
		for(Product1 p: all) {
			System.out.println(p);
		}
	}

	@Test
	public void findByCondicions() throws Exception {
		// LIKE
		SearchFilter filter = new SearchFilter("name", SearchFilter.Operator.LIKE, "日文名称");
		SearchFilter filter2 = new SearchFilter("product2.name", SearchFilter.Operator.LIKE, "中文名");
		List<Product1> productList = productDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter, filter2), Product1.class));
		//com.xyxy.platform.examples.showcase.repository.jpa.DynamicSpecificationTest
		for(Product1 p : productList) {
			System.out.println(p.toString());
		}
	}
}
