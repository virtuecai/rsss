/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.service.account;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.xyxy.platform.data.UserData;
import com.xyxy.platform.entity.User;
import com.xyxy.platform.repository.TaskDao;
import com.xyxy.platform.repository.UserDao;
import com.xyxy.platform.service.ServiceException;
import com.xyxy.platform.service.account.ShiroDbRealm.ShiroUser;
import com.xyxy.platform.modules.core.test.security.shiro.ShiroTestUtils;
import com.xyxy.platform.modules.core.utils.Clock.MockClock;

/**
 * AccountService的测试用例, 测试Service层的业务逻辑.
 * 
 *
 */
public class AccountServiceTest {

	@InjectMocks
	private AccountService accountService;

	@Mock
	private UserDao mockUserDao;

	@Mock
	private TaskDao mockTaskDao;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ShiroTestUtils.mockSubject(new ShiroUser(3L, "foo", "Foo"));
	}

	@Test
	public void registerUser() {
		User user = UserData.randomNewUser();
		Date currentTime = new Date();
		accountService.setClock(new MockClock(currentTime));

		accountService.registerUser(user);

		// 验证user的角色，注册日期和加密后的密码都被自动更新了。
		assertThat(user.getRoles()).isEqualTo("user");
		assertThat(user.getRegisterDate()).isEqualTo(currentTime);
		assertThat(user.getPassword()).isNotNull();
		assertThat(user.getSalt()).isNotNull();
	}

	@Test
	public void updateUser() {
		// 如果明文密码不为空，加密密码会被更新.
		User user = UserData.randomNewUser();
		accountService.updateUser(user);
		assertThat(user.getSalt()).isNotNull();

		// 如果明文密码为空，加密密码无变化。
		User user2 = UserData.randomNewUser();
		user2.setPlainPassword(null);
		accountService.updateUser(user2);
		assertThat(user2.getSalt()).isNull();
	}

	@Test
	public void deleteUser() {
		// 正常删除用户.
		accountService.deleteUser(2L);
		Mockito.verify(mockUserDao).delete(2L);

		// 删除超级管理用户抛出异常, userDao没有被执行
		try {
			accountService.deleteUser(1L);
			failBecauseExceptionWasNotThrown(ServiceException.class);
		} catch (ServiceException e) {
			// expected exception
		}
		Mockito.verify(mockUserDao, Mockito.never()).delete(1L);
	}

	@Test
	public void findUserByLoginNameAndStatus() {
		User user = accountService.findUserByLoginNameAndStatus("admin", User.Status.ONLINE);
		user = accountService.findUserByLoginName("admin");
		System.out.println(user);
	}

}
