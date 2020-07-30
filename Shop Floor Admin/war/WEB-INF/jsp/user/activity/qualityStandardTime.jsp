<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
function checkValues(){
		if(document.qualityStandardTimeForm.sltTestType.value == ""){
			alert('Please Select Test Type.');
			document.qualityStandardTimeForm.sltTestType.focus();
			return false;
		}
		document.qualityStandardTimeForm.sltTestType.disabled = true;
		document.qualityStandardTimeForm.userAction.value='getDetails';
		document.getElementById('addStandardTime').innerHTML = "";
		document.getElementById('tableStandardTimeDetails').innerHTML = "";
		document.getElementById('addOrUpdateDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
		
		XT.doAjaxSubmit('getDetails',this);
}
function updateTestMethod(testMethodName, oldStandardTime){
	if(trim((document.getElementById('StandardTime').value)) == "") {
		alert('Please enter Standard Time');
		document.getElementById('StandardTime').focus();
		return false;
	}else if(checkNumeric(trim(document.getElementById('StandardTime').value)) == false) {
		alert('Please enter a numeric value');
		document.getElementById('StandardTime').focus();
		return false;
	}else if(document.getElementById('StandardTime').value > 1000 ) {
		alert('Standard time should be between 1 and 1000');
		document.getElementById('StandardTime').focus();
		return false;
	} 
	
		document.qualityStandardTimeForm.testMethodName.value = trim(testMethodName);
		document.qualityStandardTimeForm.stdTimeOld.value = trim(oldStandardTime);
		document.qualityStandardTimeForm.txtStdTime.value = trim(document.getElementById('StandardTime').value);
		document.qualityStandardTimeForm.userAction.value='updateTestMethod';
		document.getElementById('statusMessage').innerHTML = "";
		document.getElementById('addOrUpdateDiv').innerHTML = "";
		XT.doAjaxSubmit('updateTestMethod',this);

}

function deleteTestMethod(testMethodName, oldStandardTime){
	var a = confirm("Do you want to Continue deletion?");
		if(a) {
			document.qualityStandardTimeForm.userAction.value='deleteTestMethod';
			document.getElementById('testMethodName').value = trim(testMethodName);
			document.getElementById('stdTimeOld').value = trim(oldStandardTime);
			document.getElementById('statusMessage').innerHTML = "";
			document.getElementById('addOrUpdateDiv').innerHTML = "";
			XT.doAjaxSubmit('deleteTestMethod',this);
		} 
}

function addOrUpdateTestMethod(testMethodName, oldStandardTime){

	document.qualityStandardTimeForm.testMethodName.value = trim(testMethodName);
	document.qualityStandardTimeForm.stdTimeOld.value = trim(oldStandardTime);
	document.qualityStandardTimeForm.userAction.value='addOrUpdateTestMethod';
	document.getElementById('addStandardTime').innerHTML = "";
	document.getElementById('statusMessage').innerHTML = "";
	document.getElementById('addOrUpdateDiv').innerHTML = "";
	XT.doAjaxSubmit('addOrUpdateTestMethod',this);
}

function newStandardTime(){

	if(document.qualityStandardTimeForm.sltTestType.value == ""){
		alert('Please Select Test Type.');
		document.qualityStandardTimeForm.sltTestType.focus();
		return false;
	}
	document.qualityStandardTimeForm.sltTestType.disabled = true;
	document.qualityStandardTimeForm.testMethodName.value = "";
	document.getElementById('addStandardTime').innerHTML = "";
	document.getElementById('statusMessage').innerHTML = "";
	document.getElementById('addOrUpdateDiv').innerHTML = "";
	XT.doAjaxSubmit('addOrUpdateTestMethod',this);
}

function addStandardTime(){

	if(trim((document.getElementById('txtTestMethodName').value)) == "") {
		alert('Please enter Test Method Name');
		document.getElementById('txtTestMethodName').focus();
		return false;
	}else if(trim((document.getElementById('StandardTime').value)) == "") {
		alert('Please enter Standard Time');
		document.getElementById('StandardTime').focus();
		return false;
	}else if(checkNumeric(trim(document.getElementById('StandardTime').value)) == false) {
		alert('Please enter a numeric value');
		document.getElementById('StandardTime').focus();
		return false;
	}else if(document.getElementById('StandardTime').value > 1000 ) {
		alert('Standard time should be between 1 and 1000');
		document.getElementById('StandardTime').focus();
		return false;
	} 
		document.qualityStandardTimeForm.sltTestType.disabled = true;
		document.qualityStandardTimeForm.testMethodName.value = trim(document.getElementById('txtTestMethodName').value);
		document.qualityStandardTimeForm.txtStdTime.value = trim(document.getElementById('StandardTime').value);
		document.qualityStandardTimeForm.userAction.value='addStandardTime';
		document.getElementById('statusMessage').innerHTML = "";
		document.getElementById('addOrUpdateDiv').innerHTML = "";
		XT.doAjaxSubmit('addStandardTime',this);

}
function checkNumeric(strString) {
    //  check for valid numeric strings        
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
	
function Cancel(){
	checkValues()
	document.getElementById('addOrUpdateDiv').innerHTML = "";
}

function CancelReload(){
	document.location.href='qualityStandardTime.htm';
}

</script>
<div id="content">
<BODY>
<FORM name=qualityStandardTimeForm method=post class="form"><br>
	<input type="hidden" name=userAction>
	<input type="hidden" id="testMethodName" name="testMethodName">
	<input type="hidden" name="txtStdTime">
	<input type="hidden" name="stdTimeOld"> 
	<!-- <input type="hidden" name="oldStandardTime"> -->
<CENTER><font size="2"> <b> Quality Standard Time </b> </font>
</CENTER>
<br>
   <div id="statusMessage" align="center">&nbsp;</div>  
<br>
<div id="qualityStandardTime" align="center" style="position: static" >
<TABLE border="0" cellpadding="0" cellspacing="0" align="center"
	id="normalTableNostripe" style="position: static">
	<tr>
		<td align="left" style = "vertical-align: middle"><b>Select Test Type </b></td>
		<td class="centerAligned"><select name="sltTestType" style="width: 195px;">
			<c:forEach items="${command.testTypes}" var="data">
				<option value="${data}">${data}</option>
			</c:forEach>
		</select></td>
		<td class="centerAligned">
			<div id="filterButtonDiv">
				<img alt="Filter" src="static/images/filter.gif" onclick="javascript:checkValues();" style="cursor: pointer;">
			</div>
		</td>
		<td class="centerAligned">
			<div id="newStandardTime">
				<img alt="Add Standard Time" src="static/images/addStandardTime.gif" onclick="javascript:newStandardTime();" style="cursor: pointer;">
			</div>
		</td>
	</tr>
</TABLE>
</div>
<br>
<div id="addStandardTime" align="center" ></div>
<div id="tableStandardTimeDetails" align="center" ></div>
<br>
<br>
<div id="addOrUpdateDiv" align="center"></div>
<br>

</FORM>
</BODY>
</div>