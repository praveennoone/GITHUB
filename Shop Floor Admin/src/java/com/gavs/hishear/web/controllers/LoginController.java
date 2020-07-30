// File:         LoginController.java
// Created:      Feb 23, 2011
// Author:       sundarrajanr
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.gavs.hishear.EventLog.LogTools;
import com.gavs.hishear.configurations.Environment;
import com.gavs.hishear.web.context.WebUserContext;
import com.gavs.hishear.web.dao.LoginDAO;
import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.domain.ApplicationPropertyBean;
import com.gavs.hishear.web.domain.User;

/**
 * The Class LoginController.
 * 
 */
public class LoginController extends SimpleFormController {

	
	private static final Logger log=Logger.getLogger(LoginController.class);
	/** User Context to use. */
	private static final String USER_CONTEXT = "userContext";

	/** The dao. */
	private LoginDAO dao;

	/** The environment. */
	private Environment environment;

	/** The ENVIRONMENT. */
	private String ENVIRONMENT = "environment";

	// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	/** The application property bean. */
	private ApplicationPropertyBean applicationPropertyBean;

	/** The sequence dao. */
	private SequenceDAO sequenceDAO;

	// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011

	/**
	 * Instantiates a new login controller.
	 */
	public LoginController() {
		setCommandName("command");
	}

	/**
	 * Implements the Form Submit actions.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param command
	 *            form object with request parameters bound onto it
	 * @param errors
	 *            Errors instance without errors
	 * @return the prepared model and view, or null
	 * @throws Exception
	 *             in case of errors
	 * @see #onSubmit(HttpServletRequest, HttpServletResponse, Object,
	 *      BindException)
	 * @see #onSubmit(Object)
	 * @see #setSuccessView
	 * @see org.springframework.validation.Errors
	 * @see org.springframework.validation.BindException#getModel
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		log.info("model and view");
		LogTools.windowsEventLogInfo("welocme : ");
		LogTools.windowsEventLogInfo(": ");
		User user = (User) command;
		log.info("after user");
		environment.setDivision(user.getDivision());
		user.setQueries(dao.getQueries("SHOP"));
		user.setReportQueries(dao.getReportQueries("SHOPR"));
		WebUserContext userContext = createUserContext(user);
		request.getSession().setAttribute(USER_CONTEXT, userContext);

		Set roles = user.getRoles();
		for (Iterator iter = roles.iterator(); iter.hasNext();) {
			String role = (String) iter.next();

		}

		return new ModelAndView(getSuccessView(), "command", command);
	}

	/**
	 * Form backing object.
	 * 
	 * @param request
	 *            the request
	 * @return {@link Object}
	 * @throws Exception
	 *             the exception
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		request.getSession().setAttribute(ENVIRONMENT, environment);
        String userName = null;
        String dynamicMessagePath = applicationPropertyBean.getDynamicMessagePath();
        BufferedReader reader = new BufferedReader(new FileReader(dynamicMessagePath));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null) 
        {
            sb.append((new StringBuilder()).append(line).append("\n").toString());
            request.setAttribute("dynamicMessage", sb.toString());
        }
		String remoteUser = request.getRemoteUser();
		if (remoteUser != null) {
			userName = remoteUser.substring(remoteUser.indexOf("\\") + 1,
					remoteUser.length());
		}
		User user = new User();
		user.setUserName(userName);
		user.setQueries(dao.getQueries(null));
		user.setDivisions(dao.getDivisions((String) user.getQueries().get(
				"M3ORAL_015")));
		
		// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys -
		// 23 June 2011
		applicationPropertyBean = dao
				.getActiveDirectoryGroup(applicationPropertyBean);

		applicationPropertyBean.setDistinctActiveDirectoryGroup(dao
				.getDistinctADGroups());

		user.setApplicationPropertyBean(applicationPropertyBean);

		user.setLdapUrl(applicationPropertyBean.getLdapUrl());
		
		user.setLdapUrlSecond(applicationPropertyBean.getLdapUrlSecond());

		user.setLdapSecurity(applicationPropertyBean.getLdapSecurity());
		
		user.setPabDC(applicationPropertyBean.getPabDC());

        user.setWelcomeMessage(applicationPropertyBean.getWelcomeMessage());

		try {
			sequenceDAO.getFacilities(user.getDivision());
		} catch (Exception e) {
			e.printStackTrace();
			String strError = e.getMessage();
			if ("Could NOT communicate to M3".equals(strError)) {
				applicationPropertyBean.setM3Connectionflag(true);
			}
			// throw new Exception(e.getMessage());
		}

		// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
		// June 2011
		return user;
	}

	/**
	 * Creates the user context.
	 * 
	 * @param user
	 *            the user
	 * @return {@link WebUserContext}
	 */
	private WebUserContext createUserContext(User user) {
		WebUserContext userContext = null;
		if (user == null) {
			return userContext;
		}
		String userName = user.getUserName();
		if (userName != null) {
			userContext = new WebUserContext(user.getUserName(),
					user.getRole(), user.getDisplayName(), user.getQueries(),
					user.getReportQueries(), user.getRoles(), user
							.getDivision(), user.getApplicationPropertyBean());
		}
		return userContext;
	}

	/**
	 * Gets the dao.
	 * 
	 * @return the dao
	 */
	public LoginDAO getDao() {
		return dao;
	}

	/**
	 * Sets the dao.
	 * 
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(LoginDAO dao) {
		this.dao = dao;
	}

	/**
	 * Gets the environment.
	 * 
	 * @return the environment
	 */
	public Environment getEnvironment() {
		return environment;
	}

	/**
	 * Sets the environment.
	 * 
	 * @param environment
	 *            the new environment
	 */
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	/**
	 * @return the applicationPropertyBean
	 */
	public ApplicationPropertyBean getApplicationPropertyBean() {
		return applicationPropertyBean;
	}

	/**
	 * @param applicationPropertyBean
	 *            the applicationPropertyBean to set
	 */
	public void setApplicationPropertyBean(
			ApplicationPropertyBean applicationPropertyBean) {
		this.applicationPropertyBean = applicationPropertyBean;
	}

	/**
	 * @return the sequenceDAO
	 */
	public SequenceDAO getSequenceDAO() {
		return sequenceDAO;
	}

	/**
	 * @param sequenceDAO
	 *            the sequenceDAO to set
	 */
	public void setSequenceDAO(SequenceDAO sequenceDAO) {
		this.sequenceDAO = sequenceDAO;
	}
	// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
}