// File:         LDAPAuthenticator.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.domain;

import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * The Class LDAPAuthenticator.
 * 
 */
public class LDAPAuthenticator {

	private static final int ENVIRONMENT_SIZE = 11;

	/** The provider url. */
	private String providerURL;
	private String providerURLSecond;

	/** The security principle. */
	private String securityPrinciple;

	/** The member of. */
	private Set memberOf = new LinkedHashSet();

	// static Properties properties = new Properties();

	// Loads the properties file
	/*
	 * static { try { static InputStream is =
	 * ClassLoader.getResourceAsStream("system.properites"); properties.load(new
	 * FileInputStream("server.properties")); } catch (IOException e) {
	 * e.printStackTrace(); } }
	 */

	/**
	 * This method is used to authenticate the mobile device users. The user
	 * should be in the LDAP to access the address book. MiddlewareServlet calls
	 * this method to authenticate the login request.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @return true, if successful
	 */

	private Hashtable getAuthenticateLDAPUserInformation(String password,
			String username, User user, boolean isFirsLDAPServer) {
		Hashtable env = new Hashtable(ENVIRONMENT_SIZE);
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, isFirsLDAPServer ? user.getLdapUrl()
				: user.getLdapUrlSecond());
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, username + user.getLdapSecurity());
		env.put(Context.SECURITY_CREDENTIALS, password);
		return env;
	}

	public boolean authenticateLDAPUser(String username, String password,
			User user) {
		if ("".equals(username) || username == null || "".equals(password)
				|| password == null) {
			return false;
		}
		boolean isFirsLDAPServer = true;
		try {
			DirContext ctx = new InitialDirContext(
					getAuthenticateLDAPUserInformation(password, username,
							user, isFirsLDAPServer));
			ctx.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			isFirsLDAPServer = false;
		}

		try {
			DirContext ctx = new InitialDirContext(
					getAuthenticateLDAPUserInformation(password, username,
							user, isFirsLDAPServer));
			ctx.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	/**
	 * Gets the display name and role.
	 * 
	 * @param user
	 *            the user
	 * @return the display name and role
	 */

	private Hashtable getGetDisplayNameAndRoleInformation(User user,
			boolean isFirstLDAPServer) {
		Hashtable env = new Hashtable(ENVIRONMENT_SIZE);
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, isFirstLDAPServer ? user.getLdapUrl()
				: user.getLdapUrlSecond());
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL,
				user.getUserName() + user.getLdapSecurity());
		env.put(Context.SECURITY_CREDENTIALS, user.getPassword());
		return env;
	}

	public void getDisplayNameAndRole(User user) {
		boolean isFirstLDAPServer = true;
		try {
			DirContext ctx = new InitialDirContext(
					getGetDisplayNameAndRoleInformation(user, isFirstLDAPServer));
			setInfoToUser(ctx, user);

			ctx.close();
		} catch (Exception e) {
			System.out.println(e);
			isFirstLDAPServer = false;
		}

		if (!isFirstLDAPServer) {
			try {
				DirContext ctx = new InitialDirContext(
						getGetDisplayNameAndRoleInformation(user,
								isFirstLDAPServer));
				setInfoToUser(ctx, user);

				ctx.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void setInfoToUser(DirContext ctx, User user)
			throws NamingException {
		String filter = "(&(sAMAccountName=" + user.getUserName() + "))";

		SearchControls ctls = new SearchControls();
		ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		// Search for objects that have those matching attributes
		NamingEnumeration answer = ctx.search(user.getPabDC(), filter, ctls);
		while (answer.hasMore()) {
			SearchResult sr = (SearchResult) answer.next();
			user.setDisplayName(getDisplayName(sr.getAttributes()));
			user.setRoles(memberOf);
			user.setDivision(getUserDivision(sr.getAttributes()));
		}
	}

	/**
	 * Gets the display name.
	 * 
	 * @param attrs
	 *            the attrs
	 * @return the display name
	 */
	public String getDisplayName(Attributes attrs) {
		String displayName = null;
		if (attrs == null) {
			System.out.println("No attributes");
		} else {
			/* Print each attribute */
			try {
				for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
					Attribute attr = (Attribute) ae.next();

					if ("displayName".equals(attr.getID())) {
						NamingEnumeration e = attr.getAll();
						if (e.hasMore()) {
							displayName = (String) e.next();
						}
					}
					if ("memberOf".equals(attr.getID())) {
						NamingEnumeration e = attr.getAll();
						while (e.hasMore()) {
							String temp = e.next().toString();
							System.out.println("temp---" + temp);
							String group = temp.substring(3, temp.indexOf(","));
							memberOf.add(group);
						}
					}
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}

		}
		return displayName;
	}

	/**
	 * Gets the user division.
	 * 
	 * @param attrs
	 *            the attrs
	 * @return the user division
	 */
	private String getUserDivision(Attributes attrs) {
		String division = "";
		if (attrs != null) {
			try {
				for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
					Attribute attr = (Attribute) ae.next();
					if ("company".equals(attr.getID())) {
						NamingEnumeration e = attr.getAll();
						if (e.hasMore()) {
							String company = (String) e.next();
							if (company != null) {
								division = company;
							}
						}
					}
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return division;
	}

	/**
	 * Gets the provider url.
	 * 
	 * @return the provider url
	 */
	// public static String getProviderURL() {
	// // String ldapURL = properties.getProperty("ldap.providerURL");
	// // System.out.println("Authenticated Against ADS:"+ldapURL);
	// return "ldap://hsc1:389/";// ldapURL;
	// }
	/**
	 * Gets the security principle.
	 * 
	 * @return the security principle
	 */
	// public static String getSecurityPrinciple() {
	// return "@HISHEAR";// properties.getProperty("ldap.security.principle");
	// }
	// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	public String getProviderURL() {
		return providerURL;
	}

	public String getSecurityPrinciple() {
		return securityPrinciple;
	}

	// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	/**
	 * Sets the provider url.
	 * 
	 * @param providerURL
	 *            the new provider url
	 */
	public void setProviderURL(String providerURL) {
		this.providerURL = providerURL;
	}

	/**
	 * Sets the security principle.
	 * 
	 * @param securityPrinciple
	 *            the new security principle
	 */
	public void setSecurityPrinciple(String securityPrinciple) {
		this.securityPrinciple = securityPrinciple;
	}

	/**
	 * @return the providerURLSecond
	 */
	public String getProviderURLSecond() {
		return providerURLSecond;
	}

	/**
	 * @param providerURLSecond
	 *            the providerURLSecond to set
	 */
	public void setProviderURLSecond(String providerURLSecond) {
		this.providerURLSecond = providerURLSecond;
	}

}