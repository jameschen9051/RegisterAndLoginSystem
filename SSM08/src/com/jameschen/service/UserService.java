package com.jameschen.service;

import java.util.Map;

import com.jameschen.entity.User;

public interface UserService {
	//��¼
	public User login(User user);
	//ע��
	public int addUser(User user);
	
	//�����û�
	public User findUser(String userName);
	
	//�޸�����
	public int updatePassword(Map<String, Object> map);
	
	//ɾ���û�
	public int deleteUser(Integer id);
}
