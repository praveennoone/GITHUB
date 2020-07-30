<%@ include file="/WEB-INF/jsp/common/includes.jsp" %>
<BODY onload = "document.loginFrm.userName.focus();">
<FORM name=loginFrm method=post class="form">
<div id="pageTitle"><span class="subHeader">Login Page<fmt:message key="header.password"/></span></div>
  <TABLE width="250" border="0" align="center" cellpadding="2" cellspacing="0" id="normalTableFlush4">
	<tr align = "center"><td align="center">
		<spring:bind path="command.*"> 			
			   <td> <font color="red"> Invalid username or password... </font></td>
		</spring:bind>
	</td></tr>
  </table>
<TABLE id="Table6" cellSpacing="1" cellPadding="5" border="0">
  <!--   <TR>
        <TD colspan="2"><img id="Image1" src="/loginhere.gif" alt="" border="0" /></TD>
    </TR> -->
    <TR>

        <TD colspan="2" style="HEIGHT: 18px"><a id="HyperLink1" class="LinkA" href="/ETutor/forgotPassword.htm" style="width:130px;">Forgot your password?</a></TD>
    </TR>
    <TR>
        <TD style="WIDTH: 82px"><STRONG>User ID:</STRONG></TD>
        <spring:bind path="command.userName">
        <TD><input name="userName" type="text" id="userName" tabindex="1" style="width:173px;" /></TD>
        </spring:bind>
    </TR>
    <TR>
        <TD style="WIDTH: 82px"><STRONG>Password:</STRONG></TD>
		 <spring:bind path="command.password">
        <TD><input name="password" type="password" id="password" tabindex="2" style="width:173px;" /></TD>
        </spring:bind>
    </TR>
    <TR>
        <TD style="WIDTH: 82px"></TD>
        <TD align="right"><input type="image" name="btnLogin" id="btnLogin" src="/ETutor/images/login/login.gif" alt="" border="0" /></TD>
    </TR>
       
</TABLE>
</form>
</body>