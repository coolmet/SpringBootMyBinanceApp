package com.my.binance.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.my.binance.HttpSessionConfig;
import com.my.binance.SprinBootAppConfiguration;
import com.my.binance.service.BinSymbolsService;
import com.my.binance.service.LanguageService;

@Controller
public class WebLinkControllerWebuser
{
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private BinSymbolsService binSymbolsService;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	private HttpSessionConfig httpSessionConfig;
	
	@RequestMapping(value=
	{"/web/binance"})
	public ModelAndView webbinancemain()
	{
		ModelAndView mav=new ModelAndView();
		mav.addObject("deflangimagepath",languageService.getLanguageImagePathByLocaleName(LocaleContextHolder.getLocale().getLanguage()));
		mav.setViewName("th_binance_main");
		return mav;
	}
	
	@RequestMapping(value=
	{"/web/binance/symbols"})
	public ModelAndView webBinanceSymbols()
	{
		ModelAndView mav=new ModelAndView();
		mav.addObject("deflangimagepath",languageService.getLanguageImagePathByLocaleName(LocaleContextHolder.getLocale().getLanguage()));
		mav.addObject("defsymbols",binSymbolsService.findAll());
		mav.setViewName("th_binance_symbols");
		return mav;
	}
	
	@RequestMapping(value=
	{"/web/binance/symbols/update/all"})
	public String webBinanceSymbolsUpdateAll()
	{
		binSymbolsService.updateAllFromJson();
		return "redirect:/web/binance/symbols";
	}
	
	@RequestMapping(value="/web/binance/symbols/update/favstatus",method=RequestMethod.GET)
	public String webBinanceSymbolsUpdateFavStatus(@RequestParam(name="symbolid") long symbolId)
	{
		binSymbolsService.changeFavStatus(symbolId);
		return "redirect:/web/binance/symbols";
	}
	
	@RequestMapping(value="/web/binance/symbols/update/symboldataget",method=RequestMethod.GET)
	public ModelAndView webBinanceSymbolsUpdateSymbolDataGet(HttpSession session,HttpServletRequest request)
	{
		session.setAttribute("web.binance.symbol.data."+session.getId(),request.getParameter("symbolid"));
		ModelAndView mav=new ModelAndView();
		mav.setViewName("redirect:/web/binance/symbols/update/symboldata");
		return mav;
	}
	
	@RequestMapping(value=
	{"/web/binance/symbols/update/symboldata"})
	public ModelAndView webBinanceSymbolsUpdateSymbolData(HttpSession session,HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ModelAndView mav=new ModelAndView();
		mav.addObject("deflangimagepath",languageService.getLanguageImagePathByLocaleName(LocaleContextHolder.getLocale().getLanguage()));
		if(session.getAttribute("web.binance.symbol.data."+session.getId())!=null)
		{
			System.out.println(session.getAttribute("web.binance.symbol.data."+session.getId()));
			session.removeAttribute("web.binance.symbol.data."+session.getId());
			mav.setViewName("th_binance_symbols_data");
		}
		else
		{
			throw new RuntimeException("Bu sekilde erisemezsiniz ...");
		}
		return mav;
	}
}
