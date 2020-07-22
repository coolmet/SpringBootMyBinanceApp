package com.my.binance;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;

@ConfigurationProperties(prefix="server")
public class ConfigPropertiesServer
{
	@Min(1025)
	@Max(65536)
	private int port;
	@NotBlank
	private String connectionTimeout;
	@NotBlank
	private Servlet servlet=new Servlet();
	@NotBlank
	private Token token=new Token();
	
	public int getPort()
	{
		return port;
	}
	
	public void setPort(int port)
	{
		this.port=port;
	}
	
	public String getConnectionTimeout()
	{
		return connectionTimeout;
	}
	
	public void setConnectionTimeout(String connectionTimeout)
	{
		this.connectionTimeout=connectionTimeout;
	}
	
	public Servlet getServlet()
	{
		return servlet;
	}
	
	public void setServlet(Servlet servlet)
	{
		this.servlet=servlet;
	}
	
	public Token getToken()
	{
		return token;
	}
	
	public void setToken(Token token)
	{
		this.token=token;
	}
	
	public static class Servlet
	{
		@NotBlank
		private Session session;
		
		public Session getSession()
		{
			return session;
		}
		
		public void setSession(Session session)
		{
			this.session=session;
		}
		
		public static class Session
		{
			@NotBlank
			private String timeout;
			
			public String getTimeout()
			{
				return timeout;
			}
			
			public void setTimeout(String timeout)
			{
				this.timeout=timeout;
			}
			
		}
		
	}
	
	public static class Token
	{
		@NotBlank
		private long expirationTime;
		@NotBlank
		private String secret;
		@NotBlank
		private String prefix;
		@NotBlank
		private String headerString;
		
		public long getExpirationTime()
		{
			return expirationTime;
		}
		
		public void setExpirationTime(long expirationTime)
		{
			this.expirationTime=expirationTime;
		}
		
		public String getSecret()
		{
			return secret;
		}
		
		public void setSecret(String secret)
		{
			this.secret=secret;
		}
		
		public String getPrefix()
		{
			return prefix;
		}
		
		public void setPrefix(String prefix)
		{
			this.prefix=prefix;
		}
		
		public String getHeaderString()
		{
			return headerString;
		}
		
		public void setHeaderString(String headerString)
		{
			this.headerString=headerString;
		}
		
	}
}
