package com.gavs.hishear.web.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gavs.hishear.web.context.ThreadLocalUserContext;
import com.gavs.hishear.web.context.UserContext;

/**
 * 
 * @author sundarrajanr
 * 
 */

public class InvalidSessionHandlerInterceptor extends HandlerInterceptorAdapter {

	/** The name of the Session Attribute containing any User Context */
	private static final String USER_CONTEXT = "userContext";

	/** The Login Url to redirect to on an invalid session */
	private String loginUrl;// = DEFAULT_LOGIN_URL;

	/** The Logout url */
	private String logoutUrl;// = DEFAULT_LOGOUT_URL;

	/** The Web User Context instance to be set up */
	private UserContext userContext;

	/**
	 * Create a new instance of InvalidSessionHandlerInterceptor.
	 */
	public InvalidSessionHandlerInterceptor() {
		super();
	}

	/**
	 * Set the value of the loginUrl property.
	 * 
	 * @param loginUrl
	 *            the value to set the loginUrl property
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * Set the value of the logoutUrl property.
	 * 
	 * @param logoutUrl
	 *            the value to set the logoutUrl property
	 */
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	/**
	 * @param userContext
	 *            The userContext to set.
	 */
	public void setUserContext(
			com.gavs.hishear.web.context.UserContext userContext) {
		this.userContext = userContext;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		boolean isValidSession = isValidSession(request);
		if (!isValidSession) {
			response.sendRedirect(loginUrl);
		} else {
			if (!isEntryUrl(request.getRequestURI())) {
				com.gavs.hishear.web.context.UserContext sessionContext = (com.gavs.hishear.web.context.UserContext) request
						.getSession().getAttribute(USER_CONTEXT);

				if (userContext instanceof ThreadLocalUserContext) {
					ThreadLocalUserContext context = (ThreadLocalUserContext) userContext;
					context.setWebUserContext(sessionContext);
				}
			}
		}
		return isValidSession;
	}

	private boolean isValidSession(HttpServletRequest request) {
		boolean isValid = true;
		com.gavs.hishear.web.context.UserContext context = (com.gavs.hishear.web.context.UserContext) request
				.getSession().getAttribute(USER_CONTEXT);
		if (request.getSession().isNew() || context == null) {
			isValid = isEntryUrl(request.getRequestURI());
		}
		return isValid;
	}

	private boolean isEntryUrl(String url) {
		return url.equals(loginUrl) || url.equals(logoutUrl);
	}
}