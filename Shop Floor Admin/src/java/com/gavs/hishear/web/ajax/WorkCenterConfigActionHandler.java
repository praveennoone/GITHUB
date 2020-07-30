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

import com.gavs.hishear.web.domain.ManufacturingOrder;
import com.gavs.hishear.web.domain.WorkCenterConfig;

/**
 * @author Ambrish_V
 * 
 */
public class WorkCenterConfigActionHandler extends BaseAjaxHandler {

	/**
	 * Gets the Work Center details.
	 * 
	 * @param event
	 *            the event
	 * @return the Work Center details
	 */
	public AjaxResponse getWorkCenterConfigDetails(AjaxSubmitEvent event){

		AjaxResponse response = new AjaxResponseImpl();

		ManufacturingOrder dto = (ManufacturingOrder) event.getCommandObject();

		ArrayList<WorkCenterConfig> workCenter = dto.getWorkCenterConfig();

		if (workCenter != null && workCenter.size() > 0) {

			getWorkCenterTableDetails(response, dto);

		} else {

			ReplaceContentAction workCenterDetailAction = new ReplaceContentAction(
					"workCenterDetailDiv", new SimpleText(""));
			ReplaceContentAction buttonAction = new ReplaceContentAction(
					"buttonsDiv", new SimpleText(""));
			ReplaceContentAction messageAction = new ReplaceContentAction(
					"statusMessage",
					new SimpleText(
							"<font color=\"red\" size=\"2px\"><b>No Data Found for the Given Search Criteria</b></font>"));
			ExecuteJavascriptFunctionAction enableFields = new ExecuteJavascriptFunctionAction(
					"enableFields", new HashMap<String, Object>());
			response.addAction(enableFields);
			response.addAction(workCenterDetailAction);
			response.addAction(buttonAction);
			response.addAction(messageAction);

		}

		return response;
	}

	/**
	 * Gets the Work Center details.
	 * 
	 * @param event
	 *            the event
	 * @return the Work Center details
	 */
	public AjaxResponse addNewWorkCenter(AjaxSubmitEvent event){
		AjaxResponse response = new AjaxResponseImpl();

		ManufacturingOrder dto = (ManufacturingOrder) event.getCommandObject();

		if ((dto.getError() != null) && !(dto.getError().isEmpty())) {

			ReplaceContentAction action = new ReplaceContentAction(
					"statusMessage", new SimpleText(
							"<font color=\"red\" size=\"2px\">"
									+ dto.getError() + "</font>"));
			response.addAction(action);

			ExecuteJavascriptFunctionAction enableFields = new ExecuteJavascriptFunctionAction(
					"enableFields", new HashMap<String, Object>());
			response.addAction(enableFields);

		} else {

			getNewWorkCenterTable(response, dto);

		}

		return response;

	}

	/**
	 * Method to Update the Work center configuration details.
	 * 
	 * @param event
	 *            the event
	 * @return the response
	 */
	public AjaxResponse updateWorkCenterConfigDetails(AjaxSubmitEvent event) {
		AjaxResponse response = new AjaxResponseImpl();

		ManufacturingOrder dto = (ManufacturingOrder) event.getCommandObject();

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
							"<font color=\"green\" size=\"2px\">Updated Successfully</font>"));
			response.addAction(action);
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
	public AjaxResponse insertNewWorkCenterDetails(AjaxSubmitEvent event) {
		AjaxResponse response = new AjaxResponseImpl();

		ManufacturingOrder dto = (ManufacturingOrder) event.getCommandObject();

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

		}

		return response;

	}

	/**
	 * @param response
	 * @param dto
	 */
	public void getWorkCenterTableDetails(AjaxResponse response,
			ManufacturingOrder dto) {
		ReplaceContentAction action = null;

		Container containerTotal = new Container(Container.Type.DIV);
		containerTotal.addAttribute("style", "width: 716px");
		containerTotal.addAttribute("align", "center");

		Container container = new Container(Container.Type.DIV);
		container
				.addAttribute("style",
						"width: 716px; overflow: scroll; overflow-x: hidden;height: 260px");

		container.addComponent(buildWorkCenterDetailsTable(dto));

		containerTotal.addComponent(getHeaderForWorkCenterDetailTable());
		containerTotal.addComponent(container);

		action = new ReplaceContentAction("workCenterDetailDiv", containerTotal);
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
				"static/images/addNewWorkCenter_disable.gif",
				"Add New Work Center");
		disableFilterImage.addAttribute("style", "cursor: default;");

		ReplaceContentAction addNewbuttonAction = new ReplaceContentAction(
				"addNewButtonDiv", disableAddNewImage);
		response.addAction(addNewbuttonAction);

	}

	public void getNewWorkCenterTable(AjaxResponse response,
			ManufacturingOrder dto) {
		ReplaceContentAction action = null;

		Container containerTotal = new Container(Container.Type.DIV);
		containerTotal.addAttribute("style", "width: 920px");
		containerTotal.addAttribute("align", "center");

		Container container = new Container(Container.Type.DIV);
		if (dto.getEditFlag()) {
			container.addAttribute("style",
					"width: 920px; overflow: auto; height: 50px");
		} else {
			container.addAttribute("style",
					"width: 920px; overflow: auto; height: 200px");
		}
		container.addComponent(buildNewWorkCenterTable(dto));

		containerTotal.addComponent(getHeaderForNewWorkCenterTable(dto));
		containerTotal.addComponent(container);

		if (dto.getEditFlag()) {

			action = new ReplaceContentAction("editDiv", containerTotal);
			response.addAction(action);

			ReplaceContentAction buttonAction = new ReplaceContentAction(
					"buttonsDiv", buildButtonContainer(dto));
			response.addAction(buttonAction);

		} else {

			action = new ReplaceContentAction("workCenterDetailDiv",
					containerTotal);
			response.addAction(action);

			ReplaceContentAction buttonAction = new ReplaceContentAction(
					"buttonsDiv", buildNewButtonContainer());
			response.addAction(buttonAction);

		}

		Image disableFilterImage = new Image(
				"static/images/filter_disable.gif", "Filter");
		disableFilterImage.addAttribute("style", "cursor: default;");

		ReplaceContentAction filterbuttonAction = new ReplaceContentAction(
				"filterButtonDiv", disableFilterImage);
		response.addAction(filterbuttonAction);

		Image disableAddNewImage = new Image(
				"static/images/addNewWorkCenter_disable.gif",
				"Add New Work Center");
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
	private Table getHeaderForWorkCenterDetailTable() {
		TableHeaderData facilityHeaderData = new TableHeaderData(
				new SimpleText("Facility"));
		facilityHeaderData.addAttribute("style", "width: 10%");

		TableHeaderData workCenterHeaderData = new TableHeaderData(
				new SimpleText("Work Center"));
		workCenterHeaderData.addAttribute("style", "width: 15%");

		TableHeaderData descriptionHeaderData = new TableHeaderData(
				new SimpleText("Description"));
		descriptionHeaderData.addAttribute("style", "width: 29%");

		TableHeaderData pphPercentageHeaderData = new TableHeaderData(
				new SimpleText("PPH %"));
		pphPercentageHeaderData.addAttribute("style", "width: 10%");

		TableHeaderData scaleRequiredHeaderData = new TableHeaderData(
				new SimpleText("Scale Required"));
		scaleRequiredHeaderData.addAttribute("style", "width: 10%");

		//below code is added for ticket 414799 by saikiran
		TableHeaderData scrapCountHeaderData = new TableHeaderData(
						new SimpleText("Scrap Count"));
		scrapCountHeaderData.addAttribute("style", "width: 10%");
		//end ticket 414799 by saikiran
		
		TableHeaderData activeFlagHeaderData = new TableHeaderData(
				new SimpleText("Active"));
		activeFlagHeaderData.addAttribute("style", "width: 8%");

		TableHeaderData editHeaderData = new TableHeaderData(new SimpleText(
				"Edit"));
		editHeaderData.addAttribute("style", "width: 15%");

		ArrayList<TableHeaderData> headers = new ArrayList<TableHeaderData>();
		headers.add(facilityHeaderData);
		headers.add(workCenterHeaderData);
		headers.add(descriptionHeaderData);
		headers.add(pphPercentageHeaderData);
		headers.add(scaleRequiredHeaderData);
		//below code is added for ticket 414799 by saikiran
		headers.add(scrapCountHeaderData);
		headers.add(activeFlagHeaderData);
		headers.add(editHeaderData);

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
				"white-space: nowrap; word-spacing: normal; width: 716px");
		table.addTableHeaderAttribute("class", "fixedHeader");

		return table;

	}

	/**
	 * Gets the header table for sequence.
	 * 
	 * @return the header table for sequence
	 */
	private Table getHeaderForNewWorkCenterTable(ManufacturingOrder dto) {

		TableHeaderData descriptionHeaderData = new TableHeaderData(
				new SimpleText("Description"));
		descriptionHeaderData.addAttribute("style", "width: 40%");

		TableHeaderData pphPercentageHeaderData = new TableHeaderData(
				new SimpleText("PPH %"));
		pphPercentageHeaderData.addAttribute("style", "width: 20%");

		TableHeaderData scaleRequiredHeaderData = new TableHeaderData(
				new SimpleText("Scale Required"));
		scaleRequiredHeaderData.addAttribute("style", "width: 20%");
		//below code is added for ticket 414799 by saikiran
		TableHeaderData scrapCountHeaderData = new TableHeaderData(
				new SimpleText("Scrap Count"));
		scrapCountHeaderData.addAttribute("style", "width: 20%");
		//end ticket 414799 by saikiran
		TableHeaderData activeFlagHeaderData = new TableHeaderData(
				new SimpleText("Active"));
		activeFlagHeaderData.addAttribute("style", "width: 20%");

		ArrayList<TableHeaderData> headers = new ArrayList<TableHeaderData>();

		if (dto.getEditFlag()) {

			TableHeaderData workCenterHeaderData = new TableHeaderData(
					new SimpleText("Work Center"));
			workCenterHeaderData.addAttribute("style", "width: 15%");

			TableHeaderData facilityHeaderData = new TableHeaderData(
					new SimpleText("Facility"));

			facilityHeaderData.addAttribute("style", "width:10%");
			descriptionHeaderData.addAttribute("style", "width: 35%");
			pphPercentageHeaderData.addAttribute("style", "width: 15%");
			scaleRequiredHeaderData.addAttribute("style", "width: 10%");
			//below code is added for ticket 414799 by saikiran
			scrapCountHeaderData.addAttribute("style", "width: 10%");
			activeFlagHeaderData.addAttribute("style", "width: 10%");
			headers.add(facilityHeaderData);
			headers.add(workCenterHeaderData);

		}
		headers.add(descriptionHeaderData);
		headers.add(pphPercentageHeaderData);
		headers.add(scaleRequiredHeaderData);
		//below code is added for ticket 414799 by saikiran
		headers.add(scrapCountHeaderData);
		headers.add(activeFlagHeaderData);

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

	/**
	 * Builds the sequence details table.
	 * 
	 * @param manufacturingOrder
	 *            the manufacturing order
	 * @return the table
	 */
	public Table buildWorkCenterDetailsTable(ManufacturingOrder dto) {
		Table table = new Table();
		table.addAttribute("id", "normalTableNostripe");
		table.addAttribute("align", "center");
		table.addAttribute("cellSpacing", "0");
		table.addAttribute("cellPadding", "0");
		table.addAttribute("border", "1");
		table.addAttribute("class", "classContentTable");
		table.addAttribute("style",
				"white-space: nowrap; word-spacing: normal; width: 700px");
		table.addTableHeaderAttribute("class", "fixedHeader");

		if (dto.getWorkCenterConfig() != null
				&& dto.getWorkCenterConfig().size() > 0) {

			int count = 0;

			for (WorkCenterConfig workCenter : dto.getWorkCenterConfig()) {

				TableRow row = new TableRow();

				TableData facility = new TableData(new SimpleText((workCenter
						.getFacility())));
				facility.addAttribute("style",
						"width: 10.5% ; vertical-align: middle");

				TableData workCenterCode = new TableData(new SimpleText(
						validateDisplayField(workCenter.getWorkCenterCode())));
				workCenterCode.addAttribute("style",
						"width: 16% ; vertical-align: middle");

				TableData description = new TableData(new SimpleText(
						validateDisplayField(workCenter.getDescription())));
				description.addAttribute("style",
						"width: 30% ; vertical-align: middle");

				TableData pphPercentage = new TableData(new SimpleText(String
						.valueOf(workCenter.getPphThresholdPercentage())));
				pphPercentage.addAttribute("style",
						"width: 10.5% ; vertical-align: middle");

				String scaleReq = workCenter.getScaleRequired();

				TableData scaleRequired;
				if (!"Y".equalsIgnoreCase(scaleReq)) {
					scaleRequired = new TableData(
							new SimpleText(
									"<input type=\"checkbox\"  name=\"scaleReqd_Chkbox\" value=\"Y\" />"));
				} else {
					scaleRequired = new TableData(
							new SimpleText(
									"<input type=\"checkbox\"  name=\"scaleReqd_Chkbox\" value=\"Y\" checked=\"checked\"/>"));
				}
				scaleRequired.addAttribute("style",
						"width: 10.5% ; vertical-align: middle");
				scaleRequired.addAttribute("disabled", "disabled");

				//below code is added for ticket 414799 by saikiran
				String scrapCunt = workCenter.getScrapCount();

				TableData scrapCount;
				if (!"Y".equalsIgnoreCase(scrapCunt)) {
					scrapCount = new TableData(
							new SimpleText(
									"<input type=\"checkbox\"  name=\"scrapCount_Chkbox\" value=\"Y\" />"));
				} else {
					scrapCount = new TableData(
							new SimpleText(
									"<input type=\"checkbox\"  name=\"scrapCount_Chkbox\" value=\"Y\" checked=\"checked\"/>"));
				}
				scrapCount.addAttribute("style",
						"width: 10.5% ; vertical-align: middle");
				scrapCount.addAttribute("disabled", "disabled");
				//end *** ticket 414799 by saikiran
				
				String activeFlag = workCenter.getActiveFlag();

				TableData active;
				if (!"Y".equalsIgnoreCase(activeFlag)) {
					active = new TableData(
							new SimpleText(
									"<input type=\"checkbox\"  name=\"activeFlag_Chkbox\" value=\"Y\" />"));
				} else {
					active = new TableData(
							new SimpleText(
									"<input type=\"checkbox\"  name=\"activeFlag_Chkbox\" value=\"Y\" checked=\"checked\"/>"));
				}
				active.addAttribute("style",
						"width: 9% ; vertical-align: middle");
				active.addAttribute("disabled", "disabled");
			
				
				Image editImage = new Image("static/images/edit.jpg", "Edit");
				editImage.addAttribute("style", "cursor: pointer;");
				editImage.addAttribute("onClick", "javascript:edit('" + count
						+ "','"
						+ validateDisplayField(workCenter.getWorkCenterCode())
						+ "','" + workCenter.getFacility() + "','"
						+ validateDisplayField(workCenter.getDescription())
						+ "','" + workCenter.getPphThresholdPercentage()
						+ "','" + workCenter.getScaleRequired()
						//below code is added for ticket 414799 by saikiran
						+ "','" + workCenter.getScrapCount()
						//end *** ticket 414799 by saikiran
						+"','" + workCenter.getActiveFlag() 
						+ "');");

				TableData edit = new TableData(editImage);
				edit.addAttribute("style", "width: 10%");

				row.addTableData(facility);
				row.addTableData(workCenterCode);
				row.addTableData(description);
				row.addTableData(pphPercentage);
				row.addTableData(scaleRequired);
				//below code is added for ticket 414799 by saikiran
				row.addTableData(scrapCount);
				row.addTableData(active);
				row.addTableData(edit);

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
	public Table buildNewWorkCenterTable(ManufacturingOrder dto) {

		Table table = new Table();
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

		TableRow row = new TableRow();

		String desc = "";
		int pph = 0;
		if (dto.getEditFlag()) {

			desc = dto.getDescription();
			pph = dto.getPphThresholdPercentage();

		}
		InputField description = new InputField("description", desc,
				InputType.TEXT);
		description.addAttribute("maxlength", "35");
		TableData descriptionText = new TableData(description);
		descriptionText.addAttribute("style", "width: 32%");

		InputField pphPercentageText = new InputField("pph_TextField", String
				.valueOf(pph), InputType.TEXT);
		pphPercentageText.addAttribute("size", "5");
		pphPercentageText.addAttribute("maxlength", "3");
		TableData pphPercentage = new TableData(pphPercentageText);
		pphPercentage.addAttribute("style", "width: 12%");

		TableData scaleRequired = null;

		if (dto.getEditFlag()
				&& (dto.getScaleRequired() == null || !("Y".equals(dto
						.getScaleRequired())))) {

			scaleRequired = new TableData(
					new SimpleText(
							"<input type=\"checkbox\"  name=\"scaleReqd_Chk\" value=\"Y\"/>"));
			scaleRequired.addAttribute("style", "width: 12%");
		} else {
			scaleRequired = new TableData(
					new SimpleText(
							"<input type=\"checkbox\"  name=\"scaleReqd_Chk\" value=\"Y\" checked=\"checked\"/>"));
		}
		scaleRequired.addAttribute("style", "width: 12%");
		
		//below code is added for ticket 414799 by saikiran
		TableData scrapCount = null;

		if (dto.getEditFlag()
				&& (dto.getScrapCount() == null || !("Y".equals(dto.getScrapCount())))) {

			scrapCount = new TableData(
					new SimpleText(
							"<input type=\"checkbox\"  name=\"scrapCount_Chk\" value=\"Y\"/>"));
		} else {
			scrapCount = new TableData(
					new SimpleText(
							"<input type=\"checkbox\"  name=\"scrapCount_Chk\" value=\"Y\" checked=\"checked\"/>"));
		}
		scrapCount.addAttribute("style", "width: 10%");
		//end ticket 414799 by saikiran

		TableData active = null;

		if (dto.getEditFlag()
				&& (dto.getStatus() == null || !("Y".equals(dto.getStatus())))) {
			active = new TableData(
					new SimpleText(
							"<input type=\"checkbox\"  name=\"activeFlag_Chk\" value=\"Y\"/>"));
		} else {
			active = new TableData(
					new SimpleText(
							"<input type=\"checkbox\"  name=\"activeFlag_Chk\" value=\"Y\" checked=\"checked\"/>"));

		}
		active.addAttribute("style", "width: 12%");

		if (dto.getEditFlag()) {

			TableData workCenter = new TableData(new SimpleText(dto
					.getWorkCenterCode()));
			workCenter.addAttribute("style",
					"width: 13% ; vertical-align: middle");
			TableData facility = new TableData(new SimpleText(dto
					.getSelectedFacility()));
			facility.addAttribute("style",
					"width: 11% ; vertical-align: middle");
			pphPercentage.addAttribute("style", "width: 12%");
			active.addAttribute("style", "width: 12%");
			//below code is added for ticket 414799 by saikiran
			scrapCount.addAttribute("style", "width: 10%");
			scaleRequired.addAttribute("style", "width: 12%");
			descriptionText.addAttribute("style", "width: 32%");

			row.addTableData(facility);
			row.addTableData(workCenter);
		}
		row.addTableData(descriptionText);
		row.addTableData(pphPercentage);
		row.addTableData(scaleRequired);
		//below code is added for ticket 414799 by saikiran
		row.addTableData(scrapCount);
		row.addTableData(active);

		row.addAttribute("class", "normalRow");

		table.addTableRow(row);

		return table;
	}

	/**
	 * Builds the button container.
	 * 
	 * @return the container
	 */
	private Container buildButtonContainer(ManufacturingOrder dto) {

		Image cancelImage = new Image("static/images/cancel.gif", "Cancel");
		cancelImage.addAttribute("style", "cursor: pointer;");
		cancelImage.addAttribute("onClick", "javascript:cancel('"
				+ dto.getEditFlag() + "')");

		Container container = new Container(Type.DIV);
		Image updateWorkCenterImage = null;
		if (dto.getEditFlag()) {
			updateWorkCenterImage = new Image("static/images/Update.gif",
					"Update");
			updateWorkCenterImage.addAttribute("style", "cursor: pointer;");
			updateWorkCenterImage.addAttribute("onClick",
					"javascript:UpdateWorkCenterConfigDetails('"
							+ dto.getWorkCenterCode() + "','"
							+ dto.getFacility() + "')");
			container.addComponent(updateWorkCenterImage);
		}

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
	private Container buildNewButtonContainer() {

		Image cancelImage = new Image("static/images/cancel.gif", "Cancel");
		cancelImage.addAttribute("style", "cursor: pointer;");
		cancelImage.addAttribute("onClick", "javascript:cancelReload()");

		Image addWorkCenterImage = new Image("static/images/save.gif", "Save");
		addWorkCenterImage.addAttribute("style", "cursor: pointer;");
		addWorkCenterImage.addAttribute("onClick",
				"javascript:InsertNewWorkCenterDetails()");

		Container container = new Container(Type.DIV);
		container.addComponent(addWorkCenterImage);
		container.addComponent(cancelImage);
		container.addAttribute("style", "width: 100%");
		container.addAttribute("align", "center;");

		return container;
	}

}
