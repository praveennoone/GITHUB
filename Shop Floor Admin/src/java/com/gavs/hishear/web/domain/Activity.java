// File:         Activity.java
// Created:      Feb 23, 2011
// Author:       rahjeshd
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.domain;

import java.util.Date;

/**
 * The Class Activity.
 * 
 */
public class Activity {

	/** MO Number. */
	private String moNumber;

	/** operation Number. */
	private String operationNumber;

	/** Logon Date. */
	private Date logonDate;

	/** Logoff date. */
	private Date logoffDate;

	/** Activity Code. */
	private String activityCode;

	/** Employee Number. */
	private String employeeNumber;

	/** Completed Quantity. */
	private int completedQuantity;

	/** Job Activity Number. */
	private String jobActivityNumber;

	/** Assety Number. */
	private String assetNumber;

	/** Activity Log Number. */
	private String activityLogNumber;

	/** Minutes Processed. */
	private double minutesProcessed;

	/** The selected for edit. */
	private boolean selectedForEdit;

	/** Part Number. */
	private String productNumber;

	/** Facility. */
	private String facility;

	/** Is Reported or Completed. */
	private boolean reported;

	/** The line number. */
	private String lineNumber;

	/** The error message. */
	private String errorMessage;

	/** The morv quantity. */
	private int morvQuantity;

	/** The operation description. */
	private String operationDescription;

	/** The price time qty. */
	private double priceTimeQty;

	/** The run hours. */
	private String runHours;

	/** The pph. */
	private String pph;

	/** The std pph. */
	private String stdPPH;

	private String jobNumber;

	/**
	 * Gets the run hours.
	 * 
	 * @return the run hours
	 */
	public String getRunHours() {
		return runHours;
	}

	/**
	 * Sets the run hours.
	 * 
	 * @param runHours
	 *            the new run hours
	 */
	public void setRunHours(String runHours) {
		this.runHours = runHours;
	}

	/**
	 * Gets the pph.
	 * 
	 * @return the pph
	 */
	public String getPph() {
		return pph;
	}

	/**
	 * Sets the pph.
	 * 
	 * @param pph
	 *            the new pph
	 */
	public void setPph(String pph) {
		this.pph = pph;
	}

	/**
	 * Gets the std pph.
	 * 
	 * @return the std pph
	 */
	public String getStdPPH() {
		return stdPPH;
	}

	/**
	 * Sets the std pph.
	 * 
	 * @param stdPPH
	 *            the new std pph
	 */
	public void setStdPPH(String stdPPH) {
		this.stdPPH = stdPPH;
	}

	/**
	 * Gets the price time qty.
	 * 
	 * @return the price time qty
	 */
	public double getPriceTimeQty() {
		return priceTimeQty;
	}

	/**
	 * Sets the price time qty.
	 * 
	 * @param priceTimeQty
	 *            the new price time qty
	 */
	public void setPriceTimeQty(double priceTimeQty) {
		this.priceTimeQty = priceTimeQty;
	}

	/**
	 * Gets the mo number.
	 * 
	 * @return the moNumber
	 */
	public String getMoNumber() {
		return moNumber;
	}

	/**
	 * Sets the mo number.
	 * 
	 * @param moNumber
	 *            the moNumber to set
	 */
	public void setMoNumber(String moNumber) {
		this.moNumber = moNumber;
	}

	/**
	 * Gets the operation number.
	 * 
	 * @return the operationNumber
	 */
	public String getOperationNumber() {
		return operationNumber;
	}

	/**
	 * Sets the operation number.
	 * 
	 * @param operationNumber
	 *            the operationNumber to set
	 */
	public void setOperationNumber(String operationNumber) {
		this.operationNumber = operationNumber;
	}

	/**
	 * Gets the logon date.
	 * 
	 * @return the logonDate
	 */
	public Date getLogonDate() {
		return logonDate;
	}

	/**
	 * Sets the logon date.
	 * 
	 * @param logonDate
	 *            the logonDate to set
	 */
	public void setLogonDate(Date logonDate) {
		this.logonDate = logonDate;
	}

	/**
	 * Gets the logoff date.
	 * 
	 * @return the logoffDate
	 */
	public Date getLogoffDate() {
		return logoffDate;
	}

	/**
	 * Sets the logoff date.
	 * 
	 * @param logoffDate
	 *            the logoffDate to set
	 */
	public void setLogoffDate(Date logoffDate) {
		this.logoffDate = logoffDate;
	}

	/**
	 * Gets the activity code.
	 * 
	 * @return the activityCode
	 */
	public String getActivityCode() {
		return activityCode;
	}

	/**
	 * Sets the activity code.
	 * 
	 * @param activityCode
	 *            the activityCode to set
	 */
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	/**
	 * Gets the employee number.
	 * 
	 * @return the employeeNumber
	 */
	public String getEmployeeNumber() {
		return employeeNumber;
	}

	/**
	 * Sets the employee number.
	 * 
	 * @param employeeNumber
	 *            the employeeNumber to set
	 */
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	/**
	 * Gets the completed quantity.
	 * 
	 * @return the completedQuantity
	 */
	public int getCompletedQuantity() {
		return completedQuantity;
	}

	/**
	 * Sets the completed quantity.
	 * 
	 * @param completedQuantity
	 *            the completedQuantity to set
	 */
	public void setCompletedQuantity(int completedQuantity) {
		this.completedQuantity = completedQuantity;
	}

	/**
	 * Gets the job activity number.
	 * 
	 * @return the jobActivityNumber
	 */
	public String getJobActivityNumber() {
		return jobActivityNumber;
	}

	/**
	 * Sets the job activity number.
	 * 
	 * @param jobActivityNumber
	 *            the jobActivityNumber to set
	 */
	public void setJobActivityNumber(String jobActivityNumber) {
		this.jobActivityNumber = jobActivityNumber;
	}

	/**
	 * Gets the asset number.
	 * 
	 * @return the assetNumber
	 */
	public String getAssetNumber() {
		return assetNumber;
	}

	/**
	 * Sets the asset number.
	 * 
	 * @param assetNumber
	 *            the assetNumber to set
	 */
	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	/**
	 * Gets the activity log number.
	 * 
	 * @return the activityLogNumber
	 */
	public String getActivityLogNumber() {
		return activityLogNumber;
	}

	/**
	 * Sets the activity log number.
	 * 
	 * @param activityLogNumber
	 *            the activityLogNumber to set
	 */
	public void setActivityLogNumber(String activityLogNumber) {
		this.activityLogNumber = activityLogNumber;
	}

	/**
	 * Gets the minutes processed.
	 * 
	 * @return the minutesProcessed
	 */
	public double getMinutesProcessed() {
		return minutesProcessed;
	}

	/**
	 * Sets the minutes processed.
	 * 
	 * @param minutesProcessed
	 *            the minutesProcessed to set
	 */
	public void setMinutesProcessed(double minutesProcessed) {
		this.minutesProcessed = minutesProcessed;
	}

	/**
	 * Checks if is selected for edit.
	 * 
	 * @return the selectedForEdit
	 */
	public boolean isSelectedForEdit() {
		return selectedForEdit;
	}

	/**
	 * Sets the selected for edit.
	 * 
	 * @param selectedForEdit
	 *            the selectedForEdit to set
	 */
	public void setSelectedForEdit(boolean selectedForEdit) {
		this.selectedForEdit = selectedForEdit;
	}

	/**
	 * Gets the product number.
	 * 
	 * @return the productNumber
	 */
	public String getProductNumber() {
		return productNumber;
	}

	/**
	 * Sets the product number.
	 * 
	 * @param productNumber
	 *            the productNumber to set
	 */
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	/**
	 * Gets the facility.
	 * 
	 * @return the facility
	 */
	public String getFacility() {
		return facility;
	}

	/**
	 * Sets the facility.
	 * 
	 * @param facility
	 *            the facility to set
	 */
	public void setFacility(String facility) {
		this.facility = facility;
	}

	/**
	 * Checks if is reported.
	 * 
	 * @return the reported
	 */
	public boolean isReported() {
		return reported;
	}

	/**
	 * Sets the reported.
	 * 
	 * @param reported
	 *            the reported to set
	 */
	public void setReported(boolean reported) {
		this.reported = reported;
	}

	/**
	 * Gets the line number.
	 * 
	 * @return the lineNumber
	 */
	public String getLineNumber() {
		return lineNumber;
	}

	/**
	 * Sets the line number.
	 * 
	 * @param lineNumber
	 *            the lineNumber to set
	 */
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * Gets the error message.
	 * 
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the error message.
	 * 
	 * @param errorMessage
	 *            the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the morv quantity.
	 * 
	 * @return the morv quantity
	 */
	public int getMorvQuantity() {
		return morvQuantity;
	}

	/**
	 * Sets the morv quantity.
	 * 
	 * @param morvQuantity
	 *            the new morv quantity
	 */
	public void setMorvQuantity(int morvQuantity) {
		this.morvQuantity = morvQuantity;
	}

	/**
	 * Gets the operation description.
	 * 
	 * @return the operationDescription
	 */
	public String getOperationDescription() {
		return operationDescription;
	}

	/**
	 * Sets the operation description.
	 * 
	 * @param operationDescription
	 *            the operationDescription to set
	 */
	public void setOperationDescription(String operationDescription) {
		this.operationDescription = operationDescription;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getJobNumber() {
		return jobNumber;
	}

}