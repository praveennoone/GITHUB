// File:         WebUserContext.java
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
import java.util.Set;

import com.gavs.hishear.web.domain.ApplicationPropertyBean;

/**
 * The Class WebUserContext.
 * 
 */

public class WebUserContext implements UserContext {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user name. */
	private String userName;

	/** The user type. */
	private String userType;

	/** The display name. */
	private String displayName;

	/** The queries. */
	private HashMap queries;

	/** The report queries. */
	private HashMap reportQueries;

	/** The roles. */
	private Set roles;

	/** The division. */
	private String division;

	// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	/** The ApplicationPropertyBean */
	private ApplicationPropertyBean applicationPropertyBean;

	// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	/**
	 * Instantiates a new web user context.
	 * 
	 * @param userName
	 *            the user name
	 */
	public WebUserContext(String userName) {
		this.userName = userName;
	}

	/**
	 * Instantiates a new web user context.
	 * 
	 * @param userName
	 *            the user name
	 * @param userType
	 *            the user type
	 * @param displayName
	 *            the display name
	 * @param queries
	 *            the queries
	 * @param reportQuries
	 *            the report quries
	 * @param roles
	 *            the roles
	 * @param division
	 *            the division
	 */
	public WebUserContext(String userName, String userType, String displayName,
			HashMap queries, HashMap reportQuries, Set roles, String division,
			ApplicationPropertyBean applicationPropertyBean) {
		this.userName = userName;
		this.userType = userType;
		this.displayName = displayName;
		this.queries = queries;
		this.reportQueries = reportQuries;
		this.roles = roles;
		this.division = division;
		// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys -
		// 23 June 2011
		this.applicationPropertyBean = applicationPropertyBean;
		// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
		// June 2011
		System.out.println(" UserName ==========> " + userName
				+ " DisplayName ==========> " + displayName
				+ " Roles ========> " + roles + " Division =========> "
				+ division);
	}

	/**
	 * Instantiates a new web user context.
	 */
	public WebUserContext() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the user name.
	 * 
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 * 
	 * @param userName
	 *            The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gavs.hishear.web.context.UserContext#invalidate()
	 */
	public void invalidate() {
		// TODO Auto-generated method stub
	}

	/**
	 * Gets the user type.
	 * 
	 * @return Returns the userType.
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * Sets the user type.
	 * 
	 * @param userType
	 *            The userType to set.
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * Gets the display name.
	 * 
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the display name.
	 * 
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Gets the queries.
	 * 
	 * @return the queries
	 */
	public HashMap getQueries() {
		return queries;
	}

	/**
	 * Sets the queries.
	 * 
	 * @param queries
	 *            the queries to set
	 */
	public void setQueries(HashMap queries) {
		this.queries = queries;
	}

	/**
	 * Gets the report queries.
	 * 
	 * @return the reportQueries
	 */
	public HashMap getReportQueries() {
		return reportQueries;
	}

	/**
	 * Sets the report queries.
	 * 
	 * @param reportQueries
	 *            the reportQueries to set
	 */
	public void setReportQueries(HashMap reportQueries) {
		this.reportQueries = reportQueries;
	}

	/**
	 * Gets the roles.
	 * 
	 * @return the roles
	 */
	public Set getRoles() {
		return roles;
	}

	/**
	 * Sets the roles.
	 * 
	 * @param roles
	 *            the new roles
	 */
	public void setRoles(Set roles) {
		this.roles = roles;
	}

	/**
	 * Gets the division.
	 * 
	 * @return the division
	 */
	public String getDivision() {
		return division;
	}

	/**
	 * Sets the division.
	 * 
	 * @param division
	 *            the division to set
	 */
	public void setDivision(String division) {
		this.division = division;
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
	// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
}