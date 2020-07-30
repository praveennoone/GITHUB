/**
 * 
 */
package com.gavs.hishear.web.reports.management.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.reports.management.dao.ManagementDao;
import com.gavs.hishear.web.reports.management.domain.Correction;
import com.gavs.hishear.web.reports.management.domain.CorrectionLog;
import com.gavs.hishear.web.reports.management.domain.ManagementalDto;
import com.gavs.hishear.util.DateUtil;
import com.gavs.hishear.web.view.ProductionExcelView;

/**
 * @author mohammeda
 * 
 */
public class ControllerCorrectionTrackingDetails extends SimpleFormController {

	private static final String A = "A";
	private static final String NO = "No";
	private static final String YES = "Yes";
	private static final String ONE = "1";
	private static final String TIME = "hh:mm";
	private static final String CORRECTION_DATE_FORMAT = "yyyy/MM/dd hh:mm";
	private static final String EMPTY_STRING = "";
	private ManagementalDto dto;
	private ManagementDao dao;
	private UserContext userContext;
	private Correction correction;
	private Correction multipleCorrection;

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
		System.out.println("on submit called.....................");
		WebUtils.setSessionAttribute(request, getFormSessionAttributeName(),
				command);
		String isExcel = (String) request.getParameter("toexcel");
		dto = (ManagementalDto) command;
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		Date fromDate = DateUtil
				.getDayBegining(dto.getFromDate(), "MM/dd/yyyy");
		Date toDate = DateUtil.getDayEnd(dto.getToDate(), "MM/dd/yyyy");

		Map reports = new HashMap();

		String actionString = request.getParameter("actionString");

		dto.setDtFromDate(DateUtil.getDayBegining(request
				.getParameter("fromDate"), "MM/dd/yyyy"));
		dto.setDtToDate(DateUtil.getDayBegining(request.getParameter("toDate"),
				"MM/dd/yyyy"));
		getDetails();

		if (isExcel != null && isExcel.equalsIgnoreCase("yes")) {
			return new ModelAndView(new ProductionExcelView(reports),
					"command", command);
		} else {
			return new ModelAndView(getSuccessView(), "command", command);
		}
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
		System.out.println("formbacking called.................");
		setSessionForm(true);

		Map reports = new HashMap();
		dto = new ManagementalDto();

		dto.setDtFromDate(DateUtil.getDayBegining(new Date()));
		dto.setDtToDate(DateUtil.getDayEnd(new Date()));
		getDetails();

		/*
		 * if(report!=null && report.size()>0 ) { for(int
		 * i=0;i<report.size();i++) { dto = (ManagementalDto) report.get(i); } }
		 */
		// ArrayList display;
		// reports.put("0", report);
		return dto;
	}

	/**
	 * @return the dao
	 */
	public ManagementDao getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(ManagementDao dao) {
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

	private void getDetails() {
		ArrayList<CorrectionLog> correctionLogDetails = dao
				.getCorrectionTrackingReport(dto, userContext);
		ArrayList<Correction> employeeNameCorrections = new ArrayList<Correction>();
		ArrayList<Correction> logonCorrections = new ArrayList<Correction>();
		ArrayList<Correction> logoffCorrections = new ArrayList<Correction>();
		ArrayList<Correction> activityCorrections = new ArrayList<Correction>();
		ArrayList<Correction> qtyCompletedCorrection = new ArrayList<Correction>();
		ArrayList<Correction> assetNumberCorrection = new ArrayList<Correction>();
		ArrayList<Correction> completedSequenceCorrection = new ArrayList<Correction>();
		ArrayList<Correction> addedSequenceCorrection = new ArrayList<Correction>();
		ArrayList<Correction> multipleSequenceCorrection = new ArrayList<Correction>();
		ArrayList<CorrectionLog> multipleSequenceList = new ArrayList<CorrectionLog>();
		if (correctionLogDetails != null) {
			for (CorrectionLog correctionLog : correctionLogDetails) {
				if (A.equals(StringUtils.defaultString(correctionLog
						.getNewFlag()))) {
					Correction correction = new Correction(correctionLog
							.getCorrectedBy(), correctionLog.getMoNumber(),
							correctionLog.getSequenceNumber(), correctionLog
									.getStampNumber(), correctionLog
									.getOprName(), DateUtil.uiDateFormat(
									correctionLog.getCorrectedOn(),
									"yyyy/MM/dd"), correctionLog
									.getActivityCode(), correctionLog
									.getQtyCompleted(), correctionLog
									.getAssetNumber());
					addedSequenceCorrection.add(correction);
				} else {
					if (correctionLog.getMultipleCorrectionFlag() != null
							&& correctionLog.getMultipleCorrectionFlag()
									.equals("Y")) {
						multipleSequenceList.add(correctionLog);
					} else {

						if (!StringUtils.defaultString(
								correctionLog.getEmployeeNumber()).equals(
								StringUtils.defaultString(correctionLog
										.getOldEmployeeNumber()))) {
							Correction correction = new Correction(
									correctionLog.getCorrectedBy(),
									correctionLog.getMoNumber(), correctionLog
											.getSequenceNumber(), correctionLog
											.getOprName(), correctionLog
											.getActivityCode(), correctionLog
											.getOldEmployeeNumber(),
									correctionLog.getEmployeeNumber());
							employeeNameCorrections.add(correction);
						}

						if ((correctionLog.getLogonDate() == null && correctionLog
								.getOldLogonDate() != null)
								|| (correctionLog.getOldLogonDate() == null && correctionLog
										.getLogonDate() != null)) {
							// Correction correction = new
							// Correction(correctionLog.getCorrectedBy(),
							// correctionLog.getMoNumber(),
							// correctionLog.getSequenceNumber(),
							// correctionLog.getOprName(),correctionLog.getActivityCode(),(correctionLog.getOldLogonDate()
							// !=
							// null)?correctionLog.getOldLogonDate().toString():"",(correctionLog.getLogonDate()
							// !=
							// null)?correctionLog.getLogonDate().toString():"");
							Correction correction = new Correction(
									correctionLog.getCorrectedBy(),
									correctionLog.getMoNumber(), correctionLog
											.getSequenceNumber(), correctionLog
											.getOprName(), correctionLog
											.getActivityCode(), DateUtil
											.dateAsDefaultString(correctionLog
													.getOldLogonDate(),
													EMPTY_STRING,
													CORRECTION_DATE_FORMAT),
									DateUtil.dateAsDefaultString(correctionLog
											.getLogonDate(), EMPTY_STRING,
											CORRECTION_DATE_FORMAT));
							logonCorrections.add(correction);
						} else if ((correctionLog.getLogonDate() != null 
								&& correctionLog.getOldLogonDate() != null) 
								&&(correctionLog.getLogonDate().compareTo(
								correctionLog.getOldLogonDate()) != 0)) {
							// Correction correction = new
							// Correction(correctionLog.getCorrectedBy(),
							// correctionLog.getMoNumber(),
							// correctionLog.getSequenceNumber(),correctionLog.getOprName(),correctionLog.getActivityCode(),(correctionLog.getOldLogonDate()
							// !=
							// null)?correctionLog.getOldLogonDate().toString():"",(correctionLog.getLogonDate()
							// !=
							// null)?correctionLog.getLogonDate().toString():"");
							Correction correction = new Correction(
									correctionLog.getCorrectedBy(),
									correctionLog.getMoNumber(), correctionLog
											.getSequenceNumber(), correctionLog
											.getOprName(), correctionLog
											.getActivityCode(), DateUtil
											.dateAsDefaultString(correctionLog
													.getOldLogonDate(),
													EMPTY_STRING,
													CORRECTION_DATE_FORMAT),
									DateUtil.dateAsDefaultString(correctionLog
											.getLogonDate(), EMPTY_STRING,
											CORRECTION_DATE_FORMAT));
							logonCorrections.add(correction);
						}
						if ((correctionLog.getLogOffDate() == null && correctionLog
								.getOldLogOffDate() != null)
								|| (correctionLog.getOldLogOffDate() == null && correctionLog
										.getLogOffDate() != null)) {
							// Correction correction = new
							// Correction(correctionLog.getCorrectedBy(),
							// correctionLog.getMoNumber(),
							// correctionLog.getSequenceNumber(),
							// correctionLog.getOprName(),correctionLog.getActivityCode(),(correctionLog.getOldLogOffDate()
							// !=
							// null)?correctionLog.getOldLogOffDate().toString():"",(correctionLog.getLogOffDate()
							// !=
							// null)?correctionLog.getLogOffDate().toString():"");
							Correction correction = new Correction(
									correctionLog.getCorrectedBy(),
									correctionLog.getMoNumber(), correctionLog
											.getSequenceNumber(), correctionLog
											.getOprName(), correctionLog
											.getActivityCode(), DateUtil
											.dateAsDefaultString(correctionLog
													.getOldLogOffDate(),
													EMPTY_STRING,
													CORRECTION_DATE_FORMAT),
									DateUtil.dateAsDefaultString(correctionLog
											.getLogOffDate(), EMPTY_STRING,
											CORRECTION_DATE_FORMAT));
							logoffCorrections.add(correction);
						} else if ((correctionLog.getLogOffDate() != null && correctionLog
								.getOldLogOffDate() != null)
								&& (correctionLog.getLogOffDate().compareTo(
										correctionLog.getOldLogOffDate()) != 0)) {
							// Correction correction = new
							// Correction(correctionLog.getCorrectedBy(),
							// correctionLog.getMoNumber(),
							// correctionLog.getSequenceNumber(),correctionLog.getOprName(),correctionLog.getActivityCode(),(correctionLog.getOldLogOffDate()
							// !=
							// null)?correctionLog.getOldLogOffDate().toString():"",(correctionLog.getLogOffDate()
							// !=
							// null)?correctionLog.getLogOffDate().toString():"");
							Correction correction = new Correction(
									correctionLog.getCorrectedBy(),
									correctionLog.getMoNumber(), correctionLog
											.getSequenceNumber(), correctionLog
											.getOprName(), correctionLog
											.getActivityCode(), DateUtil
											.dateAsDefaultString(correctionLog
													.getOldLogOffDate(),
													EMPTY_STRING,
													CORRECTION_DATE_FORMAT),
									DateUtil.dateAsDefaultString(correctionLog
											.getLogOffDate(), EMPTY_STRING,
											CORRECTION_DATE_FORMAT));
							logoffCorrections.add(correction);
						}

						if (!StringUtils.defaultString(
								correctionLog.getQtyCompleted()).equals(
								StringUtils.defaultString(correctionLog
										.getOldQtyCompleted()))) {
							Correction correction = new Correction(
									correctionLog.getCorrectedBy(),
									correctionLog.getMoNumber(), correctionLog
											.getSequenceNumber(), correctionLog
											.getOprName(), correctionLog
											.getActivityCode(), correctionLog
											.getOldQtyCompleted(),
									correctionLog.getQtyCompleted());
							qtyCompletedCorrection.add(correction);
						}
						if (!StringUtils.defaultString(
								correctionLog.getActivityCode()).equals(
								StringUtils.defaultString(correctionLog
										.getOldActivityCode()))) {
							Correction correction = new Correction(
									correctionLog.getCorrectedBy(),
									correctionLog.getMoNumber(), correctionLog
											.getSequenceNumber(), correctionLog
											.getOprName(), correctionLog
											.getActivityCode(), correctionLog
											.getOldActivityCode(),
									correctionLog.getActivityCode());
							activityCorrections.add(correction);
						}
						if (!StringUtils.defaultString(
								correctionLog.getAssetNumber()).equals(
								StringUtils.defaultString(correctionLog
										.getOldAssetNumber()))) {
							Correction correction = new Correction(
									correctionLog.getCorrectedBy(),
									correctionLog.getMoNumber(), correctionLog
											.getSequenceNumber(), correctionLog
											.getOprName(), correctionLog
											.getActivityCode(), correctionLog
											.getOldAssetNumber(), correctionLog
											.getAssetNumber());
							assetNumberCorrection.add(correction);
						}
						if (StringUtils.defaultString(
								correctionLog.getCompleteStatusFlag()).equals(
								ONE)) {
							Correction correction = new Correction(
									correctionLog.getCorrectedBy(),
									correctionLog.getMoNumber(), correctionLog
											.getSequenceNumber(), correctionLog
											.getOprName(), DateUtil
											.uiDateFormat(correctionLog
													.getCorrectedOn(),
													"yyyy/MM/dd"),
									correctionLog.getActivityCode(),
									correctionLog.getQtyCompleted(), DateUtil
											.dateAsDefaultString(correctionLog
													.getLogonDate(),
													EMPTY_STRING, TIME),
									DateUtil.dateAsDefaultString(correctionLog
											.getLogOffDate(), EMPTY_STRING,
											TIME), correctionLog
											.getCompleteStatusFlag());
							completedSequenceCorrection.add(correction);

						}
					}

				}

			}
			dto
					.setMultipleCorrectionTrackingReport(buildMultipleCorrectionReport(multipleSequenceList));
		}
		/*
		 * if(report!=null && report.size()>0 ) { for(int
		 * i=0;i<report.size();i++) { ManagementalDto objmanagementalDto =
		 * (ManagementalDto) report.get(i); } }
		 */
		dto.setEmployeeCorrectionTrackingReport(employeeNameCorrections);
		dto.setLogonCorrectionTrackingReport(logonCorrections);
		dto.setLogOffCorrectionTrackingReport(logoffCorrections);
		dto.setActivityCorrectionTrackingReport(activityCorrections);
		dto.setQtyCompletedCorrectionTrackingReport(qtyCompletedCorrection);
		dto.setAssetNumberCorrectionTrackingReport(assetNumberCorrection);
		dto
				.setCompletedSequenceCorrectionTrackingReport(completedSequenceCorrection);
		dto.setAddedSequenceCorrectionTrackingReport(addedSequenceCorrection);
	}

	private ArrayList<CorrectionsByUser> buildMultipleCorrectionReport(
			ArrayList<CorrectionLog> multipleSequenceList) {
		ArrayList<CorrectionsByUser> multipleCorrection = new ArrayList<CorrectionsByUser>();
		HashMap<String, CorrectionsByUser> mapMultipleCorrections = new HashMap<String, CorrectionsByUser>();
		for (CorrectionLog correctionLog : multipleSequenceList) {
			CorrectionsByUser thisUsersCorrection = mapMultipleCorrections
					.get(correctionLog.getCorrectedBy());
			CorrectionsByUser nextUsersCorrection = null;
			if (thisUsersCorrection == null) {
				nextUsersCorrection = new CorrectionsByUser(correctionLog
						.getCorrectedBy(), new ArrayList<Correction>());
			}
			// Correction beforeCorrection = new
			// Correction(correctionLog.getMoNumber(),
			// correctionLog.getSequenceNumber(),correctionLog.getOldActivityCode(),
			// correctionLog.getOprName(),correctionLog.getCorrectedOn().toString(),
			// (correctionLog.getOldLogonDate() !=
			// null)?correctionLog.getOldLogonDate().toString():"",(correctionLog.getOldLogOffDate()
			// !=
			// null)?correctionLog.getOldLogOffDate().toString():"",correctionLog.getOldQtyCompleted(),correctionLog.getAssetNumber(),correctionLog.getCompleteStatusFlag(),"");
			String oldCompleteFlagStatus;
			if (correctionLog.getOldCompleteStatusFlag() != null
					&& ONE.equals(correctionLog.getOldCompleteStatusFlag())) {
				oldCompleteFlagStatus = YES;
			} else {
				oldCompleteFlagStatus = NO;
			}
			Correction beforeCorrection = new Correction(correctionLog
					.getMoNumber(), correctionLog.getSequenceNumber(),
					correctionLog.getOldActivityCode(), correctionLog
							.getOprName(), DateUtil.uiDateFormat(correctionLog
							.getCorrectedOn(), "yyyy/MM/dd"), DateUtil
							.dateAsDefaultString(correctionLog
									.getOldLogonDate(), EMPTY_STRING, TIME),
					DateUtil.dateAsDefaultString(correctionLog
							.getOldLogOffDate(), EMPTY_STRING, TIME),
					correctionLog.getOldQtyCompleted(), correctionLog
							.getAssetNumber(), oldCompleteFlagStatus,
					EMPTY_STRING);

			// Correction afterCorrection = new
			// Correction(correctionLog.getMoNumber(),
			// correctionLog.getSequenceNumber(),correctionLog.getActivityCode(),
			// correctionLog.getOprName(),correctionLog.getCorrectedOn().toString(),
			// (correctionLog.getLogonDate() !=
			// null)?correctionLog.getLogonDate().toString():"",(correctionLog.getLogOffDate()
			// !=
			// null)?correctionLog.getLogOffDate().toString():"",correctionLog.getQtyCompleted(),correctionLog.getAssetNumber(),correctionLog.getCompleteStatusFlag(),"");
			Correction afterCorrection = new Correction(
					correctionLog.getMoNumber(),
					correctionLog.getSequenceNumber(),
					correctionLog.getActivityCode(),
					correctionLog.getOprName(),
					DateUtil.uiDateFormat(correctionLog.getCorrectedOn(),
							"yyyy/MM/dd"),
					DateUtil.dateAsDefaultString(correctionLog.getLogonDate(),
							EMPTY_STRING, TIME),
					DateUtil.dateAsDefaultString(correctionLog.getLogOffDate(),
							EMPTY_STRING, TIME),
					correctionLog.getQtyCompleted(),
					correctionLog.getAssetNumber(),
					getCompleteStatusFlag(correctionLog.getCompleteStatusFlag()),
					EMPTY_STRING);

			if (thisUsersCorrection != null) {
				thisUsersCorrection.getCorrections().add(beforeCorrection);
				thisUsersCorrection.getCorrections().add(afterCorrection);
				mapMultipleCorrections.put(correctionLog.getCorrectedBy(),
						thisUsersCorrection);
			} else {
				nextUsersCorrection.getCorrections().add(beforeCorrection);
				nextUsersCorrection.getCorrections().add(afterCorrection);
				mapMultipleCorrections.put(correctionLog.getCorrectedBy(),
						nextUsersCorrection);
			}
		}
		ArrayList<String> keys = new ArrayList<String>();
		Iterator<String> iter1 = mapMultipleCorrections.keySet().iterator();
		while (iter1.hasNext()) {
			String key1 = (String) iter1.next();
			// System.out.println(key1);
			keys.add(key1);
		}
		for (String key : keys) {
			multipleCorrection.add(mapMultipleCorrections.get(key));
		}
		return multipleCorrection;
	}

	private String getCompleteStatusFlag(String completeStatusFlag) {
		if (ONE.equals(completeStatusFlag)) {
			return YES;
		}
		return NO;
	}
}
