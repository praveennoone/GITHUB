// File:         ControllerReverseInspectionOperation.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.gavs.hishear.m3interface.M3APIWSClient;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.domain.ApplicationPropertyBean;
import com.gavs.hishear.web.domain.ReversalDto;
import com.gavs.hishear.web.reports.exceptional.dao.ExceptionalDao;
import com.gavs.hishear.web.reports.exceptional.domain.ExceptionalDto;

/**
 * The Class ControllerReverseInspectionOperation.
 */
@SuppressWarnings("deprecation")
public class ControllerReverseInspectionOperation extends SimpleFormController {

	private static final int SECS_PER_HOUR = 3600;

	private static final double MILS_PER_SECOND = 1000.00;

	/** The exceptional reports dao. */
	private ExceptionalDao exceptionalReportsDao;

	/** The user context. */
	private UserContext userContext;

	/** The m3 apiws client. */
	private M3APIWSClient m3APIWSClient;

	/** The sequence dao. */
	private SequenceDAO sequenceDAO;

	/** The logger. */
	private static Logger logger = Logger
			.getLogger(ControllerReverseInspectionOperation.class);

	/** The application property bean. */
	private ApplicationPropertyBean applicationPropertyBean;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject
	 * (javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		System.out
				.println("ControllerReverseInspectionOperation : formBacking Called--------------------------------");

		Object command = null;

		try {
			command = this.getCommand(request);
		} catch (Exception e) {
			logger
					.info("formBackingObject: exception thrown "
							+ e.getMessage());
		}

		if (command == null) {
			request.getSession(true);
			command = new ReversalDto();
			((ReversalDto) command).setUserName(userContext.getUserName());
			((ReversalDto) command)
					.setDisplayName(userContext.getDisplayName());
		}
		WebUtils.setSessionAttribute(request, getFormSessionAttributeName(),
				command);
		return command;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		ReversalDto dto = (ReversalDto) command;
		System.out
				.println("ControllerReverseInspectionOperation : onSubmit Called----------------------------------"
						+ dto.getAction());

		if ("DETAILS".equals(dto.getAction())) {
			// All required data has already been read in the Validator
			return new ModelAndView(getSuccessView(), "command", command);
		} else if ("REVERSE".equals(dto.getAction())) {
			Map model = new HashMap();
			try {
				if (dto.isInspectionForOutsideOperation()) {
					sequenceDAO.reversePOLineTransaction(dto.getWarehouse(),
							dto.getPoNumber(), dto.getPoLineNumber(), dto
									.getPoSubLineNumber(), dto.getPoStatus(),
							dto.getPoLineSuffix());
				}

				reverseInM3(dto);

				sequenceDAO.deleteSFSSequence(dto.getMoNumber(), dto
						.getOperationNumber(), (String) userContext
						.getQueries().get("SHOP_145"));

				model.put("message", "Successfully Reversed!");
				model.put("messageType", "SUCCESS");

			} catch (Exception e) {
				dto.setReversed(false);
				// Begin Changes made to trim the M3 Error Message - Ambrish.V -
				// Infosys - 17-May-2011
				String str = trimMessage("Could Not Be Reversed!-"
						+ e.getMessage());
				System.out.println("after trim : " + str);
				model.put("message", str);
				// End Changes made to trim the M3 Error Message - Ambrish.V -
				// Infosys - 17-May-2011
				model.put("messageType", "ERROR");
			}

			WebUtils.setSessionAttribute(request,
					getFormSessionAttributeName(), null);

			return new ModelAndView(getSuccessView(), model);
		}
		return new ModelAndView(getSuccessView(), "command", command);
	}

	/**
	 * Reverse in m3.
	 * 
	 * @param dto
	 *            the dto
	 * @throws Exception
	 *             the exception
	 */
	private void reverseInM3(ReversalDto dto) throws Exception {
		double runtimeToBeReversedInSeconds = getCalculatedLabourAndMachineTime(
				dto.getMoNumber(), dto.getOperationNumber());

		double runtimeToBeReversedInHours = runtimeToBeReversedInSeconds
				/ SECS_PER_HOUR;

		System.out.println("runtimeToBeReversedInHours: "
				+ runtimeToBeReversedInHours);
		// Begin - InsertM3Request call - VP - Infosys - May 11, 2011
		// Adding the user name, error log and M3 Request query
		String m3Query = (String) userContext.getQueries().get("M3SHIP_116");
		String errorQuery = (String) userContext.getQueries().get("SHOP_108");
		sequenceDAO.PMZ70WriteSetRptOperation(dto.getMoNumber(), Integer
				.parseInt(dto.getReportedQuantity()), dto.getOperationNumber(),
				"", "0", "" + runtimeToBeReversedInHours,
				dto.getPriceTimeQty(), dto.getWorkCenterCapacity(), "RUN",
				m3Query, errorQuery, userContext.getUserName());
		// End - InsertM3Request call - VP - Infosys - May 11, 2011
	}

	/**
	 * Gets the calculated labour and machine time.
	 * 
	 * @param moNumber
	 *            the mo number
	 * @param operationNumber
	 *            the operation number
	 * @return the calculated labour and machine time
	 */
	public double getCalculatedLabourAndMachineTime(String moNumber,
			String operationNumber) {

		float breakTimeInSeconds = sequenceDAO.getBreakTime(userContext
				.getUserName(), moNumber, operationNumber, (String) userContext
				.getQueries().get("SHOP_151"));
		System.out.println("Break Time For " + userContext.getUserName() + ":"
				+ moNumber + ":" + operationNumber + "->" + breakTimeInSeconds);

		double runTimeInMilliSec = 0.0;

		ExceptionalDto exceptionalDto = new ExceptionalDto();
		exceptionalDto.setMoNumber(moNumber);
		exceptionalDto.setSequence(operationNumber);
		exceptionalDto.setQuery((String) userContext.getQueries().get(
				"SHOP_034"));
		List<ExceptionalDto> activities = exceptionalReportsDao
				.getActivityRejection(exceptionalDto);
		for (ExceptionalDto exceptionalDto2 : activities) {
			runTimeInMilliSec = runTimeInMilliSec
					+ (exceptionalDto2.getLogoffDate().getTime() - exceptionalDto2
							.getLogonDate().getTime());
		}

		System.out.println("runTimeInMilliSec: " + runTimeInMilliSec);

		double calculatedRunTimeInSeconds = (runTimeInMilliSec / MILS_PER_SECOND)
				- breakTimeInSeconds;

		return calculatedRunTimeInSeconds;
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

	/**
	 * Gets the m3 apiws client.
	 * 
	 * @return the m3APIWSClient
	 */
	public M3APIWSClient getM3APIWSClient() {
		return m3APIWSClient;
	}

	/**
	 * Sets the m3 apiws client.
	 * 
	 * @param client
	 *            the m3APIWSClient to set
	 */
	public void setM3APIWSClient(M3APIWSClient client) {
		m3APIWSClient = client;
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
	 * Gets the sequence dao.
	 * 
	 * @return the sequenceDAO
	 */
	public SequenceDAO getSequenceDAO() {
		return sequenceDAO;
	}

	/**
	 * Sets the sequence dao.
	 * 
	 * @param sequenceDAO
	 *            the sequenceDAO to set
	 */
	public void setSequenceDAO(SequenceDAO sequenceDAO) {
		this.sequenceDAO = sequenceDAO;
	}

	/**
	 * Sets the exceptional reports dao.
	 * 
	 * @param exceptionalReportsDao
	 *            the new exceptional reports dao
	 */
	public void setExceptionalReportsDao(ExceptionalDao exceptionalReportsDao) {
		this.exceptionalReportsDao = exceptionalReportsDao;
	}

	/**
	 * Gets the exceptional reports dao.
	 * 
	 * @return the exceptional reports dao
	 */
	public ExceptionalDao getExceptionalReportsDao() {
		return exceptionalReportsDao;
	}

	// Begin Changes made to trim the M3 Error Message - Ambrish.V - Infosys -
	// 17-May-2011
	/**
	 * Method to trim the in between spaces.
	 * 
	 * @return the string
	 */
	public static String trimMessage(Object object) {
		if (object != null) {
			String[] messageArray = ((String) object).split(" ");
			String afterRemovingSpace = "";
			for (int i = 0; i < messageArray.length; i++) {
				if (!messageArray[i].trim().equals("")) {
					afterRemovingSpace = afterRemovingSpace + " "
							+ messageArray[i].trim();
				}
			}
			if (afterRemovingSpace.trim().equals("")) {
				afterRemovingSpace = "No data found";
			}
			return afterRemovingSpace.trim();
		} else {
			return "No data found";
		}
	}
	// End Changes made to trim the M3 Error Message - Ambrish.V - Infosys -
	// 17-May-2011

}
