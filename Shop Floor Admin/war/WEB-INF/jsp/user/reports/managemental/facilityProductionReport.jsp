<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div id="content"><script type="text/javascript">


function ValidateForm(){
	var dt=document.frmSample.txtDate
	if (isDate(dt.value)==false){
		dt.focus()
		return false
	}
    return true
 }


function checkValues(){
	document.productionFrm.toexcel.value='no';
	document.productionFrm.actionString.value='';
	var current = new Date();
	var currentMonth = current.getMonth() + 1;
	var currentYear = current.getYear();
	var currentDate = current.getDate();	
	var fromDateValue = document.productionFrm.fromDate.value;
	var toDateValue = document.productionFrm.toDate.value;
	var frmDate = fromDateValue.split('/');
	var todate = toDateValue.split('/');
	var fmYear = frmDate[2].split(' ');
	frmDate[2] = fmYear[0];
	var toyear = todate[2].split(' ');
	todate[2] = toyear[0];
	if(document.productionFrm.fromDate.value == ""){
		alert("Enter from date");
	} else if(document.productionFrm.toDate.value == ""){
		alert("Enter To date");
	}else if(document.productionFrm.facility.value == ""){
		alert("Select a Facility");
	}else if(frmDate[2] == currentYear && frmDate[0] > currentMonth)
	{
		alert("Future date selection is Not allowed");
		document.productionFrm.fromDate.value = "";
	}

	else if(frmDate[2] > currentYear)
	{
			alert("Future date selection is Not allowed");
			document.productionFrm.fromDate.value = "";
	}

	else if(frmDate[2] == currentYear && frmDate[0] == currentMonth && frmDate[1] > currentDate)
	{
		alert("Future date selection is Not allowed");
		document.productionFrm.fromDate.value = "";
	}

		/*else if(todate[2] == currentYear && todate[0] > currentMonth)
	{
		alert("Future date selection is Not allowed");
		document.productionFrm.toDate.value = "";
	}

	else if(todate[2] > currentYear)
	{
			alert("Future date selection is Not allowed");
			document.productionFrm.toDate.value = "";
	}

	else if(todate[2] == currentYear && todate[0] == currentMonth && todate[1] > currentDate)
	{
		alert("Future date selection is Not allowed");
		document.productionFrm.toDate.value = "";
	}		*/
	else
	{			
		document.productionFrm.submit();
		return false;
	}
}
function exportToExcel() {
	document.productionFrm.toexcel.value='yes';
//	document.productionFrm.action="ProductionToExcel.htm";
	document.productionFrm.submit(); 
	return false;
}
function popupModalWithParams(url, features) {
	
	window.showModalDialog(url, '', features);
}

function popupWithParams(url, param, fieldId, features) {
	var paramValue = '';
	var field = document.getElementById(fieldId);
	if (field != null) {
	    paramValue = field.value;
	}
	window.open(url + '&' + param + '=' + paramValue, '', features);
}

function upperCase(x) {
		var y=document.getElementById(x).value;
		document.getElementById(x).value=y.toUpperCase();
}

</script>
<BODY onload="document.productionFrm.fromDate.focus();">
<div id="content">
<FORM name=productionFrm method=post class="form">
<input type="hidden" name="actionString">
<input type="hidden" name="toexcel"> 
<br>
	<CENTER>
		<font size="2"> <b> Production Report - Facility </b> </font>
	</CENTER> 
	<br>
<!-- <br>

	<CENTER>
		<font size="2"> <b> <fmt:message key="table.Production.header"/> </b> </font>
	</CENTER> 
	<br>  -->
<TABLE border="0" cellpadding="0" cellspacing="0" width="85%"
	align="center" id="normalTableNostripe"
	style="white-space: nowrap; word-spacing: normal;">
	<tr valign="top">
		<td rowspan="4" valign="center" size="5" style="font: 900 12px arial;left:20px;top:220px">
			<table width="100%">
				<tr>
					<td>Facility</td>					
				</tr>
				<tr>
					<td><select name="facility" id="facility">
						<option selected="selected"></option>
						<c:forEach items="${command.divisionFacilities}" var="data">
							<option
								<c:if test="${command.facility eq data.facilityId}"> <c:out value="selected"/></c:if>
								value="${data.facilityId}">${data.facilityId}</option>
						</c:forEach>
					</select></td>
				</tr>
			</table>
		</td>
		<td border="0" style="font: 900 11px arial; left: 20px; top: 220px">
			<fmt:message key="table.Production.column1" /></td>
		<td>

		<div id="chooserSpan" class="dateChooser select-free"
			style="display: none; visibility: hidden; width: 160px;"></div>
		<input type="text" id="fromDate" name="fromDate" readonly="readonly"
			value="${command.fromDate}"> <img src="static/images/cal.gif"
			onclick="showChooser(this, 'fromDate', 'chooserSpan', 1950, 2020, Date.patterns.USDatePattern, true);">

		</td style="font: 900 12px arial;left:20px;top:220px">
		<td border="0" style="font: 900 11px arial;left:20px;top:220px">
		<fmt:message key="table.Production.column2" /></td>
		<td>

		<div id="chooserSpan2" class="dateChooser select-free"
			style="display: none; visibility: hidden; width: 160px;"></div>
		<input type="text" id="toDate" name="toDate" readonly="readonly"
			value="${command.toDate}"> <img src="static/images/cal.gif"
			onclick="showChooser(this, 'toDate', 'chooserSpan2', 1950, 2020, Date.patterns.USDatePattern, true);">
		</td>

	</tr>
	<tr valign="top">

		<td style="font: 900 11px arial;left:20px;top:220px"><fmt:message
			key="table.Production.column4" /></td>
		<td><input type="text" name="empId" value="${command.empId}">
		</td>

		<td style="font: 900 11px arial;left:20px;top:220px"><fmt:message
			key="table.Production.column7" /></td>
		<td><input type="text" name="moNumber" size="10" id="moNumber"
			value="${command.moNumber}" onkeyup="upperCase(this.id)"></td>

	</tr>
	<tr valign="top">

		<td style="font: 900 11px arial;left:20px;top:220px">Item Number
		</td>
		<td><input type="text" name="partid" id="partid"
			onkeyup="upperCase(this.id)" value="${command.partid}"></td>

		<td style="font: 900 11px arial;left:20px;top:220px">Asset Number
		</td>
		<td><input type="text" name="assetId" value="${command.assetId}">
		</td>

	</tr>
	<tr valign="top">

		<td border="0" style="font: 900 11px arial;left:20px;top:220px">
		<fmt:message key="table.Production.column8" /></td>
		<td><c:forEach items="${command.arrShiftTime}" var="data"
			varStatus="list">

			<c:if test="${data.shift =='A'}">
				<input type="radio" name="shift"
					value="${data.shiftStartTime}-${data.shiftEndTime}"
					onclick="fnShift(productionFrm, '${data.shiftStartTime}-${data.shiftEndTime}-${data.dateShiftRequired}');"> A &nbsp;&nbsp;&nbsp;
								        </c:if>
			<c:if test="${data.shift =='B'}">
				<input type="radio" name="shift"
					value="${data.shiftStartTime}-${data.shiftEndTime}"
					onclick="fnShift(productionFrm, '${data.shiftStartTime}-${data.shiftEndTime}-${data.dateShiftRequired}');"> B
											&nbsp;&nbsp;&nbsp;
								         </c:if>
			<c:if test="${data.shift =='C'}">
				<input type="radio" name="shift"
					value="${data.shiftStartTime}-${data.shiftEndTime}"
					onclick="fnShift(productionFrm, '${data.shiftStartTime}-${data.shiftEndTime}-${data.dateShiftRequired}');"> C
											&nbsp;&nbsp;&nbsp;
								         </c:if>
			<c:if test="${data.shift =='D'}">
				<input type="radio" name="shift"
					value="${data.shiftStartTime}-${data.shiftEndTime}"
					onclick="fnShift(productionFrm, '${data.shiftStartTime}-${data.shiftEndTime}-${data.dateShiftRequired}');"> D
								         </c:if>

		</c:forEach></td>
		<td colspan="2" align="center">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;<input
			style="font: 900 12px arial;left:20px;top:220px" type="button"
			value="Search" name="ProductionFilter"
			onclick="javascript:checkValues();">
		</td>
	</tr>
</table>

<script language="JavaScript">
var cal1 = new calendar2(document.forms['productionFrm'].elements['fromDate']);
cal1.year_scroll = true;
cal1.time_comp = true;
cal1.left = 400;
cal1.top = 300;
var cal2 = new calendar2(document.forms['productionFrm'].elements['toDate']);
cal2.year_scroll = true;
cal2.time_comp = true;
cal2.left = 600;
cal2.top = 300;
</script> 
		<c:choose>
			<c:when test='${command.status == "False"}'>
				<TABLE id="normalTableFlush4" align="center" cellSpacing="1"
					cellPadding="5" border="0">
					<tr>
						<td class="selected"><font color="#B31B00"> <b> <fmt:message
							key="error.noReports" /> </b> </font></td>
					</tr>
				</TABLE>
			</c:when>
		</c:choose>
		
		<c:if test='${command.status == "True"}'>

				<CENTER>
				<div class="tableContainer" style="height: 330px;">
				<TABLE id="normalTableNostripe" align="center" cellSpacing="0"
					cellPadding="0" border="0"
					style="white-space: nowrap; word-spacing: normal;">
					<thead class="fixedHeader">
						<tr class="selected" height="15px">
							<th class="tableTitle" width="20px"><fmt:message
								key="table.pro.column1" /></th>
							<th class="tableTitle" width="10px"><fmt:message
								key="table.pro.column27" /></th>
							<th class="tableTitle" width="10px"><fmt:message
								key="table.pro.column4" /></th>
							<th class="tableTitle" width="10px"><fmt:message
								key="table.pro.column7" /></th>
							<th class="tableTitle"><fmt:message key="table.pro.column8" />
							</th>
							<th class="tableTitle"
								style="width:460px;white-space: nowrap; word-spacing: normal;">
							<fmt:message key="table.pro.column9" /></th>
							<th class="tableTitle"><fmt:message key="table.pro.column12" />
							</th>
							<th class="tableTitle" width="10px"><fmt:message
								key="table.pro.column13" /></th>
							<th class="tableTitle" width="10px"><fmt:message
								key="table.pro.column16" /></th>
							<th class="tableTitle" width="10px"><fmt:message
								key="table.pro.column20" /></th>
							<th class="tableTitle" width="10px">Qty Completed</th>
							<th class="tableTitle"><fmt:message key="table.pro.column23" />
							</th>
						</tr>
					</thead>
					<tbody class="scrollContent">
						<c:forEach items="${command.productionReport}" var="data"
							varStatus="list">

							<c:choose>
								<c:when test='${(list.index%2) == 0}'>
									<tr class="normalRow">
								</c:when>
								<c:otherwise>
									<tr class="alternateRow">
								</c:otherwise>
							</c:choose>


							<c:choose>
								<c:when test='${empty data.empName}'>
									<td class="tableContent" width="20px">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold;width:1300px;white-space: nowrap; word-spacing: normal;">
										<c:out value="${data.empName}"></c:out>
									<!-- <a
										href="javascript:popupWithParams('empPerformance.htm?empNo=${data.empId}&empName=${data.empName}&fdate=${command.fromDate}&tdate=${command.toDate}&dept=${command.workCenter}','no','${data.jobNo}','left=10,top=80,width=1000, height=400,scrollbars=no, center:yes')"
										style="text-decoration: underline;"> <c:out
										value="${data.empName}"></c:out> </a> -->
										</td>
								</c:otherwise>
							</c:choose>
							
							<c:choose>
								<c:when test='${empty data.workCenter}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold;white-space: nowrap; word-spacing: normal;">
										<c:out value="${data.workCenter}"></c:out>
										</td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.moNumber}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold;width:1300px;white-space: nowrap; word-spacing: normal;">
									<!-- Ramanan.M - 30th Aug  : Added the Parameter 'Facility' to display it in the Current Order Details Popup Screen -->
									<a
										href="javascript:popupWithParams('currentOrderDetails.htm?moNumber=${data.moNumber}&lineNumber=${data.lineNumber}&productionFacility=${command.facility}','no','${data.jobNo}','left=10,top=80,width=1000, height=500,scrollbars=no, center:yes')"
										style="text-decoration: underline;"> <c:out
										value="${data.moNumber}"></c:out> </a></td>
								</c:otherwise>
							</c:choose>


							<c:choose>
								<c:when test='${empty data.partid}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold;white-space: nowrap; word-spacing: normal;">
										<c:out value="${data.partid}"></c:out>
									<!-- <a
										href="javascript:popupWithParams('standardRate.htm?partNo=${data.partid}&PartDesc=${data.partDesc}&fdate=${command.fromDate}&tdate=${command.toDate}&dept=${command.workCenter}','no','${data.jobNo}','left=80,top=80,width=900, height=700,scrollbars=no, center:yes')"
										style="text-decoration: underline;"> <c:out
										value="${data.partid}"></c:out></a> -->
										</td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.asset}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold;white-space: nowrap; word-spacing: normal;">
									<!-- <a
										href="javascript:popupWithParams('assetUsage.htm?assetNo=${data.asset}&assetDesc=${data.assetDesc}&fdate=${command.fromDate}&tdate=${command.toDate}&dept=${command.workCenter}','no','${data.jobNo}','left=80,top=80,width=900, height=700,scrollbars=no, center:yes')"
										style="text-decoration: underline;"> <c:out
										value="${data.asset}"></c:out> </a> -->
										 <c:out value="${data.asset}"></c:out> 
										</td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.assetDesc}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="width:4160px;white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.assetDesc}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.setup}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="width:10px;white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.setup}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.run}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent" width="10px"
										style="width:10px;white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.run}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.reTool}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="width:10px;white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.reTool}"></c:out></td>
								</c:otherwise>
							</c:choose>


							<c:choose>
								<c:when test='${empty data.totalHours1}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="width:10px;white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.totalHours1}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.completedQty}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="width:10px;white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.completedQty}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<!-- 	<td class="tableContent" style="white-space: nowrap; word-spacing: normal;"> 
								<a href="javascript:popupWithParams('standardRate.htm?no=${data.jobNo}','no','${data.jobNo}','width=900,height=360,top=100,screenX=0,screenY=200,scrollbars=yes')" style="text-decoration: underline;"> <c:out value="StdRate"></c:out> </a> 
							</td>	
													
							<td class="tableContent" style="white-space: nowrap; word-spacing: normal;"> 
								<a href="javascript:popupWithParams('assetUsage.htm?no=${data.jobNo}','no','${data.jobNo}','width=900,height=390,top=100,screenX=0,screenY=200,scrollbars=yes')" style="text-decoration: underline;"> <c:out value="AssetUsage"></c:out> </a> 
							</td>					

							<td class="tableContent" style="white-space: nowrap; word-spacing: normal;"> 
								<a href="javascript:popupWithParams('empPerformance.htm?no=${data.jobNo}','no','${data.jobNo}','width=900,height=360,top=100,screenX=0,screenY=200,scrollbars=yes')" style="text-decoration: underline;"> <c:out value="Performance"></c:out> </a> 
							</td>														
					 		-->

							<c:if test="${data.expCount =='0'}">
								<td class="tableContent"
									style="white-space: nowrap; word-spacing: normal;"><c:out
									value="No Exceptions"></c:out></td>
							</c:if>

							<c:if test="${data.expCount !='0'}">
								<td class="tableContent"
									style="font-weight: bold;width:1300px;white-space: nowrap; word-spacing: normal;">
								<a
									href="javascript:popupWithParams('exceptionalActivity.htm?empNo=${data.empId}&moNo=${data.moNumber}&lineNo=${data.lineNumber}&dept=${command.workCenter}','no','${data.jobNo}','left=80,top=80,width=900, height=700,scrollbars=no, center:yes')"
									style="text-decoration: underline;"> <c:out
									value="Exceptional Activity"></c:out> </a></td>
							</c:if>

							</tr>
						</c:forEach>
					</tbody>
				</TABLE>
				</div>

				<div align="right"><b> Total Completed Qty &nbsp;&nbsp;:
				&nbsp;&nbsp;<c:out value="${command.totalQtyCompleted}"></c:out>
				&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#" name="excel" onclick="javascript:exportToExcel();"
					target="_self"> Export to Excel </a> &nbsp;&nbsp;&nbsp;&nbsp; </b></div>
				</CENTER>
			</c:if>		
</FORM>