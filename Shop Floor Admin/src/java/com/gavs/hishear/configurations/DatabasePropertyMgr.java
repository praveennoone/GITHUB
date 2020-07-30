package com.gavs.hishear.configurations;

public class DatabasePropertyMgr {

	private String url;

	private String driverClass;

	private String host;

	private String port;

	private String databaseName;

	private String username;

	private String password;

	private String reportPath;

	private String posReportPath;

	private String componentReportPath;

	public String getComponentReportPath() {
		return componentReportPath;
	}

	public void setComponentReportPath(String componentReportPath) {
		this.componentReportPath = componentReportPath;
	}

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPosReportPath() {
		return posReportPath;
	}

	public void setPosReportPath(String posReportPath) {
		this.posReportPath = posReportPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "url = " + url + "driverClass = " + driverClass + "host = "
				+ host + "port" + port + "databaseName = " + databaseName
				+ "username = " + username + "password= " + password
				+ "reportPath = " + reportPath + "posReportPath = "
				+ "componentReportPath = " + componentReportPath;
	}

}
