package com.gavs.hishear.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.ItemDAO;
import com.gavs.hishear.web.domain.Item;

public class ControllerComponentDetail extends SimpleFormController {
	private ItemDAO dao;

	private UserContext userContext;

	public ControllerComponentDetail() {
		setCommandName("command");
		setCommandClass(Item.class);
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		Item dto = new Item();
		dto.setUsername(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		return dto;
	}

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		System.out.println("on submit");
		return new ModelAndView(getSuccessView(), "command", command);
	}

	public ItemDAO getDao() {
		return dao;
	}

	public void setDao(ItemDAO dao) {
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
