<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
function checkValues(e){
	var key=e.keyCode || e.which;
	if (key==13){
		fetchData();
	}	
}

function fetchData() {
	if(document.editDetailsFrm.moNumber.value == "") {
		document.editDetailsFrm.moNumber.focus();
	} else if(document.editDetailsFrm.lineNumber.value == "") {
		document.editDetailsFrm.lineNumber.focus();
	} else if(document.editDetailsFrm.sequence.value == "") {
		document.editDetailsFrm.sequence.focus();
	} else {
		document.editDetailsFrm.submit();
		return false;
	}
}

function filterData(){
	if(document.editDetailsFrm.moNumber.value == ""){
		alert("Enter MO Number");
		document.editDetailsFrm.moNumber.focus();
	} else if(document.editDetailsFrm.lineNumber.value == ""){
		alert("Enter Line Number");
		document.editDetailsFrm.lineNumber.focus();
	}else if(document.editDetailsFrm.sequence.value == ""){
		alert("Enter Sequence Number");
		document.editDetailsFrm.sequence.focus();
	}else {
		document.editDetailsFrm.submit();
		return false;
	}
}

function editDetail(moNumber, lineNumber, sequenceNumber, qtyCompleted, logonDate, logoffDate, assetNumber, jobActivityNumber) {
	window.open('editDetailsPopup.htm?MN=' + moNumber + '&LN=' + lineNumber + '&SN=' + 	sequenceNumber + '&JN=' + jobActivityNumber + '&QC=' + qtyCompleted + '&LI=' +	logonDate + '&LO=' +	logoffDate + '&AN=' +	assetNumber, 'EditJob', 'width=860, height=150, center:yes');
}

function upperCase(x) {
	var y=document.getElementById(x).value;
	document.getElementById(x).value=y.toUpperCase();
}
</script>

<div id="content">
<BODY onload="document.editDetailsFrm.moNumber.focus();">
<div id="content">
<FORM name=editDetailsFrm method=post class="form"><br>
<CENTER><font size="2"> <b> Shop Floor System- Edit details </b> </font></CENTER>
<br>

<!-- start of new code -->
<TABLE border="0" cellpadding="0" cellspacing="0" width="85%" align="center" id="normalTableNostripe" style="white-space: nowrap; word-spacing: normal;">
	<tr valign="top">
		<td valign="center"
			style="font: 900 12px arial; left: 20px; top: 220px; vertical-align: middle">
		MO #:<input type="text" name="moNumber" onkeyup="upperCase(this.id)"
			id="moNumber" value="${command.moNumber}"
			onKeyDown="javascript:checkValues(event);" /></td>
		<td>Line #:<input type="text" name="lineNumber" id="lineNumber"
			value="${command.lineNumber}"
			} onKeyDown="javascript:checkValues(event);" /></td>
		<td>Sequence #:<input type="text" name="sequence" id="sequence"
			value="${command.sequence}"
			onKeyDown="javascript:checkValues(event);" /></td>
		<td>&nbsp;&nbsp;&nbsp;<input type="button" value="Filter" name="Filter" onClick="javascript:filterData()"></td>
	</tr>
</table>
<br>
<br>
<c:if test='${(not command.errorOccured) && (not empty command.sequenceDetails) && (not command.addNewJob)}'>

	<CENTER>
	<div class="tableContainer" style="height: 380px"><!--  <div style="width: 978px; height: 380px; overflow: scroll; background-color: #FFFFF;"> -->
	<TABLE id="normalTableNostripe" align="center" cellSpacing="0"
		cellPadding="0" border="0"
		style="white-space: nowrap; word-spacing: normal;">
		<thead class="fixedHeader">
			<tr class="selected" height="15px">
				<th class="tableTitle" width="30">Employee Name</th>
				<th class="tableTitle" width="30">Asset Id</th>
				<th class="tableTitle" width="90">Asset Description</th>
				<th class="tableTitle" width="30">Quantity Reported</th>
				<th class="tableTitle" width="30">Login Time</th>
				<th class="tableTitle" width="30">Logoff Time</th>
				<th class="tableTitle" width="30">Modify</th>
			</tr>
		</thead>
		<tbody class="scrollContent">
			<c:forEach items="${command.sequenceDetails}" var="data"
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
					<c:when test='${empty data.employeeName}'>
						<td class="tableContent" width="30">&nbsp;</td>
					</c:when>
					<c:otherwise>
						<td class="tableContent" width="30"
							style="white-space: nowrap; word-spacing: normal;"><c:out
							value="${data.employeeName}"></c:out></td>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test='${empty data.assetNumber}'>
						<td class="tableContent" width="90">&nbsp;</td>
					</c:when>
					<c:otherwise>
						<td class="tableContent" width="90"
							style="white-space: nowrap; word-spacing: normal;"><c:out
							value="${data.assetNumber}"></c:out></td>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test='${empty data.assetDesc}'>
						<td class="tableContent" width="90">&nbsp;</td>
					</c:when>
					<c:otherwise>
						<td class="tableContent" width="90"
							style="white-space: nowrap; word-spacing: normal;"><c:out
							value="${data.assetDesc}"></c:out></td>
					</c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test='${empty data.qtyCompleted}'>
						<td class="tableContent" width="80">&nbsp;</td>
					</c:when>
					<c:otherwise>
						<td class="tableContent" width="80"
							style="white-space: nowrap; word-spacing: normal;"><c:out
							value="${data.qtyCompleted}"></c:out></td>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test='${empty data.logonDate}'>
						<td class="tableContent" width="90">&nbsp;</td>
					</c:when>
					<c:otherwise>
						<td class="tableContent" width="90"
							style="white-space: nowrap; word-spacing: normal;">
							<fmt:formatDate pattern="MM-dd-yyyy HH:mm" value="${data.logonDate}"/></td>
					</c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test='${empty data.logoffDate}'>
						<td class="tableContent" width="90">&nbsp;</td>
					</c:when>
					<c:otherwise>
						<td class="tableContent" width="90"
							style="white-space: nowrap; word-spacing: normal;"><fmt:formatDate pattern="MM-dd-yyyy HH:mm" value="${data.logoffDate}"/>
							</td>
					</c:otherwise>
				</c:choose>
				
				<td class="tableContent" width="80"
					style="white-space: nowrap; word-spacing: normal;">
					<a href="javascript:editDetail('${command.moNumber}', '${command.lineNumber}', 
					'${command.sequence}', '${data.qtyCompleted}', '<fmt:formatDate pattern="MM-dd-yyyy HH:mm:ss.SSS" value="${data.logonDate}"/>', 
					'<fmt:formatDate pattern="MM-dd-yyyy HH:mm:ss.SSS" value="${data.logoffDate}"/>', 
					'${data.assetNumber}', '${data.jobActivityNumber}')">
					 <img src="static/images/icon_edit.png" style="border-style: none"/>
					</a>
				</td>
				</tr>
			</c:forEach>
		</tbody>
	</TABLE>
</c:if>
</div>
<br>
</FORM>