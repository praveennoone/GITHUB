/**
 * 
 */
package com.gavs.hishear.web.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author rahjeshd
 * 
 */
public class Sequence {
	private String userName;
	private String displayName;
	private String partNumber;
	private String partDesc;
	private double orderQty;
	private String moNumber;
	private String lineNumber;
	private String sequence;
	private String sequenceDescription;
	private boolean inputRequired;
	private List sequences;
	private ArrayList moLines;
	private String query;
	private String workCenterCode;
	private String workCenterDesc;
	private String activity;
	private double qtyCompleted;
	private Date completeddate;
	private String moLine;
	private String criteria;
	private String setupStatus;
	private String runStatus;
	private Date requiredDate;
	private String employeeName;
	private String assetNumber;
	private String assetDesc;
	private Date logonDate;
	private Date logoffDate;
	// private Date lastUpdatedDate;
	// private Date createdDate;
	private ArrayList sequenceDetails;
	private String activityCode;
	private String department;
	private double manufacturedQty;
	private String emptyString = "*********";
	// This variable is used for WC report. This is used to store the report
	// type.
	private String type;

	// Added for Pick Seq screen
	private String status;
	private boolean completeable;
	private String operation;
	private String jobNumber;
	private String jobActivityNumber;

	private String employeeID;
	private String message;
	private String errorMessage;
	private boolean addNewJob;

	private double curSeqQtyCompleted;
	private double pickQty;

	private double previousSeqQty;
	private boolean errorOccured;
	private String comments;

	private int reasonCode;
	private String reasonDesc;
	private ArrayList reasons;

	private int pageRefreshValue;

	private String originalAssetNumber;
	private double originalQtyCompleted;

	private String qtyComments;
	private String assetComments;
	private int qtyReasonCode;
	private int assetReasonCode;

	private boolean pickSuccess;

	// M3 Variables
	private String facility;
	private String moFacility;
	private String itemFacility;
	private int morvQuantity;
	private String employeeNumber;
	private String oldSequenceStatus;
	private int workCenterCapacity;

	private double priceTimeQty;

	public String getMoFacility() {
		return moFacility;
	}

	public void setMoFacility(String moFacility) {
		this.moFacility = moFacility;
	}

	public String getItemFacility() {
		return itemFacility;
	}

	public void setItemFacility(String itemFacility) {
		this.itemFacility = itemFacility;
	}

	private String completionFlag;
	private String UOM;
	private String company;
	private String nextSequenceNumber;
	private boolean isReportedToM3;
	private String planningArea;

	// Correction Interface variables
	private Object selectedSequence;
	private Object modifiedSequence;
	private boolean isModified;

	private double machineSetupTime;
	private double machineRunTime;
	private double labourSetupTime;
	private double labourRunTime;
	private double minsProcessed;
	private String selectedRowNo;
	private boolean validLastSequence;
	private String activityStatus;
	private Date approveDate;
	private String m3Status;

	private String sequenceStatus;

	private String productNumber;

	private boolean isReportWeight;
	private double itemweight;

	private String modifiedWeight;
	private double setupTime;
	private double runTime;

	private int oldCompletedQuantity;

	private String usedLabourRunTime;

	// added for Correction interface Modify MO
	private ArrayList<SequenceActivity> seqActivityDetails;
	private Date completedDate;
	private HashMap<String, String> employeeMap;
	private HashMap<String, String> assetMap;
	private SequenceActivity selectedSeqActivity;
	private ArrayList<Asset> assetsListForWC;
	private int completedQuantity;

	private int jobNum;
	private boolean isFirstSequence;

	private Date kronosPunchIn;
	private Date kronosPunchOut;

	private String costCenter;

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public ArrayList<SequenceActivity> getSeqActivityDetails() {
		return seqActivityDetails;
	}

	public void setSeqActivityDetails(
			ArrayList<SequenceActivity> seqActivityDetails) {
		this.seqActivityDetails = seqActivityDetails;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	/*
	 * added by Naveen to have a facility in Current order details interface
	 * based on divison Facility Based on Division
	 */
	private String userFacility;

	public String getUserFacility() {
		return userFacility;
	}

	public void setUserFacility(String userFacility) {
		this.userFacility = userFacility;
	}

	private ArrayList<Facility> divisionFacilities;

	/**
	 * @return the facilties based on division
	 */
	public ArrayList<Facility> getDivisionFacilities() {
		return divisionFacilities;
	}

	/**
	 * @param divisionFacilities
	 *            the facilities to set
	 */
	public void setDivisionFacilities(ArrayList<Facility> divisionFacilities) {
		this.divisionFacilities = divisionFacilities;
	}

	/**
	 * @return the selectedSequence
	 */
	public Object getSelectedSequence() {
		return selectedSequence;
	}

	/**
	 * @param selectedSequence
	 *            the selectedSequence to set
	 */
	public void setSelectedSequence(Object selectedSequence) {
		this.selectedSequence = selectedSequence;
	}

	/**
	 * @return the modifiedSequence
	 */
	public Object getModifiedSequence() {
		return modifiedSequence;
	}

	/**
	 * @param modifiedSequence
	 *            the modifiedSequence to set
	 */
	public void setModifiedSequence(Object modifiedSequence) {
		this.modifiedSequence = modifiedSequence;
	}

	/**
	 * @return the isModified
	 */
	public boolean isModified() {
		return isModified;
	}

	/**
	 * @param isModified
	 *            the isModified to set
	 */
	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}

	/*
	 * public Date getLastUpdatedDate() { return lastUpdatedDate; } public void
	 * setLastUpdatedDate(Date lastUpdatedDate) {
	 * 
	 * this.lastUpdatedDate = lastUpdatedDate; } public Date getCreatedDate() {
	 * return createdDate; } public void setCreatedDate(Date createdDate) {
	 * this.createdDate = createdDate; }
	 */

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the uOM
	 */
	public String getUOM() {
		return UOM;
	}

	/**
	 * @param uom
	 *            the uOM to set
	 */
	public void setUOM(String uom) {
		UOM = uom;
	}

	/**
	 * @return the facility
	 */
	public String getFacility() {
		return facility;
	}

	/**
	 * @param facility
	 *            the facility to set
	 */
	public void setFacility(String facility) {
		this.facility = facility;
	}

	/**
	 * @return the completionFlag
	 */
	public String getCompletionFlag() {
		return completionFlag;
	}

	/**
	 * @param completionFlag
	 *            the completionFlag to set
	 */
	public void setCompletionFlag(String completionFlag) {
		this.completionFlag = completionFlag;
	}

	/**
	 * @return the pickSuccess
	 */
	public boolean isPickSuccess() {
		return pickSuccess;
	}

	/**
	 * @param pickSuccess
	 *            the pickSuccess to set
	 */
	public void setPickSuccess(boolean pickSuccess) {
		this.pickSuccess = pickSuccess;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public boolean isCompleteable() {
		return completeable;
	}

	public void setCompleteable(boolean completeable) {
		System.out
				.println("set---------------------------------------------------");
		this.completeable = completeable;
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
	 * @return the moNumber
	 */
	public String getMoNumber() {
		return moNumber;
	}

	/**
	 * @param moNumber
	 *            the moNumber to set
	 */
	public void setMoNumber(String moNumber) {
		this.moNumber = moNumber;
	}

	/**
	 * @return the lineNumber
	 */
	public String getLineNumber() {
		return lineNumber;
	}

	/**
	 * @param lineNumber
	 *            the lineNumber to set
	 */
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @return the sequence
	 */
	public String getSequence() {
		return sequence;
	}

	/**
	 * @param sequence
	 *            the sequence to set
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return the sequenceDescription
	 */
	public String getSequenceDescription() {
		return sequenceDescription;
	}

	/**
	 * @param sequenceDescription
	 *            the sequenceDescription to set
	 */
	public void setSequenceDescription(String sequenceDescription) {
		this.sequenceDescription = sequenceDescription;
	}

	/**
	 * @return the inputRequired
	 */
	public boolean isInputRequired() {
		return inputRequired;
	}

	/**
	 * @param inputRequired
	 *            the inputRequired to set
	 */
	public void setInputRequired(boolean inputRequired) {
		this.inputRequired = inputRequired;
	}

	/**
	 * @return the sequences
	 */
	public List getSequences() {
		return sequences;
	}

	/**
	 * @param sequences
	 *            the sequences to set
	 */
	public void setSequences(List sequences) {
		this.sequences = sequences;
	}

	/**
	 * @return the manufacturedQty
	 */
	public double getManufacturedQty() {
		return manufacturedQty;
	}

	/**
	 * @param manufacturedQty
	 *            the manufacturedQty to set
	 */
	public void setManufacturedQty(double manufacturedQty) {
		this.manufacturedQty = manufacturedQty;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query
	 *            the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return the partNumber
	 */
	public String getPartNumber() {
		return partNumber;
	}

	/**
	 * @param partNumber
	 *            the partNumber to set
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
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
	 * @return the moLine
	 */
	public String getMoLine() {
		return moLine;
	}

	/**
	 * @param moLine
	 *            the moLine to set
	 */
	public void setMoLine(String moLine) {
		this.moLine = moLine;
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

	/**
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param activity
	 *            the activity to set
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}

	/**
	 * @return the qtyCompleted
	 */
	public double getQtyCompleted() {
		return qtyCompleted;
	}

	/**
	 * @param qtyCompleted
	 *            the qtyCompleted to set
	 */
	public void setQtyCompleted(double qtyCompleted) {
		this.qtyCompleted = qtyCompleted;
	}

	/**
	 * @return the completeddate
	 */
	public Date getCompleteddate() {
		return completeddate;
	}

	/**
	 * @param completeddate
	 *            the completeddate to set
	 */
	public void setCompleteddate(Date completeddate) {
		this.completeddate = completeddate;
	}

	/**
	 * @return the criteria
	 */
	public String getCriteria() {
		return criteria;
	}

	/**
	 * @param criteria
	 *            the criteria to set
	 */
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	/**
	 * @return the moLines
	 */
	public ArrayList getMoLines() {
		return moLines;
	}

	/**
	 * @param moLines
	 *            the moLines to set
	 */
	public void setMoLines(ArrayList moLines) {
		this.moLines = moLines;
	}

	/**
	 * @return the setupStatus
	 */
	public String getSetupStatus() {
		return setupStatus;
	}

	/**
	 * @param setupStatus
	 *            the setupStatus to set
	 */
	public void setSetupStatus(String setupStatus) {
		this.setupStatus = setupStatus;
	}

	/**
	 * @return the runStatus
	 */
	public String getRunStatus() {
		return runStatus;
	}

	/**
	 * @param runStatus
	 *            the runStatus to set
	 */
	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}

	/**
	 * 
	 * @return
	 */
	public String getEmptyString() {
		return emptyString;
	}

	/**
	 * 
	 * @param emptyString
	 */
	public void setEmptyString(String emptyString) {
		this.emptyString = emptyString;
	}

	/**
	 * @return the requiredDate
	 */
	public Date getRequiredDate() {
		return requiredDate;
	}

	/**
	 * @param requiredDate
	 *            the requiredDate to set
	 */
	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}

	/**
	 * @return the partDesc
	 */
	public String getPartDesc() {
		return partDesc;
	}

	/**
	 * @param partDesc
	 *            the partDesc to set
	 */
	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

	/**
	 * @return the orderQty
	 */
	public double getOrderQty() {
		return orderQty;
	}

	/**
	 * @param orderQty
	 *            the orderQty to set
	 */
	public void setOrderQty(double orderQty) {
		this.orderQty = orderQty;
	}

	/**
	 * @return the employeeName
	 */
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * @param employeeName
	 *            the employeeName to set
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
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
	 * @return the assetDesc
	 */
	public String getAssetDesc() {
		return assetDesc;
	}

	/**
	 * @param assetDesc
	 *            the assetDesc to set
	 */
	public void setAssetDesc(String assetDesc) {
		this.assetDesc = assetDesc;
	}

	/**
	 * @return the logonDate
	 */
	public Date getLogonDate() {
		return logonDate;
	}

	/**
	 * @param logonDate
	 *            the logonDate to set
	 */
	public void setLogonDate(Date logonDate) {
		this.logonDate = logonDate;
	}

	/**
	 * @return the logoffDate
	 */
	public Date getLogoffDate() {
		return logoffDate;
	}

	/**
	 * @param logoffDate
	 *            the logoffDate to set
	 */
	public void setLogoffDate(Date logoffDate) {
		this.logoffDate = logoffDate;
	}

	/**
	 * @return the sequenceDetails
	 */
	public ArrayList getSequenceDetails() {
		return sequenceDetails;
	}

	/**
	 * @param sequenceDetails
	 *            the sequenceDetails to set
	 */
	public void setSequenceDetails(ArrayList sequenceDetails) {
		this.sequenceDetails = sequenceDetails;
	}

	/**
	 * @return the activityCode
	 */
	public String getActivityCode() {
		return activityCode;
	}

	/**
	 * @param activityCode
	 *            the activityCode to set
	 */
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public boolean isAddNewJob() {
		return addNewJob;
	}

	public void setAddNewJob(boolean addNewJob) {
		this.addNewJob = addNewJob;
	}

	/**
	 * @return the curSeqQtyCompleted
	 */
	public double getCurSeqQtyCompleted() {
		return curSeqQtyCompleted;
	}

	/**
	 * @param curSeqQtyCompleted
	 *            the curSeqQtyCompleted to set
	 */
	public void setCurSeqQtyCompleted(double curSeqQtyCompleted) {
		this.curSeqQtyCompleted = curSeqQtyCompleted;
	}

	public double getPickQty() {
		return pickQty;
	}

	public void setPickQty(double pickQty) {
		this.pickQty = pickQty;
	}

	public double getPreviousSeqQty() {
		return previousSeqQty;
	}

	public void setPreviousSeqQty(double previousSeqQty) {
		this.previousSeqQty = previousSeqQty;
	}

	public boolean isErrorOccured() {
		return errorOccured;
	}

	public void setErrorOccured(boolean errorOccured) {
		this.errorOccured = errorOccured;
	}

	/**
	 * @return the reasonCode
	 */
	public int getReasonCode() {
		return reasonCode;
	}

	/**
	 * @param reasonCode
	 *            the reasonCode to set
	 */
	public void setReasonCode(int reasonCode) {
		this.reasonCode = reasonCode;
	}

	/**
	 * @return the reasonDesc
	 */
	public String getReasonDesc() {
		return reasonDesc;
	}

	/**
	 * @param reasonDesc
	 *            the reasonDesc to set
	 */
	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}

	/**
	 * @return the reasons
	 */
	public ArrayList getReasons() {
		return reasons;
	}

	/**
	 * @param reasons
	 *            the reasons to set
	 */
	public void setReasons(ArrayList reasons) {
		this.reasons = reasons;
	}

	/**
	 * @return the pageRefreshValue
	 */
	public int getPageRefreshValue() {
		return pageRefreshValue;
	}

	/**
	 * @param pageRefreshValue
	 *            the pageRefreshValue to set
	 */
	public void setPageRefreshValue(int pageRefreshValue) {
		this.pageRefreshValue = pageRefreshValue;
	}

	public String getJobActivityNumber() {
		return jobActivityNumber;
	}

	public void setJobActivityNumber(String jobActivityNumber) {
		this.jobActivityNumber = jobActivityNumber;
	}

	public String getOriginalAssetNumber() {
		return originalAssetNumber;
	}

	public void setOriginalAssetNumber(String originalAssetNumber) {
		this.originalAssetNumber = originalAssetNumber;
	}

	public double getOriginalQtyCompleted() {
		return originalQtyCompleted;
	}

	public void setOriginalQtyCompleted(double originalQtyCompleted) {
		this.originalQtyCompleted = originalQtyCompleted;
	}

	public String getQtyComments() {
		return qtyComments;
	}

	public void setQtyComments(String qtyComments) {
		this.qtyComments = qtyComments;
	}

	public String getAssetComments() {
		return assetComments;
	}

	public void setAssetComments(String assetComments) {
		this.assetComments = assetComments;
	}

	public int getQtyReasonCode() {
		return qtyReasonCode;
	}

	public void setQtyReasonCode(int qtyReasonCode) {
		this.qtyReasonCode = qtyReasonCode;
	}

	public int getAssetReasonCode() {
		return assetReasonCode;
	}

	public void setAssetReasonCode(int assetReasonCode) {
		this.assetReasonCode = assetReasonCode;
	}

	/**
	 * @return the nextSequenceNumber
	 */
	public String getNextSequenceNumber() {
		return nextSequenceNumber;
	}

	/**
	 * @param nextSequenceNumber
	 *            the nextSequenceNumber to set
	 */
	public void setNextSequenceNumber(String nextSequenceNumber) {
		this.nextSequenceNumber = nextSequenceNumber;
	}

	/**
	 * @return the isReportedToM3
	 */
	public boolean isReportedToM3() {
		return isReportedToM3;
	}

	/**
	 * @param isReportedToM3
	 *            the isReportedToM3 to set
	 */
	public void setReportedToM3(boolean isReportedToM3) {
		this.isReportedToM3 = isReportedToM3;
	}

	/**
	 * @return the planningArea
	 */
	public String getPlanningArea() {
		return planningArea;
	}

	/**
	 * @param planningArea
	 *            the planningArea to set
	 */
	public void setPlanningArea(String planningArea) {
		this.planningArea = planningArea;
	}

	/**
	 * @return the machineSetupTime
	 */
	public double getMachineSetupTime() {
		return machineSetupTime;
	}

	/**
	 * @param machineSetupTime
	 *            the machineSetupTime to set
	 */
	public void setMachineSetupTime(double machineSetupTime) {
		this.machineSetupTime = machineSetupTime;
	}

	/**
	 * @return the machineRunTime
	 */
	public double getMachineRunTime() {
		return machineRunTime;
	}

	/**
	 * @param machineRunTime
	 *            the machineRunTime to set
	 */
	public void setMachineRunTime(double machineRunTime) {
		this.machineRunTime = machineRunTime;
	}

	/**
	 * @return the labourSetupTime
	 */
	public double getLabourSetupTime() {
		return labourSetupTime;
	}

	/**
	 * @param labourSetupTime
	 *            the labourSetupTime to set
	 */
	public void setLabourSetupTime(double labourSetupTime) {
		this.labourSetupTime = labourSetupTime;
	}

	/**
	 * @return the labourRunTime
	 */
	public double getLabourRunTime() {
		return labourRunTime;
	}

	/**
	 * @param labourRunTime
	 *            the labourRunTime to set
	 */
	public void setLabourRunTime(double labourRunTime) {
		this.labourRunTime = labourRunTime;
	}

	/**
	 * @return the minsProcessed
	 */
	public double getMinsProcessed() {
		return minsProcessed;
	}

	/**
	 * @param minsProcessed
	 *            the minsProcessed to set
	 */
	public void setMinsProcessed(double minsProcessed) {
		this.minsProcessed = minsProcessed;
	}

	/**
	 * @return the selectedRowNo
	 */
	public String getSelectedRowNo() {
		return selectedRowNo;
	}

	/**
	 * @param selectedRowNo
	 *            the selectedRowNo to set
	 */
	public void setSelectedRowNo(String selectedRowNo) {
		this.selectedRowNo = selectedRowNo;
	}

	/**
	 * @return the activityStatus
	 */
	public String getActivityStatus() {
		return activityStatus;
	}

	/**
	 * @param activityStatus
	 *            the activityStatus to set
	 */
	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	/**
	 * @return the approveDate
	 */
	public Date getApproveDate() {
		return approveDate;
	}

	/**
	 * @param approveDate
	 *            the approveDate to set
	 */
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	/**
	 * @return the m3Status
	 */
	public String getM3Status() {
		return m3Status;
	}

	/**
	 * @param status
	 *            the m3Status to set
	 */
	public void setM3Status(String status) {
		m3Status = status;
	}

	/**
	 * @return the validLastSequence
	 */
	public boolean isValidLastSequence() {
		return validLastSequence;
	}

	/**
	 * @param validLastSequence
	 *            the validLastSequence to set
	 */
	public void setValidLastSequence(boolean validLastSequence) {
		this.validLastSequence = validLastSequence;
	}

	/**
	 * @return the sequenceStatus
	 */
	public String getSequenceStatus() {
		return sequenceStatus;
	}

	/**
	 * @param sequenceStatus
	 *            the sequenceStatus to set
	 */
	public void setSequenceStatus(String sequenceStatus) {
		this.sequenceStatus = sequenceStatus;
	}

	/**
	 * @return the productNumber
	 */
	public String getProductNumber() {
		return productNumber;
	}

	/**
	 * @param productNumber
	 *            the productNumber to set
	 */
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	/**
	 * @return the isReportWeight
	 */
	public boolean isReportWeight() {
		return isReportWeight;
	}

	/**
	 * @param isReportWeight
	 *            the isReportWeight to set
	 */
	public void setReportWeight(boolean isReportWeight) {
		this.isReportWeight = isReportWeight;
	}

	/**
	 * @return the itemweight
	 */
	public double getItemweight() {
		return itemweight;
	}

	/**
	 * @param itemweight
	 *            the itemweight to set
	 */
	public void setItemweight(double itemweight) {
		this.itemweight = itemweight;
	}

	/**
	 * @return the modifiedWeight
	 */
	public String getModifiedWeight() {
		return modifiedWeight;
	}

	/**
	 * @param modifiedWeight
	 *            the modifiedWeight to set
	 */
	public void setModifiedWeight(String modifiedWeight) {
		this.modifiedWeight = modifiedWeight;
	}

	/**
	 * @return the setupTime
	 */
	public double getSetupTime() {
		return setupTime;
	}

	/**
	 * @param setupTime
	 *            the setupTime to set
	 */
	public void setSetupTime(double setupTime) {
		this.setupTime = setupTime;
	}

	/**
	 * @return the runTime
	 */
	public double getRunTime() {
		return runTime;
	}

	/**
	 * @param runTime
	 *            the runTime to set
	 */
	public void setRunTime(double runTime) {
		this.runTime = runTime;
	}

	public int getOldCompletedQuantity() {
		return oldCompletedQuantity;
	}

	public void setOldCompletedQuantity(int completedQuantity) {
		this.oldCompletedQuantity = completedQuantity;
	}

	/**
	 * @return the usedLabourRunTime
	 */
	public String getUsedLabourRunTime() {
		return usedLabourRunTime;
	}

	/**
	 * @param usedLabourRunTime
	 *            the usedLabourRunTime to set
	 */
	public void setUsedLabourRunTime(String usedLabourRunTime) {
		this.usedLabourRunTime = usedLabourRunTime;
	}

	public void setAssetMap(HashMap<String, String> assetMap) {
		this.assetMap = assetMap;
	}

	public HashMap<String, String> getAssetMap() {
		return assetMap;
	}

	public HashMap<String, String> getEmployeeMap() {
		return employeeMap;
	}

	public void setEmployeeMap(HashMap<String, String> employeeMap) {
		this.employeeMap = employeeMap;
	}

	public void setSelectedSeqActivity(SequenceActivity selectedSeqActivity) {
		this.selectedSeqActivity = selectedSeqActivity;
	}

	public SequenceActivity getSelectedSeqActivity() {
		return selectedSeqActivity;
	}

	public void setAssetsListForWC(ArrayList<Asset> assetsListForWC) {
		this.assetsListForWC = assetsListForWC;
	}

	public ArrayList<Asset> getAssetsListForWC() {
		return assetsListForWC;
	}

	/**
	 * @param newCompletedQuantity
	 *            the newCompletedQuantity to set
	 */
	public void setCompletedQuantity(int newCompletedQuantity) {
		this.completedQuantity = newCompletedQuantity;
	}

	/**
	 * @return the newCompletedQuantity
	 */
	public int getCompletedQuantity() {
		return completedQuantity;
	}

	/**
	 * @return the jobNumber
	 */
	public int getJobNum() {
		return jobNum;
	}

	/**
	 * @param jobNumber
	 *            the jobNumber to set
	 */
	public void setJobNumber(int jobNumber) {
		this.jobNum = jobNumber;
	}

	public boolean isFirstSequence() {
		return isFirstSequence;
	}

	public void setFirstSequence(boolean isFirstSequence) {
		this.isFirstSequence = isFirstSequence;
	}

	public int getMorvQuantity() {
		return morvQuantity;
	}

	public void setMorvQuantity(int morvQuantity) {
		this.morvQuantity = morvQuantity;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public Sequence(String moNumber, double qtyCompleted, String company,
			String employeeNumber) {
		super();
		this.moNumber = moNumber;
		this.qtyCompleted = qtyCompleted;
		this.company = company;
		this.employeeNumber = employeeNumber;
	}

	public Sequence() {
		super();
	}

	/**
	 * @return the kronosPunchIn
	 */
	public Date getKronosPunchIn() {
		return kronosPunchIn;
	}

	/**
	 * @param kronosPunchIn
	 *            the kronosPunchIn to set
	 */
	public void setKronosPunchIn(Date kronosPunchIn) {
		this.kronosPunchIn = kronosPunchIn;
	}

	/**
	 * @return the kronosPunchOut
	 */
	public Date getKronosPunchOut() {
		return kronosPunchOut;
	}

	/**
	 * @param kronosPunchOut
	 *            the kronosPunchOut to set
	 */
	public void setKronosPunchOut(Date kronosPunchOut) {
		this.kronosPunchOut = kronosPunchOut;
	}

	public String getOldSequenceStatus() {
		return oldSequenceStatus;
	}

	public void setOldSequenceStatus(String oldSequenceStatus) {
		this.oldSequenceStatus = oldSequenceStatus;
	}

	/**
	 * @return the priceTimeQty
	 */
	public double getPriceTimeQty() {
		return priceTimeQty;
	}

	/**
	 * @param priceTimeQty
	 *            the priceTimeQty to set
	 */
	public void setPriceTimeQty(double priceTimeQty) {
		this.priceTimeQty = priceTimeQty;
	}

	/**
	 * @return the workCenterCapacity
	 */
	public int getWorkCenterCapacity() {
		return workCenterCapacity;
	}

	/**
	 * @param workCenterCapacity
	 *            the workCenterCapacity to set
	 */
	public void setWorkCenterCapacity(int workCenterCapacity) {
		this.workCenterCapacity = workCenterCapacity;
	}
}