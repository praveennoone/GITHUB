<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
function getErrorDetails(){
document.timeAdjustmentFrm.actionString.value="";
document.timeAdjustmentFrm.submit();
return false;
}

function adjustTime(index){
document.timeAdjustmentFrm.actionString.value="adjust";
document.timeAdjustmentFrm.selectedValue.value=index;
document.timeAdjustmentFrm.submit();
return false;
}

</script>

<BODY>
<div id="content">
<FORM name=timeAdjustmentFrm method=post class="form">

<input type="hidden" name="actionString">
<input type="hidden" name="selectedValue">

<CENTER>
		<font size="2"> <b> Time Adjustment </b></font>
</CENTER>
	
	<br>

<TABLE border="0" cellpadding="0" cellspacing="0" width="40%"
	align="left" id="normalTableNostripe"
	style="white-space: nowrap; word-spacing: normal;">
	<tr valign="top">
		<td style="font: 900 12px arial;left:20px;top:220px">Facility</td>
		<td align="center" size="5"
			style="font: 900 12px arial;left:20px;top:220px">
				<select name="facility" id="facility" >
					<option <c:if test="${command.facility eq 'PIN'}"> <c:out value="selected"/></c:if> value="PIN">PIN</option>
					<option <c:if test="${command.facility eq 'NUT'}"> <c:out value="selected"/></c:if> value="NUT">NUT</option>
					<option <c:if test="${command.facility eq 'CLR'}"> <c:out value="selected"/></c:if> value="CLR">COLLAR</option> 							
				</select>
			</td>
		<td align="center">
		<input 	style="font: 900 12px arial;left:20px;top:220px" type="button"
				value="Search" name="search"
			onclick="javascript:getErrorDetails();"></td>
		</tr>
</TABLE>

<br><br>
<c:if test="${not empty command.facility}">
		<c:choose>

			<c:when test='${empty command.details}'>
				<TABLE id="normalTableFlush4" align="center" cellSpacing="1" cellPadding="5" border="0">
					<tr>
						<td class="selected"><font color="#B31B00"> <b> <fmt:message
							key="error.noReports" /> </b> </font></td>
					</tr>
				</TABLE>
			</c:when>

			<c:otherwise>

				<CENTER>
				<div class="tableContainer" style="height: 330px;">
				<TABLE 	id="normalTableNostripe" align="center" cellSpacing="0"
								cellPadding="0" border="0"
								style="white-space: nowrap; word-spacing: normal;">
					<thead class="fixedHeader">
						<tr class="selected">
							<th class="tableTitle">Facility</th>
							<th class="tableTitle">Product #</th>
							<th class="tableTitle">MO #</th>
							<th class="tableTitle">Operation #</th>
							<th class="tableTitle">Employee #</th>
							<th class="tableTitle">Asset #</th>
							<th class="tableTitle">Qty</th>
							<th class="tableTitle">Activity Start Time</th>
							<th class="tableTitle">Activity End Time</th>
							<th class="tableTitle">Time Difference</th>
						</tr>
					</thead>
					<tbody class="scrollContent">
						<c:forEach items="${command.details}" var="data"  varStatus="list">
							<c:choose>
								<c:when test='${(list.index%2) == 0}'>
									<tr class="normalRow">
								</c:when>
								<c:otherwise>
									<tr class="alternateRow">
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.facility}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.facility}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.productNo}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.productNo}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.moNumber}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent" width="10px"
										style="white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.moNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.operationNo}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.operationNo}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.employeeNo}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.employeeNo}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.assetNo}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.assetNo}"></c:out></td>
								</c:otherwise>
							</c:choose>
							
							<c:choose>
								<c:when test='${empty data.quantity}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.quantity}"></c:out></td>
								</c:otherwise>
							</c:choose>
							

							<c:choose>
								<c:when test='${empty data.activityStartTime}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="white-space: nowrap; word-spacing: normal;">
										<c:out value="${data.activityStartTime}"></c:out>
									</td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.activityEndTime}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="white-space: nowrap; word-spacing: normal;">
										<c:out value="${data.activityEndTime}"></c:out>
									</td>
								</c:otherwise>
							</c:choose>
							
							<c:choose>
								<c:when test='${empty data.timeDifference}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="white-space: nowrap; word-spacing: normal;">
									<input type="text" name="difference"<c:out value="${list.index}"></c:out> value=<c:out value="${data.timeDifference}"></c:out>></td>
								</c:otherwise>
							</c:choose>

							<td class="tableContent"><input type="button" name=adjust<c:out value="${list.index}"></c:out> value="Adjust" onclick=adjustTime(<c:out value="${list.index}"></c:out>) ></td>
							</tr>
						</c:forEach>
					</tbody>
				</TABLE>
				</div>

			</CENTER>
			</c:otherwise>
		</c:choose>
</c:if>

</FORM>

	