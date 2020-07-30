<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
	function checkAndSubmit() {
		if(document.frmPendingOrderDetails.workCenterCode.value == "") {
			alert("Workcenter Code Cannot Be Empty");
			document.frmPendingOrderDetails.workCenterCode.focus();
		}else {
			document.frmPendingOrderDetails.toexcel.value='no';	
			document.frmPendingOrderDetails.submit();
			return false;
		}
		}
</script>

	
<div id="content">
<BODY onload="document.frmPendingOrderDetails.workCenterCode.focus();">
<div id="content"> 
	<FORM name=frmPendingOrderDetails method=post class="form">
	<input type="hidden" name="toexcel">
	<br>
	<CENTER>
		<font size="2"> <b> <fmt:message key="table.pendingOrderDetails.header"/> </b> </font>
	</CENTER>
	<br> 
	<center>
		<spring:hasBindErrors name="command">
			<c:forEach var="error" items="${errors.allErrors}">
				<h5 style="color: red;">${error.defaultMessage}</h5>
			</c:forEach>
		</spring:hasBindErrors>
	</center>
	<br>
	<TABLE border="0" cellpadding="0" cellspacing="0" width="55%" align="center" id="normalTableNostripe">
		<tr valign="top">
			<td>
				<input type="radio" name="type" id="type" value="current" <c:if test="${command.type eq 'current'}"> checked </c:if>> Current Jobs&nbsp;	
				<input type="radio" name="type" id="type" value="pending" <c:if test="${command.type eq 'pending'}"> checked </c:if>> Pending Jobs&nbsp;	
			</td>
			<td> 
				<b><fmt:message key="table.pendingOrderDetails.column1"/></b>
				<input type="text" id="workCenterCode"  size="6" name="workCenterCode" value="${command.workCenterCode}" > 
				<input type="button" value="Filter" name="pendingOrderDetailsFilter" onclick="javascript:checkAndSubmit();"> 
			</td>
		</tr>					
	</TABLE>
	<br>	
	<c:if test="${command.workCenterDesc ne null}">
		<TABLE border="0" cellpadding="0" cellspacing="0" width="30%" align="center" id="normalTableNostripe">
			<tr>
	 			<td> <b>Description :</b> <c:out value="${command.workCenterDesc}"/> </td>
	 		</tr>
	 </table>
	</c:if>
<br>
<c:choose>
<c:when test="${not empty command.workCenterCode}">
<c:choose>
		<c:when test='${empty command.sequences}'>
				<TABLE id="normalTableFlush4" align="center" cellSpacing="1" cellPadding="5" border="0">
					<tr>
						<td class="selected">
							<font color="#B31B00"> <b> <fmt:message key="error.noReports"/> </b> </font>
						</td>
					</tr>
				</TABLE>
			</c:when>	
			<c:otherwise>
				<CENTER>
 					<div class="tableContainer"> 
						<TABLE id="normalTableNostripe" align="center" cellSpacing="0" cellPadding="0" border="0" style="white-space: nowrap; word-spacing: normal;">
							<thead class="fixedHeader">
								<tr class="selected" height="15px">
									<th class="tableTitle" > <fmt:message key="table.pendingOrderDetails.data1"/> </th>
									<th class="tableTitle" > <fmt:message key="table.pendingOrderDetails.data2"/> </th>
									<th class="tableTitle" > <fmt:message key="table.pendingOrderDetails.data9"/> </th>
									<th class="tableTitle" > <fmt:message key="table.pendingOrderDetails.data3"/> </th>
									<th class="tableTitle" > <fmt:message key="table.pendingOrderDetails.data4"/> </th>
									<th class="tableTitle" > <fmt:message key="table.pendingOrderDetails.data5"/> </th>
									<th class="tableTitle" > <fmt:message key="table.pendingOrderDetails.data6"/> </th>
									<th class="tableTitle" > <fmt:message key="table.pendingOrderDetails.data7"/> </th>
									<th class="tableTitle" > <fmt:message key="table.pendingOrderDetails.data8"/> </th>
	 							</tr>
	 						<tbody class="scrollContent">
	 							<c:forEach items="${command.sequences}" var="data" varStatus="list">
									<c:choose>
										<c:when test='${(list.index%2) == 0}'>
											<tr class="normalRow">
										</c:when>
										<c:otherwise>
											<tr class="alternateRow">
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.moNumber}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.moNumber }"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.lineNumber}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"  style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.lineNumber}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.sequencenumber}'>
											<td class="tableContent"> &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent" style="white-space: nowrap; word-spacing: normal; width: 100px"> <c:out value="${data.sequencenumber}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.itemNumber}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"  style="white-space: nowrap; word-spacing: normal; width: 850px"> <c:out value="${data.itemNumber}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.itemDesc}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"  style="white-space: nowrap; word-spacing: normal; width: 850px"> <c:out value="${data.itemDesc}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.requiredQuantity}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"  style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.requiredQuantity}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.setup}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"  style="white-space: nowrap; word-spacing: normal;"> 
											<c:choose>
												<c:when test="${command.type eq 'current'}">
													${data.setup}
												</c:when>
												<c:otherwise>
													Required
												</c:otherwise>
											</c:choose>
											</td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.run}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"  style="white-space: nowrap; word-spacing: normal;">
												<c:choose>
												<c:when test="${command.type eq 'current'}">
													${data.run}
												</c:when>
												<c:otherwise>
													Required
												</c:otherwise>
											</c:choose>
											</td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.requiredDate}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"  style="white-space: nowrap; word-spacing: normal;">
												<fmt:formatDate pattern="yyyy-MM-dd" value="${data.requiredDate}" />
											</td>
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
	<c:when test='${empty command.sequences}'>	</c:when>
<c:otherwise>  

</c:otherwise>
</c:choose>
<center><fmt:message key="table.currentOrderDetails.data12"/></center>
</thead>
</FORM>
</BODY>
