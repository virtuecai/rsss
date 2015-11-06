/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.data;

import com.xyxy.platform.entity.Task;
import com.xyxy.platform.entity.User;
import com.xyxy.platform.modules.core.test.data.RandomData;

/**
 * Task相关实体测试数据生成.
 * 
 *
 */
public class TaskData {

	public static Task randomTask() {
		Task task = new Task();
		task.setTitle(randomTitle());
		User user = new User(1L);
		task.setUser(user);
		return task;
	}

	public static String randomTitle() {
		return RandomData.randomName("Task");
	}
}
