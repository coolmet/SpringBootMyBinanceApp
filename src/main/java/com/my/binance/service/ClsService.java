package com.my.binance.service;

import com.my.binance.model.ClsOrder;
import com.my.binance.model.ExecResult;

public interface ClsService
{
	ExecResult addClsOrder(ClsOrder abasOrder);
	
	ExecResult addClsOrder(String jsonData);
}

