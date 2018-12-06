package com.snehal.entity.model;

import java.sql.Date;

/**
 * @author $nehal
 *
 */
public class Order {
	
	int orderId;
	Date orderDate;
	double amout;
	
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public double getAmout() {
		return amout;
	}
	public void setAmout(double amout) {
		this.amout = amout;
	}
	

}
