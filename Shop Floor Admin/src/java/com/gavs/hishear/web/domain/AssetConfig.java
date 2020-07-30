/**
 * AssetConfig.java
 */
package com.gavs.hishear.web.domain;

import org.springframework.web.util.HtmlUtils;

/**
 * @author Ambrish_v
 * 
 */
// Begin WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr 2011
public class AssetConfig {

	/** Work Center Code. */
	private String workCenterCode;

	/** Facility. */
	private String facility;

	/** Description. */
	private String description;

	/** Asset Number. */
	private String assetNumber;

	/** Status. */
	private String status;

	/** Updated Asset Number. */
	private String updatedAssetNumber;

	/** Updated Description. */
	private String updatedDescription;

	/** Updated Status. */
	private String updatedStatus;

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
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = HtmlUtils.htmlEscape(description);
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
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

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param updatedAssetNumber
	 *            the updatedAssetNumber to set
	 */
	public void setUpdatedAssetNumber(String updatedAssetNumber) {
		this.updatedAssetNumber = updatedAssetNumber;
	}

	/**
	 * @return the updatedAssetNumber
	 */
	public String getUpdatedAssetNumber() {
		return updatedAssetNumber;
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

	/**
	 * @param updatedStatus
	 *            the updatedStatus to set
	 */
	public void setUpdatedStatus(String updatedStatus) {
		this.updatedStatus = updatedStatus;
	}

	/**
	 * @return the updatedStatus
	 */
	public String getUpdatedStatus() {
		return updatedStatus;
	}
	// End WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr
	// 2011
}
