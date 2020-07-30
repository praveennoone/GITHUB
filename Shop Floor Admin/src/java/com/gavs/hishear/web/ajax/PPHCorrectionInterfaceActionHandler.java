// File:         PPHCorrectionInterfaceActionHandler.java
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
import java.util.List;

import org.springframework.web.util.HtmlUtils;
import org.springmodules.xt.ajax.AjaxResponse;
import org.springmodules.xt.ajax.AjaxResponseImpl;
import org.springmodules.xt.ajax.AjaxSubmitEvent;
import org.springmodules.xt.ajax.action.ExecuteJavascriptFunctionAction;
import org.springmodules.xt.ajax.action.ReplaceContentAction;
import org.springmodules.xt.ajax.component.Anchor;
import org.springmodules.xt.ajax.component.Container;
import org.springmodules.xt.ajax.component.Image;
import org.springmodules.xt.ajax.component.SimpleText;
import org.springmodules.xt.ajax.component.Table;
import org.springmodules.xt.ajax.component.TableData;
import org.springmodules.xt.ajax.component.TableHeader;
import org.springmodules.xt.ajax.component.TableHeaderData;
import org.springmodules.xt.ajax.component.TableRow;

import com.gavs.hishear.web.domain.Activity;
import com.gavs.hishear.web.domain.ManufacturingOrder;
import com.gavs.hishear.web.domain.Sequence;

/**
 * The Class PPHCorrectionInterfaceActionHandler.
 * 
 */
public class PPHCorrectionInterfaceActionHandler extends BaseAjaxHandler {

	/**
	 * Gets the error activities.
	 * 
	 * @param event
	 *            the event
	 * @return the error activities
	 */
	public AjaxResponse getErrorActivities(AjaxSubmitEvent event) {
		AjaxResponse response = new AjaxResponseImpl();
		ManufacturingOrder dto = (ManufacturingOrder) event.getCommandObject();

		if (dto.getErrorActivities() != null
				&& dto.getErrorActivities().size() > 0) {
			buildErrorLogDetails(response, dto);
		} else {
			ExecuteJavascriptFunctionAction showNoDataFoundErrorAction = new ExecuteJavascriptFunctionAction(
					"showNoDataMessage", new HashMap<String, Object>());
			response.addAction(showNoDataFoundErrorAction);
		}
		return response;
	}

	/**
	 * Confirm sequence.
	 * 
	 * @param event
	 *            the event
	 * @return the ajax response
	 */
	public AjaxResponse confirmSequence(AjaxSubmitEvent event) {
		AjaxResponse response = new AjaxResponseImpl();
		ManufacturingOrder dto = (ManufacturingOrder) event.getCommandObject();
		if (dto.getError() != null) {
			ReplaceContentAction replaceContentAction = new ReplaceContentAction(
					"statusMessage", new SimpleText(
							"<font color=\"red\" size=\"2px\"><b> "
									+ dto.getError() + "</b></font>"));
			ReplaceContentAction replaceButtonsAction = new ReplaceContentAction(
					"buttonsDiv", new SimpleText(" "));
			response.addAction(replaceContentAction);
			response.addAction(replaceButtonsAction);
			buildErrorLogDetailsForConfirm(response, dto);
		} else {
			ReplaceContentAction replaceContentAction = new ReplaceContentAction(
					"statusMessage",
					new SimpleText(
							"<font color=\"green\" size=\"2px\">Updated Successfully</font>"));
			ReplaceContentAction replaceButtonsAction = new ReplaceContentAction(
					"buttonsDiv", new SimpleText(" "));
			response.addAction(replaceContentAction);
			response.addAction(replaceButtonsAction);
			buildErrorLogDetailsForConfirm(response, dto);
		}
		return response;
	}

	/**
	 * Builds the error log details for confirm.
	 * 
	 * @param response
	 *            the response
	 * @param dto
	 *            the dto
	 */
	private void buildErrorLogDetailsForConfirm(AjaxResponse response,
			ManufacturingOrder dto) {
		if (isDataFoundToDisplay(dto)) {
			ReplaceContentAction action = null;

			Container containerTotal = new Container(Container.Type.DIV);
			containerTotal.addAttribute("align", "center");
			containerTotal.addAttribute("style", "width: 920px;");

			Container container = new Container(Container.Type.DIV);
			container.addAttribute("style",
					"width: 920px; overflow: auto; height: 159px");

			ReplaceContentAction m3ItemAction = null;
			Container m3ItemContainer = new Container(Container.Type.DIV);
			m3ItemContainer.addAttribute("style", "width: 920px");
			m3ItemContainer.addAttribute("align", "center");
			m3ItemContainer.addComponent(buildM3ItemTable(dto));
			m3ItemAction = new ReplaceContentAction("tableM3ItemDetails",
					m3ItemContainer);
			response.addAction(m3ItemAction);

			container.addComponent(buildErrorLogTable(dto));

			containerTotal.addComponent(getHeaderTable());
			containerTotal.addComponent(container);
			action = new ReplaceContentAction("errorLogDetailsDiv",
					containerTotal);
			response.addAction(action);
		} else {
			ReplaceContentAction m3ItemAction = new ReplaceContentAction(
					"tableM3ItemDetails", new SimpleText(""));
			ReplaceContentAction action = new ReplaceContentAction(
					"errorLogDetailsDiv", new SimpleText(""));
			ReplaceContentAction errorMODiv = new ReplaceContentAction(
					"errorMODiv", buildErrorMODiv(dto));
			response.addAction(errorMODiv);
			response.addAction(action);
			response.addAction(m3ItemAction);
		}
	}

	/**
	 * Builds the error log details.
	 * 
	 * @param response
	 *            the response
	 * @param dto
	 *            the dto
	 */
	private void buildErrorLogDetails(AjaxResponse response,
			ManufacturingOrder dto) {
		if (isDataFoundToDisplay(dto)) {
			ReplaceContentAction action = null;

			Container containerTotal = new Container(Container.Type.DIV);
			containerTotal.addAttribute("align", "center");
			containerTotal.addAttribute("style", "width: 920px;");

			Container container = new Container(Container.Type.DIV);
			container.addAttribute("style",
					"width: 920px; overflow: auto; height: 159px");

			ReplaceContentAction m3ItemAction = null;
			Container m3ItemContainer = new Container(Container.Type.DIV);
			m3ItemContainer.addAttribute("style", "width: 920px");
			m3ItemContainer.addAttribute("align", "center");
			m3ItemContainer.addComponent(buildM3ItemTable(dto));
			m3ItemAction = new ReplaceContentAction("tableM3ItemDetails",
					m3ItemContainer);
			response.addAction(m3ItemAction);

			container.addComponent(buildErrorLogTable(dto));

			containerTotal.addComponent(getHeaderTable());
			containerTotal.addComponent(container);
			action = new ReplaceContentAction("errorLogDetailsDiv",
					containerTotal);
			response.addAction(action);
		} else {
			ReplaceContentAction m3ItemAction = new ReplaceContentAction(
					"tableM3ItemDetails", new SimpleText(""));
			ReplaceContentAction action = new ReplaceContentAction(
					"errorLogDetailsDiv", new SimpleText(""));
			ReplaceContentAction errorMODiv = new ReplaceContentAction(
					"errorMODiv", buildErrorMODiv(dto));
			ReplaceContentAction messageAction = new ReplaceContentAction(
					"statusMessage",
					new SimpleText(
							"<font color=\"red\" size=\"2px\"><b>No Data Found</b></font>"));
			response.addAction(errorMODiv);
			response.addAction(action);
			response.addAction(m3ItemAction);
			response.addAction(messageAction);
		}
	}

	/**
	 * Builds the error mo div.
	 * 
	 * @param dto
	 *            the dto
	 * @return the simple text
	 */
	public SimpleText buildErrorMODiv(ManufacturingOrder dto) {
		SimpleText simpleText = new SimpleText("");
		String selectText = "<select  name='moNumber' style='width:200px;margin:5px 0 5px 0;font: small-caps 900 10px arial;color:#1A4A6F;' size='5'>";
		String optionText = "";

		if (dto.getErrorMos() != null && dto.getErrorMos().size() > 0) {
			for (Sequence seq : dto.getErrorMos()) {
				optionText = optionText + "<option value='" + seq.getMoNumber()
						+ "'>" + seq.getMoNumber() + "</option>";
			}
		}
		optionText = optionText + "</select>";
		simpleText = new SimpleText(selectText + optionText);

		return simpleText;
	}

	/**
	 * Builds the m3 item table.
	 * 
	 * @param dto
	 *            the dto
	 * @return the table
	 */
	public Table buildM3ItemTable(ManufacturingOrder dto) {
		Table innerTable1 = new Table();
		innerTable1.addAttribute("id", "outerTableNostripe");
		innerTable1.addAttribute("align", "center");
		innerTable1.addAttribute("cellSpacing", "0");
		innerTable1.addAttribute("cellPadding", "0");
		innerTable1.addAttribute("border", "1");
		innerTable1
				.addAttribute(
						"style",
						"white-space: nowrap; word-spacing: normal; width: 900px; height:30px; border-left: 1px solid #465272; border-right: 1px solid #465272; border-top: 1px solid #465272; border-bottom: 1px solid #465272;");
		TableRow innerRow1 = new TableRow();
		TableData facility = new TableData(new SimpleText(HtmlUtils
				.htmlEscape(validateDisplayField("Facility : "
						+ dto.getFacility()))));
		facility.addAttribute("style", "width: 175px; font-weight: bold");
		TableData itemNumber = new TableData(new SimpleText(HtmlUtils
				.htmlEscape(validateDisplayField("M3 ItemNumber : "
						+ dto.getPartNumber()))));
		itemNumber.addAttribute("style", "width: 170px; font-weight: bold");
		TableData partName = new TableData(new SimpleText(HtmlUtils
				.htmlEscape(validateDisplayField("M3 Part Name : "
						+ dto.getPartDescription()))));
		partName.addAttribute("style", "width: 180px; font-weight: bold");
		innerRow1.addTableData(facility);
		innerRow1.addTableData(itemNumber);
		innerRow1.addTableData(partName);
		innerTable1.addTableRow(innerRow1);
		return innerTable1;
	}

	/**
	 * Gets the header table.
	 * 
	 * @return the header table
	 */
	private Table getHeaderTable() {
		TableHeaderData moNumberHeaderData = new TableHeaderData(
				new SimpleText("MO#"));
		moNumberHeaderData.addAttribute("style", "width: 6%");
		TableHeaderData sequenceNumberHeaderData = new TableHeaderData(
				new SimpleText("Sequence#"));
		sequenceNumberHeaderData.addAttribute("style", "width: 8%");
		TableHeaderData operationDescHeaderData = new TableHeaderData(
				new SimpleText("Operation Description"));
		operationDescHeaderData.addAttribute("style", "width: 16%");
		// START - WO 25251 - PPH Review
		// TableHeaderData activityHeaderData = new TableHeaderData(
		// new SimpleText("Activity"));
		// activityHeaderData.addAttribute("style", "width: 7%");
		// TableHeaderData employeeIDHeaderData = new TableHeaderData(
		// new SimpleText("Employee ID"));
		// employeeIDHeaderData.addAttribute("style", "width: 8%");
		// END - WO 25251 - PPH Review
		TableHeaderData qtyCompletedHeaderData = new TableHeaderData(
				new SimpleText("Qty Completed"));
		qtyCompletedHeaderData.addAttribute("style", "width: 9%");
		// TableHeaderData logOnHeaderData = new TableHeaderData(new
		// SimpleText("Logon Date/Time"));
		// logOnHeaderData.addAttribute("style", "width: 18%");
		// TableHeaderData logOffHeaderData = new TableHeaderData(new
		// SimpleText("Logoff Date/Time"));
		// logOffHeaderData.addAttribute("style", "width: 18%");
		// TableHeaderData standardTimeHeaderData = new TableHeaderData(new
		// SimpleText("Standard Time"));
		// standardTimeHeaderData.addAttribute("style", "width: 10%");

		TableHeaderData runHoursData = new TableHeaderData(new SimpleText(
				"Run Hours"));
		runHoursData.addAttribute("style", "width: 10%");

		// TableHeaderData minsProcessedData = new TableHeaderData(new
		// SimpleText("Minutes Processed"));
		// minsProcessedData.addAttribute("style", "width: 13%");

		TableHeaderData actualPPHData = new TableHeaderData(new SimpleText(
				"Actual PPH"));
		actualPPHData.addAttribute("style", "width: 13%");

		TableHeaderData stdPPHData = new TableHeaderData(new SimpleText(
				"Std PPH"));
		stdPPHData.addAttribute("style", "width: 8%");

		TableHeaderData editHeaderData = new TableHeaderData(new SimpleText(
				"Edit"));
		editHeaderData.addAttribute("style", "width: 8%");
		editHeaderData.addAttribute("align", "center");

		TableHeaderData confirmHeaderData = new TableHeaderData(new SimpleText(
				"Confirm"));
		confirmHeaderData.addAttribute("style", "width: 8%");
		confirmHeaderData.addAttribute("align", "center");
		/*
		 * InputField chkBoxEdit = new InputField("edit", "Edit",
		 * InputType.CHECKBOX); chkBoxEdit.addAttribute("id", "editAll");
		 * chkBoxEdit.addAttribute("checked", "checked");
		 * chkBoxEdit.addAttribute("name", "editAll");
		 * chkBoxEdit.addAttribute("onclick", "editAll()");
		 * 
		 * Container minutesProcessedDiv = new Container(Type.DIV);
		 * minutesProcessedDiv.addComponent(new SimpleText("Edit   "));
		 * 
		 * minutesProcessedDiv.addComponent(chkBoxEdit);
		 * 
		 * TableHeaderData editHeaderData = new
		 * TableHeaderData(minutesProcessedDiv);
		 * editHeaderData.addAttribute("style", "width: 8%");
		 */

		ArrayList<TableHeaderData> headers = new ArrayList<TableHeaderData>();
		headers.add(moNumberHeaderData);
		headers.add(sequenceNumberHeaderData);
		headers.add(operationDescHeaderData);
		// START - WO 25251 - PPH Review
		// headers.add(activityHeaderData);
		// headers.add(employeeIDHeaderData);
		// END - WO 25251 - PPH Review
		headers.add(qtyCompletedHeaderData);
		// headers.add(logOnHeaderData);
		// headers.add(logOffHeaderData);
		headers.add(runHoursData);
		headers.add(actualPPHData);
		headers.add(stdPPHData);
		headers.add(editHeaderData);
		headers.add(confirmHeaderData);
		TableHeader tableHeader = new TableHeader(headers);
		tableHeader.addAttribute("style", "text-align: center;");
		Table table = new Table();
		table.setTableHeader(tableHeader);
		table.addAttribute("id", "normalTableNostripe");
		table.addAttribute("align", "left");
		table.addAttribute("cellSpacing", "0");
		table.addAttribute("cellPadding", "0");
		table.addAttribute("border", "1");
		table.addAttribute("class", "classContentTable");
		table.addAttribute("style",
				"white-space: nowrap; word-spacing: normal; width: 95%");
		table.addTableHeaderAttribute("class", "fixedHeader");
		table.addTableBodyAttribute("class", "scrollContent");
		return table;
	}

	/**
	 * Builds the error log table.
	 * 
	 * @param manufacturingOrder
	 *            the manufacturing order
	 * @return the table
	 */
	public Table buildErrorLogTable(ManufacturingOrder manufacturingOrder) {
		Table table = new Table();
		table.addAttribute("id", "normalTableNostripe");
		table.addAttribute("align", "left");
		table.addAttribute("cellSpacing", "0");
		table.addAttribute("cellPadding", "0");
		table.addAttribute("border", "1");
		table.addAttribute("class", "classContentTable");
		table.addAttribute("style",
				"white-space: nowrap; word-spacing: normal; width: 95%");
		table.addTableHeaderAttribute("class", "fixedHeader");
		table.addTableBodyAttribute("class", "scrollContent");

		if (manufacturingOrder.getErrorActivities() != null
				&& manufacturingOrder.getErrorActivities().size() > 0) {

			for (Activity seq : manufacturingOrder.getErrorActivities()) {
				if (!seq.isReported()) {
					TableRow row = new TableRow();

					TableData moNumber = new TableData(new SimpleText(HtmlUtils
							.htmlEscape(validateDisplayField(manufacturingOrder
									.getMoNumber()))));
					moNumber.addAttribute("style", "width: 6%; height: 10px ");
					if (seq.getErrorMessage() != null) {
						moNumber.addAttribute("onmouseover", "Tip('"
								+ seq.getErrorMessage() + "')");
						moNumber.addAttribute("onmouseout", "UnTip()");
						moNumber.addAttribute("style",
								"width: 6%; height: 10px ; color: red;");
					}

					TableData sequenceNumber = new TableData(new SimpleText(
							HtmlUtils.htmlEscape(validateDisplayField(seq
									.getOperationNumber()))));
					sequenceNumber.addAttribute("style", "width: 8%");

					TableData operationDesc = new TableData(new SimpleText(
							HtmlUtils.htmlEscape(validateDisplayField(seq
									.getOperationDescription()))));
					operationDesc.addAttribute("style", "width: 16%");

					// START - WO 25251 - PPH Review
					// TableData activity = new TableData(new
					// SimpleText(HtmlUtils
					// .htmlEscape(validateDisplayField(seq
					// .getActivityCode()))));
					// activity.addAttribute("style", "width: 7%");
					// TableData employeeId = new TableData(new SimpleText(
					// HtmlUtils.htmlEscape(validateDisplayField(seq
					// .getEmployeeNumber()))));
					// employeeId.addAttribute("style", "width: 8%");
					// END - WO 25251 - PPH Review
					TableData quantityCompleted = new TableData(new SimpleText(
							HtmlUtils.htmlEscape(validateDisplayField(String
									.valueOf(seq.getCompletedQuantity())))));
					quantityCompleted.addAttribute("style", "width: 9%");

					// TableData logOn = new TableData(new SimpleText(HtmlUtils
					// .htmlEscape(validateDisplayField(String.valueOf(seq
					// .getLogonDate())))));
					// logOn.addAttribute("style", "width: 18%");
					// TableData logOff = new TableData(new SimpleText(HtmlUtils
					// .htmlEscape(validateDisplayField(String.valueOf(seq
					// .getLogoffDate())))));
					// logOff.addAttribute("style", "width: 18%");
					// TableData standardTime = null;
					// if(seq.getActivityCode()!=null &&
					// seq.getActivityCode().equals("S")){
					// standardTime = new TableData(new SimpleText(HtmlUtils
					// .htmlEscape(validateDisplayField(String.valueOf(
					// Util.roundDBL(getActivityFromBOMList(seq.getOperationNumber(),manufacturingOrder.getBomList()).getSetupTime()
					// * 60,2))))));
					// }else if(seq.getActivityCode()!=null &&
					// seq.getActivityCode().equals("R")){
					//
					// Sequence sequence =
					// getActivityFromBOMList(seq.getOperationNumber(),manufacturingOrder.getBomList());
					// if (sequence != null) {
					// standardTime = new TableData(new SimpleText(HtmlUtils
					// .htmlEscape(validateDisplayField(String.valueOf(
					// Util.roundDBL(sequence.getRunTime() * 60,2))))));
					// } else {
					// standardTime = new TableData(new SimpleText(HtmlUtils
					// .htmlEscape(validateDisplayField(String.valueOf(" - ")))));
					// }
					//
					// }else{
					// standardTime = new TableData(new SimpleText(HtmlUtils
					// .htmlEscape(validateDisplayField(String.valueOf(" - ")))));
					// }
					// standardTime.addAttribute("style", "width: 10%");

					TableData runHours = null;
					if (seq.getRunHours() != null
							&& !"".equals(seq.getRunHours())) {
						runHours = new TableData(new SimpleText(HtmlUtils
								.htmlEscape(validateDisplayField(seq
										.getRunHours()))));
					} else {
						runHours = new TableData(new SimpleText(HtmlUtils
								.htmlEscape(validateDisplayField(String
										.valueOf(" - ")))));
					}
					runHours.addAttribute("style", "width: 10%");

					// TableData minsProcessed = new TableData(new SimpleText(
					// HtmlUtils.htmlEscape(validateDisplayField(String
					// .valueOf(seq.getMinutesProcessed())))));
					// minsProcessed.addAttribute("id", "minsProcessed_" +
					// seq.getActivityLogNumber());
					// minsProcessed.addAttribute("style", "width: 13%");

					TableData pph = null;
					if (seq.getPph() != null && !"".equals(seq.getPph())) {
						pph = new TableData(
								new SimpleText(HtmlUtils
										.htmlEscape(validateDisplayField(seq
												.getPph()))));
					} else {
						pph = new TableData(new SimpleText(HtmlUtils
								.htmlEscape(validateDisplayField(String
										.valueOf(" - ")))));
					}
					pph.addAttribute("style", "width: 13%");

					TableData stdPPH = null;
					if (seq.getStdPPH() != null && !"".equals(seq.getStdPPH())) {
						stdPPH = new TableData(new SimpleText(HtmlUtils
								.htmlEscape(validateDisplayField(seq
										.getStdPPH()))));
					} else {
						stdPPH = new TableData(new SimpleText(HtmlUtils
								.htmlEscape(validateDisplayField(String
										.valueOf(" - ")))));
					}
					stdPPH.addAttribute("style", "width: 8%");

					/*
					 * edited by Naveen on 27 july 2010 to add edit
					 * functionality to each operation of MO in PPHCorrection
					 * interface so that it forwards to correction interface
					 * functionality.
					 */
					Image image = new Image("static/images/change.png", "Edit");
					image.addAttribute("border", "0");
					// InputField field = new InputField("edit", "Edit",
					// InputType.BUTTON);

					String link = "correctionInterfaceModifyMO.htm?faci="
							+ manufacturingOrder.getFacility() + "&amp;moNo="
							+ seq.getMoNumber() + "&amp;sequenceNumber="
							+ seq.getOperationNumber();
					Anchor anchor = new Anchor(link, image);

					TableData editSeq = new TableData(anchor);
					editSeq.addAttribute("style", "width: 8%");

					Image confirmImage = new Image(
							"static/images/confirm_ok.jpg", "Confirm");
					confirmImage.addAttribute("border", "0");
					confirmImage.addAttribute("onClick", "confirmSequence('"
							+ seq.getOperationNumber() + "')");
					TableData confirmSeq = new TableData(confirmImage);
					confirmSeq.addAttribute("style", "width: 8%");
					confirmSeq.addAttribute("align", "center");

					row.addTableData(moNumber);
					row.addTableData(sequenceNumber);
					row.addTableData(operationDesc);
					// START - WO 25251 - PPH Review
					// row.addTableData(activity);
					// row.addTableData(employeeId);
					// END - WO 25251 - PPH Review
					row.addTableData(quantityCompleted);

					row.addTableData(runHours);
					row.addTableData(pph);
					row.addTableData(stdPPH);
					row.addTableData(editSeq);
					row.addTableData(confirmSeq);
					table.addTableRow(row);

				}
			}
		}
		return table;
	}

	/**
	 * Checks if is data found to display.
	 * 
	 * @param manufacturingOrder
	 *            the manufacturing order
	 * @return true, if is data found to display
	 */
	private boolean isDataFoundToDisplay(ManufacturingOrder manufacturingOrder) {

		// Check for the empty BOM list.
		if (manufacturingOrder.getBomList() == null
				|| manufacturingOrder.getBomList().size() == 0) {
			return false;
		}

		for (Activity seq : manufacturingOrder.getErrorActivities()) {
			if (!seq.isReported()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the activity from bom list.
	 * 
	 * @param givenOperationNo
	 *            the given operation no
	 * @param bomList
	 *            the bom list
	 * @return the activity from bom list
	 */
	public Sequence getActivityFromBOMList(String givenOperationNo, List bomList) {
		if (bomList != null) {
			for (int i = 0; i < bomList.size(); i++) {
				Sequence activity = (Sequence) bomList.get(i);
				if (givenOperationNo.equals(activity.getSequence().trim())) {
					return activity;
				}
			}
		}
		return null;
	}
}
