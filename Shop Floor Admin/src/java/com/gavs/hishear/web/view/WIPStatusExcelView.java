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

import com.gavs.hishear.web.reports.management.domain.WIPStatusDto;

public class WIPStatusExcelView extends AbstractJExcelView {

	private Map model = null;

	public WIPStatusExcelView(Map model) {
		this.model = model;
	}

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
		WIPStatusDto dto = null;
		try {
			if (reports != null) {
				dto = (WIPStatusDto) reports.get(0);

				label = new Label(0, 0, "MO Number");
				sheet.addCell(label);

				label = new Label(1, 0, "Line Number");
				sheet.addCell(label);

				label = new Label(2, 0, "Part Number");
				sheet.addCell(label);

				label = new Label(3, 0, "Completed Sequence");
				sheet.addCell(label);

				label = new Label(4, 0, "Last Pick Qty");
				sheet.addCell(label);

				label = new Label(5, 0, "Current Sequence");
				sheet.addCell(label);

				label = new Label(6, 0, "Current Qty");
				sheet.addCell(label);

				label = new Label(7, 0, "Department");
				sheet.addCell(label);

				label = new Label(8, 0, "Location");
				sheet.addCell(label);

				for (int i = 0, j = 2; i < reports.size(); i++, j++) {
					dto = (WIPStatusDto) reports.get(i);

					label = new Label(0, j, dto.getMoNumber());
					sheet.addCell(label);

					label = new Label(1, j, dto.getLineNumber());
					sheet.addCell(label);

					label = new Label(2, j, dto.getItemNumber());
					sheet.addCell(label);

					label = new Label(3, j, dto.getCompletedSequence());
					sheet.addCell(label);

					label = new Label(4, j, dto.getLatestPickQty());
					sheet.addCell(label);

					label = new Label(5, j, dto.getProcessingSequence());
					sheet.addCell(label);

					label = new Label(6, j, dto.getProcessingQty());
					sheet.addCell(label);

					label = new Label(7, j, dto.getDepartment());
					sheet.addCell(label);

					label = new Label(8, j, dto.getLocation());
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