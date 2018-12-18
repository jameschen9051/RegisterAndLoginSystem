package com.jameschen.service;

import java.util.Map;

import com.jameschen.entity.User;

public interface UserService {
	//登录
	public User login(User user);
	//注册
	public int addUser(User user);
	
	//查找用户
	public User findUser(String userName);
	
	//修改密码
	public int updatePassword(Map<String, Object> map);
	
	//删除用户
	public int deleteUser(Integer id);
}
