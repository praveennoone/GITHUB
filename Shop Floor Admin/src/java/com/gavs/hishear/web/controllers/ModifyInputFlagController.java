// File:         ModifyInputFlagController.java
// Created:      Feb 23, 2011
// Author:       rahjeshd
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.gavs.hishear.m3interface.M3APIWSClient;
import com.gavs.hishear.util.PMS100MIReadUtil;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.domain.Sequence;


/**
 * The Class ModifyInputFlagController.
 * 
 */
public class ModifyInputFlagController extends SimpleFormController {

	/** The dao. */
	private SequenceDAO dao;

	/** The user context. */
	private UserContext userContext;

	/** The m3 apiws client. */
	private M3APIWSClient m3APIWSClient;

	/**
	 * Instantiates a new modify input flag controller.
	 */
	public ModifyInputFlagController() {
		setCommandName("command");
		setCommandClass(Sequence.class);
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
		Sequence dto = new Sequence();
		dto.setUserName(userContext.getUserName());
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
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		Sequence dto = (Sequence) command;

		// Added for M3 Integration
		dto.setLineNumber("000");
		String moNumber = dto.getMoNumber();

		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		dto.setCompany(m3APIWSClient.getApplicationPropertyBean().getCompany());

		ArrayList<Sequence> sequences = PMS100MIReadUtil.getOperation(dto,
				m3APIWSClient);

		LinkedHashMap hashMap = new LinkedHashMap();
		for (Sequence sequence : sequences) {
			if (sequence.getErrorMessage() == null) {
				hashMap.put(sequence.getSequence(), sequence);
			}
		}

		ArrayList<Sequence> formatedSequences = new ArrayList<Sequence>();

		Set keySet = hashMap.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			formatedSequences.add((Sequence) hashMap.get(key));
			System.out.println("hashMap.get(key)====" + hashMap.get(key));
		}

		for (Sequence sequence : formatedSequences) {
			sequence.setMoNumber(moNumber);
			// sequence.setLineNumber(lineNumber);
			sequence
					.setQuery((String) userContext.getQueries().get("SHOP_031"));
			dao.checkInputFlag(sequence);
		}

		dto.setSequences(formatedSequences);
		return new ModelAndView(getSuccessView(), "command", command);
	}

	/**
	 * Gets the dao.
	 * 
	 * @return the dao
	 */
	public SequenceDAO getDao() {
		return dao;
	}

	/**
	 * Sets the dao.
	 * 
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(SequenceDAO dao) {
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