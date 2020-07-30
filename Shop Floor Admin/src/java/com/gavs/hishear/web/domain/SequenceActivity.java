package com.gavs.hishear.web.domain;

import java.util.Date;

// used for Correction interface Modify MO

public class SequenceActivity {

	private int activityLogNumber;
	private int jobActivityNumber;
	private String oldActivity;
	private String oldEmployeeNumber;
	private String oldEmployeeName;
	private String oldActivityStatus;
	private String oldAssetNumber;
	private String oldAssetDesc;
	private int oldQtyCompleted;
	private Date oldLogonDate;
	private Date oldLogoffDate;
	private double oldLaborSetupTime;
	private double oldLaborRunTime;
	private double oldMachineSetupTime;
	private double oldMachineRunTime;

	private String activity;
	private String employeeNumber;
	private String employeeName;
	private String activityStatus;
	private String assetNumber;
	private String assetDesc;
	private int qtyCompleted;
	private Date logonDate;
	private Date logoffDate;
	private String completeSequenceFlag;
	private double laborSetupTime;
	private double laborRunTime;
	private double machineSetupTime;
	private double machineRunTime;
	private String breakActivityCode;

	private Date breakStart;
	private Date breakEnd;
	private Date startDate;
	private Date endDate;
	private boolean breakFlag;
	private boolean retoolFlag;

	private int activityBreakNumber;
	private int activityRetoolNumber;
	private boolean actChangeFlag;

	private int qtyAssigned;
	private Date kronosPunchIn;
	private Date kronosPunchOut;
	private double PPH;
	private String error;
	private boolean newFlag;
	private Date routerDate;
	private String stampNo;

	private String type;
	private String reasonCode;

	private String multipleCorrectionFlag = "";

	public int getActivityLogNumber() {
		return activityLogNumber;
	}

	public void setActivityLogNumber(int activityLogNumber) {
		this.activityLogNumber = activityLogNumber;
	}

	public String getOldActivity() {
		return oldActivity;
	}

	public void setOldActivity(String activity) {
		this.oldActivity = activity;
	}

	public String getOldEmployeeNumber() {
		return oldEmployeeNumber;
	}

	public void setOldEmployeeNumber(String employeeNumber) {
		this.oldEmployeeNumber = employeeNumber;
	}

	public String getOldEmployeeName() {
		return oldEmployeeName;
	}

	public void setOldEmployeeName(String employeeName) {
		this.oldEmployeeName = employeeName;
	}

	public String getOldActivityStatus() {
		return oldActivityStatus;
	}

	public void setOldActivityStatus(String activityStatus) {
		this.oldActivityStatus = activityStatus;
	}

	public String getOldAssetNumber() {
		return oldAssetNumber;
	}

	public void setOldAssetNumber(String assetNumber) {
		this.oldAssetNumber = assetNumber;
	}

	public String getOldAssetDesc() {
		return oldAssetDesc;
	}

	public void setOldAssetDesc(String assetDesc) {
		this.oldAssetDesc = assetDesc;
	}

	public double getQtyAssigned() {
		return qtyAssigned;
	}

	public void setQtyAssigned(int qtyAssigned) {
		this.qtyAssigned = qtyAssigned;
	}

	public int getOldQtyCompleted() {
		return this.oldQtyCompleted;
	}

	public void setOldQtyCompleted(int qtyCompleted) {
		this.oldQtyCompleted = qtyCompleted;
	}

	public Date getOldLogonDate() {
		return oldLogonDate;
	}

	public void setOldLogonDate(Date logonDate) {
		this.oldLogonDate = logonDate;
	}

	public Date getOldLogoffDate() {
		return oldLogoffDate;
	}

	public void setOldLogoffDate(Date logoffDate) {
		this.oldLogoffDate = logoffDate;
	}

	public Date getKronosPunchIn() {
		return kronosPunchIn;
	}

	public void setKronosPunchIn(Date kronosPunchIn) {
		this.kronosPunchIn = kronosPunchIn;
	}

	public Date getKronosPunchOut() {
		return kronosPunchOut;
	}

	public void setKronosPunchOut(Date kronosPunchOut) {
		this.kronosPunchOut = kronosPunchOut;
	}

	public double getPPH() {
		return PPH;
	}

	public void setPPH(double pPH) {
		PPH = pPH;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	/**
	 * @param newEmployeeName
	 *            the newEmployeeName to set
	 */
	public void setEmployeeName(String newEmployeeName) {
		this.employeeName = newEmployeeName;
	}

	/**
	 * @return the newEmployeeName
	 */
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * @param newActivity
	 *            the newActivity to set
	 */
	public void setActivity(String newActivity) {
		this.activity = newActivity;
	}

	/**
	 * @return the newActivity
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param newActivityStatus
	 *            the newActivityStatus to set
	 */
	public void setActivityStatus(String newActivityStatus) {
		this.activityStatus = newActivityStatus;
	}

	/**
	 * @return the newActivityStatus
	 */
	public String getActivityStatus() {
		return activityStatus;
	}

	/**
	 * @param newAssetNumber
	 *            the newAssetNumber to set
	 */
	public void setAssetNumber(String newAssetNumber) {
		this.assetNumber = newAssetNumber;
	}

	/**
	 * @return the newAssetNumber
	 */
	public String getAssetNumber() {
		return assetNumber;
	}

	/**
	 * @param newAssetDesc
	 *            the newAssetDesc to set
	 */
	public void setAssetDesc(String newAssetDesc) {
		this.assetDesc = newAssetDesc;
	}

	/**
	 * @return the newAssetDesc
	 */
	public String getAssetDesc() {
		return assetDesc;
	}

	/**
	 * @param newQtyCompleted
	 *            the newQtyCompleted to set
	 */
	public void setQtyCompleted(int newQtyCompleted) {
		this.qtyCompleted = newQtyCompleted;
	}

	/**
	 * @return the newQtyCompleted
	 */
	public int getQtyCompleted() {
		return qtyCompleted;
	}

	/**
	 * @param newLogonDate
	 *            the newLogonDate to set
	 */
	public void setLogonDate(Date newLogonDate) {
		this.logonDate = newLogonDate;
	}

	/**
	 * @return the newLogonDate
	 */
	public Date getLogonDate() {
		return logonDate;
	}

	/**
	 * @param newLogoffDate
	 *            the newLogoffDate to set
	 */
	public void setLogoffDate(Date newLogoffDate) {
		this.logoffDate = newLogoffDate;
	}

	/**
	 * @return the newLogoffDate
	 */
	public Date getLogoffDate() {
		return logoffDate;
	}

	public void addError(String error) {
		this.error = error;

	}

	public String getError() {
		return error;
	}

	/**
	 * @param completeSequenceFlag
	 *            the completeSequenceFlag to set
	 */
	public void setCompleteSequenceFlag(String completeSequenceFlag) {
		this.completeSequenceFlag = completeSequenceFlag;
	}

	/**
	 * @return the completeSequenceFlag
	 */
	public String getCompleteSequenceFlag() {
		return completeSequenceFlag;
	}

	/**
	 * @return the laborSetupTime
	 */
	public double getOldLaborSetupTime() {
		return oldLaborSetupTime;
	}

	/**
	 * @param laborSetupTime
	 *            the laborSetupTime to set
	 */
	public void setOldLaborSetupTime(double laborSetupTime) {
		this.oldLaborSetupTime = laborSetupTime;
	}

	/**
	 * @return the laborRunTime
	 */
	public double getOldLaborRunTime() {
		return oldLaborRunTime;
	}

	/**
	 * @param laborRunTime
	 *            the laborRunTime to set
	 */
	public void setOldLaborRunTime(double laborRunTime) {
		this.oldLaborRunTime = laborRunTime;
	}

	/**
	 * @return the machineSetupTime
	 */
	public double getOldMachineSetupTime() {
		return oldMachineSetupTime;
	}

	/**
	 * @param machineSetupTime
	 *            the machineSetupTime to set
	 */
	public void setOldMachineSetupTime(double machineSetupTime) {
		this.oldMachineSetupTime = machineSetupTime;
	}

	/**
	 * @return the machineRunTime
	 */
	public double getOldMachineRunTime() {
		return oldMachineRunTime;
	}

	/**
	 * @param machineRunTime
	 *            the machineRunTime to set
	 */
	public void setOldMachineRunTime(double machineRunTime) {
		this.oldMachineRunTime = machineRunTime;
	}

	/**
	 * @return the newLaborSetupTime
	 */
	public double getLaborSetupTime() {
		return laborSetupTime;
	}

	/**
	 * @param newLaborSetupTime
	 *            the newLaborSetupTime to set
	 */
	public void setLaborSetupTime(double newLaborSetupTime) {
		this.laborSetupTime = newLaborSetupTime;
	}

	/**
	 * @return the newLaborRunTime
	 */
	public double getLaborRunTime() {
		return laborRunTime;
	}

	/**
	 * @param newLaborRunTime
	 *            the newLaborRunTime to set
	 */
	public void setLaborRunTime(double newLaborRunTime) {
		this.laborRunTime = newLaborRunTime;
	}

	/**
	 * @return the newMachineSetupTime
	 */
	public double getMachineSetupTime() {
		return machineSetupTime;
	}

	/**
	 * @param newMachineSetupTime
	 *            the newMachineSetupTime to set
	 */
	public void setMachineSetupTime(double newMachineSetupTime) {
		this.machineSetupTime = newMachineSetupTime;
	}

	/**
	 * @return the newMachineRunTime
	 */
	public double getMachineRunTime() {
		return machineRunTime;
	}

	/**
	 * @param newMachineRunTime
	 *            the newMachineRunTime to set
	 */
	public void setMachineRunTime(double newMachineRunTime) {
		this.machineRunTime = newMachineRunTime;
	}

	/**
	 * @param breakStart
	 *            the breakStart to set
	 */
	public void setBreakStart(Date breakStart) {
		this.breakStart = breakStart;
	}

	/**
	 * @return the breakStart
	 */
	public Date getBreakStart() {
		return breakStart;
	}

	/**
	 * @param breakEnd
	 *            the breakEnd to set
	 */
	public void setBreakEnd(Date breakEnd) {
		this.breakEnd = breakEnd;
	}

	/**
	 * @return the breakEnd
	 */
	public Date getBreakEnd() {
		return breakEnd;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param breakFlag
	 *            the breakFlag to set
	 */
	public void setBreakFlag(boolean breakFlag) {
		this.breakFlag = breakFlag;
	}

	/**
	 * @return the breakFlag
	 */
	public boolean isBreakFlag() {
		return breakFlag;
	}

	/**
	 * @param retoolFlag
	 *            the retoolFlag to set
	 */
	public void setRetoolFlag(boolean retoolFlag) {
		this.retoolFlag = retoolFlag;
	}

	/**
	 * @return the retoolFlag
	 */
	public boolean isRetoolFlag() {
		return retoolFlag;
	}

	/**
	 * @param jobActivityNumber
	 *            the jobActivityNumber to set
	 */
	public void setJobActivityNumber(int jobActivityNumber) {
		this.jobActivityNumber = jobActivityNumber;
	}

	/**
	 * @return the jobActivityNumber
	 */
	public int getJobActivityNumber() {
		return jobActivityNumber;
	}

	/**
	 * @param breakActivityCode
	 *            the breakActivityCode to set
	 */
	public void setBreakActivityCode(String breakActivityCode) {
		this.breakActivityCode = breakActivityCode;
	}

	/**
	 * @return the breakActivityCode
	 */
	public String getBreakActivityCode() {
		return breakActivityCode;
	}

	/**
	 * @param newFlag
	 *            the newFlag to set
	 */
	public void setNewFlag(boolean newFlag) {
		this.newFlag = newFlag;
	}

	/**
	 * @return the newFlag
	 */
	public boolean isNewFlag() {
		return newFlag;
	}

	/**
	 * @param routerDate
	 *            the routerDate to set
	 */
	public void setRouterDate(Date routerDate) {
		this.routerDate = routerDate;
	}

	/**
	 * @return the routerDate
	 */
	public Date getRouterDate() {
		return routerDate;
	}

	/**
	 * @param stampNo
	 *            the stampNo to set
	 */
	public void setStampNo(String stampNo) {
		this.stampNo = stampNo;
	}

	/**
	 * @return the stampNo
	 */
	public String getStampNo() {
		return stampNo;
	}

	/**
	 * @param activityBreakNumber
	 *            the activityBreakNumber to set
	 */
	public void setActivityBreakNumber(int activityBreakNumber) {
		this.activityBreakNumber = activityBreakNumber;
	}

	/**
	 * @return the activityBreakNumber
	 */
	public int getActivityBreakNumber() {
		return activityBreakNumber;
	}

	/**
	 * @param activityRetoolNumber
	 *            the activityRetoolNumber to set
	 */
	public void setActivityRetoolNumber(int activityRetoolNumber) {
		this.activityRetoolNumber = activityRetoolNumber;
	}

	/**
	 * @return the activityRetoolNumber
	 */
	public int getActivityRetoolNumber() {
		return activityRetoolNumber;
	}

	/**
	 * @param actChangeFlag
	 *            the actChangeFlag to set
	 */
	public void setActChangeFlag(boolean actChangeFlag) {
		this.actChangeFlag = actChangeFlag;
	}

	/**
	 * @return the actChangeFlag
	 */
	public boolean isActChangeFlag() {
		return actChangeFlag;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param reasonCode
	 *            the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	/**
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}

	/**
	 * @return the multipleCorrectionFlag
	 */
	public String getMultipleCorrectionFlag() {
		return multipleCorrectionFlag;
	}

	/**
	 * @param multipleCorrectionFlag
	 *            the multipleCorrectionFlag to set
	 */
	public void setMultipleCorrectionFlag(String multipleCorrectionFlag) {
		this.multipleCorrectionFlag = multipleCorrectionFlag;
	}
}