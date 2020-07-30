// File:         ControllerM3TransactionReconciliation.java
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.gavs.hishear.web.comparator.M3TransactionComparator;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.domain.ApplicationPropertyBean;
import com.gavs.hishear.web.domain.ErrorLog;
import com.gavs.hishear.web.domain.M3RequestLog;
import com.gavs.hishear.web.reports.management.dao.ManagementDao;
import com.gavs.hishear.util.DateUtil;
import com.gavs.hishear.web.view.M3TransactionReconciliationLogExcelView;

/**
 * The Class ControllerM3TransactionReconciliation.
 * 
 */
public class ControllerM3TransactionReconciliation extends SimpleFormController {

	/** The user context. */
	private UserContext userContext;

	/** The dao. */
	private ManagementDao dao;

	/** The application property bean. */
	private ApplicationPropertyBean applicationPropertyBean;

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

		if (userAction.equals("getProgramNames")) {
			dto.setDateProcessed(dateProcessed);

			ArrayList<ErrorLog> m3RequestLogs = dao.getM3RequestLogs(
					dateProcessed, (String) userContext.getQueries().get(
							"SHOPR_111"));
			// Needs to get only transaction type available web services.
			ArrayList<ErrorLog> m3RequestLogsWithTransType = new ArrayList<ErrorLog>();
			for (ErrorLog errorLog : m3RequestLogs) {
				if (applicationPropertyBean.getWebServicesWithTransType()
						.indexOf(errorLog.getWebServiceName()) != -1) {
					m3RequestLogsWithTransType.add(errorLog);
				}
			}
			dto.setM3RequestLogs(m3RequestLogsWithTransType);
		} else if (userAction.equals("getProgramDetails")) {
			String programName = request.getParameter("programName");
			dto.setProgramName(programName);

			// Set Function Name for the selected Web service Name.
			for (ErrorLog errorLog : dto.getM3RequestLogs()) {
				if (errorLog.getWebServiceName().equals(dto.getProgramName())) {
					dto.setFunctionName(errorLog.getFunctionName());
					break;
				}
			}

			// Sort the data based on specific field for each program.
			ArrayList<ErrorLog> errorLogs = new ArrayList<ErrorLog>();
			for (ErrorLog errorLog : dto.getM3RequestLogs()) {
				if (errorLog.getWebServiceName().equals(dto.getProgramName())) {
					errorLogs.add(errorLog);
				}
			}
			int webServiceCount = ArrayUtils.indexOf(applicationPropertyBean
					.getWebServicesWithTransType().split(","), programName);
			Collections.sort(errorLogs, new M3TransactionComparator(
					applicationPropertyBean.getWebServicesComparableFields()
							.split(",")[webServiceCount]));

			// Get Key fields for the Programs
			dto.setDataHeaders(getProgramKeyFields(programName, false).split(
					","));
			// Map bolt on data for display
			ArrayList<Map<String, String>> programDetails = new ArrayList<Map<String, String>>();
			for (ErrorLog errorLog : errorLogs) {
				if (errorLog.getWebServiceName().equals(dto.getProgramName())) {
					programDetails.add(findKeyFieldsValues(
							dto.getDataHeaders(), errorLog));
				}
			}
			dto.setProgramDetails(programDetails);

			String[] transTypes = getProgramTransType(programName).split(",");
			ArrayList<ErrorLog> m3Transactions = new ArrayList<ErrorLog>();
			ArrayList<String> facilities = dao.getFacilitiesForDivision(
					userContext.getDivision(), (String) userContext
							.getQueries().get("PURS_013"));
			if (facilities != null) {
				for (String facility : facilities) {
					for (String transType : transTypes) {
						m3Transactions.addAll(dao.getM3TransByType(transType,
								facility, DateUtil.getDateFormat(dateProcessed,
										"yyyyMMdd")));
					}
				}
			}

			// Sort M3 transaction data based on "RIDN" field
			errorLogs = new ArrayList<ErrorLog>();
			for (ErrorLog errorLog : m3Transactions) {
				errorLogs.add(errorLog);
			}
			Collections.sort(errorLogs, new M3TransactionComparator("RIDN"));

			// Map M3 transaction data for display
			dto.setDataHeadersForM3Trans(getProgramKeyFields(programName, true)
					.split(","));
			if (m3Transactions != null) {
				programDetails = new ArrayList<Map<String, String>>();
				for (ErrorLog errorLog : errorLogs) {
					programDetails.add(findKeyFieldsValues(dto
							.getDataHeadersForM3Trans(), errorLog));
				}
			}
			dto.setM3TransByDate(programDetails);
		} else if (userAction.equals("exportToExcel")) {
			return new ModelAndView(
					new M3TransactionReconciliationLogExcelView(dto),
					"command", command);
		}
		return new ModelAndView(getSuccessView(), "command", command);
	}

	/**
	 * Method to get Key fields for the Web service.
	 * 
	 * @param programName
	 *            the program name
	 * @param isKeyFieldsForM3
	 *            the is key fields for m3
	 * @return the program key fields
	 */
	private String getProgramKeyFields(String programName,
			boolean isKeyFieldsForM3) {
		Map<String, String> webServiceKeyValuesForBoltOn = new HashMap<String, String>();
		webServiceKeyValuesForBoltOn.put("MHS850MI", applicationPropertyBean
				.getKeyFieldsMHS850MIBoltOn());
		webServiceKeyValuesForBoltOn.put("MMZ428MI", applicationPropertyBean
				.getKeyFieldsMMZ428MIBoltOn());
		webServiceKeyValuesForBoltOn.put("MWZ422MI", applicationPropertyBean
				.getKeyFieldsMWZ422MIBoltOn());
		webServiceKeyValuesForBoltOn.put("PMS050MI", applicationPropertyBean
				.getKeyFieldsPMS050MIBoltOn());
		webServiceKeyValuesForBoltOn.put("PMS130", applicationPropertyBean
				.getKeyFieldsPMS130BoltOn());
		webServiceKeyValuesForBoltOn.put("PPS320", applicationPropertyBean
				.getKeyFieldsPPS320BoltOn());
		webServiceKeyValuesForBoltOn.put("PPY001MI", applicationPropertyBean
				.getKeyFieldsPPY001MIBoltOn());

		Map<String, String> webServiceKeyValuesForM3 = new HashMap<String, String>();
		webServiceKeyValuesForM3.put("MHS850MI", applicationPropertyBean
				.getKeyFieldsMHS850MIM3());
		webServiceKeyValuesForM3.put("MMZ428MI", applicationPropertyBean
				.getKeyFieldsMMZ428MIM3());
		webServiceKeyValuesForM3.put("MWZ422MI", applicationPropertyBean
				.getKeyFieldsMWZ422MIM3());
		webServiceKeyValuesForM3.put("PMS050MI", applicationPropertyBean
				.getKeyFieldsPMS050MIM3());
		webServiceKeyValuesForM3.put("PMS130", applicationPropertyBean
				.getKeyFieldsPMS130M3());
		webServiceKeyValuesForM3.put("PPS320", applicationPropertyBean
				.getKeyFieldsPPS320M3());
		webServiceKeyValuesForM3.put("PPY001MI", applicationPropertyBean
				.getKeyFieldsPPY001MIM3());

		if (isKeyFieldsForM3) {
			return webServiceKeyValuesForM3.get(programName);
		}
		return webServiceKeyValuesForBoltOn.get(programName);
	}

	/**
	 * Method to get Transaction type for the web service name.
	 * 
	 * @param programName
	 *            the program name
	 * @return the program trans type
	 */
	private String getProgramTransType(String programName) {
		Map<String, String> webServiceTransTypes = new HashMap<String, String>();
		webServiceTransTypes.put("MHS850MI", applicationPropertyBean
				.getTransTypeMHS850MI());
		webServiceTransTypes.put("MMZ428MI", applicationPropertyBean
				.getTransTypeMMZ428MI());
		webServiceTransTypes.put("MWZ422MI", applicationPropertyBean
				.getTransTypeMWZ422MI());
		webServiceTransTypes.put("PMS050MI", applicationPropertyBean
				.getTransTypePMS050MI());
		webServiceTransTypes.put("PMS130", applicationPropertyBean
				.getTransTypePMS130());
		webServiceTransTypes.put("PPS320", applicationPropertyBean
				.getTransTypePPS320());
		webServiceTransTypes.put("PPY001MI", applicationPropertyBean
				.getTransTypePPY001MI());

		return webServiceTransTypes.get(programName);
	}

	/**
	 * Find key fields values.
	 * 
	 * @param dataHeaders
	 *            the data headers
	 * @param errorLog
	 *            the error log
	 * @return the map
	 */
	private Map<String, String> findKeyFieldsValues(String[] dataHeaders,
			ErrorLog errorLog) {
		String[] headerValues = errorLog.getColumnHeaders().split(",");
		String[] dataValues = errorLog.getColumnValues().split(",");
		Map<String, String> errorLogData = new HashMap<String, String>();

		int headerCountValue = 0;
		if (dataHeaders != null && dataHeaders.length > 0) {
			for (String header : dataHeaders) {
				headerCountValue = ArrayUtils.indexOf(headerValues, header);
				if (headerCountValue != -1) {
					errorLogData.put(header, dataValues[headerCountValue]);
				}
			}
		}
		return errorLogData;
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

	/**
	 * Gets the application property bean.
	 * 
	 * @return the applicationPropertyBean
	 */
	public ApplicationPropertyBean getApplicationPropertyBean() {
		return applicationPropertyBean;
	}

	/**
	 * Sets the application property bean.
	 * 
	 * @param applicationPropertyBean
	 *            the applicationPropertyBean to set
	 */
	public void setApplicationPropertyBean(
			ApplicationPropertyBean applicationPropertyBean) {
		this.applicationPropertyBean = applicationPropertyBean;
	}

}
