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
public class ProductionExcelView extends AbstractJExcelView {

	private Map model = null;

	public ProductionExcelView(Map model) {
		this.model = model;
	}

	@Override
	protected void buildExcelDocument(Map model,
			jxl.write.WritableWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) {
		model = this.model;
		ArrayList reports = (ArrayList) model.get("0");
		String workCenterNeeded = (String) model.get("1");
		if (workbook.getNumberOfSheets() == 0) {
			workbook.createSheet("Reports", 0);
		}
		WritableSheet sheet = workbook.getSheet("Reports");
		Label label = null;
		ManagementalDto report = null;
		try {
			if (reports != null) {
				report = (ManagementalDto) reports.get(0);

				label = new Label(0, 0, "Employee Name");
				sheet.addCell(label);

				label = new Label(1, 0, "Mo Number");
				sheet.addCell(label);

				label = new Label(2, 0, "Part Number");
				sheet.addCell(label);

				label = new Label(3, 0, "Asset Number");
				sheet.addCell(label);

				label = new Label(4, 0, "Asset Description");
				sheet.addCell(label);

				label = new Label(5, 0, "Setup");
				sheet.addCell(label);

				label = new Label(6, 0, "Run");
				sheet.addCell(label);

				label = new Label(7, 0, "Re-Tool");
				sheet.addCell(label);

				label = new Label(8, 0, "Total Hours");
				sheet.addCell(label);

				label = new Label(9, 0, "Quantity");
				sheet.addCell(label);

				if (workCenterNeeded != null && workCenterNeeded.equals("Yes")) {
					label = new Label(10, 0, "Work Center");
					sheet.addCell(label);
				}

				/*
				 * label = new Label(9, 0, "Exception Activity");
				 * sheet.addCell(label);
				 * 
				 * label = new Label(10, 0, "Quantity"); sheet.addCell(label);
				 * 
				 * label = new Label(11, 0, "Setup"); sheet.addCell(label);
				 * 
				 * label = new Label(12, 0, "Run"); sheet.addCell(label);
				 * 
				 * label = new Label(13, 0, "Run"); sheet.addCell(label);
				 * 
				 * label = new Label(14, 0, "Re-Setup"); sheet.addCell(label);
				 * 
				 * label = new Label(15, 0, "Waiting For Tools");
				 * sheet.addCell(label);
				 * 
				 * label = new Label(16, 0, "Re-Tool"); sheet.addCell(label);
				 * 
				 * label = new Label(17, 0, "Waiting For Material");
				 * sheet.addCell(label);
				 * 
				 * label = new Label(18, 0, "Engineering Hold");
				 * sheet.addCell(label);
				 * 
				 * label = new Label(19, 0, "Preventive Maintenance");
				 * sheet.addCell(label);
				 * 
				 * label = new Label(20, 0, "Real Hours"); sheet.addCell(label);
				 * 
				 * label = new Label(21, 0, "Emp Rate"); sheet.addCell(label);
				 * 
				 * label = new Label(22, 0, "4th Rate"); sheet.addCell(label);
				 */

				for (int i = 0, j = 2; i < reports.size(); i++, j++) {
					report = (ManagementalDto) reports.get(i);

					label = new Label(0, j, report.getEmpName());
					sheet.addCell(label);

					label = new Label(1, j, report.getMoNumber());
					sheet.addCell(label);

					label = new Label(2, j, report.getPartid());
					sheet.addCell(label);

					label = new Label(3, j, report.getAsset());
					sheet.addCell(label);

					label = new Label(4, j, report.getAssetDesc());
					sheet.addCell(label);

					label = new Label(5, j, report.getSetup());
					sheet.addCell(label);

					label = new Label(6, j, report.getRun());
					sheet.addCell(label);

					label = new Label(7, j, report.getReTool());
					sheet.addCell(label);

					label = new Label(8, j, report.getTotalHours1());
					sheet.addCell(label);

					label = new Label(9, j, report.getCompletedQty());
					sheet.addCell(label);

					if (workCenterNeeded != null
							&& workCenterNeeded.equals("Yes")) {
						label = new Label(10, j, report.getWorkCenter());
						sheet.addCell(label);
					}

					/*
					 * label = new Label(9, j, report.getAssetDesc());
					 * sheet.addCell(label);
					 * 
					 * label = new Label(10, j, report.getCompletedQty());
					 * sheet.addCell(label);
					 * 
					 * label = new Label(11, j, report.getTotalHrs());
					 * sheet.addCell(label);
					 * 
					 * label = new Label(12, j, report.getSetup());
					 * sheet.addCell(label);
					 * 
					 * label = new Label(13, j, report.getRun());
					 * sheet.addCell(label);
					 * 
					 * label = new Label(14, j, report.getReSetup());
					 * sheet.addCell(label);
					 * 
					 * label = new Label(15, j, report.getWaitTool());
					 * sheet.addCell(label);
					 * 
					 * label = new Label(16, j, report.getReTool());
					 * sheet.addCell(label);
					 * 
					 * label = new Label(17, j, report.getWaitMaterial());
					 * sheet.addCell(label);
					 * 
					 * label = new Label(18, j, report.getEngHold());
					 * sheet.addCell(label);
					 * 
					 * label = new Label(19, j, report.getPreMaintenance());
					 * sheet.addCell(label);
					 * 
					 * label = new Label(20, j, report.getTotalHrs());
					 * sheet.addCell(label);
					 * 
					 * label = new Label(21, j, report.getRealHours());
					 * sheet.addCell(label);
					 * 
					 * label = new Label(22, j, report.getEmpRate());
					 * sheet.addCell(label);
					 * 
					 * label = new Label(23, j, report.getFourthRate());
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
			System.out.println("error!!!!!!!!!!!!! " + e);
		}
	}
}