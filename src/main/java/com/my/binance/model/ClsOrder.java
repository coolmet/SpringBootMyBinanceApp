package com.my.binance.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClsOrder
{
	@JsonProperty("Master")
	public ClsMaster master;
	
	@JsonProperty("Line")
	public List<ClsLine> line;
	
	@Override
	public String toString()
	{
		return "ClsOrder [CLSMASTER="+master+", CLSLINE="+line+"]";
	}
	
}
