/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.repository;

import com.google.common.collect.Lists;
import com.xyxy.platform.entity.Location;
import com.xyxy.platform.entity.Product;
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
	private ProductDao productDao;

	@Test
	public void test() throws Exception {
// LIKE
		SearchFilter filter = new SearchFilter("code", SearchFilter.Operator.LIKE, "你");
		SearchFilter filter2 = new SearchFilter("name", SearchFilter.Operator.LIKE, "她");
		List<Product> productList = productDao.findAll(DynamicSpecifications.bySearchFilter(Lists.newArrayList(filter, filter2), Product.class));
		//com.xyxy.platform.examples.showcase.repository.jpa.DynamicSpecificationTest
		for(Product p : productList) {
			System.out.println(p.toString());
		}
	}
}
