package com.my.binance;

import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;

public class ShutdownConfigTerminateBean
{
	//@Autowired
	//EdpSessionService edpSessionService;
	
	@PreDestroy
	public void onDestroy() throws Exception
	{
		System.out.println("XXXService is closing ...");
		//edpSessionService.EDPSESSION_END();
		System.out.println("XXXService is closed ...");
		System.out.println("Spring Container is destroyed!");
	}
}

