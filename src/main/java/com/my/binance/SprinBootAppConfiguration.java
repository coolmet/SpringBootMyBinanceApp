package com.my.binance;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import com.my.binance.model.UserDetailsModel;
import com.my.binance.ConfigPropertiesSpring;

@Configuration
public class SprinBootAppConfiguration
{
	@Autowired
	private ConfigPropertiesApp configApp;
	
	@Autowired
	private ConfigPropertiesSpring configSpring;
	
	@Autowired
	private ConfigPropertiesServer configServer;
	
	@Autowired
	private ConfigPropertiesUser configPropertiesUser;
	
	@Autowired
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	@PostConstruct
	public void init()
	{
		requestMappingHandlerAdapter.setIgnoreDefaultModelOnRedirect(true);
		System.out.println(AnsiOutput.toString(AnsiColor.BRIGHT_BLUE,"________________________________________\n",AnsiColor.DEFAULT));
		System.out.println("\u001b[40m \u001B[38;5;220m @@@@@: SprinBootAppConfiguration->init()-> "+AnsiOutput.toString(AnsiColor.DEFAULT));
		//
		LOGGER.debug("@@@ abas.edp.password\t\t= "+configApp.getEdp().getPassword());
		for(UserDetailsModel user:configPropertiesUser.getUsers())
		{
			LOGGER.debug("@@@ abas.users\t\t= "+user.getUsername()+":"+user.getPassword()+":"+Arrays.toString(user.getRoles()));
		}
		//
		LOGGER.info("@@@ abas.edp.password\t\t= "+"*****");
		LOGGER.info("@@@ abas.edp.port\t\t\t= "+configApp.getEdp().getPort());
		LOGGER.info("@@@ abas.edp.sessiontimeout\t\t\t= "+configApp.getEdp().getSessiontimeout());
		LOGGER.info("@@@ abas.edp.serverip\t\t= "+configApp.getEdp().getServerip());
		LOGGER.info("@@@ abas.edp.fopmode\t\t\t= "+configApp.getEdp().isFopmode());
		LOGGER.info("@@@ abas.edp.fl\t\t\t= "+configApp.getEdp().isFl());
		LOGGER.info("@@@ abas.s3.dir\t\t\t= "+configApp.getS3().getDir());
		LOGGER.info("@@@ abas.s3.basedir\t\t\t= "+configApp.getS3().getBaseDir());
		LOGGER.info("@@@ abas.s3.mandant\t\t\t= "+configApp.getS3().getMandant());
		//
		LOGGER.info("@@@ spring.mvc.locale\t\t= "+configSpring.getMvc().getLocale());
		LOGGER.info("@@@ server.port\t\t\t= "+configServer.getPort());
		LOGGER.info("@@@ server.connection-timeout\t= "+configServer.getConnectionTimeout());
		LOGGER.info("@@@ server.token.expirationtime\t= "+configServer.getToken().getExpirationTime());
		LOGGER.info("@@@ server.token.headerstring\t= "+configServer.getToken().getHeaderString());
		LOGGER.info("@@@ server.token.secret\t= "+configServer.getToken().getSecret());
		LOGGER.info("@@@ server.token.prefix\t= "+configServer.getToken().getPrefix());
		LOGGER.info("@@@ server.servlet.session.timeout\t= "+configServer.getServlet().getSession().getTimeout());
		//
		System.out.println("\u001b[40m \u001B[38;5;220m @@@@@: __________________________________ ");
		System.out.println(AnsiOutput.toString(AnsiColor.DEFAULT,AnsiColor.BRIGHT_BLUE,"________________________________________",AnsiColor.DEFAULT));
	}
}
