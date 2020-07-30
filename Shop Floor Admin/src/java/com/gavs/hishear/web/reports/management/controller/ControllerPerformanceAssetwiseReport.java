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
import com.gavs.hishear.web.reports.factory.domain.FactoryDto;
import com.gavs.hishear.web.reports.management.dao.ManagementDao;
import com.gavs.hishear.web.reports.management.domain.ManagementalDto;
import com.gavs.hishear.util.DateUtil;
import com.gavs.hishear.util.Util;
import com.gavs.hishear.web.view.PerformanceExcelView;

/**
 * 
 * @author sundarrajanr
 * 
 */
public class ControllerPerformanceAssetwiseReport extends SimpleFormController {

	private FactoryDto dto;
	private ManagementDao dao;
	private UserContext userContext;

	public ControllerPerformanceAssetwiseReport() {
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
		Map reports = new HashMap();
		String isExcel = (String) request.getParameter("toexcel");
		String isFactoryChanged = (String) request
				.getParameter("factoryChanged");

		dto = (FactoryDto) command;
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		dto.setArrDepartments(dao.getDepartmentForFactory(dto, userContext));

		if (isFactoryChanged != null && isFactoryChanged.equalsIgnoreCase("")) {
			setDates();
			if (dto.getFactory() != null
					&& dto.getFactory().equalsIgnoreCase("ALL")) {
				dto.setFactory("");
			}
			if (dto.getDepartment() != null
					&& dto.getDepartment().equalsIgnoreCase("ALL")) {
				dto.setDepartment("");
			}
			ArrayList report = dao.getPerformanceAssetwiseReport(dto,
					userContext);
			dto.setFactoryReport(report);
			dto.setRecordsFound(report.size());
			reports.put("0", report);
		}
		if (isExcel != null && isExcel.equalsIgnoreCase("yes")) {
			return new ModelAndView(new PerformanceExcelView(reports),
					"command", command);
		} else {
			return new ModelAndView(getSuccessView(), "command", command);
		}
	}

	private void setDates() {
		try {
			ManagementalDto currentShift = null;
			String reportDate = dto.getReportDate().substring(0, 10);
			ArrayList<ManagementalDto> shiftTimes = dao
					.getShiftTime(userContext);
			for (ManagementalDto shift : shiftTimes) {
				if (shift.getShift().equalsIgnoreCase(dto.getShift())) {
					currentShift = shift;
				}
			}

			dto
					.setFromDate(reportDate + " "
							+ currentShift.getShiftStartTime());
			dto.setToDate(Util.getTodDateForShift(reportDate, currentShift
					.getShift(), "MM/dd/yyyy")
					+ " " + currentShift.getShiftEndTime());
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
		dto = new FactoryDto();
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		dto.setShift("A"); // Initial value for Shift
		dto.setReportType("A"); // Initial value for Report Type ie Assetwise
		dto.setArrFactory(dao.getFactories(dto, userContext));
		dto.setRecordsFound(-1);
		dto.setReportDate(DateUtil.getCurrentDate("MM/dd/yyyy"));
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
