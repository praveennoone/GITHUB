/**
 * 
 */
package com.gavs.hishear.web.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.reports.exceptional.dao.ExceptionalDao;
import com.gavs.hishear.web.reports.exceptional.domain.ExceptionalDto;

/**
 * @author balajij
 * 
 */
public class AdjustActivityPopupController extends SimpleFormController {

	private ExceptionalDto dto;
	private ExceptionalDao dao;
	private UserContext userContext;

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
	 * @return the dto
	 */
	public ExceptionalDto getDto() {
		return dto;
	}

	/**
	 * @param dto
	 *            the dto to set
	 */
	public void setDto(ExceptionalDto dto) {
		this.dto = dto;
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
			HttpServletResponse response, Object command,
			BindException exception) throws Exception {

		ExceptionalDto dto = (ExceptionalDto) command;
		dto.setQuery((String) userContext.getQueries().get("SHOPR_085"));
		dao.updatePauseTime(dto);
		return new ModelAndView(getSuccessView());
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

		ArrayList reports = (ArrayList) request.getSession().getAttribute(
				"report");
		String index = request.getParameter("index");
		System.out.println("Index --> " + index);
		ArrayList newReport = new ArrayList();
		if (index != null && !index.equals("")) {
			dto = (ExceptionalDto) reports.get(Integer.parseInt(index));
			newReport.add(dto);
			dto.setActivityAdjustment(newReport);
		}
		return dto;
	}
}