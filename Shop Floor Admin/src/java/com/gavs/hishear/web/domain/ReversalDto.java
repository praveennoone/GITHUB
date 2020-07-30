package com.gavs.hishear.web.domain;

import java.util.List;

public class ReversalDto {
	private String action;
	private String message;
	private boolean detailsExist;
	private boolean reversed;
	private String userName;
	private String displayName;
	private String moNumber;
	private String operationNumber;
	private String reportedQuantity;
	private String workCenter;
	private String costCenter;
	private String poNumber;
	private String poLineNumber;
	private String poSubLineNumber;
	private String poLineSuffix;
	private String poStatus;
	private String supplier;
	private String warehouse;
	private String itemNumber;
	private String planningArea;

	private boolean inspectionForOutsideOperation;
	private String outsideOperationNumber;
	private double priceTimeQty;
	private int workCenterCapacity;
	private List<Sequence> jobs;

	public boolean isInspectionForOutsideOperation() {
		return inspectionForOutsideOperation;
	}

	public void setInspectionForOutsideOperation(
			boolean inspectionForOutsideOperation) {
		this.inspectionForOutsideOperation = inspectionForOutsideOperation;
	}

	public boolean isReversed() {
		return reversed;
	}

	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPoStatus() {
		return poStatus;
	}

	public void setPoStatus(String poStatus) {
		this.poStatus = poStatus;
	}

	public String getPoLineSuffix() {
		return poLineSuffix;
	}

	public void setPoLineSuffix(String poLineSuffix) {
		this.poLineSuffix = poLineSuffix;
	}

	public String getPoSubLineNumber() {
		return poSubLineNumber;
	}

	public void setPoSubLineNumber(String poSubLineNumber) {
		this.poSubLineNumber = poSubLineNumber;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public boolean isDetailsExist() {
		return detailsExist;
	}

	public void setDetailsExist(boolean detailsExist) {
		this.detailsExist = detailsExist;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	// public String getEmployeeName() {
	// return employeeName;
	// }
	// public void setEmployeeName(String employeeName) {
	// this.employeeName = employeeName;
	// }
	public String getPlanningArea() {
		return planningArea;
	}

	public void setPlanningArea(String planningArea) {
		this.planningArea = planningArea;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getMoNumber() {
		return moNumber;
	}

	public void setMoNumber(String moNumber) {
		this.moNumber = moNumber;
	}

	public String getOperationNumber() {
		return operationNumber;
	}

	public void setOperationNumber(String operationNumber) {
		this.operationNumber = operationNumber;
	}

	public String getReportedQuantity() {
		return reportedQuantity;
	}

	public void setReportedQuantity(String reportedQuantity) {
		this.reportedQuantity = reportedQuantity;
	}

	public String getWorkCenter() {
		return workCenter;
	}

	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getPoLineNumber() {
		return poLineNumber;
	}

	public void setPoLineNumber(String poLineNumber) {
		this.poLineNumber = poLineNumber;
	}

	public void setOutsideOperationNumber(String outsideOperationNumber) {
		this.outsideOperationNumber = outsideOperationNumber;
	}

	public String getOutsideOperationNumber() {
		return outsideOperationNumber;
	}

	public void setPriceTimeQty(double priceTimeQty) {
		this.priceTimeQty = priceTimeQty;
	}

	public double getPriceTimeQty() {
		return priceTimeQty;
	}

	public void setWorkCenterCapacity(int workCenterCapacity) {
		this.workCenterCapacity = workCenterCapacity;
	}

	public int getWorkCenterCapacity() {
		return workCenterCapacity;
	}

	public void setJobs(List<Sequence> jobs) {
		this.jobs = jobs;
	}

	public List<Sequence> getJobs() {
		return jobs;
	}

}
