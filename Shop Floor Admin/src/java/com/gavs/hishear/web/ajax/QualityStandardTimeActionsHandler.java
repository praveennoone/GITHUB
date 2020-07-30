// File:         QualityStandardTimeActionsHandler.java
// Created:      Apr 23, 2011
// Author:       Pinky S
//
// This code is copyright (c) 2011 Lisi Aerospace
// 
// History:
//  
//
package com.gavs.hishear.web.ajax;

import java.util.ArrayList;

import org.springframework.web.util.HtmlUtils;
import org.springmodules.xt.ajax.AjaxResponse;
import org.springmodules.xt.ajax.AjaxResponseImpl;
import org.springmodules.xt.ajax.AjaxSubmitEvent;
import org.springmodules.xt.ajax.action.ReplaceContentAction;
import org.springmodules.xt.ajax.component.Container;
import org.springmodules.xt.ajax.component.Image;
import org.springmodules.xt.ajax.component.InputField;
import org.springmodules.xt.ajax.component.InputField.InputType;
import org.springmodules.xt.ajax.component.SimpleText;
import org.springmodules.xt.ajax.component.Table;
import org.springmodules.xt.ajax.component.TableData;
import org.springmodules.xt.ajax.component.TableHeader;
import org.springmodules.xt.ajax.component.TableHeaderData;
import org.springmodules.xt.ajax.component.TableRow;

import com.gavs.hishear.web.domain.StandardTime;
import com.gavs.hishear.web.domain.TestMethods;

/**
 * The Class CorrectionIntModifyMOActionsHandler.
 * 
 */
public class QualityStandardTimeActionsHandler extends BaseAjaxHandler {

	public AjaxResponse getDetails(AjaxSubmitEvent event) {
		System.out.println("in handler");
		AjaxResponse response = new AjaxResponseImpl();
		StandardTime dto = (StandardTime) event.getCommandObject();
		if (dto.getTestMethods() != null) {
			System.out.println("data from actions handler"
					+ dto.getTestMethods().size());
		}
		ReplaceContentAction action = null;
		System.out.println("gonna form the status div");
		if (dto.getMessage() != null && !(dto.getMessage().isEmpty())) {
			System.out.println("got inside ");
			if (dto.getIsSuccess()) {
				action = new ReplaceContentAction("statusMessage",
						new SimpleText("<b><font color=\"green\" size=\"3px\">"
								+ dto.getMessage() + "</font></b>"));
			} else {
				System.out.println("got else ");
				action = new ReplaceContentAction("statusMessage",
						new SimpleText("<b><font color=\"red\" size=\"3px\">"
								+ dto.getMessage() + "</font></b>"));
			}
			dto.setMessage("");
			response.addAction(action);
		}
		Container containerTotal = new Container(Container.Type.DIV);
		containerTotal.addAttribute("align", "center");
		containerTotal.addAttribute("style", "width: 700px; height: 260px");

		containerTotal.addComponent(buildHeaderTable());
		Container buildContainer = new Container(Container.Type.DIV);
		buildContainer
				.addAttribute("style",
						"width: 500px; overflow: scroll; overflow-x: hidden;height: 250px");

		buildContainer.addComponent(buildDataTable(dto));
		containerTotal.addComponent(buildContainer);

		ReplaceContentAction detailsAction = new ReplaceContentAction(
				"tableStandardTimeDetails", containerTotal);

		response.addAction(detailsAction);

		Image disableFilterImage = new Image(
				"static/images/filter_disable.gif", "Filter");
		disableFilterImage.addAttribute("style", "cursor: default;");

		ReplaceContentAction filterbuttonAction = new ReplaceContentAction(
				"filterButtonDiv", disableFilterImage);
		response.addAction(filterbuttonAction);

		Image disableAddNewImage = new Image(
				"static/images/addStandardTime_disable.gif",
				"Add Standard Time");
		disableFilterImage.addAttribute("style", "cursor: default;");

		ReplaceContentAction addNewbuttonAction = new ReplaceContentAction(
				"newStandardTime", disableAddNewImage);
		response.addAction(addNewbuttonAction);

		Image cancelImage = new Image("static/images/cancel.gif", "Cancel");
		cancelImage.addAttribute("onClick", "javascript:CancelReload()");
		cancelImage.addAttribute("style", "cursor: pointer;");

		ReplaceContentAction addCancelbuttonAction = new ReplaceContentAction(
				"addOrUpdateDiv", cancelImage);
		response.addAction(addCancelbuttonAction);

		return response;

	}

	private Table buildHeaderTable() {

		TableHeaderData testNameHeaderData = new TableHeaderData(
				new SimpleText("Test Method Name"));
		testNameHeaderData.addAttribute("style", "width: 30%");

		TableHeaderData stdTimeHeaderData = new TableHeaderData(new SimpleText(
				"Standard Time"));
		stdTimeHeaderData.addAttribute("style", "width: 10%");

		TableHeaderData editHeaderData = new TableHeaderData(new SimpleText(
				"Edit"));
		editHeaderData.addAttribute("style", "width: 10%");

		TableHeaderData deleteHeaderData = new TableHeaderData(new SimpleText(
				"Delete"));
		deleteHeaderData.addAttribute("style", "width: 10%");

		ArrayList<TableHeaderData> headers = new ArrayList<TableHeaderData>();
		headers.add(testNameHeaderData);
		headers.add(stdTimeHeaderData);
		headers.add(editHeaderData);
		headers.add(deleteHeaderData);

		TableHeader tableHeader = new TableHeader(headers);
		tableHeader.addAttribute("style", "text-align: center;");
		Table table = new Table();
		table.setTableHeader(tableHeader);
		table.addAttribute("id", "normalTableNostripe");
		table.addAttribute("align", "center");
		table.addAttribute("cellSpacing", "0");
		table.addAttribute("cellPadding", "0");
		table.addAttribute("border", "1");
		table.addAttribute("class", "classContentTable");
		table.addAttribute("style",
				"white-space: nowrap; word-spacing: normal; width: 500px");
		table.addTableHeaderAttribute("class", "fixedHeader");
		table.addTableBodyAttribute("class", "scrollContent");

		return table;

	}

	public Table buildDataTable(StandardTime standardTime) {
		Table table = new Table();

		table.addAttribute("id", "normalTableNostripe");
		table.addAttribute("align", "center");
		table.addAttribute("cellSpacing", "0");
		table.addAttribute("cellPadding", "0");
		table.addAttribute("border", "1");
		table.addAttribute("class", "classContentTable");
		table.addTableBodyAttribute("class", "scrollContent");
		table.addAttribute("style",
				"white-space: nowrap; word-spacing: normal; width: 500px");
		table.addTableHeaderAttribute("class", "fixedHeader");

		if (standardTime.getTestMethods() != null
				&& standardTime.getTestMethods().size() > 0) {
			for (TestMethods testMethod : standardTime.getTestMethods()) {

				if (testMethod.getTestMethodName() != null) {
					TableRow row = new TableRow();

					TableData testMethodName = new TableData(
							new SimpleText(validateDisplayField(testMethod
									.getTestMethodName())));

					testMethodName.addAttribute("style",
							"width: 30%  ; vertical-align: middle");

					TableData standardTimeForTestMethod = new TableData(
							new SimpleText(HtmlUtils
									.htmlEscape(String.valueOf((int) testMethod
											.getStandardTime()))));

					standardTimeForTestMethod.addAttribute("style",
							"width: 10% ; vertical-align: middle");

					Image editImage = new Image("static/images/edit.jpg",
							"Edit");
					editImage.addAttribute("border", "0");
					editImage.addAttribute("onClick", "addOrUpdateTestMethod('"
							+ validateDisplayField(testMethod
									.getTestMethodName()) + "', '"
							+ testMethod.getStandardTime() + "');");
					TableData editTime = new TableData(editImage);

					editTime.addAttribute("style",
							"text-align: center; width: 10%");

					Image deleteImage = new Image(
							"static/images/icon_delete.png", "Delete");
					deleteImage.addAttribute("border", "0");
					deleteImage.addAttribute("onClick", "deleteTestMethod('"
							+ validateDisplayField(testMethod
									.getTestMethodName()) + "', '"
							+ testMethod.getStandardTime() + "');");
					deleteImage.addAttribute("style", "cursor:pointer");
					TableData deleteTime = new TableData(deleteImage);

					deleteTime
							.addAttribute("style",
									"text-align: center; width: 10%  ; vertical-align: middle");

					row.addTableData(testMethodName);
					row.addTableData(standardTimeForTestMethod);
					row.addTableData(editTime);
					row.addTableData(deleteTime);
					table.addTableRow(row);

				}
			}
		}

		return table;
	}

	public AjaxResponse addOrUpdateTestMethod(AjaxSubmitEvent event) {

		// Add the Image
		Image addImage = new Image("static/images/addStandardTime_disable.gif",
				"Add Standard Time");

		System.out.println("in handler");
		AjaxResponse response = new AjaxResponseImpl();
		StandardTime dto = (StandardTime) event.getCommandObject();
		dto.setMessage("");
		Container containerUpdate = new Container(Container.Type.DIV);
		containerUpdate.addAttribute("align", "center");
		containerUpdate.addAttribute("style",
				"width: 500px; height: 40; border:2px solid #465272;");
		containerUpdate.addComponent(getAddOrUpdateTable(dto));

		ReplaceContentAction updateAction = new ReplaceContentAction(
				"addOrUpdateDiv", containerUpdate);

		ReplaceContentAction disableAddAction = new ReplaceContentAction(
				"newStandardTime", addImage);
		response.addAction(disableAddAction);
		response.addAction(updateAction);

		Image disableFilterImage = new Image(
				"static/images/filter_disable.gif", "Filter");
		disableFilterImage.addAttribute("style", "cursor: default;");

		ReplaceContentAction filterbuttonAction = new ReplaceContentAction(
				"filterButtonDiv", disableFilterImage);
		response.addAction(filterbuttonAction);

		return response;
	}

	public Table getAddOrUpdateTable(StandardTime dto) {

		Table table = new Table();
		table.addAttribute("id", "normalTableNostripe");
		table.addAttribute("align", "left");
		table.addAttribute("cellSpacing", "0");
		table.addAttribute("cellPadding", "0");
		table.addAttribute("border", "1");
		table.addAttribute("class", "classContentTable");
		table
				.addAttribute("style",
						"white-space: nowrap; word-spacing: normal; width: 98%; vertical-align: middle");
		table.addTableHeaderAttribute("class", "fixedHeader");

		TableData testMethodNameLabel = null;
		TableData txtTestMethodName = null;
		InputField testMethodName = null;
		String btnAddOrUpdate = null;

		testMethodNameLabel = new TableData(new SimpleText("Test Method Name"));
		testMethodNameLabel.addAttribute("class", "stripe");

		if (dto.getSelectedTestMethodName() != null
				&& !("temp".equalsIgnoreCase(dto.getSelectedTestMethodName()))) {

			testMethodName = new InputField("txtTestMethodName", "",
					InputField.InputType.TEXT);
			testMethodName.addAttribute("value", dto
					.getSelectedTestMethodName()
					+ "");
			testMethodName.addAttribute("disabled", "disabled");
			testMethodName.addAttribute("size", "20");
			txtTestMethodName = new TableData(testMethodName);
			btnAddOrUpdate = "<img src=\"static/images/Update.gif\" border=\"0\" style=\"cursor: pointer;\" onclick=\"javascript:updateTestMethod('"
					+ dto.getSelectedTestMethodName()
					+ "','"
					+ dto.getOldStandardTime() + "')\"/>";
		} else {

			testMethodName = new InputField("txtTestMethodName", "",
					InputField.InputType.TEXT);
			testMethodName.addAttribute("id", "txtTestMethodName");
			testMethodName.addAttribute("maxlength", "30");
			testMethodName.addAttribute("size", "20");
			txtTestMethodName = new TableData(testMethodName);
			btnAddOrUpdate = "<img src=\"static/images/save.gif\" border=\"0\" style=\"cursor: pointer;\" onclick=\"javascript:addStandardTime()\"/>";
		}

		TableData standardTimeLabel = new TableData(new SimpleText(
				"Standard Time"));
		standardTimeLabel.addAttribute("class", "stripe");

		InputField standardTime = new InputField("StandardTime", "",
				InputType.TEXT);
		standardTime.addAttribute("id", "StandardTime");
		standardTime.addAttribute("maxlength", "4");
		standardTime.addAttribute("size", "5");
		TableData txtStandardTime = new TableData(standardTime);

		String btnCancel = "";
		if (dto.getSelectedTestMethodName() != null
				&& !("temp").equalsIgnoreCase(dto.getSelectedTestMethodName())) {
			btnCancel = "<img src=\"static/images/cancel.gif\" border=\"0\" style=\"cursor: pointer;\" onclick=\"Cancel()\"/>";
		} else {
			btnCancel = "<img src=\"static/images/cancel.gif\" border=\"0\" style=\"cursor: pointer;\" onclick=\"CancelReload()\"/>";
		}

		TableData tdButtons = new TableData(new SimpleText(btnAddOrUpdate
				+ btnCancel));
		tdButtons.addAttribute("colspan", "4");
		tdButtons.addAttribute("valign", "middle");
		tdButtons.addAttribute("id", "buttonsDiv");

		TableRow row1 = new TableRow();
		row1.addTableData(testMethodNameLabel);
		row1.addTableData(txtTestMethodName);
		row1.addTableData(standardTimeLabel);
		row1.addTableData(txtStandardTime);
		row1.addTableData(tdButtons);

		Table addOrUpdateTable = new Table();

		addOrUpdateTable.addTableRow(row1);

		return addOrUpdateTable;
	}

	public AjaxResponse addStandardTime(AjaxSubmitEvent event) {
		AjaxResponse response = getDetails(event);
		StandardTime dto = (StandardTime) event.getCommandObject();
		ReplaceContentAction action = null;
		if (dto.getMessage() != null && !(dto.getMessage().isEmpty())) {
			if (dto.getIsSuccess()) {
				action = new ReplaceContentAction("statusMessage",
						new SimpleText("<b><font color=\"green\" size=\"3px\">"
								+ dto.getMessage() + "</font></b>"));
			} else {
				action = new ReplaceContentAction("statusMessage",
						new SimpleText("<b><font color=\"red\" size=\"3px\">"
								+ dto.getMessage() + "</font></b>"));
			}
			dto.setMessage("");
			response.addAction(action);
		}
		return response;
	}

	public AjaxResponse deleteTestMethod(AjaxSubmitEvent event) {
		AjaxResponse response = getDetails(event);
		StandardTime dto = (StandardTime) event.getCommandObject();
		ReplaceContentAction action = null;
		if (dto.getMessage() != null && !(dto.getMessage().isEmpty())) {
			if (dto.getIsSuccess()) {
				action = new ReplaceContentAction("statusMessage",
						new SimpleText("<b><font color=\"green\" size=\"3px\">"
								+ dto.getMessage() + "</font></b>"));
			} else {
				action = new ReplaceContentAction("statusMessage",
						new SimpleText("<b><font color=\"red\" size=\"3px\">"
								+ dto.getMessage() + "</font></b>"));
			}
			dto.setMessage("");
			response.addAction(action);
		}
		return response;
	}

	public AjaxResponse updateTestMethod(AjaxSubmitEvent event) {
		AjaxResponse response = getDetails(event);
		StandardTime dto = (StandardTime) event.getCommandObject();
		ReplaceContentAction action = null;
		if (dto.getMessage() != null && !(dto.getMessage().isEmpty())) {
			if (dto.getIsSuccess()) {
				action = new ReplaceContentAction("statusMessage",
						new SimpleText("<b><font color=\"green\" size=\"3px\">"
								+ dto.getMessage() + "</font></b>"));
			} else {
				action = new ReplaceContentAction("statusMessage",
						new SimpleText("<b><font color=\"red\" size=\"3px\">"
								+ dto.getMessage() + "</font></b>"));
			}
			dto.setMessage("");
			response.addAction(action);
		}
		return response;
	}
}
