package com.gavs.hishear.web.reports.exceptional.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.gavs.hishear.constant.Constant;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.reports.exceptional.dao.ExceptionalDao;
import com.gavs.hishear.web.reports.exceptional.domain.ExceptionalDto;

public class ControllerLogonLogoffDetails extends SimpleFormController {

	private ExceptionalDto dto;
	private ExceptionalDao dao;
	private UserContext userContext;

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
		SimpleDateFormat format = new SimpleDateFormat(
				Constant.DATE_FORMAT_UPTOMILLISCEONDS);
		dto = new ExceptionalDto();
		dto.setEmpId(request.getParameter("empID"));
		dto.setDeptId(request.getParameter("dept"));
		dto.setPunchInDate(format.parse(request.getParameter("punchIn")));
		String punchoutTime = request.getParameter("punchOut");

		if ("".equals(punchoutTime) || punchoutTime == null) {
			dto.setPunchOutDate(new Date());
		} else {
			dto.setPunchOutDate(format.parse(punchoutTime));
		}
		dto.setMoNumber(request.getParameter("moNumber"));
		dto.setAsset(request.getParameter("assetID"));
		dto.setItemNumber(request.getParameter("itemNo"));
		dto.setQuery((String) userContext.getReportQueries().get("SHOPR_047"));
		ArrayList report = dao.getLogonLogoffDetails(dto);

		dto.setLogonLogoffDetailsReport(report);
		return dto;
	}
}
