/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.service;

import com.xyxy.platform.data.UserData;
import com.xyxy.platform.entity.Location;
import com.xyxy.platform.entity.User;
import com.xyxy.platform.modules.core.test.security.shiro.ShiroTestUtils;
import com.xyxy.platform.modules.core.test.spring.SpringTransactionalTestCase;
import com.xyxy.platform.modules.core.utils.Clock.MockClock;
import com.xyxy.platform.repository.TaskDao;
import com.xyxy.platform.repository.UserDao;
import com.xyxy.platform.service.account.AccountService;
import com.xyxy.platform.service.account.ShiroDbRealm.ShiroUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * AccountService的测试用例, 测试Service层的业务逻辑.
 * 
 *
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class LocationServiceTest  extends SpringTransactionalTestCase {

	@Autowired
	private LocationService locationService;
	@Autowired
	private AccountService accountService;

	@Test
	public void findUserByLoginNameAndStatus() {
		//User user = accountService.findUserByLoginNameAndStatus("admin", User.Status.ONLINE);
		//User user = accountService.findUserByLoginName("admin");
		User user = accountService.getUser(1l);
		System.out.println(user);
	}

	@Test
	public void findAll() {
		for(Location location: locationService.findAll()) {
			System.out.println(location);
		}
	}

}
