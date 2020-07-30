package com.gavs.hishear.web.domain;

public class Asset {
	private String departmentNumber;
	private String departmentName;
	private String assetNumber;
	private String assetDescNumber;
	private String workCenterCode;
	private String workCenterDesc;
	private String quantityCaptureRequired;
	private String status;
	private String factory;

	/**
	 * @return the factory
	 */
	public String getFactory() {
		return factory;
	}

	/**
	 * @param factory
	 *            the factory to set
	 */
	public void setFactory(String factory) {
		this.factory = factory;
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

	public String getQuantityCaptureRequired() {
		return quantityCaptureRequired;
	}

	public void setQuantityCaptureRequired(String quantityCaptureRequired) {
		this.quantityCaptureRequired = quantityCaptureRequired;
	}

	/**
	 * @return the departmentNumber
	 */
	public String getDepartmentNumber() {
		return departmentNumber;
	}

	/**
	 * @param departmentNumber
	 *            the departmentNumber to set
	 */
	public void setDepartmentNumber(String departmentNumber) {
		this.departmentNumber = departmentNumber;
	}

	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName
	 *            the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return the assetNumber
	 */
	public String getAssetNumber() {
		return assetNumber;
	}

	/**
	 * @param assetNumber
	 *            the assetNumber to set
	 */
	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	/**
	 * @return the assetDescNumber
	 */
	public String getAssetDescNumber() {
		return assetDescNumber;
	}

	/**
	 * @param assetDescNumber
	 *            the assetDescNumber to set
	 */
	public void setAssetDescNumber(String assetDescNumber) {
		this.assetDescNumber = assetDescNumber;
	}

	/**
	 * @return the workCenterCode
	 */
	public String getWorkCenterCode() {
		return workCenterCode;
	}

	/**
	 * @param workCenterCode
	 *            the workCenterCode to set
	 */
	public void setWorkCenterCode(String workCenterCode) {
		this.workCenterCode = workCenterCode;
	}

	/**
	 * @return the workCenterDesc
	 */
	public String getWorkCenterDesc() {
		return workCenterDesc;
	}

	/**
	 * @param workCenterDesc
	 *            the workCenterDesc to set
	 */
	public void setWorkCenterDesc(String workCenterDesc) {
		this.workCenterDesc = workCenterDesc;
	}

}