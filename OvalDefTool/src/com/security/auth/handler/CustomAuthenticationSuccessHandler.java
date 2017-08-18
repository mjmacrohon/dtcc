package com.security.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy= new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
			throws IOException, ServletException {		
		_log.info("**************AUTHENTICATION*******************");
		_log.info("Name: " + auth.getName());
		_log.info("Details: " + auth.getDetails());//WebAuthenticationDetails
		_log.info("Principal: " + auth.getPrincipal());
		_log.info("Credentials: " + auth.getCredentials());
		for(GrantedAuthority ga: auth.getAuthorities()){
			_log.info("Granted Authority: " + ga.getAuthority());
		}
		
		//redirectStrategy.sendRedirect(req, res, "/home/welcome.do");
		redirectStrategy.sendRedirect(req, res, "/cve/generator.do");
		_log.info("***********************************************");		
		
	}

	Logger _log=Logger.getLogger("Login Success Handler");
}
