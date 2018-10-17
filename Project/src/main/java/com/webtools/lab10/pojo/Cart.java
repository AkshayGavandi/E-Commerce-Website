package com.webtools.lab10.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Cart")
public class Cart implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1234324234L;
	private String code;
    private String name;
    private double price;
    private int quantity;

    @Id
    @Column(name = "Code", length = 20, nullable = false)
    public String getCode() {
        return code;
    }
 
    public void setCode(String code) {
        this.code = code;
    }
 
    @Column(name = "Name", length = 255, nullable = false)
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    @Column(name = "Price", nullable = false)
    public double getPrice() {
        return price;
    }
 
    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "Quantity", nullable = false)
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
   
	@Override
    public String toString() {
		return code;
    	
    }
}
