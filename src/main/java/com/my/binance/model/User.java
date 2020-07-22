package com.my.binance.model;

import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User
{
	
	@JsonProperty("Id")
	public long id;
	
	@JsonProperty("UserName")
	public String userName;
	
	@JsonProperty("FirstName")
	public String firstName;
	
	@JsonProperty("LastName")
	public String lastName;
	
	@JsonProperty("Lang")
	public String lang;
	
	@JsonProperty("Token")
	public String token;
	
	@Override
	public String toString()
	{
		return "User [id="+id+", userName="+userName+", firstName="+firstName+", lastName="+lastName+", lang="+lang+", token="+token+"]";
	}
	
}
