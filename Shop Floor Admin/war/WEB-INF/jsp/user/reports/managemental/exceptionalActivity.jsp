<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="static/js/calendar3.js"></script>			
<link rel="stylesheet" href="static/css/common.css" type="text/css" media="all" /> 
<link rel="stylesheet" href="static/css/scroll.css" type="text/css" media="all" /> 
<BODY>
<div id="content">
	<FORM name=producationPopupFrm method=post class="form">
	
	<br> <br> 
	<CENTER>
		<font size="2"> <b> <fmt:message key="table.exceptionalActivity.header"/> </b> </font>
	</CENTER>  

<c:choose>

<c:when test='${empty command.exceptionalActivityReport}'>
<TABLE id="normalTableFlush4" align="center" cellSpacing="1" cellPadding="5" border="0">
<CENTER>
		<font size="2"> <c:out value='No Records Found'></c:out> </font>		
		
	</CENTER>
	<br> <br> 
</TABLE>
</c:when>

<c:otherwise>

<CENTER>
<div class="tableContainerPop">
	<TABLE id="normalTableNostripe" align="center" cellSpacing="0" cellPadding="0" border="0" style="white-space: nowrap; word-spacing: normal;">
			<thead class="fixedHeader">
					<tr class="selected" height="15px">
						<th class="tableTitle" > <fmt:message key="table.exceptionalActivity.column1"/> </th>
						<th class="tableTitle" > <fmt:message key="table.exceptionalActivity.column2"/> </th>						
						<th class="tableTitle" > <fmt:message key="table.exceptionalActivity.column10"/> </th>						
						<th class="tableTitle" > <fmt:message key="table.exceptionalActivity.column3"/> </th>
						<th class="tableTitle" > <fmt:message key="table.exceptionalActivity.column4"/> </th>
						<th class="tableTitle" > <fmt:message key="table.exceptionalActivity.column5"/> </th>
						<th class="tableTitle" > <fmt:message key="table.exceptionalActivity.column6"/> </th>
						<th class="tableTitle" > <fmt:message key="table.exceptionalActivity.column7"/> </th>
						<th class="tableTitle" > <fmt:message key="table.exceptionalActivity.column8"/> </th>
						<th class="tableTitle" > <fmt:message key="table.exceptionalActivity.column9"/> </th>
					</tr>
		</thead>
		<tbody class="scrollContent">
					<c:forEach items="${command.exceptionalActivityReport}" var="data" varStatus="list">
					
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
							<td class="tableContent"> &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.empName}"></c:out> </td>
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test='${empty data.moNumber}'>
							<td class="tableContent"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.moNumber}"></c:out> </td>
						</c:otherwise>				
					</c:choose>


					<c:choose>
						<c:when test='${empty data.seqn}'>
							<td class="tableContent"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.seqn}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.partid}'>
							<td class="tableContent"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.partid}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.assetId}'>
							<td class="tableContent"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" style="white-space: nowrap; word-spacing: normal; width: 250px"> <c:out value="${data.assetId}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					<c:choose>					
						<c:when test='${empty data.assetName}'>
							<td class="tableContent"> &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" style="white-space: normal; word-spacing: normal;width:500px"> <c:out value="${data.assetName}"></c:out> </td>
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test='${empty data.activity}'>
							<td class="tableContent"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.activity}"></c:out> </td>
						</c:otherwise>				
					</c:choose>		
					
					<c:choose>
						<c:when test='${empty data.hrs}'>
							<td class="tableContent"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" style="white-space: nowrap; word-spacing: normal; width: 100px"> <c:out value="${data.hrs}"></c:out> </td>
						</c:otherwise>				
					</c:choose>	
					
					<c:choose>
						<c:when test='${empty data.startTime}'>
							<td class="tableContent"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" style="white-space: nowrap; word-spacing: normal;width: 500px"> <c:out value="${data.startTime}"></c:out> </td>
						</c:otherwise>				
					</c:choose>	
					
					<c:choose>
						<c:when test='${empty data.endTime}'>
							<td class="tableContent"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<!-- Ramanan.M - 30th Aug : Added the choice to avoid null for the End Date -->						
							<c:choose>
								<c:when test='${data.endTime == "null"}'>
									<td class="tableContent"> &nbsp; </td>
								</c:when>
								<c:otherwise>
									<td class="tableContent" style="white-space: nowrap; word-spacing: normal;width: 500px"> <c:out value="${data.endTime}"></c:out> </td>
								</c:otherwise>				
							</c:choose>							
						</c:otherwise>				
					</c:choose>				
					
				</tr>
			</c:forEach>			
</tbody>
</TABLE>
</div>
</c:otherwise>
</c:choose>
<center>
	<input type="button" name="close" value="Close" onclick="window.close();">
</center>
</FORM>
</div>
<Script>
window.scrollBy (0, 0);
</Script>