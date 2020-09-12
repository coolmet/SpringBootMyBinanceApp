package com.my.binance.web;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.my.binance.HttpSessionConfig;
import com.my.binance.SprinBootAppConfiguration;
import com.my.binance.service.LanguageService;

@Controller
public class WebLinkControllerAdmin
{
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	private HttpSessionConfig httpSessionConfig;
	
	@RequestMapping(value=
	{"/admin/settings"})
	public ModelAndView adminsettings()
	{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("th_admin_settings");
		return mav;
	}
	
	@RequestMapping(value=
	{"/admin/index"})
	public ModelAndView adminindex()
	{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("th_index");
		return mav;
	}
	
	@RequestMapping(value=
	{"/admin/login"})
	public ModelAndView adminlogin()
	{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("th_login");
		return mav;
	}
	
	@RequestMapping(value=
	{"/admin/actuators"})
	public ModelAndView adminactuators()
	{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("th_admin_actuators");
		return mav;
	}
	
	@RequestMapping(value=
	{"/admin/activesessions"})
	public ModelAndView adminactivesessions()
	{
		ModelAndView mav=new ModelAndView();
		UserDetails activeSession=(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserDetails> allSessions=sessionRegistry.getAllPrincipals().stream()
		                                             .filter(u->!sessionRegistry.getAllSessions(u,true).isEmpty())
		                                             .map(u->(UserDetails)u)
		                                             .collect(Collectors.toList());
		//
		List<UserDetails> allSessions1=httpSessionConfig.getActiveSessions()
		                                                .stream()
		                                                .map(session->(UserDetails)((SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getPrincipal())
		                                                .collect(Collectors.toList());
		//
		LOGGER.debug("sessionRegistry:"+sessionRegistry.getAllPrincipals().size()+"\t"+
		"httpSessionConfig.getActiveSessions:"+httpSessionConfig.getActiveSessions().size()+"\t"+
		"allSessions:"+allSessions.size()+
		"allSessions1:"+allSessions1.size()+
		"");
		//
		mav.addObject("activeSession",activeSession);
		mav.addObject("allSessions",allSessions);
		mav.setViewName("th_admin_activesessions");
		return mav;
	}
	
	// ******
	@RequestMapping(value=
	{"/admin/login2"})
	public ModelAndView adminlogin2()
	{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("th_login2");
		return mav;
	}
	
	@RequestMapping(value=
	{"/admin/index2"})
	public ModelAndView adminindex2()
	{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("th_index2");
		return mav;
	}
	
	@RequestMapping(value=
	{"/admin/actuators2"})
	public ModelAndView adminactuators2()
	{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("th_admin_actuators2");
		return mav;
	}
	
	@RequestMapping(value=
	{"/admin/admin2"})
	public ModelAndView adminadmin2()
	{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("th_admin2");
		return mav;
	}
	
	@RequestMapping(value=
	{"/admin/whreceipt2"})
	public ModelAndView adminwhreceipt2()
	{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("th_wh_receipt2");
		return mav;
	}
	
	@RequestMapping(value=
	{"/admin/activesessions2"})
	public ModelAndView adminactivesessions2()
	{
		ModelAndView mav=new ModelAndView();
		UserDetails activeSession=(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<UserDetails> allSessions=sessionRegistry.getAllPrincipals().stream()
		                                             .filter(u->!sessionRegistry.getAllSessions(u,true).isEmpty())
		                                             .map(u->(UserDetails)u)
		                                             .collect(Collectors.toList());
		//
		List<UserDetails> allSessions1=httpSessionConfig.getActiveSessions()
		                                                .stream()
		                                                .map(session->(UserDetails)((SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getPrincipal())
		                                                .collect(Collectors.toList());
		//
		LOGGER.debug("sessionRegistry:"+sessionRegistry.getAllPrincipals().size()+"\t"+
		"httpSessionConfig.getActiveSessions:"+httpSessionConfig.getActiveSessions().size()+"\t"+
		"allSessions:"+allSessions.size()+
		"allSessions1:"+allSessions1.size()+
		"");
		//
		mav.addObject("activeSession",activeSession);
		mav.addObject("allSessions",allSessions);
		mav.setViewName("th_admin_activesessions2");
		return mav;
	}
}
