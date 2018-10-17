package com.webtools.lab10.dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.webtools.lab10.pojo.Cart;
import com.webtools.lab10.pojo.User;

public class CartDAO extends DAO{
	public CartDAO() {
		
	}
	
	public ArrayList<Cart> get() throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from Cart");
			
			ArrayList<Cart> productList =  (ArrayList<Cart>) q.list();
			return productList;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get productList"+ e);
		}
		finally {
			close();
		}
	}
	
	public boolean add(Cart u) throws Exception {
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
	
	public void delete(String code) throws Exception{
		try {
			begin();
			System.out.println("Inside DAO delete");
			Query q = getSession().createQuery("from Cart where code = :code");
			q.setString("code", code);
			System.out.println(code);
			Cart cart = (Cart) q.uniqueResult();
			if(cart!=null) {
				getSession().remove(cart);
				commit();
			}
			else {
				System.out.println("cart is null");
			}
		}
		catch(Exception e) {
			rollback();
			System.out.println("Cannot delete from cart "+ e.getMessage());
		}
		finally {
			close();
		}
	}

	
	public void clear() throws Exception{
		try {
			begin();
			System.out.println("Inside DAO clear");
			Query q = getSession().createQuery("delete from Cart");
			q.executeUpdate();
			commit();
			
		}
		catch(Exception e) {
			rollback();
			System.out.println("Cannot delete from cart "+ e.getMessage());
		}
		finally {
			close();
		}
	}
}
