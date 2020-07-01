package com.tyust.service;

import com.tyust.entity.User;

public interface UserService {
	
	public void updatePassword(String uid, String oldPass,String newPass) throws Exception;
	
	public User login(User user);
	
	public void activation(String code)throws Exception;
	
	public boolean ajaxValidateLoginname(String loginname);
	
	public boolean ajaxValidateEmail(String email);
	
	public void regist(User user) throws Exception;
		
}
