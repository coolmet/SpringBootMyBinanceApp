package com.my.binance.model;

public class BinLinks
{
	public static String URL_SYMBOLS="https://www.binance.com/api/v1/exchangeInfo";
	
	public static String URL_KLINES(String symbol,String interval,int limit,long startTime,long endTime)
	{
		return "https://www.binance.com/api/v1/klines?symbol="+symbol+"&interval="+interval+"&limit="+limit+"&startTime="+startTime+"&endTime="+endTime;
	};
	
	public static String URL_AGGTRADES(String symbol,int limit)
	{
		return "https://www.binance.com/api/v1/aggTrades?symbol="+symbol+"&limit="+limit;
	};
	
}
