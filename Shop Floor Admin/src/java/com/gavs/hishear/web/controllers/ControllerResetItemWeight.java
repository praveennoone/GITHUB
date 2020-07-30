// File:         ControllerResetItemWeight.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.gavs.hishear.m3interface.M3APIWSClient;
import com.gavs.hishear.util.MMS200MIReadUtil;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.ItemDAO;
import com.gavs.hishear.web.domain.Item;


/**
 * The Class ControllerResetItemWeight.
 */
public class ControllerResetItemWeight extends SimpleFormController {

	/** The dao. */
	private ItemDAO dao;

	/** The m3 apiws client. */
	private M3APIWSClient m3APIWSClient;

	/** The user context. */
	private UserContext userContext;

	/**
	 * Instantiates a new controller reset item weight.
	 */
	public ControllerResetItemWeight() {
		setCommandName("command");
		setCommandClass(Item.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject
	 * (javax.servlet.http.HttpServletRequest)
	 */
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		Item dto = new Item();
		dto.setUsername(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		return dto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.validation.BindException)
	 */
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		Item dto = (Item) command;
		dto.setQuery((String) userContext.getQueries().get("SHOP_006"));
		String itemNumber = dto.getItemNumber();
		dto.setCompany(m3APIWSClient.getApplicationPropertyBean().getCompany());
		ArrayList itmList = MMS200MIReadUtil.getItemBasic(dto, m3APIWSClient);
		if (itmList != null && itmList.size() != 0) {
			Item item = (Item) itmList.get(0);
			if (item == null || item.getErrorMessage() != null) {
				dto.setMessage("Invalid Item Number!!!");
			} else if (dto.getMoNumber() == null
					|| "".equals(dto.getMoNumber())) {
				dto.setMessage("Invalid MO Number!!!");
			} else {
				try {
					getDao().resetItemWeight(dto);
					dto.setMessage("Successfully Updated!!!");
				} catch (Exception e) {
					dto.setMessage(e.getMessage());
				}
			}
		} else {
			dto.setMessage("Invalid Item Number!!!");
		}

		return new ModelAndView(getSuccessView(), "command", command);
	}

	/**
	 * Gets the dao.
	 * 
	 * @return the dao
	 */
	public ItemDAO getDao() {
		return dao;
	}

	/**
	 * Sets the dao.
	 * 
	 * @param dao
	 *            the new dao
	 */
	public void setDao(ItemDAO dao) {
		this.dao = dao;
	}

	/**
	 * Gets the user context.
	 * 
	 * @return the userContext
	 */
	public UserContext getUserContext() {
		return userContext;
	}

	/**
	 * Sets the user context.
	 * 
	 * @param userContext
	 *            the userContext to set
	 */
	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}

	/**
	 * Gets the m3 apiws client.
	 * 
	 * @return the m3APIWSClient
	 */
	public M3APIWSClient getM3APIWSClient() {
		return m3APIWSClient;
	}

	/**
	 * Sets the m3 apiws client.
	 * 
	 * @param client
	 *            the m3APIWSClient to set
	 */
	public void setM3APIWSClient(M3APIWSClient client) {
		m3APIWSClient = client;
	}

}
