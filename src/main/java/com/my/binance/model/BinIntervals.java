package com.my.binance.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum BinIntervals
{
	_1s("1s",1),
	_1m("1m",60),
	_3m("3m",180),
	_5m("5m",300),
	_15m("15m",900),
	_30m("30m",1800),
	_1h("1h",3600),
	_2h("2h",7200),
	_4h("4h",14400),
	_6h("6h",21600),
	_8h("8h",28800),
	_12h("12h",43200),
	_1d("1d",86400),
	_3d("3d",259200),
	_1w("1w",604800),
	_1M("1M",2592000);
	
	public String intervalName;
	public int intervalValue;
	
	BinIntervals(final String intervalName,final int intervalValue)
	{
		this.intervalName=intervalName;
		this.intervalValue=intervalValue;
	}
	
	public String getIntervalName()
	{
		return intervalName;
	}
	
	public void setIntervalName(String intervalName)
	{
		this.intervalName=intervalName;
	}
	
	public int getIntervalValue()
	{
		return intervalValue;
	}
	
	public void setIntervalValue(int intervalValue)
	{
		this.intervalValue=intervalValue;
	}
	
	private static final Map<Integer,BinIntervals> mapValues=new HashMap<Integer,BinIntervals>();
	private static final Map<String,BinIntervals> mapNames=new HashMap<String,BinIntervals>();
	private static List<BinIntervals> listBigIntervals=new ArrayList<BinIntervals>();
	static
	{
		listBigIntervals=(List<BinIntervals>)Arrays.asList(BinIntervals.class.getEnumConstants());
		for(BinIntervals m:listBigIntervals)
		{
			mapValues.put(m.getIntervalValue(),m);
			mapNames.put(m.getIntervalName(),m);
		}
	}
	
	public static String[] names()
	{
		String valuesStr=Arrays.toString(BinIntervals.names());
		return valuesStr.substring(1,valuesStr.length()-1).replace(" ","").split(",");
	}
	
	public static List<String> nameArray()
	{
		return Arrays.asList(names());
	}
	
	public static List<BinIntervals> getEnums()
	{
		return listBigIntervals;
	}
	
	public static BinIntervals getEnumByName(String intervalName)
	{
		return !mapNames.containsKey(intervalName)?null:mapNames.get(intervalName);
	}
	
	public static BinIntervals getEnumByNumber(int intervalValue)
	{
		return !mapValues.containsKey(intervalValue)?null:mapValues.get(intervalValue);
	}
}
