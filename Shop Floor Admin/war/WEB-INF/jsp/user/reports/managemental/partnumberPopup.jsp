<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="static/js/calendar3.js"></script>			
<link rel="stylesheet" href="static/css/common.css" type="text/css" media="all" /> 
<link rel="stylesheet" href="static/css/scroll.css" type="text/css" media="all" /> 
<BODY>
<div id="content">

<script type="text/javascript">
function exportValue(currentVal) {
	window.opener.factoryForm.partNumber.value=currentVal
	window.close();
}
</script> 

<FORM name=producationPopupFrm method=post class="form">
	
	<br> <br> 
	<CENTER>
		<font size="2"> <b> Part Number Search </b> </font>
	</CENTER>  

<c:choose>

<c:when test='${empty command.matchingPartNumbers}'>
<TABLE id="normalTableFlush4" align="center" cellSpacing="1" cellPadding="5" border="0">
</TABLE>
</c:when>

<c:otherwise>

<CENTER>
<div class="tableContainer" style="height: 330px;">
	<TABLE id="normalTableNostripe" align="center" cellSpacing="0" cellPadding="0" border="0" style="white-space: nowrap; word-spacing: normal;">
		<thead class="fixedHeader">
			<tr class="selected" height="15px">
				<th class="tableTitle" width="30"> Part Number </th>
				<th class="tableTitle" width="30"> Part Description </th>
			</tr>
		</thead>
		<tbody class="scrollContent">
				<c:forEach items="${command.matchingPartNumbers}" var="data" varStatus="list">
					<c:choose>
						<c:when test='${(list.index%2) == 0}'>
							<tr class="normalRow">
						</c:when>
						<c:otherwise>
							<tr class="alternateRow">
						</c:otherwise>
					</c:choose>
					<c:choose>					
						<c:when test='${empty data.partNumber}'>
							<td class="tableContent" width="20" > &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="20" style="white-space: nowrap; word-spacing: normal;"><a href="#" onclick="javascript:exportValue('${data.partNumber}');" target="_self"> <c:out value="${data.partNumber}"></c:out> </a></td>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test='${empty data.partDescription}'>
							<td class="tableContent" width="50"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="50" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.partDescription}"></c:out> </td>
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