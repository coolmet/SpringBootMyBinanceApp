package com.my.binance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExecResult
{
	@JsonProperty("IsSuccess")
	public boolean isSuccess;
	@JsonProperty("Message")
	public String message;
	@JsonProperty("MessageLong")
	public String messageLong;
	
	@Override
	public String toString()
	{
		return "ExecResult [IsSuccess="+isSuccess+", Message="+message+", MessageLong="+messageLong+"]";
	}
	
}
