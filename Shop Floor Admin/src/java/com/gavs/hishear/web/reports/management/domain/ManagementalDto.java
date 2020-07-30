/**
 * 
 */
package com.gavs.hishear.web.reports.management.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.gavs.hishear.web.domain.Facility;

/**
 * @author sundarrajanr
 * 
 */
public class ManagementalDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	private String punchIn;
	private String punchOut;
	private String shiftTime;
	private String logon;
	private String logoff;
	private String assetId;
	private String assetName;
	private String assetDesc;
	private String partid;
	private String seqn;
	private String orderedqty;
	private String activity;
	private String moNumber;
	private String completedQty;
	private String fromDate;
	private String toDate;
	private String partDesc;
	private String userName;
	private String lineNumber;
	private String waitTool;
	private String waitMaterial;
	private String engHold;
	private String preMaintenance;
	private String totalHours1;
	private String realHours;
	private String fourthRate;
	private String orderNumber;
	private String shift;
	private ArrayList assetUsageReport;
	private ArrayList employeePerformanceReport;
	private ArrayList standardRateReport;
	private ArrayList productionReport;
	private ArrayList exceptionalActivityReport;
	private ArrayList arrShiftTime;
	private ArrayList activityLog;

	private String activityNo;

	private ArrayList assets;
	private ArrayList shiftTime1;
	private ArrayList cat1;

	private String asset;

	private String actTime;

	private String stdHrs;

	private String stdRate;

	private String actHrs;

	private String actRate;

	private String rateVar;

	private String rejComments;

	private String deptId;

	private String cat;

	private String totalHrs;

	private String empRate;

	private String query;

	private String factoryDesc;

	private String noOfPcs;

	private String pmMts;

	private String dailyHrs;

	private String availHrs;

	private String prefHrs;

	private String matchEff;

	private String jobNo;

	private ArrayList producationPopUpReport;

	private String breakStart;

	private String breakEnd;

	private String Desc;

	private String quantity;

	private String startTime;

	private String endTime;

	private String reqQty;

	private int expCount;

	private String shiftStartTime;

	private String shiftEndTime;

	private String targetPPH;

	private String actualPPH;

	private String targetQty;

	private String efficiency;

	private int productionReportSize;

	private int totalQtyCompleted;

	private String workCenterNo;

	private String workCenterDesc;

	private String sequence;

	private boolean dateShiftRequired;

	private ArrayList costCenters;

	private ArrayList workCenters;

	private String facility;

	private String costCenter;

	private String workCenter;

	private ArrayList details;

	private ArrayList<Facility> divisionFacilities;
	private int correctionLogId;
	private String correctionBy;
	private String correctionDate;
	private String oldValue;
	private String newValue;
	private String correctionOn;
	private String completeStatusFlag;
	private String date;
	private String newFlag;

	private ArrayList correctionTrackingReport;
	private ArrayList correctionLogDetails;
	private ArrayList employeeNameCorrections;
	private ArrayList logonCorrections;
	private ArrayList logoffCorrections;
	private ArrayList qtyCompletedCorrection;
	private ArrayList assetNumberCorrection;

	private ArrayList employeeCorrectionTrackingReport;
	private ArrayList logonCorrectionTrackingReport;
	private ArrayList logOffCorrectionTrackingReport;
	private ArrayList activityCorrectionTrackingReport;
	private ArrayList qtyCompletedCorrectionTrackingReport;
	private ArrayList assetNumberCorrectionTrackingReport;
	private ArrayList completedSequenceCorrectionTrackingReport;
	private ArrayList addedSequenceCorrectionTrackingReport;
	private ArrayList multipleCorrectionTrackingReport;

	private Date dtFromDate;
	private Date dtToDate;

	private String status;

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

	public ArrayList<Facility> getDivisionFacilities() {
		return divisionFacilities;
	}

	public void setDivisionFacilities(ArrayList<Facility> divisionFacilities) {
		this.divisionFacilities = divisionFacilities;
	}

	/**
	 * @return the details
	 */
	public ArrayList getDetails() {
		return details;
	}

	/**
	 * @param details
	 *            the details to set
	 */
	public void setDetails(ArrayList details) {
		this.details = details;
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
	 * @return the costCenter
	 */
	public String getCostCenter() {
		return costCenter;
	}

	/**
	 * @param costCenter
	 *            the costCenter to set
	 */
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	/**
	 * @return the workCenter
	 */
	public String getWorkCenter() {
		return workCenter;
	}

	/**
	 * @param workCenter
	 *            the workCenter to set
	 */
	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}

	/**
	 * @param shiftEndTime
	 *            to set
	 */
	public void setTargetQty(String targetQty) {
		this.targetQty = targetQty;
	}

	/**
	 * @return the shiftEndTime
	 */
	public String getTargetQty() {
		return targetQty;
	}

	/**
	 * @param shiftEndTime
	 *            to set
	 */
	public void setTotalQtyCompleted(int totalQtyCompleted) {
		this.totalQtyCompleted = totalQtyCompleted;
	}

	/**
	 * @return the shiftEndTime
	 */
	public int getTotalQtyCompleted() {
		return totalQtyCompleted;
	}

	/**
	 * @param shiftEndTime
	 *            to set
	 */
	public void setEfficiency(String efficiency) {
		this.efficiency = efficiency;
	}

	/**
	 * @return the shiftEndTime
	 */
	public String getEfficiency() {
		return efficiency;
	}

	/**
	 * @param shiftEndTime
	 *            to set
	 */
	public void setShiftEndTime(String shiftEndTime) {
		this.shiftEndTime = shiftEndTime;
	}

	/**
	 * @return the shiftEndTime
	 */
	public String getActualPPH() {
		return actualPPH;
	}

	/**
	 * @param shiftEndTime
	 *            to set
	 */
	public void setActualPPH(String actualPPH) {
		this.actualPPH = actualPPH;
	}

	/**
	 * @return the shiftEndTime
	 */
	public String getTargetPPH() {
		return targetPPH;
	}

	/**
	 * @param shiftEndTime
	 *            to set
	 */
	public void setTargetPPH(String targetPPH) {
		this.targetPPH = targetPPH;
	}

	/**
	 * @return the shiftEndTime
	 */
	public String getShiftEndTime() {
		return shiftEndTime;
	}

	/**
	 * @param shiftStartTime
	 *            to set
	 */
	public void setShiftStartTime(String shiftStartTime) {
		this.shiftStartTime = shiftStartTime;
	}

	/**
	 * @return the shiftStartTime
	 */
	public String getShiftStartTime() {
		return shiftStartTime;
	}

	/**
	 * @param expCount
	 *            to set
	 */
	public void setExpCount(int expCount) {
		this.expCount = expCount;
	}

	/**
	 * @return the expCount
	 */
	public int getExpCount() {
		return expCount;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param ReqQty
	 *            to set
	 */
	public void setReqQty(String reqQty) {
		this.reqQty = reqQty;
	}

	/**
	 * @return the startTime
	 */
	public String getReqQty() {
		return reqQty;
	}

	/**
	 * @param shift
	 *            the shift to set
	 */
	public void setShift(String shift) {
		this.shift = shift;
	}

	/**
	 * @return the shift
	 */
	public String getShift() {
		return shift;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return Desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.Desc = desc;
	}

	/**
	 * @return the breakStart
	 */
	public String getBreakStart() {
		return breakStart;
	}

	/**
	 * @param breakStart
	 *            the breakStart to set
	 */
	public void setBreakStart(String breakStart) {
		this.breakStart = breakStart;
	}

	/**
	 * @return the breakEnd
	 */
	public String getBreakEnd() {
		return breakEnd;
	}

	/**
	 * @param breakEnd
	 *            the breakEnd to set
	 */
	public void setBreakEnd(String breakEnd) {
		this.breakEnd = breakEnd;
	}

	/**
	 * @return the producationPopUpReport
	 */
	public ArrayList getProducationPopUpReport() {
		return producationPopUpReport;
	}

	/**
	 * @param producationPopUpReport
	 *            the producationPopUpReport to set
	 */
	public void setProducationPopUpReport(ArrayList producationPopUpReport) {
		this.producationPopUpReport = producationPopUpReport;
	}

	/**
	 * @return the jobNo
	 */
	public String getJobNo() {
		return jobNo;
	}

	/**
	 * @param jobNo
	 *            the jobNo to set
	 */
	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	/**
	 * @return the prefHrs
	 */
	public String getPrefHrs() {
		return prefHrs;
	}

	/**
	 * @param prefHrs
	 *            the prefHrs to set
	 */
	public void setPrefHrs(String prefHrs) {
		this.prefHrs = prefHrs;
	}

	/**
	 * @return the availHrs
	 */
	public String getAvailHrs() {
		return availHrs;
	}

	/**
	 * @param availHrs
	 *            the availHrs to set
	 */
	public void setAvailHrs(String availHrs) {
		this.availHrs = availHrs;
	}

	/**
	 * @return the dailyHrs
	 */
	public String getDailyHrs() {
		return dailyHrs;
	}

	/**
	 * @param dailyHrs
	 *            the dailyHrs to set
	 */
	public void setDailyHrs(String dailyHrs) {
		this.dailyHrs = dailyHrs;
	}

	/**
	 * @return the pmMts
	 */
	public String getPmMts() {
		return pmMts;
	}

	/**
	 * @param pmMts
	 *            the pmMts to set
	 */
	public void setPmMts(String pmMts) {
		this.pmMts = pmMts;
	}

	/**
	 * @return the noOfPcs
	 */
	public String getNoOfPcs() {
		return noOfPcs;
	}

	/**
	 * @param noOfPcs
	 *            the noOfPcs to set
	 */
	public void setNoOfPcs(String noOfPcs) {
		this.noOfPcs = noOfPcs;
	}

	/**
	 * @return the factoryDesc
	 */
	public String getFactoryDesc() {
		return factoryDesc;
	}

	/**
	 * @param factoryDesc
	 *            the factoryDesc to set
	 */
	public void setFactoryDesc(String factoryDesc) {
		this.factoryDesc = factoryDesc;
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
	 * @return the empRate
	 */
	public String getEmpRate() {
		return empRate;
	}

	/**
	 * @param empRate
	 *            the empRate to set
	 */
	public void setEmpRate(String empRate) {
		this.empRate = empRate;
	}

	/**
	 * @return the totalHrs
	 */
	public String getTotalHrs() {
		return totalHrs;
	}

	/**
	 * @param totalHrs
	 *            the totalHrs to set
	 */
	public void setTotalHrs(String totalHrs) {
		this.totalHrs = totalHrs;
	}

	/**
	 * @return the deptId
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId
	 *            the deptId to set
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
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

	/**
	 * @return the rejComments
	 */
	public String getRejComments() {
		return rejComments;
	}

	/**
	 * @param rejComments
	 *            the rejComments to set
	 */
	public void setRejComments(String rejComments) {
		this.rejComments = rejComments;
	}

	public String getActHrs() {
		return actHrs;
	}

	public void setActHrs(String actHrs) {
		this.actHrs = actHrs;
	}

	public String getActRate() {
		return actRate;
	}

	public void setActRate(String actRate) {
		this.actRate = actRate;
	}

	public String getRateVar() {
		return rateVar;
	}

	public void setRateVar(String rateVar) {
		this.rateVar = rateVar;
	}

	public String getStdHrs() {
		return stdHrs;
	}

	public void setStdHrs(String stdHrs) {
		this.stdHrs = stdHrs;
	}

	public String getStdRate() {
		return stdRate;
	}

	public void setStdRate(String stdRate) {
		this.stdRate = stdRate;
	}

	/**
	 * @return the actTime
	 */
	public String getActTime() {
		return actTime;
	}

	/**
	 * @param actTime
	 *            the actTime to set
	 */
	public void setActTime(String actTime) {
		this.actTime = actTime;
	}

	/**
	 * @return the activityNo
	 */
	public String getActivityNo() {
		return activityNo;
	}

	/**
	 * @param activityNo
	 *            the activityNo to set
	 */
	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}

	/**
	 * @return the assets
	 */
	public ArrayList getAssets() {
		return assets;
	}

	/**
	 * @param assets
	 *            the assets to set
	 */
	public void setAssets(ArrayList assets) {
		this.assets = assets;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the assetUsageReport
	 */
	public ArrayList getAssetUsageReport() {
		return assetUsageReport;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param assetUsageReport
	 *            the assetUsageReport to set
	 */
	public void setAssetUsageReport(ArrayList assetUsageReport) {
		this.assetUsageReport = assetUsageReport;
	}

	/**
	 * @return the employeePerformanceReport
	 */
	public ArrayList getExceptionalActivityReport() {
		return exceptionalActivityReport;
	}

	/**
	 * @param employeePerformanceReport
	 *            the employeePerformanceReport to set
	 */
	public void setExceptionalActivityReport(ArrayList exceptionalActivityReport) {
		this.exceptionalActivityReport = exceptionalActivityReport;
	}

	/**
	 * @return the employeePerformanceReport
	 */
	public ArrayList getEmployeePerformanceReport() {
		return employeePerformanceReport;
	}

	/**
	 * @param employeePerformanceReport
	 *            the employeePerformanceReport to set
	 */
	public void setEmployeePerformanceReport(ArrayList employeePerformanceReport) {
		this.employeePerformanceReport = employeePerformanceReport;
	}

	/**
	 * @return the standardRateReport
	 */
	public ArrayList getStandardRateReport() {
		return standardRateReport;
	}

	/**
	 * @param standardRateReport
	 *            the standardRateReport to set
	 */
	public void setStandardRateReport(ArrayList standardRateReport) {
		this.standardRateReport = standardRateReport;
	}

	/**
	 * @return the productionReport
	 */
	public ArrayList getProductionReport() {
		return productionReport;
	}

	/**
	 * @param productionReport
	 *            the productionReport to set
	 */
	public void setProductionReport(ArrayList productionReport) {
		this.productionReport = productionReport;
	}

	/**
	 * @return the productionReport
	 */
	public int getProductionReportSize() {
		return productionReportSize;
	}

	/**
	 * @param productionReport
	 *            the productionReport to set
	 */
	public void setProductionReportSize(int productionReportSize) {
		this.productionReportSize = productionReportSize;
	}

	/**
	 * @return the assetDesc
	 */
	public String getAssetDesc() {
		return assetDesc;
	}

	/**
	 * @return the assetId
	 */
	public String getAssetId() {
		return assetId;
	}

	/**
	 * @param assetDesc
	 *            the assetDesc to set
	 */
	public void setAssetDesc(String assetDesc) {
		this.assetDesc = assetDesc;
	}

	/**
	 * @param assetId
	 *            the assetId to set
	 */
	public void setAssetId(String assetId) {
		this.assetId = assetId;
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
	 * @return the part_id
	 */
	public String getPartid() {
		return partid;
	}

	/**
	 * @param part_id
	 *            the part_id to set
	 */
	public void setPartid(String partid) {
		this.partid = partid;
	}

	/**
	 * @return the seqn
	 */
	public String getSeqn() {
		return seqn;
	}

	/**
	 * @param seqn
	 *            the seqn to set
	 */
	public void setSeqn(String seqn) {
		this.seqn = seqn;
	}

	/**
	 * @return the ordered_qty
	 */
	public String getOrderedqty() {
		return orderedqty;
	}

	/**
	 * @param ordered_qty
	 *            the ordered_qty to set
	 */
	public void setOrderedqty(String orderedqty) {
		this.orderedqty = orderedqty;
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
	 *            the moNumber to set
	 */
	public void setMoNumber(String moNumber) {
		this.moNumber = moNumber;
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
	 * @return the assetName
	 */
	public String getAssetName() {
		return assetName;
	}

	/**
	 * @param assetName
	 *            the assetName to set
	 */
	public void setAssetName(String assetName) {
		this.assetName = assetName;
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
	 * @return the shiftTime1
	 */
	public ArrayList getArrShiftTime() {
		return arrShiftTime;
	}

	/**
	 * @param shiftTime1
	 *            the shiftTime1 to set
	 */
	public void setArrShiftTime(ArrayList arrShiftTime) {
		this.arrShiftTime = arrShiftTime;
	}

	/**
	 * @return the shiftTime1
	 */
	public ArrayList getShiftTime1() {
		return shiftTime1;
	}

	/**
	 * @param shiftTime1
	 *            the shiftTime1 to set
	 */
	public void setShiftTime1(ArrayList shiftTime1) {
		this.shiftTime1 = shiftTime1;
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
	 * @return the matchEff
	 */
	public String getMatchEff() {
		return matchEff;
	}

	/**
	 * @param matchEff
	 *            the matchEff to set
	 */
	public void setMatchEff(String matchEff) {
		this.matchEff = matchEff;
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
	 * @return the waitTool
	 */
	public String getWaitTool() {
		return waitTool;
	}

	/**
	 * @param waitTool
	 *            the waitTool to set
	 */
	public void setWaitTool(String waitTool) {
		this.waitTool = waitTool;
	}

	/**
	 * @return the waitMaterial
	 */
	public String getWaitMaterial() {
		return waitMaterial;
	}

	/**
	 * @param waitMaterial
	 *            the waitMaterial to set
	 */
	public void setWaitMaterial(String waitMaterial) {
		this.waitMaterial = waitMaterial;
	}

	/**
	 * @return the engHold
	 */
	public String getEngHold() {
		return engHold;
	}

	/**
	 * @param engHold
	 *            the engHold to set
	 */
	public void setEngHold(String engHold) {
		this.engHold = engHold;
	}

	/**
	 * @return the preMaintenance
	 */
	public String getPreMaintenance() {
		return preMaintenance;
	}

	/**
	 * @param preMaintenance
	 *            the preMaintenance to set
	 */
	public void setPreMaintenance(String preMaintenance) {
		this.preMaintenance = preMaintenance;
	}

	/**
	 * @return the totalHours1
	 */
	public String getTotalHours1() {
		return totalHours1;
	}

	/**
	 * @param totalHours1
	 *            the totalHours1 to set
	 */
	public void setTotalHours1(String totalHours1) {
		this.totalHours1 = totalHours1;
	}

	/**
	 * @return the realHours
	 */
	public String getRealHours() {
		return realHours;
	}

	/**
	 * @param realHours
	 *            the realHours to set
	 */
	public void setRealHours(String realHours) {
		this.realHours = realHours;
	}

	/**
	 * @return the fourthRate
	 */
	public String getFourthRate() {
		return fourthRate;
	}

	/**
	 * @param fourthRate
	 *            the fourthRate to set
	 */
	public void setFourthRate(String fourthRate) {
		this.fourthRate = fourthRate;
	}

	/**
	 * @return the fourthRate
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param fourthRate
	 *            the fourthRate to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * @return the activityLog
	 */
	public ArrayList getActivityLog() {
		return activityLog;
	}

	/**
	 * @param activityLog
	 *            the activityLog to set
	 */
	public void setActivityLog(ArrayList activityLog) {
		this.activityLog = activityLog;
	}

	public String getWorkCenterNo() {
		return workCenterNo;
	}

	public void setWorkCenterNo(String workCenterNo) {
		this.workCenterNo = workCenterNo;
	}

	public String getWorkCenterDesc() {
		return workCenterDesc;
	}

	public void setWorkCenterDesc(String workCenterDesc) {
		this.workCenterDesc = workCenterDesc;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public boolean isDateShiftRequired() {
		return dateShiftRequired;
	}

	public void setDateShiftRequired(boolean dateShiftRequired) {
		this.dateShiftRequired = dateShiftRequired;
	}

	/**
	 * @return the costCenters
	 */
	public ArrayList getCostCenters() {
		return costCenters;
	}

	/**
	 * @param costCenters
	 *            the costCenters to set
	 */
	public void setCostCenters(ArrayList costCenters) {
		this.costCenters = costCenters;
	}

	/**
	 * @return the workCenters
	 */
	public ArrayList getWorkCenters() {
		return workCenters;
	}

	/**
	 * @param workCenters
	 *            the workCenters to set
	 */
	public void setWorkCenters(ArrayList workCenters) {
		this.workCenters = workCenters;
	}

	public ArrayList getCorrectionTrackingReport() {
		return correctionTrackingReport;
	}

	public void setCorrectionTrackingReport(ArrayList correctionTrackingReport) {
		this.correctionTrackingReport = correctionTrackingReport;
	}

	public String getCorrectionBy() {
		return correctionBy;
	}

	public void setCorrectionBy(String correctionBy) {
		this.correctionBy = correctionBy;
	}

	public String getCorrectionDate() {
		return correctionDate;
	}

	public void setCorrectionDate(String correctionDate) {
		this.correctionDate = correctionDate;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getCorrectionOn() {
		return correctionOn;
	}

	public void setCorrectionOn(String correctionOn) {
		this.correctionOn = correctionOn;
	}

	public String getCompleteStatusFlag() {
		return completeStatusFlag;
	}

	public void setCompleteStatusFlag(String completeStatusFlag) {
		this.completeStatusFlag = completeStatusFlag;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNewFlag() {
		return newFlag;
	}

	public void setNewFlag(String newFlag) {
		this.newFlag = newFlag;
	}

	public int getCorrectionLogId() {
		return correctionLogId;
	}

	public void setCorrectionLogId(int correctionLogId) {
		this.correctionLogId = correctionLogId;
	}

	/**
	 * @return the correctionLogDetails
	 */
	public ArrayList getCorrectionLogDetails() {
		return correctionLogDetails;
	}

	/**
	 * @param correctionLogDetails
	 *            the correctionLogDetails to set
	 */
	public void setCorrectionLogDetails(ArrayList correctionLogDetails) {
		this.correctionLogDetails = correctionLogDetails;
	}

	/**
	 * @return the employeeNameCorrections
	 */
	public ArrayList getEmployeeNameCorrections() {
		return employeeNameCorrections;
	}

	/**
	 * @param employeeNameCorrections
	 *            the employeeNameCorrections to set
	 */
	public void setEmployeeNameCorrections(ArrayList employeeNameCorrections) {
		this.employeeNameCorrections = employeeNameCorrections;
	}

	/**
	 * @return the logonCorrections
	 */
	public ArrayList getLogonCorrections() {
		return logonCorrections;
	}

	/**
	 * @param logonCorrections
	 *            the logonCorrections to set
	 */
	public void setLogonCorrections(ArrayList logonCorrections) {
		this.logonCorrections = logonCorrections;
	}

	/**
	 * @return the logoffCorrections
	 */
	public ArrayList getLogoffCorrections() {
		return logoffCorrections;
	}

	/**
	 * @param logoffCorrections
	 *            the logoffCorrections to set
	 */
	public void setLogoffCorrections(ArrayList logoffCorrections) {
		this.logoffCorrections = logoffCorrections;
	}

	/**
	 * @return the qtyCompletedCorrection
	 */
	public ArrayList getQtyCompletedCorrection() {
		return qtyCompletedCorrection;
	}

	/**
	 * @param qtyCompletedCorrection
	 *            the qtyCompletedCorrection to set
	 */
	public void setQtyCompletedCorrection(ArrayList qtyCompletedCorrection) {
		this.qtyCompletedCorrection = qtyCompletedCorrection;
	}

	/**
	 * @return the assetNumberCorrection
	 */
	public ArrayList getAssetNumberCorrection() {
		return assetNumberCorrection;
	}

	/**
	 * @param assetNumberCorrection
	 *            the assetNumberCorrection to set
	 */
	public void setAssetNumberCorrection(ArrayList assetNumberCorrection) {
		this.assetNumberCorrection = assetNumberCorrection;
	}

	/**
	 * @return the employeeCorrectionTrackingReport
	 */
	public ArrayList getEmployeeCorrectionTrackingReport() {
		return employeeCorrectionTrackingReport;
	}

	/**
	 * @param employeeCorrectionTrackingReport
	 *            the employeeCorrectionTrackingReport to set
	 */
	public void setEmployeeCorrectionTrackingReport(
			ArrayList employeeCorrectionTrackingReport) {
		this.employeeCorrectionTrackingReport = employeeCorrectionTrackingReport;
	}

	/**
	 * @return the logonCorrectionTrackingReport
	 */
	public ArrayList getLogonCorrectionTrackingReport() {
		return logonCorrectionTrackingReport;
	}

	/**
	 * @param logonCorrectionTrackingReport
	 *            the logonCorrectionTrackingReport to set
	 */
	public void setLogonCorrectionTrackingReport(
			ArrayList logonCorrectionTrackingReport) {
		this.logonCorrectionTrackingReport = logonCorrectionTrackingReport;
	}

	/**
	 * @return the logOffCorrectionTrackingReport
	 */
	public ArrayList getLogOffCorrectionTrackingReport() {
		return logOffCorrectionTrackingReport;
	}

	/**
	 * @param logOffCorrectionTrackingReport
	 *            the logOffCorrectionTrackingReport to set
	 */
	public void setLogOffCorrectionTrackingReport(
			ArrayList logOffCorrectionTrackingReport) {
		this.logOffCorrectionTrackingReport = logOffCorrectionTrackingReport;
	}

	/**
	 * @return the qtyCompletedCorrectionTrackingReport
	 */
	public ArrayList getQtyCompletedCorrectionTrackingReport() {
		return qtyCompletedCorrectionTrackingReport;
	}

	/**
	 * @param qtyCompletedCorrectionTrackingReport
	 *            the qtyCompletedCorrectionTrackingReport to set
	 */
	public void setQtyCompletedCorrectionTrackingReport(
			ArrayList qtyCompletedCorrectionTrackingReport) {
		this.qtyCompletedCorrectionTrackingReport = qtyCompletedCorrectionTrackingReport;
	}

	/**
	 * @return the assetNumberCorrectionTrackingReport
	 */
	public ArrayList getAssetNumberCorrectionTrackingReport() {
		return assetNumberCorrectionTrackingReport;
	}

	/**
	 * @param assetNumberCorrectionTrackingReport
	 *            the assetNumberCorrectionTrackingReport to set
	 */
	public void setAssetNumberCorrectionTrackingReport(
			ArrayList assetNumberCorrectionTrackingReport) {
		this.assetNumberCorrectionTrackingReport = assetNumberCorrectionTrackingReport;
	}

	/**
	 * @return the dtFromDate
	 */
	public Date getDtFromDate() {
		return dtFromDate;
	}

	/**
	 * @param dtFromDate
	 *            the dtFromDate to set
	 */
	public void setDtFromDate(Date dtFromDate) {
		this.dtFromDate = dtFromDate;
	}

	/**
	 * @return the dtToDate
	 */
	public Date getDtToDate() {
		return dtToDate;
	}

	/**
	 * @param dtToDate
	 *            the dtToDate to set
	 */
	public void setDtToDate(Date dtToDate) {
		this.dtToDate = dtToDate;
	}

	public ArrayList getCompletedSequenceCorrectionTrackingReport() {
		return completedSequenceCorrectionTrackingReport;
	}

	public void setCompletedSequenceCorrectionTrackingReport(
			ArrayList completedSequenceCorrectionTrackingReport) {
		this.completedSequenceCorrectionTrackingReport = completedSequenceCorrectionTrackingReport;
	}

	public ArrayList getAddedSequenceCorrectionTrackingReport() {
		return addedSequenceCorrectionTrackingReport;
	}

	public void setAddedSequenceCorrectionTrackingReport(
			ArrayList addedSequenceCorrectionTrackingReport) {
		this.addedSequenceCorrectionTrackingReport = addedSequenceCorrectionTrackingReport;
	}

	public ArrayList getMultipleCorrectionTrackingReport() {
		return multipleCorrectionTrackingReport;
	}

	public void setMultipleCorrectionTrackingReport(
			ArrayList multipleCorrectionTrackingReport) {
		this.multipleCorrectionTrackingReport = multipleCorrectionTrackingReport;
	}

	public ArrayList getActivityCorrectionTrackingReport() {
		return activityCorrectionTrackingReport;
	}

	public void setActivityCorrectionTrackingReport(
			ArrayList activityCorrectionTrackingReport) {
		this.activityCorrectionTrackingReport = activityCorrectionTrackingReport;
	}
}