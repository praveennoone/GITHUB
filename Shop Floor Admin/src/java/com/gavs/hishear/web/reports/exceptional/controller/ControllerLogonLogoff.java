package com.gavs.hishear.web.reports.exceptional.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.reports.exceptional.dao.ExceptionalDao;
import com.gavs.hishear.web.reports.exceptional.domain.ExceptionalDto;
import com.gavs.hishear.util.DateUtil;
import com.gavs.hishear.web.view.LogOnOffExcelView;

/**
 * @author balaji
 * 
 */
public class ControllerLogonLogoff extends SimpleFormController {

	private ExceptionalDto dto;
	private ExceptionalDao dao;
	private UserContext userContext;

	public ControllerLogonLogoff() {
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
		dto = (ExceptionalDto) command;
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());

		String empId = dto.getEmpId();

		String fromDate = dto.getFromDate();
		String toDate = dto.getToDate();

		dto.setQuery((String) userContext.getQueries().get("SHOPR_040"));

		ArrayList report = dao.getLogonLogoffReport(dto);

		dto.setLogonLogoffReport(report);

		Map reports = new HashMap();
		reports.put("0", report);

		dto.setLogonLogoffReport(report);
		if (isExcel != null && isExcel.equalsIgnoreCase("yes")) {
			return new ModelAndView(new LogOnOffExcelView(reports));
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
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		dto = new ExceptionalDto();
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		dto.setFromDate(DateUtil.getCurrentDate("MM/dd/yyyy hh:mm aaa"));
		dto.setToDate(DateUtil.getCurrentDate("MM/dd/yyyy hh:mm aaa"));

		ArrayList departments = dao.getDepartments((String) userContext
				.getReportQueries().get("SHOPR_037"));
		dto.setCat1(departments);
		if (departments != null && !(departments.size() == 0)) {
			ExceptionalDto exceptionalDto = (ExceptionalDto) departments.get(0);

		}
		return dto;
	}

	/**
	 * @return the dao
	 */
	public ExceptionalDao getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(ExceptionalDao dao) {
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