/**
 * WorkCenterConfig.java
 */
package com.gavs.hishear.web.domain;

import java.util.ArrayList;

/**
 * @author Ambrish_v
 * 
 */
// Begin WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr 2011
public class WorkCenterConfig {

	/** Work Center Code. */
	private String workCenterCode;

	/** Facility. */
	private String facility;

	/** Description. */
	private String description;

	/** PPH Threshold Percentage. */
	private int pphThresholdPercentage;

	/** Scale Required. */
	private String scaleRequired;

	/** Active Flag. */
	private String activeFlag;

	/** Updated Description. */
	private String updatedDescription;

	/** Updated PPH Threshold Percentage. */
	private int updatedPPHThresholdPercentage;

	/** Updated Scale Required. */
	private String updatedScaleRequired;

	/** Updated Active Flag. */
	private String updatedActiveFlag;
	
	/*below scrapcount variable is add by saikiran tk.no=414799*/
	/**ScrapCount */
	private String scrapCount;
	
	/** Updated ScrapCount. */
	private String updatedScrapCount;
	


	public String getScrapCount() {
		return scrapCount;
	}

	public void setScrapCount(String scrapCount) {
		this.scrapCount = scrapCount;
	}

	public String getUpdatedScrapCount() {
		return updatedScrapCount;
	}

	public void setUpdatedScrapCount(String updatedScrapCount) {
		this.updatedScrapCount = updatedScrapCount;
	}

	/** Configuration Division. */
	private ArrayList<ConfigDivision> configDivision;

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
	 * @param facility
	 *            the facility to set
	 */
	public void setFacility(String facility) {
		this.facility = facility;
	}

	/**
	 * @return the facility
	 */
	public String getFacility() {
		return facility;
	}

	/**
	 * @param pphThresholdPercentage
	 *            the pphThresholdPercentage to set
	 */
	public void setPphThresholdPercentage(int pphThresholdPercentage) {
		this.pphThresholdPercentage = pphThresholdPercentage;
	}

	/**
	 * @return the pphThresholdPercentage
	 */
	public int getPphThresholdPercentage() {
		return pphThresholdPercentage;
	}

	/**
	 * @param scaleRequired
	 *            the scaleRequired to set
	 */
	public void setScaleRequired(String scaleRequired) {
		this.scaleRequired = scaleRequired;
	}

	/**
	 * @return the scaleRequired
	 */
	public String getScaleRequired() {
		return scaleRequired;
	}

	/**
	 * @param updatedPPHThresholdPercentage
	 *            the updatedPPHThresholdPercentage to set
	 */
	public void setupdatedPPHThresholdPercentage(
			int updatedPPHThresholdPercentage) {
		this.updatedPPHThresholdPercentage = updatedPPHThresholdPercentage;
	}

	/**
	 * @return the updatedPPHThresholdPercentage
	 */
	public int getupdatedPPHThresholdPercentage() {
		return updatedPPHThresholdPercentage;
	}

	/**
	 * @param updatedScaleRequired
	 *            the updatedScaleRequired to set
	 */
	public void setUpdatedScaleRequired(String updatedScaleRequired) {
		this.updatedScaleRequired = updatedScaleRequired;
	}

	/**
	 * @return the updatedScaleRequired
	 */
	public String getUpdatedScaleRequired() {
		return updatedScaleRequired;
	}

	/**
	 * @param activeFlag
	 *            the activeFlag to set
	 */
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * @return the activeFlag
	 */
	public String getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param updatedActiveFlag
	 *            the updatedActiveFlag to set
	 */
	public void setUpdatedActiveFlag(String updatedActiveFlag) {
		this.updatedActiveFlag = updatedActiveFlag;
	}

	/**
	 * @return the updatedActiveFlag
	 */
	public String getUpdatedActiveFlag() {
		return updatedActiveFlag;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param configDivision
	 *            the configDivision to set
	 */
	public void setConfigDivision(ArrayList<ConfigDivision> configDivision) {
		this.configDivision = configDivision;
	}

	/**
	 * @return the configDivision
	 */
	public ArrayList<ConfigDivision> getConfigDivision() {
		return configDivision;
	}

	/**
	 * @param updatedDescription
	 *            the updatedDescription to set
	 */
	public void setUpdatedDescription(String updatedDescription) {
		this.updatedDescription = updatedDescription;
	}

	/**
	 * @return the updatedDescription
	 */
	public String getUpdatedDescription() {
		return updatedDescription;
	}
	// End WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr
	// 2011
}
