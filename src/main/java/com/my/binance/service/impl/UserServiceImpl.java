package com.my.binance.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.my.binance.ConfigPropertiesUser;
import com.my.binance.model.UserDetailsModel;
import com.my.binance.service.UserService;

@Service
@Transactional(rollbackFor=Exception.class)
public class UserServiceImpl implements UserService
{
	@Autowired
	ConfigPropertiesUser configPropertiesUser;
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	@Secured(value=
	{"ROLE_RESTUSER"})
	public List<UserDetailsModel> findAll()
	{
		return configPropertiesUser.getUsers();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public UserDetailsModel findByUserName(String userName)
	{
		return configPropertiesUser.getUsers().stream().filter(f->f.getUsername().equals(userName)).findFirst().get();
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<UserDetailsModel> findAllByUserRole(String userRole)
	{
		return configPropertiesUser.getUsers().stream().filter(f->f.getRolesAsString().contains(userRole)).collect(Collectors.toList());
	}
	
}
