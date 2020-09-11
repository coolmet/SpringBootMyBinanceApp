package com.my.binance;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import com.my.binance.model.UserDetailsModel;
import com.my.binance.security.JWTAuthenticationFilter;
import com.my.binance.security.JWTAuthorizationFilter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled=true,jsr250Enabled=true)
@EnableConfigurationProperties(
{ConfigPropertiesApp.class,ConfigPropertiesSpring.class,ConfigPropertiesServer.class,ConfigPropertiesUser.class})
@EnableWebSecurity
public class SecurityConfiguration
{
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	ConfigPropertiesUser configPropertiesUser;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService).passwordEncoder(this.passwordEncoder());
		// User uu = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		for(UserDetailsModel user:configPropertiesUser.getUsers())
		{
			LOGGER.info("@@@ USERINFO: "+user.getUsername()+":"+user.getRolesAsString());
			auth.inMemoryAuthentication().withUser(user.getUsername()).password(this.passwordEncoder().encode(user.getPassword())).roles(user.getRoles());
		}
	}
	
	@Configuration
	@Order(1)
	class RestSecurity extends WebSecurityConfigurerAdapter
	{
		
		@Override
		protected void configure(HttpSecurity http) throws Exception
		{
			// JWTAuthenticationFilter>>this.setFilterProcessesUrl("/rest/login");
			http.csrf().disable();
			
			http
			    .antMatcher("/rest/**") // .antMatcher("/rest/**") .antMatcher("**/")
			    .authorizeRequests()
			    .antMatchers(HttpMethod.POST,"/rest/login").permitAll()
			    .antMatchers("/rest/login").anonymous()
			    .antMatchers("/rest/get/**").hasRole("RESTUSER") // admin api restricted to... ADMIN
			    .antMatchers("/rest/admin/**").hasRole("ADMIN") // admin api restricted to... ADMIN
			    .antMatchers("/rest/public/**").permitAll() // open api is... opened
			    .antMatchers(// @formatter:off
			                 "/rest", "/rest/*", "/rest/**"
			                 // @formatter:on
				)
			    .hasAnyRole("ADMIN","RESTUSER") // .access("hasRole('RESTUSER','ADMIN')") // .hasRole("RESTUSER")
			    .anyRequest().authenticated()
			    .and()
			    .addFilter(new JWTAuthenticationFilter(authenticationManager(),getApplicationContext()))
			    .addFilter(new JWTAuthorizationFilter(authenticationManager(),getApplicationContext()))
			    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			    .and().httpBasic();
				
		}
		
	}
	
	@Configuration
	@Order(2)
	class SecurityConfiguration_H2 extends WebSecurityConfigurerAdapter
	{
		@Override
		protected void configure(HttpSecurity http) throws Exception
		{
			// http.authorizeRequests().antMatchers("/admin/h2-console","/admin/h2-console/*","/admin/h2-console/**","/**/admin/h2-console/**").permitAll();
			
			http.antMatcher("/admin/h2-console/*")
			    .authorizeRequests()
			    .antMatchers("/admin/h2-console","/admin/h2-console/*","/admin/h2-console/**","/**/admin/h2-console/**")
			    .access("hasRole('ADMIN')")
			    .anyRequest()
			    .authenticated();
			
			http.csrf().disable();
			// http.headers().frameOptions().sameOrigin();
			http.headers().frameOptions().disable();
		}
	}
	
	@Configuration
	class THSecurity extends WebSecurityConfigurerAdapter
	{
		
		@Override
		protected void configure(HttpSecurity http) throws Exception
		{
			http
			    .antMatcher("/**") // configure the HttpSecurity to only be invoked when matching the provided ant pattern
			    .authorizeRequests() // configure restricting access
			    .antMatchers("/**/css/**",
			                 "/**/fonts/**",
			                 "/**/js/**",
			                 "/**/images/**",
			                 "/**/error/**",
			                 "/",
			                 "/index",
			                 "/login",
			                 "/loginweb",
			                 "/**/logout",
			                 "logout",
			                 "/logout")
			    .permitAll() // open api is... opened
			    .antMatchers(// @formatter:off
						  "/admin", "/admin/*", "/admin/**"
						  // @formatter:on
				)
			    .hasRole("ADMIN")
			    //
			    .antMatchers(// @formatter:off
			                 "/web",  "/web/*",  "/web/**"
			                 // @formatter:on
				)
			    .hasAnyRole("ADMIN","WEBUSER")
			    //
			    .antMatchers(// @formatter:off
						  "/rest",  "/rest/*",  "/rest/**"
						  // @formatter:on
				)
			    .hasAnyRole("ADMIN","RESTUSER")
			    .anyRequest().authenticated()
			    .and()
			    .formLogin()
			    .loginPage("/loginweb")// .loginPage("/login")
			    .loginProcessingUrl("/login")// .loginProcessingUrl("/login")
			    .defaultSuccessUrl("/default",true)// .defaultSuccessUrl("/default",true)
			    .failureUrl("/loginweb?loginFailed=true")// .failureUrl("/login?loginFailed=true")
			    .and()
			    .logout().invalidateHttpSession(true).clearAuthentication(true)
			    .deleteCookies("remember_me_cookie").logoutRequestMatcher(new AntPathRequestMatcher("/**/logout"))
			    .logoutSuccessUrl("/?logout")
			    .and()
			    .requestCache()
			    .and()
			    .exceptionHandling()
			    .accessDeniedPage("/403")
			    .and()
			    .csrf().disable();
				
			http.rememberMe().userDetailsService(userDetailsService).rememberMeServices(rememberMeServices());
			
			http.sessionManagement()
			    .maximumSessions(10)
			    .maxSessionsPreventsLogin(false)
			    .sessionRegistry(sessionRegistry())
			    .and()
			    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
			    .sessionFixation().migrateSession();
			
			http.headers().frameOptions().sameOrigin();
			
			//
			CharacterEncodingFilter filter=new CharacterEncodingFilter();
			filter.setEncoding("UTF-8");
			filter.setForceEncoding(true);
			http.addFilterBefore(filter,CsrfFilter.class);
			
			//
			// http.authorizeRequests().anyRequest().authenticated().and().formLogin();
			
		}
	}
	
	@Bean
	public RememberMeServices rememberMeServices() throws Exception
	{
		// Key must be equal to rememberMe().key()
		TokenBasedRememberMeServices rememberMeServices=new TokenBasedRememberMeServices("your_key",userDetailsService);
		rememberMeServices.setCookieName("remember_me_cookie");
		rememberMeServices.setParameter("remember_me_checkbox");
		rememberMeServices.setTokenValiditySeconds(2678400); // 1month
		return rememberMeServices;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Bean
	public SmartInitializingSingleton importProcessor()
	{
		return ()->
		{
			LOGGER.info("@@@ application initialization completed");
		};
	}
	
	@PostConstruct
	public void init()
	{
		LOGGER.info("@@@ SecurityConfiguration class init completed: "+sessionRegistry().getAllPrincipals().size());
	}
	
	@Bean
	public SessionRegistry sessionRegistry()
	{
		return new SessionRegistryImpl();
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher()
	{
		return new HttpSessionEventPublisher();
	}
	
}
