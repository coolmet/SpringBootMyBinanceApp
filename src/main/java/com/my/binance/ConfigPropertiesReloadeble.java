package com.my.binance;

import org.springframework.stereotype.Component;
import com.my.binance.service.ReloadablePropertiesService;

@Component
public class ConfigPropertiesReloadeble extends ReloadablePropertiesService
{
	@Override
	protected void propertiesReloaded()
	{
		// do something after a change in property values was done
	}
}
