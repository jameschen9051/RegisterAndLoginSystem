package com.jameschen.dao;

import java.util.Map;

import com.jameschen.entity.User;

public interface UserDao {
	
	//登录验证
	public User login(User user);
	
	//注册验证
	public int addUser(User user);
	
	//查询用户名
	public User findUser(String userName);
	
	//修改密码
	public int updatePassword(Map<String, Object> map);
	
	//注销账号
	public int deleteUser(Integer id);
}
