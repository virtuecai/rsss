/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.web.account;

import com.xyxy.platform.entity.User;
import com.xyxy.platform.service.account.AccountService;
import com.xyxy.platform.service.account.ShiroDbRealm.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 真正登录的POST请求由Filter完成,
 * 
 *
 */
@Controller
@RequestMapping(value = "/welcome")
public class WelcomeController {

	@Autowired
	private AccountService accountService;

	@RequestMapping( method = RequestMethod.GET)
	public String welcome() {
		User user = accountService.getUser(getCurrentUserId());
		if(user.getRoleList().contains("admin")) {
			return "redirect:admin/user";
		}
		return "redirect:profile";
	}

	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}

}
