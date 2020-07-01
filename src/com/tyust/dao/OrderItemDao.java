package com.tyust.dao;

import java.util.List;
import java.util.Map;

import com.tyust.entity.OrderItem;

public interface OrderItemDao {
	
	public List<OrderItem> findByOid(String oid);
	
	public OrderItem load(String orderItemId);
	
	public void addOrderItem(List<Map<String,Object>> mapList);
}
