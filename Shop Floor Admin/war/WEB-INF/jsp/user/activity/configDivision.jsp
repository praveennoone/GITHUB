<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src='static/js/springxt.js'></script>
<script type="text/javascript" src='static/js/prototype.js'></script>
<script type="text/javascript"
	src='static/js/scriptaculous.js?load=effects,dragdrop'></script>
<script type="text/javascript" src='static/js/wz_tooltip.js'></script>

<script type="text/javascript">

	function pageLoad() {
		
		var firstPositiveOp = '${command.positiveFirstOp}';
		var firstNegativeOp = '${command.negativeFirstOp}';
		var lastPositiveOp = '${command.positiveLastOp}';
		var lastNegativeOp = '${command.negativeLastOp}';
		var otherPositiveOp = '${command.positiveOtherOp}';
		var otherNegativeOp = '${command.negativeOtherOp}';
		document.frmConfigDivision.positiveFirstOp.value = trim(firstPositiveOp);
		document.frmConfigDivision.negativeFirstOp.value = trim(firstNegativeOp);
		document.frmConfigDivision.positiveLastOp.value = trim(lastPositiveOp);
		document.frmConfigDivision.negativeLastOp.value = trim(lastNegativeOp);
		document.frmConfigDivision.positiveOtherOp.value = trim(otherPositiveOp);
		document.frmConfigDivision.negativeOtherOp.value = trim(otherNegativeOp);
		
	}

	function checkValues(e) {
		if (e.keyCode == 13) {
			if (!saveVarianceDetails()) {
				return false;
			}
		}
	}

	function upperCase(x) {
		var y=document.getElementById(x).value
		document.getElementById(x).value=y.toUpperCase()
	}
	
	function saveVarianceDetails() {
		if(trim(document.frmConfigDivision.positiveFirstOp.value) == "") {
			alert("Please Enter the Positive (+ve) variance % for First Operation.");
				document.frmConfigDivision.positiveFirstOp.focus();
				return false;
		}else if(checkNumeric(trim(document.frmConfigDivision.positiveFirstOp.value)) == false || (trim(document.frmConfigDivision.positiveFirstOp.value)) > 100 ) {
				alert("Variance % values must be between 0 to 100.");
				document.frmConfigDivision.positiveFirstOp.focus();
				return false;
		}else if(trim(document.frmConfigDivision.negativeFirstOp.value) == "") {
			alert("Please Enter the Negative (-ve) variance % for First Operation.");
				document.frmConfigDivision.negativeFirstOp.focus();
				return false;
		}else if(checkNumeric(trim(document.frmConfigDivision.negativeFirstOp.value)) == false || (trim(document.frmConfigDivision.negativeFirstOp.value)) > 100 ) {
				alert("Variance % values must be between 0 to 100.");
				document.frmConfigDivision.negativeFirstOp.focus();
				return false;
		}else if(trim(document.frmConfigDivision.positiveLastOp.value) == "") {
			alert("Please Enter the Positive (+ve) variance % for Last (MORV) Operation.");
				document.frmConfigDivision.positiveLastOp.focus();
				return false;
		}else if(checkNumeric(trim(document.frmConfigDivision.positiveLastOp.value)) == false || (trim(document.frmConfigDivision.positiveLastOp.value)) > 100 ) {
				alert("Variance % values must be between 0 to 100.");
				document.frmConfigDivision.positiveLastOp.focus();
				return false;
		}else if(trim(document.frmConfigDivision.negativeLastOp.value) == "") {
			alert("Please Enter the Negative (-ve) variance % for Last (MORV) Operation.");
				document.frmConfigDivision.negativeLastOp.focus();
				return false;
		}else if(checkNumeric(trim(document.frmConfigDivision.negativeLastOp.value)) == false || (trim(document.frmConfigDivision.negativeLastOp.value)) > 100 ) {
				alert("Variance % values must be between 0 to 100.");
				document.frmConfigDivision.negativeLastOp.focus();
				return false;
		}else if(trim(document.frmConfigDivision.positiveOtherOp.value) == "") {
			alert("Please Enter the Positive (+ve) variance % for Other Operations.");
				document.frmConfigDivision.positiveOtherOp.focus();
				return false;
		}else if(checkNumeric(trim(document.frmConfigDivision.positiveOtherOp.value)) == false || (trim(document.frmConfigDivision.positiveOtherOp.value)) > 100 ) {
				alert("Variance % values must be between 0 to 100.");
				document.frmConfigDivision.positiveOtherOp.focus();
				return false;
		}else if(trim(document.frmConfigDivision.negativeOtherOp.value) == "") {
			alert("Please Enter the Negative (-ve) variance % for Other Operations.");
				document.frmConfigDivision.negativeOtherOp.focus();
				return false;
		}else if(checkNumeric(trim(document.frmConfigDivision.negativeOtherOp.value)) == false || (trim(document.frmConfigDivision.negativeOtherOp.value)) > 100 ) {
				alert("Variance % values must be between 0 to 100.");
				document.frmConfigDivision.negativeOtherOp.focus();
				return false;
		}		
		document.frmConfigDivision.submit();
		pageLoad();
	}

	function cancel() {
		
		document.location.href='configDivision.htm';
		
	}
			
	function checkNumeric(strString)
	   //  check for valid numeric strings        
	   {
	   var strValidChars = "0123456789";
	   var strChar;
	   var numberFlag = true;
	
	   if (strString.length == 0) return false;
	
	   //  test strString consists of valid characters listed above
	   for (i = 0; i < strString.length && numberFlag == true; i++)
	      {
	      strChar = strString.charAt(i);
	      if (strValidChars.indexOf(strChar) == -1)
	         {
	         numberFlag = false;
	         }
	      }
	   return numberFlag;
   }
   
   // function to trim the empty spaces on both sides.
	function trim(str) {
		return str.replace(/^\s+|\s+$/g,"");
	}
	
</script>

<div id="content">
<BODY onload="pageLoad()">
<FORM name="frmConfigDivision" method="post"><br>
<input type="hidden" id="userAction" name="userAction" /> 

<CENTER><font size="2"> <b> <fmt:message
	key="table.configDivision.header" /> </b> </font></CENTER>
<br>
<center><spring:hasBindErrors name="command">
	<c:forEach var="error" items="${errors.allErrors}">
		<h5 style="color: red;">${error.defaultMessage}</h5>
	</c:forEach>
</spring:hasBindErrors></center>
<div id="statusMessage" align="center">
<h5 style="color: green;">${command.message}</h5>
<h5 style="color: red;">${command.error}</h5>
</div>
<div id="filterCriteria" align="center">
<table cellpadding="0" cellspacing="0" width="60%" align="center"
	id="normalTableNostripe"
	style="white-space: nowrap; word-spacing: normal;">
	<tr>
	<div style="align:left; font size=6; width:550px">
				<b> Division: ${command.division} </b>
	</div>
	</tr>
	<br>
	<tr>
		<td style="vertical-align: middle; border-color: transparent;">
		<b><fmt:message key="table.configDivision.column1" /></b>
		</td>
		<td style="vertical-align: middle; border-color: transparent;">
		<input type="text" name="positiveFirstOp" id="positiveFirstOp" maxlength="3" size = "5"
		onkeypress="return checkValues(event)"></td>
	</tr>
	<tr>
		<td style="vertical-align: middle; border-color: transparent;">
		<b><fmt:message key="table.configDivision.column2" /></b>
		</td>
		<td style="vertical-align: middle; border-color: transparent;">
		<input type="text" name="negativeFirstOp" id="negativeFirstOp" maxlength="3" size = "5"
		onkeypress="return checkValues(event)"></td>
	</tr>
	<tr>	
		<td style="vertical-align: middle; border-color: transparent;">
		<b><fmt:message key="table.configDivision.column3" /></b>
		</td>
		<td style="vertical-align: middle; border-color: transparent;">
		<input type="text" name="positiveLastOp" id="positiveLastOp" maxlength="3" size = "5"
		onkeypress="return checkValues(event)"></td>
	</tr>
	<tr>	
		<td style="vertical-align: middle; border-color: transparent;">
		<b><fmt:message key="table.configDivision.column4" /></b>
		</td>
		<td style="vertical-align: middle; border-color: transparent;">
		<input type="text" name="negativeLastOp" id="negativeLastOp" maxlength="3" size = "5"
		onkeypress="return checkValues(event)"></td>
	</tr>
	<tr>	
		<td style="vertical-align: middle; border-color: transparent;">
		<b><fmt:message key="table.configDivision.column5" /></b>
		</td>
		<td style="vertical-align: middle; border-color: transparent;">
		<input type="text" name="positiveOtherOp" id="positiveOtherOp" maxlength="3" size = "5"
		onkeypress="return checkValues(event)"></td>
	</tr>
	<tr>	
		<td style="vertical-align: middle; border-color: transparent;">
		<b><fmt:message key="table.configDivision.column6" /></b>
		</td>
		<td style="vertical-align: middle; border-color: transparent;">
		<input type="text" name="negativeOtherOp" id="negativeOtherOp" maxlength="3" size = "5"
		onkeypress="return checkValues(event)"></td>
	</tr>
</table>
</div>
<br>
<br>
<br>
<table align="center">
<tr>
<td>
	<img alt="Confirm" src="static/images/Confirm.gif" onclick="saveVarianceDetails();" style="cursor: pointer;">
</td>
<td>
	<img alt="Cancel" src="static/images/cancel.gif" onclick="cancel();" style="cursor: pointer;">
</td>
</tr>
</table>
</FORM>
</BODY>
</div>