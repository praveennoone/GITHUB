/**
 * 
 */
package com.gavs.hishear.web.ajax;

import javax.servlet.http.HttpServletRequest;

import org.springmodules.xt.ajax.AbstractAjaxHandler;
import org.springmodules.xt.ajax.AjaxActionEvent;
import org.springmodules.xt.ajax.AjaxResponse;
import org.springmodules.xt.ajax.AjaxResponseImpl;
import org.springmodules.xt.ajax.action.ReplaceContentAction;
import org.springmodules.xt.ajax.component.SimpleText;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.SequenceDAO;
import com.gavs.hishear.web.domain.Sequence;

/**
 * 
 * @author rahjeshd
 * 
 */
public class QuantityInputFlagActionsHandler extends AbstractAjaxHandler {

	private SequenceDAO dao;
	private UserContext userContext;

	public AjaxResponse changeInputFlagStatus(AjaxActionEvent event) {
		HttpServletRequest request = event.getHttpRequest();
		String moNumber = request.getParameter("moNumber");
		String lineNumber = request.getParameter("lineNumber");
		String sequenceNumber = request.getParameter("sequence");
		String qtyRequired = request.getParameter("qtyRequired");

		Sequence sequence = new Sequence();
		sequence.setMoNumber(moNumber);
		sequence.setLineNumber(lineNumber);
		sequence.setSequence(sequenceNumber);
		boolean boolQtyRequired = new Boolean(qtyRequired).booleanValue();
		sequence.setInputRequired(boolQtyRequired);

		if (sequence.isInputRequired()) {
			sequence
					.setQuery((String) userContext.getQueries().get("SHOP_032"));
		} else {
			sequence
					.setQuery((String) userContext.getQueries().get("SHOP_033"));
		}

		dao.setInputFlag(sequence);

		AjaxResponse response = new AjaxResponseImpl();

		String strMessage = "";
		if (boolQtyRequired) {
			strMessage = "Input flag set for Sequence Number ";
		} else {
			strMessage = "Input flag cleared for Sequence Number ";
		}
		SimpleText message = new SimpleText(strMessage + sequenceNumber);
		response.addAction(new ReplaceContentAction("message", message));
		return response;
	}

	/**
	 * @return the dao
	 */
	public SequenceDAO getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(SequenceDAO dao) {
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
}