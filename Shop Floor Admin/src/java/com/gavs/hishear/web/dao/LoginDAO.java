// File:         LoginDAO.java
// Created:      Feb 23, 2011
// Author:       sundarrajanr
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.gavs.hishear.web.domain.ApplicationPropertyBean;

/**
 * The Interface LoginDAO.
 * 
 */
public interface LoginDAO {

	/**
	 * Gets the queries.
	 * 
	 * @param code
	 *            the code
	 * @return the queries
	 */
	public HashMap getQueries(String code);

	/**
	 * Gets the report queries.
	 * 
	 * @param code
	 *            the code
	 * @return the report queries
	 */
	public HashMap getReportQueries(String code);

	/**
	 * Gets the divisions.
	 * 
	 * @param query
	 *            the query
	 * @return the divisions
	 */
	public ArrayList<String> getDivisions(String query);

	// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	/**
	 * @param applicationPropertyBean
	 * @return
	 * @throws Exception
	 */
	public ApplicationPropertyBean getActiveDirectoryGroup(
			ApplicationPropertyBean applicationPropertyBean) throws Exception;

	public ArrayList<String> getDistinctADGroups() throws Exception;
	// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
}