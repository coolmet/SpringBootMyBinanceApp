package com.my.binance.web;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.my.binance.HttpSessionConfig;
import com.my.binance.SprinBootAppConfiguration;
import com.my.binance.service.LanguageService;

@Controller
public class WebLinkController
{
	// http://localhost:9034/loginweb
	// http://localhost:9034/
	// http://localhost:9034/index
	// http://localhost:9034/default
	// http://localhost:9034/admin/login
	// http://localhost:9034/admin/index
	// http://localhost:9034/admin/settings
	// http://localhost:9034/admin/actuators
	// http://localhost:9034/admin/activesessions
	// http://localhost:9034/admin/login2
	// http://localhost:9034/admin/index2
	// http://localhost:9034/admin/actuators2
	// http://localhost:9034/admin/admin2
	// http://localhost:9034/admin/whreceipt2
	// http://localhost:9034/admin/activesessions2
	// http://localhost:9034/rest/get/json/all
	// http://localhost:9034/?lang=en
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	private HttpSessionConfig httpSessionConfig;
	
	@RequestMapping(value=
	{"/","/index"})
	public ModelAndView indexTh(HttpServletRequest request)
	{
		ModelAndView mav=new ModelAndView();
		if((""+SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals("anonymousUser"))
		{
			mav.setViewName("th_index");
		}
		else
		{
			mav.setViewName("redirect:/default");
		}
		return mav;
	}
	
	@RequestMapping(value=
	{"/loginweb"})
	public ModelAndView loginweb()
	{
		ModelAndView mav=new ModelAndView();
		if((""+SecurityContextHolder.getContext().getAuthentication().getPrincipal()).equals("anonymousUser"))
		{
			mav.setViewName("th_login");
		}
		else
		{
			mav.setViewName("redirect:/default");
		}
		return mav;
	}
	
	@RequestMapping("/default")
	public String defaultAfterLoginTh(HttpServletRequest request)
	{
		if(request.isUserInRole("ROLE_ADMIN"))
		{
			return "redirect:/admin/actuators/";
		}
		else if(request.isUserInRole("ROLE_RESTUSER"))
		{
			return "redirect:/rest/get/json/all";
		}
		else if(request.isUserInRole("ROLE_WEBUSER"))
		{
			return "redirect:/web/binance";
		}
		return "redirect:/";
	}
	
	@RequestMapping("/responsebodytest")
	@ResponseBody
	public String responseBodyTest(HttpServletRequest request)
	{
		Integer statusCode=(Integer)request.getAttribute("javax.servlet.error.status_code");
		Exception exception=(Exception)request.getAttribute("javax.servlet.error.exception");
		return String.format("<html><body><h2>Error Page</h2><div>Status code: <b>%s</b></div>"
		+"<div>Exception Message: <b>%s</b></div><body></html>",
		                     statusCode,exception==null?"N/A":exception.getMessage());
	}
	
}
