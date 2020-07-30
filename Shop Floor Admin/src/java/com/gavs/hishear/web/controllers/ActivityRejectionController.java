// File:         ActivityRejectionController.java
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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.gavs.hishear.m3interface.M3APIWSClient;
import com.gavs.hishear.m3interface.dto.MVXDTAMI;
import com.gavs.hishear.util.DateUtil;
import com.gavs.hishear.util.M3Constants;
import com.gavs.hishear.util.PMS070MIReadUtil;
import com.gavs.hishear.util.PMS100MIReadUtil;
import com.gavs.hishear.util.Util;
import com.gavs.hishear.web.constants.ActivityCode;
import com.gavs.hishear.web.constants.PriceTimeQuantity;
import com.gavs.hishear.web.constants.WorkCenterCapacity;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.ItemDAO;
import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.domain.ApplicationPropertyBean;
import com.gavs.hishear.web.domain.Facility;
import com.gavs.hishear.web.domain.FourthShiftData;
import com.gavs.hishear.web.domain.Item;
import com.gavs.hishear.web.domain.Sequence;
import com.gavs.hishear.web.domain.WorkCenter;
import com.gavs.hishear.web.reports.exceptional.dao.ExceptionalDao;
import com.gavs.hishear.web.reports.exceptional.domain.ExceptionalDto;
import com.ibm.icu.text.DecimalFormat;

/**
 * The Class ActivityRejectionController.			
 */
public class ActivityRejectionController extends SimpleFormController {

	private static final double MILS_PER_HOUR = 3600000.00;

	private static final int SECS_PER_HOUR = 3600;

	/** The dao. */
	private ExceptionalDao dao;

	/** The item dao. */
	private ItemDAO itemDao;

	/** The user context. */
	private UserContext userContext;

	/** The m3 apiws client. */
	private M3APIWSClient m3APIWSClient;

	/** The sequence dao. */
	private SequenceDAO sequenceDAO;

	/** The logger. */
	private static Logger logger = Logger
			.getLogger(ControllerPickSequence.class);

	/** The application property bean. */
	private ApplicationPropertyBean applicationPropertyBean;

	/**
	 * Gets the application property bean.
	 * 
	 * @return the applicationPropertyBean
	 */
	public ApplicationPropertyBean getApplicationPropertyBean() {
		return applicationPropertyBean;
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

	/**
	 * Instantiates a new activity rejection controller.
	 */
	public ActivityRejectionController() {
		setCommandName("command");
		setCommandClass(ExceptionalDto.class);
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
		ExceptionalDto dto = (ExceptionalDto) command;
		String isRejected = request.getParameter("isRejectCheck");
		System.out.println("isRejected--------" + isRejected);
		// set can proceed to true to show no records found message incase of
		// errors

		DecimalFormat decimalFormat = new DecimalFormat("0.##");

		dto.setErrorOccured(false);
		validateMONumberAndSequenceNo(dto, sequenceDAO);

		if (dto.isErrorOccured()) {
			return new ModelAndView(getSuccessView(), "command", command);
		} else {
			dto.setCanProceed(true);
		}
		String moNumber = dto.getMoNumber();
		String employee = dto.getEmpId();

		dto.setLineNumber("000");
		String lineNumber = dto.getLineNumber();
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		dto.setQuery((String) userContext.getQueries().get("SHOP_034"));
		ArrayList report = dao.getActivityRejection(dto);

		dto.setReasons(dao.getReasons((String) userContext.getQueries().get(
				"SHOP_075")));

		dto.setActivitySize(report.size());
		dto.setActivityRejection(report);

		String message = null;
		dto.setUpdate(false);
		if (isRejected != null && isRejected.equalsIgnoreCase("yes")) {
			if (report != null && report.size() != 0) {
				ExceptionalDto eDto;
				String rejectAct = null;

				// for (int i = 0; i < report.size(); i++) {
				// eDto = (ExceptionalDto) report.get(i);
				// rejectAct = (String) request.getParameter("isRej" + i);
				// if (rejectAct != null && eDto.getLogoffDate() == null) {
				// dto.setErrorMessage("One Of The Selected Activities Cannot Be Rejected");
				// return new ModelAndView(getSuccessView(), "command",
				// command);
				// }
				// }

				// Added for M3 Integration - SM
				for (int i = 0; i < report.size(); i++) {
					eDto = (ExceptionalDto) report.get(i);
					rejectAct = (String) request.getParameter("isRej" + i);
					System.out
							.println("rejectAct---------------------------------------->>>>>>>>>"
									+ rejectAct + "\t" + eDto.getJobStatus());
					if (rejectAct != null) {

						int totalCompletedQty = 0;
						if (eDto.getCompletedQty() != null
								&& !"".equals(eDto.getCompletedQty().trim())) {
							try {
								totalCompletedQty = Integer.parseInt(eDto
										.getCompletedQty().trim());
							} catch (Exception e) {
								logger.info(e.getMessage());
							}
						}

						// Set the parameters to call web service
						Sequence seq = new Sequence();
						seq.setMoNumber(moNumber);
						// set the quantity in negative to reverse the quantity-
						// SM
						seq.setQtyCompleted(totalCompletedQty * (-1));
						seq.setSequence(dto.getSequence());
						seq.setCompany(m3APIWSClient
								.getApplicationPropertyBean().getCompany());

						seq.setUOM(M3Constants.UOM);
						seq.setCompletionFlag("0");

						ArrayList sequenceList = PMS070MIReadUtil.getOperation(
								seq, m3APIWSClient);
						Sequence operationDetails = null;
						if (sequenceList != null && sequenceList.size() != 0) {
							operationDetails = (Sequence) sequenceList.get(0);
						}

						int workCenterCapacity = 0;
						if (eDto.getWorkCenterNumber() != null) {
							MVXDTAMI mvxdtami = sequenceDAO
									.getMODetailsFromM3(moNumber);
							if (mvxdtami != null
									&& mvxdtami.getFacility() != null) {
								WorkCenter workCenter = sequenceDAO
										.getDeptByWorkCenter(
												mvxdtami.getFacility(),
												eDto.getWorkCenterNumber());
								workCenterCapacity = workCenter.getCapacity();
							}
						} else {
							System.out.println("Work Center Number Is NULL");
						}

						// Update M3 only if Logoff Date is not NULL
						if (eDto.getLogoffDate() != null) {

							String activityCode = null;
							if ("SETUP".equalsIgnoreCase(eDto.getActivity())) {
								activityCode = ActivityCode.SETUP;
							} else if ("RUN".equalsIgnoreCase(eDto
									.getActivity())) {
								activityCode = ActivityCode.RUN;
							}

							String valueToBeSentToM3 = null;

							// Reverse Weight
							if (operationDetails.getPriceTimeQty() == PriceTimeQuantity.WEIGHT
									&& workCenterCapacity == WorkCenterCapacity.MACHINE) {

								Item item = null;
								String query = (String) userContext
										.getQueries().get("SHOP_017");
								seq.setQuery(query);
								try {
									item = itemDao.checkItemWeight(seq);
									if (item != null) {
										System.out
												.println("In Handler ------- item Weight="
														+ item.getAverageWeight());
										if (item.getAverageWeight() > 0) {
											double weight = item
													.getAverageWeight()
													* totalCompletedQty;
											valueToBeSentToM3 = decimalFormat
													.format(weight);
										} else {
											// Begin - WO#25255 - Report weight
											// processed for manually entered
											// quantity - VP - Infosys -
											// 05-May-2011
											query = (String) userContext
													.getQueries().get(
															"SHOP_117");
											double weight = itemDao
													.getRecentAverageWeight(
															seq.getPartNumber(),
															seq.getSequence(),
															moNumber,
															lineNumber, query);
											weight = weight * totalCompletedQty;
											valueToBeSentToM3 = decimalFormat
													.format(weight);
											// End - WO#25255 - Report weight
											// processed for manually entered
											// quantity - VP - Infosys -
											// 05-May-2011
										}
									} else {

										// Begin - WO#25255 - Report weight
										// processed for manually entered
										// quantity - VP - Infosys - 05-May-2011
										query = (String) userContext
												.getQueries().get("SHOP_117");
										double weight = itemDao
												.getRecentAverageWeight(
														seq.getPartNumber(),
														seq.getSequence(),
														moNumber, lineNumber,
														query);
										weight = weight * totalCompletedQty;
										valueToBeSentToM3 = decimalFormat
												.format(weight);

										/*
										 * dto .setErrorMessage(
										 * "Cannot be Rejected! Average Weight Is Not Available In The System."
										 * ); return new ModelAndView(
										 * getSuccessView(), "command",
										 * command);
										 */
										// End - WO#25255 - Report weight
										// processed for manually entered
										// quantity - VP - Infosys - 05-May-2011
									}
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							// Reverse Time
							else {
								long timeDifference = eDto.getLogoffDate()
										.getTime()
										- eDto.getLogonDate().getTime();

								double calculatedRunTime = 0.0;
								if ("RUN".equals(eDto.getActivity())) {
									// ---------------------------------------
									float breakTime = sequenceDAO.getBreakTime(
											(String) userContext.getQueries()
													.get("SHOP_153"), eDto
													.getActNo(), eDto
													.getLogonDate(), eDto
													.getLogoffDate());
									System.out.println("Break Time ->"
											+ breakTime);

									float retoolTime = sequenceDAO
											.getRetoolTime(
													(String) userContext
															.getQueries().get(
																	"SHOP_154"),
													eDto.getActNo(), eDto
															.getLogonDate(),
													eDto.getLogoffDate());
									System.out.println("Retool Time ->"
											+ retoolTime);
									// ---------------------------------------

									calculatedRunTime = (timeDifference / MILS_PER_HOUR)
											- (breakTime / SECS_PER_HOUR)
											- (retoolTime / SECS_PER_HOUR);
								} else {
									calculatedRunTime = (timeDifference / MILS_PER_HOUR);
								}

								valueToBeSentToM3 = decimalFormat
										.format(calculatedRunTime);
							}

							try {
								// Begin - InsertM3Request call - VP - Infosys -
								// May 11, 2011
								// Adding the user name, error log and M3
								// Request query
								String m3Query = (String) userContext
										.getQueries().get("M3SHIP_116");
								String errorQuery = (String) userContext
										.getQueries().get("SHOP_108");

								sequenceDAO.PMZ70WriteSetRptOperation(moNumber,
										totalCompletedQty, seq.getSequence(),
										dto.getEmployeeNumber(), "0",
										valueToBeSentToM3,
										operationDetails.getPriceTimeQty(),
										workCenterCapacity, activityCode,
										m3Query, errorQuery,
										userContext.getUserName());
								// End - InsertM3Request call - VP - Infosys -
								// May 11, 2011
							} catch (Exception e) {
								dto.setErrorMessage(e.getMessage().replaceAll(
										"java.lang.Exception:", ""));
								return new ModelAndView(getSuccessView(),
										"command", command);
							}
						}

						eDto.setRejectedComments(request
								.getParameter("rejectedComments" + i));
						eDto.setActNo(rejectAct);
						eDto.setActivityLogNumber(request
								.getParameter("activityLogNumber" + i));
						eDto.setReasonCode(Integer.parseInt(request
								.getParameter("reason" + i)));
						eDto.setRejectedBy(userContext.getUserName());
						eDto.setRejectedDate(DateUtil
								.getCurrentDate("yyyy-MM-dd"));
						eDto.setQuery((String) userContext.getQueries().get(
								"SHOPR_065"));
						System.out.println(eDto.getActivityLogNumber()
								+ "<----------------------------->"
								+ eDto.getLogon());

						dao.rejectActivity(eDto, userContext.getUserName());
						dto.setUpdate(true);

					}
				}

				if (message != null) {
					dto.setErrorMessage(Util.trimMessage(message));
					return new ModelAndView(getSuccessView(), "command",
							command);
				}
			}
		} else if (isRejected != null && isRejected.equalsIgnoreCase("reset")) {
			return new ModelAndView(new RedirectView("activityRejection.htm"));
		}
		report = dao.getActivityRejection(dto);
		dto.setActivityRejection(report);
		dto.setActivitySize(report.size());
		return new ModelAndView(getSuccessView(), "command", command);

	}

	/**
	 * Validate mo number and sequence no.
	 * 
	 * @param dto
	 *            the dto
	 * @param dao
	 *            the dao
	 */
	private void validateMONumberAndSequenceNo(ExceptionalDto dto,
			SequenceDAO dao) {
		ArrayList bomList = null;
		Sequence sequence = new Sequence();
		sequence.setCompany(m3APIWSClient.getApplicationPropertyBean()
				.getCompany());
		sequence.setMoNumber(dto.getMoNumber());
		sequence.setSequence(dto.getSequence());

		bomList = PMS100MIReadUtil.getOperation(sequence, m3APIWSClient);

		if (bomList != null && bomList.size() > 0) {
			if (bomList.size() == 1) {
				Sequence seq = (Sequence) bomList.get(0);
				if (seq.getErrorMessage() != null) {
					dto.setErrorMessage(Util.trimMessage(seq.getErrorMessage()));
					dto.setErrorOccured(true);
					return;
				}
			}

			if (!hasAccessToFacility(dao, bomList)) {
				dto.setErrorMessage("User has no access to this Facility");
				dto.setErrorOccured(true);
				return;
			}

			Sequence prevSequence = null;
			if (!isOperationExists(bomList, dto, prevSequence)) {
				dto.setErrorMessage("Invalid Operation Number ");
				dto.setErrorOccured(true);
				return;
			}
			if (prevSequence != null
					&& prevSequence.getPlanningArea().equalsIgnoreCase(
							"OUTSIDE")) {
				dto.setErrorMessage("Error:Sequence cannot be rejected since Previous Sequence is Outside operation ");
				dto.setErrorOccured(true);
				return;
			}

		}

		if ((bomList == null || bomList.size() == 0)) {
			System.out
					.println("~~~~~~~~~~~~~~~~~~~  	bomList.size() == 0	  ~~~~~~~~~~~~~~~~~");
			dto.setErrorMessage("Invalid MO/Sequence Number ");
			dto.setErrorOccured(true);
			return;
		} else if (isMorving(sequence, bomList)) { // check if MORVING Sequence
			System.out
					.println("~~~~~~~~~~~~~~~~~~~  	isMorving(sequence,bomList)	  ~~~~~~~~~~~~~~~~~");
			dto.setErrorMessage("Morving Cannot be Rejected in Shop Floor System");
			dto.setErrorOccured(true);
			return;
		} else if (dto.getPlanningArea().equalsIgnoreCase("REVIEW")) {
			System.out
					.println("~~~~~~~~~~~~~~~~~~~  	REVIEW	  ~~~~~~~~~~~~~~~~~");
			dto.setErrorMessage("Review Cannot be Rejected in Shop Floor System");
			dto.setErrorOccured(true);
			return;
		}

		else if (dto.getPlanningArea().equalsIgnoreCase("LAB")) {
			System.out.println("~~~~~~~~~~~~~~~~~~~  	LAB  ~~~~~~~~~~~~~~~~~");
			dto.setErrorMessage("LAB Cannot be Rejected in Shop Floor System");
			dto.setErrorOccured(true);
			return;
		}

		else if (dto.getPlanningArea().equalsIgnoreCase("OUTSIDE")) {
			System.out
					.println("~~~~~~~~~~~~~~~~~~~  	OUTSIDE	  ~~~~~~~~~~~~~~~~~");
			dto.setErrorMessage("Outside Cannot be Rejected in Shop Floor System");
			dto.setErrorOccured(true);
			return;
		}
	}

	private boolean hasAccessToFacility(SequenceDAO dao, ArrayList bomList) {
		boolean hasAccessToFacility = false;
		Sequence sequnce = (Sequence) bomList.get(0);
		if (sequnce != null && sequnce.getFacility() != null
				&& userContext != null && userContext.getDivision() != null) {
			// Begin WO# 27639 - Moving Static links to dynamic - Pinky -
			// Infosys - 23 June 2011
			ArrayList<Facility> validFacilities = new ArrayList<Facility>();
			try {
				validFacilities = dao.getFacilities(userContext.getDivision());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// End WO# 27639 - Moving Static links to dynamic - Pinky -
			// Infosys - 23 June 2011
			if (validFacilities != null) {
				for (Facility facility : validFacilities) {
					if (facility.getFacilityId() != null
							&& facility.getFacilityId().equalsIgnoreCase(
									sequnce.getFacility())) {
						hasAccessToFacility = true;
					}
				}
			}
		}
		return hasAccessToFacility;
	}

	private boolean isOperationExists(ArrayList bomList, ExceptionalDto dto,
			Sequence prevSequence) {
		boolean isOperationExists = false;
		for (int i = 0; i < bomList.size(); i++) {
			Sequence seq = (Sequence) bomList.get(i);
			if (seq.getSequence().trim().equals(dto.getSequence().trim())) {
				isOperationExists = true;
				dto.setPlanningArea(seq.getPlanningArea());
				break;
			}
			prevSequence = seq;
		}
		return isOperationExists;
	}

	/**
	 * Checks if is morving.
	 * 
	 * @param sequence
	 *            the sequence
	 * @param bomList
	 *            the bom list
	 * @return true, if is morving
	 */
	public boolean isMorving(Sequence sequence, List bomList) {

		Sequence lastSequence = getLastSequence(sequence, bomList);
		return (lastSequence != null
				&& lastSequence.getSequence().equals(sequence.getSequence()) && lastSequence
				.getPlanningArea().equals("MORV"));
	}

	/**
	 * Gets the last sequence.
	 * 
	 * @param sequence
	 *            the sequence
	 * @param bomList
	 *            the bom list
	 * @return the last sequence
	 */
	public Sequence getLastSequence(Sequence sequence, List bomList) {
		Sequence lastSequence = null;
		if (bomList != null) {
			lastSequence = (Sequence) bomList.get(bomList.size() - 1);
		}
		return lastSequence;
	}

	/**
	 * Builds the arguements.
	 * 
	 * @param fourthShiftData
	 *            the fourth shift data
	 * @return the string[]
	 */
	private String[] buildArguements(FourthShiftData fourthShiftData) {
		String[] arguements = new String[11];
		arguements[0] = "M";
		arguements[1] = "X";
		arguements[2] = fourthShiftData.getMoNumber();
		arguements[3] = fourthShiftData.getLineNumber();
		arguements[4] = "R";
		arguements[5] = "Z";
		arguements[6] = fourthShiftData.getSequenceNumber();
		arguements[7] = fourthShiftData.getComponentNumber();
		arguements[8] = "2";
		arguements[9] = fourthShiftData.getQtyType();
		arguements[10] = "" + fourthShiftData.getRequiredQty();
		return arguements;
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
		ExceptionalDto dto = new ExceptionalDto();
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		return dto;
	}

	/**
	 * Gets the dao.
	 * 
	 * @return the dao
	 */
	public ExceptionalDao getDao() {
		return dao;
	}

	/**
	 * Sets the dao.
	 * 
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(ExceptionalDao dao) {
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
	 * Gets the sequence dao.
	 * 
	 * @return the sequenceDAO
	 */
	public SequenceDAO getSequenceDAO() {
		return sequenceDAO;
	}

	/**
	 * Sets the sequence dao.
	 * 
	 * @param sequenceDAO
	 *            the sequenceDAO to set
	 */
	public void setSequenceDAO(SequenceDAO sequenceDAO) {
		this.sequenceDAO = sequenceDAO;
	}

	/**
	 * Gets the item dao.
	 * 
	 * @return the itemDao
	 */
	public ItemDAO getItemDao() {
		return itemDao;
	}

	/**
	 * Sets the item dao.
	 * 
	 * @param itemDao
	 *            the itemDao to set
	 */
	public void setItemDao(ItemDAO itemDao) {
		this.itemDao = itemDao;
	}

}
