/**
 * ErrorLog.java 4:43:26 PMJun 29, 20092009 
 */
package com.gavs.hishear.web.domain;

/**
 * @author subhashri
 * 
 */
public class ErrorLog {

	private String facility;

	private String productNo;

	private String moNumber;

	private String operationNo;

	private String employeeNo;

	private String assetNo;

	private String activityStartTime;

	private String activityEndTime;

	private String quantity;

	private String timeDifference;

	private String webServiceName;

	private String columnHeaders;

	private String columnValues;

	private String m3Function;

	private String functionName;

	private boolean isDisplayProgram;

	private String subFunctionName;

	private int noOfTry;

	private String errorMessage;

	private String loginUserID;

	private int priority;

	private String inputItem;

	private String outputItem;

	/**
	 * @return the timeDifference
	 */
	public String getTimeDifference() {
		return timeDifference;
	}

	/**
	 * @param timeDifference
	 *            the timeDifference to set
	 */
	public void setTimeDifference(String timeDifference) {
		this.timeDifference = timeDifference;
	}

	/**
	 * @return the facility
	 */
	public String getFacility() {
		return facility;
	}

	/**
	 * @param facility
	 *            the facility to set
	 */
	public void setFacility(String facility) {
		this.facility = facility;
	}

	/**
	 * @return the productNo
	 */
	public String getProductNo() {
		return productNo;
	}

	/**
	 * @param productNo
	 *            the productNo to set
	 */
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	/**
	 * @return the moNumber
	 */
	public String getMoNumber() {
		return moNumber;
	}

	/**
	 * @param moNumber
	 *            the moNumber to set
	 */
	public void setMoNumber(String moNumber) {
		this.moNumber = moNumber;
	}

	/**
	 * @return the operationNo
	 */
	public String getOperationNo() {
		return operationNo;
	}

	/**
	 * @param operationNo
	 *            the operationNo to set
	 */
	public void setOperationNo(String operationNo) {
		this.operationNo = operationNo;
	}

	/**
	 * @return the activityStartTime
	 */
	public String getActivityStartTime() {
		return activityStartTime;
	}

	/**
	 * @param activityStartTime
	 *            the activityStartTime to set
	 */
	public void setActivityStartTime(String activityStartTime) {
		this.activityStartTime = activityStartTime;
	}

	/**
	 * @return the activityEndTime
	 */
	public String getActivityEndTime() {
		return activityEndTime;
	}

	/**
	 * @param activityEndTime
	 *            the activityEndTime to set
	 */
	public void setActivityEndTime(String activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	/**
	 * @return the employeeNo
	 */
	public String getEmployeeNo() {
		return employeeNo;
	}

	/**
	 * @param employeeNo
	 *            the employeeNo to set
	 */
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	/**
	 * @return the assetNo
	 */
	public String getAssetNo() {
		return assetNo;
	}

	/**
	 * @param assetNo
	 *            the assetNo to set
	 */
	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

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
	 * @return the m3Function
	 */
	public String getM3Function() {
		return m3Function;
	}

	/**
	 * @param function
	 *            the m3Function to set
	 */
	public void setM3Function(String function) {
		m3Function = function;
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
	 * @return the isDisplayProgram
	 */
	public boolean isDisplayProgram() {
		return isDisplayProgram;
	}

	/**
	 * @param isDisplayProgram
	 *            the isDisplayProgram to set
	 */
	public void setDisplayProgram(boolean isDisplayProgram) {
		this.isDisplayProgram = isDisplayProgram;
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
	 * @return the noOfTry
	 */
	public int getNoOfTry() {
		return noOfTry;
	}

	/**
	 * @param noOfTry
	 *            the noOfTry to set
	 */
	public void setNoOfTry(int noOfTry) {
		this.noOfTry = noOfTry;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
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

}
