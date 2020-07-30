package com.gavs.hishear.web.reports.management.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.reports.management.dao.ManagementDao;
import com.gavs.hishear.web.reports.management.domain.ManagementalDto;
import com.gavs.hishear.web.view.EmployeePerformanceExcelView;

public class ControllerExceptionalActivity extends SimpleFormController {

	private ManagementalDto dto;
	private ManagementDao dao;
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
		dto = (ManagementalDto) command;
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		dto.setFromDate(dto.getFromDate().replaceAll("/", "-"));
		dto.setToDate(dto.getToDate().replaceAll("/", "-"));
		String empId = dto.getEmpId();

		ArrayList report = dao.getExpActivityReport(dto, userContext);

		Map reports = new HashMap();
		reports.put("0", report);

		dto.setEmployeePerformanceReport(report);
		dto.setFromDate(dto.getFromDate().replaceAll("/", "-"));
		dto.setToDate(dto.getToDate().replaceAll("/", "-"));

		if (isExcel != null && isExcel.equalsIgnoreCase("yes")) {
			return new ModelAndView(new EmployeePerformanceExcelView(reports));
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
		dto = new ManagementalDto();
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());

		dto.setEmpId(request.getParameter("empNo"));
		dto.setMoNumber(request.getParameter("moNo"));
		dto.setLineNumber(request.getParameter("lineNo"));
		dto.setDepartment(request.getParameter("dept"));

		ArrayList report = dao.getExpActivityReport(dto, userContext);

		dto.setExceptionalActivityReport(report);

		return dto;
	}

	/**
	 * @return the dao
	 */
	public ManagementDao getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(ManagementDao dao) {
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
