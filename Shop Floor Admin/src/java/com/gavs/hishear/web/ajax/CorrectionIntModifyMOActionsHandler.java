package com.gavs.hishear.web.ajax;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.HtmlUtils;
import org.springmodules.xt.ajax.AjaxActionEvent;
import org.springmodules.xt.ajax.AjaxResponse;
import org.springmodules.xt.ajax.AjaxResponseImpl;
import org.springmodules.xt.ajax.AjaxSubmitEvent;
import org.springmodules.xt.ajax.action.ExecuteJavascriptFunctionAction;
import org.springmodules.xt.ajax.action.ReplaceContentAction;
import org.springmodules.xt.ajax.component.Anchor;
import org.springmodules.xt.ajax.component.Container;
import org.springmodules.xt.ajax.component.Container.Type;
import org.springmodules.xt.ajax.component.Image;
import org.springmodules.xt.ajax.component.InputField;
import org.springmodules.xt.ajax.component.InputField.InputType;
import org.springmodules.xt.ajax.component.Option;
import org.springmodules.xt.ajax.component.Select;
import org.springmodules.xt.ajax.component.SimpleText;
import org.springmodules.xt.ajax.component.Table;
import org.springmodules.xt.ajax.component.TableData;
import org.springmodules.xt.ajax.component.TableHeader;
import org.springmodules.xt.ajax.component.TableHeaderData;
import org.springmodules.xt.ajax.component.TableRow;

import com.gavs.hishear.util.DateUtil;
import com.gavs.hishear.util.Util;
import com.gavs.hishear.web.constants.webConstants;
import com.gavs.hishear.web.controllers.ControllerModifyMOHelper;
import com.gavs.hishear.web.domain.Asset;
import com.gavs.hishear.web.domain.ManufacturingOrder;
import com.gavs.hishear.web.domain.MasterData;
import com.gavs.hishear.web.domain.Sequence;
import com.gavs.hishear.web.domain.SequenceActivity;


public class CorrectionIntModifyMOActionsHandler extends BaseAjaxHandler {

	private static final String EMPTY_STRING = "";
	private static final String CHECKED = "checked=\"checked\"";
	private static final String DEFAULT_DATE_FORMAT = "MM-dd-yyyy hh:mm:ss aa";
	private static final String DASH = "-";
	private static final String SPACE = " ";
	private ControllerModifyMOHelper modifyMOHelper;

	/**
	 * @return the modifyMOHelper
	 */
	public ControllerModifyMOHelper getModifyMOHelper() {
		return modifyMOHelper;
	}

	/**
	 * @param modifyMOHelper
	 *            the modifyMOHelper to set
	 */
	public void setModifyMOHelper(ControllerModifyMOHelper modifyMOHelper) {
		this.modifyMOHelper = modifyMOHelper;
	}

	public AjaxResponse getMODetails(AjaxSubmitEvent event) {

		try {
			System.out.println(" Handler Called ........................ ");

			AjaxResponse response = new AjaxResponseImpl();
			ManufacturingOrder dto = (ManufacturingOrder) event
					.getCommandObject();

			if (dto != null && dto.getSequences() != null
					&& !dto.getSequences().isEmpty()) {
				buildSequenceDetails(response, dto);

			} else {
				ExecuteJavascriptFunctionAction showNoDataFoundErrorAction = new ExecuteJavascriptFunctionAction(
						"showNoDataMessage", new HashMap<String, Object>());
				response.addAction(showNoDataFoundErrorAction);
			}
			System.out.println(" bef returning ajax response");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public AjaxResponse displayMODetails(AjaxActionEvent event) {
		try {
			System.out.println(" Handler Called ........................ ");

			AjaxResponse response = new AjaxResponseImpl();
			ManufacturingOrder dto = (ManufacturingOrder) event
					.getCommandObject();

			if (dto != null && dto.getSequences() != null
					&& !dto.getSequences().isEmpty()) {
				buildSequenceDetails(response, dto);

			} else {
				ExecuteJavascriptFunctionAction showNoDataFoundErrorAction = new ExecuteJavascriptFunctionAction(
						"showNoDataMessage", new HashMap<String, Object>());
				response.addAction(showNoDataFoundErrorAction);
			}
			System.out.println(" bef returning ajax response");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public AjaxResponse displayActivityDetails(AjaxSubmitEvent event) {

		try {
			System.out.println(" Handler Called ........................ ");

			AjaxResponse response = new AjaxResponseImpl();
			ManufacturingOrder dto = (ManufacturingOrder) event
					.getCommandObject();

			Sequence seq = dto.getSelectedSequeuce();
			if (seq != null && seq.getSeqActivityDetails() != null
					&& seq.getSeqActivityDetails().size() > 0) {
				SequenceActivity seqAct = seq.getSelectedSeqActivity();
				if (seqAct != null) {
					String error = seqAct.getError();
					System.out.println("error seqact" + error);
					if (error != null && !error.equals(EMPTY_STRING)) {
						return editActivityDetails(event);
					} else {
						buildSequenceActivityDetails(response, dto);
					}
				} else {
					buildSequenceActivityDetails(response, dto);
				}
			} else {
				// this is a case where the status is not started - we need to
				// display a page for entry for the user
				buildAddMissingInfo(response, dto);
			}

			return response;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public AjaxResponse displayActivityDetails(AjaxActionEvent event) {

		try {
			System.out.println(" Handler Called ........................ ");

			AjaxResponse response = new AjaxResponseImpl();
			ManufacturingOrder dto = (ManufacturingOrder) event
					.getCommandObject();

			Sequence seq = dto.getSelectedSequeuce();
			if (seq != null && seq.getSeqActivityDetails() != null
					&& seq.getSeqActivityDetails().size() > 0) {
				buildSequenceActivityDetails(response, dto);
			} else {
				// this is a case where the status is not started - we need to
				// return to the seq details
				buildSequenceDetails(response, dto);
			}

			return response;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public AjaxResponse addMissingInfoDetails(AjaxSubmitEvent event) {

		try {
			System.out.println(" Handler Called ........................ ");

			AjaxResponse response = new AjaxResponseImpl();
			ManufacturingOrder dto = (ManufacturingOrder) event
					.getCommandObject();

			Sequence seq = dto.getSelectedSequeuce();
			if (seq != null && seq.getSeqActivityDetails() != null
					&& seq.getSeqActivityDetails().size() > 0) {
				// buildSequenceActivityDetails(response, dto);
				// add the missing info
				addMissingInfoTable(response, dto);

			} else {
				// this is a case where the status is not started - we need to
				// display a page for entry for the user
				buildAddMissingInfo(response, dto);
			}

			return response;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public AjaxResponse editActivityDetails(AjaxSubmitEvent event) {
		try {
			System.out.println(" Handler Called ........................ ");

			AjaxResponse response = new AjaxResponseImpl();
			ManufacturingOrder dto = (ManufacturingOrder) event
					.getCommandObject();

			Sequence seq = dto.getSelectedSequeuce();
			if (seq != null && seq.getSeqActivityDetails() != null
					&& seq.getSeqActivityDetails().size() > 0) {
				SequenceActivity seqAct = seq.getSelectedSeqActivity();
				editSequenceActivityDetails(response, dto);
			} else {
				// this is a case where the status is not started - we need to
				// display a page for entry for the user
				buildAddMissingInfo(response, dto);
			}

			return response;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public AjaxResponse displayActivityChangeInfo(AjaxSubmitEvent event) {

		try {
			System.out
					.println(" Handler Called ........................ displayActivityChangeInfo                        hhh");

			HttpServletRequest request = event.getHttpRequest();
			AjaxResponse response = new AjaxResponseImpl();
			ManufacturingOrder dto = (ManufacturingOrder) event
					.getCommandObject();

			Sequence seq = dto.getSelectedSequeuce();
			SequenceActivity seqAct = null;
			if (seq != null) {
				seqAct = seq.getSelectedSeqActivity();
				if (seqAct == null) {
					seqAct = new SequenceActivity();
					seq.setSelectedSeqActivity(seqAct);
				}
			}

			if (request.getParameter("activity1") != null) {
				System.out.println("activiyy"
						+ request.getParameter("activity1"));
				// modifyMOHelper.setValuesFromRequest(request, seq);
				String flag = request.getParameter("newFlag");
				String pauseFlag = EMPTY_STRING;
				if (request.getParameter("activity1").equalsIgnoreCase(
						webConstants.PAUSE)) {
					pauseFlag = webConstants.PAUSE;
				} else {
					pauseFlag = SPACE;
				}

				System.out.println("flag:" + flag);
				System.out.println("pause flag:" + pauseFlag);
				displayActivityChangeInfo(dto, response, flag, pauseFlag);

			}
			return response;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public AjaxResponse validateCompleteSequence(AjaxSubmitEvent event) {
		AjaxResponse response = new AjaxResponseImpl();
		ManufacturingOrder dto = (ManufacturingOrder) event.getCommandObject();
		Sequence seq = dto.getSelectedSequeuce();

		ReplaceContentAction action = null;
		if (seq.getErrorMessage() != null) {
			action = new ReplaceContentAction("statusMessage", new SimpleText(
					"<font color=\"red\" size=\"2px\"><b>"
							+ seq.getErrorMessage() + "</b></font>"));
			ExecuteJavascriptFunctionAction functionAction = new ExecuteJavascriptFunctionAction(
					"uncheckCompleteSequence", new HashMap<String, Object>());
			response.addAction(functionAction);
		} else {
			action = new ReplaceContentAction("statusMessage", new SimpleText(
					EMPTY_STRING));
		}

		response.addAction(action);
		return response;
	}

	private void displayActivityChangeInfo(ManufacturingOrder dto,
			AjaxResponse response, String flag, String pauseFlag) {

		ReplaceContentAction action = null;

		Container containerTotal = new Container(Container.Type.DIV);
		containerTotal.addAttribute("align", "center");
		containerTotal.addAttribute("style", "width: 920px;");

		Container container = new Container(Container.Type.DIV);
		// container.addAttribute("style",
		// "width: 920px; overflow: auto; height: 159px");

		// ReplaceContentAction m3ItemAction = null;
		// Container m3ItemContainer = new Container(Container.Type.DIV);
		// m3ItemContainer.addAttribute("style", "width: 920px");
		// m3ItemContainer.addAttribute("align", "center");
		// m3ItemContainer.addComponent(buildMOTable(dto, true));
		// m3ItemAction = new ReplaceContentAction("filterCriteria",
		// m3ItemContainer);
		// response.addAction(m3ItemAction);

		// check if error is there display it hwe

		container.addComponent(formErrorInfo(dto));
		action = new ReplaceContentAction("statusMessage", container);
		response.addAction(action);

		container = new Container(Container.Type.DIV);
		// container.addAttribute("style",
		// "width: 920px; overflow: auto; height: 159px");

		container.addComponent(new SimpleText("<br/>"));
		container.addComponent(buildMOTable(dto, true));
		if (flag.equalsIgnoreCase(webConstants.YES)) {
			container.addComponent(formEmployeeInfoTable(dto));
		} else {
			container.addComponent(formEmployeeNameTable(dto));
		}
		action = new ReplaceContentAction("tableMOInfo", container);
		response.addAction(action);

		container = new Container(Container.Type.DIV);
		// container.addAttribute("style",
		// "width: 920px; overflow: auto; height: 159px");
		container.addComponent(formActivityInfoTable(dto, pauseFlag, flag));

		action = new ReplaceContentAction("tableMODetails", container);
		response.addAction(action);

		// add buttons div here
		/*
		 * ReplaceContentAction buttonAction = null; buttonAction = new
		 * ReplaceContentAction("buttonsDiv",
		 * buildButtonContainerForConfirm(webConstants.NO,
		 * webConstants.STATUS_NOT_STARTED)); response.addAction(buttonAction);
		 */

	}

	private void buildSequenceDetails(AjaxResponse response,
			ManufacturingOrder dto) {

		if (isDataFoundToDisplay(dto)) {
			ReplaceContentAction action = null;
			System.out.println("in build seq details");
			Container containerTotal = new Container(Container.Type.DIV);
			containerTotal.addAttribute("style", "width: 920px");
			containerTotal.addAttribute("align", "left");

			Container container = new Container(Container.Type.DIV);
			container.addAttribute("style",
					"width: 920px; overflow: auto; height: 253px");

			// ReplaceContentAction m3ItemAction = null;
			// Container m3ItemContainer = new Container(Container.Type.DIV);
			// m3ItemContainer.addAttribute("style", "width: 920px");
			// m3ItemContainer.addAttribute("align", "center");
			// m3ItemContainer.addComponent(buildMOTable(dto, false));
			// m3ItemAction = new ReplaceContentAction("filterCriteria",
			// m3ItemContainer);
			// response.addAction(m3ItemAction);

			container.addComponent(buildSequenceDetailsTable(dto));

			containerTotal.addComponent(getHeaderTableForSequence());
			containerTotal.addComponent(container);

			action = new ReplaceContentAction("tableMODetails", containerTotal);
			response.addAction(action);
			System.out.println("preparing viewing the page"
					+ response.isEmpty());

		} else {
			ReplaceContentAction m3ItemAction = new ReplaceContentAction(
					"MODetailsDiv", new SimpleText(EMPTY_STRING));
			ReplaceContentAction action = new ReplaceContentAction(
					"tableMODetails", new SimpleText(EMPTY_STRING));
			ReplaceContentAction buttonAction = new ReplaceContentAction(
					"buttonsDiv", new SimpleText(EMPTY_STRING));
			ReplaceContentAction messageAction = new ReplaceContentAction(
					"statusMessage",
					new SimpleText(
							"<font color=\"red\" size=\"2px\"><b>No Data Found</b></font>"));
			response.addAction(action);
			response.addAction(m3ItemAction);
			response.addAction(buttonAction);
			response.addAction(messageAction);
		}
	}

	private void buildSequenceActivityDetails(AjaxResponse response,
			ManufacturingOrder dto) {

		if (isDataFoundToDisplay(dto)) {
			ReplaceContentAction action = null;

			Container containerTotal = new Container(Container.Type.DIV);
			containerTotal.addAttribute("align", "center");
			containerTotal.addAttribute("style", "width: 940px;");

			Container container = new Container(Container.Type.DIV);
			container.addAttribute("style",
					"width: 940px; overflow: auto; height: 200px");

			// ReplaceContentAction m3ItemAction = null;
			// Container m3ItemContainer = new Container(Container.Type.DIV);
			// m3ItemContainer.addAttribute("style", "width: 920px");
			// m3ItemContainer.addAttribute("align", "center");
			// m3ItemContainer.addComponent(buildMOTable(dto, true));
			// m3ItemAction = new ReplaceContentAction("filterCriteria",
			// m3ItemContainer);
			// response.addAction(m3ItemAction);

			container.addComponent(buildSequenceActivityDetailsTable(dto));

			containerTotal.addComponent(buildMOTable(dto, true));
			containerTotal.addComponent(getHeaderTableForActivities());
			containerTotal.addComponent(container);

			action = new ReplaceContentAction("tableMODetails", containerTotal);
			response.addAction(action);

			// add buttons div here
			ReplaceContentAction buttonAction = null;
			buttonAction = new ReplaceContentAction("buttonsDiv",
					buildButtonContainer());
			response.addAction(buttonAction);

		} else {
			ReplaceContentAction m3ItemAction = new ReplaceContentAction(
					"MODetailsDiv", new SimpleText(EMPTY_STRING));
			ReplaceContentAction action = new ReplaceContentAction(
					"tableMODetails", new SimpleText(EMPTY_STRING));
			ReplaceContentAction buttonAction = new ReplaceContentAction(
					"buttonsDiv", new SimpleText(EMPTY_STRING));
			ReplaceContentAction messageAction = new ReplaceContentAction(
					"statusMessage",
					new SimpleText(
							"<font color=\"red\" size=\"2px\"><b>No Data Found</b></font>"));
			response.addAction(action);
			response.addAction(m3ItemAction);
			response.addAction(buttonAction);
			response.addAction(messageAction);
		}

	}

	public void buildAddMissingInfo(AjaxResponse response,
			ManufacturingOrder dto) {

		ReplaceContentAction action = null;

		Container containerTotal = new Container(Container.Type.DIV);
		containerTotal.addAttribute("align", "center");
		containerTotal.addAttribute("style", "width: 920px;");

		Container container = new Container(Container.Type.DIV);
		// container.addAttribute("style",
		// "width: 920px; overflow: auto; height: 159px");

		// ReplaceContentAction m3ItemAction = null;
		// Container m3ItemContainer = new Container(Container.Type.DIV);
		// m3ItemContainer.addAttribute("style", "width: 920px");
		// m3ItemContainer.addAttribute("align", "center");
		// m3ItemContainer.addComponent(buildMOTable(dto, true));
		// m3ItemAction = new ReplaceContentAction("filterCriteria",
		// m3ItemContainer);
		// response.addAction(m3ItemAction);

		// container.addComponent(buildMOTable(dto, true));
		container.addComponent(new SimpleText("<br/>"));
		container.addComponent(buildMOTable(dto, true));
		container.addComponent(formEmployeeInfoTable(dto));
		action = new ReplaceContentAction("tableMOInfo", container);
		response.addAction(action);

		container = new Container(Container.Type.DIV);
		// container.addAttribute("style",
		// "width: 920px; overflow: auto; height: 159px");
		container.addComponent(formActivityInfoTableForAdd(dto,
				webConstants.YES, webConstants.YES));

		action = new ReplaceContentAction("tableMODetails", container);
		response.addAction(action);

		ReplaceContentAction buttonAction = new ReplaceContentAction(
				"buttonsDiv", new SimpleText(EMPTY_STRING));
		response.addAction(buttonAction);

		// add buttons div here
		/*
		 * ReplaceContentAction buttonAction = null; buttonAction = new
		 * ReplaceContentAction("buttonsDiv",
		 * buildButtonContainerForConfirm(webConstants.YES,
		 * webConstants.STATUS_NOT_STARTED)); response.addAction(buttonAction);
		 */
	}

	private void editSequenceActivityDetails(AjaxResponse response,
			ManufacturingOrder dto) {

		ReplaceContentAction action = null;

		Container containerTotal = new Container(Container.Type.DIV);
		containerTotal.addAttribute("align", "center");
		containerTotal.addAttribute("style", "width: 920px;");

		Container container = new Container(Container.Type.DIV);
		// container.addAttribute("style",
		// "width: 920px; overflow: auto; height: 159px");
		container.addAttribute("style", "width: 920px;");

		// ReplaceContentAction m3ItemAction = null;
		// Container m3ItemContainer = new Container(Container.Type.DIV);
		// m3ItemContainer.addAttribute("style", "width: 920px");
		// m3ItemContainer.addAttribute("align", "center");
		// m3ItemContainer.addComponent(buildMOTable(dto, true));
		// m3ItemAction = new ReplaceContentAction("filterCriteria",
		// m3ItemContainer);
		// response.addAction(m3ItemAction);

		container.addComponent(new SimpleText("<br/>"));
		// container.addComponent(buildMOTable(dto, true));
		container.addComponent(formEmployeeNameTable(dto));
		action = new ReplaceContentAction("tableMOInfo", container);
		response.addAction(action);

		container = new Container(Container.Type.DIV);

		container.addComponent(formErrorInfo(dto));
		action = new ReplaceContentAction("statusMessage", container);
		response.addAction(action);

		container = new Container(Container.Type.DIV);

		// container.addAttribute("style",
		// "width: 920px; overflow: auto; height: 159px");
		container.addAttribute("style", "width: 920px;");
		container.addComponent(formActivityInfoTable(dto, webConstants.NO,
				webConstants.NO));

		action = new ReplaceContentAction("tableMODetails", container);
		response.addAction(action);

		// add buttons div here
		/*
		 * ReplaceContentAction buttonAction = null; buttonAction = new
		 * ReplaceContentAction("buttonsDiv",
		 * buildButtonContainerForConfirm(webConstants.NO,
		 * webConstants.STATUS_NOT_STARTED)); response.addAction(buttonAction);
		 */
	}

	private void addMissingInfoTable(AjaxResponse response,
			ManufacturingOrder dto) {

		ReplaceContentAction action = null;

		Container container = new Container(Container.Type.DIV);
		// container.addAttribute("style",
		// "width: 920px; overflow: auto; height: 159px");
		// container.addAttribute("style",
		// "width: 920px; overflow: auto; height: 159px");

		container.addComponent(new SimpleText("<br/>"));
		container.addComponent(buildMOTable(dto, true));
		container.addComponent(formEmployeeInfoTable(dto));
		action = new ReplaceContentAction("tableMOInfo", container);
		response.addAction(action);

		container = new Container(Container.Type.DIV);
		// container.addAttribute("style",
		// "width: 920px; overflow: auto; height: 159px");
		container.addComponent(formActivityInfoTableForAdd(dto,
				webConstants.YES, webConstants.YES));

		action = new ReplaceContentAction("tableMODetails", container);
		response.addAction(action);
	}

	private Table buildMOTable(ManufacturingOrder dto, boolean seqActFlag) {
		Table innerTable1 = new Table();
		Sequence seq = dto.getSelectedSequeuce();
		innerTable1.addAttribute("id", "outerTableNostripe");
		innerTable1.addAttribute("align", "center");
		innerTable1.addAttribute("cellSpacing", "0");
		innerTable1.addAttribute("cellPadding", "0");
		innerTable1.addAttribute("border", "1");
		innerTable1
				.addAttribute(
						"style",
						"white-space: nowrap; word-spacing: normal; border-left: 1px solid #465272; border-right: 1px solid #465272; "
								+ "border-top: 1px solid #465272; border-bottom: 1px solid #465272; width: 600px");
		TableRow innerRow1 = new TableRow();
		TableData facility = new TableData(new SimpleText(HtmlUtils
				.htmlEscape(validateDisplayField("Facility : "
						+ dto.getFacility()))));
		facility.addAttribute("style", "width: 175px; font-weight: bold");
		TableData moNumber = new TableData(new SimpleText(HtmlUtils
				.htmlEscape(validateDisplayField("MO Number: "
						+ dto.getMoNumber()))));
		moNumber.addAttribute("style", "width: 180px; font-weight: bold");
		TableData sequence = null;
		TableData itemNumber = null;
		if (seqActFlag) {
			itemNumber = new TableData(new SimpleText("Part: <b>"
					+ HtmlUtils.htmlEscape(validateDisplayField(seq
							.getPartNumber()
							+ "/" + seq.getPartDesc())) + "</b>"));
			itemNumber.addAttribute("style", "width: 50%; text-align: center;");
			sequence = new TableData(new SimpleText("Sequence : <b>"
					+ HtmlUtils.htmlEscape(validateDisplayField(seq
							.getSequence())) + "</b>"));
			sequence.addAttribute("style", "width: 50%; text-align: center;");
		}

		// innerRow1.addTableData(facility);
		// innerRow1.addTableData(moNumber);
		if (seqActFlag) {
			innerRow1.addTableData(itemNumber);
			innerRow1.addTableData(sequence);
		}
		innerTable1.addTableRow(innerRow1);
		return innerTable1;
	}

	private Table getHeaderTableForActivities() {
		TableHeaderData employeeIdHeaderData = new TableHeaderData(
				new SimpleText("Emp ID"));
		employeeIdHeaderData.addAttribute("style", "width: 8%");
		TableHeaderData employeeNameHeaderData = new TableHeaderData(
				new SimpleText("Emp Name"));
		employeeNameHeaderData.addAttribute("style", "width: 13%");
		TableHeaderData assetNoHeaderData = new TableHeaderData(new SimpleText(
				"Asset No"));
		assetNoHeaderData.addAttribute("style", "width: 9%");
		TableHeaderData assetDescHeaderData = new TableHeaderData(
				new SimpleText("Asset Description"));
		assetDescHeaderData.addAttribute("style", "width: 14%");
		TableHeaderData activityHeaderData = new TableHeaderData(
				new SimpleText("Activity"));
		activityHeaderData.addAttribute("style", "width: 7%");
		TableHeaderData qtyCompletedHeaderData = new TableHeaderData(
				new SimpleText("Qty Completed"));
		qtyCompletedHeaderData.addAttribute("style", "width: 12%");
		TableHeaderData logOnHeaderData = new TableHeaderData(new SimpleText(
				"Logon Date/Time"));
		logOnHeaderData.addAttribute("style", "width: 17%");
		TableHeaderData logOffHeaderData = new TableHeaderData(new SimpleText(
				"Logoff Date/Time"));
		logOffHeaderData.addAttribute("style", "width: 17%");

		TableHeaderData editHeaderData = new TableHeaderData(new SimpleText(
				"Edit"));
		editHeaderData.addAttribute("style", "width: 4%");

		ArrayList<TableHeaderData> headers = new ArrayList<TableHeaderData>();
		headers.add(employeeIdHeaderData);
		headers.add(employeeNameHeaderData);
		headers.add(assetNoHeaderData);
		headers.add(assetDescHeaderData);
		headers.add(activityHeaderData);
		headers.add(qtyCompletedHeaderData);
		headers.add(logOnHeaderData);
		headers.add(logOffHeaderData);
		headers.add(editHeaderData);
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
				"white-space: nowrap; word-spacing: normal; width: 920px");
		table.addTableHeaderAttribute("class", "fixedHeader");
		table.addTableBodyAttribute("class", "scrollContent");
		return table;
	}

	private Table getHeaderTableForSequence() {
		TableHeaderData sequenceHeaderData = new TableHeaderData(
				new SimpleText("Sequence"));
		sequenceHeaderData.addAttribute("style", "width: 9%");
		TableHeaderData wcCodeHeaderData = new TableHeaderData(new SimpleText(
				"WC Code"));
		wcCodeHeaderData.addAttribute("style", "width: 8%");
		TableHeaderData seqDescHeaderData = new TableHeaderData(new SimpleText(
				"Description"));
		seqDescHeaderData.addAttribute("style", "width: 27%");
		TableHeaderData setupStatHeaderData = new TableHeaderData(
				new SimpleText("Setup Status"));
		setupStatHeaderData.addAttribute("style", "width: 13%");
		TableHeaderData runStatHeaderData = new TableHeaderData(new SimpleText(
				"Run Status"));
		runStatHeaderData.addAttribute("style", "width: 10%");
		TableHeaderData completedDateHeaderData = new TableHeaderData(
				new SimpleText("Completed Date"));
		completedDateHeaderData.addAttribute("style", "width: 13%");
		TableHeaderData qtyCompletedHeaderData = new TableHeaderData(
				new SimpleText("Qty Completed"));
		qtyCompletedHeaderData.addAttribute("style", "width: 13%");

		TableHeaderData editHeaderData = new TableHeaderData(new SimpleText(
				"Edit"));
		editHeaderData.addAttribute("style", "width: 7%");

		ArrayList<TableHeaderData> headers = new ArrayList<TableHeaderData>();
		headers.add(sequenceHeaderData);
		headers.add(wcCodeHeaderData);
		headers.add(seqDescHeaderData);
		headers.add(setupStatHeaderData);
		headers.add(runStatHeaderData);
		headers.add(completedDateHeaderData);
		headers.add(qtyCompletedHeaderData);
		headers.add(editHeaderData);

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
				"white-space: nowrap; word-spacing: normal; width: 900px");
		table.addTableHeaderAttribute("class", "fixedHeader");
		table.addTableBodyAttribute("class", "scrollContent");
		return table;
	}

	/**
	 * @return
	 */
	public Table buildSequenceDetailsTable(ManufacturingOrder manufacturingOrder) {
		Table table = new Table();
		table.addAttribute("id", "normalTableNostripe");
		table.addAttribute("align", "left");
		table.addAttribute("cellSpacing", "0");
		table.addAttribute("cellPadding", "0");
		table.addAttribute("border", "1");
		table.addAttribute("class", "classContentTable");
		table.addAttribute("style",
				"white-space: nowrap; word-spacing: normal; width: 900px");
		table.addTableHeaderAttribute("class", "fixedHeader");
		table.addTableBodyAttribute("class", "scrollContent");

		Image image = null;

		Anchor anchor = null;

		HashMap<String, String> statusMap = new HashMap<String, String>();
		statusMap.put(webConstants.STATUS_COMPLETE, webConstants.COMPLETED_STR);
		statusMap.put(webConstants.STATUS_INPROGRESS,
				webConstants.INPROGRESS_STR);
		statusMap.put(webConstants.STATUS_NOT_STARTED,
				webConstants.NOTSTARTED_STR);

		String sequenceValue = null;
		if (manufacturingOrder.getModifyMOs() != null
				&& manufacturingOrder.getModifyMOs().size() > 0) {
			int count = 0;
			for (Sequence seq : manufacturingOrder.getModifyMOs()) {
				TableRow row = new TableRow();
				sequenceValue = seq.getSequence();

				TableData sequence = new TableData(new SimpleText(HtmlUtils
						.htmlEscape(validateDisplayField(seq.getSequence()))));
				sequence.addAttribute("style", "width: 9%");

				TableData wcCode = new TableData(new SimpleText(HtmlUtils
						.htmlEscape(validateDisplayField(seq
								.getWorkCenterCode()))));
				wcCode.addAttribute("style", "width: 8%");

				TableData seqDesc = new TableData(new SimpleText(HtmlUtils
						.htmlEscape(validateDisplayField(seq
								.getSequenceDescription()))));
				seqDesc.addAttribute("style", "width: 27%");

				TableData setupStatus = null;
				if (statusMap.containsKey(seq.getSetupStatus())) {
					setupStatus = new TableData(new SimpleText(
							validateDisplayField(statusMap.get(seq
									.getSetupStatus()))));
				} else if (seq.getSetupStatus() != null) {
					setupStatus = new TableData(new SimpleText(seq
							.getSetupStatus()));
				} else {
					setupStatus = new TableData(new SimpleText(EMPTY_STRING));
				}
				setupStatus.addAttribute("style", "width: 13%");

				TableData runStatus = null;
				if (statusMap.containsKey(seq.getRunStatus())) {
					runStatus = new TableData(new SimpleText(
							validateDisplayField(statusMap.get(seq
									.getRunStatus()))));
				} else if (seq.getRunStatus() != null) {
					runStatus = new TableData(
							new SimpleText(seq.getRunStatus()));
				} else {
					runStatus = new TableData(new SimpleText(EMPTY_STRING));
				}
				runStatus.addAttribute("style", "width: 10%");

				String completeDate = getDateAsString(seq.getCompletedDate());
				TableData completedDate = new TableData(
						new SimpleText(HtmlUtils
								.htmlEscape(validateDisplayField(completeDate))));
				completedDate.addAttribute("style", "width: 13%");

				TableData qtyCompleted = new TableData(new SimpleText(HtmlUtils
						.htmlEscape(validateDisplayField(String
								.valueOf((int) seq.getQtyCompleted())))));
				qtyCompleted.addAttribute("style", "width: 13%");

				// InputField field = new InputField("edit", "Edit",
				// InputType.BUTTON);
				String flag = null;

				if ((seq.getSequenceStatus() != null && seq.getSequenceStatus()
						.equalsIgnoreCase(webConstants.STATUS_NOT_STARTED))
						|| (seq.getStatus() != null && Integer.parseInt(seq
								.getStatus()) <= webConstants.STATUS_NOT_STARTED_M3)) {
					// set the new flag as 'Y'
					flag = webConstants.YES;
				} else {
					flag = webConstants.NO;
				}

				System.out.println("sequence no:" + sequenceValue);
				image = new Image("static/images/change.png", "Edit");
				image.addAttribute("border", "0");
				image.addAttribute("width", "15");
				image.addAttribute("height", "15");
				image.addAttribute("onClick",
						"javascript:editSequenceDetails('" + sequenceValue
								+ "', '" + flag + "');");
				image.addAttribute("style", "cursor:pointer");

				// link =
				// "correctionInterfaceModifyMO.htm?currSequence="+seq.getSequence()+"&userAction=displayActivityDetails";
				anchor = new Anchor(validateDisplayField("#"), image);

				TableData editSeqAct = null;
				// If the planning area of the sequence is REVIEW or OUTSIDE
				// edit link should be disabled
				if (canEditThisOperation(seq)) {
					editSeqAct = new TableData(anchor);
				} else {
					editSeqAct = new TableData(new SimpleText(SPACE));
				}
				editSeqAct.addAttribute("style", "width: 7%");
				editSeqAct.addAttribute("cellpadding", "0");
				editSeqAct.addAttribute("cellspacing", "0");

				row.addTableData(sequence);
				row.addTableData(wcCode);
				row.addTableData(seqDesc);
				row.addTableData(setupStatus);
				row.addTableData(runStatus);
				row.addTableData(completedDate);
				row.addTableData(qtyCompleted);
				row.addTableData(editSeqAct);

				if (count % 2 == 0) {
					row.addAttribute("class", "normalRow");
				} else {
					row.addAttribute("class", "alternateRow");
				}
				table.addTableRow(row);
				count++;
				// System.out.println(row.render());
			}
		}

		return table;
	}

	private String getDateAsString(Date date) {
		if (date == null) {
			return StringUtils.EMPTY;
		}
		return String.valueOf(date);
	}

	/**
	 * To identify the planning area of the sequence is REVIEW or OUTSIDE
	 * 
	 * @param seq
	 * @return
	 */
	private boolean canEditThisOperation(Sequence seq) {
		return seq.getPlanningArea() == null
				|| (!seq.getPlanningArea().contains(
						webConstants.PLANNING_AREA_REVIEW) && !seq
						.getPlanningArea().contains(
								webConstants.PLANNING_AREA_OUTSIDE));
	}

	/**
	 * @return
	 */
	public Table buildSequenceActivityDetailsTable(
			ManufacturingOrder manufacturingOrder) {
		Table table = new Table();
		table.addAttribute("id", "normalTableNostripe");
		table.addAttribute("align", "left");
		table.addAttribute("cellSpacing", "0");
		table.addAttribute("cellPadding", "0");
		table.addAttribute("border", "1");
		table.addAttribute("class", "classContentTable");
		table.addAttribute("style",
				"white-space: nowrap; word-spacing: normal; width: 920px");
		table.addTableHeaderAttribute("class", "fixedHeader");
		table.addTableBodyAttribute("class", "scrollContent");

		Image image = null;
		Anchor anchor = null;

		HashMap<String, String> statusMap = new HashMap<String, String>();
		statusMap.put(webConstants.RUN, webConstants.RUN_STR);
		statusMap.put(webConstants.SETUP, webConstants.SETUP_STR);
		statusMap.put(webConstants.PAUSE, webConstants.PAUSE_STR);
		statusMap.put(webConstants.RETOOL, webConstants.RETOOL_STR);

		Sequence seq = manufacturingOrder.getSelectedSequeuce();
		int index = 0;

		if (seq.getSeqActivityDetails() != null
				&& seq.getSeqActivityDetails().size() > 0) {
			int count = 0;
			for (SequenceActivity seqAct : seq.getSeqActivityDetails()) {
				TableRow row = new TableRow();

				TableData employeeId = new TableData(new SimpleText(HtmlUtils
						.htmlEscape(validateDisplayField(seqAct
								.getEmployeeNumber()))));
				employeeId.addAttribute("style", "width: 8%;");

				TableData employeeName = new TableData(
						new SimpleText(
								HtmlUtils
										.htmlEscape(validateDisplayField((String) manufacturingOrder
												.getEmployeeMap()
												.get(seqAct.getEmployeeNumber())))));
				employeeName.addAttribute("style", "width: 13%;");

				TableData assetNo = new TableData(new SimpleText(HtmlUtils
						.htmlEscape(validateDisplayField(seqAct
								.getAssetNumber()))));
				assetNo.addAttribute("style", "width: 8%;");

				TableData assetDesc = new TableData(new SimpleText(HtmlUtils
						.htmlEscape(validateDisplayField(seq.getAssetMap().get(
								seqAct.getAssetNumber())))));
				assetDesc.addAttribute("style", "width: 14%;");

				TableData activity = new TableData(new SimpleText(HtmlUtils
						.htmlEscape(validateDisplayField(statusMap.get(seqAct
								.getActivity())))));
				activity.addAttribute("style", "width: 7%;");

				TableData qtyCompleted = new TableData(new SimpleText(HtmlUtils
						.htmlEscape(validateDisplayField(String.valueOf(seqAct
								.getQtyCompleted())))));
				qtyCompleted.addAttribute("style", "width: 12%;");

				TableData logonDate = new TableData(new SimpleText(HtmlUtils
						.htmlEscape(validateDisplayField(DateUtil.uiDateFormat(
								seqAct.getLogonDate(), DEFAULT_DATE_FORMAT)))));
				logonDate.addAttribute("style", "width: 17%;");

				TableData logoffDate = new TableData(new SimpleText(HtmlUtils
						.htmlEscape(validateDisplayField(DateUtil
								.dateAsDefaultString(seqAct.getLogoffDate(),
										DASH)))));
				logonDate.addAttribute("style", "width: 17%;");

				image = new Image("static/images/change.png", "Edit");
				image.addAttribute("border", "0");
				image.addAttribute("onClick",
						"javascript:editActivityDetails('" + index + "');");
				image.addAttribute("style", "cursor:pointer");
				// link =
				// "correctionInterfaceModifyMO.htm?currSeqActivityIndex="+index+"&userAction=editActivityDetails";
				anchor = new Anchor("#", image);
				System.out.println("anchor:" + anchor);
				TableData editSeqAct = new TableData(anchor);
				editSeqAct.addAttribute("style", "width: 4%");

				row.addTableData(employeeId);
				row.addTableData(employeeName);
				row.addTableData(assetNo);
				row.addTableData(assetDesc);
				row.addTableData(activity);
				row.addTableData(qtyCompleted);
				row.addTableData(logonDate);
				row.addTableData(logoffDate);
				row.addTableData(editSeqAct);

				if (count % 2 == 0) {
					row.addAttribute("class", "normalRow");
				} else {
					row.addAttribute("class", "alternateRow");
				}
				table.addTableRow(row);
				count++;
				System.out.println(row.render());

				index++;
			}
		}

		// add the buttons here itself

		/*
		 * Table innertable2 = new Table(); innertable2.addAttribute("id",
		 * "normalTableNostripe2"); innertable2.addAttribute("align", "center");
		 * innertable2.addAttribute("cellSpacing", "0");
		 * innertable2.addAttribute("cellPadding", "0");
		 * innertable2.addAttribute("border", "0");
		 * innertable2.addAttribute("style",
		 * "white-space: nowrap; word-spacing: normal; width: 60px; border-bottom-color: white; border-left-color: white"
		 * ); TableRow innertable2Row1 = new TableRow(); InputField
		 * btnAddMissingInfo = new InputField("addMissingInfo",
		 * "addMissingInfo", InputType.BUTTON);
		 * btnAddMissingInfo.addAttribute("onclick",
		 * "javascript:addMissingInfo"); TableData buttonAddMissingInfo = new
		 * TableData(btnAddMissingInfo);
		 * buttonAddMissingInfo.addAttribute("style"
		 * ,"border-bottom-color: white; border-left-color: white"); InputField
		 * btnCancel = new InputField("cancel", "cancel", InputType.BUTTON);
		 * btnCancel.addAttribute("onclick", "javascript:cancel()"); TableData
		 * buttonCancel = new TableData(btnCancel);
		 * buttonCancel.addAttribute("style"
		 * ,"border-bottom-color: white; border-left-color: white");
		 * innertable2Row1.addTableData(buttonAddMissingInfo);
		 * innertable2Row1.addTableData(buttonCancel);
		 * innertable2Row1.addAttribute("align", "center;");
		 * innertable2.addTableRow(innertable2Row1); TableData outerTableDate2 =
		 * new TableData(innertable2);
		 * 
		 * outerTableRow1.addTableData(outerTableDate1);
		 * outerTableRow2.addTableData(outerTableDate2);
		 * outerTableRow2.addAttribute("align", "center;");
		 * table.addTableRow(outerTableRow1); table.addTableRow(outerTableRow2);
		 */
		return table;
	}

	private Table formEmployeeInfoTable(ManufacturingOrder dto) {

		System.out
				.println("formEmployeeInfoTable.................................................");
		Sequence seq = dto.getSelectedSequeuce();
		SequenceActivity seqAct = seq.getSelectedSeqActivity();

		if (seqAct == null) {
			seqAct = new SequenceActivity();
			seq.setSelectedSeqActivity(seqAct);

		} else {
			seqAct.setKronosPunchIn(seq.getKronosPunchIn());
			seqAct.setKronosPunchOut(seq.getKronosPunchOut());
		}

		Table table = new Table();
		// table.addAttribute("id", "normalTableNostripe");
		table.addAttribute("align", "center");
		table.addAttribute("cellSpacing", "0");
		table.addAttribute("cellPadding", "0");
		table.addAttribute("border", "1");
		// table.addAttribute("class", "classContentTable");
		table.addAttribute("style",
				"white-space: nowrap; word-spacing: normal; width: 900px");

		TableRow row1 = new TableRow();
		TableRow row2 = new TableRow();

		TableData lblStampNum = new TableData(new SimpleText("Stamp #"));
		TableData lblRouterDate = new TableData(new SimpleText(
				"Date(from router)"));
		TableData lblEmployeeNum = new TableData(new SimpleText(
				"Employee Number"));
		TableData lblEmployeeName = new TableData(new SimpleText(
				"Employee Name"));
		TableData lblKronosPunchIn = new TableData(new SimpleText(
				"Kronos Punch In"));
		TableData lblKronosPunchOut = new TableData(new SimpleText(
				"Kronos Punch Out"));

		row1.addTableData(lblStampNum);
		row1.addTableData(lblRouterDate);
		row1.addTableData(lblEmployeeNum);
		row1.addTableData(lblEmployeeName);
		row1.addTableData(lblKronosPunchIn);
		row1.addTableData(lblKronosPunchOut);
		row1.addAttribute("style", "font-weight: bold");

		InputField txtStampNo = new InputField("stampNo", StringUtils
				.defaultString(seqAct.getStampNo()), InputType.TEXT);
		txtStampNo.addAttribute("id", "stampNo");
		TableData txtStampNum = new TableData(txtStampNo);

		InputField txtRouterDt = new InputField("date", DateUtil
				.dateAsDefaultString(seqAct.getRouterDate()), InputType.TEXT);
		txtRouterDt.addAttribute("id", "date");
		txtRouterDt.addAttribute("readOnly", "readOnly");
		TableData txtRouterDate = new TableData(txtRouterDt);
		Image dateImage = new Image("static/images/cal.gif", "Select Date");

		dateImage.addAttribute("onclick",
				"NewCssCal('date','mmddyyyy','dropdown',true,12,false)");
		dateImage.addAttribute("style", "cursor: pointer;");
		TableData btnDate = new TableData(dateImage);

		TableRow dateTableRow = new TableRow();
		dateTableRow.addTableData(txtRouterDate);
		dateTableRow.addTableData(btnDate);

		Table dateTable = new Table();
		dateTable.addTableRow(dateTableRow);

		TableData dateTableData = new TableData(dateTable);

		InputField txtEmployeeNo = new InputField("employeeNo", StringUtils
				.defaultString(seqAct.getEmployeeNumber()), InputType.TEXT);
		txtEmployeeNo.addAttribute("id", "employeeNo");
		txtEmployeeNo.addAttribute("onChange", "javascript:employeeChange();");
		TableData txtEmployeeNum = new TableData(txtEmployeeNo);

		TableData txtEmployeeName = new TableData(new SimpleText(SPACE));

		if (dto.getEmployeeMap() != null && !dto.getEmployeeMap().isEmpty()) {
			String name = (String) dto.getEmployeeMap().get(
					seqAct.getEmployeeNumber());
			if (dto.getEmployeeMap().containsKey(seqAct.getEmployeeNumber())) {
				txtEmployeeName = new TableData(new SimpleText(StringUtils
						.defaultString(name, SPACE)));
			} else {
				// its not a valid emp number.. add the error
				seqAct.addError("Invalid Employee Number");
			}
		}

		TableData txtKronosPunchIn = new TableData(new SimpleText(HtmlUtils
				.htmlEscape(validateDisplayField(DateUtil.dateAsDefaultString(
						seqAct.getKronosPunchIn(), DASH)))));

		TableData txtKronosPunchOut = new TableData(new SimpleText(HtmlUtils
				.htmlEscape(validateDisplayField(DateUtil.dateAsDefaultString(
						seqAct.getKronosPunchOut(), DASH)))));

		row2.addTableData(txtStampNum);
		row2.addTableData(dateTableData);
		row2.addTableData(txtEmployeeNum);
		row2.addTableData(txtEmployeeName);
		row2.addTableData(txtKronosPunchIn);
		row2.addTableData(txtKronosPunchOut);

		System.out.println("row1" + row1.render());
		System.out.println("row2" + row2.render());

		table.addTableRow(row1);
		table.addTableRow(row2);

		return table;

	}

	private Table formEmployeeNameTable(ManufacturingOrder dto) {
		System.out
				.println("formEmployeeNameTable....................................................................");
		Sequence seq = dto.getSelectedSequeuce();
		SequenceActivity seqAct = seq.getSelectedSeqActivity();

		if (seqAct == null) {
			seqAct = new SequenceActivity();
			seq.setSelectedSeqActivity(seqAct);
		}

		Table table = new Table();
		table.addAttribute("id", "normalTableNostripe");
		table.addAttribute("align", "center");
		table.addAttribute("cellSpacing", "0");
		table.addAttribute("cellPadding", "0");
		table.addAttribute("border", "1");
		table.addAttribute("class", "classContentTable");
		table.addAttribute("style",
				"white-space: nowrap; word-spacing: normal; width: 900px");
		table.addTableHeaderAttribute("class", "fixedHeader");
		table.addTableBodyAttribute("class", "scrollContent");

		TableRow row1 = new TableRow();
		TableRow row2 = new TableRow();

		TableHeaderData lblPartNumber = new TableHeaderData(new SimpleText(
				"Part Number"));
		TableHeaderData lblSeqNumber = new TableHeaderData(new SimpleText(
				"Sequence"));
		TableHeaderData lblSeqDesc = new TableHeaderData(new SimpleText(
				"Sequence Description"));
		TableHeaderData lblEmployeeNum = new TableHeaderData(new SimpleText(
				"Employee Number"));
		// lblEmployeeNum.addAttribute("style", "width: 50%");
		TableHeaderData lblEmployeeName = new TableHeaderData(new SimpleText(
				"Employee Name"));
		TableHeaderData lblKronosPunchIn = new TableHeaderData(new SimpleText(
				"Kronos Punch In"));
		TableHeaderData lblKronosPunchOut = new TableHeaderData(new SimpleText(
				"Kronos Punch Out"));

		// lblEmployeeName.addAttribute("style", "width: 50%");

		List<TableHeaderData> listHeader = new ArrayList<TableHeaderData>();
		listHeader.add(lblPartNumber);
		listHeader.add(lblSeqNumber);
		listHeader.add(lblSeqDesc);
		listHeader.add(lblEmployeeNum);
		listHeader.add(lblEmployeeName);
		listHeader.add(lblKronosPunchIn);
		listHeader.add(lblKronosPunchOut);

		TableHeader tableHeader = new TableHeader(listHeader);

		TableData txtPartNumber = new TableData(new SimpleText(
				validateDisplayField(seq.getPartNumber() + "/"
						+ seq.getPartDesc())));

		TableData txtSequence = new TableData(new SimpleText(
				validateDisplayField(seq.getSequence())));

		TableData txtSequenceDesc = new TableData(new SimpleText(
				validateDisplayField(getDescription(seq
						.getSequenceDescription()))));

		SimpleText txtEmployeeNo;

		if (seqAct.getEmployeeNumber() != null) {
			txtEmployeeNo = new SimpleText(seqAct.getEmployeeNumber());
		} else {
			txtEmployeeNo = new SimpleText(SPACE);
		}
		TableData txtEmployeeNum = new TableData(txtEmployeeNo);

		SimpleText txtEmployeeName = null;

		if (dto.getEmployeeMap() != null && !dto.getEmployeeMap().isEmpty()) {
			txtEmployeeName = new SimpleText(StringUtils.defaultString(
					(String) dto.getEmployeeMap().get(
							seqAct.getEmployeeNumber()), SPACE));
		} else {
			txtEmployeeName = new SimpleText(SPACE);
		}

		TableData txtKronosPunchIn = new TableData(new SimpleText(HtmlUtils
				.htmlEscape(validateDisplayField(DateUtil.dateAsDefaultString(
						seqAct.getLogoffDate(), DASH)))));

		TableData txtKronosPunchOut = new TableData(new SimpleText(HtmlUtils
				.htmlEscape(validateDisplayField(DateUtil.dateAsDefaultString(
						seqAct.getLogoffDate(), DASH)))));
		TableData txtEmployeeData = new TableData(txtEmployeeName);
		row2.addTableData(txtPartNumber);
		row2.addTableData(txtSequence);
		row2.addTableData(txtSequenceDesc);
		row2.addTableData(txtEmployeeNum);
		row2.addTableData(txtEmployeeData);
		row2.addTableData(txtKronosPunchIn);
		row2.addTableData(txtKronosPunchOut);

		System.out.println("row1" + row1.render());
		// System.out.println("row2" + row2.render());

		table.setTableHeader(tableHeader);
		table.addTableRow(row2);

		return table;
	}

	private String getDescription(String inputString) {
		if (inputString == null) {
			return EMPTY_STRING;
		}
		return "(" + inputString + ")";
	}

	private Table formActivityInfoTable(ManufacturingOrder dto,
			String pauseFlag, String newFlag) {

		Sequence seq = dto.getSelectedSequeuce();
		SequenceActivity seqAct = seq.getSelectedSeqActivity();

		if (seqAct == null) {
			seqAct = new SequenceActivity();
			seq.setSelectedSeqActivity(seqAct);
		}

		Table table = new Table();
		// table.addAttribute("id", "normalTableNostripe");
		table.addAttribute("align", "center");
		// table.addAttribute("cellSpacing", "0");
		// table.addAttribute("cellPadding", "0");
		table.addAttribute("border", "1");
		table.addAttribute("class", "classContentTable");
		table
				.addAttribute("style",
						"white-space: nowrap; word-spacing: normal; width: 600px; text-align: left;");

		Table innertable = new Table();
		// innertable.addAttribute("id", "normalTableNostripe1");
		innertable.addAttribute("align", "center");
		// innertable.addAttribute("cellSpacing", "0");
		// innertable.addAttribute("cellPadding", "0");
		innertable.addAttribute("border", "0");
		// innertable.addAttribute("class", "classContentTable");
		innertable.addAttribute("style",
				"white-space: nowrap; word-spacing: normal; width: 600px");

		TableRow row1 = new TableRow();
		TableRow row2 = new TableRow();
		TableRow row3 = new TableRow();
		TableRow row4 = new TableRow();
		TableRow row5 = new TableRow();

		TableRow outerTableRow1 = new TableRow();
		TableRow outerTableRow2 = new TableRow();

		/* Login Fields */
		TableData lblLoginTime = new TableData(new SimpleText("Login Time"));

		System.out
				.println("seqAct.getLogonDate()..................................................................................... "
						+ seqAct.getLogonDate());
		InputField txtLoginDate = new InputField("loginTime", DateUtil
				.dateAsDefaultString(seqAct.getLogonDate()), InputType.TEXT);
		txtLoginDate.addAttribute("id", "loginTime");
		txtLoginDate.addAttribute("readOnly", "readOnly");
		TableData txtLoginTime = new TableData(txtLoginDate);

		Image dateImage = new Image("static/images/cal.gif", "Select Date");
		dateImage.addAttribute("onclick",
				"NewCssCal('loginTime','mmddyyyy','dropdown',true,12,false)");
		dateImage.addAttribute("style", "cursor: pointer;");
		TableData btnLogonDate = new TableData(dateImage);

		TableRow loginDateImageRow = new TableRow();
		loginDateImageRow.addTableData(txtLoginTime);
		loginDateImageRow.addTableData(btnLogonDate);

		Table loginDateImageTable = new Table();
		loginDateImageTable.addTableRow(loginDateImageRow);

		TableData loginDateImageData = new TableData(loginDateImageTable);

		/* Log off fields */
		TableData lblLogoffTime = new TableData(new SimpleText("Logoff Time"));

		InputField txtLogoffDate = new InputField("logoffTime", DateUtil
				.dateAsDefaultString(seqAct.getLogoffDate()), InputType.TEXT);
		txtLogoffDate.addAttribute("id", "logoffTime");
		txtLogoffDate.addAttribute("readOnly", "readOnly");
		TableData txtLogoffTime = new TableData(txtLogoffDate);

		dateImage = new Image("static/images/cal.gif", "Select Date");
		dateImage.addAttribute("onclick",
				"NewCssCal('logoffTime','mmddyyyy','dropdown',true,12,false)");
		dateImage.addAttribute("style", "cursor: pointer;");
		TableData btnLogoffDate = new TableData(dateImage);

		TableRow logoffDateImageRow = new TableRow();
		logoffDateImageRow.addTableData(txtLogoffTime);
		logoffDateImageRow.addTableData(btnLogoffDate);

		Table logoffDateImageTable = new Table();
		logoffDateImageTable.addTableRow(logoffDateImageRow);

		TableData logoffDateImageData = new TableData(logoffDateImageTable);

		row1.addTableData(lblLoginTime);
		row1.addTableData(loginDateImageData);
		row1.addTableData(lblLogoffTime);
		row1.addTableData(logoffDateImageData);

		// formin second row

		TableData lblActivity = new TableData(new SimpleText("Activity"));
		InputField txtActivity = new InputField("activity", StringUtils
				.defaultString(seqAct.getActivity()), InputType.TEXT);
		txtActivity.addAttribute("id", "activity");
		// TableData txtActivityCode = new TableData(txtActivity);
		HashMap<String, String> activityMap = new HashMap<String, String>();
		activityMap.put(webConstants.RUN, webConstants.RUN_STR);
		activityMap.put(webConstants.SETUP, webConstants.SETUP_STR);
		activityMap.put(webConstants.PAUSE, webConstants.PAUSE_STR);
		activityMap.put(webConstants.RETOOL, webConstants.RETOOL_STR);

		List<String> keySet = new ArrayList<String>();
		keySet.add(webConstants.RUN);
		keySet.add(webConstants.SETUP);
		keySet.add(webConstants.PAUSE);
		keySet.add(webConstants.RETOOL);
		Iterator iterator = keySet.iterator();
		StringBuffer sb = new StringBuffer();
		String key = null;
		/*
		 * sb.append(
		 * "<select name=\"activity\" id=\"activity\" onchange=\"javascript:activityChange()\">"
		 * ); sb.append("<option value=\"R\">Run</option>");
		 * sb.append("<option value=\"T\">Retool</option>");
		 * sb.append("<option value=\"S\">Setup</option>");
		 * sb.append("<option value=\"P\">Pause</option>");
		 */

		Select activitySel = new Select("activity");
		Option option = null;
		boolean runStatus = false;
		if (seq.getSeqActivityDetails() != null) {
			for (SequenceActivity sequenceActivity : seq
					.getSeqActivityDetails()) {
				if (sequenceActivity.getActivity().equals(webConstants.RUN)) {
					runStatus = true;
					break;
				}
			}
		}
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			option = new Option(key, activityMap.get(key));

			if (newFlag.equals("N")) {
				activitySel.addOption(option);
			} else {
				if (runStatus) {
					// if (key.equals(webConstants.PAUSE) ||
					// key.equals(webConstants.RETOOL)) {
					activitySel.addOption(option);
					// }
				} else {
					if (!(key.equals(webConstants.PAUSE) || key
							.equals(webConstants.RETOOL))) {
						activitySel.addOption(option);
					}
				}
			}

			if (seqAct.getActivity() != null
					&& seqAct.getActivity().equalsIgnoreCase(key)) {
				option.setSelected(true);
			}
		}
		activitySel.addAttribute("onChange", "javascript:activityChange()");
		if (newFlag.equals("N")) {
			activitySel.addAttribute("disabled", "disabled");
		}

		System.out.println("activity:" + activitySel.render());
		TableData txtActivityCode = new TableData(activitySel);

		TableData reasonData = new TableData(new SimpleText(SPACE));
		reasonData.addAttribute("colspan", "2");
		reasonData.addAttribute("id", "reasonContainer");

		// TableData reasonData = new TableData(new SimpleText(" "));
		//
		//
		// sb = new StringBuffer(" ");
		//
		// Select reasonSel = new Select("reasonCode");
		//
		// if (pauseFlag != null &&
		// pauseFlag.equalsIgnoreCase(webConstants.PAUSE)) {
		// // since activity is P need to show the reason code - this will be
		// done only for on change
		// if(seqAct.getActivity().equalsIgnoreCase(webConstants.PAUSE)) {
		//
		// lblReason = new TableData(new SimpleText("Reason"));
		// ArrayList<MasterData> reasonList = dto.getPauseReasons();
		//
		// for (MasterData data: reasonList) {
		// option = new Option(data.getCode(), data.getDesc());
		// reasonSel.addOption(option);
		// if (seqAct.getReasonCode() != null &&
		// seqAct.getReasonCode().equalsIgnoreCase(data.getCode())) {
		// option.setSelected(true);
		// }
		// }
		// reasonData = new TableData(reasonSel);
		// }
		// }

		TableData lblAssetNo = new TableData(new SimpleText("Asset #"));
		InputField txtAssetNum = new InputField("assetNo", HtmlUtils
				.htmlEscapeDecimal(StringUtils.defaultString(seqAct
						.getAssetNumber())), InputType.TEXT);
		txtAssetNum.addAttribute("id", "assetNo");
		// TableData txtAssetNo= new TableData(txtAssetNum);

		sb = new StringBuffer();
		// asset # dropdown for the wc

		Select assetSel = new Select("assetNo");
		String assetDesc = EMPTY_STRING;
		HashMap<String, String> assetMapForDesc = new HashMap<String, String>();
		if (seq.getAssetsListForWC() != null
				&& seq.getAssetsListForWC().size() > 0) {
			option = new Option(EMPTY_STRING, EMPTY_STRING);
			assetSel.addOption(option);
			assetMapForDesc = new HashMap<String, String>();
			for (Asset asset : seq.getAssetsListForWC()) {
				assetMapForDesc.put(asset.getAssetNumber(), asset
						.getAssetDescNumber().substring(
								asset.getAssetDescNumber().indexOf(DASH) + 1));
				option = new Option(HtmlUtils.htmlEscapeDecimal(asset
						.getAssetNumber()), HtmlUtils.htmlEscapeDecimal(asset
						.getAssetDescNumber()));
				assetSel.addOption(option);
				if (seqAct.getAssetNumber() != null
						&& (seqAct.getAssetNumber().equalsIgnoreCase(asset
								.getAssetNumber()))) {
					option.setSelected(true);
					// assetDesc = asset.getAssetDescNumber();
				}
			}
			assetSel.addAttribute("onChange", "javascript:assetChange();");
		}
		if (!newFlag.equals("N")
				|| (newFlag.equals("N") && !seqAct.getActivity().equals(
						webConstants.SETUP))) {
			assetSel.addAttribute("disabled", "disabled");
		}

		// System.out.println("asset:" + assetSel.render());
		seq.setAssetMap(assetMapForDesc);
		TableData txtAssetNo = new TableData(assetSel);

		row2.addTableData(lblActivity);
		row2.addTableData(txtActivityCode);
		// row2.addTableData(lblReason);
		// row2.addTableData(reasonData);
		row2.addTableData(lblAssetNo);
		row2.addTableData(txtAssetNo);

		// forming third row

		TableData lblQtyCompleted = new TableData(new SimpleText(
				"Qty Completed"));
		InputField txtQtyCompl = new InputField("qtyCompleted", new Integer(
				seqAct.getQtyCompleted()).toString(), InputType.TEXT);
		txtQtyCompl.addAttribute("id", "qtyCompleted");
		TableData txtQtyCompleted = new TableData(txtQtyCompl);

		TableData lblAssetDesc = new TableData(new SimpleText("Asset Desc"));
		// InputField txtAssetDesc = new InputField("assetDesc",
		// seqAct.getAssetDesc() == null ? "" : seqAct.getAssetDesc(),
		// InputType.TEXT);
		// txtAssetDesc.addAttribute("id", "assetDesc");
		// TableData txtAssetDescription = new TableData(txtAssetDesc);
		if (assetMapForDesc != null && !assetMapForDesc.isEmpty()) {
			assetDesc = (String) assetMapForDesc.get(seqAct.getAssetNumber());
			System.out.println("asset desc:" + assetDesc);
		}
		InputField txtAssetDesc = new InputField("assetDesc",
				HtmlUtils.htmlEscapeDecimal(StringUtils.defaultString(
						assetDesc, SPACE)), InputType.TEXT);
		txtAssetDesc.addAttribute("readonly", "readonly");
		TableData txtAssetDescription = new TableData(txtAssetDesc);
		txtAssetDescription.addAttribute("id", "assetDescContainer");

		row3.addTableData(lblQtyCompleted);
		row3.addTableData(txtQtyCompleted);
		row3.addTableData(lblAssetDesc);
		row3.addTableData(txtAssetDescription);

		// forming fourth row

		TableData lblPPH = new TableData(new SimpleText("PPH"));
		double pphValue = 0.0;

		// if (seqAct.getLogoffDate() != null) {
		pphValue = Util.calculatePPH(seq.getRunTime(), seq.getPriceTimeQty());
		// }
		NumberFormat formatter = new DecimalFormat("#0.00");
		InputField txtPPHValue = new InputField("pphValue", String
				.valueOf(formatter.format(pphValue)), InputType.TEXT);
		txtPPHValue.addAttribute("readonly", "readonly");
		TableData txtPPH = new TableData(txtPPHValue);

		row4.addTableData(lblPPH);
		row4.addTableData(txtPPH);
		row4.addTableData(reasonData);
		// row4.addTableData(reasonData);

		// forming 5th row
		TableData lblSeqCompleteFlag = new TableData(new SimpleText(
				"Complete Sequence"));

		/*
		 * START - Fix for Sharepoint Item 1293 - 1 - Re-Opening a sequence
		 * makes it impossible to close
		 */

		String txtSeqCompFlag = "<input type=\"checkbox\" name=\"seqCompFlag\" value=\" \" onclick=\"validateCompleteSequence()\" "
				+ EMPTY_STRING
				+ getCheckedAttribute(dto.getSelectedSequeuce()
						.getSequenceStatus()) + "/>";

		/*
		 * END - Fix for Sharepoint Item 1293 - 1 Re-Opening a sequence makes it
		 * impossible to close
		 */

		TableData txtSeqCompleteFlag = new TableData(new SimpleText(
				txtSeqCompFlag));

		TableData emptyData = new TableData(new SimpleText(SPACE));

		row5.addTableData(lblSeqCompleteFlag);
		row5.addTableData(txtSeqCompleteFlag);
		row5.addTableData(emptyData);
		row5.addTableData(emptyData);

		System.out.println("row1:" + row1.render());
		System.out.println("row2:" + row2.render());
		System.out.println("row3:" + row3.render());
		System.out.println("row4:" + row4.render());
		System.out.println("row5:" + row5.render());

		innertable.addTableRow(row1);
		innertable.addTableRow(row2);
		innertable.addTableRow(row3);
		innertable.addTableRow(row4);
		innertable.addTableRow(row5);

		TableData outerTableDate1 = new TableData(innertable);

		// create the buttons here itself
		Table innertable2 = new Table();
		innertable2.addAttribute("id", "normalTableNostripe2");
		innertable2.addAttribute("align", "center");
		// innertable2.addAttribute("cellSpacing", "0");
		// innertable2.addAttribute("cellPadding", "0");
		innertable2.addAttribute("border", "0");
		innertable2
				.addAttribute(
						"style",
						"white-space: nowrap; word-spacing: normal; width: 60px; border-bottom-color: white; border-left-color: white");

		TableRow innertable2Row1 = new TableRow();

		int currentSeqCompletedQty = 0;
		if (seq.getSeqActivityDetails() != null) {
			for (SequenceActivity sequenceActivity : seq
					.getSeqActivityDetails()) {
				if (sequenceActivity.getActivityLogNumber() != 0
						&& seqAct.getActivityLogNumber() != sequenceActivity
								.getActivityLogNumber()) {
					currentSeqCompletedQty = sequenceActivity.getQtyCompleted();
				}
			}
		}

		Image updateImage = new Image("static/images/Confirm.gif", "Confirm");
		if (seq.isFirstSequence()) {
			updateImage.addAttribute("onclick", "javascript:confirmValues('"
					+ newFlag + "', '" + currentSeqCompletedQty + "', '"
					+ seq.getOrderQty() + "')");
		} else {
			updateImage.addAttribute("onclick", "javascript:confirmValues('"
					+ newFlag + "', '" + currentSeqCompletedQty + "', '"
					+ getPreviousSeqCompletedQty(seq, dto.getSequencesInM3())
					+ "')");
		}
		updateImage.addAttribute("style", "cursor: pointer;");
		TableData buttonUpdate = new TableData(updateImage);
		buttonUpdate.addAttribute("style",
				"border-bottom-color: white; border-left-color: white");

		Image cancelImage = new Image("static/images/cancel.gif", "Cancel");
		cancelImage.addAttribute("onclick", "javascript:cancelActDetails()");
		cancelImage.addAttribute("style", "cursor: pointer;");
		TableData buttonCancel = new TableData(cancelImage);
		buttonCancel.addAttribute("style",
				"border-bottom-color: white; border-left-color: white");

		innertable2Row1.addTableData(buttonUpdate);
		innertable2Row1.addTableData(buttonCancel);
		innertable2Row1.addAttribute("align", "center;");

		innertable2.addTableRow(innertable2Row1);
		TableData outerTableDate2 = new TableData(innertable2);

		outerTableRow1.addTableData(outerTableDate1);
		outerTableRow2.addTableData(outerTableDate2);
		outerTableRow2.addAttribute("align", "center;");
		table.addTableRow(outerTableRow1);
		table.addTableRow(outerTableRow2);

		return table;

	}

	private Table formActivityInfoTableForAdd(ManufacturingOrder dto,
			String pauseFlag, String newFlag) {

		Sequence seq = dto.getSelectedSequeuce();
		SequenceActivity seqAct = seq.getSelectedSeqActivity();

		if (seqAct == null) {
			seqAct = new SequenceActivity();
			seq.setSelectedSeqActivity(seqAct);
		}

		Table table = new Table();
		// table.addAttribute("id", "normalTableNostripe");
		table.addAttribute("align", "center");
		// table.addAttribute("cellSpacing", "0");
		// table.addAttribute("cellPadding", "0");
		table.addAttribute("border", "1");
		table.addAttribute("class", "classContentTable");
		table
				.addAttribute("style",
						"white-space: nowrap; word-spacing: normal; width: 600px; text-align: left;");

		Table innertable = new Table();
		// innertable.addAttribute("id", "normalTableNostripe1");
		innertable.addAttribute("align", "center");
		// innertable.addAttribute("cellSpacing", "0");
		// innertable.addAttribute("cellPadding", "0");
		innertable.addAttribute("border", "0");
		// innertable.addAttribute("class", "classContentTable");
		innertable.addAttribute("style",
				"white-space: nowrap; word-spacing: normal; width: 600px");

		TableRow row1 = new TableRow();
		TableRow row2 = new TableRow();
		TableRow row3 = new TableRow();
		TableRow row4 = new TableRow();
		TableRow row5 = new TableRow();

		TableRow outerTableRow1 = new TableRow();
		TableRow outerTableRow2 = new TableRow();

		/* Login Fields */
		TableData lblLoginTime = new TableData(new SimpleText("Login Time"));

		System.out
				.println("seqAct.getLogonDate()..................................................................................... "
						+ seqAct.getLogonDate());
		InputField txtLoginDate = new InputField("loginTime", DateUtil
				.dateAsDefaultString(seqAct.getLogonDate()), InputType.TEXT);
		txtLoginDate.addAttribute("id", "loginTime");
		txtLoginDate.addAttribute("readOnly", "readOnly");
		TableData txtLoginTime = new TableData(txtLoginDate);

		Image dateImage = new Image("static/images/cal.gif", "Select Date");
		dateImage.addAttribute("onclick",
				"NewCssCal('loginTime','mmddyyyy','dropdown',true,12,false)");
		dateImage.addAttribute("style", "cursor: pointer;");
		TableData btnLogonDate = new TableData(dateImage);

		TableRow loginDateImageRow = new TableRow();
		loginDateImageRow.addTableData(txtLoginTime);
		loginDateImageRow.addTableData(btnLogonDate);

		Table loginDateImageTable = new Table();
		loginDateImageTable.addTableRow(loginDateImageRow);

		TableData loginDateImageData = new TableData(loginDateImageTable);

		/* Log off fields */
		TableData lblLogoffTime = new TableData(new SimpleText("Logoff Time"));

		InputField txtLogoffDate = new InputField("logoffTime", DateUtil
				.dateAsDefaultString(seqAct.getLogoffDate()), InputType.TEXT);
		txtLogoffDate.addAttribute("id", "logoffTime");
		txtLogoffDate.addAttribute("readOnly", "readOnly");
		TableData txtLogoffTime = new TableData(txtLogoffDate);

		dateImage = new Image("static/images/cal.gif", "Select Date");
		dateImage.addAttribute("onclick",
				"NewCssCal('logoffTime','mmddyyyy','dropdown',true,12,false)");
		dateImage.addAttribute("style", "cursor: pointer;");
		TableData btnLogoffDate = new TableData(dateImage);

		TableRow logoffDateImageRow = new TableRow();
		logoffDateImageRow.addTableData(txtLogoffTime);
		logoffDateImageRow.addTableData(btnLogoffDate);

		Table logoffDateImageTable = new Table();
		logoffDateImageTable.addTableRow(logoffDateImageRow);

		TableData logoffDateImageData = new TableData(logoffDateImageTable);

		row1.addTableData(lblLoginTime);
		row1.addTableData(loginDateImageData);
		row1.addTableData(lblLogoffTime);
		row1.addTableData(logoffDateImageData);

		// formin second row

		TableData lblActivity = new TableData(new SimpleText("Activity"));
		InputField txtActivity = new InputField("activity", StringUtils
				.defaultString(seqAct.getActivity()), InputType.TEXT);
		txtActivity.addAttribute("id", "activity");
		// TableData txtActivityCode = new TableData(txtActivity);
		HashMap<String, String> activityMap = new HashMap<String, String>();
		activityMap.put(webConstants.RUN, webConstants.RUN_STR);
		activityMap.put(webConstants.SETUP, webConstants.SETUP_STR);
		// activityMap.put(webConstants.PAUSE, webConstants.PAUSE_STR);
		// activityMap.put(webConstants.RETOOL, webConstants.RETOOL_STR);

		List<String> keySet = new ArrayList<String>();
		keySet.add(webConstants.RUN);
		keySet.add(webConstants.SETUP);
		// keySet.add(webConstants.PAUSE);
		// keySet.add(webConstants.RETOOL);
		Iterator iterator = keySet.iterator();
		StringBuffer sb = new StringBuffer();
		String key = null;
		/*
		 * sb.append(
		 * "<select name=\"activity\" id=\"activity\" onchange=\"javascript:activityChange()\">"
		 * ); sb.append("<option value=\"R\">Run</option>");
		 * sb.append("<option value=\"T\">Retool</option>");
		 * sb.append("<option value=\"S\">Setup</option>");
		 * sb.append("<option value=\"P\">Pause</option>");
		 */

		Select activitySel = new Select("activity");
		Option option = null;
		boolean runStatus = false;
		if (seq.getSeqActivityDetails() != null) {
			for (SequenceActivity sequenceActivity : seq
					.getSeqActivityDetails()) {
				if (sequenceActivity.getActivity().equals(webConstants.RUN)) {
					runStatus = true;
					break;
				}
			}
		}
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			option = new Option(key, activityMap.get(key));

			if (newFlag.equals("N")) {
				activitySel.addOption(option);
			} else {
				if (runStatus) {
					// if (key.equals(webConstants.PAUSE) ||
					// key.equals(webConstants.RETOOL)) {
					activitySel.addOption(option);
					// }
				} else {
					if (!(key.equals(webConstants.PAUSE) || key
							.equals(webConstants.RETOOL))) {
						activitySel.addOption(option);
					}
				}
			}

			if (seqAct.getActivity() != null
					&& seqAct.getActivity().equalsIgnoreCase(key)) {
				option.setSelected(true);
			}
		}
		activitySel.addAttribute("onChange", "javascript:activityChange()");
		if (newFlag.equals("N")) {
			activitySel.addAttribute("disabled", "disabled");
		}

		System.out.println("activity:" + activitySel.render());
		TableData txtActivityCode = new TableData(activitySel);

		TableData reasonData = new TableData(new SimpleText(SPACE));
		reasonData.addAttribute("colspan", "2");
		reasonData.addAttribute("id", "reasonContainer");

		// TableData reasonData = new TableData(new SimpleText(" "));
		//
		//
		// sb = new StringBuffer(" ");
		//
		// Select reasonSel = new Select("reasonCode");
		//
		// if (pauseFlag != null &&
		// pauseFlag.equalsIgnoreCase(webConstants.PAUSE)) {
		// // since activity is P need to show the reason code - this will be
		// done only for on change
		// if(seqAct.getActivity().equalsIgnoreCase(webConstants.PAUSE)) {
		//
		// lblReason = new TableData(new SimpleText("Reason"));
		// ArrayList<MasterData> reasonList = dto.getPauseReasons();
		//
		// for (MasterData data: reasonList) {
		// option = new Option(data.getCode(), data.getDesc());
		// reasonSel.addOption(option);
		// if (seqAct.getReasonCode() != null &&
		// seqAct.getReasonCode().equalsIgnoreCase(data.getCode())) {
		// option.setSelected(true);
		// }
		// }
		// reasonData = new TableData(reasonSel);
		// }
		// }

		TableData lblAssetNo = new TableData(new SimpleText("Asset #"));
		InputField txtAssetNum = new InputField("assetNo", StringUtils
				.defaultString(seqAct.getAssetNumber()), InputType.TEXT);
		txtAssetNum.addAttribute("id", "assetNo");
		// TableData txtAssetNo= new TableData(txtAssetNum);

		sb = new StringBuffer();
		// asset # dropdown for the wc

		Select assetSel = new Select("assetNo");
		String assetDesc = EMPTY_STRING;
		HashMap<String, String> assetMapForDesc = new HashMap<String, String>();
		if (seq.getAssetsListForWC() != null
				&& seq.getAssetsListForWC().size() > 0) {
			option = new Option(EMPTY_STRING, EMPTY_STRING);
			assetSel.addOption(option);
			assetMapForDesc = new HashMap<String, String>();
			for (Asset asset : seq.getAssetsListForWC()) {
				assetMapForDesc.put(asset.getAssetNumber(), asset
						.getAssetDescNumber().substring(
								asset.getAssetDescNumber().indexOf(DASH) + 1));
				option = new Option(asset.getAssetNumber(), HtmlUtils
						.htmlEscape(asset.getAssetDescNumber()));
				assetSel.addOption(option);
				if (seqAct.getAssetNumber() != null
						&& (seqAct.getAssetNumber().equalsIgnoreCase(asset
								.getAssetNumber()))) {
					option.setSelected(true);
					// assetDesc = asset.getAssetDescNumber();
				}
			}
			assetSel.addAttribute("onChange", "javascript:assetChange();");
		}
		if (!newFlag.equals("N")
				|| (newFlag.equals("N") && !seqAct.getActivity().equals(
						webConstants.SETUP))) {
			assetSel.addAttribute("disabled", "disabled");
		}

		// System.out.println("asset:" + assetSel.render());
		seq.setAssetMap(assetMapForDesc);
		TableData txtAssetNo = new TableData(assetSel);

		row2.addTableData(lblActivity);
		row2.addTableData(txtActivityCode);
		// row2.addTableData(lblReason);
		// row2.addTableData(reasonData);
		row2.addTableData(lblAssetNo);
		row2.addTableData(txtAssetNo);

		// forming third row

		TableData lblQtyCompleted = new TableData(new SimpleText(
				"Qty Completed"));
		InputField txtQtyCompl = new InputField("qtyCompleted", new Integer(
				seqAct.getQtyCompleted()).toString(), InputType.TEXT);
		txtQtyCompl.addAttribute("id", "qtyCompleted");
		TableData txtQtyCompleted = new TableData(txtQtyCompl);

		TableData lblAssetDesc = new TableData(new SimpleText("Asset Desc"));
		// InputField txtAssetDesc = new InputField("assetDesc",
		// seqAct.getAssetDesc() == null ? "" : seqAct.getAssetDesc(),
		// InputType.TEXT);
		// txtAssetDesc.addAttribute("id", "assetDesc");
		// TableData txtAssetDescription = new TableData(txtAssetDesc);
		if (assetMapForDesc != null && !assetMapForDesc.isEmpty()) {
			assetDesc = (String) assetMapForDesc.get(seqAct.getAssetNumber());
			System.out.println("asset desc:" + assetDesc);
		}
		InputField txtAssetDesc = new InputField("assetDesc", StringUtils
				.defaultString(assetDesc, SPACE), InputType.TEXT);
		txtAssetDesc.addAttribute("readonly", "readonly");
		TableData txtAssetDescription = new TableData(txtAssetDesc);
		txtAssetDescription.addAttribute("id", "assetDescContainer");

		row3.addTableData(lblQtyCompleted);
		row3.addTableData(txtQtyCompleted);
		row3.addTableData(lblAssetDesc);
		row3.addTableData(txtAssetDescription);

		// forming fourth row

		TableData lblPPH = new TableData(new SimpleText("PPH"));
		double pphValue = 0.0;

		// if (seqAct.getLogoffDate() != null) {
		pphValue = Util.calculatePPH(seq.getRunTime(), seq.getPriceTimeQty());
		// }
		NumberFormat formatter = new DecimalFormat("#0.00");
		InputField txtPPHValue = new InputField("pphValue", String
				.valueOf(formatter.format(pphValue)), InputType.TEXT);
		txtPPHValue.addAttribute("readonly", "readonly");
		TableData txtPPH = new TableData(txtPPHValue);

		row4.addTableData(lblPPH);
		row4.addTableData(txtPPH);
		row4.addTableData(reasonData);
		// row4.addTableData(reasonData);

		// forming 5th row
		TableData lblSeqCompleteFlag = new TableData(new SimpleText(
				"Complete Sequence"));

		/*
		 * START - Fix for Sharepoint Item 1293 - 1 - Re-Opening a sequence
		 * makes it impossible to close
		 */

		String checked;
		getCheckedAttribute(dto.getSelectedSequeuce().getSequenceStatus());
		String txtSeqCompFlag = "<input type=\"checkbox\" name=\"seqCompFlag\" value=\" \" onclick=\"validateCompleteSequence()\" "
				+ EMPTY_STRING
				+ getCheckedAttribute(dto.getSelectedSequeuce()
						.getSequenceStatus()) + "/>";

		/*
		 * END - Fix for Sharepoint Item 1293 - 1 Re-Opening a sequence makes it
		 * impossible to close
		 */

		TableData txtSeqCompleteFlag = new TableData(new SimpleText(
				txtSeqCompFlag));

		TableData emptyData = new TableData(new SimpleText(SPACE));

		row5.addTableData(lblSeqCompleteFlag);
		row5.addTableData(txtSeqCompleteFlag);
		row5.addTableData(emptyData);
		row5.addTableData(emptyData);

		System.out.println("row1:" + row1.render());
		System.out.println("row2:" + row2.render());
		System.out.println("row3:" + row3.render());
		System.out.println("row4:" + row4.render());
		System.out.println("row5:" + row5.render());

		innertable.addTableRow(row1);
		innertable.addTableRow(row2);
		innertable.addTableRow(row3);
		innertable.addTableRow(row4);
		innertable.addTableRow(row5);

		TableData outerTableDate1 = new TableData(innertable);

		// create the buttons here itself
		Table innertable2 = new Table();
		innertable2.addAttribute("id", "normalTableNostripe2");
		innertable2.addAttribute("align", "center");
		// innertable2.addAttribute("cellSpacing", "0");
		// innertable2.addAttribute("cellPadding", "0");
		innertable2.addAttribute("border", "0");
		innertable2
				.addAttribute(
						"style",
						"white-space: nowrap; word-spacing: normal; width: 60px; border-bottom-color: white; border-left-color: white");

		TableRow innertable2Row1 = new TableRow();

		int currentSeqCompletedQty = 0;
		if (seq.getSeqActivityDetails() != null) {
			for (SequenceActivity sequenceActivity : seq
					.getSeqActivityDetails()) {
				if (sequenceActivity.getActivityLogNumber() != 0
						&& seqAct.getActivityLogNumber() != sequenceActivity
								.getActivityLogNumber()) {
					currentSeqCompletedQty = sequenceActivity.getQtyCompleted();
				}
			}
		}

		Image updateImage = new Image("static/images/Confirm.gif", "Confirm");
		if (seq.isFirstSequence()) {
			updateImage.addAttribute("onclick", "javascript:confirmValues('"
					+ newFlag + "', '" + currentSeqCompletedQty + "', '"
					+ seq.getOrderQty() + "')");
		} else {
			updateImage.addAttribute("onclick", "javascript:confirmValues('"
					+ newFlag + "', '" + currentSeqCompletedQty + "', '"
					+ getPreviousSeqCompletedQty(seq, dto.getSequencesInM3())
					+ "')");
		}
		updateImage.addAttribute("style", "cursor: pointer;");
		TableData buttonUpdate = new TableData(updateImage);
		buttonUpdate.addAttribute("style",
				"border-bottom-color: white; border-left-color: white");

		Image cancelImage = new Image("static/images/cancel.gif", "Cancel");
		cancelImage.addAttribute("onclick", "javascript:cancelActDetails()");
		cancelImage.addAttribute("style", "cursor: pointer;");
		TableData buttonCancel = new TableData(cancelImage);
		buttonCancel.addAttribute("style",
				"border-bottom-color: white; border-left-color: white");

		innertable2Row1.addTableData(buttonUpdate);
		innertable2Row1.addTableData(buttonCancel);
		innertable2Row1.addAttribute("align", "center;");

		innertable2.addTableRow(innertable2Row1);
		TableData outerTableDate2 = new TableData(innertable2);

		outerTableRow1.addTableData(outerTableDate1);
		outerTableRow2.addTableData(outerTableDate2);
		outerTableRow2.addAttribute("align", "center;");
		table.addTableRow(outerTableRow1);
		table.addTableRow(outerTableRow2);

		return table;

	}

	private String getCheckedAttribute(String status) {
		if (webConstants.STATUS_COMPLETE.equals(status)) {
			return CHECKED;
		}
		return StringUtils.EMPTY;

	}

	public AjaxResponse displayReasons(AjaxActionEvent event) {
		AjaxResponse response = new AjaxResponseImpl();

		ManufacturingOrder dto = (ManufacturingOrder) event.getCommandObject();
		Sequence seq = dto.getSelectedSequeuce();
		SequenceActivity seqAct = seq.getSelectedSeqActivity();

		ArrayList<MasterData> reasonList = dto.getPauseReasons();
		Select reasonSel = new Select("reasonCode");
		Option option = null;
		for (MasterData data : reasonList) {
			option = new Option(data.getCode(), data.getDesc());
			reasonSel.addOption(option);
			if (seqAct.getReasonCode() != null
					&& seqAct.getReasonCode().equalsIgnoreCase(data.getCode())) {
				option.setSelected(true);
			}
		}

		TableData lblReason = new TableData(new SimpleText("Reason"));
		lblReason.addAttribute("width", "76px");
		TableData reasonData = new TableData(reasonSel);

		TableRow tableRow = new TableRow();
		tableRow.addTableData(lblReason);
		tableRow.addTableData(reasonData);

		Table table = new Table();
		table.addTableRow(tableRow);
		table.addAttribute("align", "left");

		ReplaceContentAction action = new ReplaceContentAction(
				"reasonContainer", table);
		response.addAction(action);

		return response;
	}

	public AjaxResponse displayAssetDesc(AjaxActionEvent event) {
		AjaxResponse response = new AjaxResponseImpl();

		ManufacturingOrder dto = (ManufacturingOrder) event.getCommandObject();
		Sequence seq = dto.getSelectedSequeuce();
		SequenceActivity seqAct = seq.getSelectedSeqActivity();

		seqAct.setAssetNumber(event.getHttpRequest().getParameter("assetNo1"));

		Map<String, String> assetMapForDesc = seq.getAssetMap();
		String assetDesc = null;
		if (assetMapForDesc != null && !assetMapForDesc.isEmpty()) {
			assetDesc = (String) assetMapForDesc.get(seqAct.getAssetNumber());
		}

		InputField txtAssetDesc = new InputField("assetDesc", StringUtils
				.defaultString(assetDesc, SPACE), InputType.TEXT);
		txtAssetDesc.addAttribute("readonly", "readonly");

		ReplaceContentAction assetDescAction = new ReplaceContentAction(
				"assetDescContainer", txtAssetDesc);
		response.addAction(assetDescAction);

		return response;
	}

	private int getPreviousSeqCompletedQty(Sequence seq,
			ArrayList<Sequence> m3Sequences) {
		if (seq != null) {
			int i = 0;
			for (Sequence sequence : m3Sequences) {
				System.out.println(sequence.getSequence()
						+ " >> sequence.getSequence()");
				if (sequence.getSequence().equals(seq.getSequence())) {
					break;
				}
				i++;
			}
			// Get the Previous sequence completed qty.
			return m3Sequences.get(--i).getCompletedQuantity();
		}
		return 0;
	}

	private Container buildButtonContainer() {

		Image addImage = new Image("static/images/addMissingInfo.gif",
				"Add Missing Info");
		addImage.addAttribute("style", "cursor: pointer;");
		addImage.addAttribute("onClick", "javascript:addMissingInfo()");
		Image cancelImage = new Image("static/images/cancel.gif", "Cancel");
		cancelImage.addAttribute("style", "cursor: pointer;");
		cancelImage.addAttribute("onClick", "javascript:cancel()");
		Container container = new Container(Type.DIV);
		container.addComponent(addImage);
		container.addComponent(cancelImage);
		container.addAttribute("style", "width: 100%");
		container.addAttribute("align", "center;");

		return container;
	}

	private boolean isDataFoundToDisplay(ManufacturingOrder manufacturingOrder) {

		// Check for the empty modify mos.
		if (manufacturingOrder.getModifyMOs() == null
				|| manufacturingOrder.getModifyMOs().size() == 0) {
			return false;
		}

		return true;
	}

	private SimpleText formErrorInfo(ManufacturingOrder dto) {

		Sequence seq = dto.getSelectedSequeuce();
		SimpleText st = new SimpleText(SPACE);
		if (seq != null) {
			SequenceActivity seqAct = seq.getSelectedSeqActivity();

			if (seqAct.getError() != null
					&& !seqAct.getError().equals(EMPTY_STRING)) {

				StringBuffer sb = new StringBuffer();
				sb.append("<font color=\"red\" size=\"2px\"><b>");
				sb.append(seqAct.getError());
				sb.append("</b></font>");
				st = new SimpleText(sb.toString());
			}
		}

		return st;

	}
}
