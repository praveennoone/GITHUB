<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
function submitForm() {
	if (document.editDetailsPopupFrm.assetReasonCode.value=='17' && document.editDetailsPopupFrm.assetComments.value=='') {
		alert('Enter Comments!!');
		document.editDetailsPopupFrm.assetComments.focus();
		return
	}
	
	if (document.editDetailsPopupFrm.qtyReasonCode.value=='17' && document.editDetailsPopupFrm.qtyComments.value=='') {
		alert('Enter Comments!!');
		document.editDetailsPopupFrm.qtyComments.focus();
		return
	}
	
	if (document.editDetailsPopupFrm.qtyCompleted.value=='' || document.editDetailsPopupFrm.qtyCompleted.value<0) {
		alert('Enter Valid Quantity!!');
		document.editDetailsPopupFrm.qtyCompleted.focus();
		return
	}
	
	document.editDetailsPopupFrm.submit();
	pausecomp(1000);
	alert("Updated Successfully!!");
	opener.document.forms[0].submit();
	window.close();
}

function pausecomp(millis) {
	var date = new Date();
	var curDate = null;

	do { curDate = new Date(); } 
	while(curDate-date < millis);
} 

</script>
<body bgcolor="#6699CC">

<form name="editDetailsPopupFrm" method=post>
<table border=1>
<tr>
	<td>Qty Reported</td>
	<td><input type="text" name="qtyCompleted" value="${command.qtyCompleted}"></td>
	<td>Reason</td>
	<td>
	<select name="qtyReasonCode">
		<c:forEach items="${command.reasons}" var="data">
			<option value=${data.reasonCode}>${data.reasonDesc}</option>
		</c:forEach>
	</select>
	</td>
	<td>Comments</td>
	<td class="NoBorderLeftSide">
	<textarea rows="2" cols="30" name="qtyComments" id="qtyComments">${command.qtyComments}</textarea>
	</td>
</tr>
</tr>
<tr>
	<td>Asset Number</td>
	<td><input type="text" name="assetNumber" value="${command.assetNumber}"></td>
	<td>Reason</td>
	<td>
	<select name="assetReasonCode">
		<c:forEach items="${command.reasons}" var="data">
			<option value="${data.reasonCode}">${data.reasonDesc}</option>
		</c:forEach>
	</select>
	</td>
	<td>Comments</td>
	<td class="NoBorderLeftSide">
	<textarea rows="2" cols="30" name="assetComments" id="assetComments" >${command.assetComments}</textarea>
	</td>
</tr>
<br>
</tr>
<tr>
<td></td><td><input type="button" name="Update" value="Update" onClick="javascript:submitForm()"></td>
</tr>
</table>
</form>
</body>