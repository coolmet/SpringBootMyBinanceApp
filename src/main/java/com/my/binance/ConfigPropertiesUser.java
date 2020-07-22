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
	private final List<UserDetailsModel> rests=new ArrayList<>();
	
	public List<UserDetailsModel> getAdmins()
	{
		return this.admins;
	}
	
	public List<UserDetailsModel> getRests()
	{
		return rests;
	}
	
	public List<UserDetailsModel> getUsers()
	{
		return Stream.of(admins,rests)
		             .flatMap(x->x.stream())
		             .collect(Collectors.toList());
	}
	
}
