<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src='springxt.js'></script>
<script type="text/javascript" src='prototype.js'></script>
<script type="text/javascript" src='scriptaculous.js?load=effects'></script>

<script type="text/javascript">
	function checkValues(e){
		var key=e.keyCode || e.which;
		if (key==13){
			if(document.inspectionOperationReversalFrm.moNumber.value == ""){
				document.inspectionOperationReversalFrm.moNumber.focus();
			}  else if(document.inspectionOperationReversalFrm.sequence.value == ""){
				document.inspectionOperationReversalFrm.sequence.focus();
			} 
			submitThis();
			return false;
		}	
	}

	function submitThis() {
		if(document.inspectionOperationReversalFrm.moNumber.value == ""){
			alert("MO Number Cannot be Empty");
			return false;
			document.inspectionOperationReversalFrm.moNumber.focus();
		} else if(document.inspectionOperationReversalFrm.sequence.value == ""){
			alert("Operation Number Cannot be Empty");
			document.inspectionOperationReversalFrm.sequence.focus();
			return false;
		} else {
			document.inspectionOperationReversalFrm.action.value="DETAILS";
			// Clear the data before submitting
			document.getElementById('details').innerHTML = "";
			document.getElementById('buttons').innerHTML = "";
			document.getElementById('errorMessage').innerHTML = "";
			
			XT.doAjaxSubmit('showDetails', this);
			return false;
		}
	}

	function reverseThis() {
		document.inspectionOperationReversalFrm.action.value="REVERSE";
		XT.doAjaxSubmit('reverse', this);
		return false;
	}

	function resetThis() {
		document.inspectionOperationReversalFrm.action.value="";
		window.location = "reverseInspectionOperation.htm";
	}
</script>

<BODY onload = "document.inspectionOperationReversalFrm.moNumber.focus();">
<div id="content">
<FORM name=inspectionOperationReversalFrm method=post class="form">

<input type="hidden" name="action">
    <br>
	<CENTER>
		<font size="2"> <b> <fmt:message key="table.reverseInspectionOperation.header"/> </b> </font>
	</CENTER>
	<br>
	<Center><div id="loading"></div></Center>
	<br>
		<TABLE border="0" cellpadding="0" cellspacing="0" width="45%" align="center" id="normalTableNostripe">
				<tr valign="top">   
										
					<td valign="center"	style="font: 900 12px arial;left:20px;top:220px;vertical-align: middle"> 
						<fmt:message key="table.reverseInspectionOperation.column2"/>
						<input type="text" name="moNumber" id="moNumber" maxlength="15" value="${command.moNumber}" onKeyDown="return checkValues(event);"> 
    				</td>
					
					<td valign="center"	style="font: 900 12px arial;left:20px;top:220px;vertical-align: middle">
						<fmt:message key="table.reverseInspectionOperation.column3"/>
						<input type="text" name="operationNumber" id="sequence" maxlength="4" value="${command.operationNumber}" onKeyDown="return checkValues(event);"> 
					</td>
					<td><input type="image" src="static/images/filter.gif" name="FilterUp" width="60" height="30" onclick="return submitThis();" >
					</td>				
				</tr>
		</TABLE>
		<br>
		<div id="errorMessage"></div><br>
		<div id="details"></div><br><br>
		<div id="buttons"></div>
<center>


</center>
<br> 
<br>
</form>