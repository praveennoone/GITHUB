// File:         UserContext.java
// Created:      Feb 23, 2011
// Author:       sundarrajanr
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.context;

/**
 * 
 *
 */
import java.io.Serializable;
import java.util.HashMap;

import com.gavs.hishear.web.domain.ApplicationPropertyBean;

/**
 * The Interface UserContext.
 */
public interface UserContext extends Serializable {

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUserName();

	/**
	 * Gets the user type.
	 * 
	 * @return the user type
	 */
	public String getUserType();

	/**
	 * Invalidate.
	 */
	public void invalidate();

	/**
	 * Gets the display name.
	 * 
	 * @return the display name
	 */
	public String getDisplayName();

	/**
	 * Gets the queries.
	 * 
	 * @return the queries
	 */
	public HashMap getQueries();

	/**
	 * Gets the report queries.
	 * 
	 * @return the report queries
	 */
	public HashMap getReportQueries();

	/**
	 * Gets the division.
	 * 
	 * @return the division
	 */
	public String getDivision();

	// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	/**
	 * @return
	 */
	public ApplicationPropertyBean getApplicationPropertyBean();
	// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
}