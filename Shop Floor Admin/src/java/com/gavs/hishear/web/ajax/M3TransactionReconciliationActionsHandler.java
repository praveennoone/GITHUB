/**
 * 
 */
package com.gavs.hishear.web.ajax;

import java.util.ArrayList;
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
import org.springmodules.xt.ajax.component.TableRow;

import com.gavs.hishear.web.domain.ErrorLog;
import com.gavs.hishear.web.domain.M3RequestLog;

/**
 * @author mohammeda
 * 
 */
public class M3TransactionReconciliationActionsHandler extends
		AbstractAjaxHandler {

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

	private Table buildTableHeaders(String[] headers, int headerWidth) {
		TableRow headerRow = new TableRow();
		TableData headerValueData = null;
		for (String header : headers) {
			headerValueData = new TableData(new SimpleText(header));
			headerValueData.addAttribute("style", "width: " + headerWidth
					+ "%;");
			headerRow.addTableData(headerValueData);
		}

		Table headerTable = new Table();
		headerTable.addTableRow(headerRow);
		headerTable.addTableAttribute("id", "normalTableHeaderStyle");
		headerTable.addAttribute("width", "96%");
		return headerTable;
	}

	private Container buildProgramDetails(M3RequestLog dto) {
		// Building Headers & Titles
		TableData boltOnTitleData = new TableData(new SimpleText(
				"<b>Bolt On Transactions</b>"));
		boltOnTitleData.addAttribute("style", "text-align: left; width : 50%");

		TableData m3TitleData = new TableData(new SimpleText(
				"<b>M3 Transactions</b>"));
		m3TitleData.addAttribute("style", "text-align: left; width : 50%");

		TableRow titleTableRow = new TableRow();
		titleTableRow.addTableData(boltOnTitleData);
		titleTableRow.addTableData(m3TitleData);

		int headerWidth = 100 / dto.getDataHeaders().length;
		TableData boltOnHeaderTableData = new TableData(buildTableHeaders(dto
				.getDataHeaders(), headerWidth));
		TableData m3HeaderTableData = new TableData(buildTableHeaders(dto
				.getDataHeadersForM3Trans(), headerWidth));

		TableRow headerTableRow = new TableRow();
		headerTableRow.addTableData(boltOnHeaderTableData);
		headerTableRow.addTableData(m3HeaderTableData);

		Table titleAndHeaderTable = new Table();
		titleAndHeaderTable.addTableRow(titleTableRow);
		titleAndHeaderTable.addTableRow(headerTableRow);
		titleAndHeaderTable.addAttribute("width", "98%");

		// Building Bolt on transactions data for display
		Table boltOnTransactionstable = new Table();
		boltOnTransactionstable.addTableAttribute("id", "normalTableNostripe");
		boltOnTransactionstable.addTableHeaderAttribute("class", "fixedHeader");
		boltOnTransactionstable.addAttribute("width", "96%");

		// Build Bolt On data.
		buildDataRow(boltOnTransactionstable, dto.getProgramDetails(), dto
				.getDataHeaders(), headerWidth);

		// Building M3 transactions data for display
		Table m3TransactionsTable = new Table();
		m3TransactionsTable.addTableAttribute("id", "normalTableNostripe");
		m3TransactionsTable.addTableHeaderAttribute("class", "fixedHeader");
		m3TransactionsTable.addAttribute("width", "96%");

		// Build M3 Transaction data.
		buildDataRow(m3TransactionsTable, dto.getM3TransByDate(), dto
				.getDataHeadersForM3Trans(), headerWidth);

		TableData botOnTransData = new TableData(boltOnTransactionstable);
		botOnTransData.addAttribute("style", "vertical-align: top; width: 50%");

		TableData m3TransData = new TableData(m3TransactionsTable);
		m3TransData.addAttribute("style", "vertical-align: top; width: 50%");

		TableRow tableRow = new TableRow();
		tableRow.addTableData(botOnTransData);
		tableRow.addTableData(m3TransData);

		Table table = new Table();
		table.addTableRow(tableRow);
		table.addAttribute("width", "98%");

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
		container.addComponent(titleAndHeaderTable);
		container.addComponent(detailsContainer);
		container.addComponent(new SimpleText("<br/>"));
		container.addComponent(excelExportTable);
		container.addAttribute("style", "overflow-y: auto; width: 99%");
		return container;
	}

	private void buildDataRow(Table table, ArrayList<Map<String, String>> data,
			String[] headers, int headerWidth) {
		for (Map<String, String> map : data) {
			TableRow detailsRow = new TableRow();
			TableData columnData = null;
			for (String headerData : headers) {
				String columnValue = map.get(headerData);
				if (columnValue == null || columnValue.equals("")
						|| columnValue.equals("null")) {
					columnData = new TableData(new SimpleText(" "));
				} else {
					columnData = new TableData(new SimpleText(columnValue));
				}
				columnData
						.addAttribute("style", "width: " + headerWidth + "%;");
				detailsRow.addTableData(columnData);
			}
			table.addTableRow(detailsRow);
		}
	}

}
