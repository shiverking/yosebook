package com.tyust.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyust.dao.UserDao;
import com.tyust.entity.User;
import com.tyust.util.commons.CommonUtil;
import com.tyust.util.mail.Mail;
import com.tyust.util.mail.MailUtil;
@Service("userService")
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	
	public void updatePassword(String uid, String oldPass,String newPass) throws Exception{
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("uid", uid);
			map.put("oldPass", oldPass);
			map.put("newPass", newPass);
			boolean bool = userDao.findByUidAndLoginpass(map);
			if(!bool){
				throw new UserException("原密码错误");
			}
			userDao.updateLoginpass(map);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public User login(User user){
		try {
			Map<String,String> map = new HashMap<String, String>();
			map.put("loginname", user.getLoginname());
			map.put("loginpass", user.getLoginpass());
			return userDao.findByLoginnameAndLoginpass(map);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void activation(String code)throws Exception{
		User user;
		try {
			user = userDao.findByCode(code);
			if(user==null) throw new UserException("无效的激活码！");
			if(user.isStatus()) throw new UserException("您已激活过该账号，不能二次激活！");
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("uid", user.getUid());
			map.put("status", true);
			userDao.updateStatus(map);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean ajaxValidateLoginname(String loginname){
		try {
			return userDao.ajaxValidateLoginname(loginname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}	
	public boolean ajaxValidateEmail(String email){
		try {
			return userDao.ajaxValidateEmail(email);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void regist(User user) throws Exception{
		user.setUid(CommonUtil.uuid());
		user.setStatus(false);
		user.setActivationCode(CommonUtil.uuid()+CommonUtil.uuid());
		try {
			userDao.add(user);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		//发邮件
		Properties prop = new Properties();
		try{
			InputStream in = this.getClass().getClassLoader()
					.getResourceAsStream("email_template.properties");
			prop.load(in);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
		String host = prop.getProperty("host");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		Session session = MailUtil.createSession(host, username, password);
		String from = prop.getProperty("from");
		String to = user.getEmail();
		String subject = prop.getProperty("subject");
		String content = MessageFormat.format(prop.getProperty("content"),
											  user.getActivationCode());
		Mail mail = new Mail(from,to,subject,content);
		try{
			MailUtil.send(session, mail);
		}catch(MessagingException e){
			throw new RuntimeException(e);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
		
}
