package com.snehal.customerservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snehal.dao.UserDao;

/**
 * @author $nehal
 *
 */

@Service
public class SessionValidationService {
	
	@Autowired
	private UserDao userDao;
	
	public boolean validateUser(String token) throws ServletException{
		
		if(userDao.tokenExists(token)){
			return true;
		}
		
		return false;
	}
	
	
	public void registerUserSession(String userId, String token) throws ServletException{
		
		if(userDao.userExists(userId)){
			userDao.saveToken(userId, token);
		}
	}
	
	
	public void unregisterUserSession(String token) throws ServletException{
		
		userDao.resetToken(token);
	}

}
