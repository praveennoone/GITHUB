

<!------------------------------------------------------------------------------------------------------------->
<!-- FROM HERE UNTIL THE </HEAD> TAG SHOULD -NOT- BE MODIFIED -->
<!------------------------------------------------------------------------------------------------------------->

<!-- DHTML DROP DOWN MENU -->
<script language="JavaScript1.2" src="static/js/api.js" type="text/javascript"></script>
<script language="JavaScript1.2" src="static/js/menucode.js" type="text/javascript"></script>
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

sniffBrowsers();

menuItemBullet = new bulletPoint("static/bullets/one/menu_off.gif","static/bullets/one/menu_on.gif");
labelBullet = new bulletPoint("static/bullets/one/header_off.gif","static/bullets/one/header_on.gif");
subMenuBullet = new bulletPoint("static/bullets/one/sub_header_off.gif","static/bullets/one/sub_header_on.gif");
blankBullet = new bulletPoint("static/bullets/one/blank_off.gif","static/bullets/one/blank_on.gif");

// LEGEND - dropDownMenu.addLabel('Type of bullet', 'Name of Link', 'Menu no.', 'Menu length', 'Square mouse off color', 'Square mouse on color', 'Link', 'Text alignment');
dropDownMenu = new menuBar('dropDownMenu',630, 'horizontal', '#4787A9', '#4787A9');
dropDownMenu.addLabel('blankBullet', 'Home', 1, 55, '#96D1EB', '#5DB7DE', '#', 'center');
dropDownMenu.addLabel('blankBullet', 'Level 1', 2, 95, '#96D1EB', '#5DB7DE', '#', 'center');
dropDownMenu.addLabel('blankBullet', 'Level 1', 3, 130, '#96D1EB', '#5DB7DE', '#', 'center');
dropDownMenu.addLabel('blankBullet', 'Level 1', 6, 110, '#96D1EB', '#5DB7DE', '#', 'center');
dropDownMenu.height = 20;

// NOTE - THESE MENUS MUST BE IN NUMBER ORDER, 1 2 3 ETC, TO WORK. EG - PUT MENU 9 LAST EVEN IF IT'S RELATED TO MENU 2
menus[1] = new menu(55, 'vertical', '#4787A9', '#4787A9');
menus[1].height = 20;
menus[1].writeMenu();

// MY_AGX SUBMENU
menus[2] = new menu(150, 'vertical', '#4787A9', '#4787A9');
menus[2].height = 20;
menus[2].addItem('subMenuBullet', 'Level 2', 8, 150, '#96D1EB', '#5DB7DE', '#', 'left');
menus[2].addItem('subMenuBullet', 'Level 2', 7, 150, '#96D1EB', '#5DB7DE', '#', 'left');
menus[2].addItem('subMenuBullet', 'Level 2', 9, 150, '#96D1EB', '#5DB7DE', '#', 'left');
menus[2].addItem('subMenuBullet', 'Level 2', 10, 150, '#96D1EB', '#5DB7DE', '#', 'left');
menus[2].addItem('menuItemBullet', 'Level 2', null, 150, '#96D1EB', '#5DB7DE', '#', 'left');
menus[2].writeMenu(); 
// --- END

// MARKET_INFORMATION SUBMENU
menus[3] = new menu(132, 'vertical', '#4787A9', '#4787A9');
menus[3].height = 23;
menus[3].addItem('menuItemBullet', 'Level 2', null, 132, '#96D1EB', '#5DB7DE', '#', 'left');
menus[3].addItem('menuItemBullet', 'Level 2', null, 132, '#96D1EB', '#5DB7DE', '#', 'left');
menus[3].addItem('menuItemBullet', 'Level 2', null, 132, '#96D1EB', '#5DB7DE', '#', 'left');
menus[3].addItem('menuItemBullet', 'Level 2', null, 132, '#96D1EB', '#5DB7DE', '#', 'left');
menus[3].addItem('menuItemBullet', 'Level 2', null, 132, '#96D1EB', '#5DB7DE', '#', 'left');
menus[3].writeMenu(); 
// --- END

// ONLINE_TRADING SUBMENU
menus[4] = new menu(150, 'vertical', '#4787A9', '#4787A9');
menus[4].height = 20;
menus[4].addItem('menuItemBullet', 'Level 2', null, 150, '#96D1EB', '#5DB7DE', '#', 'left');
menus[4].addItem('menuItemBullet', 'Level 2', null, 150, '#96D1EB', '#5DB7DE', '#', 'left');
menus[4].writeMenu(); 
// --- END

// ENQUIRIES_AND_REPORTS SUBMENU
menus[5] = new menu(137, 'vertical', '#4787A9', '#4787A9');
menus[5].height = 20;
menus[5].addItem('menuItemBullet', 'Level 2', null, 137, '#96D1EB', '#5DB7DE', '#', 'left');
menus[5].addItem('menuItemBullet', 'Level 2', null, 137, '#96D1EB', '#5DB7DE', '#', 'left');
menus[5].addItem('menuItemBullet', 'Level 2', null, 137, '#96D1EB', '#5DB7DE', '#', 'left');
menus[5].addItem('subMenuBullet', 'Level 2', 11, 137, '#96D1EB', '#5DB7DE', '#', 'left');
menus[5].addItem('menuItemBullet', 'Level 2', null, 137, '#96D1EB', '#5DB7DE', '#', 'left');
menus[5].writeMenu(); 
// --- END

// DEAL_CALCULATOR SUBMENU
menus[6] = new menu(112, 'vertical', '#4787A9', '#4787A9');
menus[6].height = 20;
menus[6].addItem('menuItemBullet', 'Level 2', null, 112, '#96D1EB', '#5DB7DE', '#', 'left');
menus[6].addItem('menuItemBullet', 'Level 2', null, 112, '#96D1EB', '#5DB7DE', '#', 'left');
menus[6].writeMenu(); 
// --- END

// DRILL DOWN MENU 1 FOR MY_AGX - MY REGISTRATION
menus[7] = new menu(170, 'vertical', '#4787A9', '#4787A9');
menus[7].height = 20;
menus[7].addItem('menuItemBullet', 'Level 3', null, 170, '#96D1EB', '#5DB7DE', '#', 'left');
menus[7].addItem('menuItemBullet', 'Level 3', null, 170, '#96D1EB', '#5DB7DE', '#', 'left');

menus[7].writeMenu();
// --- END

// DRILL DOWN MENU 2 FOR MY_AGX - MY TRADING ACCOUNTS
menus[8] = new menu(205, 'vertical', '#4787A9', '#4787A9');
menus[8].height = 20;
menus[8].addItem('menuItemBullet', 'Level 3', null, 205, '#96D1EB', '#5DB7DE', '#', 'left');
menus[8].addItem('menuItemBullet', 'Level 3', null, 205, '#96D1EB', '#5DB7DE', '#', 'left');
menus[8].addItem('menuItemBullet', 'Level 3', null, 205, '#96D1EB', '#5DB7DE', '#', 'left');
menus[8].writeMenu();
// --- END

// DRILL DOWN MENU 3 FOR MY_AGX - MY MESSAGES
menus[9] = new menu(115, 'vertical', '#4787A9', '#4787A9');
menus[9].height = 20;
menus[9].addItem('menuItemBullet', 'Level 3', null, 115, '#96D1EB', '#5DB7DE', '#', 'left');
menus[9].addItem('menuItemBullet', 'Level 3', null, 115, '#96D1EB', '#5DB7DE', '#', 'left');
menus[9].writeMenu();
// --- END

// DRILL DOWN MENU 4 FOR MY_AGX - MY PREFERENCES
menus[10] = new menu(165, 'vertical', '#4787A9', '#4787A9');
menus[10].height = 20;
menus[10].addItem('menuItemBullet', 'Level 3', null, 180, '#96D1EB', '#5DB7DE', '#', 'left');
menus[10].addItem('menuItemBullet', 'Level 3', null, 180, '#96D1EB', '#5DB7DE', '#', 'left');
menus[10].writeMenu();
// --- END

// DRILL DOWN MENU 5 FOR MY_AGX - POSITIONS
menus[11] = new menu(125, 'vertical', '#4787A9', '#4787A9');
menus[11].height = 20;
menus[11].addItem('menuItemBullet', 'Level 3', null, 125, '#96D1EB', '#5DB7DE', '#', 'left');
menus[11].addItem('menuItemBullet', 'Level 3', null, 125, '#96D1EB', '#5DB7DE', '#', 'left');
menus[11].writeMenu();
// --- END

menus[1].align='left';
menus[2].align='left';
menus[3].align='left';
menus[4].align='left';
menus[5].align='left';
menus[6].align='left';
menus[7].align='right';
menus[8].align='right';
menus[9].align='right';
menus[10].align='right';
menus[11].align='right';

</script>
<!-- END DHTML DROP DOWN MENU -->

<!------------------------------------------------------------------------------------------------------------->