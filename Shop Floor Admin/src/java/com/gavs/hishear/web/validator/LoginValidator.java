// File:         LoginValidator.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.validator;

import java.util.Iterator;
import java.util.Set;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gavs.hishear.constant.Constant;
import com.gavs.hishear.web.domain.ApplicationPropertyBean;
import com.gavs.hishear.web.domain.LDAPAuthenticator;
import com.gavs.hishear.web.domain.User;

/**
 * The Class LoginValidator.
 * 
 */
public class LoginValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	/** The auth service. */
	private LDAPAuthenticator authService;

	// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	/** The application property bean. */
	private ApplicationPropertyBean applicationPropertyBean;

	// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	public void validate(Object userinfo, Errors errors) {
		User user = (User) userinfo;
		String userName = user.getUserName();
		String password = user.getPassword();
		String loggedInDivision = user.getDivision();
		String selectedDivision = user.getSelectedDivision();

		// user.setRole(role);
		new LDAPAuthenticator().getDisplayNameAndRole(user);
		// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys -
		// 23 June 2011
		boolean isValidUser = authService.authenticateLDAPUser(userName,
				password, user);
		// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
		// June 2011
		if (!isValidUser) {
			System.out.println("Invalid User...");
			errors.rejectValue("userName", "Invalid User Name / Password",
					"Invalid User Name / Password");
		} else if (!validateRole(user)) {

			System.out.println("Invalid Role...");
			errors.rejectValue("userName",
					"Access to this system is restricted. Contact IT",
					"Access to this system is restricted. Contact IT");
		} else if (!validateDivision(user, selectedDivision)) {
			System.out.println("Invalid Division...");
			errors.rejectValue("userName", "Invalid Division",
					"Invalid Division");
			// Begin WO# 27639 - Moving Static links to dynamic - Pinky -
			// Infosys - 23 June 2011
		} else if (applicationPropertyBean.isM3Connectionflag()) {
			System.out.println("Could not Communicate with M3");
			errors.rejectValue("userName", "Could not Communicate to M3",
					"Could not Communicate to M3");
			applicationPropertyBean.setM3Connectionflag(false);
			return;
		}
		// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
		// June 2011
	}

	/**
	 * Validate role.
	 * 
	 * @param user
	 *            the user
	 * @return true, if successful
	 */
	private boolean validateRole(User user) {
		// Get display name and roles
		new LDAPAuthenticator().getDisplayNameAndRole(user);

		Set roles = user.getRoles();
		// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys -
		// 23 June 2011
		// if
		// (roles.contains(user.getApplicationPropertyBean().getDistinctActiveDirectoryGroup()))
		// {
		// return true;
		// }
		if (user.getApplicationPropertyBean().getDistinctActiveDirectoryGroup() != null) {
			for (Iterator iterator = roles.iterator(); iterator.hasNext();) {
				String role = (String) iterator.next();
				if ((user.getApplicationPropertyBean()
						.getDistinctActiveDirectoryGroup()).contains(role)) {
					return true;
				}
			}
		}

		// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
		// June 2011

		// if (roles.contains(Role.MIS)
		// || roles.contains(Role.MDK_SHOPFLOORAPPROVERS)
		// || roles.contains(Role.MDK_SHOPFLOORMANAGERS)
		// || roles.contains(Role.MDK_SHOPFLOORREPORTUSERS)
		// || roles.contains(Role.HSC_SHOPFLOORAPPROVERS)
		// || roles.contains(Role.HSC_SHOPFLOORMANAGERS)
		// || roles.contains(Role.HSC_SHOPFLOORREPORTUSERS)
		// || roles.contains(Role.APPLICATION_SUPPORT)
		// || roles.contains(Role.HSC_ShopFloorQAManagers)
		// || roles.contains(Role.MDK_ShopFloorQAManagers)
		// || roles.contains(Role.LNA_ShopFloorQAManagers)
		// || roles.contains(Role.Infosys_Support)
		// || roles.contains(Role.LNA_SHOPFLOORAPPROVERS)
		// || roles.contains(Role.LNA_SHOPFLOORMANAGERS)
		// || roles.contains(Role.LNA_SHOPFLOORREPORTUSERS)) {
		// return true;
		// }
		return false;
	}

	/**
	 * Validate division.
	 * 
	 * @param user
	 *            the user
	 * @param selectedDivision
	 *            the selected division
	 * @return true, if successful
	 */
	private boolean validateDivision(User user, String selectedDivision) {
		if (!selectedDivision.equals(user.getDivision())) {
			if (selectedDivision.equals(Constant.LNA_DIVISION)
					&& user.getDivision().equals(Constant.HSC_DIVISION)) {
				user.setDivision(Constant.LNA_DIVISION);
			} else {
				user.setDivision(selectedDivision);
				return false;
			}
		}
		return true;
	}

	/**
	 * Gets the auth service.
	 * 
	 * @return the auth service
	 */
	public LDAPAuthenticator getAuthService() {
		return authService;
	}

	/**
	 * Sets the auth service.
	 * 
	 * @param authService
	 *            the new auth service
	 */
	public void setAuthService(LDAPAuthenticator authService) {
		this.authService = authService;
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