/**
 * 
 */
package com.gavs.hishear.web.reports.management.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.reports.management.dao.ManagementDao;
import com.gavs.hishear.web.reports.management.domain.ManagementalDto;
import com.gavs.hishear.util.DateUtil;
import com.gavs.hishear.web.view.ProductionExcelView;

/**
 * @author mohammeda
 * 
 */
public class ControllerFacilityProductionReport extends SimpleFormController {

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
		System.out.println(" On Submit Controller Facility Production Report ");
		WebUtils.setSessionAttribute(request, getFormSessionAttributeName(),
				command);
		String isExcel = (String) request.getParameter("toexcel");
		dto = (ManagementalDto) command;
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());

		String fromDate = dto.getFromDate();
		String toDate = dto.getToDate();
		String moNumber = dto.getMoNumber();
		String empid = dto.getEmpId();
		String condition = "";
		int TotalQtyCompleted = 0;
		Map reports = new HashMap();

		String actionString = request.getParameter("actionString");
		/*
		 * Ramanan.M - 30th Aug : Added the Facility in the Dto to display the
		 * facility in Current Order Details Report POPUP.
		 */
		dto.setFacility(request.getParameter("facility"));
		ArrayList report = dao.getProductionReport(dto, userContext);
		if (report != null && report.size() > 0) {
			for (int i = 0; i < report.size(); i++) {
				ManagementalDto objmanagementalDto = (ManagementalDto) report
						.get(i);
				TotalQtyCompleted = TotalQtyCompleted
						+ Integer
								.parseInt(objmanagementalDto.getCompletedQty());
			}
			dto.setStatus("True");
		} else {
			dto.setStatus("False");
		}
		dto.setTotalQtyCompleted(TotalQtyCompleted);
		dto.setProductionReport(report);

		String workCenterNeeded = "Yes";
		reports.put("0", report);
		reports.put("1", workCenterNeeded);

		if (isExcel != null && isExcel.equalsIgnoreCase("yes")) {
			return new ModelAndView(new ProductionExcelView(reports),
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
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		setSessionForm(true);
		dto = new ManagementalDto();
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		dto.setDivisionFacilities(dao.getFacilities(userContext.getDivision()));
		System.out
				.println("facilities sucessfully fetched and added to dto in "
						+ this);
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
