// File:         FactoryDao.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.reports.factory.dao;

import java.util.ArrayList;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.domain.StandardTime;
import com.gavs.hishear.web.domain.TestMethods;
import com.gavs.hishear.web.reports.factory.domain.FactoryDto;

/**
 * The Interface FactoryDao.
 * 
 */

public interface FactoryDao {

	/**
	 * Gets the factory details.
	 * 
	 * @param ObjFactoryDto
	 *            the obj factory dto
	 * @return the factory details
	 */
	public ArrayList getFactoryDetails(FactoryDto ObjFactoryDto);

	/**
	 * Gets the department details.
	 * 
	 * @param ObjFactoryDto
	 *            the obj factory dto
	 * @return the department details
	 */
	public ArrayList getDepartmentDetails(FactoryDto ObjFactoryDto);

	/**
	 * Gets the asset report.
	 * 
	 * @param ObjFactoryDto
	 *            the obj factory dto
	 * @return the asset report
	 */
	public ArrayList getAssetReport(FactoryDto ObjFactoryDto);

	/**
	 * Gets the factory summery.
	 * 
	 * @param managementalDto
	 *            the managemental dto
	 * @param ObjFactoryDto
	 *            the obj factory dto
	 * @return the factory summery
	 */
	public ArrayList getFactorySummery(FactoryDto managementalDto,
			FactoryDto ObjFactoryDto);

	/**
	 * Gets the shift time.
	 * 
	 * @param objUserContext
	 *            the obj user context
	 * @return the shift time
	 */
	public ArrayList getShiftTime(UserContext objUserContext);

	/**
	 * Gets the asset count for dept.
	 * 
	 * @param report
	 *            the report
	 * @return the asset count for dept
	 */
	public String getAssetCountForDept(FactoryDto report);

	/**
	 * Gets the operators summary.
	 * 
	 * @param report
	 *            the report
	 * @return the operators summary
	 */
	public ArrayList getOperatorsSummary(FactoryDto report);

	/**
	 * Gets the jobs currently processing report.
	 * 
	 * @param report
	 *            the report
	 * @return the jobs currently processing report
	 */
	public ArrayList getJobsCurrentlyProcessingReport(FactoryDto report);

	/**
	 * Gets the parts processed summary.
	 * 
	 * @param report
	 *            the report
	 * @return the parts processed summary
	 */
	public ArrayList getPartsProcessedSummary(FactoryDto report);

	/**
	 * Gets the parts rejected summary.
	 * 
	 * @param report
	 *            the report
	 * @return the parts rejected summary
	 */
	public ArrayList getPartsRejectedSummary(FactoryDto report);

	/**
	 * Gets the yield summary.
	 * 
	 * @param report
	 *            the report
	 * @return the yield summary
	 */
	public ArrayList getYieldSummary(FactoryDto report);

	/**
	 * Gets the jobs currently pending summary.
	 * 
	 * @param report
	 *            the report
	 * @return the jobs currently pending summary
	 */
	public ArrayList getJobsCurrentlyPendingSummary(FactoryDto report);

	/**
	 * Gets the operator count for dept.
	 * 
	 * @param report
	 *            the report
	 * @return the operator count for dept
	 */
	public String getOperatorCountForDept(FactoryDto report);

	/**
	 * Gets the job currently processing for dept.
	 * 
	 * @param report
	 *            the report
	 * @return the job currently processing for dept
	 */
	public String getJobCurrentlyProcessingForDept(FactoryDto report);

	/**
	 * Gets the jobs currently pending for dept.
	 * 
	 * @param report
	 *            the report
	 * @return the jobs currently pending for dept
	 */
	public String getJobsCurrentlyPendingForDept(FactoryDto report);

	/**
	 * Gets the parts processed for dept.
	 * 
	 * @param report
	 *            the report
	 * @return the parts processed for dept
	 */
	public String getPartsProcessedForDept(FactoryDto report);

	// Begin - 26978 - new interface Quality Standard Time - Pinky - Infosys -
	// 20 Apr 2011
	/**
	 * Gets the distinct test type from SFS_StandardTime.
	 * 
	 * @param dto
	 *            the FactoryDto
	 * @return the TestType
	 */
	public ArrayList<String> getDistinctTestType(StandardTime dto, String query);

	/**
	 * Gets the distinct test type from SFS_StandardTime.
	 * 
	 * @param dto
	 *            the FactoryDto
	 * @return the TestType
	 */
	public ArrayList<TestMethods> getTestTypeDetails(StandardTime dto,
			String query);

	/**
	 * Update Standard Time to SFS_StandardTime.
	 * 
	 * @param dto
	 *            the FactoryDto
	 * @return the TestType
	 */
	public void updateStandardTime(StandardTime dto, String query);

	/**
	 * Delete Standard Time from SFS_StandardTime.
	 * 
	 * @param dto
	 *            the FactoryDto
	 * @return the TestType
	 */
	public void deleteStandardTime(StandardTime dto, String query);

	/**
	 * Add Standard Time to SFS_StandardTime.
	 * 
	 * @param dto
	 *            the FactoryDto
	 * @return the TestType
	 */
	public void addStandardTime(StandardTime dto, String query);

	/**
	 * Add Standard Time to SFS_StandardTime.
	 * 
	 * @param dto
	 *            the FactoryDto
	 * @return the TestType
	 */
	public void updateStandardTimeLog(StandardTime dto, String query,
			UserContext objUserContext);
	// End - 26978 - new interface Quality Standard Time - Pinky - Infosys - 20
	// Apr 2011
}
