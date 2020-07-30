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
import com.gavs.hishear.util.DateUtil;
import com.gavs.hishear.web.view.ActivityRejectedExcelView;

/**
 * 
 * @author sundarrajanr
 * 
 */
public class ControllerActivityLog extends SimpleFormController {

	private ManagementalDto dto;
	private ManagementDao dao;
	private UserContext userContext;

	public ControllerActivityLog() {
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
		dto = (ManagementalDto) command;
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());

		ArrayList report = dao.getActivityLog(dto, userContext);
		dto.setActivityLog(report);

		Map reports = new HashMap();
		reports.put("0", report);

		if (isExcel != null && isExcel.equalsIgnoreCase("yes")) {
			return new ModelAndView(new ActivityRejectedExcelView(reports));
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

		dto.setFromDate(DateUtil.getCurrentDate("MM/dd/yyyy hh:mm aaa"));
		dto.setToDate(DateUtil.getCurrentDate("MM/dd/yyyy hh:mm aaa"));

		ArrayList shiftTime = dao.getShiftTime(userContext);

		dto.setArrShiftTime(shiftTime);

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
