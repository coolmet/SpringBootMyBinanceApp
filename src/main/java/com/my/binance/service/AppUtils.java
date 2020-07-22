package com.my.binance.service;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.my.binance.SprinBootAppConfiguration;
import de.abas.ceks.jedp.EDPMessage;
import de.abas.ceks.jedp.EDPMessageListener;

@Service
public class AppUtils
{
	@Autowired
	AppUtils appUtils;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	public String exceptionMessageDetails(Exception e)
	{
		String ret="@@@ "+e.getLocalizedMessage()+"\n";
		StackTraceElement st[]=e.getStackTrace();
		for(int i=0;i<st.length;i++)
		{
			ret+="@@@ "+st[i]+"\n";
		}
		return ret;
	}
	
	public String exceptionMessage(Exception e)
	{
		return "@@@ "+e.getLocalizedMessage();
	}
	
	public static boolean booleanValue(String fieldvalue)
	{
		char c=fieldvalue.toUpperCase().charAt(0);
		return (c=='E'||c=='Y'||c=='J'||c=='T'||c=='1')?true:false;
	}
	
}
