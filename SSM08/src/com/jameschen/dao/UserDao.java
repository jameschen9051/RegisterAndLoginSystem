package com.jameschen.dao;

import java.util.Map;

import com.jameschen.entity.User;

public interface UserDao {
	
	//��¼��֤
	public User login(User user);
	
	//ע����֤
	public int addUser(User user);
	
	//��ѯ�û���
	public User findUser(String userName);
	
	//�޸�����
	public int updatePassword(Map<String, Object> map);
	
	//ע���˺�
	public int deleteUser(Integer id);
}
