/**
 * WorkCenterConfigController.java
 */
package com.gavs.hishear.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.WebUtils;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.dao.WorkCenterDAO;
import com.gavs.hishear.web.domain.ApplicationPropertyBean;
import com.gavs.hishear.web.domain.ManufacturingOrder;
import com.gavs.hishear.web.domain.WorkCenterConfig;

public class WorkCenterConfigController extends SimpleFormController {

	/** The dto. */
	private ManufacturingOrder dto;

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
	public WorkCenterConfigController() {
		setCommandName("command");
		setSessionForm(true);
	}

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		WebUtils.setSessionAttribute(request, getFormSessionAttributeName(),
				command);

		dto = (ManufacturingOrder) command;
		String userAction = request.getParameter("userAction");
		System.out.println("userAction = " + userAction);
		WorkCenterConfig workCenter = new WorkCenterConfig();
		try {
			if (userAction != null) {
				dto.setEditFlag(false);
				if (userAction.equalsIgnoreCase("getWorkCenterConfigDetails")) {
					// method call to load all the data for the MO and facility
					// if available.
					dto.setEditFlag(false);
					workCenter.setWorkCenterCode(request.getParameter(
							"workCenterCode").trim());
					workCenter.setFacility(request.getParameter("facility"));
					String query = (String) userContext.getQueries().get(
							"SHOP_116");

					dto.setError("");

					try {

						dto.setWorkCenterConfig(workCenterDao
								.getWorkCenterConfigDetails(workCenter, query));

					} catch (Exception ex) {

						dto.setError("Error while retrieving the Data");
						ex.printStackTrace();

					}

					dto.setWorkCenterCode(request
							.getParameter("workCenterCode").trim());
					dto.setFacility(request.getParameter("facility"));

				} else if (userAction
						.equalsIgnoreCase("updateWorkCenterConfigDetails")) {
				
					String scaleRequired = request
							.getParameter("scaleReqd_Chk");

					if (request.getParameter("scaleReqd_Chk") == null) {

						scaleRequired = "N";

					}

					String activeFlag = request.getParameter("activeFlag_Chk");

					if (request.getParameter("activeFlag_Chk") == null) {

						activeFlag = "N";

					}
					
					//below code is added for ticket 414799 by saikiran-Verinon
					String scrapCount = request.getParameter("scrapCount_Chk");

					if (request.getParameter("scrapCount_Chk") == null) {

						scrapCount = "N";

					}
					//end 414799-Verinon
					
					dto.setError("");
					workCenter.setFacility(dto.getSelectedFacility());
					workCenter.setWorkCenterCode(request.getParameter(
							"workCenter").trim());
					workCenter.setUpdatedDescription(request.getParameter(
							"description").trim());
					workCenter.setupdatedPPHThresholdPercentage(Integer
							.parseInt(request.getParameter("pph_TextField")
									.trim()));
					workCenter.setUpdatedScaleRequired(scaleRequired);
					workCenter.setUpdatedActiveFlag(activeFlag);
					//below line added as part of 414799 by saikiran-verinon
					workCenter.setScrapCount(scrapCount);
					String query = (String) userContext.getQueries().get(
							"SHOPR_096");
					String insertLogQuery = (String) userContext.getQueries()
							.get("SHOPR_098");
					
					try {

						/*workCenterDao.insertWorkCenterLogDetails(workCenter,
								userContext, insertLogQuery);*/
						workCenterDao.updateWorkCenterConfigDetails(workCenter,
								query);
						workCenterDao.insertWorkCenterLogDetails(workCenter,
								userContext, insertLogQuery);
					} catch (Exception ex) {

						dto.setError("Error while Updating the Data");
						ex.printStackTrace();

					}

				} else if (userAction.equalsIgnoreCase("addNewWorkCenter")) {

					workCenter.setFacility(request.getParameter("facility"));

					String query = (String) userContext.getQueries().get(
							"SHOP_116");

					dto.setWorkCenterConfig(workCenterDao
							.getWorkCenterConfigDetails(workCenter, query));

					dto.setError("");

					if (dto.getWorkCenterConfig() != null
							&& dto.getWorkCenterConfig().size() > 0) {
						for (WorkCenterConfig check : dto.getWorkCenterConfig()) {

							if (Integer.parseInt(check.getWorkCenterCode()
									.trim()) == Integer.parseInt(request
									.getParameter("workCenterCode").trim())) {

								dto.setError("Work Center Already Exists");
								break;
							}
						}
					}

				} else if (userAction
						.equalsIgnoreCase("insertNewWorkCenterDetails")) {
					
					workCenter.setWorkCenterCode(request.getParameter(
							"workCenterCode").trim());
					workCenter.setFacility(request.getParameter("facility"));

					String insertQuery = (String) userContext.getQueries().get(
							"SHOPR_097");

					dto.setError("");

					String scaleRequired = request
							.getParameter("scaleReqd_Chk");

					if (scaleRequired == null) {

						scaleRequired = "N";

					}

					String activeFlag = request.getParameter("activeFlag_Chk");

					if (activeFlag == null) {

						activeFlag = "N";

					}
					
					//below code is added for ticket 414799 by saikiran-Verinon
					String scrapCount = request.getParameter("scrapCount_Chk");

					if (request.getParameter("scrapCount_Chk") == null) {

						activeFlag = "N";

					}
					//end 414799-Verinon
					

					workCenter.setFacility(request.getParameter("facility"));
					workCenter.setWorkCenterCode(request.getParameter(
							"workCenterCode").trim());
					workCenter.setDescription(request.getParameter(
							"description").toUpperCase().trim());
					workCenter.setupdatedPPHThresholdPercentage(Integer
							.parseInt(request.getParameter("pph_TextField")
									.trim()));
					workCenter.setUpdatedScaleRequired(scaleRequired);
					workCenter.setUpdatedActiveFlag(activeFlag);
					workCenter.setScrapCount(scrapCount);

					try {

						workCenterDao.insertNewWorkCenterDetails(workCenter,
								insertQuery);

					} catch (Exception ex) {

						ex.printStackTrace();
						dto.setError("Error Inserting Data");

					}

				} else if (userAction.equalsIgnoreCase("editWorkCenter")) {
					
					dto.setEditFlag(true);
					int pph = 0;
					if (request.getParameter("pphThreshold").trim() == null) {
						pph = 0;
					} else {
						pph = Integer.parseInt(request.getParameter(
								"pphThreshold").trim());
					}
					dto.setWorkCenterCode(request.getParameter("workCenter")
							.trim());
					workCenter.setPphThresholdPercentage(pph);
					workCenter.setDescription(request.getParameter("workdesc")
							.trim());
					dto.setDescription(validateDisplayField(request
							.getParameter("workdesc").trim()));
					dto.setPphThresholdPercentage(pph);
					dto.setScaleRequired(request.getParameter("scaleReq"));
					dto.setStatus(request.getParameter("activeStatus"));
					//below line is added for 414799 by saikiran-Verinon
					dto.setScrapCount(request.getParameter("scrapCount"));
					dto.setSelectedFacility(request.getParameter("fac"));
					workCenter.setScaleRequired(request
							.getParameter("scaleReq"));
					workCenter.setActiveFlag(request
							.getParameter("activeStatus"));
					//below line is added for 414799 by saikiran-Verinon
					workCenter.setActiveFlag(request
							.getParameter("scrapCount"));
					
				}
			} else {

				throw new Exception("no valid action");

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

		dto = new ManufacturingOrder();
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		dto.setDivisionFacilities(dao.getFacilities(userContext.getDivision()));

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

	public String validateDisplayField(String displayField) {
		if (displayField == null || displayField.trim().equals("")) {
			return " ";
		}
		return HtmlUtils.htmlEscape(displayField);
	}
}
