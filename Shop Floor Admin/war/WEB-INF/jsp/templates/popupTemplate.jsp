<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="tiles" uri="/WEB-INF/tld/struts-tiles.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>	
  <head> 	
    <title><tiles:getAsString name="title"/></title>
    
    <!-- META TAGS -->
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	<meta name="Distribution" content="global" />
	<meta name="Revisit-After" content="60 days" />
	<meta name="Robots" content="all" />
	<meta name="Keywords" content="" />
	<meta name="Description" content="" />
<!-- end meta tags -->								

	<link rel="stylesheet" href="static/css/footer.css" type="text/css" media="all" /> 
    <link rel="stylesheet" href="static/css/common.css" type="text/css" media="all" /> 
    <link rel="stylesheet" href="static/css/scroll.css" type="text/css" media="all" /> 
	
   <title><tiles:getAsString name="title"/></title>
   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
 </head>

 <body>		

  <div id="content">
	<tiles:insert  name="content" />
  </div>

 </body>
</html>
