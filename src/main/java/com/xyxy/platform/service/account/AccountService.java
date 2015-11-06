/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.service.account;

import java.util.List;

import com.xyxy.platform.entity.Location;
import com.xyxy.platform.service.LocationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.xyxy.platform.entity.User;
import com.xyxy.platform.repository.TaskDao;
import com.xyxy.platform.repository.UserDao;
import com.xyxy.platform.service.ServiceException;
import com.xyxy.platform.service.account.ShiroDbRealm.ShiroUser;
import com.xyxy.platform.modules.core.security.utils.Digests;
import com.xyxy.platform.modules.core.utils.Clock;
import com.xyxy.platform.modules.core.utils.Encodes;

/**
 * 用户管理类.
 * 
 *
 */
// Spring Service Bean的标识.
@Component
@Transactional
public class AccountService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);

	private UserDao userDao;
	private TaskDao taskDao;
	@Autowired
	private LocationService locationService;
	private Clock clock = Clock.DEFAULT;

	public List<User> getAllUser() {
		return (List<User>) userDao.findAll();
	}

	public User getUser(Long id) {
		return userDao.findOne(id);
	}

	public User findUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}
	public User findUserByLoginNameAndStatus(String loginName, User.Status status) {
		return userDao.findByLoginNameAndStatus(loginName, status.ordinal());
	}

	public void registerUser(User user) {
		entryptPassword(user);
		user.setRoles("user");
		user.setRegisterDate(clock.getCurrentDate());

		userDao.save(user);
	}

	public void createUser(User user, Long currentUserId) {
		User currentUser = getUser(currentUserId);
		user.setCreateUser(currentUser);
		user.setRegisterDate(clock.getCurrentDate());
		user.setLocation(user.getLocation());
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		user.setName(user.getLoginName());
		user.setUpdateDate(clock.getCurrentDate());
		user.setUpdateUser(currentUser);
		user.setStatus(User.Status.OFFLINE);
		userDao.save(user);
	}

	public void updateUser(User user, Long currentUserId) {
		User currentUser = getUser(currentUserId);
		user.setRegisterDate(clock.getCurrentDate());
		user.setLocation(user.getLocation());
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		if(StringUtils.isNotBlank(user.getLoginName())) {
			user.setName(user.getLoginName());
		}
		user.setUpdateDate(clock.getCurrentDate());
		user.setUpdateUser(currentUser);
		userDao.save(user);
	}

	public void updateUser(User user) {
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		if(StringUtils.isNotBlank(user.getLoginName())) {
			user.setName(user.getLoginName());
		}

		//User currentUser = getUser(currentUserId);
		if(null != user && null != user.getId()) {
			//user.setCreateUser(currentUser);
			user.setRegisterDate(clock.getCurrentDate());
		} else {
			user.setStatus(User.Status.OFFLINE);
		}
		user.setUpdateDate(clock.getCurrentDate());
		//user.setUpdateUser(currentUser);
		userDao.save(user);
	}

	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
		taskDao.deleteByUserId(id);

	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	private String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}
}
