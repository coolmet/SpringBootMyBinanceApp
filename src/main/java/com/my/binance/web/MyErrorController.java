package com.my.binance.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyErrorController implements ErrorController
{
	
	@RequestMapping("/error")
	public ModelAndView handleError(HttpServletRequest request)
	{
		ModelAndView mav=new ModelAndView();
		mav.setViewName("error");
		//
		Object status=request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		Exception e=(Exception)request.getAttribute("org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR");
		if(e==null)
		{
			e=(Exception)request.getAttribute("SPRING_SECURITY_403_EXCEPTION");
			status=403;
		}
		if(e==null)
		{
			e=(Exception)request.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		}
		//
		if(e!=null)
		{
			StringWriter sw=new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			mav.addObject("trace",sw.toString());
			mav.addObject("error",e.getLocalizedMessage());
			mav.addObject("message",""+e.getMessage());
			mav.addObject("exception",""+e);
		}
		else
		{
			mav.addObject("trace",request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
			mav.addObject("error",request.getAttribute(RequestDispatcher.ERROR_EXCEPTION));
			mav.addObject("message",request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
			mav.addObject("exception",request.getAttribute(RequestDispatcher.ERROR_EXCEPTION));
			for(Enumeration<String> enumeration=request.getAttributeNames();enumeration.hasMoreElements();)
			{
				String attributeName=enumeration.nextElement();
				Object attribute=request.getAttribute(attributeName);
				System.out.println(attributeName+" -> "+attribute.getClass().getName()+":"+attribute.toString());
			}
		}
		//
		if(status!=null)
		{
			Integer statusCode=Integer.valueOf(status.toString());
			if(statusCode==HttpStatus.NOT_FOUND.value())
			{
				mav.setViewName("error-404");
			}
			
		}
		mav.addObject("timestamp",ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm.ss.SSS")));
		mav.addObject("path",""+request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
		mav.addObject("status",status);
		
		return mav;
	}
	
	@Override
	public String getErrorPath()
	{
		return "/error";
	}
}
