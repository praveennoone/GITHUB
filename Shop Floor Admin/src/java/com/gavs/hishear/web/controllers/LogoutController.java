package com.gavs.hishear.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContextException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import com.gavs.hishear.configurations.Environment;
import com.gavs.hishear.web.context.UserContext;

/**
 * 
 * @author sundarrajanr
 * 
 */
public class LogoutController extends AbstractController {
	private static final String USER_CONTEXT = "userContext";

	private String targetUrl;

	/** The Web User Context to invalidate */
	private UserContext userContext;

	private String ENVIRONMENT = "environment";
	private Environment environment;

	/**
	 * @return the environment
	 */
	public Environment getEnvironment() {
		return environment;
	}

	/**
	 * @param environment
	 *            the environment to set
	 */
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	/**
	 * Set the redirect target url.
	 * 
	 * @param viewName
	 */
	public void setTargetUrl(String viewName) {
		this.targetUrl = viewName;
	}

	/**
	 * @param userContext
	 *            The userContext to set.
	 */
	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}

	/**
	 * Ensure that the required bean properties have been set.
	 * 
	 * @throws Exception
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		if (this.targetUrl == null || this.targetUrl.length() == 0) {
			throw new ApplicationContextException("Target URL must be set for "
					+ getClass().getName());
		}
	}

	/**
	 * Forward control to the tiles view for the target screen, ensuring that
	 * any request parameter context information is persisted for frame sub-
	 * Controllers.
	 * 
	 * @param request
	 *            the HTTP request being processed
	 * @param response
	 *            the HTTP response being created
	 * @return ModelAndView the target ModelAndView to be rendered
	 * @throws java.lang.Exception
	 *             in the event of any errors
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		environment.setDivision(null);
		request.getSession().invalidate();

		// Do any required request processing
		doRequestProcessing(request);
		// Return the target ModelAndView
		return new ModelAndView(new RedirectView(this.targetUrl));
	}

	/**
	 * Perform any processing which is required on a request.
	 * 
	 * @param request
	 *            the HttpServletRequest being processed
	 */
	protected void doRequestProcessing(HttpServletRequest request) {
		userContext.invalidate();

		request.getSession().removeAttribute(USER_CONTEXT);
		request.getSession().invalidate();
	}
}