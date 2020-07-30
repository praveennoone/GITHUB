// File:         ControllerM3TransactionLogReport.java
// Created:      Feb 23, 2011
// Author:       mohammeda
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.reports.management.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.domain.ErrorLog;
import com.gavs.hishear.web.domain.M3RequestLog;
import com.gavs.hishear.web.reports.management.dao.ManagementDao;
import com.gavs.hishear.web.view.M3TransactionLogExcelView;

/**
 * The Class ControllerM3TransactionLogReport.
 * 
 */
public class ControllerM3TransactionLogReport extends SimpleFormController {

	/** The user context. */
	private UserContext userContext;

	/** The dao. */
	private ManagementDao dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject
	 * (javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		M3RequestLog dto = new M3RequestLog();
		dto.setDisplayName(userContext.getDisplayName());
		setSessionForm(true);
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.validation.BindException)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		WebUtils.setSessionAttribute(request, getFormSessionAttributeName(),
				command);
		M3RequestLog dto = (M3RequestLog) command;
		String userAction = request.getParameter("userAction");
		String dateProcessed = request.getParameter("dateProcessed");
		ArrayList<ErrorLog> m3RequestLogs = null;
		if (userAction.equals("getProgramNames")) {
			m3RequestLogs = dao.getM3RequestLogs(dateProcessed,
					(String) userContext.getQueries().get("SHOPR_111"));
			dto.setDateProcessed(dateProcessed);
			dto.setM3RequestLogs(m3RequestLogs);
		} else if (userAction.equals("getProgramDetails")) {
			String programName = request.getParameter("programName");
			dto.setProgramName(programName);

			// Find the Header with the maximum headers
			String[] dataHeaders = null;
			String[] tempDataHeaders = null;
			int maxHeaderSize = 0;
			for (ErrorLog errorLog : dto.getM3RequestLogs()) {
				if (errorLog.getWebServiceName().equals(dto.getProgramName())) {
					// Set Function name.
					dto.setFunctionName(errorLog.getFunctionName());
					tempDataHeaders = errorLog.getColumnHeaders().split(",");
					if (tempDataHeaders.length > maxHeaderSize) {
						dataHeaders = tempDataHeaders;
						maxHeaderSize = dataHeaders.length;
					}
				}
			}
			dto.setDataHeaders(dataHeaders);

			// Map data for display
			String[] headerValues = null;
			String[] dataValues = null;
			ArrayList<Map<String, String>> programDetails = new ArrayList<Map<String, String>>();
			for (ErrorLog errorLog : dto.getM3RequestLogs()) {
				if (errorLog.getWebServiceName().equals(dto.getProgramName())) {
					headerValues = errorLog.getColumnHeaders().split(",");
					dataValues = errorLog.getColumnValues().split(",");
					Map<String, String> errorLogData = new HashMap<String, String>();
					for (int i = 0; i < headerValues.length; i++) {
						try {
							errorLogData.put(headerValues[i], dataValues[i]);
						} catch (Exception e) {
							System.out
									.println("Error while creating ErrorLogData: "
											+ e.getMessage());
						}
					}
					programDetails.add(errorLogData);
				}
			}
			dto.setProgramDetails(programDetails);
		} else if (userAction.equals("exportToExcel")) {
			return new ModelAndView(new M3TransactionLogExcelView(dto),
					"command", command);
		}
		return new ModelAndView(getSuccessView(), "command", command);
	}

	/**
	 * Gets the user context.
	 * 
	 * @return the userContext
	 */
	public UserContext getUserContext() {
		return userContext;
	}

	/**
	 * Sets the user context.
	 * 
	 * @param userContext
	 *            the userContext to set
	 */
	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}

	/**
	 * Gets the dao.
	 * 
	 * @return the dao
	 */
	public ManagementDao getDao() {
		return dao;
	}

	/**
	 * Sets the dao.
	 * 
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(ManagementDao dao) {
		this.dao = dao;
	}

}
