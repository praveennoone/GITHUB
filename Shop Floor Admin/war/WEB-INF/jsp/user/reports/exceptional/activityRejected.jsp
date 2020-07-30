<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="content">
<script type="text/javascript">
function checkValues(){
	document.activityRejectedFrm.toexcel.value='no';	
	if(document.activityRejectedFrm.moNumber.value == ""){
		alert("Enter MO Number");
	} else if (document.activityRejectedFrm.empId.value == "") {
		alert("Enter Employee ID");
	}
	{
		document.activityRejectedFrm.submit();
		return false;
	}
}

function upperCase(x)
{
var y=document.getElementById(x).value
document.getElementById(x).value=y.toUpperCase()
}

function exportToExcel() {
	document.activityRejectedFrm.toexcel.value='yes';
//	document.activityRejectedFrm.action="ActivityRejectedToExcel.htm";
	document.activityRejectedFrm.submit();
	return false;
}
</script>
<BODY onload = "document.activityRejectedFrm.moNumber.focus();">
<div id="content"> 
	<FORM name=activityRejectedFrm method=post class="form">
	<input type="hidden" name="toexcel">
	<br>
	<CENTER>
		<font size="2"> <b> <fmt:message key="table.ActivityRejected.header"/> </b> </font>
	</CENTER>
	<br> 
	<TABLE border="0" cellpadding="0" cellspacing="0" width="75%" align="center" id="normalTableNostripe">
				<tr valign="top">   
					<td> 
						<b><fmt:message key="table.ActivityRejected.column1"/></b>
						<input type="text" id="moNumber"  name="moNumber" value="${command.moNumber}" onkeyup="upperCase(this.id)" > 
					</td>					
					<td> 
						<b><fmt:message key="table.ActivityRejected.column2"/></b>
						<input type="text" name="empId" value="${command.empId}"> 
    				</td>
    				<td>
						 <b> <fmt:message key="table.ActivityRejected.column3"/> </b>
							<select name="department">
								<c:forEach items="${command.cat1}" var="data" varStatus="list">
								<option value="${data.deptId}" <c:if test="${command.department eq data.deptId}"> <c:out value="selected"/> </c:if> > <c:out value="${data.department}"> </c:out> </option>
								</c:forEach>
							</select>			
					</td>
					<td> <input type="button" value="Filter" name="ActivityRejectedFilter" onclick="javascript:checkValues();"> </td>
				</tr>
		</TABLE>
<br> <br><br> <br>
<c:choose>
<c:when test="${not empty command.moNumber}">


<c:choose>

<c:when test='${empty command.activityRejectedReport}'>
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
						<th class="tableTitle" width="30"> <fmt:message key="table.rej.column1"/> </th>
						<th class="tableTitle" width="30"> <fmt:message key="table.rej.column2"/> </th>
	 					<th class="tableTitle" width="30"> <fmt:message key="table.rej.column3"/> </th>				
						<th class="tableTitle" width="20"> <fmt:message key="table.rej.column4"/> </th> 
						<th class="tableTitle" width="20"> <fmt:message key="table.rej.column5"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.rej.column6"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.rej.column7"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.rej.column8"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.rej.column9"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.rej.column10"/> </th>
					</tr>
		</thead>
		<tbody class="scrollContent">
					<c:forEach items="${command.activityRejectedReport}" var="data" varStatus="list">
					
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
						<c:when test='${empty data.department}'>
							<td class="tableContent" width="30"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.department}"></c:out> </td>
						</c:otherwise>				
					</c:choose>

 					<c:choose>
						<c:when test='${empty data.shiftTime}'>
							<td class="tableContent" width="30"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.shiftTime}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.moNumber}'>
							<td class="tableContent" width="30"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.moNumber}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.lineNumber}'>
							<td class="tableContent" width="30"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.lineNumber}"></c:out> </td>
						</c:otherwise>				
					</c:choose>

					<c:choose>
						<c:when test='${empty data.sequence}'>
							<td class="tableContent" width="80"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="80" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.sequence}"></c:out> </td>
						</c:otherwise>				
					</c:choose>

					<c:choose>
						<c:when test='${empty data.activity}'>
							<td class="tableContent" width="80"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="80" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.activity}"></c:out> </td>
						</c:otherwise>				
					</c:choose>					

					<c:choose>
						<c:when test='${empty data.rejectedDate}'>
							<td class="tableContent" width="80"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="80" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.rejectedDate}"></c:out> </td>
						</c:otherwise>				
					</c:choose>					
					
					<c:choose>
						<c:when test='${empty data.rejectedBy}'>
							<td class="tableContent" width="80"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="80" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.rejectedBy}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.rejectedComments}'>
							<td class="tableContent" width="80"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="80" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.rejectedComments}"></c:out> </td>
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
<c:when test='${empty command.activityRejectedReport}'>
</c:when>
<c:otherwise>  

<div align="right">
 	<b> <a href="#" name="excel" onclick="javascript:exportToExcel();" target="_self"> Export to Excel </a> </b> 
</div>
</c:otherwise>
</c:choose>
</FORM>

