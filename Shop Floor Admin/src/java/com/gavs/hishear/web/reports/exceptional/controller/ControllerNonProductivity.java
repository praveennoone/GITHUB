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
import com.gavs.hishear.web.reports.management.dao.ManagementDao;
import com.gavs.hishear.util.DateUtil;
import com.gavs.hishear.web.view.NonProductivityExcelView;

/**
 * @author balaji
 * 
 */
public class ControllerNonProductivity extends SimpleFormController {

	private ExceptionalDao dao;
	private ManagementDao managementDao;
	private ExceptionalDto dto;
	private UserContext userContext;

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

		dto.setQuery((String) userContext.getQueries().get("SHOPR_038"));

		ArrayList report = dao.getNonProductivityReport(dto);
		dto.setNonProductivityReport(report);
		Map reports = new HashMap();
		reports.put("0", report);

		dto.setNonProductivityReport(report);
		if (isExcel != null && isExcel.equalsIgnoreCase("yes")) {
			return new ModelAndView(new NonProductivityExcelView(reports));
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

		ArrayList shiftTime = getManagementDao().getShiftTime(userContext);
		dto.setArrShiftTime(shiftTime);

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

	public ManagementDao getManagementDao() {
		return managementDao;
	}

	public void setManagementDao(ManagementDao managementDao) {
		this.managementDao = managementDao;
	}
}