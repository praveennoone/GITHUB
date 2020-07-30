// File:         StandardTime.java
// Created:      Apr 21, 2011
// Author:       Pinky S
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.domain;

import java.util.ArrayList;

/**
 * The Class Facility.
 * 
 */
public class StandardTime {

	/** The Selected Test Type. */
	private String selectedTestType;

	/** The Selected Test Method Name. */
	private String selectedTestMethodName;

	/** The New Standard Time Name. */
	private int selectedStandardTime;

	/** The Test Types. */
	private ArrayList<String> testTypes;

	/** The Test Methods. */
	private ArrayList<TestMethods> testMethods;

	/** The user name. */
	private String userName;

	/** The display name. */
	private String displayName;

	/** The message. */
	private String message;

	/** The is success message. */
	private boolean isSuccess;

	/** The old Standard Time. */
	private int oldStandardTime;

	/**
	 * @return the selectedTestType
	 */
	public String getSelectedTestType() {
		return selectedTestType;
	}

	/**
	 * @param selectedTestType
	 *            the selectedTestType to set
	 */
	public void setSelectedTestType(String selectedTestType) {
		this.selectedTestType = selectedTestType;
	}

	/**
	 * @return the testType
	 */
	public ArrayList<String> getTestTypes() {
		return testTypes;
	}

	/**
	 * @param testType
	 *            the testType to set
	 */
	public void setTestTypes(ArrayList<String> testTypes) {
		this.testTypes = testTypes;
	}

	/**
	 * @return the testMethods
	 */
	public ArrayList<TestMethods> getTestMethods() {
		return testMethods;
	}

	/**
	 * @param testMethods
	 *            the testMethods to set
	 */
	public void setTestMethods(ArrayList<TestMethods> testMethods) {
		this.testMethods = testMethods;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * @return the selectedTestMethodName
	 */
	public String getSelectedTestMethodName() {
		return selectedTestMethodName;
	}

	/**
	 * @param selectedTestMethodName
	 *            the selectedTestMethodName to set
	 */
	public void setSelectedTestMethodName(String selectedTestMethodName) {
		this.selectedTestMethodName = selectedTestMethodName;
	}

	/**
	 * @return the selectedStandardTime
	 */
	public int getSelectedStandardTime() {
		return selectedStandardTime;
	}

	/**
	 * @param selectedStandardTime
	 *            the selectedStandardTime to set
	 */
	public void setSelectedStandardTime(int selectedStandardTime) {
		this.selectedStandardTime = selectedStandardTime;
	}

	/**
	 * Gets the message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 * 
	 * @param message
	 *            the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the isSuccess
	 */
	public boolean getIsSuccess() {
		return isSuccess;
	}

	/**
	 * @param isSuccess
	 *            the isSuccess to set
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * @return the oldStandardTime
	 */
	public int getOldStandardTime() {
		return oldStandardTime;
	}

	/**
	 * @param oldStandardTime
	 *            the oldStandardTime to set
	 */
	public void setOldStandardTime(int oldStandardTime) {
		this.oldStandardTime = oldStandardTime;
	}

}
