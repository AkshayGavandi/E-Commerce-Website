package com.webtools.lab10.controller;

import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.captcha.botdetect.web.servlet.Captcha;
import com.webtools.lab10.dao.CartDAO;
import com.webtools.lab10.dao.OrderDAO;
import com.webtools.lab10.dao.ProductDAO;
import com.webtools.lab10.dao.UserDAO;
import com.webtools.lab10.pojo.Cart;
import com.webtools.lab10.pojo.Order;
import com.webtools.lab10.pojo.Product;
import com.webtools.lab10.pojo.User;

/**
 * Handles requests for the application home page.
 */
@Controller
@Scope("session")
public class MainController {

	@Autowired
	private User user;
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showLogoutForm(HttpServletRequest request) {
		HttpSession session=request.getSession();
		session.setAttribute("Role", null);
        session.invalidate();  
		return "user-login";
	}
	
	@RequestMapping(value = "/user/login.htm", method = RequestMethod.GET)
	public String showLoginForm() {

		return "user-login";
	}

	@RequestMapping(value = "/user/login.htm", method = RequestMethod.POST)
	public String handleLoginForm(HttpServletRequest request, UserDAO userDao, ModelMap map) {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		HttpSession session = request.getSession();
		try {
			User u = userDao.get(username, password);

			if (u != null && u.getStatus() == 1 && u.getUserRole().equals("Customer")) {				
				map.addAttribute("email",u.getUserEmail());
				map.addAttribute("password",u.getPassword());
				map.addAttribute("userName",u.getUserName());
				session.setAttribute("Role", "Customer");
				map.addAttribute("roledisplay", "Customer");
				return "customer-dashboard";
			} else if (u != null && u.getStatus() == 1 && u.getUserRole().equals("Manager")) {				
				map.addAttribute("email",u.getUserEmail());
				map.addAttribute("password",u.getPassword());
				map.addAttribute("userName",u.getUserName());
				session.setAttribute("Role", "Manager");
				map.addAttribute("roledisplay","Manager");
				return "manager-dashboard";
			} else if (u != null && u.getStatus() == 0) {
				map.addAttribute("errorMessage", "Please activate your account to login!");
				return "error";
			} else {
				map.addAttribute("errorMessage", "Invalid username/password!");
				return "error";
			}	
		} catch (Exception e) {
			map.addAttribute("errorMessage", "Unreachable workarea");
			return "error";
		}
	}

	@RequestMapping(value = "/user/create.htm", method = RequestMethod.GET)
	public String showCreateForm() {

		return "user-create-form";
	}

	@RequestMapping(value = "/user/create.htm", method = RequestMethod.POST)
	public String handleCreateForm(HttpServletRequest request, UserDAO userDao, ModelMap map) {

		Captcha captcha = Captcha.load(request, "CaptchaObject");
		String captchaCode = request.getParameter("captchaCode");
		HttpSession session = request.getSession();
		if (captcha.validate(captchaCode)) {
			String useremail = request.getParameter("username");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			User user = new User();
			user.setUserEmail(useremail);
			user.setPassword(password);
			user.setStatus(0);
			user.setUserRole("Customer");
			user.setUserName(name);

			try {
				User u = userDao.register(user);
				Random rand = new Random();
				int randomNum1 = rand.nextInt(5000000);
				int randomNum2 = rand.nextInt(5000000);
				try {
					String str = "http://localhost:8080/lab10/user/validateemail.htm?email=" + useremail + "&key1="
							+ randomNum1 + "&key2=" + randomNum2;
					session.setAttribute("key1", randomNum1);
					session.setAttribute("key2", randomNum2);
					sendEmail(useremail,
							"Click on this link to activate your account : "+ str);
				} catch (Exception e) {
					System.out.println("Email cannot be sent");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			map.addAttribute("errorMessage", "Invalid Captcha!");
			return "user-create-form";
		}

		return "user-created";
	}

	@RequestMapping(value = "/user/forgotpassword.htm", method = RequestMethod.GET)
	public String getForgotPasswordForm(HttpServletRequest request) {
		
		return "forgot-password";
	}

	@RequestMapping(value = "/user/forgotpassword.htm", method = RequestMethod.POST)
	public String handleForgotPasswordForm(HttpServletRequest request, UserDAO userDao) {

		String useremail = request.getParameter("useremail");
		Captcha captcha = Captcha.load(request, "CaptchaObject");
		String captchaCode = request.getParameter("captchaCode");

		if (captcha.validate(captchaCode)) {
			User user = userDao.get(useremail);
			sendEmail(useremail, "Your password is : " + user.getPassword());
			return "forgot-password-success";
		} else {
			request.setAttribute("captchamsg", "Captcha not valid");
			return "forgot-password";
		}
	}

	@RequestMapping(value = "user/resendemail.htm", method = RequestMethod.POST)
	public String resendEmail(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String useremail = request.getParameter("username");
		Random rand = new Random();
		int randomNum1 = rand.nextInt(5000000);
		int randomNum2 = rand.nextInt(5000000);
		try {
			String str = "http://localhost:8080/lab10/user/validateemail.htm?email=" + useremail + "&key1=" + randomNum1
					+ "&key2=" + randomNum2;
			session.setAttribute("key1", randomNum1);
			session.setAttribute("key2", randomNum2);
			sendEmail(useremail,
					"Click on this link to activate your account : "+ str);
		} catch (Exception e) {
			System.out.println("Email cannot be sent");
		}
		
		return "user-created";
	}

	public void sendEmail(String useremail, String message) {
		try {
			Email email = new SimpleEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("contactapplication2018@gmail.com", "springmvc"));
			email.setSSLOnConnect(true);
			email.setFrom("no-reply@msis.neu.edu"); // This user email does not
													// exist
			email.setSubject("MyCompany");
			email.setMsg(message); // Retrieve email from the DAO and send this
			email.addTo(useremail);
			email.send();
		} catch (EmailException e) {
			System.out.println("Email cannot be sent");
		}
	}

	@RequestMapping(value = "user/validateemail.htm", method = RequestMethod.GET)
	public String validateEmail(HttpServletRequest request, UserDAO userDao, ModelMap map) {

		// The user will be sent the following link when the use registers
		// This is the format of the email
		// http://hostname:8080/lab10/user/validateemail.htm?email=useremail&key1=<random_number>&key2=<body
		// of the email that when user registers>
		HttpSession session = request.getSession();
		String email = request.getParameter("email");
		int key1 = Integer.parseInt(request.getParameter("key1"));
		int key2 = Integer.parseInt(request.getParameter("key2"));
		System.out.println(session.getAttribute("key1") );
		System.out.println(session.getAttribute("key2") );
		
		
		if ((Integer)(session.getAttribute("key1")) == key1 && ((Integer)session.getAttribute("key2"))== key2) {
			try {
				System.out.println("HI________");
				boolean updateStatus = userDao.updateUser(email);
				if (updateStatus) {
					return "user-login";
				} else {

					return "error";
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			map.addAttribute("errorMessage", "Link expired , generate new link");
			map.addAttribute("resendLink", true);
			return "error";
		}

		return "user-login";

	}
	
	@RequestMapping(value = "/manager/manageproduct.htm", method = RequestMethod.GET)
	public String manageProduct(HttpServletRequest request, ProductDAO productDAO, ModelMap map){
		HttpSession session = request.getSession();
		String role = (String)session.getAttribute("Role");
		if(role.equals("Manager")) {
			return "manageproduct";
		}
		else {
			map.addAttribute("errorMessage","Access Denied!");
			return "error";
		}
		
	}
	
	@RequestMapping(value = "/manager/managecustomer.htm", method = RequestMethod.GET)
	public String manageCustomer(HttpServletRequest request, UserDAO userDAO, ModelMap map) throws Exception{
		HttpSession session = request.getSession();
		String role = (String)session.getAttribute("Role");
		if(role.equals("Manager")) {
			ArrayList<User> customerList = userDAO.getCustomers();
			map.addAttribute("customerList",customerList);
			return "managecustomer";
		}
		else {
			map.addAttribute("errorMessage","Access Denied!");
			return "error";
		}
	}
	
	@RequestMapping(value = "/manager/manageproduct/addproduct.htm", method = RequestMethod.GET)
	public String addManagerProductList(HttpServletRequest request, ProductDAO productDAO, ModelMap map) throws Exception{
		HttpSession session = request.getSession();
		String role = (String)session.getAttribute("Role");
		if(role.equals("Manager")) {
			return "addproduct";
		}
		else {
			map.addAttribute("errorMessage","Access Denied!");
			return "error";
		}
	}
	
	@RequestMapping(value = "/manager/manageproduct/addproduct/product-add-success.htm", method = RequestMethod.POST)
	public String productAddSuccess(HttpServletRequest request, ProductDAO productDAO, ModelMap map) throws Exception{
		HttpSession session = request.getSession();
		String role = (String)session.getAttribute("Role");
		if(role.equals("Manager")) {
			Product product=new Product();
			product.setCode(request.getParameter("code"));
			product.setName(request.getParameter("name"));
			product.setPrice(Double.parseDouble(request.getParameter("price")));
			product.setProductOwner("Company");
			int contains=0;
			ArrayList<Product> productlist = productDAO.get();
			for(Product p : productlist) {
				if(p.getCode().equals(product.getCode())) {
					contains++;
					break;
				}
			}
			if(contains==0){
				boolean c = productDAO.add(product);
				if(c)
					return "product-add-success";
				else {
					map.addAttribute("errorMessage","Create Failed!");
					return "error";
				}
			}
			else {
				map.addAttribute("errorMessage","Product code already exists");
				return "error";
			}
		}
		else {
			map.addAttribute("errorMessage","Access Denied!");
			return "error";
		}
	}
	
	@RequestMapping(value = "/manager/manageproduct/editproduct.htm", method = RequestMethod.GET)
	public String editManagerProductList(HttpServletRequest request, ProductDAO productDAO, ModelMap map) throws Exception{
		HttpSession session = request.getSession();
		String role = (String)session.getAttribute("Role");
		if(role.equals("Manager")) {
			ArrayList<Product> productList = productDAO.get();
			map.addAttribute("productList",productList);
			return "editproduct";
		}
		else {
			map.addAttribute("errorMessage","Access Denied!");
			return "error";
		}
	}
	
	@RequestMapping(value = "/manager/manageproduct/editproduct/edit-selected-product.htm", method = RequestMethod.GET)
	public String productEditSelected(HttpServletRequest request, ProductDAO productDAO, ModelMap map) throws Exception{
		HttpSession session = request.getSession();
		String role = (String)session.getAttribute("Role");
		if(role.equals("Manager")) {
			String code = request.getParameter("code");
			String name = request.getParameter("name");
			double price = Double.parseDouble(request.getParameter("price"));
			map.addAttribute("code",code);
			map.addAttribute("name",name);
			map.addAttribute("price",price);
			return "edit-selected-product";
		}
		else {
			map.addAttribute("errorMessage","Access Denied!");
			return "error";
		}
	}
	
	@RequestMapping(value = "/manager/manageproduct/editproduct/edit-selected-product/product-edit-success.htm", method = RequestMethod.POST)
	public String productEditSuccess(HttpServletRequest request, ProductDAO productDAO, ModelMap map) throws Exception{
		HttpSession session = request.getSession();
		String role = (String)session.getAttribute("Role");
		if(role.equals("Manager")) {
			Product product=new Product();
			product.setCode(request.getParameter("code"));
			product.setName(request.getParameter("name"));
			product.setPrice(Double.parseDouble(request.getParameter("price")));
			product.setProductOwner("Company");
			boolean p = productDAO.update(product);
			if(p) return "product-edit-success";
			else {
				map.addAttribute("errorMessage","Update failed! "+" "+product.getCode()+" "+product.getName()+" "+product.getPrice());
				return "error";
			}
		}
		else {
			map.addAttribute("errorMessage","Access Denied!");
			return "error";
		}
	}
	
	@RequestMapping(value = "/manager/manageproduct/editproduct/delete-product.htm", method = RequestMethod.GET)
	public String productDeleteSuccess(HttpServletRequest request, ProductDAO productDAO, ModelMap map) throws Exception{
		HttpSession session = request.getSession();
		String role = (String)session.getAttribute("Role");
		if(role.equals("Manager")) {
			
			String code = request.getParameter("code");
			productDAO.delete(code);
			return "delete-product";
			
		}
		else {
			map.addAttribute("errorMessage","Access Denied!");
			return "error";	
		}
	}
	
	@RequestMapping(value = "/manager/managecustomer/editcustomer.htm", method = RequestMethod.GET)
	public String customerEdit(HttpServletRequest request, UserDAO userDAO, ModelMap map) throws Exception{
		HttpSession session = request.getSession();
		String role = (String)session.getAttribute("Role");
		if(role.equals("Manager")) {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String status = request.getParameter("status");
			String password = request.getParameter("password");
			
			map.addAttribute("id", id);
			map.addAttribute("name", name);
			map.addAttribute("email", email);
			map.addAttribute("status", status);
			map.addAttribute("password", password);
			
			return "editcustomer";
		}
		else {
			map.addAttribute("errorMessage","Access Denied!");
			return "error";
		}
	}
	
	@RequestMapping(value = "/manager/managecustomer/editcustomer/customer-edit-success.htm", method = RequestMethod.POST)
	public String customerEditSuccess(HttpServletRequest request, UserDAO userDAO, ModelMap map) throws Exception{
		HttpSession session = request.getSession();
		String role = (String)session.getAttribute("Role");
		if(role.equals("Manager")) {
			User user=new User();
			user.setId(Long.parseLong(request.getParameter("id")));
			user.setUserName(request.getParameter("name"));
			user.setUserEmail(request.getParameter("email"));
			user.setStatus(Integer.parseInt(request.getParameter("status")));
			user.setPassword(request.getParameter("password"));
			boolean p = userDAO.updateCustomer(user);
			if(p) return "customer-edit-success";
			else {
				map.addAttribute("errorMessage","Update failed!");
				return "error";
			}
		}
		else {
			map.addAttribute("errorMessage","Access Denied!");
			return "error";
		}
	}
	
	@RequestMapping(value = "/manager/vieworders.htm", method = RequestMethod.GET)
	public String viewOrders(HttpServletRequest request, OrderDAO orderDAO, ModelMap map) throws Exception {
		HttpSession session = request.getSession();
		String role = (String)session.getAttribute("Role");
		if(role.equals("Manager")) {
			ArrayList<Order> orderlist = orderDAO.get();
			map.addAttribute("orderlist",orderlist);
			return "vieworders";	
		}
		else {
			map.addAttribute("errorMessage","Access Denied!");
			return "error";
		}
	}
	
	@RequestMapping(value = "/manager/vieworders/approveorder.htm", method = RequestMethod.GET)
	public String approve(HttpServletRequest request, OrderDAO orderDAO, ModelMap map) throws Exception {
		HttpSession session = request.getSession();
		String role = (String)session.getAttribute("Role");
		if(role.equals("Manager")) {
			Long id = Long.parseLong(request.getParameter("id"));
			orderDAO.setStatus(id);
			return "approveorder";
		}
		else {
			map.addAttribute("errorMessage","Access Denied!");
			return "error";
		}
	}
	
	@RequestMapping(value = "customer/products.htm", method = RequestMethod.GET)
	public String displayCustomerProductList(HttpServletRequest request, ProductDAO productDAO, ModelMap map) throws Exception {
		HttpSession session = request.getSession();
		String role = (String)session.getAttribute("Role");
		if(role.equals("Customer")) {
			ArrayList<Product> productList = productDAO.get();
			map.addAttribute("productList",productList);
			return "products";	
		}
		else {
			map.addAttribute("errorMessage","Access Denied!");
			return "error";
		}
	}
	
	@RequestMapping(value = "/customer/products/viewcart.htm", method = RequestMethod.GET)
	public String updateCart(HttpServletRequest request, CartDAO cartDAO, ModelMap map) throws Exception {
		HttpSession session = request.getSession();
		String role = (String)session.getAttribute("Role");
		if(role.equals("Customer")) {
			String action=request.getParameter("action");
			if(action.equals("view")) {
				ArrayList<Cart> cartlist = cartDAO.get();
				map.addAttribute("cartList",cartlist);
				return "viewcart";
			}		
			else {
				Cart p = new Cart();
				p.setCode(request.getParameter("code"));
				p.setName(request.getParameter("name"));
				p.setPrice(Double.parseDouble(request.getParameter("price")));
				ArrayList<Cart> cartList = cartDAO.get();
				int contains=0;
				for(Cart cart : cartList) {
					if(cart.getCode().equals(p.getCode())) {
						contains++;
						break;
					}
				}
				if(contains==0){
					boolean c = cartDAO.add(p);
					if(c) {
						ArrayList<Cart> cartlist = cartDAO.get();
						map.addAttribute("cartList",cartlist);
						return "viewcart";
					}
					else {
						map.addAttribute("errorMessage","Unable to add to cart!"+p.getCode()+p.getName()+p.getPrice());
						return "error";
					}
				}
				else {
					map.addAttribute("errorMessage","Product already present in cart!");
					return "error";
	
				}
			}
			
		}
		else {
			map.addAttribute("errorMessage","Access Denied!");
			return "error";
		}
	}
	
	@RequestMapping(value = "/customer/products/viewcart/removesuccess.htm", method = RequestMethod.GET)
	public String removeFromCart(HttpServletRequest request, CartDAO cartDAO, ModelMap map) throws Exception {
		HttpSession session = request.getSession();
		String role = (String)session.getAttribute("Role");
		if(role.equals("Customer")) {
			String code = request.getParameter("code");
			cartDAO.delete(code);
			return "removesuccess";
		}
		else {
			map.addAttribute("errorMessage","Access Denied!");
			return "error";
		}
	}
	
	@RequestMapping(value = "/customer/products/viewcart/orderplaced.htm", method = RequestMethod.GET)
	public String orderPlaced(HttpServletRequest request, CartDAO cartDAO, OrderDAO orderDAO, ModelMap map) throws Exception {
		HttpSession session = request.getSession();
		String role = (String)session.getAttribute("Role");
		if(role.equals("Customer")) {
			String orderlist=request.getParameter("orderlist");
			Order order = new Order();
			order.setOrderItems(orderlist);
			order.setStatus("Awaiting approval");
			boolean o = orderDAO.add(order);
			cartDAO.clear();
			String useremail = "akshayuniq8592@gmail.com";
			sendEmail(useremail, "An order is awaiting your approval");
			return "orderplaced";
		}
		else {
			map.addAttribute("errorMessage","Access Denied!");
			return "error";
		}
	}
	
//	@RequestMapping(value = "/customer/managecustomerproducts.htm", method = RequestMethod.GET)
//	public String manageCustomerProduct(HttpServletRequest request, ProductDAO productDAO, ModelMap map){
//		HttpSession session = request.getSession();
//		String role = (String)session.getAttribute("Role");
//		if(role.equals("Customer")) {
//			return "managecustomerproducts";
//		}
//		else {
//			map.addAttribute("errorMessage","Access Denied!");
//			return "error";
//		}
//		
//	}
//	
//	@RequestMapping(value = "/customer/managecustomerproducts/addcustomerproduct.htm", method = RequestMethod.GET)
//	public String addCustomerProduct(HttpServletRequest request, ProductDAO productDAO, ModelMap map){
//		HttpSession session = request.getSession();
//		String role = (String)session.getAttribute("Role");
//		if(role.equals("Customer")) {
//			return "addcustomerproduct";
//		}
//		else {
//			map.addAttribute("errorMessage","Access Denied!");
//			return "error";
//		}
//	}
//
//	@RequestMapping(value = "/customer/managecustomerproducts/editcustomerproduct.htm", method = RequestMethod.GET)
//	public String editCustomerProduct(HttpServletRequest request, ProductDAO productDAO, ModelMap map){
//		HttpSession session = request.getSession();
//		String role = (String)session.getAttribute("Role");
//		if(role.equals("Customer")) {
//			ArrayList<Product> productList = productDAO.get();
//			map.addAttribute("productList",productList);
//			return "editcustomerproduct";
//		}
//		else {
//			map.addAttribute("errorMessage","Access Denied!");
//			return "error";
//		}
//	}
}
