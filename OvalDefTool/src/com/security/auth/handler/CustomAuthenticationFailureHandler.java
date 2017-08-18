package com.security.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private RedirectStrategy redirectStrategy= new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException authExc)
			throws IOException, ServletException {
		
		_log.info("**************AUTHENTICATION FAILURE*******************");
		_log.info("Auth Exc Msg: " + authExc.getMessage());
		_log.info("*******************************************************");
		redirectStrategy.sendRedirect(req, res, "/login.do");
	}
	
	Logger _log=Logger.getLogger("Login Failure Handler");
}
