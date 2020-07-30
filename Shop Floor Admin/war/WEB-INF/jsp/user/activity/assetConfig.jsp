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
			document.frmAssetConfig.workCenterCode.value = trim(workCenterCode);

			for (i = 0; i < document.frmAssetConfig.facility.length; i++) {
				if (document.frmAssetConfig.facility[i].value == facility) {
					document.frmAssetConfig.facility[i].selected = true;
					break;
				}
			}
		} else {
			document.frmAssetConfig.workCenterCode.focus();
		}
	}

	function checkValues(e) {
		if (e.keyCode == 13) {
			if (!getAssetConfigDetails()) {
				return false;
			}
		}
	}

	function upperCase(x) {
		var y=document.getElementById(x).value
		document.getElementById(x).value=y.toUpperCase()
	}
	
	function getAssetConfigDetails() {

		document.frmAssetConfig.facility.value = trim(document.frmAssetConfig.facility.value);
		document.frmAssetConfig.workCenterCode.value = trim(document.frmAssetConfig.workCenterCode.value);
		
		if(document.frmAssetConfig.facility.value == "" && document.frmAssetConfig.workCenterCode.value == ""){
				alert("Please Enter Either Facility and/or Work Center.");
				document.frmAssetConfig.facility.focus();
				return false;
		}
		/* if(document.frmAssetConfig.workCenterCode.value != "") {
			if(checkNumeric(document.frmAssetConfig.workCenterCode.value) == false){
					alert("Work Center should be a Number");
					document.frmAssetConfig.facility.focus();
					return false;
				}
		}		 */
		if(document.frmAssetConfig.workCenterCode.value != "") {
			if(specialCharValidate(document.frmAssetConfig.workCenterCode.value) == false){
					alert("Work Center should not contain special symbols");
					document.frmAssetConfig.facility.focus();
					return false;
				}
		}				
		document.frmAssetConfig.facility.disabled = true;
		document.frmAssetConfig.workCenterCode.disabled = true;
	
		document.frmAssetConfig.userAction.value='getAssetConfigDetails';
		document.getElementById('assetDetailDiv').innerHTML = "";
		document.getElementById('editDiv').innerHTML = "";
		document.getElementById('buttonsDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
		XT.doAjaxSubmit('getAssetConfigDetails',this);
	}

	function edit(id,workCenter,facility,desc,asset,status){

		document.frmAssetConfig.workCenter.value=trim(workCenter);
		document.frmAssetConfig.fac.value=trim(facility);
		document.frmAssetConfig.description.value=trim(desc);
		document.frmAssetConfig.asset.value=trim(asset);
		document.frmAssetConfig.status.value=status;
		
		document.frmAssetConfig.userAction.value='editAsset';
		document.getElementById('editDiv').innerHTML = "";
		document.getElementById('buttonsDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
		XT.doAjaxSubmit('addNewAsset',this);
	}
	function enableFields(){
	
		document.frmAssetConfig.facility.disabled = false;
		document.frmAssetConfig.workCenterCode.disabled = false;
	
	}
	
	function showNoDataMessage() {
		document.getElementById('workCenterDetailDiv').innerHTML = "";
		document.getElementById('buttonsDiv').innerHTML = "";
		document.getElementById('editDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
	}

	function cancel(flag) {

		if(flag == "true"){
			document.getElementById('editDiv').innerHTML = "";
			document.getElementById('buttonsDiv').innerHTML = "";
			document.getElementById('statusMessage').innerHTML = "";
			getAssetConfigDetails();
		}else{
			document.location.href='assetConfiguration.htm';		
		}
	}
	
	function cancelReload() {	
			document.location.href='assetConfiguration.htm';	
	}
	
	function UpdateAssetConfigDetails(workCenterNum,facility,asset){

		document.frmAssetConfig.descrip.value = trim(document.frmAssetConfig.descrip.value);
		if(document.frmAssetConfig.descrip.value == "") {
				alert("Please Enter the Asset Description.");
				document.frmAssetConfig.descrip.focus();
				return false;
		}	
		document.frmAssetConfig.workCenter.value = trim(workCenterNum) ;
		document.frmAssetConfig.fac.value = trim(facility) ;
		document.frmAssetConfig.asset.value = trim(asset) ;
		document.frmAssetConfig.userAction.value='updateAssetConfigDetails';
		XT.doAjaxSubmit('updateAssetConfigDetails',this);
		getAssetConfigDetails();
		
	}
	
	function InsertNewAssetDetails(){

		document.frmAssetConfig.assetNumber.value = trim(document.frmAssetConfig.assetNumber.value);
		document.frmAssetConfig.descrip.value = trim(document.frmAssetConfig.descrip.value);
		
		if(document.frmAssetConfig.assetNumber.value == "") {
				alert("Please Enter the Asset Number.");
				document.frmAssetConfig.assetNumber.focus();
				return false;
		}else if(document.frmAssetConfig.descrip.value == "") {
				alert("Please Enter the Asset Description.");
				document.frmAssetConfig.descrip.focus();
				return false;
		}
		document.frmAssetConfig.facility.disabled = true;
		document.frmAssetConfig.workCenterCode.disabled = true;
		document.frmAssetConfig.assetNumber.disabled = true;
		document.frmAssetConfig.descrip.disabled = true;
		document.frmAssetConfig.statusFlag_Chk.disabled = true;
		document.frmAssetConfig.userAction.value='insertNewAssetDetails';
		XT.doAjaxSubmit('insertNewAssetDetails',this);
		
		/*else if (checkNumeric(document.frmAssetConfig.assetNumber.value) == false) {
				alert("Asset Number should be Number");
				document.frmAssetConfig.assetNumber.focus();
				return false;   
		}*/
	}
	function addNewAsset(){
				
		document.frmAssetConfig.facility.value = trim(document.frmAssetConfig.facility.value);
		document.frmAssetConfig.workCenterCode.value = trim(document.frmAssetConfig.workCenterCode.value);
		
		if(	document.frmAssetConfig.facility.value == "") {
				alert("Please select facility.");
				document.frmAssetConfig.facility.focus();
				return false;
		}else if(document.frmAssetConfig.workCenterCode.value == "") {
				alert("Please Enter Work center Number.");
				document.frmAssetConfig.workCenterCode.focus();
				return false;
		}else if (checkNumeric(document.frmAssetConfig.workCenterCode.value) == false) {
				alert("Work Center should be a Number");
				document.frmAssetConfig.workCenterCode.focus();
				return false;   
		}
		document.frmAssetConfig.facility.disabled = true;
		document.frmAssetConfig.workCenterCode.disabled = true;
		document.getElementById('assetDetailDiv').innerHTML = "";
		document.getElementById('buttonsDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
		document.getElementById('editDiv').innerHTML = "";
		document.frmAssetConfig.userAction.value='addNewAsset';
		XT.doAjaxSubmit('addNewAsset',this);
		
	}
		
	function enableAssetFields(){
	
		document.frmAssetConfig.assetNumber.disabled = false;
		document.frmAssetConfig.descrip.disabled = false;
		document.frmAssetConfig.statusFlag_Chkbox.disabled = false;
	
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
<FORM name="frmAssetConfig" method="post"><br>
<input type="hidden" id="userAction" name="userAction" />
<input type="hidden" name="fac" />
<input type="hidden" name="workCenter" />
<input type="hidden" name="description" />
<input type="hidden" name="asset" />
<input type="hidden" name="status" />
<CENTER><font size="2"> <b> <fmt:message
	key="table.assetConfig.header" /> </b> </font></CENTER>
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
				<b><fmt:message key="table.assetConfig.column1" /></b>
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
				<b><fmt:message key="table.assetConfig.column2" /></b>
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
				<img alt="Filter" src="static/images/filter.gif" onclick="getAssetConfigDetails();" style="cursor: pointer;">
			</div>
		</td>
		<td class="centerAligned">
			<div id="addNewButtonDiv">
			<img alt="Add New Asset" src="static/images/addNewAsset.gif" onclick="addNewAsset();" style="cursor: pointer;">
			</div>
		</td>
	</tr>
</table>
</div>
<br>
<br>
<div id="assetDetailDiv" align="center"></div>
<br>
<div id="editDiv" align="center"></div>
<br>
<div id="buttonsDiv" align="center" style="width: 100%;"></div>
</FORM>
</BODY>
</div>