<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="mtg" uri="/WEB-INF/tld/LisiTags"%>
<!------------------------------------------------------------------------------------------------------------->
<!-- FROM HERE UNTIL THE </HEAD> TAG SHOULD -NOT- BE MODIFIED -->
<!------------------------------------------------------------------------------------------------------------->

<!-- DHTML DROP DOWN MENU -->
<script language="JavaScript1.2" src="static/js/api.js"
	type="text/javascript"></script>
<script language="JavaScript1.2" src="static/js/menucode.js"
	type="text/javascript"></script>
<script language="JavaScript1.2" type="text/javascript">

// DETECT BROWSER VERSION TO LOAD APPROPRIATE STYLESHEET		 
browserName = navigator.appName; 
browserVersion = parseInt(navigator.appVersion);  

if (browserName == "Netscape" && browserVersion == 5) 
    browser = "nn6"; 
else if (browserName == "Netscape" && browserVersion == 4) 
    browser= "nn4"; 
else if (browserName == "Netscape" && browserVersion == 3) 
    browser = "nn3"; 
else if (browserName == "Microsoft Internet Explorer" && 
         browserVersion == 4 && 
         navigator.appVersion.indexOf("MSIE 5.5") != -1) 
    browser = "ie55";
else if (browserName == "Microsoft Internet Explorer" && 
         browserVersion == 4 && 
         navigator.appVersion.indexOf("MSIE 5.0") != -1) 
    browser = "ie5"; 
else if (browserName == "Microsoft Internet Explorer" 
         && browserVersion == 4) 
    browser = "ie4";             

// Handle browser-specific code
if (browser == "ie55" || browser == "ie5") { 
    document.write("<link REL='stylesheet' HREF='static/css/stylesheetIE55.css' TYPE='text/css'>");
} else { 
    document.write("<link REL='stylesheet' HREF='static/css/stylesheet.css' TYPE='text/css'>");
}  
//--> 
<c:set var="userCont" value="${sessionScope.userContext}"/>
sniffBrowsers();

menuItemBullet = new bulletPoint("static/bullets/one/menu_off.gif","static/bullets/one/menu_on.gif");
labelBullet = new bulletPoint("static/bullets/one/header_off.gif","static/bullets/one/header_on.gif");
subMenuBullet = new bulletPoint("static/bullets/one/sub_header_off.gif","static/bullets/one/sub_header_on.gif");
blankBullet = new bulletPoint("static/bullets/one/blank_off.gif","static/bullets/one/blank_on.gif");

dropDownMenu = new menuBar('dropDownMenu', 623, 'horizontal', '#4787A9', '#4787A9');
<%-- Begin WO# 27639 - Moving Static links to dynamic -  Pinky - Infosys - 23 June 2011 --%>
<c:choose>
	<c:when test="${mtg:contains(userCont,userCont.roles,'Activities',false)}">
		<c:choose>
			<c:when test="${mtg:contains(userCont,userCont.roles,'Configuration',false)}">
				dropDownMenu.addLabel('blankBullet', 'Home', 1, 95, '#96D1EB', '#5DB7DE', 'home.htm', 'center');
				dropDownMenu.addLabel('blankBullet', 'Shop Floor Reports', 2, 180, '#96D1EB', '#5DB7DE', '#', 'center');
				dropDownMenu.addLabel('blankBullet', 'Activities', 3, 180, '#96D1EB', '#5DB7DE', '#', 'center');
				dropDownMenu.addLabel('blankBullet', 'Configuration', 4, 180, '#96D1EB', '#5DB7DE', '#', 'center');
				dropDownMenu.addLabel('blankBullet', 'Logoff', 5, 100, '#96D1EB', '#5DB7DE', 'logout.htm', 'center');
				dropDownMenu.height = 20;
				menus[1] = new menu(95, 'vertical', '#4787A9', '#4787A9');
				menus[1].height = 20;
				menus[1].writeMenu();
				
				menus[2] = new menu(170, 'vertical', '#4787A9', '#4787A9');
				menus[2].height = 20;
				<c:if test="${mtg:contains(userCont,userCont.roles,'production.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Production Report', null, 180, '#96D1EB', '#5DB7DE', 'production.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'costCenterProductionReport.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Production Report - By Cost Center', null, 180, '#96D1EB', '#5DB7DE', 'costCenterProductionReport.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'facilityProductionReport.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Production Report - By Facility', null, 180, '#96D1EB', '#5DB7DE', 'facilityProductionReport.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'currentOrderDetails.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Process Router Report', null, 180, '#96D1EB', '#5DB7DE', 'currentOrderDetails.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'correctionTrackingDetails.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Corrections Tracking Report', null, 180, '#96D1EB', '#5DB7DE', 'correctionTrackingDetails.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'m3TransactionReconciliation.htm',true)}">
						menus[2].addItem('menuItemBullet', 'M3 Transaction - Reconciliation', null, 180, '#96D1EB', '#5DB7DE', 'm3TransactionReconciliation.htm', 'left');
				</c:if>
				menus[2].writeMenu();
				
				menus[3] = new menu(150, 'vertical', '#4787A9', '#4787A9');
				menus[3].height = 20;
				<c:if test="${mtg:contains(userCont,userCont.roles,'activityRejection.htm',true)}">
						menus[3].addItem('menuItemBullet', 'Activity Rejection', null, 170, '#96D1EB', '#5DB7DE', 'activityRejection.htm', 'left');
				</c:if>
				
				<c:if test="${mtg:contains(userCont,userCont.roles,'resetItemWeight.htm',true)}">
						menus[3].addItem('menuItemBullet', 'Reset Item Weight', null, 170, '#96D1EB', '#5DB7DE', 'resetItemWeight.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'updateItemWeight.htm',true)}">
						menus[3].addItem('menuItemBullet', 'Update Item Weight', null, 170, '#96D1EB', '#5DB7DE', 'updateItemWeight.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'pphCorrectionInterface.htm',true)}">
						menus[3].addItem('menuItemBullet', 'PPH Error Correction ', null, 170, '#96D1EB', '#5DB7DE', 'pphCorrectionInterface.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'correctionInterfaceModifyMO.htm',true)}">
					menus[3].addItem('menuItemBullet', 'Correction Interface Modify MO', null, 170, '#96D1EB', '#5DB7DE', 'correctionInterfaceModifyMO.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'reverseOutsideOperation.htm',true)}">
						menus[3].addItem('menuItemBullet', 'Reverse Outside Operation', null, 170, '#96D1EB', '#5DB7DE', 'reverseOutsideOperation.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'reverseInspectionOperation.htm',true)}">
						menus[3].addItem('menuItemBullet', 'Reverse Inspection', null, 170, '#96D1EB', '#5DB7DE', 'reverseInspectionOperation.htm', 'left');
				</c:if>
				
				menus[3].writeMenu(); 
				
				<%-- Begin - 26978 - new interface Quality Standard Time - Pinky - Infosys - 20 Apr 2011 --%>
				
				menus[4] = new menu(180, 'vertical', '#4787A9', '#4787A9');
				menus[4].height = 20;
				
				<c:if test="${mtg:contains(userCont,userCont.roles,'qualityStandardTime.htm',true)}">
						menus[4].addItem('menuItemBullet', 'Configure Quality Standard Time', null, 170, '#96D1EB', '#5DB7DE', 'qualityStandardTime.htm', 'left');
				</c:if>
				<%-- End - 26978 - new interface Quality Standard Time - Pinky - Infosys - 20 Apr 2011 --%>
				
				<!-- Begin WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr 2011 -->
				<!-- Adding the Work Center Configuration in the Activities User Menu -->
				<c:if test="${mtg:contains(userCont,userCont.roles,'workCenterConfig.htm',true)}">
						menus[4].addItem('menuItemBullet', 'Configure Work Center', null, 170, '#96D1EB', '#5DB7DE', 'workCenterConfig.htm', 'left');
				</c:if>
				<!-- Adding the Asset Configuration in the Activities User Menu -->
				<c:if test="${mtg:contains(userCont,userCont.roles,'assetConfiguration.htm',true)}">
						menus[4].addItem('menuItemBullet', 'Configure Asset', null, 170, '#96D1EB', '#5DB7DE', 'assetConfiguration.htm', 'left');
				</c:if>
				<!-- Adding the Reason Master Configuration in the Activities User Menu -->
				<c:if test="${mtg:contains(userCont,userCont.roles,'configReason.htm',true)}">
						menus[4].addItem('menuItemBullet', 'Configure Reason Codes', null, 170, '#96D1EB', '#5DB7DE', 'configReason.htm', 'left');
				</c:if>
				<!-- Adding the Configuration Division in the Activities User Menu -->
				<c:if test="${mtg:contains(userCont,userCont.roles,'configDivision.htm',true)}">
						menus[4].addItem('menuItemBullet', 'Configure Division Parameters', null, 170, '#96D1EB', '#5DB7DE', 'configDivision.htm', 'left');
				</c:if>
				<!-- End WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr 2011 -->
				
				menus[4].writeMenu();
				
				menus[5] = new menu(100, 'vertical', '#4787A9', '#4787A9');
				menus[5].height = 20;
				menus[5].writeMenu();
				
				menus[1].align='right';
				menus[2].align='right';
				menus[3].align='right';
				menus[4].align='right';
				menus[5].align='right';
			</c:when>
			<c:otherwise>
				dropDownMenu.addLabel('blankBullet', 'Home', 1, 95, '#96D1EB', '#5DB7DE', 'home.htm', 'center');
				dropDownMenu.addLabel('blankBullet', 'Shop Floor Reports', 2, 180, '#96D1EB', '#5DB7DE', '#', 'center');
				dropDownMenu.addLabel('blankBullet', 'Activities', 3, 180, '#96D1EB', '#5DB7DE', '#', 'center');
				dropDownMenu.addLabel('blankBullet', 'Logoff', 4, 100, '#96D1EB', '#5DB7DE', 'logout.htm', 'center');
				dropDownMenu.height = 20;
				menus[1] = new menu(95, 'vertical', '#4787A9', '#4787A9');
				menus[1].height = 20;
				menus[1].writeMenu();
				
				menus[2] = new menu(170, 'vertical', '#4787A9', '#4787A9');
				menus[2].height = 20;
				<c:if test="${mtg:contains(userCont,userCont.roles,'production.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Production Report', null, 180, '#96D1EB', '#5DB7DE', 'production.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'costCenterProductionReport.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Production Report - By CostCenter', null, 180, '#96D1EB', '#5DB7DE', 'costCenterProductionReport.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'facilityProductionReport.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Production Report - By Facility', null, 180, '#96D1EB', '#5DB7DE', 'facilityProductionReport.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'currentOrderDetails.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Process Router Report', null, 180, '#96D1EB', '#5DB7DE', 'currentOrderDetails.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'correctionTrackingDetails.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Corrections Tracking Report', null, 180, '#96D1EB', '#5DB7DE', 'correctionTrackingDetails.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'m3TransactionReconciliation.htm',true)}">
						menus[2].addItem('menuItemBullet', 'M3 Transaction - Reconciliation', null, 180, '#96D1EB', '#5DB7DE', 'm3TransactionReconciliation.htm', 'left');
				</c:if>
				menus[2].writeMenu();
				
				menus[3] = new menu(150, 'vertical', '#4787A9', '#4787A9');
				menus[3].height = 20;
				<c:if test="${mtg:contains(userCont,userCont.roles,'activityRejection.htm',true)}">
						menus[3].addItem('menuItemBullet', 'Activity Rejection', null, 170, '#96D1EB', '#5DB7DE', 'activityRejection.htm', 'left');
				</c:if>
				
				<c:if test="${mtg:contains(userCont,userCont.roles,'resetItemWeight.htm',true)}">
						menus[3].addItem('menuItemBullet', 'Reset Item Weight', null, 170, '#96D1EB', '#5DB7DE', 'resetItemWeight.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'updateItemWeight.htm',true)}">
						menus[3].addItem('menuItemBullet', 'Update Item Weight', null, 170, '#96D1EB', '#5DB7DE', 'updateItemWeight.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'pphCorrectionInterface.htm',true)}">
						menus[3].addItem('menuItemBullet', 'PPH Error Correction ', null, 170, '#96D1EB', '#5DB7DE', 'pphCorrectionInterface.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'correctionInterfaceModifyMO.htm',true)}">
					menus[3].addItem('menuItemBullet', 'Correction Interface Modify MO', null, 170, '#96D1EB', '#5DB7DE', 'correctionInterfaceModifyMO.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'reverseOutsideOperation.htm',true)}">
						menus[3].addItem('menuItemBullet', 'Reverse Outside Operation', null, 170, '#96D1EB', '#5DB7DE', 'reverseOutsideOperation.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'reverseInspectionOperation.htm',true)}">
						menus[3].addItem('menuItemBullet', 'Reverse Inspection', null, 170, '#96D1EB', '#5DB7DE', 'reverseInspectionOperation.htm', 'left');
				</c:if>
				menus[3].writeMenu();
				
				menus[4] = new menu(100, 'vertical', '#4787A9', '#4787A9');
				menus[4].height = 20;
				menus[4].writeMenu();
				
				menus[1].align='right';
				menus[2].align='right';
				menus[3].align='right';
				menus[4].align='right';
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${mtg:contains(userCont,userCont.roles,'Configuration',false)}">
				dropDownMenu.addLabel('blankBullet', 'Home', 1, 95, '#96D1EB', '#5DB7DE', 'home.htm', 'center');
				dropDownMenu.addLabel('blankBullet', 'Shop Floor Reports', 2, 180, '#96D1EB', '#5DB7DE', '#', 'center');
				dropDownMenu.addLabel('blankBullet', 'Configuration', 3, 180, '#96D1EB', '#5DB7DE', '#', 'center');
				dropDownMenu.addLabel('blankBullet', 'Logoff', 4, 100, '#96D1EB', '#5DB7DE', 'logout.htm', 'center');
				dropDownMenu.height = 20;
				menus[1] = new menu(95, 'vertical', '#4787A9', '#4787A9');
				menus[1].height = 20;
				menus[1].writeMenu();
				
				menus[2] = new menu(170, 'vertical', '#4787A9', '#4787A9');
				menus[2].height = 20;
				<c:if test="${mtg:contains(userCont,userCont.roles,'production.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Production Report', null, 180, '#96D1EB', '#5DB7DE', 'production.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'costCenterProductionReport.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Production Report - By CostCenter', null, 180, '#96D1EB', '#5DB7DE', 'costCenterProductionReport.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'facilityProductionReport.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Production Report - By Facility', null, 180, '#96D1EB', '#5DB7DE', 'facilityProductionReport.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'currentOrderDetails.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Process Router Report', null, 180, '#96D1EB', '#5DB7DE', 'currentOrderDetails.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'correctionTrackingDetails.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Corrections Tracking Report', null, 180, '#96D1EB', '#5DB7DE', 'correctionTrackingDetails.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'m3TransactionReconciliation.htm',true)}">
						menus[2].addItem('menuItemBullet', 'M3 Transaction - Reconciliation', null, 180, '#96D1EB', '#5DB7DE', 'm3TransactionReconciliation.htm', 'left');
				</c:if>
				menus[2].writeMenu();
				
				menus[3] = new menu(180, 'vertical', '#4787A9', '#4787A9');
				menus[3].height = 20;
				
				<c:if test="${mtg:contains(userCont,userCont.roles,'qualityStandardTime.htm',true)}">
						menus[3].addItem('menuItemBullet', 'Configure Quality Standard Time', null, 170, '#96D1EB', '#5DB7DE', 'qualityStandardTime.htm', 'left');
				</c:if>
				<%-- End - 26978 - new interface Quality Standard Time - Pinky - Infosys - 20 Apr 2011 --%>
				
				<!-- Begin WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr 2011 -->
				<!-- Adding the Work Center Configuration in the Activities User Menu -->
				<c:if test="${mtg:contains(userCont,userCont.roles,'workCenterConfig.htm',true)}">
						menus[3].addItem('menuItemBullet', 'Configure Work Center', null, 170, '#96D1EB', '#5DB7DE', 'workCenterConfig.htm', 'left');
				</c:if>
				<!-- Adding the Asset Configuration in the Activities User Menu -->
				<c:if test="${mtg:contains(userCont,userCont.roles,'assetConfiguration.htm',true)}">
						menus[3].addItem('menuItemBullet', 'Configure Asset', null, 170, '#96D1EB', '#5DB7DE', 'assetConfiguration.htm', 'left');
				</c:if>
				<!-- Adding the Reason Master Configuration in the Activities User Menu -->
				<c:if test="${mtg:contains(userCont,userCont.roles,'configReason.htm',true)}">
						menus[3].addItem('menuItemBullet', 'Configure Reason Codes', null, 170, '#96D1EB', '#5DB7DE', 'configReason.htm', 'left');
				</c:if>
				<!-- Adding the Configuration Division in the Activities User Menu -->
				<c:if test="${mtg:contains(userCont,userCont.roles,'configDivision.htm',true)}">
						menus[3].addItem('menuItemBullet', 'Configure Division Parameters', null, 170, '#96D1EB', '#5DB7DE', 'configDivision.htm', 'left');
				</c:if>
				<!-- End WO# 26989 - PPH Threshold Interface - Ambrish.V - Infosys - 20 Apr 2011 -->
				
				menus[3].writeMenu();
				
				menus[4] = new menu(100, 'vertical', '#4787A9', '#4787A9');
				menus[4].height = 20;
				menus[4].writeMenu();
				
				menus[1].align='right';
				menus[2].align='right';
				menus[3].align='right';
				menus[4].align='right';
			</c:when>
			<c:otherwise>
				dropDownMenu.addLabel('blankBullet', 'Home', 1, 95, '#96D1EB', '#5DB7DE', 'home.htm', 'center');
				dropDownMenu.addLabel('blankBullet', 'Shop Floor Reports', 2, 180, '#96D1EB', '#5DB7DE', '#', 'center');
				dropDownMenu.addLabel('blankBullet', 'Logoff', 3, 100, '#96D1EB', '#5DB7DE', 'logout.htm', 'center');
				dropDownMenu.height = 20;
				menus[1] = new menu(95, 'vertical', '#4787A9', '#4787A9');
				menus[1].height = 20;
				menus[1].writeMenu();
				
				menus[2] = new menu(170, 'vertical', '#4787A9', '#4787A9');
				menus[2].height = 20;
				<c:if test="${mtg:contains(userCont,userCont.roles,'production.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Production Report', null, 180, '#96D1EB', '#5DB7DE', 'production.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'costCenterProductionReport.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Production Report - By CostCenter', null, 180, '#96D1EB', '#5DB7DE', 'costCenterProductionReport.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'facilityProductionReport.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Production Report - By Facility', null, 180, '#96D1EB', '#5DB7DE', 'facilityProductionReport.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'currentOrderDetails.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Process Router Report', null, 180, '#96D1EB', '#5DB7DE', 'currentOrderDetails.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'correctionTrackingDetails.htm',true)}">
						menus[2].addItem('menuItemBullet', 'Corrections Tracking Report', null, 180, '#96D1EB', '#5DB7DE', 'correctionTrackingDetails.htm', 'left');
				</c:if>
				<c:if test="${mtg:contains(userCont,userCont.roles,'m3TransactionReconciliation.htm',true)}">
						menus[2].addItem('menuItemBullet', 'M3 Transaction - Reconciliation', null, 180, '#96D1EB', '#5DB7DE', 'm3TransactionReconciliation.htm', 'left');
				</c:if>
				menus[2].writeMenu();
				
				menus[3] = new menu(100, 'vertical', '#4787A9', '#4787A9');
				menus[3].height = 20;
				menus[3].writeMenu();
			</c:otherwise>
		</c:choose>
	</c:otherwise>		
</c:choose>
<%-- End WO# 27639 - Moving Static links to dynamic -  Pinky - Infosys - 23 June 2011 --%>
</script>


<!-- END DHTML DROP DOWN MENU -->

<!------------------------------------------------------------------------------------------------------------->
