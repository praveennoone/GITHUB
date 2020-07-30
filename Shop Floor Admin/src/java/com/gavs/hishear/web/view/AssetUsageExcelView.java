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
 * 
 * @author sundarrajanr
 * 
 */

public class AssetUsageExcelView extends AbstractJExcelView {

	private Map model = null;

	public AssetUsageExcelView(Map model) {
		// TODO Auto-generated constructor stub
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

				label = new Label(0, 0, "Factory Description");
				sheet.addCell(label);

				label = new Label(1, 0, "Asset Description");
				sheet.addCell(label);

				label = new Label(2, 0, "Department Name");
				sheet.addCell(label);

				label = new Label(3, 0, "No of PC's used");
				sheet.addCell(label);

				label = new Label(4, 0, "Setup Hrs");
				sheet.addCell(label);

				label = new Label(5, 0, "Run Hrs");
				sheet.addCell(label);

				label = new Label(6, 0, "ReTool Hrs");
				sheet.addCell(label);

				label = new Label(7, 0, "PM Hrs");
				sheet.addCell(label);

				label = new Label(8, 0, "Daily Hrs");
				sheet.addCell(label);

				label = new Label(9, 0, "Avail Hrs");
				sheet.addCell(label);

				label = new Label(10, 0, "Set Perf");
				sheet.addCell(label);

				label = new Label(11, 0, "Run Perf");
				sheet.addCell(label);

				label = new Label(12, 0, "Mach Eff");
				sheet.addCell(label);

				label = new Label(13, 0, "ActPCs/Hr");
				sheet.addCell(label);

				/*
				 * label = new Label(12, 0, "LogonDate"); sheet.addCell(label);
				 */

				for (int i = 0, j = 2; i < reports.size(); i++, j++) {
					report = (ManagementalDto) reports.get(i);

					label = new Label(0, j, report.getFactoryDesc());
					sheet.addCell(label);

					label = new Label(1, j, report.getAssetDesc());
					sheet.addCell(label);

					label = new Label(2, j, report.getDepartment());
					sheet.addCell(label);

					label = new Label(3, j, report.getNoOfPcs());
					sheet.addCell(label);
					label = new Label(4, j, report.getSetup());
					sheet.addCell(label);
					label = new Label(5, j, report.getRun());
					sheet.addCell(label);

					label = new Label(6, j, report.getReTool());
					sheet.addCell(label);

					label = new Label(7, j, report.getPmMts());
					sheet.addCell(label);

					label = new Label(8, j, report.getStp_Perf());
					sheet.addCell(label);

					label = new Label(9, j, report.getDailyHrs());
					sheet.addCell(label);

					label = new Label(10, j, report.getAvailHrs());
					sheet.addCell(label);

					label = new Label(11, j, report.getPrefHrs());
					sheet.addCell(label);

					label = new Label(12, j, report.getRun_Perf());
					sheet.addCell(label);
					label = new Label(13, j, report.getMatchEff());
					sheet.addCell(label);
					label = new Label(14, j, report.getActHrs());
					sheet.addCell(label);
					/*
					 * label = new Label(12, j, report.getLogon());
					 * sheet.addCell(label);
					 */
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

		}
	}
}