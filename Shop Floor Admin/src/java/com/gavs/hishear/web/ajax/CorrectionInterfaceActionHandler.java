/**
 * 
 */
package com.gavs.hishear.web.ajax;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.HtmlUtils;
import org.springmodules.xt.ajax.AjaxResponse;
import org.springmodules.xt.ajax.AjaxResponseImpl;
import org.springmodules.xt.ajax.AjaxSubmitEvent;
import org.springmodules.xt.ajax.action.ReplaceContentAction;
import org.springmodules.xt.ajax.component.Container;
import org.springmodules.xt.ajax.component.Image;
import org.springmodules.xt.ajax.component.InputField;
import org.springmodules.xt.ajax.component.InputField.InputType;
import org.springmodules.xt.ajax.component.SimpleText;
import org.springmodules.xt.ajax.component.Table;
import org.springmodules.xt.ajax.component.TableData;
import org.springmodules.xt.ajax.component.TableHeader;
import org.springmodules.xt.ajax.component.TableHeaderData;
import org.springmodules.xt.ajax.component.TableRow;

import com.gavs.hishear.constant.Constant;
import com.gavs.hishear.util.DateUtil;
import com.gavs.hishear.web.domain.Sequence;


/**
 * @author ramananm
 * 
 */
public class CorrectionInterfaceActionHandler extends BaseAjaxHandler {

	private static final String EMPTY_STRING = "";

	/**
	 * @param event
	 * @return AjaxResponse
	 */
	public AjaxResponse getSequenceDetails(AjaxSubmitEvent event) {
		AjaxResponse response = new AjaxResponseImpl();
		ReplaceContentAction action = null;
		ReplaceContentAction action1 = null;
		ReplaceContentAction m3ItemAction = null;
		ReplaceContentAction statusAction = null;
		Sequence dto = (Sequence) event.getCommandObject();
		Container headerContainer = new Container(Container.Type.DIV);
		headerContainer.addAttribute("style",
				"width: 900px; overflow: auto; height: 150px");
		Container m3ItemContainer = new Container(Container.Type.DIV);
		m3ItemContainer.addAttribute("style", "width: 900px");

		ArrayList<Sequence> sequence = dto.getSequenceDetails();
		if (sequence != null && sequence.size() > 0) {
			m3ItemContainer.addComponent(buildM3ItemTable(dto));
			m3ItemAction = new ReplaceContentAction("tableM3ItemDetails",
					m3ItemContainer);
			response.addAction(m3ItemAction);
			headerContainer.addComponent(buildTableHeader(sequence));
			action = new ReplaceContentAction("tableSequenceDetails",
					headerContainer);
		} else {
			SimpleText m3noRecords = new SimpleText(" ");
			m3ItemAction = new ReplaceContentAction("tableM3ItemDetails",
					m3noRecords);
			response.addAction(m3ItemAction);
			action1 = new ReplaceContentAction("tableSequenceDetails",
					m3noRecords);
			response.addAction(action1);
			SimpleText noRecords = new SimpleText(" No Records Found");
			action = new ReplaceContentAction("statusMessage", noRecords);
		}
		if (dto.isModified()) {
			SimpleText status = new SimpleText(" Updated Successfully");
			statusAction = new ReplaceContentAction("statusMessage", status);
			response.addAction(statusAction);
		} else {
			SimpleText status = new SimpleText(dto.getM3Status());
			statusAction = new ReplaceContentAction("statusMessage", status);
			response.addAction(statusAction);
		}
		response.addAction(action);
		return response;
	}

	/**
	 * @param event
	 * @return AjaxResponse
	 */
	public AjaxResponse getEditSequenceDetails(AjaxSubmitEvent event) {
		AjaxResponse response = new AjaxResponseImpl();
		ReplaceContentAction action = null;
		Sequence dto = (Sequence) event.getCommandObject();
		Container editContainer = new Container(Container.Type.DIV);
		editContainer.addAttribute("style", "width: 900px;");
		Sequence selectedSequence = (Sequence) dto.getSelectedSequence();
		editContainer.addComponent(buildEditSequenceDetails(selectedSequence));
		action = new ReplaceContentAction("tableEditSequenceDetails",
				editContainer);
		response.addAction(action);
		return response;
	}

	public Table buildM3ItemTable(Sequence dto) {
		Table innerTable1 = new Table();
		innerTable1.addAttribute("id", "outerTableNostripe");
		innerTable1.addAttribute("align", "center");
		innerTable1.addAttribute("cellSpacing", "0");
		innerTable1.addAttribute("cellPadding", "0");
		innerTable1.addAttribute("border", "1");
		innerTable1
				.addAttribute(
						"style",
						"white-space: nowrap; word-spacing: normal; width: 888px; height:30px; border-left: 1px solid #465272; border-right: 1px solid #465272; border-top: 1px solid #465272; border-bottom: 1px solid #465272;");
		TableRow innerRow1 = new TableRow();
		TableData facility = new TableData(new SimpleText(HtmlUtils
				.htmlEscape(validateDisplayField("Facility : "
						+ dto.getFacility()))));
		facility.addAttribute("style", "width: 175px; font-weight: bold");
		TableData itemNumber = new TableData(new SimpleText(HtmlUtils
				.htmlEscape(validateDisplayField("M3 ItemNumber : "
						+ dto.getPartNumber()))));
		itemNumber.addAttribute("style", "width: 175px; font-weight: bold");
		TableData partName = new TableData(new SimpleText(HtmlUtils
				.htmlEscape(validateDisplayField("M3 Part Name : "
						+ dto.getPartDesc()))));
		partName.addAttribute("style", "width: 175px; font-weight: bold");
		innerRow1.addTableData(facility);
		innerRow1.addTableData(itemNumber);
		innerRow1.addTableData(partName);
		innerTable1.addTableRow(innerRow1);
		return innerTable1;
	}

	/**
	 * @return
	 */
	public Table buildTableHeader(ArrayList<Sequence> sequence) {
		TableHeaderData employeeIDHeaderData = new TableHeaderData(
				new SimpleText("Employee ID"));
		employeeIDHeaderData.addAttribute("style", "width: 85px");
		TableHeaderData employeeNameHeaderData = new TableHeaderData(
				new SimpleText("Employee Name"));
		employeeNameHeaderData.addAttribute("style", "width: 150px");
		TableHeaderData assetNoHeaderData = new TableHeaderData(new SimpleText(
				"Asset No."));
		assetNoHeaderData.addAttribute("style", "width: 75px");
		TableHeaderData AssetDescHeaderData = new TableHeaderData(
				new SimpleText("Asset Description"));
		AssetDescHeaderData.addAttribute("style", "width: 80px");
		TableHeaderData activityHeaderData = new TableHeaderData(
				new SimpleText("Activity"));
		activityHeaderData.addAttribute("style", "width: 75");
		TableHeaderData qtyCompletedHeaderData = new TableHeaderData(
				new SimpleText("Qty Completed"));
		qtyCompletedHeaderData.addAttribute("style", "width: 75px");
		TableHeaderData logOnHeaderData = new TableHeaderData(new SimpleText(
				"Logon Date/Time"));
		logOnHeaderData.addAttribute("style", "width: 75px");
		TableHeaderData logOffHeaderData = new TableHeaderData(new SimpleText(
				"Logoff Date/Time"));
		logOffHeaderData.addAttribute("style", "width: 75px");
		TableHeaderData editHeaderData = new TableHeaderData(new SimpleText(
				"  "));
		editHeaderData.addAttribute("style", "width: 45px");

		ArrayList<TableHeaderData> headers = new ArrayList<TableHeaderData>();
		headers.add(employeeIDHeaderData);
		headers.add(employeeNameHeaderData);
		headers.add(assetNoHeaderData);
		headers.add(AssetDescHeaderData);
		headers.add(activityHeaderData);
		headers.add(qtyCompletedHeaderData);
		headers.add(logOnHeaderData);
		headers.add(logOffHeaderData);
		headers.add(editHeaderData);
		TableHeader tableHeader = new TableHeader(headers);
		tableHeader.addAttribute("align", "center");
		tableHeader.addAttribute("style", "text-align: center;");
		Table table = new Table();
		table.setTableHeader(tableHeader);
		table.addAttribute("id", "normalTableNostripe");
		table.addAttribute("align", "center");
		table.addAttribute("cellSpacing", "0");
		table.addAttribute("cellPadding", "0");
		table.addAttribute("border", "1");
		table.addAttribute("class", "classContentTable");
		table.addAttribute("style",
				"white-space: nowrap; word-spacing: normal; width: 888px");
		table.addTableHeaderAttribute("class", "fixedHeader");
		table.addTableBodyAttribute("class", "scrollContent");
		int i = 0;
		for (Sequence seq : sequence) {
			TableRow row = new TableRow();

			TableData employeeID = new TableData(new SimpleText(
					validateDisplayField(seq.getEmployeeID())));
			employeeID.addAttribute("style", "width: 85px");
			TableData employeeName = new TableData(new SimpleText(
					validateDisplayField(seq.getEmployeeName())));
			employeeName.addAttribute("style", "width: 150px");
			TableData assetNo = new TableData(new SimpleText(
					validateDisplayField(seq.getAssetNumber())));
			assetNo.addAttribute("style", "width: 75px");
			TableData AssetDesc = new TableData(new SimpleText(
					validateDisplayField(seq.getAssetDesc())));
			AssetDesc.addAttribute("style", "width: 80px");
			TableData activity = new TableData(new SimpleText(
					validateDisplayField(seq.getActivity())));
			activity.addAttribute("style", "width: 75px");
			TableData quantityCompleted = new TableData(
					new SimpleText(validateDisplayField(String.valueOf(seq
							.getQtyCompleted()))));
			quantityCompleted.addAttribute("style", "width: 75px");
			TableData logOn = new TableData(new SimpleText(
					validateDisplayField(DateUtil.dateAsDefaultString(seq
							.getLogonDate(), EMPTY_STRING,
							Constant.DATE_FORMAT_UPTOSCEONDS))));
			logOn.addAttribute("style", "width: 75px");
			TableData logOff = new TableData(new SimpleText(
					validateDisplayField(DateUtil.dateAsDefaultString(seq
							.getLogoffDate(), EMPTY_STRING,
							Constant.DATE_FORMAT_UPTOSCEONDS))));
			logOff.addAttribute("style", "width: 75px");

			InputField field = new InputField("edit", "Edit", InputType.BUTTON);
			TableData editSeq = new TableData(field);
			editSeq.addAttribute("style", "width: 45px");
			editSeq.addAttribute("onclick", "editSequence('" + i + "')");

			row.addTableData(employeeID);
			row.addTableData(employeeName);
			row.addTableData(assetNo);
			row.addTableData(AssetDesc);
			row.addTableData(activity);
			row.addTableData(quantityCompleted);
			row.addTableData(logOn);
			row.addTableData(logOff);
			row.addTableData(editSeq);
			table.addTableRow(row);
			i++;
		}

		return table;
	}

	public Table buildEditSequenceDetails(Sequence selecteSequence) {
		Table table = new Table();
		table.addAttribute("id", "normalTableNostripe");
		table.addAttribute("align", "center");
		table.addAttribute("cellSpacing", "0");
		table.addAttribute("cellPadding", "0");
		table.addAttribute("border", "1");
		table.addAttribute("class", "classContentTable");
		table.addAttribute("style",
				"white-space: nowrap; word-spacing: normal; width: 600px");

		Table innertable = new Table();
		innertable.addAttribute("id", "normalTableNostripe1");
		innertable.addAttribute("align", "center");
		innertable.addAttribute("cellSpacing", "0");
		innertable.addAttribute("cellPadding", "0");
		innertable.addAttribute("border", "0");
		// innertable.addAttribute("class", "classContentTable");
		innertable.addAttribute("style",
				"white-space: nowrap; word-spacing: normal; width: 600px");

		TableRow row1 = new TableRow();
		TableRow row2 = new TableRow();
		TableRow outerTableRow1 = new TableRow();
		TableRow outerTableRow2 = new TableRow();

		TableData lblLoginTime = new TableData(new SimpleText("Login Time"));
		InputField txtLogIn = new InputField("loginTime", DateUtil
				.dateAsDefaultString(selecteSequence.getLogonDate(),
						EMPTY_STRING, Constant.DATE_FORMAT_UPTOSCEONDS),
				InputType.TEXT);
		txtLogIn.addAttribute("readOnly", "readOnly");
		txtLogIn.addAttribute("id", "loginTime");
		TableData txtLoginTime = new TableData(txtLogIn);
		Image loginImage = new Image("static/images/cal.gif",
				"Select Login Time");
		// loginImage.addAttribute("onclick",
		// "showChooser(this, 'loginTime', 'chooserSpan', 1950, 2020, 'yyyy-MM-dd HH:mm:ss', true)");
		loginImage.addAttribute("onclick",
				"NewCssCal('loginTime','yyyymmdd','dropdown',true,24,false)");
		loginImage.addAttribute("style", "cursor: pointer;");
		TableData btnLoginTime = new TableData(loginImage);
		TableData lblLogoutTime = new TableData(new SimpleText("Logout Time"));
		InputField txtLogOut = new InputField("logoutTime", DateUtil
				.dateAsDefaultString(selecteSequence.getLogoffDate(),
						EMPTY_STRING, Constant.DATE_FORMAT_UPTOSCEONDS),
				InputType.TEXT);
		txtLogOut.addAttribute("readOnly", "readOnly");
		txtLogOut.addAttribute("id", "logoutTime");
		TableData txtLoginoutTime = new TableData(txtLogOut);
		Image logoutImage = new Image("static/images/cal.gif",
				"Select Login Time");
		// logoutImage.addAttribute("onclick",
		// "showChooser(this, 'logoutTime', 'chooserSpan2', 1950, 2020, Date.patterns.USDatePattern, true)");
		logoutImage.addAttribute("onclick",
				"NewCssCal('logoutTime','yyyymmdd','dropdown',true,24,false)");
		logoutImage.addAttribute("style", "cursor: pointer;");
		TableData btnLogoutTime = new TableData(logoutImage);

		row1.addTableData(lblLoginTime);
		row1.addTableData(txtLoginTime);
		row1.addTableData(btnLoginTime);
		row1.addTableData(lblLogoutTime);
		row1.addTableData(txtLoginoutTime);
		row1.addTableData(btnLogoutTime);

		TableData lblQtyCompleted = new TableData(new SimpleText(
				"Quantity Completed"));
		InputField txtQty = new InputField("qtyCompleted", String
				.valueOf(selecteSequence.getQtyCompleted()), InputType.TEXT);
		txtQty.addAttribute("id", "qtyCompleted");
		if (selecteSequence.getActivityCode().equals("S")) {
			txtQty.addAttribute("readOnly", "readOnly");
		}
		TableData txtQtyCompleted = new TableData(txtQty);
		TableData lblEmptyTD1 = new TableData(new SimpleText(" "));
		TableData lblAssetNo = new TableData(new SimpleText("Asset #"));
		InputField txtAsset = new InputField("assetNo", StringUtils
				.defaultString(selecteSequence.getAssetNumber()),
				InputType.TEXT);
		TableData txtAssetNo = new TableData(txtAsset);
		TableData lblEmptyTD2 = new TableData(new SimpleText(" "));

		row2.addTableData(lblQtyCompleted);
		row2.addTableData(txtQtyCompleted);
		row2.addTableData(lblEmptyTD1);
		row2.addTableData(lblAssetNo);
		row2.addTableData(txtAssetNo);
		row2.addTableData(lblEmptyTD2);

		innertable.addTableRow(row1);
		innertable.addTableRow(row2);
		TableData outerTableDate1 = new TableData(innertable);

		Table innertable2 = new Table();
		innertable2.addAttribute("id", "normalTableNostripe2");
		innertable2.addAttribute("align", "center");
		innertable2.addAttribute("cellSpacing", "0");
		innertable2.addAttribute("cellPadding", "0");
		innertable2.addAttribute("border", "0");
		innertable2
				.addAttribute(
						"style",
						"white-space: nowrap; word-spacing: normal; width: 60px; border-bottom-color: white; border-left-color: white");
		TableRow innertable2Row1 = new TableRow();
		InputField btnUpdate = new InputField("Update", "Update",
				InputType.BUTTON);
		btnUpdate.addAttribute("onclick", "updateSequence()");
		TableData buttonUpdate = new TableData(btnUpdate);
		buttonUpdate.addAttribute("style",
				"border-bottom-color: white; border-left-color: white");
		innertable2Row1.addTableData(buttonUpdate);
		innertable2Row1.addAttribute("align", "center;");
		innertable2.addTableRow(innertable2Row1);
		TableData outerTableDate2 = new TableData(innertable2);

		outerTableRow1.addTableData(outerTableDate1);
		outerTableRow2.addTableData(outerTableDate2);
		outerTableRow2.addAttribute("align", "center;");
		table.addTableRow(outerTableRow1);
		table.addTableRow(outerTableRow2);

		return table;
	}
}
