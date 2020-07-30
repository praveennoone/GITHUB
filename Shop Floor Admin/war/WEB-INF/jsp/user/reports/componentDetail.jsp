<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src='static/js/springxt.js'></script>
<script type="text/javascript" src='static/js/prototype.js'></script>
<script type="text/javascript"
	src='static/js/scriptaculous.js?load=effects,dragdrop'></script>
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

	if(document.componentDetailFrm.moNumber.value == ""){
		document.componentDetailFrm.moNumber.focus();
	} else if(document.componentDetailFrm.lineNumber.value == ""){
		document.componentDetailFrm.lineNumber.focus();
	} else {
		document.componentDetailFrm.submitBtn.click();
	}
}

function submitForm() {
	if(document.componentDetailFrm.moNumber.value == ""){
		alert("MO Number Cannot Be Empty");
		document.componentDetailFrm.moNumber.focus();
	} else if(document.componentDetailFrm.lineNumber.value == ""){
		alert("Line Number Cannot Be Empty");
		document.componentDetailFrm.lineNumber.focus();
	} else {
		XT.defaultLoadingElementId = 'status';
		XT.defaultLoadingImage ='static/images/ajax-loader.gif';
		XT.doAjaxAction('getComponentDetails', this);
	}
}
</script>
<BODY onload="document.componentDetailFrm.moNumber.focus();">
<FORM name=componentDetailFrm method=post class="form">
<CENTER><font size="2"> <b> Component Details </b> </font></CENTER>
<br>
<center><c:if test="${command.message != null}">
	<h5 style="color: blue">${command.message}</h5>
</c:if></center>
<br>
<br>
<center>
<TABLE border="0" cellpadding="0" cellspacing="0" width="55%"
	align="center" id="normalTableNostripe">
	<tr valign="top">
		<td>MO Number <input
			type="text" id="moNumber" name="moNumber"
			onkeyup="javascript:upperCase(this.id)" onkeydown="javascript:checkValues(event);" value="${command.moNumber}">
		</td>

		<td>Line Number <input
			type="text" id="lineNumber" name="lineNumber"
			onkeyup="javascript:upperCase(this.id)"
			onkeydown="javascript:checkValues(event);" value="${command.lineNumber}" onkeypress="return isNumberKey(event)">
		</td>

		<td><input type="button" value="Submit" name="submitBtn" onclick="javascript:submitForm()"></td>
	</tr>
</TABLE>

<br>
<div id="status"></div><div id="statusMessage"></div>
<br>
<div id="headerDiv"></div>
<br><br>
<div id="detailsDiv"></div>
</center>
</form>