package com.snehal.customerservice;

import java.util.List;
import javax.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snehal.dao.CustomerDao;
import com.snehal.dao.OrderDao;
import com.snehal.dao.UserDao;
import com.snehal.entity.model.Customer;
import com.snehal.entity.model.Order;

/**
 * @author $nehal
 *
 */

@Service
public class CustomerDataService {
	
	static Logger log = LoggerFactory.getLogger(CustomerDataService.class);
	
	static{
		try {
			Class.forName("com.mysql.jdbc.GoogleDriver");
			
		} catch (ClassNotFoundException e) {
			
			log.error("Could not load driver class. " + e.getMessage());
		}  
	}
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private OrderDao orderDao;
	
	
	public Customer getCustomer(int id) throws ServletException {
		
		Customer customer = customerDao.getCustomerForId(id);
		
		if(customer!=null){
			
			List<Order> orders = orderDao.getOrders(id);
			customer.setOrders(orders);
		}
		return customer;
	}

	public int saveCustomer(Customer customer) throws ServletException {
		
		List<Order> orders = customer.getOrders();
		
		int id = customerDao.save(customer);
		
		for(Order order : orders){
			
			orderDao.save(order, id);
		}
		return id;
	}

	
	public boolean validateUser(String id, String password) throws ServletException {
		
		String pwd = userDao.getPassword(id);
		
		if(pwd!=null){
			return pwd.equals(password);
		}
		
		return false;
	}

	
	public void saveUser(String id, String password) throws ServletException {
		
		userDao.save(id, password);
	}

	public boolean userExists(String user) throws ServletException {
		
		return userDao.userExists(user);
	}
	
	
}
