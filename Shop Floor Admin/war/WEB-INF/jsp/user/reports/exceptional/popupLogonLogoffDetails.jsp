<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<title>Shop Floor - Actual Hrs Report</title>
</head>
	<FORM name=producationPopupFrm method=post class="form">	
	<br> <br> 
	<CENTER>
		<font size="2"> <b> <fmt:message key="table.Production.header"/> </b> </font>
	</CENTER>  

<c:choose>
<c:when test='${empty command.logonLogoffDetailsReport}'>
<TABLE id="normalTableFlush4" align="center" cellSpacing="1" cellPadding="5" border="0">
</TABLE>
</c:when>
<c:otherwise>

<CENTER>
 <div class="tableContainer"> 
	<TABLE id="normalTableNostripe" align="center" cellSpacing="0" cellPadding="0" border="0" style="white-space: nowrap; word-spacing: normal;">
			<thead class="fixedHeader">
					<tr class="selected" height="15px">
						<th class="tableTitle" > Emp # </th>
						<th class="tableTitle" > Employee Name </th>
						<th class="tableTitle" > MONumber </th>
						<th class="tableTitle" > Seq No.</th>
						<th class="tableTitle" > Asset No. </th>				
						<th class="tableTitle" > Asset Description </th>
						<th class="tableTitle" > Item No. </th>
						<th class="tableTitle" > Activity </th>
						<th class="tableTitle" > Qty Completed </th>
						<th class="tableTitle" > Logon Date/Time</th>
						<th class="tableTitle" > Logoff Date/Time</th>
					</tr>
		</thead>
		<tbody class="scrollContent">
					<c:forEach items="${command.logonLogoffDetailsReport}" var="data" varStatus="list">
					
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
							<td class="tableContent"  > &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent"   style="white-space: nowrap; word-spacing: normal;"><c:out value="${data.empId}"/></td>
						</c:otherwise>
					</c:choose>
					
					<c:choose>					
						<c:when test='${empty data.empName}'>
							<td class="tableContent"  > &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent"   style="white-space: nowrap; word-spacing: normal;"><c:out value="${data.empName}"/></td>
						</c:otherwise>
					</c:choose>


					<c:choose>
						<c:when test='${empty data.moNumber}'>
							<td class="tableContent"  > &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent"   style="white-space: nowrap; word-spacing: normal;"><c:out value="${data.moNumber}"/></td>
						</c:otherwise>				
					</c:choose>

					<c:choose>
						<c:when test='${empty data.sequence}'>
							<td class="tableContent" width="90"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="90" style="white-space: nowrap; word-spacing: normal;"><c:out value="${data.sequence}"/></td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.asset}'>
							<td class="tableContent" > &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" style="white-space: nowrap; word-spacing: normal;"><c:out value="${data.asset}"/></td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>					
						<c:when test='${empty data.assetDescription}'>
							<td class="tableContent" width="100" > &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100"  style="white-space: nowrap; word-spacing: normal;"><c:out value="${data.assetDescription}"/></td>
						</c:otherwise>
					</c:choose>
					
					<c:choose>					
						<c:when test='${empty data.itemNumber}'>
							<td class="tableContent"  > &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent"   style="white-space: nowrap; word-spacing: normal;"><c:out value="${data.itemNumber}"/></td>
						</c:otherwise>
					</c:choose>
					
					<c:choose>					
						<c:when test='${empty data.activity}'>
							<td class="tableContent" width="120" > &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="120"  style="white-space: nowrap; word-spacing: normal;"><c:out value="${data.activity}"/></td>
						</c:otherwise>
					</c:choose>
					
					<c:choose>					
						<c:when test='${empty data.qtyComleted}'>
							<td class="tableContent"  > &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent"   style="white-space: nowrap; word-spacing: normal;"><c:out value="${data.qtyComleted}"/></td>
						</c:otherwise>
					</c:choose>
					
					<c:choose>					
						<c:when test='${empty data.logon}'>
							<td class="tableContent"  > &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent"   style="white-space: nowrap; word-spacing: normal;"><c:out value="${data.logon}"/></td>
						</c:otherwise>
					</c:choose>
					
					<c:choose>					
						<c:when test='${empty data.logoff}'>
							<td class="tableContent"  > &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent"   style="white-space: nowrap; word-spacing: normal;"><c:out value="${data.logoff}"/></td>
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
