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
    	<script type="text/javaScript" src="scripts/tracker.js"></script>
		<link rel="Shortcut Icon" href="images/tracker.ico" type="image/x-icon" />
		<title>Stealth submit</title>
	</head>
	<body>
		<div class='logo' id='logo' style='height: 180px; float: left;'>
			<div id='image' style='height:130px;'><img src='images/trackerSmall.bmp'/></div>
			<div id='menuObj' style='height:25px;'></div>
			Submitting an issue for: <s:property value="appName" /><BR>
		</div>
		<div class='search' id='search' style='height: 180px;'>
		</div>
		
		<div class='app' id='app'>
		<s:form action="stealthSubmitAction" method="post">
			<input type='hidden' name='appName' id='appName' value='<s:property value="appName" />' />
			<input type='hidden' name='appId' id='appId' value='<s:property value="appId" />' />
			<table>
				<tr>
					<td>
					<div id='Impact'>
						<s:iterator id="impactGroup" value="impactList">
							<table class='impact'>
								<col width=20>
								<col width=200>
								<col width=93>
								<col width=21>
								<tbody>
									<tr>
										<td colspan='4' class='impactGroupTitle'> <s:property value="#impactGroup.impactName" /> </td>
									</tr>
									<s:iterator id="impactGroupItem" value="#impactGroup.items">
										<s:set name="listSize" value="#impactGroupItem.selectionListSize" />
										<s:set name="firstRow" value="true" />
										<s:iterator id="impactGroupItemCB" value="#impactGroupItem.selectionList"> 
											<tr>
												<s:if test="#firstRow == true" >
													<td class='impactItemRow' rowspan='<s:property value="#listSize"/>' >
														&nbsp;
													</td>
													<td class='<s:if test="#listSize > 1">impactItemRow</s:if><s:else>impactItemRowSingle</s:else>' rowspan='<s:property value="#listSize"/>' >
														<s:property value="#impactGroupItem.name" />
														<s:if test="#impactGroupItem.subDesc != null"> 
															<BR>
															<font style='font-size: 8px'>
																<s:property value="#impactGroupItem.subDesc" />
															</font>
														</s:if>
													</td>
												</s:if>
												<td class='<s:if test="#listSize > 1">impactItemSelect</s:if><s:else>impactItemRowSingle</s:else>'>
													<s:property value="#impactGroupItemCB.name" /> 
												</td>
												<td class='impactItemRow'> 
													<input type='radio' 
														name='<s:property value="#impactGroupItemCB.groupName" />' 
														value='<s:property value="#impactGroupItemCB.value" />'
														<s:if test="#impactGroupItemCB.defaultOpt == true" > cheked='checked' </s:if> 
														onClick="CalculatePrio('<s:property value="#impactGroupItemCB.value" />')" />
												</td>
											</tr>
											<s:set name="firstRow" value="false" />
										</s:iterator>
									</s:iterator>
								</tbody>
							</table>
							<br>
						</s:iterator>
					</div>
					</td>
					<td style='vertical-align: top;'>
						<table class='impact'>
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
								<td class='impactGroupTitle' colspan='<%= maxDetailsNo + 2 %>'> 
									Issue Details 
								</td>
							</tr>
							<s:if test="severityRequired == true">
								<tr>
									<td >Severity/Priority</td>
									<td colspan='<%= maxDetailsNo %>' >
										<input id='severity' type='text' onChange='CantChange()' 
											   style="background-color: rgb(0, 215, 0); text-align: center; color: white;"
											   size=10 value='LOW' name='severity'>
										/
										<input id='priority' type='text' onChange='CantChange()'
											   style="background-color: rgb(0, 215, 0); text-align: center; color: white;"
											   size=10 value='LOW' name='priority'>
									</td>
								</tr>
							</s:if>
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
												type='<s:property value="#issueDetail.controlType" />'
												<s:if test="#issueDetail.infoText != null" >
				onClick="SetTextInfoVisibiliy('<s:property value="#issueDetItem.name" />',
											  '<s:property value="#issueDetail.infoText.showIfOneOf" />',
											  '<s:property value="#issueDetail.infoText.name" />',
											  <s:property value="#issueDetail.infoText.clearOnHide" />)"
												</s:if>
												value='<s:property value="#issueDetItem.name" />'
												name='<s:property value="#issueDetail.groupName" />'
											>
											<s:property value="#issueDetItem.name" />
										</td>
									</s:iterator>
									<td colspan='<%= colSpan %>'>
										<s:if test="#issueDetail.infoText != null" >
											<input 
												id='<s:property value="#issueDetail.infoText.name" />'
												type='text'
												size='<s:property value="#issueDetail.infoText.fieldLength" />'
												value=''
												name='<s:property value="#issueDetail.infoText.name" />'
												style="visibility: hidden;" />
										</s:if>
										<s:else>
											&nbsp;
										</s:else>
									</td>
								</tr>
							</s:iterator>
							<tr>
								<td>Subject</td>
								<td colspan='8'>
									<input id='subject' type='text' size='83' name='subject'>
								</td>
							</tr>
							<tr>
								<td>Details</td>
								<td colspan='8'>
									<textarea id='details' rows='13' cols='63' name='details'></textarea>
								</td>
							</tr>
							<tr>
								<td>Attachments</td>
								<td colspan='8'>
						            <INPUT id='my_file_element' name='file_1' type='file' size=65/>
									<DIV id='files_list'></DIV>
									<SCRIPT>
										var multi_selector = new 
												MultiSelector(document.getElementById('files_list'), 5);
												multi_selector.addElement(
															document.getElementById('my_file_element'));
						            </SCRIPT>
								</td>
							</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan='2' style='text-align: left;'>
						<s:submit key="Submit" align="center" />
					</td>
				</tr>
			</table>
			<s:if test="severityRequired == true">
				<input type='hidden' id='severityVal' value='<s:property value="initialSeve" />' />
				<input type='hidden' id='severityLow' value='<s:property value="severityLow" />' />
				<input type='hidden' id='severityMed' value='<s:property value="severityMed" />' />
				<input type='hidden' id='severityHig' value='<s:property value="severityHig" />' />
				<input type='hidden' id='priorityVal' value='<s:property value="initialPrio" />' />
				<input type='hidden' id='priorityLow' value='<s:property value="priorityLow" />' />
				<input type='hidden' id='priorityMed' value='<s:property value="priorityMed" />' />
				<input type='hidden' id='priorityHig' value='<s:property value="priorityHig" />' />
				<input type='hidden' id='prioValueList' value='<s:property value="prioValueList" />' />
			</s:if>	
		</s:form>
		</div>
		<script>
			var menu = new dhtmlXMenuObject("menuObj");
			menu.setIconsPath("ext/dhtmlxMenu/imgs/");
			menu.loadXMLString("<s:property value='XMLMenu' escape='false'/>");
		</script>
	</body>
</html>