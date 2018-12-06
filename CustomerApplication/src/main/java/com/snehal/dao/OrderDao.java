package com.snehal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.stereotype.Repository;

import com.snehal.entity.model.Order;

/**
 * @author $nehal
 *
 */
@Repository
public class OrderDao {

	public List<Order> getOrders(int id) throws ServletException {
		
		String url = System.getProperty("cloudsql");
	    try {
	      Connection conn = DriverManager.getConnection(url);
	      
	      String sql = "select ID, DATE, AMOUNT from ORDERS where CUSTOMER_ID=?";
	      
	      PreparedStatement stmt = conn.prepareStatement(sql);
	      stmt.setInt(1, id);
	      
	      ResultSet rs = stmt.executeQuery();
	      
	      List<Order> orders = new ArrayList<Order>();
	      
	      while(rs.next()){
	    	 Order order = new Order();
	    	 
	    	 order.setOrderId(rs.getInt(1));
	    	 order.setAmout(rs.getDouble(3));
	    	 order.setOrderDate(rs.getDate(2));
	    	 
	    	 orders.add(order);
	      }
	      
	      stmt.close();
	      conn.close();
	      return orders;
	      
	    } catch (SQLException e) {
	      throw new ServletException("Unable to connect to Cloud SQL" + e.getMessage(), e);
	    }
	}

	public void save(Order order, int id) throws ServletException {
		
		String url = System.getProperty("cloudsql");
	    try {
	      Connection conn = DriverManager.getConnection(url);
	      
	      String sql = "insert into ORDERS(DATE, CUSTOMER_ID, AMOUNT) values (?,?,?)";
	      
	      PreparedStatement stmt = conn.prepareStatement(sql);
	      stmt.setDate(1, order.getOrderDate());
	      stmt.setInt(2, id);
	      stmt.setDouble(3, order.getAmout());
	      stmt.executeUpdate();
	      
	      stmt.close();
	      conn.close();
	      
	    } catch (SQLException e) {
	      throw new ServletException("Unable to connect to Cloud SQL" + e.getMessage(), e);
	    }
	    
	}

}
