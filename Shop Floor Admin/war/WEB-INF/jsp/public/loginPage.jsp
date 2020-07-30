
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<BODY onload = "document.loginFrm.password.focus();">
<script type="text/javascript">
function submitForm() {
	if (event.keyCode == 13) {
		document.loginFrm.submit();
		return false;
	}
}
</script>
<div id="content">
<FORM name=loginFrm method="post" class="form" id="loginFrm" action="">
<!-- <div id="pageTitle"><span class="subHeader">Login Page</span></div> -->
  <TABLE border="0" align="center" cellpadding="2" cellspacing="0">
	<tr align = "center"><td class ="errorMessage" align="center">
		<spring:bind path="command.*"> 
			<font color="red">
			   <c:forEach items="${status.errorMessages}" var="error">
			     <c:out value="${error}"/><br/>
			   </c:forEach>
			</font><br/>
		</spring:bind>	
	</td></tr>
  </table>

<TABLE id="normalTableFlush4" align="center" cellSpacing="1" cellPadding="5" border="0">
    <TR>
        <TD style="WIDTH: 82px" class="selected"><STRONG>User ID:</STRONG></TD>
        <spring:bind path="command.userName">
        <TD>
        <input name="userName" type="text" id="userName" tabindex="1" style="width:173px;" value="<c:out value='${command.userName}'/>" />
        </TD>
        </spring:bind>
    </TR>
    <TR>
        <TD style="WIDTH: 82px" class="selected"><STRONG>Password:</STRONG></TD>
        <TD><input name="password" type="password" id="password" tabindex="2" style="width:173px;"/></TD>        
    </TR>
    <tr>
    	<TD style="WIDTH: 82px" class="selected"><STRONG>Division:</STRONG></TD>
  		<td>
    		<select name="selectedDivision" onkeypress="submitForm()">
    		
        	<c:forEach var="data" items="${command.divisions}">
        		<option value="${data}" 
        		<c:if test="${command.division != null && command.division == data}"> selected = selected</c:if>>${data}</option>
        	</c:forEach>
        </select>
        </td>
    </tr>   
    <TR>
        <TD style="WIDTH: 82px"></TD> 
        <TD align="right">
	        	<input type="image" name="btnLogin" id="btnLogin" src="static/images/login.gif" alt="" border="0"/>
        </TD>
    </TR>
</TABLE>
</form>
</div>
<c:if test="${command.welcomeMessage.text != null && not empty command.welcomeMessage.text}">
    <table align="center" cellSpacing="1" cellPadding="5" border="0">
        <tr>
            <c:if test="${command.welcomeMessage.type == 'INFO'}">
                <td><img src="static/images/info.png"/></td>
                <td style="border: 1px solid #1F4D78; background-color: #DEEAF6">
                       <%--  ${command.welcomeMessage.text} --%>
                       <%= request.getAttribute("dynamicMessage") %>
                </td>
            </c:if>
            <c:if test="${command.welcomeMessage.type == 'WARN'}">
                <td><img src="static/images/warn.png"/></td>
                <td style="border: 1px solid #FFD966; background-color: #FFF2CC">
                        <%-- ${command.welcomeMessage.text} --%>
                        <%= request.getAttribute("dynamicMessage") %>
                </td>
            </c:if>
        </tr>
    </table>
</c:if>
</body>