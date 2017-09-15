package com.dtcc.controller;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class Home {
	
	@Autowired
	Properties appProp;	
	
	@Value("${jdbc.username}")
	private String jdbcUserName;
	
	@Value("${web.server}")
	private String webServer;
		

	public Home() {
		super();

		
	}

	@RequestMapping("/login.do")
	public String login(){		
		return "login";
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest req, HttpServletResponse res, Authentication auth){
		//Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		if (auth!=null){
			new SecurityContextLogoutHandler().logout(req, res, auth);
		}
		return "redirect:/home/login.do";
	}
	
	@RequestMapping("/welcome.do")
	public String welcome(){
		_log.info("Application Name: " + appProp.getProperty("app.login.header.title"));
		_log.info("Web Server: " + webServer);
		_log.info("jdbc.userName: " + jdbcUserName);

		return "welcome";
	}
	

	
	
	
	
	
	
	
	
	
	
	
	Logger _log=Logger.getLogger(getClass().getName());
}
