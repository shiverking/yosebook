package com.tyust.dao;

import java.sql.SQLException;
import java.util.Map;

import com.tyust.entity.User;

public interface UserDao {
	
	public boolean findByUidAndLoginpass(Map<String,String> map) throws SQLException;
	
	public void updateLoginpass(Map<String,String> map) throws SQLException;
	
	public User findByLoginnameAndLoginpass(Map<String,String> map) throws SQLException;
	
	public User findByCode(String code) throws SQLException;
	
	public void updateStatus(Map<String,Object> map) throws SQLException;
	
	public boolean ajaxValidateLoginname(String loginname) throws SQLException;
	
	public boolean ajaxValidateEmail(String email) throws SQLException;
	
	public void add(User user) throws SQLException;
}
