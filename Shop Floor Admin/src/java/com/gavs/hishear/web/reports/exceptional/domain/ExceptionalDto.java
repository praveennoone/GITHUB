/**
 * 
 */
package com.gavs.hishear.web.reports.exceptional.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author sundarrajanr
 * 
 */
public class ExceptionalDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String activity;

	private String empName;

	private String empId;

	private String hrs;

	private String setup;

	private String run;

	private String reSetup;

	private String reTool;

	private String nps;

	private String ttl;

	private String netHrs;

	private String ttl_pcs;

	private String pcs_hrs;

	private String stp_Perf;

	private String run_Perf;

	private String rsp_Perf;

	private String rtl_Perf;

	private String ttl_Perf;

	private String supervisorComment;

	private String displayName;

	private String department;

	private String deptId;

	private String punchIn;

	private String punchOut;

	private String shiftTime;

	private String logon;

	private String logoff;

	private String fromDate;

	private String toDate;

	private String moNumber;

	private String rejectedAct;

	private String rejectedLogon;

	private String rejectedLogoff;

	private String sequence;

	private String asset;

	private String assetDescription;

	private String orderedQty;

	private String completedQty;

	private String lineNumber;

	private String userName;

	private String brkStart;

	private String brkEnd;

	private String cat;

	private String rejectedDate;

	private String rejectedBy;

	private String rejectedComments;

	private ArrayList logonLogoffReport;

	private ArrayList employeeDetailsReport;

	private ArrayList nonProductivityReport;

	private ArrayList activityRejectedReport;

	private ArrayList activityRejection;

	private ArrayList activityAdjustment;

	private ArrayList cat1;

	private String rejCheck;

	private String actNo;

	private String jobStatus;

	private boolean isUpdate;
	private boolean canProceed;

	private String query;

	private String assignedQty;

	private String shiftCode;

	private String shiftStartTime;

	private String shiftEndTime;

	private String workingHrs;
	private String productivityHrs;
	private ArrayList arrShiftTime;
	private ArrayList logonLogoffDetailsReport;
	private String itemNumber;
	private int qtyComleted;
	private Date punchInDate;
	private Date punchOutDate;

	private int jobNumber;

	private String assetNumber;
	private String employeeNumber;

	private int reasonCode;
	private String reasonDesc;
	private ArrayList reasons;

	private String errorMessage;

	private int activitySize;

	private String message;

	private boolean errorOccured;

	private String planningArea;

	private String activityLogNumber;

	private String workCenterNumber;

	private Date logonDate;
	private Date logoffDate;

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
	 * @return the workCenterNumber
	 */
	public String getWorkCenterNumber() {
		return workCenterNumber;
	}

	/**
	 * @param workCenterNumber
	 *            the workCenterNumber to set
	 */
	public void setWorkCenterNumber(String workCenterNumber) {
		this.workCenterNumber = workCenterNumber;
	}

	/**
	 * @return the activityLogNumber
	 */
	public String getActivityLogNumber() {
		return activityLogNumber;
	}

	/**
	 * @param activityLogNumber
	 *            the activityLogNumber to set
	 */
	public void setActivityLogNumber(String activityLogNumber) {
		this.activityLogNumber = activityLogNumber;
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
	 * @return the errorOccured
	 */
	public boolean isErrorOccured() {
		return errorOccured;
	}

	/**
	 * @param errorOccured
	 *            the errorOccured to set
	 */
	public void setErrorOccured(boolean errorOccured) {
		this.errorOccured = errorOccured;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
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

	public ArrayList getLogonLogoffDetailsReport() {
		return logonLogoffDetailsReport;
	}

	public void setLogonLogoffDetailsReport(ArrayList logonLogoffDetailsReport) {
		this.logonLogoffDetailsReport = logonLogoffDetailsReport;
	}

	public String getProductivityHrs() {
		return productivityHrs;
	}

	public void setProductivityHrs(String productivityHrs) {
		this.productivityHrs = productivityHrs;
	}

	public String getWorkingHrs() {
		return workingHrs;
	}

	public void setWorkingHrs(String workingHrs) {
		this.workingHrs = workingHrs;
	}

	public String getShiftEndTime() {
		return shiftEndTime;
	}

	public void setShiftEndTime(String shiftEndTime) {
		this.shiftEndTime = shiftEndTime;
	}

	public String getShiftStartTime() {
		return shiftStartTime;
	}

	public void setShiftStartTime(String shiftStartTime) {
		this.shiftStartTime = shiftStartTime;
	}

	public String getShiftCode() {
		return shiftCode;
	}

	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}

	/**
	 * @return the assignedQty
	 */
	public String getAssignedQty() {
		return assignedQty;
	}

	/**
	 * @param assignedQty
	 *            the assignedQty to set
	 */
	public void setAssignedQty(String assignedQty) {
		this.assignedQty = assignedQty;
	}

	/**
	 * @return the isUpdate
	 */
	public boolean getIsUpdate() {
		return isUpdate;
	}

	/**
	 * @param isUpdate
	 *            the isUpdate to set
	 */
	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	/**
	 * @return the canProceed
	 */
	public boolean isCanProceed() {
		return canProceed;
	}

	/**
	 * @param canProceed
	 *            the canProceed to set
	 */
	public void setCanProceed(boolean canProceed) {
		this.canProceed = canProceed;
	}

	/**
	 * @return the jobStatus
	 */
	public String getJobStatus() {
		return jobStatus;
	}

	/**
	 * @param jobStatus
	 *            the jobStatus to set
	 */
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	/**
	 * @return the actNo
	 */
	public String getActNo() {
		return actNo;
	}

	/**
	 * @param actNo
	 *            the actNo to set
	 */
	public void setActNo(String actNo) {
		this.actNo = actNo;
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
	 * @return the logonLogoffReport
	 */
	public ArrayList getLogonLogoffReport() {
		return logonLogoffReport;
	}

	/**
	 * @param logonLogoffReport
	 *            the logonLogoffReport to set
	 */
	public void setLogonLogoffReport(ArrayList logonLogoffReport) {
		this.logonLogoffReport = logonLogoffReport;
	}

	/**
	 * @return the employeeDetailsReport
	 */
	public ArrayList getEmployeeDetailsReport() {
		return employeeDetailsReport;
	}

	/**
	 * @param employeeDetailsReport
	 *            the employeeDetailsReport to set
	 */
	public void setEmployeeDetailsReport(ArrayList employeeDetailsReport) {
		this.employeeDetailsReport = employeeDetailsReport;
	}

	/**
	 * @return the nonProductivityReport
	 */
	public ArrayList getNonProductivityReport() {
		return nonProductivityReport;
	}

	/**
	 * @param nonProductivityReport
	 *            the nonProductivityReport to set
	 */
	public void setNonProductivityReport(ArrayList nonProductivityReport) {
		this.nonProductivityReport = nonProductivityReport;
	}

	/**
	 * @return the activityRejectedReport
	 */
	public ArrayList getActivityRejectedReport() {
		return activityRejectedReport;
	}

	/**
	 * @param activityRejectedReport
	 *            the activityRejectedReport to set
	 */
	public void setActivityRejectedReport(ArrayList activityRejectedReport) {
		this.activityRejectedReport = activityRejectedReport;
	}

	/**
	 * @return the activityRejection
	 */
	public ArrayList getActivityRejection() {
		return activityRejection;
	}

	/**
	 * @param activityRejection
	 *            the activityRejection to set
	 */
	public void setActivityRejection(ArrayList activityRejection) {
		this.activityRejection = activityRejection;
	}

	/**
	 * @return the activityRejection
	 */
	public ArrayList getActivityAdjustment() {
		return activityAdjustment;
	}

	/**
	 * @param activityAdjustment
	 *            the activityAdjustment to set
	 */
	public void setActivityAdjustment(ArrayList activityAdjustment) {
		this.activityAdjustment = activityAdjustment;
	}

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}

	/**
	 * @param empName
	 *            the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * @return the empId
	 */
	public String getEmpId() {
		return empId;
	}

	/**
	 * @param empId
	 *            the empId to set
	 */
	public void setEmpId(String empId) {
		this.empId = empId;
	}

	/**
	 * @return the hrs
	 */
	public String getHrs() {
		return hrs;
	}

	/**
	 * @param hrs
	 *            the hrs to set
	 */
	public void setHrs(String hrs) {
		this.hrs = hrs;
	}

	/**
	 * @return the setup
	 */
	public String getSetup() {
		return setup;
	}

	/**
	 * @param setup
	 *            the setup to set
	 */
	public void setSetup(String setup) {
		this.setup = setup;
	}

	/**
	 * @return the run
	 */
	public String getRun() {
		return run;
	}

	/**
	 * @param run
	 *            the run to set
	 */
	public void setRun(String run) {
		this.run = run;
	}

	/**
	 * @return the reSetup
	 */
	public String getReSetup() {
		return reSetup;
	}

	/**
	 * @param reSetup
	 *            the reSetup to set
	 */
	public void setReSetup(String reSetup) {
		this.reSetup = reSetup;
	}

	/**
	 * @return the reTool
	 */
	public String getReTool() {
		return reTool;
	}

	/**
	 * @param reTool
	 *            the reTool to set
	 */
	public void setReTool(String reTool) {
		this.reTool = reTool;
	}

	/**
	 * @return the nps
	 */
	public String getNps() {
		return nps;
	}

	/**
	 * @param nps
	 *            the nps to set
	 */
	public void setNps(String nps) {
		this.nps = nps;
	}

	/**
	 * @return the ttl
	 */
	public String getTtl() {
		return ttl;
	}

	/**
	 * @param ttl
	 *            the ttl to set
	 */
	public void setTtl(String ttl) {
		this.ttl = ttl;
	}

	/**
	 * @return the netHrs
	 */
	public String getNetHrs() {
		return netHrs;
	}

	/**
	 * @param netHrs
	 *            the netHrs to set
	 */
	public void setNetHrs(String netHrs) {
		this.netHrs = netHrs;
	}

	/**
	 * @return the ttl_pcs
	 */
	public String getTtl_pcs() {
		return ttl_pcs;
	}

	/**
	 * @param ttl_pcs
	 *            the ttl_pcs to set
	 */
	public void setTtl_pcs(String ttl_pcs) {
		this.ttl_pcs = ttl_pcs;
	}

	/**
	 * @return the pcs_hrs
	 */
	public String getPcs_hrs() {
		return pcs_hrs;
	}

	/**
	 * @param pcs_hrs
	 *            the pcs_hrs to set
	 */
	public void setPcs_hrs(String pcs_hrs) {
		this.pcs_hrs = pcs_hrs;
	}

	/**
	 * @return the stp_Perf
	 */
	public String getStp_Perf() {
		return stp_Perf;
	}

	/**
	 * @param stp_Perf
	 *            the stp_Perf to set
	 */
	public void setStp_Perf(String stp_Perf) {
		this.stp_Perf = stp_Perf;
	}

	/**
	 * @return the run_Perf
	 */
	public String getRun_Perf() {
		return run_Perf;
	}

	/**
	 * @param run_Perf
	 *            the run_Perf to set
	 */
	public void setRun_Perf(String run_Perf) {
		this.run_Perf = run_Perf;
	}

	/**
	 * @return the rsp_Perf
	 */
	public String getRsp_Perf() {
		return rsp_Perf;
	}

	/**
	 * @param rsp_Perf
	 *            the rsp_Perf to set
	 */
	public void setRsp_Perf(String rsp_Perf) {
		this.rsp_Perf = rsp_Perf;
	}

	/**
	 * @return the rtl_Perf
	 */
	public String getRtl_Perf() {
		return rtl_Perf;
	}

	/**
	 * @param rtl_Perf
	 *            the rtl_Perf to set
	 */
	public void setRtl_Perf(String rtl_Perf) {
		this.rtl_Perf = rtl_Perf;
	}

	/**
	 * @return the ttl_Perf
	 */
	public String getTtl_Perf() {
		return ttl_Perf;
	}

	/**
	 * @param ttl_Perf
	 *            the ttl_Perf to set
	 */
	public void setTtl_Perf(String ttl_Perf) {
		this.ttl_Perf = ttl_Perf;
	}

	/**
	 * @return the supervisorComment
	 */
	public String getSupervisorComment() {
		return supervisorComment;
	}

	/**
	 * @param supervisorComment
	 *            the supervisorComment to set
	 */
	public void setSupervisorComment(String supervisorComment) {
		this.supervisorComment = supervisorComment;
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
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @return the logoff
	 */
	public String getLogoff() {
		return logoff;
	}

	/**
	 * @return the logon
	 */
	public String getLogon() {
		return logon;
	}

	/**
	 * @return the punchIn
	 */
	public String getPunchIn() {
		return punchIn;
	}

	/**
	 * @return the punchOut
	 */
	public String getPunchOut() {
		return punchOut;
	}

	/**
	 * @return the shiftTime
	 */
	public String getShiftTime() {
		return shiftTime;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @param logoff
	 *            the logoff to set
	 */
	public void setLogoff(String logoff) {
		this.logoff = logoff;
	}

	/**
	 * @param logon
	 *            the logon to set
	 */
	public void setLogon(String logon) {
		this.logon = logon;
	}

	/**
	 * @param punchIn
	 *            the punchIn to set
	 */
	public void setPunchIn(String punchIn) {
		this.punchIn = punchIn;
	}

	/**
	 * @param punchOut
	 *            the punchOut to set
	 */
	public void setPunchOut(String punchOut) {
		this.punchOut = punchOut;
	}

	/**
	 * @param shiftTime
	 *            the shiftTime to set
	 */
	public void setShiftTime(String shiftTime) {
		this.shiftTime = shiftTime;
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
	 * @return the moNumber
	 */
	public String getMoNumber() {
		return moNumber;
	}

	/**
	 * @param moNumber
	 *            the mo_number to set
	 */
	public void setMoNumber(String moNumber) {
		this.moNumber = moNumber;
	}

	/**
	 * @return the rejectedAct
	 */
	public String getRejectedAct() {
		return rejectedAct;
	}

	/**
	 * @param rejectedAct
	 *            the rejectedAct to set
	 */
	public void setRejectedAct(String rejectedAct) {
		this.rejectedAct = rejectedAct;
	}

	/**
	 * @return the rejectedLogoff
	 */
	public String getRejectedLogoff() {
		return rejectedLogoff;
	}

	/**
	 * @param rejectedLogoff
	 *            the rejectedLogoff to set
	 */
	public void setRejectedLogoff(String rejectedLogoff) {
		this.rejectedLogoff = rejectedLogoff;
	}

	/**
	 * @return the rejectedLogon
	 */
	public String getRejectedLogon() {
		return rejectedLogon;
	}

	/**
	 * @param rejectedLogon
	 *            the rejectedLogon to set
	 */
	public void setRejectedLogon(String rejectedLogon) {
		this.rejectedLogon = rejectedLogon;
	}

	/**
	 * @return the completedQty
	 */
	public String getCompletedQty() {
		return completedQty;
	}

	/**
	 * @param completedQty
	 *            the completedQty to set
	 */
	public void setCompletedQty(String completedQty) {
		this.completedQty = completedQty;
	}

	/**
	 * @return the asset
	 */
	public String getAsset() {
		return asset;
	}

	/**
	 * @param asset
	 *            the asset to set
	 */
	public void setAsset(String asset) {
		this.asset = asset;
	}

	/**
	 * @return the orderedQty
	 */
	public String getOrderedQty() {
		return orderedQty;
	}

	/**
	 * @param orderedQty
	 *            the orderedQty to set
	 */
	public void setOrderedQty(String orderedQty) {
		this.orderedQty = orderedQty;
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
	 * @return the brkEnd
	 */
	public String getBrkEnd() {
		return brkEnd;
	}

	/**
	 * @param brkEnd
	 *            the brkEnd to set
	 */
	public void setBrkEnd(String brkEnd) {
		this.brkEnd = brkEnd;
	}

	/**
	 * @return the brkStart
	 */
	public String getBrkStart() {
		return brkStart;
	}

	/**
	 * @param brkStart
	 *            the brkStart to set
	 */
	public void setBrkStart(String brkStart) {
		System.out.println(brkStart.replace(".0", ""));
		this.brkStart = brkStart.replace(".0", "");
	}

	/**
	 * @return the rejectedBy
	 */
	public String getRejectedBy() {
		return rejectedBy;
	}

	/**
	 * @param rejectedBy
	 *            the rejectedBy to set
	 */
	public void setRejectedBy(String rejectedBy) {
		this.rejectedBy = rejectedBy;
	}

	/**
	 * @return the rejectedDate
	 */
	public String getRejectedDate() {
		return rejectedDate;
	}

	/**
	 * @param rejectedDate
	 *            the rejectedDate to set
	 */
	public void setRejectedDate(String rejectedDate) {
		this.rejectedDate = rejectedDate;
	}

	/**
	 * @return the rejectedComments
	 */
	public String getRejectedComments() {
		return rejectedComments;
	}

	/**
	 * @param rejectedComments
	 *            the rejectedComments to set
	 */
	public void setRejectedComments(String rejectedComments) {
		this.rejectedComments = rejectedComments;
	}

	/**
	 * @return the rejCheck
	 */
	public String getRejCheck() {
		return rejCheck;
	}

	/**
	 * @param rejCheck
	 *            the rejCheck to set
	 */
	public void setRejCheck(String rejCheck) {
		this.rejCheck = rejCheck;
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
	 * @return the cat1
	 */
	public ArrayList getCat1() {
		return cat1;
	}

	/**
	 * @param cat1
	 *            the cat1 to set
	 */
	public void setCat1(ArrayList cat1) {
		this.cat1 = cat1;
	}

	/**
	 * @return the cat
	 */
	public String getCat() {
		return cat;
	}

	/**
	 * @param cat
	 *            the cat to set
	 */
	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public ArrayList getArrShiftTime() {
		return arrShiftTime;
	}

	public void setArrShiftTime(ArrayList arrShiftTime) {
		this.arrShiftTime = arrShiftTime;
	}

	public String getAssetDescription() {
		return assetDescription;
	}

	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public int getQtyComleted() {
		return qtyComleted;
	}

	public void setQtyComleted(int qtyComleted) {
		this.qtyComleted = qtyComleted;
	}

	public Date getPunchInDate() {
		return punchInDate;
	}

	public void setPunchInDate(Date punchInDate) {
		this.punchInDate = punchInDate;
	}

	public Date getPunchOutDate() {
		return punchOutDate;
	}

	public void setPunchOutDate(Date punchOutDate) {
		this.punchOutDate = punchOutDate;
	}

	/**
	 * @return the jobNumber
	 */
	public int getJobNumber() {
		return jobNumber;
	}

	/**
	 * @param jobNumber
	 *            the jobNumber to set
	 */
	public void setJobNumber(int jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	/**
	 * @return the activitySize
	 */
	public int getActivitySize() {
		return activitySize;
	}

	/**
	 * @param activitySize
	 *            the activitySize to set
	 */
	public void setActivitySize(int activitySize) {
		this.activitySize = activitySize;
	}

}