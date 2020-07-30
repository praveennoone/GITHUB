/**
 * 
 */
package com.gavs.hishear.configurations;

/**
 * @author rahjeshd					
 * 
 */
public class Environment {
	private String m3Environment;
	private String databaseServer;
	private String databaseName;
	private String division;
	private String version;

	/**
	 * @return the m3Environment
	 */
	public String getM3Environment() {
		return m3Environment;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	/**
	 * @param environment
	 *            the m3Environment to set
	 */
	public void setM3Environment(String environment) {
		m3Environment = environment;
	}

	/**
	 * @return the databaseServer
	 */
	public String getDatabaseServer() {
		return databaseServer;
	}

	/**
	 * @param databaseServer
	 *            the databaseServer to set
	 */
	public void setDatabaseServer(String databaseServer) {
		this.databaseServer = databaseServer;
	}

	/**
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * @param databaseName
	 *            the databaseName to set
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
}
