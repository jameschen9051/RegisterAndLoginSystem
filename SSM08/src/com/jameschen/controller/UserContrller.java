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

	// ������֤��
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
	 * ������ҳ�����¼��ת
	 * 
	 * @return
	 */
	@RequestMapping("/preLogin")
	public String preLogin(HttpServletRequest request) {
		request.setAttribute("errorMsg1", "�������û���");
		request.setAttribute("errorMsg2", "�������¼����");
		request.setAttribute("errorMsg3", "��������֤��");
		// ������֤��
		this.createCapcha(request);
		return "login";
	}

	/**
	 * ������ҳע����ת
	 * 
	 * @return
	 */
	@RequestMapping("/preReg")
	public String preReg(HttpServletRequest request) {

		request.setAttribute("errorMsg1", "�������û���");
		request.setAttribute("errorMsg2", "����������");
		request.setAttribute("errorMsg3", "���ٴ���������");
		request.setAttribute("errorMsg4", "��������֤��");

		// ������֤��
		this.createCapcha(request);

		return "register";
	}

	/**
	 * ��¼��֤ ����mysql�����ִ�Сд����ʱ��ѯ�ɹ���ҲҪ�ж�������������ݿⷵ��ֵ�Ƿ�һ�� �ж�resultUser�Ƿ񷵻�ֵΪ�գ�Ϊ��˵����¼��֤���ɹ�
	 * ��Ϊ�����¼�ɹ���ͨ��session���������û�JSON����
	 */
	@RequestMapping("/login")
	public String login(User user, HttpServletRequest request) {

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		String userInput = request.getParameter("userInput");
		String yanzhengma = (String) session.getAttribute("yanzhengma");

		if ("".equals(userName.trim())) {
			// �ж��û����Ƿ�Ϊ��
			request.setAttribute("errorMsg1", "�û�������Ϊ�գ�");
			request.setAttribute("errorMsg2", "����������");
			request.setAttribute("errorMsg3", "��������֤��");
			// ������֤��
			this.createCapcha(request);
			return "login";
		} else if (userService.findUser(userName) == null) {
			// �ж��û��Ƿ�ע��
			request.setAttribute("userName", user.getUserName());
			request.setAttribute("password", user.getPassword());
			request.setAttribute("errorMsg1", "���û���δע�ᣡ");
			request.setAttribute("errorMsg3", "��������֤��");
			// ������֤��
			this.createCapcha(request);
			return "login";
		} else if ("".equals(password.trim())) {
			// �ж������Ƿ�Ϊ��
			request.setAttribute("userName", userName);
			request.setAttribute("errorMsg2", "���벻��Ϊ�գ�");
			request.setAttribute("errorMsg3", "��������֤��");
			// ������֤��
			this.createCapcha(request);
			return "login";
		} else if (!yanzhengma.equals(userInput)) {
			// �ж���֤���Ƿ���ȷ
			request.setAttribute("userName", userName);
			request.setAttribute("password", user.getPassword());
			request.setAttribute("errorMsg3", "��֤�����");
			// ������֤��
			this.createCapcha(request);
			return "login";
		} else {
			User resultUser = userService.login(user);
			// �ж��û��������������ݿ��Ƿ���ȫһ��
			if (resultUser == null) {
				request.setAttribute("userName", user.getUserName());
				request.setAttribute("password", user.getPassword());
				request.setAttribute("errorMsg2", "�������");
				request.setAttribute("errorMsg3", "��������֤��");
				// ������֤��
				this.createCapcha(request);
				return "login";
			} else {
				session.setAttribute("currentUser", user);
				return "loginSuccess";
			}
		}
	}

	/**
	 * ע����֤ �û�����Ҫ�ж��Ƿ��Ѵ��ڣ������ж��Ƿ������λ
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

		// �ж��û����������Ƿ�Ϊ��
		if ("".equals(userName.trim()) || "".equals(password.trim())) {
			request.setAttribute("userName", user.getUserName());
			request.setAttribute("password", user.getPassword());
			request.setAttribute("errorMsg1", "�������û��������룡");
			// ������֤��
			this.createCapcha(request);
			return "register";
		} else if (userService.findUser(userName) != null) {
			// �ж��û����Ƿ��Ѵ���
			request.setAttribute("userName", user.getUserName());
			request.setAttribute("password", user.getPassword());
			request.setAttribute("confirmPassword", confirmPassword);
			request.setAttribute("errorMsg1", "�û����Ѵ���,�������������ƣ�");
			// ������֤��
			this.createCapcha(request);
			return "register";
		} else if (password.length() < 6 || password.length() > 18) {
			// �ж������Ƿ����Ҫ��
			request.setAttribute("userName", user.getUserName());
			request.setAttribute("password", user.getPassword());
			request.setAttribute("confirmPassword", confirmPassword);
			request.setAttribute("errorMsg2", "����λ��������Ҫ��������6-18λ���룡");
			// ������֤��
			this.createCapcha(request);
			return "register";
		} else if (!password.equals(confirmPassword)) {
			// �ж��������������Ƿ�һ��
			request.setAttribute("userName", user.getUserName());
			request.setAttribute("errorMsg3", "��ȷ��ǰ�����������Ƿ�һ�£�");
			// ������֤��
			this.createCapcha(request);
			return "register";
		} else if (!yanzhengma.equals(userInput)) {
			// �ж���֤���Ƿ���ȷ
			request.setAttribute("errorMsg1", userName);
			request.setAttribute("errorMsg2", password);
			request.setAttribute("errorMsg3", confirmPassword);
			request.setAttribute("errorMsg4", "��֤�����");
			// ������֤��
			this.createCapcha(request);
			return "register";
		} else {
			int regResult = userService.addUser(user);
			if (regResult > 0) {
				session = request.getSession();
				session.setAttribute("currentUser", user);
				return "regsuccess";
			} else {
				request.setAttribute("errorMsg3", "ע��ʧ�ܣ�");
				// ������֤��
				this.createCapcha(request);
				return "register";
			}
		}
	}

	/**
	 * �˳���¼
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
	 * �����޸���������s������ת
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("changePassword")
	public String changePassword(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("currentUser") == null) {
			request.setAttribute("errorMsg1", "�˺�δ��¼�����ȵ�¼!");
			return "login";
		} else {
			request.setAttribute("errorMsg1", "����������룡");
			request.setAttribute("errorMsg2", "�����������룡");
			return "changePassword";
		}
	}

	/**
	 * �����޸�����
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
			request.setAttribute("errorMsg1", "����������������룡");
			request.setAttribute("errorMsg2", "�����������룡");
			return "changePassword";
		} else if (newPassword.length() < 6 || newPassword.length() > 18) {
			request.setAttribute("password", password);
			request.setAttribute("errorMsg3", "����λ��������Ҫ��������6-18λ���룡");
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
				request.setAttribute("errorMsg", "�����޸�ʧ�ܣ�");
				return "changePassword";
			}
		}
	}

	/**
	 * ����ע���˺�ǰ��ȷ�ϲ���
	 * 
	 * @return
	 */
	@RequestMapping("/deleteConfirm")
	public String deleteComfirm(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("currentUser") == null) {
			request.setAttribute("errorMsg1", "�˺�δ��¼�����ȵ�¼!");
			return "login";
		} else {
			request.setAttribute("errorMsg", "����������ȷ��ע���˺ţ�");
			return "deleteConfirm";
		}
	}

	/**
	 * ɾ���˺�
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
				request.setAttribute("errorMsg", "ɾ��ʧ�ܣ�");
				return "deleteConfirm";
			}
		} else {
			request.setAttribute("userName", userName);
			request.setAttribute("userName", password);
			request.setAttribute("errorMsg", "����������������룡");
			return "deleteConfirm";
		}
	}
}
