<html>
<script type="text/javascript" src='static/js/springxt.js'></script>
<script type="text/javascript" src='static/js/prototype.js'></script>
<script type="text/javascript"
	src='static/js/scriptaculous.js?load=effects,dragdrop'></script>
<script type="text/javascript">
function getProgramNames() {
	clearValues(new Array('statusMessage', 'programDetailsDiv', 'programNameId'));
	if (document.frmM3TransactionLog.dateProcessed.value != "") { 
		document.frmM3TransactionLog.userAction.value = 'getProgramNames';
		XT.doAjaxSubmit('displayProgramNames', this);
	}
}

function displayProgramDetails() {
	clearValues(new Array('statusMessage', 'programDetailsDiv', 'functionDiv'));
	if (document.frmM3TransactionLog.programName.selectedIndex != 0) {
		document.frmM3TransactionLog.userAction.value = 'getProgramDetails';
		XT.doAjaxSubmit('displayProgramDetails', this);
	}
}

function exportToExcel() {
	document.frmM3TransactionLog.userAction.value = 'exportToExcel';
	document.frmM3TransactionLog.submit();
	return false;
}

function clearValues(clearValues) {
	for (var i = 0; i < clearValues.length; i++) {
		document.getElementById(clearValues[i]).innerHTML = "";
	}
}

</script>
<form name="frmM3TransactionLog" method="post">
<input type="hidden" name="userAction">
<input type="hidden" name="tempDateProcessed">
<br>
<table width="100%"  border="0">
	<tr>
		<td align="center"><font size="2px"><b> M3 Transaction Log Report </b> </font></td>
	</tr>
</table>
<br>
<table style="border: 1px solid #465272;" align="center" cellpadding="3">

	<tr>
		<td style="font-weight: bold"> Date Processed : </td>
		<td><input type="text" id="dateProcessed" name="dateProcessed" size="10" 
			readonly="readonly">
		<A onmouseover="window.status  ='Date Picker';return true;"
			onmouseout="window.status='';return true;"
			href="javascript:cal1.popup();"> <IMG alt="Calendar"
			src="static/images/cal.gif" border="0"
			style="vertical-align: bottom;"></A></td>
		<td><img alt="Get Details" src="static/images/filter.gif" border="0" onclick="getProgramNames()" style="cursor: pointer;"></td>
	</tr>

</table>
<br>
<div id="programNameId"></div>
<div id="loadingStatus" style="position: absolute;left: 435px;top: 250px;"></div>
<table style="position: absolute;" width="100%" border="0">
	<tr>
		<td><div id="statusMessage" align="center"></div></td>
	</tr>
</table>
<br>

<div id="programDetailsDiv"></div>
<script language="JavaScript">
var cal1 = new calendar2(document.forms['frmM3TransactionLog'].elements['dateProcessed']);
cal1.year_scroll = true;
cal1.time_comp = false;
cal1.left = 400;
cal1.top = 300;
</script></form>
</html>