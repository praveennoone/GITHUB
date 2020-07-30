package com.gavs.hishear.web.ajax;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springmodules.xt.ajax.AbstractAjaxHandler;
import org.springmodules.xt.ajax.AjaxActionEvent;
import org.springmodules.xt.ajax.AjaxResponse;
import org.springmodules.xt.ajax.AjaxResponseImpl;
import org.springmodules.xt.ajax.action.ReplaceContentAction;
import org.springmodules.xt.ajax.component.Option;
import org.springmodules.xt.ajax.component.SimpleText;
import org.springmodules.xt.ajax.component.Table;
import org.springmodules.xt.ajax.component.TableData;
import org.springmodules.xt.ajax.component.TableHeader;
import org.springmodules.xt.ajax.component.TableRow;

import com.gavs.hishear.util.DateUtil;
import com.gavs.hishear.web.context.UserContext;
import com.gavs.hishear.web.reports.factory.dao.FactoryDao;
import com.gavs.hishear.web.reports.factory.domain.FactoryDto;


/**
 * @author Bashkar Shanmugam
 * 
 */

public class FactorySummeryActionsHandler extends AbstractAjaxHandler {

	private static final int SECONDS_PER_MINUTE = 60;

	private UserContext userContext;

	private FactoryDao dao;

	/**
	 * @return the dao
	 */
	public FactoryDao getDao() {
		return dao;
	}

	/**
	 * @param dao
	 *            the dao to set
	 */
	public void setDao(FactoryDao dao) {
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

	public AjaxResponse getDepartmentDetails(AjaxActionEvent event) {

		AjaxResponse response = new AjaxResponseImpl();
		FactoryDto report = (FactoryDto) event.getCommandObject();

		HttpServletRequest request = event.getHttpRequest();

		String factory = request.getParameter("factory");

		if (factory != null) {
			report.setFactory(factory);
		}

		report.setUserName(userContext.getUserName());
		report.setDisplayName(userContext.getDisplayName());
		report.setQueries(userContext.getQueries());
		// -----------
		report.setQuery((String) userContext.getQueries().get("SHOPR_068"));
		ArrayList reports = dao.getDepartmentDetails(report);

		List options = new ArrayList();
		options.add(new Option("ALL", "ALL"));
		for (int i = 0; i < reports.size(); i++) {
			FactoryDto objReport = (FactoryDto) reports.get(i);
			String departmentid = objReport.getDepartmentId();
			String department = objReport.getDepartment();

			String dept = departmentid + "--->" + department;
			Option first = new Option(departmentid, dept);
			options.add(first);
		}

		report.setArrDepartments(reports);
		request.setAttribute("command", report);
		event.setCommandObject(report);

		ReplaceContentAction BaseAssetsAction = new ReplaceContentAction(
				"department", options);

		// Add the actions:
		response.addAction(BaseAssetsAction);

		return response;
	}

	public AjaxResponse getAllDetails(AjaxActionEvent event) {
		AjaxResponse response = new AjaxResponseImpl();
		FactoryDto dto = (FactoryDto) event.getCommandObject();

		HttpServletRequest request = event.getHttpRequest();
		String departmentId = request.getParameter("department");

		if (departmentId != null) {
			dto.setDepartmentId(departmentId);
		}

		String fromDate = request.getParameter("fromDate");

		if (departmentId != null) {
			dto.setFromDate(fromDate);
		}

		String toDate = request.getParameter("toDate");

		if (departmentId != null) {
			dto.setToDate(toDate);
		}

		// String reportType = "asset";
		dto.setUserName(userContext.getUserName());
		dto.setDisplayName(userContext.getDisplayName());
		dto.setQueries(userContext.getQueries());

		request.setAttribute("command", dto);
		event.setCommandObject(dto);

		dto.setQuery((String) userContext.getQueries().get("SHOPR_069"));
		String numberOfAssets = dao.getAssetCountForDept(dto);
		if (numberOfAssets != null) {
			SimpleText text = new SimpleText(numberOfAssets);
			response.addAction(new ReplaceContentAction("assetCount", text));
		} else {
			SimpleText empty = new SimpleText("");
			response.addAction(new ReplaceContentAction("assetCount", empty));
		}

		dto.setQuery((String) userContext.getQueries().get("SHOPR_074"));
		String numberOfOperators = dao.getOperatorCountForDept(dto);
		if (numberOfOperators != null) {
			SimpleText text = new SimpleText(numberOfOperators);
			response.addAction(new ReplaceContentAction("operatorCount", text));
		} else {
			SimpleText empty = new SimpleText("");
			response
					.addAction(new ReplaceContentAction("operatorCount", empty));
		}

		dto.setQuery((String) userContext.getQueries().get("SHOPR_075"));
		String jobCurrentlyProcessing = dao
				.getJobCurrentlyProcessingForDept(dto);
		if (jobCurrentlyProcessing != null) {
			SimpleText text = new SimpleText(jobCurrentlyProcessing);
			response.addAction(new ReplaceContentAction(
					"jobCurrentlyProcessing", text));
		} else {
			SimpleText empty = new SimpleText("");
			response.addAction(new ReplaceContentAction(
					"jobCurrentlyProcessing", empty));
		}

		dto.setQuery((String) userContext.getQueries().get("SHOPR_076"));
		String jobCurrentlyPending = dao.getJobsCurrentlyPendingForDept(dto);
		if (jobCurrentlyPending != null) {
			SimpleText text = new SimpleText(jobCurrentlyPending);
			response.addAction(new ReplaceContentAction("jobCurrentlyPending",
					text));
		} else {
			SimpleText empty = new SimpleText("");
			response.addAction(new ReplaceContentAction("jobCurrentlyPending",
					empty));
		}

		dto.setQuery((String) userContext.getQueries().get("SHOPR_077"));
		String partsProcessed = dao.getPartsProcessedForDept(dto);
		if (partsProcessed != null) {
			SimpleText text = new SimpleText(partsProcessed);
			response
					.addAction(new ReplaceContentAction("partsProcessed", text));
		} else {
			SimpleText empty = new SimpleText("");
			response
					.addAction(new ReplaceContentAction("partsProcessed", empty));
		}

		SimpleText text = new SimpleText("Yield Report");
		response.addAction(new ReplaceContentAction("yield", text));
		return response;
	}

	/**
	 * This method returns Asset Number and Asset Description
	 * 
	 * @param event
	 * @return
	 */
	public AjaxResponse getAssetReport(AjaxActionEvent event) {

		AjaxResponse response = new AjaxResponseImpl();
		FactoryDto report = (FactoryDto) event.getCommandObject();

		HttpServletRequest request = event.getHttpRequest();
		// report.setDepartmentId(request.getParameter("dept"));
		report.setFromDate(request.getParameter("fromDate"));
		report.setToDate(request.getParameter("toDate"));
		// report.setAssetNumber(request.getParameter("assetNumber"));

		printReportInputs(report);

		report.setUserName(userContext.getUserName());
		report.setDisplayName(userContext.getDisplayName());
		report.setQueries(userContext.getQueries());
		report.setQuery((String) userContext.getQueries().get("SHOPR_067"));
		ArrayList reports = dao.getAssetReport(report);

		report.setAssetReports(reports);
		request.setAttribute("command", report);
		event.setCommandObject(report);
		Table table = new Table();

		if (reports != null && reports.size() != 0) {

			String[] headers = { "Asset#", "AssetDesc" };
			TableHeader header = new TableHeader(headers);
			table.setTableHeader(header);
			table.addTableHeaderAttribute("class", "fixedHeader");
			table.addAttribute("id", "normalTableNostripe");
			table.addAttribute("align", "center");
			table.addAttribute("width", "97%");

			for (int i = 0; i < reports.size(); i++) {

				FactoryDto objFactoryDto = (FactoryDto) reports.get(i);

				TableRow row = new TableRow();
				if ((i % 2) == 0) {
					row.addAttribute("class", "normalRow");
				} else {
					row.addAttribute("class", "alternateRow");
				}
				if (objFactoryDto.getAssetNumber() != null) {
					String value = "<a style=\"text-decoration: underline\" href=\"javascript:popupWindowWithParams('popupFactoryAsset.htm?assetNumber="
							+ objFactoryDto.getAssetNumber()
							+ "&amp;dept="
							+ report.getDepartmentId()
							+ "&amp;fromDate="
							+ report.getFromDate()
							+ "&amp;toDate="
							+ report.getToDate()
							+ "','left=10,top=80,width=1000, height=400,scrollbars=no, center:yes')\" >"
							+ objFactoryDto.getAssetNumber() + "</a>";
					// value = HtmlUtils.htmlUnescape(value);

					TableData assetData = new TableData(new SimpleText(value));
					assetData.addAttribute("class", "tableContent");
					row.addTableData(assetData);
				} else {
					TableData assetData = new TableData(new SimpleText(""));
					assetData.addAttribute("class", "tableContent");
					row.addTableData(assetData);
				}
				if (objFactoryDto.getAssetDesc() != null) {

					TableData assetData = new TableData(new SimpleText(
							objFactoryDto.getAssetDesc()));
					assetData.addAttribute("class", "tableContent");
					assetData
							.addAttribute("style",
									"width:1600px;white-space: nowrap; word-spacing: normal;");
					row.addTableData(assetData);
				} else {
					TableData assetData = new TableData(new SimpleText(""));
					assetData.addAttribute("class", "tableContent");
					/*
					 * td2.addAttribute("style", "white-space: nowrap;
					 * word-spacing: normal;");
					 */row.addTableData(assetData);
				}

				table.addTableRow(row);

			}
		} else {
			table.addAttribute("id", "normalTableNostripe");
			TableRow row = new TableRow();
			TableData data = new TableData(new SimpleText(
					"No data available for the given search"));
			row.addTableData(data);
			table.addTableRow(row);
			table.addAttribute("align", "center");
		}

		ReplaceContentAction action = new ReplaceContentAction("factoryReport",
				table);
		response.addAction(action);
		return response;
	}

	private void printReportInputs(FactoryDto report) {
		System.out.println("Department------------------->"
				+ report.getDepartmentId());
		System.out.println("From Date-------------------->"
				+ report.getFromDate());
		System.out.println("To Date---------------------->"
				+ report.getToDate());
	}

	public AjaxResponse getJobsCurrentlyProcessingReport(AjaxActionEvent event) {

		AjaxResponse response = new AjaxResponseImpl();
		FactoryDto report = (FactoryDto) event.getCommandObject();

		HttpServletRequest request = event.getHttpRequest();
		// String reportType = request.getParameter("reportType");
		String reportType = "pending";
		report.setUserName(userContext.getUserName());
		report.setDisplayName(userContext.getDisplayName());
		report.setQueries(userContext.getQueries());

		report.setQuery((String) userContext.getQueries().get("SHOPR_071"));
		ArrayList reports = dao.getJobsCurrentlyProcessingReport(report);
		// printForJobsCurrentlyRunning(reports);

		report.setAssetReports(reports);
		request.setAttribute("command", report);
		event.setCommandObject(report);
		Table table = new Table();

		if (reportType != null && reportType.equalsIgnoreCase("pending")) {
			if (reports != null && reports.size() != 0) {

				String[] headers = { "MO Number", "Part#", "Description",
						"MO Qty", "Qty Processed", "Qty Pending",
						"Qty Rejected", "Yield" };
				TableHeader header = new TableHeader(headers);
				table.setTableHeader(header);
				table.addTableHeaderAttribute("class", "fixedHeader");
				table.addAttribute("id", "normalTableNostripe");
				table.addAttribute("align", "center");
				table.addAttribute("width", "96%");

				for (int i = 0; i < reports.size(); i++) {

					FactoryDto objFactoryDto = (FactoryDto) reports.get(i);

					TableRow row = new TableRow();
					if ((i % 2) == 0) {
						row.addAttribute("class", "normalRow");
					} else {
						row.addAttribute("class", "alternateRow");
					}
					if (objFactoryDto.getMoNumber() != null) {

						String value = "<a style=\"text-decoration: underline\" href=\"javascript:popupWindowWithParams('popupFactoryAsset.htm?assetNumber="
								+ objFactoryDto.getMoNumber()
								+ "&amp;dept="
								+ report.getDepartmentId()
								+ "&amp;fromDate="
								+ report.getFromDate()
								+ "&amp;toDate="
								+ report.getToDate()
								+ "','left=10,top=80,width=1000, height=400, center:yes')\" >"
								+ objFactoryDto.getMoNumber() + "</a>";

						TableData data = new TableData(new SimpleText(
								objFactoryDto.getMoNumber()));
						data.addAttribute("class", "tableContent");
						row.addTableData(data);
					} else {
						TableData data = new TableData(new SimpleText(""));
						data.addAttribute("class", "tableContent");
						row.addTableData(data);
					}
					// --------
					if (objFactoryDto.getPartNumber() != null) {
						TableData data = new TableData(new SimpleText(
								objFactoryDto.getPartNumber()));
						data.addAttribute("class", "tableContent");
						row.addTableData(data);
					} else {
						TableData data = new TableData(new SimpleText(""));
						data.addAttribute("class", "tableContent");
						row.addTableData(data);
					}
					// --------
					if (objFactoryDto.getPartDescription() != null) {
						TableData data = new TableData(new SimpleText(
								objFactoryDto.getPartDescription()));
						data.addAttribute("class", "tableContent");
						data
								.addAttribute("style",
										"width:1100px;white-space: nowrap; word-spacing: normal;");
						row.addTableData(data);
					} else {
						TableData data = new TableData(new SimpleText(""));
						data.addAttribute("class", "tableContent");
						row.addTableData(data);
					}
					// --------
					if (objFactoryDto.getQuantity() != null) {
						TableData data = new TableData(new SimpleText(
								objFactoryDto.getQuantity()));
						data.addAttribute("class", "tableContent");
						row.addTableData(data);
					} else {
						TableData data = new TableData(new SimpleText(""));
						data.addAttribute("class", "tableContent");
						row.addTableData(data);
					}
					// --------
					if (objFactoryDto.getQtyProcessed() != null) {
						TableData data = new TableData(new SimpleText(
								objFactoryDto.getQtyProcessed()));
						data.addAttribute("class", "tableContent");
						row.addTableData(data);
					} else {
						TableData data = new TableData(new SimpleText(""));
						data.addAttribute("class", "tableContent");
						row.addTableData(data);
					}
					// --------
					if (objFactoryDto.getQtyPending() != null) {
						TableData data = new TableData(new SimpleText(
								objFactoryDto.getQtyPending()));
						data.addAttribute("class", "tableContent");
						row.addTableData(data);
					} else {
						TableData data = new TableData(new SimpleText(""));
						data.addAttribute("class", "tableContent");
						row.addTableData(data);
					}
					// --------
					if (objFactoryDto.getQtyRejected() != null) {
						TableData data = new TableData(new SimpleText(
								objFactoryDto.getQtyRejected()));
						data.addAttribute("class", "tableContent");
						row.addTableData(data);
					} else {
						TableData data = new TableData(new SimpleText(""));
						data.addAttribute("class", "tableContent");
						row.addTableData(data);
					}
					// --------

					if (objFactoryDto.getYield() != null) {
						TableData data = new TableData(new SimpleText(
								objFactoryDto.getYield()));
						data.addAttribute("class", "tableContent");
						row.addTableData(data);
					} else {
						TableData data = new TableData(new SimpleText(""));
						data.addAttribute("class", "tableContent");
						row.addTableData(data);
					}
					// --------

					table.addTableRow(row);

				}
			} else {
				table.addAttribute("id", "normalTableNostripe");
				TableRow row = new TableRow();
				TableData data = new TableData(new SimpleText(
						"No data available for the given search"));
				row.addTableData(data);
				table.addTableRow(row);
				table.addAttribute("align", "center");
			}
		}

		ReplaceContentAction action = new ReplaceContentAction("factoryReport",
				table);
		response.addAction(action);
		return response;
	}

	private void printForJobsCurrentlyRunning(ArrayList reports) {
		for (Iterator iter = reports.iterator(); iter.hasNext();) {
			FactoryDto objFactoryDto = (FactoryDto) iter.next();

		}
	}

	public AjaxResponse getOperatorsReport(AjaxActionEvent event) {
		AjaxResponse response = new AjaxResponseImpl();
		FactoryDto report = (FactoryDto) event.getCommandObject();

		HttpServletRequest request = event.getHttpRequest();
		// report.setDepartmentId(request.getParameter("dept"));
		report.setFromDate(request.getParameter("fromDate"));
		report.setToDate(request.getParameter("toDate"));

		report.setUserName(userContext.getUserName());
		report.setDisplayName(userContext.getDisplayName());
		report.setQueries(userContext.getQueries());

		report.setQuery((String) userContext.getQueries().get("SHOPR_070"));
		ArrayList reports = dao.getOperatorsSummary(report);

		report.setAssetReports(reports);
		request.setAttribute("command", report);
		event.setCommandObject(report);
		Table table = new Table();

		if (reports != null && reports.size() != 0) {

			String[] headers = { "Operator Id", "Operator Name", "MO Number",
					"Asset Number" };
			TableHeader header = new TableHeader(headers);
			table.setTableHeader(header);
			table.addTableHeaderAttribute("class", "fixedHeader");
			table.addAttribute("id", "normalTableNostripe");
			table.addAttribute("align", "center");
			table.addAttribute("width", "97%");

			for (int i = 0; i < reports.size(); i++) {

				FactoryDto objFactoryDto = (FactoryDto) reports.get(i);

				TableRow row = new TableRow();
				if ((i % 2) == 0) {
					row.addAttribute("class", "normalRow");
				} else {
					row.addAttribute("class", "alternateRow");
				}
				if (objFactoryDto.getUserName() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getUserName()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}
				// -----------------
				if (objFactoryDto.getDisplayName() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getDisplayName()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}
				// -----------------
				if (objFactoryDto.getMoNumber() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getMoNumber()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}
				// -----------------
				if (objFactoryDto.getAssetNumber() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getAssetNumber()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}
				table.addTableRow(row);

			}
		} else {
			table.addAttribute("id", "normalTableNostripe");
			TableRow row = new TableRow();
			TableData data = new TableData(new SimpleText(
					"No data available for the given search"));
			row.addTableData(data);
			table.addTableRow(row);
			table.addAttribute("align", "center");
		}

		ReplaceContentAction action = new ReplaceContentAction("factoryReport",
				table);
		response.addAction(action);
		return response;
	}

	public AjaxResponse getPartsProcessedReport(AjaxActionEvent event) {
		AjaxResponse response = new AjaxResponseImpl();
		FactoryDto report = (FactoryDto) event.getCommandObject();

		HttpServletRequest request = event.getHttpRequest();
		// report.setDepartmentId(request.getParameter("dept"));
		report.setFromDate(request.getParameter("fromDate"));
		report.setToDate(request.getParameter("toDate"));

		report.setUserName(userContext.getUserName());
		report.setDisplayName(userContext.getDisplayName());
		report.setQueries(userContext.getQueries());

		report.setQuery((String) userContext.getQueries().get("SHOPR_072"));
		ArrayList reports = dao.getPartsProcessedSummary(report);

		report.setAssetReports(reports);
		request.setAttribute("command", report);
		event.setCommandObject(report);
		Table table = new Table();

		if (reports != null && reports.size() != 0) {

			String[] headers = { "Operator Id", "Operator Name", "MO Number",
					"Asset Number", "Qty Processed" };
			TableHeader header = new TableHeader(headers);
			table.setTableHeader(header);
			table.addTableHeaderAttribute("class", "fixedHeader");
			table.addAttribute("id", "normalTableNostripe");
			table.addAttribute("align", "center");
			table.addAttribute("width", "97%");

			for (int i = 0; i < reports.size(); i++) {

				FactoryDto objFactoryDto = (FactoryDto) reports.get(i);

				TableRow row = new TableRow();
				if ((i % 2) == 0) {
					row.addAttribute("class", "normalRow");
				} else {
					row.addAttribute("class", "alternateRow");
				}
				if (objFactoryDto.getUserName() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getUserName()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}
				// -----------------
				if (objFactoryDto.getDisplayName() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getDisplayName()));
					data.addAttribute("class", "tableContent");
					data
							.addAttribute("style",
									"width:1000px;white-space: nowrap; word-spacing: normal;");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}
				// -----------------
				if (objFactoryDto.getMoNumber() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getMoNumber()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}
				// -----------------
				if (objFactoryDto.getAssetNumber() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getAssetNumber()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}
				// -----------------
				if (objFactoryDto.getQtyProcessed() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getQtyProcessed()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}
				table.addTableRow(row);

			}
		} else {
			table.addAttribute("id", "normalTableNostripe");
			TableRow row = new TableRow();
			TableData data = new TableData(new SimpleText(
					"No data available for the given search"));
			row.addTableData(data);
			table.addTableRow(row);
			table.addAttribute("align", "center");
		}

		ReplaceContentAction action = new ReplaceContentAction("factoryReport",
				table);
		response.addAction(action);
		return response;
	}

	public AjaxResponse getYieldReport(AjaxActionEvent event) {
		AjaxResponse response = new AjaxResponseImpl();
		FactoryDto report = (FactoryDto) event.getCommandObject();

		HttpServletRequest request = event.getHttpRequest();

		report.setFromDate(request.getParameter("fromDate"));
		report.setToDate(request.getParameter("toDate"));

		printReportInputs(report);

		report.setUserName(userContext.getUserName());
		report.setDisplayName(userContext.getDisplayName());
		report.setQueries(userContext.getQueries());

		ArrayList reports = dao.getYieldSummary(report);

		report.setAssetReports(reports);
		request.setAttribute("command", report);
		event.setCommandObject(report);
		Table table = new Table();

		if (reports != null && reports.size() != 0) {

			String[] headers = { "Employee Name", "MO #", "Part #", "Asset #",
					"Asset Description", "Setup", "Run", "Re-Tool",
					"Total Hours", "Qty Completed" };
			TableHeader header = new TableHeader(headers);
			table.setTableHeader(header);
			table.addTableHeaderAttribute("class", "fixedHeader");
			table.addAttribute("id", "normalTableNostripe");
			table.addAttribute("align", "center");
			table.addAttribute("width", "96%");

			for (int i = 0; i < reports.size(); i++) {

				FactoryDto objFactoryDto = (FactoryDto) reports.get(i);

				TableRow row = new TableRow();
				if ((i % 2) == 0) {
					row.addAttribute("class", "normalRow");
				} else {
					row.addAttribute("class", "alternateRow");
				}
				if (objFactoryDto.getDisplayName() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getDisplayName()));
					data.addAttribute("class", "tableContent");

					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}
				// ------------------

				if (objFactoryDto.getMoNumber() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getMoNumber()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}
				// -----------------

				if (objFactoryDto.getPartNumber() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getPartNumber()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}

				// -----------------

				if (objFactoryDto.getAssetNumber() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getAssetNumber()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}

				// -----------------

				if (objFactoryDto.getAssetDesc() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getAssetDesc()));
					data.addAttribute("class", "tableContent");

					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}

				// -----------------

				if (objFactoryDto.getSetup() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getSetup()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}

				// -----------------

				if (objFactoryDto.getRun() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getRun()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}

				// -----------------

				if (objFactoryDto.getRetool() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getRetool()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}

				// -----------------

				if (objFactoryDto.getTotalHours() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getTotalHours()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}

				// -----------------

				if (objFactoryDto.getQuantity() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getQuantity()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}
				table.addTableRow(row);

			}
		} else {
			table.addAttribute("id", "normalTableNostripe");
			TableRow row = new TableRow();
			TableData data = new TableData(new SimpleText(
					"No data available for the given search"));
			row.addTableData(data);
			table.addTableRow(row);
			table.addAttribute("align", "center");
		}

		ReplaceContentAction action = new ReplaceContentAction("factoryReport",
				table);
		response.addAction(action);
		return response;
	}

	public AjaxResponse getJobsCurrentlyPendingReport(AjaxActionEvent event) {
		AjaxResponse response = new AjaxResponseImpl();
		FactoryDto report = (FactoryDto) event.getCommandObject();

		HttpServletRequest request = event.getHttpRequest();
		// report.setDepartmentId(request.getParameter("dept"));
		report.setFromDate(request.getParameter("fromDate"));
		report.setToDate(request.getParameter("toDate"));

		report.setUserName(userContext.getUserName());
		report.setDisplayName(userContext.getDisplayName());
		report.setQueries(userContext.getQueries());

		report.setQuery((String) userContext.getQueries().get("SHOPR_073"));
		ArrayList reports = dao.getJobsCurrentlyPendingSummary(report);

		report.setAssetReports(reports);
		request.setAttribute("command", report);
		event.setCommandObject(report);
		Table table = new Table();

		if (reports != null && reports.size() != 0) {

			String[] headers = { "MO Number", "Part Number", "Description",
					"MO Quantity", "Date Queued", "Duration(Hr:min)" };
			TableHeader header = new TableHeader(headers);
			table.setTableHeader(header);
			table.addTableHeaderAttribute("class", "fixedHeader");
			table.addAttribute("id", "normalTableNostripe");
			table.addAttribute("align", "center");
			table.addAttribute("width", "97%");

			for (int i = 0; i < reports.size(); i++) {

				FactoryDto objFactoryDto = (FactoryDto) reports.get(i);

				TableRow row = new TableRow();
				if ((i % 2) == 0) {
					row.addAttribute("class", "normalRow");
				} else {
					row.addAttribute("class", "alternateRow");
				}
				if (objFactoryDto.getMoNumber() != null) {

					String value = "<a style=\"text-decoration: underline\"  href=\"javascript:popupWindowWithParams('popupFactoryAsset.htm?assetNumber="
							+ objFactoryDto.getMoNumber()
							+ "&amp;dept="
							+ report.getDepartmentId()
							+ "&amp;fromDate="
							+ report.getFromDate()
							+ "&amp;toDate="
							+ report.getToDate()
							+ "','left=10,top=80,width=1000, height=400,scrollbars=no, center:yes')\" >"
							+ objFactoryDto.getMoNumber() + "</a>";

					TableData data = new TableData(new SimpleText(objFactoryDto
							.getMoNumber()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}

				// --------
				if (objFactoryDto.getPartNumber() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getPartNumber()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}
				// --------
				if (objFactoryDto.getPartDescription() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getPartDescription()));
					data.addAttribute("class", "tableContent");
					data
							.addAttribute("style",
									"width:1400px;white-space: nowrap; word-spacing: normal;");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}
				// --------
				if (objFactoryDto.getQuantity() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getQuantity()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}
				// --------
				if (objFactoryDto.getDateQueued() != null) {
					TableData data = new TableData(new SimpleText(objFactoryDto
							.getDateQueued()));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}
				// --------
				if (objFactoryDto.getDuration() != null) {
					int durationInSeconds = Integer.parseInt(objFactoryDto
							.getDuration())
							* SECONDS_PER_MINUTE;
					TableData data = new TableData(new SimpleText(DateUtil
							.getHoursMinutes(durationInSeconds)));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				} else {
					TableData data = new TableData(new SimpleText(""));
					data.addAttribute("class", "tableContent");
					row.addTableData(data);
				}
				table.addTableRow(row);

			}
		} else {
			table.addAttribute("id", "normalTableNostripe");
			TableRow row = new TableRow();
			TableData data = new TableData(new SimpleText(
					"No data available for the given search"));
			row.addTableData(data);
			table.addTableRow(row);
			table.addAttribute("align", "center");
		}

		ReplaceContentAction action = new ReplaceContentAction("factoryReport",
				table);
		response.addAction(action);
		return response;
	}
}