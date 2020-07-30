/*
 * PerformanceExcelView.java Jun 18, 2008
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

import com.gavs.hishear.web.reports.factory.domain.FactoryDto;

/**
 * @author subhashri
 * 
 */
public class PerformanceExcelView extends AbstractJExcelView {

	private Map model = null;

	public PerformanceExcelView(Map model) {
		System.out.println("in performance excel view constructor");
		this.model = model;
	}

	@Override
	protected void buildExcelDocument(Map model,
			jxl.write.WritableWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("in build excel document called ");
		System.out.println("reports in View = " + model);
		model = this.model;
		ArrayList reports = (ArrayList) model.get("0");
		if (reports != null) {
			System.out.println("arraylist reports assigned=" + reports.size());
		}
		if (workbook.getNumberOfSheets() == 0) {
			workbook.createSheet("Reports", 0);
			System.out.println("in if condition of get number of sheets");
		}
		WritableSheet sheet = workbook.getSheet("Reports");
		Label label = null;
		FactoryDto report = null;
		System.out.println("WritableSheet sheet got");

		try {
			if (reports != null) {
				report = (FactoryDto) reports.get(0);

				label = new Label(0, 0, "Asset Number");
				sheet.addCell(label);

				label = new Label(1, 0, "Part Number ");
				sheet.addCell(label);

				label = new Label(2, 0, "Material Type");
				sheet.addCell(label);

				label = new Label(3, 0, "Employee Name");
				sheet.addCell(label);

				label = new Label(4, 0, "Run Hrs");
				sheet.addCell(label);

				label = new Label(5, 0, "PPH");
				sheet.addCell(label);

				label = new Label(6, 0, "PPH Act");
				sheet.addCell(label);

				label = new Label(7, 0, "QTY Target ");
				sheet.addCell(label);

				label = new Label(8, 0, "QTY Act");
				sheet.addCell(label);

				label = new Label(9, 0, "Act Run Hrs");
				sheet.addCell(label);

				label = new Label(10, 0, "Down Hrs");
				sheet.addCell(label);

				label = new Label(11, 0, "Efficiency");
				sheet.addCell(label);

				System.out.println("labels assigned");

				for (int i = 0, j = 2; i < reports.size(); i++, j++) {
					report = (FactoryDto) reports.get(i);

					label = new Label(0, j, report.getAssetNumber());
					System.out.println("report.getAssetNumber()"
							+ report.getAssetNumber());
					sheet.addCell(label);

					label = new Label(1, j, report.getPartNumber());
					sheet.addCell(label);
					System.out.println("report.getPartNumber()"
							+ report.getPartNumber());

					label = new Label(2, j, report.getShift());
					sheet.addCell(label);

					label = new Label(3, j, report.getEmployeeName());
					sheet.addCell(label);

					label = new Label(4, j, report.getRun());
					sheet.addCell(label);

					label = new Label(5, j, report.getPPH());
					sheet.addCell(label);

					label = new Label(6, j, report.getPPHAct());
					sheet.addCell(label);

					label = new Label(7, j, report.getQtyTarget());
					sheet.addCell(label);

					label = new Label(8, j, report.getQtyAct());
					sheet.addCell(label);

					label = new Label(9, j, report.getActRunHrs());
					sheet.addCell(label);

					label = new Label(10, j, report.getDownHrs());
					sheet.addCell(label);

					label = new Label(11, j, report.getEfficiency());
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
