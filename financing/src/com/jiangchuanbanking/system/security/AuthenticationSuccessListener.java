package com.jiangchuanbanking.system.security;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

/**
 * Authentication success listener
 */
public class AuthenticationSuccessListener implements
		HttpSessionAttributeListener {
	public final static String LOGIN_USER = "loginUser";

	public void attributeAdded(HttpSessionBindingEvent se) {
		String sessionName = se.getName();
		if (sessionName.equals("SPRING_SECURITY_CONTEXT")) {
			SecurityContext securityContext = (SecurityContext) se.getSession()
					.getAttribute("SPRING_SECURITY_CONTEXT");
			Authentication authentication = securityContext.getAuthentication();
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication
					.getPrincipal();
			se.getSession().setAttribute(LOGIN_USER, userDetails.getUser());
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent se) {

	}

	public void attributeReplaced(HttpSessionBindingEvent se) {
		String sessionName = se.getName();
		if (sessionName.equals("SPRING_SECURITY_CONTEXT")) {
			SecurityContext securityContext = (SecurityContext) se.getSession()
					.getAttribute("SPRING_SECURITY_CONTEXT");
			Authentication authentication = securityContext.getAuthentication();
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication
					.getPrincipal();
			se.getSession().setAttribute(LOGIN_USER, userDetails.getUser());
		}
	}

}
