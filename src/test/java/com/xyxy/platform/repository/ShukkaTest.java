/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.repository;

import com.xyxy.platform.entity.ShukkaD;
import com.xyxy.platform.entity.ShukkaH;
import com.xyxy.platform.entity.Task;
import com.xyxy.platform.modules.core.test.spring.SpringTransactionalTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ShukkaTest extends SpringTransactionalTestCase {

	@Autowired
	private ShukkaHDao shukkaHDao;
	@Autowired
	private ShukkaDDao shukkaDDao;

	@Test
	@Rollback(false)
	public void save() throws Exception {
		ShukkaH shukkaH = new ShukkaH();
		ShukkaD shukkaD = new ShukkaD();

		shukkaH.setContenerId("1");
		shukkaH.setWarehouseId(1l);
		shukkaH.setShukkaDate(new Date());
		shukkaH.setUserId(1l);
		shukkaH.setCustomerId("11");
		shukkaH.setSaleCd(123l);
		shukkaH.setStatus(0);
		shukkaH.setCreDate(new Date());
		shukkaH.setUpdDate(new Date());
		shukkaH.setCreUserId(1l);
		shukkaH.setUpdUserId(1l);

		shukkaD.setContenerId("1");
		shukkaD.setJanCd("001");
		shukkaD.setCaseQty(10);
		shukkaD.setQty(1);
		shukkaD.setCustomId("123");
		shukkaD.setJanNameJp("日文名");
		shukkaD.setJanNameCn("中文名");
		shukkaD.setUnitQty(1d);
		shukkaD.setUnitPrice(11.11d);
		shukkaD.setGrossWeight(1d);
		shukkaD.setNetWeight(1d);
		shukkaD.setMadeinCountry("中国");
		shukkaD.setCreDate(new Date());
		shukkaD.setUpdDate(new Date());
		shukkaD.setCreUserId(1l);
		shukkaD.setUpdUserId(1l);


		shukkaHDao.save(shukkaH);

	}

	@Test
	public void search() throws Exception {

	}
}
