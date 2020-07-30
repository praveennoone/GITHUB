<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src='static/js/validate.js'></script>
<script>
function checkValues(e){
	var key=e.keyCode || e.which;
	if (key==13){
		fetchData();
	}
}

function isNumberKey(evt) {
   var charCode = (evt.which) ? evt.which : event.keyCode;
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
}

function upperCase(x) {
	var y=document.getElementById(x).value
	document.getElementById(x).value=y.toUpperCase()
}

function fetchData() {

	if(document.resetItemWeightFrm.itemNumber.value == ""){
		document.resetItemWeightFrm.itemNumber.focus();
	} else if(document.resetItemWeightFrm.sequenceNumber.value == ""){
		document.resetItemWeightFrm.sequenceNumber.focus();
	} else {
		document.resetItemWeightFrm.resetBtn.click();
	}
}

function submitForm() {
	if(document.resetItemWeightFrm.itemNumber.value == ""){
		alert("Item Number Cannot Be Empty");
		document.resetItemWeightFrm.itemNumber.focus();
		return false;
	} else if (specialCharValidate(document.resetItemWeightFrm.itemNumber.value) == false) {
		alert("Special characters not allowed");
		document.resetItemWeightFrm.itemNumber.focus();
		return false;
	} else if(document.resetItemWeightFrm.sequenceNumber.value == ""){
		alert("Sequence Number Cannot Be Empty");
		document.resetItemWeightFrm.sequenceNumber.focus();
		return false;
	} else if(document.resetItemWeightFrm.moNumber.value == ""){
		alert("MO Number Cannot Be Empty");
		document.resetItemWeightFrm.sequenceNumber.focus();
		return false;
	} else if (isNaN(document.resetItemWeightFrm.sequenceNumber.value)) {
		alert("Sequence Number should be numeric");
		document.resetItemWeightFrm.sequenceNumber.focus();
		return false;
	} else {
		document.resetItemWeightFrm.itemNumber.value = trim(document.resetItemWeightFrm.itemNumber.value);
		document.resetItemWeightFrm.submit();
		return false;
	}
}

function specialCharValidate(string) {
   if (!string) return false;
   var iChars = "~!^.?_*|,\"/:<>[]{}`\';()@&$#%''";
   for (var i = 0; i < string.length; i++) {
      if (iChars.indexOf(string.charAt(i)) != -1)
         return false;
   }
   return true;
} 
</script>
<BODY onload="document.resetItemWeightFrm.itemNumber.focus();">
<FORM name=resetItemWeightFrm method=post class="form">
<CENTER><font size="2"> <br><br><b> <fmt:message
	key="table.resetItemWeight.header" /> </b> </font></CENTER>
<br>
<center><c:if test="${command.message != null}">
	<h5 style="color: blue">${command.message}</h5>
</c:if></center>

<br>

<TABLE border="0" cellpadding="0" cellspacing="0" width="46%"
	align="center" id="normalTableNostripe">
	<tr valign="top">
		<td valign="center"	style="font: 900 12px arial;left:20px;top:220px;vertical-align: middle">
		<fmt:message key="table.resetItemWeight.column1" /> <input
			type="text" id="itemNumber" name="itemNumber"
			onkeyup="javascript:upperCase(this.id)" onkeydown="javascript:checkValues(event);" value="${command.itemNumber}">
		</td>

		<td valign="center"	style="font: 900 12px arial;left:20px;top:220px;vertical-align: middle">
		<fmt:message key="table.resetItemWeight.column2" /> <input
			type="text" id="moNumber" name="moNumber"
			onkeyup="javascript:upperCase(this.id)"
			onkeydown="javascript:checkValues(event);" value="${command.moNumber}" >
		</td>
		
		<td valign="center"	style="font: 900 12px arial;left:20px;top:220px;vertical-align: middle">
		<fmt:message key="table.resetItemWeight.column3" /> <input
			type="text" id="sequenceNumber" name="sequenceNumber"
			onkeyup="javascript:upperCase(this.id)"
			onkeydown="javascript:checkValues(event);" value="${command.sequenceNumber}" onkeypress="return isNumberKey(event)"
			maxlength="4">
		</td>
		
		<td> <input type="image" src="static/images/filter.gif" name="Filter" width="60" height="30" onClick=" disabled=true;return submitForm();" >
						
						
					</td>

		<!--td><input type="button" value="Reset" name="resetBtn" onclick="javascript:submitForm()"></td-->
	</tr>
</TABLE>

<br>
<br>

</center>
</form>