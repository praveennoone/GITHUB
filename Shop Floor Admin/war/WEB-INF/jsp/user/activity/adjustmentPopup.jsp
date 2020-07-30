
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="static/js/calendar3.js"></script>			

<script type="text/javascript">
<!--
function updateRecords() {
	document.activityAdjustmentFrm.submit();
	opener.document.location.reload();
	window.close();
}
//-->
</script>
<BODY onload = "document.activityAdjustmentFrm.brkStart.focus();">
<div id="content">
	<FORM name=activityAdjustmentFrm method=post class="form">
	
	<br> <br> 
	<CENTER>
		<font size="2"> <b> <fmt:message key="table.activityAdjustment.header"/> </b> </font>
	</CENTER>

<c:choose>

<c:when test='${empty command.activityAdjustment}'>
<TABLE id="normalTableFlush4" align="center" cellSpacing="1" cellPadding="5" border="0">
</TABLE>
</c:when>

<c:otherwise>

<CENTER>
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
							<td class="tableContent" width="100"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.punchIn}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.punchOut}'>
							<td class="tableContent" width="100"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.punchOut}"></c:out> </td>
						</c:otherwise>				
					</c:choose>

					<c:choose>
						<c:when test='${empty data.logon}'>
							<td class="tableContent" width="100"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.logon}"></c:out> </td>
						</c:otherwise>				
					</c:choose>					

					<c:choose>
						<c:when test='${empty data.logoff}'>
							<td class="tableContent" width="100"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.logoff}"></c:out> </td>
						</c:otherwise>				
					</c:choose>					
					
					<c:choose>
						<c:when test='${empty data.brkStart}'>
							<td class="tableContent" width="100"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100" style="white-space: nowrap; word-spacing: normal;"> 
							
							<input type="text" name="brkStart" readonly="readonly" value="<c:out value="${data.brkStart}"></c:out>"> 
							<A onmouseover="window.status='Date Picker';return true;" onmouseout="window.status='';return true;" href="javascript:cal1.popup();" >
							<IMG alt="Calendar"  src="static/images/cal.gif"  border=0></A>	</td>
							
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.brkEnd}'>
							<td class="tableContent" width="100"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.brkEnd}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
				</tr>
			</c:forEach>			
</tbody>
</TABLE>
<script language="JavaScript">
var cal1 = new calendar3(document.forms['activityAdjustmentFrm'].elements['brkStart']);
cal1.year_scroll = true;
cal1.time_comp = true;
cal1.left = 200;
cal1.top = 100;
</script>
</c:otherwise>
</c:choose>
<center>
	<input type="button" name="adj" value="Update and Close" onclick="javascript:updateRecords();">
</center>
</FORM>
</div>