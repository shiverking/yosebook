package com.tyust.admin.service;

import com.tyust.admin.entity.Admin;

public interface AdminService {	
	public Admin findByAdminnameAndAdminpwd(String adminname, String adminpwd);	
}
