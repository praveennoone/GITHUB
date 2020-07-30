// File:         ManufacturingOrder.java
// Created:      Feb 23, 2011
// Author:       rahjeshd
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class ManufacturingOrder.
 * 
 */
public class ManufacturingOrder {

	/** MO Number. */
	private String moNumber;

	/** Facility. */
	private String facility;

	/** Part Number. */
	private String partNumber;

	/** Part Description. */
	private String partDescription;

	/** Activities. */
	private ArrayList<Activity> errorActivities;

	/** Activity selected for edit. */
	private Activity selectedAvtivity;

	/** The user name. */
	private String userName;

	/** The display name. */
	private String displayName;

	/** The failed activities. */
	private ArrayList<Activity> failedActivities;

	/** The error mos. */
	private ArrayList<Sequence> errorMos;

	/** The bom list. */
	private ArrayList bomList;

	// added for Correction interface Modify MO
	/** The sequences. */
	private HashMap<String, Sequence> sequences;

	/** The selected sequeuce. */
	private Sequence selectedSequeuce;

	/** The pause reasons. */
	private ArrayList<MasterData> pauseReasons;

	/** The modify m os. */
	private ArrayList<Sequence> modifyMOs;

	/** The page selected. */
	private String pageSelected;

	/** The parameter map. */
	private HashMap<String, Object> parameterMap;

	/** Facility Based on Division. */
	private ArrayList<Facility> divisionFacilities;

	/** The sequences in m3. */
	private ArrayList<Sequence> sequencesInM3;

	/** The error. */
	private String error;

	/** The selected sequence number. */
	private String selectedSequenceNumber;

	/** The employee map. */
	private HashMap employeeMap;

	/** The job number. */
	private int jobNumber;

	// Begin WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr
	// 2011

	/** The Work Center Configuration. */
	private ArrayList<WorkCenterConfig> workCenterConfig;

	/** The Asset Configuration. */
	private ArrayList<AssetConfig> assetConfig;

	/** The Work Center Code. */
	private String workCenterCode;

	/** The Asset Number. */
	private String assetNumber;

	/** The Edit Flag. */
	private boolean editFlag;

	/** Description. */
	private String description;

	/** The PPH Threshold Percentage. */
	private int pphThresholdPercentage;

	/** The Scale Required. */
	private String scaleRequired;

	/** The Status. */
	private String status;

	//this variable is added by the saikiran tk.no=414799
	/** The ScrapCount*/
	private String scrapCount;
	
	private String selectedFacility;

	
	public String getScrapCount() {
		return scrapCount;
	}

	public void setScrapCount(String scrapCount) {
		this.scrapCount = scrapCount;
	}
	
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the pphThresholdPercentage
	 */
	public int getPphThresholdPercentage() {
		return pphThresholdPercentage;
	}

	/**
	 * @param pphThresholdPercentage
	 *            the pphThresholdPercentage to set
	 */
	public void setPphThresholdPercentage(int pphThresholdPercentage) {
		this.pphThresholdPercentage = pphThresholdPercentage;
	}

	/**
	 * @return the scaleRequired
	 */
	public String getScaleRequired() {
		return scaleRequired;
	}

	/**
	 * @param scaleRequired
	 *            the scaleRequired to set
	 */
	public void setScaleRequired(String scaleRequired) {
		this.scaleRequired = scaleRequired;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param selectedFacility
	 *            the selectedFacility to set
	 */
	public void setSelectedFacility(String selectedFacility) {
		this.selectedFacility = selectedFacility;
	}

	/**
	 * @return the selectedFacility
	 */
	public String getSelectedFacility() {
		return selectedFacility;
	}

	// End WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr
	// 2011

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
	 * Gets the part number.
	 * 
	 * @return the partNumber
	 */
	public String getPartNumber() {
		return partNumber;
	}

	/**
	 * Sets the part number.
	 * 
	 * @param partNumber
	 *            the partNumber to set
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	/**
	 * Gets the part description.
	 * 
	 * @return the partDescription
	 */
	public String getPartDescription() {
		return partDescription;
	}

	/**
	 * Sets the part description.
	 * 
	 * @param partDescription
	 *            the partDescription to set
	 */
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	/**
	 * Gets the error activities.
	 * 
	 * @return the errorActivities
	 */
	public ArrayList<Activity> getErrorActivities() {
		return errorActivities;
	}

	/**
	 * Sets the error activities.
	 * 
	 * @param errorActivities
	 *            the errorActivities to set
	 */
	public void setErrorActivities(ArrayList<Activity> errorActivities) {
		this.errorActivities = errorActivities;
	}

	/**
	 * Gets the selected avtivity.
	 * 
	 * @return the selectedAvtivity
	 */
	public Activity getSelectedAvtivity() {
		return selectedAvtivity;
	}

	/**
	 * Sets the selected avtivity.
	 * 
	 * @param selectedAvtivity
	 *            the selectedAvtivity to set
	 */
	public void setSelectedAvtivity(Activity selectedAvtivity) {
		this.selectedAvtivity = selectedAvtivity;
	}

	/**
	 * Gets the user name.
	 * 
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 * 
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the display name.
	 * 
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the display name.
	 * 
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Gets the failed activities.
	 * 
	 * @return the failedActivities
	 */
	public ArrayList<Activity> getFailedActivities() {
		return failedActivities;
	}

	/**
	 * Sets the failed activities.
	 * 
	 * @param failedActivities
	 *            the failedActivities to set
	 */
	public void setFailedActivities(ArrayList<Activity> failedActivities) {
		this.failedActivities = failedActivities;
	}

	/**
	 * Gets the error mos.
	 * 
	 * @return the errorMos
	 */
	public ArrayList<Sequence> getErrorMos() {
		return errorMos;
	}

	/**
	 * Sets the error mos.
	 * 
	 * @param errorMos
	 *            the errorMos to set
	 */
	public void setErrorMos(ArrayList<Sequence> errorMos) {
		this.errorMos = errorMos;
	}

	/**
	 * Gets the bom list.
	 * 
	 * @return the bomList
	 */
	public ArrayList getBomList() {
		return bomList;
	}

	/**
	 * Sets the bom list.
	 * 
	 * @param bomList
	 *            the bomList to set
	 */
	public void setBomList(ArrayList bomList) {
		this.bomList = bomList;
	}

	/**
	 * Gets the division facilities.
	 * 
	 * @return the divisionFacilities
	 */
	public ArrayList<Facility> getDivisionFacilities() {
		return divisionFacilities;
	}

	/**
	 * Sets the division facilities.
	 * 
	 * @param divisionFacilities
	 *            the divisionFacilities to set
	 */
	public void setDivisionFacilities(ArrayList<Facility> divisionFacilities) {
		this.divisionFacilities = divisionFacilities;
	}

	/**
	 * Sets the sequences.
	 * 
	 * @param sequences
	 *            the sequences
	 */
	public void setSequences(HashMap<String, Sequence> sequences) {
		this.sequences = sequences;
	}

	/**
	 * Gets the sequences.
	 * 
	 * @return the sequences
	 */
	public HashMap<String, Sequence> getSequences() {
		return sequences;
	}

	/**
	 * Sets the selected sequeuce.
	 * 
	 * @param selectedSequeuce
	 *            the new selected sequeuce
	 */
	public void setSelectedSequeuce(Sequence selectedSequeuce) {
		this.selectedSequeuce = selectedSequeuce;
	}

	/**
	 * Gets the selected sequeuce.
	 * 
	 * @return the selected sequeuce
	 */
	public Sequence getSelectedSequeuce() {
		return selectedSequeuce;
	}

	/**
	 * Sets the pause reasons.
	 * 
	 * @param pauseReasons
	 *            the pauseReasons to set
	 */
	public void setPauseReasons(ArrayList<MasterData> pauseReasons) {
		this.pauseReasons = pauseReasons;
	}

	/**
	 * Gets the pause reasons.
	 * 
	 * @return the pauseReasons
	 */
	public ArrayList<MasterData> getPauseReasons() {
		return pauseReasons;
	}

	/**
	 * Sets the modify m os.
	 * 
	 * @param seqs
	 *            the new modify m os
	 */
	public void setModifyMOs(ArrayList<Sequence> seqs) {
		this.modifyMOs = seqs;
	}

	/**
	 * Gets the modify m os.
	 * 
	 * @return the modifyMOs
	 */
	public ArrayList<Sequence> getModifyMOs() {
		return modifyMOs;
	}

	/**
	 * Sets the page selected.
	 * 
	 * @param pageSelected
	 *            the pageSelected to set
	 */
	public void setPageSelected(String pageSelected) {
		this.pageSelected = pageSelected;
	}

	/**
	 * Gets the page selected.
	 * 
	 * @return the pageSelected
	 */
	public String getPageSelected() {
		return pageSelected;
	}

	/**
	 * Sets the parameter map.
	 * 
	 * @param parameterMap
	 *            the parameterMap to set
	 */
	public void setParameterMap(HashMap<String, Object> parameterMap) {
		this.parameterMap = parameterMap;
	}

	/**
	 * Gets the parameter map.
	 * 
	 * @return the parameterMap
	 */
	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}

	/**
	 * Sets the employee map.
	 * 
	 * @param employeeMap
	 *            the employeeMap to set
	 */
	public void setEmployeeMap(HashMap employeeMap) {
		this.employeeMap = employeeMap;
	}

	/**
	 * Gets the employee map.
	 * 
	 * @return the employeeMap
	 */
	public HashMap getEmployeeMap() {
		return employeeMap;
	}

	/**
	 * Gets the sequences in m3.
	 * 
	 * @return the sequences in m3
	 */
	public ArrayList<Sequence> getSequencesInM3() {
		return sequencesInM3;
	}

	/**
	 * Sets the sequences in m3.
	 * 
	 * @param sequencesInM3
	 *            the new sequences in m3
	 */
	public void setSequencesInM3(ArrayList<Sequence> sequencesInM3) {
		this.sequencesInM3 = sequencesInM3;
	}

	/**
	 * Gets the selected sequence number.
	 * 
	 * @return the selectedSequenceNumber
	 */
	public String getSelectedSequenceNumber() {
		return selectedSequenceNumber;
	}

	/**
	 * Sets the selected sequence number.
	 * 
	 * @param selectedSequenceNumber
	 *            the selectedSequenceNumber to set
	 */
	public void setSelectedSequenceNumber(String selectedSequenceNumber) {
		this.selectedSequenceNumber = selectedSequenceNumber;
	}

	// Begin WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr
	// 2011

	/**
	 * Gets the error.
	 * 
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * Sets the error.
	 * 
	 * @param error
	 *            the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	// End WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr
	// 2011

	/**
	 * Sets the job number.
	 * 
	 * @param jobNumber
	 *            the new job number
	 */
	public void setJobNumber(int jobNumber) {
		this.jobNumber = jobNumber;
	}

	/**
	 * Gets the job number.
	 * 
	 * @return the job number
	 */
	public int getJobNumber() {
		return jobNumber;
	}

	// Begin WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr
	// 2011

	/**
	 * @param workCenterConfig
	 *            the workCenterConfig to set
	 */
	public void setWorkCenterConfig(ArrayList<WorkCenterConfig> workCenterConfig) {
		this.workCenterConfig = workCenterConfig;
	}

	/**
	 * @return the workCenterConfig
	 */
	public ArrayList<WorkCenterConfig> getWorkCenterConfig() {
		return workCenterConfig;
	}

	/**
	 * @param workCenterCode
	 *            the workCenterCode to set
	 */
	public void setWorkCenterCode(String workCenterCode) {
		this.workCenterCode = workCenterCode;
	}

	/**
	 * @return the workCenterCode
	 */
	public String getWorkCenterCode() {
		return workCenterCode;
	}

	/**
	 * @param assetConfig
	 *            the assetConfig to set
	 */
	public void setAssetConfig(ArrayList<AssetConfig> assetConfig) {
		this.assetConfig = assetConfig;
	}

	/**
	 * @return the assetConfig
	 */
	public ArrayList<AssetConfig> getAssetConfig() {
		return assetConfig;
	}

	/**
	 * @param editFlag
	 *            the editFlag to set
	 */
	public void setEditFlag(boolean editFlag) {
		this.editFlag = editFlag;
	}

	/**
	 * @return the editFlag
	 */
	public boolean getEditFlag() {
		return editFlag;
	}

	/**
	 * @param assetNumber
	 *            the assetNumber to set
	 */
	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	/**
	 * @return the assetNumber
	 */
	public String getAssetNumber() {
		return assetNumber;
	}

	// End WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr
	// 2011

}