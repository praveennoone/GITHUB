/**
 * ReasonCode.java
 */
package com.gavs.hishear.web.domain;

import java.util.ArrayList;

/**
 * @author ambrishv
 * 
 */
// Begin WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr 2011
public class ReasonCode {

	/** The user name. */
	private String userName;

	/** The display name. */
	private String displayName;

	/** The error. */
	private String error;

	/** The Reason Type. */
	private ArrayList<String> reasonType;

	/** The Reason Details. */
	private ArrayList<ReasonType> reasonDetails;

	/** The Reason Selected Reason Type. */
	private String selectedReasonType;

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
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @param reasonType
	 *            the reasonType to set
	 */
	public void setReasonType(ArrayList<String> reasonType) {
		this.reasonType = reasonType;
	}

	/**
	 * @return the reasonType
	 */
	public ArrayList<String> getReasonType() {
		return reasonType;
	}

	/**
	 * @param selectedReasonType
	 *            the selectedReasonType to set
	 */
	public void setSelectedReasonType(String selectedReasonType) {
		this.selectedReasonType = selectedReasonType;
	}

	/**
	 * @return the selectedReasonType
	 */
	public String getSelectedReasonType() {
		return selectedReasonType;
	}

	/**
	 * @param reasonDetails
	 *            the reasonDetails to set
	 */
	public void setReasonDetails(ArrayList<ReasonType> reasonDetails) {
		this.reasonDetails = reasonDetails;
	}

	/**
	 * @return the reasonDetails
	 */
	public ArrayList<ReasonType> getReasonDetails() {
		return reasonDetails;
	}
	// End WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr
	// 2011
}
