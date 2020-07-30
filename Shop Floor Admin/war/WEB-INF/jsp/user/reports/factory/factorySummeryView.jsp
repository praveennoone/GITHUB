<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src='static/js/springxt.js'></script>
<script type="text/javascript" src='static/js/prototype.js'></script>
<script type="text/javascript"
	src='static/js/scriptaculous.js?load=effects,dragdrop'></script>

<div id="content"><script type="text/javascript">


function ValidateForm(){
	var dt=document.frmSample.txtDate
	if (isDate(dt.value)==false){
		dt.focus()
		return false
	}
    return true
 }

function popupWindowWithParams(url, features) {
	window.open(url, '', features);
}

function popupWithParams(value) {
	document.getElementById('factoryReport').innerHTML = "<br><br><br><br><center><img align='middle' style='vertical-align: middle;' src=static/images/ajax-loader.gif><center>";
	var unselected='#99CCFF';
	var selected='#FF66FF';
	var header = document.getElementById('noOfAssetsHeader');
	header.style.backgroundColor=unselected;
	var header = document.getElementById('noOfOperatorsheader');
	header.style.backgroundColor=unselected;
	var header = document.getElementById('jobsCurrentlyProcessingHeader');
	header.style.backgroundColor=unselected;
	var header = document.getElementById('jobsCurrentlyPendingHeader');
	header.style.backgroundColor=unselected;
	var header = document.getElementById('noOfPartsheader');
	header.style.backgroundColor=unselected;
	var header = document.getElementById('yieldHeader');
	header.style.backgroundColor=unselected;
	
	if(value=='1')
	{
	var header = document.getElementById('noOfAssetsHeader');
	header.style.backgroundColor=selected;
	XT.doAjaxAction('getAssetReport', this);
	}
	else if(value=='2')
	{
	var header = document.getElementById('jobsCurrentlyProcessingHeader');
	header.style.backgroundColor=selected;
	XT.doAjaxAction('getJobsCurrentlyProcessingReport', this);
	}
	else if(value=='3')
	{
	var header = document.getElementById('jobsCurrentlyPendingHeader');
	header.style.backgroundColor=selected;
	XT.doAjaxAction('getJobsCurrentlyPendingReport', this);
	}
	
	else if(value=='7')
	{
	var header = document.getElementById('noOfOperatorsheader');
	header.style.backgroundColor=selected;
	XT.doAjaxAction('getOperatorsReport', this);
	}
	else if(value=='4')
	{
	var header = document.getElementById('noOfPartsheader');
	header.style.backgroundColor=selected;
	XT.doAjaxAction('getPartsProcessedReport', this);
	}
	else if(value=='5')
	{
	var header = document.getElementById('noOfAssetsHeader');
	header.style.backgroundColor=selected;
	XT.doAjaxAction('getPartsrejectedReport', this);
	}
	else if(value=='6')
	{
	var header = document.getElementById('yieldHeader');
	header.style.backgroundColor=selected;
	XT.doAjaxAction('getYieldReport', this);
	}
}

function getDepartmentDetails() {
	XT.doAjaxAction('getDepartmentDetails', this);	
}

function getAllDetails() {
	document.factoryForm.initialLoading.value = '0';
	document.getElementById('assetCount').innerHTML = "<center><img src=static/images/loading.gif><center>";
	document.getElementById('operatorCount').innerHTML = "<center><img src=static/images/loading.gif><center>";
	document.getElementById('jobCurrentlyProcessing').innerHTML = "<center><img src=static/images/loading.gif><center>";
	document.getElementById('jobCurrentlyPending').innerHTML = "<center><img src=static/images/loading.gif><center>";
	document.getElementById('partsProcessed').innerHTML = "<center><img src=static/images/loading.gif><center>";
	document.getElementById('yield').innerHTML = "<center><img src=static/images/loading.gif><center>";
	
	XT.doAjaxAction('getAllDetails', this);
}

function upperCase(x) {
	var y=document.getElementById(x).value
	document.getElementById(x).value=y.toUpperCase()
}

function refreshPage() {
	var initialLoading = document.factoryForm.initialLoading.value;
	if (initialLoading == '1') {
		getAllDetails();
	}
}
</script>


<BODY>
<FORM name=factoryForm method=post class="form"><input
	type="hidden" name="toexcel">
<TABLE border="0" cellpadding="0" cellspacing="0" width="100%"
	align="center" id="normalTableNostripe"
	style="white-space: nowrap; word-spacing: normal;">
	<tr valign="top" align="center">
		<td valign="center" size="5"
			style="width:180px;font: 900 12px arial;left:20px;top:220px">
		<div
			style="text-align: center;background-color:#99CCFF;height:20px;font:  900 12px arial;center:20px;top:220px">
		Factory Name</div>
		<div><select name="factory"
			style="width:180px;margin:5px 0 5px 0;font: small-caps 900 10px arial;color:#1A4A6F;"
			onChange="javascript:getDepartmentDetails()">
			<option>Select Facory</option>
			<option value="ALL" <c:if test="${command.factory eq 'ALL'}"> <c:out value="selected"/> </c:if>>ALL</option>
			<option value="PINS" <c:if test="${command.factory eq 'PINS'}"> <c:out value="selected"/> </c:if>>PIN</option>
			<option value="COLLARS" <c:if test="${command.factory eq 'COLLARS'}"> <c:out value="selected"/> </c:if>>COLLAR</option>
			<option value="NUTS" <c:if test="${command.factory eq 'NUTS'}"> <c:out value="selected"/> </c:if>>NUT</option>
		</select></div>
		</td>
		<td align="center">
		<div
			style="text-align: left;background-color:#99CCFF;width:240px;height:20px;font: small-caps 900 11px arial;center:20px;top:220px">
		<fmt:message key="table.Production.column1" /></div>
		<div id="chooserSpan" class="dateChooser select-free"
			style="display: none; visibility: hidden; width: 160px;"></div>
		<input type="text" id="fromDate" name="fromDate" readonly="readonly"
			value="${command.fromDate}" > <img src="static/images/cal.gif"
			onclick="showChooser(this, 'fromDate', 'chooserSpan', 1950, 2020, Date.patterns.USDatePattern, true);">

		</td>
		<td align="center">
		<div
			style="text-align: left;background-color:#99CCFF;width:240px;height:20px;font: small-caps 900 11px arial;center:20px;top:220px">
		<fmt:message key="table.Production.column2" /></div>
		<div id="chooserSpan2" class="dateChooser select-free"
			style="display: none; visibility: hidden; width: 160px;"></div>
		<input type="text" id="toDate" name="toDate" readonly="readonly"
			value="${command.toDate}" > <img src="static/images/cal.gif"
			onclick="showChooser(this, 'toDate', 'chooserSpan2', 1950, 2020, Date.patterns.USDatePattern, true);">
		</td>
		<td>
		<div
			style="text-align: left;background-color:#99CCFF;width:280px;height:20px;font: small-caps 900 11px arial;center:20px;top:220px">
		Shift</div>
		<c:forEach items="${command.arrShiftTime}" var="data" varStatus="list">

			<c:if test="${data.shift =='A'}">
				<input type="radio" name="shift"
					value="${data.shiftStartTime}-${data.shiftEndTime}"
					onClick="fnShift(factoryForm, '${data.shiftStartTime}-${data.shiftEndTime}-${data.dateShiftRequired}');"> A &nbsp;&nbsp;&nbsp;
				        </c:if>
			<c:if test="${data.shift =='B'}">
				<input type="radio" name="shift"
					value="${data.shiftStartTime}-${data.shiftEndTime}"
					onClick="fnShift(factoryForm, '${data.shiftStartTime}-${data.shiftEndTime}-${data.dateShiftRequired}');"> B
							&nbsp;&nbsp;&nbsp;
				         </c:if>
			<c:if test="${data.shift =='C'}">
				<input type="radio" name="shift"
					value="${data.shiftStartTime}-${data.shiftEndTime}"
					onClick="fnShift(factoryForm, '${data.shiftStartTime}-${data.shiftEndTime}-${data.dateShiftRequired}');"> C
							&nbsp;&nbsp;&nbsp;
				         </c:if>
			<c:if test="${data.shift =='D'}">
				<input type="radio" name="shift"
					value="${data.shiftStartTime}-${data.shiftEndTime}"
					onClick="fnShift(factoryForm, '${data.shiftStartTime}-${data.shiftEndTime}-${data.dateShiftRequired}');"> D
				         </c:if>

		</c:forEach></td>

	</tr>
	<tr valign="top">

		<td>

		<div
			style="text-align: center;background-color:#99CCFF;height:20px;font: small-caps 900 12px arial;center:20px;top:220px">
		Department</div>

		<div><select name="department" id="department"
			style="width:180px;margin:5px 0 5px 0;font: small-caps 900 10px arial;color:#1A4A6F;"
			size="4" onchange="javascript:getAllDetails()">
			<option value="ALL">ALL</option>
			<c:forEach items="${command.arrDepartments}" var="data"
				varStatus="list">
				<option value="${data.departmentId}"
					<c:if test="${command.departmentId eq data.departmentId}"> <c:out value="selected"/> </c:if>>
				<c:out value="${data.departmentId} --> ${data.department}">
				</c:out></option>
			</c:forEach>
		</select></div>

		<div id="noOfAssetsHeader"
			style="text-align: center;background-color:#99CCFF;height:20px;font: small-caps 900 12px arial;center:20px;top:220px">
		Number Of Assets</div>

		<div
			style="text-align: center;height:20px;font: small-caps 900 11px arial;center:20px;top:220px;  ">
		<a href="javascript:popupWithParams('1')">
		<div id="assetCount">${command.noOfAssets}</div>
		</a></div>


		<div id="noOfOperatorsheader"
			style="text-align: center;background-color:#99CCFF;height:20px;font: small-caps 900 12px arial;center:20px;top:220px">
		Number Of Operators</div>

		<div
			style="text-align: center;height:20px;font: small-caps 900 11px arial;center:20px;top:220px">
		<a href="javascript:popupWithParams('7')">
		<div id="operatorCount">${command.noOfOperators}</div>
		</a></div>


		<div id="jobsCurrentlyProcessingHeader"
			style="background-color:#99CCFF;height:20px;font: small-caps 900 12px arial;center:20px;top:220px">
		Job Currently Processing</div>

		<div
			style="text-align: center;height:20px;font: small-caps 900 11px arial;center:20px;top:220px">
		<a href="javascript:popupWithParams('2')">
		<div id="jobCurrentlyProcessing">${command.noOfprocessingJob}</div>
		</a></div>

		<div id="jobsCurrentlyPendingHeader"
			style="text-align: center;background-color:#99CCFF;height:20px;font: small-caps 900 12px arial;center:20px;top:220px">
		Job Currently Pending</div>

		<div
			style="text-align: center;height:20px;font: small-caps 900 11px arial;center:20px;top:220px">
		<a href="javascript:popupWithParams('3')">
		<div id="jobCurrentlyPending">${command.noOfPendingingJob}</div>
		</a></div>


		<div id="noOfPartsheader"
			style="text-align: center;background-color:#99CCFF;height:20px;font: small-caps 900 12px arial;center:20px;top:220px">
		Number Of Parts Processed</div>

		<div
			style="text-align: center;height:20px;font: small-caps 900 11px arial;center:20px;top:220px">
		<a href="javascript:popupWithParams('4')">
		<div id="partsProcessed">${command.noOfPartsProcessed}</div>
		</a></div>


		<div
			id="yieldHeader" style="text-align: center;background-color:#99CCFF;height:20px;font: small-caps 900 12px arial;center:20px;top:220px">
		Yield</div>

		<div
			style="text-align: center;height:20px;font: small-caps 900 11px arial;center:20px;top:220px">
		<a href="javascript:popupWithParams('6')">
		<div id="yield"></div>
		</a></div>
		</td>
		<td colspan="5">
		<div class="tableContainer" style="height: 400px;">
		<div id="factoryReport" style="width: 100%; height: 100%;vertical-align: middle;"></div>
		</div>
		</td>
	</tr>
</table>
<input type="hidden" name="initialLoading" value="${command.initialLoading}">
<script type="text/javascript">
refreshPage();
</script>
</FORM>