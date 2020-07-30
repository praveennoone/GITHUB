// File:         SequenceDAO.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gavs.hishear.m3interface.dto.MVXDTAMI;
import com.gavs.hishear.m3interface.dto.POTransaction;
import com.gavs.hishear.web.domain.Activity;
import com.gavs.hishear.web.domain.Facility;
import com.gavs.hishear.web.domain.FourthShiftData;
import com.gavs.hishear.web.domain.ManufacturingOrder;
import com.gavs.hishear.web.domain.MasterData;
import com.gavs.hishear.web.domain.Sequence;
import com.gavs.hishear.web.domain.SequenceActivity;
import com.gavs.hishear.web.domain.User;
import com.gavs.hishear.web.domain.WorkCenter;

/**
 * The Interface SequenceDAO.
 */
public interface SequenceDAO {

	/**
	 * Gets the completed sequences.
	 * 
	 * @param sequence
	 *            the sequence
	 * @return the completed sequences
	 */
	public ArrayList<Sequence> getCompletedSequences(Sequence sequence);

	/**
	 * Gets the sequences.
	 * 
	 * @param sequence
	 *            the sequence
	 * @return the sequences
	 */
	public ArrayList<Sequence> getSequences(Sequence sequence);

	/**
	 * Sets the input flag.
	 * 
	 * @param sequence
	 *            the new input flag
	 */
	public void setInputFlag(Sequence sequence);

	/**
	 * Check input flag.
	 * 
	 * @param sequence
	 *            the sequence
	 */
	public void checkInputFlag(Sequence sequence);

	/**
	 * Gets the mo line.
	 * 
	 * @param sequence
	 *            the sequence
	 * @return the mo line
	 */
	public ArrayList<Sequence> getMoLine(Sequence sequence);

	/**
	 * Gets the sequence details.
	 * 
	 * @param dto
	 *            the dto
	 * @return the sequence details
	 */
	public ArrayList getSequenceDetails(Sequence dto);

	/**
	 * Gets the sequence details for pick.
	 * 
	 * @param dto
	 *            the dto
	 * @return the sequence details for pick
	 */
	public ArrayList getSequenceDetailsForPick(Sequence dto);

	/**
	 * Complete sequence.
	 * 
	 * @param jobNumber
	 *            the job number
	 * @param query
	 *            the query
	 */
	public void completeSequence(String jobNumber, String query);

	/**
	 * Complete sequence activities.
	 * 
	 * @param jobNumber
	 *            the job number
	 * @param query
	 *            the query
	 */
	public void completeSequenceActivities(String jobNumber, String query);

	/**
	 * Gets the pending orders.
	 * 
	 * @param sequence
	 *            the sequence
	 * @return the pending orders
	 */
	public List getPendingOrders(Sequence sequence);

	/*
	 * public List getFourthShiftData(Sequence dto); public void
	 * insertFourthShiftData(FourthShiftData fourthShiftData);
	 */
	/**
	 * Creates the and complete sequence.
	 * 
	 * @param dto
	 *            the dto
	 * @param username
	 *            the username
	 */
	public void createAndCompleteSequence(Sequence dto, String username);

	/**
	 * Gets the bOM activities for mo line.
	 * 
	 * @param dto
	 *            the dto
	 * @return the bOM activities for mo line
	 */
	public List getBOMActivitiesForMOLine(Sequence dto);

	/**
	 * Gets the previous completed qty.
	 * 
	 * @param dto
	 *            the dto
	 * @param query
	 *            the query
	 * @return the previous completed qty
	 */
	public double getPreviousCompletedQty(Sequence dto, String query);

	/**
	 * Gets the local activities for mo line seq.
	 * 
	 * @param dto
	 *            the dto
	 * @param query
	 *            the query
	 * @return the local activities for mo line seq
	 */
	public List getLocalActivitiesForMOLineSeq(Sequence dto, String query);

	/**
	 * Gets the user.
	 * 
	 * @param userId
	 *            the user id
	 * @param query
	 *            the query
	 * @return the user
	 */
	public User getUser(String userId, String query);

	/**
	 * Creates the audit log.
	 * 
	 * @param dto
	 *            the dto
	 * @param query
	 *            the query
	 * @return true, if successful
	 */
	public boolean createAuditLog(Sequence dto, String query);

	/**
	 * Log sequence pick.
	 * 
	 * @param dto
	 *            the dto
	 * @param username
	 *            the username
	 * @param query
	 *            the query
	 * @param freshSequence
	 *            the fresh sequence
	 */
	public void logSequencePick(Sequence dto, String username, String query,
			boolean freshSequence);

	/**
	 * Gets the fourth shift data.
	 * 
	 * @param moNumber
	 *            the mo number
	 * @param lineNumber
	 *            the line number
	 * @param sequenceNumber
	 *            the sequence number
	 * @param qtyCompleted
	 *            the qty completed
	 * @param query
	 *            the query
	 * @return the fourth shift data
	 */
	public List<FourthShiftData> getFourthShiftData(String moNumber,
			String lineNumber, String sequenceNumber, double qtyCompleted,
			String query);

	/**
	 * Gets the fourth shift data for pick reversal.
	 * 
	 * @param moNumber
	 *            the mo number
	 * @param lineNumber
	 *            the line number
	 * @param sequenceNumber
	 *            the sequence number
	 * @param qtyCompleted
	 *            the qty completed
	 * @param query
	 *            the query
	 * @return the fourth shift data for pick reversal
	 */
	public List<FourthShiftData> getFourthShiftDataForPickReversal(
			String moNumber, String lineNumber, String sequenceNumber,
			double qtyCompleted, String query);

	/**
	 * Insert fourth shift data.
	 * 
	 * @param fourthShiftData
	 *            the fourth shift data
	 * @param query
	 *            the query
	 */
	public void insertFourthShiftData(FourthShiftData fourthShiftData,
			String query);

	/**
	 * Check dept existence.
	 * 
	 * @param deptNumber
	 *            the dept number
	 * @param query
	 *            the query
	 * @return true, if successful
	 */
	public boolean checkDeptExistence(String deptNumber, String query);

	/**
	 * Gets the assets.
	 * 
	 * @param query
	 *            the query
	 * @param workCenter
	 *            the work center
	 * @param facility
	 *            the facility
	 * @return the assets
	 */
	public List getAssets(String query, String workCenter, String facility);

	/**
	 * Gets the reasons.
	 * 
	 * @param query
	 *            the query
	 * @return the reasons
	 */
	public ArrayList<Sequence> getReasons(String query);

	/**
	 * Gets the sequence details for modification.
	 * 
	 * @param dto
	 *            the dto
	 * @return the sequence details for modification
	 */
	public ArrayList getSequenceDetailsForModification(Sequence dto);

	/**
	 * Update asset number.
	 * 
	 * @param userName
	 *            the user name
	 * @param dto
	 *            the dto
	 * @param query
	 *            the query
	 */
	public void updateAssetNumber(String userName, Sequence dto, String query);

	/**
	 * Update reported qty.
	 * 
	 * @param userName
	 *            the user name
	 * @param dto
	 *            the dto
	 * @param query
	 *            the query
	 */
	public void updateReportedQty(String userName, Sequence dto, String query);

	/**
	 * Log original data.
	 * 
	 * @param userName
	 *            the user name
	 * @param dto
	 *            the dto
	 * @param query
	 *            the query
	 * @param changeFlag
	 *            the change flag
	 * @param reasonCode
	 *            the reason code
	 * @param comments
	 *            the comments
	 */
	public void logOriginalData(String userName, Sequence dto, String query,
			String changeFlag, int reasonCode, String comments);

	/**
	 * Gets the error activities.
	 * 
	 * @param moNumber
	 *            the mo number
	 * @param query
	 *            the query
	 * @return the error activities
	 */
	public ArrayList<Activity> getErrorActivities(String moNumber, String query);

	/*
	 * public void PMS70WriteSetRptOperation(String moNumber, int
	 * completedQuantity, double labourSetupTime, double labourRunTime, double
	 * machineRunTime, double machineSetupTime, String sequenceNumber, String
	 * employeeNumber, String manualCompletionFlag, boolean isNegativeReporting,
	 * String errorLogQuery) throws Exception;
	 */

	/**
	 * PM z070 write set rpt operation.
	 * 
	 * @param moNumber
	 *            the mo number
	 * @param completedQuantity
	 *            the completed quantity
	 * @param timeToReport
	 *            the time to report
	 * @param sequenceNumber
	 *            the sequence number
	 * @param employeeNumber
	 *            the employee number
	 * @param manualCompletionFlag
	 *            the manual completion flag
	 * @param isNegativeReporting
	 *            the is negative reporting
	 * @param workCenterCapacity
	 *            the work center capacity
	 * @param priceTimeQuantity
	 *            the price time quantity
	 * @param activityCode
	 *            the activity code
	 * @param weightToReport
	 *            the weight to report
	 * @param errorLogQuery
	 *            the error log query
	 * @param userName
	 *            the user name
	 * @throws Exception
	 *             the exception
	 */
	public void PMZ070WriteSetRptOperation(String moNumber,
			int completedQuantity, double timeToReport, String sequenceNumber,
			String employeeNumber, String manualCompletionFlag,
			boolean isNegativeReporting, int workCenterCapacity,
			double priceTimeQuantity, String activityCode,
			double weightToReport, String errorLogQuery, String userName,
			String query) throws Exception;

	// public ArrayList<Sequence> getExistingErrorLogForMONumber(Sequence
	// currentActivity,String query);

	/**
	 * Gets the mO details.
	 * 
	 * @param query
	 *            the query
	 * @return the mO details
	 */
	public ArrayList<Sequence> getMODetails(String query);

	/**
	 * Update activity log.
	 * 
	 * @param modifiedSequence
	 *            the modified sequence
	 * @param selectedSequence
	 *            the selected sequence
	 * @param query
	 *            the query
	 */
	public void updateActivityLog(Sequence modifiedSequence,
			Sequence selectedSequence, String query);

	/**
	 * Insert rejected activity.
	 * 
	 * @param selectedSequence
	 *            the selected sequence
	 * @param query
	 *            the query
	 */
	public void insertRejectedActivity(Sequence selectedSequence, String query);

	/**
	 * Do physical allocation.
	 * 
	 * @param itemNumber
	 *            the item number
	 * @param facility
	 *            the facility
	 */
	// Begin WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	// void doPhysicalAllocation(String itemNumber, String facility);
	// End WO# 27639 - Moving Static links to dynamic - Pinky - Infosys - 23
	// June 2011
	/**
	 * Inspected item putaway using pm s130.
	 * 
	 * @param sequence
	 *            the sequence
	 * @param errorLogQuery
	 *            the error log query
	 * @param userName
	 *            the user name
	 * @throws Exception
	 *             the exception
	 */
	void inspectedItemPutawayUsingPMS130(Sequence sequence,
			String errorLogQuery, String userName, String m3RequestQuery)
			throws Exception;

	/**
	 * Approve inspection result using li s200.
	 * 
	 * @param sequence
	 *            the sequence
	 * @param errorLogQuery
	 *            the error log query
	 * @param userName
	 *            the user name
	 */
	void approveInspectionResultUsingLIS200(Sequence sequence,
			String errorLogQuery, String userName, String m3RequestQuery);

	/**
	 * PM s50 write set rpt receipt.
	 * 
	 * @param sequence
	 *            the sequence
	 * @param errorLogQuery
	 *            the error log query
	 * @param userName
	 *            the user name
	 */
	void PMS50WriteSetRptReceipt(Sequence sequence, String errorLogQuery,
			String userName, String query);

	/**
	 * Update error log completion.
	 * 
	 * @param minutsProcessed
	 *            the minuts processed
	 * @param userName
	 *            the user name
	 * @param activityLogNumber
	 *            the activity log number
	 * @param query
	 *            the query
	 * @param query2
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	boolean updateErrorLogCompletion(String flag, String user,
			String jobNumber, String query) throws Exception;

	/**
	 * void.
	 * 
	 * @param sequence
	 *            the sequence
	 * @param query
	 *            the query
	 */
	public void closeAllOpenedActivities(Sequence sequence, String query);

	/**
	 * void.
	 * 
	 * @param sequence
	 *            the sequence
	 * @param query
	 *            the query
	 */
	public void updateMOStatusAsCompleted(Sequence sequence, String query);

	/**
	 * ArrayList<Sequence>.
	 * 
	 * @param query
	 *            the query
	 * @param facility
	 *            the facility
	 * @return the error mo numbers
	 */
	public ArrayList<Sequence> getErrorMoNumbers(String query, String facility);

	/**
	 * Method to get facilities based on the logged in user's division.
	 * 
	 * @param division
	 *            the division
	 * @return ArrayList<Facility>
	 * @throws Exception
	 */
	public ArrayList<Facility> getFacilities(String division) throws Exception;

	/**
	 * PM z70 write set rpt operation.
	 * 
	 * @param moNumber
	 *            the mo number
	 * @param completedQuantity
	 *            the completed quantity
	 * @param sequenceNumber
	 *            the sequence number
	 * @param employeeNumber
	 *            the employee number
	 * @param manualCompletionFlag
	 *            the manual completion flag
	 * @param usedLabourRunTime
	 *            the used labour run time
	 * @param priceTimeQuantity
	 *            the price time quantity
	 * @param workCenterCapacity
	 *            the work center capacity
	 * @param activityCode
	 *            the activity code
	 * @throws Exception
	 *             the exception
	 */
	// Begin - InsertM3Request call - VP - Infosys - May 11, 2011
	// Adding the error log and M3 Request query
	public void PMZ70WriteSetRptOperation(String moNumber,
			int completedQuantity, String sequenceNumber,
			String employeeNumber, String manualCompletionFlag,
			String usedLabourRunTime, double priceTimeQuantity,
			int workCenterCapacity, String activityCode, String m3Query,
			String errorQuery, String userName) throws Exception;

	// End - InsertM3Request call - VP - Infosys - May 11, 2011
	/**
	 * Method to get Operation details and activity details for that operation
	 * using MO number and facility.
	 * 
	 * @param dto
	 *            the dto
	 * @param qry
	 *            the qry
	 * @return void
	 * @throws Exception
	 *             the exception
	 */

	public void fetchMOSeqActDetails(ManufacturingOrder dto, String qry)
			throws Exception;

	/**
	 * Fetch asset details.
	 * 
	 * @param dto
	 *            the dto
	 * @param qry
	 *            the qry
	 * @throws Exception
	 *             the exception
	 */
	public void fetchAssetDetails(ManufacturingOrder dto, String qry)
			throws Exception;

	/**
	 * Fetch employee details.
	 * 
	 * @param dto
	 *            the dto
	 * @param qry
	 *            the qry
	 * @throws Exception
	 *             the exception
	 */
	public void fetchEmployeeDetails(ManufacturingOrder dto, String qry)
			throws Exception;

	/**
	 * Gets the employee login details.
	 * 
	 * @param dto
	 *            the dto
	 * @param qry
	 *            the qry
	 * @return the employee login details
	 * @throws Exception
	 *             the exception
	 */
	public void getEmployeeLoginDetails(ManufacturingOrder dto, String qry)
			throws Exception;

	/**
	 * Validate employee.
	 * 
	 * @param qry
	 *            the qry
	 * @param newEmployeeNumber
	 *            the new employee number
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String validateEmployee(String qry, String newEmployeeNumber)
			throws Exception;

	/**
	 * Insert activity log.
	 * 
	 * @param seqAct
	 *            the seq act
	 * @param qry
	 *            the qry
	 * @throws Exception
	 *             the exception
	 */
	public void insertActivityLog(SequenceActivity seqAct, String qry)
			throws Exception;

	/**
	 * Update activity log.
	 * 
	 * @param seqAct
	 *            the seq act
	 * @param qry
	 *            the qry
	 * @throws Exception
	 *             the exception
	 */
	public void updateActivityLog(SequenceActivity seqAct, String qry)
			throws Exception;

	/**
	 * Delete activity log.
	 * 
	 * @param seqAct
	 *            the seq act
	 * @param qry
	 *            the qry
	 * @throws Exception
	 *             the exception
	 */
	public void deleteActivityLog(SequenceActivity seqAct, String qry)
			throws Exception;

	/**
	 * Insert activity retool.
	 * 
	 * @param seqAct
	 *            the seq act
	 * @param qry
	 *            the qry
	 * @throws Exception
	 *             the exception
	 */
	public void insertActivityRetool(SequenceActivity seqAct, String qry)
			throws Exception;

	/**
	 * Update activity retool.
	 * 
	 * @param seqAct
	 *            the seq act
	 * @param qry
	 *            the qry
	 * @throws Exception
	 *             the exception
	 */
	public void updateActivityRetool(SequenceActivity seqAct, String qry)
			throws Exception;

	/**
	 * Delete activity retool.
	 * 
	 * @param seqAct
	 *            the seq act
	 * @param qry
	 *            the qry
	 * @throws Exception
	 *             the exception
	 */
	public void deleteActivityRetool(SequenceActivity seqAct, String qry)
			throws Exception;

	/**
	 * Insert activity break.
	 * 
	 * @param seqAct
	 *            the seq act
	 * @param qry
	 *            the qry
	 * @throws Exception
	 *             the exception
	 */
	public void insertActivityBreak(SequenceActivity seqAct, String qry)
			throws Exception;

	/**
	 * Update activity break.
	 * 
	 * @param seqAct
	 *            the seq act
	 * @param qry
	 *            the qry
	 * @throws Exception
	 *             the exception
	 */
	public void updateActivityBreak(SequenceActivity seqAct, String qry)
			throws Exception;

	/**
	 * Delete activity break.
	 * 
	 * @param seqAct
	 *            the seq act
	 * @param qry
	 *            the qry
	 * @throws Exception
	 *             the exception
	 */
	public void deleteActivityBreak(SequenceActivity seqAct, String qry)
			throws Exception;

	// START Correction Interface Issue fix
	/**
	 * Update sequence activity.
	 * 
	 * @param seqAct
	 *            the seq act
	 * @param qry
	 *            the qry
	 * @param status
	 *            the status
	 * @throws Exception
	 *             the exception
	 */
	public void updateSequenceActivity(SequenceActivity seqAct, String qry,
			String status) throws Exception;

	// END Correction Interface Issue fix
	/**
	 * Update order detail.
	 * 
	 * @param seq
	 *            the seq
	 * @param qry
	 *            the qry
	 * @throws Exception
	 *             the exception
	 */
	public void updateOrderDetail(Sequence seq, String qry) throws Exception;

	/**
	 * Insert order master.
	 * 
	 * @param dto
	 *            the dto
	 * @param qry
	 *            the qry
	 * @throws Exception
	 *             the exception
	 */
	public void insertOrderMaster(ManufacturingOrder dto, String qry)
			throws Exception;

	/**
	 * Insert order detail.
	 * 
	 * @param dto
	 *            the dto
	 * @param qry
	 *            the qry
	 * @throws Exception
	 *             the exception
	 */
	public void insertOrderDetail(ManufacturingOrder dto, String qry)
			throws Exception;

	// START Correction Interface Issue fix
	/**
	 * Insert sequence activity.
	 * 
	 * @param seq
	 *            the seq
	 * @param qry
	 *            the qry
	 * @param status
	 *            the status
	 * @throws Exception
	 *             the exception
	 */
	public void insertSequenceActivity(Sequence seq, String qry, String status)
			throws Exception;

	// END Correction Interface Issue fix
	/**
	 * Gets the pause reasons.
	 * 
	 * @param qry
	 *            the qry
	 * @return the pause reasons
	 * @throws Exception
	 *             the exception
	 */
	public ArrayList<MasterData> getPauseReasons(String qry) throws Exception;

	/**
	 * Creates the correction log.
	 * 
	 * @param dto
	 *            the dto
	 * @param qry
	 *            the qry
	 * @throws Exception
	 *             the exception
	 */
	public void createCorrectionLog(ManufacturingOrder dto, String qry)
			throws Exception;

	/**
	 * Gets the job activity number.
	 * 
	 * @param seq
	 *            the seq
	 * @param qry
	 *            the qry
	 * @return the job activity number
	 * @throws Exception
	 *             the exception
	 */
	public boolean getJobActivityNumber(Sequence seq, String qry)
			throws Exception;

	/**
	 * Fetch the Department Based on the WorkCenter and Facility.
	 * 
	 * @param facility
	 *            the facility
	 * @param workCenter
	 *            the work center
	 * @return the dept by work center
	 * @throws Exception
	 *             the exception
	 */
	public WorkCenter getDeptByWorkCenter(String facility, String workCenter)
			throws Exception;

	/**
	 * Gets the employee kronos logon latest.
	 * 
	 * @param dto
	 *            the dto
	 * @param query
	 *            the query
	 * @return the employee kronos logon latest
	 */
	Date[] getEmployeeKronosLogonLatest(ManufacturingOrder dto, String query);

	/**
	 * Gets the employee kronos logon by login date.
	 * 
	 * @param dto
	 *            the dto
	 * @param query
	 *            the query
	 * @return the employee kronos logon by login date
	 */
	Date[] getEmployeeKronosLogonByLoginDate(ManufacturingOrder dto,
			String query);

	/**
	 * Gets the employee kronos logon by logout date.
	 * 
	 * @param dto
	 *            the dto
	 * @param query
	 *            the query
	 * @return the employee kronos logon by logout date
	 */
	Date[] getEmployeeKronosLogonByLogoutDate(ManufacturingOrder dto,
			String query);

	/**
	 * Update activity complete status.
	 * 
	 * @param query
	 *            the query
	 * @param jobNumber
	 *            the job number
	 */
	public void updateActivityCompleteStatus(String query, int jobNumber);

	/**
	 * Checks if is pPH error exist.
	 * 
	 * @param query
	 *            the query
	 * @param moNumber
	 *            the mo number
	 * @return true, if is pPH error exist
	 */
	public boolean isPPHErrorExist(String query, String moNumber);

	/**
	 * Gets the mO details from m3.
	 * 
	 * @param moNumber
	 *            the mo number
	 * @return the mO details from m3
	 * @throws Exception
	 *             the exception
	 */
	public MVXDTAMI getMODetailsFromM3(String moNumber) throws Exception;

	/**
	 * Gets the work center capacity.
	 * 
	 * @param workCenterNumber
	 *            the work center number
	 * @param moNumber
	 *            the mo number
	 * @return the work center capacity
	 * @throws Exception
	 *             the exception
	 */
	public int getWorkCenterCapacity(String workCenterNumber, String moNumber)
			throws Exception;

	/**
	 * Gets the break time.
	 * 
	 * @param query
	 *            the query
	 * @param actNo
	 *            the act no
	 * @param logonDate
	 *            the logon date
	 * @param logoffDate
	 *            the logoff date
	 * @return the break time
	 */
	public float getBreakTime(String query, String actNo, Date logonDate,
			Date logoffDate);

	/**
	 * Gets the retool time.
	 * 
	 * @param query
	 *            the query
	 * @param actNo
	 *            the act no
	 * @param logonDate
	 *            the logon date
	 * @param logoffDate
	 *            the logoff date
	 * @return the retool time
	 */
	public float getRetoolTime(String query, String actNo, Date logonDate,
			Date logoffDate);

	/**
	 * Gets the operation details.
	 * 
	 * @param MONumber
	 *            the mO number
	 * @param operationNumber
	 *            the operation number
	 * @param query
	 *            the query
	 * @return the operation details
	 */
	public List<Sequence> getOperationDetails(String MONumber,
			String operationNumber, String query);

	/**
	 * Gets the purchase order details using mvxdtami.
	 * 
	 * @param moNumber
	 *            the mo number
	 * @param operationNumber
	 *            the operation number
	 * @return the purchase order details using mvxdtami
	 * @throws Exception
	 *             the exception
	 */
	public MVXDTAMI getPurchaseOrderDetailsUsingMVXDTAMI(String moNumber,
			String operationNumber) throws Exception;

	/**
	 * Gets the pO transactions.
	 * 
	 * @param poNumber
	 *            the po number
	 * @return the pO transactions
	 * @throws Exception
	 *             the exception
	 */
	public List<POTransaction> getPOTransactions(String poNumber)
			throws Exception;

	/**
	 * Reverse po line transaction.
	 * 
	 * @param warehouse
	 *            the warehouse
	 * @param poNumber
	 *            the po number
	 * @param poLineNumber
	 *            the po line number
	 * @param poSubLineNumber
	 *            the po sub line number
	 * @param poStatus
	 *            the po status
	 * @param poLineSuffix
	 *            the po line suffix
	 * @throws Exception
	 *             the exception
	 */
	public void reversePOLineTransaction(String warehouse, String poNumber,
			String poLineNumber, String poSubLineNumber, String poStatus,
			String poLineSuffix) throws Exception;

	/**
	 * Delete sfs sequence.
	 * 
	 * @param moNumber
	 *            the mo number
	 * @param sequenceNumber
	 *            the sequence number
	 * @param query
	 *            the query
	 */
	public void deleteSFSSequence(String moNumber, String sequenceNumber,
			String query);

	/**
	 * Gets the break time.
	 * 
	 * @param username
	 *            the username
	 * @param moNumber
	 *            the mo number
	 * @param sequenceNumber
	 *            the sequence number
	 * @param query
	 *            the query
	 * @return the break time
	 */
	public float getBreakTime(String username, String moNumber,
			String sequenceNumber, String query);

	/**
	 * Gets the order details.
	 * 
	 * @param dto
	 *            the dto
	 * @param qry
	 *            the qry
	 * @return the order details
	 */
	public ManufacturingOrder getOrderDetails(ManufacturingOrder dto, String qry);
}
