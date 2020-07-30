// File:         ManagementDao.java
// Created:      Feb 23, 2011
// Author:       sundarrajanr
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.reports.management.dao;

import java.util.ArrayList;

import com.gavs.hishear.m3interface.dto.MVXDTAMI;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.domain.ErrorLog;
import com.gavs.hishear.web.domain.Facility;
import com.gavs.hishear.web.reports.factory.domain.FactoryDto;
import com.gavs.hishear.web.reports.management.domain.ManagementalDto;

/**
 * The Interface ManagementDao.
 * 
 */

public interface ManagementDao {

	/**
	 * Gets the employee performance report.
	 * 
	 * @param managementalDto
	 *            the managemental dto
	 * @param ObjUserContext
	 *            the obj user context
	 * @return the employee performance report
	 */
	public ArrayList getEmployeePerformanceReport(
			ManagementalDto managementalDto, UserContext ObjUserContext);

	/**
	 * Gets the asset usage report.
	 * 
	 * @param managementalDto
	 *            the managemental dto
	 * @param userContext
	 *            the user context
	 * @return the asset usage report
	 */
	public ArrayList getAssetUsageReport(ManagementalDto managementalDto,
			UserContext userContext);

	/**
	 * Gets the standard rate report.
	 * 
	 * @param managementalDto
	 *            the managemental dto
	 * @param userContext
	 *            the user context
	 * @return the standard rate report
	 */
	public ArrayList getStandardRateReport(ManagementalDto managementalDto,
			UserContext userContext);

	/**
	 * Gets the production report.
	 * 
	 * @param managementalDto
	 *            the managemental dto
	 * @param userContext
	 *            the user context
	 * @return the production report
	 */
	public ArrayList getProductionReport(ManagementalDto managementalDto,
			UserContext userContext);

	/**
	 * Gets the correction tracking report.
	 * 
	 * @param managementalDto
	 *            the managemental dto
	 * @param userContext
	 *            the user context
	 * @return the correction tracking report
	 */
	public ArrayList getCorrectionTrackingReport(
			ManagementalDto managementalDto, UserContext userContext);

	/**
	 * Gets the shift time.
	 * 
	 * @param objUserContext
	 *            the obj user context
	 * @return the shift time
	 */
	public ArrayList getShiftTime(UserContext objUserContext);

	/**
	 * Gets the exp activity report.
	 * 
	 * @param dto
	 *            the dto
	 * @param userContext
	 *            the user context
	 * @return the exp activity report
	 */
	public ArrayList getExpActivityReport(ManagementalDto dto,
			UserContext userContext);

	/**
	 * Gets the activity log.
	 * 
	 * @param dto
	 *            the dto
	 * @param userContext
	 *            the user context
	 * @return the activity log
	 */
	public ArrayList getActivityLog(ManagementalDto dto, UserContext userContext);

	/**
	 * Gets the activity log summary.
	 * 
	 * @param dto
	 *            the dto
	 * @param userContext
	 *            the user context
	 * @return the activity log summary
	 */
	public ArrayList getActivityLogSummary(ManagementalDto dto,
			UserContext userContext);

	/**
	 * Gets the department for factory.
	 * 
	 * @param factoryDto
	 *            the factory dto
	 * @param objUserContext
	 *            the obj user context
	 * @return the department for factory
	 */
	public ArrayList getDepartmentForFactory(FactoryDto factoryDto,
			UserContext objUserContext);

	/**
	 * Gets the factories.
	 * 
	 * @param factoryDto
	 *            the factory dto
	 * @param objUserContext
	 *            the obj user context
	 * @return the factories
	 */
	public ArrayList getFactories(FactoryDto factoryDto,
			UserContext objUserContext);

	/**
	 * Gets the performance assetwise report.
	 * 
	 * @param factoryDto
	 *            the factory dto
	 * @param userContext
	 *            the user context
	 * @return the performance assetwise report
	 */
	public ArrayList getPerformanceAssetwiseReport(FactoryDto factoryDto,
			UserContext userContext);

	/**
	 * Gets the departments.
	 * 
	 * @param userContext
	 *            the user context
	 * @return the departments
	 */
	public ArrayList getDepartments(UserContext userContext);

	/**
	 * Gets the cost centers for facility.
	 * 
	 * @param mvxdtami
	 *            the mvxdtami
	 * @return the cost centers for facility
	 */
	public ArrayList getCostCentersForFacility(MVXDTAMI mvxdtami);

	/**
	 * Gets the work centers for cost center.
	 * 
	 * @param mvxdtami
	 *            the mvxdtami
	 * @return the work centers for cost center
	 */
	public ArrayList getWorkCentersForCostCenter(MVXDTAMI mvxdtami);

	/**
	 * Gets the facilities.
	 * 
	 * @param division
	 *            the division
	 * @return the facilities
	 */
	public ArrayList<Facility> getFacilities(String division);

	/**
	 * Gets the m3 request logs.
	 * 
	 * @param dateProcessed
	 *            the date processed
	 * @return the m3 request logs
	 * @throws Exception
	 *             the exception
	 */
	public ArrayList<ErrorLog> getM3RequestLogs(String dateProcessed,
			String query) throws Exception;

	/**
	 * Gets the facilities for division.
	 * 
	 * @param division
	 *            the division
	 * @param query
	 * @return the facilities for division
	 */
	public ArrayList<String> getFacilitiesForDivision(String division,
			String query);

	/**
	 * Gets the m3 trans by type.
	 * 
	 * @param transType
	 *            the trans type
	 * @param wareHouse
	 *            the ware house
	 * @param dateProcessed
	 *            the date processed
	 * @return the m3 trans by type
	 * @throws Exception
	 *             the exception
	 */
	public ArrayList<ErrorLog> getM3TransByType(String transType,
			String wareHouse, String dateProcessed) throws Exception;

}