<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="content">
<script type="text/javascript">
function checkValues(){
	
	if(document.activityAdjustmentFrm.moNumber.value == ""){
		alert("Enter MO Number");
	} else if(document.activityAdjustmentFrm.lineNumber.value == ""){
		alert("Enter LineNumber")
	} else if(document.activityAdjustmentFrm.sequence.value == ""){
		alert("Enter Sequence")
	}
	else
	{
		document.activityAdjustmentFrm.submit();
		return false;
	}
}

function popupWithParams(url, param, fieldId, features) {
	var paramValue = '';
	var field = document.getElementById(fieldId);
	if (field != null) {
	    paramValue = field.value;
	}
	window.open(url + '&' + param + '=' + paramValue, '', features);
}

</script>
<BODY onload = "document.activityAdjustmentFrm.moNumber.focus();">
<div id="content">
	<FORM name=activityAdjustmentFrm method=post class="form">
	<input type="hidden" name="toexcel">
	<CENTER>
		<font size="2"> <b> <fmt:message key="table.activityAdjustment.header"/> </b> </font>
	</CENTER>
	<br> 
	<TABLE border="0" cellpadding="0" cellspacing="0" width="75%" align="center" id="normalTableNostripe">
				<tr valign="top">   
					<td> 
						<fmt:message key="table.activityAdjustment.column1"/>
						<input type="text" name="moNumber" value="${command.moNumber}"> 
					</td>					
					<td> 
						<fmt:message key="table.activityAdjustment.column2"/>
						<input type="text" name="lineNumber" value="${command.lineNumber }"> 
    				</td>
					<td> 
						<fmt:message key="table.activityAdjustment.column3"/>
						<input type="text" name="sequence" value="${command.sequence}"> 
					</td>					
					<td> <input type="button" value="Filter" name="activityAdjustmentFilter" onclick="javascript:checkValues();"> </td>
				</tr>
		</TABLE>
	</FORM> 
<br> <br>

<c:choose>
<c:when test="${not empty command.moNumber}">


<c:choose>

<c:when test='${empty command.activityAdjustment}'>
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
						<th class="tableTitle" width="30"> <fmt:message key="table.actadj.column1"/> </th>
						<th class="tableTitle" width="30"> <fmt:message key="table.actadj.column2"/> </th>
						<th class="tableTitle" width="30"> <fmt:message key="table.actadj.column3"/> </th>				
						<th class="tableTitle" width="20"> <fmt:message key="table.actadj.column4"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.actadj.column5"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.actadj.column6"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.actadj.column7"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.actadj.column8"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.actadj.column9"/> </th>
					</tr>
		</thead>
		<tbody class="scrollContent">
					<c:forEach items="${command.activityAdjustment}" var="data" varStatus="list">
					
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
							<td class="tableContent" width="30"> &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.empName}"></c:out> </td>
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test='${empty data.empId}'>
							<td class="tableContent" width="30"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.empId}"></c:out> </td>
						</c:otherwise>				
					</c:choose>

					<c:choose>
						<c:when test='${empty data.punchIn}'>
							<td class="tableContent" width="30"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.punchIn}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.punchOut}'>
							<td class="tableContent" width="30"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.punchOut}"></c:out> </td>
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
						<c:when test='${empty data.brkStart}'>
							<td class="tableContent" width="80"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="80" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.brkStart}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.brkEnd}'>
							<td class="tableContent" width="80"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="80" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.brkEnd}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					<td class="tableContent" width="80" style="white-space: nowrap; word-spacing: normal;"> 
						<a href="javascript:popupWithParams('showActivity.htm?index=${list.index}','index','${list.index}','width=750,height=200')"> Edit </a>
					</td>
				</tr>
			</c:forEach>			
</tbody>
</TABLE>
</div>
</CENTER>
</c:otherwise>
</c:choose>
</c:when>
</c:choose>