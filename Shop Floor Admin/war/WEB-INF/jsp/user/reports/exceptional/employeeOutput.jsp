<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
function checkValues() {
		document.PerformanceAssetwiseFrm.submit();
		return false;
	}
</script>

<BODY >
<div id="content">
<FORM name="EmployeeOutputFrm" method=post class="form">
 	<input type="hidden" name="toexcel">
 	
 	<CENTER>
		<font size="2"> <b> <div id="headerText"><fmt:message key="table.EmployeeOutput.header"/></div> </b> </font>
	</CENTER>
	
	<br>
	<TABLE border="0" cellpadding="0" cellspacing="0" width="75%" align="center" id="normalTableNostripe">
				<tr valign="top" >  
					<td rowspan="2" valign="top">
						  <b> <fmt:message key="table.EmployeeOutput.column1"/> </b><br>
							
							<select name="department" id="department"
			                             style="width:180px;margin:5px 0 5px 0;font: small-caps 900 10px arial;color:#1A4A6F;"
			                              size="4" >
			                       <option value="ALL" selected >ALL</option>
			                     <c:forEach items="${command.departments}" var="data" varStatus="list">
				                   <option value="${data.deptNumber}" <c:if test="${command.department eq data.deptNumber}">
				                    <c:out value="selected"/> </c:if> > <c:out value="${data.deptNumber}  --> ${data.deptName}"> </c:out> </option>
			                     </c:forEach>
				                        
		                  </select>	
					</td>
					
					<td border="0" style="font: 900 11px arial;left:20px;top:220px">
						<fmt:message key="table.EmployeeOutput.column2" /><br>
					
				
						<div id="chooserSpan" class="dateChooser select-free"
							style="display: none; visibility: hidden; width: 160px;"></div>
						    <input type="text" id="fromDate" name="fromDate" readonly="readonly"
							value="${command.fromDate}"> <img src="static/images/cal.gif"
							onclick="showChooser(this, 'fromDate', 'chooserSpan', 1950, 2020, Date.patterns.USDatePattern, true);">
				
				  </td style="font: 900 12px arial;left:20px;top:220px">
				  
				   <td border="0" style="font: 900 11px arial;left:20px;top:220px">
						<fmt:message key="table.EmployeeOutput.column3" /><br>
					
				
						<div id="chooserSpan2" class="dateChooser select-free"
							style="display: none; visibility: hidden; width: 160px;"></div>
						<input type="text" id="toDate" name="toDate" readonly="readonly"
							value="${command.toDate}"> <img src="static/images/cal.gif"
							onclick="showChooser(this, 'toDate', 'chooserSpan2', 1950, 2020, Date.patterns.USDatePattern, true);">
		            </td>
		            
		            <td rowspan="2"> <input type="submit" value="Filter" name="EmployeeOutputFilter" onClick="javascript:doOnSubmit();" > </td>
					</tr>
	
	</table>	
	</form>
	</body>
					
					