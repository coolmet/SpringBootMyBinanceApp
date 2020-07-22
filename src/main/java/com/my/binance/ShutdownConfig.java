package com.my.binance;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShutdownConfig
{
	@Bean
	public ShutdownConfigTerminateBean getTerminateBean()
	{
		return new ShutdownConfigTerminateBean();
	}
}
