<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@taglib prefix="tiles" uri="/WEB-INF/tld/struts-tiles.tld" %>
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
									
  	<!-- STYLESHEETS -->
		<link rel="stylesheet" href="static/css/footer.css" type="text/css" media="all" /> 
		<link rel="stylesheet" href="static/css/common.css" type="text/css" media="all" />
	<!-- END STYLESHEETS -->	
 
	<!--[if IE 5]>
		<link rel="stylesheet" href="../common/ie5Home.css" type="text/css" media="all" />
	<![endif]-->
<!-- END HEAD -->
</head>

<body>
 	<div id="wrapper"> 
	<div id="header"><tiles:insert name="header"/></div>
	<table width="100%" class="wrapper" style="background-color: #FFFFFF" height="455" border=0 valign="top">
		<tr>
			<td valign="top">
				<div id="content"> <tiles:insert name="content"/> </div>
			</td>
		</tr>	
	</table>
	</div> 
	<div id="footer"><tiles:insert name="footer"/></div>
</body>