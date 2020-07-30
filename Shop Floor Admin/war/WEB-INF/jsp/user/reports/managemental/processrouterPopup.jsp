<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<BODY>
<div id="content"> 
	<FORM name=frmCurrentOrderDetails method=post class="form">
	<input type="hidden" name="toexcel">
	<br>
	<CENTER>
		<font size="2"> <b> <fmt:message key="table.currentOrderDetails.header"/> </b> </font>
	</CENTER>
	<br> 
	<TABLE border="0" cellpadding="0" cellspacing="0" width="25%" align="center" id="normalTableNostripe">
			<tr align="center">
	 			<td align="right"> <b>Facility: </b>${command.facility}</td><td> <b>MO Number: </b>${command.moNumber}</td>
			</tr>
	</TABLE>
	<br>
	<center>
		<spring:hasBindErrors name="command">
			<c:forEach var="error" items="${errors.allErrors}">
				<h5 style="color: red;">${error.defaultMessage}</h5>
			</c:forEach>
		</spring:hasBindErrors>
	</center>
	<br>
	
	<c:if test="${command.partDesc ne null}">
		<TABLE border="0" cellpadding="0" cellspacing="0" width="60%" align="center" id="normalTableNostripe">
			<tr>
	 			<td> <b>Part No/Part Desc :</b> <c:out value="${command.partNumber}"/> / <c:out value="${command.partDesc}"/>   </td>
	 			<td> <b>Order Qty :</b> <c:out value="${command.orderQty}"/> </td>
	 		</tr>
	 </table>
	</c:if>
<br>
<c:choose>
<c:when test="${not empty command.moNumber or not empty command.partNumber }">
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
									<th class="tableTitle" > <fmt:message key="table.currentOrderDetails.data3"/> </th>
									<th class="tableTitle" > <fmt:message key="table.currentOrderDetails.data4"/> </th>
									<th class="tableTitle" > <fmt:message key="table.currentOrderDetails.data5"/> </th>
									<th class="tableTitle" > <fmt:message key="table.currentOrderDetails.data9"/> </th>
									<th class="tableTitle" > <fmt:message key="table.currentOrderDetails.data10"/> </th>
									<th class="tableTitle" > <fmt:message key="table.currentOrderDetails.data11"/> </th>
									<th class="tableTitle" > <fmt:message key="table.currentOrderDetails.data7"/> </th>
								
									<th class="tableTitle" > &nbsp; </th>
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
										<c:when test='${empty data.sequence}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.sequence}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.workCenterCode}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"  style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.workCenterCode}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.workCenterDesc}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"  style="white-space: nowrap; word-spacing: normal; width: 850px"> <c:out value="${data.workCenterDesc}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.setupStatus}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${data.setupStatus eq 'Not Started'}">
													<td class="tableContent"  style="white-space: nowrap; word-spacing: normal; color: red"> <c:out value="${data.setupStatus}"></c:out> </td>
												</c:when>
												<c:otherwise>
													<td class="tableContent"  style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.setupStatus}"></c:out> </td>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.runStatus}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${data.runStatus eq 'Not Started'}">
													<td class="tableContent"  style="white-space: nowrap; word-spacing: normal; color: red"> <c:out value="${data.runStatus}"></c:out> </td>
												</c:when>
												<c:otherwise>
													<td class="tableContent"  style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.runStatus}"></c:out> </td>
												</c:otherwise>
												</c:choose>
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
									<c:choose>					
										<c:when test='${empty data.qtyCompleted}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"  style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.qtyCompleted}"></c:out> </td>
										</c:otherwise>
									</c:choose>
											<td class="tableContent" > 
											<!-- Ramanan.M - 30th Aug  : Added the Parameter 'Facility' to display it in the Sequence Details Popup Screen -->
												<A href="javascript:popupModalWithParams('sequenceDetails.htm?moNumber=${command.moNumber}&lineNumber=${command.lineNumber}&sequenceNumber=${data.sequence}&activityCode=${data.activityCode}&facility=${command.facility}','dialogWidth:950px; dialogHeight:410px; center:yes')" style="color: blue">Details</A>
											</td>
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
</thead>

</FORM>
</div>
<center>
	<input type="button" name="close" value="Close" onclick="window.close();">
</center>
</BODY>
