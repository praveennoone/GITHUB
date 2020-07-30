// File:         ControllerPickSequence.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.gavs.hishear.m3interface.M3APIWSClient;
import com.gavs.hishear.util.M3Constants;
import com.gavs.hishear.util.PMS070MIWriteUtil;
import com.gavs.hishear.util.PMS100MIReadUtil;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.domain.Sequence;


/**
 * The Class ControllerPickSequence.
 */
public class ControllerPickSequence extends SimpleFormController {

	/** The logger. */
	private static Logger logger = Logger
			.getLogger(ControllerPickSequence.class);

	/** The dao. */
	private SequenceDAO dao;

	/** The user context. */
	private UserContext userContext;

	/** The m3 apiws client. */
	private M3APIWSClient m3APIWSClient;

	/**
	 * Instantiates a new controller pick sequence.
	 */
	public ControllerPickSequence() {
		setCommandName("command");
		setCommandClass(Sequence.class);
		setSessionForm(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject
	 * (javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		Sequence dto = new Sequence();
		String canDisplyMessage = request.getParameter("canDisplyMessage");
		/*
		 * if(canDisplyMessage != null &&
		 * "yes".equalsIgnoreCase(canDisplyMessage)) {
		 * dto.setMessage("Picked Successfully"); } else { dto.setMessage(""); }
		 */
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
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
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		WebUtils.setSessionAttribute(request, getFormSessionAttributeName(),
				command);
		Sequence dto = (Sequence) command;

		String operation = dto.getOperation();
		dto.setLineNumber("000");

		dto.setErrorOccured(false);

		String moNumber = request.getParameter("moNumber");
		List<Sequence> seqList = PMS100MIReadUtil.getOperation(dto,
				m3APIWSClient);
		if (seqList.size() > 0) {
			Sequence m3Sequence = seqList.get(0);
			dto.setFacility(m3Sequence.getFacility());
			dto.setPartNumber(m3Sequence.getPartNumber());
			dto.setPartDesc(m3Sequence.getPartDesc());

		}

		if ("filter".equals(operation)) {

			dto.setAddNewJob(false);
			dto.setAssetNumber("");
			dto.setEmployeeID("");
			String canDisplyMessage = request.getParameter("canDisplyMessage");
			if (canDisplyMessage != null
					&& "yes".equalsIgnoreCase(canDisplyMessage)) {
				dto.setMessage("Picked Successfully");
			} else {
				dto.setMessage("");
			}
			getSequenceDetails(dto);
			ArrayList sequencesDetails = dto.getSequenceDetails();

			if (dto.getStatus() != null
					&& dto.getStatus().equalsIgnoreCase("Complete")) {
				dto.setErrorOccured(true);
			}

			// If the sequence is completed in local database(SFS) set the error
			// message
			if (dto.isErrorOccured()) {

				errors.rejectValue("Sequence", "completedSequence",
						"Sequence Already Completed");
				return new ModelAndView(getSuccessView(), "command", command);
			}

			if (sequencesDetails.size() == 0) {
				dto.setAddNewJob(true);

				// To display on screen as pick qty
				dto.setPickQty(dao.getPreviousCompletedQty(dto,
						(String) userContext.getQueries().get("SHOP_016")));
			} else {
				// To display on screen as pick qty
				dto.setPickQty(getCompletedQty(dto));

			}

			// retrieving prev seq qty for validation
			dto.setPreviousSeqQty(dao.getPreviousCompletedQty(dto,
					(String) userContext.getQueries().get("SHOP_016")));

			// Check if the sequence can be picked or not
			dto.setCompleteable(isSequenceCompleteable(sequencesDetails));

			dto.setQuery((String) userContext.getQueries().get("SHOP_011"));
			dto.setCurSeqQtyCompleted(getCompletedQty(dto));
			dto.setReasons(dao.getReasons((String) userContext.getQueries()
					.get("SHOP_075")));
			dto.setPageRefreshValue(1);
			dto.setMessage("");

			dto.setComments("");
			dto.setReasonDesc("");
			System.out.println("dto.getQtyCompleted()--"
					+ dto.getQtyCompleted());
		} else if (operation != null && "pick".equals(operation)) {
			System.out.println("Pick OPeration called...........");
			String jobNumber = dto.getJobNumber();
			double qtyToBeAdded = dto.getQtyCompleted() - dto.getPickQty();
			dto.setReasonCode(Integer.parseInt(request.getParameter("reason")));
			dto.setComments(request.getParameter("comments"));
			if (qtyToBeAdded > 0) {
				double actualCompletedQty = dto.getQtyCompleted();
				dto.setQtyCompleted(dto.getQtyCompleted() - dto.getPickQty());

				// Parameters hard coded for M3 Update - SM
				dto.setCompany(m3APIWSClient.getApplicationPropertyBean()
						.getCompany());
				// dto.setFacility("CLR");
				dto.setUOM(M3Constants.UOM);
				dto.setCompletionFlag("1");
				System.out.println("Facility passed-->" + dto.getFacility());
				// Method added to pick the sequence in M3
				String message = PMS070MIWriteUtil.pickSequenceInM3(dto,
						m3APIWSClient);
				dto.setReportedToM3(true);
				if (message != null) {

					dto.setMessage(trimMessage(message));
					dto.setPageRefreshValue(2);
					dto.setPickSuccess(false);
					dto.setCompleteable(false);
					return new ModelAndView(getSuccessView(), "command",
							command);
				}
				List bomList = PMS100MIReadUtil
						.getOperation(dto, m3APIWSClient);
				String nextSequenceFromBOM = getNextSequenceFromBOM(dto
						.getSequence(), bomList);
				dto.setNextSequenceNumber(nextSequenceFromBOM);
				// fetchDepartment(dto);
				createAJobAndPick(dto);
				dto.setQtyCompleted(actualCompletedQty);
				// Retrieve the Next Sequence Number for the Current Activity
				// from BOM(set to update the next sequence number in
				// SFS_OrderDetail)

				dao.completeSequence(jobNumber, (String) userContext
						.getQueries().get("SHOP_027"));
				dao.completeSequenceActivities(jobNumber, (String) userContext
						.getQueries().get("SHOPR_051"));
				dao.logSequencePick(dto, userContext.getUserName(),
						(String) userContext.getQueries().get("SHOPR_081"),
						false);
				dto.setMessage("Picked Successfully");
				dto.setPickSuccess(true);
				dto.setCompleteable(false);
				dto.setPageRefreshValue(2);

			} else {

				// Parameters hard coded for M3 Update - SM
				dto.setCompany(m3APIWSClient.getApplicationPropertyBean()
						.getCompany());
				// dto.setFacility("CLR");
				dto.setUOM(M3Constants.UOM);
				dto.setCompletionFlag("1");

				// Method added to pick the sequence in M3
				String message = PMS070MIWriteUtil.pickSequenceInM3(dto,
						m3APIWSClient);
				if (message != null) {

					dto.setMessage(trimMessage(message));
					dto.setPageRefreshValue(2);
					dto.setPickSuccess(false);
					dto.setCompleteable(false);
					return new ModelAndView(getSuccessView(), "command",
							command);
				}

				// Retrieve the Next Sequence Number for the Current Activity
				// from BOM(set to update the next sequence number in
				// SFS_OrderDetail)
				List bomList = PMS100MIReadUtil
						.getOperation(dto, m3APIWSClient);
				String nextSequenceFromBOM = getNextSequenceFromBOM(dto
						.getSequence(), bomList);
				dao.completeSequence(jobNumber, (String) userContext
						.getQueries().get("SHOP_027"));
				dao.completeSequenceActivities(jobNumber, (String) userContext
						.getQueries().get("SHOPR_051"));
				dao.logSequencePick(dto, userContext.getUserName(),
						(String) userContext.getQueries().get("SHOPR_081"),
						false);
				dto.setMessage("Picked Successfully");

				// Insert the details in Audit Log
				dao.createAuditLog(dto, (String) userContext.getQueries().get(
						"SHOP_092"));

				dto.setComments("");

				dto.setPickSuccess(true);
				dto.setCompleteable(false);
				dto.setPageRefreshValue(2);
			}

		} else if (operation != null && "addNew".equals(operation)) {

			System.out.println("Add New OPeration called...........");
			dto.setReasonCode(Integer.parseInt(request.getParameter("reason")));
			dto.setComments(request.getParameter("comments"));
			dto.setCompany(m3APIWSClient.getApplicationPropertyBean()
					.getCompany());
			dto.setUOM(M3Constants.UOM);
			dto.setCompletionFlag("1");
			System.out.println("Facility passed-->" + dto.getFacility());
			String message = PMS070MIWriteUtil.pickSequenceInM3(dto,
					m3APIWSClient);
			dto.setReportedToM3(true);
			if (message == null) {
				List bomList = PMS100MIReadUtil
						.getOperation(dto, m3APIWSClient);
				String nextSequenceFromBOM = getNextSequenceFromBOM(dto
						.getSequence(), bomList);
				System.out.println("nextSequenceFromBOM==="
						+ nextSequenceFromBOM);
				dto.setNextSequenceNumber(nextSequenceFromBOM);
				fetchDepartment(dto);
				createAJobAndPick(dto);
				dao.logSequencePick(dto, userContext.getUserName(),
						(String) userContext.getQueries().get("SHOPR_081"),
						true);
				dto.setMessage("Picked Successfully");

				// Insert the details in Audit Log
				dao.createAuditLog(dto, (String) userContext.getQueries().get(
						"SHOP_092"));

				getSequenceDetails(dto);
				dto.setAddNewJob(false);
				dto.setCompleteable(false);
			} else {
				System.out.println("M3 Return message--->"
						+ message.substring(3, message.length() - 8).trim());
				dto.setMessage(trimMessage(message));
			}
			dto.setPageRefreshValue(2);
		}

		return new ModelAndView(getSuccessView(), "command", command);
	}

	/**
	 * Trim message.
	 * 
	 * @param message
	 *            the message
	 * @return the string
	 */
	public String trimMessage(String message) {
		String[] errorMsg = message.split(":");
		String afterRemovingSpace = "";
		if (errorMsg != null) {
			String[] messageArray = message.split(" ");

			for (int i = 0; i < messageArray.length; i++) {
				if (!messageArray[i].trim().equals("")) {
					afterRemovingSpace = afterRemovingSpace + " "
							+ messageArray[i].trim();
				}
			}
			afterRemovingSpace = afterRemovingSpace.substring(
					afterRemovingSpace.indexOf("-") + 1, afterRemovingSpace
							.lastIndexOf(" "));

		}
		return afterRemovingSpace.trim();
	}

	/**
	 * Creates the a job and pick.
	 * 
	 * @param dto
	 *            the dto
	 */
	private void createAJobAndPick(Sequence dto) {
		dto.setQuery((String) userContext.getQueries().get("SHOPR_059"));
		dao.createAndCompleteSequence(dto, userContext.getUserName());
	}

	/**
	 * Fetch department.
	 * 
	 * @param dto
	 *            the dto
	 */
	private void fetchDepartment(Sequence dto) {
		try {
			String dept = dao.getDeptByWorkCenter(dto.getFacility(),
					dto.getWorkCenterCode()).getDepartment();
			dto.setDepartment(dept);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out
					.println("Error While Fetching the Department Using Work Center");
		}
	}

	/**
	 * Gets the sequence details.
	 * 
	 * @param dto
	 *            the dto
	 * @return the sequence details
	 */
	public void getSequenceDetails(Sequence dto) {
		dto.setQuery((String) userContext.getQueries().get("SHOPR_050"));
		ArrayList sequencesDetails = dao.getSequenceDetailsForPick(dto);
		dto.setSequenceDetails(sequencesDetails);
	}

	/**
	 * Checks if is sequence completeable.
	 * 
	 * @param sequencesDetails
	 *            the sequences details
	 * @return true, if is sequence completeable
	 */
	private boolean isSequenceCompleteable(ArrayList sequencesDetails) {
		int completedJobsCount = 0;
		for (Iterator iter = sequencesDetails.iterator(); iter.hasNext();) {
			Sequence sequence = (Sequence) iter.next();
			if ("LoggedIn".equals(sequence.getStatus())) {
				return false;
			}

			if ("Complete".equals(sequence.getStatus())) {
				completedJobsCount++;
			}
		}

		if (completedJobsCount == sequencesDetails.size()
				&& sequencesDetails.size() != 0) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the completed qty.
	 * 
	 * @param currentSequence
	 *            the current sequence
	 * @return the completed qty
	 */
	private double getCompletedQty(Sequence currentSequence) {
		double completedQty = 0;
		List localActivities = dao.getLocalActivitiesForMOLineSeq(
				currentSequence, (String) userContext.getQueries().get(
						"SHOP_011"));
		for (Iterator iterator = localActivities.iterator(); iterator.hasNext();) {
			Sequence sequence = (Sequence) iterator.next();
			if ("R".equalsIgnoreCase(sequence.getActivityCode())) {
				completedQty = completedQty + sequence.getQtyCompleted();
			}
		}
		return completedQty;
	}

	/**
	 * Gets the next sequence from bom.
	 * 
	 * @param givenSeqNumber
	 *            the given seq number
	 * @param bomList
	 *            the bom list
	 * @return the next sequence from bom
	 */
	public String getNextSequenceFromBOM(String givenSeqNumber, List bomList) {
		String nextSeqNumber = null;
		if (bomList != null) {
			for (int i = 0; i < bomList.size(); i++) {
				if (i == (bomList.size() - 1)) {
					return null;
				}
				Sequence sequence = (Sequence) bomList.get(i);
				if (givenSeqNumber.equals(sequence.getSequence().trim())) {
					Sequence nextSequence = (Sequence) bomList.get(i + 1);
					if (nextSequence != null
							&& !nextSequence.getSequence().equals(
									givenSeqNumber.trim())) {
						nextSeqNumber = nextSequence.getSequence();

						break;
					}
				}

			}
		}
		System.out.println("Before ");
		return nextSeqNumber;
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
	 *            the new dao
	 */
	public void setDao(SequenceDAO dao) {
		this.dao = dao;
	}

	/**
	 * Gets the user context.
	 * 
	 * @return the user context
	 */
	public UserContext getUserContext() {
		return userContext;
	}

	/**
	 * Sets the user context.
	 * 
	 * @param userContext
	 *            the new user context
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

}
