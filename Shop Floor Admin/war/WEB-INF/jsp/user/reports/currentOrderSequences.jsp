<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tld/c-rt.tld" prefix="c_rt" %>

<script type="text/javascript">
	function checkValues(){
		document.frmCurrentOrderDetails.toexcel.value='no';	
		document.frmCurrentOrderDetails.submit();
		return false;
	}
	
	function upperCase(x) {
		var y=document.getElementById(x).value
		document.getElementById(x).value=y.toUpperCase()
	}
	
	function exportToExcel() {
		document.activityRejectedFrm.toexcel.value='yes';
	//	document.activityRejectedFrm.action="ActivityRejectedToExcel.htm";
		document.activityRejectedFrm.submit();
		return false;
	}
</script>


<div id="content">
<BODY onload = "document.frmCurrentOrderDetails.partNumber.focus();">
<div id="content"> 
	<FORM name=frmCurrentOrderDetails method=post class="form">
	<input type="hidden" name="toexcel">
	<br>
	<CENTER>
		<font size="2"> <b> <fmt:message key="table.currentOrderDetails.header"/> </b> </font>
	</CENTER>
	<br> 
	<TABLE border="0" cellpadding="0" cellspacing="0" width="75%" align="center" id="normalTableNostripe">
				<tr valign="top">   
					<td colspan = "4" align = "center"> 
							<input type="radio" name="group1" value="Item" onClick="javascript:doCheck('item')" checked> Item no 
							<input type="radio" name="group1" value="mo" onClick="javascript:doCheck('mo')" > MO Number<br>
					</td>
				</tr>
				<tr valign="top" id="trItem">
					<td id="tdItemNumber"> 
						<b><fmt:message key="table.currentOrderDetails.column1"/></b>
						<input type="text" id="partNumber"  name="partNumber" value="${command.partNumber}" > 
					</td>
					<td id="tdMoLineNumber"  > 
						<b><fmt:message key="table.currentOrderDetails.column4"/></b>
					<select name="moLine">
								<c:forEach items="${command.sequences}" var="data" varStatus="list">
								<option value="${data.moNumber}-${data.lineNumber}">
									 <c:out value="${data.moNumber}-${data.lineNumber}"/> 
								 </option>
								</c:forEach>
							</select>		 
					</td>
					<td> <input type="button" value="Filter" name="currentOrderDetailsFilter" onclick="javascript:checkValues();"> </td>
				<tr>					
				<tr valign="top" id="trMo">
					<td id="tdMoNumber"> 
						<b><fmt:message key="table.currentOrderDetails.column2"/></b>
						<input type="text" name="moNumber" value="${command.moNumber}" onkeyup="upperCase(this.id)"> 
    				</td>
    				<td id="tdLineNumber"> 
						<b><fmt:message key="table.currentOrderDetails.column3"/></b>
						<input type="text" id="lineNumber"  name="lineNumber" value="${command.lineNumber}" > 
					</td>
					<td> <input type="button" value="Filter" name="currentOrderDetailsFilter" onclick="javascript:checkValues();"> </td>
				</tr>
		</TABLE>
<br> <br><br> <br>
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
				<CENTER>
 					<div class="tableContainer"> 
						<TABLE id="normalTableNostripe" align="center" cellSpacing="0" cellPadding="0" border="0" style="white-space: nowrap; word-spacing: normal;">
							<thead class="fixedHeader">
								<tr class="selected" height="15px">
									<th class="tableTitle" > <fmt:message key="table.currentOrderDetails.data3"/> </th>
									<th class="tableTitle" > <fmt:message key="table.currentOrderDetails.data4"/> </th>
									<th class="tableTitle" > <fmt:message key="table.currentOrderDetails.data5"/> </th>
									<th class="tableTitle" > <fmt:message key="table.currentOrderDetails.data6"/> </th>
									<th class="tableTitle" > <fmt:message key="table.currentOrderDetails.data7"/> </th>
									<th class="tableTitle" > <fmt:message key="table.currentOrderDetails.data8"/> </th>
									
	 							</tr>
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
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.sequence}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.workCenterCode}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"  style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.workCenterCode}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.workCenterDesc}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"  style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.workCenterDesc}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.activity}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"  style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.activity}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.seqQty}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"  style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.seqQty}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.completeddate}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent"  style="white-space: nowrap; word-spacing: normal;">
											<fmt:formatDate pattern="yyyy-MM-dd" value="${data.completeddate}" />

										</c:otherwise>
									</c:choose>
									
								</tr>
							</c:forEach>			
						</tbody>
					</TABLE>
				</c:otherwise>
			</c:choose>
</div>

<c:choose>
	<c:when test='${empty command.sequences}'>	</c:when>
<c:otherwise>  
<div align="right">
 	<b> <a href="#" name="excel" onclick="javascript:exportToExcel();" target="_self"> Export to Excel </a> </b> 
</div>
</c:otherwise>
</c:choose>
	 							
		</thead>
</FORM>