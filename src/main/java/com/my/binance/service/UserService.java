package com.my.binance.service;

import java.util.List;
import com.my.binance.model.UserDetailsModel;

public interface UserService
{
	List<UserDetailsModel> findAll();
	
	UserDetailsModel findByUserName(String username);
	
	List<UserDetailsModel> findAllByUserRole(String userRole);
}
