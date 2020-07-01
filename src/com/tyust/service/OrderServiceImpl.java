package com.tyust.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tyust.dao.OrderDao;
import com.tyust.dao.OrderItemDao;
import com.tyust.entity.Order;
import com.tyust.entity.OrderItem;
import com.tyust.pager.PageBean;
import com.tyust.pager.PageConstant;

@Service("orderService")
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderItemDao orderItemDao;
	int ps = PageConstant.ORDER_PAGE_SIZE;
	
	public void updateStatus(String oid,int status){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("oid", oid);
			map.put("status", status);
			orderDao.updateStatus(map);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int findStatus(String oid){
		try {
			return orderDao.findStatus(oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//开启事务
	@Transactional
	public Order load(String oid){
		try {
			Order order = orderDao.load(oid);
			return order;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//事务
	@Transactional
	public void createOrder(Order order){
		try {
			Map<String,Object> map = toMap(order);
			orderDao.add(map);
			List<OrderItem> orderItemList = order.getOrderItemList();
			List<Map<String,Object>> mapList = toMapList(orderItemList);
			orderItemDao.addOrderItem(mapList);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	//事务
	@Transactional
	public PageBean<Order> myOrders(String uid,int pc){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("uid", uid);
			map.put("ps", ps);
			map.put("start", getStart(pc));
			List<Order> orderList = orderDao.findByUser(map);
			PageBean<Order> pb = toPageBean(orderList,pc,map);
			return pb;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

	/**
	 * 查询所有订单
	 * @param pc
	 * @return
	 */
	//事务
	@Transactional
	public PageBean<Order> findAll(int pc){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ps", ps);
			map.put("start", getStart(pc));
			List<Order> orderList = orderDao.findAll(map);
			PageBean<Order> pb = toPageBean(orderList,pc,map);
			return pb;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	//事务
	@Transactional
	public PageBean<Order> findByStatus (int status ,int pc){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status", status);
			List<Order> orderList = orderDao.findByStatus(map);
			PageBean<Order> pb = toPageBean(orderList,pc,map);
			return pb;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private PageBean<Order> toPageBean(List<Order> orderList,int pc,Map<String,Object> map){
		Integer tr=0;
			try {
				tr = orderDao.findOrderTr(map);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		PageBean<Order> pageBean = new PageBean<Order>();
		pageBean.setPc(pc);
		pageBean.setPs(ps);
		pageBean.setTr(tr);
		pageBean.setBeanList(orderList);
		return pageBean;
	}
	
	private Map<String,Object> toMap(Order order){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("oid", order.getOid());
		map.put("ordertime", order.getOrdertime());
		map.put("total", order.getTotal());
		map.put("status", order.getStatus());
		map.put("address", order.getAddress());
		map.put("uid", order.getOwner().getUid());
		return map;
	}
	
	private List<Map<String,Object>> toMapList(List<OrderItem> orderItemList){
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		for(OrderItem orderItem : orderItemList){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("orderItemId", orderItem.getOrderItemId());
			map.put("quantity", orderItem.getQuantity());
			map.put("subtotal", orderItem.getSubtotal());
			map.put("bid", orderItem.getBook().getBid());
			map.put("bname", orderItem.getBook().getBname());
			map.put("currPrice", orderItem.getBook().getCurrPrice());
			map.put("image_b", orderItem.getBook().getImage_b());
			map.put("oid", orderItem.getOrder().getOid());
			mapList.add(map);
		}
		return mapList;
	}
	
	private int getStart(int pc){
		return (pc-1)*ps;
	}
}
