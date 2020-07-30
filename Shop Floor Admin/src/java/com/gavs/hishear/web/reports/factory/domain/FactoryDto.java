// File:         FactoryDto.java
// Created:      Feb 23, 2011
// Author:       sundarrajanr
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.reports.factory.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Class FactoryDto.
 * 
 */
public class FactoryDto implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The from date. */
	private String fromDate;

	/** The to date. */
	private String toDate;

	/** The user name. */
	private String userName;

	/** The display name. */
	private String displayName;

	/** The queries. */
	private HashMap queries;

	/** The asset number. */
	private String assetNumber;

	/** The asset desc. */
	private String assetDesc;

	/** The part number. */
	private String partNumber;

	/** The mo qty. */
	private String moQty;

	/** The co qty. */
	private String coQty;

	/** The qty processed. */
	private String qtyProcessed;

	/** The qty rejected. */
	private String qtyRejected;

	/** The qty pending. */
	private String qtyPending;

	/** The no of assets. */
	private String noOfAssets;

	/** The pending job. */
	private String pendingJob;

	/** The no of operators. */
	private String noOfOperators;

	/** The no of pendinging job. */
	private String noOfPendingingJob;

	/** The no ofprocessing job. */
	private String noOfprocessingJob;

	/** The no of parts processed. */
	private String noOfPartsProcessed;

	/** The no of parts rejected. */
	private String noOfPartsRejected;

	/** The yield. */
	private String yield;

	/** The department id. */
	private String departmentId;

	/** The department. */
	private String department;

	/** The factory report. */
	private ArrayList factoryReport;

	/** The asset reports. */
	private ArrayList assetReports;

	/** The exceptional activity report. */
	private ArrayList exceptionalActivityReport;

	/** The arr shift time. */
	private ArrayList arrShiftTime;

	/** The activity log. */
	private ArrayList activityLog;

	/** The arr departments. */
	private ArrayList arrDepartments;

	/** The arr factory. */
	private ArrayList arrFactory;

	/** The shift start time. */
	private String shiftStartTime;

	/** The shift end time. */
	private String shiftEndTime;

	/** The shift. */
	private String shift;

	/** The factory. */
	private String factory;

	/** The factory asset pop up report. */
	private ArrayList factoryAssetPopUpReport;

	/** The mo number. */
	private String moNumber;

	/** The quantity. */
	private String quantity;

	/** The part description. */
	private String partDescription;

	/** The date queued. */
	private String dateQueued;

	/** The duration. */
	private String duration;

	/** The setup. */
	private String setup;

	/** The run. */
	private String run;

	/** The retool. */
	private String retool;

	/** The line number. */
	private String lineNumber;

	/** The total hours. */
	private String totalHours;

	/** The query. */
	private String query;

	/** The initial loading. */
	private String initialLoading;

	/** The date shift required. */
	private boolean dateShiftRequired;

	/** The rate. */
	private String rate;

	/** The PPH. */
	private String PPH;

	/** The PPH act. */
	private String PPHAct;

	/** The qty target. */
	private String qtyTarget;

	/** The qty act. */
	private String qtyAct;

	/** The act run hrs. */
	private String actRunHrs;

	/** The down hrs. */
	private String downHrs;

	/** The efficiency. */
	private String efficiency;

	/** The employee name. */
	private String employeeName;

	/** The report type. */
	private String reportType;

	/** The records found. */
	private int recordsFound;

	/** The report date. */
	private String reportDate;

	/**
	 * Gets the line number.
	 * 
	 * @return the line number
	 */
	public String getLineNumber() {
		return lineNumber;
	}

	/**
	 * Sets the line number.
	 * 
	 * @param lineNumber
	 *            the new line number
	 */
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * Gets the retool.
	 * 
	 * @return the retool
	 */
	public String getRetool() {
		return retool;
	}

	/**
	 * Sets the retool.
	 * 
	 * @param retool
	 *            the new retool
	 */
	public void setRetool(String retool) {
		this.retool = retool;
	}

	/**
	 * Gets the run.
	 * 
	 * @return the run
	 */
	public String getRun() {
		return run;
	}

	/**
	 * Sets the run.
	 * 
	 * @param run
	 *            the new run
	 */
	public void setRun(String run) {
		this.run = run;
	}

	/**
	 * Gets the setup.
	 * 
	 * @return the setup
	 */
	public String getSetup() {
		return setup;
	}

	/**
	 * Sets the setup.
	 * 
	 * @param setup
	 *            the new setup
	 */
	public void setSetup(String setup) {
		this.setup = setup;
	}

	/**
	 * Gets the mo number.
	 * 
	 * @return the mo number
	 */
	public String getMoNumber() {
		return moNumber;
	}

	/**
	 * Sets the mo number.
	 * 
	 * @param moNumber
	 *            the new mo number
	 */
	public void setMoNumber(String moNumber) {
		this.moNumber = moNumber;
	}

	/**
	 * Gets the quantity.
	 * 
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity.
	 * 
	 * @param quantity
	 *            the new quantity
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the arr departments.
	 * 
	 * @return the arrDepartments
	 */
	public ArrayList getArrDepartments() {
		return arrDepartments;
	}

	/**
	 * Sets the arr departments.
	 * 
	 * @param arrDepartments
	 *            the arrDepartments to set
	 */
	public void setArrDepartments(ArrayList arrDepartments) {
		this.arrDepartments = arrDepartments;
	}

	/**
	 * Gets the arr factory.
	 * 
	 * @return the arrFactory
	 */
	public ArrayList getArrFactory() {
		return arrFactory;
	}

	/**
	 * Sets the arr factory.
	 * 
	 * @param arrFactory
	 *            the arrFactory to set
	 */
	public void setArrFactory(ArrayList arrFactory) {
		this.arrFactory = arrFactory;
	}

	/**
	 * Gets the department id.
	 * 
	 * @return the departmentId
	 */
	public String getDepartmentId() {
		return departmentId;
	}

	/**
	 * Sets the department id.
	 * 
	 * @param departmentId
	 *            the departmentId to set
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * Gets the department.
	 * 
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * Sets the department.
	 * 
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * Gets the no of assets.
	 * 
	 * @return the noOfAssets
	 */
	public String getNoOfAssets() {
		return noOfAssets;
	}

	/**
	 * Sets the no of assets.
	 * 
	 * @param noOfAssets
	 *            the noOfAssets to set
	 */
	public void setNoOfAssets(String noOfAssets) {
		this.noOfAssets = noOfAssets;
	}

	/**
	 * Gets the pending job.
	 * 
	 * @return the pendingJob
	 */
	public String getPendingJob() {
		return pendingJob;
	}

	/**
	 * Sets the pending job.
	 * 
	 * @param pendingJob
	 *            the pendingJob to set
	 */
	public void setPendingJob(String pendingJob) {
		this.pendingJob = pendingJob;
	}

	/**
	 * Gets the no ofprocessing job.
	 * 
	 * @return the noOfprocessingJob
	 */
	public String getNoOfprocessingJob() {
		return noOfprocessingJob;
	}

	/**
	 * Sets the no ofprocessing job.
	 * 
	 * @param noOfprocessingJob
	 *            the noOfprocessingJob to set
	 */
	public void setNoOfprocessingJob(String noOfprocessingJob) {
		this.noOfprocessingJob = noOfprocessingJob;
	}

	/**
	 * Gets the no of parts processed.
	 * 
	 * @return the noOfPartsProcessed
	 */
	public String getNoOfPartsProcessed() {
		return noOfPartsProcessed;
	}

	/**
	 * Sets the no of parts processed.
	 * 
	 * @param noOfPartsProcessed
	 *            the noOfPartsProcessed to set
	 */
	public void setNoOfPartsProcessed(String noOfPartsProcessed) {
		this.noOfPartsProcessed = noOfPartsProcessed;
	}

	/**
	 * Gets the yield.
	 * 
	 * @return the yield
	 */
	public String getYield() {
		return yield;
	}

	/**
	 * Sets the yield.
	 * 
	 * @param yield
	 *            the yield to set
	 */
	public void setYield(String yield) {
		this.yield = yield;
	}

	/**
	 * Gets the no of parts rejected.
	 * 
	 * @return the asnoOfPartsRejectedsetReports
	 */
	public String getNoOfPartsRejected() {
		return noOfPartsRejected;
	}

	/**
	 * Sets the no of parts rejected.
	 * 
	 * @param assetReports
	 *            the new no of parts rejected
	 */
	public void setNoOfPartsRejected(String assetReports) {
		this.noOfPartsRejected = noOfPartsRejected;
	}

	/**
	 * Gets the asset reports.
	 * 
	 * @return the assetReports
	 */
	public ArrayList getAssetReports() {
		return assetReports;
	}

	/**
	 * Sets the asset reports.
	 * 
	 * @param assetReports
	 *            the assetReports to set
	 */
	public void setAssetReports(ArrayList assetReports) {
		this.assetReports = assetReports;
	}

	/**
	 * Gets the from date.
	 * 
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * Sets the from date.
	 * 
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * Gets the mo qty.
	 * 
	 * @return the moQty
	 */
	public String getMoQty() {
		return moQty;
	}

	/**
	 * Sets the co qty.
	 * 
	 * @param coQty
	 *            the coQty to set
	 */
	public void setCoQty(String coQty) {
		this.moQty = coQty;
	}

	/**
	 * Gets the co qty.
	 * 
	 * @return the coQty
	 */
	public String getCoQty() {
		return coQty;
	}

	/**
	 * Sets the qty rejected.
	 * 
	 * @param qtyRejected
	 *            the qtyRejected to set
	 */
	public void setQtyRejected(String qtyRejected) {
		this.qtyRejected = qtyRejected;
	}

	/**
	 * Gets the qty rejected.
	 * 
	 * @return the qtyRejected
	 */
	public String getQtyRejected() {
		return qtyRejected;
	}

	/**
	 * Sets the qty processed.
	 * 
	 * @param qtyProcessed
	 *            the qtyProcessed to set
	 */
	public void setQtyProcessed(String qtyProcessed) {
		this.qtyProcessed = qtyProcessed;
	}

	/**
	 * Gets the qty processed.
	 * 
	 * @return the moQty
	 */
	public String getQtyProcessed() {
		return qtyProcessed;
	}

	/**
	 * Sets the mo qty.
	 * 
	 * @param moQty
	 *            the moQty to set
	 */
	public void setMoQty(String moQty) {
		this.moQty = moQty;
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
	 * Gets the asset desc.
	 * 
	 * @return the assetDesc
	 */
	public String getAssetDesc() {
		return assetDesc;
	}

	/**
	 * Sets the asset desc.
	 * 
	 * @param assetDesc
	 *            the assetDesc to set
	 */
	public void setAssetDesc(String assetDesc) {
		this.assetDesc = assetDesc;
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
	 * Gets the queries.
	 * 
	 * @return the queries
	 */
	public HashMap getQueries() {
		return queries;
	}

	/**
	 * Sets the queries.
	 * 
	 * @param queries
	 *            the queries to set
	 */
	public void setQueries(HashMap queries) {
		this.queries = queries;
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
	 * Gets the to date.
	 * 
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * Sets the to date.
	 * 
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * Sets the shift.
	 * 
	 * @param shift
	 *            the shift to set
	 */
	public void setShift(String shift) {
		this.shift = shift;
	}

	/**
	 * Gets the shift.
	 * 
	 * @return the shift
	 */
	public String getShift() {
		return shift;
	}

	/**
	 * Sets the shift end time.
	 * 
	 * @param shiftEndTime
	 *            to set
	 */
	public void setShiftEndTime(String shiftEndTime) {
		this.shiftEndTime = shiftEndTime;
	}

	/**
	 * Gets the shift end time.
	 * 
	 * @return the shiftEndTime
	 */
	public String getShiftEndTime() {
		return shiftEndTime;
	}

	/**
	 * Sets the shift start time.
	 * 
	 * @param shiftStartTime
	 *            to set
	 */
	public void setShiftStartTime(String shiftStartTime) {
		this.shiftStartTime = shiftStartTime;
	}

	/**
	 * Gets the shift start time.
	 * 
	 * @return the shiftStartTime
	 */
	public String getShiftStartTime() {
		return shiftStartTime;
	}

	/**
	 * Gets the arr shift time.
	 * 
	 * @return the arrShiftTime
	 */
	public ArrayList getArrShiftTime() {
		return arrShiftTime;
	}

	/**
	 * Sets the arr shift time.
	 * 
	 * @param arrShiftTime
	 *            the arrShiftTime to set
	 */
	public void setArrShiftTime(ArrayList arrShiftTime) {
		this.arrShiftTime = arrShiftTime;
	}

	/**
	 * Gets the factory.
	 * 
	 * @return the factory
	 */
	public String getFactory() {
		return factory;
	}

	/**
	 * Sets the factory.
	 * 
	 * @param factory
	 *            the new factory
	 */
	public void setFactory(String factory) {
		this.factory = factory;
	}

	/**
	 * Sets the factory asset pop up report.
	 * 
	 * @param report
	 *            the new factory asset pop up report
	 */
	public void setFactoryAssetPopUpReport(ArrayList report) {
		this.factoryAssetPopUpReport = report;

	}

	/**
	 * Gets the factory asset pop up report.
	 * 
	 * @return the factory asset pop up report
	 */
	public ArrayList getFactoryAssetPopUpReport() {
		return factoryAssetPopUpReport;
	}

	/**
	 * Gets the part description.
	 * 
	 * @return the part description
	 */
	public String getPartDescription() {
		return partDescription;
	}

	/**
	 * Sets the part description.
	 * 
	 * @param partDescription
	 *            the new part description
	 */
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	/**
	 * Gets the qty pending.
	 * 
	 * @return the qty pending
	 */
	public String getQtyPending() {
		return qtyPending;
	}

	/**
	 * Sets the qty pending.
	 * 
	 * @param qtyPending
	 *            the new qty pending
	 */
	public void setQtyPending(String qtyPending) {
		this.qtyPending = qtyPending;
	}

	/**
	 * Gets the date queued.
	 * 
	 * @return the date queued
	 */
	public String getDateQueued() {
		return dateQueued;
	}

	/**
	 * Sets the date queued.
	 * 
	 * @param dateQueued
	 *            the new date queued
	 */
	public void setDateQueued(String dateQueued) {
		this.dateQueued = dateQueued;
	}

	/**
	 * Gets the duration.
	 * 
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * Sets the duration.
	 * 
	 * @param duration
	 *            the new duration
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * Gets the total hours.
	 * 
	 * @return the total hours
	 */
	public String getTotalHours() {
		return totalHours;
	}

	/**
	 * Sets the total hours.
	 * 
	 * @param totalHours
	 *            the new total hours
	 */
	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	/**
	 * Gets the query.
	 * 
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Sets the query.
	 * 
	 * @param query
	 *            the new query
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * Gets the no of operators.
	 * 
	 * @return the no of operators
	 */
	public String getNoOfOperators() {
		return noOfOperators;
	}

	/**
	 * Sets the no of operators.
	 * 
	 * @param noOfOperators
	 *            the new no of operators
	 */
	public void setNoOfOperators(String noOfOperators) {
		this.noOfOperators = noOfOperators;
	}

	/**
	 * Gets the no of pendinging job.
	 * 
	 * @return the no of pendinging job
	 */
	public String getNoOfPendingingJob() {
		return noOfPendingingJob;
	}

	/**
	 * Sets the no of pendinging job.
	 * 
	 * @param noOfPendingingJob
	 *            the new no of pendinging job
	 */
	public void setNoOfPendingingJob(String noOfPendingingJob) {
		this.noOfPendingingJob = noOfPendingingJob;
	}

	/**
	 * Gets the initial loading.
	 * 
	 * @return the initial loading
	 */
	public String getInitialLoading() {
		return initialLoading;
	}

	/**
	 * Sets the initial loading.
	 * 
	 * @param initialLoading
	 *            the new initial loading
	 */
	public void setInitialLoading(String initialLoading) {
		this.initialLoading = initialLoading;
	}

	/**
	 * Checks if is date shift required.
	 * 
	 * @return true, if is date shift required
	 */
	public boolean isDateShiftRequired() {
		return dateShiftRequired;
	}

	/**
	 * Sets the date shift required.
	 * 
	 * @param dateShiftRequired
	 *            the new date shift required
	 */
	public void setDateShiftRequired(boolean dateShiftRequired) {
		this.dateShiftRequired = dateShiftRequired;
	}

	/**
	 * Gets the rate.
	 * 
	 * @return the rate
	 */
	public String getRate() {
		return rate;
	}

	/**
	 * Sets the rate.
	 * 
	 * @param rate
	 *            the new rate
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}

	/**
	 * Gets the pPH.
	 * 
	 * @return the pPH
	 */
	public String getPPH() {
		return PPH;
	}

	/**
	 * Sets the pPH.
	 * 
	 * @param pph
	 *            the new pPH
	 */
	public void setPPH(String pph) {
		PPH = pph;
	}

	/**
	 * Gets the pPH act.
	 * 
	 * @return the pPH act
	 */
	public String getPPHAct() {
		return PPHAct;
	}

	/**
	 * Sets the pPH act.
	 * 
	 * @param act
	 *            the new pPH act
	 */
	public void setPPHAct(String act) {
		PPHAct = act;
	}

	/**
	 * Gets the qty target.
	 * 
	 * @return the qty target
	 */
	public String getQtyTarget() {
		return qtyTarget;
	}

	/**
	 * Sets the qty target.
	 * 
	 * @param qtyTarget
	 *            the new qty target
	 */
	public void setQtyTarget(String qtyTarget) {
		this.qtyTarget = qtyTarget;
	}

	/**
	 * Gets the qty act.
	 * 
	 * @return the qty act
	 */
	public String getQtyAct() {
		return qtyAct;
	}

	/**
	 * Sets the qty act.
	 * 
	 * @param qtyAct
	 *            the new qty act
	 */
	public void setQtyAct(String qtyAct) {
		this.qtyAct = qtyAct;
	}

	/**
	 * Gets the act run hrs.
	 * 
	 * @return the act run hrs
	 */
	public String getActRunHrs() {
		return actRunHrs;
	}

	/**
	 * Sets the act run hrs.
	 * 
	 * @param actRunHrs
	 *            the new act run hrs
	 */
	public void setActRunHrs(String actRunHrs) {
		this.actRunHrs = actRunHrs;
	}

	/**
	 * Gets the down hrs.
	 * 
	 * @return the down hrs
	 */
	public String getDownHrs() {
		return downHrs;
	}

	/**
	 * Sets the down hrs.
	 * 
	 * @param downHrs
	 *            the new down hrs
	 */
	public void setDownHrs(String downHrs) {
		this.downHrs = downHrs;
	}

	/**
	 * Gets the efficiency.
	 * 
	 * @return the efficiency
	 */
	public String getEfficiency() {
		return efficiency;
	}

	/**
	 * Sets the efficiency.
	 * 
	 * @param efficiency
	 *            the new efficiency
	 */
	public void setEfficiency(String efficiency) {
		this.efficiency = efficiency;
	}

	/**
	 * Gets the factory report.
	 * 
	 * @return the factory report
	 */
	public ArrayList getFactoryReport() {
		return factoryReport;
	}

	/**
	 * Sets the factory report.
	 * 
	 * @param factoryReport
	 *            the new factory report
	 */
	public void setFactoryReport(ArrayList factoryReport) {
		this.factoryReport = factoryReport;
	}

	/**
	 * Gets the employee name.
	 * 
	 * @return the employeeName
	 */
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * Sets the employee name.
	 * 
	 * @param employeeName
	 *            the employeeName to set
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * Gets the report type.
	 * 
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}

	/**
	 * Sets the report type.
	 * 
	 * @param reportType
	 *            the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	/**
	 * Gets the records found.
	 * 
	 * @return the recordsFound
	 */
	public int getRecordsFound() {
		return recordsFound;
	}

	/**
	 * Sets the records found.
	 * 
	 * @param recordsFound
	 *            the recordsFound to set
	 */
	public void setRecordsFound(int recordsFound) {
		this.recordsFound = recordsFound;
	}

	/**
	 * Gets the report date.
	 * 
	 * @return the report date
	 */
	public String getReportDate() {
		return reportDate;
	}

	/**
	 * Sets the report date.
	 * 
	 * @param reportDate
	 *            the new report date
	 */
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
}