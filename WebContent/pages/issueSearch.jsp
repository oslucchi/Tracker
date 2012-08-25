<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="pages/style.css"/>
		<link rel="stylesheet" type="text/css" href="pages/calendar.css"/>
		<link rel="stylesheet" type="text/css" href="ext/dhtmlxMenu/skins/dhtmlxmenu_dhx_skyblue.css">
	
		<script src="ext/dhtmlxMenu/dhtmlxcommon.js"></script>
		<script src="ext/dhtmlxMenu/dhtmlxmenu.js"></script>
		<script src="ext/dhtmlxMenu/ext/dhtmlxmenu_ext.js"></script>
    	<script type="text/javaScript" src="scripts/tracker.js"></script>
    	<script type='text/javaScript' src='scripts/calendar_us.js'></script>
		<link rel="Shortcut Icon" href="images/tracker.ico" type="image/x-icon" />
		<title>Stealth submit</title>
	</head>
	<body>
		<div class='logo' id='logo' style='height: 180px; float: left;'>
			<div id='image' style='height:130px;'><img src='images/trackerSmall.bmp'/></div>
			<font size="3">Currently on: <s:property value="appName" /> </font> <BR>
			<div id='menuObj' style='height:25px;'></div>
		</div>
		<div class='search' id='search' style='height: 180px;'>
		<form id='<s:property value="appPrefix" />SearchAction' 
			  name='<s:property value="appPrefix" />SearchAction'
			  onsubmit='return true;' 
			  action='/Tracker/<s:property value="appPrefix" />SearchAction.html' 
			  method='post' >
			<input type='hidden' name='appName' id='appName' value='<s:property value="appName" />' />
			<input type='hidden' name='appId' id='appId' value='<s:property value="appId" />' />
			<table>
				<tr>
					<td>
						<table class='impact' style='border-style: none'>
							<col style='width: 96pt;'>
							<s:set name="maxDetailsNo" value="maxDetailsNo" scope="request" />
							<%
								int colSpan = 0;
								int maxDetailsNo = ((Integer) request.getAttribute("maxDetailsNo")).intValue();
								int colSize = 500 / maxDetailsNo;
								for(int i = 0; i < maxDetailsNo; i++)
								{
							%>
									<col style='width: <%= colSize %>pt'>
							<%
								}
							%>
							<tbody>
							<tr>
								<td>From</td>
								<td colspan='<%= maxDetailsNo / 2 %>' style='vertical-align: middle;'>
									<input type='text' name='calFrom' size=10 />
									<script language='JavaScript'>
										new tcal (
											{
												'formname': '<s:property value="appPrefix" />SearchAction',
												'controlname': 'calFrom'
											}
										);
									</script>
								</td>
								<td>To</td>
								<td colspan='<%= maxDetailsNo - (maxDetailsNo /2) %>' style='vertical-align: middle;'>
									<input type='text' name='calTo' size=10 />
									<script language='JavaScript'>
										new tcal (
											{
												'formname': '<s:property value="appPrefix" />SearchAction',
												'controlname': 'calTo'
											}
										);
									</script>
								</td>
							</tr>
							<tr>
								<td>Keywords</td>
								<td colspan='<%= maxDetailsNo %>'>
									<input id='keyword' type='text' size='83' name='keyword'>
								</td>
							</tr>
							<tr>
								<td>Status</td>
								<td>
									<input id='status' type='radio' value='all' name='status' checked='checked'>
									All
								</td>
								<td>
									<input id='status' type='radio' value='opened' name='status' >
									Open
								</td>
								<td>
									<input id='status' type='radio' value='closeed' name='status' >
									Close
								</td>
								<td colspan='<%= maxDetailsNo - 3 %>'>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td>Issue ID</td>
								<td>
									<input id="issueId" type="text" onkeydown="return ChkKeyInput(event);" title="Numeric id" maxlength="5" size="5" name="issueId">
								</td>
								<td colspan='<%= maxDetailsNo - 1 %>'>
									&nbsp;
								</td>
							</tr>
							<s:iterator id="issueDetail" value="detailsList">
								<tr>
									<td>
										<s:property value="#issueDetail.name" />
									</td>
									<s:set name="listSize" value="#issueDetail.itemsSize" />
									<%
										colSpan = maxDetailsNo - 
												  ((Integer) request.getAttribute("listSize")).intValue();
									%>
									<s:iterator id="issueDetItem" value="#issueDetail.items">
										<td>
											<input 
												id='<s:property value="#issueDetail.groupName" />_<s:property value="#issueDetItem.id" />'
												type='checkbox'
												value='<s:property value="#issueDetItem.name" />'
												name='<s:property value="#issueDetail.groupName" />'
											>
											<s:property value="#issueDetItem.name" />
										</td>
									</s:iterator>
									<td colspan='<%= colSpan %>'>
										&nbsp;
									</td>
								</tr>
							</s:iterator>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan='2' style='text-align: left;'>
						<s:submit key="Search" align="center" />
					</td>
				</tr>
			</table>
		</form>
		</div>
		
		<div>
			<br>
			<br>
		</div>
		
		<div class='app' id='app'>
			<form id='<s:property value="appPrefix" />ListAction' 
				  name='<s:property value="appPrefix" />ListAction'
				  onsubmit='return true;' 
				  action='/Tracker/<s:property value="appPrefix" />ListAction.html' 
				  method='post' >
			<s:iterator id="issueItem" value="issueList">
				<table style='width: 100%; border-width: 1px; border-style: solid; border-collapse: collapse'>
				<colgroup>
					<col width='30' />
					<col width='450' />
					<col width='30' />
					<col width='150' />
					<col width='150' />
					<col width='150' />
					<col width='80' />
				</colgroup>
				<thead>
					<tr style='border-style: solid; border-collapse: collapse'>
						<th style='display: none;'>hidden</th>
						<th style='text-align: right;'>Id</th>
						<th>Subject</th>
						<th style='text-align: center;'>ETA</th>
						<th>Issued</th>
						<th>Acked</th>
						<th>Closed</th>
						<th style='text-align: center;'>Actions</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style='display: none;'>
							<s:property value="#issueItem.mailSubBy" />
						</td>
						<td>
							<s:property value="#issueItem.id" />
						</td>
						<td>
							<s:property value="#issueItem.subject" />
						</td>
						<td>
							<s:property value="#issueItem.eta" />
						</td>
						<td>
							<s:property value="#issueItem.submitted" />
						</td>
						<td>
							<s:property value="#issueItem.acknowledged" />
						</td>
						<td>
							<s:property value="#issueItem.closed" />
						</td>
						<td>
							<table>
								<tr>
									<td>
										<a href='' 
							onClick='SpawnComments(<s:property value="#issueItem.id" />, "A");'>A</a>
									</td>
									<td>
										<a href='' 
							onClick='SpawnComments(<s:property value="#issueItem.id" />, "V");'>V</a>
									</td>
									<td>
										<a href='' 
							onClick='SpawnComments(<s:property value="#issueItem.id" />, "C");'>C</a>
									</td>
									<td>
										<a href='' 
							onClick='SpawnComments(<s:property value="#issueItem.id" />, "W");'>W</a>
									</td>
									<td>
										<a href='' 
							onClick='SpawnComments(<s:property value="#issueItem.id" />, "X");'>X</a>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</tbody>
				</table>
			</s:iterator>
			</form>
		</div>
		<script>
			var menu = new dhtmlXMenuObject("menuObj");
			menu.setIconsPath("ext/dhtmlxMenu/imgs/");
			menu.loadXMLString("<s:property value='XMLMenu' escape='false'/>");
		</script>
	</body>
</html>