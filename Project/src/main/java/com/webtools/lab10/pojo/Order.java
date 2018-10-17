package com.webtools.lab10.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="order_table")
public class Order {
	public Order(){
		
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="order_number")
	private long orderNumber;
	
	@Column(name="order_items")
	private String orderItems;
	
	@Column(name="status")
	private String status;

	public long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(String orderItems) {
		this.orderItems = orderItems;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
