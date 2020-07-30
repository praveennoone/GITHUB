package com.gavs.hishear.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.gavs.hishear.web.constants.webConstants;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.domain.ApplicationPropertyBean;
import com.gavs.hishear.web.domain.ManufacturingOrder;
import com.gavs.hishear.web.domain.Sequence;
import com.gavs.hishear.web.domain.SequenceActivity;

public class CorrectionIntModifyMOController extends SimpleFormController {

	private ManufacturingOrder dto;

	private SequenceDAO dao;

	private UserContext userContext;

	private ControllerModifyMOHelper modifyMOHelper;

	private ApplicationPropertyBean applicationPropertyBean;

	private static Logger logger = Logger
			.getLogger(CorrectionIntModifyMOController.class);

	public CorrectionIntModifyMOController() {
		setCommandName("command");
		setSessionForm(true);
	}

	@SuppressWarnings("unchecked")
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		WebUtils.setSessionAttribute(request, getFormSessionAttributeName(),
				command);

		dto = (ManufacturingOrder) command;
		String userAction = request.getParameter("userAction");
		System.out.println("userAction = " + userAction);
		try {
			if (userAction != null) {

				// modifyMOHelper = new ControllerModifyMOHelper();
				if (userAction.equalsIgnoreCase(webConstants.GET_MO_DETAILS)) {
					// method call to load all the data for the MO and facility
					// if available.
					modifyMOHelper.getMODetails(dto);
					// dto.setPageSelected(webConstants.FISRT_PAGE);
				} else if (userAction
						.equalsIgnoreCase(webConstants.DISPLAY_ACTIVITY_DETAILS)) {
					// method to get asset desc and employee name and set to dto
					// set the selected Sequence
					String moDetailsNeeded = request
							.getParameter("moDetailsNeeded");
					if (moDetailsNeeded != null
							&& "Y".equalsIgnoreCase(moDetailsNeeded)) {
						modifyMOHelper.getMODetails(dto);
					}
					String sequence = request.getParameter("currSequence");
					if (sequence != null && !sequence.equals("")) {
						HashMap map = dto.getSequences();
						if (map != null) {
							dto.setSelectedSequeuce((Sequence) map
									.get(sequence));

							modifyMOHelper.getAssetForWC(dto);
							String flag = request.getParameter("newFlag");
							if (dto.getSelectedSequeuce() != null) {
								Sequence seq = dto.getSelectedSequeuce();

								/*
								 * START. Sharepoint item 1441. Reporting of
								 * Setup and Run Times Incorrect
								 */
								try {
									seq.setWorkCenterCapacity(dao
											.getWorkCenterCapacity(seq
													.getWorkCenterCode(), dto
													.getMoNumber()));
								} catch (Exception e) {
									logger.error(e.getMessage());
								}
								/*
								 * END. Sharepoint item 1441. Reporting of Setup
								 * and Run Times Incorrect
								 */

								if (flag != null
										&& flag
												.equalsIgnoreCase(webConstants.YES)) {
									seq.setAddNewJob(true);
									seq.setSelectedSeqActivity(null);
								} else {
									seq.setAddNewJob(false);
								}

							}
						}
					}
				} else if (userAction
						.equalsIgnoreCase(webConstants.EDIT_ACTIVITY_DETAILS)) {
					// method to get the kronos punch in and punch out

					String seqActivityIndex = request
							.getParameter("currSeqActivityIndex");
					if (seqActivityIndex != null) {
						Sequence seq = dto.getSelectedSequeuce();
						SequenceActivity seqAct = seq.getSeqActivityDetails()
								.get(new Integer(seqActivityIndex).intValue());
						seq.setSelectedSeqActivity(seqAct);

						modifyMOHelper.getKronosLogonDetails(dto);
						// method to get the Asset # and desc
						// modifyMOHelper.getAssetForWC(dto);

						// get the pause reasons and keep it.
						if (dto.getPauseReasons() == null) {
							modifyMOHelper.getPauseReasons(dto);
						}
						seq.setFirstSequence(isFirstSequence(
								applicationPropertyBean.getCompany(), dto
										.getMoNumber(), seq.getSequence()));
					}
				} else if (userAction
						.equalsIgnoreCase(webConstants.CONFIRM_ACTIVITY_DETAILS)) {
					// method to confirm activity details this will in turn take
					// care of checking for status
					Sequence seq = dto.getSelectedSequeuce();
					SequenceActivity seqAct = seq.getSelectedSeqActivity();
					String flag = request.getParameter("newFlag");
					if (seqAct == null
							|| (flag != null && flag
									.equalsIgnoreCase(webConstants.YES))) {
						seqAct = new SequenceActivity();
						seqAct.setNewFlag(true);
					}

					if (flag != null && flag.equalsIgnoreCase(webConstants.YES)) {
						seqAct.setNewFlag(true);
					} else {
						seqAct.setNewFlag(false);
					}

					if (seq.isAddNewJob()) {
						seqAct = new SequenceActivity();
						seqAct.setNewFlag(true);
					}
					seqAct.addError(null);
					seq.setSelectedSeqActivity(seqAct);
					try {
						modifyMOHelper.confirmActivityDetails(dto, request);
					} catch (Exception e) {
						seqAct.addError(e.getMessage());
					}
					if (logger.isDebugEnabled()) {
						logger.debug("confirm activity details:");
					}
					dto.setPageSelected(webConstants.SECOND_PAGE_WITH_EDIT);
				} else if (userAction
						.equalsIgnoreCase(webConstants.ADD_MISSING_INFO_DETAILS)) {
					// method to add missing info details
					Sequence seq = dto.getSelectedSequeuce();
					SequenceActivity seqAct = seq.getSelectedSeqActivity();
					// modifyMOHelper.getKronosLogonDetails(dto);
					String flag = request.getParameter("newFlag");
					if (seqAct == null
							|| (flag != null && flag
									.equalsIgnoreCase(webConstants.YES))) {
						seqAct = new SequenceActivity();
						// seqAct.setJobActivityNumber(Integer.parseInt(seq.getJobActivityNumber()));
					}
					if (flag != null && flag.equalsIgnoreCase(webConstants.YES)) {
						seqAct.setNewFlag(true);
					} else {
						seqAct.setNewFlag(false);
					}

					if (seq.isAddNewJob()) {
						seqAct = new SequenceActivity();
						seqAct.setNewFlag(true);
						// seq.getSeqActivityDetails().add(seqAct);
					}
					seq.setSelectedSeqActivity(seqAct);
					seqAct.addError(null);

					// method to get the Asset # and desc
					modifyMOHelper.getAssetForWC(dto);
					if (logger.isDebugEnabled()) {
						logger.debug("getting asset for work center");
					}

					// get the pause reasons and keep it.
					if (dto.getPauseReasons() == null) {
						modifyMOHelper.getPauseReasons(dto);
					}
					seq.setFirstSequence(isFirstSequence(
							applicationPropertyBean.getCompany(), dto
									.getMoNumber(), seq.getSequence()));

					// modifyMOHelper.confirmActivityDetails(dto, request);
					dto
							.setPageSelected(webConstants.SECOND_PAGE_WITH_ADD_MISSING_INFO);
					// modifyMOHelper.getLatestKronosLogonDetails(dto);
				} else if (userAction
						.equalsIgnoreCase("displayActivityChangeInfo")) {

					boolean flag = modifyMOHelper.setValuesFromRequest(request,
							dto);
					modifyMOHelper.getLatestKronosLogonDetails(dto);
				} else if (userAction
						.equalsIgnoreCase("validateCompleteSequence")) {
					Sequence seq = dto.getSelectedSequeuce();
					seq.setErrorMessage(null);
					SequenceActivity selectedActivity = seq
							.getSelectedSeqActivity();

					ArrayList<SequenceActivity> seqActivities = seq
							.getSeqActivityDetails();
					if (seqActivities != null) {
						for (SequenceActivity sequenceActivity : seqActivities) {
							if (selectedActivity.getActivityLogNumber() != sequenceActivity
									.getActivityLogNumber()
									&& sequenceActivity.getLogoffDate() == null) {
								seq
										.setErrorMessage("Cannot Complete The Sequence, Since Other Activities Are Not Completed.");
								break;

							}
						}
					}

				}
			} else {
				throw new Exception("no valid action");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("error occured");
		}
		return new ModelAndView(getSuccessView(), "command", command);
	}

	private boolean isFirstSequence(String company, String moNumber,
			String selectedSequence) {
		List<Sequence> materials = modifyMOHelper.getMaterials(company,
				moNumber);
		if (materials != null) {
			if (selectedSequence.equals(materials.get(0).getSequence())) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		dto = new ManufacturingOrder();
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		dto.setDivisionFacilities(dao.getFacilities(userContext.getDivision()));

		String facility = request.getParameter("faci");
		String moNumber = request.getParameter("moNo");
		String currSequence = request.getParameter("sequenceNumber");

		if (facility != null) {
			dto.setFacility(facility);
		}
		if (moNumber != null) {
			dto.setMoNumber(moNumber);
		}
		if (currSequence != null) {
			dto.setSelectedSequenceNumber(currSequence);
		}

		HashMap map = userContext.getQueries();
		String qry = (String) map.get("SHOP_143");

		// getEmployee data and keep it
		dao.fetchEmployeeDetails(dto, qry);
		return dto;
	}

	public void displaySequenceDetails() {

	}

	public ManufacturingOrder getDto() {
		return dto;
	}

	public void setDto(ManufacturingOrder dto) {
		this.dto = dto;
	}

	public SequenceDAO getDao() {
		return dao;
	}

	public void setDao(SequenceDAO dao) {
		this.dao = dao;
	}

	public UserContext getUserContext() {
		return userContext;
	}

	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}

	public ControllerModifyMOHelper getModifyMOHelper() {
		return modifyMOHelper;
	}

	public void setModifyMOHelper(ControllerModifyMOHelper modifyMOHelper) {
		this.modifyMOHelper = modifyMOHelper;
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
	 * @return the applicationPropertyBean
	 */
	public ApplicationPropertyBean getApplicationPropertyBean() {
		return applicationPropertyBean;
	}

}
