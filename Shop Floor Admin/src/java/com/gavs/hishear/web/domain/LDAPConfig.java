package com.gavs.hishear.web.domain;

/**
 * This DataObject holds the configuration details of the LDAP
 * 
 * @author sundarrajanr
 * 
 */
public class LDAPConfig {
	/**
	 * This variable is used to hold the ldap url. example:
	 * ldap://localhost:389/
	 */
	private String ldapURL = "ldap://hsc1:389/";

	/**
	 * Distinguished Name of the PAB(Public Address Book)
	 */
	private String pabDN = "ou=People,o=gavsin.com,dc=gavsin,dc=com";

	/**
	 * This user should have access to browse the PAB
	 */
	private String adminUserDN = "cn=directory manager";

	/**
	 * This variable is used to hold the password of the above mentioned admin
	 * user
	 */
	private String adminPassword;

	/**
	 * This variable is used to hold the id from DB
	 */
	private String id;

	/**
	 * This variable is used to hold the timeout limit for search
	 */
	private int timeout;

	/**
	 * This variable is used to set the maximum number of rows that can be
	 * returned for any search
	 */
	private int maxNoOfRows;

	private String port;

	private String secure;

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the secure
	 */
	public String getSecure() {
		return secure;
	}

	/**
	 * @param secure
	 *            the secure to set
	 */
	public void setSecure(String secure) {
		this.secure = secure;
	}

	/**
	 * @return the maxNoOfRows
	 */
	public int getMaxNoOfRows() {
		return maxNoOfRows;
	}

	/**
	 * @param maxNoOfRows
	 *            the maxNoOfRows to set
	 */
	public void setMaxNoOfRows(int maxNoOfRows) {
		this.maxNoOfRows = maxNoOfRows;
	}

	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the adminPassword
	 */
	public String getAdminPassword() {
		return adminPassword;
	}

	/**
	 * @param adminPassword
	 *            the adminPassword to set
	 */
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	/**
	 * @return the adminUserDN
	 */
	public String getAdminUserDN() {
		return adminUserDN;
	}

	/**
	 * @param adminUserDN
	 *            the adminUserDN to set
	 */
	public void setAdminUserDN(String adminUserDN) {
		this.adminUserDN = adminUserDN;
	}

	/**
	 * @return the ldapURL
	 */
	public String getLdapURL() {
		return ldapURL;
	}

	/**
	 * @param ldapURL
	 *            the ldapURL to set
	 */
	public void setLdapURL(String ldapURL) {
		this.ldapURL = ldapURL;
	}

	/**
	 * @return the pabDN
	 */
	public String getPabDN() {
		return pabDN;
	}

	/**
	 * @param pabDN
	 *            the pabDN to set
	 */
	public void setPabDN(String pabDN) {
		this.pabDN = pabDN;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "---------LDAP---------" + "\n" + "URL:" + getLdapURL() + "\n"
				+ "PORT:" + getPort() + "\n" + "SECURE:" + getSecure() + "\n"
				+ "PAB DN:" + getPabDN() + "\n" + "ADMIN USER DN:"
				+ getAdminUserDN() + "\n" + "PASSWORD:" + getAdminPassword()
				+ "\n" + "TIMEOUT:" + getTimeout() + "\n" + "MAX ROWS:"
				+ getMaxNoOfRows() + "\n" + "ID:" + getId();
	}

}
