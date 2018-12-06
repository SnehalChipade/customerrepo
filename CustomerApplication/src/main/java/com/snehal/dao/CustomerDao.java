package com.snehal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;

import org.springframework.stereotype.Repository;

import com.snehal.entity.model.Customer;

/**
 * @author $nehal
 *
 */
@Repository
public class CustomerDao {

	public Customer getCustomerForId(int id) throws ServletException {
		
		String url = System.getProperty("cloudsql");
	    try {
	      Connection conn = DriverManager.getConnection(url);
	      
	      String sql = "select ID, NAME, AGE, ADDRESS from CUSTOMER where ID=?";
	      
	      PreparedStatement stmt = conn.prepareStatement(sql);
	      stmt.setInt(1, id);
	      
	      ResultSet rs = stmt.executeQuery();
	      
	      Customer cust = null;
	      
	      while(rs.next()){
	    	 
	    	  cust = new Customer();
	    	 
	    	  cust.setAddress(rs.getString(4));
	    	  cust.setAge(rs.getInt(3));
	    	  cust.setName(rs.getString(2));
	    	  cust.setId(rs.getInt(1));
	      }
	      
	      stmt.close();
	      conn.close();
	      
	      return cust;
	      	
	    } catch (SQLException e) {
	      throw new ServletException("Unable to connect to Cloud SQL" + e.getMessage(), e);
	    }
	}

	public int save(Customer customer) throws ServletException {
		
		String url = System.getProperty("cloudsql");
	    try {
	      Connection conn = DriverManager.getConnection(url);
	      
	      String sql = "insert into CUSTOMER(NAME, AGE, ADDRESS) values (?,?,?)";
	      
	      PreparedStatement stmt = conn.prepareStatement(sql);
	      stmt.setString(1, customer.getName());
	      stmt.setInt(2, customer.getAge());
	      stmt.setString(3, customer.getAddress());
	      stmt.executeUpdate();
	      
	      sql = "select ID from CUSTOMER where NAME = ? and  AGE = ? and ADDRESS = ?";
	      
	      stmt = conn.prepareStatement(sql);
	      stmt.setString(1, customer.getName());
	      stmt.setInt(2, customer.getAge());
	      stmt.setString(3, customer.getAddress());
	     
	      ResultSet rs = stmt.executeQuery();
	      
	      int id = -1;
	      
	      while(rs.next()){
	    	  id = rs.getInt(1);
	      }
	      
	      if(id!=-1){
	    	  return id;
	      }else{
	    	  throw new ServletException("Failed to save customer to database");
	      }
	      
	    } catch (SQLException e) {
	      throw new ServletException("Unable to connect to Cloud SQL" + e.getMessage(), e);
	    }
	}

	
}
