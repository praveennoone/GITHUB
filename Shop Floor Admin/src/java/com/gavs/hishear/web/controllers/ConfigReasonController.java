/**
 * WorkCenterConfigController.java
 */
package com.gavs.hishear.web.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.dao.WorkCenterDAO;
import com.gavs.hishear.web.domain.ApplicationPropertyBean;
import com.gavs.hishear.web.domain.ReasonCode;
import com.gavs.hishear.web.domain.ReasonType;

public class ConfigReasonController extends SimpleFormController {

	/** The dto. */
	private ReasonCode dto;

	/** The dao. */
	private SequenceDAO dao;

	private WorkCenterDAO workCenterDao;

	/** The user context. */
	private UserContext userContext;

	/** The application property bean. */
	private ApplicationPropertyBean applicationPropertyBean;

	/**
	 * Instantiates a new correction int modify mo controller.
	 */
	public ConfigReasonController() {
		setCommandName("command");
		setSessionForm(true);
	}

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		WebUtils.setSessionAttribute(request, getFormSessionAttributeName(),
				command);

		dto = (ReasonCode) command;
		String userAction = request.getParameter("userAction");
		System.out.println("userAction = " + userAction);
		try {
			if (userAction != null) {
				if (userAction.equalsIgnoreCase("getReasonFilterDetails")) {

					dto.setSelectedReasonType(request.getParameter("reason")
							.trim());
					String query = "";
					if (request.getParameter("reason").isEmpty()) {
						query = (String) userContext.getQueries().get(
								"SHOPR_105");
						dto.setReasonDetails(workCenterDao.getReasonDetails(
								dto, query));
					} else {
						query = (String) userContext.getQueries().get(
								"M3ORAL_008");
						dto.setReasonDetails(workCenterDao
								.getReasonFilterDetails(dto, query));
					}
				} else if (userAction.equalsIgnoreCase("insertNewReason")) {

					String reasonType = (request.getParameter("reason"));
					String reasonDesc = request.getParameter("description");

					boolean duplicateFlag = false;
					for (ReasonType reasondescrip : dto.getReasonDetails()) {
						if (reasonDesc.equals(reasondescrip.getReasonDesc())) {
							dto.setError("Duplicate Data Entry");
							duplicateFlag = true;
							break;
						}
					}
					if (!duplicateFlag) {
						dto.setError("");
						String query = (String) userContext.getQueries().get(
								"SHOPR_106");
						try {
							workCenterDao.insertNewReason(reasonType,
									reasonDesc, query);
						} catch (Exception ex) {
							dto.setError("Error Inserting Data");
							ex.printStackTrace();
						}
					}

				} else {

					throw new Exception("no valid action");

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return new ModelAndView(getSuccessView(), "command", command);
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {

		dto = new ReasonCode();
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());

		String query = (String) userContext.getQueries().get("SHOPR_105");

		dto.setReasonDetails(workCenterDao.getReasonDetails(dto, query));

		String reasonType = "";
		int i = 0;
		ArrayList<String> reasonTypeList = new ArrayList<String>();
		for (ReasonType reason : dto.getReasonDetails()) {
			if (i == 0) {
				reasonTypeList.add(reason.getReasonType());
				i = i + 1;
				reasonType = reason.getReasonType();
			} else if (!reasonType.equals(reason.getReasonType())) {
				reasonType = reason.getReasonType();
				reasonTypeList.add(reason.getReasonType());
			}
		}

		dto.setReasonType(reasonTypeList);

		return dto;

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
	 *            the new dao
	 */
	public void setDao(SequenceDAO dao) {
		this.dao = dao;
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
	 * Sets the application property bean.
	 * 
	 * @param applicationPropertyBean
	 *            the applicationPropertyBean to set
	 */
	public void setApplicationPropertyBean(
			ApplicationPropertyBean applicationPropertyBean) {
		this.applicationPropertyBean = applicationPropertyBean;
	}

	/**
	 * Gets the application property bean.
	 * 
	 * @return the applicationPropertyBean
	 */
	public ApplicationPropertyBean getApplicationPropertyBean() {
		return applicationPropertyBean;
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
