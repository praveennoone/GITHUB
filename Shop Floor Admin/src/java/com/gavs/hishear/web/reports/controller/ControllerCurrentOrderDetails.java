// File:         ControllerCurrentOrderDetails.java
// Created:      Feb 23, 2011
// Author:       rahjeshd
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.reports.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.gavs.hishear.m3interface.M3APIWSClient;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.domain.Sequence;
import com.gavs.hishear.util.PMS100MIReadUtil;
import com.gavs.hishear.web.view.CurrentOrderDetailsExcelView;

/**
 * The Class ControllerCurrentOrderDetails.
 * 
 */
public class ControllerCurrentOrderDetails extends SimpleFormController {

	/** The dto. */
	private Sequence dto;

	/** The dao. */
	private SequenceDAO dao;

	/** The user context. */
	private UserContext userContext;

	/** The m3 apiws client. */
	private M3APIWSClient m3APIWSClient;

	/** The redirect view. */
	private String redirectView;

	/** The main view. */
	private String mainView;

	/**
	 * Instantiates a new controller current order details.
	 */
	public ControllerCurrentOrderDetails() {
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
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		WebUtils.setSessionAttribute(request, getFormSessionAttributeName(),
				command);
		String isExcel = (String) request.getParameter("toexcel");
		dto = (Sequence) command;

		if (dto.getCriteria().equals("mo")) {
			dto.setUserFacility(dto.getMoFacility());

		} else {
			dto.setUserFacility(dto.getItemFacility());

		}

		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		dto.setFacility(dto.getUserFacility());
		if (dto.getCriteria() != null
				&& "Item".equalsIgnoreCase(dto.getCriteria())) {

			dto.setQuery((String) userContext.getQueries().get("SHOPR_049"));
			dto.setMoLines(null);

			ArrayList listMoLines = dao.getMoLine(dto);

			if (listMoLines.size() > 0) {
				dto.setMoLines(listMoLines);
				if (dto.getMoLine() == null
						|| dto.getEmptyString().equals(dto.getMoLine())) {
					dto.setMoNumber(((Sequence) dto.getMoLines().get(0))
							.getMoNumber());
					dto.setLineNumber(((Sequence) dto.getMoLines().get(0))
							.getLineNumber());
				} else {
					dto.setMoNumber(dto.getMoLine().substring(0,
							dto.getMoLine().length()));
					dto.setLineNumber("000");
				}
			}
		}
		ArrayList mergeSeq = fillDetails();
		Map reports = new HashMap();
		reports.put("0", mergeSeq);

		if (isExcel != null && isExcel.equalsIgnoreCase("yes")) {
			return new ModelAndView(new CurrentOrderDetailsExcelView(reports),
					"command", command);
		} else {
			return new ModelAndView(getSuccessView(), "command", command);
		}
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

		String requestType = request.getMethod();
		System.out.println("requestType>>>>>>>>>>>>>>>>>>>>>>>>>>"
				+ requestType);

		String formAttrName = getFormSessionAttributeName(request);
		dto = (Sequence) request.getSession().getAttribute(formAttrName);

		setSessionForm(true);
		if (dto == null) {
			System.out.println("Creating new command object..................");
			dto = new Sequence();
		}
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		dto.setCompany(m3APIWSClient.getApplicationPropertyBean().getCompany());
		dto.setSequences(null);

		String moNumber = request.getParameter("moNumber");
		System.out
				.println("moNumber is:*********************************************************:::::::::::"
						+ moNumber);
		String lineNumber = "000";
		dto.setLineNumber(lineNumber);
		dto.setDivisionFacilities(dao.getFacilities(userContext.getDivision()));

		if (moNumber != null && !dto.isErrorOccured()
				&& "GET".equals(requestType)) {
			dto.setMoNumber(moNumber);
			fillDetails();
			dto.setFacility(request.getParameter("productionFacility"));
			setFormView(redirectView);
		} else {
			setFormView(mainView);
			dto.setCriteria("mo"); // To Disable Mo Number and Line Number in
			// the view because default filter criteria
			// is Item No
		}
		return dto;
	}

	/**
	 * Fill details.
	 * 
	 * @return the array list
	 */
	private ArrayList fillDetails() {
		ArrayList mergeSeq = new ArrayList();
		dto.setQuery((String) userContext.getQueries().get("SHOPR_044"));
		ArrayList<Sequence> completedSequences = dao.getCompletedSequences(dto);

		// Commented for M3 Integration- SM(22-05-09)
		/*
		 * dto.setQuery((String) userContext.getQueries().get("SHOPR_064"));
		 * ArrayList allSequences = dao.getSequences(dto);
		 */
		String userFacility = dto.getUserFacility();
		System.out.println("dto.getUserFacility() = " + dto.getUserFacility());
		ArrayList<Sequence> allSequences = null;
		try {
			allSequences = PMS100MIReadUtil.getOperationByFacility(dto,
					m3APIWSClient);
		} catch (Exception e) {
			dto.setPartDesc(null);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dto.setSequences(allSequences);
		if (allSequences != null && completedSequences != null) {
			mergeSeq = mergeSequences(completedSequences, allSequences);
			dto.setSequences(mergeSeq);

		}

		return mergeSeq;
	}

	/**
	 * Merge sequences.
	 * 
	 * @param completedSequences
	 *            the completed sequences
	 * @param allSequences
	 *            the all sequences
	 * @return the array list
	 */
	private ArrayList<Sequence> mergeSequences(
			ArrayList<Sequence> completedSequences,
			ArrayList<Sequence> allSequences) {
		ArrayList<Integer> indexesAll = new ArrayList<Integer>();
		for (Sequence allSequence : allSequences) {
			// set the Run status to Not Started - SM(M3-I)

			allSequence.setRunStatus("Not Started");
			dto.setPartNumber(allSequence.getPartNumber());
			dto.setPartDesc(allSequence.getPartDesc());
			dto.setOrderQty(allSequence.getOrderQty());
			dto.setFacility(dto.getUserFacility());
			for (Sequence completedSequence : completedSequences) {
				if (allSequence
						.getSequence()
						.trim()
						.equalsIgnoreCase(
								completedSequence.getSequence().trim())) {
					dto.setPartNumber(allSequence.getPartNumber());
					completedSequence.setWorkCenterCode(allSequence
							.getWorkCenterCode());
					completedSequence.setSequenceDescription(allSequence
							.getSequenceDescription());
					completedSequence.setPartDesc(allSequence.getPartDesc());
					completedSequence.setRequiredDate(allSequence
							.getRequiredDate());
					completedSequence.setWorkCenterDesc(allSequence
							.getWorkCenterDesc());
					indexesAll.add(allSequences.indexOf(allSequence));
					break;
				}
			}
		}

		int i = 0;
		for (Integer index : indexesAll) {
			allSequences.remove(index.intValue());
			try {
				allSequences.add(index, completedSequences.get(i));
				i++;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return allSequences;
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
	 * Gets the redirect view.
	 * 
	 * @return the redirectView
	 */
	public String getRedirectView() {
		return redirectView;
	}

	/**
	 * Sets the redirect view.
	 * 
	 * @param redirectView
	 *            the redirectView to set
	 */
	public void setRedirectView(String redirectView) {
		this.redirectView = redirectView;
	}

	/**
	 * Gets the main view.
	 * 
	 * @return the mainView
	 */
	public String getMainView() {
		return mainView;
	}

	/**
	 * Sets the main view.
	 * 
	 * @param mainView
	 *            the mainView to set
	 */
	public void setMainView(String mainView) {
		this.mainView = mainView;
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