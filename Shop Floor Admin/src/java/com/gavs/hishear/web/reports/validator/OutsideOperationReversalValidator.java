// File:         OutsideOperationReversalValidator.java
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
 * The Class OutsideOperationReversalValidator.
 */
public class OutsideOperationReversalValidator implements Validator {

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
		System.out.println("Validate Method Called-------------------");
		ReversalDto dto = (ReversalDto) command;

		Sequence sequence = new Sequence();
		sequence.setCompany(m3APIWSClient.getApplicationPropertyBean()
				.getCompany());
		sequence.setMoNumber(dto.getMoNumber());
		sequence.setSequence(dto.getOperationNumber());

		ArrayList bomList = PMS100MIReadUtil.getOperation(sequence,
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
					&& userContext != null && userContext.getDivision() != null) {
				// Begin WO# 27639 - Moving Static links to dynamic - Pinky -
				// Infosys - 23 June 2011
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
								&& facility.getFacilityId().equalsIgnoreCase(
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

			boolean isOperationExists = false;
			for (int i = 0; i < bomList.size(); i++) {
				Sequence seq = (Sequence) bomList.get(i);
				if (seq.getSequence().trim().equals(
						dto.getOperationNumber().trim())) {
					isOperationExists = true;
					dto.setPlanningArea(seq.getPlanningArea());

					break;
				}
			}
			if (!isOperationExists) {
				errors.rejectValue("moNumber", "errorMessage",
						"Invalid Operation Number ");
				return;
			}
		}

		if ((bomList == null || bomList.size() == 0)) {
			System.out
					.println("~~~~~~~~~~~~~~~~~~~  	bomList.size() == 0	  ~~~~~~~~~~~~~~~~~");
			errors.rejectValue("moNumber", "errorMessage",
					"Invalid MO/Sequence Number ");
			return;
		} else if (dto.getPlanningArea() == null
				|| !dto.getPlanningArea().equalsIgnoreCase("OUTSIDE")) {
			System.out
					.println("~~~~~~~~~~~~~~~~~~~ NOT 	OUTSIDE	  ~~~~~~~~~~~~~~~~~");
			errors.rejectValue("moNumber", "errorMessage",
					"The Selected operation Is Not An Outside Operation");
			return;
		}

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
			dto.setJobs(sequences);

			try {
				MVXDTAMI mvxdtami = sequenceDAO
						.getPurchaseOrderDetailsUsingMVXDTAMI(
								dto.getMoNumber(), dto.getOperationNumber());

				if (mvxdtami != null) {
					dto.setPoNumber(mvxdtami.getPurchaseOrderNumber());
					dto.setPoLineNumber(mvxdtami.getPurchaseOrderLineNo());
					dto
							.setPoSubLineNumber(mvxdtami
									.getPurchaseOrderSubLineNo());
					dto.setSupplier(mvxdtami.getSupplierNumber());
					dto.setItemNumber(mvxdtami.getProductNumber());
					dto.setWarehouse(mvxdtami.getWareHouse());

					// Read PNLX
					getPOLineSuffixForStatus51(dto, 51);

				}
			} catch (Exception e) {
				errors.rejectValue("moNumber", "errorMessage", e.getMessage());
				return;
			}

			dto.setDetailsExist(true);
		}

	}

	/**
	 * Gets the pO line suffix for status51.
	 * 
	 * @param dto
	 *            the dto
	 * @param status
	 *            the status
	 * @return the pO line suffix for status51
	 * @throws Exception
	 *             the exception
	 */
	private void getPOLineSuffixForStatus51(ReversalDto dto, int status)
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
				if (poTransaction.getStatus() == status) {
					transactionsBasedOnPOLineAndStatus.add(poTransaction);
				}
			}
		}

		if (maxStatus > 51) {
			throw new Exception(
					"Cannot Be Reversed. PO Transaction With Status "
							+ maxStatus + " Exists.");
		}

		if (transactionsBasedOnPOLineAndStatus.size() == 0) {
			throw new Exception("PO Transaction With Status 51 Does Not Exist");
		}

		if (transactionsBasedOnPOLineAndStatus.size() > 1) {
			throw new Exception(
					"Cannot Be Reversed. Multiple PO Transactions Exists With Status 51");
		}

		POTransaction poTransaction = transactionsBasedOnPOLineAndStatus.get(0);

		dto.setPoLineSuffix(poTransaction.getPoLineSuffix());
		dto.setPoStatus("" + status);
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
