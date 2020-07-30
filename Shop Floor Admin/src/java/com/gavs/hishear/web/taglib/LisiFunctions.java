// File:         LisiFunctions.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.taglib;

import java.util.Iterator;

/**
 * The Class LisiFunctions.
 */
public final class LisiFunctions {

	// Utility classes should not have a public or default constructor.
	private LisiFunctions() {
		super();
	}

	/**
	 * Contains.
	 * 
	 * @param set
	 *            the set
	 * @param target
	 *            the target
	 * @return true, if successful
	 */
	// public static boolean contains(java.util.Set set, String target) {
	// return set.contains(target) || set.contains("\\\\" + target);
	// }
	// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	public static boolean contains(
			com.gavs.hishear.web.context.UserContext userContext,
			java.util.Set resultSet, java.lang.String htm,
			java.lang.Boolean htmFlag) {
		System.out.println("TARGET---" + resultSet);
		if (userContext == null || resultSet == null) {
			System.out.println("Roles == NULL");
			return false;
		}

		// Check if user role matches with the roles that has access to Menu
		// Item and set the flag
		String userRole = null;
		boolean exitFlag = false;
		// if htmFlag is true, the parameter sent is HTML else it is a MENU name
		System.out.println("Gonna Process " + htm);
		if (htmFlag) {
			for (Iterator iterator = resultSet.iterator(); iterator.hasNext();) {
				userRole = (String) iterator.next();
				if (userContext.getApplicationPropertyBean()
						.getActiveDirectoryMap().get(htm).contains(userRole)) {
					exitFlag = true;
					break;
				}

			}
		} else {
			for (Iterator iterator = resultSet.iterator(); iterator.hasNext();) {
				userRole = (String) iterator.next();
				if (userContext.getApplicationPropertyBean()
						.getActiveMenuDirectoryMap().get(htm)
						.contains(userRole)) {
					exitFlag = true;
					break;
				}

			}
		}
		return exitFlag;
	}
	// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
}