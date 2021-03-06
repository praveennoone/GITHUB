<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="content">

<script type="text/javascript">
	
	function checkValues(){
		document.activityLogFrm.toexcel.value='no';	
		document.activityLogFrm.submit();
		return false;
	}
	
	function exportToExcel() {
		document.activityLogFrm.toexcel.value='yes';
		document.activityLogFrm.submit();
		return false;
	}
	
	function fnShift(shiftTime){
		var fDateTime = document.activityLogFrm.fromDate.value;
		var tDateTime = document.activityLogFrm.toDate.value;
		var shiftdate = shiftTime;	
	 	var fDateTime_array=fDateTime.split(" ");
		var tDateTime_array=tDateTime.split(" ");
		var shiftDateTime_array=shiftdate.split("-");
		sfdTime = shiftDateTime_array[0];
		stdTime = shiftDateTime_array[1];
	
		fDateTime = fDateTime_array[0]+" "+shiftDateTime_array[0];
		tDateTime = tDateTime_array[0]+" "+shiftDateTime_array[1];
		document.activityLogFrm.fromDate.value = fDateTime;
		document.activityLogFrm.toDate.value = tDateTime;
	}
	
</script>

<BODY>
<div id="content"> 
	<FORM name=activityLogFrm method=post class="form">
	<input type="hidden" name="toexcel">
	<br>
	<CENTER>
		<font size="2"> <b> <fmt:message key="table.ActivityLog.header"/> </b> </font>
	</CENTER>
	<br> 
	<TABLE border="0" cellpadding="0" cellspacing="0" width="100%" align="center" id="normalTableNostripe" style="white-space: nowrap; word-spacing: normal;">
		<tr valign="top" > 
  			<td rowspan="2" valign="center" size="5" style="font: 900 11px verdana;left:20px;top:220px">
				&nbsp;<b> <fmt:message key="table.ActivityLog.column1"/> </b>
			</td>
			<td rowspan="2">	
				<select name="department" style="width:250px;margin:5px 0 5px 0;font: 900 10px arial;color:#1A4A6F;" size="6">
					<c:forEach items="${command.cat1}" var="data" varStatus="list">
						<option value="${data.deptId}" <c:if test="${command.department eq data.deptId}"> <c:out value="selected"/> </c:if> > <c:out value="${data.deptId} - ${data.cat} --> ${data.department}"/> </option>
					</c:forEach>
				</select>			
			</td>   
			<td>
				<b> <fmt:message key="table.ActivityLog.column2"/> </b>
			</td>
			<td>	
				<div id="chooserSpan" class="dateChooser select-free" style="display: none; visibility: hidden; width: 160px;"></div>
				<input type="text" id="fromDate" name="fromDate" value="${command.fromDate}"> 
				<img src="static/images/cal.gif" onclick="showChooser(this, 'fromDate', 'chooserSpan', 1950, 2020, Date.patterns.USDatePattern, true);">
			</td>
			<td>
				<b> <fmt:message key="table.ActivityLog.column3"/> </b>
			</td>
			<td>	
				<div id="chooserSpan" class="dateChooser select-free" style="display: none; visibility: hidden; width: 160px;"></div>
				<input type="text" id="toDate" name="toDate" value="${command.toDate}"> 
				<img src="static/images/cal.gif" onclick="showChooser(this, 'toDate', 'chooserSpan', 1950, 2020, Date.patterns.USDatePattern, true);">
			</td>
		</tr>
		<tr>
			<td border="0" style="font: 900 11px verdana;left:20px;top:220px">
				<fmt:message key="table.ActivityLog.column4"/>
			</td>
			<td>	
				<c:forEach items="${command.arrShiftTime}" var="data" varStatus="list">										
					<c:if test="${data.shift =='A'}">       
						<input type="radio" name="shift" value="${data.shiftStartTime}-${data.shiftEndTime}" onClick="fnShift('${data.shiftStartTime}-${data.shiftEndTime}');"> A &nbsp;&nbsp;&nbsp;
					</c:if>
					<c:if test="${data.shift =='B'}"> 
						<input type="radio" name="shift" value="${data.shiftStartTime}-${data.shiftEndTime}" onClick="fnShift('${data.shiftStartTime}-${data.shiftEndTime}');"> B &nbsp;&nbsp;&nbsp;
					</c:if>
					<c:if test="${data.shift =='C'}"> 
						<input type="radio" name="shift" value="${data.shiftStartTime}-${data.shiftEndTime}" onClick="fnShift('${data.shiftStartTime}-${data.shiftEndTime}');"> C &nbsp;&nbsp;&nbsp;
					</c:if>
					<c:if test="${data.shift =='D'}"> 
						<input type="radio" name="shift" value="${data.shiftStartTime}-${data.shiftEndTime}" onClick="fnShift('${data.shiftStartTime}-${data.shiftEndTime}');"> D 
					</c:if>							   
				</c:forEach> 
			</td>		
			<td colspan="2" style="vertical-align: middle; text-align: center"> 
				<input type="button" value="Filter" name="ActivityLogFilter"  size="10" onclick="javascript:checkValues();"> 
			</td>
		</tr>
	</TABLE>
<br> <br>
<c:choose>
<c:when test="${not empty command.department}">
<c:choose>
<c:when test='${empty command.activityLog}'>
<TABLE id="normalTableFlush4" align="center" cellSpacing="1" cellPadding="5" border="0">
<tr>
	<td class="selected">
		<font color="#B31B00"> <b> <fmt:message key="error.noReports"/> </b>
		</font>
	</td>
</tr>
</TABLE>
</c:when>

<c:otherwise>

<CENTER>
 <div class="tableContainer"> 
<!--  <div style="width: 978px; height: 380px; overflow: scroll; background-color: #FFFFF;"> -->
			<TABLE id="normalTableNostripe" align="center" cellSpacing="0" cellPadding="0" border="0" style="white-space: nowrap; word-spacing: normal;">
			<thead class="fixedHeader">
					<tr class="selected" height="15px">
						<th class="tableTitle" width="30"> <fmt:message key="table.activityLog.column1"/> </th>
						<th class="tableTitle" width="30"> <fmt:message key="table.activityLog.column2"/> </th>
	 					<th class="tableTitle" width="30"> <fmt:message key="table.activityLog.column3"/> </th>				
						<th class="tableTitle" width="20"> <fmt:message key="table.activityLog.column4"/> </th> 
						<th class="tableTitle" width="20"> <fmt:message key="table.activityLog.column5"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.activityLog.column6"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.activityLog.column7"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.activityLog.column8"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.activityLog.column9"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.activityLog.column10"/> </th>
					</tr>
		</thead>
		<tbody class="scrollContent">
					<c:forEach items="${command.activityLog}" var="data" varStatus="list">
					
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
							<td class="tableContent" width="30"> &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.empId}"></c:out> </td>
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test='${empty data.empName}'>
							<td class="tableContent" width="30"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.empName}"></c:out> </td>
						</c:otherwise>				
					</c:choose>

 					<c:choose>
						<c:when test='${empty data.asset}'>
							<td class="tableContent" width="30"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.asset}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.assetDesc}'>
							<td class="tableContent" width="30"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.assetDesc}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.activity}'>
							<td class="tableContent" width="30"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.activity}"></c:out> </td>
						</c:otherwise>				
					</c:choose>

					<c:choose>
						<c:when test='${empty data.partid}'>
							<td class="tableContent" width="80"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="80" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.partid}"></c:out> </td>
						</c:otherwise>				
					</c:choose>

					<c:choose>
						<c:when test='${empty data.partDesc}'>
							<td class="tableContent" width="80"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="80" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.partDesc}"></c:out> </td>
						</c:otherwise>				
					</c:choose>					

					<c:choose>
						<c:when test='${empty data.logon}'>
							<td class="tableContent" width="80"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="80" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.logon}"></c:out> </td>
						</c:otherwise>				
					</c:choose>					
					
					<c:choose>
						<c:when test='${empty data.logoff}'>
							<td class="tableContent" width="80"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="80" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.logoff}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.completedQty}'>
							<td class="tableContent" width="80"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="80" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.completedQty}"></c:out> </td>
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
<c:when test='${empty command.activityLog}'>
</c:when>
<c:otherwise>  

<div align="right">
 	<b> <a href="#" name="excel" onclick="javascript:exportToExcel();" target="_self"> Export to Excel </a> </b> 
</div>
</c:otherwise>
</c:choose>
</FORM>
