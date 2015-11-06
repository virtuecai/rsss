package com.xyxy.platform.web.account;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.xyxy.platform.service.LocationService;
import com.xyxy.platform.service.account.ShiroDbRealm;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.xyxy.platform.entity.User;
import com.xyxy.platform.service.account.AccountService;
import org.springframework.web.servlet.support.RequestContextUtils;


/**
 * 管理员管理用户的Controller.
 * 
 *
 */
@Controller
@RequestMapping(value = "/admin/user")
public class UserAdminController {

    @Autowired
	private LocationService locationService;

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		List<User> users = accountService.getAllUser();
		model.addAttribute("users", users);

		return "account/adminUserList";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addView(Model model) {
		model.addAttribute("locationList", locationService.findAll());
		return "account/adminUserAddForm";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("user") User user,BindingResult result, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (result.hasErrors()){
			List<ObjectError> errorList = result.getAllErrors();
			for(ObjectError error : errorList){
				System.out.println(error.getDefaultMessage());
			}
		}
		ApplicationContext ctx = RequestContextUtils.getWebApplicationContext(request);
		String msg = ctx.getMessage("user.add.success", new Object[]{user.getLoginName()}, RequestContextUtils.getLocale(request));
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
		accountService.createUser(user, shiroUser.id);
		redirectAttributes.addFlashAttribute("message", msg);
		return "redirect:/admin/user";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", accountService.getUser(id));
		model.addAttribute("locationList", locationService.findAll());
		return "account/adminUserForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("user") User user, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
		accountService.updateUser(user, shiroUser.id);
		ApplicationContext ctx = RequestContextUtils.getWebApplicationContext(request);
		String msg = ctx.getMessage("user.update.success", new Object[]{user.getLoginName()}, RequestContextUtils.getLocale(request));
		redirectAttributes.addFlashAttribute("message", msg);
		return "redirect:/admin/user";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		User user = accountService.getUser(id);
		accountService.deleteUser(id);
		redirectAttributes.addFlashAttribute("message", "删除用户" + user.getLoginName() + "成功");
		return "redirect:/admin/user";
	}

	/**
	 * 实现了对象属性copy
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			User user = accountService.getUser(id);
			user.setLocation(null);
			model.addAttribute("user", accountService.getUser(id));
		}
	}
}
