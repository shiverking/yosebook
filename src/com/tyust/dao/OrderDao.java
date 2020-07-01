package com.tyust.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.tyust.entity.Order;

public interface OrderDao {

	public int findStatus(String oid) throws SQLException;
	
	public void updateStatus(Map<String,Object> map) throws SQLException;
	
	public Order load(String oid) throws SQLException;
	
	public void add(Map<String,Object> map) throws SQLException;
	
	public List<Order> findByStatus(Map<String,Object> map) throws SQLException;
	
	public List<Order> findByUser(Map<String,Object> map) throws SQLException;
	
	public List<Order> findAll(Map<String,Object> map) throws SQLException;
	
	public Integer findOrderTr(Map<String, Object> map) throws SQLException;
}
