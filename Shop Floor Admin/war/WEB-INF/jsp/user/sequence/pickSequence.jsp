<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src='static/js/validate.js'></script>
<div id="content"><script type="text/javascript">
function checkValues(e){
	var key=e.keyCode || e.which;
	if (key==13){
		fetchData('true');
	}	
}

function fetchData() {
	if(document.pickSequenceFrm.moNumber.value == "") {
		document.pickSequenceFrm.moNumber.focus();
	} else if(document.pickSequenceFrm.lineNumber.value == "") {
		document.pickSequenceFrm.lineNumber.focus();
	} else if(document.pickSequenceFrm.sequence.value == "") {
		document.pickSequenceFrm.sequence.focus();
	} else if (specialCharValidate(document.pickSequenceFrm.moNumber.value) == false) {
		alert("Special characters are not allowed here");
		document.pickSequenceFrm.moNumber.focus();
		return false;
	} else if (isNaN(document.pickSequenceFrm.lineNumber.value) || 
					isProper(document.pickSequenceFrm.lineNumber.value) == false) {
		alert("Line Number should be Numeric");
		document.pickSequenceFrm.lineNumber.focus();
		return false;
	} else if (isNaN(trim(document.pickSequenceFrm.sequence.value)) || 
					isProper(trim(document.pickSequenceFrm.sequence.value)) == false) {
		alert("Sequence Number should be Numeric");
		document.pickSequenceFrm.sequence.focus();
		return false;
	} else {
		document.pickSequenceFrm.operation.value="filter";
		document.pickSequenceFrm.submit();
		return false;
	}
}

function filterData() {
	if(document.pickSequenceFrm.moNumber.value == ""){
		alert("Enter MO Number");
		document.pickSequenceFrm.moNumber.focus();
		return false;
	} else if(document.pickSequenceFrm.lineNumber.value == ""){
		alert("Enter Line Number");
		document.pickSequenceFrm.lineNumber.focus();
		return false;
	} else if(document.pickSequenceFrm.sequence.value == ""){
		alert("Enter Sequence Number");
		document.pickSequenceFrm.sequence.focus();
		return false;
	}  else if (specialCharValidate(document.pickSequenceFrm.moNumber.value) == false) {
		alert("Special characters are not allowed here");
		document.pickSequenceFrm.moNumber.focus();
		return false;
	} else if (isNaN(document.pickSequenceFrm.lineNumber.value) || 
						isProper(document.pickSequenceFrm.lineNumber.value) == false) {
		alert("Line Number should be Numeric");
		document.pickSequenceFrm.lineNumber.focus();
		return false;
	} else if (isNaN(trim(document.pickSequenceFrm.sequence.value)) || 
						isProper(trim(document.pickSequenceFrm.sequence.value)) == false) {
		alert("Sequence Number should be Numeric");
		document.pickSequenceFrm.sequence.focus();
		return false;
	} else {
		document.pickSequenceFrm.operation.value="filter";
		document.pickSequenceFrm.submit();
		return false;
	}
}
 
function pickSequence() {
	var qty = document.pickSequenceFrm.qtyCompleted.value;
	var comments = trim(document.pickSequenceFrm.comments.value);
	var  reasonIndex = document.pickSequenceFrm.reason.selectedIndex;
		
	if((qty == "") || (qty == "0") || (qty == "0.0")) {
		alert("Current Sequence Completed Quantity is Zero.");
		document.pickSequenceFrm.qtyCompleted.focus();
	}else if (qty < 0) {
		alert("Quantity Cannot Be Negative");
		document.pickSequenceFrm.qtyCompleted.focus();
	} else if(document.pickSequenceFrm.assetNumber.value == ""){
		alert("Asset Number cannot be blank.Enter \"0000\" for No Asset");
		document.pickSequenceFrm.assetNumber.focus();
	} else if(document.pickSequenceFrm.employeeID.value == ""){
		alert("Enter Employee ID");
		document.pickSequenceFrm.employeeID.focus();
	} else if (document.pickSequenceFrm.reason[reasonIndex].text == "Other" && comments == "") { 
		alert("Enter Comments");
		document.pickSequenceFrm.comments.value="";
		document.pickSequenceFrm.comments.focus();
	} else {
		document.pickSequenceFrm.operation.value="pick";
		document.pickSequenceFrm.submit();
		return false;
	}
}

function addNewSequence() {
	var qty = document.pickSequenceFrm.qtyCompleted.value;
	var comments = trim(document.pickSequenceFrm.comments.value);
	var  reasonIndex = document.pickSequenceFrm.reason.selectedIndex;
	
	if((qty == "") || (qty == "0") || (qty == "0.0")) {
		alert("Enter Pick Quantity");
		document.pickSequenceFrm.qtyCompleted.focus();
	} else if (qty < 0) {
		alert("Quantity Cannot Be Negative");
		document.pickSequenceFrm.qtyCompleted.focus();
	} else if(document.pickSequenceFrm.assetNumber.value == ""){
		alert("Asset Number cannot be blank.Enter \"0000\" for No Asset");
		document.pickSequenceFrm.assetNumber.focus();
	} else if(document.pickSequenceFrm.employeeID.value == ""){
		alert("Enter Employee ID");
		document.pickSequenceFrm.employeeID.focus();
	}else if (document.pickSequenceFrm.reason[reasonIndex].text == "Other" && comments == "") { 
		alert("Enter Comments");
		document.pickSequenceFrm.comments.value="";
		document.pickSequenceFrm.comments.focus();
	} else {
		var answer = confirm("The Pick Quantity is :  "+qty+". Want To Continue?")
		if (answer){
			document.pickSequenceFrm.operation.value="addNew";
			document.pickSequenceFrm.submit();
			return false;
		} else {
		}
	}
}

function trim(str) {
	return str.replace(/^\s+|\s+$/g,"");
}

function upperCase(x) {
	var y=document.getElementById(x).value
	document.getElementById(x).value=y.toUpperCase()
}

function upperCase_Comments(commentsBox) {
		var comments=commentsBox.value;
		commentsBox.value=comments.toUpperCase();
}

function validateComments(commentsField, maxLength) {
	if (commentsField.value.length > maxLength) {
		commentsField.value = commentsField.value.substring(0,maxLength);
		alert("Maximum 100 characters only allowed");
	}
}

function specialCharValidate(string) {
   if (!string) return false;
   var iChars = "~!^.?_*|,\"/:<>[]{}`\';()@&$#%' '";
   for (var i = 0; i < string.length; i++) {
      if (iChars.indexOf(string.charAt(i)) != -1)
         return false;
   }
   return true;
} 
</script>
<BODY onload="document.pickSequenceFrm.moNumber.focus();">
<div id="content">
<FORM name=pickSequenceFrm method=post class="form"><br><br>
<CENTER><font size="2"> <b>Pick Sequence </b> </font></CENTER>
<!-- start of new code -->
<table style="position: absolute;" width="100%">
	<tr>
		<td><div id="successMsg" align="center">
			<font size="2" color="red"><b><c:out value="${command.message}"></c:out></b> </font>
			</div></td>
	</tr>
	<tr>
		<td><div id="errorMsg" align="center">
					<spring:hasBindErrors name="command">
						<c:forEach var="error" items="${errors.allErrors}">
							<h5 style="color: red;">${error.defaultMessage}</h5>
						</c:forEach>
					</spring:hasBindErrors>
				</div>
		</td>
	</tr>
</table>
<br><br>
<TABLE border="0" cellpadding="0" cellspacing="0" width="85%" align="center" id="normalTableNostripe" style="white-space: nowrap; width: 555px; word-spacing: normal;">
	<tr valign="top">
		<!--  <td valign="center"
			style="font: 900 12px arial; left: 20px; top: 220px; vertical-align: middle">
		Product #:<input type="text" name="partNumber" maxlength="15" onkeyup="upperCase(this.id)"
			id="partNumber" value="${command.partNumber}"
			onKeyDown="javascript:checkValues(event);" /></td> -->
		<td valign="center" align="center"
			style="font: 900 12px arial; left: 20px; top: 220px; vertical-align: middle">
		MO #:<input type="text" name="moNumber" maxlength="15" onkeyup="upperCase(this.id)"
			id="moNumber" value="${command.moNumber}"
			onKeyDown="return checkValues(event);" /></td>
		<td valign="center" align="center"
			style="font: 900 12px arial; left: 20px; top: 220px; vertical-align: middle">
		Operation #:<input type="text" name="sequence" id="sequence" maxlength="4"
			value="${command.sequence}"
			onKeyDown="return checkValues(event);" /></td>
			<td> 
				 <input type="image" src="static/images/filter.gif" name="Filter" width="60" height="30" border="0" onClick="return filterData('true');" >
								
					</td>
		
		<input type="hidden" name="lineNumber" id="lineNumber" maxlength="3" 
			value="000" readOnly="readonly" />
	</tr>
</table>
<br>
<br>
<c:if test='${(not command.errorOccured) && (not empty command.sequenceDetails) && (not command.addNewJob)}'>
	
	<TABLE id="m3TableNostripe" align="center" cellSpacing="0" cellPadding="0" border="1" style="white-space: nowrap; word-spacing: normal; width: 555px; height:30px; border-left: 1px solid #465272; border-right: 1px solid #465272; border-top: 1px solid #465272; border-bottom: 1px solid #465272;">
		<tr>
			<td align="center"><b> Facility : <c:out value="${command.facility}"></c:out></b></td>
			<td align="center"><b> M3 ItemNumber : <c:out value="${command.partNumber}"></c:out></b></td>
			<td align="center"><b> M3 Part Name : <c:out value="${command.partDesc}"></c:out></b></td>
		</tr>
	</TABLE>	
	<br><br>
	
	<CENTER>
	<div class="tableContainer" style="height: 120px"><!--  <div style="width: 978px; height: 380px; overflow: scroll; background-color: #FFFFF;"> -->
	<TABLE id="normalTableNostripe" align="center" cellSpacing="0"
		cellPadding="0" border="0"
		style="white-space: nowrap; word-spacing: normal;">
		<thead class="fixedHeader">
			<tr class="selected" height="15px">
				<th class="tableTitle" width="30">Employee Name</th>
				<th class="tableTitle" width="30">Asset Id</th>
				<th class="tableTitle" width="90">Asset Description</th>
				<th class="tableTitle" width="30">Activity</th>
				<th class="tableTitle" width="30">Quantity Completed</th>
				<th class="tableTitle" width="30">Status</th>
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
					<c:when test='${empty data.activity}'>
						<td class="tableContent" width="80">&nbsp;</td>
					</c:when>
					<c:otherwise>
						<td class="tableContent" width="80"
							style="white-space: nowrap; word-spacing: normal;"><c:out
							value="${data.activity}"></c:out></td>
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
					<c:when test='${empty data.status}'>
						<td class="tableContent" width="80">&nbsp;</td>
					</c:when>
					<c:otherwise>
						<td class="tableContent" width="80"
							style="white-space: nowrap; word-spacing: normal;"><c:out
							value="${data.status}"></c:out></td>
					</c:otherwise>
				</c:choose>

				</tr>
			</c:forEach>
		</tbody>
	</TABLE>
</c:if>
</div>
<br>


<c:if 
	test='${(not empty command.moNumber)&& (not command.errorOccured) && (command.completeable)}'>
	
	<TABLE id="m3TableNostripe" align="center" cellSpacing="0" cellPadding="0" border="1" style="white-space: nowrap; word-spacing: normal; width: 555px; height:30px; border-left: 1px solid #465272; border-right: 1px solid #465272; border-top: 1px solid #465272; border-bottom: 1px solid #465272;">
		<tr>
			<td align="center"><b> Facility : <c:out value="${command.facility}"></c:out></b></td>
			<td align="center"><b> M3 ItemNumber : <c:out value="${command.partNumber}"></c:out></b></td>
			<td align="center"><b> M3 Part Name : <c:out value="${command.partDesc}"></c:out></b></td>
		</tr>
	</TABLE>	
	<br><br>
	
	<TABLE id="normalTableNostripe" align="center" cellSpacing="1" cellPadding="5" border="0">
		<tr>
			<td>Asset Number:</td>
			<td class="NoBorderLeftSide"><input type="text"
				name="assetNumber" id="assetNumber" value="${command.assetNumber}" /></td>
			<td>Employee ID: <input type="text" name="employeeID"
				id="employeeID" value="${command.employeeID}" /></td>
			<td>Total Pick Quantity: <input type="text" name="qtyCompleted"
				id="qtyCompleted" value="${command.pickQty}" /></td>
			<td><c:if test='${command.addNewJob}'>
				<input type="button" name="addNewButton" value="Add & Pick"
					onclick="javascript:addNewSequence()">
			</c:if></td>
		</tr>
		<tr>
			<td>Reason:</td>
			<td colspan="3"><select name="reason">
				<c:forEach items="${command.reasons}" var="data">
					<option value="${data.reasonCode}">${data.reasonDesc}</option>
				</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td class="verticalAlignCenter">Comments :</td>
			<td class="NoBorderLeftSide" colspan="2"><textarea rows="4"
				cols="42" name="comments" id="comments" onkeydown="validateComments(this, 99)" onkeyup="upperCase_Comments(this)">${command.comments}</textarea>
			</td>
			<td><input type="button" value="Pick Sequence"
				<c:if test="${(empty command.sequenceDetails) or (not command.completeable) or (command.addNewJob)}"> style="visibility: hidden" </c:if>
				onclick="javascript:pickSequence()" /></td>
				<td></td>
		</tr>
	</table>
</c:if>

<input type="hidden" name="operation" id="operation" />
<input type="hidden" name="pickQty" id="pickQty"
	value="${command.pickQty}" />
</FORM>
<center>

<div style="position:absolute;left:250px;top:550px; ">
	<ul>
		<li>
		<h6>If Pick Qty Is Increased, Employee ID Should Be Provided For
		Whom The Extra Qty Is Added</h6>
		</li>		
	</ul>
</div>
</center>
<input type="hidden" name="canDisplyMessage">
<c:choose>
	<c:when test='${command.pageRefreshValue == 2}'>
		<script>
			//fetchData();
			document.getElementById("canDisplyMessage").value="yes";
		</script>
	</c:when>
	<c:otherwise>
		<script>
		document.getElementById("canDisplyMessage").value="false";
		</script>
	</c:otherwise>
</c:choose>

 
</form>