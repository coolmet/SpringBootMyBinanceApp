package com.my.binance.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.my.binance.SprinBootAppConfiguration;
import com.my.binance.service.TokenService;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter
{
	@Autowired
	TokenService tokenService;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	public JWTAuthorizationFilter(AuthenticationManager authManager,ApplicationContext ctx)
	{
		super(authManager);
		this.tokenService=ctx.getBean(TokenService.class);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest req,
	                                HttpServletResponse res,
	                                FilterChain chain) throws IOException,ServletException
	{
		String header=req.getHeader(tokenService.getHeaderString());
		LOGGER.info("header-2>>>>>>>>>"+header);
		if(header==null||!header.startsWith(tokenService.getPrefix()))
		{
			chain.doFilter(req,res);
			return;
		}
		UsernamePasswordAuthenticationToken authentication=getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req,res);
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
	{
		String token=request.getHeader(tokenService.getHeaderString());
		LOGGER.info("token-2>>>>>>>>>"+token);
		if(token!=null)
		{
			// parse the token.
			String user_auth=Jwts.parser()
			                     .setSigningKey(tokenService.getSecret().getBytes())
			                     .parseClaimsJws(token.replace(tokenService.getPrefix(),""))
			                     .getBody()
			                     .getSubject();
			LOGGER.info("token-2-user_auth>>>>>>>>>"+Jwts.parser()
			                                             .setSigningKey(tokenService.getSecret().getBytes())
			                                             .parseClaimsJws(token.replace(tokenService.getPrefix(),""))
			                                             .getBody());
			
			if(user_auth!=null)
			{
				String[] auths=user_auth.substring(user_auth.indexOf("^")+1).split(";");
				List<GrantedAuthority> grantedAuthorities=new ArrayList<GrantedAuthority>();
				for(String auth:auths)
				{
					grantedAuthorities.add(new SimpleGrantedAuthority(auth));
				}
				return new UsernamePasswordAuthenticationToken(user_auth.substring(0,user_auth.indexOf("^")),null,grantedAuthorities);
			}
			return null;
		}
		return null;
	}
}
