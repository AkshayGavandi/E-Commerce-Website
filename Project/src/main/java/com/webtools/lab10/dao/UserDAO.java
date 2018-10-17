package com.webtools.lab10.dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.webtools.lab10.pojo.Product;
import com.webtools.lab10.pojo.User;


public class UserDAO extends DAO {

	public UserDAO() {
	}

	public User get(String userEmail, String password) throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from User where userEmail = :useremail and password = :password");
			q.setString("useremail", userEmail);
			q.setString("password", password);			
			User user = (User) q.uniqueResult();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get user " + userEmail, e);
		}
		finally {
			close();
		}
	}
	
	public User get(String userEmail){
		try {
			begin();
			Query q = getSession().createQuery("from User where userEmail = :useremail");
			q.setString("useremail", userEmail);
			User user = (User) q.uniqueResult();
			return user;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
			
		finally {
			close();
		}
	}
	

	public User register(User u) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");
			getSession().save(u);
			commit();
			return u;

		} catch (HibernateException e) {
			rollback();
			throw new Exception("Exception while creating user: " + e.getMessage());
		}
		finally {
			close();
		}
	}
	
	public boolean updateUser(String email) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");
			Query q = getSession().createQuery("from User where userEmail = :useremail");
			q.setString("useremail", email);
			User user = (User) q.uniqueResult();
			if(user!=null){
				user.setStatus(1);
				getSession().update(user);
				commit();
				return true;
			}else{
				return false;
			}

		} catch (HibernateException e) {
			rollback();
			throw new Exception("Exception while creating user: " + e.getMessage());
		}
		finally {
			close();
		}
	
	}
	
	public ArrayList<User> getCustomers() throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from User where userRole='Customer'");
			
			ArrayList<User> customerList =  (ArrayList<User>) q.list();
			return customerList;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get customerList"+ e);
		}
		finally {
			close();
		}
	}

	public boolean updateCustomer(User u) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");
			Query q = getSession().createQuery("from User where id = :id");
			q.setString("id", String.valueOf(u.getId()));
			User user = (User) q.uniqueResult();
			user.setId(u.getId());
			user.setUserName(u.getUserName());
			user.setUserEmail(u.getUserEmail());
			user.setStatus(u.getStatus());
			user.setPassword(u.getPassword());
			user.setStatus(1);
			getSession().update(user);
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
}