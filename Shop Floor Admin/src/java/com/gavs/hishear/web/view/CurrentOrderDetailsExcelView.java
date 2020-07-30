package com.gavs.hishear.web.view;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

import org.springframework.web.servlet.view.document.AbstractJExcelView;

import com.gavs.hishear.web.domain.Sequence;

/**
 * @author kamalb
 * 
 */
public class CurrentOrderDetailsExcelView extends AbstractJExcelView {

	private Map model = null;

	public CurrentOrderDetailsExcelView(Map model) {
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
		Sequence report = null;
		try {
			if (reports != null) {
				report = (Sequence) reports.get(0);

				label = new Label(0, 0, "Sequence");
				sheet.addCell(label);

				label = new Label(1, 0, "WC Code");
				sheet.addCell(label);

				label = new Label(2, 0, "Description");
				sheet.addCell(label);

				label = new Label(3, 0, "Setup");
				sheet.addCell(label);

				label = new Label(4, 0, "Run");
				sheet.addCell(label);

				label = new Label(5, 0, "Required Date");
				sheet.addCell(label);

				label = new Label(6, 0, "Quantity Completed");
				sheet.addCell(label);

				for (int i = 0, j = 2; i < reports.size(); i++, j++) {
					report = (Sequence) reports.get(i);

					label = new Label(0, j, report.getSequence());
					sheet.addCell(label);

					label = new Label(1, j, report.getWorkCenterCode());
					sheet.addCell(label);

					label = new Label(2, j, report.getSequenceDescription());
					sheet.addCell(label);

					label = new Label(3, j, report.getSetupStatus());
					sheet.addCell(label);
					String runStatus = "";
					if (report.getRunStatus() != "Not Started") {
						runStatus = report.getRunStatus();
					} else if (report.getQtyCompleted() != 0.0) {
						runStatus = " Completed ";
					} else {
						runStatus = report.getRunStatus();
					}
					label = new Label(4, j, runStatus);
					sheet.addCell(label);

					label = new Label(5, j, new SimpleDateFormat("MM-dd-yyyy")
							.format(report.getRequiredDate()));
					sheet.addCell(label);

					label = new Label(6, j, "" + report.getQtyCompleted());
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

		}
	}
}