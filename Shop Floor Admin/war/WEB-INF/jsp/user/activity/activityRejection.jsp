<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.util.Date"%>
<%@page import="com.gavs.hishear.util.DateUtil"%>
<script src="static/js/calendar3.js"></script>	
<script type="text/javascript" src='static/js/validate.js'></script>
<script type="text/javascript">
function checkValues(e){
	var key=e.keyCode || e.which;
	if (key==13){
		fetchData();
	}	
}

function fetchData() {
	if(document.activityRejectionFrm.moNumber.value == "") {
		document.activityRejectionFrm.moNumber.focus();
		return false;
	} else if(document.activityRejectionFrm.sequence.value == "") {
		document.activityRejectionFrm.sequence.focus();
		return false;
	} else if (specialCharValidate(document.activityRejectionFrm.moNumber.value) == false) {
		alert("Special characters are not allowed here");
		document.activityRejectionFrm.moNumber.focus();
		return false;
	} else if (isNaN(document.activityRejectionFrm.sequence.value) || 
						isProper(document.activityRejectionFrm.sequence.value) == false) {
		alert("Sequence Number should be Numeric");
		document.activityRejectionFrm.sequence.focus();
		return false;
	} else {
		document.activityRejectionFrm.submit();
		return false;
	}
}

function submitThis() {
	if(document.activityRejectionFrm.moNumber.value == ""){
		alert("Enter MONumber");
		return false;
		document.activityRejectionFrm.moNumber.focus();
	} else if(document.activityRejectionFrm.sequence.value == ""){
		alert("Enter Sequence Number");
		document.activityRejectionFrm.sequence.focus();
		return false;
	} else if (specialCharValidate(document.activityRejectionFrm.moNumber.value) == false) {
		alert("Special characters are not allowed here");
		document.activityRejectionFrm.moNumber.focus();
		return false;
	}  else if (isNaN(document.activityRejectionFrm.sequence.value) || 
					isProper(document.activityRejectionFrm.sequence.value) == false) {
		alert("Sequence Number should be Numeric");
		document.activityRejectionFrm.sequence.focus();
		return false;
	} else {
		document.activityRejectionFrm.submit();
		return false;
	}
}

function upperCase(x) {
	var y=document.getElementById(x).value
	document.getElementById(x).value=y.toUpperCase()
}

function upperCase_Comments(commentsBox) {	
		var comments=commentsBox.value;
		commentsBox.value=comments.toUpperCase();
	}
	

function updateRecord(listSize) {
	var chkStatus = 0;
	for (var i=0; i < listSize; i++) {
		var rejectChkBox = 'isRej' + i;
		if (document.getElementById(rejectChkBox).checked) {
			chkStatus = 1;
			break;
		}
	}

	if (chkStatus == 0) {
		alert("Select atleast one record to reject");
		return false;
	}	
	
	for (var i=0; i < listSize; i++) {
		var rejectChkBox = 'isRej' + i;
		var reasonName = 'reason' +i;
		var commentName = 'rejectedComments' + i;
		var commentValue = trim(document.getElementById(commentName).value); 
		var reasonCode = document.getElementById(reasonName).selectedIndex;
		if (document.getElementById(rejectChkBox).checked) {
			if (document.getElementById(reasonName).options[reasonCode].text == 'Other' && commentValue == "") {
				alert("Comment cannot be empty");
				document.getElementById(commentName).focus();
				return false;
			}
		}
	}
		
	document.activityRejectionFrm.isRejectCheck.value='yes';
	document.activityRejectionFrm.submit();
	return false;
}

function trim(str) {
	return str.replace(/^\s+|\s+$/g,"");
}

function pageReset() {
	document.activityRejectionFrm.isRejectCheck.value='reset';
	document.activityRejectionFrm.submit();
	return false;
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
<BODY onload = "document.activityRejectionFrm.moNumber.focus();">
<div id="content">
	<FORM name=activityRejectionFrm method=post class="form">
	<input type="hidden" name="toexcel">
	<input type="hidden" name="isRejectCheck">
	<input type="hidden" name="rejectVal">
	<br>
	<CENTER><br>
		<font size="2"> <b> <fmt:message key="table.activityRejection.header"/> </b> </font>
	</CENTER>
	<br> <br>
	<TABLE border="0" cellpadding="0" cellspacing="0" width="45%" align="center" id="normalTableNostripe">
				<tr valign="top">   
										
					<td valign="center"	style="font: 900 12px arial;left:20px;top:220px;vertical-align: middle"> 
						<fmt:message key="table.activityRejection.column2"/>#:
						<input type="text" name="moNumber" id="moNumber" maxlength="15" value="${command.moNumber}" onkeyup="upperCase(this.id)" onKeyDown="return checkValues(event);"> 
    				</td>
					
					<td valign="center"	style="font: 900 12px arial;left:20px;top:220px;vertical-align: middle">
						Sequence #:
						<input type="text" name="sequence" id="sequence" maxlength="4" value="${command.sequence}" onKeyDown="return checkValues(event);"> 
					</td>
					<td><input type="image" src="static/images/filter.gif" name="Filter" width="60" height="30" onClick="disabled=true;return submitThis();" >
						 
						
					</td>				
					
				</tr>
		</TABLE>

<br> <br>

<CENTER>
<c:choose>
	<c:when test="${command.isUpdate eq 'true'}">
		<center><font size="3" color="blue">Record Updated!!</font></center>
	</c:when>
	<c:otherwise>
		<center><font size="2" color="red"><c:out value="${command.errorMessage}"></c:out></font></center>
	</c:otherwise>
</c:choose>	
</CENTER>

<c:choose>
<c:when test="${not empty command.sequence}">


<c:choose>

<c:when test='${empty command.activityRejection}'>
	<c:if test="${command.canProceed eq 'true'}">
		<c:if test="${command.isUpdate eq 'false'}">
			<TABLE id="normalTableFlush4" align="center" cellSpacing="1" cellPadding="5" border="0">
			<tr>
				<td class="selected">
					<font color="#B31B00"> <b> <fmt:message key="error.noReports"/> </b>
					</font>
				</td>
			</tr>
			</TABLE>
		</c:if>
	</c:if>
</c:when>

<c:otherwise>

<CENTER>
  <div class="tableContainer" style="height: 320px"> 
<!--  <div style="width: 978px; height: 380px; overflow: scroll; background-color: #FFFFF;"> -->
			<TABLE id="normalTableNostripe" align="center" cellSpacing="0" cellPadding="0" border="0" style="white-space: nowrap; word-spacing: normal;">
			<thead class="fixedHeader">
					<tr class="selected" height="15px">
						<th class="tableTitle" width="30"> Employee #</th>
						<th class="tableTitle" style="width:400px;white-space: nowrap; word-spacing: normal;"> Employee Name </th>
						<th class="tableTitle" width="30"> <fmt:message key="table.actrej.column2"/> </th>
						<th class="tableTitle" width="30"> Asset # </th>
						<!-- <th class="tableTitle" width="30"> <fmt:message key="table.actrej.column3"/> </th> -->
						<th class="tableTitle" width="20"> <fmt:message key="table.actrej.column4"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.actrej.column6"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.actrej.column7"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.actrej.column8"/> </th>
						<th class="tableTitle" width="20"> <fmt:message key="table.actrej.column9"/> </th>
						<!--<th class="tableTitle" width="20"> <fmt:message key="table.actrej.column10"/> </th>-->
						<!-- <th class="tableTitle" width="20"> <fmt:message key="table.actrej.column11"/> </th> -->
					</tr>
		</thead>
		<tbody class="scrollContent">
					<c:forEach items="${command.activityRejection}" var="data" varStatus="list">
					
					<c:choose>
						<c:when test='${(list.index%2) == 0}'>
							<tr class="normalRow">
						</c:when>
						<c:otherwise>
							<tr class="alternateRow">
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test='${empty data.employeeNumber}'>
							<td class="tableContent" width="30"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.employeeNumber}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					<c:choose>
						<c:when test='${empty data.empName}'>
							<td class="tableContent" width="30"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" style="width:300px;white-space: nowrap; word-spacing: normal;"> <c:out value="${data.empName}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.activity}'>
							<td class="tableContent" width="30"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="25" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.activity}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<c:choose>
						<c:when test='${empty data.assetNumber}'>
							<td class="tableContent" width="30"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.assetNumber}"></c:out> </td>
						</c:otherwise>				
					</c:choose>

					<!--<c:choose>
						<c:when test='${empty data.asset}'>
							<td class="tableContent" width="30"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.asset}"></c:out> </td>
						</c:otherwise>				
					</c:choose>-->
					
					<c:choose>
						<c:when test='${empty data.logon}'>
							<td class="tableContent" width="30"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.logon}"></c:out> </td>
						</c:otherwise>				
					</c:choose>

					<c:choose>
						<c:when test='${empty data.completedQty}'>
							<td class="tableContent" width="90"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="90" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.completedQty}"></c:out> </td>
						</c:otherwise>				
					</c:choose>					
					
					<c:choose>
						<c:when test='${empty data.logoff}'>
							<td class="tableContent" width="70"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="70" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.logoff}"></c:out> </td>
						</c:otherwise>				
					</c:choose>
					
					<td class="tableContent" width="30"> 
						<input type="checkbox" name=isRej<c:out value="${list.index}"></c:out> value="<c:out value='${data.actNo}'/>" <c:if test="${data.jobStatus eq 'R'}"> checked=checked </c:if>> 
					</td>
					<td class="tableContent" width="130"> 
						<select name="reason<c:out value="${list.index}"></c:out>">
							<c:forEach items="${command.reasons}" var="cmd">
								<option value="${cmd.reasonCode}">${cmd.reasonDesc}</option>
							</c:forEach>
						</select><br>
						<textarea name=rejectedComments<c:out value="${list.index}"></c:out> rows="2" cols="27" 
									onkeydown="validateComments(this, 99)" onkeyup="upperCase_Comments(this)"></textarea>  
						<input type="hidden" name="activityLogNumber<c:out value="${list.index}"></c:out>" value="<c:out value="${data.activityLogNumber}"></c:out>" />
					</td>
				<!--	<td class="tableContent" width="80"> <input type="text" name=rejectedBy<c:out value="${list.index}"></c:out> value="${command.rejectedBy}"> </td> -->
					<!-- <td class="tableContent" width="80">
						<input type="text" name=rejDate<c:out value="${list.index}"></c:out>  readonly="readonly" value=<%= new DateUtil().getCurrentDate("yyyy-MM-dd") %>> 
					</td> -->
					</tr>
			</c:forEach>			
</tbody>
</TABLE>
</div>
<br>
<input type="hidden" name="listSize" value="${command.activitySize}">
<input type="button" name="reject" value="Confirm" onclick="javascript:updateRecord(document.activityRejectionFrm.listSize.value)">
<input type="reset" name="cancel" value="Reset" onclick="pageReset()">
</center>
</c:otherwise>
</c:choose>
</c:when>
</c:choose>
</form>