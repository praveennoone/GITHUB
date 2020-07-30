// File:         InspectionOperationReversalValidator.java
// Created:      Feb 23, 2011
// Author:       KamalB
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.reports.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gavs.hishear.m3interface.M3APIWSClient;
import com.gavs.hishear.m3interface.dto.MVXDTAMI;
import com.gavs.hishear.m3interface.dto.POTransaction;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.domain.Facility;
import com.gavs.hishear.web.domain.ReversalDto;
import com.gavs.hishear.web.domain.Sequence;
import com.gavs.hishear.util.PMS100MIReadUtil;
import com.gavs.hishear.util.Util;

/**
 * The Class InspectionOperationReversalValidator.
 */
public class InspectionOperationReversalValidator implements Validator {

	/** The user context. */
	private UserContext userContext;

	/** The sequence dao. */
	private SequenceDAO sequenceDAO;

	/** The m3 apiws client. */
	private M3APIWSClient m3APIWSClient;

	/**
	 * Sets the user context.
	 * 
	 * @param userContext
	 *            the new user context
	 */
	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	public boolean supports(Class arg0) {
		return arg0.equals(ReversalDto.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	public void validate(Object command, Errors errors) {
		System.out
				.println("InspectionOperationReversalValidator : Validate Method Called-------------------");
		ReversalDto dto = (ReversalDto) command;

		List<Sequence> sequences = sequenceDAO.getOperationDetails(dto
				.getMoNumber(), dto.getOperationNumber(), (String) userContext
				.getQueries().get("SHOP_148"));

		if (sequences == null || sequences.size() == 0) {
			errors.rejectValue("moNumber", "errorMessage", "No Data Found");
			return;
		}

		for (Sequence sequence2 : sequences) {
			if (sequence2 != null && sequence2.getSequenceStatus() != null
					&& !"C".equals(sequence2.getSequenceStatus())) {
				errors.rejectValue("moNumber", "errorMessage",
						"The Operation Has Not Been Completed");
				return;
			}
		}

		if (sequences != null && sequences.size() > 0) {

			Sequence tempSeq = new Sequence();
			tempSeq.setCompany(m3APIWSClient.getApplicationPropertyBean()
					.getCompany());
			tempSeq.setMoNumber(dto.getMoNumber());
			tempSeq.setSequence(dto.getOperationNumber());

			ArrayList bomList = PMS100MIReadUtil.getOperation(tempSeq,
					m3APIWSClient);

			if (bomList != null && bomList.size() > 0) {
				if (bomList.size() == 1) {
					Sequence seq = (Sequence) bomList.get(0);
					if (seq.getErrorMessage() != null) {
						errors.rejectValue("moNumber", "errorMessage", Util
								.trimMessage(seq.getErrorMessage()));
						return;
					}
				}

				boolean hasAccessToFacility = false;
				Sequence sequnce = (Sequence) bomList.get(0);
				if (sequnce != null && sequnce.getFacility() != null
						&& userContext != null
						&& userContext.getDivision() != null) {
					// Begin WO# 27639 - Moving Static links to dynamic - Pinky
					// - Infosys - 23 June 2011
					ArrayList<Facility> validFacilities = new ArrayList<Facility>();
					try {
						validFacilities = sequenceDAO.getFacilities(userContext
								.getDivision());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// End WO# 27639 - Moving Static links to dynamic - Pinky -
					// Infosys - 23 June 2011
					if (validFacilities != null) {
						for (Facility facility : validFacilities) {
							if (facility.getFacilityId() != null
									&& facility.getFacilityId()
											.equalsIgnoreCase(
													sequnce.getFacility())) {
								hasAccessToFacility = true;
							}
						}
					}
				}
				if (!hasAccessToFacility) {
					errors.rejectValue("moNumber", "errorMessage",
							"User has no access to this Facility");
					return;
				}

				Sequence previousSequence = null;
				Sequence secondPreviousSequence = null;
				boolean isOperationExists = false;
				for (int i = 0; i < bomList.size(); i++) {
					Sequence seq = (Sequence) bomList.get(i);

					int sequenceNumber = Integer.parseInt(seq.getSequence()
							.trim());

					if (previousSequence == null) {
						previousSequence = seq;
					} else {
						if (sequenceNumber > Integer.parseInt(previousSequence
								.getSequence().trim())
								&& sequenceNumber < Integer
										.parseInt(previousSequence
												.getSequence().trim())) {
							previousSequence = seq;
						}
					}
					if (secondPreviousSequence == null) {
						secondPreviousSequence = seq;
					} else {
						if (sequenceNumber > Integer
								.parseInt(secondPreviousSequence.getSequence()
										.trim())
								&& sequenceNumber < Integer.parseInt(dto
										.getOperationNumber().trim())) {
							previousSequence = seq;
						}
					}
					if (seq.getSequence().trim().equals(
							dto.getOperationNumber().trim())) {
						isOperationExists = true;
						dto.setPlanningArea(seq.getPlanningArea());
						dto.setPriceTimeQty(seq.getPriceTimeQty());
					}
				}
				if (!isOperationExists) {
					errors.rejectValue("moNumber", "errorMessage",
							"Invalid Operation Number ");
					return;
				}

				if (!(dto.getPriceTimeQty() == 1000)) {
					errors.rejectValue("moNumber", "errorMessage",
							"Price Time Qty Is Not Set To 1000");
					return;
				}

				System.out.println("Previous Planning Area: "
						+ previousSequence.getPlanningArea());
				System.out.println("Second Previous Planning Area: "
						+ secondPreviousSequence.getPlanningArea());

				if ("OUTSIDE".equals(previousSequence.getPlanningArea())) {
					dto.setInspectionForOutsideOperation(true);
					dto.setOutsideOperationNumber(previousSequence
							.getSequence());
				} else if ("SPLIT".equals(previousSequence.getPlanningArea())
						&& "OUTSIDE".equals(secondPreviousSequence
								.getPlanningArea())) {
					dto.setInspectionForOutsideOperation(true);
					dto.setOutsideOperationNumber(secondPreviousSequence
							.getSequence());
				}
			}

			if ((bomList == null || bomList.size() == 0)) {
				System.out
						.println("~~~~~~~~~~~~~~~~~~~  	bomList.size() == 0	  ~~~~~~~~~~~~~~~~~");
				errors.rejectValue("moNumber", "errorMessage",
						"Invalid MO/Sequence Number ");
				return;
			} else if (dto.getPlanningArea() == null
					|| !dto.getPlanningArea().equalsIgnoreCase("INSPECT")) {
				System.out
						.println("~~~~~~~~~~~~~~~~~~~ NOT 	OUTSIDE	  ~~~~~~~~~~~~~~~~~");
				errors
						.rejectValue("moNumber", "errorMessage",
								"The Selected operation Is Not An Inspection Operation");
				return;
			}

			dto.setJobs(sequences);

			if (sequences != null && sequences.size() > 0) {
				dto.setWorkCenter(sequences.get(0).getWorkCenterCode());
			}

			if (dto.getWorkCenter() == null) {
				errors.rejectValue("moNumber", "errorMessage",
						"Work Center Not Available");
				return;
			} else {
				try {
					dto.setWorkCenterCapacity(sequenceDAO
							.getWorkCenterCapacity(dto.getWorkCenter(), dto
									.getMoNumber()));
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (!(dto.getWorkCenterCapacity() == 1)) {
					errors.rejectValue("moNumber", "errorMessage",
							"Work Center Capacity Is Not Set To 1");
					return;
				}
			}

			if (dto.isInspectionForOutsideOperation()) {
				try {
					MVXDTAMI mvxdtami = sequenceDAO
							.getPurchaseOrderDetailsUsingMVXDTAMI(dto
									.getMoNumber(), dto
									.getOutsideOperationNumber());

					if (mvxdtami != null) {
						dto.setPoNumber(mvxdtami.getPurchaseOrderNumber());
						dto.setPoLineNumber(mvxdtami.getPurchaseOrderLineNo());
						dto.setPoSubLineNumber(mvxdtami
								.getPurchaseOrderSubLineNo());
						dto.setSupplier(mvxdtami.getSupplierNumber());
						dto.setItemNumber(mvxdtami.getProductNumber());
						dto.setWarehouse(mvxdtami.getWareHouse());

						// Read PNLX
						getPOLineSuffixForStatus64Or65(dto);

					}
				} catch (Exception e) {
					errors.rejectValue("moNumber", "errorMessage", e
							.getMessage());
					return;
				}
			} else {
				errors.rejectValue("moNumber", "errorMessage",
						"Previous Operation Is Not An Outside Operation");
				return;
			}

			dto.setDetailsExist(true);
		}

	}

	/**
	 * Gets the pO line suffix for status64 or65.
	 * 
	 * @param dto
	 *            the dto
	 * @return the pO line suffix for status64 or65
	 * @throws Exception
	 *             the exception
	 */
	private void getPOLineSuffixForStatus64Or65(ReversalDto dto)
			throws Exception {
		List<POTransaction> transactionsBasedOnPO = sequenceDAO
				.getPOTransactions(dto.getPoNumber());

		List<POTransaction> transactionsBasedOnPOLineAndStatus = new ArrayList<POTransaction>();

		int maxStatus = 0;

		for (POTransaction poTransaction : transactionsBasedOnPO) {
			// Line number match
			if (dto.getPoLineNumber().equals(poTransaction.getPoLineNumber())) {

				if (poTransaction.getStatus() > maxStatus) {
					maxStatus = poTransaction.getStatus();
				}
				if (poTransaction.getStatus() == 64
						|| poTransaction.getStatus() == 65) {
					transactionsBasedOnPOLineAndStatus.add(poTransaction);
				}
			}
		}

		if (maxStatus > 65) {
			throw new Exception(
					"Cannot Be Reversed. PO Transaction With Status "
							+ maxStatus + " Exists.");
		}

		if (transactionsBasedOnPOLineAndStatus.size() == 0) {
			throw new Exception(
					"PO Transaction With Status 64 Or 65 Does Not Exist");
		}

		if (transactionsBasedOnPOLineAndStatus.size() > 1) {
			throw new Exception(
					"Cannot Be Reversed. Multiple PO Transactions Exists With Status 64 Or 65");
		}

		POTransaction poTransaction = transactionsBasedOnPOLineAndStatus.get(0);

		dto.setPoLineSuffix(poTransaction.getPoLineSuffix());
		dto.setPoStatus("" + poTransaction.getStatus());
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
	 * Gets the sequence dao.
	 * 
	 * @return the sequence dao
	 */
	public SequenceDAO getSequenceDAO() {
		return sequenceDAO;
	}

	/**
	 * Sets the sequence dao.
	 * 
	 * @param sequenceDAO
	 *            the new sequence dao
	 */
	public void setSequenceDAO(SequenceDAO sequenceDAO) {
		this.sequenceDAO = sequenceDAO;
	}

}
