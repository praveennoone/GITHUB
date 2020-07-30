package com.gavs.hishear.web.reports.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gavs.hishear.m3interface.M3APIWSClient;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.ItemDAO;
import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.domain.Sequence;
import com.gavs.hishear.util.PMS100MIReadUtil;

public class CurrentOrderDetailsValidator implements Validator {

	private ItemDAO itemDAO;
	private UserContext userContext;
	private SequenceDAO dao;

	private M3APIWSClient m3APIWSClient;

	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}

	public boolean supports(Class arg0) {
		return arg0.equals(Sequence.class);
	}

	public void validate(Object command, Errors errors) {
		Sequence dto = (Sequence) command;

		if (dto.getCriteria().equals("mo")) {
			dto.setUserFacility(dto.getMoFacility());

		} else {
			dto.setUserFacility(dto.getItemFacility());

		}

		dto.setFacility(dto.getUserFacility());

		String moFacility = PMS100MIReadUtil.getMOFacility(dto.getMoNumber(),
				m3APIWSClient);

		if (moFacility != null && dto.getFacility() != null
				&& !moFacility.equals(dto.getFacility())) {
			errors.rejectValue("moNumber", "facilityDoesNotMatch",
					"Selected Facility Does Not Match With MO's Facility");
		}

		dto.setErrorOccured(true);
		dto.setSequences(null);
	}

	public ItemDAO getItemDAO() {
		return itemDAO;
	}

	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

	/**
	 * @return the m3APIWSClient
	 */
	public M3APIWSClient getM3APIWSClient() {
		return m3APIWSClient;
	}

	/**
	 * @param client
	 *            the m3APIWSClient to set
	 */
	public void setM3APIWSClient(M3APIWSClient client) {
		m3APIWSClient = client;
	}

}
