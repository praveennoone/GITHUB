/**
 * AssetConfigActionHandler.java
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

import com.gavs.hishear.web.domain.AssetConfig;
import com.gavs.hishear.web.domain.ManufacturingOrder;

/**
 * @author Ambrish_V
 * 
 */
public class AssetConfigActionHandler extends BaseAjaxHandler {

	/**
	 * Gets the Work Center details.
	 * 
	 * @param event
	 *            the event
	 * @return the Work Center details
	 */
	public AjaxResponse getAssetConfigDetails(AjaxSubmitEvent event) {

		AjaxResponse response = new AjaxResponseImpl();

		System.out
				.println(" Handler Called : Work Center........................ ");

		ManufacturingOrder dto = (ManufacturingOrder) event.getCommandObject();

		ArrayList<AssetConfig> asset = dto.getAssetConfig();

		if (asset != null && asset.size() > 0) {

			getAssetTableDetails(response, dto);

		} else {

			ReplaceContentAction assetDetailAction = new ReplaceContentAction(
					"assetDetailDiv", new SimpleText(""));
			ReplaceContentAction buttonAction = new ReplaceContentAction(
					"buttonsDiv", new SimpleText(""));
			ReplaceContentAction messageAction = new ReplaceContentAction(
					"statusMessage",
					new SimpleText(
							"<font color=\"red\" size=\"2px\"><b>No Data Found for the Given Search Criteria</b></font>"));
			ExecuteJavascriptFunctionAction enableFields = new ExecuteJavascriptFunctionAction(
					"enableFields", new HashMap<String, Object>());
			response.addAction(enableFields);
			response.addAction(assetDetailAction);
			response.addAction(buttonAction);
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
	public AjaxResponse addNewAsset(AjaxSubmitEvent event) {

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

			getNewAssetTable(response, dto);

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
	public AjaxResponse updateAssetConfigDetails(AjaxSubmitEvent event) {

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
	public AjaxResponse insertNewAssetDetails(AjaxSubmitEvent event) {

		AjaxResponse response = new AjaxResponseImpl();

		ManufacturingOrder dto = (ManufacturingOrder) event.getCommandObject();

		if ((dto.getError() != null) && !(dto.getError().isEmpty())) {

			ReplaceContentAction action = new ReplaceContentAction(
					"statusMessage", new SimpleText(
							"<font color=\"red\" size=\"2px\">"
									+ dto.getError() + "</font>"));
			response.addAction(action);

			ReplaceContentAction buttonAction = new ReplaceContentAction(
					"buttonsDiv", buildNewButtonContainer());
			response.addAction(buttonAction);

			ExecuteJavascriptFunctionAction enableFields = new ExecuteJavascriptFunctionAction(
					"enableAssetFields", new HashMap<String, Object>());
			response.addAction(enableFields);

			dto.setError("");

		} else {
			// Success Message
			ReplaceContentAction action = new ReplaceContentAction(
					"statusMessage",
					new SimpleText(
							"<font color=\"green\" size=\"2px\">Added Successfully</font>"));
			response.addAction(action);

			getAssetTableDetails(response, dto);

		}

		return response;

	}

	/**
	 * @param response
	 * @param dto
	 */
	public void getAssetTableDetails(AjaxResponse response,
			ManufacturingOrder dto) {

		ReplaceContentAction action = null;

		System.out.println("in table details ");

		Container containerTotal = new Container(Container.Type.DIV);
		containerTotal.addAttribute("style", "width: 610px");
		containerTotal.addAttribute("align", "center");

		Container container = new Container(Container.Type.DIV);
		container
				.addAttribute("style",
						"width: 610px; overflow: scroll; overflow-x: hidden; height: 245px");

		container.addComponent(buildAssetDetailsTable(dto));

		containerTotal.addComponent(getHeaderForAssetDetailTable());
		containerTotal.addComponent(container);

		action = new ReplaceContentAction("assetDetailDiv", containerTotal);
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
				"static/images/addNewAsset_disable.gif", "Add New Asset");
		disableAddNewImage.addAttribute("style", "cursor: default;");

		ReplaceContentAction addNewbuttonAction = new ReplaceContentAction(
				"addNewButtonDiv", disableAddNewImage);
		response.addAction(addNewbuttonAction);

		System.out.println("preparing viewing the page" + response.isEmpty());

	}

	public void getNewAssetTable(AjaxResponse response, ManufacturingOrder dto) {

		ReplaceContentAction action = null;

		System.out.println("in new table details ");

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
		container.addComponent(buildNewAssetTable(dto));

		containerTotal.addComponent(getHeaderForNewAssetTable(dto));
		containerTotal.addComponent(container);

		if (dto.getEditFlag()) {

			action = new ReplaceContentAction("editDiv", containerTotal);
			response.addAction(action);

			ReplaceContentAction buttonAction = new ReplaceContentAction(
					"buttonsDiv", buildButtonContainer(dto));
			response.addAction(buttonAction);

		} else {

			action = new ReplaceContentAction("assetDetailDiv", containerTotal);
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
				"static/images/addNewAsset_disable.gif", "Add New Asset");
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
	private Table getHeaderForAssetDetailTable() {

		TableHeaderData facilityHeaderData = new TableHeaderData(
				new SimpleText("Facility"));
		facilityHeaderData.addAttribute("style", "width: 10%");

		TableHeaderData workCenterHeaderData = new TableHeaderData(
				new SimpleText("Work Center"));
		workCenterHeaderData.addAttribute("style", "width: 15%");

		TableHeaderData assetNumberHeaderData = new TableHeaderData(
				new SimpleText("Asset"));
		assetNumberHeaderData.addAttribute("style", "width: 20%");

		TableHeaderData descriptionHeaderData = new TableHeaderData(
				new SimpleText("Description"));
		descriptionHeaderData.addAttribute("style", "width: 35%");

		TableHeaderData statusHeaderData = new TableHeaderData(new SimpleText(
				"Active"));
		statusHeaderData.addAttribute("style", "width: 10%");

		TableHeaderData editHeaderData = new TableHeaderData(new SimpleText(
				"Edit"));
		editHeaderData.addAttribute("style", "width: 16%");

		ArrayList<TableHeaderData> headers = new ArrayList<TableHeaderData>();
		headers.add(facilityHeaderData);
		headers.add(workCenterHeaderData);
		headers.add(assetNumberHeaderData);
		headers.add(descriptionHeaderData);
		headers.add(statusHeaderData);
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
				"white-space: nowrap; word-spacing: normal; width: 610px");
		table.addTableHeaderAttribute("class", "fixedHeader");
		table.addTableBodyAttribute("class", "scrollContent");

		return table;

	}

	/**
	 * Gets the header table for sequence.
	 * 
	 * @return the header table for sequence
	 */
	private Table getHeaderForNewAssetTable(ManufacturingOrder dto) {

		TableHeaderData assetNumberHeaderData = new TableHeaderData(
				new SimpleText("Asset #"));
		assetNumberHeaderData.addAttribute("style", "width: 22%");

		TableHeaderData descriptionHeaderData = new TableHeaderData(
				new SimpleText("Description"));

		TableHeaderData statusHeaderData = new TableHeaderData(new SimpleText(
				"Active"));

		ArrayList<TableHeaderData> headers = new ArrayList<TableHeaderData>();

		if (dto.getEditFlag()) {

			TableHeaderData facilityHeaderData = new TableHeaderData(
					new SimpleText("Facility"));
			facilityHeaderData.addAttribute("style", "width:10%");

			TableHeaderData workCenterHeaderData = new TableHeaderData(
					new SimpleText("Work Center"));
			workCenterHeaderData.addAttribute("style", "width: 10%");

			TableHeaderData assetHeaderData = new TableHeaderData(
					new SimpleText("Asset"));
			assetHeaderData.addAttribute("style", "width: 10%");

			statusHeaderData.addAttribute("style", "width: 10%");
			descriptionHeaderData.addAttribute("style", "width: 30%");
			headers.add(facilityHeaderData);
			headers.add(workCenterHeaderData);
			headers.add(assetHeaderData);
			headers.add(descriptionHeaderData);
			headers.add(statusHeaderData);
		} else {
			descriptionHeaderData.addAttribute("style", "width: 30%");
			statusHeaderData.addAttribute("style", "width: 15%");

			headers.add(assetNumberHeaderData);
			headers.add(descriptionHeaderData);
			headers.add(statusHeaderData);

		}

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
	public Table buildAssetDetailsTable(ManufacturingOrder dto) {

		Table table = new Table();
		table.addAttribute("id", "normalTableNostripe");
		table.addAttribute("align", "center");
		table.addAttribute("cellSpacing", "0");
		table.addAttribute("cellPadding", "0");
		table.addAttribute("border", "1");
		table.addAttribute("class", "classContentTable");
		table.addAttribute("style",
				"white-space: nowrap; word-spacing: normal; width: 610px");
		table.addTableHeaderAttribute("class", "fixedHeader");
		table.addTableBodyAttribute("class", "scrollContent");

		if (dto.getAssetConfig() != null && dto.getAssetConfig().size() > 0) {

			int count = 0;

			for (AssetConfig asset : dto.getAssetConfig()) {

				TableRow row = new TableRow();

				TableData facility = new TableData(new SimpleText(
						validateDisplayField(asset.getFacility())));
				facility.addAttribute("style",
						"width: 10% ; vertical-align: middle");

				TableData workCenterCode = new TableData(new SimpleText(
						validateDisplayField(asset.getWorkCenterCode())));
				workCenterCode.addAttribute("style",
						"width: 15% ; vertical-align: middle");

				TableData description = new TableData(new SimpleText(
						validateDisplayField(asset.getDescription())));
				description.addAttribute("style",
						"width: 35% ; vertical-align: middle");

				TableData assetNumber = new TableData(new SimpleText(
						validateDisplayField(/*asset.getAssetNumber()*/"123456789123456")));
				assetNumber.addAttribute("style",
						"width: 20% ; vertical-align: middle");

				String statusFlag = asset.getStatus();

				TableData status;
				if (!"A".equalsIgnoreCase(statusFlag)) {
					status = new TableData(
							new SimpleText(
									"<input type=\"checkbox\"  name=\"statusFlag_Chkbox\" value=\"A\" />"));
				} else {
					status = new TableData(
							new SimpleText(
									"<input type=\"checkbox\"  name=\"statusFlag_Chkbox\" value=\"A\" checked=\"checked\"/>"));
				}
				status.addAttribute("style",
						"width: 10% ; vertical-align: middle");
				status.addAttribute("disabled", "disabled");

				TableData editImage = new TableData(new Image(
						"static/images/edit.jpg", "Edit"));
				editImage.addAttribute("style", "width: 10%");
				editImage.addAttribute("onClick", "javascript:edit('" + count
						+ "','"
						+ validateDisplayField(asset.getWorkCenterCode())
						+ "','" + asset.getFacility() + "','"
						+ validateDisplayField(asset.getDescription()) + "','"
						+ asset.getAssetNumber() + "','" + asset.getStatus()
						+ "');");
				editImage.addAttribute("style", "cursor: pointer;");

				row.addTableData(facility);
				row.addTableData(workCenterCode);
				row.addTableData(assetNumber);
				row.addTableData(description);
				row.addTableData(status);
				row.addTableData(editImage);

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
	public Table buildNewAssetTable(ManufacturingOrder dto) {

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

		InputField assetNumberText = new InputField("assetNumber", "",
				InputType.TEXT);
		assetNumberText.addAttribute("maxlength", "6");
		assetNumberText.addAttribute("size", "10");
		TableData asset = null;
		if (dto.getEditFlag()) {
			asset = new TableData(new SimpleText(dto.getAssetNumber()));
			asset.addAttribute("style", "width: 10% ; vertical-align: middle");

		} else {
			asset = new TableData(assetNumberText);
			asset.addAttribute("style", "width: 30%");
		}
		String desc = "";

		if (dto.getEditFlag()) {
			desc = dto.getDescription();
		}

		InputField description = new InputField("descrip", desc, InputType.TEXT);
		description.addAttribute("maxlength", "100");
		description.addAttribute("size", "30");
		TableData descriptionText = new TableData(description);
		descriptionText.addAttribute("style", "width: 40%");

		TableData status = null;
		if (dto.getEditFlag()) {

			if (dto.getStatus() == null || !("A".equals(dto.getStatus()))) {

				status = new TableData(
						new SimpleText(
								"<input type=\"checkbox\"  name=\"statusFlag_Chk\" value=\"A\"/>"));
			} else {
				status = new TableData(
						new SimpleText(
								"<input type=\"checkbox\"  name=\"statusFlag_Chk\" value=\"A\" checked=\"checked\"/>"));
			}

			TableData facility = new TableData(new SimpleText(dto
					.getSelectedFacility()));
			facility.addAttribute("style",
					"width: 10% ; vertical-align: middle");

			TableData workCenter = new TableData(new SimpleText(dto
					.getWorkCenterCode()));
			workCenter.addAttribute("style",
					"width: 10% ; vertical-align: middle");
			row.addTableData(facility);
			row.addTableData(workCenter);
			status.addAttribute("style", "width: 10%");
			descriptionText.addAttribute("style", "width: 30%");

		} else {
			status = new TableData(
					new SimpleText(
							"<input type=\"checkbox\"  name=\"statusFlag_Chk\" value=\"A\" checked=\"checked\"/>"));
			status.addAttribute("style", "width: 20%");
		}
		row.addTableData(asset);
		row.addTableData(descriptionText);
		row.addTableData(status);

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
		Image updateAssetImage = null;

		if (dto.getEditFlag()) {
			updateAssetImage = new Image("static/images/Update.gif", "Update");
			updateAssetImage.addAttribute("style", "cursor: pointer;");
			updateAssetImage.addAttribute("onClick",
					"javascript:UpdateAssetConfigDetails('"
							+ dto.getWorkCenterCode() + "','"
							+ dto.getFacility() + "','" + dto.getAssetNumber()
							+ "')");

			container.addComponent(updateAssetImage);
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

		Image addAssetImage = new Image("static/images/save.gif", "Save");
		addAssetImage.addAttribute("style", "cursor: pointer;");
		addAssetImage.addAttribute("onClick",
				"javascript:InsertNewAssetDetails()");

		Container container = new Container(Type.DIV);
		container.addComponent(addAssetImage);
		container.addComponent(cancelImage);
		container.addAttribute("style", "width: 100%");
		container.addAttribute("align", "center;");

		return container;
	}

}
