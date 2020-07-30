package com.gavs.hishear.web.ajax;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springmodules.xt.ajax.AbstractAjaxHandler;
import org.springmodules.xt.ajax.AjaxActionEvent;
import org.springmodules.xt.ajax.AjaxResponse;
import org.springmodules.xt.ajax.AjaxResponseImpl;
import org.springmodules.xt.ajax.action.ReplaceContentAction;
import org.springmodules.xt.ajax.component.SimpleText;
import org.springmodules.xt.ajax.component.Table;
import org.springmodules.xt.ajax.component.TableData;
import org.springmodules.xt.ajax.component.TableHeader;
import org.springmodules.xt.ajax.component.TableRow;

import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.dao.ItemDAO;
import com.gavs.hishear.web.domain.Item;

/**
 * 
 * @author kamalb
 * 
 */
public class ComponentDetailActionsHandler extends AbstractAjaxHandler {

	private static final String SPACE = " ";

	private ItemDAO dao;

	private UserContext userContext;

	public AjaxResponse getComponentDetails(AjaxActionEvent event) {
		final String INITIAL_MESSAGE = "Inventory Qty Is Less Than Required Qty For ";

		HttpServletRequest request = event.getHttpRequest();
		String moNumber = request.getParameter("moNumber");
		String lineNumber = request.getParameter("lineNumber");

		Item item = (Item) event.getCommandObject();
		item.setMoNumber(moNumber);
		item.setLineNumber(lineNumber);

		dao.getItemDetails(item, (String) userContext.getQueries().get(
				"SHOPR_082"));

		AjaxResponse response = new AjaxResponseImpl();

		if (item.getItemNumber() == null) {
			ReplaceContentAction action = new ReplaceContentAction(
					"statusMessage", new SimpleText(
							"<b><font color=\"red\" size=\"3px\">"
									+ "Invalid MO Number/Line Number"
									+ "</font></b>"));
			response.addAction(action);
			return response;
		}

		Table headerTable = new Table();

		headerTable.addAttribute("align", "center");
		headerTable.addAttribute("width", "67%");

		TableRow row = new TableRow();
		TableData itemNumberLabel = new TableData(new SimpleText("Item Number"));
		row.addTableData(itemNumberLabel);
		itemNumberLabel.addAttribute("class", "stripe");
		TableData itemNumber = new TableData(new SimpleText(item
				.getItemNumber()));
		itemNumber.addAttribute("style",
				"white-space: nowrap; word-spacing: normal;");
		row.addTableData(itemNumber);

		TableData itemDescriptionLabel = new TableData(new SimpleText(
				"Item Description"));
		row.addTableData(itemDescriptionLabel);
		itemDescriptionLabel.addAttribute("class", "stripe");
		TableData itemDescription = new TableData(new SimpleText(item
				.getItemDescription()));
		itemDescription.addAttribute("style",
				"white-space: nowrap; word-spacing: normal;");
		row.addTableData(itemDescription);

		TableData orderedQtyLabel = new TableData(new SimpleText("Ordered Qty"));
		row.addTableData(orderedQtyLabel);
		orderedQtyLabel.addAttribute("class", "stripe");
		TableData orderedQty = new TableData(new SimpleText(""
				+ item.getItemOrderedQuantity()));
		orderedQty.addAttribute("style",
				"white-space: nowrap; word-spacing: normal;");
		row.addTableData(orderedQty);

		headerTable.addTableRow(row);
		headerTable.addAttribute("id", "verticalStripedTable");

		ReplaceContentAction action = new ReplaceContentAction("headerDiv",
				headerTable);
		response.addAction(action);

		List componentDetails = dao.getParentAndComponentDetails(item
				.getItemNumber(), (String) userContext.getQueries().get(
				"ORAL_049"));

		Table table = new Table();
		String[] headers = new String[] { "Component Part #",
				"Part Description", "Lot Number", "Location", "Inventory Qty" };
		TableHeader tableHeader = new TableHeader(headers);
		tableHeader.addAttribute("class", "containerTableTitle");
		tableHeader.addAttribute("style", "position: relative");

		table.addTableHeaderAttribute("class", "fixedHeader");
		table.addAttribute("id", "normalTableNostripe");
		table.addAttribute("align", "center");
		table.addAttribute("width", "97%");
		table.setTableHeader(tableHeader);

		if (componentDetails != null) {

			boolean isParent = false;
			for (Iterator iter = componentDetails.iterator(); iter.hasNext();) {
				Item itemObj = (Item) iter.next();
				if (item.getItemNumber().equals(itemObj.getItemNumber())
						&& "P".equals(itemObj.getItemType())) {
					isParent = true;
				}
			}

			if (!isParent) {
				action = new ReplaceContentAction("statusMessage",
						new SimpleText("<b><font color=\"red\" size=\"3px\">"
								+ "This Item Is Not A Parent Part"
								+ "</font></b>"));
				response.addAction(action);
				return response;
			}

			if (componentDetails.size() < 3) {
				action = new ReplaceContentAction("statusMessage",
						new SimpleText("<b><font color=\"red\" size=\"3px\">"
								+ "This Item Does Not Contain 2 Compoments"
								+ "</font></b>"));
				response.addAction(action);
				return response;
			}

			String errorMessage = INITIAL_MESSAGE;
			for (Iterator iter = componentDetails.iterator(); iter.hasNext();) {
				Item itemObj = (Item) iter.next();
				if ("C".equals(itemObj.getItemType())) {
					List inventoryDetails = dao.getInventoryQuantity(itemObj
							.getItemNumber(), (String) userContext.getQueries()
							.get("ORAL_051"));
					if (inventoryDetails != null) {
						boolean hasSufficientQty = false;
						String chosenLot = null;
						String chosenLocation = null;
						int chosenQty = 0;
						for (Iterator iterator = inventoryDetails.iterator(); iterator
								.hasNext();) {
							Item inventoryItem = (Item) iterator.next();
							if (inventoryItem.getQuantity() >= item
									.getItemOrderedQuantity()) {
								hasSufficientQty = true;
								chosenLot = inventoryItem.getLotNumber();
								chosenLocation = inventoryItem.getLocation();
								chosenQty = inventoryItem.getQuantity();
								break;
							} else {
								chosenLot = inventoryItem.getLotNumber();
								chosenLocation = inventoryItem.getLocation();
								chosenQty = inventoryItem.getQuantity();
							}
						}

						if (!hasSufficientQty) {
							if (!INITIAL_MESSAGE.equals(errorMessage)) {
								errorMessage += ", ";
							}
							errorMessage += itemObj.getItemNumber();
						}

						row = new TableRow();
						itemNumber = new TableData(new SimpleText(itemObj
								.getItemNumber()));
						itemNumber.addAttribute("class", "tableContent");
						itemNumber.addAttribute("style",
								"white-space: nowrap; word-spacing: normal;");

						itemDescription = new TableData(new SimpleText(itemObj
								.getItemDescription()));
						itemDescription.addAttribute("class", "tableContent");
						itemDescription.addAttribute("style",
								"white-space: nowrap; word-spacing: normal;");

						TableData lotNumber = new TableData(new SimpleText(
								StringUtils.defaultString(chosenLot, SPACE)));
						lotNumber.addAttribute("class", "tableContent");
						lotNumber.addAttribute("style",
								"white-space: nowrap; word-spacing: normal;");
						TableData location = new TableData(new SimpleText(
								StringUtils
										.defaultString(chosenLocation, SPACE)));
						location.addAttribute("class", "tableContent");
						location.addAttribute("style",
								"white-space: nowrap; word-spacing: normal;");

						TableData quantity = new TableData(new SimpleText(""
								+ chosenQty));
						quantity.addAttribute("class", "tableContent");
						quantity
								.addAttribute("style",
										"text-align: right;white-space: nowrap; word-spacing: normal;");

						row.addTableData(itemNumber);
						row.addTableData(itemDescription);
						row.addTableData(lotNumber);
						row.addTableData(location);
						row.addTableData(quantity);

						table.addTableRow(row);

					} else {
						action = new ReplaceContentAction(
								"statusMessage",
								new SimpleText(
										"<b><font color=\"red\" size=\"3px\">"
												+ "Inventory Not Available For "
												+ itemObj.getItemNumber()
												+ "</font></b>"));
						response.addAction(action);
					}
				}
			}

			if (errorMessage != null) {
				action = new ReplaceContentAction("statusMessage",
						new SimpleText("<b><font color=\"red\" size=\"3px\">"
								+ errorMessage + "</font></b>"));
				response.addAction(action);
			}
		}

		action = new ReplaceContentAction("detailsDiv", table);
		response.addAction(action);
		return response;
	}

	/**
	 * @return the dao
	 */
	public ItemDAO getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(ItemDAO dao) {
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