<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="true"%>
<script type="text/javascript" src='static/js/springxt.js'></script>
<script type="text/javascript" src='static/js/prototype.js'></script>
<script type="text/javascript"
	src='static/js/scriptaculous.js?load=effects,dragdrop'></script>

<div id="content"><script type="text/javascript">
function upperCase(x) {
	var y=document.getElementById(x).value
	document.getElementById(x).value=y.toUpperCase()
}

function submitForm() {
	document.factoryForm.toExcel.value='no';	
	document.factoryForm.submit();	
	return false;
}

function exportToExcel() {
	document.factoryForm.toExcel.value='yes';
	document.factoryForm.submit();
	return false;
}

function popupWithParams(url, param, fieldId, features) {
	var paramValue = '';
	var field = document.getElementById(fieldId);
	if (field != null) {
	    paramValue = field.value;
	}
	window.open(url + '&' + param + '=' + paramValue, '', features);
}

function popupParams(url, param, features) {
	var partNum = param+'='+document.getElementById(param).value;
	alert(partNum);
	window.open(url + '?&'+partNum, '', features);
}
</script>
<BODY>
<script type="text/javascript" src="static/js/wz_tooltip.js"></script>
<FORM name=factoryForm method="POST" class="form">
<CENTER><font size="2"><b>Production WIP Status</b> </font></CENTER>
<TABLE border="0" cellpadding="0" cellspacing="0" width="65%"
	align="center" id="normalTableNostripe">
	<tr>
		<td><b>Factory</b> <select name="factory"
			id="factory" style="width:180px;">
			<option value=""
				<c:if test="${command.factory == null}">selected</c:if>>ALL</option>
			<option value="P"
				<c:if test="${command.factory == 'P'}">selected</c:if>>PIN</option>
			<option value="C"
				<c:if test="${command.factory == 'C'}">selected</c:if>>COLLAR</option>
			<option value="N"
				<c:if test="${command.factory == 'N'}">selected</c:if>>NUT</option>
		</select></td>
		<td><b>Part Number</b> <input type="text"
			onkeyup="upperCase(this.id)" id="partNumber" name="partNumber"
			value="${command.partNumber}"> <a
			href="javascript:popupParams('partnumberPopup.htm','partNumber', 'left=10,top=80,width=500, height=400,scrollbars=no, center:yes')"
			style="text-decoration: underline;"><img
			src="static/images/search.jpg" alt="Search" border="0" align="middle" /></td>
		</td>
		<td><A href="javaScript:submitForm();"
			name="orderFilter"><img src="static/images/filter.gif" alt=""
			border="0"></A></td>
	</tr>
</TABLE>
<br>
<c:choose>
	<c:when
		test="${not empty command.partNumber || not empty command.factory || not empty command.wipStatusReport}">
		<c:choose>
			<c:when test='${empty command.wipStatusReport}'>
				<TABLE id="normalTableFlush4" align="center" cellSpacing="0"
					cellPadding="0" border="0">
					<tr>
						<td class="selected"><font color="#B31B00"> <b> <fmt:message
							key="error.noReports" /> </b> </font></td>
					</tr>
				</TABLE>
			</c:when>

			<c:otherwise>

				<CENTER>
				<div class="tableContainer" style="height: 360px;">
				<TABLE id="normalTableNostripe" align="center" cellSpacing="0"
					cellPadding="0" border="0"
					style="white-space: nowrap; word-spacing: normal;">
					<thead class="fixedHeader">
						<tr class="selected" height="15px">
							<th class="tableTitle">MO Number</th>
							<th class="tableTitle">Line#</th>
							<th class="tableTitle">Part Number</th>
							<th class="tableTitle">Part Description</th>
							<th class="tableTitle">Completed Sequence</th>
							<th class="tableTitle">Last Pick Qty</th>
							<th class="tableTitle">Current Sequence</th>
							<th class="tableTitle">Current Qty</th>
							<th class="tableTitle">Dept</th>
							<th class="tableTitle">Location</th>
						</tr>
					</thead>
					<tbody class="scrollContent">
						<c:forEach items="${command.wipStatusReport}" var="data"
							varStatus="list">

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
									<td class="tableContent" width="20px">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="width:10px;white-space: nowrap; word-spacing: normal;">
										<a
										href="javascript:popupWithParams('currentOrderDetails.htm?moNumber=${data.moNumber}&lineNumber=${data.lineNumber}','no','','left=10,top=80,width=1000, height=500,scrollbars=no, center:yes')"
										style="text-decoration: underline;"> 
									<c:out value="${data.moNumber}"></c:out></a></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.lineNumber}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="width:2px;white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.lineNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>
							
							<c:choose>
								<c:when test='${empty data.itemNumber}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="width:300px;white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.itemNumber}"></c:out></td>
								</c:otherwise>
							</c:choose>
							
							<c:choose>
								<c:when test='${empty data.itemDescription}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="width:600px;white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.itemDescription}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.completedSequence}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="width:5px;white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.completedSequence}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.latestPickQty}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="width:10px;white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.latestPickQty}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.processingSequence}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="width:5px;white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.processingSequence}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.processingQty}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="width:10px;white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.processingQty}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.department}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="width:10px;white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.department}"></c:out></td>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${empty data.location}'>
									<td class="tableContent">&nbsp;</td>
								</c:when>
								<c:otherwise>
									<td class="tableContent"
										style="width:1200px;white-space: nowrap; word-spacing: normal;">
									<c:out value="${data.location}"></c:out></td>
								</c:otherwise>
							</c:choose>
							</tr>
						</c:forEach>
					</tbody>
				</TABLE>
				</div>
				</CENTER>
				<div align="right" style="font: small-caps 900 11px arial;"><b><a
					href="#" name="excel" onclick="javascript:exportToExcel();">
				Export to Excel </a></b></div>
			</c:otherwise>
		</c:choose>
</c:when>
</c:choose>
<input type="hidden" name="toExcel" id="toExcel"></FORM>