<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<TABLE borderColor=#111111 cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TBODY>
		<TR>
    		<TD vAlign=top width="100%" background="static/images/footer.JPG" width="973" border="0"
    		style="text-align: center;">
    			<font style="font-family: verdana; font-weight: normal; color: #1A4A6F;">
	    			| M3 Environment - 
	    		</font>
	    		<font style="font-family: verdana; font-weight: bold; color: #1A4A6F;">	
	    			<c:out value="${sessionScope.environment.m3Environment}"/>
	    		</font>
	    		<font style="font-family: verdana; font-weight: normal; color: #1A4A6F;">	 
	    			| Database Server -  
	    		</font>
	    		<font style="font-family: verdana; font-weight: bold; color: #1A4A6F;">	
	    			<c:out value="${sessionScope.environment.databaseServer}"/>  
	    		</font>
	    		<font style="font-family: verdana; font-weight: normal; color: #1A4A6F;">	
	    			| Database Name - 
	    		</font>
	    		<font style="font-family: verdana; font-weight: bold; color: #1A4A6F;">	
	    			<c:out value="${sessionScope.environment.databaseName}"/> 
    			</font>
    			<font style="font-family: verdana; font-weight: normal; color: #1A4A6F;">
    			  |
    			</font>
    			<c:if test='${not empty sessionScope.environment.division}'>
    			<font style="font-family: verdana; font-weight: normal; color: #1A4A6F;">	
	    			Division - 
	    		</font>
	    		<font style="font-family: verdana; font-weight: bold; color: #1A4A6F;">	
	    			<c:out value="${sessionScope.environment.division}"/> 
    			</font>
    			<font style="font-family: verdana; font-weight: normal; color: #1A4A6F;">
    			  |
    			</font>
    			</c:if>
    			<c:if test='${not empty sessionScope.environment.version}'>
    			<font style="font-family: verdana; font-weight: normal; color: #1A4A6F;">	
	    			Version - 
	    		</font>
	    		<font style="font-family: verdana; font-weight: bold; color: #1A4A6F;">	
	    			<c:out value="${sessionScope.environment.version}"/> 
    			</font>
    			<font style="font-family: verdana; font-weight: normal; color: #1A4A6F;">
    			  |
    			</font>
    			</c:if>
    			
    		</TD>
		</TR>
	</TBODY>
</TABLE> 
