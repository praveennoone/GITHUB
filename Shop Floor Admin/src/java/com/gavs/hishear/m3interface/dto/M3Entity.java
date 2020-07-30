package com.gavs.hishear.m3interface.dto;

import java.util.HashMap;

public class M3Entity {
	private String webServiceName;
	
	private String functionName;

	private HashMap<String, String> inputData;

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
	 * @return the inputData
	 */
	public HashMap<String, String> getInputData() {
		return inputData;
	}

	/**
	 * @param inputData
	 *            the inputData to set
	 */
	public void setInputData(HashMap<String, String> inputData) {
		this.inputData = inputData;
	}
}
