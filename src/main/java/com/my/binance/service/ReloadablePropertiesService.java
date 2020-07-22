package com.my.binance.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.StreamSupport;
import javax.annotation.PostConstruct;
import org.assertj.core.error.ShouldBeAbsolutePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import com.my.binance.SprinBootAppConfiguration;
import com.my.binance.SpringBootAppStarter;

public abstract class ReloadablePropertiesService
{
	
	@Autowired
	protected StandardEnvironment environment;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private RestartEndpoint restartEndpoint;
	
	@Autowired
	private RefreshEndpoint refreshEndpoint;
	
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	private long lastModTime=0L;
	private PropertySource<?> appConfigPropertySource=null;
	private Path configPath;
	private boolean isProdMode=false;
	
	@PostConstruct
	private void stopIfProblemsCreatingContext()
	{
		LOGGER.debug("@@@ Releadeble properties is starting");
		System.out.println("###starting###");
		MutablePropertySources propertySources=environment.getPropertySources();
		StreamSupport.stream(propertySources.spliterator(),false).forEach(ff->
		{
			LOGGER.debug("@@@ "+ff.getName()+":"+ff.getName().contains("appconfig.properties"));
		});
		Optional<PropertySource<?>> appConfigPsOp=
		StreamSupport.stream(propertySources.spliterator(),false)
		             .filter(ps->ps.getName().contains("appconfig.properties"))
		             .findFirst();
		if(!appConfigPsOp.isPresent())
		{
			// this will stop context initialization
			// (i.e. kill the spring boot program before it initializes)
			throw new RuntimeException("Unable to find property Source as file");
		}
		appConfigPropertySource=appConfigPsOp.get();
		try
		{
			isProdMode=Arrays.stream(environment.getActiveProfiles()).anyMatch(env->(env.equalsIgnoreCase("prod")));
			configPath=Arrays.stream(environment.getActiveProfiles()).anyMatch(env->(env.equalsIgnoreCase("prod")))
			?Paths.get("./config/appconfig.properties")// jar
			:Paths.get(new File("./src/main/resources/config/appconfig.properties").getPath());// springtools
			
			// LOGGER.info("@@>>>>>>"+resourceLoader.getResource("classpath:./config/appconfig.properties").getFile().getAbsolutePath());
			// LOGGER.info("@@>>>>>>"+resourceLoader.getResource("./config/appconfig.properties").getFile().getAbsolutePath());
			// LOGGER.info("@@>>>>>>"+resourceLoader.getResource("file:./config/appconfig.properties").getFile().getAbsolutePath());
			// LOGGER.info("@@>>>>>>"+getClass().getClassLoader().getResource("./config/appconfig.properties").getFile().toString());
			// LOGGER.info("@@>>>>>>"+new File("./config/appconfig.properties").getAbsolutePath());
			// LOGGER.info("@@>>>>>>"+new File("./src/main/resources/config/appconfig.properties").getAbsolutePath());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Scheduled(fixedRate=5000)
	private void reload() throws IOException
	{
		LOGGER.debug("@@@ checking appconfig.properties");
		// Path configPath=Paths.get("./src/main/resources/config/appconfig.properties");
		// Path configPath=Paths.get(new ClassPathResource("./config/appconfig.properties").getPath());
		long currentModTs=Files.getLastModifiedTime(configPath).toMillis();
		if(currentModTs>lastModTime)
		{
			
			LOGGER.debug("@@@ reloading appconfig.properties \t>"+lastModTime+":"+currentModTs);
			boolean isFirstTime=lastModTime==0;
			lastModTime=currentModTs;
			Properties properties=new Properties();
			InputStream inputStream=Files.newInputStream(configPath);
			properties.load(inputStream);
			
			environment.getPropertySources()
			           .replace(
			                    appConfigPropertySource.getName(),
			                    new PropertiesPropertySource(
			                                                 appConfigPropertySource.getName(),
			                                                 properties));
			LOGGER.debug("@@@ reloaded appconfig.properties");
			propertiesReloaded();
			
			if(isProdMode&&!isFirstTime)
			{
				// SpringBootAppStarter.refresh();
				// SpringBootAppStarter.restart();
				// restartEndpoint.restart();
				// refreshEndpoint.refresh();
				Thread restartThread=new Thread(()->restartEndpoint.restart());
				restartThread.setDaemon(false);
				restartThread.start();
			}
		}
	}
	
	protected abstract void propertiesReloaded();
	
}
