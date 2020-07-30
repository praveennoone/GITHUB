// File:         ControllerQualityStandardTime.java
// Created:      Apr 21, 2011
// Author:       Pinky S
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
import org.springframework.web.util.WebUtils;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.domain.StandardTime;
import com.gavs.hishear.web.domain.TestMethods;
import com.gavs.hishear.web.reports.factory.dao.FactoryDao;

/**
 * The Class ControllerTimeAdjustment.
 * 
 */
public class ControllerQualityStandardTime extends SimpleFormController {

	/** The user context. */
	private UserContext userContext;

	/** The dao. */
	private FactoryDao dao;

	public ControllerQualityStandardTime() {
		setCommandName("command");
		setSessionForm(true);
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		StandardTime dto = new StandardTime();
		setSessionForm(true);
		String query = (String) userContext.getQueries().get("SHOPR_090");
		ArrayList<String> testTypes = new ArrayList<String>();
		testTypes.add("");
		testTypes.add("Inspection");
		testTypes.add("Mechanical");
		testTypes.add("Metallurgical");
		// dto.setTestTypes(dao.getDistinctTestType(dto, query));
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		dto.setTestTypes(testTypes);
		System.out.println("test type" + dto.getTestTypes().size());
		return dto;
	}

	public void getTestMethodDetails(Object command) {
		StandardTime dto = (StandardTime) command;
		String query = (String) userContext.getQueries().get("SHOPR_091");
		try {
			dto.setTestMethods(dao.getTestTypeDetails(dto, query));
			System.out.println("dto.getTestMethods size from controller"
					+ dto.getTestMethods().size());
		} catch (Exception e) {
			dto.setMessage("Exception");
			e.printStackTrace();
		}
	}

	// private void getDetails(ActionEvent actionEvent) {
	// dto = (FactoryDto) command;
	// }
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

		WebUtils.setSessionAttribute(request, getFormSessionAttributeName(),
				command);

		StandardTime dto = (StandardTime) command;
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());

		String userAction = request.getParameter("userAction");
		System.out.println("userAction = " + userAction);
		dto.setSuccess(true);
		dto.setMessage("");
		try {
			if (userAction != null) {
				if (("getDetails").equalsIgnoreCase(userAction)) {
					dto
							.setSelectedTestType(request
									.getParameter("sltTestType"));

					System.out.println("selected test type"
							+ dto.getSelectedTestType());

					String query = (String) userContext.getQueries().get(
							"SHOPR_091");
					try {
						dto.setTestMethods(dao.getTestTypeDetails(dto, query));
						System.out
								.println("dto.getTestMethods size from controller"
										+ dto.getTestMethods().size());
					} catch (Exception e) {
						dto.setMessage("Exception");
						e.printStackTrace();
					}

					if (dto.getTestMethods().size() == 0) {
						dto.setSuccess(false);
						dto.setMessage("No Records Found");
					}
				} else if (userAction.equalsIgnoreCase("updateTestMethod")) {
					int standardTime = Integer.parseInt(request.getParameter(
							"txtStdTime").trim());
					if (standardTime >= 1 && standardTime <= 1000) {
						dto.setSelectedStandardTime(standardTime);
						String testMethodName = request
								.getParameter("testMethodName");
						dto.setSelectedTestMethodName(testMethodName);
						int oldStandardTime = Integer.parseInt(request
								.getParameter("stdTimeOld").trim());
						dto.setOldStandardTime(oldStandardTime);

						String query = (String) userContext.getQueries().get(
								"SHOPR_092");

						String queryLog = (String) userContext.getQueries()
								.get("SHOPR_094");
						dto.setMessage("");
						try {
							dao.updateStandardTime(dto, query);
							dao.updateStandardTimeLog(dto, queryLog,
									userContext);
							getTestMethodDetails(command);
							dto.setSuccess(true);
							dto.setMessage("Updated Successfully");
						} catch (Exception e) {
							dto.setMessage("Exception");
							e.printStackTrace();
							dto.setSuccess(false);
							dto.setMessage("Update Failed");
						}
					} else {
						dto.setSuccess(false);
						dto
								.setMessage("Standard time should be between 1 and 1000");
					}
				} else if (userAction.equalsIgnoreCase("deleteTestMethod")) {
					String testMethodName = request
							.getParameter("testMethodName");
					int oldStandardTime = Integer.parseInt(request
							.getParameter("stdTimeOld").trim());
					dto.setOldStandardTime(oldStandardTime);

					dto.setSelectedTestMethodName(testMethodName);

					String query = (String) userContext.getQueries().get(
							"SHOPR_093");

					String queryLog = (String) userContext.getQueries().get(
							"SHOPR_094");
					dto.setMessage("");
					try {
						dao.deleteStandardTime(dto, query);
						dao.updateStandardTimeLog(dto, queryLog, userContext);
						getTestMethodDetails(command);
						dto.setMessage("Deleted Successfully");
						dto.setSuccess(true);
					} catch (Exception e) {
						dto.setMessage("Exception");
						e.printStackTrace();
						dto.setMessage("Delete Failed");
						dto.setSuccess(false);
					}
				} else if (userAction.equalsIgnoreCase("addStandardTime")) {

					String query = (String) userContext.getQueries().get(
							"SHOPR_095");
					int standardTime = Integer.parseInt(request.getParameter(
							"txtStdTime").trim());
					if (standardTime >= 1 && standardTime <= 1000) {
						String testMethodName = request
								.getParameter("testMethodName");
						dto.setSelectedStandardTime(standardTime);
						dto.setSelectedTestMethodName(testMethodName);
						dto.setSelectedTestType(request
								.getParameter("sltTestType"));
						String queryForTestMtds = (String) userContext
								.getQueries().get("SHOPR_091");
						try {
							dto.setTestMethods(dao.getTestTypeDetails(dto,
									queryForTestMtds));
							System.out
									.println("dto.getTestMethods size from controller"
											+ dto.getTestMethods().size());
						} catch (Exception e) {
							dto.setMessage("Exception");
							e.printStackTrace();
						}

						boolean flagExists = false;
						ArrayList<TestMethods> availableTestMethods = dto
								.getTestMethods();
						dto.setMessage("");
						if (availableTestMethods != null) {
							for (TestMethods testMethod : availableTestMethods) {
								if (dto.getSelectedTestMethodName()
										.equalsIgnoreCase(
												testMethod.getTestMethodName())) {
									dto
											.setMessage("Test Method Name already exists");
									dto.setSuccess(false);

									flagExists = true;
									break;
								}
							}
						}
						if (!flagExists) {
							try {
								dao.addStandardTime(dto, query);
								getTestMethodDetails(command);
								dto.setMessage("Added Successfully");
								dto.setSuccess(true);
							} catch (Exception e) {
								dto.setMessage("Exception");
								e.printStackTrace();
								dto.setMessage("Adding the Test Method Failed");
								dto.setSuccess(false);
							}
						}
					} else {
						dto.setSuccess(false);
						dto
								.setMessage("Standard time should be between 1 and 1000");
					}

				} else if (userAction.equalsIgnoreCase("addOrUpdateTestMethod")) {
					String testMethodName = request
							.getParameter("testMethodName");
					dto.setSelectedTestMethodName(testMethodName);
					int oldStandardTime = Integer.parseInt(request
							.getParameter("stdTimeOld").trim());
					dto.setOldStandardTime(oldStandardTime);
					dto.setMessage("");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error occured");
			throw new Exception("error occured");
		}
		return new ModelAndView(getSuccessView(), "command", command);
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
	 * Gets the dao.
	 * 
	 * @return the dao
	 */
	public FactoryDao getDao() {
		return dao;
	}

	/**
	 * Sets the dao.
	 * 
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(FactoryDao dao) {
		this.dao = dao;
	}
}
