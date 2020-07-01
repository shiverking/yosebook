package com.tyust.admin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.tyust.admin.entity.Admin;

public class AdminLoginFilter implements Filter {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		StringBuffer url = request.getRequestURL();
		String path = url.toString();
		if(path.endsWith("login.do")){
			chain.doFilter(request, res);
		}else{
			Admin admin = (Admin) request.getSession().getAttribute("sessionAdmin");
			if(admin==null){
				request.setAttribute("msg", "您还未登录！");
				request.getRequestDispatcher("/adminjsps/login.jsp").forward(request, res);;
			}else{
				chain.doFilter(request, res);
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
