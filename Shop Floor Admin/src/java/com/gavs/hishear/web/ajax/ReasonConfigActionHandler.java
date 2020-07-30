/**
 * WorkCenterConfigActionHandler.java
 */
package com.gavs.hishear.web.ajax;

import java.util.ArrayList;
import java.util.HashMap;

import org.springmodules.xt.ajax.AjaxResponse;
import org.springmodules.xt.ajax.AjaxResponseImpl;
import org.springmodules.xt.ajax.AjaxSubmitEvent;
import org.springmodules.xt.ajax.action.ExecuteJavascriptFunctionAction;
import org.springmodules.xt.ajax.action.ReplaceContentAction;
import org.springmodules.xt.ajax.component.Container;
import org.springmodules.xt.ajax.component.Container.Type;
import org.springmodules.xt.ajax.component.Image;
import org.springmodules.xt.ajax.component.InputField;
import org.springmodules.xt.ajax.component.InputField.InputType;
import org.springmodules.xt.ajax.component.SimpleText;
import org.springmodules.xt.ajax.component.Table;
import org.springmodules.xt.ajax.component.TableData;
import org.springmodules.xt.ajax.component.TableHeader;
import org.springmodules.xt.ajax.component.TableHeaderData;
import org.springmodules.xt.ajax.component.TableRow;

import com.gavs.hishear.web.domain.ReasonCode;
import com.gavs.hishear.web.domain.ReasonType;

/**
 * @author Ambrish_V
 * 
 */
public class ReasonConfigActionHandler extends BaseAjaxHandler {

	/**
	 * Gets the Work Center details.
	 * 
	 * @param event
	 *            the event
	 * @return the Work Center details
	 */
	public AjaxResponse getReasonDetails(AjaxSubmitEvent event) {

		AjaxResponse response = new AjaxResponseImpl();

		System.out
				.println(" Handler Called : Work Center........................ ");

		ReasonCode dto = (ReasonCode) event.getCommandObject();

		ArrayList<ReasonType> reason = dto.getReasonDetails();

		if (reason != null && reason.size() > 0) {

			getReasonTableDetails(response, dto);

		} else {

			ReplaceContentAction reasonDetailAction = new ReplaceContentAction(
					"reasonDetailDiv", new SimpleText(""));
			ReplaceContentAction messageAction = new ReplaceContentAction(
					"statusMessage",
					new SimpleText(
							"<font color=\"red\" size=\"2px\"><b>No Data Found</b></font>"));
			ExecuteJavascriptFunctionAction enableFields = new ExecuteJavascriptFunctionAction(
					"enableFields", new HashMap<String, Object>());
			response.addAction(enableFields);
			response.addAction(reasonDetailAction);
			response.addAction(messageAction);

		}
		System.out.println(" bef returning ajax response");

		return response;
	}

	/**
	 * Gets the Work Center details.
	 * 
	 * @param event
	 *            the event
	 * @return the Work Center details
	 */
	public AjaxResponse addNewReason(AjaxSubmitEvent event) {

		AjaxResponse response = new AjaxResponseImpl();

		ReasonCode dto = (ReasonCode) event.getCommandObject();

		if ((dto.getError() != null) && !(dto.getError().isEmpty())) {

			ReplaceContentAction action = new ReplaceContentAction(
					"statusMessage", new SimpleText(
							"<font color=\"red\" size=\"2px\">"
									+ dto.getError() + "</font>"));
			response.addAction(action);

		} else {

			getNewReasonTable(response, dto);

		}

		return response;

	}

	/**
	 * Gets the mO details.
	 * 
	 * @param event
	 *            the event
	 * @return the mO details
	 */
	public AjaxResponse insertNewReason(AjaxSubmitEvent event) {

		AjaxResponse response = new AjaxResponseImpl();

		ReasonCode dto = (ReasonCode) event.getCommandObject();

		if ((dto.getError() != null) && !(dto.getError().isEmpty())) {

			ReplaceContentAction action = new ReplaceContentAction(
					"statusMessage", new SimpleText(
							"<font color=\"red\" size=\"2px\">"
									+ dto.getError() + "</font>"));
			response.addAction(action);

			dto.setError("");

		} else {
			// Success Message
			ReplaceContentAction action = new ReplaceContentAction(
					"statusMessage",
					new SimpleText(
							"<font color=\"green\" size=\"2px\">Added Successfully</font>"));
			response.addAction(action);

			ReplaceContentAction buttonAction = new ReplaceContentAction(
					"buttonsDiv", disableAddButtonContainer());
			response.addAction(buttonAction);

		}

		return response;

	}

	/**
	 * @param response
	 * @param dto
	 */
	public void getReasonTableDetails(AjaxResponse response, ReasonCode dto) {

		ReplaceContentAction action = null;

		System.out.println("in table details ");

		Container containerTotal = new Container(Container.Type.DIV);
		containerTotal.addAttribute("style", "width: 620px");
		containerTotal.addAttribute("align", "center");

		Container container = new Container(Container.Type.DIV);
		container
				.addAttribute("style",
						"width: 620px; overflow: scroll; overflow-x: hidden; height: 275px");

		container.addComponent(buildReasonDetailsTable(dto));

		containerTotal.addComponent(getHeaderForReasonDetailTable());
		containerTotal.addComponent(container);

		action = new ReplaceContentAction("reasonDetailDiv", containerTotal);
		response.addAction(action);

		System.out.println("preparing viewing the page" + response.isEmpty());

	}

	public void getNewReasonTable(AjaxResponse response, ReasonCode dto) {

		ReplaceContentAction action = null;

		System.out.println("in new table details ");

		Container containerTotal = new Container(Container.Type.DIV);
		containerTotal.addAttribute("style", "width: 920px");
		containerTotal.addAttribute("align", "center");

		Container container = new Container(Container.Type.DIV);

		container.addAttribute("style",
				"width: 920px; overflow: auto; height: 200px");

		container.addComponent(buildNewReasonTable(dto));

		containerTotal.addComponent(getHeaderForNewReasonTable(dto));
		containerTotal.addComponent(container);

		action = new ReplaceContentAction("reasonDetailDiv", containerTotal);
		response.addAction(action);

		ReplaceContentAction buttonAction = new ReplaceContentAction(
				"buttonsDiv", buildButtonContainer(dto));
		response.addAction(buttonAction);

		Image disableFilterImage = new Image(
				"static/images/filter_disable.gif", "Filter");
		disableFilterImage.addAttribute("style", "cursor: default;");

		ReplaceContentAction filterbuttonAction = new ReplaceContentAction(
				"filterButtonDiv", disableFilterImage);
		response.addAction(filterbuttonAction);

		Image disableAddNewImage = new Image(
				"static/images/addNewReason_disable.gif", "Add New Reason");
		disableFilterImage.addAttribute("style", "cursor: default;");

		ReplaceContentAction addNewbuttonAction = new ReplaceContentAction(
				"addNewButtonDiv", disableAddNewImage);
		response.addAction(addNewbuttonAction);

	}

	/**
	 * Gets the header table for sequence.
	 * 
	 * @return the header table for sequence
	 */
	private Table getHeaderForReasonDetailTable() {

		TableHeaderData reasonDescHeaderData = new TableHeaderData(
				new SimpleText("Reason Description"));
		reasonDescHeaderData.addAttribute("style", "width: 68%");

		TableHeaderData reasonTypeHeaderData = new TableHeaderData(
				new SimpleText("Reason Type"));
		reasonTypeHeaderData.addAttribute("style", "width: 35%");

		ArrayList<TableHeaderData> headers = new ArrayList<TableHeaderData>();
		headers.add(reasonDescHeaderData);
		headers.add(reasonTypeHeaderData);

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
				"white-space: nowrap; word-spacing: normal; width: 618px");
		table.addTableHeaderAttribute("class", "fixedHeader");
		table.addTableBodyAttribute("class", "scrollContent");

		return table;

	}

	/**
	 * Gets the header table for sequence.
	 * 
	 * @return the header table for sequence
	 */
	private Table getHeaderForNewReasonTable(ReasonCode dto) {

		TableHeaderData descriptionHeaderData = new TableHeaderData(
				new SimpleText("Reason Description"));
		descriptionHeaderData.addAttribute("style", "width: 100%");

		ArrayList<TableHeaderData> headers = new ArrayList<TableHeaderData>();

		headers.add(descriptionHeaderData);

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
				"white-space: nowrap; word-spacing: normal; width: 250px");
		table.addTableHeaderAttribute("class", "fixedHeader");
		table.addTableBodyAttribute("class", "scrollContent");

		return table;

	}

	/**
	 * Builds the sequence details table.
	 * 
	 * @param manufacturingOrder
	 *            the manufacturing order
	 * @return the table
	 */
	public Table buildReasonDetailsTable(ReasonCode dto) {

		Table table = new Table();
		table.addAttribute("id", "normalTableNostripe");
		table.addAttribute("align", "center");
		table.addAttribute("cellSpacing", "0");
		table.addAttribute("cellPadding", "0");
		table.addAttribute("border", "1");
		table.addAttribute("class", "classContentTable");
		table.addAttribute("style",
				"white-space: nowrap; word-spacing: normal; width: 600px");
		table.addTableHeaderAttribute("class", "fixedHeader");
		table.addTableBodyAttribute("class", "scrollContent");

		if (dto.getReasonDetails() != null && dto.getReasonDetails().size() > 0) {

			int count = 0;

			for (ReasonType reason : dto.getReasonDetails()) {

				TableRow row = new TableRow();

				TableData reasonDesc = new TableData(new SimpleText(
						validateDisplayField(reason.getReasonDesc())));
				reasonDesc.addAttribute("style", "width: 70%");

				TableData reasonType = new TableData(new SimpleText(
						validateDisplayField(reason.getReasonType())));
				reasonType.addAttribute("style", "width: 30%");

				row.addTableData(reasonDesc);
				row.addTableData(reasonType);

				if (count % 2 == 0) {
					row.addAttribute("class", "normalRow");
				} else {
					row.addAttribute("class", "alternateRow");
				}
				table.addTableRow(row);
				count++;

			}
		}

		return table;
	}

	/**
	 * Builds the sequence details table.
	 * 
	 * @param manufacturingOrder
	 *            the manufacturing order
	 * @return the table
	 */
	public Table buildNewReasonTable(ReasonCode dto) {

		Table table = new Table();
		table.addAttribute("id", "normalTableNostripe");
		table.addAttribute("align", "center");
		table.addAttribute("cellSpacing", "0");
		table.addAttribute("cellPadding", "0");
		table.addAttribute("border", "1");
		table.addAttribute("class", "classContentTable");
		table.addAttribute("style",
				"white-space: nowrap; word-spacing: normal; width: 250px");
		table.addTableHeaderAttribute("class", "fixedHeader");
		table.addTableBodyAttribute("class", "scrollContent");

		TableRow row = new TableRow();

		InputField description = new InputField("description", "",
				InputType.TEXT);
		description.addAttribute("maxlength", "100");
		description.addAttribute("size", "30");
		TableData descriptionText = new TableData(description);
		descriptionText.addAttribute("style", "width: 100%");

		row.addTableData(descriptionText);

		row.addAttribute("class", "normalRow");

		table.addTableRow(row);

		return table;
	}

	/**
	 * Builds the button container.
	 * 
	 * @return the container
	 */
	private Container buildButtonContainer(ReasonCode dto) {

		Image cancelImage = new Image("static/images/cancel.gif", "Cancel");
		cancelImage.addAttribute("style", "cursor: pointer;");
		cancelImage.addAttribute("onClick", "javascript:cancel()");

		Image updateWorkCenterImage = new Image("static/images/save.gif",
				"Save");
		updateWorkCenterImage.addAttribute("style", "cursor: pointer;");
		updateWorkCenterImage.addAttribute("onClick",
				"javascript:insertReasonDetails()");

		Container container = new Container(Type.DIV);
		container.addComponent(updateWorkCenterImage);
		container.addComponent(cancelImage);
		container.addAttribute("style", "width: 100%");
		container.addAttribute("align", "center;");

		return container;
	}

	/**
	 * Builds the button container.
	 * 
	 * @return the container
	 */
	private Container disableAddButtonContainer() {

		Image cancelImage = new Image("static/images/cancel.gif", "Cancel");
		cancelImage.addAttribute("style", "cursor: pointer;");
		cancelImage.addAttribute("onClick", "javascript:cancel()");

		Image addReasonImage = new Image("static/images/save_disable.gif",
				"Save");
		addReasonImage.addAttribute("style", "cursor: default;");

		Container container = new Container(Type.DIV);
		container.addComponent(addReasonImage);
		container.addComponent(cancelImage);
		container.addAttribute("style", "width: 100%");
		container.addAttribute("align", "center;");

		return container;
	}
}
