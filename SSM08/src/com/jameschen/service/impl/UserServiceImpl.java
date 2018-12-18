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
	
	//�û���¼
	@Override
	public User login(User user) {
		
		return userDao.login(user);
	}
	
	//����û�
	@Override
	public int addUser(User user) {
		return userDao.addUser(user);
	}

	//�����û�
	@Override
	public User findUser(String userName) {
		// TODO Auto-generated method stub
		return userDao.findUser(userName);
	}

	//ɾ���û�
	@Override
	public int deleteUser(Integer id) {
		// TODO Auto-generated method stub
		return userDao.deleteUser(id);
	}

	//�޸�����
	@Override
	public int updatePassword(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userDao.updatePassword(map);
	}

}
