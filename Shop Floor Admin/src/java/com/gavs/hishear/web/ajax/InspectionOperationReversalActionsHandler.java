package com.gavs.hishear.web.ajax;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.util.HtmlUtils;
import org.springmodules.xt.ajax.AjaxResponse;
import org.springmodules.xt.ajax.AjaxResponseImpl;
import org.springmodules.xt.ajax.AjaxSubmitEvent;
import org.springmodules.xt.ajax.action.ReplaceContentAction;
import org.springmodules.xt.ajax.component.Component;
import org.springmodules.xt.ajax.component.Container;
import org.springmodules.xt.ajax.component.Container.Type;
import org.springmodules.xt.ajax.component.H5;
import org.springmodules.xt.ajax.component.Image;
import org.springmodules.xt.ajax.component.SimpleText;
import org.springmodules.xt.ajax.component.Table;
import org.springmodules.xt.ajax.component.TableData;
import org.springmodules.xt.ajax.component.TableRow;

import com.gavs.hishear.web.domain.ReversalDto;
import com.gavs.hishear.web.domain.Sequence;

public class InspectionOperationReversalActionsHandler extends BaseAjaxHandler {
	private static final String BLUE = "blue";
	private static final String RED = "red";
	private static final String ERROR = "ERROR";
	private static final String DASH = "-";
	private static final String NO_DATA_FOUND = "No Data Found!";

	public AjaxResponse showDetails(AjaxSubmitEvent event) {
		AjaxResponse response = new AjaxResponseImpl();
		ReversalDto dto = (ReversalDto) event.getCommandObject();
		if (dto.isDetailsExist()) {
			ReplaceContentAction replaceContentAction = new ReplaceContentAction(
					"details", buildDetailsTale(dto));
			response.addAction(replaceContentAction);

			replaceContentAction = new ReplaceContentAction("buttons",
					buildButtons());
			response.addAction(replaceContentAction);
		} else {
			String errorMessage = null;
			List errors = event.getValidationErrors().getAllErrors();
			if (errors.size() > 0) {
				FieldError error = (FieldError) errors.get(0);
				errorMessage = error.getDefaultMessage();
			}

			H5 text = new H5(StringUtils.defaultString(errorMessage,
					NO_DATA_FOUND));
			text.addAttribute("style", "color: red; text-align: center;");
			ReplaceContentAction replaceContentAction = new ReplaceContentAction(
					"errorMessage", text);
			response.addAction(replaceContentAction);
			// Clear details
			replaceContentAction = new ReplaceContentAction("details",
					new SimpleText(""));

			// Clear buttons
			replaceContentAction = new ReplaceContentAction("buttons",
					new SimpleText(""));
			response.addAction(replaceContentAction);

		}

		return response;
	}

	public AjaxResponse reverse(AjaxSubmitEvent event) {
		ReversalDto dto = (ReversalDto) event.getCommandObject();
		AjaxResponse response = new AjaxResponseImpl();

		Map model = event.getModel();
		H5 text = new H5((String) model.get("message"));
		String messageType = (String) model.get("messageType");
		text.addAttribute("style", "color: " + getMessageColor(messageType)
				+ "; text-align: center;");
		ReplaceContentAction replaceContentAction = new ReplaceContentAction(
				"errorMessage", text);
		response.addAction(replaceContentAction);

		return response;
	}

	private String getMessageColor(String messageType) {
		if (ERROR.equals(messageType)) {
			return RED;
		}
		return BLUE;
	}

	private Component buildButtons() {
		Container container = new Container(Container.Type.DIV);

		Image image = new Image("static/images/Confirm.gif", "Reverse");
		image.addAttribute("border", "0");
		image.addAttribute("onclick", "return reverseThis();");

		container.addComponent(image);

		image = new Image("static/images/cancel.gif", "Reset");
		image.addAttribute("border", "0");
		image.addAttribute("onclick", "return resetThis();");

		container.addComponent(image);
		container.addAttribute("style", "text-align: center");

		return container;
	}

	private Component buildDetailsTale(ReversalDto dto) {
		Container container = new Container(Type.DIV);

		Table table = new Table();
		table.addAttribute("id", "outerTableNostripe");
		table.addAttribute("align", "center");
		// table.addAttribute("cellSpacing", "0");
		// table.addAttribute("cellPadding", "0");
		table.addAttribute("border", "1");
		table
				.addAttribute(
						"style",
						"white-space: nowrap; word-spacing: normal; width: 1000px; height:30px; border-left: 1px solid #465272; border-right: 1px solid #465272; border-top: 1px solid #465272; border-bottom: 1px solid #465272;");

		Table jobsTable = new Table();
		jobsTable.addAttribute("id", "outerTableNostripe");
		jobsTable.addAttribute("align", "center");
		jobsTable.addAttribute("border", "1");
		jobsTable
				.addAttribute(
						"style",
						"white-space: nowrap; word-spacing: normal; width: 600px; height:30px; border-left: 1px solid #465272; border-right: 1px solid #465272; border-top: 1px solid #465272; border-bottom: 1px solid #465272;");

		if (dto.isInspectionForOutsideOperation()) {
			List<Sequence> sequences = dto.getJobs();

			String workCenter = "";
			if (sequences != null && sequences.size() > 0) {
				workCenter = sequences.get(0).getWorkCenterCode();
			}

			TableRow row1 = new TableRow();
			TableData warehouseHeader = new TableData(new SimpleText(
					"Warehouse"));
			warehouseHeader.addAttribute("style", getHeaderStyle());
			TableData warehouseData = new TableData(new SimpleText(HtmlUtils
					.htmlEscape(StringUtils.defaultString(dto.getWarehouse(),
							DASH))));
			warehouseData.addAttribute("style",
					"width: 150px; font-weight: bold");
			row1.addTableData(warehouseHeader);
			row1.addTableData(warehouseData);

			TableData workcenterHeader = new TableData(new SimpleText(
					"Work Center"));
			workcenterHeader.addAttribute("style", getHeaderStyle());
			TableData workcenterData = new TableData(new SimpleText(HtmlUtils
					.htmlEscape(workCenter)));
			workcenterData.addAttribute("style",
					"width: 150px; font-weight: bold");
			row1.addTableData(workcenterHeader);
			row1.addTableData(workcenterData);

			TableData poNumberHeader = new TableData(
					new SimpleText("PO Number"));
			poNumberHeader.addAttribute("style", getHeaderStyle());
			TableData poNumberData = new TableData(new SimpleText(HtmlUtils
					.htmlEscape(StringUtils.defaultString(dto.getPoNumber(),
							DASH))));
			poNumberData.addAttribute("style",
					"width: 150px; font-weight: bold");
			row1.addTableData(poNumberHeader);
			row1.addTableData(poNumberData);

			TableData poLineHeader = new TableData(new SimpleText("PO Line"));
			poLineHeader.addAttribute("style", getHeaderStyle());
			TableData poLineData = new TableData(new SimpleText(HtmlUtils
					.htmlEscape(StringUtils.defaultString(
							dto.getPoLineNumber(), DASH))));
			poLineData.addAttribute("style", "width: 150px; font-weight: bold");
			row1.addTableData(poLineHeader);
			row1.addTableData(poLineData);

			table.addTableRow(row1);

			if (sequences != null) {
				for (Sequence sequence : sequences) {
					TableRow row2 = new TableRow();
					TableData employeeHeader = new TableData(new SimpleText(
							"Employee"));
					employeeHeader.addAttribute("style", getHeaderStyle());
					TableData employeeData = new TableData(new SimpleText(
							HtmlUtils.htmlEscape(StringUtils.defaultString(
									sequence.getEmployeeName(), DASH))));
					employeeData.addAttribute("style",
							"width: 150px; font-weight: bold");
					row2.addTableData(employeeHeader);
					row2.addTableData(employeeData);
					TableData reportedQtyHeader = new TableData(new SimpleText(
							"Reported Qty"));
					reportedQtyHeader.addAttribute("style", getHeaderStyle());
					TableData reportedQtyData = new TableData(new SimpleText(""
							+ sequence.getQtyCompleted()));
					reportedQtyData.addAttribute("style",
							"width: 150px; font-weight: bold");
					row2.addTableData(reportedQtyHeader);
					row2.addTableData(reportedQtyData);
					jobsTable.addTableRow(row2);
				}
			}
		}

		container.addComponent(table);
		container.addComponent(new SimpleText("<BR/>"));
		container.addComponent(jobsTable);

		return container;
	}

	private String getHeaderStyle() {
		String style = "background: #96D1EB; "
				+ "border-left: 1px solid #465272; "
				+ "border-right: 1px solid #465272; "
				+ "border-top: 1px solid #465272; "
				+ "border-bottom: 1px solid #465272; " + "font-weight: bold; "
				+ "padding: 4px 3px; " + "text-align: left ";
		return style;
	}
}
