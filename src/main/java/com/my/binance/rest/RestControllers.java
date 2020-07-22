package com.my.binance.rest;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.my.binance.SprinBootAppConfiguration;
import com.my.binance.model.ClsMaster;
import com.my.binance.model.ClsOrder;
import com.my.binance.model.UserDetailsModel;
import com.my.binance.model.ExecResult;
import com.my.binance.model.User;
import com.my.binance.service.ClsService;
import com.my.binance.service.UserService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Order(1)
@RestController
@RequestMapping("/rest")
public class RestControllers
{
	Logger LOGGER=LoggerFactory.getLogger(SprinBootAppConfiguration.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ClsService clsService;
	
	@RequestMapping(method=RequestMethod.GET,value="/get/json/all",produces=MediaType.APPLICATION_JSON_VALUE) // http://localhost:8080/restadmin/user/get/json/all
	public ResponseEntity<List<UserDetailsModel>> getUsersJson()
	{
		List<UserDetailsModel> users=userService.findAll();
		users.stream().forEach(u->u.setPassword("*****"));
		return ResponseEntity.ok(users);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/get/json/all2",produces=MediaType.APPLICATION_JSON_VALUE) // http://localhost:8080/restadmin/user/get/json/all
	public ResponseEntity<List<User>> getUsersJson2()
	{
		List<UserDetailsModel> users=userService.findAll();
		List<User> uusers=new ArrayList<User>();
		users.stream().forEach(u->
		{
			User uu=new User();
			uu.firstName="";
			uu.lastName="";
			uu.token="";
			uu.lang="TR";
			uu.userName=u.getUsername();
			uusers.add(uu);
		});
		return ResponseEntity.ok(uusers);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/add/order",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExecResult> addOrder(@RequestBody String clsOrder) // http://localhost:8080/restadmin/user/create
	{
		try
		{
			LOGGER.info(clsOrder.toString().replace("\n"," ").replace("\r"," "));
			ExecResult er=clsService.addClsOrder(clsOrder);
			return ResponseEntity.ok(er);
		}
		catch(Exception ex)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/add/order2",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExecResult> addOrder2(@RequestBody ClsOrder abasOrder) // http://localhost:8080/restadmin/user/create
	{
		try
		{
			LOGGER.info(abasOrder.toString());
			ExecResult er=clsService.addClsOrder(abasOrder);
			return ResponseEntity.ok(er);
		}
		catch(Exception ex)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/add/order3",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExecResult> addOrder3(@RequestBody String clsOrder) // http://localhost:8080/restadmin/user/create
	{
		try
		{
			LOGGER.info(clsOrder.toString());
			ObjectMapper mapper=new ObjectMapper();
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
			mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,false);
			ClsOrder clsOrderr=null;
			try
			{
				clsOrderr=mapper.readValue(clsOrder,ClsOrder.class);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			ExecResult er=clsService.addClsOrder(clsOrderr);
			return ResponseEntity.ok(er);
		}
		catch(Exception ex)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST,value="/testclsmaster",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> testClsMaster(@RequestBody ClsMaster master) // http://localhost:8080/restadmin/user/create
	{
		try
		{
			LOGGER.info(master.toString());
			return ResponseEntity.ok(master.toString());
		}
		catch(Exception ex)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST,value="/testclsorder",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> testClsOrder(@RequestBody ClsOrder abasOrder) // http://localhost:8080/restadmin/user/create
	{
		try
		{
			LOGGER.info(abasOrder.toString());
			return ResponseEntity.ok(abasOrder.toString());
		}
		catch(Exception ex)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
