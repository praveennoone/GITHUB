package com.gavs.hishear.web.reports.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.SimpleFormController;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.domain.Sequence;

/**
 * 
 * @author rahjeshd
 * 
 */
public class ControllerSequenceDetails extends SimpleFormController {

	private Sequence dto;
	private SequenceDAO dao;
	private UserContext userContext;

	public ControllerSequenceDetails() {
		setCommandName("command");
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
		dto = new Sequence();

		String moNumber = request.getParameter("moNumber");

		String sequenceNumber = request.getParameter("sequenceNumber");
		String activityCode = request.getParameter("activityCode");
		String facility = request.getParameter("facility");

		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		dto.setMoNumber(moNumber);
		dto.setLineNumber("000");
		dto.setSequence(sequenceNumber);
		dto.setFacility(facility);

		dto.setActivityCode(activityCode);
		dto.setQuery((String) userContext.getQueries().get("SHOPR_048"));
		ArrayList sequenceDetails = dao.getSequenceDetails(dto);
		dto.setSequenceDetails(sequenceDetails);
		return dto;
	}

	/**
	 * @return the dao
	 */
	public SequenceDAO getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(SequenceDAO dao) {
		this.dao = dao;
	}

	/**
	 * @return the userContext
	 */
	public UserContext getUserContext() {
		return userContext;
	}

	/**
	 * @param userContext
	 *            the userContext to set
	 */
	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}
}