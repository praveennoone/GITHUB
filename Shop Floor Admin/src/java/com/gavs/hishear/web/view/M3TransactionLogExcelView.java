package com.gavs.hishear.web.view;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

import org.springframework.web.servlet.view.document.AbstractJExcelView;

import com.gavs.hishear.web.domain.M3RequestLog;

/**
 * @author mohammeda
 * 
 */
public class M3TransactionLogExcelView extends AbstractJExcelView {

	private M3RequestLog dto = null;

	public M3TransactionLogExcelView(M3RequestLog dto) {
		this.dto = dto;
	}

	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map model,
			jxl.write.WritableWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) {
		String sheetName = dto.getProgramName() + " - "
				+ dto.getDateProcessed().replace("/", "-");
		if (workbook.getNumberOfSheets() == 0) {
			workbook.createSheet(sheetName, 0);
		}
		WritableSheet sheet = workbook.getSheet(sheetName);
		Label label = null;
		Number number = null;
		try {

			if (dto.getProgramDetails() != null) {
				// Build Header
				int headerCount = 0;
				label = new Label(headerCount, 0, "Date Processed");
				sheet.addCell(label);
				headerCount++;

				label = new Label(headerCount, 0, "Program Name");
				sheet.addCell(label);
				headerCount++;

				label = new Label(headerCount, 0, "Function Name");
				sheet.addCell(label);
				headerCount++;

				for (String header : dto.getDataHeaders()) {
					label = new Label(headerCount, 0, header);
					sheet.addCell(label);
					headerCount++;
				}

				// Build Data.
				for (int i = 0, j = 2; i < dto.getProgramDetails().size(); i++) {
					Map<String, String> map = dto.getProgramDetails().get(i);
					int columnCount = 0;
					label = new Label(columnCount, j, dto.getDateProcessed());
					sheet.addCell(label);
					columnCount++;

					label = new Label(columnCount, j, dto.getProgramName());
					sheet.addCell(label);
					columnCount++;

					label = new Label(columnCount, j, dto.getFunctionName());
					sheet.addCell(label);
					columnCount++;

					for (String header : dto.getDataHeaders()) {
						String columnValue = map.get(header);
						try {
							number = new Number(columnCount, j, Double
									.parseDouble(columnValue));
							sheet.addCell(number);
						} catch (Exception e) {
							label = new Label(columnCount, j, columnValue);
							sheet.addCell(label);
						}
						columnCount++;
					}
					j++;
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