/**
 * 
 */
package com.gavs.hishear.web.ajax;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springmodules.xt.ajax.AbstractAjaxHandler;
import org.springmodules.xt.ajax.AjaxResponse;
import org.springmodules.xt.ajax.AjaxResponseImpl;
import org.springmodules.xt.ajax.AjaxSubmitEvent;
import org.springmodules.xt.ajax.action.ReplaceContentAction;
import org.springmodules.xt.ajax.component.Anchor;
import org.springmodules.xt.ajax.component.Container;
import org.springmodules.xt.ajax.component.Container.Type;
import org.springmodules.xt.ajax.component.Option;
import org.springmodules.xt.ajax.component.Select;
import org.springmodules.xt.ajax.component.SimpleText;
import org.springmodules.xt.ajax.component.Table;
import org.springmodules.xt.ajax.component.TableData;
import org.springmodules.xt.ajax.component.TableHeader;
import org.springmodules.xt.ajax.component.TableHeaderData;
import org.springmodules.xt.ajax.component.TableRow;

import com.gavs.hishear.web.domain.ErrorLog;
import com.gavs.hishear.web.domain.M3RequestLog;

/**
 * @author mohammeda
 * 
 */
public class M3TransactionLogActionsHandler extends AbstractAjaxHandler {

	public AjaxResponse displayProgramNames(AjaxSubmitEvent event) {
		M3RequestLog dto = (M3RequestLog) event.getCommandObject();
		AjaxResponse response = new AjaxResponseImpl();
		ReplaceContentAction contentAction = null;
		if (dto.getM3RequestLogs() == null
				|| dto.getM3RequestLogs().size() == 0) {
			contentAction = new ReplaceContentAction(
					"statusMessage",
					new SimpleText(
							"<font color=\"red\" size=\"2px\"><b> No Data Found </b></font>"));
		} else {
			contentAction = new ReplaceContentAction("programNameId",
					buildProgramNames(dto.getM3RequestLogs()));
		}
		response.addAction(contentAction);
		return response;
	}

	private Table buildProgramNames(ArrayList<ErrorLog> m3RequestLogs) {
		Select programNamesSelect = new Select("programName");
		programNamesSelect.addAttribute("style", "width: 100px;");
		programNamesSelect.addAttribute("onchange", "displayProgramDetails()");
		Option option = new Option("", "");
		programNamesSelect.addOption(option);

		ArrayList<String> distinctProgramNames = new ArrayList<String>();
		if (m3RequestLogs != null) {
			for (ErrorLog errorLog : m3RequestLogs) {
				if (!distinctProgramNames
						.contains(errorLog.getWebServiceName())) {
					distinctProgramNames.add(errorLog.getWebServiceName());
				}
			}
		}
		for (String programName : distinctProgramNames) {
			option = new Option(programName, programName);
			programNamesSelect.addOption(option);
		}

		TableData programNameData = new TableData(new SimpleText(
				"<b>Program Name : </b>"));
		TableData programsData = new TableData(programNamesSelect);

		Container container = new Container(Type.DIV);
		container.addAttribute("id", "functionDiv");
		TableData functionData = new TableData(container);

		TableRow programsRow = new TableRow();
		programsRow.addTableData(programNameData);
		programsRow.addTableData(programsData);
		programsRow.addTableData(functionData);

		Table table = new Table();
		table.addTableRow(programsRow);
		table.addAttribute("cellpadding", "3");

		System.out.println(table.render());
		return table;
	}

	public AjaxResponse displayProgramDetails(AjaxSubmitEvent event) {
		M3RequestLog dto = (M3RequestLog) event.getCommandObject();
		AjaxResponse response = new AjaxResponseImpl();

		Container replaceFunctionName = new Container(Type.DIV);
		replaceFunctionName
				.addComponent(new SimpleText("<b>Function Name : </b>"
						+ dto.getFunctionName().toUpperCase()));
		ReplaceContentAction functionAction = new ReplaceContentAction(
				"functionDiv", replaceFunctionName);
		response.addAction(functionAction);

		ReplaceContentAction contentAction = new ReplaceContentAction(
				"programDetailsDiv", buildProgramDetails(dto));
		response.addAction(contentAction);
		return response;
	}

	private Container buildProgramDetails(M3RequestLog dto) {
		// Building Headers
		List<TableHeaderData> headerDatas = new ArrayList<TableHeaderData>();
		for (String columnHeader : dto.getDataHeaders()) {
			TableHeaderData columnHeaderData = new TableHeaderData(
					new SimpleText(columnHeader));
			headerDatas.add(columnHeaderData);
		}
		TableHeader tableHeader = new TableHeader(headerDatas);

		// Building Data for display
		Table table = new Table();
		table.setTableHeader(tableHeader);
		table.addTableAttribute("id", "normalTableNostripe");
		table.addTableHeaderAttribute("class", "fixedHeader");
		table.addAttribute("width", "98%");

		for (Map<String, String> map : dto.getProgramDetails()) {
			TableRow detailsRow = new TableRow();
			TableData columnData = null;
			for (String headerData : dto.getDataHeaders()) {
				String columnValue = map.get(headerData);
				if (columnValue == null || columnValue.equals("")
						|| columnValue.equals("null")) {
					columnData = new TableData(new SimpleText(" "));
				} else {
					columnData = new TableData(new SimpleText(columnValue));
				}
				detailsRow.addTableData(columnData);
			}
			table.addTableRow(detailsRow);
		}

		Anchor excelExportAnchor = new Anchor("javascript:exportToExcel();",
				new SimpleText("Export To Excel"));

		TableData excelExportData = new TableData(excelExportAnchor);
		excelExportData.addAttribute("style",
				"text-align: right; font-weight: bold;");

		TableRow excelExportRow = new TableRow();
		excelExportRow.addTableData(excelExportData);

		Table excelExportTable = new Table();
		excelExportTable.addTableRow(excelExportRow);
		excelExportTable.addAttribute("width", "99%");

		Container detailsContainer = new Container(Type.DIV);
		detailsContainer
				.addAttribute("style", "overflow: auto; height: 265px;");
		detailsContainer.addComponent(table);

		Container container = new Container(Type.DIV);
		container.addComponent(detailsContainer);
		container.addComponent(new SimpleText("<br/>"));
		container.addComponent(excelExportTable);
		container.addAttribute("style", "overflow-y: auto; width: 99%");
		return container;
	}

}
