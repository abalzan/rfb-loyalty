package br.com.andrei.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class RfbAjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {

		String errorMessage = "Failed to sign in! Please check your credentials and try again!";
		
		if(exception.getMessage().equals("Access Blocked")) {
			errorMessage = "You have been blocked for our system for 3 invalid logging attempts!!";
			
			response.sendError(401, errorMessage);
		}
		
	}
	
}
