<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
function checkValues(){
	document.logonLogoffFrm.toexcel.value='no';
var current = new Date();
	var currentMonth = current.getMonth() + 1;
	var currentYear = current.getYear();
	var currentDate = current.getDate();	
	var fromDateValue = document.logonLogoffFrm.fromDate.value;
	var toDateValue = document.logonLogoffFrm.toDate.value;
	var frmDate = fromDateValue.split('-');
	var todate = toDateValue.split('-');
	if(document.logonLogoffFrm.fromDate.value == ""){
		alert("Enter from date");
	} else if(document.logonLogoffFrm.toDate.value == ""){
		alert("Enter To date");
	}else if(document.logonLogoffFrm.empId.value == ""){
		alert("Enter Employee ID");
	}else if(frmDate[0] == currentYear && frmDate[1] > currentMonth) 
	{
		alert("Future date selection is Not allowed");
		document.logonLogoffFrm.fromDate.value = "";
	}

	else if(frmDate[0] > currentYear)  
	{
			alert("Future date selection is Not allowed");
			document.logonLogoffFrm.fromDate.value = "";
	}

	else if(frmDate[0] == currentYear && frmDate[1] == currentMonth && frmDate[2] > currentDate) 
	{
		alert("Future date selection is Not allowed");
		document.logonLogoffFrm.fromDate.value = "";
	}
		/*
		else if(todate[0] == currentYear && todate[1] > currentMonth) 
	{
		alert("Future date selection is Not allowed");
		document.logonLogoffFrm.toDate.value = "";
	}

	else if(todate[0] > currentYear)  
	{
			alert("Future date selection is Not allowed");
			document.logonLogoffFrm.toDate.value = "";
	}

	else if(todate[0] == currentYear && todate[1] == currentMonth && todate[2] > currentDate) 
	{
		alert("Future date selection is Not allowed");
		document.logonLogoffFrm.toDate.value = "";
	}		*/
	else
	{
		document.logonLogoffFrm.submit();
		return false;
	}
}
function exportToExcel() {
	document.logonLogoffFrm.toexcel.value='yes';
//	document.logonLogoffFrm.action="LogonLogoffToExcel.htm";
	document.logonLogoffFrm.submit();
	return false;
}

</script>
<BODY onload="document.logonLogoffFrm.fromDate.focus();">
<div id="content">
<FORM name=logonLogoffFrm method=post class="form"><input
	type="hidden" name="toexcel"> <br>
<CENTER><font size="2"> <b> <fmt:message
	key="table.LogonLogoff.header" /> </b> </font></CENTER>
<br>


<!-- start of new code -->

<TABLE border="0" cellpadding="0" cellspacing="0" width="100%"
	align="center" id="normalTableNostripe"
	style="white-space: nowrap; word-spacing: normal;">
	<tr valign="top">
		<td rowspan="2" valign="center" size="5"
			style="font: 900 12px arial;left:20px;top:220px;vertical-align: middle">
		&nbsp;&nbsp;<fmt:message key="table.NonProductivity.column4" /></td>
		<td rowspan="3"><select name="department"
			style="width:250px;margin:5px 0 5px 0;font: small-caps 900 10px arial;color:#1A4A6F;"
			size="5">
			<c:forEach items="${command.cat1}" var="data" varStatus="list">
				<option value="${data.deptId}"
					<c:if test="${command.department eq data.deptId}"> <c:out value="selected"/> </c:if>>
				${data.deptId}-<c:out value="${data.department}">
				</c:out></option>
			</c:forEach>
		</select></td>
		<td border="0"
			style="font: 900 11px arial;left:20px;top:220px;vertical-align: middle">
		<fmt:message key="table.LogonLogoff.column1" /></td>
		<td style="vertical-align: middle">
		<div id="chooserSpan" class="dateChooser select-free"
			style="display: none; visibility: hidden; width: 160px;"></div>
		<input type="text" id="fromDate" name="fromDate" 
			value="${command.fromDate}"> <img src="static/images/cal.gif"
			onclick="showChooser(this, 'fromDate', 'chooserSpan', 1950, 2020, Date.patterns.USDatePattern, true);">
			</td>
		<td border="0"
			style="font: 900 11px arial;left:20px;top:220px;vertical-align: middle">
		<fmt:message key="table.LogonLogoff.column2" /></td>
		<td style="vertical-align: middle">
		<div id="chooserSpan2" class="dateChooser select-free"
			style="display: none; visibility: hidden; width: 160px;"></div>
		<input type="text" id="toDate" name="toDate" 
			value="${command.toDate}"> <img src="static/images/cal.gif"
			onclick="showChooser(this, 'toDate', 'chooserSpan2', 1950, 2020, Date.patterns.USDatePattern, true);">
		</td>

	</tr>
	<tr valign="top">

		<td
			style="font: 900 11px arial;left:20px;top:220px;vertical-align: middle">
		<fmt:message key="table.LogonLogoff.column3" /></td>
		<td style="vertical-align: middle"><input type="text"
			name="empId" value="${command.empId}"></td>

		<td style="vertical-align: middle" colspan=2><input type="button"
			value="Filter" name="LogonLogoffFilter"
			onclick="javascript:checkValues();"></td>

	</tr>

</table>
<!-- end of new code -->
<br>
<br>
<c:choose>
	<c:when test="${not empty command.empId}">
		<c:choose>

			<c:when test='${empty command.logonLogoffReport}'>
				<TABLE id="normalTableFlush4" align="center" cellSpacing="1"
					cellPadding="5" border="0">
					<tr>
						<td class="selected"><font color="#B31B00"> <b> <fmt:message
							key="error.noReports" /> </b> </font></td>
					</tr>
				</TABLE>
			</c:when>

			<c:otherwise>

				<CENTER>
				<div class="tableContainer"><!--  <div style="width: 978px; height: 380px; overflow: scroll; background-color: #FFFFF;"> -->
				<TABLE id="normalTableNostripe" align="center" cellSpacing="0"
					cellPadding="0" border="0"
					style="white-space: nowrap; word-spacing: normal;">
					<thead class="fixedHeader">
						<tr class="selected" height="15px">
							<th class="tableTitle" width="30"><fmt:message
								key="table.log.column1" /></th>
							<th class="tableTitle" width="30"><fmt:message
								key="table.log.column2" /></th>
							<th class="tableTitle" width="30"><fmt:message
								key="table.log.column3" /></th>
							<th class="tableTitle" width="90"><fmt:message
								key="table.log.column4" /></th>
							<th class="tableTitle" width="90"><fmt:message
								key="table.log.column5" /></th>
							<th class="tableTitle" width="80"><fmt:message
								key="table.log.column6" /></th>
							<th class="tableTitle" width="30"><fmt:message
								key="table.log.column7" /></th>
							<th class="tableTitle" width="90"><fmt:message
								key="table.log.column8" /></th>
							<th class="tableTitle" width="90"><fmt:message
								key="table.log.column9" /></th>
							<th class="tableTitle" width="80"><fmt:message
								key="table.log.column10" /></th>
							<th class="tableTitle" width="80"><fmt:message
								key="table.log.column11" /></th>
							<th class="tableTitle" width="80"><fmt:message
								key="table.log.column12" /></th>
						</tr>
					</thead>
					<tbody class="scrollContent">
						<c:forEach items="${command.logonLogoffReport}" var="data"
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
								<c:when test='${empty data.empId}'>
									<td class="tableContent" width="30">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent" width="30"
										style="white-space: nowrap; word-spacing: normal;"><c:out
										value="${data.empId}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.empName}'>
									<td class="tableContent" width="30">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent" width="30"
										style="white-space: nowrap; word-spacing: normal;"><c:out
										value="${data.empName}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.department}'>
									<td class="tableContent" width="30">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent" width="30"
										style="white-space: nowrap; word-spacing: normal;"><c:out
										value="${data.department}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.punchIn}'>
									<td class="tableContent" width="90">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent" width="90"
										style="white-space: nowrap; word-spacing: normal;"><c:out
										value="${data.punchIn}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.punchOut}'>
									<td class="tableContent" width="90">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent" width="90"
										style="white-space: nowrap; word-spacing: normal;"><c:out
										value="${data.punchOut}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.shiftTime}'>
									<td class="tableContent" width="80">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent" width="80"
										style="white-space: nowrap; word-spacing: normal;"><c:out
										value="${data.shiftTime}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.moNumber}'>
									<td class="tableContent" width="30">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent" width="30"
										style="white-space: nowrap; word-spacing: normal;"><c:out
										value="${data.moNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.lineNumber}'>
									<td class="tableContent" width="90">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent" width="90"
										style="white-space: nowrap; word-spacing: normal;"><c:out
										value="${data.lineNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.sequence}'>
									<td class="tableContent" width="90">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent" width="90"
										style="white-space: nowrap; word-spacing: normal;"><c:out
										value="${data.sequence}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.activity}'>
									<td class="tableContent" width="80">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent" width="80"
										style="white-space: nowrap; word-spacing: normal;"><c:out
										value="${data.activity}"></c:out></td>
								</c:otherwise>
							</c:choose>


							<c:choose>
								<c:when test='${empty data.logon}'>
									<td class="tableContent" width="80">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent" width="80"
										style="white-space: nowrap; word-spacing: normal;"><c:out
										value="${data.logon}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.logoff}'>
									<td class="tableContent" width="80">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent" width="80"
										style="white-space: nowrap; word-spacing: normal;"><c:out
										value="${data.logoff}"></c:out></td>
								</c:otherwise>
							</c:choose>
							</tr>
						</c:forEach>
					</tbody>
				</TABLE>
			</c:otherwise>
		</c:choose>
	</c:when>

</c:choose>
</div>


<c:choose>
	<c:when test='${empty command.logonLogoffReport}'>
	</c:when>
	<c:otherwise>
		<div align="right"><b> <a href="#" name="excel"
			onclick="javascript:exportToExcel();" target="_self"> Export to
		Excel </a> </b></div>
	</c:otherwise>
</c:choose>
</FORM>