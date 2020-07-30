/**
 * 
 */
package com.gavs.hishear.web.domain;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author mohammeda
 * 
 */
public class M3RequestLog {

	private ArrayList<ErrorLog> m3RequestLogs;

	private String displayName;

	private String programName;

	private String functionName;

	private String dateProcessed;

	private String[] dataHeaders;

	private ArrayList<Map<String, String>> programDetails;

	private ArrayList<Map<String, String>> m3TransByDate;

	private String[] dataHeadersForM3Trans;

	/**
	 * @return the m3RequestLogs
	 */
	public ArrayList<ErrorLog> getM3RequestLogs() {
		return m3RequestLogs;
	}

	/**
	 * @param requestLogs
	 *            the m3RequestLogs to set
	 */
	public void setM3RequestLogs(ArrayList<ErrorLog> requestLogs) {
		m3RequestLogs = requestLogs;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the programName
	 */
	public String getProgramName() {
		return programName;
	}

	/**
	 * @param programName
	 *            the programName to set
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
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
	 * @return the dateProcessed
	 */
	public String getDateProcessed() {
		return dateProcessed;
	}

	/**
	 * @param dateProcessed
	 *            the dateProcessed to set
	 */
	public void setDateProcessed(String dateProcessed) {
		this.dateProcessed = dateProcessed;
	}

	/**
	 * @return the dataHeaders
	 */
	public String[] getDataHeaders() {
		return dataHeaders;
	}

	/**
	 * @param dataHeaders
	 *            the dataHeaders to set
	 */
	public void setDataHeaders(String[] dataHeaders) {
		this.dataHeaders = dataHeaders;
	}

	/**
	 * @return the programDetails
	 */
	public ArrayList<Map<String, String>> getProgramDetails() {
		return programDetails;
	}

	/**
	 * @param programDetails
	 *            the programDetails to set
	 */
	public void setProgramDetails(ArrayList<Map<String, String>> programDetails) {
		this.programDetails = programDetails;
	}

	/**
	 * @return the m3TransByDate
	 */
	public ArrayList<Map<String, String>> getM3TransByDate() {
		return m3TransByDate;
	}

	/**
	 * @param transByDate
	 *            the m3TransByDate to set
	 */
	public void setM3TransByDate(ArrayList<Map<String, String>> transByDate) {
		m3TransByDate = transByDate;
	}

	/**
	 * @return the dataHeadersForM3Trans
	 */
	public String[] getDataHeadersForM3Trans() {
		return dataHeadersForM3Trans;
	}

	/**
	 * @param dataHeadersForM3Trans
	 *            the dataHeadersForM3Trans to set
	 */
	public void setDataHeadersForM3Trans(String[] dataHeadersForM3Trans) {
		this.dataHeadersForM3Trans = dataHeadersForM3Trans;
	}

}
