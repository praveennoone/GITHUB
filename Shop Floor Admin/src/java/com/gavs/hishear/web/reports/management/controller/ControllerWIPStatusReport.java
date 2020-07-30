package com.gavs.hishear.web.reports.management.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.reports.management.dao.WIPStatusReportDao;
import com.gavs.hishear.web.reports.management.domain.WIPStatusDto;
import com.gavs.hishear.web.view.WIPStatusExcelView;

public class ControllerWIPStatusReport extends SimpleFormController {

	private WIPStatusReportDao dao;
	private UserContext userContext;

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String isExcel = (String) request.getParameter("toExcel");
		WIPStatusDto dto = (WIPStatusDto) command;
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		System.out.println("Factory:" + dto.getFactory() + " Part:"
				+ dto.getPartNumber());
		String query = (String) userContext.getQueries().get("SHOPR_080");
		dto.setQuery(query);

		WebUtils.setSessionAttribute(request, getFormSessionAttributeName(),
				command);

		if (isExcel != null && isExcel.equalsIgnoreCase("yes")) {
			Map reports = new HashMap();
			reports.put("0", dto.getWipStatusReport());
			return new ModelAndView(new WIPStatusExcelView(reports), "command",
					command);
		} else {
			dto.setWipStatusReport(dao.getWipStatusReport(dto));
			Collections.sort(dto.getWipStatusReport());
			return new ModelAndView(getSuccessView(), "command", command);
		}
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		WIPStatusDto dto = new WIPStatusDto();
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		this.setCommandName("command");
		this.setSessionForm(true);
		return dto;
	}

	/**
	 * @return the dao
	 */
	public WIPStatusReportDao getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(WIPStatusReportDao dao) {
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