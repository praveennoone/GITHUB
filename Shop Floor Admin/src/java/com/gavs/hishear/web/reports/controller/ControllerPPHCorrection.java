// File:         ControllerPPHCorrection.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.reports.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.gavs.hishear.m3interface.M3APIWSClient;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.domain.Activity;
import com.gavs.hishear.web.domain.ApplicationPropertyBean;
import com.gavs.hishear.web.domain.ManufacturingOrder;
import com.gavs.hishear.web.domain.Sequence;
import com.gavs.hishear.util.PMS100MIReadUtil;
import com.gavs.hishear.util.Util;

/**
 * The Class ControllerPPHCorrection.
 * 
 */
public class ControllerPPHCorrection extends SimpleFormController {

	private static final int MINUTES_PER_HOUR = 60;

	private static final int SECS_PER_HOUR = 3600;

	/** The dto. */
	private ManufacturingOrder dto;

	/** The dao. */
	private SequenceDAO dao;

	/** The user context. */
	private UserContext userContext;

	/** The m3 apiws client. */
	private M3APIWSClient m3APIWSClient;

	/** The application property bean. */
	private ApplicationPropertyBean applicationPropertyBean;

	/**
	 * Instantiates a new controller pph correction.
	 */
	public ControllerPPHCorrection() {
		setCommandName("command");
		setSessionForm(true);
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
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		WebUtils.setSessionAttribute(request, getFormSessionAttributeName(),
				command);
		dto = (ManufacturingOrder) command;
		String userAction = request.getParameter("userAction");
		ArrayList<Sequence> moNumbers;
		if (userAction != null && userAction.equals("getErrorActivities")) {
			getErrorActivities();
		} else if (userAction != null && userAction.equals("getErrorMONumbers")) {
			String facility = request.getParameter("facility");
			dto.setFacility(facility);
			moNumbers = getErrorMoNumberByFacility(facility);
			dto.setErrorMos(moNumbers);
		} else if (userAction != null && userAction.equals("confirmSequence")) {
			String operationNumberSelected = request
					.getParameter("operationNoSelected");
			try {
				// START - WO 25251 - PPH Review
				if (operationNumberSelected != null) {
					System.out.println("dto.getMoNumber()---"
							+ dto.getMoNumber());
					ArrayList<Activity> errorActivities = dto
							.getErrorActivities();
					for (Activity activity : errorActivities) {
						System.out
								.println("-----------------------------------"
										+ activity.getJobNumber());
						if (operationNumberSelected.equals(activity
								.getOperationNumber())) {
							updateActualCostStatus("C",
									userContext.getUserName(),
									activity.getJobNumber());
							break;
						}
					}
				}
				// END - WO 25251 - PPH Review
				getErrorActivities();
				if (dto.getErrorActivities() == null
						|| dto.getErrorActivities().size() == 0) {
					closeMo(dto);
				}
			} catch (Exception e) {
				dto.setError(e.getMessage());
			}
		}
		return new ModelAndView(getSuccessView(), "command", command);
	}

	/**
	 * Update actual cost status.
	 * 
	 * @param string
	 * 
	 * @param activityLogNumber
	 *            the activity log number
	 * @throws Exception
	 *             the exception
	 */
	private void updateActualCostStatus(String flag, String user,
			String jobNumber) throws Exception {
		String query = (String) userContext.getQueries().get("SHOPR_088");
		dao.updateErrorLogCompletion(flag, user, jobNumber, query);
	}

	/**
	 * Gets the error mo number by facility.
	 * 
	 * @param facility
	 *            the facility
	 * @return the error mo number by facility
	 */
	private ArrayList<Sequence> getErrorMoNumberByFacility(String facility) {
		String query = (String) userContext.getQueries().get("SHOPR_089");
		ArrayList<Sequence> moNumbers = dao.getErrorMoNumbers(query, facility);
		return moNumbers;
	}

	/**
	 * Gets the error activities.
	 * 
	 * @return the error activities
	 */
	private void getErrorActivities() {
		String errorActivitiesQuery = (String) userContext.getQueries().get(
				"SHOPR_087");

		dto.setErrorActivities(dao.getErrorActivities(dto.getMoNumber(),
				errorActivitiesQuery));
		List array = dto.getErrorActivities();

		// Set BOMList
		Sequence sequence = new Sequence();
		sequence.setCompany(applicationPropertyBean.getCompany());
		sequence.setMoNumber(dto.getMoNumber());
		ArrayList bomList = PMS100MIReadUtil.getOperation(sequence,
				m3APIWSClient);
		// START - WO 25251 - PPH Review
		// END - WO 25251 - PPH Review
		if (bomList.size() > 0) {
			Sequence m3Sequence = (Sequence) bomList.get(0);
			dto.setFacility(m3Sequence.getFacility());
			dto.setPartNumber(m3Sequence.getPartNumber());
			dto.setPartDescription(m3Sequence.getPartDesc());

			HashMap<String, Activity> consolidatedActivitiesMap = getConsolidatedActivitiesMap();

			setStdForActivity(consolidatedActivitiesMap, bomList);
			// START - WO 25251 - PPH Review

		}
		dto.setBomList(bomList);
	}

	private HashMap<String, Activity> getConsolidatedActivitiesMap() {
		// START - WO 25251 - PPH Review
		HashMap<String, Activity> consolidatedActivitiesMap = new HashMap<String, Activity>();
		String pauseQuery = (String) userContext.getQueries().get("SHOP_153");
		String retoolQuery = (String) userContext.getQueries().get("SHOP_154");
		for (Iterator iterator = dto.getErrorActivities().iterator(); iterator
				.hasNext();) {
			Activity activity = (Activity) iterator.next();
			if ("R".equals(activity.getActivityCode())) {

				float totalBreakSeconds = dao.getBreakTime(pauseQuery,
						activity.getJobActivityNumber(),
						activity.getLogonDate(), activity.getLogoffDate());
				float totalRetoolSeconds = dao.getRetoolTime(retoolQuery,
						activity.getJobActivityNumber(),
						activity.getLogonDate(), activity.getLogoffDate());

				float totalDeductableHours = (totalBreakSeconds + totalRetoolSeconds)
						/ SECS_PER_HOUR;
				if (consolidatedActivitiesMap.containsKey(activity
						.getOperationNumber())) {
					Activity existingActivity = consolidatedActivitiesMap
							.get(activity.getOperationNumber());
					float existingRunHours = Float.parseFloat(existingActivity
							.getRunHours());
					float newRunHours = existingRunHours
							+ (((float) activity.getMinutesProcessed() / MINUTES_PER_HOUR) - totalDeductableHours);
					existingActivity.setRunHours(""
							+ Util.roundDBL(newRunHours, 2));
					existingActivity.setCompletedQuantity(existingActivity
							.getCompletedQuantity()
							+ activity.getCompletedQuantity());
					existingActivity.setPph(""
							+ Util.roundDBL(
									existingActivity.getCompletedQuantity()
											/ newRunHours, 2));
				} else {
					activity.setRunHours(""
							+ Util.roundDBL(
									((activity.getMinutesProcessed() / MINUTES_PER_HOUR) - totalDeductableHours),
									2));
					consolidatedActivitiesMap.put(
							activity.getOperationNumber(), activity);
				}
			}
		}
		return consolidatedActivitiesMap;
	}

	private void setStdForActivity(
			HashMap<String, Activity> consolidatedActivitiesMap,
			List bomList) {
		ArrayList<Activity> consolidatedActivitiesList = new ArrayList<Activity>(
				consolidatedActivitiesMap.values());
		for (Iterator iterator = consolidatedActivitiesList.iterator(); iterator
				.hasNext();) {
			// END - WO 25251 - PPH Review
			Activity activity = (Activity) iterator.next();

			for (Iterator iterator2 = bomList.iterator(); iterator2.hasNext();) {
				Sequence bomActivity = (Sequence) iterator2.next();
				if (bomActivity.getSequence() != null
						&& activity.getOperationNumber() != null
						&& bomActivity.getSequence().trim()
								.equals(activity.getOperationNumber())) {
					activity.setPriceTimeQty(bomActivity.getPriceTimeQty());

					if (bomActivity.getRunTime() == 0) {
						activity.setStdPPH("0");
					} else {
						activity.setStdPPH(""
								+ Util.roundDBL(bomActivity.getPriceTimeQty()
										/ bomActivity.getRunTime(), 2));
					}

				}
			}
		}
		dto.setErrorActivities(consolidatedActivitiesList);
		// END - WO 25251 - PPH Review
	}

	/**
	 * Close mo.
	 * 
	 * @param dto2
	 *            the dto2
	 */
	private void closeMo(ManufacturingOrder dto2) {
		try {

			if (!isAllOperationsCompletedInMo(dto.getMoNumber())) {
				return;
			}

			Sequence seq = new Sequence();

			seq.setMoNumber(dto.getMoNumber());
			seq.setLineNumber("000");

			int morvQuantity = getMorvQuantity(dto.getBomList());
			seq.setMorvQuantity(morvQuantity);

			dao.PMS50WriteSetRptReceipt(seq, (String) userContext.getQueries()
					.get("SHOP_108"), userContext.getUserName(),
					(String) userContext.getQueries().get("M3SHIP_116"));

			// Call Procedure to close the Openend Activities

			dao.closeAllOpenedActivities(seq, (String) userContext.getQueries()
					.get("SHOP_084"));
			// Update the MO status in SFS_OrderMaster as 'C'
			dao.updateMOStatusAsCompleted(seq, (String) userContext
					.getQueries().get("SHOP_094"));

			dao.approveInspectionResultUsingLIS200(seq, (String) userContext
					.getQueries().get("SHOP_108"), userContext.getUserName(),
					(String) userContext.getQueries().get(" SHOP_159 "));

			dao.inspectedItemPutawayUsingPMS130(seq, (String) userContext
					.getQueries().get("SHOP_108"), userContext.getUserName(),
					(String) userContext.getQueries().get(" SHOP_159 "));

			PhysicalAllocationThread physicalAllocationThread = new PhysicalAllocationThread(
					dao, seq);
			physicalAllocationThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
		dto = new ManufacturingOrder();
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		dto.setDivisionFacilities(dao.getFacilities(userContext.getDivision()));
		return dto;
	}

	/**
	 * Checks if is all operations completed in mo.
	 * 
	 * @param moNumber
	 *            the mo number
	 * @return true, if is all operations completed in mo
	 */
	private boolean isAllOperationsCompletedInMo(String moNumber) {
		Sequence sequence = new Sequence();
		sequence.setCompany(applicationPropertyBean.getCompany());
		sequence.setMoNumber(moNumber);
		List bomList = null;
		bomList = PMS100MIReadUtil.getOperation(sequence, m3APIWSClient);
		for (int i = 0; i < bomList.size(); i++) {
			Sequence sequence2 = (Sequence) bomList.get(i);
			if (sequence2.getStatus() != null
					&& Integer.parseInt(sequence2.getStatus()) != 90) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gets the morv quantity.
	 * 
	 * @param bomList
	 *            the bom list
	 * @return the morv quantity
	 */
	private int getMorvQuantity(List bomList) {
		for (int i = 0; i < bomList.size(); i++) {
			Sequence sequence = (Sequence) bomList.get(i);

			if (sequence.getPlanningArea() != null
					&& "MORV".equalsIgnoreCase(sequence.getPlanningArea())) {
				return sequence.getCompletedQuantity();
			}
		}
		return 0;
	}

	/**
	 * Gets the dto.
	 * 
	 * @return the dto
	 */
	public ManufacturingOrder getDto() {
		return dto;
	}

	/**
	 * Sets the dto.
	 * 
	 * @param dto
	 *            the dto to set
	 */
	public void setDto(ManufacturingOrder dto) {
		this.dto = dto;
	}

	/**
	 * Gets the dao.
	 * 
	 * @return the dao
	 */
	public SequenceDAO getDao() {
		return dao;
	}

	/**
	 * Sets the dao.
	 * 
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(SequenceDAO dao) {
		this.dao = dao;
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