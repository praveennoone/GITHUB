<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="mtg" uri="/WEB-INF/tld/LisiTags"%>

<script type="text/javascript" src='static/js/springxt.js'></script>
<script type="text/javascript" src='static/js/prototype.js'></script>
<script type="text/javascript" src='static/js/scriptaculous.js?load=effects,dragdrop'></script>

<script type="text/javascript">
function changeFlag(id, asset){
	if (document.getElementById(id).checked) {
		document.frmAssetDetails.qtyCaptureRequired.value = "Y";
	} else {
		document.frmAssetDetails.qtyCaptureRequired.value = "N";
	}
	document.frmAssetDetails.asset.value = asset;
	XT.doAjaxAction('changeQtyCaptureRequired', this);
}
</script>

<div id="content">
<BODY>
<c:set var="userCont" value="${sessionScope.userContext}"/>
<div id="content"> 
	<FORM name=frmAssetDetails method=post class="form">
	<input type="hidden" name="toexcel">
	<br>
	<CENTER>
		<font size="2"> <b> <fmt:message key="table.assetDetails.header"/> </b> </font>
	</CENTER>
	<center>
		<spring:hasBindErrors name="command">
			<c:forEach var="error" items="${errors.allErrors}">
				<h5 style="color: red;">${error.defaultMessage}</h5>
			</c:forEach>
		</spring:hasBindErrors>
	</center>
	<br>
			<TABLE border="0" cellpadding="0" cellspacing="0" width="40%" align="center" id="normalTableNostripe" valign="top">
				<tr valign="top">  
					<td width="30%"> 
						 <b><fmt:message key="table.assetDetails.column1"/></b>
					</td>
					<td width="70%" colspan="2">
						<b><fmt:message key="table.assetDetails.column2"/></b>
					</td>
				</tr>
				<tr valign="top">  	 
					<td width="20%">
						<input type="text" name="newDeptNumber"  size="5"> 
						<input type="submit" value="add" name="addDept" onclick="javascript:submit();"> 
					</td>	 
					<td  width="60%"> 
						<select name="deptNumber" style="width:220px;margin:5px 0 5px 0;font: small-caps 900 10px arial;color:#1A4A6F;" size="5">
								<c:forEach items="${command.departments}" var="data" varStatus="list">
								<option value="${data.deptNumber}" <c:if test="${command.deptNumber eq data.deptNumber}"> <c:out value="selected"/> </c:if> > <c:out value="${data.deptNumber}"/> - <c:out value="${data.deptName}"/> </option>
								</c:forEach>
						</select>
						</td><td>		
						<input type="button" value="Filter" name="deptFilter" onclick="javascript:submit();">
					</td>	 
								
				</tr>
			</TABLE>
			<br>
			<CENTER><div id="message" style="font-weight: bold;color: maroon"></div></CENTER><br>
			<TABLE id="normalTableNostripe" width="99%"  align="center" cellSpacing="0" cellPadding="0" border="0" style="white-space: nowrap; word-spacing: normal;">
			
				<tr>
					<td width="50%">
					<div class="tableContainerPop"> 
						<TABLE border="0" cellpadding="0" cellspacing="0" width="96%"  align="center" id="normalTableNostripe" valign="top">
						<thead class="fixedHeader">
								<tr class="selected" height="15px">
									<th class="tableTitle" > <fmt:message key="table.assetDetails.data1"/> </th>
									<th class="tableTitle" > <fmt:message key="table.assetDetails.data2"/> </th>
								</tr>
	 						<tbody class="scrollContent">
	 						<c:forEach items="${command.workcenters}" var="data" varStatus="list">
									<c:choose>
										<c:when test='${(list.index%2) == 0}'>
											<tr class="normalRow">
										</c:when>
										<c:otherwise>
											<tr class="alternateRow">
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.workcenterCode}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.workcenterCode}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.workcenterDesc}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent" style="white-space: nowrap; word-spacing: normal;"> <c:out value="${data.workcenterDesc}"></c:out> </td>
										</c:otherwise>
									</c:choose>
								</tr>	
							</c:forEach>	
						</table>		
					</td>
					</div>
					<td  width="50%">
					
					
					<div class="tableContainerPop"> 
						<TABLE border="0" cellpadding="0" cellspacing="0"  width="96%" align="center" id="normalTableNostripe" >
							<thead class="fixedHeader">
								<tr class="selected" height="15px">
									<th class="tableTitle" > <fmt:message key="table.assetDetails.data3"/> </th>
									<th class="tableTitle" > <fmt:message key="table.assetDetails.data4"/> </th>
									<th class="tableTitle" > Qty Capture Required </th>
								</tr>
	 						<tbody class="scrollContent">
							<c:forEach items="${command.assets}" var="data" varStatus="list">
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
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent" style="white-space: nowrap; word-spacing: normal; "> <c:out value="${data.assetNumber}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<c:choose>					
										<c:when test='${empty data.assetDescNumber}'>
											<td class="tableContent" > &nbsp;</td>
										</c:when>
										<c:otherwise>
											<td class="tableContent" style="white-space: nowrap; word-spacing: normal; width: 1300px"> <c:out value="${data.assetDescNumber}"></c:out> </td>
										</c:otherwise>
									</c:choose>
									<td>
										<input type="checkbox" name="flag${data.assetNumber}" id="flag${data.assetNumber}" 
										<c:if test="${data.quantityCaptureRequired eq 'Y'}">
											checked=checked 
										</c:if>
										<c:if test="${not mtg:contains(userCont.roles,'ShopFloorManagers')}">
											disabled
										</c:if>
										onClick="javascript:changeFlag('flag${data.assetNumber}', '${data.assetNumber}' );"/>
									</td>
								</tr>	
							</c:forEach>	
							</td>
							</tr>
						</table>		
					</td>
					</div>
				</tr>				
						</table>
						<input type="hidden" name="" id="department" />
						<input type="hidden" name="" id="asset" />
						<input type="hidden" name="" id="qtyCaptureRequired" />
</FORM>
</BODY>
