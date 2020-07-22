package com.my.binance.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.binance.ConfigPropertiesApp;
import com.my.binance.SprinBootAppConfiguration;
import com.my.binance.model.ClsOrder;
import com.my.binance.model.ExecResult;
import com.my.binance.service.AppUtils;
import com.my.binance.service.ClsService;
import com.my.binance.service.ClsSessionService;

@Service
@Transactional(rollbackFor=Exception.class)
public class ClsServiceImpl implements ClsService
{
	@Autowired
	private ConfigPropertiesApp configApp;
	
	@Autowired
	ClsSessionService edpSessionService;
	
	@Autowired
	AppUtils appUtils;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	@Override
	public ExecResult addClsOrder(ClsOrder clsOrder)
	{
		ExecResult er=new ExecResult();
		try
		{
			er=this.addClsOrder(new ObjectMapper().writeValueAsString(clsOrder));
		}
		catch(Exception e)
		{
			er.isSuccess=false;
			er.message=appUtils.exceptionMessage(e);
			er.messageLong=appUtils.exceptionMessageDetails(e);
		}
		return er;
	}
	
	@Override
	public ExecResult addClsOrder(String jsonData)
	{
		ExecResult er=new ExecResult();
		try
		{
			er.isSuccess=false;
			er.message="Bağlantı Hatası";
			er.messageLong="Bağlantı Hatası ..."+"{"+configApp.getEdp().getServerip()+":"+configApp.getEdp().getPort()+"/"+configApp.getS3().getMandant()+"}";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			LOGGER.info(appUtils.exceptionMessage(e));
			LOGGER.debug(appUtils.exceptionMessageDetails(e));
			er.isSuccess=false;
			er.message=appUtils.exceptionMessage(e);
			er.messageLong=appUtils.exceptionMessageDetails(e);
		}
		return er;
	}
	
}
