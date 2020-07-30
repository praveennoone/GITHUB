/**
 * AssetConfigController.java
 */
package com.gavs.hishear.web.controllers;

import java.util.ArrayList;

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
import com.gavs.hishear.web.domain.AssetConfig;
import com.gavs.hishear.web.domain.ManufacturingOrder;
import com.gavs.hishear.web.domain.WorkCenterConfig;

public class AssetConfigController extends SimpleFormController {

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
	public AssetConfigController() {
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
		AssetConfig asset = new AssetConfig();
		try {
			if (userAction != null) {

				dto.setEditFlag(false);
				if (userAction.equalsIgnoreCase("getAssetConfigDetails")) {
					// method call to load all the data for the MO and facility
					// if available.

					String query = (String) userContext.getQueries().get(
							"SHOP_158");

					asset.setWorkCenterCode(request.getParameter(
							"workCenterCode").trim());
					asset.setFacility(request.getParameter("facility"));

					dto.setError("");

					try {

						dto.setAssetConfig(workCenterDao.getAssetConfigDetails(
								asset, query));

					} catch (Exception ex) {

						dto.setError("Error while getting the Data");
						ex.printStackTrace();

					}

					dto.setWorkCenterCode(request
							.getParameter("workCenterCode").trim());
					dto.setFacility(request.getParameter("facility"));

				} else if (userAction
						.equalsIgnoreCase("updateAssetConfigDetails")) {

					String statusRequired = request
							.getParameter("statusFlag_Chk");

					if (statusRequired == null) {

						statusRequired = "I";

					}

					dto.setError("");
					asset.setFacility(dto.getSelectedFacility());
					asset.setAssetNumber(request.getParameter("asset").trim());
					asset.setWorkCenterCode(request.getParameter("workCenter")
							.trim());
					asset.setUpdatedDescription(request.getParameter("descrip")
							.trim());
					asset.setUpdatedStatus(statusRequired);
					String query = (String) userContext.getQueries().get(
							"SHOPR_099");
					String insertLogQuery = (String) userContext.getQueries()
							.get("SHOPR_101");
					try {

						workCenterDao.insertAssetLogDetails(asset, userContext,
								insertLogQuery);
						workCenterDao.updateAssetConfigDetails(asset, query);

					} catch (Exception ex) {

						dto.setError("Error while Updating the Data");
						ex.printStackTrace();

					}

				} else if (userAction.equalsIgnoreCase("addNewAsset")) {
					WorkCenterConfig workCenter = new WorkCenterConfig();
					workCenter.setWorkCenterCode(request.getParameter(
							"workCenterCode").trim());
					workCenter.setFacility(request.getParameter("facility"));
					String workCenterQuery = (String) userContext.getQueries()
							.get("SHOP_116");
					dto.setError("");

					if (workCenterDao.getWorkCenterConfigDetails(workCenter,
							workCenterQuery).size() > 0) {
						asset.setWorkCenterCode(request.getParameter(
								"workCenterCode").trim());
						asset.setFacility(request.getParameter("facility"));

					} else {
						dto
								.setError("Entered Work Center Does Not Belong to the Selected Facility");

					}
				} else if (userAction.equalsIgnoreCase("insertNewAssetDetails")) {

					String query = (String) userContext.getQueries().get(
							"SHOP_158");

					asset.setWorkCenterCode(request.getParameter(
							"workCenterCode").trim());
					asset.setFacility(request.getParameter("facility"));

					ArrayList<AssetConfig> assetNumbers = workCenterDao
							.getAssetConfigDetails(asset, query);

					if (assetNumbers != null && assetNumbers.size() > 0) {
						for (AssetConfig check : assetNumbers) {

							if (check.getAssetNumber().equals(
									request.getParameter("assetNumber").trim())) {
								dto.setError("Asset Number Already Exists");
								break;
							}
						}

					}
					if ("".equalsIgnoreCase(dto.getError())) {

						asset.setAssetNumber((request
								.getParameter("assetNumber").trim()));
						dto.setAssetConfig(workCenterDao.getAssetConfigDetails(
								asset, query));

						String insertQuery = (String) userContext.getQueries()
								.get("SHOPR_100");

						dto.setError("");

						String statusRequired = request
								.getParameter("statusFlag_Chk");

						if (statusRequired == null) {

							statusRequired = "I";

						}

						asset.setFacility(request.getParameter("facility"));
						asset.setWorkCenterCode(request.getParameter(
								"workCenterCode").trim());
						asset.setUpdatedDescription(request.getParameter(
								"descrip").toUpperCase().trim());
						asset.setUpdatedAssetNumber(request.getParameter(
								"assetNumber").trim());
						asset.setUpdatedStatus(statusRequired);

						try {

							workCenterDao.insertNewAssetDetails(asset,
									insertQuery);
							String getQuery = (String) userContext.getQueries()
									.get("SHOP_158");
							asset.setAssetNumber("");
							dto.setAssetConfig(workCenterDao
									.getAssetConfigDetails(asset, getQuery));

						} catch (Exception ex) {

							ex.printStackTrace();
							dto.setError("Error Inserting Data");

						}

					}
				} else if (userAction.equalsIgnoreCase("editAsset")) {
					dto.setEditFlag(true);
					dto.setSelectedFacility(request.getParameter("fac"));
					dto.setWorkCenterCode(request.getParameter("workCenter")
							.trim());
					dto.setAssetNumber(request.getParameter("asset").trim());
					dto.setDescription(validateDisplayField(request
							.getParameter("description").trim()));
					dto.setStatus(request.getParameter("status"));
					asset.setDescription(request.getParameter("description")
							.trim());
					asset.setAssetNumber(request.getParameter("asset").trim());
					asset.setStatus(request.getParameter("status"));
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
