// File:         WIPStatusReportDao.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.reports.management.dao;

import java.util.List;

import com.gavs.hishear.web.reports.management.domain.WIPStatusDto;

/**
 * The Interface WIPStatusReportDao.
 */
public interface WIPStatusReportDao {

	/**
	 * Gets the wip status report.
	 * 
	 * @param dto
	 *            the dto
	 * @return the wip status report
	 * @throws Exception
	 *             the exception
	 */
	List getWipStatusReport(WIPStatusDto dto) throws Exception;

}
