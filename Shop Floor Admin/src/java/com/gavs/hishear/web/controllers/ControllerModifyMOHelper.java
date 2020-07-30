// File:         ControllerModifyMOHelper.java
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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gavs.hishear.m3interface.M3APIWSClient;
import com.gavs.hishear.util.DateUtil;
import com.gavs.hishear.util.PMS100MIReadUtil;
import com.gavs.hishear.util.SequenceComparator;
import com.gavs.hishear.web.constants.PriceTimeQuantity;
import com.gavs.hishear.web.constants.WorkCenterCapacity;
import com.gavs.hishear.web.constants.webConstants;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.ItemDAO;
import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.domain.ApplicationPropertyBean;
import com.gavs.hishear.web.domain.Asset;
import com.gavs.hishear.web.domain.Item;
import com.gavs.hishear.web.domain.ManufacturingOrder;
import com.gavs.hishear.web.domain.MasterData;
import com.gavs.hishear.web.domain.Sequence;
import com.gavs.hishear.web.domain.SequenceActivity;


/**
 * The Class ControllerModifyMOHelper.
 */
public class ControllerModifyMOHelper {

	private static final int SECS_PER_HOUR = 3600;

	private static final double MILS_PER_HOUR = 3600000.00;

	private static final String EMPTY_STRING = "";

	/** The dao. */
	private SequenceDAO dao;

	/** The m3 apiws client. */
	private M3APIWSClient m3APIWSClient;

	/** The application property bean. */
	private ApplicationPropertyBean applicationPropertyBean;

	/** The user context. */
	private UserContext userContext;

	/** The item dao. */
	private ItemDAO itemDao;

	/** The logger. */
	private static Logger logger = Logger
			.getLogger(ControllerModifyMOHelper.class);

	/**
	 * Gets the mO details.
	 * 
	 * @param dto
	 *            the dto
	 * @return the mO details
	 */
	@SuppressWarnings("unchecked")
	public void getMODetails(ManufacturingOrder dto) {

		try {
			// get the query from the usercontext using the queryid
			HashMap qryMap = userContext.getQueries();
			String qry = (String) qryMap
					.get(webConstants.SHOP_ADMIN_GET_MO_DETAILS);

			qry = (String) userContext.getQueries().get("SHOP_123");
			// getting MO details for the entered facility and MO number
			dao.fetchMOSeqActDetails(dto, qry);
			// fetch the part desc and operation desc from m3

			mergeSequenceData(dto);

		} catch (Exception ex) {
			// todo put in error and show to user
			ex.printStackTrace();
		}

	}

	/**
	 * Merge sequence data.
	 * 
	 * @param dto
	 *            the dto
	 * @throws Exception
	 *             the exception
	 */
	private void mergeSequenceData(ManufacturingOrder dto) throws Exception {
		HashMap map = dto.getSequences();
		if (!map.isEmpty()) {
			Sequence seq = new Sequence();
			seq.setCompany(applicationPropertyBean.getCompany());
			seq.setMoNumber(dto.getMoNumber());
			ArrayList<Sequence> sequences = getBOMList(seq);
			String desc = null;
			if (sequences != null) {
				dto.setSequencesInM3(sequences);
				Sequence previousSequence = null;
				for (Sequence sequence : sequences) {
					boolean seqStatus = false;
					seq = (Sequence) map.get(sequence.getSequence());

					// added this so that this record is not to be shown in the
					// interface for the supervisor to modify

					/*
					 * // added this so that this record is not to be shown in
					 * the interface for the supervisor to modify if
					 * (sequence.getPlanningArea() != null &&
					 * sequence.getPlanningArea
					 * ().contains(webConstants.PLANNING_AREA_REVIEW)) { if (seq
					 * != null) { System.out.println("need to remove seq" +
					 * seq.getSequence()); map.remove(sequence.getSequence());
					 * continue; } }
					 */
					if (seq == null) {
						// case where its not started and with status less than
						// 40 will be not started operations in BOLT ON
						if (previousSequence != null
								&& previousSequence.getRunStatus() != null
								&& previousSequence.getRunStatus().equals("C")
								&& sequence.getStatus() != null
								&& Integer.parseInt(sequence.getStatus()) <= webConstants.STATUS_NOT_STARTED_M3
								&& sequence.getPlanningArea() != null
								&& !sequence.getPlanningArea().contains(
										webConstants.PLANNING_AREA_REVIEW)) {

							seq = new Sequence();
							seq.setSequence(sequence.getSequence());
							seq.setWorkCenterCode(sequence.getWorkCenterCode());
							seq.setRunStatus(webConstants.STATUS_NOT_STARTED);
							seq
									.setSequenceStatus(webConstants.STATUS_NOT_STARTED);
							seq.setPartNumber(sequence.getPartNumber());
							seq.setPlanningArea(sequence.getPlanningArea());
							seq.setStatus(sequence.getStatus());
							seq.setPriceTimeQty(sequence.getPriceTimeQty());
							seq.setRunTime(sequence.getRunTime());
							seqStatus = true;
							previousSequence = seq;
						} else {
							// this case is where bolt on is not handling
							continue;
						}
					} else {
						previousSequence = seq;
					}

					setStatusForRunAndSetupActivity(seq);
					desc = sequence.getSequenceDescription();
					seq.setSequenceDescription(desc);
					desc = sequence.getPartDesc();
					seq.setPartDesc(desc);
					seq.setOrderQty(sequence.getOrderQty());
					seq.setPlanningArea(sequence.getPlanningArea());
					seq.setRunTime(sequence.getRunTime());
					seq.setPriceTimeQty(sequence.getPriceTimeQty());
					map.put(sequence.getSequence(), seq);
					// if
					// (!sequence.getPlanningArea().contains(webConstants.PLANNING_AREA_OUTSIDE)
					// && seqStatus) {
					// break;
					// }
				}
			}
		}
		// convert the hashmap to arraylist for display purposes
		if (map != null) {
			ArrayList<Sequence> modifyMOs = new ArrayList<Sequence>();
			modifyMOs.addAll(dto.getSequences().values());
			Sequence[] seqs = new Sequence[dto.getSequences().size()];
			modifyMOs.toArray(seqs);
			SequenceComparator sequenceComparator = new SequenceComparator();
			Arrays.sort(seqs, sequenceComparator);
			modifyMOs = new ArrayList();
			modifyMOs.addAll(Arrays.asList(seqs));
			dto.setModifyMOs(modifyMOs);
		}
	}

	/**
	 * Sets the status for run and setup activity.
	 * 
	 * @param sequence
	 *            the new status for run and setup activity
	 */
	private void setStatusForRunAndSetupActivity(Sequence sequence) {
		ArrayList<SequenceActivity> seqActivities = sequence
				.getSeqActivityDetails();
		sequence.setSetupStatus("-");
		sequence.setRunStatus("Not Started");
		if (seqActivities != null) {
			// for Setup Status
			for (SequenceActivity sequenceActivity : seqActivities) {
				if (sequenceActivity.getActivity().equals("S")) {
					if (sequenceActivity.getActivityStatus().equals(
							webConstants.STATUS_INPROGRESS)) {
						sequence.setSetupStatus(webConstants.STATUS_INPROGRESS);
						break;
					} else {
						sequence.setSetupStatus(webConstants.STATUS_COMPLETE);
					}
				}
			}
			// for Run Status
			for (SequenceActivity sequenceActivity : seqActivities) {
				if (sequenceActivity.getActivity().equals("R")) {
					if (sequenceActivity.getActivityStatus().equals(
							webConstants.STATUS_INPROGRESS)) {
						sequence.setRunStatus(webConstants.STATUS_INPROGRESS);
						break;
					} else {
						sequence.setRunStatus(webConstants.STATUS_COMPLETE);
					}
				}
			}
		}
	}

	/**
	 * Sets the activity details.
	 * 
	 * @param dto
	 *            the new activity details
	 */
	public void setActivityDetails(ManufacturingOrder dto) {
		// get the employeeName and Asset Desc
		// in dto we have maps which has the asset number and emp numbers,
		// we can get the corresponding desc from emp master and emp detail and
		// set it in maps

		// try {
		// get the query from the usercontext using the queryid
		// HashMap qryMap = userContext.getQueries();
		// String qry =
		// (String)qryMap.get(webConstants.SHOP_ADMIN_GET_ASSET_DETAILS);
		// getting asset details for the asset map which is available in dto
		// System.out.println(" qry:" + qry);
		// qry =
		// "SELECT AssetNumber, AssetName from SFS_AssetMaster where Facility = ? and AssetNumber = ? and Status = 'A' and WorkCenter = ?";
		// dao.fetchAssetDetails(dto, qry);

		// qry =
		// (String)qryMap.get(webConstants.SHOP_ADMIN_GET_EMPLOYEE_DETAILS);
		// getting employee details for the employee map which is available
		// in dto
		// dao.fetchEmployeeDetails(dto, qry);

		// } catch (Exception ex) {
		// to do set the error for user
		// }
	}

	/**
	 * Gets the kronos logon details.
	 * 
	 * @param dto
	 *            the dto
	 * @return the kronos logon details
	 */
	public void getKronosLogonDetails(ManufacturingOrder dto) {
		Sequence seq = dto.getSelectedSequeuce();
		SequenceActivity seqAct = seq.getSelectedSeqActivity();
		String query = null;
		Date[] kronosPunchInPunchOut = null;
		if (seqAct.getLogoffDate() != null) {
			query = "select top 1 PunchOutRounded, PunchInRounded"
					+ " from SFS_EmployeeLogonDetails ELD"
					+ " where ELD.EmployeeNumber = ?"
					+ " and convert(varchar, ELD.PunchOutRounded, 101) = ISNull(?, convert(varchar, ELD.PunchOutRounded, 101))"
					+ " order by PunchOutRounded desc";
			kronosPunchInPunchOut = dao.getEmployeeKronosLogonByLogoutDate(dto,
					query);
		} else {
			query = "select top 1 PunchOutRounded, PunchInRounded"
					+ " from SFS_EmployeeLogonDetails ELD"
					+ " where ELD.EmployeeNumber = ? "
					+ " and convert(varchar, ELD.PunchInRounded, 101) = ISNull(?, convert(varchar, ELD.PunchInRounded, 101))"
					+ " order by PunchInRounded desc";
			kronosPunchInPunchOut = dao.getEmployeeKronosLogonByLogoutDate(dto,
					query);
		}

		if (kronosPunchInPunchOut != null) {
			seqAct.setKronosPunchIn(kronosPunchInPunchOut[0]);
			seqAct.setKronosPunchOut(kronosPunchInPunchOut[1]);
		}
	}

	/**
	 * Gets the latest kronos logon details.
	 * 
	 * @param dto
	 *            the dto
	 * @return the latest kronos logon details
	 */
	public void getLatestKronosLogonDetails(ManufacturingOrder dto) {
		Sequence seq = dto.getSelectedSequeuce();
		String query = null;
		Date[] kronosPunchInPunchOut = null;
		query = (String) userContext.getQueries().get("SHOPR_107");
		kronosPunchInPunchOut = dao.getEmployeeKronosLogonLatest(dto, query);

		if (kronosPunchInPunchOut != null) {
			seq.setKronosPunchIn(kronosPunchInPunchOut[0]);
			seq.setKronosPunchOut(kronosPunchInPunchOut[1]);
		}
	}

	/**
	 * Gets the asset for wc.
	 * 
	 * @param dto
	 *            the dto
	 * @return the asset for wc
	 */
	public void getAssetForWC(ManufacturingOrder dto) {
		// get assets for the workcenter the selected activity belongs to
		try {
			// get the query from the usercontext using the queryid
			HashMap qryMap = userContext.getQueries();
			String qry = (String) qryMap
					.get(webConstants.SHOP_ADMIN_GET_ASSET_WC_DETAILS);
			// getting assets for the workcenter and facility
			Sequence seq = dto.getSelectedSequeuce();
			if (seq.getAssetsListForWC() == null) {
				ArrayList<Asset> list = (ArrayList<Asset>) dao.getAssets(qry,
						seq.getWorkCenterCode(), dto.getFacility());
				seq.setAssetsListForWC(list);

				// prepare a hashMap assetMap from this arraylist and set to seq
				// as assetMap
				HashMap<String, String> assetMap = new HashMap<String, String>();
				for (Asset asset : seq.getAssetsListForWC()) {
					assetMap.put(asset.getAssetNumber(), getDescNumber(asset
							.getAssetDescNumber()));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// to do set the error for user
		}

	}

	private String getDescNumber(String inputString) {
		if (StringUtils.isEmpty(inputString)) {
			return EMPTY_STRING;
		}
		return inputString.substring(inputString.indexOf("-") + 1);

	}

	/**
	 * Gets the pause reasons.
	 * 
	 * @param dto
	 *            the dto
	 * @return the pause reasons
	 */
	public void getPauseReasons(ManufacturingOrder dto) {
		// todo get pause reasons from ExActivity table
		try {
			// get the query from the usercontext using the queryid
			HashMap qryMap = userContext.getQueries();
			String qry = (String) qryMap
					.get(webConstants.SHOP_ADMIN_PAUSE_REASONS);
			// getting master data info for pause reasons
			ArrayList<MasterData> list = (ArrayList<MasterData>) dao
					.getPauseReasons(qry);
			dto.setPauseReasons(list);

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

	}

	/**
	 * M3 update needed.
	 * 
	 * @param seq
	 *            the seq
	 * @param manualCompletionFlag
	 *            the manual completion flag
	 * @return true, if successful
	 */
	private boolean m3UpdateNeeded(Sequence seq, String manualCompletionFlag) {
		Date oldLogonDate = null;
		Date oldLogoffDate = null;
		Date logonDate = null;
		Date logoffDate = null;
		String oldAssetNumber = EMPTY_STRING;
		String assetNumber = EMPTY_STRING;
		String oldSeqStatus = EMPTY_STRING;
		String seqStatus = EMPTY_STRING;
		SequenceActivity seqAct = seq.getSelectedSeqActivity();

		if (seqAct.getOldLogonDate() == null) {
			oldLogonDate = new Date();
		} else {
			oldLogonDate = seqAct.getOldLogonDate();
		}
		if (seqAct.getOldLogoffDate() == null) {
			oldLogoffDate = new Date();
		} else {
			oldLogoffDate = seqAct.getOldLogoffDate();
		}
		if (seqAct.getLogonDate() == null) {
			logonDate = new Date();
		} else {
			logonDate = seqAct.getLogonDate();
		}
		if (seqAct.getLogoffDate() == null) {
			logoffDate = new Date();
		} else {
			logoffDate = seqAct.getLogoffDate();
		}

		if (seqAct.getOldAssetNumber() != null) {
			oldAssetNumber = seqAct.getOldAssetNumber();
		}

		if (seqAct.getAssetNumber() != null) {
			assetNumber = seqAct.getAssetNumber();
		}

		if (seq.getOldSequenceStatus() != null) {
			oldSeqStatus = seq.getOldSequenceStatus();
		}

		if (manualCompletionFlag != null) {
			if (manualCompletionFlag.equals("1")) {
				seqStatus = webConstants.STATUS_COMPLETE;
			} else {
				seqStatus = webConstants.STATUS_INPROGRESS;
			}
		}

		/*
		 * System.out.println("oldLogonDate = " + oldLogonDate);
		 * System.out.println("oldLogoffDate = " + oldLogoffDate);
		 * System.out.println("logonDate = " + logonDate);
		 * System.out.println("logoffDate = " + logoffDate);
		 * System.out.println("seqAct.getOldQtyCompleted() = " +
		 * seqAct.getOldQtyCompleted());
		 * System.out.println("seqAct.getQtyCompleted() = " +
		 * seqAct.getQtyCompleted());
		 * System.out.println("oldLogonDate.compareTo(logonDate) = " +
		 * oldLogonDate.compareTo(logonDate));
		 * System.out.println("oldLogoffDate.compareTo(logoffDate) = " +
		 * oldLogoffDate.compareTo(logoffDate));System.out.println(
		 * "seqAct.getOldQtyCompleted() == seqAct.getQtyCompleted() = " +
		 * (seqAct.getOldQtyCompleted() == seqAct.getQtyCompleted()));
		 */
		return (oldLogonDate.compareTo(logonDate) != 0
				|| oldLogoffDate.compareTo(logoffDate) != 0
				|| seqAct.getOldQtyCompleted() != seqAct.getQtyCompleted() || !oldSeqStatus
				.equalsIgnoreCase(seqStatus));
	}

	/**
	 * Validate.
	 * 
	 * @param seq
	 *            the seq
	 * @param manualCompletionFlag
	 *            the manual completion flag
	 * @throws Exception
	 *             the exception
	 */
	private void validate(Sequence seq, String manualCompletionFlag)
			throws Exception {
		Date oldLogonDate = null;
		Date oldLogoffDate = null;
		Date logonDate = null;
		Date logoffDate = null;
		String oldAssetNumber = EMPTY_STRING;
		String assetNumber = EMPTY_STRING;
		String oldSeqStatus = EMPTY_STRING;
		String seqStatus = EMPTY_STRING;
		SequenceActivity seqAct = seq.getSelectedSeqActivity();

		if (seqAct.getOldLogonDate() == null) {
			oldLogonDate = new Date();
		} else {
			oldLogonDate = seqAct.getOldLogonDate();
		}
		if (seqAct.getOldLogoffDate() == null) {
			oldLogoffDate = new Date();
		} else {
			oldLogoffDate = seqAct.getOldLogoffDate();
		}
		if (seqAct.getLogonDate() == null) {
			logonDate = new Date();
		} else {
			logonDate = seqAct.getLogonDate();
		}
		if (seqAct.getLogoffDate() == null) {
			logoffDate = new Date();
		} else {
			logoffDate = seqAct.getLogoffDate();
		}

		if (seqAct.getOldAssetNumber() != null) {
			oldAssetNumber = seqAct.getOldAssetNumber();
		}

		if (seqAct.getAssetNumber() != null) {
			assetNumber = seqAct.getAssetNumber();
		}

		/*
		 * START - Fix for Sharepoint Item 1293 - 1 - Re-Opening a sequence
		 * makes it impossible to close
		 */

		if (seq.getOldSequenceStatus() != null) {
			oldSeqStatus = seq.getOldSequenceStatus();
		}

		if (manualCompletionFlag != null) {
			if (manualCompletionFlag.equals("1")) {
				seqStatus = webConstants.STATUS_COMPLETE;
			} else {
				seqStatus = webConstants.STATUS_INPROGRESS;
			}
		}
		/*
		 * END - Fix for Sharepoint Item 1293 - 1 - Re-Opening a sequence makes
		 * it impossible to close
		 */

		/*
		 * System.out.println("oldLogonDate = " + oldLogonDate);
		 * System.out.println("oldLogoffDate = " + oldLogoffDate);
		 * System.out.println("logonDate = " + logonDate);
		 * System.out.println("logoffDate = " + logoffDate);
		 * System.out.println("seq.getOldSequenceStatus() = "
		 * +seq.getOldSequenceStatus());
		 * System.out.println("seq.getSequenceStatus() = "
		 * +seq.getSequenceStatus());
		 * System.out.println("seqAct.getOldQtyCompleted() = " +
		 * seqAct.getOldQtyCompleted());
		 * System.out.println("seqAct.getQtyCompleted() = " +
		 * seqAct.getQtyCompleted());
		 * System.out.println("oldLogonDate.compareTo(logonDate) = " +
		 * oldLogonDate.compareTo(logonDate));
		 * System.out.println("oldLogoffDate.compareTo(logoffDate) = " +
		 * oldLogoffDate.compareTo(logoffDate));System.out.println(
		 * "seqAct.getOldQtyCompleted() == seqAct.getQtyCompleted() = " +
		 * (seqAct.getOldQtyCompleted() == seqAct.getQtyCompleted()));
		 */

		int noOfChanges = findNoOfChanges(oldLogonDate, logonDate,
				oldLogoffDate, logoffDate, seqAct.getOldQtyCompleted(), seqAct
						.getQtyCompleted(), oldAssetNumber, assetNumber,
				oldSeqStatus, seqStatus);

		if (noOfChanges == 0) {
			throw new Exception("No Changes Done.");
		} else if (noOfChanges > 1) {
			seqAct.setMultipleCorrectionFlag("Y");
		}

	}

	/**
	 * Find no of changes.
	 * 
	 * @param oldLogonDate
	 *            the old logon date
	 * @param logonDate
	 *            the logon date
	 * @param oldLogoffDate
	 *            the old logoff date
	 * @param logoffDate
	 *            the logoff date
	 * @param oldQtyCompleted
	 *            the old qty completed
	 * @param qtyCompleted
	 *            the qty completed
	 * @param oldAssetNumber
	 *            the old asset number
	 * @param assetNumber
	 *            the asset number
	 * @param oldSeqStatus
	 *            the old seq status
	 * @param seqStatus
	 *            the seq status
	 * @return the int
	 */
	private int findNoOfChanges(Date oldLogonDate, Date logonDate,
			Date oldLogoffDate, Date logoffDate, int oldQtyCompleted,
			int qtyCompleted, String oldAssetNumber, String assetNumber,
			String oldSeqStatus, String seqStatus) {
		int noOfChanges = 0;
		if (oldLogonDate.compareTo(logonDate) != 0) {
			noOfChanges++;
		}
		if (oldLogoffDate.compareTo(logoffDate) != 0) {
			noOfChanges++;
		}
		if (oldQtyCompleted != qtyCompleted) {
			noOfChanges++;
		}
		if (!oldAssetNumber.equalsIgnoreCase(assetNumber)) {
			noOfChanges++;
		}

		/*
		 * START - Fix for Sharepoint Item 1293 - 1 - Re-Opening a sequence
		 * makes it impossible to close
		 */

		if (!oldSeqStatus.equalsIgnoreCase(seqStatus)) {
			noOfChanges++;
		}

		/*
		 * END - Fix for Sharepoint Item 1293 - 1 - Re-Opening a sequence makes
		 * it impossible to close
		 */

		return noOfChanges;
	}

	/**
	 * Refresh sequence.
	 * 
	 * @param seqAct
	 *            the seq act
	 * @param seq
	 *            the seq
	 */
	private void refreshSequence(SequenceActivity seqAct, Sequence seq) {
		seqAct.setOldActivity(seqAct.getActivity());
		seqAct.setOldLogoffDate(seqAct.getLogoffDate());
		seqAct.setOldLogonDate(seqAct.getLogonDate());
		seqAct.setOldAssetNumber(seqAct.getAssetNumber());
		seqAct.setOldAssetDesc(seqAct.getAssetDesc());
		seqAct.setOldQtyCompleted(seqAct.getQtyCompleted());
		seqAct.setOldEmployeeNumber(seqAct.getEmployeeName());
		seq.setOldSequenceStatus(seq.getSequenceStatus());

		seqAct.setMultipleCorrectionFlag(EMPTY_STRING);
	}

	/**
	 * Confirm activity details.
	 * 
	 * @param dto
	 *            the dto
	 * @param request
	 *            the request
	 * @throws Exception
	 *             the exception
	 */
	public void confirmActivityDetails(ManufacturingOrder dto,
			HttpServletRequest request) throws Exception {
		Sequence seq = dto.getSelectedSequeuce();
		SequenceActivity seqAct = seq.getSelectedSeqActivity();
		seqAct.addError(null);
		// the values needs to be obtained from the request and set back to the
		// seqAct

		boolean flag = setValuesFromRequest(request, dto);

		String manualCompletionFlag = null;
		if (request.getParameter("seqCompFlag1") != null
				&& request.getParameter("seqCompFlag1").equalsIgnoreCase("0")) {
			manualCompletionFlag = "0";
		} else {
			manualCompletionFlag = "1";
		}

		// when the user confirms the activity details,
		// the following validations needs to be done and update in M3
		// boolean flag = validateActivityDetails(dto);

		validate(seq, manualCompletionFlag);

		if (flag) {

			// since the validation is through, update in M3 as well as local

			// first update in M3

			boolean negativeFlag = true;

			HashMap map = userContext.getQueries();
			String errorLog = (String) map.get("SHOP_108");

			boolean updateRun = false;
			try {
				if (m3UpdateNeeded(seq, manualCompletionFlag)) {
					// reversal record - this is not reqd for new rec insertion
					// for status not started or missing info
					if (!seqAct.isNewFlag() || !seq.isAddNewJob()) {
						if (seqAct.getOldActivity() != null
								&& (seqAct.getOldActivity().equalsIgnoreCase(
										webConstants.RUN) || seqAct
										.getOldActivity().equalsIgnoreCase(
												webConstants.SETUP))) {
							// Commented by Rahjesh Allow the user to report
							// only quantity difference
							// if (getTimeDiffInHrs(seq, negativeFlag)) {

							/*
							 * START. Sharepoint item 1441. Reporting of Setup
							 * and Run Times Incorrect
							 */
							if (seq.getSequenceStatus() != null
									&& !seq
											.getSequenceStatus()
											.equalsIgnoreCase(
													webConstants.STATUS_NOT_STARTED)) {
								dao.PMZ070WriteSetRptOperation(dto
										.getMoNumber(), seqAct
										.getOldQtyCompleted(),
										getTimeDiffInHrs(seq, negativeFlag,
												dao,
												seq.getSelectedSeqActivity()),
										seq.getSequence(), seqAct
												.getOldEmployeeNumber(),
										webConstants.SEQ_NOT_COMPLETED,
										negativeFlag, seq
												.getWorkCenterCapacity(), seq
												.getPriceTimeQty(), seqAct
												.getOldActivity(), getWeight(
												dto.getMoNumber(), seq
														.getSequence(), seq
														.getPartNumber(),
												seqAct.getOldQtyCompleted(),
												seq.getWorkCenterCapacity(),
												seq.getPriceTimeQty()),
										errorLog, userContext.getUserName(),
										(String) userContext.getQueries().get(
												"M3SHIP_116"));
								/*
								 * END. Sharepoint item 1441. Reporting of Setup
								 * and Run Times Incorrect
								 */
								// }
							}
						} else if (seqAct.getOldActivity() != null
								&& (seqAct.getOldActivity()
										.equalsIgnoreCase(webConstants.RETOOL))) {
							reverseRetool(dto, seqAct, seq,
									manualCompletionFlag, errorLog);
							updateRun = true;
						} else if (seqAct.getOldActivity() != null
								&& (seqAct.getOldActivity()
										.equalsIgnoreCase(webConstants.PAUSE))) {
							reversePause(dto, seqAct, seq,
									manualCompletionFlag, errorLog);
							updateRun = true;
						}
						// else if (seqAct.getOldActivity() != null &&
						// (seqAct.getOldActivity().equalsIgnoreCase(
						// webConstants.PAUSE))) {
						// reverseRetool();
						// }
					}
					// changed or added record
					if (seqAct.getActivity().equalsIgnoreCase(webConstants.RUN)
							|| seqAct.getActivity().equalsIgnoreCase(
									webConstants.SETUP)) {
						negativeFlag = false;
						/*
						 * START. Sharepoint item 1441. Reporting of Setup and
						 * Run Times Incorrect
						 */
						// getTimeDiffInHrs(seq, negativeFlag);
						// if (getTimeDiffInHrs(seq, negativeFlag)) {
						/*
						 * dao.PMS70WriteSetRptOperation(dto.getMoNumber(),
						 * seqAct .getQtyCompleted(),
						 * seqAct.getLaborSetupTime(), seqAct.getLaborRunTime(),
						 * seqAct .getMachineRunTime(), seqAct
						 * .getMachineSetupTime(), seq .getSequence(), seqAct
						 * .getEmployeeNumber(), manualCompletionFlag,
						 * negativeFlag, errorLog);
						 */
						dao.PMZ070WriteSetRptOperation(dto.getMoNumber(),
								seqAct.getQtyCompleted(), getTimeDiffInHrs(seq,
										negativeFlag, dao, seq
												.getSelectedSeqActivity()), seq
										.getSequence(), seqAct
										.getEmployeeNumber(),
								manualCompletionFlag, negativeFlag, seq
										.getWorkCenterCapacity(), seq
										.getPriceTimeQty(), seqAct
										.getActivity(), getWeight(dto
										.getMoNumber(), seq.getSequence(), seq
										.getPartNumber(), seqAct
										.getQtyCompleted(), seq
										.getWorkCenterCapacity(), seq
										.getPriceTimeQty()), errorLog,
								userContext.getUserName(), (String) userContext
										.getQueries().get("M3SHIP_116"));
						/*
						 * END. Sharepoint item 1441. Reporting of Setup and Run
						 * Times Incorrect
						 */
						// }
					} else if (seqAct.getActivity().equalsIgnoreCase(
							webConstants.RETOOL)) {
						editRetool(dto, seqAct, seq, manualCompletionFlag,
								errorLog);
					}
				}

				// calculate total qty
				double qtyCompleted = seq.getQtyCompleted();
				qtyCompleted = qtyCompleted + seqAct.getQtyCompleted()
						- seqAct.getOldQtyCompleted();
				seq.setQtyCompleted(qtyCompleted);

				updateLocalDB(dto, manualCompletionFlag);
				System.out
						.println("updateRun......................................................."
								+ updateRun);
				if (updateRun) {
					updateRun(dto, seqAct, seq, manualCompletionFlag, errorLog);
				}

				if (request.getParameter("newFlag").equals("Y")) {
					seq.getSeqActivityDetails().add(seqAct);
				}
				// update the status of all the activities, if the complete
				// sequence option checked.
				if (manualCompletionFlag != null) {
					if (manualCompletionFlag.equals("1")) {
						ArrayList<SequenceActivity> sequenceActivities = seq
								.getSeqActivityDetails();
						for (SequenceActivity sequenceActivity : sequenceActivities) {
							sequenceActivity
									.setActivityStatus(webConstants.STATUS_COMPLETE);
						}
						seq.setSequenceStatus(webConstants.STATUS_COMPLETE);
					} else {
						seq.setSequenceStatus(webConstants.STATUS_INPROGRESS);
					}
				}
				setStatusForRunAndSetupActivity(seq);

				// To Do add next NOT STARTED Seq from M3
				if (seq.getSequenceStatus() != null
						&& webConstants.STATUS_COMPLETE.equalsIgnoreCase(seq
								.getSequenceStatus())) {
					mergeSequenceData(dto);
				}
				try {
					closeMo(new Sequence(dto.getMoNumber(), seqAct
							.getQtyCompleted(), applicationPropertyBean
							.getCompany(), seqAct.getEmployeeNumber()));
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			} catch (Exception e) {
				e.printStackTrace();
				seqAct.addError(e.getMessage());
			}
		}
		refreshSequence(seqAct, seq);
	}

	/**
	 * Update run.
	 * 
	 * @param dto
	 *            the dto
	 * @param seqAct
	 *            the seq act
	 * @param seq
	 *            the seq
	 * @param manualCompletionFlag
	 *            the manual completion flag
	 * @param errorLog
	 *            the error log
	 * @throws Exception
	 *             the exception
	 */
	private void updateRun(ManufacturingOrder dto, SequenceActivity seqAct,
			Sequence seq, String manualCompletionFlag, String errorLog)
			throws Exception {
		System.out
				.println("updateRun////////////////////////////////////////////////////");
		ArrayList<SequenceActivity> ad = seq.getSeqActivityDetails();
		for (SequenceActivity sequenceActivity : ad) {
			System.out.println("sequenceActivity = "
					+ sequenceActivity.getActivity());
			System.out.println("sequenceActivity = "
					+ sequenceActivity.getJobActivityNumber());
			System.out.println("seqAct.getJobActivityNumber()  = "
					+ seqAct.getJobActivityNumber());
			if (sequenceActivity.getJobActivityNumber() == seqAct
					.getJobActivityNumber()
					&& sequenceActivity.getActivity().equalsIgnoreCase(
							webConstants.RUN)) {
				System.out.println("updating ..................... Run");
				dao.PMZ070WriteSetRptOperation(dto.getMoNumber(),
						sequenceActivity.getQtyCompleted(), getTimeDiffInHrs(
								seq, false, dao, sequenceActivity), seq
								.getSequence(), sequenceActivity
								.getEmployeeNumber(), manualCompletionFlag,
						false, seq.getWorkCenterCapacity(), seq
								.getPriceTimeQty(), sequenceActivity
								.getActivity(), getWeight(dto.getMoNumber(),
								seq.getSequence(), seq.getPartNumber(),
								sequenceActivity.getQtyCompleted(), seq
										.getWorkCenterCapacity(), seq
										.getPriceTimeQty()), errorLog,
						userContext.getUserName(), (String) userContext
								.getQueries().get("M3SHIP_116"));
			}
		}

	}

	/**
	 * Reverse retool.
	 * 
	 * @param dto
	 *            the dto
	 * @param seqAct
	 *            the seq act
	 * @param seq
	 *            the seq
	 * @param manualCompletionFlag
	 *            the manual completion flag
	 * @param errorLog
	 *            the error log
	 * @throws Exception
	 *             the exception
	 */
	private void reverseRetool(ManufacturingOrder dto, SequenceActivity seqAct,
			Sequence seq, String manualCompletionFlag, String errorLog)
			throws Exception {
		System.out
				.println("reverseRetool..........................................................................................................");
		dao.PMZ070WriteSetRptOperation(dto.getMoNumber(), seqAct
				.getOldQtyCompleted(), getTimeDiffInHrs(seq, true, dao, seq
				.getSelectedSeqActivity()), seq.getSequence(), seqAct
				.getOldEmployeeNumber(), webConstants.SEQ_NOT_COMPLETED, true,
				seq.getWorkCenterCapacity(), seq.getPriceTimeQty(),
				webConstants.SETUP, getWeight(dto.getMoNumber(), seq
						.getSequence(), seq.getPartNumber(), seqAct
						.getOldQtyCompleted(), seq.getWorkCenterCapacity(), seq
						.getPriceTimeQty()), errorLog, userContext
						.getUserName(), (String) userContext.getQueries().get(
						"M3SHIP_116"));

		ArrayList<SequenceActivity> ad = seq.getSeqActivityDetails();
		for (SequenceActivity sequenceActivity : ad) {
			System.out.println("sequenceActivity = "
					+ sequenceActivity.getActivity());
			System.out.println("sequenceActivity = "
					+ sequenceActivity.getJobActivityNumber());
			System.out.println("seqAct.getJobActivityNumber()  = "
					+ seqAct.getJobActivityNumber());
			if (sequenceActivity.getJobActivityNumber() == seqAct
					.getJobActivityNumber()
					&& sequenceActivity.getActivity().equalsIgnoreCase(
							webConstants.RUN)) {
				System.out
						.println("Reverse Run..............................................................................");
				dao.PMZ070WriteSetRptOperation(dto.getMoNumber(),
						sequenceActivity.getOldQtyCompleted(),
						getTimeDiffInHrs(seq, true, dao, sequenceActivity), seq
								.getSequence(), sequenceActivity
								.getOldEmployeeNumber(),
						webConstants.SEQ_NOT_COMPLETED, true, seq
								.getWorkCenterCapacity(),
						seq.getPriceTimeQty(), sequenceActivity
								.getOldActivity(), getWeight(dto.getMoNumber(),
								seq.getSequence(), seq.getPartNumber(),
								sequenceActivity.getOldQtyCompleted(), seq
										.getWorkCenterCapacity(), seq
										.getPriceTimeQty()), errorLog,
						userContext.getUserName(), (String) userContext
								.getQueries().get("M3SHIP_116"));
			}
		}

		System.out
				.println("reverseRetool.........................................................................................................END .");
	}

	/**
	 * Reverse pause.
	 * 
	 * @param dto
	 *            the dto
	 * @param seqAct
	 *            the seq act
	 * @param seq
	 *            the seq
	 * @param manualCompletionFlag
	 *            the manual completion flag
	 * @param errorLog
	 *            the error log
	 * @throws Exception
	 *             the exception
	 */
	private void reversePause(ManufacturingOrder dto, SequenceActivity seqAct,
			Sequence seq, String manualCompletionFlag, String errorLog)
			throws Exception {
		System.out
				.println("reversePause..........................................................................................................");
		ArrayList<SequenceActivity> ad = seq.getSeqActivityDetails();
		for (SequenceActivity sequenceActivity : ad) {
			System.out.println("sequenceActivity = "
					+ sequenceActivity.getActivity());
			System.out.println("sequenceActivity = "
					+ sequenceActivity.getJobActivityNumber());
			System.out.println("seqAct.getJobActivityNumber()  = "
					+ seqAct.getJobActivityNumber());
			if (sequenceActivity.getJobActivityNumber() == seqAct
					.getJobActivityNumber()
					&& sequenceActivity.getActivity().equalsIgnoreCase(
							webConstants.RUN)) {
				System.out
						.println("Reverse Run..............................................................................");
				dao.PMZ070WriteSetRptOperation(dto.getMoNumber(),
						sequenceActivity.getOldQtyCompleted(),
						getTimeDiffInHrs(seq, true, dao, sequenceActivity), seq
								.getSequence(), sequenceActivity
								.getOldEmployeeNumber(),
						webConstants.SEQ_NOT_COMPLETED, true, seq
								.getWorkCenterCapacity(),
						seq.getPriceTimeQty(), sequenceActivity
								.getOldActivity(), getWeight(dto.getMoNumber(),
								seq.getSequence(), seq.getPartNumber(),
								sequenceActivity.getOldQtyCompleted(), seq
										.getWorkCenterCapacity(), seq
										.getPriceTimeQty()), errorLog,
						userContext.getUserName(), (String) userContext
								.getQueries().get("M3SHIP_116"));
			}
		}

		System.out
				.println("reverseRetool.........................................................................................................END .");
	}

	/**
	 * Edits the retool.
	 * 
	 * @param dto
	 *            the dto
	 * @param seqAct
	 *            the seq act
	 * @param seq
	 *            the seq
	 * @param manualCompletionFlag
	 *            the manual completion flag
	 * @param errorLog
	 *            the error log
	 * @throws Exception
	 *             the exception
	 */
	private void editRetool(ManufacturingOrder dto, SequenceActivity seqAct,
			Sequence seq, String manualCompletionFlag, String errorLog)
			throws Exception {
		System.out
				.println("editRetool..............................................................................................."
						+ seqAct.getActivity());
		dao.PMZ070WriteSetRptOperation(dto.getMoNumber(), seqAct
				.getQtyCompleted(), getTimeDiffInHrs(seq, false, dao, seq
				.getSelectedSeqActivity()), seq.getSequence(), seqAct
				.getEmployeeNumber(), manualCompletionFlag, false, seq
				.getWorkCenterCapacity(), seq.getPriceTimeQty(),
				webConstants.SETUP, getWeight(dto.getMoNumber(), seq
						.getSequence(), seq.getPartNumber(), seqAct
						.getQtyCompleted(), seq.getWorkCenterCapacity(), seq
						.getPriceTimeQty()), errorLog, userContext
						.getUserName(), (String) userContext.getQueries().get(
						"M3SHIP_116"));

	}

	// get BOM list.. check status whether all the oprations completed by 90.
	// Call 3 API.s and follwed with Allocation Engine.
	/**
	 * Close mo.
	 * 
	 * @param seq
	 *            the seq
	 */
	private void closeMo(Sequence seq) {

		System.out.println("closeMo..................................");
		String query = "Select count(*) PPHError from dbo.v_OpenJobDetails where MONumber = ? and ActualCostSuccess = 'N'";
		if (dao.isPPHErrorExist(query, seq.getMoNumber())) {
			System.out
					.println("..............................................");
			return;
		}

		ArrayList<Sequence> sequences = getBOMList(seq);
		for (Sequence sequence : sequences) {
			// System.out.println("sequence.getStatus() = "+sequence.getStatus());
			int status = 0;
			try {
				if (sequence.getStatus() != null) {
					status = Integer.parseInt(sequence.getStatus());
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			// System.out.println("status = " + status);
			if (status != 90) {
				// System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
				return;
			}
		}

		int morvQty = getMorvQty(sequences);
		seq.setMorvQuantity(morvQty);

		dao.PMS50WriteSetRptReceipt(seq, (String) userContext.getQueries().get(
				"SHOP_108"), userContext.getUserName(), (String) userContext
				.getQueries().get("M3SHIP_116"));

		// Call Procedure to close the Openend Activities
		seq.setLineNumber("000");
		dao.closeAllOpenedActivities(seq, (String) userContext.getQueries()
				.get("SHOP_084"));
		// Update the MO status in SFS_OrderMaster as 'C'
		dao.updateMOStatusAsCompleted(seq, (String) userContext.getQueries()
				.get("SHOP_094"));

		dao.approveInspectionResultUsingLIS200(seq, (String) userContext
				.getQueries().get("SHOP_108"), userContext.getUserName(),
				(String) userContext.getQueries().get(" SHOP_159 "));
		try {
			dao.inspectedItemPutawayUsingPMS130(seq, (String) userContext
					.getQueries().get("SHOP_108"), userContext.getUserName(),
					(String) userContext.getQueries().get(" SHOP_159 "));
			// Begin WO# 27639 - Moving Static links from Application to
			// Database - Pinky S - Infosys - 22 June 2011
			// dao.doPhysicalAllocation(seq.getProductNumber(),
			// seq.getFacility());
			// End WO# 27639 - Moving Static links from Application to Database
			// - Pinky S - Infosys - 22 June 2011
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets the morv qty.
	 * 
	 * @param bomList
	 *            the bom list
	 * @return the morv qty
	 */
	private int getMorvQty(ArrayList<Sequence> bomList) {
		for (int i = 0; i < bomList.size(); i++) {
			Sequence sequence = (Sequence) bomList.get(i);

			if (sequence.getPlanningArea() != null
					&& "MORV".equalsIgnoreCase(sequence.getPlanningArea())) {
				return sequence.getCompletedQuantity();
			}
		}
		return 0;
	}

	/**
	 * Update local db.
	 * 
	 * @param dto
	 *            the dto
	 * @param manualCompletionFlag
	 *            the manual completion flag
	 * @throws Exception
	 *             the exception
	 */
	private void updateLocalDB(ManufacturingOrder dto,
			String manualCompletionFlag) throws Exception {
		// now local db we need to update into seq act & activity log table also
		// also save the old and new data in audit table
		// update into seq act log
		String qry = null;
		HashMap map = userContext.getQueries();
		Sequence seq = dto.getSelectedSequeuce();
		SequenceActivity seqAct = seq.getSelectedSeqActivity();
		// seqAct.setJobActivityNumber(Integer.parseInt(seq.getJobActivityNumber()));

		// we need to check whether activity is changed
		boolean actChangeFlag = !seqAct.isNewFlag()
				&& !(seqAct.getActivity().equalsIgnoreCase(seqAct
						.getOldActivity()));
		// boolean actChangeFlag = false;

		// System.out.println("actChangeFlag = " + actChangeFlag);

		try {
			// if (seqAct.isNewFlag() || (
			// (seqAct.getActivity().equalsIgnoreCase(webConstants.RUN) &&
			// seqAct.getOldActivity().equalsIgnoreCase(webConstants.SETUP)) ||
			// (seqAct.getActivity().equalsIgnoreCase(webConstants.SETUP) &&
			// seqAct.getOldActivity().equalsIgnoreCase(webConstants.RUN)))) {
			// set the job activity number also need to understand whether we
			// have to have seq act insert also
			// check if the activity is setup or run otherwise no need for
			// insertion into seq act. only updation
			// but still need to get Job act number for it
			qry = (String) map
					.get(webConstants.SHOP_FLOOR_ADMIN_GET_JOBACTIVITY_NUMBER);
			// START Correction Interface Issue fix
			String status = null;
			if (manualCompletionFlag != null) {
				if (manualCompletionFlag.equals("1")) {
					status = webConstants.STATUS_COMPLETE;
				} else {
					status = webConstants.STATUS_INPROGRESS;
				}
			} else {
				status = webConstants.STATUS_INPROGRESS;
			}
			// END Correction Interface Issue fix
			if (dao.getJobActivityNumber(seq, qry)) {
				// System.out.println("record exists so update.................................................");
				// record exists so update
				qry = (String) userContext.getQueries().get("SHOP_136");
				// START Correction Interface Issue fix
				dao.updateSequenceActivity(seqAct, qry, status);
				// END Correction Interface Issue fix
			} else {
				qry = (String) map
						.get(webConstants.SHOP_FLOOR_ADMIN_INSERT_SEQ_ACT);
				// insert into seq act table
				seqAct.setActivityStatus("C");
				// START Correction Interface Issue fix
				dao.insertSequenceActivity(seq, qry, status);
				// END Correction Interface Issue fix
			}

			// }

			// System.out.println("job act number:" +
			// seqAct.getJobActivityNumber());
			// System.out.println("---------------------------------------------------------");
			// System.out.println("                                                                seq.getSequenceStatus()  = "
			// + seq.getSequenceStatus() );
			// System.out.println("---------------------------------------------------------");
			if (seq.getSequenceStatus() != null
					&& !seq.getSequenceStatus().equalsIgnoreCase(
							webConstants.STATUS_NOT_STARTED)) {
				if (seqAct.getCompleteSequenceFlag() != null) {
					// &&
					// seqAct.getCompleteSequenceFlag().equalsIgnoreCase(webConstants.SEQ_COMPLETED))
					// { */
					// qry =
					// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_UPDATE_ORD_DETAIL);
					qry = (String) userContext.getQueries().get("SHOP_137");
					dao.updateOrderDetail(seq, qry);
				}
				// thsi needs to be done if activity is changed from Run to
				// Setup or viceversa
				/*
				 * if (actChangeFlag &&
				 * (seqAct.getActivity().equalsIgnoreCase(webConstants.RUN) &&
				 * seqAct.getOldActivity().equalsIgnoreCase(webConstants.SETUP))
				 * || (seqAct.getActivity().equalsIgnoreCase(webConstants.SETUP)
				 * &&
				 * seqAct.getOldActivity().equalsIgnoreCase(webConstants.RUN)))
				 * { // qry =
				 * (String)map.get(webConstants.SHOP_FLOOR_ADMIN_UPDATE_SEQ_ACT
				 * );
				 * 
				 * qry =
				 * "update SFS_SequenceActivity set ActivityCode = ? , AssetNumber = ? , ActivityStatus = ? where JobActivityNumber = ?"
				 * ; seqAct.setActChangeFlag(actChangeFlag); } else { qry =
				 * "update SFS_SequenceActivity set AssetNumber = ? , ActivityStatus = ? where JobActivityNumber = ?"
				 * ; }
				 * 
				 * dao.updateSequenceActivity(seqAct, qry);
				 */

				// System.out.println("seqAct.isNewFlag()......................................................................................");
				// depending upon the activity insert into corresponding table
				if (seqAct.getActivity().equalsIgnoreCase(webConstants.RUN)
						|| seqAct.getActivity().equalsIgnoreCase(
								webConstants.SETUP)) {
					if (seqAct.isNewFlag()) {
						// System.out.println("1111111111111111111111");
						// qry =
						// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_INSERT_ACT_LOG);
						qry = (String) userContext.getQueries().get("SHOP_130");
						dao.insertActivityLog(seqAct, qry);
					} else {
						if (actChangeFlag) {
							// System.out.println("222222222222222222222222222");
							if (seqAct.getOldActivity().equalsIgnoreCase(
									webConstants.RUN)
									|| seqAct.getOldActivity()
											.equalsIgnoreCase(
													webConstants.SETUP)) {
								// update
								// qry =
								// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_UPDATE_ACT_LOG);
								qry = (String) userContext.getQueries().get(
										"SHOPR_113");
								dao.updateActivityLog(seqAct, qry);
							} else if (seqAct.getOldActivity()
									.equalsIgnoreCase(webConstants.PAUSE)) {
								// qry =
								// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_INSERT_ACT_LOG);
								qry = (String) userContext.getQueries().get(
										"SHOP_130");
								dao.insertActivityLog(seqAct, qry);
								// qry =
								// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_DELETE_ACT_BREAK);
								qry = (String) userContext.getQueries().get(
										"SHOP_140");
								dao.deleteActivityBreak(seqAct, qry);
							} else if (seqAct.getOldActivity()
									.equalsIgnoreCase(webConstants.RETOOL)) {
								//
								// qry =
								// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_INSERT_ACT_LOG);
								qry = (String) userContext.getQueries().get(
										"SHOP_130");
								dao.insertActivityLog(seqAct, qry);
								// qry =
								// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_DELETE_ACT_RETOOL);
								qry = (String) userContext.getQueries().get(
										"SHOP_139");
								dao.deleteActivityRetool(seqAct, qry);
							}
						} else {
							// System.out.println("1111111111111111111111");
							// qry =
							// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_UPDATE_ACT_LOG);
							qry = (String) userContext.getQueries().get(
									"SHOPR_113");
							dao.updateActivityLog(seqAct, qry);
						}
					}
				} else if (seqAct.getActivity().equalsIgnoreCase(
						webConstants.RETOOL)) {
					if (seqAct.isNewFlag()) {
						// qry =
						// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_INSERT_ACT_RETOOL);
						qry = (String) userContext.getQueries().get("SHOP_131");
						dao.insertActivityRetool(seqAct, qry);
					} else {
						if (!actChangeFlag) {
							// qry =
							// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_UPDATE_ACT_RETOOL);
							qry = (String) userContext.getQueries().get(
									"SHOP_135");
							dao.updateActivityRetool(seqAct, qry);
						} else {
							if (seqAct.getOldActivity().equalsIgnoreCase(
									webConstants.PAUSE)) {
								// qry =
								// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_INSERT_ACT_RETOOL);
								qry = (String) userContext.getQueries().get(
										"SHOP_131");
								dao.insertActivityRetool(seqAct, qry);
								// qry =
								// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_DELETE_ACT_BREAK);
								qry = (String) userContext.getQueries().get(
										"SHOP_140");
								dao.deleteActivityBreak(seqAct, qry);
							} else if (seqAct.getOldActivity()
									.equalsIgnoreCase(webConstants.RUN)
									|| seqAct.getOldActivity()
											.equalsIgnoreCase(
													webConstants.SETUP)) {
								// qry =
								// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_INSERT_ACT_RETOOL);
								qry = (String) userContext.getQueries().get(
										"SHOP_131");
								dao.insertActivityRetool(seqAct, qry);
								// qry =
								// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_DELETE_ACT_LOG);
								qry = (String) userContext.getQueries().get(
										"SHOP_138");
								dao.deleteActivityLog(seqAct, qry);
							}
						}
					}

				} else if (seqAct.getActivity().equalsIgnoreCase(
						webConstants.PAUSE)) {
					if (seqAct.isNewFlag()) {
						// qry =
						// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_INSERT_ACT_BREAK);
						qry = (String) userContext.getQueries().get("SHOP_132");
						dao.insertActivityBreak(seqAct, qry);
					} else {
						if (!actChangeFlag) {
							// qry =
							// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_UPDATE_ACT_BREAK);
							qry = (String) userContext.getQueries().get(
									"SHOP_134");
							dao.updateActivityBreak(seqAct, qry);
						} else {
							if (seqAct.getOldActivity().equalsIgnoreCase(
									webConstants.RETOOL)) {
								// qry =
								// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_INSERT_ACT_BREAK);
								qry = (String) userContext.getQueries().get(
										"SHOP_132");
								dao.insertActivityBreak(seqAct, qry);
								// qry =
								// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_DELETE_ACT_RETOOL);
								qry = (String) userContext.getQueries().get(
										"SHOP_139");
								dao.deleteActivityRetool(seqAct, qry);
							} else if (seqAct.getOldActivity()
									.equalsIgnoreCase(webConstants.RUN)
									|| seqAct.getOldActivity()
											.equalsIgnoreCase(
													webConstants.SETUP)) {
								// qry =
								// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_INSERT_ACT_BREAK);
								qry = (String) userContext.getQueries().get(
										"SHOP_132");
								dao.insertActivityBreak(seqAct, qry);
								// qry =
								// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_DELETE_ACT_LOG);
								qry = (String) userContext.getQueries().get(
										"SHOP_138");
								dao.deleteActivityLog(seqAct, qry);
							}
						}
					}
				}
				// if (seq.getSeqActivityDetails() != null) {
				// seq.getSeqActivityDetails().add(seqAct);
				// }

			} else {
				// insert in all tables for not started status - this is not
				// reqd
				// qry =
				// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_INSERT_ORD_MSTR);
				// qry =
				// "INSERT INTO SFS_OrderMaster(MONumber, LineNumber, Facility, Status) values(?,?,?,?)";
				// dao.insertOrderMaster(dto, qry);
				// qry =
				// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_INSERT_ORD_DETAIL);
				// START - WO26943 - Avoid Duplication Of Records in
				// SFS_OrderDetail table
				qry = (String) userContext.getQueries().get("SHOPR_108");
				ManufacturingOrder order = dao.getOrderDetails(dto, qry);
				if (order == null) {
					qry = (String) userContext.getQueries().get("SHOP_128");
					dao.insertOrderDetail(dto, qry);
				} else {
					dto.getSelectedSequeuce()
							.setJobNumber(order.getJobNumber());
				}
				// END - WO26943 - Avoid Duplication Of Records in
				// SFS_OrderDetail table
				// qry =
				// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_INSERT_SEQ_ACT);
				qry = (String) userContext.getQueries().get("SHOP_129");
				// START Correction Interface Issue fix
				dao
						.insertSequenceActivity(seq, qry, seqAct
								.getActivityStatus());
				// END Correction Interface Issue fix
				if (seqAct.getActivity().equalsIgnoreCase(webConstants.RUN)
						|| seqAct.getActivity().equalsIgnoreCase(
								webConstants.SETUP)) {
					// qry =
					// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_INSERT_ACT_LOG);
					qry = (String) userContext.getQueries().get("SHOP_130");
					dao.insertActivityLog(seqAct, qry);
				} else if (seqAct.getActivity().equalsIgnoreCase(
						webConstants.RETOOL)) {
					// qry =
					// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_INSERT_ACT_RETOOL);
					qry = (String) userContext.getQueries().get("SHOP_131");
					dao.insertActivityRetool(seqAct, qry);
				} else if (seqAct.getActivity().equalsIgnoreCase(
						webConstants.PAUSE)) {
					// qry =
					// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_INSERT_ACT_BREAK);
					qry = (String) userContext.getQueries().get("SHOP_132");
					dao.insertActivityBreak(seqAct, qry);
				}
				// since a record is added in this, the sequence will be in
				// progress and also addNewJob will be false
				seq.setAddNewJob(false);
				ArrayList<SequenceActivity> al = seq.getSeqActivityDetails();
				if (al == null) {
					al = new ArrayList<SequenceActivity>();
				}
				// al.add(seqAct);
				seq.setSeqActivityDetails(al);
			}

			// we need to store the old values in a table
			// qry =
			// (String)map.get(webConstants.SHOP_FLOOR_ADMIN_CREATE_AUDIT_LOG);
			qry = (String) userContext.getQueries().get("SHOP_141");
			dao.createCorrectionLog(dto, qry);

			// call the status as "C" in sequence activity table, if the
			// complete sequence option is selected.
			if (manualCompletionFlag != null
					&& manualCompletionFlag.equals("1")) {
				qry = (String) userContext.getQueries().get("SHOPR_051");
				dao.updateActivityCompleteStatus(qry, seq.getJobNum());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			// to do add error and show
			seqAct.addError(ex.getMessage());
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * Validate activity details.
	 * 
	 * @param dto
	 *            the dto
	 * @return true, if successful
	 */
	private boolean validateActivityDetails(ManufacturingOrder dto) {

		boolean flag = false;
		// validations
		// 1. data type mismatch - this wil be taken care of in JS
		// 2. Logon date and Logoff date check -- JS validation
		// 3. mandatory check -- JS validation
		// 4. if the activity selected is pause then reason needs to be shown
		// and made mandatory -- JS validation
		// 5. Employee Number-- validate employee number

		Sequence seq = dto.getSelectedSequeuce();
		SequenceActivity seqAct = seq.getSelectedSeqActivity();
		HashMap empMap = dto.getEmployeeMap();
		// get the query from the usercontext using the queryid
		String name = null;
		if (seqAct.isNewFlag()) {
			HashMap qryMap = userContext.getQueries();
			String qry = (String) qryMap
					.get(webConstants.SHOP_ADMIN_GET_EMPLOYEE_DETAILS);
			// qry =
			// "select EmployeeName from SFS_EmployeeMaster where EmployeeNumber = ?";
			try {
				name = dao.validateEmployee(qry, seqAct.getEmployeeNumber());
				if (name == null) {
					flag = false;
				} else {
					empMap.put(seqAct.getEmployeeNumber(), name);
					flag = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				seqAct.addError("enter valid emp number");
			}
		} else {
			flag = true;
		}

		return flag;
	}

	/**
	 * Gets the time diff in hrs.
	 * 
	 * @param seq
	 *            the seq
	 * @param negativeFlag
	 *            the negative flag
	 * @param sequenceDAO
	 *            the sequence dao
	 * @param seqAct
	 *            the seq act
	 * @return the time diff in hrs
	 */
	private double getTimeDiffInHrs(Sequence seq, boolean negativeFlag,
			SequenceDAO sequenceDAO, SequenceActivity seqAct) {
		double timeDiffInHrs = 0;
		double calculatedRunTime = 0;
		Date logoffDate;
		Date logonDate;

		System.out
				.println("******************************************************************************************");
		System.out.println("getTimeDiffInHrs.........");
		System.out.println("negativeFlag = " + negativeFlag);
		System.out.println("seqAct.getOldLogonDate() = "
				+ seqAct.getOldLogonDate());
		System.out.println("seqAct.getOldLogonDate() = "
				+ seqAct.getOldLogonDate());
		System.out
				.println("seqAct.getLogoffDate() = " + seqAct.getLogoffDate());
		System.out.println("seqAct.getLogonDate() = " + seqAct.getLogonDate());
		System.out
				.println("******************************************************************************************");

		if (negativeFlag) {
			logoffDate = seqAct.getOldLogoffDate();
			logonDate = seqAct.getOldLogonDate();
		} else {
			logoffDate = seqAct.getLogoffDate();
			logonDate = seqAct.getLogonDate();
		}

		System.out.println("logoffDate = " + logoffDate);
		System.out.println("logonDate = " + logonDate);
		if (logoffDate != null && logonDate != null) {
			System.out.println("logonDate = " + logonDate);
			System.out.println("logoffDate = " + logoffDate);
			long timeDifference = logoffDate.getTime() - logonDate.getTime();

			if (seqAct.getOldActivity() != null
					&& seqAct.getOldActivity().equalsIgnoreCase(
							webConstants.RUN)) {
				System.out.println("timeDifference = " + timeDifference);
				// ---------------------------------------
				float breakTime = 0;
				breakTime = sequenceDAO.getBreakTime((String) userContext
						.getQueries().get("SHOP_153"), new Integer(seqAct
						.getJobActivityNumber()).toString(), logonDate,
						logoffDate);
				System.out.println("Break Time ->" + breakTime);

				float retoolTime = 0;
				retoolTime = sequenceDAO.getRetoolTime((String) userContext
						.getQueries().get("SHOP_154"), new Integer(seqAct
						.getJobActivityNumber()).toString(), logonDate,
						logoffDate);
				System.out.println("Retool Time ->" + retoolTime);
				// ---------------------------------------
				calculatedRunTime = (timeDifference / MILS_PER_HOUR)
						- (breakTime / SECS_PER_HOUR)
						- (retoolTime / SECS_PER_HOUR);
			} else {
				calculatedRunTime = (timeDifference / MILS_PER_HOUR);
			}

			System.out.println("calculatedRunTime = " + calculatedRunTime);
			// timeDiffInHrs = (logoffDate.getTime() - logonDate.getTime())
			// / webConstants.MILLISECS_PER_HR;
			// System.out.println("time diff" + timeDiffInHrs);
		}
		return calculatedRunTime;
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
	 * Gets the m3 apiws client.
	 * 
	 * @return the m3 apiws client
	 */
	public M3APIWSClient getM3APIWSClient() {
		return m3APIWSClient;
	}

	/**
	 * Sets the m3 apiws client.
	 * 
	 * @param m3apiwsClient
	 *            the new m3 apiws client
	 */
	public void setM3APIWSClient(M3APIWSClient m3apiwsClient) {
		m3APIWSClient = m3apiwsClient;
	}

	/**
	 * Gets the application property bean.
	 * 
	 * @return the application property bean
	 */
	public ApplicationPropertyBean getApplicationPropertyBean() {
		return applicationPropertyBean;
	}

	/**
	 * Sets the application property bean.
	 * 
	 * @param applicationPropertyBean
	 *            the new application property bean
	 */
	public void setApplicationPropertyBean(
			ApplicationPropertyBean applicationPropertyBean) {
		this.applicationPropertyBean = applicationPropertyBean;
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
	 * Sets the values from request.
	 * 
	 * @param request
	 *            the request
	 * @param dto
	 *            the dto
	 * @return true, if successful
	 */
	public boolean setValuesFromRequest(HttpServletRequest request,
			ManufacturingOrder dto) {

		boolean flag = true;
		Sequence seq = dto.getSelectedSequeuce();
		if (seq != null) {
			SequenceActivity seqAct = seq.getSelectedSeqActivity();
			if (seqAct == null) {
				seqAct = new SequenceActivity();
				seq.setSelectedSeqActivity(seqAct);

			}
			seqAct.addError(null);
			if (seq.isAddNewJob()) {
				seqAct.setNewFlag(true);
			}
			if (request.getParameter("loginTime1") != null
					&& !request.getParameter("loginTime1").trim().equals(
							EMPTY_STRING)) {
				try {
					seqAct.setLogonDate(DateUtil.formatDate(request
							.getParameter("loginTime1"),
							"MM-dd-yyyy hh:mm:ss aa"));
				} catch (Exception e) {
					try {
						seqAct.setLogonDate(DateUtil.formatDate(request
								.getParameter("loginTime1"),
								"MM-dd-yyyy hh:mm:ssaa"));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			if (request.getParameter("logoffTime1") != null
					&& !request.getParameter("logoffTime1").trim().equals(
							EMPTY_STRING)) {
				try {
					seqAct.setLogoffDate(DateUtil.formatDate(request
							.getParameter("logoffTime1"),
							"MM-dd-yyyy hh:mm:ss aa"));
				} catch (Exception e) {
					try {
						seqAct.setLogoffDate(DateUtil.formatDate(request
								.getParameter("logoffTime1"),
								"MM-dd-yyyy hh:mm:ssaa"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			// System.out.println("request.getParameter(activity1) = " +
			// request.getParameter("activity1"));
			if (request.getParameter("activity1") != null) {
				seqAct.setActivity(request.getParameter("activity1"));
			}

			seqAct.setAssetNumber(request.getParameter("assetNo1"));

			seqAct.setAssetNumber(request.getParameter("assetNo1"));

			// System.out.println("qty comp" +
			// request.getParameter("qtyCompleted1"));
			if (request.getParameter("qtyCompleted1") != null) {
				seqAct.setQtyCompleted(Integer.parseInt(request
						.getParameter("qtyCompleted1")));
			}
			// System.out.println("qty comp after setting" +
			// seqAct.getQtyCompleted());

			seqAct
					.setCompleteSequenceFlag(request
							.getParameter("seqCompFlag1"));

			// System.out.println("compl seq flag" +
			// seqAct.getCompleteSequenceFlag());

			if (seqAct.getCompleteSequenceFlag() != null
					&& seqAct.getCompleteSequenceFlag().equalsIgnoreCase("1")) {
				seqAct.setCompleteSequenceFlag("1");
			} else {
				seqAct.setCompleteSequenceFlag("0");
			}

			if (seqAct.getActivity() != null
					&& seqAct.getActivity()
							.equalsIgnoreCase(webConstants.PAUSE)) {
				seqAct.setReasonCode(request.getParameter("reasonCode1"));
			}

			if (seqAct.isNewFlag()) {
				// set the stamp #, date and employee number fields

				seqAct.setEmployeeNumber(request.getParameter("employeeNo1"));
				if (seqAct.getEmployeeNumber() != null
						&& !seqAct.getEmployeeNumber().equals(EMPTY_STRING)) {
					// validate and set employee name
					if (dto.getEmployeeMap() != null) {
						String name = (String) dto.getEmployeeMap().get(
								seqAct.getEmployeeNumber());
						if (name == null) {
							// add error
							seqAct.addError("Invalid Employee number");
							flag = false;
						} else {
							seqAct.setEmployeeName(name);
						}
					}
				}

				if (request.getParameter("date1") != null
						&& !request.getParameter("date1").trim().equals(
								EMPTY_STRING)) {
					try {
						seqAct.setRouterDate(DateUtil.formatDate(request
								.getParameter("date1"),
								"MM-dd-yyyy hh:mm:ss aa"));
					} catch (Exception e) {
						try {
							seqAct.setRouterDate(DateUtil.formatDate(request
									.getParameter("date1"),
									"MM-dd-yyyy hh:mm:ssaa"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}

				seqAct.setStampNo(request.getParameter("stampNo1"));

			}

			// System.out.println("employee no" + seqAct.getEmployeeNumber());
		}

		return flag;
	}

	/* START. Sharepoint item 1441. Reporting of Setup and Run Times Incorrect */
	/**
	 * Gets the weight.
	 * 
	 * @param moNumber
	 *            the mo number
	 * @param sequenceNumber
	 *            the sequence number
	 * @param partNumber
	 *            the part number
	 * @param totalCompletedQty
	 *            the total completed qty
	 * @param workCenterCapacity
	 *            the work center capacity
	 * @param priceTimeQty
	 *            the price time qty
	 * @return the weight
	 */
	private double getWeight(String moNumber, String sequenceNumber,
			String partNumber, double totalCompletedQty,
			int workCenterCapacity, double priceTimeQty) {
		System.out.println("getWeight............................ Controller");
		System.out.println("moNumber = " + moNumber);
		System.out.println("sequenceNumber = " + sequenceNumber);
		System.out.println("partNumber = " + partNumber);
		System.out.println("totalCompletedQty = " + totalCompletedQty);
		System.out.println("workCenterCapacity = " + workCenterCapacity);
		System.out.println("priceTimeQty = " + priceTimeQty);

		double weight = 0;
		if (priceTimeQty == PriceTimeQuantity.WEIGHT
				&& workCenterCapacity == WorkCenterCapacity.MACHINE) {
			// System.out.println("calculate weight.........................");
			Sequence seq = new Sequence();
			seq.setMoNumber(moNumber);
			seq.setSequence(sequenceNumber);
			seq.setPartNumber(partNumber);

			Item item = null;
			String query = (String) userContext.getQueries().get("SHOP_017");// "Select ItemWeight From SFS_ItemWeight Where ItemNumber = ? And SequenceNumber = ?";
			seq.setQuery(query);
			try {
				item = itemDao.checkItemWeight(seq);
				if (item != null) {
					// System.out.println("In Handler ------- item Weight="
					// + item.getAverageWeight());
					// Begin - WO#25255 - Report weight processed for manually
					// entered quantity - VP - Infosys - 05-May-2011
					if (item.getAverageWeight() > 0) {
						// End - WO#25255 - Report weight processed for manually
						// entered quantity - VP - Infosys - 05-May-2011

						weight = (item.getAverageWeight() * totalCompletedQty) * 0.00220462262;

						// Begin - WO#25255 - Report weight processed for
						// manually entered quantity - VP - Infosys -
						// 05-May-2011
						// if returned average weight is 0, get the recent
						// average weight from previous activities
					} else {
						weight = 0.0;
						query = (String) userContext.getQueries().get(
								"SHOP_117");
						weight = itemDao.getRecentAverageWeight(partNumber,
								sequenceNumber, moNumber, "000", query);
						weight = (weight * totalCompletedQty) * 0.00220462262;
					}
				} else {
					// if there is no average weight of item is available, get
					// the recent average weight from previous activities
					weight = 0.0;
					query = (String) userContext.getQueries().get("SHOP_117");
					weight = itemDao.getRecentAverageWeight(partNumber,
							sequenceNumber, moNumber, "000", query);
					weight = (weight * totalCompletedQty) * 0.00220462262;
				}
				// End - WO#25255 - Report weight processed for manually entered
				// quantity - VP - Infosys - 05-May-2011
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return weight;
	}

	/* END. Sharepoint item 1441. Reporting of Setup and Run Times Incorrect */

	/**
	 * Gets the materials.
	 * 
	 * @param company
	 *            the company
	 * @param moNumber
	 *            the mo number
	 * @return the materials
	 */
	public List<Sequence> getMaterials(String company, String moNumber) {
		return PMS100MIReadUtil.getMaterials(company, moNumber, m3APIWSClient);
	}

	/**
	 * Gets the bOM list.
	 * 
	 * @param seq
	 *            the seq
	 * @return the bOM list
	 */
	public ArrayList<Sequence> getBOMList(Sequence seq) {
		return PMS100MIReadUtil.getOperation(seq, m3APIWSClient);
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