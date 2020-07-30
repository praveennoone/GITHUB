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
		var facility = '${command.facility}';
		var moNumber = '${command.moNumber}';
		var selectedSequenceNumber = '${command.selectedSequenceNumber}';
		if (moNumber != "") {
			document.frmCorrectionInterfaceModifyMO.moNumber.value = moNumber;
			document.frmCorrectionInterfaceModifyMO.currSequence.value = selectedSequenceNumber;
			
			for (i = 0; i < document.frmCorrectionInterfaceModifyMO.facility.length; i++) {
				if (document.frmCorrectionInterfaceModifyMO.facility[i].value == facility) {
					document.frmCorrectionInterfaceModifyMO.facility[i].selected = true;
					break;
				}
			}
			displaySequenceDetails();
		} else {
			document.frmCorrectionInterfaceModifyMO.moNumber.focus();
		}
	}

	function checkValues(e) {
		if (e.keyCode == 13) {
			if (!getMODetails()) {
				return false;
			}
		}
	}

	function upperCase(x) {
		var y=document.getElementById(x).value
		document.getElementById(x).value=y.toUpperCase()
	}
	
	function getMODetails() {

		if(	document.frmCorrectionInterfaceModifyMO.facility.value == "") {
			alert("Please select facility.");
				document.frmCorrectionInterfaceModifyMO.facility.focus();
				return false;
			}
		 else if(	document.frmCorrectionInterfaceModifyMO.moNumber.value == "") {
			alert("Please Enter MO Number.");
				document.frmCorrectionInterfaceModifyMO.moNumber.focus();
				return false;
			}else if (specialCharValidate(document.frmCorrectionInterfaceModifyMO.moNumber.value) == false) {
				alert("Special characters are not allowed here");
				document.frmCorrectionInterfaceModifyMO.moNumber.focus();
				return false;   
			}				
				document.frmCorrectionInterfaceModifyMO.userAction.value='getMODetails';
				document.getElementById('MODetailsDiv').innerHTML = "";
				document.getElementById('tableMODetails').innerHTML = "<img align='middle' style='vertical-align: middle;' src='static/images/ajax-loader.gif'>";
				document.getElementById('buttonsDiv').innerHTML = "";
				document.getElementById('statusMessage').innerHTML = "";
				document.getElementById('tableMOInfo').innerHTML = "";
				
				// document.frmCorrectionInterfaceModifyMO.submit();
				XT.doAjaxSubmit('getMODetails',this);
	}

	function editSequenceDetails(rowNo, flag) {
		// alert(" button clicked edit");	
		document.frmCorrectionInterfaceModifyMO.userAction.value='displayActivityDetails';
		// alert(rowNo);
		document.frmCorrectionInterfaceModifyMO.currSequence.value = rowNo;
		// alert(document.frmCorrectionInterfaceModifyMO.currSequence.value);
		document.frmCorrectionInterfaceModifyMO.newFlag.value = flag;
		document.getElementById('MODetailsDiv').innerHTML = "";
		document.getElementById('tableMODetails').innerHTML = "<img align='middle' style='vertical-align: middle;' src='static/images/ajax-loader.gif'>";
		document.getElementById('buttonsDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
		document.getElementById('tableMOInfo').innerHTML = "";
		// document.frmCorrectionInterfaceModifyMO.submit();
		XT.doAjaxSubmit('displayActivityDetails',this);
		}		

	function displaySequenceDetails(flag) {
		// alert(" button clicked edit");	
		document.frmCorrectionInterfaceModifyMO.userAction.value='displayActivityDetails';
		document.frmCorrectionInterfaceModifyMO.moDetailsNeeded.value='Y';
		// alert(rowNo);
		
		// alert(document.frmCorrectionInterfaceModifyMO.currSequence.value);
		document.frmCorrectionInterfaceModifyMO.newFlag.value = flag;
		document.getElementById('MODetailsDiv').innerHTML = "";
		document.getElementById('tableMODetails').innerHTML = "<img align='middle' style='vertical-align: middle;' src='static/images/ajax-loader.gif'>";
		document.getElementById('buttonsDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
		document.getElementById('tableMOInfo').innerHTML = "";
		// document.frmCorrectionInterfaceModifyMO.submit();
		XT.doAjaxSubmit('displayActivityDetails',this);
		}		
	
	function editActivityDetails(index) {
		// alert(" button clicked edit");	
		document.frmCorrectionInterfaceModifyMO.userAction.value='editActivityDetails';
		// alert(index);
		document.frmCorrectionInterfaceModifyMO.currSeqActivityIndex.value = index;
		// alert(document.frmCorrectionInterfaceModifyMO.currSeqActivityIndex.value);
		document.frmCorrectionInterfaceModifyMO.newFlag.value = 'N';
		document.getElementById('MODetailsDiv').innerHTML = "";
		document.getElementById('tableMODetails').innerHTML = "<img align='middle' style='vertical-align: middle;' src='static/images/ajax-loader.gif'>";
		document.getElementById('buttonsDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
		document.getElementById('tableMOInfo').innerHTML = "";
		// document.frmCorrectionInterfaceModifyMO.submit();
		XT.doAjaxSubmit('editActivityDetails',this);
	}

	
	function showNoDataMessage() {
		document.getElementById('MODetailsDiv').innerHTML = "";
		document.getElementById('tableMODetails').innerHTML = "<font color = 'RED'> No Data Found </font>";
		document.getElementById('buttonsDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
		document.getElementById('tableMOInfo').innerHTML = "";
	}

	function addMissingInfo() {
		// alert("add missing info");
		/// alert(" button clicked edit");	
		document.frmCorrectionInterfaceModifyMO.userAction.value='addMissingInfoDetails';
		
		document.frmCorrectionInterfaceModifyMO.newFlag.value = 'Y';
		// alert(document.frmCorrectionInterfaceModifyMO.newFlag.value);
		document.getElementById('MODetailsDiv').innerHTML = "";
		document.getElementById('tableMODetails').innerHTML = "<img align='middle' style='vertical-align: middle;' src='static/images/ajax-loader.gif'>";
		document.getElementById('buttonsDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
		document.getElementById('tableMOInfo').innerHTML = "";
		// document.frmCorrectionInterfaceModifyMO.submit();
		XT.doAjaxSubmit('addMissingInfoDetails',this);
	}
	
	function confirmValues(newFlag, currentSeqCompletedQty, sequnceQty) {
		
		if (document.frmCorrectionInterfaceModifyMO.activity.value != "S") {
			if ((parseInt(currentSeqCompletedQty) + parseInt(document.frmCorrectionInterfaceModifyMO.qtyCompleted.value)) > sequnceQty) {
				var answer = confirm("Completed Qty Greater Than The Order Qty / Previous Seq. Completed Qty. Do You Want To Continue?");
				if (!answer) {
					return false;
				}		
			}
		}

			 // alert("confirmed!")
			document.frmCorrectionInterfaceModifyMO.userAction.value='confirmActivityDetails';
			// alert(newFlag);
			// alert(newFlag);
			setValues(newFlag);
			// alert(document.frmCorrectionInterfaceModifyMO.newFlag.value);
			  if (newFlag == 'Y' && document.frmCorrectionInterfaceModifyMO.activity.value == 'P'){
			 if( document.frmCorrectionInterfaceModifyMO.reasonCode.value == "") {
				alert("Please enter reason code");
				document.frmCorrectionInterfaceModifyMO.reasonCode.focus();
			   } 
			 } //else {
			if(validate(newFlag)) {
				var answer = confirm("please confirm the modifications");
				if (answer) {
					// document.frmCorrectionInterfaceModifyMO.reasonCode1.value = document.frmCorrectionInterfaceModifyMO.reasonCode.value;
					document.getElementById('MODetailsDiv').innerHTML = "";
					document.getElementById('tableMODetails').innerHTML = "<img align='middle' style='vertical-align: middle;' src='static/images/ajax-loader.gif'>";
					document.getElementById('buttonsDiv').innerHTML = "";
					document.getElementById('statusMessage').innerHTML = "";
					document.getElementById('tableMOInfo').innerHTML = "";
					// document.frmCorrectionInterfaceModifyMO.submit();
					XT.doAjaxSubmit('displayActivityDetails',this);
				} else {
				}
				
			}		
			 //}
		  
	}
	function setValues(newFlag) {
		document.frmCorrectionInterfaceModifyMO.newFlag.value = newFlag;
		document.frmCorrectionInterfaceModifyMO.loginTime1.value = document.frmCorrectionInterfaceModifyMO.loginTime.value;
		document.frmCorrectionInterfaceModifyMO.logoffTime1.value = document.frmCorrectionInterfaceModifyMO.logoffTime.value;
		document.frmCorrectionInterfaceModifyMO.assetNo1.value = document.frmCorrectionInterfaceModifyMO.assetNo.value;
		document.frmCorrectionInterfaceModifyMO.activity1.value = document.frmCorrectionInterfaceModifyMO.activity.value;
		document.frmCorrectionInterfaceModifyMO.qtyCompleted1.value = trim(document.frmCorrectionInterfaceModifyMO.qtyCompleted.value);
		if(document.frmCorrectionInterfaceModifyMO.seqCompFlag.checked) {
			document.frmCorrectionInterfaceModifyMO.seqCompFlag1.value = 1;
		} else {
			document.frmCorrectionInterfaceModifyMO.seqCompFlag1.value = 0;
		}
		
		if (newFlag == 'Y') {
			// alert("in emp set values");
			document.frmCorrectionInterfaceModifyMO.stampNo1.value = trim(document.frmCorrectionInterfaceModifyMO.stampNo.value);
			document.frmCorrectionInterfaceModifyMO.date1.value = document.frmCorrectionInterfaceModifyMO.date.value;
			document.frmCorrectionInterfaceModifyMO.employeeNo1.value = trim(document.frmCorrectionInterfaceModifyMO.employeeNo.value);
			
		}	
	}
	
	function validate(newFlag) {
		// do js validations - datatype, mandatory, logon time, logofftime less than check and so on
		var loginTime = document.frmCorrectionInterfaceModifyMO.loginTime.value;
		var logoffTime = document.frmCorrectionInterfaceModifyMO.logoffTime.value;

		// alert(new Date(loginTime));
		// alert(new Date(logoffTime));
		
		var flag = false;

		
		if(loginTime == "") {
			alert("Enter Logon Date");
			document.frmCorrectionInterfaceModifyMO.loginTime.focus();
		} else if (logoffTime == "") {
			alert("Enter Logoff Date");
			document.frmCorrectionInterfaceModifyMO.logoffTime.focus();
		} else if (document.frmCorrectionInterfaceModifyMO.activity.value == "") {
			alert("Select activity");
			document.frmCorrectionInterfaceModifyMO.activity.focus();
		} else if (document.frmCorrectionInterfaceModifyMO.assetNo.value == "" && 
				document.frmCorrectionInterfaceModifyMO.activity.value == "S") {
			alert("Enter assetNo");
			document.frmCorrectionInterfaceModifyMO.assetNo.focus();
		} else if (!document.frmCorrectionInterfaceModifyMO.activity.value == "S" && 
			document.frmCorrectionInterfaceModifyMO.qtyCompleted.value == "") {
				alert("Enter qty Completed");
				document.frmCorrectionInterfaceModifyMO.qtyCompleted.focus();
		} else if (!document.frmCorrectionInterfaceModifyMO.activity.value == "S" &&
			isNaN(document.frmCorrectionInterfaceModifyMO.qtyCompleted.value)) {
			 	alert("Enter Valid Quantity");
			 	document.frmCorrectionInterfaceModifyMO.qtyCompleted.focus();
		} else if (document.frmCorrectionInterfaceModifyMO.activity.value == "S" && 
			!(trim(document.frmCorrectionInterfaceModifyMO.qtyCompleted.value) == "0" ||  
			document.frmCorrectionInterfaceModifyMO.qtyCompleted.value == "")) {
				alert("Quantity Is Not Needed For The Setup Activity");
				document.frmCorrectionInterfaceModifyMO.qtyCompleted.value = trim(document.frmCorrectionInterfaceModifyMO.qtyCompleted.value);
				document.frmCorrectionInterfaceModifyMO.qtyCompleted.focus();
		} else if (newFlag == 'Y' && trim(document.frmCorrectionInterfaceModifyMO.stampNo.value) == "") {
			 alert("Enter Stamp No");
			 document.frmCorrectionInterfaceModifyMO.stampNo.focus();
		 } else if (newFlag == 'Y' && document.frmCorrectionInterfaceModifyMO.date.value == "" ) {
			 alert("Enter Router Date");
			 document.frmCorrectionInterfaceModifyMO.date.focus();
		 } else if (newFlag == 'Y' && document.frmCorrectionInterfaceModifyMO.employeeNo.value == "") {
			 alert ("Enter Employee Number");
			 document.frmCorrectionInterfaceModifyMO.employeeNo.focus();				
		} else if (newFlag == 'Y' && isNaN(document.frmCorrectionInterfaceModifyMO.employeeNo.value)) {
			 	alert("Enter Valid Employee");
			 	document.frmCorrectionInterfaceModifyMO.employeeNo.focus();
		}else if(new Date(logoffTime.replace(/-/g, '/')) > new Date()) {
			alert("Future date not allowed");
			document.frmCorrectionInterfaceModifyMO.logoffTime.focus();
		}else if(new Date(loginTime.replace(/-/g, '/')) > new Date()) {
			alert("Future date not allowed");
			document.frmCorrectionInterfaceModifyMO.loginTime.focus();
		} else if (new Date(logoffTime.replace(/-/g, '/')) <= new Date(loginTime.replace(/-/g, '/'))) {
				alert("Logon Date should be less than Log off date");
				document.frmCorrectionInterfaceModifyMO.loginTime.focus();
			}
			else{
			flag = true;
		}

		// date check
		//if(loginTime.getTime() > logoffTime.getTime())  {
		//	alert("Logon Date should be less than Log off date");
		//}
		return flag;
	}

	function cancel() {
		// alert("in cancel");

		// document.frmCorrectionInterfaceModifyMO.userAction.value='getMODetails';
		document.getElementById('MODetailsDiv').innerHTML = "";
		document.getElementById('tableMODetails').innerHTML = "<img align='middle' style='vertical-align: middle;' src='static/images/ajax-loader.gif'>";
		document.getElementById('buttonsDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
		document.getElementById('tableMOInfo').innerHTML = "";
		
		// document.frmCorrectionInterfaceModifyMO.submit();
		XT.doAjaxAction('displayMODetails',this);
	}

	function cancelActDetails() {
		
		document.getElementById('MODetailsDiv').innerHTML = "";
		document.getElementById('tableMODetails').innerHTML = "<img align='middle' style='vertical-align: middle;' src='static/images/ajax-loader.gif'>";
		document.getElementById('buttonsDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
		document.getElementById('tableMOInfo').innerHTML = "";

		
		// document.frmCorrectionInterfaceModifyMO.submit();
		XT.doAjaxAction('displayActivityDetails',this);
	}
	
		
	function checkCheckBox(options) {
		//alert("checkCheckBox...");
		var chkBoxName = options.chkBoxName;
		//alert("chkBoxName = "+chkBoxName);
		document.getElementById(chkBoxName).checked = true;
	}

	function unCheckCheckBox(options) {
		//alert("unCheckCheckBox...");		
		var chkBoxName = options.chkBoxName;
		//alert("chkBoxName = "+chkBoxName);
		document.getElementById(chkBoxName).checked = false;
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

	
	function assetChange() {
		// alert("aset changed");
		if (document.frmCorrectionInterfaceModifyMO.assetNo.value == "") {
			document.frmCorrectionInterfaceModifyMO.assetDesc.value="";
		} 

		document.frmCorrectionInterfaceModifyMO.userAction.value="displayActivityChangeInfo";
		// validate the values bef setting
		// if  (validate(document.frmCorrectionInterfaceModifyMO.newFlag.value)) {
			
			// set the values of the screen and then do the AJAX Action
			setValues(document.frmCorrectionInterfaceModifyMO.newFlag.value);

			// alert(document.frmCorrectionInterfaceModifyMO.newFlag.value);
			// do ajax submit to populate asset desc on to the screen

			XT.doAjaxAction('displayAssetDesc',this);
		// }
	}


	function activityChange() {
		document.getElementById('reasonContainer').innerHTML = "";
	
		document.frmCorrectionInterfaceModifyMO.assetNo.disabled = false;
		if (document.frmCorrectionInterfaceModifyMO.activity.value != "S") {
			document.frmCorrectionInterfaceModifyMO.assetNo.disabled = true;
			document.frmCorrectionInterfaceModifyMO.assetNo.selectedIndex = 0;
			document.frmCorrectionInterfaceModifyMO.assetDesc.value = "";
		}
		
		if (document.frmCorrectionInterfaceModifyMO.activity.value == "") {
			alert("Please select activity");
			document.frmCorrectionInterfaceModifyMO.activity.focus();
		} else if (document.frmCorrectionInterfaceModifyMO.activity.value == "P") {
			// validate the values bef setting
			//if  (validate('P')) {
				// 				
				// set the values of the screen and then do the AJAX Action
				document.frmCorrectionInterfaceModifyMO.userAction.value="displayActivityChangeInfo";
				
				setValues(document.frmCorrectionInterfaceModifyMO.newFlag.value);
				// do ajax submit to populate asset desc on to the screen
				XT.doAjaxAction('displayReasons',this);
			// }
		} else {
			document.frmCorrectionInterfaceModifyMO.activity1.value = document.frmCorrectionInterfaceModifyMO.activity.value;
		}
	}
	

	function employeeChange() {
		// alert("emp changed" );
		
			if(isNaN(document.frmCorrectionInterfaceModifyMO.employeeNo.value)) {
			 	alert("Enter Valid Employee");
			 	document.frmCorrectionInterfaceModifyMO.employeeNo.focus();
			} else {
				// 				
				// set the values of the screen and then do the AJAX Action
				document.frmCorrectionInterfaceModifyMO.userAction.value="displayActivityChangeInfo";
				setValues(document.frmCorrectionInterfaceModifyMO.newFlag.value);
				
				// do ajax submit to populate asset desc on to the screen
				// alert(document.frmCorrectionInterfaceModifyMO.newFlag.value);
				XT.doAjaxSubmit('displayActivityChangeInfo',this);
			}
			
	}
	
	// function to trim the empty spaces on both sides.
	function trim(str) {
		return str.replace(/^\s+|\s+$/g,"");
	}
	
	function validateCompleteSequence() {
		document.getElementById('statusMessage').innerHTML = "";
		document.frmCorrectionInterfaceModifyMO.userAction.value="validateCompleteSequence";
		XT.doAjaxSubmit('validateCompleteSequence', this);	
	}	
	
	function uncheckCompleteSequence() {
		document.frmCorrectionInterfaceModifyMO.seqCompFlag.checked = false;
	}
</script>

<div id="content">
<BODY onload="pageLoad()">
<FORM name="frmCorrectionInterfaceModifyMO" method="post"><br>
<input type="hidden" id="userAction" name="userAction" /> 
<input type="hidden" id="currSequence" name="currSequence" /> 
<input type="hidden" id="currSeqActivityIndex" name="currSeqActivityIndex"></input>
<input type="hidden" id="loginTime1" name="loginTime1" />
<input type="hidden" id="newFlag" name="newFlag" />
 <input type="hidden" id="logoffTime1" name="logoffTime1" /> 
 <input type="hidden" id="activity1" name="activity1" /> 
 <input type="hidden" id="qtyCompleted1" name="qtyCompleted1" /> 
 <input type="hidden" id="assetNo1" name="assetNo1" />
 <input type="hidden" id="stampNo1" name="stampNo1" /> 
<input type="hidden" id="date1" name="date1" /> 
<input type="hidden" id="employeeNo1" name="employeeNo1" />
<input type="hidden" id="seqCompFlag1" name="seqCompFlag1" />
<input type="hidden" id="reasonCode1" name="reasonCode1" />
<input type="hidden" id="moDetailsNeeded" name="moDetailsNeeded" />



<CENTER><font size="2"> <b> <fmt:message
	key="table.correctionInterfaceModifyMO.header" /> </b> </font></CENTER>
<br>
<center><spring:hasBindErrors name="command">
	<c:forEach var="error" items="${errors.allErrors}">
		<h5 style="color: red;">${error.defaultMessage}</h5>
	</c:forEach>
</spring:hasBindErrors></center>
<div id="statusMessage" align="center">&nbsp;</div>
<div id="filterCriteria" align="center">
<table cellpadding="0" cellspacing="0" width="60%" align="center"
	id="normalTableNostripe"
	style="white-space: nowrap; word-spacing: normal;">
	<tr>
		<td class="centerAligned">
		<table cellpadding="0" cellspacing="0"
			style="border-color: transparent;">
			<tr>
				<td style="vertical-align: middle; border-color: transparent;">
				<b><fmt:message key="table.correctionInterfaceModifyMO.column1" /></b>
				</td>
				<td style="vertical-align: middle; border-color: transparent;">
				<select name="facility" id="facility">
					<option selected="selected"></option>
					<c:forEach items="${command.divisionFacilities}" var="data">
						<option
							<c:if test="${command.facility eq data.facilityId}"> <c:out value="selected"/></c:if>
							value="${data.facilityId}">${data.facilityId}</option>
					</c:forEach>
				</select></td>
			</tr>
		</table>
		</td>
		<td class="centerAligned">
		<table cellpadding="0" cellspacing="0"
			style="border-color: transparent;">
			<tr>
				<td style="vertical-align: middle; border-color: transparent;">
				<b><fmt:message key="table.correctionInterfaceModifyMO.column2" /></b>
				</td>
				<td style="vertical-align: middle; border-color: transparent;">
				<!-- The max length of MO Number has been changed from 15 to 7. 
				This is done to prevent the user from entering special characters by mistake.
				This change was done for WO 27714  -->
				<input type="text" name="moNumber" id="moNumber" maxlength="7"
					value="${command.moNumber}" style="text-transform: uppercase;" onblur="this.value=this.value.toUpperCase();"
					onkeypress="return checkValues(event)"></td>
			</tr>
		</table>
		</td>
		<td class="centerAligned">
			<img alt="Filter" src="static/images/filter.gif" onclick="getMODetails();" style="cursor: pointer;">
		</td>

	</tr>
</table>

</div>
<div id="tableMOInfo" align="center"></div>
<br>
<div id="tableMODetails" align="center"></div>
<br>
<div id="MODetailsDiv" align="center"></div>
<br>
<div id="buttonsDiv" align="center" style="width: 100%;"></div>
</FORM>
</BODY>
</div> 