/**
 * 
 */
package com.gavs.hishear.web.domain;

import java.util.Date;

/**
 * Abstraction of the API Request sent to M3
 * 
 * @author rahjeshd
 * 
 */
public class M3Request {
	private String webServiceName;
	private String columnHeaders;
	private String columnValues;
	private String inputItem;
	private String outputItem;
	private String functionName;
	private String m3FunctionName;
	private String subFunctionName;
	private Date dateProcessed;
	private String loginUserID;

	/**
	 * @return the webServiceName
	 */
	public String getWebServiceName() {
		return webServiceName;
	}

	/**
	 * @param webServiceName
	 *            the webServiceName to set
	 */
	public void setWebServiceName(String webServiceName) {
		this.webServiceName = webServiceName;
	}

	/**
	 * @return the columnHeaders
	 */
	public String getColumnHeaders() {
		return columnHeaders;
	}

	/**
	 * @param columnHeaders
	 *            the columnHeaders to set
	 */
	public void setColumnHeaders(String columnHeaders) {
		this.columnHeaders = columnHeaders;
	}

	/**
	 * @return the columnValues
	 */
	public String getColumnValues() {
		return columnValues;
	}

	/**
	 * @param columnValues
	 *            the columnValues to set
	 */
	public void setColumnValues(String columnValues) {
		this.columnValues = columnValues;
	}

	/**
	 * @return the inputItem
	 */
	public String getInputItem() {
		return inputItem;
	}

	/**
	 * @param inputItem
	 *            the inputItem to set
	 */
	public void setInputItem(String inputItem) {
		this.inputItem = inputItem;
	}

	/**
	 * @return the outputItem
	 */
	public String getOutputItem() {
		return outputItem;
	}

	/**
	 * @param outputItem
	 *            the outputItem to set
	 */
	public void setOutputItem(String outputItem) {
		this.outputItem = outputItem;
	}

	/**
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * @param functionName
	 *            the functionName to set
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	/**
	 * @return the m3FunctionName
	 */
	public String getM3FunctionName() {
		return m3FunctionName;
	}

	/**
	 * @param functionName
	 *            the m3FunctionName to set
	 */
	public void setM3FunctionName(String functionName) {
		m3FunctionName = functionName;
	}

	/**
	 * @return the subFunctionName
	 */
	public String getSubFunctionName() {
		return subFunctionName;
	}

	/**
	 * @param subFunctionName
	 *            the subFunctionName to set
	 */
	public void setSubFunctionName(String subFunctionName) {
		this.subFunctionName = subFunctionName;
	}

	/**
	 * @return the dateProcessed
	 */
	public Date getDateProcessed() {
		return dateProcessed;
	}

	/**
	 * @param dateProcessed
	 *            the dateProcessed to set
	 */
	public void setDateProcessed(Date dateProcessed) {
		this.dateProcessed = dateProcessed;
	}

	/**
	 * @return the loginUserID
	 */
	public String getLoginUserID() {
		return loginUserID;
	}

	/**
	 * @param loginUserID
	 *            the loginUserID to set
	 */
	public void setLoginUserID(String loginUserID) {
		this.loginUserID = loginUserID;
	}
}