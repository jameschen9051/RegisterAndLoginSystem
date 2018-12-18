package com.jameschen.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jameschen.entity.User;
import com.jameschen.service.UserService;
import com.jameschen.util.CaptchaUtil;

@Controller
@RequestMapping("/user")
public class UserContrller {

	@Resource
	private UserService userService;

	private List<String> strList = new ArrayList<>();

	private String a, b, c, d;

	// 生成验证码
	public void createCapcha(HttpServletRequest request) {
		strList = new CaptchaUtil().createCaptcha();
		a = strList.get(0);
		b = strList.get(1);
		c = strList.get(2);
		d = strList.get(3);
		String str = new String();
		for (String s : strList) {
			str += s;
		}
		HttpSession session = request.getSession();
		session.setAttribute("yanzhengma", str);
		session.setAttribute("a", a);
		session.setAttribute("b", b);
		session.setAttribute("c", c);
		session.setAttribute("d", d);
	}

	/**
	 * 处理主页点击登录跳转
	 * 
	 * @return
	 */
	@RequestMapping("/preLogin")
	public String preLogin(HttpServletRequest request) {
		request.setAttribute("errorMsg1", "请输入用户名");
		request.setAttribute("errorMsg2", "请输入登录密码");
		request.setAttribute("errorMsg3", "请输入验证码");
		// 生成验证码
		this.createCapcha(request);
		return "login";
	}

	/**
	 * 处理主页注册跳转
	 * 
	 * @return
	 */
	@RequestMapping("/preReg")
	public String preReg(HttpServletRequest request) {

		request.setAttribute("errorMsg1", "请输入用户名");
		request.setAttribute("errorMsg2", "请输入密码");
		request.setAttribute("errorMsg3", "请再次输入密码");
		request.setAttribute("errorMsg4", "请输入验证码");

		// 生成验证码
		this.createCapcha(request);

		return "register";
	}

	/**
	 * 登录验证 由于mysql不区分大小写，即时查询成功，也要判断请求参数与数据库返回值是否一致 判断resultUser是否返回值为空，为空说明登录验证不成功
	 * 不为空则登录成功，通过session，将返回用户JSON对象
	 */
	@RequestMapping("/login")
	public String login(User user, HttpServletRequest request) {

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		String userInput = request.getParameter("userInput");
		String yanzhengma = (String) session.getAttribute("yanzhengma");

		if ("".equals(userName.trim())) {
			// 判断用户名是否为空
			request.setAttribute("errorMsg1", "用户名不能为空！");
			request.setAttribute("errorMsg2", "请输入密码");
			request.setAttribute("errorMsg3", "请输入验证码");
			// 生成验证码
			this.createCapcha(request);
			return "login";
		} else if (userService.findUser(userName) == null) {
			// 判断用户是否注册
			request.setAttribute("userName", user.getUserName());
			request.setAttribute("password", user.getPassword());
			request.setAttribute("errorMsg1", "该用户名未注册！");
			request.setAttribute("errorMsg3", "请输入验证码");
			// 生成验证码
			this.createCapcha(request);
			return "login";
		} else if ("".equals(password.trim())) {
			// 判断密码是否为空
			request.setAttribute("userName", userName);
			request.setAttribute("errorMsg2", "密码不能为空！");
			request.setAttribute("errorMsg3", "请输入验证码");
			// 生成验证码
			this.createCapcha(request);
			return "login";
		} else if (!yanzhengma.equals(userInput)) {
			// 判断验证码是否正确
			request.setAttribute("userName", userName);
			request.setAttribute("password", user.getPassword());
			request.setAttribute("errorMsg3", "验证码错误！");
			// 生成验证码
			this.createCapcha(request);
			return "login";
		} else {
			User resultUser = userService.login(user);
			// 判断用户名和密码与数据库是否完全一致
			if (resultUser == null) {
				request.setAttribute("userName", user.getUserName());
				request.setAttribute("password", user.getPassword());
				request.setAttribute("errorMsg2", "密码错误！");
				request.setAttribute("errorMsg3", "请输入验证码");
				// 生成验证码
				this.createCapcha(request);
				return "login";
			} else {
				session.setAttribute("currentUser", user);
				return "loginSuccess";
			}
		}
	}

	/**
	 * 注册验证 用户名需要判断是否已存在，密码判断是否大于六位
	 * 
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("/add")
	public String register(User user, HttpServletRequest request) {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		HttpSession session = request.getSession();
		String userInput = request.getParameter("userInput");
		String yanzhengma = (String) session.getAttribute("yanzhengma");

		// 判断用户名和密码是否为空
		if ("".equals(userName.trim()) || "".equals(password.trim())) {
			request.setAttribute("userName", user.getUserName());
			request.setAttribute("password", user.getPassword());
			request.setAttribute("errorMsg1", "请输入用户名和密码！");
			// 生成验证码
			this.createCapcha(request);
			return "register";
		} else if (userService.findUser(userName) != null) {
			// 判断用户名是否已存在
			request.setAttribute("userName", user.getUserName());
			request.setAttribute("password", user.getPassword());
			request.setAttribute("confirmPassword", confirmPassword);
			request.setAttribute("errorMsg1", "用户名已存在,请输入其他名称！");
			// 生成验证码
			this.createCapcha(request);
			return "register";
		} else if (password.length() < 6 || password.length() > 18) {
			// 判断密码是否符合要求
			request.setAttribute("userName", user.getUserName());
			request.setAttribute("password", user.getPassword());
			request.setAttribute("confirmPassword", confirmPassword);
			request.setAttribute("errorMsg2", "密码位数不符合要求，请输入6-18位密码！");
			// 生成验证码
			this.createCapcha(request);
			return "register";
		} else if (!password.equals(confirmPassword)) {
			// 判断两次输入密码是否一致
			request.setAttribute("userName", user.getUserName());
			request.setAttribute("errorMsg3", "请确认前后输入密码是否一致！");
			// 生成验证码
			this.createCapcha(request);
			return "register";
		} else if (!yanzhengma.equals(userInput)) {
			// 判断验证码是否正确
			request.setAttribute("errorMsg1", userName);
			request.setAttribute("errorMsg2", password);
			request.setAttribute("errorMsg3", confirmPassword);
			request.setAttribute("errorMsg4", "验证码错误！");
			// 生成验证码
			this.createCapcha(request);
			return "register";
		} else {
			int regResult = userService.addUser(user);
			if (regResult > 0) {
				session = request.getSession();
				session.setAttribute("currentUser", user);
				return "regsuccess";
			} else {
				request.setAttribute("errorMsg3", "注册失败！");
				// 生成验证码
				this.createCapcha(request);
				return "register";
			}
		}
	}

	/**
	 * 退出登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/exit")
	public String exit(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("currentUser", null);
		return "exit";

	}

	/**
	 * 处理修改密码请求s界面跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("changePassword")
	public String changePassword(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("currentUser") == null) {
			request.setAttribute("errorMsg1", "账号未登录，请先登录!");
			return "login";
		} else {
			request.setAttribute("errorMsg1", "请输入旧密码！");
			request.setAttribute("errorMsg2", "请输入新密码！");
			return "changePassword";
		}
	}

	/**
	 * 处理修改密码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("updatePassword")
	public String updatePassword(User user, HttpServletRequest request) {
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newPassword");
		User resultUser = userService.login(user);

		if (resultUser == null) {
			request.setAttribute("errorMsg1", "密码错误，请重新输入！");
			request.setAttribute("errorMsg2", "请输入新密码！");
			return "changePassword";
		} else if (newPassword.length() < 6 || newPassword.length() > 18) {
			request.setAttribute("password", password);
			request.setAttribute("errorMsg3", "密码位数不符合要求，请输入6-18位密码！");
			return "changePassword";
		} else {
			Map<String, Object> map = new HashMap<>();
			map.put("id", resultUser.getId());
			map.put("newPassword", newPassword);
			int result = userService.updatePassword(map);
			if (result > 0) {
				HttpSession session = request.getSession();
				session.setAttribute("currentUser", null);
				return "changePasswordSuccess";
			} else {
				request.setAttribute("password", password);
				request.setAttribute("newPassword", newPassword);
				request.setAttribute("errorMsg", "密码修改失败！");
				return "changePassword";
			}
		}
	}

	/**
	 * 进行注销账号前的确认操作
	 * 
	 * @return
	 */
	@RequestMapping("/deleteConfirm")
	public String deleteComfirm(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("currentUser") == null) {
			request.setAttribute("errorMsg1", "账号未登录，请先登录!");
			return "login";
		} else {
			request.setAttribute("errorMsg", "请输入密码确认注销账号！");
			return "deleteConfirm";
		}
	}

	/**
	 * 删除账号
	 * 
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("deleteUser")
	public String deleteUse(User user, HttpServletRequest request) {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		User resultUser = userService.login(user);
		if (resultUser != null) {
			int deleteResult = userService.deleteUser(resultUser.getId());
			if (deleteResult > 0) {
				return "deleteSuccess";
			} else {
				request.setAttribute("errorMsg", "删除失败！");
				return "deleteConfirm";
			}
		} else {
			request.setAttribute("userName", userName);
			request.setAttribute("userName", password);
			request.setAttribute("errorMsg", "密码错误，请重新输入！");
			return "deleteConfirm";
		}
	}
}
