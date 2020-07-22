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
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@XmlRootElement
public class UserDetailsModel
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@NotEmpty
	@Column(name="UserName")
	@JsonProperty("UserName")
	private String username;	
	@JsonProperty("Password")
	private String password;
	@JsonProperty("Roles")
	private String[] roles;
	
	public long getId()
	{
		return id;
	}
	
	public void setId(long id)
	{
		this.id=id;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username=username;
	}

	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password=password;
	}
	
	public String[] getRoles()
	{
		return roles;
	}
	
	public void setRoles(String[] roles)
	{
		this.roles=roles;
	}
	
	public String getRolesAsString()
	{
		return Arrays.toString(getRoles());
	}
	
	@Override
	public String toString()
	{
		return "AbasUserDetailsModel [username="+username+", password="+password+", roles="+Arrays.toString(roles)+"]";
	}
	
}
