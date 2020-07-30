// File:         User.java
// Created:      Feb 23, 2011
// Author:       sundarrajanr
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * The Data Transfer object for the User.
 * 
 * 
 */
public class User implements Serializable {

	/**
	 * Instantiates a new user.
	 */
	public User() {

	}

	/**
	 * Instantiates a new user.
	 * 
	 * @param userName
	 *            the user name
	 */
	public User(String userName) {
		this.userName = userName;
	}

	/** The serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The User Name. */
	private String userName;

	/** The password of the User. */
	private String password;

	/** The role. */
	private String role;

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

	/** The selected division. */
	private String selectedDivision;

	// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	/** The ApplicationPropertyBean */
	private ApplicationPropertyBean applicationPropertyBean;

	private String ldapUrl;
	
	private String ldapUrlSecond;

	// private String ldapPort;
	private String ldapSecurity;
	
	private String pabDC;

    private WelcomeMessage welcomeMessage;

	// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	/*
	 * added by Naveen to have division in LOgin page
	 */

	/**
	 * Gets the selected division.
	 * 
	 * @return the selected division
	 */
	public String getSelectedDivision() {
		return selectedDivision;
	}

	/**
	 * Sets the selected division.
	 * 
	 * @param selectedDivision
	 *            the new selected division
	 */
	public void setSelectedDivision(String selectedDivision) {
		this.selectedDivision = selectedDivision;
	}

	/** The divisions. */
	private ArrayList<String> divisions;

	/**
	 * Gets the divisions.
	 * 
	 * @return the divisions
	 */
	public ArrayList<String> getDivisions() {
		return divisions;
	}

	/**
	 * Sets the divisions.
	 * 
	 * @param divisions
	 *            the new divisions
	 */
	public void setDivisions(ArrayList<String> divisions) {
		this.divisions = divisions;
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
	 * Gets the display name.
	 * 
	 * @return the display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the display name.
	 * 
	 * @param displayName
	 *            the new display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	/**
	 * Gets the password.
	 * 
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the role.
	 * 
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 * 
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
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

	/**
	 * @return the ldapUrl
	 */
	public String getLdapUrl() {
		return ldapUrl;
	}

	/**
	 * @param ldapUrl
	 *            the ldapUrl to set
	 */
	public void setLdapUrl(String ldapUrl) {
		this.ldapUrl = ldapUrl;
	}

	/**
	 * @return the ldapSecurity
	 */
	public String getLdapSecurity() {
		return ldapSecurity;
	}

	/**
	 * @param ldapSecurity
	 *            the ldapSecurity to set
	 */
	public void setLdapSecurity(String ldapSecurity) {
		this.ldapSecurity = ldapSecurity;
	}
	// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011

	/**
	 * @return the pabDC
	 */
	public String getPabDC() {
		return pabDC;
	}

	/**
	 * @param pabDC the pabDC to set
	 */
	public void setPabDC(String pabDC) {
		this.pabDC = pabDC;
	}

	/**
	 * @return the ldapUrlSecond
	 */
	public String getLdapUrlSecond() {
		return ldapUrlSecond;
	}

	/**
	 * @param ldapUrlSecond the ldapUrlSecond to set
	 */
	public void setLdapUrlSecond(String ldapUrlSecond) {
		this.ldapUrlSecond = ldapUrlSecond;
	}

    public WelcomeMessage getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(WelcomeMessage welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }
}