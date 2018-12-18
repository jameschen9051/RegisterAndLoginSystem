package com.jameschen.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jameschen.dao.UserDao;
import com.jameschen.entity.User;
import com.jameschen.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Resource
	private UserDao userDao;
	
	//用户登录
	@Override
	public User login(User user) {
		
		return userDao.login(user);
	}
	
	//添加用户
	@Override
	public int addUser(User user) {
		return userDao.addUser(user);
	}

	//查找用户
	@Override
	public User findUser(String userName) {
		// TODO Auto-generated method stub
		return userDao.findUser(userName);
	}

	//删除用户
	@Override
	public int deleteUser(Integer id) {
		// TODO Auto-generated method stub
		return userDao.deleteUser(id);
	}

	//修改密码
	@Override
	public int updatePassword(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userDao.updatePassword(map);
	}

}
