<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
	function doOnSubmit() {
		document.PerformanceAssetwiseFrm.toexcel.value='no';
	}
	function getDepartmentDetails() {
		document.PerformanceAssetwiseFrm.factoryChanged.value='yes';
		document.PerformanceAssetwiseFrm.toexcel.value='no';
		document.PerformanceAssetwiseFrm.submit();
		return false;
	}
	function checkValues() {
		document.PerformanceAssetwiseFrm.submit();
		return false;
	}
	function getHeader(reportType){
		if(reportType=='A') {
	 		document.getElementById('headerText').innerHTML='Daily Performance Report - Asset wise';
	 	}
		if(reportType=='E') {
		 	document.getElementById('headerText').innerHTML='Daily Performance Report - Employee wise';
		}
		if(reportType=='P') {
	 		document.getElementById('headerText').innerHTML='Daily Performance Report - Partno wise';
		}
	} 
	function exportToExcel() {
		document.PerformanceAssetwiseFrm.toexcel.value='yes';
		document.PerformanceAssetwiseFrm.submit(); 
		return false;
	}
</script>

<BODY >
<div id="content">
<FORM name="PerformanceAssetwiseFrm" method=post class="form">
 	<input type="hidden" name="toexcel">
 	<input type="hidden" name="factoryChanged">
 	<CENTER>
		<font size="2"> <b> <div id="headerText"><fmt:message key="table.PerformanceAssetwiseReport.header.${command.reportType}"/></div> </b> </font>
	</CENTER>
	
	<br>
	<TABLE border="0" cellpadding="0" cellspacing="0" width="75%" align="center" id="normalTableNostripe">
				<tr valign="top" >  
					<td rowspan="2" valign="top"> 
						<b><fmt:message key="table.PerformanceAssetwiseRept.column1"/></b><br>
						
						<select name="factory"
			                         style="width:180px;margin:5px 0 5px 0;font: small-caps 900 10px arial;color:#1A4A6F;"
			                         onChange="javascript:getDepartmentDetails()" size="4">
								<option value="ALL" selected >ALL</option>
								 <c:forEach items="${command.arrFactory}" var="data" varStatus="list">
				                   <option value="${data.factory}" <c:if test="${command.factory eq data.factory}">
				                    <c:out value="selected"/> </c:if> > <c:out value="${data.factory}"> </c:out> </option>
			                     </c:forEach>
		                </select>
						
					</td>	 
					<td rowspan="2">
						 <b> <fmt:message key="table.PerformanceAssetwiseRept.column2"/> </b><br>
							
							<select name="department" id="department"
			                             style="width:180px;margin:5px 0 5px 0;font: small-caps 900 10px arial;color:#1A4A6F;"
			                              size="4" >
			                       <option value="ALL" selected >ALL</option>
			                     <c:forEach items="${command.arrDepartments}" var="data" varStatus="list">
				                   <option value="${data.deptId}" <c:if test="${command.department eq data.deptId}">
				                    <c:out value="selected"/> </c:if> > <c:out value="${data.deptId} - ${data.cat} --> ${data.department}"> </c:out> </option>
			                     </c:forEach>
				                        
		                  </select>	
					</td>
					
					<td border="1" style="font: 900 11px arial;left:20px;top:220px">
					  <b> <fmt:message key="table.PerformanceAssetwiseRept.column3"/> </b><br>
					         <div id="chooserSpan" class="dateChooser select-free"
									style="display: none; visibility: hidden; width: 160px;"></div>
					          <input type="text" id="reportDate" name="reportDate" value="${command.reportDate}" size="10" readOnly="readOnly"> 
					                <img src="static/images/cal.gif" onclick="showChooser(this, 'reportDate', 'chooserSpan', 1950, 2020, Date.patterns.USDatePattern, false);">
					
					</td>
					
					<td border="1" style="font: 900 11px arial;left:20px;top:220px">
						 <b> <fmt:message key="table.PerformanceAssetwiseRept.column4"/> </b><br>
					      
					       <input type="radio" name="shift" value="A" <c:if test="${command.shift eq 'A'}">checked=checked</c:if> > A &nbsp;
					        <input type="radio" name="shift" value="B" <c:if test="${command.shift eq 'B'}">checked=checked</c:if> > B &nbsp;
					        <input type="radio" name="shift" value="C" <c:if test="${command.shift eq 'C'}">checked=checked</c:if> > C &nbsp;
					        <input type="radio" name="shift" value="D" <c:if test="${command.shift eq 'D'}">checked=checked</c:if>>  D &nbsp;
											
					</td>
					<td rowspan="2"> <input type="submit" value="Filter" name="FactoryReportFilter" onClick="javascript:doOnSubmit();" > </td>
					</tr>
					
					<tr>
					<td border="1" style="font: 900 11px arial;left:20px;top:220px" colspan="2">
					       <b> <fmt:message key="table.PerformanceAssetwiseRept.column5"/> </b><br>
					        <input type="radio" name="reportType" value="A" onClick="javascript:getHeader('A')"<c:if test="${command.reportType eq 'A'}" >checked=checked</c:if>> Asset wise 
					        <input type="radio" name="reportType" value="E" onClick="javascript:getHeader('E')"<c:if test="${command.reportType eq 'E'}" >checked=checked</c:if>> Employee wise  
					        <input type="radio" name="reportType" value="P" onClick="javascript:getHeader('P')"<c:if test="${command.reportType eq 'P'}" >checked=checked</c:if>> PartNo wise       
					</td>
				</tr>
		</TABLE>
		<br>
<c:choose>     
	<c:when test='${command.recordsFound eq -1}'></c:when>
	<c:otherwise>
<c:choose>     
	<c:when test='${command.recordsFound eq 0}'>
		<TABLE id="normalTableFlush4" align="center" cellSpacing="1" cellPadding="5" border="0">
			<tr>
				<td class="selected">
					<font color="#B31B00"> <b> <fmt:message key="error.noReports"/> </b>	</font>
				</td>
			</tr>
		</TABLE>
	</c:when>
<c:otherwise>

<CENTER>
 <div class="tableContainer"  style="height: 303px;"> 
			<TABLE id="normalTableNostripe" align="center" cellSpacing="0" cellPadding="0" border="0" style="white-space: nowrap; word-spacing: normal;">
			<thead class="fixedHeader">
					<tr class="selected" height="15px">
						<th class="tableTitle" > <fmt:message key="table.PerformanceAssetwiseReport.column1"/> </th>
						<th class="tableTitle" > <fmt:message key="table.PerformanceAssetwiseReport.column2"/> </th>
						<th class="tableTitle" > <fmt:message key="table.PerformanceAssetwiseReport.column3"/> </th>
						<th class="tableTitle" > <fmt:message key="table.PerformanceAssetwiseReport.column4"/> </th>
						<th class="tableTitle" > <fmt:message key="table.PerformanceAssetwiseReport.column5"/> </th>
						<th class="tableTitle" > <fmt:message key="table.PerformanceAssetwiseReport.column6"/> </th>
						<th class="tableTitle" > <fmt:message key="table.PerformanceAssetwiseReport.column7"/> </th>
						<th class="tableTitle" > <fmt:message key="table.PerformanceAssetwiseReport.column8"/> </th>
						<th class="tableTitle" > <fmt:message key="table.PerformanceAssetwiseReport.column9"/> </th>
						<th class="tableTitle" > <fmt:message key="table.PerformanceAssetwiseReport.column10"/> </th>
						<th class="tableTitle" > <fmt:message key="table.PerformanceAssetwiseReport.column11"/> </th>
						<th class="tableTitle" > <fmt:message key="table.PerformanceAssetwiseReport.column12"/> </th>
												
					</tr>
		</thead>
		<tbody class="scrollContent">
					<c:forEach items="${command.factoryReport}" var="data" varStatus="list">
					
						<c:choose>
							<c:when test='${(list.index%2) == 0}'>
								<tr class="normalRow">
							</c:when>
							<c:otherwise>
								<tr class="alternateRow">
							</c:otherwise>
						</c:choose>
	
						<c:choose>					
							<c:when test='${empty data.assetNumber}'>
								<td class="tableContent" width="30"> &nbsp;</td>
							</c:when>
							<c:otherwise>
								<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.assetNumber}"></c:out> </td>
							</c:otherwise>
						</c:choose>
	
						<c:choose>
							<c:when test='${empty data.partNumber}'>
								<td class="tableContent" width="30"> &nbsp; </td>
							</c:when>
							<c:otherwise>
								<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.partNumber}"></c:out> </td>
							</c:otherwise>				
						</c:choose>
						
						<c:choose>
							<c:when test='${empty data.shift}'>
								<td class="tableContent" width="30"> &nbsp; </td>
							</c:when>
							<c:otherwise>
								<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.shift}"></c:out></td>
							</c:otherwise>				
						</c:choose>
						
						<c:choose>
							<c:when test='${empty data.employeeName}'>
								<td class="tableContent" width="30"> &nbsp; </td>
							</c:when>
							<c:otherwise>
								<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"><c:out value="${data.employeeName}"></c:out></td>
							</c:otherwise>				
						</c:choose>
						
											
						<c:choose>
							<c:when test='${empty data.run}'>
								<td class="tableContent" width="30"> &nbsp; </td>
							</c:when>
							<c:otherwise>
								<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.run}"></c:out>  </td>
							</c:otherwise>				
						</c:choose>
						
												
						<c:choose>
							<c:when test='${empty data.PPH}'>
								<td class="tableContent" width="30"> &nbsp; </td>
							</c:when>
							<c:otherwise>
								<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.PPH}"></c:out> </td>
							</c:otherwise>				
						</c:choose>
						
						<c:choose>
							<c:when test='${empty data.PPHAct}'>
								<td class="tableContent" width="30"> &nbsp; </td>
							</c:when>
							<c:otherwise>
								<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.PPHAct}"></c:out> </td>
							</c:otherwise>				
						</c:choose>
						
						<c:choose>
							<c:when test='${empty data.qtyTarget}'>
								<td class="tableContent" width="30"> &nbsp; </td>
							</c:when>
							<c:otherwise>
								<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.qtyTarget}"></c:out> </td>
							</c:otherwise>				
						</c:choose>
						
						<c:choose>
							<c:when test='${empty data.qtyAct}'>
								<td class="tableContent" width="30"> &nbsp; </td>
							</c:when>
							<c:otherwise>
								<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.qtyAct}"></c:out> </td>
							</c:otherwise>				
						</c:choose>
						
						<c:choose>
							<c:when test='${empty data.actRunHrs}'>
								<td class="tableContent" width="30"> &nbsp; </td>
							</c:when>
							<c:otherwise>
								<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.actRunHrs}"></c:out> </td>
							</c:otherwise>				
						</c:choose>
						
						<c:choose>
							<c:when test='${empty data.downHrs}'>
								<td class="tableContent" width="30"> &nbsp; </td>
							</c:when>
							<c:otherwise>
								<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.downHrs}"></c:out> </td>
							</c:otherwise>				
						</c:choose>
						
						<c:choose>
							<c:when test='${empty data.efficiency}'>
								<td class="tableContent" width="30"> &nbsp; </td>
							</c:when>
							<c:otherwise>
								<td class="tableContent" width="30" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.efficiency}"></c:out>% </td>
							</c:otherwise>				
						</c:choose>
							</tr>
			</c:forEach>		
		</tbody>
	</TABLE>
	</div>
	<div align="right">
		<b> <a href="#" name="excel" onclick="javascript:exportToExcel();" target="_self"> Export to Excel </a> </b> 
	</div>
</c:otherwise>
</c:choose>
</c:otherwise>
	</c:choose>	  
	
	<script language="JavaScript">
var cal1 = new calendar2(document.forms['PerformanceAssetwiseFrm'].elements['reportDate']);
cal1.year_scroll = true;
cal1.time_comp = false;
cal1.left = 400;
cal1.top = 300;


</script>
</form>
  