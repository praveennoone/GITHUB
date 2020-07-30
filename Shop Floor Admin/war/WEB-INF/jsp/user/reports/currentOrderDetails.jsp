<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
	function checkValues(e){
		var key=e.keyCode || e.which;
	
	if (key==13){
		fetchData();
	}
		
	}
	
	function upperCase(x) {
		var y=document.getElementById(x).value
		document.getElementById(x).value=y.toUpperCase()
	}
	
	function exportToExcel() {
		document.frmCurrentOrderDetails.toexcel.value='yes';
	//	document.activityRejectedFrm.action="ActivityRejectedToExcel.htm";
		document.frmCurrentOrderDetails.submit();
		return false;
	}
	
	function doCheck(criteria) {
		if(criteria == 'item') {
			document.getElementById("trItem").style.display='';
			document.getElementById("trMo").style.display='none';
			document.frmCurrentOrderDetails.partNumber.focus();
		} else {
			document.getElementById("trItem").style.display='none';
			document.getElementById("trMo").style.display='';
			document.frmCurrentOrderDetails.moNumber.focus();
		}
		document.frmCurrentOrderDetails.hiddenCriteria.value=criteria;
	}
	
	function fetchData() {
		var criteria=document.getElementById("hiddenCriteria");
	
		if (criteria.value=="mo") {
			if(document.frmCurrentOrderDetails.moNumber.value == "") {
				document.frmCurrentOrderDetails.moNumber.focus();
			} else {
				document.frmCurrentOrderDetails.toexcel.value='no';	
				document.frmCurrentOrderDetails.submit();
				return false;
			}
		} else {
			if (document.frmCurrentOrderDetails.partNumber.value == "") {
				document.frmCurrentOrderDetails.partNumber.focus();
			} else {
				document.frmCurrentOrderDetails.toexcel.value='no';	
				document.frmCurrentOrderDetails.submit();
				return false;
			}
		}
	}
	
	function checkSubmitValues(e){
		var key=e.keyCode || e.which;
		if (key==13){
			return checkAndSubmit();
		}
	}
	
	function checkAndSubmit() {
		var criteria=document.getElementById("hiddenCriteria");
		if (criteria.value=="mo") {
			if(document.getElementById('moFacility').selectedIndex== "0"){
				alert("Select a facility");
				return false;
			} else if(document.frmCurrentOrderDetails.moNumber.value == "") {
				alert("MO Number Cannot Be Empty");
				document.frmCurrentOrderDetails.moNumber.focus();
			} else {
				document.frmCurrentOrderDetails.toexcel.value='no'; 
				document.frmCurrentOrderDetails.submit();
				return false;
			}
		} 
		else {
			//alert(document.getElementById('itemFacility').selectedIndex);
			if(document.getElementById('itemFacility').selectedIndex== "0"){
				alert("Select a facility.");
				return false;
			}else
			if (document.frmCurrentOrderDetails.partNumber.value == "") {
				alert("Item Number Cannot Be Empty");
				document.frmCurrentOrderDetails.partNumber.focus();
			} else {
				document.frmCurrentOrderDetails.toexcel.value='no';	
				document.frmCurrentOrderDetails.submit();
				return false;
			}
		}		
	}

	


	function isNumberKey(evt) {
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode > 31 && (charCode < 48 || charCode > 57))
	      return false;
	
	   return true;
	}

</script>


<div id="content">
<BODY 
<c:choose>
<c:when test="${command.criteria eq 'mo'}">
onload="document.frmCurrentOrderDetails.moNumber.focus();"
</c:when>
<c:otherwise>
onload="document.frmCurrentOrderDetails.partNumber.focus();"
</c:otherwise>
</c:choose>
>
<div id="content"> 
	<FORM name=frmCurrentOrderDetails method=post class="form">
	<input type="hidden" name="toexcel">
	<br><br>
	<CENTER>
		<font size="2"> <b> <fmt:message key="table.currentOrderDetails.header"/> </b> </font>
	</CENTER>
	<br> 
	<center>
		<spring:hasBindErrors name="command">
			<c:forEach var="error" items="${errors.allErrors}">
				<h5 style="color: red;">${error.defaultMessage}</h5>
			</c:forEach>
		</spring:hasBindErrors>
	</center>
	<br>
	<TABLE border="0" cellpadding="0" cellspacing="0" width="60%" align="center" id="normalTableNostripe">
				<tr valign="top">   
					<td colspan = "4" align = "center"> 
							<input type="radio" id="criteria" tabindex="1" name="criteria" value="Item" onClick="javascript:doCheck('item')" <c:if test="${command.criteria eq 'Item'}">checked</c:if> > Item no 
							<input type="radio" id="criteria" tabindex="2" name="criteria" value="mo" onClick="javascript:doCheck('mo')" <c:if test="${command.criteria eq 'mo'}">checked</c:if> > MO Number<br>
					</td>
				</tr>
				<tr valign="center" id="trItem"
				<c:if test='${command.criteria eq "mo"}'>
				 style="display: none"
				 </c:if>
				>
					
					<td>
					
						<b><fmt:message key="table.currentOrderDetails.column5"/></b>  
																							
								<select name="itemFacility" id="itemFacility" >
									<option selected="selected"></option>
									<c:forEach items="${command.divisionFacilities}" var="data">
											<option <c:if test="${command.facility eq data.facilityId}"> <c:out value="selected"/></c:if> value="${data.facilityId}">${data.facilityId}</option>								
									</c:forEach>									 							
								</select>
					</td>				
					<td id="tdItemNumber"> 
						<b><fmt:message key="table.currentOrderDetails.column1"/></b>

						<input type="text" id="partNumber" onKeyDown="return checkValues(event);"
			                 onkeyup="upperCase(this.id)" name="partNumber" value="${command.partNumber}" > 

						

					</td>

					<td id="tdMoNumber"  > 
						<b><fmt:message key="table.currentOrderDetails.column2"/></b>
					<select name="moLine" tabindex="4" onChange="javascript:submit();return false;">

						<c:if test="${empty command.moLines}">
							<option>${command.emptyString}</option>
						</c:if>
						<c:forEach items="${command.moLines}" var="data" varStatus="list">
						<option value="${data.moNumber}" <c:if test="${command.moNumber eq data.moNumber}"> <c:out value="selected"/> </c:if> >
							 <c:out value="${data.moNumber}"/> 
						 </option>
						</c:forEach>
					</select>		 
					</td>

		
					<td> <input type="image" src="static/images/filter.gif" name="Filter" width="60" height="30" onClick=" disabled=true;return checkAndSubmit();" >
						
						
					</td>
				</tr>					
				<tr valign="center" id="trMo"
				<c:if test='${command.criteria eq "Item"}'>
				 style="display: none"
				 </c:if>
				 >

					
						<td>
						<b><fmt:message key="table.currentOrderDetails.column5"/></b>  
																
						<select name="moFacility" id="moFacility" >
									<option selected="selected"></option>
									<c:forEach items="${command.divisionFacilities}" var="data">
											<option <c:if test="${command.facility eq data.facilityId}"> <c:out value="selected"/></c:if> value="${data.facilityId}">${data.facilityId}</option>								
									</c:forEach>									 							
								</select>
</td>
						<td><b><fmt:message key="table.currentOrderDetails.column2"/></b>
						<input type="text" id="moNumber" name="moNumber" value="${command.moNumber}" onKeyDown="return checkSubmitValues(event);">

					</td>
					<td> <input type="image" src="static/images/filter.gif" name="Filter" width="60" height="30" onClick=" disabled=true;return checkAndSubmit();" >
						
					</td>
				</tr>
		</TABLE>
		
<br>
	<c:if test="${command.partDesc ne null}">
		<TABLE border="0" cellpadding="0" cellspacing="0" width="60%" align="center" id="normalTableNostripe">
			<tr>
	 			<td> <b>Part No/Part Desc :</b> <c:out value="${command.partNumber}"/> / <c:out value="${command.partDesc}"/>   </td>
	 			<td> <b>Order Qty :</b> <c:out value="${command.orderQty}"/> </td>
	 		</tr>
	 </table>
	</c:if>
<br>
<c:choose>
<c:when test="${not empty command.moNumber or not empty command.partNumber }">
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
					<div style=" width: 90%;"> 
						<TABLE id="normalTableNostripe" align="center" cellSpacing="0" cellPadding="0" border="0" style="white-space: nowrap; word-spacing: normal;">
							<thead class="fixedHeader">
								<tr class="selected" height="15px">
									<th style="width: 9%;"> <fmt:message key="table.currentOrderDetails.data3"/> </th>
									<th style="width: 9%;"> <fmt:message key="table.currentOrderDetails.data4"/> </th>
									<th style="width: 29%;"> <fmt:message key="table.currentOrderDetails.data5"/> </th>
									<th style="width: 10%;"> <fmt:message key="table.currentOrderDetails.data9"/> </th>
									<th style="width: 10%;"> <fmt:message key="table.currentOrderDetails.data10"/> </th>
									<th style="width: 10%;"> <fmt:message key="table.currentOrderDetails.data11"/> </th>
									<th style="width: 12%;"> <fmt:message key="table.currentOrderDetails.data7"/> </th>
									<th style="width: 11%;"> <fmt:message key="table.currentOrderDetails.data14"/> </th>
	 							</tr>
	 						</thead>
	 					</TABLE>
	 				</div>	
 					<div style="clear: both; border: 1px solid #FFF; height: 260px;	overflow: scroll; width: 90%;"> 
						<TABLE id="normalTableNostripe" align="center" cellSpacing="0" cellPadding="0" border="0" style="white-space: nowrap; word-spacing: normal;">
							<tbody style="display: block; height: 10px;	overflow: auto;">
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
											<td style="width: 9%;"> &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td style="width: 9%; white-space: nowrap; word-spacing: normal;"> <c:out value="${data.sequence}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.workCenterCode}'>
											<td style="width: 9%;"> &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td style="width: 9%; white-space: nowrap; word-spacing: normal;"> <c:out value="${data.workCenterCode}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.sequenceDescription}'>
											<td style="width: 30%;"> &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td style="width: 30%; white-space: nowrap; word-spacing: normal; width: 850px"> <c:out value="${data.sequenceDescription}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.setupStatus}'>
											<td style="width: 10%;"> &nbsp;</td>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${data.setupStatus eq 'Not Started'}">
													<td style="width: 10%; white-space: nowrap; word-spacing: normal; color: red"> <c:out value="${data.setupStatus}"></c:out> </td>
												</c:when>
												<c:otherwise>
													<td style="width: 10%; white-space: nowrap; word-spacing: normal;"> <c:out value="${data.setupStatus}"></c:out> </td>
												</c:otherwise>
											</c:choose>									
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.runStatus}'>
											<td style="width: 10%;"> &nbsp;</td>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${data.runStatus ne 'Not Started'}">
													<td style="width: 10%; white-space: nowrap; word-spacing: normal;"> <c:out value="${data.runStatus}"></c:out> </td>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${data.qtyCompleted ne 0.0}">
															<td style="width: 10%; white-space: nowrap; word-spacing: normal;"> Completed </td>
														</c:when>
														<c:otherwise>
															<td style="width: 10%; white-space: nowrap; word-spacing: normal; color: red"> <c:out value="${data.runStatus}"></c:out> </td>
														</c:otherwise>
													</c:choose>													
												</c:otherwise>												
											</c:choose>												
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.requiredDate}'>
											<td style="width: 10%;"> &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td style="width: 10%; white-space: nowrap; word-spacing: normal;">
												<fmt:formatDate pattern="yyyy-MM-dd" value="${data.requiredDate}" />
											</td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.qtyCompleted}'>
											<td style="width: 12%;"> &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td style="width: 12%; white-space: nowrap; word-spacing: normal;"> <c:out value="${data.qtyCompleted}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${data.runStatus eq 'Not Started'}">
											<td style="width: 10%;"> &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td style="width: 10%;"> 
												<A href="javascript:popupModalWithParams('sequenceDetails.htm?moNumber=${command.moNumber}&facility=${command.userFacility}&sequenceNumber=${data.sequence}&activityCode=${data.activityCode}','dialogWidth:900px; dialogHeight:750px; center:yes')" style="color: blue">Details</A>
											</td>
										</c:otherwise>
									</c:choose>
		
								</tr>
							</c:forEach>			
						</tbody>
					</TABLE>
				</c:otherwise>
			</c:choose>
			</c:when>
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
<input type="text" style="visibility: hidden" name="hiddenCriteria" id="hiddenCriteria" value="${command.criteria}">
</tbody>

</FORM>
</BODY>
