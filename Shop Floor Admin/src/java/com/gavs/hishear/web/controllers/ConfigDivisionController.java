// File:         CorrectionIntModifyMOController.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.WorkCenterDAO;
import com.gavs.hishear.web.domain.ConfigDivision;

/**
 * The Class CorrectionIntModifyMOController.
 */
public class ConfigDivisionController extends SimpleFormController {

	/** The dto. */
	private ConfigDivision dto;

	/** The dao. */
	private WorkCenterDAO workCenterDao;

	/** The user context. */
	private UserContext userContext;

	/**
	 * Instantiates a new correction int modify mo controller.
	 */
	public ConfigDivisionController() {
		setCommandName("command");
		setSessionForm(true);
	}

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		WebUtils.setSessionAttribute(request, getFormSessionAttributeName(),
				command);

		dto = (ConfigDivision) command;

		dto.setDivision(userContext.getDivision());

		String query = (String) userContext.getQueries().get("SHOPR_102");

		String logQuery = (String) userContext.getQueries().get("SHOPR_104");

		boolean flag = false;
		dto.setMessage("");
		dto.setError("");

		if (!dto.getPositiveFirstOp().trim().equals(
				dto.getOldPositiveFirstOp().trim())) {

			workCenterDao.insertVarianceLogDetails(userContext, String
					.valueOf(Integer.parseInt(dto.getOldPositiveFirstOp()
							.trim())), "First", "Positive", dto.getDivision(),
					logQuery);
			workCenterDao.updateVarianceDetails(String.valueOf(Integer
					.parseInt(dto.getPositiveFirstOp().trim())), "First",
					"Positive", dto.getDivision(), query);
			flag = true;
		}
		if (!dto.getNegativeFirstOp().trim().equals(
				dto.getOldNegativeFirstOp().trim())) {

			workCenterDao.insertVarianceLogDetails(userContext, String
					.valueOf(Integer.parseInt(dto.getOldNegativeFirstOp()
							.trim())), "First", "Negative", dto.getDivision(),
					logQuery);
			workCenterDao.updateVarianceDetails(String.valueOf(Integer
					.parseInt(dto.getNegativeFirstOp().trim())), "First",
					"Negative", dto.getDivision(), query);
			flag = true;
		}
		if (!dto.getPositiveLastOp().trim().equals(
				dto.getOldPositiveLastOp().trim())) {

			workCenterDao.insertVarianceLogDetails(userContext, String
					.valueOf(Integer
							.parseInt(dto.getOldPositiveLastOp().trim())),
					"Last", "Positive", dto.getDivision(), logQuery);
			workCenterDao.updateVarianceDetails(String.valueOf(Integer
					.parseInt(dto.getPositiveLastOp().trim())), "Last",
					"Positive", dto.getDivision(), query);
			flag = true;
		}
		if (!dto.getNegativeLastOp().trim().equals(
				dto.getOldNegativeLastOp().trim())) {

			workCenterDao.insertVarianceLogDetails(userContext, String
					.valueOf(Integer
							.parseInt(dto.getOldNegativeLastOp().trim())),
					"Last", "Negative", dto.getDivision(), logQuery);
			workCenterDao.updateVarianceDetails(String.valueOf(Integer
					.parseInt(dto.getNegativeLastOp().trim())), "Last",
					"Negative", dto.getDivision(), query);
			flag = true;
		}
		if (!dto.getPositiveOtherOp().trim().equals(
				dto.getOldPositiveOtherOp().trim())) {

			workCenterDao.insertVarianceLogDetails(userContext, String
					.valueOf(Integer.parseInt(dto.getOldPositiveOtherOp()
							.trim())), "Other", "Positive", dto.getDivision(),
					logQuery);
			workCenterDao.updateVarianceDetails(String.valueOf(Integer
					.parseInt(dto.getPositiveOtherOp().trim())), "Other",
					"Positive", dto.getDivision(), query);
			flag = true;
		}
		if (!dto.getNegativeOtherOp().trim().equals(
				dto.getOldNegativeOtherOp().trim())) {

			workCenterDao.insertVarianceLogDetails(userContext, String
					.valueOf(Integer.parseInt(dto.getOldNegativeOtherOp()
							.trim())), "Other", "Negative", dto.getDivision(),
					logQuery);
			workCenterDao.updateVarianceDetails(String.valueOf(Integer
					.parseInt(dto.getNegativeOtherOp().trim())), "Other",
					"Negative", dto.getDivision(), query);
			flag = true;
		}
		if (flag) {
			dto.setMessage("Updated Successfully");
			String getquery = (String) userContext.getQueries()
					.get("SHOPR_103");
			dto = workCenterDao.getVarianceDetails(dto, getquery);
			dto.setError("");
			flag = false;
		} else {
			dto.setMessage("");
			dto.setError("No Changes Made");
		}
		return new ModelAndView(getSuccessView(), "command", command);
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {

		dto = new ConfigDivision();
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		dto.setDivision(userContext.getDivision());

		String query = (String) userContext.getQueries().get("SHOPR_103");

		dto = workCenterDao.getVarianceDetails(dto, query);

		return dto;

	}

	/**
	 * Gets the user context.
	 * 
	 * @return the user context
	 */
	public UserContext getUserContext() {
		return userContext;
	}

	/**
	 * Sets the user context.
	 * 
	 * @param userContext
	 *            the new user context
	 */
	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}

	/**
	 * @param workCenterDao
	 *            the workCenterDao to set
	 */
	public void setWorkCenterDao(WorkCenterDAO workCenterDao) {
		this.workCenterDao = workCenterDao;
	}

	/**
	 * @return the workCenterDao
	 */
	public WorkCenterDAO getWorkCenterDao() {
		return workCenterDao;
	}

}
