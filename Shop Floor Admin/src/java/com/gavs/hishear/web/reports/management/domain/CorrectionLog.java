package com.gavs.hishear.web.reports.management.domain;

import java.util.Date;

public class CorrectionLog {

	private String moNumber;
	private String sequenceNumber;
	private String correctedBy;
	private Date CorrectedOn;
	private String stampNumber;
	private String employeeNumber;
	private String oldEmployeeNumber;
	private String activityCode;
	private String oldActivityCode;
	private Date logonDate;
	private Date oldLogonDate;
	private Date logOffDate;
	private Date oldLogOffDate;
	private String qtyCompleted;
	private String oldQtyCompleted;
	private String assetNumber;
	private String oldAssetNumber;
	private String completeStatusFlag;
	private String routerDate;
	private String newFlag;
	private String oprName;
	private String multipleCorrectionFlag;
	private String oldCompleteStatusFlag;

	public String getMoNumber() {
		return moNumber;
	}

	public void setMoNumber(String moNumber) {
		this.moNumber = moNumber;
	}

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getCorrectedBy() {
		return correctedBy;
	}

	public void setCorrectedBy(String correctedBy) {
		this.correctedBy = correctedBy;
	}

	public Date getCorrectedOn() {
		return CorrectedOn;
	}

	public void setCorrectedOn(Date CorrectedOn) {
		this.CorrectedOn = CorrectedOn;
	}

	public String getStampNumber() {
		return stampNumber;
	}

	public void setStampNumber(String stampNumber) {
		this.stampNumber = stampNumber;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getOldEmployeeNumber() {
		return oldEmployeeNumber;
	}

	public void setOldEmployeeNumber(String oldEmployeeNumber) {
		this.oldEmployeeNumber = oldEmployeeNumber;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getOldActivityCode() {
		return oldActivityCode;
	}

	public void setOldActivityCode(String oldActivityCode) {
		this.oldActivityCode = oldActivityCode;
	}

	public Date getLogonDate() {
		return logonDate;
	}

	public void setLogonDate(Date logonDate) {
		this.logonDate = logonDate;
	}

	public Date getOldLogonDate() {
		return oldLogonDate;
	}

	public void setOldLogonDate(Date oldLogonDate) {
		this.oldLogonDate = oldLogonDate;
	}

	public Date getLogOffDate() {
		return logOffDate;
	}

	public void setLogOffDate(Date logOffDate) {
		this.logOffDate = logOffDate;
	}

	public Date getOldLogOffDate() {
		return oldLogOffDate;
	}

	public void setOldLogOffDate(Date oldLogOffDate) {
		this.oldLogOffDate = oldLogOffDate;
	}

	public String getQtyCompleted() {
		return qtyCompleted;
	}

	public void setQtyCompleted(String qtyCompleted) {
		this.qtyCompleted = qtyCompleted;
	}

	public String getOldQtyCompleted() {
		return oldQtyCompleted;
	}

	public void setOldQtyCompleted(String oldQtyCompleted) {
		this.oldQtyCompleted = oldQtyCompleted;
	}

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	public String getOldAssetNumber() {
		return oldAssetNumber;
	}

	public void setOldAssetNumber(String oldAssetNumber) {
		this.oldAssetNumber = oldAssetNumber;
	}

	public String getCompleteStatusFlag() {
		return completeStatusFlag;
	}

	public void setCompleteStatusFlag(String completeStatusFlag) {
		this.completeStatusFlag = completeStatusFlag;
	}

	public String getRouterDate() {
		return routerDate;
	}

	public void setRouterDate(String routerDate) {
		this.routerDate = routerDate;
	}

	public String getNewFlag() {
		return newFlag;
	}

	public void setNewFlag(String newFlag) {
		this.newFlag = newFlag;
	}

	public String getMultipleCorrectionFlag() {
		return multipleCorrectionFlag;
	}

	public void setMultipleCorrectionFlag(String multipleCorrectionFlag) {
		this.multipleCorrectionFlag = multipleCorrectionFlag;
	}

	public String getOprName() {
		return oprName;
	}

	public void setOprName(String oprName) {
		this.oprName = oprName;
	}

	/**
	 * @return the oldCompleteStatusFlag
	 */
	public String getOldCompleteStatusFlag() {
		return oldCompleteStatusFlag;
	}

	/**
	 * @param oldCompleteStatusFlag
	 *            the oldCompleteStatusFlag to set
	 */
	public void setOldCompleteStatusFlag(String oldCompleteStatusFlag) {
		this.oldCompleteStatusFlag = oldCompleteStatusFlag;
	}
}
