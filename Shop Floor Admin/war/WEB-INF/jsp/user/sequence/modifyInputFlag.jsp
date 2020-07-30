<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.util.Date"%>
<%@page import="com.gavs.hishear.util.DateUtil"%>
<script type="text/javascript" src='static/js/validate.js'></script>
<script type="text/javascript" src='static/js/springxt.js'></script>
<script type="text/javascript" src='static/js/prototype.js'></script>
<script type="text/javascript" src='static/js/scriptaculous.js?load=effects,dragdrop'></script>

<script type="text/javascript">
function changeFlag(idInputRequired, sequence){
	document.getElementById("sequence").value = sequence;
	document.getElementById("qtyRequired").value = document.getElementById(idInputRequired).checked;
	XT.doAjaxAction('changeInputFlagStatus', this);
}
</script>
<script type="text/javascript">

function checkValuesEnterKey(){

	if ((event.which && event.which == 13) || 
    (event.keyCode && event.keyCode == 13))
    {

		if(document.modifyInputFlagFrm.moNumber.value == ""){
			alert("Enter MONumber")
			return false;
		}  else
		{
			document.modifyInputFlagFrm.submit();
			return false;
		}
	
	} 
	    else return true;
}

function checkValues(){

		if(document.modifyInputFlagFrm.moNumber.value == ""){
			alert("Enter MONumber");
			document.modifyInputFlagFrm.moNumber.focus();
			return false;
		} else if (specialCharValidate(document.modifyInputFlagFrm.moNumber.value) == false) {
			alert("Special characters not allowed");
			document.modifyInputFlagFrm.moNumber.focus();
			return false;
		} else {
			document.modifyInputFlagFrm.moNumber.value = trim(document.modifyInputFlagFrm.moNumber.value);
			document.modifyInputFlagFrm.submit();
			return false;
		}
	
}

function upperCase(x)
{
var y=document.getElementById(x).value
document.getElementById(x).value=y.toUpperCase()
}

function fetchData() {
	if(document.modifyInputFlagFrm.moNumber.value == ""){
		alert("Please enter MO Number");
		document.modifyInputFlagFrm.moNumber.focus();
	} else if (specialCharValidate(document.modifyInputFlagFrm.moNumber.value) == false) {
		alert("Special characters not allowed");
		document.modifyInputFlagFrm.moNumber.focus();
	}  else {
		document.modifyInputFlagFrm.moNumber.value = trim(document.modifyInputFlagFrm.moNumber.value);
		document.modifyInputFlagFrm.modifyInputFlag.click();
	}
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
<BODY onload="document.modifyInputFlagFrm.moNumber.focus()">
<div id="content">
	<FORM name="modifyInputFlagFrm" method="post">	
	<CENTER>
	<br><br>
		<font size="2"> <b> <fmt:message key="table.modifyInputFlag.header"/> </b> </font>
		<br><br><br>
	</CENTER>
	
	
	<TABLE border="0" cellpadding="0" cellspacing="0" width="27%" align="center" id="normalTableNostripe">
				<tr valign="top">   
					<td valign="center"	style="font: 900 12px arial;left:20px;top:220px;vertical-align: middle">
						<fmt:message key="table.modifyInputFlag.column1"/>#:
						<input type="text" id="moNumber" onkeyup="upperCase(this.id)" onkeydown="return checkValuesEnterKey();" name="moNumber" value="${command.moNumber}"
							maxlength="7"> 
							
    				</td>    			
						
					<td> <input type="hidden" id="sequence"  name="sequence">
						 <input type="hidden" id="qtyRequired"  name="qtyRequired">
						 <input type="image" src="static/images/filter.gif" name="Filter" width="60" height="30" onClick="return checkValues();" >
					
						
					</td>			
					
				</tr>
		</TABLE>
<br>
<CENTER>
	<div id="message" style="font-size: 2; color: red"></div>
</CENTER>
 <br>
 <CENTER>
 <c:choose>
 <c:when test="${not empty command.moNumber}">
<c:choose> 
  <c:when test='${empty command.sequences}'>
				<TABLE id="normalTableFlush4" align="center" cellSpacing="1" cellPadding="5" border="0">
					<tr>
						<td class="selected">
							<font color="#B31B00"> <b> <fmt:message key="error.noReports"/> </b> </font>
						</td>
					</tr>
				</TABLE>
	</c:when>
	<c:otherwise>
	 <div style="width: 800px; height: 345px; overflow: scroll; background-color: #FFFFF;"> 
			<TABLE id="normalTableNostripe" width="80%" align="center" cellSpacing="0" cellPadding="0" border="0" style="white-space: nowrap; word-spacing: normal;">
			<thead class="fixedHeader">
					<tr class="selected" height="15px">
						<th class="tableTitle" width="60px"> <fmt:message key="table.modifyInputFlagData.column1"/> </th>
						<th class="tableTitle" width="300px"> <fmt:message key="table.modifyInputFlagData.column2"/> </th>
						<th class="tableTitle" width="100px"> <fmt:message key="table.modifyInputFlagData.column3"/> </th>
					</tr>
		</thead>
		<tbody class="scrollContent">
					<c:forEach items="${command.sequences}" var="data" varStatus="list">
					
					<c:choose>
						<c:when test='${(list.index%2) == 0}'>
							<tr class="normalRow">
						</c:when>
						<c:otherwise>
							<tr class="alternateRow">
						</c:otherwise>
					</c:choose>

					<c:choose>					
						<c:when test='${empty data.sequence}'>
							<td class="tableContent" width="60px"> &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="60px" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.sequence}"></c:out> </td>
						</c:otherwise>
					</c:choose>
					<c:choose>					
						<c:when test='${empty data.sequenceDescription}'>
							<td class="tableContent" width="300px"> &nbsp;</td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="300px" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.sequenceDescription}"></c:out> </td>
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test='${empty data.inputRequired}'>
							<td class="tableContent" width="100px"> &nbsp; </td>
						</c:when>
						<c:otherwise>
							<td class="tableContent" width="100px" style="white-space: nowrap; word-spacing: normal;">
							<input type="checkbox" 
										name="inputRequired_<c:out value="${data.sequence}"/>" 
										id="inputRequired_<c:out value="${data.sequence}"/>" 
										value='<c:out value="${data.inputRequired}"></c:out>' 
										<c:if test="${data.inputRequired eq true}">
											checked="checked"
										</c:if>  
										onClick="javascript:changeFlag('inputRequired_<c:out value="${data.sequence}"/>','<c:out value="${data.sequence}"/>');">
							</td>
						</c:otherwise>				
					</c:choose>
					</tr>
			</c:forEach>			
</tbody>
<div align="right" id="exportToExcel" style="display: none;">

<b> <a href="#" name="excel" onclick="javascript:exportToExcel();" target="_self"> Export to Excel a> b>
</TABLE>
</div>
</c:otherwise>
</c:choose>
</c:when>
</c:choose>
</CENTER>
</form>