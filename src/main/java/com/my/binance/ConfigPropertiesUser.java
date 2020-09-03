package com.my.binance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.boot.context.properties.ConfigurationProperties;
import com.my.binance.model.UserDetailsModel;

@ConfigurationProperties("users")
public class ConfigPropertiesUser
{
	private final List<UserDetailsModel> admins=new ArrayList<>();
	private final List<UserDetailsModel> restusers=new ArrayList<>();
	private final List<UserDetailsModel> webusers=new ArrayList<>();
	
	public List<UserDetailsModel> getAdmins()
	{
		return this.admins;
	}
	
	public List<UserDetailsModel> getRestusers()
	{
		return restusers;
	}
	
	public List<UserDetailsModel> getWebusers()
	{
		return webusers;
	}
	
	public List<UserDetailsModel> getUsers()
	{
		return Stream.of(admins,restusers,webusers)
		             .flatMap(x->x.stream())
		             .collect(Collectors.toList());
	}
	
}
