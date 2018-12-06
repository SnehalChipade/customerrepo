package com.snehal.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;

import org.springframework.stereotype.Repository;


/**
 * @author $nehal
 *
 */
@Repository
public class UserDao {
	
	public String getPassword(String id) throws ServletException {
		
		String url = System.getProperty("cloudsql");
	    try {
	      Connection conn = DriverManager.getConnection(url);
	      
	      String sql = "select PASSWORD from  USER where USERID=?";
	      
	      PreparedStatement stmt = conn.prepareStatement(sql);
	      stmt.setString(1, id);
	      
	      ResultSet rs = stmt.executeQuery();
	      
	      String pwd = null;
	      
	      while(rs.next()){
	    	  pwd = rs.getString(1);
	      }
	      
	      stmt.close();
	      conn.close();
	      return pwd;
	      
	    } catch (SQLException e) {
	      throw new ServletException("Unable to connect to Cloud SQL" + e.getMessage(), e);
	    }
	}

	public void save(String id, String password) throws ServletException {
		
		String url = System.getProperty("cloudsql");
	    try {
	    	Connection conn = DriverManager.getConnection(url);
	      
	      String sql = "insert into USER(USERID, PASSWORD) values (?,?)";
	      
	      PreparedStatement stmt = conn.prepareStatement(sql);
	      stmt.setString(1, id);
	      stmt.setString(2, password);
	      
	      stmt.executeUpdate();
	      stmt.close();
	      conn.close();
	      
	    } catch (SQLException e) {
	      throw new ServletException("Unable to connect to Cloud SQL" + e.getMessage(), e);
	    }
	}

	public boolean userExists(String user) throws ServletException {
		
		String url = System.getProperty("cloudsql");
	    try {
	      Connection conn = DriverManager.getConnection(url);
	      
	      String sql = "select USERID from  USER where USERID=?";
	      
	      PreparedStatement stmt = conn.prepareStatement(sql);
	      stmt.setString(1, user);
	      
	      ResultSet rs = stmt.executeQuery();
	      
	      boolean exists = false;
	      
	      while(rs.next()){
	    	  exists = true;
	      }
	      
	      stmt.close();
	      conn.close();
	      return exists;
	      
	    } catch (SQLException e) {
	      throw new ServletException("Unable to connect to Cloud SQL" + e.getMessage(), e);
	    }
	}

	public boolean tokenExists(String token) throws ServletException {
			
			String url = System.getProperty("cloudsql");
		    try {
		      Connection conn = DriverManager.getConnection(url);
		      
		      String sql = "select TOKEN from  USER where TOKEN=?";
		      
		      PreparedStatement stmt = conn.prepareStatement(sql);
		      stmt.setString(1, token);
		      
		      ResultSet rs = stmt.executeQuery();
		      
		      boolean exists = false;
		      
		      while(rs.next()){
		    	  String authToken = rs.getString(1);
		    	  if(authToken!=null){
		    		  exists = true;
		    	  }
		      }
		      
		      stmt.close();
		      conn.close();
		      return exists;
		      
		    }catch(SQLException e){
		    	
		      throw new ServletException("Unable to connect to Cloud SQL" + e.getMessage(), e);
		      
		    }
		}
	
	public void saveToken(String id, String token) throws ServletException {
		
		String url = System.getProperty("cloudsql");
	    try {
	    	Connection conn = DriverManager.getConnection(url);
	      
	      String sql = "update USER set TOKEN=? where USERID=?" ;
	      
	      PreparedStatement stmt = conn.prepareStatement(sql);
	      stmt.setString(1, token);
	      stmt.setString(2, id);
	      
	      stmt.executeUpdate();
	      stmt.close();
	      conn.close();
	      
	    } catch (SQLException e) {
	      throw new ServletException("Unable to connect to Cloud SQL" + e.getMessage(), e);
	    }
	}

	public void resetToken(String token) throws ServletException {
		
		String url = System.getProperty("cloudsql");
	    try {
	    	Connection conn = DriverManager.getConnection(url);
	      
	      String sql = "update USER set TOKEN=? where TOKEN=?" ;
	      
	      PreparedStatement stmt = conn.prepareStatement(sql);
	      stmt.setString(1, null);
	      stmt.setString(2, token);
	      
	      stmt.executeUpdate();
	      stmt.close();
	      conn.close();
	      
	    } catch (SQLException e) {
	      throw new ServletException("Unable to connect to Cloud SQL" + e.getMessage(), e);
	    }
	}
	
	
	}
