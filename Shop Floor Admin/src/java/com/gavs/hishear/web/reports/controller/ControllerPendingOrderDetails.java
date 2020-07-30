package com.gavs.hishear.web.reports.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.domain.Sequence;

/**
 * 
 * @author rahjeshd
 * 
 */
public class ControllerPendingOrderDetails extends SimpleFormController {

	private Sequence dto;

	private SequenceDAO dao;

	private UserContext userContext;

	public ControllerPendingOrderDetails() {
		setCommandName("command");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String isExcel = (String) request.getParameter("toexcel");
		dto = (Sequence) command;
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		if (dto.getType() == null) {
			dto.setType("pending");
		}
		String query = null;
		if ("current".equals(dto.getType())) {
			query = (String) userContext.getQueries().get("SHOPR_052");
		} else {
			query = (String) userContext.getQueries().get("SHOPR_053");
		}

		dto.setQuery(query);

		List report = dao.getPendingOrders(dto);
		System.out.println("Report Size === " + report.size());
		dto.setSequences(report);
		return new ModelAndView(getSuccessView(), "command", command);
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
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		if (dto.getType() == null) {
			dto.setType("current");
		}
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