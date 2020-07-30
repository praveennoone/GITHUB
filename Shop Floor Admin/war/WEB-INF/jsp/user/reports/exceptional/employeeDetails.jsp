<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<script type="text/javascript">
function checkValues()
{
	document.employeeDetailsFrm.toexcel.value='no';
	if(document.employeeDetailsFrm.empId.value == "")
	{
		alert("Enter Employee ID")
	}else

		document.employeeDetailsFrm.submit();
	return false;
}
 function exportToExcel() {
	document.employeeDetailsFrm.toexcel.value='yes';
//	document.employeeDetailsFrm.action="employeeDetailsFrm.htm";
	document.employeeDetailsFrm.submit();
	return false;
}
</script>
<BODY onload = "document.employeeDetailsFrm.empId.focus();">
<div id="content">
<FORM name=employeeDetailsFrm method=post class="form">
 	<input type="hidden" name="toexcel">
 	<br>
	<CENTER>
		<font size="2"> <b> <fmt:message key="table.EmployeeDetails.header"/> </b> </font>
	</CENTER>
	<br> 
	<TABLE border="0" cellpadding="0" cellspacing="0" width="50%" align="center" id="normalTableNostripe">
				<tr valign="top">  
					<td> 
						<fmt:message key="table.EmployeeDetails.column1"/>
						<!-- <input type="text" name="empId" value=""> -->
						<input type="text" name="empId"  value="${command.empId}"> 
					</td>	 
					<td>
						 <b> <fmt:message key="table.EmployeeDetails.column2"/> </b>
							<select name="deptId" id="deptId">
								<c:forEach items="${command.cat1}" var="data" varStatus="list">
								<option value="${data.deptId}" <c:if test="${command.deptId eq data.deptId}"> <c:out value="selected"/> </c:if> > ${data.deptId}-<c:out value="${data.department}"> </c:out> </option>
								</c:forEach>
							</select>			
					</td>
					<td> <input type="button" value="Filter" name="EmployeeDetailsFilter" onclick="javascript:checkValues();"> </td>
				</tr>
		</TABLE>

<br> <br>
<br> <br>
<c:choose>
<c:when test="${not empty command.empId}">
<c:choose>
<c:when test='${empty command.employeeDetailsReport}'>
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
						<th class="tableTitle" width="30"> <fmt:message key="table.emp.column1"/> </th>
						<th class="tableTitle" width="30"> <fmt:message key="table.emp.column2"/> </th>
						<th class="tableTitle" width="30"> <fmt:message key="table.emp.column3"/> </th>				
						<th class="tableTitle" width="90"> <fmt:message key="table.emp.column4"/> </th>
						<th class="tableTitle" width="90"> <fmt:message key="table.emp.column5"/> </th>
						<th class="tableTitle" width="90"> <fmt:message key="table.emp.column6"/> </th>
					</tr>
		</thead>
		<tbody class="scrollContent">
					<c:forEach items="${command.employeeDetailsReport}" var="data" varStatus="list">
					
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
						<c:when test='${empty data.department}'>
							<td class="tableContent" width="30"> &nbsp; </td>				
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.department}"></c:out> </td>				
						</c:otherwise>				
					</c:choose>
					
 					<c:choose>
						<c:when test='${empty data.punchIn}'>
							<td class="tableContent" width="90"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="90" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.punchIn}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.punchOut}'>
							<td class="tableContent" width="90"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="90" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.punchOut}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.shiftTime}'>
							<td class="tableContent" width="90"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="90" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.shiftTime}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
				</tr>
			</c:forEach>			
</tbody>
</TABLE>
</div>
<div align="right">
 	<b> <a href="#" name="excel" onclick="javascript:exportToExcel();" target="_self"> Export to Excel </a> </b> 
</div>
</CENTER>
</c:otherwise>
</c:choose>
</c:when>
</c:choose>
</FORM>