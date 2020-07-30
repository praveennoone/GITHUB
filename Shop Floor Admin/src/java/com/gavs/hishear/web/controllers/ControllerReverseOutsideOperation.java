package com.gavs.hishear.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.gavs.hishear.m3interface.M3APIWSClient;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.ItemDAO;
import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.domain.ApplicationPropertyBean;
import com.gavs.hishear.web.domain.ReversalDto;

@SuppressWarnings("deprecation")
public class ControllerReverseOutsideOperation extends SimpleFormController {

	private ItemDAO itemDao;
	private UserContext userContext;
	private M3APIWSClient m3APIWSClient;
	private SequenceDAO sequenceDAO;

	private static Logger logger = Logger
			.getLogger(ControllerReverseOutsideOperation.class);
	private ApplicationPropertyBean applicationPropertyBean;

	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		System.out
				.println("formBacking Called--------------------------------");

		Object command = null;

		try {
			command = this.getCommand(request);
		} catch (Exception e) {
			logger
					.info("formBackingObject: exception thrown "
							+ e.getMessage());
		}

		if (command == null) {
			request.getSession(true);
			command = new ReversalDto();
			((ReversalDto) command).setUserName(userContext.getUserName());
			((ReversalDto) command)
					.setDisplayName(userContext.getDisplayName());
		}
		WebUtils.setSessionAttribute(request, getFormSessionAttributeName(),
				command);
		return command;
	}

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		ReversalDto dto = (ReversalDto) command;
		System.out.println("onSubmit Called----------------------------------"
				+ dto.getAction());

		if ("DETAILS".equals(dto.getAction())) {
			// All required data has already been read in the Validator
			return new ModelAndView(getSuccessView(), "command", command);
		} else if ("REVERSE".equals(dto.getAction())) {
			Map model = new HashMap();
			try {
				sequenceDAO.reversePOLineTransaction(dto.getWarehouse(), dto
						.getPoNumber(), dto.getPoLineNumber(), dto
						.getPoSubLineNumber(), dto.getPoStatus(), dto
						.getPoLineSuffix());

				sequenceDAO.deleteSFSSequence(dto.getMoNumber(), dto
						.getOperationNumber(), (String) userContext
						.getQueries().get("SHOP_145"));

				model.put("message", "Successfully Reversed!");
				model.put("messageType", "SUCCESS");

			} catch (Exception e) {
				dto.setReversed(false);
				model
						.put("message", "Could Not Be Reversed!-"
								+ e.getMessage());
				model.put("messageType", "ERROR");
			}

			WebUtils.setSessionAttribute(request,
					getFormSessionAttributeName(), null);

			return new ModelAndView(getSuccessView(), model);
		}
		return new ModelAndView(getSuccessView(), "command", command);
	}

	/**
	 * @return the applicationPropertyBean
	 */
	public ApplicationPropertyBean getApplicationPropertyBean() {
		return applicationPropertyBean;
	}

	/**
	 * @param applicationPropertyBean
	 *            the applicationPropertyBean to set
	 */
	public void setApplicationPropertyBean(
			ApplicationPropertyBean applicationPropertyBean) {
		this.applicationPropertyBean = applicationPropertyBean;
	}

	/**
	 * @return the m3APIWSClient
	 */
	public M3APIWSClient getM3APIWSClient() {
		return m3APIWSClient;
	}

	/**
	 * @param client
	 *            the m3APIWSClient to set
	 */
	public void setM3APIWSClient(M3APIWSClient client) {
		m3APIWSClient = client;
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

	/**
	 * @return the sequenceDAO
	 */
	public SequenceDAO getSequenceDAO() {
		return sequenceDAO;
	}

	/**
	 * @param sequenceDAO
	 *            the sequenceDAO to set
	 */
	public void setSequenceDAO(SequenceDAO sequenceDAO) {
		this.sequenceDAO = sequenceDAO;
	}

	/**
	 * @return the itemDao
	 */
	public ItemDAO getItemDao() {
		return itemDao;
	}

	/**
	 * @param itemDao
	 *            the itemDao to set
	 */
	public void setItemDao(ItemDAO itemDao) {
		this.itemDao = itemDao;
	}

}
