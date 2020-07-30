<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src='static/js/springxt.js'></script>
<script type="text/javascript" src='static/js/prototype.js'></script>
<script type="text/javascript" src='static/js/scriptaculous.js?load=effects,dragdrop'></script>
<script type="text/javascript" src='static/js/wz_tooltip.js'></script>

<script type="text/javascript">

	function checkValues(e){
		var key=e.keyCode || e.which;
		if (key==13){
			getErrorLog();
		}
	}
	
	function getErrorLog() {	
		if(	document.frmPPHCorrectionInterface.moNumber.value == "") {
			alert("Please Enter MO Number.");
				document.frmPPHCorrectionInterface.moNumber.focus();
			} else {
				document.frmPPHCorrectionInterface.userAction.value='getErrorActivities';
				document.getElementById('errorLogDetailsDiv').innerHTML = "<img align='middle' style='vertical-align: middle;' src='static/images/ajax-loader.gif'>";
				document.getElementById('tableM3ItemDetails').innerHTML = "";
				document.getElementById('buttonsDiv').innerHTML = "";
				document.getElementById('statusMessage').innerHTML = "";
				XT.doAjaxSubmit('getErrorActivities',this);
			}				
	}
		
	function editSequence(activityLogNoSelected){
			document.getElementById('statusMessage').innerHTML = "";
			document.frmPPHCorrectionInterface.userAction.value='editSequence';
			document.getElementById('operationNoSelected').value = activityLogNoSelected;
			XT.doAjaxSubmit('editSequence',this);
	}

	function editAll(){
		document.getElementById('statusMessage').innerHTML = "";
		document.frmPPHCorrectionInterface.userAction.value='editAll';
		XT.doAjaxSubmit('editAll',this);
	}

	
	function updateSequence(){
			document.getElementById('statusMessage').innerHTML = "<img align='center' style='vertical-align: middle;' src='static/images/defaultLoadingImage.gif'>";
			document.frmPPHCorrectionInterface.userAction.value='updateSequence';
			XT.doAjaxSubmit('updateSequence',this);
	}
	
	function cancel(){
		document.getElementById('statusMessage').innerHTML = "";
		document.location.href='pphCorrectionInterface.htm';
	}

	function showNoDataMessage() {
		document.getElementById('errorLogDetailsDiv').innerHTML = "<font color = 'RED'> No Data Found </font>";
		document.getElementById('tableM3ItemDetails').innerHTML = "";
		document.getElementById('buttonsDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
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
	
	function getErrorMONumbers(){
	document.getElementById('errorLogDetailsDiv').innerHTML = "<img align='middle' style='vertical-align: middle;' src='static/images/ajax-loader.gif'>";
	document.getElementById('tableM3ItemDetails').innerHTML = "";
	document.getElementById('buttonsDiv').innerHTML = "";
	document.getElementById('statusMessage').innerHTML = "";
	document.frmPPHCorrectionInterface.userAction.value='getErrorMONumbers';
	document.frmPPHCorrectionInterface.submit();
	return false;
	//XT.doAjaxSubmit('getErrorMONumbers',this);
	}
	
	function confirmSequence(activityLogNoSelected){
		document.getElementById('errorLogDetailsDiv').innerHTML = "<img align='middle' style='vertical-align: middle;' src='static/images/ajax-loader.gif'>";
		document.getElementById('tableM3ItemDetails').innerHTML = "";
		document.getElementById('buttonsDiv').innerHTML = "";
		document.getElementById('statusMessage').innerHTML = "";
	document.frmPPHCorrectionInterface.userAction.value='confirmSequence';
	document.getElementById('operationNoSelected').value = activityLogNoSelected;
	XT.doAjaxSubmit('confirmSequence',this);
	}
	
</script>

<div id="content">
<BODY>
<FORM name="frmPPHCorrectionInterface" method="post" class="form" ><br>
	<input type="hidden" id="userAction" name="userAction">
	<input type="hidden" id="operationNoSelected" name="operationNoSelected">
	<CENTER>
		<font size="2"> <b> <fmt:message key="table.pphCorrectionInterface.header" /> </b> </font>
	</CENTER>
	<br>
	<center>
		<spring:hasBindErrors name="command">
			<c:forEach var="error" items="${errors.allErrors}">
				<h5 style="color: red;">${error.defaultMessage}</h5>
			</c:forEach>
		</spring:hasBindErrors>
	</center>
	
	<div id="statusMessage" align="center">&nbsp;</div>
	<br>
	<table 	cellpadding="0" cellspacing="0" width="50%" 
	           	align="center" id="normalTableNostripe"
	          	style="white-space: nowrap; word-spacing: normal;">
			<tr>	
				<td class="centerAligned">
					<table cellpadding="0" cellspacing="0" style="border-color: transparent;">
						<tr>
							<td style="vertical-align: middle; border-color: transparent;">
								<b>Facility : </b>									
							</td>
							<td style="vertical-align: middle; border-color: transparent;"> 
								<select name="facility" id="facility" onChange='getErrorMONumbers();'>
									<option selected="selected"></option>
									<c:forEach items="${command.divisionFacilities}" var="data">
											<option <c:if test="${command.facility eq data.facilityId}"> <c:out value="selected"/></c:if> value="${data.facilityId}">${data.facilityId}</option>								
									</c:forEach>									 							
								</select>
							</td>
						</tr>
					</table>
				</td>
				<td class="centerAligned">
					<table cellpadding="0" cellspacing="0" style="border-color: transparent;">
						<tr>
							<td style="vertical-align: middle; border-color: transparent;">
								<b>MO Numbers : </b>
							</td>
							<td style="vertical-align: middle; border-color: transparent;"> 
								<div id = "errorMODiv" name="errorMODiv" >
								<select  name="moNumber"
										  style="width:150px;margin:5px 0 5px 0;font: small-caps 900 10px arial;color:#1A4A6F;"
							              size="5">
									<c:forEach items="${command.errorMos}" var="data" varStatus="list">
										<option value="${data.moNumber}" <c:if test="${command.moNumber eq data.moNumber}"> <c:out value="selected"/> </c:if> >
											<c:out value="${data.moNumber}"></c:out>
										</option>
									</c:forEach>						
								</select>
								</div>
							</td>
						</tr>
					</table>
				</td><td class="centerAligned">
	
				
				
				
					<a href="javascript:getErrorLog();" alt="Filter" tabindex="5" style="cursor: hand;">
							<img src="static/images/filter.gif" alt="Filter" border="0">
						</a>
							
						
				</td>
											
	         </tr>
		</table>
	
	<br><br>
		
		<div id="tableM3ItemDetails" align="center"></div><br>
		<div id="errorLogDetailsDiv" align="center"></div>
		<br><br>
		<div id="buttonsDiv" align="right" style="width: 800px;"></div>

</FORM>
</BODY>
</div>