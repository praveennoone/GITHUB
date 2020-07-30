package com.gavs.hishear.web.reports.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.DeptWCAssetReportDAO;
import com.gavs.hishear.web.reports.DeptWCAssetReport;

/**
 * 
 * @author rahjeshd
 * 
 */
public class ControllerDeptWCAssetReport extends SimpleFormController {

	private DeptWCAssetReport dto;

	private DeptWCAssetReportDAO dao;

	private UserContext userContext;

	public ControllerDeptWCAssetReport() {
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
		dto = (DeptWCAssetReport) command;
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		if (dto.getAddDept() != null) {
			dto.setQuery((String) userContext.getQueries().get("SHOPR_055"));
			dao.insertDepartment(dto);
			dto.setDepartments(dao.getDepartments((String) userContext
					.getQueries().get("SHOPR_058")));
		} else {
			System.out.println("To view the department....................");
			dto.setQuery((String) userContext.getQueries().get("SHOPR_057"));
			dto.setWorkcenters(dao.getWorkcenters(dto));
			dto.setQuery((String) userContext.getQueries().get("SHOPR_056"));
			dto.setAssets(dao.getAssetDetails(dto));
		}

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
		dto = new DeptWCAssetReport();
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		dto.setDepartments(dao.getDepartments((String) userContext.getQueries()
				.get("SHOPR_058")));

		return dto;
	}

	/**
	 * @return the dao
	 */
	public DeptWCAssetReportDAO getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(DeptWCAssetReportDAO dao) {
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