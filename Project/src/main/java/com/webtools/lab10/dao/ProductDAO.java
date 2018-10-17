package com.webtools.lab10.dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.webtools.lab10.pojo.Cart;
import com.webtools.lab10.pojo.Product;
import com.webtools.lab10.pojo.User;

public class ProductDAO extends DAO{
	public ProductDAO() {
		
	}
	
	public ArrayList<Product> get() throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from Product");
			
			ArrayList<Product> productList =  (ArrayList<Product>) q.list();
			return productList;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get productList"+ e);
		}
		finally {
			close();
		}
	}
	
	public boolean add(Product u) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");
			getSession().save(u);
			commit();
			return true;

		} catch (HibernateException e) {
			rollback();
			return false;	
		}
		finally {
			close();
		}
		
	}
	
	public boolean update(Product p) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");
			Query q = getSession().createQuery("from Product where code = :code");
			q.setString("code", p.getCode());
			Product product = (Product) q.uniqueResult();
			product.setCode(p.getCode());
			product.setName(p.getName());
			product.setPrice(p.getPrice());
			System.out.println(p.getCode()+p.getName()+p.getPrice());
			getSession().update(product);
			commit();
			return true;
		} 
		catch (HibernateException e) {
			rollback();
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
			Query q = getSession().createQuery("from Product where code = :code");
			q.setString("code", code);
			System.out.println(code);
			Product cart = (Product) q.uniqueResult();
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
			System.out.println("Cannot delete from product "+ e.getMessage());
		}
		finally {
			close();
		}
	}

}
