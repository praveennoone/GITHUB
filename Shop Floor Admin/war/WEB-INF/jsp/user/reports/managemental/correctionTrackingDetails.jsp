<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">

function checkValues(){
	
	/*document.correctionTrackingDetailsFrm.actionString.value='';
	var current = new Date();
	var currentMonth = current.getMonth() + 1;
	var currentYear = current.getYear();
	var currentDate = current.getDate();	
	var fromDateValue = document.correctionTrackingDetailsFrm.fromDate.value;
	var toDateValue = document.correctionTrackingDetailsFrm.toDate.value;
	var frmDate = fromDateValue.split('/');
	var todate = toDateValue.split('/');
	var fmYear = frmDate[2].split(' ');
	frmDate[2] = fmYear[0];
	var toyear = todate[2].split(' ');
	todate[2] = toyear[0];
	if(document.correctionTrackingDetailsFrm.fromDate.value == ""){
		alert("Enter from date");
	} else if(document.correctionTrackingDetailsFrm.toDate.value == ""){
		alert("Enter To date");*/
	/*}else if(frmDate[2] == currentYear && frmDate[0] > currentMonth)
	{
		alert("Future date selection is Not allowed");
		document.correctionTrackingDetailsFrm.fromDate.value = "";
	}

	else if(frmDate[2] > currentYear)
	{
			alert("Future date selection is Not allowed");
			document.correctionTrackingDetailsFrm.fromDate.value = "";
	}

	else if(frmDate[2] == currentYear && frmDate[0] == currentMonth && frmDate[1] > currentDate)
	{
		alert("Future date selection is Not allowed");
		document.correctionTrackingDetailsFrm.fromDate.value = "";
	}*/

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
	/*else
	{	*/		
		document.correctionTrackingDetailsFrm.submit();/*
	}*/
}
function exportToExcel() {
	//document.correctionTrackingDetailsFrm.toexcel.value='yes';
//	document.productionFrm.action="ProductionToExcel.htm";
	//document.correctionTrackingDetailsFrm.submit(); 
}

</script>
<BODY onload="document.correctionTrackingDetailsFrm.fromDate.focus();">

<FORM name=correctionTrackingDetailsFrm method=post class="form">

<input type="hidden" name="actionString">
<br>
<CENTER><font size="2"> <b> <fmt:message
	key="table.correctionTrackingDetails.header" /> </b> </font></CENTER>
<br>

<table style="border: 1px solid #465272;" align="center">
	<tr>
		<td border="0" style="font: 900 11px arial; left: 20px; top: 220px">
		<fmt:message key="table.correctionTrackingDetails.column1" /></td>
		<td>

		<div id="chooserSpan" class="dateChooser select-free"
			style="display: none; visibility: hidden; width: 160px;"></div>

		<input type="text" id="fromDate" name="fromDate" readonly="readonly"
			value=<fmt:formatDate  value="${command.dtFromDate}" pattern="MM/dd/yyyy"/>>
		<A onmouseover="window.status  ='Date Picker';return true;"
			onmouseout="window.status='';return true;"
			href="javascript:cal1.popup();"> <IMG alt="Calendar"
			src="static/images/cal.gif" border="0"
			style="vertical-align: bottom;"></A></td style="font: 900 12px arial;left:20px;top:220px">
		<td border="0" style="font: 900 11px arial; left: 20px; top: 220px">
		<fmt:message key="table.correctionTrackingDetails.column2" /></td>
		<td>

		<div id="chooserSpan2" class="dateChooser select-free"
			style="display: none; visibility: hidden; width: 160px;"></div>
		<input type="text" id="toDate" name="toDate" readonly="readonly"
			value=<fmt:formatDate  value="${command.dtToDate}" pattern="MM/dd/yyyy"/>>
		<A onmouseover="window.status  ='Date Picker';return true;"
			onmouseout="window.status='';return true;"
			href="javascript:cal2.popup();"> <IMG alt="Calendar"
			src="static/images/cal.gif" border="0"
			style="vertical-align: bottom;"></A></td>
		<td><!-- <input type="button" value="Filter" name="currentOrderDetailsFilter" onclick="javascript:checkAndSubmit();"> -->
		<a href="javascript:checkValues();" alt="Filter" tabindex="5"
			style="cursor: hand;"> <img src="static/images/filter.gif"
			alt="Filter" border="0"> </a></td>
	</tr>

</TABLE>

<script language="JavaScript">
var cal1 = new calendar2(document.forms['correctionTrackingDetailsFrm'].elements['fromDate']);
cal1.year_scroll = true;
cal1.time_comp = false;
cal1.left = 400;
cal1.top = 300;
var cal2 = new calendar2(document.forms['correctionTrackingDetailsFrm'].elements['toDate']);
cal2.year_scroll = true;
cal2.time_comp = false;
cal2.left = 600;
cal2.top = 300;
</script>
<div class="tableContainer" style="height: 380px; overflow: auto; width: 100%;">

<!-- Build Asset Corrections --> 
<c:choose>
	<c:when test="${not empty command.assetNumberCorrectionTrackingReport}">

		<c:choose>

			<c:when test='${empty command.assetNumberCorrectionTrackingReport}'>
				<TABLE id="normalTableFlush4" align="center" cellSpacing="1"
					cellPadding="5" border="0">
					<tr>
						<td class="selected"><font color="#B31B00"> <b> <fmt:message
							key="error.noReports" /> </b> </font></td>
					</tr>
				</TABLE>
			</c:when>

			<c:otherwise>
				<p align="left"><font size="2"> <b> <fmt:message
					key="table.correctionTrackingDetails.header.assetCorrections" /> </b>
				</font></p>
				<TABLE id="normalTableNostripe" align="center"
					cellSpacing="0" cellPadding="0" border="0"
					style="white-space: nowrap; word-spacing: normal; width: 96%;">
					<thead class="fixedHeader">
						<tr class="noFixedHeader">
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data1" />
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data2" />
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data3" />
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data4" />
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data5" />
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data6" />
							<th class="tableTitle" style="width: 16%;"><fmt:message
								key="table.correctionTrackingDetails.data7" />
						</tr>
					</thead>
				</table>
				<div class="tableContainer" style="height: 122px; width: 98%; overflow: auto;">
				<table id="normalTableNostripe" style="width: 98%;">
					<tbody class="scrollContent">
						<c:forEach items="${command.assetNumberCorrectionTrackingReport}"
							var="data" varStatus="list">

							<c:choose>
								<c:when test='${(list.index%2) == 0}'>
									<tr class="normalRow">
								</c:when>
								<c:otherwise>
									<tr class="alternateRow">
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.correctedBy}'>
									<td class="tableContent" style="width: 14%">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.correctedBy}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.moNumber}'>
									<td class="tableContent" style="width: 14%">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.moNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.sequenceNumber}'>
									<td class="tableContent" style="width: 14%">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.sequenceNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.operator}'>
									<td class="tableContent" style="width: 14%">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.operator}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.activity}'>
									<td class="tableContent" style="width: 14%">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.activity}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.error}'>
									<td class="tableContent" style="width: 14%">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.error}"></c:out></td>
								</c:otherwise>
							</c:choose>
							
							<c:choose>
								<c:when test='${empty data.correction}'>
									<td class="tableContent" style="width: 16%">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 16%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.correction}"></c:out></td>
								</c:otherwise>
							</c:choose>

							</tr>
						</c:forEach>
					</tbody>
				</TABLE>
				</div>

			</c:otherwise>

		</c:choose>
	</c:when>
</c:choose>

 <!-- Build Logon Corrections --> <c:choose>
	<c:when test="${not empty command.logonCorrectionTrackingReport}">

		<c:choose>

			<c:when test='${empty command.logonCorrectionTrackingReport}'>
				<TABLE id="normalTableFlush4" align="center" cellSpacing="1"
					cellPadding="5" border="0">
					<tr>
						<td class="selected"><font color="#B31B00"> <b> <fmt:message
							key="error.noReports" /> </b> </font></td>
					</tr>
				</TABLE>
			</c:when>

			<c:otherwise>
				<p align="left"><font size="2"> <b> <fmt:message
					key="table.correctionTrackingDetails.header.logonCorrections" /> </b>
				</font></p>
				<TABLE id="normalTableNostripe" align="center"
					cellSpacing="0" cellPadding="0" border="0"
					style="white-space: nowrap; word-spacing: normal; width: 96%;">
					<thead class="fixedHeader">
						<tr class="noFixedHeader">
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data1" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data2" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data3" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data4" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data5" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data6" /></th>
							<th class="tableTitle" style="width: 16%;"><fmt:message
								key="table.correctionTrackingDetails.data7" /></th>
						</tr>
					</thead>
				</table>
				
				<div class="tableContainer" style="height: 122px; width: 98%; overflow: auto;">
				<table id="normalTableNostripe" style="width: 98%;">
					<tbody class="scrollContent">
						<c:forEach items="${command.logonCorrectionTrackingReport}"
							var="data" varStatus="list">

							<c:choose>
								<c:when test='${(list.index%2) == 0}'>
									<tr class="normalRow">
								</c:when>
								<c:otherwise>
									<tr class="alternateRow">
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.correctedBy}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.correctedBy}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.moNumber}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.moNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.sequenceNumber}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.sequenceNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.operator}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.operator}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.activity}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.activity}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.error}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">

									<c:out value="${data.error}"></c:out></td>
								</c:otherwise>
							</c:choose>
							
							<c:choose>
								<c:when test='${empty data.correction}'>
									<td class="tableContent" style="width: 16%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 16%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.correction}"></c:out></td>
								</c:otherwise>
							</c:choose>

							</tr>
						</c:forEach>
					</tbody>
				</TABLE>
				</div>

			</c:otherwise>

		</c:choose>
	</c:when>
</c:choose> 

<!-- Build Logoff Corrections --> 
<c:choose>
	<c:when test="${not empty command.logOffCorrectionTrackingReport}">

		<c:choose>

			<c:when test='${empty command.logOffCorrectionTrackingReport}'>
				<TABLE id="normalTableFlush4" align="center" cellSpacing="1"
					cellPadding="5" border="0">
					<tr>
						<td class="selected"><font color="#B31B00"> <b> <fmt:message
							key="error.noReports" /> </b> </font></td>
					</tr>
				</TABLE>
			</c:when>

			<c:otherwise>
				<p align="left"><font size="2"> <b> <fmt:message
					key="table.correctionTrackingDetails.header.logofCorrections" /> </b>
				</font></p>
				<TABLE id="normalTableNostripe" cellSpacing="0"
					cellPadding="0" border="0"
					style="white-space: nowrap; word-spacing: normal; width: 96%;">
					<thead class="fixedHeader">
						<tr class="noFixedHeader">
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data1" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data2" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data3" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data4" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data5" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data6" /></th>
							<th class="tableTitle" style="width: 16%;"><fmt:message
								key="table.correctionTrackingDetails.data7" /></th>
						</tr>
					</thead>
				</table>
				<div class="tableContainer" style="height: 122px; overflow: auto; width: 98%;">
				<table id="normalTableNostripe" style="width: 98%;">
					<tbody class="scrollContent">
						<c:forEach items="${command.logOffCorrectionTrackingReport}"
							var="data" varStatus="list">

							<c:choose>
								<c:when test='${(list.index%2) == 0}'>
									<tr class="normalRow">
								</c:when>
								<c:otherwise>
									<tr class="alternateRow">
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.correctedBy}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.correctedBy}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.moNumber}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.moNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.sequenceNumber}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.sequenceNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.operator}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.operator}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.activity}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.activity}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.error}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.error}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.correction}'>
									<td class="tableContent" style="width: 16%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 16%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.correction}"></c:out></td>
								</c:otherwise>
							</c:choose>

							</tr>
						</c:forEach>
					</tbody>
				</TABLE>
				</div>

			</c:otherwise>

		</c:choose>
	</c:when>
</c:choose> 

<c:choose>
	<c:when test="${not empty command.activityCorrectionTrackingReport}">

		<c:choose>

			<c:when test='${empty command.activityCorrectionTrackingReport}'>
				<TABLE id="normalTableFlush4" align="center" cellSpacing="1"
					cellPadding="5" border="0">
					<tr>
						<td class="selected"><font color="#B31B00"> <b> <fmt:message
							key="error.noReports" /> </b> </font></td>
					</tr>
				</TABLE>
			</c:when>

			<c:otherwise>
				<p align="left"><font size="2"> <b> <fmt:message
					key="table.correctionTrackingDetails.header.activityCorrections" />
				</b> </font></p>
				<TABLE id="normalTableNostripe" align="center"
					cellSpacing="0" cellPadding="0" border="0"
					style="white-space: nowrap; word-spacing: normal; width: 96%;">
					<thead class="fixedHeader">
						<tr class="noFixedHeader">
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data1" />
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data2" />
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data3" />
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data4" />
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data5" />
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data6" />
							<th class="tableTitle" style="width: 16%;"><fmt:message
								key="table.correctionTrackingDetails.data7" /></th>
						</tr>
					</thead>
				</table>
				<div class="tableContainer" style="height: 122px; overflow: auto; width: 98%;">
				<table id="normalTableNostripe" style="width: 98%;">
					<tbody class="scrollContent">
						<c:forEach items="${command.activityCorrectionTrackingReport}"
							var="data" varStatus="list">

							<c:choose>
								<c:when test='${(list.index%2) == 0}'>
									<tr class="normalRow">
								</c:when>
								<c:otherwise>
									<tr class="alternateRow">
								</c:otherwise>
							</c:choose>


							<c:choose>
								<c:when test='${empty data.correctedBy}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.correctedBy}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.moNumber}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.moNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.sequenceNumber}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.sequenceNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.operator}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.operator}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.activity}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.activity}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.error}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.error}"></c:out></td>
								</c:otherwise>
							</c:choose>
							
							<c:choose>
								<c:when test='${empty data.correction}'>
									<td class="tableContent" style="width: 16%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 16%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.correction}"></c:out></td>
								</c:otherwise>
							</c:choose>

							</tr>
						</c:forEach>
					</tbody>
				</TABLE>
				</div>

			</c:otherwise>

		</c:choose>
	</c:when>
</c:choose> 

<c:choose>
	<c:when test="${not empty command.employeeCorrectionTrackingReport}">

		<c:choose>

			<c:when test='${empty command.employeeCorrectionTrackingReport}'>
				<TABLE id="normalTableFlush4" align="center" cellSpacing="1"
					cellPadding="5" border="0">
					<tr>
						<td class="selected"><font color="#B31B00"> <b> <fmt:message
							key="error.noReports" /> </b> </font></td>
					</tr>
				</TABLE>
			</c:when>

			<c:otherwise>
				<p align="left"><font size="2"> <b> <fmt:message
					key="table.correctionTrackingDetails.header.empCorrections" /> </b> </font></p>
				<table id="normalTableNostripe" align="center"
					cellSpacing="0" cellPadding="0" border="0"
					style="white-space: nowrap; word-spacing: normal; width: 96%;">
					<thead class="fixedHeader">
						<tr class="noFixedHeader">
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data1" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data2" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data3" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data4" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data5" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data6" /></th>
							<th class="tableTitle" style="width: 16%;"><fmt:message
								key="table.correctionTrackingDetails.data7" /></th>
						</tr>
					</thead>
				</table>
				<div class="tableContainer" style="height: 122px; width: 98%; overflow: auto;">
				<table id="normalTableNostripe" style="width: 98%;">
					<tbody class="scrollContent">
						<c:forEach items="${command.employeeCorrectionTrackingReport}"
							var="data" varStatus="list">

							<c:choose>
								<c:when test='${(list.index%2) == 0}'>
									<tr class="normalRow">
								</c:when>
								<c:otherwise>
									<tr class="alternateRow">
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.correctedBy}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.correctedBy}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.moNumber}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.moNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.sequenceNumber}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.sequenceNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.operator}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.operator}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.activity}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.activity}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.error}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.error}"></c:out></td>
								</c:otherwise>
							</c:choose>
							
							<c:choose>
								<c:when test='${empty data.correction}'>
									<td class="tableContent" style="width: 16%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 16%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.correction}"></c:out></td>
								</c:otherwise>
							</c:choose>

							</tr>
						</c:forEach>
					</tbody>
				</TABLE>
				</div>

			</c:otherwise>

		</c:choose>
	</c:when>
</c:choose> 

<c:choose>
	<c:when
		test="${not empty command.qtyCompletedCorrectionTrackingReport}">

		<c:choose>

			<c:when test='${empty command.qtyCompletedCorrectionTrackingReport}'>
				<TABLE id="normalTableFlush4" align="center" cellSpacing="1"
					cellPadding="5" border="0">
					<tr>
						<td class="selected"><font color="#B31B00"> <b> <fmt:message
							key="error.noReports" /> </b> </font></td>
					</tr>
				</TABLE>
			</c:when>

			<c:otherwise>
				<p align="left"><font size="2"> <b> <fmt:message
					key="table.correctionTrackingDetails.header.qtyCompletedCorrections" />
				</b> </font></p>
				<TABLE id="normalTableNostripe" align="center"
					cellSpacing="0" cellPadding="0" border="0"
					style="white-space: nowrap; word-spacing: normal; width: 96%;">
					<thead class="fixedHeader">
						<tr class="noFixedHeader">
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data1" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data2" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data3" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data4" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data5" /></th>
							<th class="tableTitle" style="width: 14%;"><fmt:message
								key="table.correctionTrackingDetails.data6" /></th>
							<th class="tableTitle" style="width: 16%;"><fmt:message
								key="table.correctionTrackingDetails.data7" /></th>
						</tr>
					</thead>
				</table>

				<div class="tableContainer" style="height: 122px; width: 98%; overflow: auto;">
				<table id="normalTableNostripe" style="width: 98%;">
					<tbody class="scrollContent">
						<c:forEach items="${command.qtyCompletedCorrectionTrackingReport}"
							var="data" varStatus="list">

							<c:choose>
								<c:when test='${(list.index%2) == 0}'>
									<tr class="normalRow">
								</c:when>
								<c:otherwise>
									<tr class="alternateRow">
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.correctedBy}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.correctedBy}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.moNumber}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.moNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.sequenceNumber}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.sequenceNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.operator}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.operator}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.activity}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">

									<c:out value="${data.activity}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.error}'>
									<td class="tableContent" style="width: 14%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 14%; white-space: nowrap; word-spacing: normal;">

									<c:out value="${data.error}"></c:out></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test='${empty data.correction}'>
									<td class="tableContent" style="width: 16%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 16%; white-space: nowrap; word-spacing: normal;">

									<c:out value="${data.correction}"></c:out></td>
								</c:otherwise>
							</c:choose>


							</tr>
						</c:forEach>
					</tbody>
				</TABLE>
				</div>

			</c:otherwise>


		</c:choose>
	</c:when>
</c:choose>

<c:choose>
	<c:when
		test="${not empty command.completedSequenceCorrectionTrackingReport}">

		<c:choose>

			<c:when
				test='${empty command.completedSequenceCorrectionTrackingReport}'>
				<TABLE id="normalTableFlush4" align="center" cellSpacing="1"
					cellPadding="5" border="0">
					<tr>
						<td class="selected"><font color="#B31B00"> <b> <fmt:message
							key="error.noReports" /> </b> </font></td>
					</tr>
				</TABLE>
			</c:when>

			<c:otherwise>
				<p align="left"><font size="2"> <b> <fmt:message
					key="table.correctionTrackingDetails.header.completedSequenceCorrections" />
				</b> </font></p>
				<TABLE id="normalTableNostripe" align="center"
					cellSpacing="0" cellPadding="0" border="0"
					style="white-space: nowrap; word-spacing: normal; width: 96%;">
					<thead class="fixedHeader">
						<tr class="noFixedHeader">
							<th class="tableTitle" style="width: 11%;"><fmt:message
								key="table.correctionTrackingDetails.data1" />
							<th class="tableTitle" style="width: 11%;"><fmt:message
								key="table.correctionTrackingDetails.data2" />
							<th class="tableTitle" style="width: 11%;"><fmt:message
								key="table.correctionTrackingDetails.data3" />
							<th class="tableTitle" style="width: 11%;"><fmt:message
								key="table.correctionTrackingDetails.data4" />
							<th class="tableTitle" style="width: 11%;"><fmt:message
								key="table.correctionTrackingDetails.data18" />
							<th class="tableTitle" style="width: 11%;"><fmt:message
								key="table.correctionTrackingDetails.data5" />
							<th class="tableTitle" style="width: 11%;"><fmt:message
								key="table.correctionTrackingDetails.data9" />
							<th class="tableTitle" style="width: 11%;"><fmt:message
								key="table.correctionTrackingDetails.data10" />
							<th class="tableTitle" style="width: 12%;"><fmt:message
								key="table.correctionTrackingDetails.data11" /></th>
						</tr>
					</thead>
				</table>
				<div class="tableContainer" style="height: 122px; width: 98%; overflow: auto;">
				<table id="normalTableNostripe" style="width: 98%;">
					<tbody class="scrollContent">
						<c:forEach
							items="${command.completedSequenceCorrectionTrackingReport}"
							var="data" varStatus="list">

							<c:choose>
								<c:when test='${(list.index%2) == 0}'>
									<tr class="normalRow">
								</c:when>
								<c:otherwise>
									<tr class="alternateRow">
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.correctedBy}'>
									<td class="tableContent" style="width: 11%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 11%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.correctedBy}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.moNumber}'>
									<td class="tableContent" style="width: 11%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 11%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.moNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.sequenceNumber}'>
									<td class="tableContent" style="width: 11%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 11%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.sequenceNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.operator}'>
									<td class="tableContent" style="width: 11%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 11%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.operator}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.date}'>
									<td class="tableContent" style="width: 11%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 11%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.date}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.activity}'>
									<td class="tableContent" style="width: 11%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 11%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.activity}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.quantity}'>
									<td class="tableContent" style="width: 11%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 11%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.quantity}"></c:out></td>
								</c:otherwise>
							</c:choose>
							
							<c:choose>
								<c:when test='${empty data.login}'>
									<td class="tableContent" style="width: 11%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 11%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.login}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.logout}'>
									<td class="tableContent" style="width: 12%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 12%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.logout}"></c:out></td>
								</c:otherwise>
							</c:choose>

							</tr>
						</c:forEach>
					</tbody>
				</TABLE>
				</div>

			</c:otherwise>

		</c:choose>
	</c:when>
</c:choose> 

<c:choose>
	<c:when
		test="${not empty command.addedSequenceCorrectionTrackingReport}">

		<c:choose>

			<c:when test='${empty command.addedSequenceCorrectionTrackingReport}'>
				<TABLE id="normalTableFlush4" align="center" cellSpacing="1"
					cellPadding="5" border="0">
					<tr>
						<td class="selected"><font color="#B31B00"> <b> <fmt:message
							key="error.noReports" /> </b> </font></td>
					</tr>
				</TABLE>
			</c:when>

			<c:otherwise>
				<p align="left"><font size="2"> <b> <fmt:message
					key="table.correctionTrackingDetails.header.addedSequenceCorrections" />
				</b> </font></p>
				<TABLE id="normalTableNostripe" align="center"
					cellSpacing="0" cellPadding="0" border="0"
					style="white-space: nowrap; word-spacing: normal; width: 96%;">
					<thead class="fixedHeader">
						<tr class="noFixedHeader">
							<th class="tableTitle" style="width: 11%;"><fmt:message
								key="table.correctionTrackingDetails.data1" />
							<th class="tableTitle" style="width: 11%;"><fmt:message
								key="table.correctionTrackingDetails.data2" />
							<th class="tableTitle" style="width: 11%;"><fmt:message
								key="table.correctionTrackingDetails.data3" />
							<th class="tableTitle" style="width: 11%;"><fmt:message
								key="table.correctionTrackingDetails.data12" />
							<th class="tableTitle" style="width: 11%;"><fmt:message
								key="table.correctionTrackingDetails.data4" />
							<th class="tableTitle" style="width: 11%;"><fmt:message
								key="table.correctionTrackingDetails.data18" />
							<th class="tableTitle" style="width: 11%;"><fmt:message
								key="table.correctionTrackingDetails.data5" />
							<th class="tableTitle" style="width: 11%;"><fmt:message
								key="table.correctionTrackingDetails.data9" />
							<th class="tableTitle" style="width: 12%;"><fmt:message
								key="table.correctionTrackingDetails.data13" /></th>
						</tr>
					</thead>
				</table>

				<div class="tableContainer"
					style="height: 122px; width: 98%; overflow: auto;">
				<table id="normalTableNostripe" style="width: 98%;">
					<tbody class="scrollContent">
						<c:forEach
							items="${command.addedSequenceCorrectionTrackingReport}"
							var="data" varStatus="list">

							<c:choose>
								<c:when test='${(list.index%2) == 0}'>
									<tr class="normalRow">
								</c:when>
								<c:otherwise>
									<tr class="alternateRow">
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.correctedBy}'>
									<td class="tableContent" style="width: 11%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 11%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.correctedBy}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.moNumber}'>
									<td class="tableContent" style="width: 11%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 11%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.moNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.sequenceNumber}'>
									<td class="tableContent" style="width: 11%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 11%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.sequenceNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.stampNo}'>
									<td class="tableContent" style="width: 11%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 11%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.stampNo}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.operator}'>
									<td class="tableContent" style="width: 11%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 11%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.operator}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.date}'>
									<td class="tableContent" style="width: 11%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 11%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.date}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.activity}'>
									<td class="tableContent" style="width: 11%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 11%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.activity}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.quantity}'>
									<td class="tableContent" style="width: 11%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 11%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.quantity}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.assetNumber}'>
									<td class="tableContent" style="width: 12%;">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="font-weight: bold; width: 12%; white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.assetNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							</tr>
						</c:forEach>
					</tbody>
				</TABLE>
				</div>

			</c:otherwise>

		</c:choose>
	</c:when>
</c:choose>

<c:choose>
	<c:when test="${not empty command.multipleCorrectionTrackingReport}">

		<c:choose>

			<c:when test='${empty command.multipleCorrectionTrackingReport}'>
				<TABLE id="normalTableFlush4" align="center" cellSpacing="1"
					cellPadding="5" border="0">
					<tr>
						<td class="selected"><font color="#B31B00"> <b> <fmt:message
							key="error.noReports" /> </b> </font></td>
					</tr>
				</TABLE>
			</c:when>

			<c:otherwise>
				<p align="left"><font size="2"> <b> <fmt:message
					key="table.correctionTrackingDetails.header.multipleSequenceCorrections" />
				</b> </font></p>
				<TABLE id="normalTableNostripe" align="center"
					cellSpacing="0" cellPadding="0" border="0"
					style="white-space: nowrap; word-spacing: normal; width: 96%;">
					<thead class="fixedHeader">
						<tr class="noFixedHeader">
							<th class="tableTitle" style="width: 10%;"></th>
							<th class="tableTitle" style="width: 8%;"><fmt:message
								key="table.correctionTrackingDetails.data2" />
							<th class="tableTitle" style="width: 8%;"><fmt:message
								key="table.correctionTrackingDetails.data3" />
							<th class="tableTitle" style="width: 13%;"><fmt:message
								key="table.correctionTrackingDetails.data5" />
							<th class="tableTitle" style="width: 13%;"><fmt:message
								key="table.correctionTrackingDetails.data4" />
							<th class="tableTitle" style="width: 10%;"><fmt:message
								key="table.correctionTrackingDetails.data18" />
							<th class="tableTitle" style="width: 8%;"><fmt:message
								key="table.correctionTrackingDetails.data10" />
							<th class="tableTitle" style="width: 9%;"><fmt:message
								key="table.correctionTrackingDetails.data11" />
							<th class="tableTitle" style="width: 7%;"><fmt:message
								key="table.correctionTrackingDetails.data9" />
							<th class="tableTitle" style="width: 7%;"><fmt:message
								key="table.correctionTrackingDetails.data13" />
							<th class="tableTitle" style="width: 8%;"><fmt:message
								key="table.correctionTrackingDetails.data14" />
						</tr>
					</thead>
				</TABLE>
				
				<div class="tableContainer" style="height: 122px; width: 98%; overflow: auto;">
				<table style="width: 98%;">
					<c:forEach items="${command.multipleCorrectionTrackingReport}"
						var="data" varStatus="list">
						<TABLE id="normalTableNostripe" width="100%" align="center"
							cellSpacing="0" cellPadding="0" border="0"
							style="white-space: nowrap; word-spacing: normal;">
							<tr class="noFixedHeader">
								<th class="tableTitle" width="10%"><fmt:message
									key="table.correctionTrackingDetails.data15" /> <c:out
									value="${data.correctedBy}"></c:out></th>
							</tr>
						</TABLE>

						<table id="normalTableNostripe"  style="width: 98%;">
							<tbody class="scrollContent">
								<c:forEach items="${data.corrections}" var="alldata"
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
										<c:when test='${(list.index%2) == 0}'>
											<td class="tableTitle" style="width: 10%;"><fmt:message
												key="table.correctionTrackingDetails.data16" />
										</c:when>

										<c:otherwise>
											<td class="tableTitle" style="width: 10%;"><fmt:message
												key="table.correctionTrackingDetails.data17" />
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test='${empty alldata.moNumber}'>
											<td class="tableTitle" style="width: 8%;">&nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"
												style="font-weight: bold; width: 8%; white-space: nowrap; word-spacing: normal;">
											<c:out value="${alldata.moNumber}"></c:out></td>
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test='${empty alldata.sequenceNumber}'>
											<td class="tableTitle" style="width: 8%;">&nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"
												style="font-weight: bold; width: 8%; white-space: nowrap; word-spacing: normal;">
											<c:out value="${alldata.sequenceNumber}"></c:out></td>
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test='${empty alldata.activity}'>
											<td class="tableTitle" style="width: 13%;">&nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"
												style="font-weight: bold; width: 13%; white-space: nowrap; word-spacing: normal;">
											<c:out value="${alldata.activity}"></c:out></td>
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test='${empty alldata.operator}'>
											<td class="tableTitle" style="width: 13%;">&nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"
												style="font-weight: bold; width: 13%; white-space: nowrap; word-spacing: normal;">
											<c:out value="${alldata.operator}"></c:out></td>
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test='${empty alldata.date}'>
											<td class="tableTitle" style="width: 10%;">&nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"
												style="font-weight: bold; width: 10%; white-space: nowrap; word-spacing: normal;">
											<c:out value="${alldata.date}"></c:out></td>
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test='${empty alldata.login}'>
											<td class="tableTitle" style="width: 8%;">&nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"
												style="font-weight: bold; width: 8%; white-space: nowrap; word-spacing: normal;">
											<c:out value="${alldata.login}"></c:out></td>
										</c:otherwise>
									</c:choose>
									
									<c:choose>
										<c:when test='${empty alldata.logout}'>
											<td class="tableTitle" style="width: 9%;">&nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"
												style="font-weight: bold; width: 9%; white-space: nowrap; word-spacing: normal;">
											<c:out value="${alldata.logout}"></c:out></td>
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test='${empty alldata.quantity}'>
											<td class="tableTitle" style="width: 7%;">&nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"
												style="font-weight: bold; width: 7%; white-space: nowrap; word-spacing: normal;">
											<c:out value="${alldata.quantity}"></c:out></td>
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test='${empty alldata.assetNumber}'>
											<td class="tableTitle" style="width: 7%;">&nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"
												style="font-weight: bold; width: 7%; white-space: nowrap; word-spacing: normal;">
											<c:out value="${alldata.assetNumber}"></c:out></td>
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test='${empty alldata.completedSequence}'>
											<td class="tableTitle" style="width: 8%;">&nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"
												style="font-weight: bold; width: 8%; white-space: nowrap; word-spacing: normal;">
											<c:out value="${alldata.completedSequence}"></c:out></td>
										</c:otherwise>
									</c:choose>

									</tr>

								</c:forEach>
							</tbody>
						</table>
					</c:forEach>

				</TABLE>
				</div>

			</c:otherwise>

		</c:choose>
	</c:when>
</c:choose>
</div>
</FORM>
