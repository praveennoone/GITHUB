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
		getReasonDetails();
	}

	function getReasonDetails(){

		document.frmconfigReason.userAction.value='getReasonDetails';
		XT.doAjaxSubmit('getReasonDetails',this);

	}
	
	function checkValues(e) {
		if (e.keyCode == 13) {
			if (!getReasonDetails()) {
				return false;
			}
		}
	}
	
	function upperCase(x) {
		var y=document.getElementById(x).value
		document.getElementById(x).value=y.toUpperCase()
	}
	
	function getReasonFilterDetails() {
		
		document.frmconfigReason.userAction.value='getReasonFilterDetails';
		document.getElementById('reasonDetailDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
		XT.doAjaxSubmit('getReasonDetails',this);
	}
	
	function addNewReason(){
				
		if(trim(document.frmconfigReason.reason.value) == "") {
				alert("Please select Reason Type.");
				document.frmconfigReason.reason.focus();
				return false;
		}
		document.getElementById('reasonDetailDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";		
		document.frmconfigReason.userAction.value='addNewReason';
		XT.doAjaxSubmit('addNewReason',this);		
	}
	
	function insertReasonDetails(){

		document.frmconfigReason.description.value = trim(document.frmconfigReason.description.value);
		document.frmconfigReason.reason.value = trim(document.frmconfigReason.reason.value);
		
		if(document.frmconfigReason.reason.value == "") {
				alert("Please select Reason Type.");
				document.frmconfigReason.reason.focus();
				return false;
		}else if(document.frmconfigReason.description.value == "") {
				alert("Please Enter the Reason Description.");
				document.frmconfigReason.description.value = "";
				document.frmconfigReason.description.focus();
				return false;
		}
		document.frmconfigReason.reason.disabled = true;
		document.frmconfigReason.description.disabled = true;
		document.frmconfigReason.userAction.value='insertNewReason';
		XT.doAjaxSubmit('insertNewReason',this);
	}
	
	function cancel() {

		document.location.href='configReason.htm';		
		
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

	// function to trim the empty spaces on both sides.
	function trim(str) {
		return str.replace(/^\s+|\s+$/g,"");
	}
	
</script>
<div id="content">
<BODY onload="pageLoad()">
<FORM name="frmconfigReason" method="post"><br>
<input type="hidden" id="userAction" name="userAction" />
<CENTER><font size="2"> <b> <fmt:message
	key="table.configReason.header" /> </b> </font></CENTER>
<br>
<center><spring:hasBindErrors name="command">
	<c:forEach var="error" items="${errors.allErrors}">
		<h5 style="color: red;">${error.defaultMessage}</h5>
	</c:forEach>
</spring:hasBindErrors></center>


<div id="statusMessage" align="center">&nbsp;</div>
<br>
<div id="filterCriteria" align="center">
<table cellpadding="0" cellspacing="0" width="45%" align="center"
	id="normalTableNostripe"
	style="white-space: nowrap; word-spacing: normal;">
	<tr>
		<td class="centerAligned">
		<table cellpadding="0" cellspacing="0"
			style="border-color: transparent;">
			<tr>
				<td style="vertical-align: middle; border-color: transparent;">
				<b><fmt:message key="table.configReason.column1" /></b>
				</td>
				<td style="vertical-align: middle; border-color: transparent;">
				<select name="reason" id="reason">
					<option selected="selected"></option>
					<c:forEach items="${command.reasonType}" var="data">
						<option value="${data}">${data}</option>			
					</c:forEach>
				</select></td>
			</tr>
		</table>
		</td>
		<td class="centerAligned">
			<div id="filterButtonDiv">
				<img alt="Filter" src="static/images/filter.gif" onclick="getReasonFilterDetails();" style="cursor: pointer;">
			</div>
		</td>
		<td class="centerAligned">
			<div id="addNewButtonDiv">
			<img alt="Add New Reason" src="static/images/addNewReason.gif" onclick="addNewReason();" style="cursor: pointer;">
			</div>
		</td>
	</tr>
</table>
</div>
<br>
<br>
<div id="reasonDetailDiv" align="center"></div>
<br>
<div id="buttonsDiv" align="center" style="width: 100%;"></div>
</FORM>
</BODY>
</div>