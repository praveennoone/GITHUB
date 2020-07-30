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
		var workCenterCode = '${command.workCenterCode}';
		if (trim(workCenterCode) != "") {
			document.frmWorkCenterConfig.workCenterCode.value = trim(workCenterCode);

			for (i = 0; i < document.frmWorkCenterConfig.facility.length; i++) {
				if (document.frmWorkCenterConfig.facility[i].value == facility) {
					document.frmWorkCenterConfig.facility[i].selected = true;
					break;
				}
			}
		} else {
			document.frmWorkCenterConfig.workCenterCode.focus();
		}
	}

	function checkValues(e) {
		if (e.keyCode == 13) {
			if (!getWorkCenterConfigDetails()) {
				return false;
			}
		}
	}

	function upperCase(x) {
		var y=document.getElementById(x).value
		document.getElementById(x).value=y.toUpperCase()
	}
	
	function getWorkCenterConfigDetails() {

		document.frmWorkCenterConfig.facility.value = trim(document.frmWorkCenterConfig.facility.value);
		document.frmWorkCenterConfig.workCenterCode.value = trim(document.frmWorkCenterConfig.workCenterCode.value);
		if(document.frmWorkCenterConfig.workCenterCode.value == "" && document.frmWorkCenterConfig.facility.value ==""){
				alert("Please Enter Either Facility and/or Work Center.");
				document.frmWorkCenterConfig.facility.focus();
				return false;
		}
		if(document.frmWorkCenterConfig.workCenterCode.value != "") {
				if(checkNumeric(document.frmWorkCenterConfig.workCenterCode.value) == false) {
					alert("Work Center should be a Number");
					document.frmWorkCenterConfig.workCenterCode.focus();
					return false;   
				}	
		}	
		document.frmWorkCenterConfig.facility.disabled = true;
		document.frmWorkCenterConfig.workCenterCode.disabled = true;
	
		document.frmWorkCenterConfig.userAction.value='getWorkCenterConfigDetails';
		document.getElementById('workCenterDetailDiv').innerHTML = "";
		document.getElementById('editDiv').innerHTML = "";
		document.getElementById('buttonsDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
		XT.doAjaxSubmit('getWorkCenterConfigDetails',this);
	}

	function edit(id,workCenter,facility,desc,pph,scale,scrapCount,active){
		
		document.frmWorkCenterConfig.workCenter.value=trim(workCenter);
		document.frmWorkCenterConfig.fac.value=trim(facility);
		document.frmWorkCenterConfig.pphThreshold.value=trim(pph);
		document.frmWorkCenterConfig.scaleReq.value=trim(scale);
		document.frmWorkCenterConfig.workdesc.value=trim(desc);
		//below line is added for ticket 414799 by saikiran-verinon
		document.frmWorkCenterConfig.scrapCount.value=trim(scrapCount);
		document.frmWorkCenterConfig.activeStatus.value=trim(active);
		
		document.frmWorkCenterConfig.userAction.value='editWorkCenter';
		document.getElementById('editDiv').innerHTML = "";
		document.getElementById('buttonsDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
		XT.doAjaxSubmit('addNewWorkCenter',this);
	}
	function enableFields(){
	
		document.frmWorkCenterConfig.facility.disabled = false;
		document.frmWorkCenterConfig.workCenterCode.disabled = false;
	
	}
	function showNoDataMessage() {
		document.getElementById('workCenterDetailDiv').innerHTML = "";
		document.getElementById('editDiv').innerHTML = "";
		document.getElementById('buttonsDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
	}

	
	function cancel(flag) {
		if(flag == "true"){
			document.getElementById('editDiv').innerHTML = "";
			document.getElementById('buttonsDiv').innerHTML = "";
			document.getElementById('statusMessage').innerHTML = "";
			getWorkCenterConfigDetails();
		}else{
			document.location.href='workCenterConfig.htm';
		}		
		
	}
	
	function cancelReload() {	
			document.location.href='workCenterConfig.htm';	
	}
	
	function UpdateWorkCenterConfigDetails(workCenterNum,facility){

		document.frmWorkCenterConfig.description.value = trim(document.frmWorkCenterConfig.description.value);
		document.getElementById('pph_TextField').value = trim(document.getElementById('pph_TextField').value);
		if(document.frmWorkCenterConfig.description.value == ""){
				alert("Please Enter the Work Center Description.");
				document.frmWorkCenterConfig.description.focus();
				return false;
		}else if (checkNumeric(document.getElementById('pph_TextField').value) == false || document.getElementById('pph_TextField').value > 100) {
				alert("PPH Threshold value must be between 0 to 100");
				document.frmWorkCenterConfig.pph_TextField.focus();
				return false;   
		}
		document.frmWorkCenterConfig.workCenter.value = trim(workCenterNum) ;
		document.frmWorkCenterConfig.fac.value = trim(facility) ;
		document.frmWorkCenterConfig.userAction.value='updateWorkCenterConfigDetails';
		XT.doAjaxSubmit('updateWorkCenterConfigDetails',this);
		getWorkCenterConfigDetails();
	}
	
	function InsertNewWorkCenterDetails(){

		document.frmWorkCenterConfig.description.value = trim(document.frmWorkCenterConfig.description.value);
		document.getElementById('pph_TextField').value = trim(document.getElementById('pph_TextField').value);
		
		if(document.frmWorkCenterConfig.description.value == "") {
				alert("Please Enter the Work Center Description.");
				document.frmWorkCenterConfig.description.focus();
				return false;
		}else if (checkNumeric(document.getElementById('pph_TextField').value) == false || document.getElementById('pph_TextField').value > 100) {
				alert("PPH Threshold value must be between 0 to 100");
				document.frmWorkCenterConfig.pph_TextField.focus();
				return false;   
		}
		document.frmWorkCenterConfig.facility.disabled = true;
		document.frmWorkCenterConfig.workCenterCode.disabled = true;
		document.frmWorkCenterConfig.pph_TextField.disabled = true;
		document.frmWorkCenterConfig.description.disabled = true;
		document.frmWorkCenterConfig.scaleReqd_Chk.disabled = true;
		//below code is added for ticket 414799 by saikiran
		document.frmWorkCenterConfig.scrapCount_Chk.disabled = true;
		document.frmWorkCenterConfig.activeFlag_Chk.disabled = true;
		document.frmWorkCenterConfig.userAction.value='insertNewWorkCenterDetails';
		XT.doAjaxSubmit('insertNewWorkCenterDetails',this);
		getWorkCenterConfigDetails();
	}
	function addNewWorkCenter(){
				
		document.frmWorkCenterConfig.facility.value = trim(document.frmWorkCenterConfig.facility.value);
		document.frmWorkCenterConfig.workCenterCode.value = trim(document.frmWorkCenterConfig.workCenterCode.value);
		
		if(document.frmWorkCenterConfig.facility.value == "") {
				alert("Please select facility.");
				document.frmWorkCenterConfig.facility.focus();
				return false;
		}else if(document.frmWorkCenterConfig.workCenterCode.value == "") {
				alert("Please Enter Work center Number.");
				document.frmWorkCenterConfig.workCenterCode.focus();
				return false;
		}else if (checkNumeric(document.frmWorkCenterConfig.workCenterCode.value) == false) {
				alert("Work Center should be a Number");
				document.frmWorkCenterConfig.workCenterCode.focus();
				return false;   
		}
		document.frmWorkCenterConfig.facility.disabled = true;
		document.frmWorkCenterConfig.workCenterCode.disabled = true;
		document.getElementById('workCenterDetailDiv').innerHTML = "";
		document.getElementById('editDiv').innerHTML = "";
		document.getElementById('buttonsDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
		document.frmWorkCenterConfig.userAction.value='addNewWorkCenter';
		XT.doAjaxSubmit('addNewWorkCenter',this);
		
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
<FORM name="frmWorkCenterConfig" method="post"><br>
<input type="hidden" id="userAction" name="userAction" />
<input type="hidden" name="fac" />
<input type="hidden" name="workCenter" />
<input type="hidden" name="workdesc" />
<input type="hidden" name="pphThreshold" />
<input type="hidden" name="scaleReq" />
<input type="hidden" name="activeStatus" />
<input type="hidden" name="scrapCount" />
<CENTER><font size="2"> <b> <fmt:message
	key="table.workCenterConfig.header" /> </b> </font></CENTER>
<br>
<center><spring:hasBindErrors name="command">
	<c:forEach var="error" items="${errors.allErrors}">
		<h5 style="color: red;">${error.defaultMessage}</h5>
	</c:forEach>
</spring:hasBindErrors></center>
<div id="statusMessage" align="center">&nbsp;</div>
<br>
<div id="filterCriteria" align="center">
<table cellpadding="0" cellspacing="0" width="63%" align="center"
	id="normalTableNostripe"
	style="white-space: nowrap; word-spacing: normal;">
	<tr>
		<td class="centerAligned">
		<table cellpadding="0" cellspacing="0"
			style="border-color: transparent;">
			<tr>
				<td style="vertical-align: middle; border-color: transparent;">
				<b><fmt:message key="table.workCenterConfig.column1" /></b>
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
				<b><fmt:message key="table.workCenterConfig.column2" /></b>
				</td>
				<td style="vertical-align: middle; border-color: transparent;">
				<input type="text" name="workCenterCode" id="workCenterCode" maxlength="8"
					value="${command.workCenterCode}"
					onkeypress="return checkValues(event)"></td>
			</tr>
		</table>
		</td>
		<td class="centerAligned">
			<div id="filterButtonDiv">
				<img alt="Filter" src="static/images/filter.gif" onclick="getWorkCenterConfigDetails();" style="cursor: pointer;">
			</div>
		</td>
		<td class="centerAligned">
			<div id="addNewButtonDiv">
			<img alt="Add New Work Center" src="static/images/addNewWorkCenter.gif" onclick="addNewWorkCenter();" style="cursor: pointer;">
			</div>
		</td>
	</tr>
</table>
</div>
<br>
<br>
<div id="workCenterDetailDiv" align="center"></div>
<br>
<div id="editDiv" align="center"></div>
<br>
<div id="buttonsDiv" align="center" style="width: 100%;"></div>
</FORM>
</BODY>
</div>