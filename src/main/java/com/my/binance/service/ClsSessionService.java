package com.my.binance.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.binance.ConfigPropertiesApp;
import com.my.binance.SprinBootAppConfiguration;

@Service
// @Scope(value="session")
public class ClsSessionService
{
	@Autowired
	AppUtils appUtils;
	
	@Autowired
	private ConfigPropertiesApp configPropertiesApp;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	public boolean CLSSESSION_START()
	{
		
		return true;
	}
	
	public boolean CLSSESSION_END()
	{
		return true;
	}
	
}
