package com.my.binance.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
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
	{"/web/binance/symbols/updateall"})
	public String webBinanceSymbolsUpdateAll()
	{
		binSymbolsService.updateAllFromJson();
		return "redirect:/web/binance/symbols";
	}
	
	@RequestMapping(value="/web/binance/symbols/update",method=RequestMethod.GET)
	public String handleDeleteUser(@RequestParam(name="symbolid") String symbolId)
	{
		System.out.println(symbolId);
		System.out.println("test");
		return "redirect:/web/binance/symbols";
	}
}
