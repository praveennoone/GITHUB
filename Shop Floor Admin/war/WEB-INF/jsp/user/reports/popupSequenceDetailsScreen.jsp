<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<FORM name=sequenceDetailsPopupFrm method=post class="form">
	
	<br> <br> 
	<CENTER>
		<font size="2"> <b> <fmt:message key="table.sequenceDetails.header"/> </b> </font>
	</CENTER>  

<br>
	<c:if test="${command.moNumber ne null}">
		<TABLE border="0" cellpadding="0" cellspacing="0" width="60%" align="center" id="normalTableNostripe">
			<tr><td> <b> Facility : </b> <c:out value="${command.facility}"/> </td>
	 			<td> <b> MO Number : </b> <c:out value="${command.moNumber}"/> </td>
	 			<td> <b> Sequence : </b> <c:out value="${command.sequence}"/> </td>
			</tr>
	 </TABLE>
	</c:if>
<br>

<c:choose>
<c:when test='${empty command.sequenceDetails}'>
<TABLE id="normalTableFlush4" align="center" cellSpacing="1" cellPadding="5" border="0">
</TABLE>
</c:when>
<c:otherwise>
<div class="tableContainerPop" style="height: 270px">
<CENTER>
	<TABLE id="normalTableNostripe" align="center" cellSpacing="0" cellPadding="0" border="0" style="white-space: nowrap; word-spacing: normal;">
			<thead class="fixedHeader">
					<tr class="selected" height="15px">
						<th class="tableTitle" ><b> Employee ID </b> </th>
						<th class="tableTitle" ><b> <fmt:message key="table.sequenceDetails.data1"/> </b> </th>
						<th class="tableTitle" ><b> <fmt:message key="table.sequenceDetails.data2"/> </b>  </th>				
						<th class="tableTitle" ><b> <fmt:message key="table.sequenceDetails.data3"/> </b>  </th>
						<th class="tableTitle" ><b> <fmt:message key="table.sequenceDetails.data4"/> </b>  </th>
						<th class="tableTitle" ><b> <fmt:message key="table.sequenceDetails.data5"/> </b> </th>
						<th class="tableTitle" ><b> <fmt:message key="table.sequenceDetails.data6"/> </b> </th>
						<th class="tableTitle" ><b> <fmt:message key="table.sequenceDetails.data7"/> </b> </th>
					</tr>
		</thead>
		<tbody class="scrollContent">
					<c:forEach items="${command.sequenceDetails}" var="data" varStatus="list">
					
					<c:choose>
						<c:when test='${(list.index%2) == 0}'>
							<tr class="normalRow">
						</c:when>
						<c:otherwise>
							<tr class="alternateRow">
						</c:otherwise>
					</c:choose>
					
					<c:choose>					
						<c:when test='${empty data.employeeID}'>
							<td class="tableContent"  > &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent"   style="white-space: nowrap; word-spacing: normal;"><c:out value="${data.employeeID}"/></td>
						</c:otherwise>
					</c:choose>
					
					<c:choose>					
						<c:when test='${empty data.employeeName}'>
							<td class="tableContent"  > &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent"   style="width:340px; white-space: normal; word-spacing: normal;"><c:out value="${data.employeeName}"/></td>
						</c:otherwise>
					</c:choose>

				
					<c:choose>
						<c:when test='${empty data.assetNumber}'>
							<td class="tableContent" > &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" style="width:310px; white-space: nowrap; word-spacing: normal;"><c:out value="${data.assetNumber}"/></td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>					
						<c:when test='${empty data.assetDesc}'>
							<td class="tableContent" > &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" style="white-space: nowrap; word-spacing: normal;width: 800px"><c:out value="${data.assetDesc}"/></td>
						</c:otherwise>
					</c:choose>
					
					<c:choose>					
						<c:when test='${empty data.activity}'>
							<td class="tableContent"  > &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent"   style="white-space: nowrap; word-spacing: normal;"><c:out value="${data.activity}"/></td>
						</c:otherwise>
					</c:choose>
					
					<c:choose>					
						<c:when test='${empty data.qtyCompleted}'>
							<td class="tableContent"  > &nbsp;</td>
						</c:when>
						<c:otherwise>
						<!-- Ramanan.M - 30th Aug : Added the choice to avoid null for the End Date -->						
							<c:choose>					
								<c:when test='${data.activity == "SETUP"}'>
									<td class="tableContent"  > &nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"   style="white-space: nowrap; word-spacing: normal;"><c:out value="${data.qtyCompleted}"/></td>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					
					<c:choose>					
						<c:when test='${empty data.logonDate}'>
							<td class="tableContent"  > &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent"   style="white-space: nowrap; word-spacing: normal;">
								<!-- Ramanan.M - 30th Aug : Changed the date Format from 24 Hours to 12 Hours AM/PM -->						
								<fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss a" value="${data.logonDate}" />
							</td>
						</c:otherwise>
					</c:choose>
					
					<c:choose>					
						<c:when test='${empty data.logoffDate}'>
							<td class="tableContent"  > &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent"   style="white-space: nowrap; word-spacing: normal;">
								<!-- Ramanan.M - 30th Aug : Changed the date Format from 24 Hours to 12 Hours AM/PM -->						
								<fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss a" value="${data.logoffDate}" />
							</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>			
</tbody>
</TABLE>
</CENTER>
</div>
</c:otherwise>
</c:choose>
<br>
</FORM>
<center>
<a href="javascript:window.close();" alt="Close" tabindex="5" style="cursor: hand;">
							<img src="static/images/close.gif" alt="Close" border="0">
						</a>
	
</center>
