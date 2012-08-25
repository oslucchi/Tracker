<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="pages/style.css"/>
	<link rel="stylesheet" type="text/css" href="ext/dhtmlxMenu/skins/dhtmlxmenu_dhx_skyblue.css">

	<script src="ext/dhtmlxMenu/dhtmlxcommon.js"></script>
	<script src="ext/dhtmlxMenu/dhtmlxmenu.js"></script>
	<script src="ext/dhtmlxMenu/ext/dhtmlxmenu_ext.js"></script>

	<link rel="Shortcut Icon" href="images/tracker.ico" type="image/x-icon" />
	<title>Tracker</title>
</head>
<body>
	<div class="logo" id="logo" style="height: 180px; float: left;">
		<div id="image" style="height:130px;"><img src="images/trackerSmall.bmp"/></div>
		You are in: <s:property value="currentOption" /><BR>
		<div id="menuObj" style="height:25px;"></div>
	</div>
	<div class="search" id="search" style="height: 180px;">
	this is the search box
	</div>
	<div class="app" id="app">
	</div>
	<script>
		var menu = new dhtmlXMenuObject("menuObj");
		menu.setIconsPath("ext/dhtmlxMenu/imgs/");
		menu.loadXMLString("<s:property value='XMLMenu' escape='false'/>");
	</script>
</body>
</html>