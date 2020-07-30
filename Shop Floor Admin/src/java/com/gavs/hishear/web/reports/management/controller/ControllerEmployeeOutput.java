package com.gavs.hishear.web.reports.management.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.SimpleFormController;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.reports.management.dao.ManagementDao;
import com.gavs.hishear.web.reports.management.domain.EmployeeOutputDto;
import com.gavs.hishear.util.DateUtil;

/**
 * @author balaji
 * 
 */
public class ControllerEmployeeOutput extends SimpleFormController {

	private EmployeeOutputDto dto;
	private ManagementDao dao;
	private UserContext userContext;

	public ControllerEmployeeOutput() {
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
		dto = new EmployeeOutputDto();
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		dto.setFromDate(DateUtil.getCurrentDate("MM/dd/yyyy hh:mm aaa"));
		dto.setToDate(DateUtil.getCurrentDate("MM/dd/yyyy hh:mm aaa"));

		ArrayList departments = dao.getDepartments(userContext);
		dto.setDepartments(departments);

		return dto;
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

	public ManagementDao getDao() {
		return dao;
	}

	public void setDao(ManagementDao dao) {
		this.dao = dao;
	}

	public EmployeeOutputDto getDto() {
		return dto;
	}

	public void setDto(EmployeeOutputDto dto) {
		this.dto = dto;
	}
}