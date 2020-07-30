<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<title>Shop Floor - Asset Usage Report</title>
</head>

<script src="static/js/calendar3.js"></script>			
<link rel="stylesheet" href="static/css/common.css" type="text/css" media="all" /> 
<link rel="stylesheet" href="static/css/scroll.css" type="text/css" media="all" /> 
<BODY>
<div id="content">
	<FORM name=producationPopupFrm method=post class="form">
	
	<br> <br> 
	<CENTER>
		<font size="2"> <b> <fmt:message key="table.AssetUsage.header"/> </b> </font>
	</CENTER> 	
	<br> <br> 
		 
	 <CENTER>
		<font size="2"> <fmt:message key="table.ast.column2"/> :<c:out value='${command.asset} - ${command.assetDesc}'></c:out> </font>		
		
	</CENTER>
	<br>

<c:choose>

<c:when test='${empty command.assetUsageReport}'>
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
  					    <th class="tableTitle" width="20"> <fmt:message key="table.ast.column14"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.ast.column4"/> </th>
						<th class="tableTitle" width="30"> <fmt:message key="table.ast.column5"/> </th>
						<th class="tableTitle" width="30"> <fmt:message key="table.ast.column6"/> </th>
						<th class="tableTitle" width="30"> <fmt:message key="table.ast.column7"/> </th>
						<th class="tableTitle" width="30"> <fmt:message key="table.ast.column8"/> </th>
						<th class="tableTitle" width="30"> <fmt:message key="table.ast.column9"/> </th>
						<th class="tableTitle" width="30"> <fmt:message key="table.ast.column10"/> </th>
						<th class="tableTitle" width="30"> <fmt:message key="table.ast.column11"/> </th>
						<th class="tableTitle" width="30"> <fmt:message key="table.ast.column12"/> </th>
						<th class="tableTitle" width="30"> <fmt:message key="table.ast.column13"/> </th>
					</tr>
		</thead>
		<tbody class="scrollContent">
					<c:forEach items="${command.assetUsageReport}" var="data" varStatus="list">
					
					<c:choose>
						<c:when test='${(list.index%2) == 0}'>
							<tr class="normalRow">
						</c:when>
						<c:otherwise>
							<tr class="alternateRow">
						</c:otherwise>
					</c:choose>					
					<!-- 
					<c:choose>					
						<c:when test='${empty data.assetId}'>
							<td class="tableContent" width="30"> &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.assetId}"></c:out> </td>
						</c:otherwise>
					</c:choose>
					-->
					<c:choose>
						<c:when test='${empty data.empId}'>
							<td class="tableContent" width="30" > &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" > <c:out value="${data.empId}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					 
					<c:choose>
						<c:when test='${empty data.moNumber}'>
							<td class="tableContent" width="100"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.moNumber}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.partid}'>
							<td class="tableContent" width="100"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100" style="width:1160px;white-space: nowrap; word-spacing: normal;"> <c:out value="${data.partid}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.partDesc}'>
							<td class="tableContent" width="100"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100" style="width:2160px;white-space: nowrap; word-spacing: normal;"> <c:out value="${data.partDesc}"></c:out> </td>
						</c:otherwise>				
					</c:choose>						

					<c:choose>
						<c:when test='${empty data.activity}'>
							<td class="tableContent" width="100"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.activity}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.realHours}'>
							<td class="tableContent" width="100"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100" style="font-weight: bold;white-space: nowrap; word-spacing: normal;"> 
							<A href="javascript:popupModalWithParams('logonLogoffDetails.htm?dept=${data.department}&moNumber=${data.moNumber}
							&assetID=${data.assetId}&punchIn=${command.fromDate}&punchOut=${command.toDate}','dialogWidth:900px; dialogHeight:410px; center:yes')"  
							style="color: maroon"><c:out value="${data.realHours}"></c:out></A> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.quantity}'>
							<td class="tableContent" width="100"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.quantity}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.targetQty}'>
							<td class="tableContent" width="100"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.targetQty}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.actualPPH}'>
							<td class="tableContent" width="100"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.actualPPH}"></c:out> </td>
						</c:otherwise>				
					</c:choose>	
					
					<c:choose>
						<c:when test='${empty data.targetPPH}'>
							<td class="tableContent" width="100"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.targetPPH}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.efficiency}'>
							<td class="tableContent" width="100"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.efficiency}"></c:out> </td>
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