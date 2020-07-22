package com.my.binance.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.my.binance.SprinBootAppConfiguration;
import com.my.binance.model.UserDetailsModel;
import com.my.binance.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
	@Autowired
	TokenService tokenService;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager,ApplicationContext ctx)
	{
		this.authenticationManager=authenticationManager;
		this.tokenService=ctx.getBean(TokenService.class);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
	                                            HttpServletResponse res) throws AuthenticationException
	{
		try
		{
			UserDetailsModel creds=new ObjectMapper().readValue(req.getInputStream(),UserDetailsModel.class);
			Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
			                                                                                               creds.getPassword(),
			                                                                                               new ArrayList<>()));
			User u=(User)auth.getPrincipal();
			LOGGER.info("header-1>>>>>>>>>"+u.getUsername()+":"+u.getAuthorities().toString());
			return auth;
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest req,
	                                        HttpServletResponse res,
	                                        FilterChain chain,
	                                        Authentication auth) throws IOException,ServletException
	{
		
		User u=(User)auth.getPrincipal();
		String auths="";
		Collection<? extends GrantedAuthority> authorities=u.getAuthorities();
		for(GrantedAuthority grantedAuthority:authorities)
		{
			auths+=";"+grantedAuthority.getAuthority();
		}
		String token=Jwts.builder()
		                 .setSubject((u).getUsername()+"^"+auths.substring(1))
		                 .setIssuedAt(new Date())
		                 // .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
		                 .setExpiration(new Date(System.currentTimeMillis()+tokenService.getTokenExpirationTime()))
		                 .signWith(SignatureAlgorithm.HS512,tokenService.getSecret().getBytes())
		                 .compact();
		LOGGER.info("token-1>>>>>>>>>"+u.getUsername()+":"+u.getAuthorities().toString()+":"+token);
		res.addHeader(tokenService.getHeaderString(),tokenService.getPrefix()+token);
		//
		com.my.binance.model.User u2=new com.my.binance.model.User();
		u2.userName=u.getUsername();
		u2.firstName="";
		u2.lang="TR";
		u2.lastName="";
		u2.token=tokenService.getPrefix()+token;
		res.addHeader("User",new ObjectMapper().writeValueAsString(u2));
		//
		res.getWriter().write(new ObjectMapper().writeValueAsString(u2));
		res.getWriter().flush();
		res.getWriter().close();
	}
}
