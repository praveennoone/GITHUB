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

import com.gavs.hishear.web.reports.exceptional.domain.ExceptionalDto;

/**
 * 
 * @author sundarrajanr
 * 
 */
public class EmployeeDetailsExcelView extends AbstractJExcelView {

	private Map model = null;

	public EmployeeDetailsExcelView(Map model) {
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
		ExceptionalDto report = null;
		try {
			if (reports != null) {
				report = (ExceptionalDto) reports.get(0);

				label = new Label(0, 0, "Employee Id");
				sheet.addCell(label);

				label = new Label(1, 0, "Employee Name");
				sheet.addCell(label);

				label = new Label(2, 0, "Department");
				sheet.addCell(label);

				label = new Label(3, 0, "Punch In Time");
				sheet.addCell(label);

				label = new Label(4, 0, "Punch Out Time");
				sheet.addCell(label);

				label = new Label(5, 0, "Shift Time");
				sheet.addCell(label);

				for (int i = 0, j = 2; i < reports.size(); i++, j++) {
					report = (ExceptionalDto) reports.get(i);

					label = new Label(0, j, report.getEmpId());
					sheet.addCell(label);

					label = new Label(1, j, report.getEmpName());
					sheet.addCell(label);

					label = new Label(2, j, report.getDepartment());
					sheet.addCell(label);

					label = new Label(3, j, report.getPunchIn());
					sheet.addCell(label);
					label = new Label(4, j, report.getPunchOut());
					sheet.addCell(label);
					label = new Label(5, j, report.getShiftTime());
					sheet.addCell(label);

				}
			}
		} catch (Exception e) {
			try {
				workbook.setProtected(true);
				workbook.close();
			} catch (WriteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

}
