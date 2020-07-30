<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript" src='static/js/validate.js'></script>
<script type="text/javascript" src='static/js/springxt.js'></script>
<script type="text/javascript" src='static/js/prototype.js'></script>
<script type="text/javascript" 	src='static/js/scriptaculous.js?load=effects,dragdrop'></script>

<script type="text/javascript">
	
	function upperCase(x) {
		var y=document.getElementById(x).value
		document.getElementById(x).value=y.toUpperCase()
	}
	
	function checkValues(e){
	var key=e.keyCode || e.which;
	if (key==13){
		filterData();
	}	
	}
	
	function filterData(){
		
		if(document.updateItemWeightFrm.moNumber.value == ""){
			alert("Enter MO Number");
			document.updateItemWeightFrm.moNumber.focus();
		} else if (specialCharValidate(document.updateItemWeightFrm.moNumber.value) == false) {
			alert("Special characters not allowed");
			document.updateItemWeightFrm.moNumber.focus();
		}  else if(document.updateItemWeightFrm.sequence.value == ""){
			alert("Enter Sequence Number");
			document.updateItemWeightFrm.sequence.focus();
		} else if(isNaN(document.updateItemWeightFrm.sequence.value)) {
			alert("Sequence Number should be numeric");
			document.updateItemWeightFrm.sequence.focus();
		} else {
			document.updateItemWeightFrm.moNumber.value = trim(document.updateItemWeightFrm.moNumber.value);
			document.getElementById('statusMessage').innerHTML = "";
			document.getElementById('result').innerHTML = "<br><br><br><br><center><img align='middle' style='vertical-align: middle;' src=static/images/ajax-loader.gif><center>";
			document.getElementById('result').style.backgroundRepeat= "no-repeat" ;
			document.updateItemWeightFrm.operation.value="filter";
			XT.doAjaxAction('getFilteredData', this);			
		}
	}

	function updateItemWeight(){
	if(document.updateItemWeightFrm.availableQty.value == ""){
			alert("Enter Available Quantity");
			document.updateItemWeightFrm.availableQty.focus();
		} else if(document.updateItemWeightFrm.totalWeight.value == ""){
			alert("Enter Total Weight");
			document.updateItemWeightFrm.totalWeight.focus();
		} else if(validateAvailableQty(document.updateItemWeightFrm.availableQty.value) == false ){
			alert("Invalid Available Quantity");
			document.getElementById('availableQty').focus();
			return false;
		}else if(document.updateItemWeightFrm.availableQty.value == 0 ){
			alert("Invalid Available Quantity");
			document.getElementById('availableQty').focus();
			return false;
		} else if(validateTotalWeight(document.updateItemWeightFrm.totalWeight.value) == false ){
			alert("Invalid Total weight");
			document.getElementById('totalWeight').focus();
			return false;
		} else {
			document.getElementById('statusMessage').innerHTML = "";
			document.updateItemWeightFrm.operation.value="update";
			XT.doAjaxAction('updateItemWeight', this);
		}
	}
	
	function Cancel(){
	document.getElementById("result").innerHTML="";
	document.updateItemWeightFrm.moNumber.value="";
	document.updateItemWeightFrm.sequence.value="";
	document.getElementById("statusMessage").innerHTML="";	
	document.updateItemWeightFrm.moNumber.focus();
	}
	
	function focusAvailableQty(){
	document.updateItemWeightFrm.availableQty.focus();
	}

	function callUpdateItemWeight(e){
	var key=e.keyCode || e.which;
	if (key==13){
		updateItemWeight();
	}	
	}
	
	function validateAvailableQty(string) {
	   if (!string) return false;
	   var iChars = "_*|,\":<>[]{}`\';()@&$#%'-.";
	
	   for (var i = 0; i < string.length; i++) {
	      if (iChars.indexOf(string.charAt(i)) != -1)
	         return false;
	   }
   return true;
}       

	function validateTotalWeight(string) {
	   if (!string) return false;
	   var iChars = "_*|,\":<>[]{}`\';()@&$#%'-";
	
	   for (var i = 0; i < string.length; i++) {
	      if (iChars.indexOf(string.charAt(i)) != -1)
	         return false;
	   }
	   return true;
	}

	function specialCharValidate(string) {
	   if (!string) return false;
	   var iChars = "~!^.?_*|,\"/:<>[]{}`\';()@&$#%''";
	   for (var i = 0; i < string.length; i++) {
	      if (iChars.indexOf(string.charAt(i)) != -1)
	         return false;
	   }
	   return true;
	}       
</script>
 
<BODY onload="document.updateItemWeightFrm.moNumber.focus();">

<div id="content">
<FORM name=updateItemWeightFrm method=post class="form">
<input type="hidden" name="operation">
<input type="hidden" name="itemNumber">
<br>
<CENTER><font size="2"> <b> Update Item Weight </b> </font></CENTER>
<br>

<div id="statusMessage" style="position:absolute;left:550px;top:110px"></div>
<br><br>
<TABLE 	border="0" cellpadding="0" cellspacing="0" width="40%"
				align="center" id="normalTableNostripe"
				style="white-space: nowrap; word-spacing: normal;">
	<tr valign="top">
		<td valign="center"	style="font: 900 12px arial;left:20px;top:220px;vertical-align: middle">
				MO #:<input type="text" name="moNumber" onkeyup="upperCase(this.id)" id="moNumber" value="${command.moNumber}" 
									onKeyDown="javascript:checkValues(event);"/>
		</td>
		
		<td style="font: 900 12px arial;left:20px;top:220px;vertical-align: middle">
				Sequence #:<input type="text" name="sequence" id="sequence" 
										       value="${command.sequence}" onKeyDown="javascript:checkValues(event);"
										       maxlength="4"/>
		</td>
		<td> 
						<!-- <input type="button" value="Filter" name="currentOrderDetailsFilter" onclick="javascript:checkAndSubmit();"> --> 
						<a href="javascript:filterData();" alt="Filter" tabindex="5" style="cursor: hand;">
							<img src="static/images/filter.gif" alt="Filter" border="0">
						</a>
					</td>
	
	</tr>
</table>

<br>
<br>
<div id="result" align="center"></div>

</FORM>
</div>
</BODY>
