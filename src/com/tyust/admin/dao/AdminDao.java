package com.tyust.admin.dao;

import java.sql.SQLException;
import java.util.Map;

import com.tyust.admin.entity.Admin;

public interface AdminDao {
	
	public Admin findByAdminnameAndAdminpwd(Map<String,Object> map) throws SQLException;
}
