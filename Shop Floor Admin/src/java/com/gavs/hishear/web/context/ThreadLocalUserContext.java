// File:         ThreadLocalUserContext.java
// Created:      Feb 23, 2011
// Author:       sundarrajanr
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.context;

import java.util.HashMap;

import com.gavs.hishear.web.domain.ApplicationPropertyBean;

/**
 * The Class ThreadLocalUserContext.
 * 
 */

public class ThreadLocalUserContext implements UserContext {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant WEB_USER_CONTEXT. */
	private static final ThreadLocal WEB_USER_CONTEXT = new ThreadLocal();

	/**
	 * Instantiates a new thread local user context.
	 */
	public ThreadLocalUserContext() {
		WebUserContext context = new WebUserContext();
		WEB_USER_CONTEXT.set(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.context.UserContext#getUserName()
	 */
	public String getUserName() {

		return ((WebUserContext) WEB_USER_CONTEXT.get()).getUserName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.context.UserContext#getUserType()
	 */
	public String getUserType() {

		return ((WebUserContext) WEB_USER_CONTEXT.get()).getUserType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.context.UserContext#invalidate()
	 */
	public void invalidate() {
		WEB_USER_CONTEXT.set(null);
	}

	/**
	 * Sets the web user context.
	 * 
	 * @param sessionContext
	 *            the new web user context
	 */
	public void setWebUserContext(UserContext sessionContext) {
		WEB_USER_CONTEXT.set(sessionContext);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.context.UserContext#getDisplayName()
	 */
	public String getDisplayName() {
		return ((WebUserContext) WEB_USER_CONTEXT.get()).getDisplayName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.context.UserContext#getQueries()
	 */
	public HashMap getQueries() {
		return ((WebUserContext) WEB_USER_CONTEXT.get()).getQueries();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.context.UserContext#getReportQueries()
	 */
	public HashMap getReportQueries() {
		return ((WebUserContext) WEB_USER_CONTEXT.get()).getReportQueries();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.context.UserContext#getDivision()
	 */
	@Override
	public String getDivision() {
		return ((WebUserContext) WEB_USER_CONTEXT.get()).getDivision();
	}

	// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	@Override
	public ApplicationPropertyBean getApplicationPropertyBean() {

		return ((WebUserContext) WEB_USER_CONTEXT.get())
				.getApplicationPropertyBean();
	}
	// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
}