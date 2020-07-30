
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
		<font size="2"> <b> <fmt:message key="table.Production.header"/> </b> </font>
	</CENTER>  

<c:choose>

<c:when test='${empty command.producationPopUpReport}'>
<TABLE id="normalTableFlush4" align="center" cellSpacing="1" cellPadding="5" border="0">
</TABLE>
</c:when>

<c:otherwise>

<CENTER>
	<TABLE id="normalTableNostripe" align="center" cellSpacing="0" cellPadding="0" border="0" style="white-space: nowrap; word-spacing: normal;">
			<thead class="fixedHeader">
					<tr class="selected" height="15px">
						<th class="tableTitle" width="30"> <fmt:message key="table.production.popup.activity"/> </th>
						<th class="tableTitle" width="30"> <fmt:message key="table.production.popup.brkStart"/> </th>
						<th class="tableTitle" width="30"> <fmt:message key="table.production.popup.brkEnd"/> </th>				
						<th class="tableTitle" width="20"> <fmt:message key="table.production.popup.desc"/> </th>
					</tr>
		</thead>
		<tbody class="scrollContent">
					<c:forEach items="${command.producationPopUpReport}" var="data" varStatus="list">
					
					<c:choose>
						<c:when test='${(list.index%2) == 0}'>
							<tr class="normalRow">
						</c:when>
						<c:otherwise>
							<tr class="alternateRow">
						</c:otherwise>
					</c:choose>

					<c:choose>					
						<c:when test='${empty data.activity}'>
							<td class="tableContent" width="30"> &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.activity}"></c:out> </td>
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test='${empty data.breakStart}'>
							<td class="tableContent" width="30"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.breakStart}"></c:out> </td>
						</c:otherwise>				
					</c:choose>

					<c:choose>
						<c:when test='${empty data.breakEnd}'>
							<td class="tableContent" width="100"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.breakEnd}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.desc}'>
							<td class="tableContent" width="100"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.desc}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
				</tr>
			</c:forEach>			
</tbody>
</TABLE>
</c:otherwise>
</c:choose>
<center>
	<input type="button" name="close" value="Close" onclick="window.close();">
</center>
</FORM>
</div>