<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="tiles" uri="/WEB-INF/tld/struts-tiles.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

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
    <link rel="stylesheet" href="static/css/scroll.css" type="text/css" media="all" />
    <link rel="stylesheet" href="static/css/table.css" type="text/css" media="all" />
    <link rel="stylesheet" href="static/css/datechooser.css" type="text/css" media="all" /> 
    <!--[if lte IE 6.5]>
	<link rel="stylesheet" type="text/css" href="static/css/select-free.css"/>
	<![endif]-->
	<!-- END STYLESHEETS -->	
	<script src="static/js/calendar2.js"></script>
	<script src="static/js/datetimepicker.js"></script>		
	<script language="JavaScript1.2" src="static/js/api.js" type="text/javascript"></script>
	<script language="JavaScript1.2" src="static/js/menucode.js" type="text/javascript"></script>
	<script language="JavaScript1.2" src="static/js/common.js" type="text/javascript"></script>
	<script src="static/js/date-functions.js" type="text/javascript"></script>
	<script src="static/js/datechooser.js" type="text/javascript"></script>
	<script language="JavaScript1.2" src="static/js/date.js" type="text/javascript"></script>
	<script language="JavaScript1.2" src="static/js/lisi.js" type="text/javascript"></script>
	<script type="text/javascript" src='static/js/springxt.js'></script>
<script type="text/javascript" src='static/js/custom.js'></script>
	<script src="static/js/datetimepicker_css.js" type="text/javascript"></script>
	<script type="text/javascript" src='static/js/prototype.js'></script>
<script type="text/javascript" src='static/js/scriptaculous.js?load=effects'></script>
	<div id="userMenu"> <tiles:insert name="userMenu"/> </div>  			
</head>

<body>
	<div id="wrapper">
		
		<div id="header"> <tiles:insert name="header"/> </div>

		<table width="100%" style="background-color: #96D1EB" height="10" cellpadding = "0" cellspacing ="0">
			<tr valign="top">
				<td width="60%">
					<div id="menu"> <tiles:insert name="topLeftcontent"/> </div>
				</td>
				<td width="40%">
					<div align="center">
		  				<div id="DropDownMenu">
  							<script language="JavaScript1.2" type="text/javascript">dropDownMenu.writeMenuBar();</script>
						</div>
					</div> 
				</td>
			</tr>
		</table>
		 
		<table width="100%" style="background-color: #FFFFFF" height="455" border=0 valign="top">
			<tr valign="top">
				<td>
					<div id="content"> <tiles:insert name="content"/> </div>
				</td>
			</tr>
		</table>	
	</div>
	
	<div id="footer"><tiles:insert name="footer"/></div>
	
</body>