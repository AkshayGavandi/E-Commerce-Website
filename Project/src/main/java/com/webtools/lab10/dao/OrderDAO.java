package com.webtools.lab10.dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.webtools.lab10.pojo.Cart;
import com.webtools.lab10.pojo.Order;
import com.webtools.lab10.pojo.User;

public class OrderDAO extends DAO{
	public OrderDAO() {
		
	}
	
	public boolean add(Order u) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");
			if(u!=null) {
				getSession().save(u);
				commit();
				return true;
			}
			else {return false;}

		} catch (HibernateException e) {
			rollback();
			System.out.println("Cannot add to cart "+ e.getMessage());
			return false;
		}
		
		finally {
			close();
		}
	}
	
	public ArrayList<Order> get() throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from Order");
			
			ArrayList<Order> productList =  (ArrayList<Order>) q.list();
			return productList;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get orderlist "+ e);
		}
		finally {
			close();
		}
	}
	
	public void setStatus(long id) throws Exception{
		try {
			begin();
			Query q = getSession().createQuery("from Order where order_number = :id");
			q.setString("id", String.valueOf(id));
			Order order = (Order) q.uniqueResult();
			order.setStatus("Approved");
			getSession().update(order);
			commit();
		}
		catch (HibernateException e) {
			rollback();
		}
	}
}
