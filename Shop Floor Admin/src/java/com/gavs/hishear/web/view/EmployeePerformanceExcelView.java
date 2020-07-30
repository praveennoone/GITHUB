/**
 * 
 */
package com.gavs.hishear.web.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

import org.springframework.web.servlet.view.document.AbstractJExcelView;

import com.gavs.hishear.web.reports.management.domain.ManagementalDto;

/**
 * @author sundarrajanr
 * 
 */
public class EmployeePerformanceExcelView extends AbstractJExcelView {

	private Map model = null;

	public EmployeePerformanceExcelView(Map model) {
		this.model = model;
	}

	@Override
	protected void buildExcelDocument(Map model,
			jxl.write.WritableWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) {
		model = this.model;
		ArrayList reports = (ArrayList) model.get("0");
		if (workbook.getNumberOfSheets() == 0) {
			workbook.createSheet("Reports", 0);
		}
		WritableSheet sheet = workbook.getSheet("Reports");
		Label label = null;
		ManagementalDto report = null;
		try {
			if (reports != null) {
				report = (ManagementalDto) reports.get(0);

				label = new Label(0, 0, "Employee name");
				sheet.addCell(label);

				label = new Label(1, 0, "MO Number");
				sheet.addCell(label);

				label = new Label(2, 0, "Line Number");
				sheet.addCell(label);

				label = new Label(3, 0, "Shift");
				sheet.addCell(label);

				label = new Label(4, 0, "Setup");
				sheet.addCell(label);

				label = new Label(5, 0, "Run");
				sheet.addCell(label);

				label = new Label(6, 0, "ReTool");
				sheet.addCell(label);

				label = new Label(7, 0, "NPS");
				sheet.addCell(label);

				label = new Label(8, 0, "TTL");
				sheet.addCell(label);

				label = new Label(9, 0, "TTL_PCS");
				sheet.addCell(label);

				label = new Label(10, 0, "PCS/HR");
				sheet.addCell(label);

				label = new Label(11, 0, "STP_Perf");
				sheet.addCell(label);

				label = new Label(12, 0, "Run_Perf");
				sheet.addCell(label);

				/*
				 * label = new Label(15, 0, "RSP_Perf"); sheet.addCell(label);
				 */

				label = new Label(13, 0, "RTL_Perf");
				sheet.addCell(label);

				label = new Label(14, 0, "TTL_Perf");
				sheet.addCell(label);

				label = new Label(15, 0, "Supervisor Comments");
				sheet.addCell(label);

				for (int i = 0, j = 2; i < reports.size(); i++, j++) {
					report = (ManagementalDto) reports.get(i);

					label = new Label(0, j, report.getEmpName());
					sheet.addCell(label);

					label = new Label(1, j, report.getMoNumber());
					sheet.addCell(label);

					label = new Label(2, j, report.getLineNumber());
					sheet.addCell(label);

					label = new Label(3, j, report.getShiftTime());
					sheet.addCell(label);

					label = new Label(4, j, report.getSetup());
					sheet.addCell(label);
					label = new Label(5, j, report.getRun());
					sheet.addCell(label);
					/*
					 * label = new Label(6, j, report.getReSetup());
					 * sheet.addCell(label);
					 */
					label = new Label(6, j, report.getReTool());
					sheet.addCell(label);
					label = new Label(7, j, report.getNps());
					sheet.addCell(label);
					label = new Label(8, j, report.getTtl());
					sheet.addCell(label);
					/*
					 * label = new Label(10, j, report.getNetHrs());
					 * sheet.addCell(label);
					 */
					label = new Label(9, j, report.getTtl_pcs());
					sheet.addCell(label);
					label = new Label(10, j, report.getPcs_hrs());
					sheet.addCell(label);
					label = new Label(11, j, report.getStp_Perf());
					sheet.addCell(label);
					label = new Label(12, j, report.getRun_Perf());
					sheet.addCell(label);
					/*
					 * label = new Label(15, j, report.getRsp_Perf());
					 * sheet.addCell(label);
					 */
					label = new Label(13, j, report.getRtl_Perf());
					sheet.addCell(label);
					label = new Label(14, j, report.getTtl_Perf());
					sheet.addCell(label);

					label = new Label(15, j, report.getRejComments());
					sheet.addCell(label);
				}
			}
		} catch (Exception e) {
			try {
				workbook.setProtected(true);
				workbook.close();
			} catch (WriteException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("error!!!!!!!!!!!!! " + e);
		}
	}
}