<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="menu">
<p align="left"> <b> Welcome <c:out value="${sessionScope.userContext.displayName}"></c:out> </b>	</p>
</div>