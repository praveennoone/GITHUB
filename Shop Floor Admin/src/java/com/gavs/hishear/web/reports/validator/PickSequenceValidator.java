// File:         PickSequenceValidator.java
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
import java.util.Iterator;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gavs.hishear.m3interface.M3APIWSClient;
import com.gavs.hishear.web.constants.Role;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.domain.Asset;
import com.gavs.hishear.web.domain.Facility;
import com.gavs.hishear.web.domain.Sequence;
import com.gavs.hishear.util.PMS100MIReadUtil;
import com.gavs.hishear.util.Util;

/**
 * The Class PickSequenceValidator.
 */
public class PickSequenceValidator implements Validator {

	/** The dao. */
	private SequenceDAO dao;

	/** The user context. */
	private UserContext userContext;

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
		return arg0.equals(Sequence.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	public void validate(Object arg0, Errors errors) {
		Sequence dto = (Sequence) arg0;
		dto.setMessage("");

		dto.setCompany(m3APIWSClient.getApplicationPropertyBean().getCompany());
		ArrayList bomList = null;
		ArrayList assets = null;
		String empID = "";

		bomList = PMS100MIReadUtil.getOperation(dto, m3APIWSClient);

		assets = (ArrayList) dao.getAssets((String) userContext.getQueries()
				.get("SHOP_015"), "", "");

		if (bomList != null && bomList.size() > 0) {
			if (bomList.size() == 1) {
				Sequence seq = (Sequence) bomList.get(0);
				if (seq.getErrorMessage() != null) {
					errors.rejectValue("moNumber", "invalidMOLine", Util
							.trimMessage(seq.getErrorMessage()));
					dto.setErrorOccured(true);
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
					validFacilities = dao.getFacilities(userContext
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
				errors.rejectValue("moNumber", "invalidFacility",
						"User has no access to this Facility");
				return;
			}
			boolean isOperationExists = false;
			for (int i = 0; i < bomList.size(); i++) {
				Sequence seq = (Sequence) bomList.get(i);
				if (seq.getSequence().trim().equals(dto.getSequence().trim())) {
					isOperationExists = true;
					dto.setStatus(seq.getStatus());
					dto.setPartNumber(seq.getPartNumber());
					dto.setFacility(seq.getFacility());
					dto.setOrderQty(seq.getOrderQty());
					dto.setWorkCenterCode(seq.getWorkCenterCode());
					dto.setPlanningArea(seq.getPlanningArea());

					break;
				}
			}
			if (!isOperationExists) {
				errors.rejectValue("moNumber", "invalidMOLine",
						"Invalid Operation Number ");
				dto.setErrorOccured(true);
				return;
			}
		}
		// }
		// }
		if ((bomList == null || bomList.size() == 0)) {
			errors.rejectValue("moNumber", "invalidMOLine",
					"Invalid MO/Sequence Number ");
			dto.setErrorOccured(true);
		} else if (dto.getStatus() != null && dto.getStatus().equals("90")) {

			errors.rejectValue("sequence", "completedSequence",
					"Sequence Already Completed");
			dto.setErrorOccured(true);
		} else if (isMorving(dto, bomList)) {// check if MORVING Sequence
			errors
					.rejectValue("moNumber",
							"thisSequenceShouldBeCompletedInShopFloorSystem",
							"Morving Sequence Should be Completed in Shop Floor System");
			return;
		} else if (dto.getPlanningArea().equalsIgnoreCase("REVIEW")) {

			errors.rejectValue("moNumber",
					"thisSequenceShouldBeCompletedInShopFloorSystem",
					"Review Should Be Completed In M3");
			return;
		} else if (dto.getPlanningArea().equalsIgnoreCase("SPLIT")) {

			errors.rejectValue("moNumber",
					"thisSequenceShouldBeCompletedInShopFloorSystem",
					"Split Should Be Completed In Shop Floor System");
			return;
		} else if (dto.getPlanningArea().equalsIgnoreCase("INSPECT")) {

			errors.rejectValue("moNumber",
					"thisSequenceShouldBeCompletedInShopFloorSystem",
					"Inspect Should Be Completed In Shop Floor System");
			return;
		} else if (dto.getPlanningArea().equalsIgnoreCase("OUTSIDE")) {

			errors.rejectValue("moNumber",
					"thisSequenceShouldBeCompletedInShopFloorSystem",
					"Outside Should Be Completed In Shop Floor System");
			return;
		}

		else if (dto.getOperation() != null
				&& "pick".equals(dto.getOperation())) {

			double qtyCompleted = dto.getQtyCompleted();

			System.out.println("assetsListSize=" + assets.size());

			boolean assetExists = isAssetAvailable(assets, dto.getAssetNumber());

			if (dto.getEmployeeID() != null) {
				empID = Role.PREFIX_ZERO + dto.getEmployeeID();
				System.out
						.println("employeeID 22222222222222222222 = " + empID);
				empID = empID.substring(empID.length() - 6, empID.length());
				if (empID.equals("000000")) {
					empID = "";
				}
				dto.setEmployeeID(empID);
			}

			if (dao.getUser(dto.getEmployeeID(), (String) userContext
					.getQueries().get("SHOP_001")) == null
					|| !assetExists) {
				errors.rejectValue("moNumber",
						"Invalid Asset[or]Employee Number",
						"Invalid Asset[or]Employee Number");
			}

			else if (qtyCompleted < dto.getCurSeqQtyCompleted()) {
				errors
						.rejectValue(
								"moNumber",
								"Total Pick Qty Cannot Be Lesser Than Already Completed Qty",
								"Total Pick Qty Cannot Be Lesser Than Already Completed Qty");
			}

		} else if (dto.getOperation() != null
				&& "addNew".equals(dto.getOperation())) {

			empID = dto.getEmployeeID();
			int length = empID.length();
			int balanceLength = 6 - length;
			String pad = "";
			for (int i = 0; i < balanceLength; i++) {
				pad += "0";
			}
			empID = pad + empID;
			if (empID.equals("000000")) {
				empID = "";
			}
			dto.setEmployeeID(empID);

			System.out.println("Asset Number===" + dto.getAssetNumber());
			boolean assetExists = isAssetAvailable(assets, dto.getAssetNumber());

			System.out.println("Employee id---" + empID);
			System.out.println("Is asset available" + assetExists);
			if (dao.getUser(empID, (String) userContext.getQueries().get(
					"SHOP_001")) == null
					|| !assetExists) {
				errors.rejectValue("moNumber",
						"Invalid Asset[or]Employee Number",
						"Invalid Asset[or]Employee Number");
			}

		}
	}

	/**
	 * Checks if is valid sequence.
	 * 
	 * @param bomList
	 *            the bom list
	 * @param sequence
	 *            the sequence
	 * @return true, if is valid sequence
	 */
	private boolean isValidSequence(List bomList, String sequence) {
		for (Iterator iter = bomList.iterator(); iter.hasNext();) {
			Sequence sequenc = (Sequence) iter.next();
			if (sequenc.getSequence().equals(sequence)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if is asset available.
	 * 
	 * @param assets
	 *            the assets
	 * @param assetNumber
	 *            the asset number
	 * @return true, if is asset available
	 */
	private boolean isAssetAvailable(ArrayList assets, String assetNumber) {
		System.out.println("Asset Number--->" + assetNumber);
		for (Iterator iter = assets.iterator(); iter.hasNext();) {
			Asset asset = (Asset) iter.next();
			if (asset.getAssetNumber().equals(assetNumber)) {
				return true;
			}
		}
		return false;

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

}
