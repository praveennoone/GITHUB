// File:         UpdateItemWeightActionsHandler.java
// Created:      Feb 23, 2011
// Author:       subhashri
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.ajax;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.HtmlUtils;
import org.springmodules.xt.ajax.AbstractAjaxHandler;
import org.springmodules.xt.ajax.AjaxActionEvent;
import org.springmodules.xt.ajax.AjaxResponse;
import org.springmodules.xt.ajax.AjaxResponseImpl;
import org.springmodules.xt.ajax.action.ExecuteJavascriptFunctionAction;
import org.springmodules.xt.ajax.action.ReplaceContentAction;
import org.springmodules.xt.ajax.component.Container;
import org.springmodules.xt.ajax.component.InputField;
import org.springmodules.xt.ajax.component.SimpleText;
import org.springmodules.xt.ajax.component.Table;
import org.springmodules.xt.ajax.component.TableData;
import org.springmodules.xt.ajax.component.TableRow;

import com.gavs.hishear.m3interface.M3APIWSClient;
import com.gavs.hishear.util.PMS070MIReadUtil;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.ItemDAO;
import com.gavs.hishear.web.domain.Item;
import com.gavs.hishear.web.domain.Sequence;


/**
 * The Class UpdateItemWeightActionsHandler.
 * 
 */
public class UpdateItemWeightActionsHandler extends AbstractAjaxHandler {

	/** The user context. */
	private UserContext userContext;

	/** The dao. */
	private ItemDAO dao;

	/** The m3 apiws client. */
	private M3APIWSClient m3APIWSClient;

	/**
	 * Gets the dao.
	 * 
	 * @return the dao
	 */
	public ItemDAO getDao() {
		return dao;
	}

	/**
	 * Sets the dao.
	 * 
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(ItemDAO dao) {
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

	/**
	 * Gets the filtered data.
	 * 
	 * @param event
	 *            the event
	 * @return the filtered data
	 */
	public AjaxResponse getFilteredData(AjaxActionEvent event) {
		AjaxResponse response = new AjaxResponseImpl();
		ReplaceContentAction replaceResult = null;
		Sequence sequence = new Sequence();
		HttpServletRequest request = event.getHttpRequest();
		String moNumber = request.getParameter("moNumber");

		String sequenceNumber = request.getParameter("sequence");
		Sequence item = null;

		if (moNumber != null && !moNumber.equals("")) {

			sequence.setMoNumber(moNumber);
			sequence.setSequence(sequenceNumber);
			sequence.setCompany(m3APIWSClient.getApplicationPropertyBean()
					.getCompany());

			try {

				ArrayList sequenceList = PMS070MIReadUtil.getOperation(
						sequence, m3APIWSClient);
				if (sequenceList != null && sequenceList.size() != 0) {
					item = (Sequence) sequenceList.get(0);
					System.out.println("item.getPartNumber()=="
							+ item.getPartNumber());
					System.out.println("item.getPartDesc()=="
							+ item.getPartDesc());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (item != null && item.getErrorMessage() == null) {
			Container container = new Container(Container.Type.DIV);
			container.addComponent(getItemDetails(item));
			System.out.println("item.getPartNumber()===>"
					+ item.getPartNumber());
			InputField itemNumber = new InputField("hiddenItemNo", item
					.getPartNumber(), InputField.InputType.HIDDEN);
			container.addComponent(itemNumber);
			replaceResult = new ReplaceContentAction("result", container);
			response.addAction(replaceResult);
			ExecuteJavascriptFunctionAction jsAction = new ExecuteJavascriptFunctionAction(
					"focusAvailableQty", new HashMap<String, Object>());
			response.addAction(jsAction);
		} else {
			replaceResult = new ReplaceContentAction("result", new SimpleText(
					"<font  color=\"red\" size=\"2\">No Records Found</font>"));
			response.addAction(replaceResult);
		}

		return response;
	}

	/**
	 * Gets the item details.
	 * 
	 * @param item
	 *            the item
	 * @return the item details
	 */
	public Table getItemDetails(Sequence item) {
		Table tableItemDetails = new Table();
		tableItemDetails.addAttribute("id", "normalTableNostripe");
		tableItemDetails.addAttribute("align", "center");
		tableItemDetails.addAttribute("width", "550px");

		TableData labeItemNumber = new TableData(new SimpleText(
				"<b>Item Number</b>"));
		TableData valueItemNumber = new TableData(new SimpleText(item
				.getPartNumber()));

		TableData labeItemDescription = new TableData(new SimpleText(
				"<b>Item Description</b>"));
		TableData valueItemDescription = new TableData(new SimpleText(HtmlUtils
				.htmlEscape(item.getPartDesc())));

		TableRow trItemDetails = new TableRow();
		trItemDetails.addTableData(labeItemNumber);
		trItemDetails.addTableData(valueItemNumber);
		trItemDetails.addTableData(labeItemDescription);
		trItemDetails.addTableData(valueItemDescription);

		tableItemDetails.addTableRow(trItemDetails);
		// /////////////////////////////////////////////////////////////////////////////////////
		Table tableItemWeight = new Table();
		tableItemWeight.addAttribute("id", "normalTableNostripe");
		tableItemWeight.addAttribute("align", "center");
		tableItemWeight.addAttribute("border", "0");
		tableItemWeight.addAttribute("width", "600px");
		TableData labeIAvailableQty = new TableData(new SimpleText(
				"<b>Available Quantity</b>"));

		InputField availableQty = new InputField("availableQty", "",
				InputField.InputType.TEXT);
		availableQty.addAttribute("onKeyDown",
				"javascript:callUpdateItemWeight(event);");
		availableQty.addAttribute("id", "availableQty");
		TableData txtAvailableQty = new TableData(availableQty);

		TableData labeITotalWeight = new TableData(new SimpleText(
				"<b>Total Weight (in grams)</b>"));

		InputField totalWeight = new InputField("totalWeight", "",
				InputField.InputType.TEXT);
		totalWeight.addAttribute("onKeyDown",
				"javascript:callUpdateItemWeight(event);");
		totalWeight.addAttribute("id", "totalWeight");
		TableData txtTotalWeight = new TableData(totalWeight);

		TableRow trInput = new TableRow();
		trInput.addTableData(labeIAvailableQty);
		trInput.addTableData(txtAvailableQty);
		trInput.addTableData(labeITotalWeight);
		trInput.addTableData(txtTotalWeight);

		String btnUpdate = "<img src=\"static/images/Update.gif\" border=\"0\" style=\"cursor: pointer;\" onclick=\"javascript:updateItemWeight()\"/>";
		String btnCancel = "<img src=\"static/images/cancel.gif\" border=\"0\" style=\"cursor: pointer;\" onclick=\"Cancel()\"/>";
		// This is to allign the buttons
		String dummyComponent = "<br/><br/><input type=\"text\" name=\"dummy\" size=\"50\" style=\"visibility:hidden\"/>";

		TableData tdButtons = new TableData(new SimpleText(dummyComponent
				+ btnUpdate + btnCancel));
		tdButtons.addAttribute("colspan", "4");
		tdButtons.addAttribute("valign", "middle");
		tdButtons.addAttribute("id", "buttonsDiv");

		TableRow trButtons = new TableRow();
		trButtons.addTableData(tdButtons);

		tableItemWeight.addTableRow(trInput);
		tableItemWeight.addTableRow(trButtons);

		Table table = new Table();

		TableData tItemDetails = new TableData(tableItemDetails);
		TableData tItemWeight = new TableData(tableItemWeight);

		TableRow trItem = new TableRow();
		trItem.addTableData(tItemDetails);
		TableRow trBlankSpace = new TableRow();
		trBlankSpace.addAttribute("height", "25px");
		TableRow trItemWeight = new TableRow();
		trItemWeight.addTableData(tItemWeight);

		table.addTableRow(trItem);
		table.addTableRow(trBlankSpace);
		table.addTableRow(trItemWeight);

		return table;
	}

	/**
	 * Update item weight.
	 * 
	 * @param event
	 *            the event
	 * @return the ajax response
	 */
	public AjaxResponse updateItemWeight(AjaxActionEvent event) {

		HttpServletRequest request = event.getHttpRequest();

		String moNumber = request.getParameter("moNumber");
		String itemNumber = request.getParameter("hiddenItemNo");
		String sequenceNumber = request.getParameter("sequence");
		Sequence sequence = new Sequence();
		sequence.setMoNumber(moNumber);
		sequence.setPartNumber(itemNumber);
		sequence.setSequence(sequenceNumber);
		// Added for M3 Integration

		Item item = null;
		String query = (String) userContext.getQueries().get("SHOP_017");// "Select ItemWeight From SFS_ItemWeight Where ItemNumber = ? And SequenceNumber = ?";
		sequence.setQuery(query);
		try {
			item = dao.checkItemWeight(sequence);
			if (item != null) {
				System.out.println("In Handler ------- item =" + item);
				System.out.println("In Handler ------- item Weight="
						+ item.getAverageWeight());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String availableQty = request.getParameter("availableQty");
		String totalWeight = request.getParameter("totalWeight");

		float itemWeight = 0;

		if (Integer.parseInt(availableQty) > 0) {
			itemWeight = (float) Float.parseFloat(totalWeight)
					/ Integer.parseInt(availableQty);
		}

		System.out.println("ItemWight=" + itemWeight);
		boolean updated = false;
		if (item == null) {
			try {
				sequence.setQuery((String) userContext.getQueries().get(
						"SHOP_005"));
				updated = dao.insertItemweight(itemWeight, sequence);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				sequence.setQuery((String) userContext.getQueries().get(
						"SHOP_006"));
				updated = dao.updateItemweight(itemWeight, sequence);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		AjaxResponse response = new AjaxResponseImpl();
		ReplaceContentAction replaceContentAction = null;
		ReplaceContentAction replaceButtonsAction = null;
		if (updated) {
			replaceContentAction = new ReplaceContentAction(
					"statusMessage",
					new SimpleText(
							"<font color=\"green\" size=\"3\">Updated Successfully</font>"));

			// Replace Update Button with Disabled Button Image
			String btnUpdate = "<img src=\"static/images/Update_disable.gif\" border=\"0\" style=\"cursor: pointer;\" />";
			String btnCancel = "<img src=\"static/images/cancel.gif\" border=\"0\" style=\"cursor: pointer;\" onclick=\"Cancel()\"/>";
			// This is to allign the buttons
			String dummyComponent = "<br/><br/><input type=\"text\" name=\"dummy\" size=\"50\" style=\"visibility:hidden\"/>";

			TableData tdButtons = new TableData(new SimpleText(dummyComponent
					+ btnUpdate + btnCancel));
			tdButtons.addAttribute("colspan", "4");
			tdButtons.addAttribute("valign", "middle");
			tdButtons.addAttribute("id", "buttonsDiv");

			replaceButtonsAction = new ReplaceContentAction("buttonsDiv",
					tdButtons);

		} else {
			replaceContentAction = new ReplaceContentAction(
					"statusMessage",
					new SimpleText(
							"<font color=\"red\" size=\"3\">Error While Updating</font>"));
		}
		response.addAction(replaceContentAction);
		response.addAction(replaceButtonsAction);
		return response;
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

}
