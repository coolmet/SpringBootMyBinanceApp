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
		String path=""+request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
		String timestamp=ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm.ss.SSS"));
		String error="";
		String message="";
		String exception="";
		String trace="";
		//
		Exception e=(Exception)request.getAttribute("org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR");
		if(e==null)
		{
			e=(Exception)request.getAttribute("javax.servlet.error.exception");
		}
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
		if(e==null)
		{
			error=""+request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
			message=""+request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
			exception=""+request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
			trace=""+status;
			// for(Enumeration<String> enumeration=request.getAttributeNames();enumeration.hasMoreElements();)
			// {
			// String attributeName=enumeration.nextElement();
			// Object attribute=request.getAttribute(attributeName);
			// System.out.println(attributeName+" \t\t-> "+attribute.getClass().getName()+":"+attribute.toString());
			// }
		}
		else
		{
			StringWriter sw=new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			error=""+e.getLocalizedMessage();
			message=""+e.getMessage();
			exception=""+e;
			trace=sw.toString();
		}
		error=error.equals("null")?"":error;
		message=message.equals("null")?"":message;
		exception=exception.equals("null")?"":exception;
		trace=trace.equals("null")?"":trace;
		//
		if(status!=null)
		{
			if(error.equals(""))
			{
				error=HttpStatus.valueOf(Integer.valueOf(status.toString())).series().name();
			}
			if(message.equals(""))
			{
				message=HttpStatus.valueOf(Integer.valueOf(status.toString())).getReasonPhrase();
			}
			if(exception.equals(""))
			{
				exception=HttpStatus.valueOf(Integer.valueOf(status.toString())).name();
			}
		}
		mav.addObject("timestamp",""+timestamp);
		mav.addObject("path",""+path);
		mav.addObject("status",""+status);
		mav.addObject("error",""+error);
		mav.addObject("message",""+message);
		mav.addObject("exception",""+exception);
		mav.addObject("trace",""+trace);
		
		return mav;
	}
	
	@Override
	public String getErrorPath()
	{
		return "/error";
	}
}
