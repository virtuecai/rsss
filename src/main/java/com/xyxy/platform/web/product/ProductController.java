/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.web.product;

import com.alibaba.fastjson.JSONObject;
import com.xyxy.platform.entity.Product1;
import com.xyxy.platform.entity.User;
import com.xyxy.platform.modules.core.web.bind.annotation.FormModel;
import com.xyxy.platform.service.ProductService;
import com.xyxy.platform.service.account.AccountService;
import com.xyxy.platform.service.account.ShiroDbRealm;
import com.xyxy.platform.service.account.ShiroDbRealm.ShiroUser;
import com.xyxy.platform.web.utils.I18nMessageUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 商品controller.
 * 
 * caizhengda
 */
@Controller
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping(method = RequestMethod.GET)
	public String listView(Model model) {
		model.addAttribute("productList", productService.findAll());
		return "product/list";
	}

	@RequestMapping(value = "search" ,method = RequestMethod.POST)
	public String search(String code, String nameJa, String nameZh, Model model) {
		model.addAttribute("productList", productService.findByConditions(code,nameJa,nameZh));
		model.addAttribute("code", code);
		model.addAttribute("nameZh", nameZh);
		model.addAttribute("model", model);
		return "product/list";
	}

	@ResponseBody
	@RequestMapping(value = "save" ,method = RequestMethod.POST)
	public String save(@RequestBody ProductsForm productsForm) {
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
		//productService.save(productsForm.getProduct1List(), shiroUser.id);
		return "success";
	}

	@RequestMapping(value = "export" ,method = RequestMethod.POST)
	public String export(String code, String nameJa, String nameZh, Model model) {
		model.addAttribute("productList", productService.findByConditions(code,nameJa,nameZh));
		model.addAttribute("code", code);
		model.addAttribute("nameZh", nameZh);
		model.addAttribute("model", model);
		return "product/list";
	}
}
