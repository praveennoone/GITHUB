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
public class StandardRateExcelView extends AbstractJExcelView {

	private Map model = null;

	public StandardRateExcelView(Map model) {
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

				label = new Label(0, 0, "PartNumber");
				sheet.addCell(label);

				label = new Label(1, 0, "Part Description");
				sheet.addCell(label);

				label = new Label(2, 0, "Sequence");
				sheet.addCell(label);

				label = new Label(3, 0, "Completed Quantity");
				sheet.addCell(label);

				label = new Label(4, 0, "Actual Hours");
				sheet.addCell(label);

				label = new Label(5, 0, "Standard Rate");
				sheet.addCell(label);

				label = new Label(6, 0, "Standard hours");
				sheet.addCell(label);

				label = new Label(7, 0, "Actual Rate");
				sheet.addCell(label);

				label = new Label(8, 0, "Rate Variance");
				sheet.addCell(label);

				for (int i = 0, j = 2; i < reports.size(); i++, j++) {
					report = (ManagementalDto) reports.get(i);

					label = new Label(0, j, report.getPartid());
					sheet.addCell(label);

					label = new Label(1, j, report.getPartDesc());
					sheet.addCell(label);

					label = new Label(2, j, report.getSeqn());
					sheet.addCell(label);

					label = new Label(3, j, report.getCompletedQty());
					sheet.addCell(label);
					label = new Label(4, j, report.getActTime());
					sheet.addCell(label);
					label = new Label(5, j, report.getStdRate());
					sheet.addCell(label);
					label = new Label(6, j, report.getStdHrs());
					sheet.addCell(label);
					label = new Label(7, j, report.getActRate());
					sheet.addCell(label);
					label = new Label(8, j, report.getRateVar());
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